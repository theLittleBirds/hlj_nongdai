package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface NjProductOrderService {
    int deleteProductOrderByPK(String id) throws Exception;

    int insert(NjProductOrder record) throws Exception;

    int addProductOrderSelective(NjProductOrder record) throws Exception;

    NjProductOrder queryProductOrderByPK(String id) throws Exception;

    int updateProductOrderByPKSelective(NjProductOrder record) throws Exception;

    int updateByPrimaryKey(NjProductOrder record) throws Exception;
    
    PageBeanUtil<NjProductOrder> queryNjProductOrderSelective(int currentPage, int pageSize, NjProductOrder record) throws Exception;
    
    List<NjProductOrder> queryNjProductOrderProductids(String orderProductids) throws Exception;

	List<NjProductOrder> queryAllOnNjProductOrders() throws Exception;
}