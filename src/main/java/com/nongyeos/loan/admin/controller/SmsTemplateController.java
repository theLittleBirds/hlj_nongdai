package com.nongyeos.loan.admin.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusSmsTemplate;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.ISmsTemplateService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/sms")
public class SmsTemplateController {
	
	@Autowired
	private ISmsTemplateService smsTemplateService;
	
	@Autowired
	private IOrgService orgService;
	
	private static final Logger logger = LogManager.getLogger(SmsTemplateController.class);
	
	@RequestMapping("/list")
    @ResponseBody
    public PageBeanUtil<BusSmsTemplate> smsTemplateList(int currentPage,int pageSize) throws Exception{
		return smsTemplateService.selectByPage(currentPage, pageSize);
    }
	
	@RequestMapping("/delete")
    @ResponseBody
	public String delete(String ids) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(ids)){
			json.put("msg", "请选择要删除的模板");
			json.put("code", 400);
		}else{
			String id[] = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				smsTemplateService.deleteById(id[i]);
			}
			json.put("msg", "删除成功");
			json.put("code", 200);
		}
		return json.toString();
	}
	
	@RequestMapping("/id")
    @ResponseBody
	public JSONObject selectById(String id) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("msg", "模板标识为空");
			json.put("code", 400);
			return json;
		}
		try {
			json.put("obj", smsTemplateService.selectById(id));	
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
			json.put("code", 400);
		}
		return json;
	}
	
	@RequestMapping("/baseorg")
    @ResponseBody
	public List<SysOrg> baseOrg(){
		try {
			return orgService.selectBaseOrg();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/save")
    @ResponseBody
	public String save(BusSmsTemplate st) throws Exception{
		JSONObject json = new JSONObject();
		BusSmsTemplate queryST = smsTemplateService.checkExist(st);
		if(StrUtils.isEmpty(st.getMsgId())){
			if(queryST == null){
				st.setMsgId(UUIDUtil.getUUID());
				smsTemplateService.addRecord(st);
				json.put("msg", "保存成功");
				json.put("code", 200);
			}else{
				json.put("msg", "码值已存在，请用更改");
				json.put("code", 400);
			}	
		}else{
			if(st.getMsgId().equals(queryST.getMsgId())){
				smsTemplateService.updateByPrimaryKeySelective(st);
				json.put("msg", "修改成功");
				json.put("code", 200);
			}else{
				json.put("msg", "码值已存在，请用更改");
				json.put("code", 400);
			}
		}
		return json.toString();
	}
}
