package com.nongyeos.loan.admin.controller.nj;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nongyeos.loan.admin.entity.*;
import com.nongyeos.loan.admin.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.impl.LoanServiceImpl;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Controller
@RequestMapping("/nj/findLoan")
public class CFindLoanController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CFindLoanController.class);
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private IApplySignFileService applySignFileService;
	
	@Autowired
	private SysWebUserMapper userMapper;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IFlowNodeService fleNodeService;
	
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	
	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private IMediaService mediaService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Autowired
	private ILoanDetailService detailService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	
	@Autowired
	private IFlowNodeService flowNodeService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	@Autowired
	private ILendingService lendingService;

	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	
	@Autowired
	private ApiWxGuaranteefeeService wxGuaranteefeeService;
	
	@Autowired
	private IRefundService refundService;
	
	@Autowired
	private IFamilyCapitalService capitalService;

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
	private IIntopieceReverseService intopieceReverseService;
	
	@Autowired
	private IWebUserService userService;

	@Autowired
	private IStationBondService stationBondService;
	
	private static int pageSize = 10;
	
	/**
	 * 
	 * @Title: findLoan 
	 * @Description: 个人查询借款列表及还款列表
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/findLoan")
	@ResponseBody
	public JSONObject findLoan(HttpServletRequest request,String memberName,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		BusMemberLogin memberLogin =null;
		try {
			memberLogin =memberLoginService.selectById(loginId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId =null;
		 if(memberLogin==null){
			 userId =loginId;
		 }
		String page = request.getParameter("page");
		Integer page1 = null;
		if(StringUtils.isEmpty(page)){
			page1 =1;
		}else{
			page1= new Integer(page);
		}
		//status借款状态（1:所有借款 2：审核中  3：待放款 4：还款中）
		String status1 = request.getParameter("status");
		try {
			String channel = request.getHeader("channel");
			PageBeanUtil<IntoPieceMap> pageBean =null;
			Integer status = new Integer(status1);
			JSONArray arr = new JSONArray();
			StringBuilder condition = new StringBuilder();
			if(status==1){
				condition.append("0");
				for (int i = 1; i < 16; i++) {
					condition.append(","+i+"");
				}
				
			}else if(status==2){
				condition.append("2");
				for (int i = 3; i < 6; i++) {
					condition.append(","+i+"");
				}
			}else if(status==3){
				condition.append("8");
			}else if(status==4){
				condition.append("10");
			}else{
				response.setStatus(400);
				retJson.put("errno", 3010);
				retJson.put("message", "无效的查询条件!");
				return retJson;
			}
			if(StringUtils.isEmpty(userId)){
				pageBean =intoPieceService.selectByMemberLoginId(page1,pageSize,condition.toString(),loginId,Constants.COMMON_ISNOT_DELETE,channel);
			}else{
				pageBean = intoPieceService.selectByUserId(page1,pageSize,condition.toString(),userId,Constants.COMMON_ISNOT_DELETE,memberName);
			}
			if(pageBean!=null){
	 			List<IntoPieceMap> list = pageBean.getItems();
	 			pageBean.setItems(list);
				if(pageBean.getTotalPage()<page1){
					response.setStatus(400);
					retJson.put("errno", 3011);
					retJson.put("message", "没有更多数据了！");
					return retJson;
				}else{
					int totalPage = pageBean.getTotalPage();
					int totalRow = pageBean.getTotalNum();
					int currentPage = pageBean.getCurrentPage();
					for (int i = 0; i < pageBean.getItems().size(); i++) {
						IntoPieceMap intoPieceMap = pageBean.getItems().get(i);
						JSONObject obj = new JSONObject();
						if(status==4){
							BusLoan loan = loanService.selectByIpId(intoPieceMap.getId());
							if(loan!=null){
								//如果是已还则不显示
								SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
						        Calendar calendar = Calendar.getInstance();  
						        calendar.add(Calendar.MONTH, 1);  	
						        String date = df.format(calendar.getTime())+"01";
						        //未还
//								if(DateUtils.parse(date,"yyyyMMdd").after(loan.getFirstRepaymentDate())){
									obj.put("loanId", loan.getId());
									obj.put("intoPieceId", intoPieceMap.getId());
									obj.put("memberName", intoPieceMap.getMemberName());
									obj.put("phone", intoPieceMap.getPhone());
									obj.put("money", intoPieceMap.getCapital()==null?"-":intoPieceMap.getCapital());
									obj.put("term", intoPieceMap.getTerm());
									obj.put("status", intoPieceMap.getStatus());
									obj.put("createDate", DateUtils.format(intoPieceMap.getCreDate(), "yyyy-MM-dd"));
									arr.add(obj);
									continue;
//								}else{
//									//当前期已还
//									continue;
//								}
								
							}else{
								continue;
							}
						}else if(status==3){
							BusLoan loan = loanService.selectByIpId(intoPieceMap.getId());
							//默认没有签约链接按钮
							obj.put("isSign", "0");
							if(memberLogin != null){
								List<BusApplySignFile> signList = applySignFileService.waitSign(loan.getId());
								if(signList != null && signList.size() > 0){
									SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
									Map<String, String> map = new HashMap<String, String>();								
									map.put("userid", user.getUserId());
									String signature = user.getUserId() + user.getUsername() + user.getPassword();
									map.put("signature", DigestUtils.md5Hex(signature));
									map.put("applyNo", signList.get(0).getApplyNo());
									map.put("fullName",intoPieceMap.getMemberName());
									map.put("idCard", intoPieceMap.getIdCard());
									String result = HttpClientUtil.doPost(pieceConfig.getJunziqianSignUrl(), map, "utf-8");
									if(StrUtils.isNotEmpty(result)){
										JSONObject resultObj = JSONObject.parseObject(result);
										String isSuccess = resultObj.getString("isSuccess");
										if("1".equals(isSuccess)){
											obj.put("url", resultObj.getString("url"));
											obj.put("isSign", "1");
										}
									}																
								}
							}							
							obj.put("intoPieceId", intoPieceMap.getId());
							obj.put("memberName", intoPieceMap.getMemberName());
							obj.put("phone", intoPieceMap.getPhone());
							obj.put("money", intoPieceMap.getCapital()==null?"-":intoPieceMap.getCapital());
							obj.put("term", intoPieceMap.getTerm());
							obj.put("status", intoPieceMap.getStatus());
							obj.put("createDate", DateUtils.format(intoPieceMap.getCreDate(), "yyyy-MM-dd"));
							arr.add(obj);
						}else{
							obj.put("intoPieceId", intoPieceMap.getId());
							obj.put("memberName", intoPieceMap.getMemberName());
							obj.put("phone", intoPieceMap.getPhone());
							obj.put("money", intoPieceMap.getCapital()==null?"-":intoPieceMap.getCapital());
							obj.put("term", intoPieceMap.getTerm());
							obj.put("status", intoPieceMap.getStatus());
							obj.put("createDate", DateUtils.format(intoPieceMap.getCreDate(), "yyyy-MM-dd"));
							arr.add(obj);
						}
						
					}
					retJson.put("totalPage", totalPage);
					retJson.put("totalRow", totalRow);
					retJson.put("currentPage", currentPage);
					retJson.put("data", arr);
					retJson.put("errno", 0);
				}
				
			}else{
				response.setStatus(400);
				retJson.put("errno", 3009);
				retJson.put("message", "未找到贷款信息！");
				return retJson;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("errno", 3009);
			retJson.put("message", "未找到贷款信息！");
			return retJson;
		}
		return retJson;
	}
	
	@RequestMapping("/repaymentInfo")
	@ResponseBody
	public JSONObject repaymentInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String loanId = request.getParameter("loanId");
			if(StringUtils.isEmpty(loanId)){
				retJson.put("message", "请选择查看的贷款");
				response.setStatus(400);
				return retJson;
			}
			BusLoan loan = loanService.selectByPrimaryKey(loanId);
			JSONObject obj = new JSONObject();
			if(loan!=null){
//				obj.put("", value);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retJson;
	}
	
	@RequestMapping("/pendingLoan")
	@ResponseBody
	public JSONObject pendingLoan(HttpServletRequest request,HttpServletResponse response,String type){
		JSONObject retJson = new JSONObject();
		try {
			String userId = (String) request.getAttribute("loginId");
			String intoPieceId = request.getParameter("intoPieceId");
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			//1标识银行已放款，2标识放弃贷款将会直接拒件
			if(type.equals("2")){
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
					response.setStatus(400);
					retJson.put("message", "未找到该进件！");
					return retJson;
				}
				BusExamine examine = new BusExamine();
				examine.setId(UUIDUtil.getUUID());
				examine.setIntoPieceId(intoPieceId);
				examine.setNode(entry.getNodeName());
				examine.setCapital(ip.getCapital());
				examine.setTerm(ip.getTerm());
				examine.setExamineBy(userId);
				examine.setExamineDate(new Date());
				examine.setExamineOpinion("放弃贷款");
				examineService.insert(examine);
				ip.setUpdOperId(userId);
				ip.setUpdDate(DateUtils.getNowDate());
				intoPieceService.updateByPrimaryKey(ip);
			}
			response.setStatus(200);
			retJson.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("message", "网络错误！");
			return retJson;
		}
		return retJson;
	} 
	
	@RequestMapping("/saveBankBack")
	@ResponseBody
	public JSONObject  saveBankBack(HttpServletRequest request,HttpServletResponse response,String loanTime,String intoPieceId,MultipartFile bankBack,MultipartFile bankloan){
		JSONObject retJson = new JSONObject();
		String user =(String) request.getAttribute("loginId");
		try {
			if(bankBack==null){
				response.setStatus(400);
				retJson.put("message", "请上传银行回单");
				return retJson;
			}
			if(bankloan==null){
				response.setStatus(400);
				retJson.put("message", "请上传银行借款合同");
				return retJson;
			}
			Date date = DateUtils.parse(loanTime, "yyyy-MM-dd");
			if(date.after(new Date())){
				response.setStatus(400);
				retJson.put("message", "放款日期不能在今天之后！");
				return retJson;
			}
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			BusLoan loan = loanService.selectByIpId(intoPieceId);
			BusProduct product = productService.selectByProductId(ip.getProduct());
			SysPerson person = personService.selectByUserIdAndIsdefault(user);
			Date loanDate = DateUtils.parse(loanTime, "yyyy-MM-dd");
			List<BusGuaranteeReverse> list = guaranteeReverseService.selectByIntoPieceId(intoPieceId);
			if(list==null||list.size()<2){
				retJson.put("message", "未支付反担保金");
				response.setStatus(400);
				return retJson;
			}
			for (int i = 0; i < list.size(); i++) {
				if(!"S".equals(list.get(i).getStatus())){
					retJson.put("message", "未完成支付反担保金");
					response.setStatus(400);
					return retJson;
				}
			}
			BusIntopieceReverse ipr = intopieceReverseService.selectByIpId(intoPieceId);
			//查询出进件的组织机构
			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			if(ipr==null){
				ipr = new BusIntopieceReverse();
				ipr.setId(UUIDUtil.getUUID());
				ipr.setOrgId(org.getOrgId());
				ipr.setIntoPieceId(intoPieceId);
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
            byte[] f = bankBack.getBytes();
            fos.write(f); 
            fos.close();
            
			m.setId(UUIDUtil.getUUID());
			m.setIntoPieceId(intoPieceId);
			m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);	
			m.setType(Constants.MEDIATYPE_FILE);
			m.setPath(dir+name);
			m.setName(showFileName);
			m.setCreOperId(user);
			m.setCreDate(new Date());
			m.setUpdOperId(user);
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
            byte[] f1 = bankloan.getBytes();
            fos1.write(f1); 
            fos1.close();
            
			m1.setId(UUIDUtil.getUUID());
			m1.setIntoPieceId(intoPieceId);
			m1.setIsDeleted(Constants.COMMON_ISNOT_DELETE);	
			m1.setType(Constants.MEDIATYPE_FILE);
			m1.setPath(dir1+name1);
			m1.setName(showFileName1);
			m1.setCreOperId(user);
			m1.setCreDate(new Date());
			m1.setUpdOperId(user);
			m1.setUpdDate(new Date());	
			mediaService.insert(m1);
			mediaService.insert(m);
			detailService.creatDetail(loan, product,loanDate);
			
			/**
			 * 线下放款，农资贷同时开始生成预支付订单
			 * 
			 * 1.土地抵押贷
			 * 2.土地+农资贷
			 * 3.土地抵押+粮食质押农资贷
			 * 
			 * 只有2,3才生成相应的订单
			 * 
			 */
			if(ip!=null && !StringUtils.isEmpty(ip.getProductListInfo()) && ip.getType() != null){
				if(Constants.LOAN_LAND_PRODUCT.equals(ip.getType().toString()) || Constants.LOAN_LAND_PRODUCT_GRAIN.equals(ip.getType().toString())){
					JSONArray proListInfo = JSONArray.parseArray(ip.getProductListInfo());
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
						njOrderGather.setSettlePrice(njProduct.getSettlePrice());//结算单价
						njOrderGather.setSettleTotalprice(new BigDecimal(njProduct.getSettlePrice()).multiply(new BigDecimal(njProduct.getProductNum())).toString());//结算总价
						njOrderGather.setProductNum(njProduct.getProductNum());//商品数量
						njOrderGather.setIsDeleted(String.valueOf(Constants.COMMON_ISNOT_DELETE));//有效
						njOrderGather.setCreateDate(DateUtils.getNowDate());//创建时间
						njOrderGather.setUpdateDate(DateUtils.getNowDate());//更新时间
						njOrderGatherService.addOrderGatherSelective(njOrderGather);
					}
					BusStationBond sb = stationBondService.selectByIpId(intoPieceId);
					if(sb == null){
						sb = new BusStationBond();
						sb.setIntoPieceId(intoPieceId);
						sb.setOrgId(ip.getOrgId());
						sb.setMemberName(ip.getMemberName());
						sb.setIdCard(ip.getIdCard());
						sb.setCapital(loan.getCapital());
						sb.setRecieveNongZi(new BigDecimal(loan.getRecieveNongZi()));
						sb.setBond(new BigDecimal(org.getWarrantRate()).multiply(new BigDecimal("0.01")).multiply(new BigDecimal(loan.getRecieveNongZi())));
					}
					sb.setUpdDate(new Date());
					sb.setUpdOperId(user);
					if(sb.getId() == null){
						sb.setId(UUIDUtil.getUUID());
						stationBondService.insert(sb);
					}else{
						stationBondService.updateByPrimaryKey(sb);
					}
				}
			}
			
			BusLoan loansave = new BusLoan();
			loansave.setId(loan.getId());
			//放款日期
			loansave.setLoanDate(DateUtils.parse(loanTime, "yyyy-MM-dd"));
			if(Constants.LOAN_LAND_PRODUCT.equals(ip.getType().toString()) || Constants.LOAN_LAND_PRODUCT_GRAIN.equals(ip.getType().toString())){
				loan.setRemitDate(loanTime);
			}
			loansave.setLoanWay(Constants.MONEY_OUT);
			loansave.setStatus(Constants.LOAN_NORMAL);
			AppEntry entry = appEntryService.queryByAppModeId(loan.getIntoPieceId());
			JSONObject node = flowMgrImpl.getNextTask(entry);
			String nextNodeId = node.getJSONArray("nodes").getJSONObject(0).getString("nodeId");
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, loan.getIntoPieceId(), nextNodeId, request);
			BusMessageReminder messageReminder = new BusMessageReminder();
			messageReminder.setIntoPieceId(intoPieceId);
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
			lendingService.underLineLoan(loansave, person.getPersonId());
			retJson.put("code", 200);
			retJson.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("message", "网络错误");
		}
		return retJson;
	}
	
	@RequestMapping("/wxsaveBankBack")
	@ResponseBody
	public JSONObject  wxSaveBankBack(HttpServletRequest request,HttpServletResponse response,String loanTime,String intoPieceId,String bankBack){
		JSONObject retJson = new JSONObject();
		String user =(String) request.getAttribute("loginId");
		try {
			Date date = DateUtils.parse(loanTime, "yyyy-MM-dd");
			if(date.after(new Date())){
				response.setStatus(400);
				retJson.put("message", "放款日期不能在今天之后！");
				return retJson;
			}
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			BusLoan loan = loanService.selectByIpId(intoPieceId);
			BusProduct product = productService.selectByProductId(ip.getProduct());
			SysPerson person = personService.selectByUserIdAndIsdefault(user);
			Date loanDate = DateUtils.parse(loanTime, "yyyy-MM-dd");
			BusMedia m = new BusMedia();
			m.setId(UUIDUtil.getUUID());
			m.setIntoPieceId(intoPieceId);
			m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);	
			m.setType(Constants.MEDIATYPE_FILE);
			m.setPath(bankBack);
			m.setName("银行回单"+bankBack.substring(bankBack.lastIndexOf(".")));
			m.setCreOperId(user);
			m.setCreDate(new Date());
			m.setUpdOperId(user);
			m.setUpdDate(new Date());	
			mediaService.insert(m);
			detailService.creatDetail(loan, product,loanDate);
			BusLoan loansave = new BusLoan();
			loansave.setId(loan.getId());
			//放款日期
			loansave.setLoanDate(DateUtils.parse(loanTime, "yyyy-MM-dd"));
			loansave.setLoanWay(Constants.MONEY_OUT);
			loansave.setStatus(Constants.LOAN_NORMAL);
			AppEntry entry = appEntryService.queryByAppModeId(loan.getIntoPieceId());
			JSONObject node = flowMgrImpl.getNextTask(entry);
			String nextNodeId = node.getJSONArray("nodes").getJSONObject(0).getString("nodeId");
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, loan.getIntoPieceId(), nextNodeId, request);
			BusMessageReminder messageReminder = new BusMessageReminder();
			messageReminder.setIntoPieceId(intoPieceId);
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
			lendingService.underLineLoan(loansave, person.getPersonId());
			retJson.put("code", 200);
			retJson.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("message", "网络错误");
		}
		return retJson;
	}
	
//	/**
//	 * 
//	 * @Title: findLoan 
//	 * @Description: 商户查询借款列表及还款列表
//	 * @param @param request
//	 * @param @return     
//	 * @return JSONObject     
//	 * @throws
//	 */
//	@RequestMapping("/userFind")
//	@ResponseBody
//	public JSONObject userFindLoan(HttpServletRequest request){
//		JSONObject retJson = new JSONObject();
//		String userId = (String) request.getAttribute("loginId");
//		String page = request.getParameter("page");
//		Integer page1 = null;
//		if(StringUtils.isEmpty(page)){
//			page1 =1;
//		}else{
//			page1= new Integer(page);
//		}
//		//status借款状态（1:所有借款 2：审核中  3：还款中 4：已还清）
//		String status1 = request.getParameter("status");
//		try {
//			PageBeanUtil<IntoPieceMap> pageBean =null;
//			Integer status = new Integer(status1);
//			JSONArray arr = new JSONArray();
//			if(status==1){
//				StringBuilder condition = new StringBuilder();
//				condition.append("('0'");
//				for (int i = 1; i < 14; i++) {
//					condition.append(",'"+i+"'");
//				}
//				condition.append(")");
//				pageBean = intoPieceService.selectByUserId(page1,pageSize,condition.toString(),userId,Constants.COMMON_ISNOT_DELETE);
//			}else if(status==2){
//				StringBuilder condition = new StringBuilder();
//				condition.append("('1'");
//				for (int i = 2; i < 9; i++) {
//					condition.append(",'"+i+"'");
//				}
//				condition.append(")");
//				pageBean = intoPieceService.selectByUserId(page1,pageSize,condition.toString(),userId,Constants.COMMON_ISNOT_DELETE);
//			}else if(status==3){
//				StringBuilder condition = new StringBuilder();
//				condition.append("('9'");
//				for (int i = 10; i < 13; i++) {
//					condition.append(",'"+i+"'");
//				}
//				condition.append(")");
//				pageBean = intoPieceService.selectByUserId(page1,pageSize,condition.toString(),userId,Constants.COMMON_ISNOT_DELETE);
//			}else if(status==4){
//				StringBuilder condition = new StringBuilder();
//				condition.append("('13'");
//				condition.append(")");
//				pageBean = intoPieceService.selectByUserId(page1,pageSize,condition.toString(),userId,Constants.COMMON_ISNOT_DELETE);
//			}else{
//				response.setStatus(400);
//				retJson.put("errno", 3010);
//				retJson.put("message", "无效的查询条件!");
//				return retJson;
//			}
//			if(pageBean!=null){
//				if(pageBean.getTotalPage()<page1){
//					response.setStatus(400);
//					retJson.put("errno", 3011);
//					retJson.put("message", "没有更多数据了！");
//					return retJson;
//				}else{
//					int totalPage = pageBean.getTotalPage();
//					int totalRow = pageBean.getTotalNum();
//					int currentPage = pageBean.getCurrentPage();
//					for (int i = 0; i < pageBean.getItems().size(); i++) {
//						IntoPieceMap intoPieceMap = pageBean.getItems().get(i);
//						JSONObject obj = new JSONObject();
//						obj.put("intoPieceId", intoPieceMap.getId());
//						obj.put("status", intoPieceMap.getStatus());
//						obj.put("create_date", DateUtils.format(intoPieceMap.getCreDate(), "yyyy-MM-dd"));
//						arr.add(obj);
//					}
//					retJson.put("totalPage", totalPage);
//					retJson.put("totalRow", totalRow);
//					retJson.put("currentPage", currentPage);
//					retJson.put("data", arr);
//					retJson.put("errno", 0);
//				}
//				
//			}else{
//				response.setStatus(400);
//				retJson.put("errno", 3009);
//				retJson.put("message", "未找到贷款信息！");
//				return retJson;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return retJson;
//	}
	
	@RequestMapping("/abandon")
	@ResponseBody
	public JSONObject abandon(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String loginId = (String) request.getAttribute("loginId");
			BusMemberLogin memberLogin =null;
			try {
				memberLogin =memberLoginService.selectById(loginId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId =null;
			 if(memberLogin==null){
				 userId =loginId;
			 }
			String intoPieceId = request.getParameter("intoPieceId");
			if(StringUtils.isEmpty(intoPieceId)){
				response.setStatus(400);
				retJson.put("message", "进件标识为空");
				return retJson;
			}
			SysPerson person =null;
			
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(StringUtils.isNotEmpty(userId)){
				person =personService.selectByUserIdAndIsdefault(loginId);
			}else{
				person =personService.selectByPersonId(ip.getOperUserId());
			}
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			JSONObject node = flowMgrImpl.abandon(entry);
			
			if(node != null && node.getIntValue("code") == 200){
				JSONArray arr = node.getJSONArray("nodes");
				if(arr != null){
					String nextNodeId = arr.getJSONObject(0).getString("nodeId");
					BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
					flowMgrImpl.changeNode(business, nextNodeId, person.getPersonId());
				}				
			}
			boolean flag = true;
			//先锋
			if(ip.getServiceFeeWay()!=null&&ip.getServiceFeeWay().equals(1)){
				JSONObject node1 = flowMgrImpl.getNextTask(entry);
				if(node1 != null && node1.getIntValue("code") == 200){
					JSONArray arr = node1.getJSONArray("nodes");
					if(arr != null){
						String nextNodeId = arr.getJSONObject(0).getString("nodeId");
						String pcId = node1.getString("pcId");
						BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
						flowMgrImpl.next(business, nextNodeId, pcId, person.getPersonId());
					}	
				}
				//微信
			}else if(ip.getServiceFeeWay()!=null&&ip.getServiceFeeWay().equals(2)){
				BusGuaranteeFeeInfo info = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
				ApiWXGuaranteeFee wxGuaranteeFee = new ApiWXGuaranteeFee();
				wxGuaranteeFee.setIntoPieceId(intoPieceId);
				wxGuaranteeFee.setStatus("S");
				wxGuaranteeFee= wxGuaranteefeeService.selectByIpIdAndStatus(wxGuaranteeFee);
				//成功通过微信支付付款
				if(info!=null&&info.getPayWay().equals("2") &&info.getStatus().equals("S")){
					flag=false;
					BusRefund refund = refundService.selectByIntoPieceId(intoPieceId);
					if(refund==null){
						refund=new BusRefund();
						refund.setId(UUIDUtil.getUUID());
						refund.setOrgId(info.getOrgId());
						refund.setOrgName(info.getOrgName());
						refund.setIntoPieceId(intoPieceId);
						refund.setUse(info.getUse());
						refund.setAccountName(info.getAccountName());
						refund.setCertificateNo(info.getCertificateNo());
						//贷款总金额
						refund.setAccountNo(info.getAccountNo());
						refund.setMobileNo(info.getMobileNo());
						refund.setTotalAmount(info.getTotalAmount());
						refund.setPayer(info.getPayer());
						refund.setPayerIdcard(info.getPayerIdcard());
						refund.setPayWay(info.getPayWay());
						refund.setStatus("P");
						if(StringUtils.isNotEmpty(userId)){
							refund.setCreateBy(person.getPersonId());
						}else{
							refund.setCreateBy(memberLogin.getLoginId());
						}
						refund.setCreateDate(DateUtils.getNowDate());
						//服务费
						refund.setRefundType(1);
						refund.setPaymentMerid(wxGuaranteeFee.getMerchantNo());
						refundService.insert(refund);
					}
				}else{
					if(info!=null&&info.getPayWay().equals("2") &&info.getStatus().equals("I")){
						BusMessageReminder reminder = new BusMessageReminder();
						reminder.setType(Constants.WEI_XIN_SERVICE_PAY);
						reminder.setIntoPieceId(intoPieceId);
						reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
						BusMessageReminder m = messageReminderService.queryMRByTypeAndDelete(reminder);
						if(m!=null){
							m.setIsDelete(new Short("1"));
							messageReminderService.updateByPrimaryKey(m);
						}
					}
					info.setStatus("GL");
					guaranteeFeeInfoService.updateByPrimaryKey(info);
					JSONObject node1 = flowMgrImpl.getNextTask(entry);
					if(node1 != null && node1.getIntValue("code") == 200){
						JSONArray arr = node1.getJSONArray("nodes");
						if(arr != null){
							String nextNodeId = arr.getJSONObject(0).getString("nodeId");
							String pcId = node1.getString("pcId");
							BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
							flowMgrImpl.next(business, nextNodeId, pcId, person.getPersonId());
						}	
					}
				}
				//线下或暂不支付
			}else{
				JSONObject node1 = flowMgrImpl.getNextTask(entry);
				if(node1 != null && node1.getIntValue("code") == 200){
					JSONArray arr = node1.getJSONArray("nodes");
					if(arr != null){
						String nextNodeId = arr.getJSONObject(0).getString("nodeId");
						String pcId = node1.getString("pcId");
						BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
						flowMgrImpl.next(business, nextNodeId, pcId, person.getPersonId());
					}else{
						retJson.put("message", "未配置流程节点");
						response.setStatus(400);
						return retJson;
					}	
				}else{
					retJson.put("message", "未配置流程节点");
					response.setStatus(400);
					return retJson;
				}
			}
			
			if(flag){
				List<BusGuaranteeReverse> list = guaranteeReverseService.selectByIntoPieceId(intoPieceId);
				if(list!=null&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						BusGuaranteeReverse guaranteeReverse = list.get(i);
						if("S".equals(guaranteeReverse.getStatus()) ){
							//银行卡支付退款
							if("1".equals(guaranteeReverse.getPayWay()) ){
								
								//微信支付退款
							}else if("2".equals(guaranteeReverse.getPayWay()) ){
								ApiWXGuaranteeFee wxGuaranteeFee = new ApiWXGuaranteeFee();
								wxGuaranteeFee.setIntoPieceId(intoPieceId);
								wxGuaranteeFee.setStatus("S");
								wxGuaranteeFee= wxGuaranteefeeService.selectByIpIdAndStatus(wxGuaranteeFee);
								String url = pieceConfig.getWxRefundCheck();
								SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
								String signature = user.getUserId() + user.getUsername() + user.getPassword();
								Map<String, String> map = new HashMap<String, String>();
								map.put("userid", user.getUserId());
								map.put("signature", DigestUtils.md5Hex(signature));
								map.put("companyType", "HLJSX");
								map.put("amount", guaranteeReverse.getTotalAmount().toString());
//								map.put("certificateNo", person.getCardNo());
								map.put("certificateNo", guaranteeReverse.getPayerIdcard());
								map.put("accountName", guaranteeReverse.getPayer());
								map.put("mobileNo", guaranteeReverse.getPayerMobile());
								map.put("intoPieceId", intoPieceId);
								map.put("refundId", guaranteeReverse.getId());
								map.put("merchantNo", wxGuaranteeFee.getMerchantNo());
								System.err.println(map);
								String retMap = HttpClientUtil.doPost(url, map,
										 "utf-8");
								System.err.println("退款申请返回数据"+retMap);
								if(retMap!=null){
									JSONObject retMap1 = JSONObject.parseObject(retMap);
									if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
										retJson.put("msg", "微信申请退款失败");
										response.setStatus(400);
										return retJson;
									}
									if(!StringUtils.isEmpty(retMap1.getString("refundId"))&&!StringUtils.isEmpty(retMap1.getString("status"))){
										if(retMap1.getString("refundId").equals(guaranteeReverse.getId())){
											guaranteeReverse.setStatus("R"+retMap1.getString("status"));
											guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
										}else{
											retJson.put("msg", "微信退款标识错误");
											response.setStatus(400);
											return retJson;
										}
									}else{
										retJson.put("msg", "微信申请退款失败");
										response.setStatus(400);
										return retJson;
									}
								}else{
									retJson.put("msg", "未接收到微信回应");
									response.setStatus(400);
									return retJson;
								}
							}else if(guaranteeReverse.getPayWay().equals("3")){
								guaranteeReverse.setStatus("RS");
								guaranteeReverse.setUpdateDate(DateUtils.getNowDate());
								guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
								//暂不支付
							}else if(guaranteeReverse.getPayWay().equals("4")){
								guaranteeReverse.setStatus("RS");
								guaranteeReverse.setUpdateDate(DateUtils.getNowDate());
								guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
							}
						}else{
							BusMessageReminder reminder = new BusMessageReminder();
							reminder.setIntoPieceId(guaranteeReverse.getIntoPieceId());
							reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
							//客户
							if(guaranteeReverse.getType().equals(1)){
								reminder.setType(Constants.MGUATANTEE_REVERSE);
								//站长
							}else{
								reminder.setType(Constants.UGUATANTEE_REVERSE);
							}
							BusMessageReminder message = messageReminderService.queryMRByTypeAndDelete(reminder);
							if(message!=null){
								message.setIsDelete(Constants.COMMON_IS_DELETE);
								messageReminderService.updateByPrimaryKey(message);
							}
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络错误");
			response.setStatus(400);
			return retJson;
		}
		retJson.put("message", "申请成功");
		response.setStatus(200);
		return retJson;
	}

	
}
