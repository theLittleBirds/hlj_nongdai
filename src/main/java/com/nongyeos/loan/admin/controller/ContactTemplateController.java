package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusContactTemplate;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.IContactTemplateService;
import com.nongyeos.loan.admin.service.IContractSignatoryService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HtmlToPdfUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.PdfKeywordFinder;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/contacttemplate")
public class ContactTemplateController {
	
	@Autowired
	private IContactTemplateService contactTemplateService;
	
	@Autowired
	private IBusFinsService busFinsService;
	
	@Autowired
	private IContractSignatoryService contractSignatoryService;
	
	@Autowired
	private IPersonService personService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@RequestMapping("/templatelist")
	public String list(){
		return "template/list";
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
	public PageBeanUtil<BusContactTemplate>  selectByPage(HttpServletRequest request, int currentPage, int pageSize, String finsName) throws Exception{
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("personId", request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			if(StrUtils.isNotEmpty(finsName)){
				map.put("finsName",finsName);
			}
			return contactTemplateService.selectByPage(currentPage, pageSize,map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		if(id == null){
			json.put("code", 400);
			json.put("msg", "合同模板id为空");
			return json;
		}
		try {
			BusContactTemplate ct = new BusContactTemplate();
			ct.setId(id);
			ct.setIsDeleted(Constants.COMMON_IS_DELETE);
			contactTemplateService.updateByPrimaryKeySelective(ct);
			json.put("code", 200);
			json.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,String id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("template/edit");
		if(id == null){
			id="";
		}
		mv.addObject("id",id);
		try {
			List<SysPersonorg> list = personService.getOrgs(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			List<String> orgIds = new LinkedList<String>();
			for (int i = 0; i < list.size(); i++) {
				orgIds.add(list.get(i).getOrgId());
			}
			mv.addObject("finsIdList", busFinsService.selectAll(orgIds));
			mv.addObject("signatoryList", contractSignatoryService.selectList(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/selectbyid")
	public BusContactTemplate selectById(String id){
		if(id == null)
			return null;
		try {
			return contactTemplateService.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/save")
	public JSONObject saveOrUpdate(HttpServletRequest request,String id,String content,
			String title,String finsId,Integer isOpean,String variable,String first,String firstMark){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(content)){
			json.put("code", 400);
			json.put("msg", "合同模板为空");
			return json;
		}
		if(StrUtils.isEmpty(title)){
			json.put("code", 400);
			json.put("msg", "合同模板标题为空");
			return json;
		}
		if(StrUtils.isEmpty(finsId)){
			json.put("code", 400);
			json.put("msg", "金融机构为空");
			return json;
		}
		if(StrUtils.isEmpty(first)){
			json.put("code", 400);
			json.put("msg", "甲方不能为空");
			return json;
		}
		if(StrUtils.isEmpty(firstMark)){
			json.put("code", 400);
			json.put("msg", "甲方标识不能为空");
			return json;
		}
		try {
			BusContactTemplate template = new BusContactTemplate();
			template.setContent(content);
			template.setTitle(title);
			template.setFinsId(finsId);
			template.setIsOpean(isOpean);
			template.setFirst(first);
			template.setFirstMark(firstMark);
			template.setUpdDate(new Date());
			template.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			template.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
			//签字位置定位
			String basePath = baseDir + "temp/";
			String dir = FileUtils.getTimeFilePath();
			String pdfName = FileUtils.generateRandomFilename()+".pdf";
			FileUtils.createDirectory(basePath + dir);
			String pdfPath = basePath + dir + pdfName;
			HtmlToPdfUtils.createPdf(content.replace("<br>", "<br/>"), pdfPath);
			//甲方
			List<float[]> jia = PdfKeywordFinder.position(pdfPath, firstMark);
			if(jia == null){
				throw new Exception("甲方签字位置标识未找到");
			}
			int jiaSize = jia.size();
			template.setFirstP((int)jia.get(jiaSize - 1)[0]);
			template.setFirstX(floatRound(jia.get(jiaSize - 1)[1] + 0.015F * (float)firstMark.length()));
			template.setFirstY(floatRound(jia.get(jiaSize - 1)[2]));
			//乙方
			String second = request.getParameter("second");
			if(StrUtils.isNotEmpty(second)){
				String secondMark = request.getParameter("secondMark");
				if(StrUtils.isEmpty(secondMark))
					throw new Exception("请输入乙方标识");
				List<float[]> yi = PdfKeywordFinder.position(pdfPath, secondMark);
				if(yi == null){
					throw new Exception("乙方签字位置标识未找到");
				}
				int yiSize = yi.size();
				template.setSecond(second);
				template.setSecondMark(secondMark);
				template.setSecondP((int)yi.get(yiSize - 1)[0]);
				template.setSecondX(floatRound(yi.get(yiSize - 1)[1] + 0.015F * (float)secondMark.length()));
				template.setSecondY(floatRound(yi.get(yiSize - 1)[2]));
			}			
			//丙方
			String third = request.getParameter("third");
			if(StrUtils.isNotEmpty(third)){
				String thirdMark = request.getParameter("thirdMark");
				if(StrUtils.isEmpty(thirdMark))
					throw new Exception("请输入丙方标识");
				List<float[]> bing = PdfKeywordFinder.position(pdfPath, thirdMark);
				if(bing == null){
					throw new Exception("丙方签字位置标识未找到");
				}
				int bingSize = bing.size();
				template.setThird(third);
				template.setThirdMark(thirdMark);
				template.setThirdP((int)bing.get(bingSize - 1)[0]);
				template.setThirdX(floatRound(bing.get(bingSize - 1)[1] + 0.015F * (float)thirdMark.length()));
				template.setThirdY(floatRound(bing.get(bingSize - 1)[2]));
			}
						
			if(variable == null){
				variable = "";
			}else{
				variable = variable.substring(0, variable.length()-1);
			}				
			template.setVariable(variable);
			if(id == null || "".equals(id)){
				template.setId(UUIDUtil.getUUID());
				template.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				template.setCreDate(new Date());
				template.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				template.setCreOperName(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				contactTemplateService.insert(template);
			}else{
				template.setId(id);
				contactTemplateService.updateByPrimaryKeySelective(template);
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
	
	/**
	 * 保留两位小数
	 * @param f
	 * @return
	 */
	private float floatRound(float f){
		return (float)(Math.ceil(f*100)/100);
	}
}
