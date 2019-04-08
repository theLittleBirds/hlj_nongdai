package com.nongyeos.loan.admin.service;

import java.util.List;
import com.nongyeos.loan.admin.entity.BusPicture;

public interface IPictureService {
	
	List<BusPicture> selectByIpId(BusPicture record) throws Exception;
	
	int updateByPrimaryKeySelective(BusPicture record) throws Exception;
	
	int insert(BusPicture record) throws Exception;
	
	BusPicture selectByPrimaryKey(String id) throws Exception;
	
	int existenceByKey(BusPicture record) throws Exception;
}
