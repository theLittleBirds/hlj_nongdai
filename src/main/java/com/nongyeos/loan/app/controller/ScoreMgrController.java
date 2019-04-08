package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.model.MsgQueue;
import com.nongyeos.loan.core.service.FlowMgr;
import com.nongyeos.loan.core.service.ScoreMgr;
@Controller
@RequestMapping("/scoreMgr")
public class ScoreMgrController {
	
	@Autowired
	private ScoreMgr scoreMgr;
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@RequestMapping("/scoreCalculateMgr")
	@ResponseBody
	public Map<String, Object> scoreCalculateMgr(String intoPieceId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StrUtils.isEmpty(intoPieceId)){
			map.put("code", 400);
			map.put("msg","参数为空");
			return map;
		}
		try {
			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(intoPiece == null){
				throw new Exception("没有查询到对应的进件");
			}
			
			AppEntry appEntry  = appEntryMapper.queryByAppModeId(intoPieceId);
			AppApplication appApplication = new AppApplication();
			appApplication.setAppId(intoPiece.getModelId());
			
			BusinessObject bo = new BusinessObject(appEntry, intoPiece, appApplication);
			List<ScoreScore> scoreScoreList = scoreMgr.calculate(bo, null);
			
			bo.setMsgQueue(new MsgQueue());
			JSONObject result = flowMgrImpl.score(bo);
			
			if(result.getIntValue("code") == 400){
				return result;
			}
			intoPiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			
			map.put("code", 200);
			map.put("scoreCalculate", scoreScoreList);
			map.put("creditCapital", intoPiece.getCreditCapital() == null ? "" :intoPiece.getCreditCapital().toString());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
}

