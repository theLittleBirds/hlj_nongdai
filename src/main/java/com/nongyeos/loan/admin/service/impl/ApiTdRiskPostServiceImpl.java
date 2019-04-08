package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.ApiTdRiskPost;
import com.nongyeos.loan.admin.mapper.ApiTdRiskPostMapper;
import com.nongyeos.loan.admin.resultMap.ApiTdRiskPostMap;
import com.nongyeos.loan.admin.service.IApiTdRiskPostService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("apiTdRiskPostService")
public class ApiTdRiskPostServiceImpl implements IApiTdRiskPostService{

	@Autowired
	private ApiTdRiskPostMapper apiTdRiskPostMapper;

	@Override
	public PageBeanUtil<ApiTdRiskPost> queryAllRiskPost(int currentPage,
			int pageSize, ApiTdRiskPostMap apiTdRiskPost) throws Exception {
		try {
			List<ApiTdRiskPost> riskPostByPageTotal = apiTdRiskPostMapper.queryAllRiskPostByPage(apiTdRiskPost);
			
			PageHelper.startPage(currentPage, pageSize);
			List<ApiTdRiskPost> riskPostByPage = apiTdRiskPostMapper.queryAllRiskPostByPage(apiTdRiskPost);
			int total = riskPostByPageTotal.size();
			PageBeanUtil<ApiTdRiskPost> pageData = new PageBeanUtil<ApiTdRiskPost>(currentPage, pageSize, total);
	        pageData.setItems(riskPostByPage);
			
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int updateByTdRiskPostSelective(ApiTdRiskPost record)
			throws Exception {
		try {
			if(record == null){
				throw new Exception("ApiTdRiskPost对象为空");
			}
			if(StringUtils.isEmpty(record.getId())){
				throw new Exception("ApiTdRiskPost对象id为空");
			}
			return apiTdRiskPostMapper.updateByTdRiskPostSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public ApiTdRiskPost queryRiskByIntoPieceId(String intoPieceId)
			throws Exception {
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				throw new Exception("进件id为空");
			}
			return apiTdRiskPostMapper.queryRiskByIntoPieceId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int addRiskPostSelective(ApiTdRiskPost record) throws Exception {
		try {
			if(record == null){
				throw new Exception("ApiTdRiskPost对象为空");
			}
			return apiTdRiskPostMapper.addRiskPostSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public ApiTdRiskPost queryRiskByPrimaryKey(String id) throws Exception {
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("ApiTdRiskPost对象id为空");
			}
			return apiTdRiskPostMapper.queryRiskByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ApiTdRiskPost record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ApiTdRiskPost record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}