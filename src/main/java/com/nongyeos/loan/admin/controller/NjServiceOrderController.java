package com.nongyeos.loan.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusNjmerorder;
import com.nongyeos.loan.admin.entity.BusNjorder;
import com.nongyeos.loan.admin.entity.BusNjorderinfo;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.service.IBusNjmerorderService;
import com.nongyeos.loan.admin.service.INjOrderService;
import com.nongyeos.loan.admin.service.INjorderinfoService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ThinkPad on 2019/3/25.
 */
@Controller
@RequestMapping("/njServiceOrder")
public class NjServiceOrderController {

    @Autowired
    private INjOrderService njOrderService;
    @Autowired
    private INjorderinfoService njorderinfoService;
    @Autowired
    private IOrgService orgService;
    @Autowired
    private IBusNjmerorderService njmerorderService;

    /**
     * 点击左侧菜单跳转页面
     * @return
     */
    @RequestMapping("/njServiceOrderList")
    public String njServiceOrderList(){
        return "shoppingCenter/njServiceOrderList";
    }

    /**
     * 异步获取订单列表
     * @param currentPage
     * @param pageSize
     * @param njorder
     * @return
     */
    @RequestMapping("/njOrderList")
    @ResponseBody
    public PageBeanUtil<BusNjorder> njOrderList(int currentPage, int pageSize, BusNjorder njorder){
        try {
            if (njorder==null) {
                njorder = new BusNjorder();
            }
            PageBeanUtil<BusNjorder> orderList = njOrderService.njOrderList(currentPage, pageSize, njorder);
            return orderList;
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
    @RequestMapping("/njOrderDetails")
    public String njOrderDetails(ModelMap model, String orderId){
        try {
            if (orderId==null) {
                throw new Exception("订单orderId为空");
            }else{
                BusNjorder njorder = njOrderService.selectByPrimaryKey(orderId);
                model.put("njorder", njorder);
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
                                    totalPrice = totalPrice.add(new BigDecimal(orderInfo.getProductSettlePrice()).multiply(new BigDecimal(orderInfo.getProductNum())));
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
                if("1".equals(njorder.getStatus())){
                    return "shoppingCenter/njOrderConfirmDetails";
                }else{
                    return "shoppingCenter/njOrderDetails";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            return "shoppingCenter/njorderinfoList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转上传凭证图片页面
     * @param model
     * @param orgId
     * @return
     */
    @RequestMapping("/uploadImg")
    public String uploadImg(ModelMap model, String orgId,String orderId,String flag){
        try {
            if (orgId==null) {
                throw new Exception("商户orgId为空");
            }else{
                model.put("orgId", orgId);
                model.put("orderId", orderId);
                model.put("flag", flag);
                model.put("token", QiNiuUtil.upToken());
            }
            return "shoppingCenter/njMerUploadVoucher";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 子订单保存上传凭证图片
     * @param hashKey
     * @param orderId
     * @param orgId
     * @return
     */
    @RequestMapping("/saveImg")
    @ResponseBody
    public JSONObject save(String hashKey,String orderId,String orgId,String flag){
        try{
            JSONObject json = new JSONObject();
            if(StrUtils.isEmpty(hashKey)){
                json.put("code", 400);
                json.put("msg", "图片标识为空");
                return json;
            }
            if(StrUtils.isEmpty(orderId)){
                json.put("code", 400);
                json.put("msg", "订单id为空");
                return json;
            }
            if(StrUtils.isEmpty(orgId)){
                json.put("code", 400);
                json.put("msg", "商户id为空");
                return json;
            }
            //根据订单id和商户id查询所有子订单，并设置图片字段
            List<BusNjorderinfo> busNjorderinfos = njorderinfoService.selectByOrderIdAndOrgId(orderId, orgId);
            if(busNjorderinfos!=null){
                BigDecimal totalPrice = new BigDecimal("0.00");//总价
                String orgName = "";
                for (BusNjorderinfo njOrderInfo:busNjorderinfos){
                    orgName = njOrderInfo.getMerName();
                    totalPrice = totalPrice.add(new BigDecimal(njOrderInfo.getProductSettlePrice()).multiply(new BigDecimal(njOrderInfo.getProductNum())));
                    if("1".equals(flag)){
                        njOrderInfo.setPayPic(hashKey); //存图片key定金
                        njOrderInfo.setStatus(Constants.MER_NEW_WAIT_OERDER);  //状态改为 1 已付定金，商户待接单
                    }else{
                        njOrderInfo.setPayPicTail(hashKey);//存图片key尾款
                        njOrderInfo.setStatus(Constants.MER_NEW_DELIVER_ORDER);  //状态改为 3 已付尾款，商户待发货
                    }

                    njorderinfoService.updateByPrimaryKeySelective(njOrderInfo);
                }
                DecimalFormat format = new DecimalFormat("0.0");
                BusNjmerorder merOrder = new BusNjmerorder();
                merOrder.setOrderId(orderId);
                merOrder.setOrgId(orgId);
                merOrder.setOrgName(orgName);
                merOrder.setTotalSettlePrice(format.format(totalPrice));
                if("1".equals(flag)){
                    merOrder.setPayPic(hashKey);//定金
                    merOrder.setStatus(Constants.MER_NEW_WAIT_OERDER);
                    merOrder.setCreateDate(new Date());
                    merOrder.setUpdateDate(new Date());
                    njmerorderService.insertSelective(merOrder);
                }else{
                    merOrder = njmerorderService.selectByOrderIdAndOrgId(orderId, orgId);
                    merOrder.setPayPicTail(hashKey);//尾款
                    merOrder.setStatus(Constants.MER_NEW_DELIVER_ORDER);
                    merOrder.setUpdateDate(new Date());
                    njmerorderService.updateByPrimaryKeySelective(merOrder);
                }

            }
            json.put("code", 200);
            json.put("msg", "上传成功");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回图片集合
     * @param orderId
     * @param orgId
     * @return
     */
    @RequestMapping("/listImg")
    @ResponseBody
    public JSONArray listImg(String orderId,String orgId,String flag){
        try{
            List<BusNjorderinfo> busNjorderinfoList = njorderinfoService.selectByOrderIdAndOrgId(orderId, orgId);
            JSONArray arr = new JSONArray();
            if (busNjorderinfoList!=null){
                if("1".equals(flag)){
                    JSONObject obj = new JSONObject();
                    obj.put("key", busNjorderinfoList.get(0).getPayPic());
                    obj.put("fileType", "1");
                    arr.add(obj);
                }else if ("2".equals(flag)){
                    JSONObject obj = new JSONObject();
                    obj.put("key", busNjorderinfoList.get(0).getPayPicTail());
                    obj.put("fileType", "2");
                    arr.add(obj);
                }

            }
            return QiNiuUtil.getUrlEndJpg(arr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回图片集合
     * @param orderId
     * @param orgId
     * @return
     */
    @RequestMapping("/listImgDetail")
    @ResponseBody
    public JSONArray listImgDetail(String orderId,String orgId){
        try{
            List<BusNjorderinfo> busNjorderinfoList = njorderinfoService.selectByOrderIdAndOrgId(orderId, orgId);
            JSONArray arr = new JSONArray();
            if (busNjorderinfoList!=null){
                JSONObject obj = new JSONObject();
                obj.put("key", busNjorderinfoList.get(0).getPayPic());
                obj.put("fileType", "1");
                arr.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("key", busNjorderinfoList.get(0).getPayPicTail());
                obj2.put("fileType", "2");
                arr.add(obj2);
            }
            return QiNiuUtil.getUrlEndJpg(arr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回大订单凭证图片
     * @param orderId
     * @return
     */
    @RequestMapping("/orderImg")
    @ResponseBody
    public JSONArray orderImg(String orderId){
        try{
            BusNjorder busNjorder = njOrderService.selectByPrimaryKey(orderId);
            JSONArray arr = new JSONArray();
            if (busNjorder!=null){
                JSONObject obj = new JSONObject();
                obj.put("key", busNjorder.getPayPic());
                arr.add(obj);
            }
            return QiNiuUtil.getUrlEndJpg(arr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步加载子订单table列表
     * @param currentPage
     * @param pageSize
     * @param orgId
     * @return
     */
    @RequestMapping("/njOrderInfoList")
    @ResponseBody
    public PageBeanUtil<BusNjorderinfo> njOrderInfoList(int currentPage, int pageSize, String orgId, String orderId){
        try {
            if (orgId==null) {
                throw new Exception("商户orgId为空");
            }
            PageBeanUtil<BusNjorderinfo> njorderinfoList = njorderinfoService.njOrderInfoList(currentPage, pageSize, orgId, orderId);
            return njorderinfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 订单确认凭证
     * @param orderId
     * @return
     */
    @RequestMapping("/updateNjOrder")
    @ResponseBody
    public JSONObject updateNjOrder(String orderId){
        try {
            JSONObject json = new JSONObject();
            if (orderId==null) {
                json.put("code",400);
                json.put("msg","订单id为空");
            }else{
                BusNjorder busNjorder = njOrderService.selectByPrimaryKey(orderId);
                if(busNjorder==null){
                    json.put("code",400);
                    json.put("msg","订单对象为空");
                }else{
                    busNjorder.setStatus(Constants.SERVICESITE_SURE_ORDER);
                    njOrderService.updateByPrimaryKeySelective(busNjorder);
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

}
