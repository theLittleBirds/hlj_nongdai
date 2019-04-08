package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusGradeModel;

public interface BusGradeModelMapper {
    int deleteByPrimaryKey(String modelId);

    int insert(BusGradeModel record);

    int insertSelective(BusGradeModel record);

    BusGradeModel selectByPrimaryKey(String modelId);

    int updateByPrimaryKeySelective(BusGradeModel record);

    int updateByPrimaryKey(BusGradeModel record);
}