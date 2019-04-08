package com.nongyeos.loan.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/picture")
public class PictureController {
	
	@Autowired
	private IPictureService pictureService;
	@Autowired
	private NjOrderGatherService njOrderGatherService;//联合社订单
	@Autowired
	private NjMerGatherService njMerGatherService;//商户订单	
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(String hashKey,Integer fileType,String intoPieceId,String type,String orderId,String orderType,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(fileType == null){
			fileType = Constants.PICTURE_OTHER;//类型：其他
		}
		if(StrUtils.isEmpty(intoPieceId)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");
			return json;
		}
		if(StrUtils.isEmpty(hashKey)){
			json.put("code", 400);
			json.put("msg", "图片标识为空");
			return json;
		}
		if(StrUtils.isEmpty(type)){
			type = "1";//默认是公共图片
		}
		try {
			String key[] = hashKey.split(",");
			for (int i = 0; i < key.length; i++) {
				if(StrUtils.isNotEmpty(key[i])){
					BusPicture p = new BusPicture();
					p.setIntoPieceId(intoPieceId);
					p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
					p.setQiniuKey(key[i]);
					p.setType(type);
/*					int check = pictureService.existenceByKey(p);
					//如果存在，不再保存
					if(check>0){
						continue;
					}*/
					p.setId(UUIDUtil.getUUID());
					p.setFileType(fileType);
					p.setType(type);
					p.setOrderId(orderId);
					p.setOrderType(orderType);
					p.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					p.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
					p.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
					p.setCreDate(new Date());
					p.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					p.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
					p.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
					p.setUpdDate(new Date());
					pictureService.insert(p);
				}
			}			
			json.put("code", 200);
			json.put("msg", "保存成功");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "保存失败");
			return json;
		}
	}
	
	
	@RequestMapping("/list")
	@ResponseBody
	public JSONArray list(String intoPieceId,String type,Integer fileType,String orderId,String orderType){
		try {
			if(StrUtils.isEmpty(intoPieceId))
				return null;
			BusPicture p = new BusPicture();
			p.setIntoPieceId(intoPieceId);
			p.setType(type);
			p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			if(fileType != null)
				p.setFileType(fileType);
			if(StrUtils.isNotEmpty(orderType))
				p.setOrderType(orderType);
			if(StrUtils.isNotEmpty(orderId))
				p.setOrderId(orderId);
			List<BusPicture> list = pictureService.selectByIpId(p);
			if(list.size() == 0)
				return null;
			JSONArray arr = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("key", list.get(i).getQiniuKey());
				obj.put("fileType", list.get(i).getFileType());
				arr.add(obj);
			}
			return QiNiuUtil.getUrlEndJpg(arr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 进件公共照片  线下打款 、线下保证金
	 * @param intoPieceId
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping("/intopiecepicture")
	public ModelAndView intopiecePicture(String intoPieceId){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("union/intopiecepicture");
		mv.addObject("intoPieceId", intoPieceId);
		mv.addObject("token", QiNiuUtil.upToken());
		return mv;
	}
	
	/**
	 * 订单图片
	 * @param intoPieceId
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping("/showlist")
	public ModelAndView showList(String intoPieceId, String type, String id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("union/picture");
		mv.addObject("intoPieceId", intoPieceId);
		mv.addObject("token", QiNiuUtil.upToken());
		mv.addObject("type", type);//用来区分定金，尾款，发货单
		mv.addObject("id", id);//联合社订单id
		return mv;
	}
	
	/**
	 * 检查对应类型的图片
	 * @param intoPieceId
	 * @param type
	 * @param fileType
	 * @return
	 */
	@RequestMapping("/checkList")
	@ResponseBody
	public JSONObject checkList(String intoPieceId,String type,Integer fileType, String orderId,String orderType){
		JSONObject json = new JSONObject();
		try {
			
			BusPicture p = new BusPicture();
			p.setIntoPieceId(intoPieceId);
			p.setOrderId(orderId);
			p.setOrderType(orderType);
			p.setType(type);
			p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			if(fileType != null)
				p.setFileType(fileType);
			List<BusPicture> list = pictureService.selectByIpId(p);
			if(list.size() == 0){
				throw new Exception("请上传该类型图片");
			}

			json.put("code", 200);
			json.put("msg", "存在该类型图片成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}	
	
	/**
	 * 商户全部照片   特殊处理
	 * @param intoPieceId
	 * @param type
	 * @param fileType
	 * @return
	 */
	@RequestMapping("/merList")
	@ResponseBody
	public JSONArray merList(String intoPieceId,String type,Integer fileType,String orderId,String orderType){
		try {
			if(StrUtils.isEmpty(intoPieceId))
				return null;
			BusPicture p = new BusPicture();
			p.setIntoPieceId(intoPieceId);
			p.setType(type);
			p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			if(fileType != null)
				p.setFileType(fileType);
			if(StrUtils.isNotEmpty(orderType))
				p.setOrderType(orderType);
			if(StrUtils.isNotEmpty(orderId))
				p.setOrderId(orderId);
			List<BusPicture> list = pictureService.selectByIpId(p);
			
			/**
			 * 通过orderId和orderType来查看对应订单的“联合社”或“商户”图片
			 * 
			 * type：2， orderType：2 为联合社图片
			 * type：2， orderType：1 为商户图片
			 */
			if(!StringUtils.isEmpty(type) && "2".equals(type)){
				//商户   orderId：商户主键
				if(!StringUtils.isEmpty(orderType) && "1".equals(orderType) && !StringUtils.isEmpty(orderId)){
					NjMerGather njMerGather = njMerGatherService.queryMerGatherByPK(orderId);
					
					if(njMerGather != null){
						BusPicture _p = new BusPicture();
						_p.setIntoPieceId(intoPieceId);
						_p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
						_p.setType(type);
						_p.setOrderId(njMerGather.getOrderId());
						List<BusPicture> _pList = pictureService.selectByIpId(_p);//联合社图片
						for (BusPicture busPicture : _pList) {
							if(list == null){
								list = new ArrayList<BusPicture>();
							}
							list.add(busPicture);
						}
					}
				}
				//联合社   orderId：联合社主键
				if(!StringUtils.isEmpty(orderType) && "2".equals(orderType) && !StringUtils.isEmpty(orderId)){
					NjMerGather njMerGather = njMerGatherService.queryMerGatherByOrderId(orderId);
					
					if(njMerGather != null){
						BusPicture _p = new BusPicture();
						_p.setIntoPieceId(intoPieceId);
						_p.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
						_p.setType(type);
						_p.setOrderId(njMerGather.getId());
						List<BusPicture> _pList = pictureService.selectByIpId(_p);//商户图片
						for (BusPicture busPicture : _pList) {
							if(list == null){
								list = new ArrayList<BusPicture>();
							}
							list.add(busPicture);
						}
					}
				}
			}
			
			if(list.size() == 0)
				return null;
			JSONArray arr = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("key", list.get(i).getQiniuKey());
				obj.put("fileType", list.get(i).getFileType());
				arr.add(obj);
			}
			return QiNiuUtil.getUrlEndJpg(arr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 商户图片页面
	 * @param intoPieceId
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping("/merShowList")
	public ModelAndView merShowList(String intoPieceId, String type, String id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("union/merPicture");
		mv.addObject("intoPieceId", intoPieceId);
		mv.addObject("token", QiNiuUtil.upToken());
		mv.addObject("type", type);//用来区分定金，尾款，发货单
		mv.addObject("id", id);//联合社订单id
		return mv;
	}
	
}
