package com.nongyeos.loan.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FlowData;
import com.nongyeos.loan.app.mapper.FlowDataMapper;
import com.nongyeos.loan.app.service.IFlowDataService;

@Service("flowDataService")
public class FlowDataServiceImpl implements IFlowDataService {

	@Autowired
	private FlowDataMapper flowDataMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addFlowData(FlowData flowData) throws Exception {
		try{
			if(flowData!=null){
				flowDataMapper.insertSelective(flowData);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("flowData为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateFlowDataz(FlowData flowData) throws Exception {
		try{
			if(flowData!=null){
				flowDataMapper.updateByPrimaryKey(flowData);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("flowData为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer dataid) throws Exception {
		try{
			if(dataid!=null){
				flowDataMapper.deleteByPrimaryKey(dataid);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("dataid为空");
		}
	}

	@Override
	public List<FlowData> selectByNodeIdAndType(String nodeId, Integer type)throws Exception {
		List<FlowData> list=null;
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("nodeId", nodeId);
		map.put("type", type);
		try{
			if(nodeId!=null && !nodeId.equals("")){
				list=flowDataMapper.selectByNodeIdAndType(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("nodeId为空");
		}
		return list;
	}

	@Override
	public boolean existenceObject(Short controlType, String objectId,Short objectType,String nodeId) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<FlowData> list=null;
		int num=0;
		boolean b=false;
		map.put("controlType", controlType);
		map.put("objectType", objectType);
		map.put("nodeId", nodeId);
		try{
			if(controlType!=null && objectId!=null && !objectId.equals("")){
				list=flowDataMapper.selectByControlType(map);
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						if(list.get(i).getObjectId().equals(objectId)){
							num++;
						}
					}
				}
			}
			if(num>0){
				b=true;
			}else{
				b=false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception();
		}
		return b;
	}
	
}
