package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.ApiThirdMsg;
import com.nongyeos.loan.admin.mapper.ApiThirdMsgMapper;
import com.nongyeos.loan.admin.service.IApiThirdMsgService;

@Service("apiThirdMsgService")
public class ApiThirdMsgServiceImpl implements IApiThirdMsgService{

	@Autowired
	private ApiThirdMsgMapper apiThirdMsgMapper;
	
	@Override
	public List<ApiThirdMsg> queryByPostRisk(String postRisk) throws Exception {
		try {
			if(StringUtils.isEmpty(postRisk)){
				throw new Exception("贷后监控标识为空");
			}
			return apiThirdMsgMapper.queryByPostRisk(postRisk);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<ApiThirdMsg> queryByPlatform(String idCardNo, String platform)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApiThirdMsg> queryByIntoPiecePlatform(String intoPieceId,
			String platform) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiThirdMsg queryByPrimaryPlatform(String id, String platform)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ApiThirdMsg record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addPlatformMsg(ApiThirdMsg record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ApiThirdMsg selectByPrimaryKey(String id) throws Exception {
		
		return apiThirdMsgMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateApiThirdMsg(ApiThirdMsg record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ApiThirdMsg record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ApiThirdMsg selectLastByIntoPAndIDC(ApiThirdMsg thirdsql)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiThirdMsg selectLastByIDC(ApiThirdMsg thirdsql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByIntoPAndIDCSelective(ApiThirdMsg thirdsqlConditionbr)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}