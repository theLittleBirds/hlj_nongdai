package com.nongyeos.loan.admin.service;

import java.util.Map;

import com.nongyeos.loan.admin.entity.BusStationBond;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IStationBondService {
	
	int insert(BusStationBond record) throws Exception ;
	
	BusStationBond selectByPrimaryKey(String id) throws Exception ;
	
	int updateByPrimaryKeySelective(BusStationBond record) throws Exception ;

    int updateByPrimaryKey(BusStationBond record) throws Exception ;
    
    PageBeanUtil<BusStationBond> selectByPage(int currentPage, int pageSize, Map<String, String> map) throws Exception;
    
    BusStationBond selectByIpId(String intoPieceId)throws Exception ;
}
