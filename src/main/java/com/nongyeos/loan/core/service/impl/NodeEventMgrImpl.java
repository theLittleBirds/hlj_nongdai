package com.nongyeos.loan.core.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.resultMap.IntServiceimplMap;
import com.nongyeos.loan.app.entity.FlowNevent;
import com.nongyeos.loan.app.mapper.IntServiceimplMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.model.MsgQueue;
import com.nongyeos.loan.core.model.ResMsg;
import com.nongyeos.loan.core.service.NodeEventMgr;

@Service("nodeEventMgrImpl")
public class NodeEventMgrImpl implements NodeEventMgr {
	
	@Autowired
	private IntServiceimplMapper serviceimplMapper;
	
	@Autowired
	private SqlSession session;
	
	@Override
	public MsgQueue arrived(BusinessObject business) {
		return doEvent(business,Constants.NEVENT_RUNTIME_ARRIVED);
	}

	@Override
	public MsgQueue close(BusinessObject business) {
		return doEvent(business,Constants.NEVENT_RUNTIME_CLOSE);
	}

	@Override
	public MsgQueue end(BusinessObject business) {
		return doEvent(business,Constants.NEVENT_RUNTIME_END);
	}

	@Override
	public MsgQueue opend(BusinessObject business) {
		return doEvent(business,Constants.NEVENT_RUNTIME_OPEN);
	}

	@Override
	public MsgQueue save(BusinessObject business) {
		return doEvent(business,Constants.NEVENT_RUNTIME_SAVE);
	}

	@Override
	public MsgQueue start(BusinessObject business) {
		return doEvent(business,Constants.NEVENT_RUNTIME_START);
	}
	
	/**
	 * 2000 执行成成功    4000 错误，输入参数有错      5000 错误，有异常
	 */
	public MsgQueue doEvent(BusinessObject business, short runtime) {
		MsgQueue  queue	=  business.getMsgQueue();
		FlowNevent fn = new FlowNevent();
		fn.setNodeId(business.getEntry().getNodeId());
		fn.setRuntime(runtime);
		List<IntServiceimplMap> serviceList = serviceimplMapper.selectByEventAndRunTime(fn);
		for (int i = 0; i < serviceList.size(); i++) {
			ResMsg msg = new ResMsg();				
			long start = System.currentTimeMillis();
			IntServiceimplMap service = serviceList.get(i);
			String className = service.getClassName();
			String classMethod = service.getClassMethod();
			if(className == null || "".equals(className) || classMethod == null || "".equals(classMethod)){
				msg.setDate(new Date());
				msg.setCode(4000);
				msg.setMsg(service.getCname()+"未找到 ");
				long time = System.currentTimeMillis() - start;
				msg.setDuration(Integer.parseInt(time+""));
				queue.add(msg);
				continue;
			}					
			try {
				Class<?> class1 = Class.forName("com.nongyeos.loan.core.service.impl."+className);
	            Method m = class1.getDeclaredMethod(classMethod,new Class[]{BusinessObject.class,String.class,SqlSession.class}); 
	            //打破封装 
	            m.setAccessible(true);
	            //执行方法，并且接收返回值
	            m.invoke(class1.newInstance(), new Object[]{business,service.getTardataId(),session});
			} catch (Exception e) {
				e.printStackTrace();
				//存在异常，未通过
				msg.setDate(new Date());
				msg.setCode(5000);
				msg.setMsg(service.getCname()+"出错："+e.getMessage());
				long time = System.currentTimeMillis() - start;
				msg.setDuration(Integer.parseInt(time+""));
				queue.add(msg);
			}	
		}
		return queue;
	}

}
