package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusRefund;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.mapper.BusGuaranteeFeeInfoMapper;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusMessageReminderMapper;
import com.nongyeos.loan.admin.mapper.BusRefundMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IRefundService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Service("refundService")
public class RefundServiceImpl implements IRefundService {

	@Autowired
	private BusRefundMapper refundMapper;
	
	@Autowired
	private SysOrgMapper orgMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private BusMessageReminderMapper messageReminderMapper;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private AppApplicationMapper appLicationMapper;
	@Autowired
	private IMessageReminderService messageReminderService;
	@Autowired
	private BusGuaranteeFeeInfoMapper infoMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("退款id为空");
		}
		try {
			return refundMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除出错");
		}
		
	}

	@Override
	public int insert(BusRefund record) throws Exception {
		if(record==null){
			throw new Exception("退款模板为空");
		}
		try {
			return refundMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public int insertSelective(BusRefund record) throws Exception {
		if(record==null){
			throw new Exception("退款模板为空");
		}
		try {
			return refundMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public BusRefund selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("退款id为空");
		}
		try {
			return refundMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusRefund record) throws Exception {
		if(record==null){
			throw new Exception("退款模板为空");
		}
		try {
			return refundMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public int updateByPrimaryKey(BusRefund record) throws Exception {
		if(record==null){
			throw new Exception("退款模板为空");
		}
		try {
			return refundMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public BusRefund selectByIntoPieceId(String intoPieceId) throws Exception {
		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("退款id为空");
		}
		try {
			return refundMapper.selectByIntoPieceId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public PageBeanUtil<BusRefund> selectByPage(String personId,
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
			List<BusRefund> list = refundMapper.selectByCondition(map);
			int countNums = refundMapper.selectCountByCondition(map);
			PageBeanUtil<BusRefund> pageData = new PageBeanUtil<BusRefund>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public void refundSuccess(JSONObject resultJson) throws Exception {
		try {
			String refundId = resultJson.getString("refundId");
			String status = resultJson.getString("status");
			BusRefund refund = refundMapper.selectByPrimaryKey(refundId);
			if(refund==null){
				throw new Exception("未查询到该订单");
			}
			BusGuaranteeFeeInfo info = infoMapper.selectByIpId(refund.getIntoPieceId());
			refund.setStatus(status);
			refund.setUpdateDate(DateUtils.getNowDate());
			refundMapper.updateByPrimaryKey(refund);
			info.setStatus("R"+status);
			infoMapper.updateByPrimaryKey(info);
			BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(refund.getIntoPieceId());
			AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
			JSONObject node1 = flowMgrImpl.getNextTask(entry);
			if(node1 != null && node1.getIntValue("code") == 200){
				JSONArray arr = node1.getJSONArray("nodes");
				if(arr != null){
					String nextNodeId = arr.getJSONObject(0).getString("nodeId");
					String pcId = node1.getString("pcId");
					BusinessObject business = new BusinessObject(entry,ip,appLicationMapper.selectByPrimaryKey(ip.getModelId()));
					flowMgrImpl.next(business, nextNodeId, pcId, ip.getOperUserId());
					if("S".equals(status)){
						messageReminderService.saveRefund(ip.getChannel(), ip.getId(), refund.getTotalAmount().toString());
					}
				}	
			}else{
				throw new Exception("未配置流程节点");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BusRefund> queryList(String personId, Map<String, Object> map)
			throws Exception {
		try {
			List<SysOrg> orgList = orgMapper.selectOrgsByPerson(personId);
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < orgList.size(); i++) {
				idList.add(orgList.get(i).getOrgId());
			}
			map.put("orgIds", idList);
			return  refundMapper.selectByCondition(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
}
