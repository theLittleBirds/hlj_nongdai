package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusApplySignFile;
import com.nongyeos.loan.admin.mapper.BusApplySignFileMapper;
import com.nongyeos.loan.admin.service.IApplySignFileService;
@Service("applySignFileService")
public class ApplySignFileServiceImpl implements IApplySignFileService {
	
	@Autowired
	private BusApplySignFileMapper applySignFileMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusApplySignFile record) throws Exception {
		if(record == null)
			throw new Exception("合同文件模板为空");
		try {
			return applySignFileMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusApplySignFile selectByPrimaryKey(String id) throws Exception {
		if(id == null)
			throw new Exception("合同文件模板id为空");
		try {
			return applySignFileMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusApplySignFile record)
			throws Exception {
		if(record == null)
			throw new Exception("合同文件模板为空");
		try {
			return applySignFileMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusApplySignFile record) throws Exception {
		if(record == null)
			throw new Exception("合同文件模板为空");
		try {
			return applySignFileMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusApplySignFile> selectByLoanId(String loanId)
			throws Exception {
		if(loanId == null)
			throw new Exception("贷款标识为空");
		try {
			return applySignFileMapper.selectByLoanId(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusApplySignFile> waitSign(String loanId)
			throws Exception {
		if(loanId == null)
			throw new Exception("贷款标识为空");
		try {
			return applySignFileMapper.waitSign(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusApplySignFile> finishSign(String loanId) throws Exception {
		if(loanId == null)
			throw new Exception("贷款标识为空");
		try {
			return applySignFileMapper.finishSign(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
