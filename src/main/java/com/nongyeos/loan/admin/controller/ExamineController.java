package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusBankCard;
import com.nongyeos.loan.admin.entity.BusExamine;
import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.BusBankCardService;
import com.nongyeos.loan.admin.service.IBusIntopieceScoreService;
import com.nongyeos.loan.admin.service.IContactMakeService;
import com.nongyeos.loan.admin.service.IExamineService;
import com.nongyeos.loan.admin.service.IFamilyCapitalService;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeService;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanDetailService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.admin.service.ISignatorieService;
import com.nongyeos.loan.admin.service.ISmsTemplateService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.CnNumberUtils;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.base.util.WordUtils;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Controller
@RequestMapping("/examine")
public class ExamineController {
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IWebUserService userService;


	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IParaService paraService;
	
	@Autowired  
    private IParaGroupService paraGroupService;
	
	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private ILoanDetailService loanDetailService;
	
	@Autowired
	private IFamilySituationService familySituationService;
	
	@Autowired
	private IBusIntopieceScoreService intopieceScoreService;
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@Autowired
	private IFlowNodeService flowNodeService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IMediaService mediaService;
	
	@Autowired
	private IBusFinsService finsService;
	
	@Autowired
	private IPersonService personService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Autowired
	private IFamilyCapitalService familyCapitalService;// 家庭资产
	
	@Autowired
	private IGuaranteeFeeService guaranteeFeeService;
	
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
    private RootPointConfig rootPointConfig;
	
	@Autowired
	private IOrgService orgService;
	
	@Autowired
	private IContactMakeService contactMakeService;
	
	@Autowired
	private ISysSenoService sysSenoService;
	
	@Autowired
	private ISignatorieService signatorieService;
	
	@Autowired
	private ISmsTemplateService smsTemplateService;
	
	@Autowired
	private BusBankCardService bankCardService;
	
	@Autowired
	private NjProductService njproductService;
	
	@Autowired
	private NjProductOrderService njProductOrderService;
	
	@Autowired
	private IGuaranteeReverseService reverseService;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	/**
	 * 计算借款
	 * @return
	 */
	@RequestMapping("/calculationloan")
	@ResponseBody
	public JSONObject calculationLoan(Integer capital,Integer term,String product){
		JSONObject json =  new JSONObject();
		if(capital == null){
			json.put("code", 400);
			json.put("msg", "请填写授信额度");
			return json;
		}
		if(term == null){
			json.put("code", 400);
			json.put("msg", "请填写借款期限");
			return json;
		}
		if(StrUtils.isEmpty(product)){
			json.put("code", 400);
			json.put("msg", "请选择金融产品");
			return json;
		}
		try {
			BusProduct busProduct = productService.selectByProductId(product);
			if(busProduct!=null){
				Short serviceRateType = busProduct.getServiceRateType();
				
				Short monthRateType = busProduct.getMonthRateType();
				if(serviceRateType==null||monthRateType==null){
					json.put("code", 400);
					json.put("msg", "金融产品未选择利率类型");
					return json;
				}
				Short repaymentWay = busProduct.getRepaymentWay();
				Map<String, Object> map = null;
				BigDecimal serviceRate =null;
				BigDecimal monthRate =null;
				Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(new BigDecimal(capital), term, busProduct);
				//等额本息
				if(repaymentWay.equals(Constants.AVERAGE_CAPITAL_INTEREST)){
					if(serviceRateType.equals(Constants.YEAR)){
						serviceRate=busProduct.getServiceRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(term));
					}else{
						serviceRate=busProduct.getServiceRate().multiply(new BigDecimal(term));
					}
					if(monthRateType.equals(Constants.YEAR)){
						monthRate = busProduct.getMonthRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
					}else{
						monthRate = busProduct.getMonthRate();
					}
					map = GetRepaymentUtils.equalMonth(new BigDecimal(capital), term, monthRate,serviceRate , null, "all");
					json.put("firstRepayment", map.get("monthlyRepayment"));
					json.put("serviceFeeFirst", serviceFee.get("first"));
				//先息后本包含通用与惠农
				}else if(repaymentWay.equals(Constants.INTEREST_THEN_CAPITAL)||repaymentWay.equals(Constants.INTEREST_THEN_CAPITAL_HN)){
					if(monthRateType.equals(Constants.YEAR)){
						monthRate = busProduct.getMonthRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
					}else{
						monthRate = busProduct.getMonthRate();
					}
					map = GetRepaymentUtils.equalEndMonth(new BigDecimal(capital), term, monthRate, null, "all");
					json.put("firstRepayment", map.get("monthlyRepayment"));
					json.put("serviceFeeFirst", serviceFee.get("first"));
				//按季度还息
				}else if(repaymentWay.equals(Constants.QUARTERLY_REPAYMENT)){
					if(term%3!=0){
						json.put("code", 400);
						json.put("msg", "借款期限不是整季度");
						return json;
					}
					if(serviceRateType.equals(Constants.YEAR)){
						serviceRate=busProduct.getServiceRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(term));
					}else{
						serviceRate=busProduct.getServiceRate().multiply(new BigDecimal(term));
					}
					if(monthRateType.equals(Constants.YEAR)){
						monthRate = busProduct.getMonthRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
					}else{
						monthRate = busProduct.getMonthRate();
					}
					map = GetRepaymentUtils.quarterlyRepayment(new BigDecimal(capital), term, monthRate, serviceRate, null, "all");
					json.put("firstRepayment", map.get("monthlyRepayment"));
					json.put("serviceFeeFirst", serviceFee.get("first"));
				//一次还清本息（首信）
				}else if(repaymentWay.equals(Constants.SHOUXIN_YEARS_REPAYMENT)){
					if(term%12!=0){
						json.put("code", 400);
						json.put("msg", "借款期限不是整年");
						return json;
					}
					Integer year = term/12;
					BigDecimal yearRate = null;
					if(serviceRateType.equals(Constants.YEAR)){
						serviceRate=busProduct.getServiceRate();
					}else{
						serviceRate=busProduct.getServiceRate().multiply(new BigDecimal("12"));
					}
					if(monthRateType.equals(Constants.YEAR)){
						yearRate = busProduct.getMonthRate();
					}else{
						yearRate = busProduct.getMonthRate().multiply(new BigDecimal("12"));
					}
					map = GetRepaymentUtils.shouXinYearsPayBack(new BigDecimal(capital), year, yearRate, serviceRate, null, "all");
					json.put("firstRepayment", map.get("monthlyRepayment"));
					json.put("serviceFeeFirst", serviceFee.get("first"));
					//等额本息（惠农）
				}else if(repaymentWay.equals(Constants.AVERAGE_CAPITAL_INTEREST_HN)){
					if(serviceRateType.equals(Constants.YEAR)){
						serviceRate=busProduct.getServiceRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(term));
					}else{
						serviceRate=busProduct.getServiceRate().multiply(new BigDecimal(term));
					}
					if(monthRateType.equals(Constants.YEAR)){
						monthRate = busProduct.getMonthRate().divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
					}else{
						monthRate = busProduct.getMonthRate();
					}
					map = GetRepaymentUtils.equalMonthHN(new BigDecimal(capital), term, monthRate, serviceRate, null, "all");
					//Integer capital,Integer term,String product
					
					if(busProduct.getBorrowWay().equals(1)){
						json.put("firstRepayment", map.get("monthlyRepaymentWithService"));
						json.put("serviceFeeFirst", serviceFee.get("first"));
					}else{
						json.put("firstRepayment", map.get("monthlyRepayment"));
						json.put("serviceFeeFirst", serviceFee.get("first"));
					}
				}else{
					json.put("code", 400);
					json.put("msg", "暂无该借款产品，请等待系统更新");
					return json;
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "计算出错了");
			return json;
		}
		json.put("code", 200);
		return json;
	}
	
	/**
	 * 获取评分授信历史
	 * @return
	 */
	@RequestMapping("/historyscore")
	@ResponseBody
	public JSONObject historyScore(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");
			return json;
		}
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			json.put("money", ip.getCreditCapital() == null ? "" : ip.getCreditCapital().toString());
			json.put("history", intopieceScoreService.historyScore(id));
			json.put("code", 200);
		} catch (Exception e) {
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 初审列表页面
	 * @return
	 */
	@RequestMapping("/primary")
	public String primary(){
		return "intoPiece/primary";
	}
	
	/**
	 * 初审表单页面
	 * @return
	 */
	@RequestMapping("/primaryform")
	public ModelAndView primaryForm(String intoPieceId){
		ModelAndView mv = new ModelAndView();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			mv.addObject("productList", productService.selectListByFinsId(ip.getLenderId()));
			mv.addObject("DATA_VERIFY", paraGroupService.getSelectOption("DATA_VERIFY", null));
			mv.addObject("trail_opinion", ip.getTrialOpinion());
			mv.addObject("id", intoPieceId);
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(ip.getId());
			fs.setCoBorrower(1);
			List<BusFamilySituation> family = familySituationService.queryCoBorrower(fs);
			StringBuffer name = new StringBuffer(ip.getMemberName());
			StringBuffer phone = new StringBuffer(ip.getPhone());
			StringBuffer idCard = new StringBuffer(ip.getIdCard());
			for (int i = 0; i < family.size(); i++) {
				name.append(" ");
				name.append(family.get(i).getName());
				phone.append(" ");
				phone.append(family.get(i).getPhone());
				idCard.append(" ");
				idCard.append(family.get(i).getIdCard());
			}
//			//计算农资首信额度
//			AppApplication app = applicationService.selectByModelId(ip.getModelId());
//			BusFamilyCapital fc = new BusFamilyCapital();
//			fc.setIntoPieceId(intoPieceId);
//			fc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
//			BusFamilyCapital familyCapital = familyCapitalService.selectByIntoPieceId(fc);
//			String expressionString = app.getShortEname();
//			String creditCapital ="0";
//			if(StringUtils.isNoneEmpty(expressionString)&&expressionString.contains("=")){
//				Integer term = ip.getTerm();
//				Integer year =  term/12;
//				String[] split = expressionString.split("=");
//				expressionString=split[1];
//				expressionString = expressionString.replaceAll("DL", familyCapital.getDryFarmlandRegistered());
//				expressionString = expressionString.replaceAll("WL", familyCapital.getWaterFarmlandRegistered());
//				expressionString = expressionString.replaceAll("T", year.toString());
//				JexlEngine je = new JexlBuilder().create();
//				JexlExpression createExpression = je.createExpression(expressionString);
//				Object creditCapital1 =  createExpression.evaluate(null);
//				creditCapital=creditCapital1.toString();
//				BigDecimal bd = new BigDecimal(creditCapital);
//				bd=bd.divide(new BigDecimal("1000"), 0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal("1000"));
//				creditCapital=bd.toString();
//				if(bd.compareTo(new BigDecimal(50000))==1){
//					creditCapital="50000";
//				}
//			}
//			mv.addObject("creditCapital", creditCapital);
			if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
				mv.addObject("applyType", ip.getType());
			}else{
				if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
					List<NjProduct> njproList = njproductService.queryAllNjProduct();
					List<NjProductOrder> njproorderList = njProductOrderService.queryAllOnNjProductOrders();
					JSONArray arr = new JSONArray();
					if(njproList!=null&&njproList.size()>0){
						for (int i = 0; i < njproList.size(); i++) {
							NjProduct njProduct = njproList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProduct.getId());
							obj.put("productName", njProduct.getProductName());
							obj.put("price", njProduct.getPrice());
							obj.put("type", Constants.PRODUCT);
							arr.add(obj);
						}
					}
					if(njproorderList!=null&&njproorderList.size()>0){
						for (int i = 0; i < njproorderList.size(); i++) {
							NjProductOrder njProductOrder = njproorderList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProductOrder.getId());
							obj.put("productName", njProductOrder.getOrderName());
							obj.put("price", njProductOrder.getOrderPrice());
							obj.put("type", Constants.ORDER);
							arr.add(obj);
						}
					}
					mv.addObject("productListp", arr);
					if(StringUtils.isEmpty(ip.getProductListInfo())){
						mv.addObject("productmel", new JSONArray());
					}else{
						JSONArray parseArray = JSONArray.parseArray(ip.getProductListInfo()); 
						mv.addObject("productmel", parseArray);
					}
				}
				mv.addObject("applyType", ip.getType());
				
			}
			mv.addObject("name", name.toString());
			mv.addObject("phone", phone.toString());
			mv.addObject("idCard", idCard.toString()); 
			mv.addObject("address", ip.getAddress());
			mv.addObject("capital", ip.getCapital().setScale(0).toString());
			mv.addObject("recieveNongZi", ip.getRecieveNongZi());
			mv.addObject("recieveCash", ip.getRecieveCash());
			mv.addObject("term", ip.getTerm());
			mv.addObject("product", ip.getProduct());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("intoPiece/primaryform");
		return mv;
	}
	
	@RequestMapping("/productsByFins")
	@ResponseBody
	public JSONObject productsByFins(HttpServletRequest request,String lenderId){
		JSONObject retJson = new JSONObject();
		try {
			if(StringUtils.isEmpty(lenderId)){
				retJson.put("msg", "请先选择金融机构");
				retJson.put("errono", 3000);
				return retJson;
			}
			String products = productService.selectByFinsId(lenderId);
			retJson.put("result", products);
			retJson.put("errono", 2000);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("result", e.getMessage());
			retJson.put("errono", 3000);
		}
		return retJson;
	}
	
	/**
	 * 初审表单保存
	 * @return
	 */
	@RequestMapping("/primaryformsave")
	@ResponseBody
	public JSONObject primaryFormSave(HttpServletRequest request,String nextNodeId,String pcId,String id,BigDecimal capital,Integer term,String product,String opinion,String productListInfo,
			String recieveNongZi,String recieveCash,String type){
		JSONObject json = new JSONObject();
		try {
			if(StringUtils.isEmpty(opinion)){
				json.put("msg", "请填写审核意见");
				json.put("code", 400);
				return json;
			}
			String memberInfoChecking = request.getParameter("memberInfoChecking");
			/*String familyRecordSummary = request.getParameter("familyRecordSummary");
			String assetChecking = request.getParameter("assetChecking");
			String asset = request.getParameter("asset");*/
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			if(StrUtils.isNotEmpty(memberInfoChecking)){
				ip.setMemberInfoChecking(Integer.parseInt(memberInfoChecking));
			}
			BusExamine be = new BusExamine();
			FlowNode node = flowNodeService.selectByNodeId(nextNodeId);
			//保存农资信息
			if(node!=null&&!node.getEname().equals(Constants.FLOW_REFUSE)){
				if("1".equals(request.getParameter("nodeType")) ){
					if(ip.getType()!=null){
						if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
							JSONArray array = JSONArray.parseArray(productListInfo);
							BigDecimal recieveNongZif = new BigDecimal("0");
							BigDecimal nongzisettlef = new BigDecimal("0");
							for (int i = 0; i < array.size(); i++) {
								JSONObject proorder = array.getJSONObject(i);
								String productTime = proorder.getString("productTime");
								if(StringUtils.isNotEmpty(productTime)){
									Date date = DateUtils.parse(productTime, "yyyy-MM-dd");
									if(date.before(new Date())){
										json.put("msg", "预计使用时间不能在今天之前！");
										json.put("code", 400);
										return json;
									}
								}
								String productId = proorder.getString("productId");
								String productNum = proorder.getString("productNum");
								//商品或套餐
								NjProduct productp = njproductService.queryNjProductByPrimaryKey(productId);
								if(productp==null){
									NjProductOrder productOrder =njProductOrderService.queryProductOrderByPK(productId);
									recieveNongZif=recieveNongZif.add(new BigDecimal(productOrder.getOrderPrice()).multiply(new BigDecimal(productNum)));
									nongzisettlef=nongzisettlef.add(new BigDecimal(productOrder.getOrderSettleprice()).multiply(new BigDecimal(productNum)));
								}else{
									recieveNongZif=recieveNongZif.add(new BigDecimal(productp.getPrice()).multiply(new BigDecimal(productNum)));
									nongzisettlef=nongzisettlef.add(new BigDecimal(productp.getSettlePrice()).multiply(new BigDecimal(productNum)));
								}
								
							}
							
							int compareNongZi = recieveNongZif.compareTo(new BigDecimal(recieveNongZi));
							if(compareNongZi!=0){
								json.put("msg", "计算错误，请重新提交");
								json.put("code", 400);
								return json;
							}else{
								be.setRecieveNongZi(recieveNongZif.toString());
								be.setRecieveCash(recieveCash);
								be.setProductListInfo(productListInfo);
								be.setProductPrice(recieveNongZif.toString());
								be.setProductSettlePrice(nongzisettlef.toString());
							}
						}
						
					}
				}
			}
			
			/*ip.setFamilyRecordSummary(familyRecordSummary);
			ip.setAssetChecking(assetChecking);
			ip.setAsset(asset);*/
			
			be.setId(UUIDUtil.getUUID());
			be.setIntoPieceId(id);
			be.setTerm(term);
			be.setNode(Constants.FLOW_FIRST_TRIAL);
			be.setProductId(product);
			be.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			be.setExamineDate(new Date());
			be.setExamineOpinion(opinion);
			be.setExamineName(request.getSession().getAttribute("personName").toString());
			be.setCapital(capital);
			ip.setProduct(product);
			intoPieceService.updateByPrimaryKey(ip);
			json = examineService.primaryFormSave(ip, be, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, id, nextNodeId, request);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getLocalizedMessage());
			return json;
		}		
	}
	
	/**
	 * 复审列表页面
	 * @return
	 */
	@RequestMapping("/review")
	public String review(){
		return "intoPiece/review";
	}
	
	/**
	 * 复审表单页面
	 * @return
	 */
	@RequestMapping("/reviewform")
	public ModelAndView reviewForm(String intoPieceId){
		ModelAndView mv = new ModelAndView();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			mv.addObject("productList", productService.selectListByFinsId(ip.getLenderId()));
			mv.addObject("opinionList", examineService.historyByIpId(intoPieceId));
			mv.addObject("trail_opinion", ip.getTrialOpinion());
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			//根据上一个节点所审核的信息查询出商品信息及到手现金和到手农资
			FlowNode node = flowNodeService.selectByNodeId(entry.getRecordId());
			mv.addObject("id", intoPieceId);
			BusExamine e = new BusExamine();
			e.setIntoPieceId(intoPieceId);
			e.setNode(node.getEname());
			BusExamine data = examineService.selectByIpIdNode(e);
			if(data != null){
				if(data.getCapital() != null)
					mv.addObject("capital", data.getCapital().intValue()+"");
				if(data.getTerm() != null)
					mv.addObject("term", data.getTerm().toString());
				if(data.getProductId() != null)
					mv.addObject("product", data.getProductId());
			}
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(ip.getId());
			fs.setCoBorrower(1);
			List<BusFamilySituation> family = familySituationService.queryCoBorrower(fs);
			StringBuffer name = new StringBuffer(ip.getMemberName());
			StringBuffer phone = new StringBuffer(ip.getPhone());
			StringBuffer idCard = new StringBuffer(ip.getIdCard());
			for (int i = 0; i < family.size(); i++) {
				name.append(" ");
				name.append(family.get(i).getName());
				phone.append(" ");
				phone.append(family.get(i).getPhone());
				idCard.append(" ");
				idCard.append(family.get(i).getIdCard());
			}
			
			if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
				mv.addObject("applyType", ip.getType());
			}else{
				if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
					List<NjProduct> njproList = njproductService.queryAllNjProduct();
					List<NjProductOrder> njproorderList = njProductOrderService.queryAllOnNjProductOrders();
					JSONArray arr = new JSONArray();
					if(njproList!=null&&njproList.size()>0){
						for (int i = 0; i < njproList.size(); i++) {
							NjProduct njProduct = njproList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProduct.getId());
							obj.put("productName", njProduct.getProductName());
							obj.put("price", njProduct.getPrice());
							obj.put("type", Constants.PRODUCT);
							arr.add(obj);
						}
					}
					if(njproorderList!=null&&njproorderList.size()>0){
						for (int i = 0; i < njproorderList.size(); i++) {
							NjProductOrder njProductOrder = njproorderList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProductOrder.getId());
							obj.put("productName", njProductOrder.getOrderName());
							obj.put("price", njProductOrder.getOrderPrice());
							obj.put("type", Constants.ORDER);
							arr.add(obj);
						}
					}
					mv.addObject("productListp", arr);
					if(data != null){
						if(data.getRecieveCash()!=null)
							mv.addObject("recieveCash", data.getRecieveCash());
						if(data.getRecieveNongZi()!=null)
							mv.addObject("recieveNongZi", data.getRecieveNongZi());
						if(StringUtils.isEmpty(data.getProductListInfo())){
							mv.addObject("productmel", new JSONArray());
						}else{
							JSONArray parseArray = JSONArray.parseArray(data.getProductListInfo()); 
							mv.addObject("productmel", parseArray);
						}
					}
					
				}
				mv.addObject("applyType", ip.getType());
				
			}
			
			mv.addObject("name", name.toString());
			mv.addObject("phone", phone.toString());
			mv.addObject("idCard", idCard.toString());
			mv.addObject("address", ip.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("intoPiece/reviewform");
		return mv;
	}
	
	/**
	 * 复审表单保存
	 * @return
	 */
	@RequestMapping("/reviewformsave")
	@ResponseBody
	public JSONObject reviewFormSave(HttpServletRequest request,String nextNodeId,String pcId,String id,BigDecimal capital,Integer term,String product,String opinion,String productListInfo,
			String recieveNongZi,String recieveCash,String type) throws Exception{
		JSONObject json = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			BusExamine be = new BusExamine();
			FlowNode node = flowNodeService.selectByNodeId(nextNodeId);
			//保存农资信息
			if(node!=null&&!node.getEname().equals(Constants.FLOW_REFUSE)){
				if(ip.getType()!=null){
					if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
						JSONArray array = JSONArray.parseArray(productListInfo);
						BigDecimal recieveNongZif = new BigDecimal("0");
						BigDecimal nongzisettlef = new BigDecimal("0");
						for (int i = 0; i < array.size(); i++) {
							JSONObject proorder = array.getJSONObject(i);
							String productTime = proorder.getString("productTime");
							if(StringUtils.isNotEmpty(productTime)){
								Date date = DateUtils.parse(productTime, "yyyy-MM-dd");
								if(date.before(new Date())){
									json.put("msg", "预计使用时间不能在今天之前！");
									json.put("code", 400);
									return json;
								}
							}
							String productId = proorder.getString("productId");
							String productNum = proorder.getString("productNum");
							//商品或套餐
							NjProduct productp = njproductService.queryNjProductByPrimaryKey(productId);
							if(productp==null){
								NjProductOrder productOrder =njProductOrderService.queryProductOrderByPK(productId);
								recieveNongZif=recieveNongZif.add(new BigDecimal(productOrder.getOrderPrice()).multiply(new BigDecimal(productNum)));
								nongzisettlef=nongzisettlef.add(new BigDecimal(productOrder.getOrderSettleprice()).multiply(new BigDecimal(productNum)));
							}else{
								recieveNongZif=recieveNongZif.add(new BigDecimal(productp.getPrice()).multiply(new BigDecimal(productNum)));
								nongzisettlef=nongzisettlef.add(new BigDecimal(productp.getSettlePrice()).multiply(new BigDecimal(productNum)));
							}
							
						}
						
						int compareNongZi = recieveNongZif.compareTo(new BigDecimal(recieveNongZi));
						if(compareNongZi!=0){
							json.put("msg", "计算错误，请重新提交");
							json.put("code", 400);
							return json;
						}else{
							be.setRecieveNongZi(recieveNongZif.toString());
							be.setRecieveCash(recieveCash);
							be.setProductListInfo(productListInfo);
							be.setProductPrice(recieveNongZif.toString());
							be.setProductSettlePrice(nongzisettlef.toString());
						}
					}
					
				}	
			}
			
			be.setId(UUIDUtil.getUUID());
			be.setIntoPieceId(id);
			be.setTerm(term);
			be.setNode(Constants.FLOW_SECOND_TRIAL);
			be.setProductId(product);
			be.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			be.setExamineDate(new Date());
			be.setExamineOpinion(opinion);
			be.setExamineName(request.getSession().getAttribute("personName").toString());
			be.setCapital(capital);
			ip.setProduct(product);
			intoPieceService.updateByPrimaryKey(ip);
			json = examineService.reviewFormSave(intoPieceService.selectByPrimaryKey(id),be,nextNodeId,pcId,request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, id, nextNodeId, request);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "400");
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 保函出具列表页面
	 * @return
	 */
	@RequestMapping("/guarantee")
	public String guarantee(){
		return "intoPiece/guarantee";
	}
	
	/**
	 * 保函出具表单页面
	 * @return
	 */
	@RequestMapping("/guaranteeform")
	public ModelAndView guaranteeForm(String intoPieceId){
		ModelAndView mv = new ModelAndView();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			mv.addObject("productList", productService.selectListByFinsId(ip.getLenderId()));
			mv.addObject("opinionList", examineService.historyByIpId(intoPieceId));
			mv.addObject("trail_opinion", ip.getTrialOpinion());
			mv.addObject("id", intoPieceId);
			BusExamine e = new BusExamine();
			e.setIntoPieceId(intoPieceId);
			e.setNode(Constants.FLOW_SECOND_TRIAL);
			BusExamine data = examineService.selectByIpIdNode(e);
			if(data != null){
				if(data.getCapital() != null)
					mv.addObject("capital", data.getCapital().intValue()+"");
				if(data.getTerm() != null)
					mv.addObject("term", data.getTerm().toString());
				if(data.getProductId() != null)
					mv.addObject("product", data.getProductId());
			}
			BusProduct p = productService.selectByProductId(ip.getProduct());
			Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(data.getCapital(), data.getTerm(), p);
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(ip.getId());
			fs.setCoBorrower(1);
			List<BusFamilySituation> family = familySituationService.queryCoBorrower(fs);
			StringBuffer name = new StringBuffer(ip.getMemberName());
			StringBuffer phone = new StringBuffer(ip.getPhone());
			StringBuffer idCard = new StringBuffer(ip.getIdCard());
			for (int i = 0; i < family.size(); i++) {
				name.append(" ");
				name.append(family.get(i).getName());
				phone.append(" ");
				phone.append(family.get(i).getPhone());
				idCard.append(" ");
				idCard.append(family.get(i).getIdCard());
			}
			if(ip.getServiceFeeWay()!=null){
				mv.addObject("serviceFeeWay", ip.getServiceFeeWay());
			}else{
				mv.addObject("serviceFeeWay", "");
			}
			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
			if(guaranteeFeeInfo==null){
				if(ip.getServiceFeeWay()==null){
					mv.addObject("serviceFee", serviceFee.get("first"));
				}else{
					mv.addObject("serviceFee", "0");
				}
			}else{
				mv.addObject("serviceFee", guaranteeFeeInfo.getTotalAmount());
			}

			if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
				mv.addObject("applyType", ip.getType());
			}else{
				if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
					List<NjProduct> njproList = njproductService.queryAllNjProduct();
					List<NjProductOrder> njproorderList = njProductOrderService.queryAllOnNjProductOrders();
					JSONArray arr = new JSONArray();
					if(njproList!=null&&njproList.size()>0){
						for (int i = 0; i < njproList.size(); i++) {
							NjProduct njProduct = njproList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProduct.getId());
							obj.put("productName", njProduct.getProductName());
							obj.put("price", njProduct.getPrice());
							obj.put("type", Constants.PRODUCT);
							arr.add(obj);
						}
					}
					if(njproorderList!=null&&njproorderList.size()>0){
						for (int i = 0; i < njproorderList.size(); i++) {
							NjProductOrder njProductOrder = njproorderList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProductOrder.getId());
							obj.put("productName", njProductOrder.getOrderName());
							obj.put("price", njProductOrder.getOrderPrice());
							obj.put("type", Constants.ORDER);
							arr.add(obj);
						}
					}
					mv.addObject("productListp", arr);
					if(data != null){
						if(data.getRecieveCash()!=null)
							mv.addObject("recieveCash", data.getRecieveCash());
						if(data.getRecieveNongZi()!=null)
							mv.addObject("recieveNongZi", data.getRecieveNongZi());
						if(StringUtils.isEmpty(data.getProductListInfo())){
							mv.addObject("productmel", new JSONArray());
						}else{
							JSONArray parseArray = JSONArray.parseArray(data.getProductListInfo()); 
							mv.addObject("productmel", parseArray);
						}
					}
					
				}
				mv.addObject("applyType", ip.getType());
				
			}
			
			mv.addObject("name", name.toString());
			mv.addObject("phone", phone.toString());
			mv.addObject("idCard", idCard.toString());
			mv.addObject("address", ip.getAddress());
			BusExamine be = new BusExamine();
			be.setIntoPieceId(intoPieceId);
			be.setNode(Constants.FLOW_THIRD_TRIAL);
			if(examineService.selectByIpIdNode(be) == null){
				mv.addObject("showButton", 1);
			}else{
				mv.addObject("showButton", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("intoPiece/guaranteeform");
		return mv;
	}
	
	/**
	 * 保函出具表单保存
	 * @return
	 */
	@RequestMapping("/guaranteeformsave")
	@ResponseBody
	public JSONObject guaranteeFormSave(HttpServletRequest request,String id,BigDecimal capital,Integer term,String product,String opinion,String serviceFee,String serviceFeeWay,String productListInfo,
			String recieveNongZi,String recieveCash,String type) throws Exception{
		JSONObject json = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			String channel = ip.getChannel();
			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(id);
			BusGuaranteeFee guaranteeFee = guaranteeFeeService.selectByIntopieceId(id);
			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			SysPerson person = personService.selectByPersonId(ip.getOperUserId());
			//站长代扣
			if(StringUtils.isNotEmpty(serviceFeeWay)&&serviceFeeWay.equals("1")){
				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
				feeInfo.setId(UUIDUtil.getUUID());
				feeInfo.setOrgId(org.getOrgId());
				feeInfo.setOrgName(org.getFullCname());
				feeInfo.setIntoPieceId(ip.getId());
				feeInfo.setUse(ip.getUse());
				feeInfo.setAccountName(ip.getMemberName());
				feeInfo.setCertificateNo(ip.getIdCard());
				//银行卡改成申请金额
				feeInfo.setAccountNo(ip.getCapital().toString());
				feeInfo.setPayerIdcard(org.getIdCard());
				feeInfo.setPayer(org.getLeader());
				feeInfo.setPayWay("1");
				feeInfo.setMobileNo(ip.getPhone());
				feeInfo.setTotalAmount(new BigDecimal(serviceFee));
				feeInfo.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
				feeInfo.setCreateDate(DateUtils.getNowDate());
				if(guaranteeFee==null&&!ip.getMemberName().contains("测试")&&!ip.getMemberName().contains("TEST")){
					SysWebUser user =
							 userService.selectUserByUserName(pieceConfig.getUserName());
							 Map<String, String> map1 = new HashMap<String, String>();
							 map1.put("amount", serviceFee);
							 map1.put("certificateNo", org.getIdCard());
							 map1.put("accountNo", org.getCardNo());
							 map1.put("accountName", org.getLeader());
							 map1.put("mobileNo", org.getPhone());
							 map1.put("intoPieceId", id);
							 map1.put("userid", user.getUserId());
							 map1.put("companyType", channel);
							 String signature = user.getUserId() + user.getUsername() +
							 user.getPassword();
							 map1.put("signature", DigestUtils.md5Hex(signature));
							 String retMap =
							 HttpClientUtil.doPost(pieceConfig.getXfGuaranteeFeeUrl(), map1,
							 "utf-8");
							 System.err.println(retMap);
							 if(retMap!=null){
								 JSONObject retMap1 = JSONObject.parseObject(retMap);
								 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
									 json.put("code", "400");
									 json.put("msg", "代扣服务费失败，请联系管理员！");
									 feeInfo.setStatus("F");
									 guaranteeFeeInfoService.insert(feeInfo);
									 return json;
								 }
							 }else{
								 json.put("code", "400");
								 json.put("msg", "代扣服务费失败，请联系管理员！");
								 feeInfo.setStatus("F");
								 return json;
							 }
				}
				guaranteeFeeInfoService.insert(feeInfo);
				//微信支付
			}else if(StringUtils.isNotEmpty(serviceFeeWay)&&serviceFeeWay.equals("2")&&guaranteeFeeInfo==null){
				messageReminderService.saveweixinpay(Constants.WEI_XIN_SERVICE_PAY,channel,id,serviceFee);
				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
				feeInfo.setId(UUIDUtil.getUUID());
				feeInfo.setOrgId(org.getOrgId());
				feeInfo.setOrgName(org.getFullCname());
				feeInfo.setIntoPieceId(ip.getId());
				feeInfo.setUse(ip.getUse());
				feeInfo.setAccountName(ip.getMemberName());
				feeInfo.setCertificateNo(ip.getIdCard());
				//银行卡改成申请金额
				feeInfo.setAccountNo(ip.getCapital().toString());
				feeInfo.setPayerIdcard(person.getCardNo());
				feeInfo.setMobileNo(ip.getPhone());
				feeInfo.setPayer(person.getNameCn());
				feeInfo.setPayWay("2");
				feeInfo.setTotalAmount(new BigDecimal(serviceFee));
				feeInfo.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
				feeInfo.setCreateDate(DateUtils.getNowDate());
				feeInfo.setStatus("I");
				guaranteeFeeInfoService.insert(feeInfo);
				//黑龙江首信暂不支付
			}else if(StringUtils.isNotEmpty(serviceFeeWay)&&serviceFeeWay.equals("3")&&guaranteeFeeInfo==null){
				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
				feeInfo.setId(UUIDUtil.getUUID());
				feeInfo.setOrgId(org.getOrgId());
				feeInfo.setOrgName(org.getFullCname());
				feeInfo.setIntoPieceId(ip.getId());
				feeInfo.setUse(ip.getUse());
				feeInfo.setAccountName(ip.getMemberName());
				feeInfo.setCertificateNo(ip.getIdCard());
				//银行卡改成申请金额
				feeInfo.setAccountNo(ip.getCapital().toString());
				feeInfo.setMobileNo(ip.getPhone());
				feeInfo.setPayerIdcard(org.getIdCard());
				feeInfo.setPayer(org.getLeader());
				feeInfo.setPayWay("3");
				feeInfo.setTotalAmount(new BigDecimal(serviceFee));
				feeInfo.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
				feeInfo.setCreateDate(DateUtils.getNowDate());
				feeInfo.setStatus("S");
				guaranteeFeeInfoService.insert(feeInfo);
			}else if(StringUtils.isNotEmpty(serviceFeeWay)&&serviceFeeWay.equals("5")&&guaranteeFeeInfo==null){
				messageReminderService.saveweixinpay(Constants.XIAO_CHENG_XU_SERVICE_PAY,channel,id,serviceFee);
				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
				feeInfo.setId(UUIDUtil.getUUID());
				feeInfo.setOrgId(org.getOrgId());
				feeInfo.setOrgName(org.getFullCname());
				feeInfo.setIntoPieceId(ip.getId());
				feeInfo.setUse(ip.getUse());
				feeInfo.setAccountName(ip.getMemberName());
				feeInfo.setCertificateNo(ip.getIdCard());
				//银行卡改成申请金额
				feeInfo.setAccountNo(ip.getCapital().toString());
				feeInfo.setPayerIdcard(person.getCardNo());
				feeInfo.setMobileNo(ip.getPhone());
				feeInfo.setPayer(person.getNameCn());
				feeInfo.setPayWay("5");
				feeInfo.setTotalAmount(new BigDecimal(serviceFee));
				feeInfo.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
				feeInfo.setCreateDate(DateUtils.getNowDate());
				feeInfo.setStatus("I");
				guaranteeFeeInfoService.insert(feeInfo);
			}
			//保存服务费扣除方式
			if(StringUtils.isNotEmpty(serviceFeeWay)){
				ip.setServiceFeeWay(new Integer(serviceFeeWay));
				intoPieceService.updateByPrimaryKey(ip);
			}
			BusProduct p = productService.selectByProductId(product);
			BusLoan loan = loanService.selectByIpId(id);
			if(loan == null){
				loan = new BusLoan();
				loan.setIntoPieceId(id);
				loan.setContractNo(sysSenoService.getNextString("contract_no", 10, "No", 1));
				loan.setChannel(ip.getChannel());
				loan.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				loan.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				loan.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				loan.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				loan.setCreDate(new Date());
				loan.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			}
			loan.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			loan.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
			loan.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
			loan.setUpdDate(new Date());
			loan.setCapital(capital);
			loan.setTerm(term);
			loan.setIntoPieceName(ip.getName());
			loan.setMemberId(ip.getMemberId());
			loan.setMemberName(ip.getMemberName());
			loan.setProductId(p.getProductId());
			loan.setRate(p.getMonthRate());
			loan.setOverdueRate(p.getOverdueDayRate());
			loan.setRepaymentType(p.getRepaymentWay().intValue());
			loan.setServiceFeeType(p.getBorrowWay().intValue());
			loan.setServiceRate(p.getServiceRate());
			Map<String, BigDecimal> serviceFee2 = GetRepaymentUtils.getServiceFee(capital, term, p);
			loan.setServiceFee(serviceFee2.get("first"));
			loan.setLenderId(ip.getLenderId());
								
			BusExamine be = new BusExamine();
				if(ip.getType()!=null){
					if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
						JSONArray array = JSONArray.parseArray(productListInfo);
						BigDecimal recieveNongZif = new BigDecimal("0");
						BigDecimal nongzisettlef = new BigDecimal("0");
						for (int i = 0; i < array.size(); i++) {
							JSONObject proorder = array.getJSONObject(i);
							String productTime = proorder.getString("productTime");
							if(StringUtils.isNotEmpty(productTime)){
								Date date = DateUtils.parse(productTime, "yyyy-MM-dd");
								if(date.before(new Date())){
									json.put("msg", "预计使用时间不能在今天之前！");
									json.put("code", 400);
									return json;
								}
							}
							String productId = proorder.getString("productId");
							String productNum = proorder.getString("productNum");
							//商品或套餐
							NjProduct productp = njproductService.queryNjProductByPrimaryKey(productId);
							if(productp==null){
								NjProductOrder productOrder =njProductOrderService.queryProductOrderByPK(productId);
								recieveNongZif=recieveNongZif.add(new BigDecimal(productOrder.getOrderPrice()).multiply(new BigDecimal(productNum)));
								nongzisettlef=nongzisettlef.add(new BigDecimal(productOrder.getOrderSettleprice()).multiply(new BigDecimal(productNum)));
							}else{
								recieveNongZif=recieveNongZif.add(new BigDecimal(productp.getPrice()).multiply(new BigDecimal(productNum)));
								nongzisettlef=nongzisettlef.add(new BigDecimal(productp.getSettlePrice()).multiply(new BigDecimal(productNum)));
							}
							
						}
						
						int compareNongZi = recieveNongZif.compareTo(new BigDecimal(recieveNongZi));
						if(compareNongZi!=0){
							json.put("msg", "计算错误，请重新提交");
							json.put("code", 400);
							return json;
						}else{
							be.setRecieveNongZi(recieveNongZif.toString());
							be.setRecieveCash(recieveCash);
							be.setProductListInfo(productListInfo);
							be.setProductPrice(recieveNongZif.toString());
							be.setProductSettlePrice(nongzisettlef.toString());
							loan.setRecieveNongZi(recieveNongZif.toString());
							loan.setRecieveCash(recieveCash);
							loan.setProductListInfo(productListInfo);
							loan.setProductPrice(recieveNongZif.toString());
							loan.setProductSettlePrice(nongzisettlef.toString());
							loan.setSignStatus(Constants.SIGN_STATUS1);
						}
					}
				}
			if(loan.getId() == null){
				loan.setId(UUIDUtil.getUUID());
				loanService.insert(loan);
			}else{
				loanService.updateByPrimaryKeySelective(loan);
			}	
			be.setId(UUIDUtil.getUUID());
			be.setIntoPieceId(id);
			be.setTerm(term);
			be.setNode(Constants.FLOW_THIRD_TRIAL);
			be.setProductId(product);
			be.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			be.setExamineDate(new Date());
			be.setExamineOpinion(opinion);
			be.setExamineName(request.getSession().getAttribute("personName").toString());
			be.setCapital(capital);
			ip.setProduct(product);
			intoPieceService.updateByPrimaryKey(ip);
			examineService.insert(be);		
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "400");
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 保函出具提交页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/guaranteepush")
	public ModelAndView guaranteePush(HttpServletRequest request, String id) throws Exception{
		ModelAndView mv = new ModelAndView();
		int wrong =0;
		try {
			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(id);
			BusLoan loan = loanService.selectByIpId(id);
			BusGuaranteeFeeInfo guaranteeFee = guaranteeFeeInfoService.selectByIntopieceId(id);
			contactMakeService.cnh(intoPiece, loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			contactMakeService.fwht(intoPiece, loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			contactMakeService.wtcg(intoPiece, loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
//			contactMakeService.tdlz(intoPiece, loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			contactMakeService.dyfdb(intoPiece, loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			contactMakeService.wxld(intoPiece, loan, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			loan.setSignStatus(Constants.SIGN_STATUS2);
			if(loan.getId() == null){
				loan.setId(UUIDUtil.getUUID());
				loanService.insert(loan);
			}else{
				loanService.updateByPrimaryKeySelective(loan);
			}
			if(intoPiece.getServiceFeeWay()==null){
				wrong =2;
			}else if(intoPiece.getServiceFeeWay()==1||intoPiece.getServiceFeeWay()==2){
				if(!intoPiece.getMemberName().contains("测试")&&!intoPiece.getMemberName().contains("TEST")){
					if(guaranteeFee==null||!guaranteeFee.getStatus().equals("S")){
						wrong =2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("code", "400");
			mv.addObject("msg", e.getMessage());
			return mv;
		}
		mv.addObject("wrong", wrong);
		mv.addObject("id", id);
		mv.setViewName("/intoPiece/examine");
		return mv;
	}
	
	/**
	 * 保函出具提交保存
	 * @return
	 */
	@RequestMapping("/guaranteepushsave")
	@ResponseBody
	public JSONObject guaranteePushSave(HttpServletRequest request,String id,String nextNodeId,String pcId){
		JSONObject json = new JSONObject();
		try {
			BusExamine be = new BusExamine();
			be.setIntoPieceId(id);
			be.setNode(Constants.FLOW_THIRD_TRIAL);
			if(examineService.selectByIpIdNode(be) == null){
				json.put("code", "400");
				json.put("msg", "请先审核再提交");
				return json;
			}			
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			json = examineService.guaranteePushSave(ip, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			String channel = ip.getChannel();
			BusLoan loan = loanService.selectByIpId(id);
			loan.setStartDate(DateUtils.getNow());
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, id, nextNodeId, request);
			
			String mark = rootPointConfig.getMark();
			JSONObject object = JSONObject.parseObject(mark);
			String root =null;
			if(channel!=null){
				root = object.getString(channel);
			}
			messageReminderService.saveServiceRemind(root,id);
			FlowNode nextNode = flowNodeService.selectByNodeId(nextNodeId);
			Map<String, String> map = new HashMap<String, String>();
			BusGuaranteeFeeInfo s = guaranteeFeeInfoService.selectByIntopieceId(id);
			if(s!=null&&s.getTotalAmount()!=null){
				map.put("CODE", s.getTotalAmount().toString());
				map.put("MEMBER", ip.getMemberName());
				/*if(nextNode.getEname().equals("5")&&"HLJSX".equals(channel)){
//					smsTemplateService.smsSend(Constants.U_YIXIANG_REMIND, root, map, person.getMobile());
					//农户短信提醒服务费支付
					smsTemplateService.smsSend(Constants.M_SERVICE_FEE_REMIND, root, map, ip.getPhone());
				}*/
				//如果下个节点为待放款(黑龙江首信)保存首页消息
			}
			if(nextNode.getEname().equals("8")){
				reverseService.savePreReverseMessage(id,request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "400");
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 文档页面
	 * @return
	 */
	@RequestMapping("/file")
	public ModelAndView file(String id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.setViewName("/intoPiece/file");
		return mv;
	}
	
	/**
	 * 终审列表页面
	 * @return
	 */
	@RequestMapping("/last")
	public String last(){
		return "intoPiece/last";
	}
	
	/**
	 * 终审表单页面
	 * @return
	 */
	@RequestMapping("/lastform")
	public ModelAndView lastForm(String intoPieceId){
		ModelAndView mv = new ModelAndView();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			mv.addObject("productList", productService.selectListByFinsId(ip.getLenderId()));
			mv.addObject("opinionList", examineService.historyByIpId(intoPieceId));
			mv.addObject("trail_opinion", ip.getTrialOpinion());
			mv.addObject("id", intoPieceId);
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			FlowNode node = flowNodeService.selectByNodeId(entry.getRecordId());
			BusExamine e = new BusExamine();
			e.setIntoPieceId(intoPieceId);
			e.setNode(node.getEname());
			BusExamine data = examineService.selectByIpIdNode(e);
			if(data != null){
				if(data.getCapital() != null)
					mv.addObject("capital", data.getCapital().intValue()+"");
				if(data.getTerm() != null)
					mv.addObject("term", data.getTerm().toString());
				if(data.getProductId() != null)
					mv.addObject("product", data.getProductId());
			}else{
				/*e.setNode(Constants.FLOW_THIRD_TRIAL);
				data = examineService.selectByIpIdNode(e);
				if(data != null){
					if(data.getCapital() != null)
						mv.addObject("capital", data.getCapital().intValue()+"");
					if(data.getTerm() != null)
						mv.addObject("term", data.getTerm().toString());
					if(data.getProductId() != null)
						mv.addObject("product", data.getProductId());
				}*/
				throw new Exception("未查询到历史审核信息");
			}
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(ip.getId());
			fs.setCoBorrower(1);
			List<BusFamilySituation> family = familySituationService.queryCoBorrower(fs);
			StringBuffer name = new StringBuffer(ip.getMemberName());
			StringBuffer phone = new StringBuffer(ip.getPhone());
			StringBuffer idCard = new StringBuffer(ip.getIdCard());
			for (int i = 0; i < family.size(); i++) {
				name.append(" ");
				name.append(family.get(i).getName());
				phone.append(" ");
				phone.append(family.get(i).getPhone());
				idCard.append(" ");
				idCard.append(family.get(i).getIdCard());
			}
			
			if(ip.getServiceFeeWay()!=null){
				mv.addObject("serviceFeeWay", ip.getServiceFeeWay());
			}else{
				mv.addObject("serviceFeeWay", "");
			}
			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
			BusProduct p = productService.selectByProductId(ip.getProduct());
			Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(data.getCapital(), data.getTerm(), p);
			if(guaranteeFeeInfo==null){
				if(ip.getServiceFeeWay()==null){
					mv.addObject("serviceFee", serviceFee.get("first"));
				}else{
					mv.addObject("serviceFee", "0");
				}
			}else{
				mv.addObject("serviceFee", guaranteeFeeInfo.getTotalAmount());
			}

			if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
				mv.addObject("applyType", ip.getType());
			}else{
				if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
					List<NjProduct> njproList = njproductService.queryAllNjProduct();
					List<NjProductOrder> njproorderList = njProductOrderService.queryAllOnNjProductOrders();
					JSONArray arr = new JSONArray();
					if(njproList!=null&&njproList.size()>0){
						for (int i = 0; i < njproList.size(); i++) {
							NjProduct njProduct = njproList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProduct.getId());
							obj.put("productName", njProduct.getProductName());
							obj.put("price", njProduct.getPrice());
							obj.put("type", Constants.PRODUCT);
							arr.add(obj);
						}
					}
					if(njproorderList!=null&&njproorderList.size()>0){
						for (int i = 0; i < njproorderList.size(); i++) {
							NjProductOrder njProductOrder = njproorderList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("id", njProductOrder.getId());
							obj.put("productName", njProductOrder.getOrderName());
							obj.put("price", njProductOrder.getOrderPrice());
							obj.put("type", Constants.ORDER);
							arr.add(obj);
						}
					}
					mv.addObject("productListp", arr);
					if(data != null){
						if(data.getRecieveCash()!=null)
							mv.addObject("recieveCash", data.getRecieveCash());
						if(data.getRecieveNongZi()!=null)
							mv.addObject("recieveNongZi", data.getRecieveNongZi());
						if(StringUtils.isEmpty(data.getProductListInfo())){
							mv.addObject("productmel", new JSONArray());
						}else{
							JSONArray parseArray = JSONArray.parseArray(data.getProductListInfo()); 
							mv.addObject("productmel", parseArray);
						}
					}
					
				}
				mv.addObject("applyType", ip.getType());
				
			}
			mv.addObject("name", name.toString());
			mv.addObject("phone", phone.toString());
			mv.addObject("idCard", idCard.toString());
			mv.addObject("address", ip.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("intoPiece/lastform");
		return mv;
	}
	
	/**
	 * 终审表单保存
	 * @return
	 */
	@RequestMapping("/lastformsave")
	@ResponseBody
	public JSONObject lastFormSave(HttpServletRequest request,String nodeType,String nextNodeId,String pcId,String id,String opinion) throws Exception{
		JSONObject json = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			
			BusExamine be = new BusExamine();
			be.setId(UUIDUtil.getUUID());
			be.setIntoPieceId(id);
			be.setTerm(ip.getTerm());
			be.setNode(Constants.FLOW_FORCH_TRIAL);
			be.setProductId(ip.getProduct());
			be.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			be.setExamineDate(new Date());
			be.setExamineOpinion(opinion);
			be.setExamineName(request.getSession().getAttribute("personName").toString());
			be.setCapital(ip.getCapital());
			
			AppEntry entry = appEntryService.queryByAppModeId(ip.getId());
			examineService.lastFormSave(ip, be, entry, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			FlowNode nextNode = flowNodeService.selectByNodeId(nextNodeId);
			if(nextNode.getEname().equals("8")){
				reverseService.savePreReverseMessage(id,request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			}
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "400");
			json.put("msg",e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("result")
	public ModelAndView result(String id){
		ModelAndView mv = new ModelAndView();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
			mv.addObject("productList", productService.selectListByFinsId(ip.getLenderId()));
			List<BusExamine> list = examineService.historyByIpId(id);
			mv.addObject("opinionList", list);
			mv.addObject("id", id);
			if(list != null){
				BusExamine data = list.get(list.size()-1);
				if(data.getCapital() != null)
					mv.addObject("capital", data.getCapital().intValue()+"");
				if(data.getTerm() != null)
					mv.addObject("term", data.getTerm().toString());
				if(data.getProductId() != null)
					mv.addObject("product", data.getProductId());
				BusProduct p = productService.selectByProductId(ip.getProduct());
				Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(data.getCapital(), data.getTerm(), p);
				if(ip.getServiceFeeWay()!=null){
					mv.addObject("serviceFeeWay", ip.getServiceFeeWay());
				}else{
					mv.addObject("serviceFeeWay", "");
				}
				BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(id);
				if(guaranteeFeeInfo==null){
					if(ip.getServiceFeeWay()==null){
						mv.addObject("serviceFee", serviceFee.get("first"));
					}else{
						mv.addObject("serviceFee", "0");
					}
				}else{
					mv.addObject("serviceFee", guaranteeFeeInfo.getTotalAmount());
				}

				if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
					mv.addObject("applyType", ip.getType());
				}else{
					if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
						List<NjProduct> njproList = njproductService.queryAllNjProduct();
						List<NjProductOrder> njproorderList = njProductOrderService.queryAllOnNjProductOrders();
						JSONArray arr = new JSONArray();
						if(njproList!=null&&njproList.size()>0){
							for (int i = 0; i < njproList.size(); i++) {
								NjProduct njProduct = njproList.get(i);
								JSONObject obj = new JSONObject();
								obj.put("id", njProduct.getId());
								obj.put("productName", njProduct.getProductName());
								obj.put("price", njProduct.getPrice());
								obj.put("type", Constants.PRODUCT);
								arr.add(obj);
							}
						}
						if(njproorderList!=null&&njproorderList.size()>0){
							for (int i = 0; i < njproorderList.size(); i++) {
								NjProductOrder njProductOrder = njproorderList.get(i);
								JSONObject obj = new JSONObject();
								obj.put("id", njProductOrder.getId());
								obj.put("productName", njProductOrder.getOrderName());
								obj.put("price", njProductOrder.getOrderPrice());
								obj.put("type", Constants.ORDER);
								arr.add(obj);
							}
						}
						mv.addObject("productListp", arr);
						if(data != null){
							if(data.getRecieveCash()!=null)
								mv.addObject("recieveCash", data.getRecieveCash());
							if(data.getRecieveNongZi()!=null)
								mv.addObject("recieveNongZi", data.getRecieveNongZi());
							if(StringUtils.isEmpty(data.getProductListInfo())){
								mv.addObject("productmel", new JSONArray());
							}else{
								JSONArray parseArray = JSONArray.parseArray(data.getProductListInfo()); 
								mv.addObject("productmel", parseArray);
							}
						}
						
					}
					mv.addObject("applyType", ip.getType());
					
				}
			}
			
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(ip.getId());
			fs.setCoBorrower(1);
			List<BusFamilySituation> family = familySituationService.queryCoBorrower(fs);
			StringBuffer name = new StringBuffer(ip.getMemberName());
			StringBuffer phone = new StringBuffer(ip.getPhone());
			StringBuffer idCard = new StringBuffer(ip.getIdCard());
			for (int i = 0; i < family.size(); i++) {
				name.append(" ");
				name.append(family.get(i).getName());
				phone.append(" ");
				phone.append(family.get(i).getPhone());
				idCard.append(" ");
				idCard.append(family.get(i).getIdCard());
			}
			
			
			mv.addObject("name", name.toString());
			mv.addObject("phone", phone.toString());
			mv.addObject("idCard", idCard.toString());
			mv.addObject("address", ip.getAddress());
			mv.addObject("trail_opinion", ip.getTrialOpinion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("intoPiece/result");
		return mv;
	}	
	
	@RequestMapping("/batchlastexamine")
	@ResponseBody
	public JSONObject batchLastExamine(HttpServletRequest request,String ids){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(ids)){
			json.put("code", "400");
			json.put("msg","请选择要提交的进件");
			return json;
		}
		String[] id = ids.split(",");
		try {
			for (int i = 0; i < id.length; i++) {
				if(StrUtils.isNotEmpty(id[i])){
					BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id[i]);
					
					BusExamine be = new BusExamine();
					be.setId(UUIDUtil.getUUID());
					be.setIntoPieceId(id[i]);
					be.setTerm(ip.getTerm());
					be.setNode(Constants.FLOW_FORCH_TRIAL);
					be.setProductId(ip.getProduct());
					be.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					be.setExamineDate(new Date());
					be.setExamineOpinion("通过");
					be.setExamineName(request.getSession().getAttribute("personName").toString());
					be.setCapital(ip.getCapital());
					
					AppEntry entry = appEntryService.queryByAppModeId(ip.getId());
					flowMgrImpl.getNextTask(entry);
					JSONObject node = flowMgrImpl.getNextTask(entry);
					if(node != null && node.getIntValue("code") == 200){
						JSONArray arr = node.getJSONArray("nodes");
						if(arr != null){
							String nextNodeId = arr.getJSONObject(0).getString("nodeId");
							String pcId = node.getString("pcId");
							examineService.lastFormSave(ip, be, entry, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
							continue;
						}
					}
					throw new Exception(entry.getAppName()+"未配置下一步节点");
				}
			}
		} catch (Exception e) {
			json.put("code", "400");
			json.put("msg",e.getMessage());
			return json;
		}
		return json;
	}
}
