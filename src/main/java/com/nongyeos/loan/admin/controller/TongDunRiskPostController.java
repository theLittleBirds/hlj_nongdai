package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiTdRiskPost;
import com.nongyeos.loan.admin.entity.ApiThirdMsg;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.resultMap.ApiTdRiskPostMap;
import com.nongyeos.loan.admin.service.IApiTdRiskPostService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/tongDunRiskPost")
public class TongDunRiskPostController {
	
	@Autowired
	private IApiTdRiskPostService apiTdRiskPostService;
	@Autowired
	private IWebUserService userService; 
	
	/**
	 * 同盾贷后监控列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/loanRiskPost")
	public String loanRiskPost(){
		return "loanPost/tongdunLoanRiskPost";
	}

	/**
	 * 异步获取同盾贷后监控风险列表，bootstrap的table表格用
	 * @param currentPage
	 * @param pageSize
	 * @param memberName
	 * @param idCard
	 * @param phone
	 * @return
	 */
	@RequestMapping("/asyncLoanRiskPostList")
    @ResponseBody
	public PageBeanUtil<ApiTdRiskPost> asyncLoanRiskPostList(int currentPage, int pageSize, String memberName, 
			String idCard, String phone, HttpServletRequest request){
		try {
			ApiTdRiskPostMap riskPost = new ApiTdRiskPostMap();
			if(StrUtils.isNotEmpty(memberName)){
				riskPost.setMemberName(memberName);
			}
				
			if(StrUtils.isNotEmpty(idCard)){
				riskPost.setIdCard(idCard);
			}
				
			if(StrUtils.isNotEmpty(phone)){
				riskPost.setPhone(phone);
			}
			
			riskPost.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			return apiTdRiskPostService.queryAllRiskPost(currentPage, pageSize, riskPost);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 同盾贷后风险详情
	 */
	@RequestMapping("/tdloanRiskPostInfo")
	public String tdloanRiskPostInfo(ModelMap model, ApiTdRiskPost apiTdRiskPost){
		try {
			if(apiTdRiskPost==null || StringUtils.isEmpty(apiTdRiskPost.getId())){
				throw new Exception("贷后风险详情id为空");
			}
			
			ApiTdRiskPost tdRiskPost = apiTdRiskPostService.queryRiskByPrimaryKey(apiTdRiskPost.getId());
			if(tdRiskPost==null || StringUtils.isEmpty(tdRiskPost.getData())){
				//throw new Exception("没有获取相应贷后风险");
				
				//暂时修改为无风险列表也允许查看风险报告 
				model.put("bad_rate", null);//"坏"客户占比
				model.put("score", null);//用户行为分
				model.put("overdue_level", null);//逾期风险等级
				
				model.put("blacklist", null);//风险名单：身份证号命中法院执行风险名单
				model.put("fuzzyblacklist", null);//模糊名单：身份证号命中法院执行模糊名单
				model.put("greylist", null);//关注名单：身份证号命中高风险关注名单
				model.put("p2pdiscredit", null);//信贷逾期名单： 身份证号命中信贷逾期名单
				model.put("precrosspartner", null);//多平台申请：身份证号命中多平台申请 
				model.put("postcrosspartner", null);//多平台负债：身份证号命中多平台负债 
				
				return "loanPost/tongdunLoanRiskPostInfo";
			}
		
			JSONObject td_data = JSONObject.parseObject(tdRiskPost.getData());
			
			//风险详情列表
			JSONArray risk_list = JSONArray.parseArray(td_data.getString("risk_list"));
			//同盾贷后监控风险列表数据结构JSONArray里只有一个元素，故写死取0
			JSONObject risk_list_info = JSONObject.parseObject(risk_list.getString(0));
			
			String bad_rate = risk_list_info.getString("bad_rate");//"坏"客户占比
			String score = risk_list_info.getString("score");//用户行为分
			String overdue_level = risk_list_info.getString("overdue_level");//逾期风险等级
			
			//风险详情   
			JSONArray risk_detail = JSONArray.parseArray(risk_list_info.getString("risk_detail"));
			
			//风险名单：身份证号命中法院执行风险名单
			JSONObject blacklist = new JSONObject();
			//模糊名单：身份证号命中法院执行模糊名单
			JSONObject fuzzyblacklist = new JSONObject();
			//关注名单：身份证号命中高风险关注名单
			JSONObject greylist = new JSONObject();
			//信贷逾期名单： 身份证号命中信贷逾期名单
			JSONObject p2pdiscredit = new JSONObject();
			//多平台申请：身份证号命中多平台申请 
			JSONObject precrosspartner = new JSONObject();
			//多平台负债：身份证号命中多平台负债 
			JSONObject postcrosspartner = new JSONObject();
			//处理风险类别
			for (Object riskObj : risk_detail) {
				JSONObject risk_detail_obj = JSONObject.parseObject(riskObj.toString());
				//风险名单：身份证号命中法院执行风险名单
				if("BLACKLIST".equals(risk_detail_obj.getString("rule_type"))){
					blacklist.put("fraud_type", risk_detail_obj.getString("fraud_type"));//风险类型
					blacklist.put("detail", risk_detail_obj.getString("detail"));//风险详情
					blacklist.put("monitor_field", risk_detail_obj.getString("monitor_field"));//监控字段
					blacklist.put("monitor_value", risk_detail_obj.getString("monitor_value"));//监控字段值
					blacklist.put("rule_name", risk_detail_obj.getString("rule_name"));//规则名称
					blacklist.put("rule_type", risk_detail_obj.getString("rule_type"));//规则类型
					blacklist.put("evidence_type", risk_detail_obj.getString("evidence_type"));//证据类型
					blacklist.put("monitor_field_zh", risk_detail_obj.getString("monitor_field_zh"));//监控字段对应的中文描述
					blacklist.put("evidence_type_zh", risk_detail_obj.getString("evidence_type_zh"));//证据类型对应的中文描述
					blacklist.put("fraud_type_zh", risk_detail_obj.getString("fraud_type_zh"));//风险类型对应的中文描述
					blacklist.put("hit_time", risk_detail_obj.getString("hit_time"));//命中时间
				}
				//模糊名单：身份证号命中法院执行模糊名单
				if("FUZZYBLACKLIST".equals(risk_detail_obj.getString("rule_type"))){
					fuzzyblacklist.put("fraud_type", risk_detail_obj.getString("fraud_type"));//风险类型
					fuzzyblacklist.put("detail", risk_detail_obj.getString("detail"));//风险详情
					fuzzyblacklist.put("monitor_field", risk_detail_obj.getString("monitor_field"));//监控字段
					fuzzyblacklist.put("monitor_value", risk_detail_obj.getString("monitor_value"));//监控字段值
					fuzzyblacklist.put("fuzzy_id", risk_detail_obj.getString("fuzzy_id"));//模糊身份证号
					fuzzyblacklist.put("rule_name", risk_detail_obj.getString("rule_name"));//规则名称
					fuzzyblacklist.put("rule_type", risk_detail_obj.getString("rule_type"));//规则类型
					fuzzyblacklist.put("evidence_type", risk_detail_obj.getString("evidence_type"));//证据类型
					fuzzyblacklist.put("monitor_field_zh", risk_detail_obj.getString("monitor_field_zh"));//监控字段对应的中文描述
					fuzzyblacklist.put("evidence_type_zh", risk_detail_obj.getString("evidence_type_zh"));//证据类型对应的中文描述
					fuzzyblacklist.put("fraud_type_zh", risk_detail_obj.getString("fraud_type_zh"));//风险类型对应的中文描述
					fuzzyblacklist.put("hit_time", risk_detail_obj.getString("hit_time"));//命中时间
				}
				//关注名单：身份证号命中高风险关注名单
				if("GREYLIST".equals(risk_detail_obj.getString("rule_type"))){
					greylist.put("monitor_field", risk_detail_obj.getString("monitor_field"));//监控字段
					greylist.put("monitor_value", risk_detail_obj.getString("monitor_value"));//监控字段值
					greylist.put("rule_type", risk_detail_obj.getString("rule_type"));//规则类型
					greylist.put("rule_name", risk_detail_obj.getString("rule_name"));//规则名称
					greylist.put("evidence_type", risk_detail_obj.getString("evidence_type"));//证据类型
					greylist.put("risk_level", risk_detail_obj.getString("risk_level"));//风险等级
					greylist.put("risk_type", risk_detail_obj.getString("risk_type"));//风险类型
					greylist.put("monitor_field_zh", risk_detail_obj.getString("monitor_field_zh"));//监控字段对应的中文描述
					greylist.put("evidence_type_zh", risk_detail_obj.getString("evidence_type_zh"));//证据类型对应的中文描述
					greylist.put("risk_level_zh", risk_detail_obj.getString("risk_level_zh"));//风险等级对应的中文描述
					//Array类型
					greylist.put("risk_type_zh", risk_detail_obj.getString("risk_type_zh"));//风险类型列表对应的中文描述   
				} 
				//信贷逾期名单： 身份证号命中信贷逾期名单
				if("P2PDISCREDIT".equals(risk_detail_obj.getString("rule_type"))){
					p2pdiscredit.put("monitor_field", risk_detail_obj.getString("monitor_field"));//监控字段
					p2pdiscredit.put("monitor_value", risk_detail_obj.getString("monitor_value"));//监控字段值
					p2pdiscredit.put("rule_type", risk_detail_obj.getString("rule_type"));//规则类型
					p2pdiscredit.put("rule_name", risk_detail_obj.getString("rule_name"));//规则名称
					p2pdiscredit.put("cal_type", risk_detail_obj.getString("cal_type"));//计算类型
					p2pdiscredit.put("cal_value", risk_detail_obj.getString("cal_value"));//计算值
					
					/**
					 * details里的字段：overdue_amount_range（逾期金额），overdue_count（逾期笔数），overdue_day_range（逾期天数）
					 */
					//JSONArray类型
					p2pdiscredit.put("details", risk_detail_obj.getString("details"));//风险详情列表	
					p2pdiscredit.put("monitor_field_zh", risk_detail_obj.getString("monitor_field_zh"));//监控字段对应的中文描述
				}
				//多平台申请：身份证号命中多平台申请 
				if("PRECROSSPARTNER".equals(risk_detail_obj.getString("rule_type"))){
					precrosspartner.put("monitor_field", risk_detail_obj.getString("monitor_field"));//扫描字段
					precrosspartner.put("monitor_value", risk_detail_obj.getString("monitor_value"));//扫描字段值
					precrosspartner.put("rule_type", risk_detail_obj.getString("rule_type"));//规则类型
					precrosspartner.put("rule_name", risk_detail_obj.getString("rule_name"));//规则名称
					//Array类型
					precrosspartner.put("industry", risk_detail_obj.getString("industry"));//第一行业
					//Array类型
					precrosspartner.put("industry_second", risk_detail_obj.getString("industry_second"));//第二行业
					precrosspartner.put("new_platform_total", risk_detail_obj.getString("new_platform_total"));//新增平台总数
					/**
					 * platform_industry_details里的字段：hit_time（命中时间），hit_details（命中行业详情）
					 * 
					 * hit_details（命中行业详情）：JSONArray类型
					 * hit_details（命中行业详情）里的字段：industry（行业），industry_zh（行业对应中文描述），total（行业平台总数）
					 */
					//JSONArray类型
					precrosspartner.put("platform_industry_details", risk_detail_obj.getString("platform_industry_details"));//新增行业平台详情
					precrosspartner.put("monitor_field_zh", risk_detail_obj.getString("monitor_field_zh"));//监控字段对应的中文描述
					//Array类型
					precrosspartner.put("industry_zh", risk_detail_obj.getString("industry_zh"));//第一行业对应的中文描述
					//Array类型
					precrosspartner.put("industry_second_zh", risk_detail_obj.getString("industry_second_zh"));//第二行业对应的中文描述
				}
				//多平台负债：身份证号命中多平台负债 
				if("POSTCROSSPARTNER".equals(risk_detail_obj.getString("rule_type"))){
					postcrosspartner.put("monitor_field", risk_detail_obj.getString("monitor_field"));//扫描字段
					postcrosspartner.put("monitor_value", risk_detail_obj.getString("monitor_value"));//扫描字段值
					postcrosspartner.put("rule_type", risk_detail_obj.getString("rule_type"));//规则类型
					postcrosspartner.put("rule_name", risk_detail_obj.getString("rule_name"));//规则名称
					//Array类型
					postcrosspartner.put("industry", risk_detail_obj.getString("industry"));//第一行业
					//Array类型
					postcrosspartner.put("industry_second", risk_detail_obj.getString("industry_second"));//第二行业
					postcrosspartner.put("new_platform_total", risk_detail_obj.getString("new_platform_total"));//新增平台总数
					/**
					 * platform_industry_details里的字段：hit_time（命中时间），hit_details（命中行业详情）
					 * 
					 * hit_details（命中行业详情）：JSONArray类型
					 * hit_details（命中行业详情）里的字段：industry（行业），industry_zh（行业对应中文描述），total（行业平台总数）
					 */
					//JSONArray类型
					postcrosspartner.put("platform_industry_details", risk_detail_obj.getString("platform_industry_details"));//新增行业平台详情
					postcrosspartner.put("monitor_field_zh", risk_detail_obj.getString("monitor_field_zh"));//监控字段对应的中文描述
					//Array类型
					postcrosspartner.put("industry_zh", risk_detail_obj.getString("industry_zh"));//第一行业对应的中文描述
					//Array类型
					postcrosspartner.put("industry_second_zh", risk_detail_obj.getString("industry_second_zh"));//第二行业对应的中文描述
				}
			}
			
			model.put("bad_rate", bad_rate);//"坏"客户占比
			model.put("score", score);//用户行为分
			model.put("overdue_level", overdue_level);//逾期风险等级
			
			model.put("blacklist", blacklist.toString());//风险名单：身份证号命中法院执行风险名单
			model.put("fuzzyblacklist", fuzzyblacklist.toString());//模糊名单：身份证号命中法院执行模糊名单
			model.put("greylist", greylist.toString());//关注名单：身份证号命中高风险关注名单
			model.put("p2pdiscredit", p2pdiscredit.toString());//信贷逾期名单： 身份证号命中信贷逾期名单
			model.put("precrosspartner", precrosspartner.toString());//多平台申请：身份证号命中多平台申请 
			model.put("postcrosspartner", postcrosspartner.toString());//多平台负债：身份证号命中多平台负债 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loanPost/tongdunLoanRiskPostInfo";
	}
	
	/**
	 * 获取同盾贷后监控风险
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loanRiskPostInfo")
	public boolean loanRiskPostInfo(ModelMap model, HttpServletRequest request, ApiThirdMsg apiThirdMsg, 
			String begin_time, String end_time){
		//TODO 逻辑等接口工程实现完在添加
		try {
			String userId = request.getSession().getAttribute(Constants.SESSION_USERCD).toString();
			SysWebUser user = userService.selectUserById(userId);
			Map<String,String> map = new HashMap<String, String>();
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("userid", userId);
			map.put("intoPieceId", apiThirdMsg.getIntoPieceId());
			map.put("reportId", apiThirdMsg.getReportId());
			map.put("begin_time", begin_time);
			map.put("end_time", end_time);
			String result = HttpClientUtil.doPost("http://127.0.0.1:8086/apiTongDun/tongdunRiskList", map, "utf-8");
			
			JSONObject httpResult = JSONObject.parseObject(result);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
