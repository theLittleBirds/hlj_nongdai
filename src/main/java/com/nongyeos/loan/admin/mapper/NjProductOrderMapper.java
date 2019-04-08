package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.NjProductOrder;

public interface NjProductOrderMapper {
    int deleteProductOrderByPK(String id);

    int insert(NjProductOrder record);

    int addProductOrderSelective(NjProductOrder record);

    NjProductOrder queryProductOrderByPK(String id);

    int updateProductOrderByPKSelective(NjProductOrder record);

    int updateByPrimaryKey(NjProductOrder record);
    
    List<NjProductOrder> queryNjProductOrderSelective(NjProductOrder record);

    List<NjProductOrder> queryNjProductOrderProductids(String orderProductids);
}