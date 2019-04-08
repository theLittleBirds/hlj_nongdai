package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FlowNevent;
import com.nongyeos.loan.app.mapper.FlowNeventMapper;
import com.nongyeos.loan.app.service.IFlowNeventService;

@Service("neventService")
public class FlowNeventServiceImpl implements IFlowNeventService {

	@Autowired
	private FlowNeventMapper neventMapper;
	
	@Override
	public List<FlowNevent> selectByNodeId(String nodeId) throws Exception {
		List<FlowNevent> list = null;
		try{
			if(nodeId!=null && !nodeId.equals("")){
				list=neventMapper.selectByNodeId(nodeId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("node为空");
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addNevent(FlowNevent flowNevent) throws Exception {
		try{
			if(flowNevent != null){
				neventMapper.insertSelective(flowNevent);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("flowNevent为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateNevent(FlowNevent flowNevent) throws Exception {
		try{
			if(flowNevent != null){
				neventMapper.updateByPrimaryKey(flowNevent);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("flowNevent为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteNevent(String neventId) throws Exception {
		try{
			if(neventId != null){
				neventMapper.deleteByPrimaryKey(neventId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("neventId为空");
		}
		
	}

	@Override
	public FlowNevent selectByNeventId(String neventId) throws Exception {
		FlowNevent flowNevent=null;
		try{
			if(neventId !=null && !neventId.equals("")){
				flowNevent=neventMapper.selectByPrimaryKey(neventId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("neventId为空");
		}
		return flowNevent;
	}

}
