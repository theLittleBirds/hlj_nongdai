package com.nongyeos.loan.admin.controller.nj;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusExamine;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.service.IExamineService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.DateUtils;

@Controller
@RequestMapping("/nj/auditOpinions")
public class CAuditOpinionsController {
	private static final Logger logger = LogManager.getLogger(CAuditOpinionsController.class);
	
	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@RequestMapping("/index")
	@ResponseBody
	public JSONObject auditOpinions(HttpServletRequest request,HttpServletResponse response,String intoPieceId)throws Exception{
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(intoPieceId)){
			response.setStatus(400);
			retJson.put("message", "进件标识为空！");
			retJson.put("errno", 3001);
			return retJson;
		}
		BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
		List<BusExamine> examineList = examineService.historyByIpId(intoPieceId);
		JSONObject obj = new JSONObject();
		JSONArray trailOpinion = new JSONArray();
		JSONArray firstTrial = new JSONArray();
		JSONArray nextTrial = new JSONArray();
		JSONArray lenderTrial = new JSONArray();
		JSONArray lastTrial = new JSONArray();
		if(examineList!=null&&examineList.size()>0){
			for (int i = 0; i < examineList.size(); i++) {
				String node = examineList.get(i).getNode();
				BusExamine busExamine = examineList.get(i);
				String date = DateUtils.format(busExamine.getExamineDate(), "yyyy-MM-dd HH:mm:ss");
				switch (node) {
				//初审意见 授信额度：${opinion.capital}元；借款期限：${opinion.term}个月；金融产品：${opinion.productId}；审核人：${opinion.examineName}；审核时间：${opinion.examineDate?string("yyyy-MM-dd HH:mm:ss")}；意见：${opinion.examineOpinion}
				case "2":
					firstTrial.add(" 授信额度："+busExamine.getCapital()+"元；借款期限："+busExamine.getTerm()+"个月；金融产品："+busExamine.getProductId()+"；审核人："+busExamine.getExamineName()+"；审核时间："+date+"；意见："+busExamine.getExamineOpinion()+"；" );
					break;
					//复审意见
				case "3":
					nextTrial.add(" 授信额度："+busExamine.getCapital()+"元；借款期限："+busExamine.getTerm()+"个月；金融产品："+busExamine.getProductId()+"；审核人："+busExamine.getExamineName()+"；审核时间："+date+"；意见："+busExamine.getExamineOpinion()+"；" );
					break;
					//保函
				case "4":
					lenderTrial.add(" 授信额度："+busExamine.getCapital()+"元；借款期限："+busExamine.getTerm()+"个月；金融产品："+busExamine.getProductId()+"；审核人："+busExamine.getExamineName()+"；审核时间："+date+"；意见："+busExamine.getExamineOpinion()+"；" );
					break;
				default:
					break;
				}
			}
		}
		if(firstTrial.size()<1&&nextTrial.size()<1&&lenderTrial.size()<1&&lastTrial.size()<1){
			retJson.put("message", "审核意见未出，请耐心等待！");
			retJson.put("errno", 0);
			response.setStatus(400);
			return retJson;
		}
		trailOpinion.add(ip.getTrialOpinion()==null?"":ip.getTrialOpinion());
		obj.put("trailOpinion", trailOpinion);
		obj.put("firstTrial", firstTrial);
		obj.put("nextTrial", nextTrial);
		obj.put("lenderTrial", lenderTrial);
		obj.put("lastTrial", lastTrial);
		retJson.put("data", obj);
		retJson.put("errno", 0);
		response.setStatus(200);
		return retJson;
	}
}
