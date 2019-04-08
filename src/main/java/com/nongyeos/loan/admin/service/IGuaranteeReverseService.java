package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;

public interface IGuaranteeReverseService {
	int deleteByPrimaryKey(String id)throws Exception;

    int insert(BusGuaranteeReverse record)throws Exception;

    int insertSelective(BusGuaranteeReverse record)throws Exception;

    BusGuaranteeReverse selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusGuaranteeReverse record)throws Exception;

    int updateByPrimaryKey(BusGuaranteeReverse record)throws Exception;

	void savePreReverseMessage(String id, String personId)throws Exception;

	List<BusGuaranteeReverse> selectByIntoPieceId(String id)throws Exception;

	BusGuaranteeReverse selectByTypeAndIpId(BusGuaranteeReverse record)throws Exception;
}
