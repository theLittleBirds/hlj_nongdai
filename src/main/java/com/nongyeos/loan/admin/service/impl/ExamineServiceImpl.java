package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusExamine;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.mapper.BusExamineMapper;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.service.IExamineService;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Service("examineService")
public class ExamineServiceImpl implements IExamineService {
	
	@Autowired
	private BusExamineMapper examineMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private BusLoanMapper loanMapper;
	
	@Autowired
	private IMediaService mediaService;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private AppApplicationMapper applicationMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusExamine record) throws Exception {
		if(record == null){
			throw new Exception("审核记录模板为空");
		}
		if(record.getId() == null){
			throw new Exception("审核记录模板id为空");
		}
		try {
			return examineMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusExamine> historyByIpId(String intoPieceId) throws Exception{
		if(intoPieceId == null || "".equals(intoPieceId)){
			throw new Exception("进件标识为空为空");
		}
		try {
			return examineMapper.historyByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	/**
	 * 初审
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject primaryFormSave(BusIntoPiece ip, BusExamine e, String nextNodeId,String pcId,String personId)
			throws Exception {
		if(e == null){
			throw new Exception("审核记录为空");
		}
		examineMapper.insert(e);
		intoPieceMapper.updateByPrimaryKey(ip);
		AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
		BusinessObject business = new BusinessObject(entry,intoPieceMapper.selectByPrimaryKey(ip.getId()),applicationMapper.selectByPrimaryKey(entry.getAppId()));
		return flowMgrImpl.next(business, nextNodeId, pcId, personId);
	}
	
	/**
	 * 复审
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject reviewFormSave(BusIntoPiece ip,BusExamine e,String nextNodeId,String pcId,String personId) throws Exception {
		if(e == null){
			throw new Exception("审核记录为空");
		}
		examineMapper.insert(e);
		AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
		BusinessObject business = new BusinessObject(entry,ip,applicationMapper.selectByPrimaryKey(entry.getAppId()));
		return flowMgrImpl.next(business, nextNodeId, pcId, personId);
	}
	/**
	 * 保函出具提交
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject guaranteePushSave(BusIntoPiece ip,String nextNodeId,String pcId,String personId) throws Exception{
		AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
		BusinessObject business = new BusinessObject(entry,ip,applicationMapper.selectByPrimaryKey(entry.getAppId()));
		return flowMgrImpl.next(business, nextNodeId, pcId, personId);
	}
	/**
	 * 终审
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject lastFormSave(BusIntoPiece ip,BusExamine e,AppEntry entry,String nextNodeId,String pcId,String personId) throws Exception {
		if(e == null){
			throw new Exception("审核记录为空");
		}
		examineMapper.insert(e);		
		BusinessObject business = new BusinessObject(entry,ip,applicationMapper.selectByPrimaryKey(entry.getAppId()));
		return flowMgrImpl.next(business, nextNodeId, pcId, personId);
	}

	@Override
	public BusExamine selectByIpIdNode(BusExamine record) throws Exception {
		if(record == null){
			throw new Exception("审核记录模板为空");
		}
		try {
			return examineMapper.selectByIpIdNode(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusExamine selectByIpIdLast(String intoPieceId) throws Exception {
		if(intoPieceId == null){
			throw new Exception("进件标识为空");
		}
		try {
			return examineMapper.selectByIpIdLast(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询被拒原因失败");
		}
		
	}

}
