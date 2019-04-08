package com.nongyeos.loan.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.mapper.BusMemberLoginMapper;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("memberLoginService")
public class MemberLoginServiceImpl implements IMemberLoginService{
	
	@Autowired
	private BusMemberLoginMapper memberLoginMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addRecord(BusMemberLogin record) throws Exception {
		if(record == null){
			throw new Exception("用户模板为空");
		}
		try {
			return memberLoginMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusMemberLogin selectById(String loginId) throws Exception{
		if(loginId == null || "".equals(loginId)){
			throw new Exception("主键为空");
		}
		try {
			return memberLoginMapper.selectByPrimaryKey(loginId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusMemberLogin record) throws Exception{
		if(record == null){
			throw new Exception("用户模板为空");
		}
		if(record.getLoginId() == null){
			throw new Exception("主键为空");
		}
		try {
			return memberLoginMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusMemberLogin record) throws Exception{
		if(record == null){
			throw new Exception("用户模板为空");
		}
		if(record.getLoginId() == null){
			throw new Exception("主键为空");
		}
		try {
			return memberLoginMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	public PageBeanUtil<BusMemberLogin> selectByPage(int currentPage,
			int pageSize, BusMemberLogin record) throws Exception{
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusMemberLogin> list = memberLoginMapper.selectAllByCondition(record);
			int countNums = memberLoginMapper.selectCountByCondition(record);
			PageBeanUtil<BusMemberLogin> pageData = new PageBeanUtil<BusMemberLogin>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	public BusMemberLogin selectByToken(String token) throws Exception{
		if(token == null || "".equals(token)){
			throw new Exception("令牌为空");
		}
		try {
			return memberLoginMapper.selectByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}		
	}

	@Override
	public BusMemberLogin selectByLoginName(BusMemberLogin record) throws Exception{
		if(record == null){
			throw new Exception("登录名为空");
		}
		try {
			return memberLoginMapper.selectByLoginName(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}		
	}

	@Override
	public List<BusMemberLogin> selectByMemberId(Map<String, String> memberIdChannel)
			throws Exception {
		if(memberIdChannel==null){
			throw new Exception("参数为空!");
		}
		try {
			return memberLoginMapper.selectByMemberId(memberIdChannel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
	}	
}
