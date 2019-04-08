package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.ScoreClass;
import com.nongyeos.loan.app.service.IClassService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/class")
public class ScoreClassController {

	@Resource
	private IClassService classService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "listClass", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreClass> scvarPage(int limit,int offset,String scoreid) {
		try {
			PageBeanUtil<ScoreClass> list = classService.selectByPage(limit, offset,scoreid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ScoreClass sclass) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (sclass != null) {
				if ("".equals(sclass.getClassId())) {
					sclass.setClassId(sysSenoService.getNextString(Constants.TABLE_NAME_CLASS, 10, "CL", 1));
					classService.add(sclass);
					map.put("msg", "添加成功");
				} else {
					classService.update(sclass);
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
				String[] classIds = currIds.split(",");
				for (String classId : classIds) {
					classService.deleteById(classId);
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
