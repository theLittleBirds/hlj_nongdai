package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppItemsection;
import com.nongyeos.loan.app.entity.AppSection;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ISectionService {

	List<AppSection> selectAll(String appId) throws Exception;
	
	PageBeanUtil<AppSection> selectAll(int limit,int offset,String appId) throws Exception;

	void addSection(AppSection section) throws Exception;

	void updateSection(AppSection section) throws Exception;

	void deleteBySectionId(String sectionId) throws Exception;

	List<AppItemsection> getItemSectionBySectionId(String sectionId) throws Exception;

	void delItemSectionBySectionId(String sectionId) throws Exception;

	void addItemSectionBySectionId(AppItemsection itemSection) throws Exception;

	void delItemSectionByseitId(int seitId) throws Exception;

	PageBeanUtil<AppItemsection> selectByPage(int limit, int offset, String sectionId) throws Exception;


}
