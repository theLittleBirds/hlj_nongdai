package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.entity.BusTransferLand;
import com.nongyeos.loan.admin.mapper.BusTransferLandMapper;
import com.nongyeos.loan.admin.service.ITransferLandService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("transferLandService")
public class TransferLandServiceImpl implements ITransferLandService {

	@Autowired
	private BusTransferLandMapper transferLandMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new  Exception("土地标识为空");
		}
		try {
			return transferLandMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new  Exception("删除土地信息失败");
		}
		
	}

	@Override
	public int insert(BusTransferLand record) throws Exception {
		if(record==null){
			throw new  Exception("土地模板为空");
		}
		try {
			return transferLandMapper.insert(record);
		} catch (Exception e) {
			throw new  Exception("保存土地信息失败");
		}
	}

	@Override
	public int insertSelective(BusTransferLand record) throws Exception {
		if(record==null){
			throw new  Exception("土地模板为空");
		}
		try {
			return transferLandMapper.insertSelective(record);
		} catch (Exception e) {
			throw new  Exception("保存土地信息失败");
		}
	}

	@Override
	public BusTransferLand selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new  Exception("土地标识为空");
		}
		try {
			return transferLandMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new  Exception("查询土地信息失败");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusTransferLand record)
			throws Exception {
		if(record==null){
			throw new  Exception("土地模板为空");
		}
		try {
			return transferLandMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			throw new  Exception("更新土地信息失败");
		}
	}

	@Override
	public int updateByPrimaryKey(BusTransferLand record) throws Exception {
		if(record==null){
			throw new  Exception("土地模板为空");
		}
		try {
			return transferLandMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			throw new  Exception("更新土地信息失败");
		}
	}

	@Override
	public List<BusTransferLand> selectByIpId(String intopieceId)
			throws Exception {
		if(StringUtils.isEmpty(intopieceId)){
			throw new Exception("进件标识为空");
		}
		try {
			return transferLandMapper.selectByIpId(intopieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
	}

	@Override
	public PageBeanUtil<BusTransferLand> selectPageByIpId(int currentPage, int pageSize, String intoPieceId)
			throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize);
			List<BusTransferLand> transferLands = transferLandMapper.selectByIpId(intoPieceId);
			int countNums=0;
			if(transferLands!=null&&transferLands.size()>0){
				countNums=transferLands.size();
			}
			PageBeanUtil<BusTransferLand> pageData = new PageBeanUtil<BusTransferLand>(currentPage, pageSize, countNums);
			pageData.setItems(transferLands);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
		
	}

	@Override
	public Integer selectMaxSortByIpId(String intoPieceId) throws Exception {
		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("进件标识为空");
		}
		try {
			return transferLandMapper.selectMaxSortByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

}
