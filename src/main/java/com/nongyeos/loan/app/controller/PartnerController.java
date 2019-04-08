package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.IntPartner;
import com.nongyeos.loan.app.service.IPartnerService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/partner")
public class PartnerController {

	@Resource
	private IPartnerService partnerService;

	/**
	 * 合作伙伴-分页查询
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/partnerList")
	@ResponseBody
	public PageBeanUtil<IntPartner> partnerPage(int limit,int offset){
		try {
			PageBeanUtil<IntPartner> list = partnerService.selectAll(limit, offset);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 合作伙伴-添加或者修改
	 * 
	 * @param response
	 * @param request
	 * @param partner
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletResponse response,HttpServletRequest request, IntPartner partner) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 判断重复
		try {
			if (partnerService.existedSameName(partner)) {
				map.put("msg", "该组织机构已存在，请重新填写！");
			} else {
				if (partner.getPartnerId() == null|| partner.getPartnerId().equals("")) {
					partner.setPartnerCode(UUIDUtil.getUUID());
					partnerService.addPartner(partner);
				} else {
					partnerService.updatePartner(partner);
				}
				map.put("msg", "添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 合作伙伴-删除
	 * 
	 * @param response
	 * @param request
	 * @param currIds
	 * @return
	 */
	@RequestMapping("/delPartner")
    @ResponseBody
    public Map<String, Object> delete(HttpServletResponse response,HttpServletRequest request,String currIds){
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if(currIds != null && !currIds.equals("")){
				String[] partnerIds = currIds.split(",");
				for(String partnerId : partnerIds){
					Integer partnerid=Integer.parseInt(partnerId);
					partnerService.deletePartner(partnerid);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "删除失败");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return map;
    }
}
