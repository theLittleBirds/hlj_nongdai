package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.IntServiceresult;
import com.nongyeos.loan.app.mapper.IntServiceresultMapper;
import com.nongyeos.loan.app.service.IServiceresultService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("serviceresultService")
public class ServiceresultServiceImpl implements IServiceresultService {

	@Autowired
	private IntServiceresultMapper serviceresultMapper;

	@Override
	public PageBeanUtil<IntServiceresult> selectByPage(int limit, int offset,String code) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit,false);
			List<IntServiceresult> allItems = serviceresultMapper.selectAllByCode(code);
			int countNums = serviceresultMapper.count(code);//总记录数
			PageBeanUtil<IntServiceresult> pageData = new PageBeanUtil<IntServiceresult>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<IntServiceresult> getListByCode(String code) {
		List<IntServiceresult> list = serviceresultMapper.selectAllByCode(code);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(IntServiceresult servRes) {
		try {
			servRes.setServresCode(UUIDUtil.getUUID());
			serviceresultMapper.insert(servRes);
		} catch (Exception e) {
			  e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(IntServiceresult servRes) {
		try{
			serviceresultMapper.updateByPrimaryKey(servRes);
			} catch (Exception e) {
				  e.printStackTrace();
			}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByPCode(String pCode) {
		try{
			serviceresultMapper.deleteByPrimaryKey(pCode);
			} catch (Exception e) {
				  e.printStackTrace();
			}
	}

	@Override
	public List<IntServiceresult> selectServiceResultByServimplId(String servimplId) throws Exception {
		List<IntServiceresult> list=null;
		try{
			if(servimplId!=null && !servimplId.equals("")){
				list=serviceresultMapper.selectByServimplId(servimplId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("servimplId为空");
		}
		return list;
	}

	@Override
	public List<IntServiceresult> selectAll() throws Exception {
		List<IntServiceresult> list=serviceresultMapper.selectAll();;
		return list;
	}

}

