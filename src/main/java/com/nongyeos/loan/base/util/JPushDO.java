package com.nongyeos.loan.base.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.core.model.BusinessObject;

public class JPushDO {
	public static void Do(IIntoPieceService intoPieceService,IAppEntryService appEntryService,IFlowNodeService flowNodeService,JpushUtils jpushUtils,IApplicationService applicationService,String id,String nextNodeId,HttpServletRequest request) throws Exception{
		AppEntry entry = appEntryService.queryByAppModeId(intoPieceService.selectByPrimaryKey(id).getId());
		BusinessObject business = new BusinessObject(entry,intoPieceService.selectByPrimaryKey(id),applicationService.selectByModelId(intoPieceService.selectByPrimaryKey(id).getModelId()));
		FlowNode nextNode = flowNodeService.selectByNodeId(nextNodeId);
		try {
			jpushUtils.save(business.getBean(), nextNode.getEname());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
