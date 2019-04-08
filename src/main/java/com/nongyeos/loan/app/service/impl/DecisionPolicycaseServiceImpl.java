package com.nongyeos.loan.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.DecisionCasebase;
import com.nongyeos.loan.app.entity.DecisionPolicycase;
import com.nongyeos.loan.app.mapper.DecisionCasebaseMapper;
import com.nongyeos.loan.app.mapper.DecisionPolicycaseMapper;
import com.nongyeos.loan.app.service.IDecisionPolicycaseService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("dePoliCaseService")
public class DecisionPolicycaseServiceImpl implements IDecisionPolicycaseService{

	@Autowired
	private DecisionPolicycaseMapper dePoliCaseMapper;
	
	@Autowired
	private DecisionCasebaseMapper cabaMapper;
	
	@Override
	public List<DecisionPolicycase> getlistByAppId(String appId,String value)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(!appId.equals("") && !value.equals("")){
				map.put("appId", appId);
				map.put("categoryId", value);
				List<DecisionPolicycase> list= dePoliCaseMapper.selectByAppId(map);
			    return list;
			}else{
				throw new Exception ("appId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addCase(DecisionPolicycase Pocase)throws Exception{
		try{
			if(Pocase != null){
				dePoliCaseMapper.insertSelective(Pocase);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delCase(String caseId)throws Exception{
		try{
			if(caseId != null && !caseId.equals("")){
				dePoliCaseMapper.deleteByPrimaryKey(caseId);
			}else{
				throw new Exception ("主键为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCase(DecisionPolicycase Pocase)throws Exception{
		try{
			if(Pocase != null){
				dePoliCaseMapper.updateByPrimaryKey(Pocase);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public DecisionPolicycase getCase(String caseId)throws Exception{
		try{
			if(caseId != null && !caseId.equals("")){
				DecisionPolicycase bean = dePoliCaseMapper.selectByPrimaryKey(caseId);
			    return bean;
			}else{
				throw new Exception ("Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public PageBeanUtil<DecisionCasebase> selectByPage(int currentPage, int pageSize,String caseId,int type) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			Map<String, Object> map = new HashMap<String, Object>();
			if(caseId != null && !caseId.equals("")){
				map.put("caseId", caseId);
				map.put("type", type);
			}
			PageHelper.startPage(currentPage, pageSize);
			List<DecisionCasebase> allItems = cabaMapper.selectByCaseAndType(map);
			int countNums = cabaMapper.count(map);//总记录数
			PageBeanUtil<DecisionCasebase> pageData = new PageBeanUtil<DecisionCasebase>(currentPage, pageSize, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}
	
	@Override
	public List<DecisionCasebase> getCabaByType(String caseId,int type)throws Exception{
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			List<DecisionCasebase> list = null;
			if(caseId != null && !caseId.equals("")){
				map.put("caseId", caseId);
				map.put("type", type);
				list = cabaMapper.selectByCaseAndType(map);
				return list;
			}else{
				throw new Exception("pagebean为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delCaseAndBase(String caseId,int type)throws Exception{
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			if(caseId != null && !caseId.equals("")){
				map.put("caseId", caseId);
				map.put("type", type);
				cabaMapper.deleteByCaseIdAndType(map);
			}else{
				throw new Exception ("caseId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addCaseAndBase(DecisionCasebase caba)throws Exception{
		try{
			if(caba != null){
				cabaMapper.insertSelective(caba);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delCaba(int csbaseId)throws Exception{
		try{
			if(csbaseId != 0){
				cabaMapper.deleteByPrimaryKey(csbaseId);
			}else{
				throw new Exception ("ID为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public List<DecisionPolicycase> getList() throws Exception {
		try {
			List<DecisionPolicycase> list = dePoliCaseMapper.getList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<DecisionPolicycase> getListByAppId2(String appId) throws Exception {
		try {
			List<DecisionPolicycase> list = dePoliCaseMapper.selectByAppId2(appId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}