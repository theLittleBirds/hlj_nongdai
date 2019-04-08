package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.base.util.CompletingDegree;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/memberoperate")
public class MemberOperateController {
	
	@Autowired
	private IMemberOperateService memberOperateService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private FileConfig fileConfig; 
	
	@Autowired
	private IOrgService orgService;
	
	
	@RequestMapping("/list")
    @ResponseBody
	public PageBeanUtil<BusMemberOperate> list(HttpServletRequest request,int currentPage,int pageSize,
			String org,String operate,String name,String phone){
		BusMemberOperate mo = new BusMemberOperate();
		mo.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
		if(StrUtils.isNotEmpty(org))
			mo.setOrgId(org);
		if(StrUtils.isNotEmpty(operate))
			mo.setOperUserId(operate);
		if(StrUtils.isNotEmpty(name))
			mo.setName(name);
		if(StrUtils.isNotEmpty(phone))
			mo.setPhone(phone);
		try {
			return memberOperateService.selectByPage(currentPage, pageSize, mo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("/page")
	public String page(){
		return "memberOperate/list";
	}
	
	/**
	 * 基本信息页面
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form")
	public ModelAndView form(String id) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("id",id == null ? "" : id);
		mv.setViewName("memberOperate/detail");
		return mv;
	}
	
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/selectbyid")
	@ResponseBody
	public BusMemberOperate selectById(String id){
		if(StrUtils.isEmpty(id))
			return null;
		try {
			return memberOperateService.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 保存
	 * @param 
	 * @return
	 */
	@RequestMapping("/saveorupdate")
	@ResponseBody
	public JSONObject saveOrUpdate(HttpServletRequest request,BusMemberOperate mo){
		JSONObject json = new JSONObject();
		if(mo == null){
			json.put("code", 400);
			json.put("msg", "参数为空");
			return json;
		}
		try {
			if(StrUtils.isEmpty(mo.getId())){
				mo.setId(UUIDUtil.getUUID());
				mo.setOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				mo.setOperUserId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				mo.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				mo.setCreDate(new Date());
				mo.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				mo.setCreOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				mo.setUpdDate(new Date());
				mo.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				mo.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				mo = CompletingDegree.caculate(mo);
				memberOperateService.insert(mo);
			}else{
				BusMemberOperate record = memberOperateService.selectByPrimaryKey(mo.getId());
				mo.setOrgId(record.getOrgId());
				mo.setOperUserId(record.getOperUserId());
				mo.setIsDeleted(record.getIsDeleted());
				mo.setCreDate(record.getCreDate());
				mo.setCreOperId(record.getCreOperId());
				mo.setCreOperName(record.getCreOperName());
				mo.setUpdDate(new Date());
				mo.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				mo.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				mo = CompletingDegree.caculate(mo);
				memberOperateService.updateByPrimaryKey(mo);
			}
			json.put("code", "200");
			json.put("msg", "保存成功");
			json.put("id", mo.getId());
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
			return json;	
		}		
	}
	
	@RequestMapping("/operChange")
	@ResponseBody
	public JSONObject operChange(HttpServletRequest request,String orgId) throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			List<SysPerson> list = personService.selectByPage(orgId);
			StringBuilder selectsb = new StringBuilder();
			selectsb.append("<option value=\"\" >--请选择--</option>");
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
						selectsb.append("<option value=\""+list.get(i).getPersonId()+"\" >"+list.get(i).getNameCn()+"</option>");
				}
				
			}
			retJson.put("result", selectsb);
			retJson.put("errno", 2000);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "系统异常");
			retJson.put("errno", 3000);
		}
		return retJson;
	}
	
	@RequestMapping("/operUserIdChange")
	@ResponseBody
	public JSONObject operUserIdChange(HttpServletRequest request,String MOIds,String orgId,String operUserId){
		JSONObject retJson = new JSONObject();
		try {
//			BusMemberOperate memberOperate2 = memberOperateService.selectByPrimaryKey(memberOperate.getId());
//			if(StringUtils.isNotEmpty(memberOperate.getOperUserId())&&!memberOperate.getOperUserId().equals(memberOperate2.getOperUserId())){
//				memberOperate2.setOperUserId(memberOperate.getOperUserId());
//				memberOperateService.updateByPrimaryKey(memberOperate2);
//			}
			if(StringUtils.isEmpty(MOIds)){
				retJson.put("msg", "未选择需要修改的记录！");
				retJson.put("errno", 3000);
				return retJson;
			}
			String[] split = MOIds.split(",");
			for (int i = 0; i < split.length; i++) {
				BusMemberOperate mo = new BusMemberOperate();
				mo.setId(split[i]);
				mo.setOrgId(orgId);
				mo.setOperUserId(operUserId);
				mo.setUpdDate(DateUtils.getNowDate());
				mo.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				mo.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				memberOperateService.updateByPrimaryKeySelective(mo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "修改失败！");
			retJson.put("errno", 3000);
			return retJson;
		}
		retJson.put("msg", "修改成功！");
		retJson.put("errno", 2000);
		return retJson;
	}
	
	@RequestMapping("/exportExc")
	public ResponseEntity<FileSystemResource> exportExc(HttpServletRequest request,
			String org,String operate,String name,String phone,String ids) throws Exception{
		List<BusMemberOperate> list=null;
		if(!StringUtils.isEmpty(ids)){
			List<String> asList = Arrays.asList(ids.split(","));
			list= memberOperateService.selectByIds(asList);
		}else{
			BusMemberOperate mo = new BusMemberOperate();
			mo.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			if(StrUtils.isNotEmpty(org))
				mo.setOrgId(org);
			if(StrUtils.isNotEmpty(operate))
				mo.setOperUserId(operate);
			if(StrUtils.isNotEmpty(name))
				mo.setName(name);
			if(StrUtils.isNotEmpty(phone))
				mo.setPhone(phone);
			list= memberOperateService.selectByCondition(mo);
		}
		for (int i = 0; i < list.size(); i++) {
			//部门
			if(StringUtils.isEmpty(list.get(i).getOrgId())){
				list.get(i).setOrgId("");
			}else{
				SysOrg sysOrg = orgService.selectByOrgId(list.get(i).getOrgId());
				list.get(i).setOrgId(sysOrg.getFullCname());
			}
			//操作人员
			if(StringUtils.isEmpty(list.get(i).getOperUserId())){
				list.get(i).setOperUserId("");
			}else{
				SysPerson person = personService.selectByPersonId(list.get(i).getOperUserId());
				if(person==null){
					list.get(i).setOperUserId("");
				}else{
					list.get(i).setOperUserId(person.getNameCn());
				}
				
			}
			if(StringUtils.isEmpty(list.get(i).getCropType())){
				list.get(i).setCropType("");
			}
			if(StringUtils.isEmpty(list.get(i).getLivestockType())){
				list.get(i).setLivestockType("");
			}
			if(list.get(i).getLivestockSiteType()==null){
				list.get(i).setLivestockSiteType(0);
			}
			if(StringUtils.isEmpty(list.get(i).getNongsellOtherType())){
				list.get(i).setNongsellOtherType("");
			}
			if(list.get(i).getNoSite()==null){
				list.get(i).setNoSite(0);
			}
			if(StringUtils.isNotEmpty(list.get(i).getOperateType())){
				String operateType = list.get(i).getOperateType();
				String[] split = operateType.split(",");
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < split.length; j++) {
					String s ="";
					if(split[j].equals("1")){
						s="大田作物";
					}else if(split[j].equals("2")){
						s="经济作物";
					}else if(split[j].equals("3")){
						s="养殖";
					}else if(split[j].equals("4")){
						s="经商/个体经营";
					}else if(split[j].equals("5")){
						s="其他";
					}
					if(j==0){
						sb.append(s);
					}else{
						sb.append(","+s);
					}
				}
				list.get(i).setOperateType(sb.toString());
			};
		}
		Workbook workbook =null;
		try {
			workbook = ExcelExportUtil.exportBigExcel(new ExportParams("档案信息", "档案信息"), BusMemberOperate.class, list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		String format = DateUtils.format(DateUtils.getNowDate(), "yyyyMMdd-HHmmss");
		String baseurl = fileConfig.getBaseDir();
		baseurl =baseurl+"xlsFile\\download\\";
		FileUtils.createDirectory(baseurl);
		File file = new File(baseurl, format+".xls");
		workbook.write(new FileOutputStream(file));
		HttpHeaders headers = new HttpHeaders();	
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Content-Disposition", "attachment; filename=" + format + ".xls");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");
	    headers.add("Last-Modified", new Date().toString());
	    headers.add("ETag", String.valueOf(System.currentTimeMillis()));
	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(new FileSystemResource(file));
	}
	
	@RequestMapping("/importExc")
	@ResponseBody
	public JSONObject importExc(HttpServletRequest request,MultipartFile excelFile) throws IOException{
		JSONObject retJson = new JSONObject();
		try {
			ImportParams params = new ImportParams();
	        params.setTitleRows(1);
	        params.setHeadRows(1);
	        String baseurl = fileConfig.getBaseDir();
	        String format = DateUtils.format(DateUtils.getNowDate(), "yyyyMMdd-HHmmss");
	        baseurl =baseurl+"xlsFile\\upload\\";
			FileUtils.createDirectory(baseurl);
	        File pathDest = new File(baseurl, format+".xls");
	        if (!pathDest.exists()) {
	        	pathDest.createNewFile();
	           }
	        FileOutputStream fos = new FileOutputStream(pathDest);
	        byte[] f = excelFile.getBytes();
	        fos.write(f); 
	        fos.close();
	        List<BusMemberOperate> list = ExcelImportUtil.importExcel(
	        		pathDest,
	                BusMemberOperate.class, params);
	        for (int i = 0; i < list.size(); i++) {
	        	BusMemberOperate operate = new BusMemberOperate();
				BusMemberOperate busMemberOperate = list.get(i);
				operate.setName(busMemberOperate.getName());
	        	operate.setPhone(busMemberOperate.getPhone());
	        	List<BusMemberOperate> selectByCondition = memberOperateService.selectByCondition(operate);
	        	if(selectByCondition!=null&&selectByCondition.size()>0){
	        		continue;
	        	}
	        	//部门
	        	if(StringUtils.isNotEmpty(busMemberOperate.getOrgId())){
	        		SysOrg org = orgService.selectByName(busMemberOperate.getOrgId());
	        		if(org!=null){
	        			busMemberOperate.setOrgId(org.getOrgId());
	        		}else{
	        			busMemberOperate.setOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
	        		}
	        	}else{
	        		busMemberOperate.setOrgId(null);
	        	}
	        	if(StringUtils.isNotEmpty(busMemberOperate.getOperUserId())){
	        		SysPerson person = personService.sectByName(busMemberOperate.getOperUserId());
	        		if(person!=null){
	        			busMemberOperate.setOperUserId(person.getPersonId());
	        		}else{
	        			busMemberOperate.setOperUserId(null);
	        		}
	        	}else{
	        		busMemberOperate.setOperUserId(null);
	        	}
	        	if(StringUtils.isNotEmpty(busMemberOperate.getOperateType())){
	        		String operateType = list.get(i).getOperateType();
					String[] split = operateType.split(",");
					String operateType1 =null;
					List<String> asList = new ArrayList<String>();
	        		for (int j = 0; j < split.length; j++) {
						String s ="";
						if(split[j].equals("大田作物")){
							asList.add("1");
						}else if(split[j].equals("经济作物")){
							asList.add("2");
						}else if(split[j].equals("养殖")){
							asList.add("3");
						}else if(split[j].equals("经商/个体经营")){
							asList.add("4");
						}else if(split[j].equals("其他")){
							asList.add("5");
						}
						Collections.sort(asList);
						operateType1 = StringUtils.join(asList, ",");
					}
	        		
	        		busMemberOperate.setOperateType(operateType1);
	        	}
				busMemberOperate.setId(UUIDUtil.getUUID());
				busMemberOperate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				if(busMemberOperate.getLivestockSiteType()!=null){
					if(busMemberOperate.getLivestockSiteType()==0){
						busMemberOperate.setLivestockSiteType(null);
					}
				}
				if(busMemberOperate.getNoSite()!=null){
					if(busMemberOperate.getNoSite()==0){
						busMemberOperate.setNoSite(null);
					}
				}
				busMemberOperate.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				busMemberOperate.setCreOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				busMemberOperate.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				busMemberOperate.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				if(busMemberOperate.getCreDate()==null){
					busMemberOperate.setCreDate(DateUtils.getNowDate());
				}
				busMemberOperate.setUpdDate(DateUtils.getNowDate());
				busMemberOperate = CompletingDegree.caculate(busMemberOperate);
				memberOperateService.insert(busMemberOperate);
			}
	        retJson.put("errno", 2000);
			retJson.put("msg", "上传成功");
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("errno", 3000);
			retJson.put("msg", e.getMessage());
		}
		
        return retJson;
	}
	
	@RequestMapping("/searchOrgs")
	@ResponseBody
	public JSONObject searchOrgs(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
		try {
			 List<SysOrg> list = orgService.selectOrgsByPerson(personId);
			 StringBuilder selectsb = new StringBuilder();
				selectsb.append("<option value=\"\" >--请选择--</option>");
				if(list!=null&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
							selectsb.append("<option value=\""+list.get(i).getOrgId()+"\" >"+list.get(i).getFullCname()+"</option>");
						}
				}
				retJson.put("result", selectsb);
				retJson.put("errno", 2000);
			} catch (Exception e) {
				e.printStackTrace();
				retJson.put("msg", e.getMessage());
				retJson.put("errno", 3000);
		}
		return retJson;
	}
	
}
