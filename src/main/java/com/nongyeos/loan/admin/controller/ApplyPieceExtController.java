package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.resultMap.DynamicDataMap;
import com.nongyeos.loan.admin.service.IApplyPieceExtService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.app.service.IAppParaService;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/applypieceext")
public class ApplyPieceExtController {

	@Autowired
	private IApplyPieceExtService applyPieceExtService;

	@Autowired
	private IAppParaService appParaService;

	@Autowired
	private IIntoPieceService intoPieceService;

	private static final Logger logger = LogManager.getLogger(ApplyPieceExtController.class);

	@RequestMapping("/list")
	@ResponseBody
	public List<DynamicDataMap> list(String intoPieceId,String sectionId) throws Exception{
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
	public JSONObject save(String intoPieceId,String sectionId,String data) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId) || StrUtils.isEmpty(sectionId)){
			json.put("msg", "保存失败");
			json.put("code", 400);
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
			throw new Exception(e);
		}
		return json;
	}
}
