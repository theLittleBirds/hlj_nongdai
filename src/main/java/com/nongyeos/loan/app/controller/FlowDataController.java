package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.FlowData;
import com.nongyeos.loan.app.service.IFlowDataService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/data")
public class FlowDataController {

	@Resource
	private IFlowDataService flowDataService;

	@RequestMapping(value = "listLeft", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<FlowData> partnerPage1(String nodeId, Integer type) {
		PageBeanUtil<FlowData> pi = new PageBeanUtil<FlowData>();
		try {
			List<FlowData> list = flowDataService.selectByNodeIdAndType(nodeId,type);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@RequestMapping(value = "listRight", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<FlowData> partnerPage2(String nodeId, Integer type) {
		PageBeanUtil<FlowData> pi = new PageBeanUtil<FlowData>();
		try {
			List<FlowData> list = flowDataService.selectByNodeIdAndType(nodeId,type);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	@RequestMapping(value = "saveleft", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveleft(FlowData flowData) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String itemIds = flowData.getObjectId();
			if (itemIds != null && !itemIds.equals("")) {
				String[] itemids = itemIds.split(",");
				if (itemids.length > 0) {
					for (int i = 0; i < itemids.length; i++) {
						flowData.setObjectId(itemids[i]);
						flowData.setObjectType((short) 1);
						if (flowDataService.existenceObject(flowData.getControlType(),flowData.getObjectId(),flowData.getObjectType(),flowData.getNodeId())) {
							map.put("msg", "此权限类型已存在相同的对象，请重新选择");
						} else {
							if (flowData.getDataId() == null) {
								flowDataService.addFlowData(flowData);
								map.put("msg", "添加成功");
							} else {
								flowDataService.updateFlowDataz(flowData);
								map.put("msg", "修改成功");
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "添加失败");
		}
		return map;
	}

	@RequestMapping(value = "saveright", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveright(FlowData flowData) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String itemIds = flowData.getObjectId();
			if (itemIds != null && !itemIds.equals("")) {
				String[] itemids = itemIds.split(",");
				if (itemids.length > 0) {
					for (int i = 0; i < itemids.length; i++) {
						flowData.setObjectId(itemids[i]);
						flowData.setObjectType((short) 2);
						if (flowDataService.existenceObject(flowData.getControlType(),flowData.getObjectId(),flowData.getObjectType(),flowData.getNodeId())) {
							map.put("msg", "此权限类型已存在相同的对象，请重新选择");
						} else {
							if (flowData.getDataId() == null) {
								flowDataService.addFlowData(flowData);
								map.put("msg", "添加成功");
							} else {
								flowDataService.updateFlowDataz(flowData);
								map.put("msg", "修改成功");
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "添加失败");
		}
		return map;
	}

	@RequestMapping(value = "delData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] DataIds = currIds.split(",");
				for (String DataId : DataIds) {
					Integer dataid = Integer.parseInt(DataId);
					flowDataService.deleteById(dataid);
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
