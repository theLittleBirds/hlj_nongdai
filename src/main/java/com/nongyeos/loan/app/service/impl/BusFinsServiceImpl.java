package com.nongyeos.loan.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.BusFinsMapper;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("busFinsService")
public class BusFinsServiceImpl implements IBusFinsService {

	@Autowired
	private BusFinsMapper busFinsMapper;
	
	//全查
	@Override
	public List<BusFins> selectAll(List<String> orgIdList) {
		return busFinsMapper.selectByOrgIdList(orgIdList);
	}

	//添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(BusFins busFins) {
		try {
			if(busFins==null || busFins.equals("")){
				throw  new Exception("busFins为空");
			}
			busFinsMapper.insertSelective(busFins);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(BusFins busFins) {
		try {
			if(busFins==null || busFins.equals("")){
				throw  new Exception("busFins为空");
			}
			busFinsMapper.updateByPrimaryKey(busFins);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteBusFins(String busFin) {
		try {
			if(busFin==null || busFin.equals("")){
				throw  new Exception("busFins为空");
			}
			busFinsMapper.deleteBusFins(busFin);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public PageBeanUtil<BusFins> selectByPage(int limit, int offset, List<String> orgIdList) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit);
			List<BusFins> allItems = busFinsMapper.selectByOrgIdList(orgIdList);
			int countNums = busFinsMapper.countByOrgIdList(orgIdList);//总记录数
			PageBeanUtil<BusFins> pageData = new PageBeanUtil<BusFins>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusFins selectById(String finsId) throws Exception {
		try {
			BusFins fins = busFinsMapper.selectByPrimaryKey(finsId);
			return fins;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusFins> selectByOrgId(String orgId) throws Exception {
		if(StringUtils.isEmpty(orgId)){
			throw new Exception("组织机构标识为空");
		}
		try {
			List<String> orgIdList = new ArrayList<String>();
			orgIdList.add(orgId);
			return busFinsMapper.selectByOrgIdList(orgIdList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public List<BusFins> queryAll() {
		return busFinsMapper.selectAll();
	}
}
