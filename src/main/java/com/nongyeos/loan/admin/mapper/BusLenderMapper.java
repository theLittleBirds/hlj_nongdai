package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusLender;

public interface BusLenderMapper {
    int deleteByPrimaryKey(String lenderId);

    int insert(BusLender record);

    int insertSelective(BusLender record);

    BusLender selectByPrimaryKey(String lenderId);

    int updateByPrimaryKeySelective(BusLender record);

    int updateByPrimaryKey(BusLender record);
    
	List<BusLender> selectByPage(Map<String, Object> map);

	int selectTotalNum(BusLender lender);

	BusLender selectByName(String name);
	
	List<BusLender> selectByOrgId(String orgId);
	
	List<BusLender> selectAll();
}