package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysCarouselFigure;
import com.nongyeos.loan.admin.mapper.SysCarouselFigureMapper;
import com.nongyeos.loan.admin.service.ICarouselFigureService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("carouselFigureService")
public class CarouselFigureServiceImpl implements ICarouselFigureService {
	
	@Autowired
	private SysCarouselFigureMapper carouselFigureMapper;

	@Override
	public List<SysCarouselFigure> selectListByCondition(SysCarouselFigure carouselFigure) throws Exception {
		if(carouselFigure==null){
			throw new Exception("轮播图模板为空！");
		}
		try {
			 return carouselFigureMapper.selectListByCondition(carouselFigure);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询错误！");
		}
	}

	@Override
	public PageBeanUtil<SysCarouselFigure> selectListByPage(SysCarouselFigure cf, int currentPage, int pageSize) throws Exception {
		if(cf==null){
			throw new Exception("轮播图模板为空！");
		}
		try {
			PageHelper.startPage(currentPage, pageSize);
			List<SysCarouselFigure> carouselFigureList = carouselFigureMapper.selectListByCondition(cf);
			int totalNum= carouselFigureMapper.selectCountByCondition(cf);
			PageBeanUtil<SysCarouselFigure> pb = new PageBeanUtil<SysCarouselFigure>(currentPage, pageSize, totalNum);
			pb.setItems(carouselFigureList);
			return pb;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
		
	}

	@Override
	public Integer selectMaxNumber() throws Exception {
		try {
			return carouselFigureMapper.selectMaxNumber();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
		
	}

	@Override
	public void save(SysCarouselFigure carouselFigure) throws Exception {
		if(carouselFigure==null){
			throw new Exception("轮播图为空！");
		}
		try {
			carouselFigureMapper.insert(carouselFigure);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
	}

	@Override
	public void del(SysCarouselFigure carouselFigure) throws Exception {
		if(carouselFigure==null)
			throw new Exception("轮播图为空！");
		try {
			carouselFigureMapper.updateByPrimaryKeySelective(carouselFigure);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败！");
		}
	}

	@Override
	public void update(SysCarouselFigure carouselFigure) throws Exception {
		if(carouselFigure==null){
			throw new Exception();
		}
		try {
			SysCarouselFigure selectByPrimaryKey = carouselFigureMapper.selectByPrimaryKey(carouselFigure.getId());
			selectByPrimaryKey.setNumber(carouselFigure.getNumber());
			selectByPrimaryKey.setStatus(carouselFigure.getStatus());
			selectByPrimaryKey.setChannel(carouselFigure.getChannel());
			carouselFigureMapper.updateByPrimaryKey(selectByPrimaryKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("更新失败！");
		}
		
			
	}
	
	
}
