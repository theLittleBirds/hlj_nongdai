package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppScore;
import com.nongyeos.loan.app.service.IAppScoreService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/appScore")
public class AppScoreController {

	@Resource
	private IAppScoreService appScoreService;
	@Resource
	private ISysSenoService sysSenoService;
	
	/**
	 * 根据appid查询评分卡
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "getAppScoreByAppId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<AppScore> partnerPage(String appId) {
		PageBeanUtil<AppScore> pi = new PageBeanUtil<AppScore>();
		try {
			List<AppScore> list = appScoreService.selectByAppId(appId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	/**
	 * 保存
	 * @param appScore
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="save",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppScore appScore) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (appScore != null) {
				if(appScore.getAppscId()==null || appScore.getAppscId().equals("")) {
					appScore.setAppscId(sysSenoService.getNextString(Constants.TABLE_NAME_APPSCORE, 12, "ASC", 1));
					appScoreService.addAppScore(appScore);
					map.put("msg", "添加成功");
				} else {
					appScoreService.updateAppScore(appScore);
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
	
	@RequestMapping(value="delAppScore", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String currIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (currIds != null && !currIds.equals("")) {
                String[] appScoreIds = currIds.split(",");
                for (String appScoreId : appScoreIds) {
                	appScoreService.delAppScore(appScoreId);
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
