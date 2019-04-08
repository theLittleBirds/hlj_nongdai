package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.DecisionCasebase;


public interface DecisionCasebaseMapper {
    int deleteByPrimaryKey(Integer csbaseId);

    int insert(DecisionCasebase record);

    int insertSelective(DecisionCasebase record);

    DecisionCasebase selectByPrimaryKey(Integer csbaseId);
    
    int count(Map<String,Object> map);
    
    List<DecisionCasebase> selectByCaseAndType(Map<String,Object> map);
    
    List<DecisionCasebase> selectByCaseId(String caseId);
    
    void deleteByCaseIdAndType(Map<String,Object> map);

    int updateByPrimaryKeySelective(DecisionCasebase record);

    int updateByPrimaryKey(DecisionCasebase record);
}