package com.nongyeos.loan.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.ScoreComvalue;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.service.IScoreComcaseService;
import com.nongyeos.loan.app.service.IScoreComvalueService;
import com.nongyeos.loan.app.service.IScvarService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/comvalue")
public class ScoreComvalueController {

	@Resource
	private IScoreComvalueService comvalueService;
	@Resource
	private IScoreComcaseService comcaseService;
	@Resource
	private IScvarService scvarService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreComvalue> selectPage(String scoreid) {
		PageBeanUtil<ScoreComvalue> pi = new PageBeanUtil<ScoreComvalue>();
		List<ScoreComvalue> list1 = new ArrayList<ScoreComvalue>();
		try {
			short type = 2;
			List<ScoreScvar> list = scvarService.selectByPage(scoreid, type);
			for (ScoreScvar scvar : list) {
				String scvarId = scvar.getScvarId();
				List<ScoreComvalue> list2 = comvalueService.selectByPage(scvarId);
				list1.addAll(list2);
			}
			pi.setItems(list1);
			pi.setTotalNum(list1.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ScoreComvalue comvalue) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (comvalue != null) {
				if ("".equals(comvalue.getCvId())) {
					comvalue.setCvId(sysSenoService.getNextString(Constants.TABLE_NAME_COMVALUE, 10, "CV", 1));
					comvalueService.add(comvalue);
					map.put("msg", "添加成功");
				} else {
					comvalueService.update(comvalue);
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
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] comvalueIds = currIds.split(",");
				for (String comvalueId : comvalueIds) {
					comcaseService.deleteByCvId(comvalueId);
					comvalueService.deleteById(comvalueId);
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
