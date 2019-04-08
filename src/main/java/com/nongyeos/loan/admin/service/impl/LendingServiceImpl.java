package com.nongyeos.loan.admin.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMoneyLog;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.mapper.BusMoneyLogMapper;
import com.nongyeos.loan.admin.service.ILendingService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Service("lendingService")
public class LendingServiceImpl implements ILendingService {
	
	@Autowired
	private BusLoanMapper loanMapper;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private AppApplicationMapper applicationMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private BusMoneyLogMapper moneyLogMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject underLineLoan(BusLoan loan,String personId) throws Exception {
		loanMapper.updateByPrimaryKeySelective(loan);
		loan = loanMapper.selectByPrimaryKey(loan.getId());
		BusMoneyLog ml = new BusMoneyLog();
		ml.setId(UUIDUtil.getUUID());
		ml.setLoanId(loan.getId());
		if(loan.getServiceFeeType() == 1){
			ml.setCapital(loan.getServiceFee() == null ? loan.getCapital() : loan.getCapital().subtract(loan.getServiceFee()));
		}else{
			ml.setCapital(loan.getCapital());
		}
		//1放款  2收款
		ml.setType(1);
		ml.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		ml.setUpdOperId(personId);
		ml.setUpdDate(new Date());
		moneyLogMapper.insert(ml);
		BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(loan.getIntoPieceId());
		AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
		JSONObject node = flowMgrImpl.getNextTask(entry);
		if(node != null && node.getIntValue("code") == 200){
			JSONArray arr = node.getJSONArray("nodes");
			if(arr != null){
				String nextNodeId = arr.getJSONObject(0).getString("nodeId");
				String pcId = node.getString("pcId");
				BusinessObject business = new BusinessObject(entry,loan,applicationMapper.selectByPrimaryKey(ip.getModelId()));
				return flowMgrImpl.next(business, nextNodeId, pcId, personId);
			}
		}throw new Exception("未配置下一步节点");
	}

	@Override
	public JSONObject onLineLoan(BusLoan loan,String personId) throws Exception {
		loanMapper.updateByPrimaryKeySelective(loan);
		loan = loanMapper.selectByPrimaryKey(loan.getId());
		BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(loan.getIntoPieceId());
		AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
		JSONObject node = flowMgrImpl.getNextTask(entry);
		if(node != null && node.getIntValue("code") == 200){
			JSONArray arr = node.getJSONArray("nodes");
			if(arr != null){
				String nextNodeId = arr.getJSONObject(0).getString("nodeId");
				String pcId = node.getString("pcId");
				BusinessObject business = new BusinessObject(entry,loan,applicationMapper.selectByPrimaryKey(ip.getModelId()));
				return flowMgrImpl.next(business, nextNodeId, pcId, personId);
			}
		}throw new Exception("未配置下一步节点");
	}

}
