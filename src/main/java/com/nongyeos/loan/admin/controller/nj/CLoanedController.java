package com.nongyeos.loan.admin.controller.nj;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.ILoanDetailService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/nj/loaned")
public class CLoanedController {
	
	@Autowired
	private ILoanDetailService loanDetailService;
	@Autowired
	private IWebUserService userService;
	@Resource  
    private IPersonService personService;
	@Autowired
	private IntoPieceConfig pieceConfig;
	@Autowired
	private SysWebUserMapper userMapper;
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	/**
	 * 还款列表
	 * @param loanId
	 * @param response
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public JSONObject detail(String loanId ,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			if(StringUtils.isEmpty(loanId)){
				throw new Exception("贷款id为空");
			}
			
			BusLoanDetail loanDetail = new BusLoanDetail();
			loanDetail.setLoanId(loanId);
			loanDetail.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			//查询贷款明细
			List<Map<String,Object>> detail = loanDetailService.queryLoanDetail(loanDetail);
			
			List<Map<String,Object>> data = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Map<String, Object> map : detail) {
				Date date = sdf.parse(String.valueOf(map.get("deadline_date")));
				String dateFormat = sdf.format(date);
				
				//应还金额
				BigDecimal capital_intrest = BigDecimal.ZERO;
				capital_intrest = capital_intrest.add(new BigDecimal(map.get("capital").toString()).add(new BigDecimal(map.get("intrest").toString())));
				//实际还款
				BigDecimal receive = BigDecimal.ZERO;
				receive = receive.add(new BigDecimal(map.get("receive_capital").toString()).add(new BigDecimal(map.get("receive_interest").toString())).add(new BigDecimal(map.get("receive_overdue").toString())));
				
				Map<String,Object> m = new HashMap<>();
				//应还金额
				m.put("capital_intrest", capital_intrest);
				//实际还款
				m.put("receive", receive);
				//状态
				m.put("status", map.get("status").toString().equals("1")?"待还款": 
					map.get("status").toString().equals("2")?"还款中": 
					map.get("status").toString().equals("3")?"还款失败":"还款完成");
				//还款期限
				m.put("deadline_date", dateFormat);
				//贷款id
				m.put("loan_id", map.get("loan_id"));
				data.add(m);
			}
			
			retJson.put("data", data);
			retJson.put("message", "贷款明细列表查询成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e);
			response.setStatus(400);
		}
		return retJson;
	}
	
	/**
	 * 先锋免短免密，新协议支付
	 * @param loanId
	 * @param response
	 * @return
	 */
	@RequestMapping("/xfNoSmsNoContractNo")
	@ResponseBody
	public JSONObject xfNoSmsNoContractNo(BusLoanDetail loanDetail, HttpServletResponse response, HttpServletRequest request){
		JSONObject retJson = new JSONObject();
		try {
			String channel = request.getHeader("channel");
			if(StrUtils.isEmpty(channel)){
				retJson.put("message", "版本错误");
				response.setStatus(400);
				return retJson;
			}
			
			if(loanDetail== null || StringUtils.isEmpty(loanDetail.getLoanId())){
				throw new Exception("贷款id为空");
			}
			SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("ids", loanDetail.getLoanId());
			map.put("companyType", channel);//公司版本
			//先锋免短免密，新协议支付
			String xfNoSmsNoContractNo = HttpClientUtil.doPost(pieceConfig.getXfNoSmsNoContractNo(), map, "utf-8");
			
			JSONObject httpResult = JSONObject.parseObject(xfNoSmsNoContractNo);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				//SysPerson sysPerson = personService.selectByUserIdAndIsdefault(user.getUserId());
				//更新还款信息和流程
				//JSONObject object = loanDetailService.underLineRepay(loanDetail.getId(), sysPerson.getPersonId());

				retJson.put("message", httpResult.get("msg"));
			}else{
				throw new Exception("先锋新协议支付调用失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e);
			response.setStatus(400);
		}
		return retJson;
	}
	
	/**
	 * 君子签人脸识别
	 * @param request
	 * @param file
	 * @param response
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/personFaceVerify")
	@ResponseBody
	public JSONObject personFaceVerify(HttpServletRequest request, MultipartFile file, HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		
		try {
			String channel = request.getHeader("channel");
			if(StrUtils.isEmpty(channel)){
				retJson.put("message", "版本错误");
				response.setStatus(400);
				return retJson;
			}
			
			request.setCharacterEncoding("utf-8");
			
			String fullname = request.getParameter("fullname");
    		String identityCard = request.getParameter("identityCard");
    		if(StringUtils.isEmpty(fullname)){
    			throw new Exception("识别人姓名为空");
    		}
    		if(StringUtils.isEmpty(identityCard)){
    			throw new Exception("识别人身份证号为空");
    		}
			
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
	        String personFace = "";
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost(pieceConfig.getPersonFace());
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            
            //拼接人脸识别参数
            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
            builder.addTextBody("fullname", fullname, ContentType.create(HTTP.PLAIN_TEXT_TYPE,HTTP.UTF_8));
            builder.addTextBody("identityCard", identityCard);
            builder.addTextBody("companyType", channel);//公司版本
            builder.addTextBody("userid", user.getUserId());
            builder.addTextBody("signature", DigestUtils.md5Hex(signature));
            
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse responseFile = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = responseFile.getEntity();
            //人脸识别
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	personFace = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
			JSONObject httpResult = JSONObject.parseObject(personFace);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				response.setStatus(200);
				retJson.put("message", "人脸识别成功");
			}else{
				throw new Exception("人脸识别失败");
			}
		} catch (Exception e) {
			response.setStatus(400);
			retJson.put("message", "人脸识别失败");
		}
		return retJson;
	}
	
	/**
	 * 小程序君子签人脸识别
	 * @param request
	 * @param file
	 * @param response
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/wxpersonFaceVerify")
	@ResponseBody
	public JSONObject wxPersonFaceVerify(HttpServletRequest request, String key, HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		
		try {
			String channel = request.getHeader("channel");
			if(StrUtils.isEmpty(channel)){
				retJson.put("message", "版本错误");
				response.setStatus(400);
				return retJson;
			}
			
			request.setCharacterEncoding("utf-8");
			
			String fullname = request.getParameter("fullname");
    		String identityCard = request.getParameter("identityCard");
    		if(StringUtils.isEmpty(fullname)){
    			throw new Exception("识别人姓名为空");
    		}
    		if(StringUtils.isEmpty(identityCard)){
    			throw new Exception("识别人身份证号为空");
    		}
    		if(StringUtils.isEmpty(key)){
    			throw new Exception("识别人身份证图片为空");
    		}
    		
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
	        String personFace = "";
	        
	        String filePath = baseDir+"contract/"+key;
			File file = new File(filePath);
            String fileName = file.getName();
            HttpPost httpPost = new HttpPost(pieceConfig.getPersonFace());
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            
            //拼接人脸识别参数
            builder.addBinaryBody("file", new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
            builder.addTextBody("fullname", fullname, ContentType.create(HTTP.PLAIN_TEXT_TYPE,HTTP.UTF_8));
            builder.addTextBody("identityCard", identityCard);
            builder.addTextBody("companyType", channel);//公司版本
            builder.addTextBody("userid", user.getUserId());
            builder.addTextBody("signature", DigestUtils.md5Hex(signature));
            
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse responseFile = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = responseFile.getEntity();
            //人脸识别
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	personFace = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
			JSONObject httpResult = JSONObject.parseObject(personFace);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				response.setStatus(200);
				retJson.put("message", "人脸识别成功");
			}else{
				throw new Exception("人脸识别失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("message", "人脸识别失败");
		}
		return retJson;
	}
}
