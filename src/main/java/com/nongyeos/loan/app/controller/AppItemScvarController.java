package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.AppItemScvar;
import com.nongyeos.loan.app.service.IAppItemScvarService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/itemScvar")
public class AppItemScvarController {

	@Resource
	private IAppItemScvarService itemScvarService;

	@RequestMapping(value = "getItemScvarByAppscId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<AppItemScvar> partnerPage(String appscId) {
		PageBeanUtil<AppItemScvar> pi = new PageBeanUtil<AppItemScvar>();
		try {
			List<AppItemScvar> list = itemScvarService.selectByAppscId(appscId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppItemScvar appItemScvar,String appId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (appItemScvar != null) {
				appItemScvar.setAppId(appId);
				String appscId=appItemScvar.getAppscId();
				String itemId = appItemScvar.getItemId();
				String scvarId = appItemScvar.getScvarId();
				List<AppItemScvar> list1 = null;
				List<AppItemScvar> list2 = null;
				if (appItemScvar.getItemscvarId() == null) {
					list1 = itemScvarService.existItemId(itemId,appscId);
					list2 = itemScvarService.existItemscvarId(scvarId,appscId);
					if (list1 != null && list1.size() > 0) {
						map.put("msg", "数据项已存在，请重新选择！");
						return map;
					}
					if (list2 != null && list2.size() > 0) {
						map.put("msg", "评分变量已存在，请重新选择！");
						return map;
					} else{
						itemScvarService.addAppItem(appItemScvar);
						map.put("msg", "添加成功");
						return map;
					}
				} else if (appItemScvar.getItemscvarId() != null || !appItemScvar.getItemscvarId().equals("")) {
					Integer appItemScvarId = appItemScvar.getItemscvarId();
					list1 = itemScvarService.existItemId1(appItemScvarId,itemId,appscId);
					list2 = itemScvarService.existItemscvarId1(appItemScvarId,scvarId,appscId);
					if (list1 != null && list1.size() > 0) {
						map.put("msg", "数据项已存在，请重新选择！");
						return map;
					}
					if (list2 != null && list2.size() > 0) {
						map.put("msg", "评分变量已存在，请重新选择！");
						return map;
					}else{
						itemScvarService.updateAppItem(appItemScvar);
						map.put("msg", "修改成功");
					}
				}
			} else {
				map.put("msg", "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "delAppItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] appItemIds = currIds.split(",");
				for (String appItemId : appItemIds) {
					Integer appItemid = Integer.parseInt(appItemId);
					itemScvarService.delAppItem(appItemid);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
