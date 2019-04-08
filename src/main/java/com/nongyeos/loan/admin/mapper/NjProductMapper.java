package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.NjProduct;

public interface NjProductMapper {
    int deleteNjProductByPK(String id);

    int insert(NjProduct record);

    int addNjProductSelective(NjProduct record);

    NjProduct queryNjProductByPrimaryKey(String id);
    
    List<NjProduct> queryNjProductSelective(NjProduct record);

    int updateNjProductPKSelective(NjProduct record);

    int updateByPrimaryKey(NjProduct record);
    
    List<NjProduct> queryAllNjProduct();
}