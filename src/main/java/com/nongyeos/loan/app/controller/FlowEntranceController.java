package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.FlowEntrance;
import com.nongyeos.loan.app.service.IFlowEntranceService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/flowEntrance")
public class FlowEntranceController {

	@Resource
	private IFlowEntranceService flowEntranceService;
	
	/**
	 * 流程入口--根据应用Id去查
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "selectByAppId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<FlowEntrance> partnerPage(String appId) {
		PageBeanUtil<FlowEntrance> pi = new PageBeanUtil<FlowEntrance>();
		try {
			List<FlowEntrance> list = flowEntranceService.selectByAppId(appId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	/**
	 * 流程入口--save
	 * @param entrance
	 * @return
	 */
	@RequestMapping(value="save",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(FlowEntrance entrance) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (entrance != null) {
				if (entrance.getEntranceId()==null) {
					flowEntranceService.addEntrance(entrance);
						map.put("msg", "添加成功");
				} else {
					flowEntranceService.updateEntrance(entrance);
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
	
	/**
	 * 流程入口--根据主建删除
	 * @param currIds
	 * @return
	 */
	@RequestMapping(value="delEntrance", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String currIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (currIds != null && !currIds.equals("")) {
                String[] entranceIds = currIds.split(",");
                for (String entranceid : entranceIds) {
                	Integer entranceId=Integer.parseInt(entranceid);
                    flowEntranceService.delEntranceById(entranceId);
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
