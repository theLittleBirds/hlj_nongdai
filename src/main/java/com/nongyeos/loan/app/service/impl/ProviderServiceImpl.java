package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.IntProvider;
import com.nongyeos.loan.app.mapper.IntProviderMapper;
import com.nongyeos.loan.app.service.IProviderService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("providerService")
public class ProviderServiceImpl implements IProviderService {

	@Autowired
	private IntProviderMapper providerMapper;

	@Override
	public PageBeanUtil<IntProvider> selectByPage(int limit, int offset) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<IntProvider> allItems = providerMapper.selectAll();
			int countNums = providerMapper.count();// 总记录数
			PageBeanUtil<IntProvider> pageData = new PageBeanUtil<IntProvider>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	@Override
	public IntProvider selectByName(String cname) throws Exception {
		try {
			if (cname != null) {
				IntProvider provider = providerMapper.selectByName(cname);
				return provider;
			} else {
				throw new Exception("服务机构id为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(IntProvider pro) {
		try {
			pro.setProviderCode(UUIDUtil.getUUID());
			providerMapper.insert(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(IntProvider pro) {
		try {
			providerMapper.updateByPrimaryKey(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByPCode(String providerCode) throws Exception {
		try {
			if (providerCode != null) {
				providerMapper.deleteByPrimaryKey(providerCode);
			}else {
				throw new Exception("服务机构id为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<IntProvider> selectAll() {
		try {
			// TODO Auto-generated method stub
			List<IntProvider> list = providerMapper.selectAll();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
