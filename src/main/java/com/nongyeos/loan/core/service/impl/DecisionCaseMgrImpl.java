package com.nongyeos.loan.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.app.entity.DecisionCasebase;
import com.nongyeos.loan.app.entity.DecisionPolicycase;
import com.nongyeos.loan.app.mapper.DecisionCasebaseMapper;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.DecisionBaseMgr;
import com.nongyeos.loan.core.service.DecisionCaseMgr;

@Service("CaseMgrImpl")
public class DecisionCaseMgrImpl implements DecisionCaseMgr{
	
	@Autowired
	private DecisionCasebaseMapper cabaMapper;
	@Autowired
	private DecisionBaseMgr decisionBaseMgrImpl;

	@Override
	public boolean logicExpress(BusinessObject bo, String caseId) throws Exception{
		boolean caseState;
		try{
			List<DecisionCasebase> Andlist = getAndList(caseId);
			List<DecisionCasebase> Orlist = getOrList(caseId);
			if(getAndBase(bo, Andlist) && getOrBase(bo, Orlist)){
				caseState = true;
			}else{
				caseState = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return caseState;
	}

	@Override
	public boolean logicExpress(BusinessObject bo, DecisionPolicycase pcase) throws Exception {
		boolean caseState = false;
		try{
			if(pcase != null){
				caseState = logicExpress(bo,pcase.getCaseId());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return caseState;
	}

	
	private List<DecisionCasebase> getAndList(String caseId) throws Exception{
		List<DecisionCasebase> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(caseId != null && !caseId.equals("")){
				map.put("caseId", caseId);
				map.put("type", 2);
				list = cabaMapper.selectByCaseAndType(map);
			}else{
				throw new Exception ("caseId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return list;
	}
	
	private List<DecisionCasebase> getOrList(String caseId) throws Exception{
		List<DecisionCasebase> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(caseId != null && !caseId.equals("")){
				map.put("caseId", caseId);
				map.put("type", 1);
				list = cabaMapper.selectByCaseAndType(map);
			}else{
				throw new Exception ("caseId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return list;
	}
	
	private boolean getAndBase(BusinessObject bo, List<DecisionCasebase> list) throws Exception{
		boolean andCase = true;
		boolean andBase;
		try{
			if(list != null && list.size() > 0){
				for(int i = 0;i < list.size();i++){
					andBase = decisionBaseMgrImpl.logicExpress(bo, list.get(i).getBaseId(), (short)1);
					if(andBase == false){
						andCase = false;
						return andCase;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return andCase;
	}
	
	private boolean getOrBase(BusinessObject bo, List<DecisionCasebase> list) throws Exception{
		boolean orCase = false;
		boolean orBase;
		try{
			if(list != null && list.size() > 0){
				for(int i = 0;i < list.size();i++){
					orBase = decisionBaseMgrImpl.logicExpress(bo, list.get(i).getBaseId(), (short)1);
					if(orBase == true){
						orCase = true;
						return orCase;
					}
				}
			}else{
				orCase = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return orCase;
	}
	
	@Override
	public boolean logicExpress2(BusinessObject bo, String caseId) throws Exception{
		boolean caseState;
		try{
			List<DecisionCasebase> Andlist = getAndList(caseId);
			List<DecisionCasebase> Orlist = getOrList(caseId);
			if(getAndBase2(bo, Andlist) && getOrBase2(bo, Orlist)){
				caseState = true;
			}else{
				caseState = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return caseState;
	}
	
	private boolean getAndBase2(BusinessObject bo, List<DecisionCasebase> list) throws Exception{
		boolean andCase = true;
		boolean andBase;
		try{
			if(list != null && list.size() > 0){
				for(int i = 0;i < list.size();i++){
					andBase = decisionBaseMgrImpl.logicExpress(bo, list.get(i).getBaseId(), (short)2);
					if(andBase == false){
						andCase = false;
						return andCase;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return andCase;
	}
	
	private boolean getOrBase2(BusinessObject bo, List<DecisionCasebase> list) throws Exception{
		boolean orCase = false;
		boolean orBase;
		try{
			if(list != null && list.size() > 0){
				for(int i = 0;i < list.size();i++){
					orBase = decisionBaseMgrImpl.logicExpress(bo, list.get(i).getBaseId(), (short)2);
					if(orBase == true){
						orCase = true;
						return orCase;
					}
				}
			}else{
				orCase = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("决策条件执行失败");
		}
		return orCase;
	}
	
	
	
	
}
