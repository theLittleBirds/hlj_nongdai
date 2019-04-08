package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.mapper.SysParaGroupMapper;
import com.nongyeos.loan.admin.mapper.SysParaMapper;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("paraGroupService")
public class ParaGroupServiceImpl implements IParaGroupService{
	
	@Autowired
	private SysParaGroupMapper paraGroupMapper;
	@Autowired
	private SysParaMapper paraMapper;

	@Override
	public List<SysParaGroup> getList() throws Exception {
		try{
			List<SysParaGroup> list = paraGroupMapper.selectList();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("list为空");
		}
		
	}
	
	@Override
	public SysParaGroup selectByName(String name) throws Exception {
		try{
			if (name != null) {
				SysParaGroup pg = paraGroupMapper.selectByName(name);
				return pg;
			}else {
				throw new Exception("name为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(SysParaGroup paraGroup) throws Exception {
		try{
			if (paraGroup != null) {
				paraGroupMapper.insert(paraGroup);
			}else {
				throw new Exception("paraGroup为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(SysParaGroup paraGroup) throws Exception {
		try{
			if (paraGroup != null) {
				paraGroupMapper.updateByPrimaryKey(paraGroup);
			}else {
				throw new Exception("paraGroup为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(String ids) throws Exception {
		try {
			if(ids!=null){
				String[] paraGroupIds = ids.split(",");
				for (String paraGroupId : paraGroupIds) {
					paraMapper.deleteParaGroupId(Integer.parseInt(paraGroupId));
					paraGroupMapper.deleteByPrimaryKey(Integer.parseInt(paraGroupId));
				}
			}else {
				throw new Exception("ids为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public boolean existedSameName(SysParaGroup paraGroup) {
		List<SysParaGroup> list = paraGroupMapper.existedSameName(paraGroup.getName());
		SysParaGroup bean = null;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			if(!bean.getPgroupId().equals(paraGroup.getPgroupId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean existedSameDesc(SysParaGroup paraGroup) {
		List<SysParaGroup> list = paraGroupMapper.existedSameDesc(paraGroup.getDescr());
		SysParaGroup bean = null;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);
			if(!bean.getPgroupId().equals(paraGroup.getPgroupId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public PageBeanUtil<SysParaGroup> selectByPage(int offset, int limit) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<SysParaGroup> allItems = paraGroupMapper.selectAll();
			int countNums = paraGroupMapper.count();//总记录数
			PageBeanUtil<SysParaGroup> pageData = new PageBeanUtil<SysParaGroup>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}
	
    public String getSelectOption(String name,String value){
    	StringBuffer sb = new StringBuffer("<option value=''>--请选择--</option>");
    	try {
			if(name != null && !"".equals(name)){
				SysParaGroup pg = paraGroupMapper.selectByName(name);
				if(pg != null){
					List<SysPara> list = paraMapper.selectByPGroupId(pg.getPgroupId());
					if(value == null){
						for (int i = 0; i < list.size(); i++) {
							sb.append("<option value='"+list.get(i).getValue()+"'>"+list.get(i).getDescr()+"</option>");
						}
					}else{
						for (int i = 0; i < list.size(); i++) {
							if(value.equals(list.get(i).getValue())){
								sb.append("<option value='"+list.get(i).getValue()+"' selected = 'selected'>"+list.get(i).getDescr()+"</option>");
							}else{
								sb.append("<option value='"+list.get(i).getValue()+"'>"+list.get(i).getDescr()+"</option>");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return sb.toString();
    }
}
