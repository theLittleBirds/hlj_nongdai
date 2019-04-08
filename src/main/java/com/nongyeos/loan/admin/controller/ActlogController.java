package com.nongyeos.loan.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysActLog;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.service.ActLogService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller  
@RequestMapping("/log")
public class ActlogController {
	@Resource  
	private ActLogService logService;
	
    /**
     * 分页功能(集成mybatis的分页插件pageHelper实现)
     * 
     * @param currentPage    :当前页数
     * @param pageSize       :每页显示的总记录数
     * @return
     * @throws Exception 
     */
    @RequestMapping("/logList")
    @ResponseBody
    public PageBeanUtil<SysActLog> usersPage(int currentPage,int pageSize) throws Exception{
    	
    	PageBeanUtil<SysActLog> list = logService.logPage(currentPage, pageSize);
    	if(list != null)
		    return list;
    	else 
    		return null;
    }

}
