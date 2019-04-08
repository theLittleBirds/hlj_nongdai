package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface NjProductService {
    int deleteNjProductByPK(String id) throws Exception;

    int addNjProductSelective(NjProduct record) throws Exception;

    NjProduct queryNjProductByPrimaryKey(String id) throws Exception;
    

    int updateNjProductPKSelective(NjProduct record) throws Exception;

    int updateByPrimaryKey(NjProduct record) throws Exception;
    
    int insert(NjProduct record) throws Exception;
    
    PageBeanUtil<NjProduct> queryNjProductSelective(int currentPage, int pageSize, NjProduct record) throws Exception;
    
    List<NjProduct> queryAllNjProduct() throws Exception;
}