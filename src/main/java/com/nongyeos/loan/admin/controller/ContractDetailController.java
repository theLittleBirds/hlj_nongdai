package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusContractDetail;
import com.nongyeos.loan.admin.entity.BusLender;
import com.nongyeos.loan.admin.service.IContractDetailService;
import com.nongyeos.loan.admin.service.ILenderService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/contactdetail")
public class ContractDetailController {
	
	@Autowired
	private IContractDetailService  contractDetailService;
	
	@Autowired
	private ILenderService lenderService;
	
	@RequestMapping("page")
	public String page(){
		return "contract/detail";
	}
	
	@RequestMapping("/list")
    @ResponseBody
    public PageBeanUtil<BusContractDetail> list(int currentPage,int pageSize,String lender) throws Exception{
		try {
			if("".equals(lender))
				lender = null;
			return contractDetailService.selectByPage(currentPage, pageSize, lender);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/delete")
    @ResponseBody
	public JSONObject delete(String id,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("msg", "请选择要删除的记录");
			json.put("code", 400);
			return json;
		}
		try {
			BusContractDetail cd = new BusContractDetail();
			cd.setId(id);
			cd.setIsDeleted(Constants.COMMON_IS_DELETE);
			cd.setUpdDate(new Date());
			cd.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			contractDetailService.updateByPrimaryKeySelective(cd);
			json.put("msg", "删除成功");
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("/savepage")
    @ResponseBody
	public ModelAndView savePage(String id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("contract/edit");
		if(StrUtils.isNotEmpty(id))
			mv.addObject("id", id);
		StringBuffer sb = new StringBuffer("<option value=''>--请选择--</option>");
		try {
			List<BusLender> list = lenderService.selectAll();
			for (int i = 0; i < list.size(); i++) {
				sb.append("<option value='"+list.get(i).getLenderId()+"'>"+list.get(i).getName()+"</option>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("lender", sb.toString());
		return mv;
	}
	
	@RequestMapping("/getbyid")
    @ResponseBody
	public JSONObject getById(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("msg", "参数错误");
			json.put("code", 200);
			return json;
		}
		try {
			json.put("para", contractDetailService.selectByPrimaryKey(id));
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
			json.put("code", 400);
		}
		return json;
	}
	
	@RequestMapping("/save")
    @ResponseBody
	public JSONObject saveOrUpdate(HttpServletRequest request,BusContractDetail cd){
		JSONObject json = new JSONObject();
		if(cd == null){
			json.put("msg", "参数为空");
			json.put("code", 400);
			return json;
		}
		if(cd.getFinsId() == null){
			json.put("msg", "出借人为空");
			json.put("code", 400);
			return json;
		}
		if(cd.getName() == null){
			json.put("msg", "合同名称为空");
			json.put("code", 400);
			return json;
		}
		if(cd.getFileName() == null){
			json.put("msg", "文件名称为空");
			json.put("code", 400);
			return json;
		}
		try {
			if(StrUtils.isEmpty(cd.getId())){
				cd.setId(UUIDUtil.getUUID());
				cd.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				cd.setCreDate(new Date());
				cd.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				cd.setUpdDate(new Date());
				cd.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				contractDetailService.insert(cd);
			}else{
				cd.setUpdDate(new Date());
				cd.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				contractDetailService.updateByPrimaryKeySelective(cd);
			}
			json.put("msg", "保存成功");
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
			json.put("code", 400);
		}
		return json;
	}
		
}
