package com.nongyeos.loan.admin.controller.nj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.resultMap.DynamicDataMap;
import com.nongyeos.loan.admin.service.IApplyPieceExtService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.app.entity.AppSection;
import com.nongyeos.loan.app.service.IAppParaService;
import com.nongyeos.loan.app.service.ISectionService;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/ext")
public class UApplyPieceExtController {
		
	@Autowired
	private ISectionService sectionService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IApplyPieceExtService applyPieceExtService;
	
	@Autowired
	private IAppParaService appParaService;
	
	@RequestMapping("/form")
	public ModelAndView page(HttpServletRequest request,String intoPieceId){
		ModelAndView mv= new ModelAndView();
		mv.addObject("intoPieceId", intoPieceId);
		mv.addObject("token", request.getHeader("token"));
		mv.setViewName("nongJing/dynamic");
		return mv;
	}
	
	@RequestMapping("/getsectionlist")
	@ResponseBody
	public List<AppSection> getSectionByIpId(String intoPieceId) {
		if(intoPieceId == null || "".equals(intoPieceId))
			return null;
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(ip == null || ip.getModelId() == null || "".equals(ip.getModelId()))
				return null;
			return sectionService.selectAll(ip.getModelId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/loaddata")
	@ResponseBody
	public List<DynamicDataMap> loadData(String intoPieceId,String sectionId){
		try {
			if(StrUtils.isEmpty(sectionId) || StrUtils.isEmpty(intoPieceId))
				return null;
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			DynamicDataMap dd = new DynamicDataMap();
			dd.setSectionId(sectionId);
			dd.setIntoPieceId(intoPieceId);
			List<DynamicDataMap> list = applyPieceExtService.selectBySectionId(dd);
			for (int i = 0; i < list.size(); i++) {
				String optionsGroup = list.get(i).getOptionsGroup();
				if(StrUtils.isNotEmpty(optionsGroup)){
					AppPara app = new AppPara();
					app.setAppId(ip.getModelId());
					app.setGroupName(optionsGroup);
					List<AppPara> appList = appParaService.selectByGroupName(app);
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < appList.size(); j++) {
						if(j==0){
							sb.append(appList.get(j).getDescr()+":"+appList.get(j).getValue());
						}else{
							sb.append(","+appList.get(j).getDescr()+":"+appList.get(j).getValue());
						}						
					}
					list.get(i).setOptionsGroup(sb.toString());
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(String intoPieceId,String sectionId,String data,HttpServletResponse response){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId) || StrUtils.isEmpty(sectionId)){
			json.put("msg", "保存失败");
			json.put("code", 400);
			return json;
		}
		try {
			DynamicDataMap dd = new DynamicDataMap();
			dd.setIntoPieceId(intoPieceId);
			dd.setSectionId(sectionId);
			applyPieceExtService.deleteByIpAndItem(dd);
			if(StrUtils.isNotEmpty(data)){
				Map<String,String> map = new HashMap<String, String>();
				String[] all = data.split(",");
				for (int i = 0; i < all.length; i++) {
					String[] one = all[i].split(":");
					if(one.length != 2){
						continue;
					}
					if(map.get(one[0]) == null){
						map.put(one[0], one[1]);
					}else{
						String value = map.get(one[0]) +"," +one[1];
						map.put(one[0], value);
					}
				}
				for (Map.Entry<String, String> entry : map.entrySet()) {
					BusApplyPieceExt ap = new BusApplyPieceExt();
					ap.setExtId(UUIDUtil.getUUID());
					ap.setIntoPieceId(intoPieceId);
					ap.setItemId(entry.getKey());
					ap.setItemValue(entry.getValue());
					applyPieceExtService.insert(ap);
				}
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
