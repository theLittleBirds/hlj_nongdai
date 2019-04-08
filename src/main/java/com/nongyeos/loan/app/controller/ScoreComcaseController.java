package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.ScoreComcase;
import com.nongyeos.loan.app.service.IScoreComcaseService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/comcase")
public class ScoreComcaseController {

	@Resource
	private IScoreComcaseService comcaseService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreComcase> selectPage(String cvId) {
		PageBeanUtil<ScoreComcase> pi = new PageBeanUtil<ScoreComcase>();
		try {
			List<ScoreComcase> list = comcaseService.selectByPage(cvId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ScoreComcase comcase = new ScoreComcase();
			comcase = JSON.parseObject(json,ScoreComcase.class);
			if (comcase != null) {
				if ("".equals(comcase.getId()) || comcase.getId()==null) {
					comcaseService.add(comcase);
					map.put("msg", "添加成功");
				} else {
					comcaseService.update(comcase);
					map.put("msg", "修改成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e);
		}
		return map;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		int Id = 0;
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] comcaseIds = currIds.split(",");
				for (String comcaseId : comcaseIds) {
					Id = Integer.parseInt(comcaseId);
					comcaseService.deleteById(Id);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e);
		}
		return map;
	}
	
}
