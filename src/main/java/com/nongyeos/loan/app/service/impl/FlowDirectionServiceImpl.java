package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FlowDirection;
import com.nongyeos.loan.app.mapper.FlowDirectionMapper;
import com.nongyeos.loan.app.service.IFlowDirectionService;

@Service("flowDirectionService")
public class FlowDirectionServiceImpl implements IFlowDirectionService{

	@Autowired
	private FlowDirectionMapper flowDirectionMapper;

	//选择
	@Override
	public List<FlowDirection> selectByNodeId(String nodeId) throws Exception {
		List<FlowDirection> ls=null;
		try{
			if(nodeId == null || nodeId.equals("")){
				throw new Exception("nodeId为空");
			}
			ls=flowDirectionMapper.selectByNodeId(nodeId);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("nodeId为空");
		}
		return ls;
	}

	//删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delDirectionsById(Integer directionid) throws Exception {
		try{
			if(directionid != null){
				flowDirectionMapper.deleteByPrimaryKey(directionid);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("directionid为空");
		}
	}

	//修改
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDirction(FlowDirection dirction) throws Exception {
		try{
			if(dirction != null){
				flowDirectionMapper.updateByPrimaryKey(dirction);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("direction为空");
		}
	}

	//插入
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addDirction(FlowDirection dirction) throws Exception {
		try{
			if(dirction != null){
				flowDirectionMapper.insertSelective(dirction);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("direction为空");
		}
	}
	
}
