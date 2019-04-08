package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.mapper.BusGuaranteeFeeInfoMapper;
import com.nongyeos.loan.admin.mapper.BusGuaranteeReverseMapper;
import com.nongyeos.loan.admin.mapper.BusMessageReminderMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("/guaranteeFeeInfoService")
public class GuaranteeFeeInfoServiceImpl implements IGuaranteeFeeInfoService {

	@Autowired
	private BusGuaranteeFeeInfoMapper guaranteeFeeInfoMapper;
	
	@Autowired
	private SysOrgMapper orgMapper;
	
	@Autowired
	private BusMessageReminderMapper messageReminderMapper;
	
	@Autowired
	private BusGuaranteeReverseMapper guaranteeReverseMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("服务费标识为空");
		}
		try {
			return guaranteeFeeInfoMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败");
		}
	}

	@Override
	public int insert(BusGuaranteeFeeInfo record) throws Exception {
		if(record==null){
			throw new Exception("服务费模板为空");
		}
		try {
			return guaranteeFeeInfoMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	@Override
	public int insertSelective(BusGuaranteeFeeInfo record) throws Exception {
		if(record==null){
			throw new Exception("服务费模板为空");
		}
		try {
			return guaranteeFeeInfoMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	@Override
	public BusGuaranteeFeeInfo selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("服务费标识为空");
		}
		try {
			return guaranteeFeeInfoMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusGuaranteeFeeInfo record)
			throws Exception {
		if(record==null){
			throw new Exception("服务费模板为空");
		}
		try {
			return guaranteeFeeInfoMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	@Override
	public int updateByPrimaryKey(BusGuaranteeFeeInfo record) throws Exception {
		if(record==null){
			throw new Exception("服务费模板为空");
		}
		try {
			return guaranteeFeeInfoMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	@Override
	public PageBeanUtil<BusGuaranteeFeeInfo> selectByPage(String personId,
			Map<String, Object> map, int currentPage, int pageSize)
			throws Exception {
		try {		
			List<SysOrg> orgList = orgMapper.selectOrgsByPerson(personId);
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < orgList.size(); i++) {
				idList.add(orgList.get(i).getOrgId());
			}
			map.put("orgIds", idList);
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusGuaranteeFeeInfo> list = guaranteeFeeInfoMapper.selectByCondition(map);
			int countNums = guaranteeFeeInfoMapper.selectCountByCondition(map);
			PageBeanUtil<BusGuaranteeFeeInfo> pageData = new PageBeanUtil<BusGuaranteeFeeInfo>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusGuaranteeFeeInfo selectByIntopieceId(String intoPieceId)
			throws Exception {
		try {
			return guaranteeFeeInfoMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误");
		}
	}

	@Override
	public List<BusGuaranteeFeeInfo> queryList(String personId,Map<String, Object> map)
			throws Exception {
		try {
			List<SysOrg> orgList = orgMapper.selectOrgsByPerson(personId);
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < orgList.size(); i++) {
				idList.add(orgList.get(i).getOrgId());
			}
			map.put("orgIds", idList);
			return guaranteeFeeInfoMapper.selectByCondition(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	
	/**
	 * 微信支付扣款异步回调删除首页消息及将状态置为支付成功（服务费支付及反担保金支付）
	 */
	@Override
	public void payNotice(JSONObject resultJson) throws Exception {
		String guaranteeId = resultJson.getString("guaranteeId");
		String status = resultJson.getString("status");
		System.err.println("异步回调的json"+resultJson);
		System.err.println("guaranteeId"+guaranteeId);
		System.err.println("status"+status);
		BusGuaranteeFeeInfo info = guaranteeFeeInfoMapper.selectByPrimaryKey(guaranteeId);
		System.err.println("info"+info);
		BusGuaranteeReverse reverse = null;
		if(info==null){
			reverse=guaranteeReverseMapper.selectByPrimaryKey(guaranteeId);
			if(reverse==null){
				throw new Exception("未查询到该订单");
			}
			
		}
		if(info!=null){
			info.setStatus(status);
			info.setUpdateDate(DateUtils.getNowDate());
			guaranteeFeeInfoMapper.updateByPrimaryKey(info);
			if("S".equals(status)){
				BusMessageReminder reminder = new BusMessageReminder();
				reminder.setIntoPieceId(info.getIntoPieceId());
				if(info.getPayWay().equals("5")){
					reminder.setType(Constants.XIAO_CHENG_XU_SERVICE_PAY);
				}else if(info.getPayWay().equals("2")){
					reminder.setType(Constants.WEI_XIN_SERVICE_PAY);
				}
				reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
				BusMessageReminder message = messageReminderMapper.queryMRByTypeAndDelete(reminder);
				if(message!=null){
					message.setIsDelete(Constants.COMMON_IS_DELETE);
					messageReminderMapper.updateByPrimaryKey(reminder);
				}
			}
		}else{
			reverse.setStatus(status);
			reverse.setUpdateDate(DateUtils.getNowDate());
			guaranteeReverseMapper.updateByPrimaryKey(reverse);
			if("S".equals(status)){
				BusMessageReminder reminder = new BusMessageReminder();
				reminder.setIntoPieceId(reverse.getIntoPieceId());
				reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
				//客户
				if(reverse.getType().equals(1)){
					reminder.setType(Constants.MGUATANTEE_REVERSE);
					//站长
				}else{
					reminder.setType(Constants.UGUATANTEE_REVERSE);
				}
				BusMessageReminder message = messageReminderMapper.queryMRByTypeAndDelete(reminder);
				if(message!=null){
					message.setIsDelete(Constants.COMMON_IS_DELETE);
					messageReminderMapper.updateByPrimaryKey(reminder);
				}
			}
		}
		
	}
}
