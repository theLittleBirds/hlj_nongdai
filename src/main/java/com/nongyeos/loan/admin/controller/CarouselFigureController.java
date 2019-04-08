package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.SysCarouselFigure;
import com.nongyeos.loan.admin.service.ICarouselFigureService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/carouselFigure")
public class CarouselFigureController {
	
	private static final Logger logger = LogManager.getLogger(CarouselFigureController.class);
	
	@Autowired
	private ICarouselFigureService carouselFigureService;
	
	@Value("${rootpoint.mark}")
	private String appMark;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		try {
			JSONObject obj = JSONObject.parseObject(appMark);
			Set<String> set = obj.keySet();
			Iterator<String> it = set.iterator();
			StringBuffer sb = new StringBuffer();
			sb.append("<option value=''>--请选择--</option>");
			while(it.hasNext()){
				String name = it.next();
				sb.append("<option value='"+name+"'>"+name+"</option>");
			}
			mv.addObject("app", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("carouselFigure/list");		
		return mv;
	}
	
	@RequestMapping("/carouselFigureList")
	@ResponseBody
	public PageBeanUtil<SysCarouselFigure> carouselFigureList(int currentPage,int pageSize,HttpServletRequest request) throws Exception{
		PageBeanUtil<SysCarouselFigure> pbu = null;
		try {
			SysCarouselFigure cF = new SysCarouselFigure();
			cF.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			pbu =carouselFigureService.selectListByPage(cF,currentPage,pageSize);
			List<SysCarouselFigure> items = pbu.getItems();
			for (SysCarouselFigure sysCarouselFigure : items) {
				String urlLimit10k2 = QiNiuUtil.getUrlLimit10k(sysCarouselFigure.getQiniuKey());
				String url = QiNiuUtil.getUrl(sysCarouselFigure.getQiniuKey());
				sysCarouselFigure.setName("<div style='width: 80px; height: 50px; position: relative; overflow: hidden; display: block;'><image src='"+urlLimit10k2+"'></div>");
				sysCarouselFigure.setUpdOperName(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return pbu;
	}
	
	
	@RequestMapping("/upload")
	@ResponseBody
	public JSONObject upload(MultipartFile tu,String channel){
		JSONObject json =  new JSONObject();
		try {
			String fileName = tu.getOriginalFilename();
			String extName = fileName.substring(fileName.lastIndexOf("."));
			String name = fileName.substring(0, fileName.lastIndexOf("."));
			long size = tu.getSize();
			String tuSize = FileUtils.convertSize(size);
			String qiniukey = QiNiuUtil.upLoadByteData(tu.getBytes(),UUIDUtil.getUUID()+extName);
			Integer maxNumber = carouselFigureService.selectMaxNumber();
			if(maxNumber==null){
				maxNumber=0;
			}
			SysCarouselFigure carouselFigure = new SysCarouselFigure();
			carouselFigure.setId(UUIDUtil.getUUID());
			carouselFigure.setChannel(channel);
			carouselFigure.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			carouselFigure.setName(name);
			carouselFigure.setExtName(extName);
			carouselFigure.setSize(tuSize);
			carouselFigure.setQiniuKey(qiniukey);
			carouselFigure.setStatus(Constants.PICTRUE_SHOW);
			carouselFigure.setNumber(maxNumber+1);
			carouselFigure.setCreDate(DateUtils.getNowDate());
			carouselFigure.setUpdDate(DateUtils.getNowDate());
			carouselFigureService.save(carouselFigure);
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "图片上传失败");
		}
		return json;
	}
	
	@RequestMapping("/del")
	public String del(String id) throws Exception{
		try {
			SysCarouselFigure carouselFigure = new SysCarouselFigure();
			carouselFigure.setIsDeleted(Constants.COMMON_IS_DELETE);
			carouselFigure.setId(id);
			carouselFigure.setUpdDate(DateUtils.getNowDate());
			carouselFigureService.del(carouselFigure);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/carouselFigure/index";
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Map<String, Object> update(SysCarouselFigure carouselFigure){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			carouselFigureService.update(carouselFigure);
			retMap.put("msg", "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "保存失败！");
			retMap.put("errorno", 10000);
		}
		return retMap;
	}
	
}
