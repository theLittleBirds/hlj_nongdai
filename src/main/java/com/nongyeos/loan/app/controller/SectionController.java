package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppItemsection;
import com.nongyeos.loan.app.entity.AppSection;
import com.nongyeos.loan.app.service.ISectionService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/section")
public class SectionController {

	@Resource
	private ISectionService sectionService;
	@Resource
	private ISysSenoService sysSenoService;
	@Autowired
	private IIntoPieceService intoPieceService;

	/**
	 * 区段--全查 json--返回树状表的数据以及关系
	 * 
	 * @return
	 */
	@RequestMapping("/getSectionAll")
	@ResponseBody
	public PageBeanUtil<AppSection> selectAll(int limit,int offset,String appId) {
		try {
			PageBeanUtil<AppSection> list = sectionService.selectAll(limit, offset, appId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 区段--保存和修改
	 * 
	 * @param section
	 * @param entityId
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(AppSection section) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (section.getSectionId() == null || section.getSectionId().equals("")) {
				section.setSectionId(sysSenoService.getNextString(
						Constants.TABLE_NAME_SECTION, 10, "SE", 1));
				sectionService.addSection(section);
				map.put("msg", "添加成功");
			} else {
				sectionService.updateSection(section);
				map.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 区段--删除
	 * 
	 * @param currIds
	 * @return
	 */
	@RequestMapping(value = "delSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] sectionIds = currIds.split(",");
				for (String sectionId : sectionIds) {
					sectionService.deleteBySectionId(sectionId);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据产品ID查询所有区段
	 * 
	 * @return
	 */
	@RequestMapping("/getbyipid")
	@ResponseBody
	public List<AppSection> getByIpId(String intoPieceId) {
		if(intoPieceId == null || "".equals(intoPieceId))
			return null;
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(ip == null || ip.getModelId() == null || "".equals(ip.getModelId()))
				return null;
			return sectionService.selectAll(ip.getModelId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getItemSectionBySectionId")
	@ResponseBody
	public PageBeanUtil<AppItemsection> itemSectionPage(int limit,int offset,String sectionId) {
		try {
			PageBeanUtil<AppItemsection> list = sectionService.selectByPage(limit, offset, sectionId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/getSectionItemString")
	@ResponseBody
	public String getItemSectionIds(String sectionId) throws Exception{
		List<AppItemsection> list = sectionService.getItemSectionBySectionId(sectionId);
		String objectString = "";
		if(list != null && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				objectString += list.get(i).getItemId() +",";
			}
			if(!objectString.equals(""))
			objectString = objectString.substring(0,objectString.length()-1);
		}
		return objectString;
	}
	
	@RequestMapping("/saveItemSection")
	@ResponseBody
	public Map<String,Object> saveItemSection(AppItemsection itemsection) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(itemsection != null){
			String sectionId = itemsection.getSectionId();
			String itemIds = itemsection.getItemId();
			if(sectionId != null && !sectionId.equals("")){
				sectionService.delItemSectionBySectionId(sectionId);
				if(itemIds != null && !itemIds.equals("")){
					AppItemsection beanItemsection = new AppItemsection();
					String[] itemIds2 = itemIds.split(",");
					for(int i = 0;i < itemIds2.length;i++){
						beanItemsection.setSectionId(sectionId);
						beanItemsection.setItemId(itemIds2[i]);
						beanItemsection.setSeqno(i);
						sectionService.addItemSectionBySectionId(beanItemsection);
					}
				}
				map.put("msg", "保存成功");
			}else{
				map.put("msg", "保存失败");
			}
		}
		return map;
	}
	
	@RequestMapping("/delItemSection")
	@ResponseBody
	public Map<String,Object> delSectionItem(String currIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(currIds != null && !currIds.equals("")){
			String[] seitIds = currIds.split(",");
			for(String seitId2 : seitIds){
				int seitId = Integer.valueOf(seitId2);
				sectionService.delItemSectionByseitId(seitId);
			}
			map.put("msg", "删除成功");
		}else{
			map.put("msg", "删除失败");
		}
		return map;
	}
	
	@RequestMapping(value = "getSectionByAppId", method = RequestMethod.POST)
	@ResponseBody
	public String getSectionByAppId(String appId)throws Exception{
		String strJson = "[";
		List<AppSection> list = sectionService.selectAll(appId);
		if(list != null && list.size() > 0){
			AppSection beanAppSection = null;
			for(int i = 0;i < list.size();i++){
				beanAppSection = list.get(i);
				if(beanAppSection != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanAppSection.getCname() + "', parameterValue:'" + beanAppSection.getSectionId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
}
