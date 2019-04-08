package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysCarouselFigure;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ICarouselFigureService {

	List<SysCarouselFigure> selectListByCondition(SysCarouselFigure carouselFigure) throws Exception;

	PageBeanUtil<SysCarouselFigure> selectListByPage(SysCarouselFigure cF, int currentPage, int pageSize) throws Exception;

	Integer selectMaxNumber()throws Exception;

	void save(SysCarouselFigure carouselFigure)throws Exception;

	void del(SysCarouselFigure carouselFigure)throws Exception;

	void update(SysCarouselFigure carouselFigure)throws Exception;

}
