package com.nongyeos.loan.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.ReportSourcesql;
import com.nongyeos.loan.admin.entity.ReportTemplate;
import com.nongyeos.loan.admin.service.IReportSourcesqlService;
import com.nongyeos.loan.admin.service.IReportTemplateService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/reportSourcesql")
public class ReportSourcesqlController {

	@Resource
	private IReportSourcesqlService reportSourcesqlService;
	@Resource
	private IReportTemplateService reportTemplateService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ReportSourcesql> selectByTplId(String rptId) {
		PageBeanUtil<ReportSourcesql> pi = new PageBeanUtil<ReportSourcesql>();
		try {
			List<ReportSourcesql> alist = new ArrayList<ReportSourcesql>();
			List<ReportTemplate> tplist = reportTemplateService
					.selectByRptId(rptId);
			List<ReportSourcesql> list = new ArrayList<ReportSourcesql>();
			List<ReportSourcesql> list1 = new ArrayList<ReportSourcesql>();
			List<ReportSourcesql> list2 = new ArrayList<ReportSourcesql>();
			for (ReportTemplate temp : tplist) {
				list = reportSourcesqlService.selectByTplId(temp.getTplId());
				alist.addAll(list);
			}
			for (ReportSourcesql sourcesql : alist) {
				if (sourcesql.getStatus() == 1) {
					list1.add(sourcesql);
				} else {
					list2.add(sourcesql);
				}
			}
			ReportSourcesql temp = null;
			for (int i = 0; i < list1.size(); i++) {
				if (i == list1.size() - 1) {
					break;
				} else {
					for (int j = i + 1; j < list1.size(); j++) {
						if (list1.get(i).getSeqno() > list1.get(j).getSeqno()) {
							temp = list1.get(j);
							list1.set((j), list1.get(i));
							list1.set(i, temp);
						}
					}
				}
			}
			ReportSourcesql temp2 = null;
			for (int i = 0; i < list2.size(); i++) {
				if (i == list2.size() - 1) {
					break;
				} else {
					for (int j = i + 1; j < list2.size(); j++) {
						if (list2.get(i).getSeqno() > list2.get(j).getSeqno()) {
							temp2 = list2.get(j);
							list2.set((j), list2.get(i));
							list2.set(i, temp2);
						}
					}
				}
			}
			alist = new ArrayList<ReportSourcesql>();
			alist.addAll(list1);
			alist.addAll(list2);
			pi.setItems(alist);
			pi.setTotalNum(alist.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ReportSourcesql reportSourcesql) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (reportSourcesql != null) {
				if (reportSourcesql.getDsId().equals("")
						|| reportSourcesql.getDsId() == null) {
					reportSourcesql.setDsId(sysSenoService.getNextString(
							Constants.TABLE_NAME_RPTSOURCESQL, 10, "DS", 1));
					reportSourcesqlService.add(reportSourcesql);
					map.put("msg", "添加成功");
				} else {
					reportSourcesqlService.update(reportSourcesql);
					map.put("msg", "修改成功");
				}
			} else {
				map.put("msg", "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] sourcesqlIds = currIds.split(",");
				for (String sourcesqlId : sourcesqlIds) {
					reportSourcesqlService.del(sourcesqlId);
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
