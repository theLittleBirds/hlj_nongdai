package com.nongyeos.loan.admin.controller.nj;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusPicture;
import com.nongyeos.loan.admin.entity.NjMerGather;
import com.nongyeos.loan.admin.entity.NjOrderGather;
import com.nongyeos.loan.admin.service.IPictureService;
import com.nongyeos.loan.admin.service.NjMerGatherService;
import com.nongyeos.loan.admin.service.NjOrderGatherService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/cNjAssociate")
public class CNjAssociateController {

	@Autowired
	private NjOrderGatherService njOrderGatherService;//联合社订单
	@Autowired
	private NjMerGatherService njMerGatherService;//商户订单
	@Autowired
	private IPictureService pictureService;
	
	/**
	 * 联合社订单列表
	 * 
	 * 只查询“商户已发货”
	 */
	@RequestMapping("/cNjAssociateList")
	@ResponseBody
	public JSONObject cNjAssociateList(String currentPage, String memberName, HttpServletResponse response){
		//先每页显示10条
		int pageSize = 10;
		
		JSONObject json = new JSONObject();
		try {
			if(StringUtils.isEmpty(currentPage)){
				//默认第一页
				currentPage = "1";
			}
			NjOrderGather njOrderGather = new NjOrderGather();
			if(StrUtils.isNotEmpty(memberName)){
				njOrderGather.setMemberName(memberName);
			}
			//只查询“商户已发货”
			njOrderGather.setStatus(Constants.ASSOCIATE_DELIVER_ORDER);
				
			PageBeanUtil<NjOrderGather> njProductOrder = njOrderGatherService.queryNjProductOrderSelective(Integer.valueOf(currentPage), pageSize, njOrderGather);
			if(njProductOrder!=null && njProductOrder.getItems()!=null && njProductOrder.getItems().size()>0){
				json.put("totalPage", njProductOrder.getTotalPage());//总页数
				json.put("totalRow", njProductOrder.getTotalNum());//总记录数
				json.put("currentPage", njProductOrder.getCurrentPage());//当前页数
				json.put("data", njProductOrder.getItems());
			}else{
				json.put("data", new ArrayList<NjOrderGather>());
			}
			
			json.put("message", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			json.put("message", e.getMessage());
		}
		return json;
	}	
	
	@RequestMapping("/mNjAssociateList")
	@ResponseBody
	public JSONObject mNjAssociateList(HttpServletRequest request, HttpServletResponse response){
		String loginId = (String) request.getAttribute("loginId");
		//先每页显示10条
		JSONObject retJson = new JSONObject();
		int pageSize = 10;
		try {
			String page = request.getParameter("page");
			Integer page1 = null;
			if(StringUtils.isEmpty(page)){
				page1 =1;
			}else{
				page1= new Integer(page);
			}
			PageBeanUtil<NjOrderGather> pb = njOrderGatherService.selectByloginId(page1, pageSize,loginId,Constants.ASSOCIATE_DELIVER_ORDER);
			if(pb!=null && pb.getItems()!=null && pb.getItems().size()>0){
				retJson.put("totalPage", pb.getTotalPage());//总页数
				retJson.put("totalRow", pb.getTotalNum());//总记录数
				retJson.put("currentPage", pb.getCurrentPage());//当前页数
				retJson.put("data", pb.getItems());
			}else{
				retJson.put("data", new ArrayList<NjOrderGather>());
			}
			response.setStatus(200);
			retJson.put("message", "查询成功");
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(200);
			retJson.put("message", "查询失败");
			return retJson;
		}
	}	
	
	/**
	 * 商户收货单
	 */
	@RequestMapping("/cNjPicReceive")
	@ResponseBody
	public JSONObject cNjPicReceive(String intoPieceId, String orderId, String hashKey, String receiveDocTime, 
			HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				throw new Exception("进件id为空");
			}
			if(StringUtils.isEmpty(orderId)){
				throw new Exception("订单号为空");
			}
			if(StringUtils.isEmpty(hashKey)){
				throw new Exception("七牛key为空");
			}
			if(StringUtils.isEmpty(receiveDocTime)){
				throw new Exception("发货单时间不能为空");
			}
			String loginId = (String) request.getAttribute("loginId");//取出过滤器的loginId
			
			String key[] = hashKey.split(",");
			for (int i = 0; i < key.length; i++) {
				BusPicture p = new BusPicture();
				
				p.setId(UUIDUtil.getUUID());
				p.setIntoPieceId(intoPieceId);
				p.setFileType(6);//农户收货单
				p.setType("2");//非公众图片
				p.setOrderId(orderId);
				p.setOrderType("2");//联合社类型
				p.setQiniuKey(key[i]);//七牛
				p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				p.setCreOperId(loginId);
				p.setCreDate(DateUtils.getNowDate());
				p.setUpdDate(DateUtils.getNowDate());
				pictureService.insert(p);
			}
			
			//联合社订单
			NjOrderGather njOrderGather = njOrderGatherService.queryOrderGatherByPk(orderId);
			njOrderGather.setStatus(Constants.ASSOCIATE_FINISH_ORDER);//联合社   已完成
			njOrderGather.setReceiveDocTime(receiveDocTime);//收货单时间
			njOrderGather.setUpdateDate(DateUtils.getNowDate());
			njOrderGatherService.updateOrderGatherByPKSelective(njOrderGather);
			
			//商户订单
			NjMerGather njMerGather = njMerGatherService.queryMerGatherByOrderId(orderId);
			njMerGather.setStatus(Constants.MER_FINISH_ORDER);//商户   已完成
			njMerGather.setReceiveDocTime(receiveDocTime);//收货单时间
			njMerGather.setUpdateDate(DateUtils.getNowDate());
			njMerGatherService.updateMerGatherByPKSelective(njMerGather);
			
			json.put("message", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			json.put("message", e.getMessage());
		}
		return json;
	}	
	
}
