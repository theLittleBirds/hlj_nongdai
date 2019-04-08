package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;
import com.nongyeos.loan.admin.mapper.BusMemberOperateMediaMapper;
import com.nongyeos.loan.admin.service.IMemberOperateMediaService;

@Service("memberOperateMediaService")
public class MemberOperateMediaServiceImpl implements IMemberOperateMediaService {

	@Autowired
	private BusMemberOperateMediaMapper memberOperateMediaMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("图片标识为空");
		}
		int i =0;
		try {
			i = memberOperateMediaMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败");
		}
		return i;
	}

	@Override
	public int insert(BusMemberOperateMedia record) throws Exception {
		if(record==null){
			throw new Exception("图片模板为空");
		}
		int i =0;
		try {
			i = memberOperateMediaMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
		return i;
	}

	@Override
	public int insertSelective(BusMemberOperateMedia record) throws Exception {
		if(record==null){
			throw new Exception("图片模板为空");
		}
		int i =0;
		try {
			i = memberOperateMediaMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
		return i;
	}

	@Override
	public BusMemberOperateMedia selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("图片标识为空");
		}
		BusMemberOperateMedia selectByPrimaryKey =null;
		try {
			selectByPrimaryKey = memberOperateMediaMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
		return selectByPrimaryKey;
	}

	@Override
	public int updateByPrimaryKeySelective(BusMemberOperateMedia record)
			throws Exception {
		if(record==null){
			throw new Exception("图片模板为空");
		}
		int i =0;
		try {
			i = memberOperateMediaMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("更新失败");
		}
		return i;
	}

	@Override
	public int updateByPrimaryKey(BusMemberOperateMedia record)
			throws Exception {
		if(record==null){
			throw new Exception("图片模板为空");
		}
		int i =0;
		try {
			i = memberOperateMediaMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("更新失败");
		}
		return i;
	}

	@Override
	public int existenceByKey(BusMemberOperateMedia pictureforsearch)
			throws Exception {
		if(pictureforsearch==null||StringUtils.isEmpty(pictureforsearch.getQiniuKey()) ){
			throw new Exception("无效的查询条件");
		}
		int check = 0;
		try {
			check = memberOperateMediaMapper.existenceByKey(pictureforsearch);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统出错");
		}
		return check;
	}

	@Override
	public List<BusMemberOperateMedia> selectByMOId(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("图片标识为空");
		}
		try {
			return memberOperateMediaMapper.selectByMOId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统出错");
		}
	}

}
