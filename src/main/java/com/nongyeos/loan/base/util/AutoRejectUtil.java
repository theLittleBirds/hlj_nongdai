package com.nongyeos.loan.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusRejectReason;

/**
 * 
 * Title:AutoRejectUtil 
 * Description:  自动拒件
 * @author lcg
 * @date 2018年4月27日 上午10:27:16
 */
public class AutoRejectUtil {
	/**
	 * 
	 * @Title: tongdunReject 
	 * @Description: 百融自动拒件
	 * @param @param tongdunJson
	 * @param @param intoPieceId
	 * @param @param idCard
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	public static Map<String, Object> bairongReject(JSONObject bairongJson, String intoPieceId, String idCard){
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = true;
		List<BusRejectReason> reasons = new LinkedList<BusRejectReason>();
		JSONObject specialList_c = bairongJson.getJSONObject("specialList_c");
//		JSONObject bankFourPro = bairongJson.getJSONObject("bankFourPro");
//		if(bankFourPro!=null){
//			JSONObject product = bankFourPro.getJSONObject("product");
//			if(product!=null&&"验证不匹配".equals(product.get("msg"))){
//				BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"四要素验证不匹配",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//				reasons.add(busRejectReason);
//				flag=false;
//			}
//		}
		
		//特殊名单核查
//		if("0".equals(specialList_c.get("sl_id_bank_bad"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中银行中风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
//		if("0".equals(specialList_c.get("sl_id_bank_lost"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中银行高风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
		if("0".equals(specialList_c.get("sl_id_bank_refuse"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中银行拒绝",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
//		if("0".equals(specialList_c.get("sl_id_p2p_bad"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中非银中风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
//		if("0".equals(specialList_c.get("sl_id_p2p_lost"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中非银高风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
		if("0".equals(specialList_c.get("sl_id_p2p_refuse"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中非银拒绝",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
//		if("0".equals(specialList_c.get("sl_id_nbank_p2p_bad"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中p2p中风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
//		if("0".equals(specialList_c.get("sl_id_nbank_p2p_lost"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中p2p高风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
		if("0".equals(specialList_c.get("sl_id_nbank_p2p_refuse"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中p2p拒绝",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
//		if("0".equals(specialList_c.get("sl_id_nbank_mc_bad"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中小贷中风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
//		if("0".equals(specialList_c.get("sl_id_nbank_mc_lost"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中小贷高风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
		if("0".equals(specialList_c.get("sl_id_nbank_mc_refuse"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中小贷拒绝",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
//		if("0".equals(specialList_c.get("sl_id_nbank_ca_bad"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中现金类分期中风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
//		if("0".equals(specialList_c.get("sl_id_nbank_ca_lost"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中现金类分期高风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
		if("0".equals(specialList_c.get("sl_id_nbank_ca_refuse"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中现金类分期拒绝",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
//		if("0".equals(specialList_c.get("sl_id_nbank_cf_bad"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中消费类分期中风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
//		if("0".equals(specialList_c.get("sl_id_nbank_cf_lost"))){
//			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中消费类分期高风险",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//			reasons.add(busRejectReason);
//			flag=false;
//		}
		if("0".equals(specialList_c.get("sl_id_nbank_cf_refuse"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中消费类分期拒绝",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
		if("0".equals(specialList_c.get("sl_id_court_bad"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中法院失信人名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
		if("0".equals(specialList_c.get("sl_id_court_executed"))){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中法院被执行人名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
		JSONObject applyLoanMon = bairongJson.getJSONObject("applyLoanMon");
		int num = applyLoanMon.getIntValue("alm_d7_id_bank_allnum")+applyLoanMon.getIntValue("alm_d7_id_nbank_allnum")+applyLoanMon.getIntValue("alm_d7_id_nbank_p2p_allnum")+applyLoanMon.getIntValue("alm_d7_id_nbank_mc_allnum")+applyLoanMon.getIntValue("alm_d7_id_nbank_ca_allnum")+applyLoanMon.getIntValue("alm_d7_id_nbank_cf_allnum")-1;
		if(num>15){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"7天内在银行(含信用卡)、非银、P2P、小贷、现金分期、消费分期申请次数合计大于15次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
		int num1 = applyLoanMon.getIntValue("alm_m1_id_bank_allnum")+applyLoanMon.getIntValue("alm_m1_id_nbank_allnum")+applyLoanMon.getIntValue("alm_m1_id_nbank_p2p_allnum")+applyLoanMon.getIntValue("alm_m1_id_nbank_mc_allnum")+applyLoanMon.getIntValue("alm_m1_id_nbank_ca_allnum")+applyLoanMon.getIntValue("alm_m1_id_nbank_cf_allnum")-1;
		if(num1>18){
			BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"近1个月内在银行(含信用卡)、非银、P2P、小贷、现金分期、消费分期申请次数合计大于18次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
			reasons.add(busRejectReason);
			flag=false;
		}
		map.put("rejected", flag);
		map.put("reject_reason", reasons);
		return map;
	}
	/**
	 * 
	 * @Title: tongdunReject 
	 * @Description: 同盾自动拒件
	 * @param @param tongdunJson
	 * @param @param intoPieceId
	 * @param @param idCard
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	public static Map<String, Object> tongdunReject(JSONObject tongdunJson, String intoPieceId, String idCard){
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = true;
		List<BusRejectReason> reasons = new LinkedList<BusRejectReason>();
		JSONArray jsonArray = tongdunJson.getJSONArray("risk_items");
		if(jsonArray!=null&&jsonArray.size()>0){
			for (Object object : jsonArray) {
				JSONObject risk_items = JSON.parseObject(JSONObject.toJSONString(object));
				Integer item_id = risk_items.getInteger("item_id");
				if(9382243==item_id){
					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中法院失信名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
					reasons.add(busRejectReason);
					flag=false;
				}
				if(9382253==item_id){
					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中犯罪通缉名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
					reasons.add(busRejectReason);
					flag=false;
				}
				if(9382263==item_id){
					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中法院执行名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
					reasons.add(busRejectReason);
					flag=false;
				}
				if(9382313==item_id){
					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中法院结案名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
					reasons.add(busRejectReason);
					flag=false;
				}
//				if(9382383==item_id){
//					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中欠款公司法人代表名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//					reasons.add(busRejectReason);
//					flag=false;
//				}
//				if(9382293==item_id){
//					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证号命中高风险关注名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//					reasons.add(busRejectReason);
//					flag=false;
//				}
//				if(9382523==item_id){
//					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"手机号命中高风险关注名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//					reasons.add(busRejectReason);
//					flag=false;
//				}
//				if(9382283==item_id){
//					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"身份证命中信贷逾期名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//					reasons.add(busRejectReason);
//					flag=false;
//				}
//				if(9382533==item_id){
//					BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"手机号命中信贷逾期名单",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//					reasons.add(busRejectReason);
//					flag=false;
//				}
//				if(9383283==item_id){
//					JSONObject item_detail = risk_items.getJSONObject("item_detail");
//					JSONArray platform_detail_dimension = item_detail.getJSONArray("platform_detail_dimension");
//					for (Object object2 : platform_detail_dimension) {
//						JSONObject parseObject = JSON.parseObject(JSONObject.toJSONString(object2));
//						if("借款人身份证详情".equals(parseObject.getString("dimension"))){
//							if(parseObject.getInteger("count")>14){
//								BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"7天内身份证命中多平台申请次数大于15次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
//								reasons.add(busRejectReason);
//								flag=false;
//							}
//						}
//					}
//				}
				if(9383293==item_id){
					JSONObject item_detail = risk_items.getJSONObject("item_detail");
					JSONArray platform_detail_dimension = item_detail.getJSONArray("platform_detail_dimension");
					for (Object object2 : platform_detail_dimension) {
						JSONObject parseObject = JSON.parseObject(JSONObject.toJSONString(object2));
						if("借款人身份证详情".equals(parseObject.getString("dimension"))){
							if(parseObject.getInteger("count")>12){
								BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"1个月内身份证命中多平台申请次数大于12次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
								reasons.add(busRejectReason);
								flag=false;
							}
						}
					}
				}
				if(9383303==item_id){
					JSONObject item_detail = risk_items.getJSONObject("item_detail");
					JSONArray platform_detail_dimension = item_detail.getJSONArray("platform_detail_dimension");
					for (Object object2 : platform_detail_dimension) {
						JSONObject parseObject = JSON.parseObject(JSONObject.toJSONString(object2));
						if("借款人身份证详情".equals(parseObject.getString("dimension"))){
							if(parseObject.getInteger("count")>20){
								BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"3个月内身份证命中多平台申请次数大于20次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
								reasons.add(busRejectReason);
								flag=false;
							}
						}
					}
				}
				if(9688023==item_id){
					JSONObject item_detail = risk_items.getJSONObject("item_detail");
					JSONArray platform_detail_dimension = item_detail.getJSONArray("platform_detail_dimension");
					for (Object object2 : platform_detail_dimension) {
						JSONObject parseObject = JSON.parseObject(JSONObject.toJSONString(object2));
						if("借款人身份证详情".equals(parseObject.getString("dimension"))){
							if(parseObject.getInteger("count")>30){
								BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"6个月内身份证命中多平台申请次数大于30次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
								reasons.add(busRejectReason);
								flag=false;
							}
						}
					}
				}
				if(9688033==item_id){
					JSONObject item_detail = risk_items.getJSONObject("item_detail");
					JSONArray platform_detail_dimension = item_detail.getJSONArray("platform_detail_dimension");
					for (Object object2 : platform_detail_dimension) {
						JSONObject parseObject = JSON.parseObject(JSONObject.toJSONString(object2));
						if("借款人身份证详情".equals(parseObject.getString("dimension"))){
							if(parseObject.getInteger("count")>40){
								BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"12个月内身份证命中多平台申请次数大于40次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
								reasons.add(busRejectReason);
								flag=false;
							}
						}
					}
				}
				if(9383313==item_id){
					JSONObject item_detail = risk_items.getJSONObject("item_detail");
					JSONArray platform_detail_dimension = item_detail.getJSONArray("platform_detail_dimension");
					for (Object object2 : platform_detail_dimension) {
						JSONObject parseObject = JSON.parseObject(JSONObject.toJSONString(object2));
						if("借款人身份证详情".equals(parseObject.getString("dimension"))){
							if(parseObject.getInteger("count")>10){
								BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"3个月内在多个平台被放款大于10次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
								reasons.add(busRejectReason);
								flag=false;
							}
						}
					}
				}
			}
		}
		map.put("rejected", flag);
		map.put("reject_reason", reasons);
		return map;
	}
	//鹏元拒件
	public static Map<String, Object> pengyuanReject(JSONObject pengyuanJson,
			String intoPieceId, String idCard) {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean flag = true;
		List<BusRejectReason> reasons = new LinkedList<BusRejectReason>();
		JSONArray cisReport = pengyuanJson.getJSONArray("cisReport");
		for (int i = 0; i < cisReport.size(); i++) {
			JSONObject cisReporti = cisReport.getJSONObject(i);
			JSONObject personRiskInfo = cisReporti.getJSONObject("personRiskInfo");
			if(personRiskInfo!=null){
				JSONObject stat = personRiskInfo.getJSONObject("stat");
				if(stat!=null){
					if(StringUtils.isNotEmpty(stat.getString("alCount"))&&stat.getInteger("alCount")>0){
						BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"司法案例信息"+stat.getString("alCount")+"条",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
						reasons.add(busRejectReason);
						flag=false;
					}
					if(StringUtils.isNotEmpty(stat.getString("cqggCount"))&&stat.getInteger("cqggCount")>0){
						BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"催欠公告信息"+stat.getString("cqggCount")+"条",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
						reasons.add(busRejectReason);
						flag=false;
					}
					if(StringUtils.isNotEmpty(stat.getString("swCount"))&&stat.getInteger("swCount")>0){
						BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"税务行政执法信息"+stat.getString("swCount")+"条",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
						reasons.add(busRejectReason);
						flag=false;
					}
					if(StringUtils.isNotEmpty(stat.getString("sxCount"))&&stat.getInteger("sxCount")>0){
						BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"司法失信信息"+stat.getString("sxCount")+"条",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
						reasons.add(busRejectReason);
						flag=false;
					}
					if(StringUtils.isNotEmpty(stat.getString("wdyqCount"))&&stat.getInteger("wdyqCount")>0){
						BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"网贷逾期信息"+stat.getString("wdyqCount")+"条",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
						reasons.add(busRejectReason);
						flag=false;
					}
					if(StringUtils.isNotEmpty(stat.getString("zxCount"))&&stat.getInteger("zxCount")>0){
						BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,idCard,"司法执行信息"+stat.getString("zxCount")+"条",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
						reasons.add(busRejectReason);
						flag=false;
					}
				}
			}
		}
		map.put("rejected", flag);
		map.put("reject_reason", reasons);
		return map;
	}
}
