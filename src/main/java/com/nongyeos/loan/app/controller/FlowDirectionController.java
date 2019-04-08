package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.entity.FlowDirection;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.app.service.IFlowDirectionService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/flowDirection")
public class FlowDirectionController {

	@Resource
	private IFlowDirectionService flowDirectionService;
	@Resource
	private IFlowNodeService fleNodeService;
	
	/**
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "selectByNodeId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<FlowDirection> partnerPage(String nodeId) {
		PageBeanUtil<FlowDirection> pi = new PageBeanUtil<FlowDirection>();
		try {
			List<FlowDirection> list = flowDirectionService.selectByNodeId(nodeId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	/**
	 * @param dirction
	 * @return
	 */
	@RequestMapping(value="save",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(FlowDirection dirction) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (dirction != null) {
				if (dirction.getDirectionId()==null) {
					flowDirectionService.addDirction(dirction);
						map.put("msg", "添加成功");
				} else {
					flowDirectionService.updateDirction(dirction);
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
	 * @param currIds
	 * @return
	 */
	@RequestMapping(value="delDirections", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String currIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (currIds != null && !currIds.equals("")) {
                String[] directionIds = currIds.split(",");
                for (String directionId : directionIds) {
                	Integer directionid=Integer.parseInt(directionId);
                	flowDirectionService.delDirectionsById(directionid);
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
	@RequestMapping(value="getStartNodeDSByAppId", method = RequestMethod.POST)
    @ResponseBody
    public String getStartNodeDSByAppId(String appId){
		String strJson = "[";
        List<FlowNode> list = null;
        try {
        	list=fleNodeService.selectByAppId(appId);
            if (list != null && list.size() > 0) {
                FlowNode beanFlowNode = null;
                for (int i = 0; i < list.size(); i++) {
                	beanFlowNode = list.get(i);
                    if (beanFlowNode != null) {
                        if (i > 0)
                            strJson = strJson + ", ";
                        	strJson = strJson + "{parameterName:'" + beanFlowNode.getCname() + "', parameterValue:'" + beanFlowNode.getNodeId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
	}
	
	@RequestMapping(value="allNodeDS", method = RequestMethod.POST)
    @ResponseBody
    public String getAllNodeDS(){
		String strJson = "[";
        List<FlowNode> list = null;
        try {
        	list=fleNodeService.selectAll();
            if (list != null && list.size() > 0) {
                FlowNode beanFlowNode = null;
                for (int i = 0; i < list.size(); i++) {
                	beanFlowNode = list.get(i);
                    if (beanFlowNode != null) {
                        if (i > 0)
                            strJson = strJson + ", ";
                        	strJson = strJson + "{parameterName:'" + beanFlowNode.getCname() + "', parameterValue:'" + beanFlowNode.getNodeId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
	}
}
