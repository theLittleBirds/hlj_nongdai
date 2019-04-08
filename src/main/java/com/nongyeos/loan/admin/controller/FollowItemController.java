package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFollowItem;
import com.nongyeos.loan.admin.resultMap.FollowItemMap;
import com.nongyeos.loan.admin.service.IFollowItemService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/followitem")
public class FollowItemController {
	
	@Autowired
	private IFollowItemService followItemService;
	
	@RequestMapping("form")
	private ModelAndView page(String id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("memberOperate/dynamic");
		mv.addObject("id", id);
		return mv;
	}
	
	@RequestMapping("/loaddata")
	@ResponseBody
	public List<FollowItemMap> loadData(String type,String id){
		if(StrUtils.isEmpty(type))
			return null;
		if(StrUtils.isEmpty(id))
			return null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", type);
			map.put("id", id);
			return followItemService.selectDynamicByType(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/list")
    @ResponseBody
    public PageBeanUtil<BusFollowItem> list(int currentPage,int pageSize,String type) throws Exception{
		if(StrUtils.isEmpty(type))
			return null;
		try {
			return followItemService.selectByPage(currentPage, pageSize, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/selectbyid")
	@ResponseBody
	public BusFollowItem selectById(String id){
		try {
			return followItemService.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveOrUpdate(HttpServletRequest request,String id,String type,String name,
			Integer inputType,String unit,Integer inputWidth,String inputOption,Integer seqno){
		JSONObject json =  new JSONObject();
		if(type == null){
			json.put("code", 400);
			json.put("msg", "跟进信息类型为空");
			return json;
		}
		if(name == null){
			json.put("code", 400);
			json.put("msg", "数据项名称为空");
			return json;
		}
		if(inputType == null){
			json.put("code", 400);
			json.put("msg", "数据项类型为空");
			return json;
		}
		BusFollowItem fi = new BusFollowItem();
		fi.setId(id);
		fi.setName(name);
		fi.setType(type);
		fi.setSeqno(seqno);
		fi.setUnit(unit);
		fi.setInputOption(inputOption);
		fi.setInputType(inputType);
		fi.setInputWidth(inputWidth);
		try {
			if(StrUtils.isNotEmpty(fi.getInputOption())){
				String str = fi.getInputOption();
				str = str.replaceAll("：", ":");
				str = str.replaceAll("，", ",");
				fi.setInputOption(str);
			}
			fi.setUpdDate(new Date());
			fi.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			fi.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
			if(StrUtils.isEmpty(fi.getId())){
				fi.setId(UUIDUtil.getUUID());
				fi.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				fi.setCreDate(new Date());
				fi.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				fi.setCreOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				followItemService.insert(fi);
			}else{
				BusFollowItem record = followItemService.selectByPrimaryKey(fi.getId());
				fi.setIsDeleted(record.getIsDeleted());
				fi.setCreDate(record.getCreDate());
				fi.setCreOperId(record.getCreOperId());
				fi.setCreOperName(record.getCreOperName());
				followItemService.updateByPrimaryKey(fi);
			}
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;	
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject deleteMany(String ids, HttpServletRequest request){
		JSONObject json =  new JSONObject();
		if(StrUtils.isEmpty(ids)){
			json.put("code", 400);
			json.put("msg", "请选择要删除的记录");
			return json;
		}
		String id[] = ids.split(",");
		BusFollowItem fi = new BusFollowItem();
		fi.setIsDeleted(Constants.COMMON_IS_DELETE);
		try {
			for (int i = 0; i < id.length; i++) {
				fi.setId(id[i]);
				fi.setUpdDate(new Date());
				fi.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				fi.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				followItemService.updateByPrimaryKeySelective(fi);
			}
			json.put("code", 200);
			json.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "删除失败");
		}		
		return json;
	}
}
