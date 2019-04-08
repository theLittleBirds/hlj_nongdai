package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.mapper.BusMediaMapper;
import com.nongyeos.loan.admin.service.IMediaService;
@Service("mediaService")
public class MediaServiceImpl implements IMediaService {
	
	@Autowired
	private BusMediaMapper mediaMapper;
	
	@Override
	public List<BusMedia> selectByIpId(BusMedia record)  throws Exception{
		if(record == null){
			throw new Exception("多媒体模板为空");
		}
		try {
			return mediaMapper.selectByIpId(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusMedia record)  throws Exception{
		if(record == null){
			throw new Exception("多媒体模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("多媒体模板主键为空");
		}
		try {
			return mediaMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusMedia record)  throws Exception{
		if(record == null){
			throw new Exception("多媒体模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("多媒体模板主键为空");
		}
		try {
			return mediaMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusMedia selectByPrimaryKey(String id)  throws Exception{
		if(id == null || "".equals(id)){
			throw new Exception("多媒体模板主键为空");
		}
		try {
			return mediaMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int existenceByKey(BusMedia record) throws Exception {
		if(record == null){
			throw new Exception("多媒体模板为空");
		}
		try {
			return mediaMapper.existenceByKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
