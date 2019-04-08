package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysCarouselFigure;

public interface SysCarouselFigureMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysCarouselFigure record);

    int insertSelective(SysCarouselFigure record);

    SysCarouselFigure selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysCarouselFigure record);

    int updateByPrimaryKey(SysCarouselFigure record);

	List<SysCarouselFigure> selectListByCondition(
			SysCarouselFigure carouselFigure);

	int selectCountByCondition(SysCarouselFigure cf);

	Integer selectMaxNumber();
}