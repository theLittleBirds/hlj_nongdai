package com.nongyeos.loan.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.base.util.Constants;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/fins")
public class BusFinsController {

    @Resource
    private IBusFinsService busFinsService;
    @Resource
    private ISysSenoService sysSenoService;
    @Resource
    private IApplicationService applicationService;
    /**
     * 金融服务-分页查询
     *
     * @return
     */
    @RequestMapping(value = "selectAll", method = RequestMethod.POST)
    @ResponseBody
    public PageBeanUtil<BusFins> BusFinsSelectAll(int limit, int offset, HttpSession session) {
            try {
            	List<String> orgIdList = new ArrayList<>();
            	List<SysPersonorg> list = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
            	for (SysPersonorg perorg : list) {
					orgIdList.add(perorg.getOrgId());
				}
            	PageBeanUtil<BusFins> list1 = busFinsService.selectByPage(limit, offset, orgIdList);
    			return list1;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return null;
    		}
    }

    /*
     * 金融服务-添加和修改
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(BusFins busFins) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (busFins.getFinsId() == null || busFins.getFinsId().equals("")) {
                busFins.setFinsId(sysSenoService.getNextString(Constants.TABLE_NAME_FINS, 12, "F", 1));
                busFinsService.add(busFins);
                map.put("msg", "添加成功");
            } else {
                busFinsService.update(busFins);
                map.put("msg", "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 金融服务-删除操作
     *
     * @param currIds
     * @return
     */
    @RequestMapping("/delFins")
    @ResponseBody
    public Map<String, Object> delete(String currIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (currIds != null && !currIds.equals("")) {
                String[] busFins = currIds.split(",");
                for (String busFin : busFins) {
                    busFinsService.deleteBusFins(busFin);
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

    @RequestMapping("/getFinsDS")
    @ResponseBody
    public String getFinsDS(HttpSession session) {
        String strJson = "[";
        try {
        	List<String> orgIdList = new ArrayList<>();
        	List<SysPersonorg> perorgList = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
        	for (SysPersonorg perorg : perorgList) {
        		orgIdList.add(perorg.getOrgId());
        	}
        	List<BusFins> list = busFinsService.selectAll(orgIdList);
            if (list != null && list.size() > 0) {
                BusFins beanBusFins = null;
                for (int i = 0; i < list.size(); i++) {
                    beanBusFins = list.get(i);
                    if (beanBusFins != null) {
                        if (i > 0)
                            strJson = strJson + ", ";

                        strJson = strJson + "{parameterName:'" + beanBusFins.getCname() + "', parameterValue:'" + beanBusFins.getFinsId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
    }

    @RequestMapping("/getFinsDS1")
    @ResponseBody
    public String getFinsDS1(HttpSession session) {
        String strJson = "[";
        try {
        	List<String> orgIdList = new ArrayList<>();
        	List<SysPersonorg> perorgList = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
        	for (SysPersonorg perorg : perorgList) {
        		orgIdList.add(perorg.getOrgId());
        	}
        	List<BusFins> list = busFinsService.selectAll(orgIdList);
            if (list != null && list.size() > 0) {
                BusFins beanBusFins = null;
                for (int i = 0; i < list.size(); i++) {
                    beanBusFins = list.get(i);
                    List<AppApplication> list1=applicationService.selectByFinsCode(beanBusFins.getFinsId());
                    if(list1!=null  && list1.size()>0){
                        if (beanBusFins != null) {
                            if (i > 0)
                                strJson = strJson + ", ";

                            strJson = strJson + "{parameterName:'" + beanBusFins.getCname() + "', parameterValue:'" + beanBusFins.getFinsId() + "'}";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
    }
}
