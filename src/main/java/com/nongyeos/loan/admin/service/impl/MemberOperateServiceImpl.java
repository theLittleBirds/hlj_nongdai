package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.mapper.BusMemberOperateMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.mapper.SysPersonorgMapper;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("memberOperateService")
public class MemberOperateServiceImpl implements IMemberOperateService {

	@Autowired
	private BusMemberOperateMapper memberOperateMapper;
	
	@Autowired
	private SysPersonMapper personMapper;
	
	@Autowired
	private SysPersonorgMapper personorgMapper;
	
	@Autowired
	private SysOrgMapper orgMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("档案主键为空！");
		}
		try {
			return memberOperateMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败");
		}
	}

	@Override
	public int insert(BusMemberOperate record) throws Exception {
		if(record==null){
			throw new Exception("档案模板为空！");
		}
		try {
			return memberOperateMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	@Override
	public int insertSelective(BusMemberOperate record) throws Exception {
		if(record==null){
			throw new Exception("档案模板为空！");
		}
		try {
			return memberOperateMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	@Override
	public BusMemberOperate selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("档案主键为空！");
		}
		try {
			return memberOperateMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusMemberOperate record)
			throws Exception {
		if(record==null){
			throw new Exception("档案模板为空！");
		}
		try {
			return memberOperateMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
	}

	@Override
	public int updateByPrimaryKey(BusMemberOperate record) throws Exception {
		if(record==null){
			throw new Exception("档案模板为空！");
		}
		try {
			return memberOperateMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
	}

	@Override
	public BusMemberOperate selectHistory(BusMemberOperate operate)
			throws Exception {
		if(operate==null){
			throw new Exception("档案模板为空！");
		}
		try {
			return memberOperateMapper.selectHistory(operate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询重复失败");
		}
	}

	@Override
	public List<BusMemberOperate> selectByUser(String userId) throws Exception {
		if(StringUtils.isEmpty(userId)){
			throw new Exception("用户标识为空");
		}
		try {
			SysPerson person = personMapper.selectByUserIdAndIsdefault(userId);
			List<SysPersonorg> personorgs = personorgMapper.selectByPersonId(person.getPersonId());
			StringBuilder condition = new StringBuilder();
			List<String> orgIds = new ArrayList<String>(); 
			if(personorgs!=null&&personorgs.size()>0){
				for (int i = 0; i < personorgs.size(); i++) {
					List<String> orgIdsTemp = orgMapper.selectByIdOrParent(personorgs.get(i).getOrgId());
					if(orgIdsTemp!=null&&orgIdsTemp.size()>0){
						for (int j = 0; j < orgIdsTemp.size(); j++) {
							orgIds.add(orgIdsTemp.get(j));
						}
					}
				}
			}
			if(orgIds.size()>0){
				condition.append(orgIds.get(0));
				for (int i = 0; i < orgIds.size(); i++) {
					condition.append(","+orgIds.get(i));
				}
			}
			List<String> orgId = Arrays.asList(condition.toString().split(","));
			return memberOperateMapper.selectByOrgIds(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public List<BusMemberOperate> selectByUserAndCondition(String userId,
			BusMemberOperate mo) throws Exception {
		if(StringUtils.isEmpty(userId)){
			throw new Exception("用户标识为空");
		}
		try {
			SysPerson person = personMapper.selectByUserIdAndIsdefault(userId);
			List<SysPersonorg> personorgs = personorgMapper.selectByPersonId(person.getPersonId());
			StringBuilder condition = new StringBuilder();
			List<String> orgIds = new ArrayList<String>(); 
			if(personorgs!=null&&personorgs.size()>0){
				for (int i = 0; i < personorgs.size(); i++) {
					List<String> orgIdsTemp = orgMapper.selectByIdOrParent(personorgs.get(i).getOrgId());
					if(orgIdsTemp!=null&&orgIdsTemp.size()>0){
						for (int j = 0; j < orgIdsTemp.size(); j++) {
							orgIds.add(orgIdsTemp.get(j));
						}
					}
				}
			}
			if(orgIds.size()>0){
				condition.append(orgIds.get(0));
				for (int i = 0; i < orgIds.size(); i++) {
					condition.append(","+orgIds.get(i));
				}
			}
			List<String> orgId = Arrays.asList(condition.toString().split(","));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgIds", orgId);
			map.put("condition", mo);
			return memberOperateMapper.selectByOrgIdsAndCondition(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public PageBeanUtil<BusMemberOperate> selectByPage(int currentPage, int pageSize,
			BusMemberOperate operate) throws Exception {
		if(operate==null){
			throw new Exception("查询模板为空");
		}
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusMemberOperate> list = memberOperateMapper.selectPage(operate);
			int countNums = memberOperateMapper.selectCount(operate);
			PageBeanUtil<BusMemberOperate> pageData = new PageBeanUtil<BusMemberOperate>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusMemberOperate> selectByCondition(
			BusMemberOperate busMemberOperate) throws Exception {
		try {
			return memberOperateMapper.selectByCondition(busMemberOperate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	@Override
	public List<BusMemberOperate> selectByIds(List<String> asList)
			throws Exception {
		if(asList==null&&asList.size()<1){
			throw new Exception("档案资料标识为空");
		}
		try {
			return memberOperateMapper.selectByIds(asList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}


}
