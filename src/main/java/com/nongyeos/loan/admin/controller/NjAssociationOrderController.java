package com.nongyeos.loan.admin.controller;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusNjaddress;
import com.nongyeos.loan.admin.entity.BusNjmerorder;
import com.nongyeos.loan.admin.entity.BusNjorder;
import com.nongyeos.loan.admin.entity.BusNjorderinfo;
import com.nongyeos.loan.admin.service.IBusNjmerorderService;
import com.nongyeos.loan.admin.service.INjOrderService;
import com.nongyeos.loan.admin.service.INjorderinfoService;
import com.nongyeos.loan.admin.service.NjAddressService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ThinkPad on 2019/3/28.
 */
@Controller
@RequestMapping("/njAssociationOrder")
public class NjAssociationOrderController {

    @Autowired
    private IBusNjmerorderService njmerorderService;
    @Autowired
    private INjorderinfoService njorderinfoService;
    @Autowired
    private NjAddressService addressService;
    @Autowired
    private INjOrderService orderService;


    /**
     * 点击左侧菜单跳转页面
     * @return
     */
    @RequestMapping("/njAssociationOrderList")
    public String njServiceOrderList(){
        return "shoppingCenter/njAssociationOrderList";
    }


    /**
     * 异步获取商户订单列表
     * @param currentPage
     * @param pageSize
     * @param njmerorder
     * @return
     */
    @RequestMapping("/njmerorderList")
    @ResponseBody
    public PageBeanUtil<BusNjmerorder> njOrderList(int currentPage, int pageSize, BusNjmerorder njmerorder, HttpServletRequest request){
        try {
            String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
            if (njmerorder==null) {
                njmerorder = new BusNjmerorder();
            }
            PageBeanUtil<BusNjmerorder> orderList = njmerorderService.njmerorderList(currentPage, pageSize, njmerorder,personId);
            return orderList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看商户订单详情
     * @param model
     * @param merOrderId
     * @return
     */
    @RequestMapping("/njAssociationOrderDetails")
    public String njOrderDetails(ModelMap model, Integer merOrderId){
        try {
            if(merOrderId==null){
                throw new Exception("商户订单merOrderId为空");
            }
            BusNjmerorder busNjmerorder = njmerorderService.selectByPrimaryKey(merOrderId);
            if(busNjmerorder==null) {
                throw new Exception("商户订单对象为空");
            }
            BusNjorder busNjorder = orderService.selectByPrimaryKey(busNjmerorder.getOrderId());
            if(busNjorder==null) {
                throw new Exception("订单对象为空");
            }
            BusNjaddress njaddress = JSON.parseObject(busNjorder.getOrderAddress(), new TypeReference<BusNjaddress>() {});
            if(njaddress==null) {
                throw new Exception("收货地址为空");
            }
            busNjmerorder.setContact(njaddress.getContact());
            busNjmerorder.setPhone(njaddress.getPhone());
            busNjmerorder.setAddressZh(njaddress.getAddressZh());
            model.put("busNjmerorder", busNjmerorder);
            model.put("token", QiNiuUtil.upToken());
            List<BusNjorderinfo> busNjorderinfoList = njorderinfoService.selectByOrderIdAndOrgId(busNjmerorder.getOrderId(), busNjmerorder.getOrgId());
            model.put("busNjorderinfoList", busNjorderinfoList);
            if(busNjmerorder.getStatus().equals(Constants.MER_NEW_WAIT_OERDER) || busNjmerorder.getStatus().equals(Constants.MER_NEW_DELIVER_ORDER)){
                return "shoppingCenter/njMerOrderConfirmDetails";   //跳转接单页面
            }else if(busNjmerorder.getStatus().equals(Constants.MER_NEW_TOBESHIP_ORDER)){
                return "shoppingCenter/njMerOrderConfirm";    //上传发货凭证
            }else{
                return "shoppingCenter/njMerOrderDetails";    //跳转详情页面
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 点击图片信息跳转图片集合页面
     * @param model
     * @param merOrderId
     * @return
     */
    @RequestMapping("/njAssociationOrderImg")
    public String njAssociationOrderImg(ModelMap model, Integer merOrderId){
        try {
            if(merOrderId==null){
                throw new Exception("商户订单merOrderId为空");
            }
            BusNjmerorder busNjmerorder = njmerorderService.selectByPrimaryKey(merOrderId);
            if(busNjmerorder==null) {
                throw new Exception("商户订单对象为空");
            }
            model.put("busNjmerorder", busNjmerorder);
            model.put("token", QiNiuUtil.upToken());
            List<BusNjorderinfo> busNjorderinfoList = njorderinfoService.selectByOrderIdAndOrgId(busNjmerorder.getOrderId(), busNjmerorder.getOrgId());
            model.put("busNjorderinfoList", busNjorderinfoList);
            return "shoppingCenter/njMerImageList";    //跳转图片集合页面
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看凭证，返回凭证图片
     * @param merOrderId
     * @return
     */
    @RequestMapping("/merOrderImg")
    @ResponseBody
    public JSONArray merOrderImg(Integer merOrderId){
        try{
            BusNjmerorder busNjmerorder = njmerorderService.selectByPrimaryKey(merOrderId);
            JSONArray arr = new JSONArray();
            if (busNjmerorder!=null){
                if (busNjmerorder.getStatus().equals(Constants.MER_NEW_FINISH_ORDER)){
                    JSONObject obj2 = new JSONObject();
                    obj2.put("key", busNjmerorder.getSendPic());
                    obj2.put("fileType", "3");
                    arr.add(obj2);
                }else{
                    JSONObject obj = new JSONObject();
                    obj.put("key", busNjmerorder.getPayPic());
                    obj.put("fileType", "1");
                    arr.add(obj);
                    JSONObject obj1 = new JSONObject();
                    obj1.put("key", busNjmerorder.getPayPicTail());
                    obj1.put("fileType", "2");
                    arr.add(obj1);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("key", busNjmerorder.getSendPic());
                    obj2.put("fileType", "3");
                    arr.add(obj2);
                    JSONObject obj3 = new JSONObject();
                    obj3.put("key", busNjmerorder.getReceivePic());
                    obj3.put("fileType", "4");
                    arr.add(obj3);
                }
            }
            return QiNiuUtil.getUrlEndJpg(arr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片信息
     * @param merOrderId
     * @return
     */
    @RequestMapping("/merOrderImgList")
    @ResponseBody
    public JSONArray merOrderImgList(Integer merOrderId){
        try{
            BusNjmerorder busNjmerorder = njmerorderService.selectByPrimaryKey(merOrderId);
            JSONArray arr = new JSONArray();
            if (busNjmerorder!=null) {
                JSONObject obj = new JSONObject();
                obj.put("key", busNjmerorder.getPayPic());
                obj.put("fileType", "1");
                arr.add(obj);
                JSONObject obj1 = new JSONObject();
                obj1.put("key", busNjmerorder.getPayPicTail());
                obj1.put("fileType", "2");
                arr.add(obj1);
                JSONObject obj2 = new JSONObject();
                obj2.put("key", busNjmerorder.getSendPic());
                obj2.put("fileType", "3");
                arr.add(obj2);
                JSONObject obj3 = new JSONObject();
                obj3.put("key", busNjmerorder.getReceivePic());
                obj3.put("fileType", "4");
                arr.add(obj3);
            }
            return QiNiuUtil.getUrlEndJpg(arr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 商户订单状态更新
     * @param merOrderId
     * @return
     */
    @RequestMapping("/updateNjMerOrder")
    @ResponseBody
    public JSONObject updateNjMerOrder(Integer merOrderId){
        try {
            JSONObject json = new JSONObject();
            if (merOrderId==null) {
                json.put("code",400);
                json.put("msg","商户订单id为空");
            }else{
                BusNjmerorder busNjmerorder = njmerorderService.selectByPrimaryKey(merOrderId);
                if(busNjmerorder==null){
                    json.put("code",400);
                    json.put("msg","订单对象为空");
                }else{
                    List<BusNjorderinfo> busNjorderinfoList = njorderinfoService.selectByOrderIdAndOrgId(busNjmerorder.getOrderId(), busNjmerorder.getOrgId());
                    if(busNjorderinfoList!=null){
                        for (BusNjorderinfo njorderinfo:busNjorderinfoList) {
                            if(njorderinfo.getStatus().equals(Constants.MER_NEW_WAIT_OERDER)){
                                njorderinfo.setStatus(Constants.MER_NEW_MER_ORDER);
                            }else if(njorderinfo.getStatus().equals(Constants.MER_NEW_DELIVER_ORDER)){
                                njorderinfo.setStatus(Constants.MER_NEW_TOBESHIP_ORDER);
                            }

                            njorderinfoService.updateByPrimaryKeySelective(njorderinfo);
                        }
                    }
                    if(busNjmerorder.getStatus().equals(Constants.MER_NEW_WAIT_OERDER)){
                        busNjmerorder.setStatus(Constants.MER_NEW_MER_ORDER);
                    }else if(busNjmerorder.getStatus().equals(Constants.MER_NEW_DELIVER_ORDER)){
                        busNjmerorder.setStatus(Constants.MER_NEW_TOBESHIP_ORDER);
                    }
                    njmerorderService.updateByPrimaryKeySelective(busNjmerorder);
                    json.put("code",200);
                    json.put("msg","确认成功！");
                }
            }
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传发货凭证
     * @param hashKey
     * @param merOrderId
     * @return
     */
    @RequestMapping("/saveImg")
    @ResponseBody
    public JSONObject save(String hashKey,Integer merOrderId){
        try {
            JSONObject json = new JSONObject();
            if(StrUtils.isEmpty(hashKey)){
                json.put("code", 400);
                json.put("msg", "图片标识为空");
                return json;
            }
            if(merOrderId==null){
                json.put("code", 400);
                json.put("msg", "商户Id为空");
                return json;
            }
            BusNjmerorder njmerorder = njmerorderService.selectByPrimaryKey(merOrderId);
            if(njmerorder!=null){
                njmerorder.setSendPic(hashKey);
                njmerorder.setStatus(Constants.MER_NEW_FINISH_ORDER);
                njmerorderService.updateByPrimaryKeySelective(njmerorder);
                List<BusNjorderinfo> busNjorderinfos = njorderinfoService.selectByOrderIdAndOrgId(njmerorder.getOrderId(), njmerorder.getOrgId());
                if(busNjorderinfos!=null){
                    for (BusNjorderinfo njOrderInfo:busNjorderinfos){
                        njOrderInfo.setStatus(Constants.MER_NEW_FINISH_ORDER);
                        njorderinfoService.updateByPrimaryKeySelective(njOrderInfo);
                    }
                }
            }
            json.put("code", 200);
            json.put("msg", "上传成功");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
