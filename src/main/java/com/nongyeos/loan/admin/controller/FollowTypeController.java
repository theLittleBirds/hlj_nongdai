package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFollowType;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.service.IFollowItemService;
import com.nongyeos.loan.admin.service.IFollowTypeService;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/followtype")
public class FollowTypeController {
	
	@Autowired
	private IFollowTypeService followTypeService;
	
	@Autowired
	private IFollowItemService followItemService;
	
	@Autowired
	private IMemberOperateService memberOperateService;
	
	@Autowired
	private IOrgService orgService;
	
	@RequestMapping("/selectall")
	@ResponseBody
	public List<BusFollowType> selectAll(String id){
		try {
			BusMemberOperate mo = memberOperateService.selectByPrimaryKey(id);
			String org_id = mo.getOrgId();
			SysOrg org = orgService.selectByOrgId(org_id);
			if(StrUtils.isNotEmpty(org.getParentOrgId())){
				org_id = org.getParentOrgId();
			}
			return followTypeService.selectAll(org_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/list")
    @ResponseBody
    public PageBeanUtil<BusFollowType> list(int currentPage,int pageSize,HttpServletRequest request) throws Exception{
		try {
			return followTypeService.selectByPage(currentPage, pageSize,request.getSession().getAttribute(Constants.SESSION_ORGCDBASE).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/selectbyid")
	@ResponseBody
	public BusFollowType selectById(String id){
		try {
			return followTypeService.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveOrUpdate(String id,String name,Integer seqno,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(name)){
			json.put("code", 400);
			json.put("msg", "类型名必填");
			return json;
		}
		if(seqno == null){
			json.put("code", 400);
			json.put("msg", "序号必填");
			return json;
		}
		try {
			BusFollowType ft = new BusFollowType();
			ft.setName(name);
			ft.setSeqno(seqno);
			if(StrUtils.isEmpty(id)){
				ft.setId(UUIDUtil.getUUID());
				ft.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				ft.setCreDate(new Date());
				ft.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				ft.setCreOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				//只能看见所属根目录下
				ft.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCDBASE).toString());
				ft.setUpdDate(new Date());
				ft.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				ft.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				followTypeService.insert(ft);
			}else{
				ft.setId(id);
				ft.setUpdDate(new Date());
				ft.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				ft.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				followTypeService.updateByPrimaryKeySelective(ft);
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
		BusFollowType ft = new BusFollowType();
		ft.setIsDeleted(Constants.COMMON_IS_DELETE);
		int sum = 0;
		try {
			for (int i = 0; i < id.length; i++) {
				if(followItemService.selectCountByType(id[i])>0){
					continue;
				}
				ft.setId(id[i]);
				ft.setUpdDate(new Date());
				ft.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				ft.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
				followTypeService.updateByPrimaryKeySelective(ft);
				sum++;
			}
			json.put("code", 200);
			if(sum < id.length){
				json.put("msg", "存在数据项的类型不能删除");
			}else{
				json.put("msg", "删除成功");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "删除失败");
		}		
		return json;
	}
}
