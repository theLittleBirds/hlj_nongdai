package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusMedia;

public interface IMediaService {
	
	List<BusMedia> selectByIpId(BusMedia record) throws Exception;
	
	int updateByPrimaryKeySelective(BusMedia record) throws Exception;
	
	int insert(BusMedia record) throws Exception;
	
	BusMedia selectByPrimaryKey(String id) throws Exception;
	
	int existenceByKey(BusMedia record) throws Exception;
}
