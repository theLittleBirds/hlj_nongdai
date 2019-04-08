package com.nongyeos.loan.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiPyReport;
import com.nongyeos.loan.admin.entity.ApiThirdMsg;
import com.nongyeos.loan.admin.entity.BusFamilyCredit;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusRejectReason;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.ApiPyReportMapper;
import com.nongyeos.loan.admin.mapper.ApiThirdMsgMapper;
import com.nongyeos.loan.admin.mapper.BusFamilyCreditMapper;
import com.nongyeos.loan.admin.mapper.BusFamilySituationMapper;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IRejectReasonService;
import com.nongyeos.loan.base.util.AutoRejectUtil;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("familySituationService")
public class FamilySituationServiceImpl implements IFamilySituationService {
	@Autowired
	private BusFamilySituationMapper familySituationMapper;
	
	@Autowired
	private ApiThirdMsgMapper thirdMsgMapper;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private SysWebUserMapper userMapper;
	
	@Autowired
	private IRejectReasonService reasonService;
	
	@Autowired
	private ApiPyReportMapper pyReportMapper;

	@Override
	public BusFamilySituation selectByIntopIdAndType(
			BusFamilySituation familySituation) throws Exception{
		if(familySituation==null){
			throw new Exception("家庭成员模板为空！");
		}
		BusFamilySituation busFamilySituation=null;
		try {
			busFamilySituation= familySituationMapper.selectByIntopIdAndType(familySituation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busFamilySituation;
	}

	@Override
	public Map<String, Object> saveOrUpdate(BusFamilySituation selectedRecord)
			throws Exception {
		if(selectedRecord==null){
			throw new  Exception("家庭成员为空！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String intoPieceId = selectedRecord.getIntoPieceId();
			//false代表未被拒绝
			boolean flag = false;
			int need =1;
			if(selectedRecord.getName()!=null&&!selectedRecord.getName().contains("测试")&&!selectedRecord.getName().contains("TEST")){
				if(!StringUtils.isEmpty(selectedRecord.getIdCard())&&!StringUtils.isEmpty(selectedRecord.getName())&&!StringUtils.isEmpty(selectedRecord.getPhone())){
					ApiPyReport pysql = new ApiPyReport();
					pysql.setIdCardNo(selectedRecord.getIdCard());
					ApiPyReport pyLast=pyReportMapper.selectByIDC(pysql);
					if(pyLast!=null){
						Integer rejectFlag = pyLast.getRejectFlag();
						if(rejectFlag==Constants.REJECT_FLAG_YES){
							long time = pyLast.getCreatetime().getTime()+30*24*60*60*1000l;
							if(time>System.currentTimeMillis()){
								String date = DateUtils.format(new Date(time), "MM月dd日");
								throw new Exception("不符合进件条件，请在"+date+"之后再次尝试申请！");
							}
						}else{
							long time = pyLast.getCreatetime().getTime()+7*24*60*60*1000l;
							if(time>System.currentTimeMillis()){
								if(StringUtils.isEmpty(selectedRecord.getId())){
									selectedRecord.setId(UUIDUtil.getUUID());
									BusFamilySituation selectByIntopIdAndType = familySituationMapper.selectByIntopIdAndType(selectedRecord);
									if(selectByIntopIdAndType==null){
										if(!pyLast.getIntoPieceId().equals(intoPieceId)){
											pyLast.setId(UUIDUtil.getUUID());
											pyLast.setIntoPieceId(intoPieceId);
											pyReportMapper.addPyReportSelective(pyLast);
										}
										familySituationMapper.insert(selectedRecord);
									}else{
										throw new Exception("请不要重复提交！");
									}
								}else{
									familySituationMapper.updateByPrimaryKey(selectedRecord);
								}
								need=0;
							}
						}
					}
					if(need==0){
						result.put("familySituation", selectedRecord);
						result.put("reject", flag);
						return result;
					}
					SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", intoPieceId);
					map.put("memberName", selectedRecord.getName());
					map.put("idCard", selectedRecord.getIdCard());
					map.put("phone", selectedRecord.getPhone());
					map.put("userid", user.getUserId());
					String signature = user.getUserId() + user.getUsername() + user.getPassword();
					map.put("signature", DigestUtils.md5Hex(signature));
					map.put("rejectKey", "rejectKey");
					String stringPengyuan = HttpClientUtil.doPost(pieceConfig.getPengyuanurl(), map, "utf-8");
					if(StringUtils.isEmpty(stringPengyuan)){
						throw new Exception("鹏元数据查询为空！");
					}
					JSONObject resultJson = JSONObject.parseObject(stringPengyuan);
					if(!resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
						throw new Exception("鹏元数据查询失败！");
					}
					JSONObject pengyuanJson = resultJson.getJSONObject(Constants.COUNT_JSON);
					Map<String, Object> rejectReasonpengyuan = AutoRejectUtil.pengyuanReject(pengyuanJson,intoPieceId,selectedRecord.getIdCard());
					if(rejectReasonpengyuan==null){
						throw new Exception("数据分析出错！");
					}
					ApiPyReport pyReport = new ApiPyReport();
					if(!(Boolean)rejectReasonpengyuan.get("rejected")){
						flag=true;
						reasonService.addRejectReason((List<BusRejectReason>)rejectReasonpengyuan.get("reject_reason"));
						pyReport.setRejectFlag(Constants.REJECT_FLAG_YES);
					}else{
						pyReport.setRejectFlag(Constants.REJECT_FLAG_NO);
					}
					pyReport.setIdCardNo(selectedRecord.getIdCard());
					pyReport.setIntoPieceId(intoPieceId);
					pyReportMapper.updateByIntoPAndIDCSelective(pyReport);
					
				}
			}
			if(StringUtils.isEmpty(selectedRecord.getId())){
				selectedRecord.setId(UUIDUtil.getUUID());
				familySituationMapper.insert(selectedRecord);
			}else{
				familySituationMapper.updateByPrimaryKey(selectedRecord);
			}
			result.put("familySituation", selectedRecord);
			result.put("reject", flag);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<BusFamilySituation> thirdpartycredit(String intoPieceId)
			throws Exception {
		if(StrUtils.isEmpty(intoPieceId)){
			throw new Exception("进件标识为空！");
		}
		try {
			return familySituationMapper.thirdpartycredit(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusFamilySituation> queryByIntopId(
			BusFamilySituation familySituation) throws Exception {
		try {
			if(familySituation == null){
				throw new Exception("家庭成员对象为空");
			}
			return familySituationMapper.queryByIntopId(familySituation);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusFamilySituation> queryCoBorrower(
			BusFamilySituation familySituation) throws Exception {
		try {
			if(familySituation == null){
				throw new Exception("家庭成员对象为空");
			}
			return familySituationMapper.queryCoBorrower(familySituation);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	public List<Object> getSonEntryByParentItemId(String mainId,SqlSession session) throws Exception{
		List<Object> list = null;
		try{
			if(mainId != null && !mainId.equals("")){
				list = session.getMapper(BusFamilySituationMapper.class).selectByParentItemId(mainId);
			}
		}catch(Exception e){
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	public void updateBean(Object record,SqlSession session) throws Exception{
		try{
			if(record != null){
				session.getMapper(BusFamilySituationMapper.class).updateByPrimaryKeySelective((BusFamilySituation) record);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("record为空");
		}
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "0b118bdd92354c72822969d7e1763dfa");
		map.put("memberName", "白金锁");
		map.put("idCard", "152322197210204054");
		map.put("phone", "15545454545");
		map.put("userid", "U000000017");
		String signature = "U000000017" + "admin" + "038bdaf98f2037b31f1e75b5b4c9b26e";
		map.put("signature", DigestUtils.md5Hex(signature));
		map.put("rejectKey", "rejectKey");
		String stringPengyuan = HttpClientUtil.doPost("http://127.0.0.1:8086/apiPengYuan/pengyuanReport", map, "utf-8");
		 if(StringUtils.isEmpty(stringPengyuan)){
			 throw new Exception("鹏元数据查询为空！");
		 }
		 JSONObject resultJson = JSONObject.parseObject(stringPengyuan);
		 if(!resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("鹏元数据查询失败！");
			}
		 JSONObject pengyuanJson = resultJson.getJSONObject(Constants.COUNT_JSON);
		 System.err.println(pengyuanJson);
	}

	@Override
	public BusFamilySituation selectByIntopIdAndSeqno(
			BusFamilySituation familySituation) throws Exception {
		if(familySituation==null){
			throw new Exception("家庭成员模板为空！");
		}
		try {
			return familySituationMapper.selectByIntopIdAndSeqno(familySituation);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int insert(BusFamilySituation record) throws Exception {
		if(record==null){
			throw new Exception("家庭成员模板为空！");
		}
		try {
			return familySituationMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int updateByPrimaryKey(BusFamilySituation record) throws Exception {
		if(record==null){
			throw new Exception("家庭成员模板为空！");
		}
		try {
			return familySituationMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
