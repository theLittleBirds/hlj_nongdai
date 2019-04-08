package com.nongyeos.loan.admin.controller.nj;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.entity.BusFamilyCredit;
import com.nongyeos.loan.admin.entity.BusFamilyOperate;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.IFamilyCapitalService;
import com.nongyeos.loan.admin.service.IFamilyCreditService;
import com.nongyeos.loan.admin.service.IFamilyOperateService;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMemberOperateMediaService;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.entity.FlowEntrance;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.app.service.IFlowEntranceService;
import com.nongyeos.loan.base.util.AppNull;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.NotEmptyUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/applyLoan")
public class CApplyLoanController {
	
	@Autowired
    private IPersonService personService;
	@Autowired  
    private IOrgService orgService;
	
	@Autowired
	private FileConfig fileConfig;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private IMediaService mediaService;
	@Autowired
	private IApplicationService applicationService;
	//事件节点相关
	@Autowired
	private IFlowEntranceService flowEntranceService;
	@Autowired
	private IFlowNodeService fleNodeService;
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CApplyLoanController.class);
	
	@Autowired
	private IFamilyOperateService familyOperateService;
	
	@Autowired
	private IMemberOperateService memberOperateService;
	
	@Autowired
	private RootPointConfig rootPointConfig;
	
	@Autowired
	private IMemberOperateMediaService memberOperateMediaService;
	
	@Autowired
	private IFamilyCapitalService familyCapitalService;// 家庭资产
	
	@Autowired
	private IProductService productService;// 家庭资产
	
	@Autowired
	private NjProductService njproductService;
	
	@Autowired
	private NjProductOrderService njProductOrderService;
	
	@Autowired
	private IWebUserService userService;
	
	@Autowired
	private IFamilySituationService familySituationService;
	
	@Autowired
	private IFamilyCreditService familyCreditService;
	/**
	 * 
	 * @Title: memberApply 
	 * @Description: 个人用户申请进件
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/apply")
    @ResponseBody
    @Transactional
	public JSONObject memberApply(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		// 初始化时，如果startupPath文件夹不存在，则自动创建startupPath文件夹
        try {
        	String loginId = (String) request.getAttribute("loginId");
        	BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
        	String userId = null;
        	if(memberLogin==null){
        		userId=loginId;
        	}
        	String memberId = request.getParameter("memberId");
        	BusMember member1 = memberService.selectByPrimaryKey(memberId);
            //模型选择（模糊查询）
        	 String modelId = request.getParameter("modelId");
        	Map<String, Object> modelIdnull = NotEmptyUtils.somthingLose(request,response, "modelId", "请选择应用场景！", retJson);
			if((boolean) modelIdnull.get("flag"))
				return JSONArray.parseObject(modelIdnull.get("retJson").toString()) ;
            //部门选择（模糊查询）
            String orgId = request.getParameter("orgId");
            Map<String, Object> orgIdnull = NotEmptyUtils.somthingLose(request, response, "orgId", "请选择部门！", retJson);
			if((boolean) orgIdnull.get("flag"))
				return JSONArray.parseObject(orgIdnull.get("retJson").toString()) ;
			String use = request.getParameter("use");
			Map<String, Object> usenull = NotEmptyUtils.somthingLose(request, response, "use", "请填写客户类型！", retJson);
			if((boolean) usenull.get("flag"))
				return JSONArray.parseObject(usenull.get("retJson").toString()) ;
    		//申请期限
            String term = request.getParameter("term");
            if(!StringUtils.isEmpty(term)){
            	try {
            		term = term.trim().replaceAll("\\s+","");
            		Integer.parseInt(term);
            	} catch (Exception e) {
            		retJson.put("message", "申请期限请填写数字！");
            		retJson.put("errno", 3003);
            		response.setStatus(400);
            		return retJson;
            	}
            }
            //用途备注
            String useRemark = request.getParameter("useRemark");
            Map<String, Object> useRemarknull = NotEmptyUtils.somthingLose(request,response,  "useRemark", "请填写用途！", retJson);
			if((boolean) useRemarknull.get("flag"))
				return JSONArray.parseObject(useRemarknull.get("retJson").toString()) ;
			String name = member1.getName();
			 //银行预留手机号
            String bankPhone = member1.getBankPhone();
            //身份证号
            String idCard = member1.getIdCard();
            String real = idCard.trim().replaceAll("\\s+","");
            //年龄
            int age =0;
            //性别
    		String sex =null;
    		if(real!=null&&IdCheck.isValidatedAllIdcard(real)){
    			if(real.length()==15){
    				real = IdCheck.convertIdcarBy15bit(real);
    			}
    			age=IdCheck.getAgeByIdCard(real);
    			sex = IdCheck.getGenderByIdCard(real);
    		}
    		//个人或商户标识
    		String channelId = null;
    		//银行卡号
    		String bankCardNo = member1.getBankCardNo();
//          //开户行
    		String bank = member1.getBank();
            BusIntoPiece intoPiece = new BusIntoPiece();
            intoPiece.setOrgId(orgId);
            intoPiece.setModelId(modelId);
            //根据应用产品反查金融机构
            AppApplication app = applicationService.selectByModelId(intoPiece.getModelId());
            //金融机构
			intoPiece.setLenderId(app.getFinsCode());
			//农资或土地抵押
			intoPiece.setType(new Integer(app.getEname()));
			if(StringUtils.isNoneEmpty(app.getEname()) ){
				if(app.getEname().equals(Constants.LOAN_LAND_PRODUCT)||app.getEname().equals(Constants.LOAN_LAND_PRODUCT_GRAIN)){
					//待打款
					intoPiece.setOrderStatus(2);
				}
			}
//			Map<String, Object> bankPhonenull = NotEmptyUtils.somthingLose(request,response,  "bankPhone", "请输入手机号！", retJson);
//			if((boolean) bankPhonenull.get("flag"))
//				return JSONArray.parseObject(bankPhonenull.get("retJson").toString());
//			Map<String, Object> bankPhonewrong = NotEmptyUtils.somethingwrong(request.getParameter("bankPhone"),response,  "^1\\d{10}$", "预留手机号输入有误，请重新输入！", retJson);
//			if((boolean) bankPhonewrong.get("flag"))
//				return JSONArray.parseObject(bankPhonewrong.get("retJson").toString());
//			Map<String, Object> addressnull = NotEmptyUtils.somthingLose(request, response, "address",  "请输入家庭住址！", retJson);
//			if((boolean) addressnull.get("flag"))
//				return JSONArray.parseObject(addressnull.get("retJson").toString());
			//文化程度
//			Map<String, Object> educationLevelnull = NotEmptyUtils.somthingLose(request,response,  "educationLevel", "请选择文化程度！", retJson);
//			if((boolean) educationLevelnull.get("flag"))
//				return JSONArray.parseObject(educationLevelnull.get("retJson").toString());
//			//婚姻状况
//			Map<String, Object> maritalStatusnull = NotEmptyUtils.somthingLose(request, response, "maritalStatus", "请选择婚姻状况！", retJson);
//			if((boolean) maritalStatusnull.get("flag"))
//				return JSONArray.parseObject(maritalStatusnull.get("retJson").toString());
            intoPiece.setTerm(new Integer(term));
            intoPiece.setUseRemark(useRemark);
            intoPiece.setPhone(bankPhone);
            intoPiece.setMemberName(name);
            intoPiece.setSex(new Integer(sex));
            intoPiece.setAge(age);
            intoPiece.setIdCard(idCard);
            intoPiece.setBank(bank);
            intoPiece.setBankCardNo(bankCardNo);
            intoPiece.setEducationLevel(member1.getEducationLevel().intValue());
            intoPiece.setMaritalStatus(member1.getMaritalStatus().intValue());
            intoPiece.setAddress(member1.getAddress());
            intoPiece.setUse(Integer.parseInt(use));
            if(memberLogin==null){
            	SysPerson person = personService.selectByUserIdAndIsdefault(userId);
            	intoPiece.setOperUserId(person.getPersonId());
            	intoPiece.setCreOperId(userId);
                intoPiece.setUpdOperId(userId);
                channelId = Constants.NJ_TOKEN_USER;
            }else{
            	List<SysPerson> list = personService.queryByOrgId(orgId);
            	if(list==null){
            		retJson.put("message", "该部门信息不全，请联系管理员！");
            		response.setStatus(400);
            		return retJson;
            	}
            	intoPiece.setOperUserId(list.get(0).getPersonId());
            	intoPiece.setCreOperId(memberLogin.getLoginId());
                intoPiece.setUpdOperId(memberLogin.getLoginId());
                channelId = Constants.NJ_TOKEN_MEMBER;
            }
            SysOrg org = orgService.selectByOrgId(intoPiece.getOrgId());
			String mark = rootPointConfig.getMark();
			JSONObject markJson = JSONObject.parseObject(mark);
			Set<String> keySet = markJson.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				String rootOrgId = markJson.getString(key);
				if(org.getParentOrgIds().contains(rootOrgId)){
					intoPiece.setChannel(key);
					break;
				}else if(rootOrgId.equals(org.getOrgId())){
					intoPiece.setChannel(key);
					break;
				}
			}
            BusIntoPiece busIntoPiece = intoPieceService.saveIntoPieceAndMember(intoPiece);
            if(busIntoPiece==null){
            	retJson.put("message", "有未完成的贷款，无法再次申请！");
            	response.setStatus(400);
				return retJson;
			
			}else{
				AppApplication application = applicationService.selectByModelId(busIntoPiece.getModelId());
				if(busIntoPiece.getReject()==Constants.REJECT_FLAG_YES){
					//拒件
					FlowNode flowNode = new FlowNode();
					flowNode.setAppId(modelId);
					flowNode.setEname(Constants.FLOW_REFUSE);
					FlowNode fleNode = fleNodeService.queryByEnameAndModel(flowNode);
					AppEntry appEntry = new AppEntry();
					appEntry.setEntryId(UUIDUtil.getUUID());//记录表主键
					appEntry.setModeId(busIntoPiece.getId());//业务表主键
					appEntry.setChannelId(channelId);
					appEntry.setFromId(Constants.T_BUS_INTOPIECE);//进件表名字
					appEntry.setOrgId(orgId);//当前用户的orgid
					appEntry.setFinsId(application.getFinsCode());//金融机构
					appEntry.setAppId(intoPiece.getModelId());//产品应用的appid 主键
					appEntry.setNodeId(fleNode.getNodeId());//流程节点id 主键
					appEntry.setNodeName(fleNode.getEname());//流程节点的名称
					appEntry.setAppName(application.getCname());
					appEntry.setCreDate(DateUtils.getNowDate());//创建时间
					appEntry.setUpdDate(DateUtils.getNowDate());// 更新时间
					appEntryService.addAppEntrySelective(appEntry);
					retJson.put("message", "不满足借款条件,已拒件！");
	        		retJson.put("errno", 3032);
	        		response.setStatus(400);
	    	    	return retJson;
				}else{
					//档案资料填写的复制到进件资料中
					try {
						String memberOperateId = request.getParameter("memberOperateId");
						if(!StringUtils.isEmpty(memberOperateId)){
							BusMemberOperate MO = memberOperateService.selectByPrimaryKey(memberOperateId);
							if(MO!=null){
								BusFamilyOperate familyOperate = new BusFamilyOperate();
								familyOperate.setId(UUIDUtil.getUUID());
								familyOperate.setIntoPieceId(busIntoPiece.getId());
								familyOperate.setCreOperId(userId);
								familyOperate.setUpdOperId(userId);
								familyOperate.setCreDate(DateUtils.getNowDate());
								familyOperate.setUpdDate(DateUtils.getNowDate());
								familyOperate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
								StringBuilder sb = new StringBuilder();
								String cropType = MO.getCropType();
								String cropYears = MO.getCropYears();
								String cropScale = MO.getCropScale();
								String cropExpectedValue = MO.getCropExpectedValue();
								String cropInvestment = MO.getCropInvestment();
								if(!StringUtils.isEmpty(cropType)||!StringUtils.isEmpty(cropYears)||!StringUtils.isEmpty(cropScale)||!StringUtils.isEmpty(cropExpectedValue)||!StringUtils.isEmpty(cropInvestment)){
									sb.append("1");
									familyOperate.setBigLandCropType(cropType);
									familyOperate.setBlcYears(cropYears);
									familyOperate.setBlcScale(cropScale);
									familyOperate.setBlcExpectedValue(cropExpectedValue);
									familyOperate.setBlcInvestment(cropInvestment);
								}
								String livestockType = MO.getLivestockType();
								String livestockScale = MO.getLivestockScale();
								String livestockYears = MO.getLivestockYears();
								String livestockExpectedValue = MO.getLivestockExpectedValue();
								Integer livestockSiteType = MO.getLivestockSiteType();
								if(!StringUtils.isEmpty(livestockType)||!StringUtils.isEmpty(livestockScale)||!StringUtils.isEmpty(livestockYears)||!StringUtils.isEmpty(livestockExpectedValue)||livestockSiteType!=null){
									String sb1 = sb.toString();
									if(StringUtils.isEmpty(sb1)){
										sb.append("3");
									}else{
										sb.append(",3");
									}
									familyOperate.setLivestockType(livestockType);
									familyOperate.setLivestockScale(livestockScale);
									familyOperate.setLivestockYears(livestockYears);
									familyOperate.setLivestockExpectedValue(livestockExpectedValue);
									familyOperate.setLivestockSiteType(livestockSiteType);
								}
								familyOperate.setOperateType(AppNull.s2SNull(sb.toString()));
								familyOperateService.insert(familyOperate);
								List<BusMemberOperateMedia> list = memberOperateMediaService.selectByMOId(memberOperateId);
								if(list!=null&&list.size()>0){
									for (int i = 0; i < list.size(); i++) {
										BusMedia media = new BusMedia();
										BusMemberOperateMedia busMemberOperateMedia = list.get(i);
										media.setIntoPieceId(busIntoPiece.getId());
										media.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
										media.setQiniuKey(busMemberOperateMedia.getQiniuKey());
										media.setId(UUIDUtil.getUUID());
										media.setFileType(Constants.OTHER);
										media.setExtName("jpg");
										media.setType(Constants.MEDIATYPE_IMAGE);
										media.setCreDate(DateUtils.getNowDate());
										media.setCreOperId(loginId);
										media.setUpdDate(DateUtils.getNowDate());
										media.setUpdOperId(loginId);
										mediaService.insert(media);
									}
								}
							}
							
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(intoPiece.getType()==null||intoPiece.getType().equals(new Integer(Constants.LOAN_LAND)) ){
						retJson.put("applyType", intoPiece.getType()==null?"1":intoPiece.getType());
					}else{
						retJson.put("applyType", intoPiece.getType());
					}
					retJson.put("message", "申请成功！");
					response.setStatus(200);
	    	    	retJson.put("intoPieceId", busIntoPiece.getId());
				}
			}
            
//        	retJson.put("message", "申请失败！");
//	    	retJson.put("code", 400);
            AppApplication application = applicationService.selectByModelId(busIntoPiece.getModelId());
			//记录当前流程节点
			//应用_产品应用
			//进件表的modelId是业务_状态记录的id又是T_FLOW_ENTRANCE表的appid
			List<FlowEntrance> flowEntranceInit = flowEntranceService.selectByAppId(intoPiece.getModelId());
			if(flowEntranceInit!=null && flowEntranceInit.size()>0){
				//现在目前用一个流程入口，手机端和pc端的流程是一样的（在选择入口时候目前不加入决策）
				FlowEntrance flowEntrance = flowEntranceInit.get(0);
				if(flowEntrance != null){
					//查询流程节点
					FlowNode fleNodeId = fleNodeService.selectByNodeId(flowEntrance.getStartNodeId());
					
					//保存业务_状态记录     TODO未完待续...
					AppEntry appEntry = new AppEntry();
					appEntry.setEntryId(UUIDUtil.getUUID());//记录表主键
					appEntry.setChannelId(channelId);
					appEntry.setModeId(busIntoPiece.getId());//业务表主键
					appEntry.setFromId(Constants.T_BUS_INTOPIECE);//进件表名字
					appEntry.setOrgId(orgId);//当前用户的orgid
					appEntry.setFinsId(application.getFinsCode());//金融机构
					appEntry.setAppId(intoPiece.getModelId());//产品应用的appid 主键
					appEntry.setNodeId(fleNodeId.getNodeId());//流程节点id 主键
					appEntry.setNodeName(fleNodeId.getEname());//流程节点的名称
					appEntry.setAppName(application.getCname());
					appEntry.setCreDate(DateUtils.getNowDate());//创建时间
					appEntry.setUpdDate(DateUtils.getNowDate());// 更新时间
					appEntryService.addAppEntrySelective(appEntry);
				}else{
					throw new Exception("流程入口内流向为空");
				}
			}else{
				throw new Exception("没有查询到符合条件的流程入口");
			}
	    	
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络错误！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	
	private void savePicKey(String qiniuKey, String intoPieceId, String loginId, Integer type) throws Exception{
		try {
			BusMedia media = new BusMedia();
			media.setIntoPieceId(intoPieceId);
			media.setQiniuKey(qiniuKey);
			media.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			int i = mediaService.existenceByKey(media);
			if(i>0){
				return;
			}
			media.setId(UUIDUtil.getUUID());
			media.setFileType(type);
			media.setType(Constants.MEDIATYPE_IMAGE);
			media.setCreDate(DateUtils.getNowDate());
			media.setCreOperId(loginId);
			media.setUpdDate(DateUtils.getNowDate());
			media.setUpdOperId(loginId);
			mediaService.insert(media);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("照片保存失败！");
		}
	}
	
	@RequestMapping("/intoPieceInfo")
	@ResponseBody
	public JSONObject intoPieceInfo(HttpServletRequest request ,HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			JSONObject obj = new JSONObject();
			obj.put("modelId", intoPiece.getModelId());
			AppApplication appApplication = applicationService.selectByModelId(intoPiece.getModelId());
			if(appApplication==null){
				retJson.put("message", "未找到产品！");
				response.setStatus(400);
				return retJson;
			}
			obj.put("modelName", appApplication.getCname());
			obj.put("orgId", intoPiece.getOrgId());
			SysOrg org = orgService.selectByOrgId(intoPiece.getOrgId());
			obj.put("orgName", org.getFullCname());
			obj.put("term", AppNull.o2SNotNull(intoPiece.getTerm()));
			obj.put("use", intoPiece.getUse() == null ? "" : intoPiece.getUse().toString());
			obj.put("useRemark", AppNull.o2SNotNull(intoPiece.getUseRemark()));
			obj.put("bankCardNo", AppNull.o2SNotNull(intoPiece.getBankCardNo()));
			obj.put("bank", AppNull.o2SNotNull(intoPiece.getBank()));
			obj.put("bankPhone", AppNull.o2SNotNull(intoPiece.getPhone()));
			obj.put("name", AppNull.o2SNotNull(intoPiece.getMemberName()));
			obj.put("idCard", AppNull.o2SNotNull(intoPiece.getIdCard()));
			obj.put("address", AppNull.o2SNotNull(intoPiece.getAddress()));
			obj.put("educationLevel", AppNull.o2SNotNull(intoPiece.getEducationLevel()));
			obj.put("maritalStatus", AppNull.o2SNotNull(intoPiece.getMaritalStatus()));
			retJson.put("data", obj);
			retJson.put("errono", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	
	@RequestMapping("/findData")
	@ResponseBody
	public JSONObject findData(HttpServletRequest request,HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			AppApplication app = applicationService.selectByModelId(ip.getModelId());
			if(StringUtils.isNotEmpty(app.getEname()) ){
				if(app.getEname().equals(Constants.LOAN_LAND_PRODUCT)||app.getEname().equals(Constants.LOAN_LAND_PRODUCT_GRAIN)){
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
							obj.put("detail", njProduct.getProductDesc());
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
							obj.put("detail", njProductOrder.getOrderDesc());
							obj.put("type", Constants.ORDER);
							arr.add(obj);
						}
					}
					retJson.put("productList", arr);
					}
				}
//			BusFamilyCapital fc = new BusFamilyCapital();
//			fc.setIntoPieceId(intoPieceId);
//			fc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
//			BusFamilyCapital familyCapital = familyCapitalService.selectByIntoPieceId(fc);
//			String expressionString = app.getShortEname();
//			String creditCapital ="";
			/*if(StringUtils.isNoneEmpty(expressionString)&&expressionString.contains("=")){
				Integer term = ip.getTerm();
				if(term%12!=0){
					retJson.put("message", "贷款期限不是整年");
					response.setStatus(400);
					return retJson;
				}
				Integer year =  term/12;
				String[] split = expressionString.split("=");
				expressionString=split[1];
				expressionString = expressionString.replaceAll("DL", familyCapital.getDryFarmlandRegistered());
				expressionString = expressionString.replaceAll("WL", familyCapital.getWaterFarmlandRegistered());
				expressionString = expressionString.replaceAll("T", year.toString());
				JexlEngine je = new JexlBuilder().create();
				JexlExpression createExpression = je.createExpression(expressionString);
				Object creditCapital1 =  createExpression.evaluate(null);
				creditCapital=creditCapital1.toString();
				BigDecimal bd = new BigDecimal(creditCapital);
				bd=bd.divide(new BigDecimal("1000"), 0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal("1000"));
				creditCapital=bd.toString();
				if(bd.compareTo(new BigDecimal(50000))==1){
					creditCapital="50000";
				}
			}*/
			List<BusProduct> listPro = productService.selectListByFinsId(ip.getLenderId());
			JSONArray proArr = new JSONArray();
			if(listPro!=null&&listPro.size()>0){
				for (int i = 0; i < listPro.size(); i++) {
					BusProduct busProduct = listPro.get(i);
					JSONObject obj = new JSONObject();
					obj.put("productId", busProduct.getProductId());
					obj.put("productName", busProduct.getName());
					proArr.add(obj);
				}
			}
			retJson.put("finProducts", proArr);
//			retJson.put("creditCapital", creditCapital);
			if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
				response.setStatus(200);
				retJson.put("applyType", ip.getType()==null?"1":ip.getType());
				return retJson;
			}
			response.setStatus(200);
			retJson.put("applyType",  ip.getType());
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "系统异常");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/submittedInfo")
	@ResponseBody
	public JSONObject getsubmittedInfo(String intoPieceId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece ip=null;
			if(StringUtils.isEmpty(intoPieceId)){
				ip=new BusIntoPiece();
			}else{
				ip= intoPieceService.selectByPrimaryKey(intoPieceId);
			}
			AppApplication app = applicationService.selectByModelId(ip.getModelId());
			if(StringUtils.isNotEmpty(app.getEname()) ){
				if(app.getEname().equals(Constants.LOAN_LAND_PRODUCT)||app.getEname().equals(Constants.LOAN_LAND_PRODUCT_GRAIN)){
					retJson.put("sellProductList", StringUtils.isEmpty(ip.getProductListInfo())?new JSONArray(): JSONArray.parseArray(ip.getProductListInfo()));
				}
			}
			retJson.put("capital", ip.getCapital()==null?"":ip.getCapital());
			retJson.put("productId", ip.getProduct()==null?"":ip.getProduct());
//			map.put("recieveNongZi", ip.getRecieveNongZi());
//			map.put("recieveCash", ip.getRecieveCash());
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络异常");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/applyDetailSave")
	@ResponseBody
	public JSONObject applyDetailSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			BufferedReader reader = request.getReader();
	    	String str,wholeStr ="";
	    	while((str=reader.readLine())!=null){
	    		wholeStr+=str;
	    	}
	    	JSONObject jsonpara = JSONObject.parseObject(wholeStr);
			
			String intoPieceId = jsonpara.getString("intoPieceId");
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			String capital = jsonpara.getString("capital");
			String product = jsonpara.getString("productId");
			String sellProductList = jsonpara.getString("sellProductList");
			if(StringUtils.isEmpty(capital)){
				retJson.put("message", "申请金额必填");
				response.setStatus(400);
				return retJson;
			}
			if(StringUtils.isEmpty(product)){
				retJson.put("message", "金融产品必选");
				response.setStatus(400);
				return retJson;
			}
			if(ip.getType()!=null){
				if(ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||ip.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN))){
					if(StringUtils.isEmpty(sellProductList)){
						retJson.put("message", "请至少选择一种商品或套餐");
						response.setStatus(400);
						return retJson;
					}
					JSONArray productListInfo = JSONArray.parseArray(sellProductList);
					if(productListInfo==null||productListInfo.size()<1){
						retJson.put("message", "请至少选择一种商品或套餐");
						response.setStatus(400);
						return retJson;
					}
					BigDecimal recieveNongZi = new BigDecimal("0");
					BigDecimal nongzisettle = new BigDecimal("0");
					for (int i = 0; i < productListInfo.size(); i++) {
						JSONObject proorder = productListInfo.getJSONObject(i);
						String productTime = proorder.getString("productTime");
						if(StringUtils.isNotEmpty(productTime)){
							Date date = DateUtils.parse(productTime, "yyyy-MM-dd");
							if(date.before(new Date())){
								retJson.put("message", "预计使用时间不能在今天之前");
								response.setStatus(400);
								return retJson;
							}
						}
						String productId = proorder.getString("productId");
						String productNum = proorder.getString("productNum");
						NjProduct productdan = njproductService.queryNjProductByPrimaryKey(productId);
						if(productdan==null){
							NjProductOrder productOrder =njProductOrderService.queryProductOrderByPK(productId);
							recieveNongZi=recieveNongZi.add(new BigDecimal(productOrder.getOrderPrice()).multiply(new BigDecimal(productNum)));
							nongzisettle=nongzisettle.add(new BigDecimal(productOrder.getOrderSettleprice()).multiply(new BigDecimal(productNum)));
						}else{
							recieveNongZi=recieveNongZi.add(new BigDecimal(productdan.getPrice()).multiply(new BigDecimal(productNum)));
							nongzisettle=nongzisettle.add(new BigDecimal(productdan.getSettlePrice()).multiply(new BigDecimal(productNum)));
						}
						
					}
						ip.setProductPrice(recieveNongZi.toString());
						ip.setProductSettlePrice(nongzisettle.toString());
						ip.setProductListInfo(sellProductList);
						ip.setRecieveCash(new BigDecimal(capital).subtract(recieveNongZi).toString());
						ip.setRecieveNongZi(recieveNongZi.toString());
						retJson.put("recieveNongZi", recieveNongZi);
						retJson.put("recieveCash", ip.getRecieveCash());
				}
			}
			ip.setCapital(new BigDecimal(capital));
			ip.setProduct(product);
			intoPieceService.updateByPrimaryKey(ip);
			retJson.put("message", "保存成功");
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络异常");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/completionDegree")
	@ResponseBody
	public JSONObject completionDegree(HttpServletRequest request,HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "进件标识为空！");
				response.setStatus(400);
				return retJson;
			}
			boolean media = true;
			boolean land = true;
			boolean applyDate = true;
			boolean family = true;
			boolean credit = true;
			boolean operate = true;
			boolean capital = true;
			//申请信息
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(ip.getCapital()==null||StringUtils.isEmpty(ip.getProduct())||StringUtils.isEmpty(ip.getProductListInfo()) ){
				applyDate=false;
			}
			BusMedia record = new BusMedia();
			record.setIntoPieceId(intoPieceId);
			record.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<BusMedia> mediaList = mediaService.selectByIpId(record);
			if(mediaList==null||mediaList.size()<1){
				media=false;
			}
			BusFamilyCapital recordc = new BusFamilyCapital();
			recordc.setIntoPieceId(intoPieceId);
			recordc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital familyCapital = familyCapitalService.selectByIntoPieceId(recordc);
			//土地情况
			if(familyCapital==null||StringUtils.isEmpty(familyCapital.getLandCertId())||StringUtils.isEmpty(familyCapital.getWaterFarmlandRegistered())||StringUtils.isEmpty(familyCapital.getDryFarmlandRegistered()) ){
				land=false;
			}
			if(familyCapital==null||StringUtils.isEmpty(familyCapital.getTotalFamilyCapital()) ){
				capital=false;
			}
			BusFamilySituation records = new BusFamilySituation();
			records.setIntoPieceId(intoPieceId);
			//父亲
			records.setType(1);
			records.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilySituation dad = familySituationService.selectByIntopIdAndType(records);
			if(dad==null){
				family=false;
			}else{
				if(dad.getIsDead()!=null){
					if(dad.getIsDead().equals(2)){
						if(dad.getAge()==null||StringUtils.isEmpty(dad.getName())||StringUtils.isEmpty(dad.getPhone())||StringUtils.isEmpty(dad.getIdCard())||dad.getStatus()==null||dad.getHealthStatus()==null||dad.getCoBorrower()==null){
							family=false;
						}
					}else{
						family=false;
					}
				}
			}
			//母亲
			records.setType(2);
			BusFamilySituation mom = familySituationService.selectByIntopIdAndType(records);
			if(mom==null){
				family=false;
			}else{
				if(mom.getIsDead()!=null){
					if(mom.getIsDead().equals(2)){
						if(mom.getAge()==null||StringUtils.isEmpty(mom.getName())||StringUtils.isEmpty(mom.getPhone())||StringUtils.isEmpty(mom.getIdCard())||mom.getStatus()==null||mom.getHealthStatus()==null||mom.getCoBorrower()==null){
							family=false;
						}
					}else{
						family=false;
					}
				}
			}
			//配偶
			records.setType(3);
			BusFamilySituation wife = familySituationService.selectByIntopIdAndType(records);
			if(wife==null||wife.getAge()==null||StringUtils.isEmpty(wife.getName())||StringUtils.isEmpty(wife.getPhone())||StringUtils.isEmpty(wife.getIdCard())
					||wife.getStatus()==null||wife.getHealthStatus()==null||wife.getEducationLevel()==null||wife.getDuty()==null||StringUtils.isEmpty(wife.getNonfarmComAddress())){
				family=false;
			}
			BusFamilyCredit recordcr = new BusFamilyCredit();
			recordcr.setIntoPieceId(intoPieceId);
			recordcr.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCredit familyCredit = familyCreditService.selectByIpId(recordcr);
			if(familyCredit==null){
				credit=false;
			}
			BusFamilyOperate familyOperate = familyOperateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				operate=false;
			}
			JSONObject obj = new JSONObject();
			obj.put("applyDate", applyDate);
			obj.put("media", media);
			obj.put("land", land);
			obj.put("family", family);
			obj.put("credit", credit);
			obj.put("operate", operate);
			obj.put("capital", capital);
			retJson.put("data", obj);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络异常");
			response.setStatus(400);
		}
		return retJson;
	}
	
}
