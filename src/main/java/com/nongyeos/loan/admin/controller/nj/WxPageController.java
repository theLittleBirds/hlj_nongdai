package com.nongyeos.loan.admin.controller.nj;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;

@Controller
@RequestMapping("/nj/wxpage")
public class WxPageController {
	
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IWebUserService userService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	
	@RequestMapping("/notice")
	public String notice(){
		return "nongJing/notice";
	}
	
	@RequestMapping("/ext")
	public ModelAndView ext(HttpServletRequest request,String intoPieceId,String token)throws Exception{
		String id = stringRedisTemplate.opsForValue().get("token"+token);
		if(id == null){
			throw new Exception("请登录");
		}
		ModelAndView mv= new ModelAndView();
		mv.addObject("intoPieceId", intoPieceId);
		mv.addObject("token", token);
		mv.setViewName("nongJing/wxdynamic");
		return mv;
	}
	
	@RequestMapping("/tongdunBairong")
	public ModelAndView tongdunBairong(HttpServletRequest request,HttpServletResponse response,String intoPieceId,String token) throws Exception{
		ModelAndView mv= new ModelAndView();
		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("未传进件标识！");
		}
		String userId = stringRedisTemplate.opsForValue().get("token"+token);
		if(userId == null){
			throw new Exception("请登录");
		}
		
		try {
			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(intoPieceId);
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
			String thirdMsgbairong = HttpClientUtil.doPost(pieceConfig.getBairongurl(), map, "utf-8");
			if(thirdMsgtongdun==null){
				throw new Exception("同盾数据查询为空！");
			}
			if(thirdMsgbairong==null){
				throw new Exception("百融数据查询为空！");
			}
			
			JSONObject thirdMsgJsontongdun = JSONObject.parseObject(thirdMsgtongdun);
			if(!thirdMsgJsontongdun.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("同盾数据查询失败！");
			}
			JSONObject thirdMsgJsonbairong = JSONObject.parseObject(thirdMsgbairong);
			if(!thirdMsgJsonbairong.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("百融数据查询失败！");
			}
			
//			ApiThirdMsg selectByPrimaryKey = thirdMsgService.selectByPrimaryKey("37cb21c10f034749964c6314aef1b189");
//			String thirdMsgtongdun = selectByPrimaryKey.getContent();
//			JSONObject thirdMsgJsontongdun = JSONObject.parseObject(thirdMsgtongdun);
//			ApiThirdMsg selectByPrimaryKey1 = thirdMsgService.selectByPrimaryKey("a19b685bb79b481da2825d6e0e3c4c45");
//			String thirdMsgbairong = selectByPrimaryKey1.getContent();
//			JSONObject thirdMsgJsonbairong = JSONObject.parseObject(thirdMsgbairong);
			/**
			 * 同盾，百融合并成大数据报告
			 */
			//同盾
			mv.addObject("report_id", thirdMsgJsontongdun.get("report_id"));//审核报告编号
			mv.addObject("report_time", thirdMsgJsontongdun.get("report_time"));//报告生成时间
			mv.addObject("final_score", thirdMsgJsontongdun.get("final_score"));//报告结论
			mv.addObject("final_decision", thirdMsgJsontongdun.get("final_decision"));//报告评分
	        
			mv.addObject("name", thirdMsgJsontongdun.get("name"));//姓名
			mv.addObject("id_number", thirdMsgJsontongdun.get("id_number"));//身份证
			mv.addObject("mobile", thirdMsgJsontongdun.get("mobile"));//手机号
			mv.addObject("id_card_address", thirdMsgJsontongdun.get("id_card_address"));//身份证所属地
			mv.addObject("mobile_address", thirdMsgJsontongdun.get("mobile_address"));//手机所属地
	        
			mv.addObject("personList", thirdMsgJsontongdun.get("personList"));//个人基本信息核查
			mv.addObject("relationList", thirdMsgJsontongdun.get("relationList"));//关联人信息扫描
			mv.addObject("customList", thirdMsgJsontongdun.get("customList"));//客户行为检测
			mv.addObject("dangerList", thirdMsgJsontongdun.get("dangerList"));//风险信息扫描
			mv.addObject("platformList", thirdMsgJsontongdun.get("platformList"));//多平台借贷申请检测
			
			//百融
			mv.addObject("third_platform", thirdMsgbairong.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\""));//百融平台数据解析
			mv.addObject("intoPieceId", intoPieceId);
			mv.addObject("token", token);
			mv.setViewName("dataRecord/dataRecordAnalysis");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
		
		return mv;
	}
}
