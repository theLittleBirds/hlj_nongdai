package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusFamilySituation;

public interface BusFamilySituationMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFamilySituation record);

    int insertSelective(BusFamilySituation record);

    BusFamilySituation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFamilySituation record);

    int updateByPrimaryKey(BusFamilySituation record);
    
    //根据进件及类型来查询家庭成员记录
  	BusFamilySituation selectByIntopIdAndType(BusFamilySituation familySituation);
  	
  	BusFamilySituation selectByIntopIdAndSeqno(BusFamilySituation familySituation);

  	//查询姓名身份证号手机号存在的人
  	List<BusFamilySituation> thirdpartycredit(String intoPieceId);

  	//根据进件及类型来查询家庭成员记录
  	List<BusFamilySituation> queryByIntopId(BusFamilySituation familySituation);
  	
  	//根据进件id和共同借款人标识查询
  	List<BusFamilySituation> queryCoBorrower(BusFamilySituation familySituation);

	List<Object> selectByParentItemId(String mainId);
	
	//查询姓名、身份证号、手机号都存在的土地共有人
	List<BusFamilySituation>  queryCoLand(String intoPieceId);
}