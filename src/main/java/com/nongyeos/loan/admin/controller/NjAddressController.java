package com.nongyeos.loan.admin.controller;

/*
 *  Creat by zhoudi on 2019/3/26.
 */

import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.BusNjaddress;
import com.nongyeos.loan.admin.entity.BusNjorder;
import com.nongyeos.loan.admin.service.NjAddressService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/njAddress")
public class NjAddressController {

    @Autowired
    private NjAddressService njAddressService;

    /**
     * 去添加地址页面
     */
    @RequestMapping("/njToAddress")
    public String njProductList(String carIds, ModelMap model){
        BusNjaddress busNjAddress = new BusNjaddress();
        model.put("carIds",carIds);
        model.put("busNjAddress",busNjAddress);
        return "shoppingCenter/njAddress";
    }

    /**
     * 去修改地址页面
     */
    @RequestMapping("/njToUpdAddress")
    public String njToUpdAddress(String carIds,String id, ModelMap model){
        try {
            BusNjaddress busNjAddress = njAddressService.queryAddressById(id);
            model.put("busNjAddress",busNjAddress);
            model.put("carIds",carIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shoppingCenter/njAddress";
    }

    /**
     * 地址插入/修改
     * @return
     */
    @RequestMapping("/njAddAddress")
    @ResponseBody
    public Map<String,Object> njAddAddress(BusNjaddress busNjaddress, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String orgId = request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
        String ordName = request.getSession().getAttribute(Constants.SESSION_ORGNM).toString();
        String personName = request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString();
        String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
        busNjaddress.setOrgId(orgId);
        busNjaddress.setOrgName(ordName);
        busNjaddress.setMemberName(personName);
        busNjaddress.setPersonId(personId);
        try {
            String addressId = busNjaddress.getAddressId();
            if(StringUtil.isNotEmpty(addressId)){//修改
                List<BusNjaddress> njaddressList = njAddressService.queryAddressAll(personId);
                //如果是单个修改，则无法修成非默认的
                if(njaddressList.size() == 1){
                    busNjaddress.setIsDefault("1");
                    busNjaddress.setUpdateDate(DateUtils.getNowDate());
                    njAddressService.updateAddress(busNjaddress);
                }else{
                    //多个修改，如果是修改成默认的，则将其他的地址修改成非默认的
                    if(busNjaddress.getIsDefault().equals("1")){
                        for(BusNjaddress busNjaddress1 :njaddressList ){
                            if(busNjaddress1.getIsDefault().equals("1")){
                                busNjaddress1.setIsDefault("2");
                                njAddressService.updateAddress(busNjaddress1);
                            }
                        }
                        busNjaddress.setUpdateDate(DateUtils.getNowDate());
                        njAddressService.updateAddress(busNjaddress);
                    }else{
                        //多个修改，如果是修改成非默认的，则将其他的地址算则一个座位默认
                        busNjaddress.setUpdateDate(DateUtils.getNowDate());
                        njAddressService.updateAddress(busNjaddress);
                        BusNjaddress busNjaddress1 = njAddressService.queryAddressByOrgId(personId);
                        if(busNjaddress1 == null){
                            njaddressList.get(0).setIsDefault("1");
                            njaddressList.get(0).setUpdateDate(DateUtils.getNowDate());
                            njAddressService.updateAddress(njaddressList.get(0));
                        }
                    }
                }
            }else{//添加
                List<BusNjaddress> busNjaddressList = njAddressService.queryAddressAll(personId);
                if(busNjaddressList.size() >= 5){
                    map.put("msg","收货地址最多五条！");
                    return map;
                }
                busNjaddress.setAddressId(UUIDUtil.getUUID());
                busNjaddress.setCreateDate(DateUtils.getNowDate());
                busNjaddress.setUpdateDate(DateUtils.getNowDate());
                BusNjaddress busNjaddress1 = njAddressService.queryAddressByOrgId(personId);
                if(busNjaddress1 == null){
                    busNjaddress.setIsDefault("1");
                    njAddressService.inserAddress(busNjaddress);
                }else{
                    if("1".equals(busNjaddress.getIsDefault())){
                        busNjaddress1.setIsDefault("2");
                        njAddressService.updateAddress(busNjaddress1);
                    }
                    njAddressService.inserAddress(busNjaddress);
                }
            }
        }catch (Exception e){
            map.put("msg","添加收货地址异常，请重试！");
            e.printStackTrace();
        }
        map.put("msg","操作成功");
        return map;
    }

    /**
     * 地址删除
     * @return
     */
    @RequestMapping("/njDeleteAddress")
    @ResponseBody
    public boolean njDeleteAddress(String id , HttpServletRequest request){
        String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
        try {
            if(StringUtil.isNotEmpty(id)){
                BusNjaddress busNjaddress = njAddressService.queryAddressById(id);
                //如果删除默认地址，则将其余地址的第一个设置为默认
                if(busNjaddress.getIsDefault().equals("1")){
                    njAddressService.delAddressById(id);
                    List<BusNjaddress> njaddressList = njAddressService.queryAddressAll(personId);
                    if(njaddressList.size() > 0){
                        njaddressList.get(0).setIsDefault("1");
                        njAddressService.updateAddress(njaddressList.get(0));
                    }
                }else{
                    njAddressService.delAddressById(id);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 设置为默认地址
     * @return
     */
    @RequestMapping("/njsetDefAddress")
    @ResponseBody
    public boolean njsetDefAddress(String id, HttpServletRequest request){
        try {
            if(StringUtil.isNotEmpty(id)){
                String orgId = request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
                String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
                BusNjaddress busNjaddress = njAddressService.queryAddressById(id);
                BusNjaddress busNjaddress1 = njAddressService.queryAddressByOrgId(personId);
                busNjaddress1.setIsDefault("2");
                njAddressService.updateAddress(busNjaddress1);
                busNjaddress.setIsDefault("1");
                njAddressService.updateAddress(busNjaddress);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
