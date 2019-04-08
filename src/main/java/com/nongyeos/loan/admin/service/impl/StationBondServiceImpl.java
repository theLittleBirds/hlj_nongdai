package com.nongyeos.loan.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusStationBond;
import com.nongyeos.loan.admin.mapper.BusStationBondMapper;
import com.nongyeos.loan.admin.service.IStationBondService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
@Service("stationBondService")
public class StationBondServiceImpl implements IStationBondService {
	
	@Autowired
	private BusStationBondMapper stationBondMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusStationBond record) throws Exception {
		if(record == null)
			throw new Exception("担保金模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("担保金模板id为空");
		try {
			return stationBondMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusStationBond selectByPrimaryKey(String id) throws Exception {
		if(StrUtils.isEmpty(id))
			throw new Exception("担保金模板id为空");
		try {
			return stationBondMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusStationBond record) throws Exception {
		if(record == null)
			throw new Exception("担保金模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("担保金模板id为空");
		try {
			return stationBondMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusStationBond record) throws Exception {
		if(record == null)
			throw new Exception("担保金模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("担保金模板id为空");
		try {
			return stationBondMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusStationBond> selectByPage(int currentPage,
			int pageSize, Map<String, String> map) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusStationBond> list = stationBondMapper.queryAll(map);
			int countNums = stationBondMapper.queryAllCount(map);
			PageBeanUtil<BusStationBond> pageData = new PageBeanUtil<BusStationBond>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusStationBond selectByIpId(String intoPieceId) throws Exception {
		if(StrUtils.isEmpty(intoPieceId))
			throw new Exception("进件标识为空");
		try {
			return stationBondMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
