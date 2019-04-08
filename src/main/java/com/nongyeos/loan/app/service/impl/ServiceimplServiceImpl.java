package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.IntServiceimpl;
import com.nongyeos.loan.app.mapper.IntServiceimplMapper;
import com.nongyeos.loan.app.service.IServiceimplService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("serviceimplService")
public class ServiceimplServiceImpl implements IServiceimplService {

	@Autowired
	private IntServiceimplMapper serviceimplMapper;

	@Override
	public PageBeanUtil<IntServiceimpl> selectByPage(int limit, int offset) throws Exception {
		try {
			// 设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<IntServiceimpl> allItems = serviceimplMapper.selectAll();
			int countNums = serviceimplMapper.count();
			PageBeanUtil<IntServiceimpl> pageData = new PageBeanUtil<IntServiceimpl>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public IntServiceimpl selectByName(String name) throws Exception {
		try {
			if (name != null) {
				IntServiceimpl impl = serviceimplMapper.selectByName(name);
				return impl;
			} else {
				throw new Exception("服务实现id为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public IntServiceimpl selectByLocalPCode(String code) throws Exception {
		try {
			if (code != null) {
				IntServiceimpl impl = serviceimplMapper.selectByLocalPCode(code);
				return impl;
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
	public void add(IntServiceimpl serimpl) throws Exception {
		try {
			serimpl.setServimplCode(UUIDUtil.getUUID());
			serviceimplMapper.insert(serimpl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(IntServiceimpl serimpl) throws Exception {
		try {
			serviceimplMapper.updateByPrimaryKey(serimpl);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteBySerimplCode(String servimplCode) throws Exception {
		try {
			serviceimplMapper.deleteByPrimaryKey(servimplCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	@Override
	public List<IntServiceimpl> selectAll() throws Exception {
		try {
				List<IntServiceimpl> list = serviceimplMapper.selectAll();
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

}
