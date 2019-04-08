package com.nongyeos.loan.admin.controller.nj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFollowData;
import com.nongyeos.loan.admin.entity.BusFollowType;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.resultMap.FollowItemMap;
import com.nongyeos.loan.admin.service.IFollowDataService;
import com.nongyeos.loan.admin.service.IFollowItemService;
import com.nongyeos.loan.admin.service.IFollowTypeService;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/operateext")
public class MOperateDynamicController {
	
	
	@Autowired
	private IFollowTypeService followTypeService;
	
	@Autowired
	private IFollowItemService followItemService;
	
	@Autowired
	private IFollowDataService followDataService;
	
	@Autowired
	private IMemberOperateService memberOperateService;
	
	@Autowired
	private IOrgService orgService;
	
	@RequestMapping("/form")
	public ModelAndView form(HttpServletRequest request,String id){
		ModelAndView mv= new ModelAndView();
		mv.addObject("id", id);
		mv.addObject("token", request.getHeader("token"));
		mv.setViewName("nongJing/operateDynamic");
		return mv;
	}
	
	@RequestMapping("/selectall")
	@ResponseBody
	public List<BusFollowType> selectAll(String id){
		try {
			BusMemberOperate mo = memberOperateService.selectByPrimaryKey(id);
			String org_id = mo.getOrgId();
			SysOrg org = orgService.selectByOrgId(org_id);
			if(StrUtils.isNotEmpty(org.getParentOrgId())){
				org_id = org.getParentOrgId();
			}
			return followTypeService.selectAll(org_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/loaddata")
	@ResponseBody
	public List<FollowItemMap> loadData(String type,String id){
		if(StrUtils.isEmpty(type))
			return null;
		if(StrUtils.isEmpty(id))
			return null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", type);
			map.put("id", id);
			return followItemService.selectDynamicByType(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(String id,String data,String followTypeId,HttpServletRequest request){
		JSONObject json =  new JSONObject();
		if(StrUtils.isEmpty(id) || StrUtils.isEmpty(followTypeId)){
			json.put("msg", "保存失败");
			json.put("code", 400);
			return json;
		}
		try {
			Map<String, String> sqlMap = new HashMap<String, String>();
			sqlMap.put("memberOperateId", id);
			sqlMap.put("type", followTypeId);
			followDataService.deleteBeforeSave(sqlMap);
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
					BusFollowData fd = new BusFollowData();
					fd.setId(UUIDUtil.getUUID());
					fd.setMemberOperateId(id);
					fd.setFollowItemId(entry.getKey());
					fd.setItemValue(entry.getValue());
					followDataService.insert(fd);
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
