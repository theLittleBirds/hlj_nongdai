package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppEntity;
import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.service.IAppItemService;
import com.nongyeos.loan.app.service.IEntityService;
import com.nongyeos.loan.base.util.Constants;

@Controller
@RequestMapping("/item")
public class AppItemController {

	@Resource
	private IEntityService entityService;
	@Resource
	private IAppItemService itemService;
	@Resource
	private ISysSenoService sysSenoService;
	
	@RequestMapping(value = "itemList", method = RequestMethod.POST)
	@ResponseBody
	public String itemList(String appId) throws Exception {
		String strJson = "[";
		int num = 1;
		List<AppEntity> entityList = entityService.selectAll(appId);
		AppEntity entity = null;
		AppItem item = null;
		if (entityList != null && entityList.size() > 0) {
			for (int i = 0; i < entityList.size(); i++) {
				entity = entityList.get(i);
				if (i > 0) {
					strJson = strJson + ", ";
				}
				strJson = strJson + "{\"id\":" + num + ", \"pid\":" + "0"
						+ ", \"name\":\"" + entity.getNameCn()
						+ "\", \"entityId\":\"" + entity.getEntityId()
						+ "\", \"appId\":\"" + entity.getAppId()
						+ "\", \"juece\":\"" + "" + "\", \"jigou\":\"" + ""
						+ "\"}";
				int num2 = num;
				num++;
				List<AppItem> itemList = itemService.selectAllByEntityId(entity
						.getEntityId());
				if (itemList != null && itemList.size() > 0) {
					strJson = strJson + ", ";
					for (int j = 0; j < itemList.size(); j++) {
						item = itemList.get(j);
						if (item != null) {
							if (j > 0) {
								strJson = strJson + ", ";
							}
							strJson = strJson + "{\"id\":" + num + ", \"pid\":"
									+ num2 + ", \"name\":\"" + item.getCname()
									+ "\", \"itemId\":\"" + item.getItemId()
									+ "\", \"entityId\":\"" + item.getEntityId() 
									+ "\", \"designType\":\"" + item.getDesignType() 
									+ "\", \"dataType\":\"" + item.getDataType()
									+ "\", \"optionsGroup\":\"" + item.getOptionsGroup()+ "\"}";
						}
						num++;
					}
				}
			}
		}
		strJson = strJson + "]";
		return (strJson);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (item != null) {
			if (item.getItemId().equals("")) {
//				AppItem item2 = itemService.selectByFiledName(item.getFieldName());
//				if (item2 == null) {
					item.setItemId(sysSenoService.getNextString(
							Constants.TABLE_NAME_ITEM, 12, "IE", 1));
					itemService.addItem(item);
					map.put("msg", "添加成功");
//				} else {
//					map.put("msg", "字段名称不能相同！");
//				}
			} else {
				itemService.updateItem(item);
				map.put("msg", "修改成功");
			}
		} else {
			map.put("msg", "操作失败！");
		}
		return map;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(currIds != null && !currIds.equals("")){
				String[] items = currIds.split(",");
				for(String itemId : items){
					itemService.deleteItem(itemId);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "getItem", method = RequestMethod.POST)
	@ResponseBody
	public AppItem getItemByItem(String itemId){
		try{
			AppItem beanAppItem = itemService.selectItem(itemId);
			return beanAppItem;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getItemDS", method = RequestMethod.POST)
	@ResponseBody
	public String getItemDS()throws Exception{
		String strJson = "[";
		List<AppItem> list = itemService.getAllItem();
		if(list != null && list.size() > 0){
			AppItem beanItem = null;
			for(int i = 0;i < list.size();i++){
				beanItem = list.get(i);
				if(beanItem != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanItem.getCname() + "', parameterValue:'" + beanItem.getItemId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	//产品-实体-数据项
	@RequestMapping(value = "getItemDS1", method = RequestMethod.POST)
	@ResponseBody
	public String getItemDS1(String appId){
		String strJson = "[";
		List<AppItem> list = null;
		List<AppEntity> listEntity=null;
		int num=0;
		try {
			listEntity=entityService.selectAll(appId);
			if(listEntity != null && listEntity.size()>0){
				for(int i=0;i<listEntity.size();i++){
					list = itemService.selectAllByEntityId(listEntity.get(i).getEntityId());
					if(list != null && list.size() > 0){
						AppItem beanItem = null;
						for(int j = 0;j < list.size();j++){
							beanItem = list.get(j);
							if(beanItem != null){
								if(num>0)
								{
			    					strJson = strJson + ", ";
								}
			    				strJson = strJson + "{parameterName:'" + beanItem.getCname() + "', parameterValue:'" + beanItem.getItemId() + "'}";
							}
							num++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		strJson = strJson + "]";
		return strJson;
	}
}
