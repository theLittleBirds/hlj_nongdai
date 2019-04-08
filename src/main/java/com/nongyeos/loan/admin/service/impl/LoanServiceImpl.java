package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.resultMap.LoanMap;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Service("loanService")
public class LoanServiceImpl implements ILoanService{
	
	@Autowired
	private BusLoanMapper loanMapper; 
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private AppApplicationMapper applicationMapper;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusLoan record) throws Exception {
		if(record == null)
			throw new Exception("借款模板为空");
		if(record.getId() == null || "".equals(record.getId()))
			throw new Exception("借款模板id为空");
		try {
			return loanMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusLoan selectByPrimaryKey(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new Exception("借款模板id为空");
		try {
			return loanMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusLoan record) throws Exception {
		if(record == null)
			throw new Exception("借款模板为空");
		if(record.getId() == null || "".equals(record.getId()))
			throw new Exception("借款模板id为空");
		try {
			return loanMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusLoan record) throws Exception {
		if(record == null)
			throw new Exception("借款模板为空");
		if(record.getId() == null || "".equals(record.getId()))
			throw new Exception("借款模板id为空");
		try {
			return loanMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusLoan selectByIpId(String intoPieceId) throws Exception {
		if(intoPieceId == null || "".equals(intoPieceId))
			throw new Exception("进件标识为空");
		try {
			return loanMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 合同制作 合同签署 待放款  已放款
	 */
	@Override
	public PageBeanUtil<LoanMap> selectLoanByPage(int currentPage,
			int pageSize, LoanMap loanMap) throws Exception{
			try {
				PageHelper.startPage(currentPage, pageSize, false);
				List<LoanMap> list = loanMapper.selectLoanByCondition(loanMap);
				int countNums = loanMapper.selectLoanCountByCondition(loanMap);
				PageBeanUtil<LoanMap> pageData = new PageBeanUtil<LoanMap>(currentPage, pageSize, countNums);
		        pageData.setItems(list);
		        return pageData;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
	}
	/**
	 * 还款中
	 */
	@Override
	public PageBeanUtil<LoanMap> selectByPage(int currentPage,
			int pageSize, LoanMap loanMap) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<LoanMap> list = loanMapper.selectByCondition(loanMap);
			int countNums = loanMapper.selectCountByCondition(loanMap);
			PageBeanUtil<LoanMap> pageData = new PageBeanUtil<LoanMap>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 还款完成
	 */
	@Override
	public PageBeanUtil<LoanMap> loanFinishPage(int currentPage,
			int pageSize, LoanMap loanMap) throws Exception{
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<LoanMap> list = loanMapper.loanFinish(loanMap);
			int countNums = loanMapper.loanFinishCount(loanMap);
			PageBeanUtil<LoanMap> pageData = new PageBeanUtil<LoanMap>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 合同记录
	 */
	@Override
	public PageBeanUtil<LoanMap> contactRecordPage(int currentPage,
			int pageSize, LoanMap loanMap) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<LoanMap> list = loanMapper.selectContactRecord(loanMap);
			int countNums = loanMapper.selectContactRecordCount(loanMap);
			PageBeanUtil<LoanMap> pageData = new PageBeanUtil<LoanMap>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject examineSave(String id, String loanId, String nextNodeId,
			String pcId,String personId) throws Exception {
		try {
			BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(id);
			BusinessObject business = new BusinessObject(appEntryMapper.queryByAppModeId(id),loanMapper.selectByPrimaryKey(loanId),applicationMapper.selectByPrimaryKey(ip.getModelId()));
			return flowMgrImpl.next(business, nextNodeId, pcId, personId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<LoanMap> alreadyLoanExport(LoanMap loanMap) throws Exception {
		try {
			return loanMapper.selectLoanByCondition(loanMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
