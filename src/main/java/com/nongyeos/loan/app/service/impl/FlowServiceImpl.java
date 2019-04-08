package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FleFlow;
import com.nongyeos.loan.app.mapper.FleFlowMapper;
import com.nongyeos.loan.app.service.IFlowService;

@Service("flowService")
public class FlowServiceImpl implements IFlowService{
	
	@Autowired
	private FleFlowMapper flowMapper;

	/**
	 *
	 * @param finsCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<FleFlow> selectByFinsCode(String finsCode)throws Exception{
		try{
			if(finsCode != null && !finsCode.equals("")){
				List<FleFlow> list = flowMapper.selectByFinsCode(finsCode);
				return list;
			}else{
				throw new Exception("机构编号为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(FleFlow flow) {
		try{
			flowMapper.insertSelective(flow);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(FleFlow flow) {
		try{
			flowMapper.updateByPrimaryKey(flow);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String flowId)throws Exception{
		try{
			if(flowId != null && !flowId.equals("")){
				flowMapper.deleteByPrimaryKey(flowId);
			}else{
				throw new Exception("流程Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public FleFlow getFlowById(String flowId) throws Exception {
		try{
			if(flowId != null && !flowId.equals("")){
				FleFlow flow = flowMapper.selectByPrimaryKey(flowId);
		        return flow;
			}else{
				throw new Exception("产品编号为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
}
