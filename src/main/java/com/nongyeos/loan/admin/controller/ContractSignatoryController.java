package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusContractSignatory;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IContractSignatoryService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
@Controller
@RequestMapping("/contactsignatory")
public class ContractSignatoryController {
	
	@Autowired
	private IContractSignatoryService contractSignatoryService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IWebUserService userService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@RequestMapping("/listpage")
	public String listPage(){
		return "signatory/list";
	}
	
	/**
	 * 异步获取列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
    @ResponseBody
	public PageBeanUtil<BusContractSignatory> list(int currentPage, int pageSize, String name) throws Exception{
		try {
			BusContractSignatory cs = new BusContractSignatory();
			if(StrUtils.isNotEmpty(name))
				cs.setName(name);
			return contractSignatoryService.selectByPage(currentPage, pageSize, cs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(String id){
		ModelAndView mv = new ModelAndView();
		try {
			if(StrUtils.isNotEmpty(id)){
				BusContractSignatory cs = contractSignatoryService.selectByPrimaryKey(id);
				mv.addObject("cs", cs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("signatory/edit");
		return mv;
	}
	
	@RequestMapping("/save")
    @ResponseBody
	public JSONObject saveOrUpdate(HttpServletRequest request, BusContractSignatory cs){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(cs.getName())){
			json.put("code", 400);
			json.put("msg", "企业名称不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getEname())){
			json.put("code", 400);
			json.put("msg", "企业简称不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getIdentityCard())){
			json.put("code", 400);
			json.put("msg", "企业证件号不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getEmail())){
			json.put("code", 400);
			json.put("msg", "企业邮箱不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getPhone())){
			json.put("code", 400);
			json.put("msg", "法人手机号不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getLegalName())){
			json.put("code", 400);
			json.put("msg", "法人姓名不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getLegalIdCard())){
			json.put("code", 400);
			json.put("msg", "法人身份证号不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getBusinessLicence())){
			json.put("code", 400);
			json.put("msg", "营业执照照片不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getAuthorization())){
			json.put("code", 400);
			json.put("msg", "授权书照片不能为空");
			return json;
		}
		try {
			/*SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("enterpriseEmail", cs.getEmail());//企业邮箱
			map.put("enterpriseName", cs.getName());//企业全称
			map.put("legalName", cs.getLegalName());//法人姓名或者是被授权人姓名
			map.put("legalIdentityCard", cs.getLegalIdCard());//法人身份证号或者是被授权人身份证号
			map.put("legalMobile", cs.getPhone());//法人手机号或者是被授权人手机号
			map.put("organizationRegNo", cs.getIdentityCard());//营业执照号
			map.put("organizationRegImg", baseDir + cs.getBusinessLicence()); //营业执照复印件
			map.put("signApplication", baseDir + cs.getAuthorization()); //君子签企业授权服务书
			map.put("companyType", "SX");
			String result = HttpClientUtil.doPost(pieceConfig.getJunziqianAuthorization(), map, "utf-8");
			if(result == null)
				throw new Exception("授权认证失败");
			JSONObject resultObj = JSONObject.parseObject(result);
			String isSuccess = resultObj.getString("isSuccess");
			if(!"1".equals(isSuccess)){
				throw new Exception(resultObj.getString("msg"));
			}*/
			if(StrUtils.isEmpty(cs.getId())){
				cs.setId(UUIDUtil.getUUID());
				cs.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				cs.setCreDate(new Date());
				cs.setUpdDate(new Date());
				contractSignatoryService.insert(cs);
			}else{
				cs.setUpdDate(new Date());
				contractSignatoryService.updateByPrimaryKeySelective(cs);
			}
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("/delete")
    @ResponseBody
	public JSONObject delete(HttpServletRequest request, String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "请选择要删除的机构");
			return json;
		}
		try {
			BusContractSignatory cs = new BusContractSignatory();
			cs.setId(id);
			cs.setIsDeleted(Constants.COMMON_IS_DELETE);
			contractSignatoryService.updateByPrimaryKeySelective(cs);
			json.put("code", 200);
			json.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
}
