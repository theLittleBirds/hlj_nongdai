package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.app.entity.DecisionPolicybase;


public interface DecisionPolicybaseMapper {
    int deleteByPrimaryKey(String baseId);

    int insert(DecisionPolicybase record);

    int insertSelective(DecisionPolicybase record);

    DecisionPolicybase selectByPrimaryKey(String baseId);
    
    DecisionPolicybase queryByPrimaryKeyAndDesc(@Param("baseId")String baseId, @Param("miaoshu")String miaoshu);
    
    List<DecisionPolicybase> selectByAppId(Map<String,Object> map);
    
    List<DecisionPolicybase> selectAllByAppId(String appId);

    int updateByPrimaryKeySelective(DecisionPolicybase record);

    int updateByPrimaryKey(DecisionPolicybase record);
}