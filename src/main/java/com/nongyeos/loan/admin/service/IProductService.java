package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.BusProductOrg;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IProductService {


	boolean existedSameName(BusProduct product)throws Exception;

	void addProduct(BusProduct product, String chooseOrgIds)throws Exception;

	void updateProduct(BusProduct product, String chooseOrgIds)throws Exception;

	void delProduct(String productId)throws Exception;

	List<BusProductOrg> selectOrgByProductId(String productId)throws Exception;

	Map<String, Object> getOrgTree(String productId) throws Exception;
	
	List<BusProduct> selectByOrgId(String orgId) throws Exception;

	BusProduct selectByProductId(String product)throws Exception;

	Map<String, Object> findFins(String productId, String personId) throws Exception;

	PageBeanUtil<BusProduct> selectByPage(String personId, int currentPage,
			int pageSize, BusProduct product) throws Exception;

	String selectByFinsId(String lenderId)throws Exception;

	List<BusProduct> selectListByFinsId(String lenderId)throws Exception;
	
	
}
