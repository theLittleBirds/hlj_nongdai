package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nongyeos.loan.admin.entity.*;
import com.nongyeos.loan.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.service.FlowMgr;

@Controller
@RequestMapping("/lending")
public class LendingController {
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private ILendingService lendingService;
	
	@Autowired
	private IWebUserService userService; 
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@Autowired
	private IFlowNodeService flowNodeService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Autowired
	private IMediaService mediaService;
	
	@Autowired
	private IFlowNodeService fleNodeService;
	
	@Autowired
	private ILoanDetailService detailService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private NjProductOrderService njProductOrderService;
	@Autowired
	private NjProductService njProductService;	
	@Autowired  
    private IOrgService orgService;
	@Autowired
	private NjOrderGatherService njOrderGatherService;//联合社订单
	
	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
	private IIntopieceReverseService intopieceReverseService;

	@Autowired
	private IStationBondService stationBondService;
	
	/**
	 * 待放款列表页面
	 * @return
	 */
	@RequestMapping("/waitloan")
	public String waitLoan(){
		return "lending/waitloanlist";
	}
	
	/**
	 * 放弃贷款
	 * @return
	 */
	@RequestMapping("/reject")
	@ResponseBody
	public JSONObject reject(HttpServletRequest request,String id){
		JSONObject retJson = new JSONObject();
		try {
			BusLoan busLoan = loanService.selectByPrimaryKey(id);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(busLoan.getIntoPieceId());
			AppEntry entry = appEntryService.queryByAppModeId(ip.getId());
			FlowNode flowNode = new FlowNode();
			flowNode.setAppId(ip.getModelId());
			flowNode.setEname(Constants.FLOW_REFUSE);
			FlowNode fleNodeId = fleNodeService
					.queryByEnameAndModel(flowNode);
			// 获的相关进件业务数据
			if (entry != null) {
				entry.setNodeId(fleNodeId.getNodeId());// 流程节点id 主键
				entry.setNodeName(fleNodeId.getEname());// 流程节点状态码
				// 更改拒件流程节点
				appEntryService.updateByAppEntrySelective(entry);
			}else{
				retJson.put("code", "400");
				retJson.put("msg", "系统错误！");
				return retJson;
			}
			BusExamine examine = new BusExamine();
			examine.setId(UUIDUtil.getUUID());
			examine.setIntoPieceId(ip.getId());
			examine.setNode(entry.getNodeName());
			examine.setCapital(ip.getCapital());
			examine.setTerm(ip.getTerm());
			examine.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			examine.setExamineDate(new Date());
			examine.setExamineOpinion("放弃贷款");
			examineService.insert(examine);
			ip.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			ip.setUpdDate(DateUtils.getNowDate());
			intoPieceService.updateByPrimaryKey(ip);
			retJson.put("code", "200");
			retJson.put("msg", "操作成功！");
		} catch (Exception e) {
			retJson.put("code", "400");
			retJson.put("msg", "系统错误！");
			return retJson;
		}
		return retJson;
	}
	
	
	/**
	 * 线下放款
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/underlineloan")
	@ResponseBody
	public JSONObject underLineLoan(HttpServletRequest request,String id,String fileNamer,MultipartFile bankBack,String start_date,String fileNamel,MultipartFile bankloan){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");	
			return json;
		}
		try {
			if(bankBack==null){
				json.put("code", 400);
				json.put("msg", "请上传银行回单");
				return json;
			}
			if(bankloan==null){
				json.put("code", 400);
				json.put("msg", "请上传银行借款合同");
				return json;
			}
			if(StringUtils.isEmpty(start_date)){
				json.put("msg", "请选择放款日期！");
				json.put("code", 400);
				return json;
			}
			Date date = DateUtils.parse(start_date, "yyyy-MM-dd");
			if(date.after(new Date())){
				json.put("msg", "放款日期不能在今天之后！");
				json.put("code", 400);
				return json;
			}
			//同盾   添加贷后监控
			BusLoan busLoan = loanService.selectByPrimaryKey(id);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(busLoan.getIntoPieceId());
			List<BusGuaranteeReverse> list = guaranteeReverseService.selectByIntoPieceId(busLoan.getIntoPieceId());
			if(list==null||list.size()<2){
				json.put("msg", "未支付反担保金");
				json.put("code", 400);
				return json;
			}
			for (int i = 0; i < list.size(); i++) {
				if(!"S".equals(list.get(i).getStatus())){
					json.put("msg", "未完成支付反担保金");
					json.put("code", 400);
					return json;
				}
			}
			BusIntopieceReverse ipr = intopieceReverseService.selectByIpId(busLoan.getIntoPieceId());
			//查询出进件的组织机构
			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			if(ipr==null){
				ipr = new BusIntopieceReverse();
				ipr.setId(UUIDUtil.getUUID());
				ipr.setOrgId(org.getOrgId());
				ipr.setIntoPieceId(busLoan.getIntoPieceId());
				ipr.setOrgName(org.getFullCname());
				ipr.setMemberName(ip.getMemberName());
				ipr.setIdCard(ip.getIdCard());
				ipr.setPhone(ip.getPhone());
				ipr.setCapital(ip.getCapital());
				for (int i = 0; i < list.size(); i++) {
					//1为客户,2为站长
					if(list.get(i).getType().equals(1)){
						ipr.setFarmerReverse(list.get(i).getTotalAmount());
					}else{
						ipr.setStationReverse(list.get(i).getTotalAmount());
					}
				}
				ipr.setStatus("1");
				ipr.setCreateDate(DateUtils.getNowDate());
				intopieceReverseService.insert(ipr);
			}
			BusProduct product = productService.selectByProductId(ip.getProduct());
			BusMedia m = new BusMedia();
			String targetPath = baseDir+"contract/";
			String dir = FileUtils.getTimeFilePath();
			String name = FileUtils.generateRandomFilename();
			String fileName = bankBack.getOriginalFilename();
			String showFileName = "银行回单"+fileName.substring(fileName.lastIndexOf("."));
			if(fileName.lastIndexOf(".") != -1){
				name = name + fileName.substring(fileName.lastIndexOf("."));
			}
			targetPath = targetPath+dir;
			FileUtils.createDirectory(targetPath);
			File pathDest = new File(targetPath, name);
            if (!pathDest.exists()) {
            	pathDest.createNewFile();
               }
            FileOutputStream fos = new FileOutputStream(pathDest);
            byte[] f = bankloan.getBytes();
            fos.write(f); 
            fos.close();
            
			m.setId(UUIDUtil.getUUID());
			m.setIntoPieceId(busLoan.getIntoPieceId());
			m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);	
			m.setType(Constants.MEDIATYPE_FILE);
			m.setPath(dir+name);
			m.setName(showFileName);
			m.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			m.setCreDate(new Date());
			m.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			m.setUpdDate(new Date());	
			
			//银行贷款合同
			BusMedia m1 = new BusMedia();
			String targetPath1 = baseDir+"contract/";
			String dir1 = FileUtils.getTimeFilePath();
			String name1 = FileUtils.generateRandomFilename();
			String fileName1 = bankloan.getOriginalFilename();
			String showFileName1 = "银行贷款合同"+fileName1.substring(fileName1.lastIndexOf("."));
			if(fileName1.lastIndexOf(".") != -1){
				name1 = name1 + fileName1.substring(fileName1.lastIndexOf("."));
			}
			targetPath1 = targetPath1+dir1;
			FileUtils.createDirectory(targetPath1);
			File pathDest1 = new File(targetPath1, name1);
            if (!pathDest1.exists()) {
            	pathDest1.createNewFile();
               }
            FileOutputStream fos1 = new FileOutputStream(pathDest1);
            byte[] f1 = bankBack.getBytes();
            fos1.write(f1); 
            fos1.close();
            
			m1.setId(UUIDUtil.getUUID());
			m1.setIntoPieceId(busLoan.getIntoPieceId());
			m1.setIsDeleted(Constants.COMMON_ISNOT_DELETE);	
			m1.setType(Constants.MEDIATYPE_FILE);
			m1.setPath(dir1+name1);
			m1.setName(showFileName1);
			m1.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			m1.setCreDate(new Date());
			m1.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			m1.setUpdDate(new Date());	
			mediaService.insert(m);
			mediaService.insert(m1);
			
			detailService.creatDetail(busLoan, product,DateUtils.parse(start_date, "yyyy-MM-dd"));
/*			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(busLoan.getIntoPieceId());
			
			String userId = request.getSession().getAttribute(Constants.SESSION_USERCD).toString();
			SysWebUser user = userService.selectUserById(userId);
			Map<String,String> map = new HashMap<String, String>();
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("userid", userId);
			map.put("intoPieceId", busLoan.getIntoPieceId());
			map.put("idCardNo", intoPiece.getIdCard());
			try {
				String result = HttpClientUtil.doPost("http://127.0.0.1:8086/apiTongDun/tongdunRiskPost", map, "utf-8");
				if(!StringUtils.isEmpty(result)){
					JSONObject httpResult = JSONObject.parseObject(result);
					if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
						System.out.println("同盾添加贷后监控=======》成功");
					}else{
						//如果添加失败，则在还款列表中手动添加
						System.out.println("同盾添加贷后监控=======》失败");
					}
				}else{
					//如果添加失败，则在还款列表中手动添加
					System.out.println("同盾添加贷后监控=======》失败");
				}
			} catch (Exception e) {
				//如果添加失败，则在还款列表中手动添加
				System.out.println("同盾添加贷后监控=======》失败");
				
				e.printStackTrace();
			}*/
			
			/**
			 * 线下放款，农资贷同时开始生成预支付订单
			 * 
			 * 1.土地抵押贷
			 * 2.土地+农资贷
			 * 3.土地抵押+粮食质押农资贷
			 * 
			 * 只有2,3才生成相应的订单
			 * 
			 * 黑龙江农资贷由于申请之后审核还能改商品和套餐数量及申请金额，所以对loan的ProductListInfo判断
			 */
			if(ip!=null && !StringUtils.isEmpty(ip.getProductListInfo()) && ip.getType() != null && !StringUtils.isEmpty(busLoan.getProductListInfo())){
				if(Constants.LOAN_LAND_PRODUCT.equals(ip.getType().toString()) || Constants.LOAN_LAND_PRODUCT_GRAIN.equals(ip.getType().toString())){
					//从loan取得ProductListInfo明细
					JSONArray proListInfo = JSONArray.parseArray(busLoan.getProductListInfo());
					/**
					 * productId：商品或者套餐id
					 * productNum：商品或者套餐数量
					 * productTime：用货时间（由于选择商品或者套餐时候选择用货时间不一致，但是商品和套餐有重叠，故订单中用货时间先不处理和展示）
					 * productType：1代表商品，2代表套餐
					 */
					//商品
					List<NjProduct> njProductList = new ArrayList<NjProduct>();
					//套餐
					List<NjProductOrder> njProductOrderList = new ArrayList<NjProductOrder>();
					for (Object object : proListInfo) {
						JSONObject jsonObject = JSONObject.parseObject(object.toString());
						
						String productId = jsonObject.getString("productId");//商品或者套餐id
						String productNum = jsonObject.getString("productNum");//商品或者套餐数量
						//String productTime = jsonObject.getString("productTime");//用货时间（由于选择商品或者套餐时候选择用货时间不一致，但是商品和套餐有重叠，故订单中用货时间先不处理和展示）
						String productType = jsonObject.getString("productType");//1代表商品，2代表套餐
						
						//商品
						if("1".equals(productType)){
							//先把所有商品查询出来在分类
							NjProduct njProduct = njProductService.queryNjProductByPrimaryKey(productId);
							njProduct.setProductNum(productNum);//商品数量
							njProductList.add(njProduct);
						}
						//套餐
						if ("2".equals(productType)) {
							//先把所有套餐查询出来在分类
							NjProductOrder njProductOrder = njProductOrderService.queryProductOrderByPK(productId);
							//先通过套餐数量统计出商品总量
							JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());
							JSONArray arrayPro = new JSONArray();
							for (Object obj : jsonArray) {
								JSONObject jsonObj = JSONObject.parseObject(obj.toString());
								String productObjNum = jsonObj.getString("productNum");//套餐选择商品数量
								
								//计算套餐中对于每个商品数量
								BigDecimal proNum = new BigDecimal(productObjNum).multiply(new BigDecimal(productNum));
								
								//更新套餐后的商品数量
								jsonObj.put("productNum", proNum.toString());
								arrayPro.add(jsonObj);
							}
							//保存多个套餐合并成一个套餐后的数量
							njProductOrder.setOrderProductinfo(arrayPro.toJSONString());
							njProductOrderList.add(njProductOrder);
						}
					}
					
			        /**
			         * 对商品进行算法统计
			         * 
			         * 通过商品id分组
			         */
			        Map<String, List<NjProduct>> njProductMap = new HashMap<>();
			        for (NjProduct njProduct : njProductList) {
			        	List<NjProduct> njProTempList = njProductMap.get(njProduct.getId());
			            /*如果取不到数据,那么直接new一个空的ArrayList**/
			            if (njProTempList == null) {
			            	njProTempList = new ArrayList<>();
			            	njProTempList.add(njProduct);
			            	njProductMap.put(njProduct.getId(), njProTempList);
			            }
			            else {
			                /*某个njProduct之前已经存放过了,则直接追加数据到原来的List里**/
			            	njProTempList.add(njProduct);
			            }
			        }
			        
			        /**
			         * 对套餐进行算法统计
			         * 
			         * 通过商品id分组，把套餐中的商品数量增加到对应商品数量上
			         */
			        for (NjProductOrder njProductOrder : njProductOrderList) {
			        	//多个套餐合并成一个套餐后的详情
			        	JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());
			        	for (Object object : jsonArray) {
			        		//取出套餐中每一类商品
			        		NjProduct njProduct = JSONObject.parseObject(object.toString(), NjProduct.class);
							
			        		List<NjProduct> njProTempList = njProductMap.get(njProduct.getId());
				            /*如果取不到数据,那么直接new一个空的ArrayList**/
				            if (njProTempList == null) {
				            	njProTempList = new ArrayList<>();
				            	njProTempList.add(njProduct);
				            	njProductMap.put(njProduct.getId(), njProTempList);
				            }
				            else {
				                /*某个njProduct之前已经存放过了,则直接追加数据到原来的List里**/
				            	njProTempList.add(njProduct);
				            }
						}
					}

			        /**
			         * 最后统计每一种商品的数量
			         */
			        List<NjProduct> proFinalList = new ArrayList<>();
			        for(String skuId : njProductMap.keySet()){
			        	List<NjProduct> njProTempList = njProductMap.get(skuId);
			        	//统计每种商品最后数量
			        	NjProduct njPro = new NjProduct();
			        	for (NjProduct njProduct : njProTempList) {
			        		if(!StringUtils.isEmpty(njPro.getProductNum())){
			        			//不是第一次统计，累加数量
			        			BigDecimal calNum = new BigDecimal(njPro.getProductNum()).add(new BigDecimal(njProduct.getProductNum()));
			        			njPro.setProductNum(calNum.toString());
			        		}else{
			        			//第一次统计
			        			njPro = njProduct;
			        		}
						}
			        	proFinalList.add(njPro);
			        }
					
			        
					for (NjProduct njProduct : proFinalList) {
						//查询商户的组织机构
						SysOrg orgPro = orgService.selectByOrgId(njProduct.getOrgId());
						
						NjOrderGather njOrderGather = new NjOrderGather();
						njOrderGather.setId(UUIDUtil.getUUID());
						njOrderGather.setIntoPieceId(ip.getId());//进件id
						njOrderGather.setOrgId(ip.getOrgId());//进件部门组织机构编码
						njOrderGather.setOrgName(org.getFullCname());//进件部门名称（组织机构名称）
						njOrderGather.setMemberName(ip.getMemberName());//客户姓名
						njOrderGather.setMerOrgId(orgPro.getOrgId());//商户组织机构编码
						njOrderGather.setMerName(orgPro.getFullCname());//商家名字
						njOrderGather.setProductName(njProduct.getProductName());//商品名称
						njOrderGather.setProductBrandName(njProduct.getProductBrandName());//商品品牌名字
						njOrderGather.setProductBrand(njProduct.getProductBrand());//商品品牌，  1：沃肯多，2：金正大，3：沃夫特，4：科雨，5：东北吉化，6：万通盛泰，7：施地壮
						njOrderGather.setProductCategoryName(njProduct.getProductCategoryName());//商品分类名字
						njOrderGather.setProductCategory(njProduct.getProductCategory());//商品分类，  1：化肥，2：种子，3：农药，4：农机，5：其他农用品
						njOrderGather.setStatus(Constants.ASSOCIATE_PENDING_ORDER);//订单状态   待下单
						//njOrderGather.setSettlePrice(njProduct.getSettlePrice());//结算单价
						//njOrderGather.setSettleTotalprice(new BigDecimal(njProduct.getSettlePrice()).multiply(new BigDecimal(njProduct.getProductNum())).toString());//结算总价
						njOrderGather.setSettleTotalprice(new BigDecimal(busLoan.getProductSettlePrice()).toString());//结算总价
						njOrderGather.setProductNum(njProduct.getProductNum());//商品数量
						njOrderGather.setIsDeleted(String.valueOf(Constants.COMMON_ISNOT_DELETE));//有效
						njOrderGather.setCreateDate(DateUtils.getNowDate());//创建时间
						njOrderGather.setUpdateDate(DateUtils.getNowDate());//更新时间
						njOrderGatherService.addOrderGatherSelective(njOrderGather);
					}
					BusStationBond sb = stationBondService.selectByIpId(ip.getId());
					if(sb == null){
						sb = new BusStationBond();
						sb.setIntoPieceId(ip.getId());
						sb.setOrgId(ip.getOrgId());
						sb.setMemberName(ip.getMemberName());
						sb.setIdCard(ip.getIdCard());
						sb.setCapital(busLoan.getCapital());
						sb.setRecieveNongZi(new BigDecimal(busLoan.getRecieveNongZi()));
						sb.setBond(new BigDecimal(org.getWarrantRate()).multiply(new BigDecimal("0.01")).multiply(new BigDecimal(busLoan.getRecieveNongZi())));
					}
					sb.setUpdDate(new Date());
					sb.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					if(sb.getId() == null){
						sb.setId(UUIDUtil.getUUID());
						stationBondService.insert(sb);
					}else{
						stationBondService.updateByPrimaryKey(sb);
					}
				}
			}
			
			BusLoan loan = new BusLoan();
			loan.setId(id);
			//放款日期
			loan.setLoanDate(DateUtils.parse(start_date, "yyyy-MM-dd"));
			if(Constants.LOAN_LAND_PRODUCT.equals(ip.getType().toString()) || Constants.LOAN_LAND_PRODUCT_GRAIN.equals(ip.getType().toString())){
				loan.setRemitDate(start_date);
			}
			loan.setLoanWay(Constants.MONEY_OUT);
			loan.setStatus(Constants.LOAN_NORMAL);
			AppEntry entry = appEntryService.queryByAppModeId(busLoan.getIntoPieceId());
			JSONObject node = flowMgrImpl.getNextTask(entry);
			String nextNodeId = node.getJSONArray("nodes").getJSONObject(0).getString("nodeId");
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, busLoan.getIntoPieceId(), nextNodeId, request);
			BusMessageReminder messageReminder = new BusMessageReminder();
			messageReminder.setIntoPieceId(busLoan.getIntoPieceId());
			messageReminder.setType(Constants.SERVICEDONE);
			messageReminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			List<BusMessageReminder> messageList = messageReminderService.selectByCondition(messageReminder);
			if(messageList!=null&&messageList.size()>0){
				for (int i = 0; i < messageList.size(); i++) {
					BusMessageReminder reminder = messageList.get(i);
					reminder.setIsDelete(Constants.COMMON_IS_DELETE);
					messageReminderService.updateByPrimaryKey(reminder);
				}
			}
			return lendingService.underLineLoan(loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());	
			return json;
		}
	}
	/**
	 * 已放款列表页面
	 * @return
	 */
	@RequestMapping("/alreadyloan")
	public String alreadyLoan(){
		return "lending/alreadyloanlist";
	}

}
