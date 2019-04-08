package com.nongyeos.loan.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.ApiPyReport;
import com.nongyeos.loan.admin.entity.ApiThirdMsg;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.admin.entity.BusRejectReason;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.ApiPyReportMapper;
import com.nongyeos.loan.admin.mapper.ApiThirdMsgMapper;
import com.nongyeos.loan.admin.mapper.BusMemberMapper;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.model.TwoElementsConfig;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.admin.service.IRejectReasonService;
import com.nongyeos.loan.base.util.AutoRejectUtil;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.HttpUtils;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;
@Service("memberService")
public class MemberServiceImpl implements IMemberService{
	@Autowired
	private BusMemberMapper busMemberMapper;
	
	@Autowired
	private SysWebUserMapper userMapper;
	
	@Autowired
	private ApiThirdMsgMapper thirdMsgMapper;
	
	@Autowired
	private IRejectReasonService reasonService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private ApiPyReportMapper pyReportMapper;
	@Autowired
	private TwoElementsConfig twoElementsConfig;

	@Override
	public PageBeanUtil<BusMember> selectByPage(int currentPage, int pageSize, BusMember member) throws Exception {
		if(member==null){
			throw new Exception("消费者模板为空");
		}
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusMember> list = busMemberMapper.selectByPage(member);
			int countNums = busMemberMapper.selectTotalNum(member);
			PageBeanUtil<BusMember> pageData = new PageBeanUtil<BusMember>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public boolean existedSameName(BusMember member) throws Exception {
		if(member==null){
			throw new Exception("消费者模板为空");
		}
		BusMember busMember =busMemberMapper.selectByIdCard(member.getIdCard());
		if(busMember!=null){
			if(member.getMemberId()==null || member.getMemberId().equals("")){
				return true;
			}else{
				if(!member.getMemberId().equals(busMember.getMemberId())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void addMember(BusMember member, String channel) throws Exception {
		if(member==null){
			throw new Exception("消费者模板为空");
		}
		member.setMemberId(UUIDUtil.getUUID());
		member.setIsDeleted( Constants.COMMON_ISNOT_DELETE);
		member.setCreDate(DateUtils.getNowDate());
		member.setUpdDate(DateUtils.getNowDate());
		try {
			if(!member.getName().contains("测试")&&!member.getName().contains("TEST")){
//				if(!channel.equals("SX")){
//					SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("memberName", member.getName());
//					map.put("idCard", member.getIdCard());
//					map.put("bankCardNo", member.getBankCardNo());
//					map.put("phone", member.getBankPhone());
//					map.put("userid", user.getUserId());
//					String signature = user.getUserId() + user.getUsername() + user.getPassword();
//					map.put("signature", DigestUtils.md5Hex(signature));
//					String fourPro = HttpClientUtil.doPost(pieceConfig.getXianfengurl(), map, "utf-8");
//					if(StringUtils.isEmpty(fourPro)){
//						throw new Exception("四要素查询失败！");
//					}
//					JSONObject fourProJson = JSONObject.parseObject(fourPro);
//					if(!fourProJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
//						throw new Exception("四要素查询失败！");
//					}
//					String status = fourProJson.getString("status");
//					if(status.equals("F")){
//						throw new Exception("姓名、身份证号、银行卡号和手机号不一致，请您核实银行卡是否为本人银行卡、预留手机号与银行预留是否一致、身份证与本人姓名是否一致后重新填写！");
//					}
//				}


				//二要素验证
			 	 String host=twoElementsConfig.getHOST();
			 	 String path = twoElementsConfig.getPATH();
			 	 String appCode = twoElementsConfig.getAPPCODE();
				 Map<String, String> headers = new HashMap<String, String>();
				 headers.put("Authorization", "APPCODE " + appCode);
				 headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				 Map<String, String> querys = new HashMap<String, String>();
			     Map<String, String> bodys = new HashMap<String, String>();
			     bodys.put("idNo", member.getIdCard());
			     bodys.put("name", member.getName());
			     HttpResponse response2 = HttpUtils.doPost(host, path, "POST", headers, querys, bodys);
			     HttpEntity entity = response2.getEntity();
			     JSONObject json = null;
			     try {
			    	 json = JSONObject.parseObject(EntityUtils.toString(entity));
				 } catch (Exception e) {
					 throw new Exception("系统错误！");
				 }
			     if(json==null){
					 throw new Exception("身份证信息查询失败！");
			     }
			     if(!"0000".equals(json.getString("respCode"))){
					 throw new Exception(json.getString("respMessage"));
			     }
			
				
			
			}
			String id_card = member.getIdCard();
			String real = id_card.trim().replaceAll("\\s+","");
			if(real!=null&&IdCheck.isValidatedAllIdcard(real)){
				if(real.length()==15){
					real = IdCheck.convertIdcarBy15bit(real);
				}
				int age = IdCheck.getAgeByIdCard(real);
				String sex = IdCheck.getGenderByIdCard(real);
				member.setAge((short) age);
				member.setSex(Short.parseShort(sex));
			}
			busMemberMapper.insert(member);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateMember(BusMember member, String channel) throws Exception {
		if(member==null){
			throw new Exception("消费者模板为空");
		}
		member.setUpdDate(DateUtils.getNowDate());
		if(member.getDuty()==null)
			member.setDuty("");
		try {
			if(!member.getName().contains("测试")&&!member.getName().contains("TEST")){
//				if(!channel.equals("SX")){
//					SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("memberName", member.getName());
//					map.put("idCard", member.getIdCard());
//					map.put("bankCardNo", member.getBankCardNo());
//					map.put("phone", member.getBankPhone());
//					map.put("userid", user.getUserId());
//					String signature = user.getUserId() + user.getUsername() + user.getPassword();
//					map.put("signature", DigestUtils.md5Hex(signature));
//					String fourPro = HttpClientUtil.doPost(pieceConfig.getXianfengurl(), map, "utf-8");
//					if(StringUtils.isEmpty(fourPro)){
//						throw new Exception("四要素查询失败！");
//					}
//					JSONObject fourProJson = JSONObject.parseObject(fourPro);
//					if(!fourProJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
//						throw new Exception("四要素查询失败！");
//					}
//					String status = fourProJson.getString("status");
//					if(status.equals("F")){
//						throw new Exception("姓名、身份证号、银行卡号和手机号不一致，请您核实银行卡是否为本人银行卡、预留手机号与银行预留是否一致、身份证与本人姓名是否一致后重新填写！");
//					}
//				}

				//二要素验证
			 	 String host=twoElementsConfig.getHOST();
			 	 String path = twoElementsConfig.getPATH();
			 	 String appCode = twoElementsConfig.getAPPCODE();
				 Map<String, String> headers = new HashMap<String, String>();
				 headers.put("Authorization", "APPCODE " + appCode);
				 headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				 Map<String, String> querys = new HashMap<String, String>();
			     Map<String, String> bodys = new HashMap<String, String>();
			     bodys.put("idNo", member.getIdCard());
			     bodys.put("name", member.getName());
			     HttpResponse response2 = HttpUtils.doPost(host, path, "POST", headers, querys, bodys);
			     HttpEntity entity = response2.getEntity();
			     JSONObject json = null;
			     try {
			    	 json = JSONObject.parseObject(EntityUtils.toString(entity));
				 } catch (Exception e) {
					 throw new Exception("系统错误！");
				 }
			     if(json==null){
					 throw new Exception("身份证信息查询失败！");
			     }
			     if(!"0000".equals(json.getString("respCode"))){
					 throw new Exception(json.getString("respMessage"));
			     }
			
			}
			busMemberMapper.updateByPrimaryKeySelective(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(String memberId) throws Exception {
		if(memberId==null){
			throw new Exception("未指定消费者标识");
		}
		BusMember member = new BusMember();
		member.setMemberId(memberId);
		member.setIsDeleted( Constants.COMMON_IS_DELETE);
		try {
			busMemberMapper.updateByPrimaryKeySelective(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> addMemberByIntoPiece(BusIntoPiece intoPiece,String intoPieceId) throws Exception {
		if(intoPiece==null){
			throw new Exception("进件模板为空");
		}
		BusMember member = new BusMember();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//false代表通过
			boolean flag = false;
			int need = 1;
			member.setAge(intoPiece.getAge().shortValue());
			member.setBank(intoPiece.getBank());
			member.setBankPhone(intoPiece.getPhone());
			member.setBankCardNo(intoPiece.getBankCardNo());
			member.setDuty(intoPiece.getDuty());
			member.setEducationLevel(intoPiece.getEducationLevel().shortValue());
			member.setIdCard(intoPiece.getIdCard());
			member.setIdCardType(new Short("1"));
			member.setMaritalStatus(intoPiece.getMaritalStatus().shortValue());
			member.setName(intoPiece.getMemberName());
			member.setNonfarmComAddress(intoPiece.getNonfarmComAddress());
			member.setNonfarmComName(intoPiece.getNonfarmComName());
			member.setNonfarmComPhone(intoPiece.getNonfarmComPhone());
			member.setSex(intoPiece.getSex().shortValue());
			member.setUpdDate(DateUtils.getNowDate());
			member.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			member.setUpdOperId(intoPiece.getUpdOperId());
			member.setUpdOperName(intoPiece.getUpdOperName());
			member.setUpdOrgId(intoPiece.getUpdOrgId());
			BusMember selectByIdCard = busMemberMapper.selectByIdCard(member.getIdCard());
			if(selectByIdCard!=null){
				if(!selectByIdCard.getName().equals(member.getName())){
					throw new Exception("已存在客户身份证号，需要修改姓名请联系系统管理员！");
				}else{
					member.setMemberId(selectByIdCard.getMemberId());
				}
			}else{
				member.setMemberId(UUIDUtil.getUUID());
				member.setCreDate(member.getUpdDate());
				member.setCreOperId(member.getUpdOperId());
				member.setCreOperName(member.getUpdOperName());
				member.setCreOrgId(member.getUpdOrgId());
			}
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(!member.getName().contains("测试")&&!member.getName().contains("TEST")){
//				ApiThirdMsg thirdsql = new ApiThirdMsg();
//				thirdsql.setIdCardNo(member.getIdCard());
//				thirdsql.setPlatform(Constants.BAIRONG);
//				ApiThirdMsg thirdMsgLast = thirdMsgMapper.selectLastByIDC(thirdsql);
				ApiThirdMsg thirdsqltd = new ApiThirdMsg();
				thirdsqltd.setIdCardNo(member.getIdCard());
				thirdsqltd.setPlatform(Constants.TONGDUN);
				ApiThirdMsg thirdMsgLasttd = thirdMsgMapper.selectLastByIDC(thirdsqltd);
				ApiPyReport pysql = new ApiPyReport();
				pysql.setIdCardNo(member.getIdCard());
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
							pyLast.setId(UUIDUtil.getUUID());
							pyLast.setIntoPieceId(intoPieceId);
							if(ip==null){
								pyReportMapper.addPyReportSelective(pyLast);
							}
							if(selectByIdCard!=null){
								busMemberMapper.updateByPrimaryKeySelective(member);
							}else{
								busMemberMapper.insert(member);
							}
							need=0;
						}
					}
				}
//				if(thirdMsgLast!=null){
//					Integer rejectFlag = thirdMsgLast.getRejectFlag();
//					if(rejectFlag==Constants.REJECT_FLAG_YES){
//						long time = thirdMsgLast.getCreatetime().getTime()+30*24*60*60*1000l;
//						if(time>System.currentTimeMillis()){
//							String date = DateUtils.format(new Date(time), "MM月dd日");
//							throw new Exception("不符合进件条件，请在"+date+"之后再次尝试申请！");
//						}
//					}else{
//						long time = thirdMsgLast.getCreatetime().getTime()+7*24*60*60*1000l;
//						if(time>System.currentTimeMillis()){
//							thirdMsgLast.setId(UUIDUtil.getUUID());
//							thirdMsgLast.setIntoPieceId(intoPieceId);
//							if(ip==null){
//								thirdMsgMapper.addPlatformMsg(thirdMsgLast);
//							}
//							if(selectByIdCard!=null){
//								busMemberMapper.updateByPrimaryKeySelective(member);
//							}else{
//								busMemberMapper.insert(member);
//							}
//							need=0;
//						}
//					}
//				}
				if(thirdMsgLasttd!=null){
					Integer rejectFlag = thirdMsgLasttd.getRejectFlag();
					if(rejectFlag==Constants.REJECT_FLAG_YES){
						long time = thirdMsgLasttd.getCreatetime().getTime()+30*24*60*60*1000l;
						if(time>System.currentTimeMillis()){
							String date = DateUtils.format(new Date(time), "MM月dd日");
							throw new Exception("不符合进件条件，请在"+date+"之后再次尝试申请！");
						}
					}else{
						long time = thirdMsgLasttd.getCreatetime().getTime()+7*24*60*60*1000l;
						if(time>System.currentTimeMillis()){
							thirdMsgLasttd.setId(UUIDUtil.getUUID());
							thirdMsgLasttd.setIntoPieceId(intoPieceId);
							if(ip==null){
								thirdMsgMapper.addPlatformMsg(thirdMsgLasttd);
							}
							if(selectByIdCard!=null){
								busMemberMapper.updateByPrimaryKeySelective(member);
							}else{
								busMemberMapper.insert(member);
								intoPiece.setMemberId(member.getMemberId());
								intoPieceService.updateByPrimaryKeySelective(intoPiece);
							}
							need=0;
							
						}
					}
				}
				if(need==0){
					result.put("member", member);
					result.put("reject", flag);
					return result;
				}
				SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", intoPieceId);
				map.put("memberName", member.getName());
				map.put("idCard", member.getIdCard());
				if(!StringUtils.isEmpty(member.getBankCardNo())){
					map.put("bankCardNo", member.getBankCardNo());
				}
				map.put("phone", member.getBankPhone());
				map.put("userid", user.getUserId());
				String signature = user.getUserId() + user.getUsername() + user.getPassword();
				map.put("signature", DigestUtils.md5Hex(signature));
				map.put("rejectKey", "rejectKey");
//				String thirdMsgbairong = HttpClientUtil.doPost(pieceConfig.getBairongurl(), map, "utf-8");
				String thirdMsgtongdun = HttpClientUtil.doPost(pieceConfig.getTongdunurl(), map, "utf-8");
				
//				if(thirdMsgbairong==null){
//					throw new Exception("百融数据查询为空！");
//				}
				if(thirdMsgtongdun==null){
					throw new Exception("同盾数据查询为空！");
				}
//				JSONObject thirdMsgJsonbairong = JSONObject.parseObject(thirdMsgbairong);
//				if(!thirdMsgJsonbairong.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
//					throw new Exception("百融数据查询失败！");
//				}
//				JSONObject bairongJson = thirdMsgJsonbairong.getJSONObject(Constants.GATE_RESULT_JSON);
//				Map<String, Object> rejectReasonbairong = AutoRejectUtil.bairongReject(bairongJson,intoPieceId,member.getIdCard());
//				if(rejectReasonbairong==null){
//					throw new Exception("数据分析出错！");
//				}
//				ApiThirdMsg thirdsqlConditionbr = new ApiThirdMsg();
//				if(!(Boolean)rejectReasonbairong.get("rejected")){
//					flag=true;
//					reasonService.addRejectReason((List<BusRejectReason>)rejectReasonbairong.get("reject_reason"));
//					thirdsqlConditionbr.setRejectFlag(Constants.REJECT_FLAG_YES);
//				}else{
//					thirdsqlConditionbr.setRejectFlag(Constants.REJECT_FLAG_NO);
//				}
//				thirdsqlConditionbr.setIdCardNo(member.getIdCard());
//				thirdsqlConditionbr.setIntoPieceId(intoPieceId);
//				thirdsqlConditionbr.setType(1);
//				thirdMsgMapper.updateByIntoPAndIDCSelective(thirdsqlConditionbr);
				JSONObject thirdMsgJsontongdun = JSONObject.parseObject(thirdMsgtongdun);
				if(!thirdMsgJsontongdun.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
					throw new Exception("同盾数据查询失败！");
				}
				JSONObject tongdunJson = thirdMsgJsontongdun.getJSONObject(Constants.GATE_RESULT_JSON);
				Map<String, Object> rejectReasontongdun = AutoRejectUtil.tongdunReject(tongdunJson,intoPieceId,member.getIdCard());
				if(rejectReasontongdun==null){
					throw new Exception("数据分析出错！");
				}
				ApiThirdMsg thirdsqlConditiontd = new ApiThirdMsg();
				if(!(Boolean)rejectReasontongdun.get("rejected")){
					flag=true;
					reasonService.addRejectReason((List<BusRejectReason>)rejectReasontongdun.get("reject_reason"));
					thirdsqlConditiontd.setRejectFlag(Constants.REJECT_FLAG_YES);
				}else{
					thirdsqlConditiontd.setRejectFlag(Constants.REJECT_FLAG_NO);
				}
				thirdsqlConditiontd.setIdCardNo(member.getIdCard());
				thirdsqlConditiontd.setIntoPieceId(intoPieceId);
				thirdsqlConditiontd.setType(1);
				thirdMsgMapper.updateByIntoPAndIDCSelective(thirdsqlConditiontd);
				//如果同盾百融没有被拒绝则查询鹏元数据
				String stringPengyuan = HttpClientUtil.doPost(pieceConfig.getPengyuanurl(), map, "utf-8");
				if(StringUtils.isEmpty(stringPengyuan)){
					throw new Exception("鹏元数据查询为空！");
				}
				JSONObject resultJson = JSONObject.parseObject(stringPengyuan);
				if(!resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
					throw new Exception("鹏元数据查询失败！");
				}
				JSONObject pengyuanJson = resultJson.getJSONObject(Constants.COUNT_JSON);
				Map<String, Object> rejectReasonpengyuan = AutoRejectUtil.pengyuanReject(pengyuanJson,intoPieceId,member.getIdCard());
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
				pyReport.setIdCardNo(member.getIdCard());
				pyReport.setIntoPieceId(intoPieceId);
				pyReportMapper.updateByIntoPAndIDCSelective(pyReport);
			}
			
			 if(selectByIdCard!=null){
					busMemberMapper.updateByPrimaryKeySelective(member);
				}else{
					busMemberMapper.insert(member);
					if(intoPiece.getId()!=null){
						intoPiece.setMemberId(member.getMemberId());
						intoPieceService.updateByPrimaryKeySelective(intoPiece);
					}
					
				}
			 	result.put("member", member);
				result.put("reject", flag);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return result;
	}

	@Override
	public BusMember selectByIdCard(String idCard) throws Exception {
		if(StringUtils.isEmpty(idCard)){
			throw new Exception("身份证号为空！");
		}
		BusMember busMember =null;
		try {
			busMember =busMemberMapper.selectByIdCard(idCard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busMember;
	}

	@Override
	public BusMember selectByPrimaryKey(String memberId) throws Exception {
		if(StringUtils.isEmpty(memberId)){
			throw new Exception("客户标识为空！");
		}
		try {
			return busMemberMapper.selectByPrimaryKey(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("不存在该客户！");
		}
	}

	@Override
	public int insert(BusMember member) throws Exception {
		if(member == null){
			throw new Exception("客户模板！");
		}
		try {
			return busMemberMapper.insert(member);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存客户信息失败！");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "284e688365e64968901025e75623e451");
		map.put("memberName", "测试一一");
		map.put("idCard", "110000199001011112");
		/*if(!StringUtils.isEmpty(member.getBankCardNo())){
			map.put("bankCardNo", member.getBankCardNo());
		}*/
		map.put("phone", "15555555555");
		map.put("userid", "U000000017");
		String signature = "U000000017" + "admin" + "038bdaf98f2037b31f1e75b5b4c9b26e";
		map.put("signature", DigestUtils.md5Hex(signature));
		map.put("rejectKey", "rejectKey");
//		String thirdMsgbairong = HttpClientUtil.doPost(pieceConfig.getBairongurl(), map, "utf-8");
		String thirdMsgtongdun = HttpClientUtil.doPost("http://127.0.0.1:8086/apiPengYuan/pengyuanReport", map, "utf-8");
		System.err.println(thirdMsgtongdun);
	}
}
