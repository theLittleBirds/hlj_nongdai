package com.nongyeos.loan.admin.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.base.util.Constants;

@RequestMapping("/main")
@Controller
public class MainController {
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@RequestMapping("/timeout")
	@ResponseBody
	public String timeOut(){
		return "hi";
	}
	
	@RequestMapping("/todaystatistics")
	@ResponseBody
	public JSONObject todayStatistics(HttpServletRequest request){
		JSONObject json = new JSONObject();	
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> status = new LinkedList<String>();
		status.add("1");
		status.add("2");
		status.add("3");
		status.add("4");
		status.add("5");
		map.put("status", status);
		map.put("personId", request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
		try {
			List<AppEntry> entry = appEntryService.todayStatistics(map);
			int status1 = 0;
			int other = 0;
			for (int i = 0; i < entry.size(); i++) {
				if("1".equals(entry.get(i).getNodeName())){
					status1 = entry.get(i).getSeqno();
				}else{
					other = other + entry.get(i).getSeqno();
				}
			}
			map.put("map", "1");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String yyyymmdd = df.format(new Date());
			map.put("yyyymmdd", yyyymmdd);
			//今日进件
			json.put("cre", intoPieceService.todayCreCount(map));
			//今日拒绝
			json.put("status0", appEntryService.todayRefuse(map));
			//待核资料
			json.put("status1", status1);
			//电审
			json.put("other", other);
			List<BusIntoPiece> ip = intoPieceService.todayMoneyToMember(map);
			//放款笔数
			json.put("moneytimes", ip.size());
			BigDecimal money = new BigDecimal(0);
			for (int i = 0; i < ip.size(); i++) {
				if(ip.get(i).getCapital() != null){
					money = money.add(ip.get(i).getCapital());
				}
			}
			//放款金额
			json.put("money",money);
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("/weekipstatistics")
	@ResponseBody
	public JSONObject weekIpStatistics(HttpServletRequest request){
		JSONObject json = new JSONObject();	
		//页面展示天数
		int showDay = 7;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -showDay); 
		String yyyymmdd = df.format(calendar.getTime());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("yyyymmdd", yyyymmdd);
		map.put("personId", request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
		try {
			List<IntoPieceMap> list = intoPieceService.weekIpStatistics(map);
			Map<String,Integer> result = new HashMap<String,Integer>();
			for (int i = 0; i < list.size(); i++) {
				result.put(list.get(i).getYyyymmdd(), list.get(i).getTotal());
			}
			JSONArray day = new JSONArray();
			JSONArray times = new JSONArray();
			for (int i = 0; i < showDay; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, 1); 
				String data = df.format(calendar.getTime());
				day.add(data);
				times.add(result.get(data) == null ? 0 : result.get(data));
			}
			json.put("day", day);
			json.put("times", times);
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("/weekmoneystatistics")
	@ResponseBody
	public JSONObject weekMoneyStatistics(HttpServletRequest request){
		JSONObject json = new JSONObject();	
		//页面展示天数
		int showDay = 7;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -showDay); 
		String yyyymmdd = df.format(calendar.getTime());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("yyyymmdd", yyyymmdd);
		map.put("personId", request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
		try {
			List<IntoPieceMap> list = intoPieceService.weekMoneyStatistics(map);
			Map<String,BigDecimal> result = new HashMap<String,BigDecimal>();
			for (int i = 0; i < list.size(); i++) {
				result.put(list.get(i).getYyyymmdd(), list.get(i).getCapital());
			}
			JSONArray day = new JSONArray();
			JSONArray money = new JSONArray();
			for (int i = 0; i < showDay; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, 1); 
				String data = df.format(calendar.getTime());
				day.add(data);
				money.add(result.get(data) == null ? 0 : result.get(data));
			}
			json.put("day", day);
			json.put("money", money);
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
}
