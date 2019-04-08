package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.AppItemsection;
import com.nongyeos.loan.app.entity.AppSection;
import com.nongyeos.loan.app.mapper.AppItemsectionMapper;
import com.nongyeos.loan.app.mapper.AppSectionMapper;
import com.nongyeos.loan.app.service.ISectionService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("secitionService")
public class SectionServiceImpl implements ISectionService {

	@Autowired
	private AppSectionMapper sectionMapper;
	@Autowired
	private AppItemsectionMapper itemsectionMapper;

	// 全查
	@Override
	public List<AppSection> selectAll(String appId) throws Exception{
		try {
			List<AppSection> ls  = sectionMapper.selectAll(appId);
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	// 添加区段
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addSection(AppSection section) throws Exception{
		try {
			if (section == null) {
				throw new Exception("section为空");
			}
			sectionMapper.insert(section);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新区段
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateSection(AppSection section) throws Exception{
		try {
			if (section == null) {
				throw new Exception("section为空");
			}
			sectionMapper.updateByPrimaryKey(section);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除区段
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteBySectionId(String sectionId) throws Exception{
		try {
			if (sectionId == null) {
				throw new Exception("sectionId为空");
			}
			sectionMapper.deleteByPrimaryKey(sectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<AppItemsection> getItemSectionBySectionId(String sectionId)throws Exception{
		try{
			if(sectionId == null){
				throw new Exception("sectionId为空");
			}
			List<AppItemsection> list = itemsectionMapper.selectBySectionId(sectionId);
		    return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delItemSectionBySectionId(String sectionId)throws Exception{
		try{
			if(sectionId == null){
				throw new Exception("sectionId为空");
			}
			itemsectionMapper.deleteBySectionId(sectionId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delItemSectionByseitId(int seitId)throws Exception{
		try{
			if(seitId == 0){
				throw new Exception("seltId为空");
			}
			itemsectionMapper.deleteByPrimaryKey(seitId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override 
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addItemSectionBySectionId(AppItemsection itemSection)throws Exception{
		try{
			if(itemSection == null){
				throw new Exception("itemSection为空");
			}
			itemsectionMapper.insertSelective(itemSection);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public PageBeanUtil<AppSection> selectAll(int limit, int offset, String appId) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<AppSection> allItems = sectionMapper.selectAll(appId);
			int countNums = sectionMapper.count(appId);// 总记录数
			PageBeanUtil<AppSection> pageData = new PageBeanUtil<AppSection>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	@Override
	public PageBeanUtil<AppItemsection> selectByPage(int limit, int offset, String sectionId) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit);
			List<AppItemsection> allItems = itemsectionMapper.selectBySectionId(sectionId);
			int countNums = itemsectionMapper.count(sectionId);// 总记录数
			PageBeanUtil<AppItemsection> pageData = new PageBeanUtil<AppItemsection>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}

}
