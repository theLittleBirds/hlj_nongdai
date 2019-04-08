package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.mapper.SysParaMapper;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("paraService")
public class ParaServiceImpl implements IParaService{
	
	@Autowired
	private SysParaMapper paraMapper;

	@Override
	public String selectByPGroupId(Integer pgroupId) {
		String strJson = "[";
		
		List<SysPara> list = paraMapper.selectByPGroupId(pgroupId);
		for(int i=0;i<list.size();i++)
		{
			SysPara para = list.get(i);
			if(para != null)
			{
				if(i>0)
					strJson = strJson + ", ";
				
				strJson = strJson + "{parameterName:'" + para.getDescr() + "', parameterValue:'" + para.getValue()+ "'}";
			}
		}
		
		strJson = strJson + "]";
		
		return strJson;
	}
	
	@Override
	public String selectByPGroupId2(Integer pgroupId) {
		String strJson = "[";
		
		List<SysPara> list = paraMapper.selectByPGroupId(pgroupId);
		for(int i=0;i<list.size();i++)
		{
			SysPara para = list.get(i);
			if(para != null)
			{
				if(i>0)
					strJson = strJson + ", ";
				
				strJson = strJson + "{text:'" + para.getDescr() + "', value:'" + para.getValue()+ "'}";
			}
		}
		
		strJson = strJson + "]";
		
		return strJson;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(SysPara para) throws Exception {
		try{
			if(para!=null){
				paraMapper.insert(para);
			}else {
				throw new Exception("参数为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(SysPara para) throws Exception {
		try{
			if(para!=null){
				paraMapper.updateByPrimaryKey(para);
			}else {
				throw new Exception("参数为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	
	}

	@Override
	public PageBeanUtil<SysPara> getList(int offset,int limit,int pgroupId) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<SysPara> allItems= paraMapper.selectList(pgroupId);
			int countNums = paraMapper.count(pgroupId);//总记录数
			PageBeanUtil<SysPara> pageData = new PageBeanUtil<SysPara>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(String paraIds) throws Exception {
		try{
			if (paraIds != null) {
				String[] pIds = paraIds.split(",");
				for (String paraId : pIds) {
					paraMapper.deleteByPrimaryKey(Integer.parseInt(paraId));
				}
			}else {
				throw new Exception("paraIds为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public boolean existedSameName(SysPara para) {
		List<SysPara> list = paraMapper.existedSameName(para.getName());
		SysPara bean = null;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			if(!bean.getParaId().equals(para.getParaId()) && bean.getPgroupId().equals(para.getPgroupId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean existedSameDesc(SysPara para) {
		List<SysPara> list = paraMapper.existedSameName(para.getName());
		SysPara bean = null;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			if(!bean.getParaId().equals(para.getParaId()) && bean.getPgroupId().equals(para.getPgroupId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<SysPara> getListByPId(Integer pgroupId) {
		try {
			List<SysPara> selectList = paraMapper.selectList(pgroupId);
			return selectList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SysPara> selectListByPGroupId(Integer pgroupId)throws Exception {
		List<SysPara> list=null;
		try {
			if(pgroupId!=null){
				list=paraMapper.selectByPGroupId(pgroupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public SysPara queryParaByGroupIdAndVal(String pgroupId, String value)
			throws Exception {
		try {
			if(StringUtils.isEmpty(pgroupId)){
				throw new Exception("pgroupId为空");
			}
			if(StringUtils.isEmpty(value)){
				throw new Exception("value为空");
			}
			return paraMapper.queryParaByGroupIdAndVal(pgroupId, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}

