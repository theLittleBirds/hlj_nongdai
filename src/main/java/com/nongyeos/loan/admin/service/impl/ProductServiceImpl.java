package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusLender;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.BusProductOrg;
import com.nongyeos.loan.admin.entity.SysMenu;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.mapper.BusProductMapper;
import com.nongyeos.loan.admin.mapper.BusProductOrgMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.BusFinsMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;
@Service("productService")
public class ProductServiceImpl implements IProductService {
	@Autowired
	private BusProductMapper productMapper;
	
	@Autowired
	private BusProductOrgMapper productOrgMapper;

	@Autowired
	private SysOrgMapper orgMapper;
	
	@Autowired
	private BusFinsMapper finsMapper;
	
	@Override
	public PageBeanUtil<BusProduct> selectByPage(String personId,int currentPage, int pageSize,
			BusProduct product) throws Exception {
		if(product==null){
			throw new Exception("金融产品模板为空");
		}
		try {
			List<SysOrg> orgList = orgMapper.selectOrgsByPerson(personId);
			PageHelper.startPage(currentPage, pageSize);
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < orgList.size(); i++) {
				idList.add(orgList.get(i).getOrgId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgIds", idList);
			map.put("product", product);
			map.put("start", (currentPage-1)*15);
			map.put("end", product);
			List<BusProduct> list = productMapper.selectByPage(map);
			int totalNum = productMapper.selectTotalNum(map);
			PageBeanUtil<BusProduct> pageBean = new PageBeanUtil<BusProduct>(currentPage, pageSize, totalNum);
			pageBean.setItems(list);
			return pageBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean existedSameName(BusProduct product) throws Exception {
		if(product==null){
			throw new Exception("金融产品模板为空");
		}
		BusProduct busproduct = productMapper.selectByName(product.getName());
		if(busproduct!=null){
			if(product.getProductId()==null||product.getProductId().equals("")){
				return true;
			}else{
				if(!product.getProductId().equals(busproduct.getProductId())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void addProduct(BusProduct product,String chooseOrgIds) throws Exception {
		if(product==null){
			throw new Exception("金融产品模板为空");
		}
		product.setProductId(UUIDUtil.getUUID());
		product.setCreDate(DateUtils.getNowDate());
		product.setUpdDate(DateUtils.getNowDate());
		product.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		try {
			if(!chooseOrgIds.isEmpty()){
				String[] orgIds = chooseOrgIds.split(",");
				for (int i = 0; i < orgIds.length; i++) {
					BusProductOrg productOrg = new BusProductOrg();
					productOrg.setId(UUIDUtil.getUUID());
					productOrg.setOrgId(orgIds[i]);
					productOrg.setProductId(product.getProductId());
					productOrgMapper.insert(productOrg);
				}
				
			}
			productMapper.insert(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(BusProduct product,String chooseOrgIds) throws Exception {
		if(product==null){
			throw new Exception("金融产品模板为空");
		}
		product.setUpdDate(DateUtils.getNowDate());
		try {
			productOrgMapper.deleteOrgByProductId(product.getProductId());
			if(!chooseOrgIds.isEmpty()){
				String[] orgIds = chooseOrgIds.split(",");
				for (int i = 0; i < orgIds.length; i++) {
					BusProductOrg productOrg = new BusProductOrg();
					productOrg.setId(UUIDUtil.getUUID());
					productOrg.setOrgId(orgIds[i]);
					productOrg.setProductId(product.getProductId());
					productOrgMapper.insert(productOrg);
				}
			}
			productMapper.updateByPrimaryKeySelective(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delProduct(String productId) throws Exception {
		if(productId==null){
			throw new Exception("未指定金融产品标识");
		}
		BusProduct product = new BusProduct();
		product.setProductId(productId);
		product.setIsDeleted(Constants.COMMON_IS_DELETE);
		try {
			productOrgMapper.deleteOrgByProductId(product.getProductId());
			productMapper.updateByPrimaryKeySelective(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BusProductOrg> selectOrgByProductId(String productId) throws Exception {
		if(productId==null){
			throw new Exception("未指定金融产品标识");
		}
		List<BusProductOrg> list= null;
		try {
			list =productOrgMapper.selectOrgByProductId(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Map<String, Object> getOrgTree(String productId) throws Exception {
		if(productId==null){
			throw new Exception("未指定金融产品标识");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<SysOrg> productOrgs = orgMapper.selectPrList();
			StringBuilder orgIds =new StringBuilder(""); 
			StringBuilder orgNames =new StringBuilder(""); 
			List<BusProductOrg> orgProduct = null;
			if(!productId.equals("noId")){
				orgProduct=productOrgMapper.selectOrgByProductId(productId);
			}
			if(orgProduct!=null&&orgProduct.size()>0){
				for (BusProductOrg busProductOrg : orgProduct) {
					orgIds.append(busProductOrg.getOrgId()+",");
					SysOrg sysOrg = orgMapper.selectByPrimaryKey(busProductOrg.getOrgId());
					orgNames.append(sysOrg.getFullCname()+",");
				}
			}
			String jsonOrg = "";
			for (int i = 0; i < productOrgs.size(); i++) {
				SysOrg first = productOrgs.get(i);
				if(!jsonOrg.equals(""))
					jsonOrg+=",";
				jsonOrg+=getDescendants(first,orgProduct);
			}
			map.put("ids", orgIds.toString());
			map.put("names", orgNames.toString());
			map.put("jsonData", "["+jsonOrg+"]");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	private String getDescendants(SysOrg pOrg,List<BusProductOrg> orgProduct) {
		try {
			String jsonDescendants = "";
			String jsonSameLevel = "";
			if(pOrg == null)
				return(jsonDescendants);
			jsonDescendants = "{\"text\":\"" + pOrg.getFullCname() + "\", \"id\":\"" + pOrg.getOrgId() + "\",  \"selectable\":" + true ;
			if(orgProduct!=null&&orgProduct.size()>0){
				for (BusProductOrg busProductOrg : orgProduct) {
					if(pOrg.getOrgId().equals(busProductOrg.getOrgId())){
						jsonDescendants+= ",\"state\":{\"checked\":true}";
					}
				}
			}
			List<SysOrg> childList = orgMapper.selectPList(pOrg.getOrgId());
			if(childList.size() == 0)
				jsonDescendants = jsonDescendants + "}";
			else
			{
				jsonDescendants = jsonDescendants + " , \"nodes\": [";
				
				for(int i=0;i<childList.size();i++)
				{
					SysOrg childOrg = childList.get(i);
					if(!jsonSameLevel.equals(""))
						jsonSameLevel = jsonSameLevel + ",";
					
					jsonSameLevel = jsonSameLevel + getDescendants(childOrg,orgProduct);
				}
				
				jsonDescendants = jsonDescendants + jsonSameLevel + "]}";
			}
			return jsonDescendants;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<BusProduct> selectByOrgId(String orgId) throws Exception{
		if(orgId == null || "".equals(orgId)){
			throw new Exception("部门标识为空");
		}
		try {
			return productMapper.selectByOrgId(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusProduct selectByProductId(String product) throws Exception {
		if(StringUtils.isEmpty(product)){
			throw new Exception("金融产品标识为空！");
		}
		BusProduct selectByPrimaryKey =null;
		try {
			selectByPrimaryKey =productMapper.selectByPrimaryKey(product);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询金融产品出错！");
		}
		
		return selectByPrimaryKey;
	}

	@Override
	public Map<String, Object> findFins(String productId, String personId) throws Exception {
		if(StringUtils.isEmpty(productId)){
			throw new Exception("金融产品标识为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//账号所拥有的组织机构权限
			List<SysOrg> orgs = orgMapper.selectOrgsByPerson(personId);
			
			
			StringBuilder sb = new StringBuilder();
			sb.append("<option value=''>--请选择--</option>");
			if(orgs==null){
				map.put("result",sb.toString() );
				return map;
			}
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < orgs.size(); i++) {
				list.add(orgs.get(i).getOrgId());
			}
			List<BusFins> fins = finsMapper.selectByOrgIdList(list);
			
			
			if(fins!=null){
				BusProduct product = new BusProduct();
				if(!productId.equals("noId")){
					product = productMapper.selectByPrimaryKey(productId);
				}
				for (int i = 0; i < fins.size(); i++) {
					if(fins.get(i).getFinsId().equals(product.getFinsId())){
						sb.append("<option value=\""+fins.get(i).getFinsId()+"\" selected='selected'>"+fins.get(i).getCname()+"</option>");
					}else{
						sb.append("<option value=\""+fins.get(i).getFinsId()+"\" >"+fins.get(i).getCname()+"</option>");
					}
				}
			}
			map.put("result",sb.toString() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String selectByFinsId(String lenderId) throws Exception {
		if(lenderId == null || "".equals(lenderId)){
			throw new Exception("部门标识为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<option value=''>--请选择--</option>");
		try {
			List<BusProduct> list = productMapper.selectByFinsId(lenderId);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
						sb.append("<option value=\""+list.get(i).getProductId()+"\" >"+list.get(i).getName()+"</option>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return sb.toString();
	}

	@Override
	public List<BusProduct> selectListByFinsId(String lenderId)
			throws Exception {
		if(lenderId == null || "".equals(lenderId)){
			throw new Exception("部门标识为空");
		}
		try {
			return productMapper.selectByFinsId(lenderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统报错了");
		}
	}

	
	
}
