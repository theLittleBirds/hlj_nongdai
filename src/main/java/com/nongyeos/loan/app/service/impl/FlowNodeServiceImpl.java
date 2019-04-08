package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.mapper.FlowNodeMapper;
import com.nongyeos.loan.app.service.IFlowNodeService;

@Service("fleNodeService")
public class FlowNodeServiceImpl implements IFlowNodeService {

	@Autowired
	private FlowNodeMapper fleNodeMapper;
	
	//根据AppId查出flenode
	@Override
	public List<FlowNode> selectByAppId(String appId) throws Exception {
		List<FlowNode> ls=null;
		try{
			if(appId==null || appId.equals("")){
				throw new Exception("appId为空");
			}
			ls=fleNodeMapper.selectByAppId(appId);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		return ls;
	}
	//根据节点id查节点
	@Override
	public FlowNode selectByNodeId(String nodeId) throws Exception {
		FlowNode ls=null;
		try{
			if(nodeId==null || nodeId.equals("")){
				throw new Exception("nodeId为空");
			}
			ls=fleNodeMapper.selectByNodeId(nodeId);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		return ls;
	}
	//添加节点
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addFleNode(FlowNode fleNode) throws Exception {
		try{
			if(fleNode==null){
				throw new Exception("fleNode为空");
			}
			fleNodeMapper.insertSelective(fleNode);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	//修改节点的审批实体
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateFleNode(FlowNode fleNode) throws Exception {
		try{
			if(fleNode==null){
				throw new Exception("fleNode为空");
			}
			fleNodeMapper.updateByPrimaryKey(fleNode);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

		//修改节点状态
		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public void updateFleNode1(FlowNode fleNode) throws Exception {
			try{
				if(fleNode==null){
					throw new Exception("fleNode为空");
				}
				fleNodeMapper.updateByPrimaryKey(fleNode);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception(e);
			}
		}

	
	//删除节点
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteFleNode(String fleNodeId) throws Exception {
		try{
			if(fleNodeId==null || fleNodeId.equals("")){
				throw new Exception("fleNodeId为空");
			}
			fleNodeMapper.deleteByPrimaryKey(fleNodeId);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	@Override
	public FlowNode getApproverIdsByNodeId(String nodeId) throws Exception {
		FlowNode fleNode=null;
		try{
			if(nodeId!=null){
				fleNode=fleNodeMapper.selectByPrimaryKey(nodeId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("nodeId为空");
		}
		return fleNode;
	}
	
	@Override
	public FlowNode queryByEnameAndModel(FlowNode node) throws Exception {
		try {
			if(node==null){
				throw new Exception("查询条件为空");
			}
			FlowNode node1 = fleNodeMapper.queryByEnameAndModel(node);
			return node1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	@Override
	public List<FlowNode> selectAll() {
		List<FlowNode> list=fleNodeMapper.selectAll();
		return list;
	}

}
