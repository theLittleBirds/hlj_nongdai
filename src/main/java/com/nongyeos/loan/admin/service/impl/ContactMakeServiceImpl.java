package com.nongyeos.loan.admin.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusApplySignFile;
import com.nongyeos.loan.admin.entity.BusContractSignatory;
import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusTransferLand;
import com.nongyeos.loan.admin.entity.IntSignatories;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.BusApplySignFileMapper;
import com.nongyeos.loan.admin.mapper.BusContactTemplateMapper;
import com.nongyeos.loan.admin.mapper.BusContractDataMapper;
import com.nongyeos.loan.admin.mapper.BusContractSignatoryMapper;
import com.nongyeos.loan.admin.mapper.BusFamilyCapitalMapper;
import com.nongyeos.loan.admin.mapper.BusFamilySituationMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.mapper.BusMediaMapper;
import com.nongyeos.loan.admin.mapper.BusTransferLandMapper;
import com.nongyeos.loan.admin.mapper.IntSignatoriesMapper;
import com.nongyeos.loan.admin.mapper.NjProductMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IContactMakeService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.BusFinsMapper;
import com.nongyeos.loan.base.util.CnNumberUtils;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.base.util.WordToPdfUtils;
import com.nongyeos.loan.base.util.WordUtils;
@Service("contactMakeService")
public class ContactMakeServiceImpl implements IContactMakeService {
	
	@Autowired
	private BusContractDataMapper contractDataMapper;
	
	@Autowired
	private BusLoanMapper loanMapper;
	
	@Autowired
	private BusContactTemplateMapper contactTemplateMapper;
	
	@Autowired
	private BusApplySignFileMapper applySignFileMapper;
	
	@Autowired
	private IntSignatoriesMapper signatoriesMapper;
	
	@Autowired
	private BusContractSignatoryMapper contractSignatoryMapper;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Autowired
	private SysWebUserMapper userMapper;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private SysPersonMapper personMapper;
	
	@Autowired
	private BusFinsMapper finsMapper;
	
	@Autowired
	private BusFamilyCapitalMapper familyCapitalMapper;
	
	@Autowired
	private BusFamilySituationMapper familySituationMapper;
	
	@Autowired
	private SysOrgMapper orgMapper;
	
	@Autowired
	private BusMediaMapper mediaMapper;
	
	@Autowired
	private NjProductMapper productMapper;
	
	@Autowired
	private BusTransferLandMapper transferLandMapper;
	
	private static final Logger logger = LogManager.getLogger(ContactMakeServiceImpl.class);
	

	
	/**
	 *  承诺函（第一个签）
	 */
	@Override
	public void cnh(BusIntoPiece ip, BusLoan loan,String personId) throws Exception {
		BusApplySignFile check = new BusApplySignFile();
		check.setLoanId(loan.getId());
		check.setSequenceOrder(1);
		if(applySignFileMapper.checkExist(check) != null){
			return ;
		}
		String filePath = baseDir + "templates//CNH.docx";
		String dir = FileUtils.getTimeFilePath();
		String basePath = baseDir+"contract/"+dir;
		FileUtils.createDirectory(basePath);
		String wordName = FileUtils.generateRandomFilename()+".docx";
		String wordPath = basePath + wordName;	
		String pdfName = FileUtils.generateRandomFilename()+".pdf";
		String pdfPath = basePath + pdfName;
		Map<String, Object> map = new HashMap<String,Object>();
		BusFins fins = finsMapper.selectByPrimaryKey(ip.getLenderId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String time = df.format(new Date());
		map.put("year", time.substring(2, 4));
		map.put("no", loan.getContractNo() + "-1");
		map.put("lender", fins.getCname());
		map.put("address", ip.getAddress());
		map.put("name", ip.getMemberName());
		map.put("idCard", ip.getIdCard());
		map.put("capital", loan.getCapital());
		map.put("time", df.format(new Date()));
		SysOrg org = orgMapper.selectByPrimaryKey(ip.getOrgId());
		if(StrUtils.isEmpty(org.getLeader())){
			throw new Exception("站长信息未设置");
		}
		map.put("operator", org.getLeader());
		
		JSONObject result = WordUtils.writeWordToLocal(filePath, map, wordPath);
		if(result.getIntValue("code") == 400){
			throw new Exception("承诺函生成失败");
		}
		result = WordToPdfUtils.convert(wordPath, pdfPath);
		if(result.getIntValue("code") == 400){
			throw new Exception(result.getString("msg"));
		}
		
		SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
		String signature = user.getUserId() + user.getUsername() + user.getPassword();
		Map<String, String> httpMap = new HashMap<String,String>();
		httpMap.put("userid", user.getUserId());
		httpMap.put("signature", DigestUtils.md5Hex(signature));
		httpMap.put("contractAmount", loan.getCapital().toString());
		httpMap.put("companyType", "HLJSX");
		BusContractSignatory  cs = contractSignatoryMapper.selectByEname("HLJSX");
		if(cs == null){
			throw new Exception("没有设置签署机构");
		}
		httpMap.put("contractName", "承诺函");
		httpMap.put("filePath", pdfPath);
		JSONArray personList = new JSONArray();
		JSONObject company = new JSONObject();
		company.put("name", cs.getName());
		company.put("idcard", cs.getIdentityCard());
		company.put("phone", cs.getPhone());
		company.put("email", cs.getEmail());
		company.put("type", "company");	
		String location = "[{\"page\":\"0\",\"chaptes\":[{\"offsetX\":\"0.50\",\"offsetY\":\"0.55\"}]}]";
		company.put("location", JSONArray.parse(location));	
		personList.add(company);
		httpMap.put("personList", personList.toString());
		
		String httpResult = HttpClientUtil.doPost(pieceConfig.getJunziqianUpload(), httpMap, "utf-8");
		if(StrUtils.isEmpty(httpResult)){
			throw new Exception("合同上传失败");
		}
		JSONObject resultObj = JSONObject.parseObject(httpResult);
		String isSuccess = resultObj.getString("isSuccess");
		if(!"1".equals(isSuccess)){
			throw new Exception(resultObj.getString("msg"));
		}
		
		BusApplySignFile signFile = new BusApplySignFile();
		signFile.setId(UUIDUtil.getUUID());
		signFile.setLoanId(loan.getId());
		signFile.setSequenceOrder(1);
		signFile.setTotalNum(6);
		signFile.setName("承诺函");
		signFile.setFilePath("/contract"+dir + pdfName);
		signFile.setRemark(ip.getMemberName()+"-承诺函");
		signFile.setStatus(2);
		signFile.setAmount(loan.getCapital());
		signFile.setChannel("HLJSX");
		signFile.setApplyNo(resultObj.getString("applyNo"));
		signFile.setSignatories(cs.getName());
		signFile.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		signFile.setCreDate(new Date());
		signFile.setCreOperId(personId);
		signFile.setUpdDate(new Date());
		signFile.setUpdOperId(personId);
		applySignFileMapper.insert(signFile);		
					
		String[] ids = resultObj.getString("signatoriesId").split(",");
		for (int j = 0; j < ids.length; j++) {
			IntSignatories sign = new IntSignatories();
			sign.setId(ids[j]);
			sign.setLoanId(loan.getId());
			sign.setApplySignFileId(signFile.getId());
			signatoriesMapper.updateByPrimaryKeySelective(sign);
		}
	}
	
	/**
	 * 服务合同(农户-首信)（第二个签）
	 */
	@Override
	public void fwht(BusIntoPiece ip, BusLoan loan,String personId) throws Exception {
		BusApplySignFile check = new BusApplySignFile();
		check.setLoanId(loan.getId());
		check.setSequenceOrder(2);
		if(applySignFileMapper.checkExist(check) != null){
			return ;
		}
		String filePath = baseDir + "templates//FWHT.docx";
		String dir = FileUtils.getTimeFilePath();
		String basePath = baseDir+"contract/"+dir;
		FileUtils.createDirectory(basePath);
		String wordName = FileUtils.generateRandomFilename()+".docx";
		String wordPath = basePath + wordName;	
		String pdfName = FileUtils.generateRandomFilename()+".pdf";
		String pdfPath = basePath + pdfName;
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		map.put("no", loan.getContractNo() + "-2");   //合同编号
		map.put("address", ip.getAddress());          //联系地址
		map.put("phone",ip.getPhone());               //联系方式
		map.put("name", ip.getMemberName());          //乙方
		map.put("idCard", ip.getIdCard());            //身份证号
		map.put("time1", df.format(new Date()));       //日期
		map.put("time2", df.format(new Date()));       //日期
		
		JSONObject result = WordUtils.writeWordToLocal(filePath, map, wordPath);
		if(result.getIntValue("code") == 400){
			throw new Exception("担保意向书生成失败");
		}
		result = WordToPdfUtils.convert(wordPath, pdfPath);
		if(result.getIntValue("code") == 400){
			throw new Exception(result.getString("msg"));
		}
		
		SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
		String signature = user.getUserId() + user.getUsername() + user.getPassword();
		Map<String, String> httpMap = new HashMap<String,String>();
		httpMap.put("userid", user.getUserId());
		httpMap.put("signature", DigestUtils.md5Hex(signature));
		httpMap.put("contractAmount", loan.getCapital().toString());
		httpMap.put("companyType", "HLJSX");
		BusContractSignatory  cs = contractSignatoryMapper.selectByEname("HLJSX");
		if(cs == null){
			throw new Exception("没有找到该企业");
		}
		httpMap.put("contractName", "服务合同");
		httpMap.put("filePath", pdfPath);
		JSONArray personList = new JSONArray();
		JSONObject company = new JSONObject();
		company.put("name", cs.getName());
		company.put("idcard", cs.getIdentityCard());
		company.put("phone", cs.getPhone());
		company.put("email", cs.getEmail());
		company.put("type", "company");	
		String location = "[{\"page\":\"6\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":\"0.40\"}]}]";
		company.put("location", JSONArray.parse(location));	
		personList.add(company);
		
		JSONObject person = new JSONObject();
		person.put("name", ip.getMemberName());
		person.put("idcard", ip.getIdCard());
		person.put("phone", ip.getPhone());
		person.put("type", "person");
		String location2 = "[{\"page\":\"6\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":\"0.50\"}]}]";
		person.put("location", JSONArray.parse(location2));
		personList.add(person);
		httpMap.put("personList", personList.toString());
		
		String httpResult = HttpClientUtil.doPost(pieceConfig.getJunziqianUpload(), httpMap, "utf-8");
		if(StrUtils.isEmpty(httpResult)){
			throw new Exception("合同上传失败");
		}
		JSONObject resultObj = JSONObject.parseObject(httpResult);
		String isSuccess = resultObj.getString("isSuccess");
		if(!"1".equals(isSuccess)){
			throw new Exception(resultObj.getString("msg"));
		}
		
		BusApplySignFile signFile = new BusApplySignFile();
		signFile.setId(UUIDUtil.getUUID());
		signFile.setLoanId(loan.getId());
		signFile.setSequenceOrder(2);
		signFile.setTotalNum(6);
		signFile.setName("服务合同");
		signFile.setFilePath("/contract"+dir + pdfName);
		signFile.setRemark(ip.getMemberName() + "-服务合同");
		signFile.setStatus(2);
		signFile.setAmount(loan.getCapital());
		signFile.setChannel("HLJSX");
		signFile.setApplyNo(resultObj.getString("applyNo"));
		signFile.setSignatories(cs.getName());
		signFile.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		signFile.setCreDate(new Date());
		signFile.setCreOperId(personId);
		signFile.setUpdDate(new Date());
		signFile.setUpdOperId(personId);
		applySignFileMapper.insert(signFile);		
					
		String[] ids = resultObj.getString("signatoriesId").split(",");
		for (int j = 0; j < ids.length; j++) {
			IntSignatories sign = new IntSignatories();
			sign.setId(ids[j]);
			sign.setLoanId(loan.getId());
			sign.setApplySignFileId(signFile.getId());
			signatoriesMapper.updateByPrimaryKeySelective(sign);
		}
	}
	
	/**
	 * 委托采购及支付协议（第三个签）
	 */
	@Override
	public void wtcg(BusIntoPiece ip,BusLoan loan,String personId) throws Exception{
		BusApplySignFile check = new BusApplySignFile();
		check.setLoanId(loan.getId());
		check.setSequenceOrder(3);
		if(applySignFileMapper.checkExist(check) != null){
			return ;
		}
		String filePath = baseDir + "templates//WTCG.docx";
		String dir = FileUtils.getTimeFilePath();
		String basePath = baseDir+"contract/"+dir;
		FileUtils.createDirectory(basePath);
		String wordName = FileUtils.generateRandomFilename()+".docx";
		String wordPath = basePath + wordName;	
		String pdfName = FileUtils.generateRandomFilename()+".pdf";
		String pdfPath = basePath + pdfName;
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		map.put("no", loan.getContractNo()+"-3");                       //编号
		map.put("name", ip.getMemberName());                            //姓名
		BusFins fins = finsMapper.selectByPrimaryKey(ip.getLenderId());
		map.put("bank", fins.getCname());                               //银行
		map.put("startTime", df.format(new Date()));                    //起始时间
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, loan.getTerm());
		map.put("endTime", df.format(c.getTime()));                     //截至时间
		map.put("capital", loan.getCapital());                          //金额
		map.put("nongZi", loan.getProductPrice());                      //农资金额
		map.put("idCard", ip.getIdCard());                              //身份证号
		map.put("address", ip.getAddress());                            //联系地址
		map.put("phone", ip.getPhone());                                //联系电话
		map.put("time", df.format(new Date()));                         //签署日期
		map.put("upperCapital", CnNumberUtils.toUppercase(loan.getCapital().toString()));  //大写金额
		
		JSONArray jsonArray = JSONArray.parseArray(ip.getProductListInfo());
		ArrayList<Map<String,String>> list = new ArrayList<>();
		ArrayList<Map<String,String>> list2 = new ArrayList<>();
		int zongnum = 0;
		double zongprice = 0.00;
		if(jsonArray != null && jsonArray.size() > 0){
			for(int i = 0;i < jsonArray.size();i++){
				JSONObject job = jsonArray.getJSONObject(i); 
				NjProduct productBean = productMapper.queryNjProductByPrimaryKey(job.getString("productId"));
				if(productBean != null){
					//商品附件
					Map<String,String> map3 = new HashMap<>();
					map3.put("brand", productBean.getProductBrandName());     //商品品牌
					map3.put("category", productBean.getProductCategoryName());     //商品分类
					map3.put("name", productBean.getProductName());      //商品名称
					map3.put("standard", productBean.getStandard());     //商品规格
					map3.put("hanliang", "");                            //含量规格
					map3.put("price", productBean.getPrice());           //商品单价
					map3.put("num", job.getString("productNum"));        //数量
					double a = Double.parseDouble(productBean.getPrice());
					int b = Integer.parseInt(job.getString("productNum"));
					map3.put("total", String.valueOf(a * b));        //金额
					list2.add(map3);
					zongnum += b;
					zongprice += a;
					
					SysOrg orgBean = orgMapper.selectByPrimaryKey(productBean.getOrgId());
					if(orgBean != null){
						Map<String,String> map2 = new HashMap<>();
						map2.put("name", orgBean.getFullCname());         //商户名称
						map2.put("leader", orgBean.getLeader());          //户名
						map2.put("cardNo", orgBean.getCardNo());          //帐号
						map2.put("cardBank", orgBean.getCardBank());      //银行
						list.add(map2);
					}
				}
			}
		}
		map.put("zongnum", zongnum);
		map.put("zongprice", zongprice);
		map.put("list", list);
		map.put("list2", list2);                                      //商品附件信息
		JSONObject result = WordUtils.writeWordToLocal(filePath, map, wordPath);
		if(result.getIntValue("code") == 400){
			throw new Exception("委托采购及支付协议生成失败");
		}
		result = WordToPdfUtils.convert(wordPath, pdfPath);
		if(result.getIntValue("code") == 400){
			throw new Exception(result.getString("msg"));
		}
		
		SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
		String signature = user.getUserId() + user.getUsername() + user.getPassword();
		Map<String, String> httpMap = new HashMap<String,String>();
		httpMap.put("userid", user.getUserId());
		httpMap.put("signature", DigestUtils.md5Hex(signature));
		httpMap.put("contractAmount", loan.getCapital().toString());
		httpMap.put("companyType", "LDDLHS");
		BusContractSignatory  cs = contractSignatoryMapper.selectByEname("LDDLHS");
		if(cs == null){
			throw new Exception("找不到签署机构");
		}
		httpMap.put("contractName", "委托采购及支付协议");
		httpMap.put("filePath", pdfPath);
		//////////////////////////////////////////////////
		JSONArray personList = new JSONArray();
		JSONObject company = new JSONObject();
		company.put("name", cs.getName());
		company.put("idcard", cs.getIdentityCard());
		company.put("phone", cs.getPhone());
		company.put("email", cs.getEmail());
		company.put("type", "company");	
		String location = "[{\"page\":\"2\",\"chaptes\":[{\"offsetX\":\"0.60\",\"offsetY\":\"0.60\"}]}]";
		company.put("location", JSONArray.parse(location));	
		personList.add(company);
		
		JSONObject person = new JSONObject();
		person.put("name", ip.getMemberName());
		person.put("idcard", ip.getIdCard());
		person.put("phone", ip.getPhone());
		person.put("type", "person");
		String location2 = "[{\"page\":\"2\",\"chaptes\":[{\"offsetX\":\"0.27\",\"offsetY\":\"0.33\"}]}]";
		person.put("location", JSONArray.parse(location2));
		personList.add(person);
		httpMap.put("personList", personList.toString());
		////////////////////////////////////////////////////
		String httpResult = HttpClientUtil.doPost(pieceConfig.getJunziqianUpload(), httpMap, "utf-8");
		if(StrUtils.isEmpty(httpResult)){
			throw new Exception("合同上传失败");
		}
		JSONObject resultObj = JSONObject.parseObject(httpResult);
		String isSuccess = resultObj.getString("isSuccess");
		if(!"1".equals(isSuccess)){
			throw new Exception(resultObj.getString("msg"));
		}
		
		BusApplySignFile signFile = new BusApplySignFile();
		signFile.setId(UUIDUtil.getUUID());
		signFile.setLoanId(loan.getId());
		signFile.setSequenceOrder(3);
		signFile.setTotalNum(6);
		signFile.setName("委托采购及支付协议");
		signFile.setFilePath("/contract"+dir + pdfName);
		signFile.setRemark(ip.getMemberName()+"-委托采购及支付协议");
		signFile.setStatus(2);
		signFile.setAmount(loan.getCapital());
		signFile.setChannel("LDDLHS");
		signFile.setApplyNo(resultObj.getString("applyNo"));
		signFile.setSignatories(cs.getName());
		signFile.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		signFile.setCreDate(new Date());
		signFile.setCreOperId(personId);
		signFile.setUpdDate(new Date());
		signFile.setUpdOperId(personId);
		applySignFileMapper.insert(signFile);		
					
		String[] ids = resultObj.getString("signatoriesId").split(",");
		for (int j = 0; j < ids.length; j++) {
			IntSignatories sign = new IntSignatories();
			sign.setId(ids[j]);
			sign.setLoanId(loan.getId());
			sign.setApplySignFileId(signFile.getId());
			signatoriesMapper.updateByPrimaryKeySelective(sign);
		}
	}
	
	/**
	 * 土地流转合同(农户-首信)（第四个签）
	 */
	@Override
	public void tdlz(BusIntoPiece ip, BusLoan loan,String personId) throws Exception {
		BusApplySignFile check = new BusApplySignFile();
		check.setLoanId(loan.getId());
		check.setSequenceOrder(4);
		if(applySignFileMapper.checkExist(check) != null){
			return ;
		}
		String filePath = baseDir + "templates//TDLZ.docx";
		String dir = FileUtils.getTimeFilePath();
		String basePath = baseDir+"contract/"+dir;
		FileUtils.createDirectory(basePath);
		String wordName = FileUtils.generateRandomFilename()+".docx";
		String wordPath = basePath + wordName;	
		String pdfName = FileUtils.generateRandomFilename()+".pdf";
		String pdfPath = basePath + pdfName;
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
//		map.put("no", loan.getContractNo() + "-2");   //合同编号
		map.put("address", ip.getAddress());          //联系地址
		map.put("name", ip.getMemberName());          //乙方
		map.put("time", df.format(new Date()));       //日期
		BusFamilyCapital capitalBean = new BusFamilyCapital();
		capitalBean.setIntoPieceId(ip.getId());
		capitalBean = familyCapitalMapper.selectByIntoPieceId(capitalBean);
		if(capitalBean != null){
			map.put("landId", capitalBean.getLandCertId()); 
		}else{
			map.put("landId", "(无)");
		}
		double landArea = 0.00;
		List<BusTransferLand> transferLandList = transferLandMapper.selectByIpId(ip.getId());
		if(transferLandList != null && transferLandList.size() > 0){
			for(BusTransferLand transferLandBean : transferLandList){
				landArea += Double.parseDouble(transferLandBean.getLandArea());
			}
		}
		map.put("landArea", landArea);                //土地亩数
		ArrayList<Map<String,String>> list = new ArrayList<>();
		BusFamilySituation familyBean = new BusFamilySituation();
		familyBean.setIntoPieceId(ip.getId());
		List<BusFamilySituation> familyList = familySituationMapper.queryByIntopId(familyBean);
		if(familyList != null && familyList.size() > 0){
			for(BusFamilySituation familyBean1 : familyList){
				Map<String,String> map2 = new HashMap<>();
				map2.put("name", familyBean1.getName());
				if(familyBean1.getType() == 1){
					map2.put("type", "父亲");
				}else if(familyBean1.getType() == 2){
					map2.put("type", "母亲");
				}else if(familyBean1.getType() == 3){
					map2.put("type", "配偶");
				}else if(familyBean1.getType() == 4){
					map2.put("type", "第一子女");
				}else if(familyBean1.getType() == 5){
					map2.put("type", "第二子女");
				}else if(familyBean1.getType() == 6){
					map2.put("type", "第三子女");
				}else if(familyBean1.getType() == 7){
					map2.put("type", "其他家属");
				}
				map2.put("sign", " ");
				map2.put("beizhu", " ");
				list.add(map2);
			}
		}
		
		map.put("list", list);
		JSONObject result = WordUtils.writeWordToLocal(filePath, map, wordPath);
		if(result.getIntValue("code") == 400){
			throw new Exception("土地流转合同生成失败");
		}
		result = WordToPdfUtils.convert(wordPath, pdfPath);
		if(result.getIntValue("code") == 400){
			throw new Exception(result.getString("msg"));
		}
		
		SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
		String signature = user.getUserId() + user.getUsername() + user.getPassword();
		Map<String, String> httpMap = new HashMap<String,String>();
		httpMap.put("userid", user.getUserId());
		httpMap.put("signature", DigestUtils.md5Hex(signature));
		httpMap.put("contractAmount", loan.getCapital().toString());
		httpMap.put("companyType", "HLJSX");
		BusContractSignatory  cs = contractSignatoryMapper.selectByEname("HLJSX");
		if(cs == null){
			throw new Exception("没有找到该企业");
		}
		httpMap.put("contractName", "土地流转合同");
		httpMap.put("filePath", pdfPath);
		JSONArray personList = new JSONArray();
		JSONObject company = new JSONObject();
		company.put("name", cs.getName());
		company.put("idcard", cs.getIdentityCard());
		company.put("phone", cs.getPhone());
		company.put("email", cs.getEmail());
		company.put("type", "company");	
		String location = "[{\"page\":\"3\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":\"0.70\"}]}]";
		company.put("location", JSONArray.parse(location));	
		personList.add(company);
		
		JSONObject person = new JSONObject();
		person.put("name", ip.getMemberName());
		person.put("idcard", ip.getIdCard());
		person.put("phone", ip.getPhone());
		person.put("type", "person");
		String location2 = "[{\"page\":\"3\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":\"0.55\"}]}]";
		person.put("location", JSONArray.parse(location2));
		personList.add(person);
		
		if(familyList != null && familyList.size() > 0){
			float y = 0.10F;
			for(int i = 0;i < familyList.size();i++){
				JSONObject family = new JSONObject();
				family.put("name", familyList.get(i).getName());
				family.put("idcard", familyList.get(i).getIdCard());
				family.put("phone", familyList.get(i).getPhone());
				family.put("type", "person");
				family.put("location", JSONArray.parse("[{\"page\":\"3\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":"+y+"}]}]"));	
				personList.add(family);
				y = y + 0.04F;
			}
		}
		
		httpMap.put("personList", personList.toString());
		
		String httpResult = HttpClientUtil.doPost(pieceConfig.getJunziqianUpload(), httpMap, "utf-8");
		if(StrUtils.isEmpty(httpResult)){
			throw new Exception("合同上传失败");
		}
		JSONObject resultObj = JSONObject.parseObject(httpResult);
		String isSuccess = resultObj.getString("isSuccess");
		if(!"1".equals(isSuccess)){
			throw new Exception(resultObj.getString("msg"));
		}
		
		BusApplySignFile signFile = new BusApplySignFile();
		signFile.setId(UUIDUtil.getUUID());
		signFile.setLoanId(loan.getId());
		signFile.setSequenceOrder(4);
		signFile.setTotalNum(6);
		signFile.setName("土地流转合同");
		signFile.setFilePath("/contract"+dir + pdfName);
		signFile.setRemark(ip.getMemberName() + "-土地流转合同");
		signFile.setStatus(2);
		signFile.setAmount(loan.getCapital());
		signFile.setChannel("HLJSX");
		signFile.setApplyNo(resultObj.getString("applyNo"));
		signFile.setSignatories(cs.getName());
		signFile.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		signFile.setCreDate(new Date());
		signFile.setCreOperId(personId);
		signFile.setUpdDate(new Date());
		signFile.setUpdOperId(personId);
		applySignFileMapper.insert(signFile);		
					
		String[] ids = resultObj.getString("signatoriesId").split(",");
		for (int j = 0; j < ids.length; j++) {
			IntSignatories sign = new IntSignatories();
			sign.setId(ids[j]);
			sign.setLoanId(loan.getId());
			sign.setApplySignFileId(signFile.getId());
			signatoriesMapper.updateByPrimaryKeySelective(sign);
		}
	}
	
	/**
	 * 抵押反担保协议（第五个签）
	 */
	@Override
	public void dyfdb(BusIntoPiece ip,BusLoan loan,String personId) throws Exception{
		BusApplySignFile check = new BusApplySignFile();
		check.setLoanId(loan.getId());
		check.setSequenceOrder(5);
		if(applySignFileMapper.checkExist(check) != null){
			return ;
		}
		String filePath = baseDir + "templates//DYFDB.docx";
		String dir = FileUtils.getTimeFilePath();
		String basePath = baseDir+"contract/"+dir;
		FileUtils.createDirectory(basePath);
		String wordName = FileUtils.generateRandomFilename()+".docx";
		String wordPath = basePath + wordName;	
		String pdfName = FileUtils.generateRandomFilename()+".pdf";
		String pdfPath = basePath + pdfName;
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		map.put("no", loan.getContractNo()+"-5");                       //编号
		map.put("name", ip.getMemberName());                            //姓名
		map.put("idCard", ip.getIdCard());                              //身份证号
		map.put("address", ip.getAddress());                            //地址
		map.put("fwhtno", loan.getContractNo()+"-2");                   //服务合同编号
		map.put("dbhno", loan.getContractNo()+"-1");                    //担保函合同编号
		BusFins fins = finsMapper.selectByPrimaryKey(ip.getLenderId());
		map.put("bank", fins.getCname());                               //银行
		map.put("capital", loan.getCapital());                          //金额
		map.put("upperCapital", CnNumberUtils.toUppercase(loan.getCapital().toString()));  //大写金额
		map.put("bond", new BigDecimal("0.03").multiply(loan.getCapital()).toString());    //担保费
		map.put("time", df.format(new Date()));
		//抵押清单数据
		ArrayList<Map<String,String>> list = new ArrayList<>();
		List<BusTransferLand> ferlandList = transferLandMapper.selectByIpId(ip.getId());
		if(ferlandList != null && ferlandList.size() > 0){
			String address1 = ip.getAddress().substring(0, ip.getAddress().indexOf("村")+1);
			for(int i = 0;i < ferlandList.size();i++){
				Map<String,String> map2 = new HashMap<>();
				map2.put("no", String.valueOf(i+1));
				map2.put("name", "地上物"+ferlandList.get(i).getLandArea()+"公顷土地收益");
				map2.put("time", String.valueOf(18));
				map2.put("membername", ip.getMemberName());
				map2.put("detaile", ferlandList.get(i).getLandArea()+"公顷2019年度地上作物预期收益;"+"质量状况良好;位于"+address1+"东边界至"+ferlandList.get(i).getEasternBorder()
						+"、西边界至"+ferlandList.get(i).getWesternBorder()+"、北边界至"+ferlandList.get(i).getNorthernBorder()
						+"、南边界至"+ferlandList.get(i).getSouthernBorder());
				list.add(map2);
			}
		}
		map.put("list", list);
		
		JSONObject result = WordUtils.writeWordToLocal(filePath, map, wordPath);
		if(result.getIntValue("code") == 400){
			throw new Exception("抵押反担保协议生成失败");
		}
		result = WordToPdfUtils.convert(wordPath, pdfPath);
		if(result.getIntValue("code") == 400){
			throw new Exception(result.getString("msg"));
		}
		
		SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
		String signature = user.getUserId() + user.getUsername() + user.getPassword();
		Map<String, String> httpMap = new HashMap<String,String>();
		httpMap.put("userid", user.getUserId());
		httpMap.put("signature", DigestUtils.md5Hex(signature));
		httpMap.put("contractAmount", loan.getCapital().toString());
		httpMap.put("companyType", "HLJSX");
		BusContractSignatory  cs = contractSignatoryMapper.selectByEname("HLJSX");
		httpMap.put("contractName", "抵押反担保协议");
		httpMap.put("filePath", pdfPath);
		JSONArray personList = new JSONArray();
		JSONObject company = new JSONObject();
		company.put("name", cs.getName());
		company.put("idcard", cs.getIdentityCard());
		company.put("phone", cs.getPhone());
		company.put("email", cs.getEmail());
		company.put("type", "company");	
		Double offsetY = 0.50 + ferlandList.size() * 0.05;
		Double offsetY2 = 0.60 + ferlandList.size() * 0.05;
//		String location = "[{\"page\":\"8\",\"chaptes\":[{\"offsetX\":\"0.35\",\"offsetY\":\"0.65\"}]},"
//				+ "{\"page\":\"10\",\"chaptes\":[{\"offsetX\":\"0.15\",\"offsetY\":\"+"+offsetY+"\"}]}]";
		String location = "[{\"page\":\"8\",\"chaptes\":[{\"offsetX\":\"0.35\",\"offsetY\":\"0.60\"}]}]";
		company.put("location", JSONArray.parse(location));	
		personList.add(company);
		
		JSONObject person = new JSONObject();
		person.put("name", ip.getMemberName());
		person.put("idcard", ip.getIdCard());
		person.put("phone", ip.getPhone());
		person.put("type", "person");
//		String location2 = "[{\"page\":\"8\",\"chaptes\":[{\"offsetX\":\"0.35\",\"offsetY\":\"0.85\"}]},"
//				+ "{\"page\":\"10\",\"chaptes\":[{\"offsetX\":\"0.15\",\"offsetY\":\"+"+offsetY2+"\"}]}]";
		String location2 = "[{\"page\":\"8\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":\"0.70\"}]}]";
		person.put("location", JSONArray.parse(location2));
		personList.add(person);
		
		httpMap.put("personList", personList.toString());
		
		String httpResult = HttpClientUtil.doPost(pieceConfig.getJunziqianUpload(), httpMap, "utf-8");
		if(StrUtils.isEmpty(httpResult)){
			throw new Exception("合同上传失败");
		}
		JSONObject resultObj = JSONObject.parseObject(httpResult);
		String isSuccess = resultObj.getString("isSuccess");
		if(!"1".equals(isSuccess)){
			throw new Exception(resultObj.getString("msg"));
		}
		
		BusApplySignFile signFile = new BusApplySignFile();
		signFile.setId(UUIDUtil.getUUID());
		signFile.setLoanId(loan.getId());
		signFile.setSequenceOrder(5);
		signFile.setTotalNum(6);
		signFile.setName("抵押反担保协议");
		signFile.setFilePath("/contract"+dir + pdfName);
		signFile.setRemark(ip.getMemberName()+"-抵押反担保协议");
		signFile.setStatus(2);
		signFile.setAmount(loan.getCapital());
		signFile.setChannel("HLJSX");
		signFile.setApplyNo(resultObj.getString("applyNo"));
		signFile.setSignatories(cs.getName());
		signFile.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		signFile.setCreDate(new Date());
		signFile.setCreOperId(personId);
		signFile.setUpdDate(new Date());
		signFile.setUpdOperId(personId);
		applySignFileMapper.insert(signFile);		
					
		String[] ids = resultObj.getString("signatoriesId").split(",");
		for (int j = 0; j < ids.length; j++) {
			IntSignatories sign = new IntSignatories();
			sign.setId(ids[j]);
			sign.setLoanId(loan.getId());
			sign.setApplySignFileId(signFile.getId());
			signatoriesMapper.updateByPrimaryKeySelective(sign);
		}
	}
	
	
	/**
	 * 个人无限连带责任保证承诺函（第六个签）
	 */
	@Override
	public void wxld(BusIntoPiece ip,BusLoan loan,String personId) throws Exception{
		BusApplySignFile check = new BusApplySignFile();
		check.setLoanId(loan.getId());
		check.setSequenceOrder(6);
		if(applySignFileMapper.checkExist(check) != null){
			return ;
		}
		String filePath = baseDir + "templates//WXLD.docx";
		String dir = FileUtils.getTimeFilePath();
		String basePath = baseDir+"contract/"+dir;
		FileUtils.createDirectory(basePath);
		String wordName = FileUtils.generateRandomFilename()+".docx";
		String wordPath = basePath + wordName;	
		String pdfName = FileUtils.generateRandomFilename()+".pdf";
		String pdfPath = basePath + pdfName;
		Map<String, Object> map = new HashMap<String,Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		map.put("no", loan.getContractNo()+"-6");                       //编号
		map.put("name", ip.getMemberName());                              //姓名
		BusFins fins = finsMapper.selectByPrimaryKey(ip.getLenderId());
		map.put("bank", fins.getCname());                                 //银行
		map.put("dbhno", loan.getContractNo()+"-1");                    //担保函合同编号
		map.put("capital", loan.getCapital());                          //金额
		map.put("upperCapital", CnNumberUtils.toUppercase(loan.getCapital().toString())); //大写金额
		map.put("idCard", ip.getIdCard());                              //身份证号
		map.put("address", ip.getAddress());                            //地址
		map.put("phone", ip.getPhone());                            //电话
		map.put("time", df.format(new Date()));
		
		//查询共同借款人LIST
		ArrayList<Map<String,String>> list = new ArrayList<>();
		BusFamilySituation familySituation = new BusFamilySituation();
		familySituation.setIntoPieceId(ip.getId());
		familySituation.setCoBorrower(1);
		List<BusFamilySituation> familyList = familySituationMapper.queryCoBorrower(familySituation);
		if(familyList != null && familyList.size() > 0){
			for(int i = 0;i < familyList.size();i++){
				Map<String,String> map2 = new HashMap<String,String>();
				map2.put("name", "");
				map2.put("idCard", familyList.get(i).getIdCard());
				map2.put("phone", familyList.get(i).getPhone());
				if(familyList.get(i).getStatus() != null && familyList.get(i).getStatus() == 1){
					map2.put("address", ip.getAddress());
				}else{
					map2.put("address", familyList.get(i).getLiveAddress());
				}
				map2.put("type", "person");
				list.add(map2);
			}
		}
		map.put("list", list);
		JSONObject result = WordUtils.writeWordToLocal(filePath, map, wordPath);
		if(result.getIntValue("code") == 400){
			throw new Exception("个人无限连带责任保证承诺函生成失败");
		}
		result = WordToPdfUtils.convert(wordPath, pdfPath);
		if(result.getIntValue("code") == 400){
			throw new Exception(result.getString("msg"));
		}
		
		SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
		String signature = user.getUserId() + user.getUsername() + user.getPassword();
		Map<String, String> httpMap = new HashMap<String,String>();
		httpMap.put("userid", user.getUserId());
		httpMap.put("signature", DigestUtils.md5Hex(signature));
		httpMap.put("contractAmount", loan.getCapital().toString());
		httpMap.put("companyType", "HLJSX");
		BusContractSignatory  cs = contractSignatoryMapper.selectByEname("HLJSX");
		httpMap.put("contractName", "个人无限连带责任保证承诺函");
		httpMap.put("filePath", pdfPath);
		
//		JSONObject company = new JSONObject();
//		company.put("name", cs.getName());
//		company.put("idcard", cs.getIdentityCard());
//		company.put("phone", cs.getPhone());
//		company.put("email", cs.getEmail());
//		company.put("type", "company");	
//		String location = "[{\"page\":\"2\",\"chaptes\":[{\"offsetX\":\"0.30\",\"offsetY\":\"0.70\"}]}]";
//		company.put("location", JSONArray.parse(location));	
//		personList.add(company);
		JSONArray personList = new JSONArray();
		JSONObject person = new JSONObject();
		person.put("name", ip.getMemberName());
		person.put("idcard", ip.getIdCard());
		person.put("phone", ip.getPhone());
		person.put("type", "person");
		String location2 = "[{\"page\":\"2\",\"chaptes\":[{\"offsetX\":\"0.15\",\"offsetY\":\"0.20\"}]}]";
		person.put("location", JSONArray.parse(location2));
		personList.add(person);
		if(familyList != null && familyList.size() > 0){
			DecimalFormat df2 = new DecimalFormat("0.00");
			double y = 0.20;
			for(int i = 0;i < familyList.size();i++){
				y += 0.05;
				JSONObject family = new JSONObject();
				family.put("name", familyList.get(i).getName());
				family.put("idcard", familyList.get(i).getIdCard());
				family.put("phone", familyList.get(i).getPhone());
				family.put("type", "person");
				String location3 = "[{\"page\":\"2\",\"chaptes\":[{\"offsetX\":\"0.15\",\"offsetY\":\""+df2.format(y)+"\"}]}]";
				family.put("location", JSONArray.parse(location3));
				personList.add(family);
			}
		}
	 	httpMap.put("personList", personList.toString());
		
		String httpResult = HttpClientUtil.doPost(pieceConfig.getJunziqianUpload(), httpMap, "utf-8");
		if(StrUtils.isEmpty(httpResult)){
			throw new Exception("合同上传失败");
		}
		JSONObject resultObj = JSONObject.parseObject(httpResult);
		String isSuccess = resultObj.getString("isSuccess");
		if(!"1".equals(isSuccess)){
			throw new Exception(resultObj.getString("msg"));
		}
		
		BusApplySignFile signFile = new BusApplySignFile();
		signFile.setId(UUIDUtil.getUUID());
		signFile.setLoanId(loan.getId());
		signFile.setSequenceOrder(6);
		signFile.setTotalNum(6);
		signFile.setName("个人无限连带责任保证承诺函");
		signFile.setFilePath("/contract"+dir + pdfName);
		signFile.setFileDownpath("/contract"+dir + pdfName);
		signFile.setRemark(ip.getMemberName()+"-个人无限连带责任保证承诺函");
		signFile.setStatus(3);
		signFile.setAmount(loan.getCapital());
		signFile.setChannel("HLJSX");
		signFile.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		signFile.setCreDate(new Date());
		signFile.setCreOperId(personId);
		signFile.setUpdDate(new Date());
		signFile.setUpdOperId(personId);
		applySignFileMapper.insert(signFile);
		
		String[] ids = resultObj.getString("signatoriesId").split(",");
		for (int j = 0; j < ids.length; j++) {
			IntSignatories sign = new IntSignatories();
			sign.setId(ids[j]);
			sign.setLoanId(loan.getId());
			sign.setApplySignFileId(signFile.getId());
			signatoriesMapper.updateByPrimaryKeySelective(sign);
		}
	}
	
}
