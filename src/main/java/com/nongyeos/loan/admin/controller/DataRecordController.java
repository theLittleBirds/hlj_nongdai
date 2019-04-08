package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiPyReport;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.ApiPyReportService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;

@Controller
@RequestMapping("/dataRecord")
public class DataRecordController {
	
	@Autowired
	private ApiPyReportService pyReportService;
	@Autowired
	private IWebUserService userService; 	
	@Autowired
	private IntoPieceConfig pieceConfig;	
	
	@RequestMapping("/dataRecordAnalysis")
	public String dataRecordAnalysis(HttpServletRequest request, BusIntoPiece intoPiece, ModelMap model){
		try {
			
			String userId = request.getSession().getAttribute(Constants.SESSION_USERCD).toString();
			SysWebUser user = userService.selectUserById(userId);
			Map<String,String> map = new HashMap<String, String>();
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("userid", userId);
			map.put("id", intoPiece.getId());
			map.put("memberName", intoPiece.getMemberName());
			map.put("idCard", intoPiece.getIdCard());
			map.put("bankCardNo", intoPiece.getBankCardNo());
			map.put("phone", intoPiece.getPhone());
			
			String thirdMsgtongdun = HttpClientUtil.doPost(pieceConfig.getTongdunurl(), map, "utf-8");
			//String thirdMsgbairong = HttpClientUtil.doPost(pieceConfig.getBairongurl(), map, "utf-8");
			String thirdMsgpengyuan = HttpClientUtil.doPost(pieceConfig.getPengyuanurl(), map, "utf-8");
			if(thirdMsgtongdun==null){
				throw new Exception("同盾数据查询为空！");
			}
			/*if(thirdMsgbairong==null){
				throw new Exception("百融数据查询为空！");
			}*/
			if(thirdMsgpengyuan==null){
				throw new Exception("鹏元数据查询为空！");
			}
			
			JSONObject thirdMsgJsontongdun = JSONObject.parseObject(thirdMsgtongdun);
			if(!thirdMsgJsontongdun.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("同盾数据查询失败！");
			}
			/*JSONObject thirdMsgJsonbairong = JSONObject.parseObject(thirdMsgbairong);
			if(!thirdMsgJsonbairong.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("百融数据查询失败！");
			}*/
			JSONObject thirdMsgJsonpengyuan = JSONObject.parseObject(thirdMsgpengyuan);
			if(!thirdMsgJsonpengyuan.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("鹏元数据查询失败！");
			}
			
			/**
			 * 同盾，百融合并成大数据报告
			 */
			//同盾
			model.put("report_id", thirdMsgJsontongdun.get("report_id"));//审核报告编号
			model.put("report_time", thirdMsgJsontongdun.get("report_time"));//报告生成时间
			model.put("final_score", thirdMsgJsontongdun.get("final_score"));//报告结论
			model.put("final_decision", thirdMsgJsontongdun.get("final_decision"));//报告评分
	        
			model.put("name", thirdMsgJsontongdun.get("name"));//姓名
			model.put("id_number", thirdMsgJsontongdun.get("id_number"));//身份证
			model.put("mobile", thirdMsgJsontongdun.get("mobile"));//手机号
			model.put("id_card_address", thirdMsgJsontongdun.get("id_card_address"));//身份证所属地
			model.put("mobile_address", thirdMsgJsontongdun.get("mobile_address"));//手机所属地
	        
			model.put("personList", thirdMsgJsontongdun.get("personList"));//个人基本信息核查
			model.put("relationList", thirdMsgJsontongdun.get("relationList"));//关联人信息扫描
			model.put("customList", thirdMsgJsontongdun.get("customList"));//客户行为检测
			model.put("dangerList", thirdMsgJsontongdun.get("dangerList"));//风险信息扫描
			model.put("platformList", thirdMsgJsontongdun.get("platformList"));//多平台借贷申请检测
			
			//百融
			//model.put("third_platform", thirdMsgJsonbairong.get("third_platform").toString().replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\""));//百融平台数据解析
			//System.out.println(thirdMsgJsonbairong.get("third_platform"));
			//System.out.println(thirdMsgJsonbairong.get("third_platform").toString().replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\""));
			
			//鹏元
			JSONObject returnValue = JSONObject.parseObject(thirdMsgJsonpengyuan.getString("countJson"));
			JSONArray cisReport= JSONObject.parseArray(returnValue.getString("cisReport"));
			JSONObject cisArr = JSONObject.parseObject(cisReport.get(0).toString());
			JSONObject personRiskInfo = JSONObject.parseObject(cisArr.getString("personRiskInfo"));
			if(StringUtils.isEmpty(personRiskInfo.getString("details")) && StringUtils.isEmpty(personRiskInfo.getString("stat"))
					&& StringUtils.isEmpty(personRiskInfo.getString("summary"))){
				model.put("isCountJson",true);
				model.put("intoPieceId",intoPiece.getId());
				model.put("idCardNo", intoPiece.getIdCard());
			}else{
				model.put("intoPieceId",intoPiece.getId());
				model.put("idCardNo", intoPiece.getIdCard());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//跳转一个错误页面
			return "";
		}
		return "dataRecord/dataRecordAnalysis";
	}
	
	/**
	 * 鹏元报告
	 */
	@RequestMapping("/pyReportAnalysis")
	public String dataRecordAnalysis(ApiPyReport apiPyReport ,ModelMap model){
		try {
			if(apiPyReport == null){
				throw new Exception("apiPyReport查询信息为空");
			}
			if(StringUtils.isEmpty(apiPyReport.getIntoPieceId())){
				throw new Exception("进件id为空");
			}
			if(StringUtils.isEmpty(apiPyReport.getIdCardNo())){
				throw new Exception("身份证为空");
			}
			
			ApiPyReport pyReport = pyReportService.queryPyReportSelective(apiPyReport);
			if(pyReport == null){
				throw new Exception("没有获的相关数据");
			}
			model.put("countHtml",pyReport.getContentHtml());
			
		} catch (Exception e) {
			e.printStackTrace();
			//跳转一个错误页面
			return "";
		}
		return "dataRecord/pyReportAnalysis";
	}
	
}
