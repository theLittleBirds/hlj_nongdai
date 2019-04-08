package com.nongyeos.loan.admin.controller;

/*
 *  Creat by zhoudi on 2019/3/26.
 */

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.*;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.admin.service.NjShoppingCarService;
import com.nongyeos.loan.base.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/njShoppingCar")
public class NjShoppingCarController {

    @Autowired
    private NjProductOrderService njProductOrderService;
    @Autowired
    private NjProductService njProductService;
    @Autowired
    private NjShoppingCarService njShoppingCarService;

    /**
     * 去购物车页面
     * @return
     */
    @RequestMapping("/njCarList")
    public String njCarList(){
        return "shoppingCenter/njShoppingCarList";
    }

    /**
     * 购物车列表
     * @return
     */
    @RequestMapping("/njShoppingCarList")
    @ResponseBody
    public PageBeanUtil<BusNjshopingcar> njShoppingCarList(int currentPage, int pageSize, HttpServletRequest request){
        try {
            String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
            if(!StringUtils.isEmpty(personId)){
                PageBeanUtil<BusNjshopingcar> busNjshopingcarList = njShoppingCarService.queryBusNjShoppingCarList(currentPage, pageSize,personId);
                return  busNjshopingcarList;
            }else{
                throw new Exception("登录失效请重新登录");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加入购物车
     * id：需要判断是单品id还是套餐id
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("njAddShoppingCar")
    @ResponseBody
    public Map AddShoppingCar(HttpServletRequest request, String id, String productNum){
        Map<String,Object> map = new HashMap<>();
        try {
            String ordId = request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
            String orgName = request.getSession().getAttribute(Constants.SESSION_ORGNM).toString();
            String personId = request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString();
            String personName = request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString();
            if(StringUtil.isEmpty(id)){
                map.put("msg","添加购物车异常，请从新尝试");
                throw new Exception("添加购物车异常，请从新尝试");
            }else{
                //查询当前机构下购物车里面是否有商品
                List<BusNjshopingcar> njshopingcarList = njShoppingCarService.queryShoppingCarList(ordId);
                //判断当前id是商品id，还是套餐id
                NjProduct njProduct = njProductService.queryNjProductByPrimaryKey(id);
                if (njProduct != null){//单品

                    /**
                     * 如果该机构下的购物车为空，则直接构建购物车，并将商品加入购物车
                     * 否则，对购物车和商品进行拆分比较
                     */
                    if(njshopingcarList == null || njshopingcarList.size() == 0){
                        BusNjshopingcar busNjshopingcar = new BusNjshopingcar();
                        busNjshopingcar.setShopingCarId(UUIDUtil.getUUID());
                        busNjshopingcar.setOrgId(ordId);
                        busNjshopingcar.setOrgName(orgName);
                        busNjshopingcar.setPersonId(personId);
                        busNjshopingcar.setMemberName(personName);
                        busNjshopingcar.setProductId(id);
                        busNjshopingcar.setProductName(njProduct.getProductName());
                        busNjshopingcar.setProductNum(Integer.parseInt(productNum));
                        busNjshopingcar.setProductPrice(njProduct.getPrice());
                        busNjshopingcar.setProductSettlePrice(njProduct.getSettlePrice());
                        busNjshopingcar.setProductBrandName(njProduct.getProductBrandName());
                        busNjshopingcar.setProductCategoryName(njProduct.getProductCategoryName());
                        busNjshopingcar.setDetailPic(njProduct.getDetailPic());
                        busNjshopingcar.setCreateDate(DateUtils.getNowDate());
                        busNjshopingcar.setUpdateDate(DateUtils.getNowDate());
                        //购物车插入数据
                        njShoppingCarService.insertShoppingCar(busNjshopingcar);
                    }else{
                        int num = 0;//记录当前商品与购物车中中商品是否有相同
                        for (BusNjshopingcar busNjshopingcar : njshopingcarList){
                            if(busNjshopingcar.getProductId().equals(id)){
                                busNjshopingcar.setProductNum(Integer.parseInt(productNum)+busNjshopingcar.getProductNum());
                                njShoppingCarService.updNjShoppingCar(busNjshopingcar);
                                num++;
                            }
                        }
                        if(num == 0){//0:没有相同
                            BusNjshopingcar busNjshopingcar = new BusNjshopingcar();
                            busNjshopingcar.setShopingCarId(UUIDUtil.getUUID());
                            busNjshopingcar.setOrgId(ordId);
                            busNjshopingcar.setOrgName(orgName);
                            busNjshopingcar.setPersonId(personId);
                            busNjshopingcar.setMemberName(personName);
                            busNjshopingcar.setProductId(id);
                            busNjshopingcar.setProductName(njProduct.getProductName());
                            busNjshopingcar.setProductNum(Integer.parseInt(productNum));
                            busNjshopingcar.setProductPrice(njProduct.getPrice());
                            busNjshopingcar.setProductSettlePrice(njProduct.getSettlePrice());
                            busNjshopingcar.setProductBrandName(njProduct.getProductBrandName());
                            busNjshopingcar.setProductCategoryName(njProduct.getProductCategoryName());
                            busNjshopingcar.setDetailPic(njProduct.getDetailPic());
                            busNjshopingcar.setCreateDate(DateUtils.getNowDate());
                            busNjshopingcar.setUpdateDate(DateUtils.getNowDate());
                            //购物车插入数据
                            njShoppingCarService.insertShoppingCar(busNjshopingcar);
                        }
                    }
                }else{//套餐
                    NjProductOrder njProductOrder = njProductOrderService.queryProductOrderByPK(id);
                    if(njProductOrder == null){
                        map.put("msg","添加购物车异常，请从新尝试");
                        throw new Exception("添加购物车异常，请从新尝试");
                    }else{
                        JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());//套餐所包含的商品对象集合
                        /**
                         * 如果该机构下的购物车为空，则直接构建购物车，并将商品加入购物车
                         * 否则，对购物车和商品进行拆分比较
                         */
                        if(njshopingcarList == null || njshopingcarList.size() == 0){
                            for (Object obj : jsonArray){
                                JSONObject jsonObj = JSONObject.parseObject(obj.toString());
                                BusNjshopingcar busNjshopingcar = new BusNjshopingcar();
                                busNjshopingcar.setShopingCarId(UUIDUtil.getUUID());
                                busNjshopingcar.setOrgId(ordId);
                                busNjshopingcar.setOrgName(orgName);
                                busNjshopingcar.setPersonId(personId);
                                busNjshopingcar.setMemberName(personName);
                                busNjshopingcar.setProductId(jsonObj.getString("id"));
                                busNjshopingcar.setProductName(jsonObj.getString("productName"));
                                Integer num = Integer.parseInt(jsonObj.getString("productNum"));
                                Integer buyProductNum = num * Integer.parseInt(productNum);
                                busNjshopingcar.setProductNum(buyProductNum);
                                busNjshopingcar.setProductPrice(jsonObj.getString("price"));
                                busNjshopingcar.setProductSettlePrice(jsonObj.getString("settlePrice"));
                                busNjshopingcar.setProductBrandName(jsonObj.getString("productBrandName"));
                                busNjshopingcar.setProductCategoryName(jsonObj.getString("productCategoryName"));
                                busNjshopingcar.setDetailPic(jsonObj.getString("detailPic"));
                                busNjshopingcar.setCreateDate(DateUtils.getNowDate());
                                busNjshopingcar.setUpdateDate(DateUtils.getNowDate());
                                //购物车插入数据
                                njShoppingCarService.insertShoppingCar(busNjshopingcar);
                            }
                        } else{//将购物车和套餐进行拆分
                                for (Object obj : jsonArray){
                                    int num = 0;
                                    JSONObject jsonObj = JSONObject.parseObject(obj.toString());
                                    for (BusNjshopingcar busNjshopingcar : njshopingcarList){
                                        String njProductId = busNjshopingcar.getProductId();
                                        if(jsonObj.getString("id").equals(njProductId)){
                                            Integer numTmp = Integer.parseInt(jsonObj.getString("productNum"));
                                            Integer buyProductNum = numTmp * Integer.parseInt(productNum);
                                            busNjshopingcar.setProductNum(busNjshopingcar.getProductNum()+buyProductNum);
                                            njShoppingCarService.updNjShoppingCar(busNjshopingcar);
                                            num++;
                                        }
                                    }
                                    if(num == 0){
                                        BusNjshopingcar busNjshopingcar = new BusNjshopingcar();
                                        busNjshopingcar.setShopingCarId(UUIDUtil.getUUID());
                                        busNjshopingcar.setOrgId(ordId);
                                        busNjshopingcar.setOrgName(orgName);
                                        busNjshopingcar.setPersonId(personId);
                                        busNjshopingcar.setMemberName(personName);
                                        busNjshopingcar.setProductId(jsonObj.getString("id"));
                                        busNjshopingcar.setProductName(jsonObj.getString("productName"));
                                        Integer numTmp = Integer.parseInt(jsonObj.getString("productNum"));
                                        Integer buyProductNum = numTmp * Integer.parseInt(productNum);
                                        busNjshopingcar.setProductNum(buyProductNum);
                                        busNjshopingcar.setProductPrice(jsonObj.getString("price"));
                                        busNjshopingcar.setProductSettlePrice(jsonObj.getString("settlePrice"));
                                        busNjshopingcar.setProductBrandName(jsonObj.getString("productBrandName"));
                                        busNjshopingcar.setProductCategoryName(jsonObj.getString("productCategoryName"));
                                        busNjshopingcar.setDetailPic(jsonObj.getString("detailPic"));
                                        busNjshopingcar.setCreateDate(DateUtils.getNowDate());
                                        busNjshopingcar.setUpdateDate(DateUtils.getNowDate());
                                        //购物车插入数据
                                        njShoppingCarService.insertShoppingCar(busNjshopingcar);
                                    }
                            }
                        }
                    }
                }
            }
            map.put("msg","加入购物车成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 删除商品
     */
    @RequestMapping("/njDeletePro")
    @ResponseBody
    public boolean njDeletePro(String id){
        try {
            if(!StrUtils.isNotEmpty(id)){
                throw new Exception("商品id为空");
            }
            njShoppingCarService.deleteNjProductByPK(id);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 全部删除
     */
    @RequestMapping("/njDelAll")
    @ResponseBody
    public boolean njDelAll(String ids){
        try {
            if(!StrUtils.isNotEmpty(ids)){
                throw new Exception("请选择要删除的商品");
            }
            String[] idS = ids.split(",");
            for(String id : idS){
                njShoppingCarService.deleteNjProductByPK(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
