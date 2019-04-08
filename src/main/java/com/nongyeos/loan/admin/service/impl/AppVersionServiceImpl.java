package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.controller.AppVersionController;
import com.nongyeos.loan.admin.entity.SysAppVersion;
import com.nongyeos.loan.admin.mapper.SysAppVersionMapper;
import com.nongyeos.loan.admin.service.IAppVersionService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;
@Service("appVersionService")
public class AppVersionServiceImpl implements IAppVersionService {

	@Autowired
	private SysAppVersionMapper appVersionMapper;
	
	@Override
	public PageBeanUtil<SysAppVersion> selectByPage(int currentPage, int pageSize, SysAppVersion sav) throws Exception {
		if(sav==null){
			throw new Exception("app版本模板为空！");
		}
		try {
			PageHelper.startPage(currentPage, pageSize);
			List<SysAppVersion> list = appVersionMapper.selectByCondition(sav);
			int totalNum = appVersionMapper.selectCountByCondition(sav);
			PageBeanUtil<SysAppVersion> pb = new PageBeanUtil<SysAppVersion>(currentPage, pageSize, totalNum);
			pb.setItems(list);
			return pb;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	public SysAppVersion selectById(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("app版本标识为空！");
		}
		try {
			return appVersionMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
		
	}


	@Override
	public SysAppVersion selectByVersionNumber(SysAppVersion appVersion)
			throws Exception {
		if(appVersion==null){
			throw new Exception("app版本模板为空！");
		}
		try {
			appVersion.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<SysAppVersion> selectByCondition = appVersionMapper.selectByCondition(appVersion);
			if(selectByCondition!=null&&selectByCondition.size()>0){
				return selectByCondition.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public void saveOrUpdate(SysAppVersion appVersion) throws Exception {
		if(appVersion==null){
			throw new Exception("app版本模板为空！");
		}
		try {
			if(StringUtils.isEmpty(appVersion.getId())){
				appVersion.setId(UUIDUtil.getUUID());
				appVersionMapper.insert(appVersion);
			}else{
				appVersionMapper.updateByPrimaryKey(appVersion);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delVersion(SysAppVersion appVersion) throws Exception {
		if(appVersion==null){
			throw new Exception("app版本模板为空！");
		}
		try {
//			appVersionMapper.updateByPrimaryKey(appVersion);
			appVersionMapper.deleteByPrimaryKey(appVersion.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败");
		}
	}

	@Override
	public List<SysAppVersion> newForceVersions(SysAppVersion appVersion)
			throws Exception {
		if(appVersion==null){
			throw new Exception("app版本模板为空！");
		}
		try {
			return appVersionMapper.newForceVersions(appVersion);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
		
	}

	@Override
	public SysAppVersion selectNewest(SysAppVersion appVersion)
			throws Exception {
		if(appVersion==null){
			throw new Exception("app版本模板为空！");
		}
		try {
			return appVersionMapper.selectNewest(appVersion);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}
	
}
