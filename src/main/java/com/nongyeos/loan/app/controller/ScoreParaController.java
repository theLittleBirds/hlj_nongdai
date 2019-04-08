package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.ScorePara;
import com.nongyeos.loan.app.service.IScoreParaService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/scorePara")
public class ScoreParaController {

	@Resource
	private IScoreParaService scoreParaService;

	/**
	 * 参数--分页全查
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "select", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScorePara> selectAll(int limit, int offset) {
		try {
			PageBeanUtil<ScorePara> list = scoreParaService.selectAll(limit, offset);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 参数--添加和修改
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ScorePara scorePara) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (scorePara.getId() == null || scorePara.getId().equals("")) {
				scoreParaService.add(scorePara);
				map.put("msg", "添加成功");
			} else {
				scoreParaService.update(scorePara);
				map.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 参数--单个删除和批量删除
	 * 
	 * @param currIds
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] scoreParaIds = currIds.split(",");
				for (String scoreParaid : scoreParaIds) {
					Integer scoreParaId = Integer.parseInt(scoreParaid);
					scoreParaService.delete(scoreParaId);
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
	
	@RequestMapping("/getParaDS")
	@ResponseBody
	public String getParaDS() throws Exception{
		String strJson = "[";
		List<ScorePara> list = scoreParaService.getParaDS();
		if(list != null && list.size() > 0){
			ScorePara beanPara = null;
			for(int i = 0;i <list.size();i++){
				beanPara = list.get(i);
				if(beanPara != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanPara.getGroupDescr()+ "', parameterValue:'" + beanPara.getGroupName() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
}
