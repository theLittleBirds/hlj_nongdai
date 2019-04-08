package com.nongyeos.loan.admin.controller;

/*
 *  Creat by zhoudi on 2019/3/26.
 */


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.*;
import com.nongyeos.loan.admin.service.*;
import com.nongyeos.loan.base.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/njSubmitOrder")
public class NjProductOrderController {


    @Autowired
    private NjShoppingCarService njShoppingCarService;
    @Autowired
    private NjAddressService njAddressService;
    @Autowired
    private INjOrderService njOrderService;
    @Autowired
    private INjorderinfoService njorderinfoService;
    @Autowired
    private IOrgService orgService;
    @Autowired
    private IBusNjmerorderService njmerorderService;


    /**
     * 去确认订单页
     * @return
     */
    @RequestMapping("/njSubmitOrderPage")
    public String njCarList(String carIds, ModelMap model, HttpServletRequest request){
        try {
            String orgId =request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
            String personId =request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
            if(StringUtil.isNotEmpty(carIds)){
                List<BusNjshopingcar> busNjshopingcarsList = new ArrayList<>();
                BusNjshopingcar busNjshopingcar = null;
                String[] carids = carIds.split(",");
                Double totalMoney = 0.00;//同时计算一下总价
                for (String carId : carids){
                    busNjshopingcar = njShoppingCarService.queryBusNjShoppingCarById(carId);
                    busNjshopingcarsList.add(busNjshopingcar);
                    double price = Double.parseDouble(busNjshopingcar.getProductPrice());
                    Integer num = busNjshopingcar.getProductNum();
                    double tmp = price * num;
                    totalMoney += tmp;
                }
                BigDecimal b = new BigDecimal(totalMoney);
                model.put("totalMoney",b.setScale(2,BigDecimal.ROUND_HALF_UP).toString());//保留两位小数
                model.put("carIds",carIds);
                model.put("carList",busNjshopingcarsList);
                List<BusNjaddress> busNjaddressList =njAddressService.queryAddressAll(personId);
                model.put("addressList",busNjaddressList);
                model.put("size",busNjaddressList.size());
                model.put("token",QiNiuUtil.upToken());//七牛上传token
            }else{
                throw  new Exception("确认订单失败，请稍后从试");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "shoppingCenter/njSubmitOrder";
    }

    /**
     * 根据hashKey获得缩略图
     * @param hashKey
     * @return
     */
    @RequestMapping("/njSubmitOrderKey")
    @ResponseBody
    public JSONArray list(String hashKey){
        try {
            JSONArray arr = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("key", hashKey);
            arr.add(obj);
            return QiNiuUtil.getUrlEndJpg(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提交订单，删除购物车，生成订单
     */
    @RequestMapping("/njSubmitOrders")
    public String njSubmitOrders(String carIds,String addressId,String hashKey, ModelMap model,HttpServletRequest request) throws Exception {
        try {
            if(StringUtil.isEmpty(carIds)){
                throw new Exception("提交订单失败，请稍后重试！");
            }
            if(StringUtil.isEmpty(addressId)){
                throw new Exception("提交订单失败，请稍后重试！");
            }
            if(StringUtil.isEmpty(hashKey)){
                throw new Exception("提交订单失败，请稍后重试！");
            }
            //获得基本常量
            String orgId = request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
            String orgName = request.getSession().getAttribute(Constants.SESSION_ORGNM).toString();
            String personName = request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString();
            /**
             * 创建主订单
             * 订单状态设置为1：已下单
             * 保存付款凭证，保存收货地址
             * 主订单：busNjorder
             */
            BusNjorder busNjorder = new BusNjorder();
            busNjorder.setOrderId(UUIDUtil.getUUID());
            busNjorder.setOrgId(orgId);
            busNjorder.setOrgName(orgName);
            busNjorder.setMemberName(personName);
            BusNjaddress busNjaddress = njAddressService.queryAddressById(addressId);
            JSONObject jsonAddress = (JSONObject)JSON.toJSON(busNjaddress);
            busNjorder.setOrderAddress(jsonAddress.toString());//收货地址id
            busNjorder.setPayPic(hashKey);//支付凭证
            busNjorder.setStatus(Constants.SERVICESITE_WAIT_ORDER);//设置订单状态
            busNjorder.setCreateDate(DateUtils.getNowDate());
            busNjorder.setUpdateDate(DateUtils.getNowDate());
            //生成订单
            njOrderService.createOrder(busNjorder,carIds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("提交订单异常，请稍后重试！");
        }
        return  "redirect:njToOrders";
    }

    /**
     * 去订单页
     * @return
     */
    @RequestMapping("/njToOrders")
    public String njToOrders(){
        return "shoppingCenter/njOrders";
    }

    /**
     * 异步获取订单列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/njOrderList")
    @ResponseBody
    public PageBeanUtil<BusNjorder> njOrderList(int currentPage, int pageSize,String status, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
            if(StringUtil.isEmpty(personId)){
                throw new Exception("登录失效请重新登录");
            }
            map.put("status",status);
            map.put("personId",personId);
            PageBeanUtil<BusNjorder> orderList = njOrderService.njOrderListByPersonId(currentPage, pageSize,map);
            return orderList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 去查看订单详情
     * @return
     */
    @RequestMapping("/njOrderInfoList")
    public String njOrderInfoList(ModelMap model, String orderId){
        try {
            if(StringUtil.isEmpty(orderId)){
                throw  new Exception("查看订单详情出错，请稍后从试...");
            }else{
                //主订单信息+子订单信息+收货地址信息
                BusNjorder busNjorder = njOrderService.selectByPrimaryKey(orderId);
                BusNjaddress busNjaddress = JSON.parseObject(busNjorder.getOrderAddress(), new TypeReference<BusNjaddress>() {});
                List<BusNjorderinfo> busNjorderinfosList = njorderinfoService.selectByOrderId(orderId);
                model.put("order",busNjorder);
                model.put("orderInfoList",busNjorderinfosList);
                model.put("orderAddress",busNjaddress);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "shoppingCenter/njOrdersInfoList";
    }

    /**
     * 跳转到商户下面的子订单页面页
     * @param model
     * @param orgId
     * @return
     */
    @RequestMapping("/njOrderInfoPage")
    public String njOrderInfoPage(ModelMap model, String orgId, String orderId){
        try {
            if (orgId==null) {
                throw new Exception("商户orgId为空");
            }else{
                model.put("orgId", orgId);
                model.put("orderId", orderId);
            }
            return "shoppingCenter/njServiceStationOrderinfoList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转到订单详情页，查看订单详情，并得到商户列表
     * @param model
     * @param orderId
     * @return
     */
    @RequestMapping("/njOrderReceive")
    public String njOrderReceive(ModelMap model, String orderId){
        try {
            if (orderId==null) {
                throw new Exception("订单orderId为空");
            }else{
                //主订单信息+子订单信息+收货地址信息
                BusNjorder busNjorder = njOrderService.selectByPrimaryKey(orderId);
                BusNjaddress busNjaddress = JSON.parseObject(busNjorder.getOrderAddress(), new TypeReference<BusNjaddress>() {});
                model.put("order",busNjorder);
                model.put("orderAddress",busNjaddress);
                //商户订单
                List<BusNjmerorder> njmerorderList = njmerorderService.selectByOrderId(orderId);
                if(njmerorderList != null && njmerorderList.size() > 0){
                    model.put("merorderList",njmerorderList);
                }
                //商品分类
                List<BusNjorderinfo> njorderinfoList = njorderinfoService.selectByOrderId(orderId);
                String orgId = "";
                if(njorderinfoList!=null && njorderinfoList.size()!=0){
                    for(BusNjorderinfo orderInfo:njorderinfoList){
                        if(orgId.indexOf(orderInfo.getMerId())<0){
                            orgId += orderInfo.getMerId()+",";
                        }
                    }
                    String[] orgIds = orgId.substring(0, orgId.length() - 1).split(",");
                    List<SysOrg> orgList = new ArrayList<SysOrg>();
                    for(String orgid:orgIds){
                        SysOrg sysOrg = orgService.selectByOrgId(orgid);
                        if(sysOrg!=null){
                            BigDecimal totalPrice = new BigDecimal("0.00");//总价
                            String flag = "";//标记
                            for(BusNjorderinfo orderInfo:njorderinfoList){
                                if(sysOrg.getOrgId().equals(orderInfo.getMerId())){
                                    totalPrice = totalPrice.add(new BigDecimal(orderInfo.getProductPrice()).multiply(new BigDecimal(orderInfo.getProductNum())));
                                    flag = orderInfo.getStatus();
                                }
                            }
                            DecimalFormat format = new DecimalFormat("0.0");
                            String price = format.format(totalPrice);
                            sysOrg.setTotalPrice(price);
                            sysOrg.setFlag(flag);
                            orgList.add(sysOrg);
                        }
                    }
                    model.put("orgList", orgList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shoppingCenter/njOrderReceive";
    }


    /**
     * 跳转上传凭证图片页面
     * @return
     */
    @RequestMapping("/uploadImg")
    public String uploadImg1(String orderId,String orgId,ModelMap model){
        try {
            if(StringUtil.isEmpty(orderId)){
                throw  new Exception("系统异常！");
            }
            if(StringUtil.isEmpty(orgId)){
                throw  new Exception("系统异常！");
            }
            String token = QiNiuUtil.upToken();
            model.put("orderId",orderId);
            model.put("orgId",orgId);
            model.put("token",token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shoppingCenter/njMerUploadReceive";
    }
    /**
     * 商户保存收货凭证图片
     * @param hashKey
     * @param orderId
     * @param orgId
     * @return
     */
    @RequestMapping("/saveImg")
    @ResponseBody
    public Map<String,Object> save(String hashKey,String orderId,String orgId){
        Map<String,Object> map = new HashMap<>();
        try{
            if(StrUtils.isEmpty(hashKey)){
                map.put("msg", "图片标识为空");
                return map;
            }
            if(StrUtils.isEmpty(orderId)){
                map.put("msg", "订单id为空");
                return map;
            }
            if(StrUtils.isEmpty(orgId)){
                map.put("msg", "商户id为空");
                return map;
            }
            BusNjmerorder busNjmerorder = njmerorderService.selectByOrderIdAndOrgId(orderId, orgId);
            if(busNjmerorder != null){
                if(busNjmerorder.getSendPic() != null && busNjmerorder.getSendPic() != ""){
                    busNjmerorder.setReceivePic(hashKey);
                    busNjmerorder.setStatus("6");//卖家已收货，订单已完成
                    njmerorderService.updateByPrimaryKeySelective(busNjmerorder);
                    //将该商户对应的子订单状态变为已完成
                    List<BusNjorderinfo> busNjorderinfoList = njorderinfoService.selectByOrderIdAndOrgId(orderId,orgId);
                    for(BusNjorderinfo busNjorderinfo : busNjorderinfoList){
                        busNjorderinfo.setStatus("6");
                        njorderinfoService.updateByPrimaryKeySelective(busNjorderinfo);
                    }
                    //检查主订单对应子订单状态，是否全部已完成，如果全部已完成，则将主订单状态改为已完成
                    List<BusNjorderinfo> busNjorderinfoList1 = njorderinfoService.selectByOrderId(orderId);
                    BusNjorder busNjorder = njOrderService.selectByPrimaryKey(orderId);
                    int num = 0;
                    for(BusNjorderinfo busNjorderinfo : busNjorderinfoList1){
                        if(!busNjorderinfo.getStatus().equals("6")){
                            num ++ ;
                        }
                    }
                    if(num == 0){
                        busNjorder.setStatus("3");
                        njOrderService.updateByPrimaryKeySelective(busNjorder);
                    }
                    map.put("msg","100");
                }else{
                    map.put("msg","200");
                }
            }else{
                map.put("msg","200");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 跳转到查看凭证图片页面
     * @return
     */
    @RequestMapping("/lookImg")
    public String lookImg(String orderId,String orgId,ModelMap model){
        try {
            if(StringUtil.isEmpty(orderId)){
                throw  new Exception("系统异常！");
            }
            if(StringUtil.isEmpty(orgId)){
                throw  new Exception("系统异常！");
            }
            BusNjmerorder busNjmerorder = njmerorderService.selectByOrderIdAndOrgId(orderId,orgId);
            if(busNjmerorder != null){
                model.put("receivePic",busNjmerorder.getReceivePic());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shoppingCenter/njLookReceivePic";
    }
}
