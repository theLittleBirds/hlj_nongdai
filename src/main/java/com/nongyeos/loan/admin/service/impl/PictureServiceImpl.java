package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusPicture;
import com.nongyeos.loan.admin.mapper.BusPictureMapper;
import com.nongyeos.loan.admin.service.IPictureService;

@Service("pictureService")
public class PictureServiceImpl implements IPictureService {
	
	@Autowired
	private BusPictureMapper pictureMapper;
	
	@Override
	public List<BusPicture> selectByIpId(BusPicture record) throws Exception {
		if(record == null){
			throw new Exception("图片模板为空");
		}
		try {
			return pictureMapper.selectByIpId(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusPicture record) throws Exception {
		if(record == null){
			throw new Exception("图片模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("图片模板主键为空");
		}
		try {
			return pictureMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusPicture record) throws Exception {
		if(record == null){
			throw new Exception("图片模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("图片模板主键为空");
		}
		try {
			return pictureMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusPicture selectByPrimaryKey(String id) throws Exception {
		if(id == null || "".equals(id)){
			throw new Exception("图片模板主键为空");
		}
		try {
			return pictureMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int existenceByKey(BusPicture record) throws Exception {
		if(record == null){
			throw new Exception("图片模板为空");
		}
		try {
			return pictureMapper.existenceByKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
