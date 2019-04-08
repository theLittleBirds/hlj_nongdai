package com.nongyeos.loan.admin.controller.nj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.bcel.generic.IfInstruction;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.SysCarouselFigure;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.ICarouselFigureService;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanDetailService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.QiNiuUtil;

@Controller
@RequestMapping("/nj/messageReminder")
public class CMessageReminderController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CMessageReminderController.class);
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private ICarouselFigureService carouselFigureService;
	
	@Autowired
	private ILoanDetailService loanDetailService;
	
	@Autowired 
	private ILoanService loanService;
	
	@Autowired
	private IBusFinsService finsService;
	
	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;
	
	@RequestMapping("/list")
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		JSONArray arr = new JSONArray();
		String loginId = (String)request.getAttribute("loginId");
		BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
		String userId = null;
		if(memberLogin==null){
			userId = loginId;
		}
		try {
			Map<Date, Object> map = new HashMap<Date, Object>();
			List<Date> listTime = new ArrayList<Date>();
			BusMessageReminder messageReminder = new BusMessageReminder();
			messageReminder.setMemberLoginId(loginId);
			messageReminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			List<BusMessageReminder> list = messageReminderService.selectByCondition(messageReminder);
			for(int i=0;i<list.size();i++){
				BusMessageReminder r = list.get(i);
				//如果是小程序可以查看小程序支付消息
				if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform"))&&r.getType().equals(Constants.WEI_XIN_SERVICE_PAY) ){
					continue;
				}else if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& !"wx".equals(request.getHeader("platform"))&&r.getType().equals(Constants.XIAO_CHENG_XU_SERVICE_PAY) ){
					continue;
				}
				JSONObject obj = new JSONObject();
				obj.put("title", r.getTitle());
				obj.put("content", r.getContent());
				obj.put("type", r.getType());
				if(StringUtils.isNotEmpty(r.getIntoPieceId())){
				obj.put("intoPieceId", r.getIntoPieceId());
				}
				if(StringUtils.isNotEmpty(r.getApplyNo())){
					obj.put("apply_no", r.getApplyNo());
				}
				String time = "";
				Date date = r.getCreateDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				if(sdf.format(new Date()).equals(sdf.format(date))){
					sdf = new SimpleDateFormat("HH:mm");
					time = sdf.format(date);
				}else{
					sdf = new SimpleDateFormat("MM/dd");
					time = sdf.format(date);
				}
				obj.put("time", time);
				map.put(saveRecord(listTime, date), obj);
				listTime.add(saveRecord(listTime, date));
			}
			
//			long currentTimeMillis = System.currentTimeMillis();
//			long rightnow = currentTimeMillis-5*1000*24*60*60;
//			Date rightNow = new Date(rightnow);
			
			if(StringUtils.isEmpty(userId)){
				//个人首页消息
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("loginId", loginId);
				map1.put("isDelete", Constants.COMMON_ISNOT_DELETE);
				map1.put("channel", memberLogin.getChannel());
				map1.put("date", new Date(DateUtils.getNowDate().getTime()-5*24*60*60*1000l) );
				List<IntoPieceMap> intoPieces = intoPieceService.selectAllIntoPieces(map1);

				for(int i=0;i<intoPieces.size();i++){
					IntoPieceMap intoPiece = intoPieces.get(i);
					JSONObject completing = new JSONObject();
					String status = null;
					if(intoPiece.getStatus()==null){
						status="";
					}else{
						status=intoPiece.getStatus();
					}
					switch (status) {
					case "1":
						completing.put("title", "请补充申请材料");
						if(intoPiece.getCapital()==null){
							completing.put("content", "您向"+intoPiece.getFullCname()+"申请的贷款请尽快补全资料");
						}else{
							completing.put("content", "您向"+intoPiece.getFullCname()+"申请的金额为："+intoPiece.getCapital()+"元的贷款请尽快补全资料");
						}
						completing.put("intoPieceId", intoPiece.getId());
						completing.put("type", Constants.ADD_INFORMATION);
						String time3 = "";
						Date date3 = intoPiece.getStatusUpdate();
						SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
						if(sdf3.format(new Date()).equals(sdf3.format(date3))){
							sdf3 = new SimpleDateFormat("HH:mm");
							time3 = sdf3.format(date3);
						}else{
							sdf3 = new SimpleDateFormat("MM/dd");
							time3 = sdf3.format(date3);
						}
						completing.put("time", time3);
						map.put(saveRecord(listTime, date3), completing);
						listTime.add(saveRecord(listTime, date3));
						break;
					case "8":
						/*if(request.getHeader("channel").equals("SX")){
							JSONObject completingp = new JSONObject();
							completingp.put("title", "放款签约提示");
							completingp.put("content", "您在"+intoPiece.getBank()+"的土地抵押贷款已通过银行审核，请尽快至银行签约！");
							completingp.put("intoPieceId", intoPiece.getId());
							completingp.put("type", Constants.PENDING_SIGN_REMIND);
							String timep = "";
							Date datep = intoPiece.getStatusUpdate();
							SimpleDateFormat sdfp = new SimpleDateFormat("yyyyMMdd");
							if(sdfp.format(new Date()).equals(sdfp.format(datep))){
								sdfp = new SimpleDateFormat("HH:mm");
								timep = sdfp.format(datep);
							}else{
								sdfp = new SimpleDateFormat("MM/dd");
								timep = sdfp.format(datep);
							}
							completingp.put("time", timep);
							map.put(saveRecord(listTime, datep), completingp);
							listTime.add(saveRecord(listTime, datep));
						}*/
						break;
					case "5":
					case "6":
					case "7":
					case "9":
					case "10":
						if(request.getHeader("channel").equals("AJ")){
							break;
						}
						/*completing.put("title", "贷款已通过审核");
						completing.put("content", "您向"+intoPiece.getFullCname()+"申请的贷款终审通过");
						completing.put("intoPieceId", intoPiece.getId());
						completing.put("type", Constants.LAST);
						String time4 = "";
						Date date4 = intoPiece.getStatusUpdate();
						SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
						if(sdf4.format(new Date()).equals(sdf4.format(date4))){
							sdf4 = new SimpleDateFormat("HH:mm");
							time4 = sdf4.format(date4);
						}else{
							sdf4 = new SimpleDateFormat("MM/dd");
							time4 = sdf4.format(date4);
						}
						completing.put("time", time4);
						map.put(saveRecord(listTime, date4), completing);
						listTime.add(saveRecord(listTime, date4));*/
						break;
					case "11":
						if(request.getHeader("channel").equals("AJ")){
							break;
						}
//						本期还款完成
						/*completing.put("title", "请上传出账单");
						completing.put("content", "您向"+intoPiece.getFullCname()+"申请的贷款已放款");
						completing.put("intoPieceId", intoPiece.getId());
						completing.put("type", Constants.REPAYMENT);
						String time5 = "";
						Date date5 = intoPiece.getStatusUpdate();
						SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
						if(sdf5.format(new Date()).equals(sdf5.format(date5))){
							sdf5 = new SimpleDateFormat("HH:mm");
							time5 = sdf5.format(date5);
						}else{
							sdf5 = new SimpleDateFormat("MM/dd");
							time5 = sdf5.format(date5);
						}
						completing.put("time", time5);
						map.put(saveRecord(listTime, date5), completing);
						listTime.add(saveRecord(listTime, date5));
						BusLoanDetail loanDetailcomp = loanDetailService.selectLastComplete(intoPiece.getId());
						if(loanDetailcomp!=null){
							BusLoan loan = loanService.selectByPrimaryKey(loanDetailcomp.getLoanId());
							JSONObject complete = new JSONObject();
							complete.put("title", "本期还款完成");
							complete.put("content", "第"+loanDetailcomp.getSort()+"期还款完成");
							complete.put("intoPieceId", intoPiece.getId());
							complete.put("type", Constants.REPAYMENTED);
							complete.put("money", loan.getCapital());
							complete.put("create_date", DateUtils.format(intoPiece.getCreDate(), "yyyy-MM-dd"));
							String time11com = "";
							Date date11com = loanDetailcomp.getCreDate();
							SimpleDateFormat sdf11com = new SimpleDateFormat("yyyyMMdd");
							if(sdf11com.format(new Date()).equals(sdf11com.format(date11com))){
								sdf11com = new SimpleDateFormat("HH:mm");
								time11com = sdf11com.format(date11com);
							}else{
								sdf11com = new SimpleDateFormat("MM/dd");
								time11com = sdf11com.format(date11com);
							}
							complete.put("time", time11com);
							map.put(saveRecord(listTime, date11com), complete);
							listTime.add(saveRecord(listTime, date11com));
						
						}*/
						break;
					default:
						break;
					}
				}
				Collections.sort(listTime);
				if(listTime!=null&&listTime.size()>0){
					for(int i = listTime.size()-1;i>=0;i--){
						arr.add(map.get(listTime.get(i)));
					}
				}
			}else{
				//商户首页消息
				List<IntoPieceMap> intoPieces = intoPieceService.selectByUserIdAndStatus(userId, Constants.COMMON_ISNOT_DELETE);
				for(int i=0;i<intoPieces.size();i++){
					IntoPieceMap intoPiece = intoPieces.get(i);
					JSONObject completing = new JSONObject();
					String status = null;
					if(intoPiece.getStatus()==null){
						status="";
					}else{
						status=intoPiece.getStatus();
					}
					switch (status) {
					case "1":
						completing.put("title", "请补充"+intoPiece.getMemberName()+"的申请材料");
						if(intoPiece.getCapital()==null){
							completing.put("content", intoPiece.getMemberName()+"向"+intoPiece.getFullCname()+"申请贷款请尽快补全资料" );
						}else{
							completing.put("content", intoPiece.getMemberName()+"向"+intoPiece.getFullCname()+"申请的金额为："+intoPiece.getCapital()+"元的贷款请尽快补全资料" );
						}
						completing.put("intoPieceId", intoPiece.getId());
						completing.put("type", Constants.ADD_INFORMATION);
						String time3 = "";
						Date date3 = intoPiece.getStatusUpdate();
						SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
						if(sdf3.format(new Date()).equals(sdf3.format(date3))){
							sdf3 = new SimpleDateFormat("HH:mm");
							time3 = sdf3.format(date3);
						}else{
							sdf3 = new SimpleDateFormat("MM/dd");
							time3 = sdf3.format(date3);
						}
						completing.put("time", time3);
						map.put(saveRecord(listTime, date3), completing);
						listTime.add(saveRecord(listTime, date3));
						break;
					/*case "9":
						if(request.getHeader("channel").equals("SX")){
							JSONObject completingp = new JSONObject();
							completingp.put("title", "请上传出账单");
							completingp.put("content", intoPiece.getMemberName()+"的承诺函已生成，请至电脑端下载，并上传银行出账单！");
							completingp.put("intoPieceId", intoPiece.getId());
							completingp.put("type", Constants.PENDING_SIGN_REMIND);
							String timep = "";
							Date datep = intoPiece.getStatusUpdate();
							SimpleDateFormat sdfp = new SimpleDateFormat("yyyyMMdd");
							if(sdfp.format(new Date()).equals(sdfp.format(datep))){
								sdfp = new SimpleDateFormat("HH:mm");
								timep = sdfp.format(datep);
							}else{
								sdfp = new SimpleDateFormat("MM/dd");
								timep = sdfp.format(datep);
							}
							completingp.put("time", timep);
							map.put(saveRecord(listTime, datep), completingp);
							listTime.add(saveRecord(listTime, datep));
						}
						break;*/
						//进件初审
					case "2":
						if(request.getHeader("channel").equals("SX")){
							JSONObject completingp = new JSONObject();
							completingp.put("title", "请签署相关合同");
							completingp.put("content", intoPiece.getMemberName()+"的资料已提交初审，请签署相关合同（纸质）！");
							completingp.put("intoPieceId", intoPiece.getId());
							completingp.put("type", Constants.SIGN_RELATIVE_CONTRACT);
							String timep = "";
							Date datep = intoPiece.getStatusUpdate();
							SimpleDateFormat sdfp = new SimpleDateFormat("yyyyMMdd");
							if(sdfp.format(new Date()).equals(sdfp.format(datep))){
								sdfp = new SimpleDateFormat("HH:mm");
								timep = sdfp.format(datep);
							}else{
								sdfp = new SimpleDateFormat("MM/dd");
								timep = sdfp.format(datep);
							}
							completingp.put("time", timep);
							map.put(saveRecord(listTime, datep), completingp);
							listTime.add(saveRecord(listTime, datep));
						}
						break;
					case "8":
							List<BusGuaranteeReverse> grs = guaranteeReverseService.selectByIntoPieceId(intoPiece.getId());
							boolean flag = false;
							if(grs==null||grs.size()<1){
								break;
							}
							for (int j = 0; j < grs.size(); j++) {
								if(grs.get(j).getStatus()==null||!grs.get(j).getStatus().equals("S")){
									flag=true;
								}
							}
							if(flag){
								break;
							}
							JSONObject completingp1 = new JSONObject();
							completingp1.put("title", "请上传银行回单");
							completingp1.put("content", "请上传"+intoPiece.getMemberName()+"客户银行出账单或客户已放弃贷款！");
							completingp1.put("intoPieceId", intoPiece.getId());
							completingp1.put("type", Constants.PENDING_BANK_BACK);
							String timep1 = "";
							Date datep1 = intoPiece.getStatusUpdate();
							SimpleDateFormat sdfp1 = new SimpleDateFormat("yyyyMMdd");
							if(sdfp1.format(new Date()).equals(sdfp1.format(datep1))){
								sdfp1 = new SimpleDateFormat("HH:mm");
								timep1 = sdfp1.format(datep1);
							}else{
								sdfp1 = new SimpleDateFormat("MM/dd");
								timep1 = sdfp1.format(datep1);
							}
							completingp1.put("time", timep1);
							map.put(saveRecord(listTime, datep1), completingp1);
							listTime.add(saveRecord(listTime, datep1));	
						break;
					case "5":
					case "6":
					case "7":
//					case "9":
					case "10":
						if(request.getHeader("channel").equals("AJ")){
							break;
						}
						/*if(request.getHeader("channel").equals("SX")&&status.equals("5")){
							JSONObject completingp = new JSONObject();
							completingp.put("title", "终审签约提示");
							BusFins fins = finsService.selectById(intoPiece.getLenderId());
							completingp.put("content", "请至"+fins.getCname()+"拿"+intoPiece.getMemberName()+"客户的意向信贷意向通知，拍照上传！");
							completingp.put("intoPieceId", intoPiece.getId());
							completingp.put("type", Constants.LAST_SIGN_REMIND);
							String timep = "";
							Date datep = intoPiece.getStatusUpdate();
							SimpleDateFormat sdfp = new SimpleDateFormat("yyyyMMdd");
							if(sdfp.format(new Date()).equals(sdfp.format(datep))){
								sdfp = new SimpleDateFormat("HH:mm");
								timep = sdfp.format(datep);
							}else{
								sdfp = new SimpleDateFormat("MM/dd");
								timep = sdfp.format(datep);
							}
							completingp.put("time", timep);
							map.put(saveRecord(listTime, datep), completingp);
							listTime.add(saveRecord(listTime, datep));
						}*/
						/*if(request.getHeader("channel").equals("SX")&&status.equals("7")){
							JSONObject completingp = new JSONObject();
							completingp.put("title", "请上传土地备案回执");
							completingp.put("content", "请上传"+intoPiece.getMemberName()+"的土地备案回执！");
							completingp.put("intoPieceId", intoPiece.getId());
							completingp.put("type", Constants.MAKE_CONTRACT_REMIND);
							String timep = "";
							Date datep = intoPiece.getStatusUpdate();
							SimpleDateFormat sdfp = new SimpleDateFormat("yyyyMMdd");
							if(sdfp.format(new Date()).equals(sdfp.format(datep))){
								sdfp = new SimpleDateFormat("HH:mm");
								timep = sdfp.format(datep);
							}else{
								sdfp = new SimpleDateFormat("MM/dd");
								timep = sdfp.format(datep);
							}
							completingp.put("time", timep);
							map.put(saveRecord(listTime, datep), completingp);
							listTime.add(saveRecord(listTime, datep));
						}*/
//						completing.put("title", intoPiece.getMemberName()+"的贷款已通过审核");
//						completing.put("content", intoPiece.getMemberName()+"向"+intoPiece.getFullCname()+"申请的贷款终审通过");
//						completing.put("intoPieceId", intoPiece.getId());
//						completing.put("type", Constants.LAST);
//						String time4 = "";
//						Date date4 = intoPiece.getStatusUpdate();
//						SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
//						if(sdf4.format(new Date()).equals(sdf4.format(date4))){
//							sdf4 = new SimpleDateFormat("HH:mm");
//							time4 = sdf4.format(date4); 
//						}else{
//							sdf4 = new SimpleDateFormat("MM/dd");
//							time4 = sdf4.format(date4);
//						}
//						completing.put("time", time4);
//						map.put(saveRecord(listTime, date4), completing);
//						listTime.add(saveRecord(listTime, date4));
						break;
					case "11":
						if(request.getHeader("channel").equals("AJ")){
							break;
						}
						//TODO 还款完成的还款明细
//						本期还款完成
						completing.put("title", intoPiece.getMemberName()+"的贷款已放款");
						completing.put("content", intoPiece.getMemberName()+"向"+intoPiece.getFullCname()+"申请的贷款已放款");
						completing.put("intoPieceId", intoPiece.getId());
						completing.put("type", Constants.REPAYMENT);
						String time5 = "";
						Date date5 = intoPiece.getStatusUpdate();
						SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
						if(sdf5.format(new Date()).equals(sdf5.format(date5))){
							sdf5 = new SimpleDateFormat("HH:mm");
							time5 = sdf5.format(date5);
						}else{
							sdf5 = new SimpleDateFormat("MM/dd");
							time5 = sdf5.format(date5);
						}
						completing.put("time", time5);
						map.put(saveRecord(listTime, date5), completing);
						listTime.add(saveRecord(listTime, date5));
						BusLoanDetail loanDetailcomp = loanDetailService.selectLastComplete(intoPiece.getId());
						if(loanDetailcomp!=null){
							BusLoan loan = loanService.selectByPrimaryKey(loanDetailcomp.getLoanId());
							JSONObject complete = new JSONObject();
							complete.put("title", intoPiece.getMemberName()+"本期还款完成");
							complete.put("content", "第"+loanDetailcomp.getSort()+"期还款完成");
							complete.put("intoPieceId", intoPiece.getId());
							complete.put("type", Constants.REPAYMENTED);
							complete.put("money", loan.getCapital());
							complete.put("create_date", DateUtils.format(intoPiece.getCreDate(), "yyyy-MM-dd"));
							String time11com = "";
							Date date11com = loanDetailcomp.getCreDate();
							SimpleDateFormat sdf11com = new SimpleDateFormat("yyyyMMdd");
							if(sdf11com.format(new Date()).equals(sdf11com.format(date11com))){
								sdf11com = new SimpleDateFormat("HH:mm");
								time11com = sdf11com.format(date11com);
							}else{
								sdf11com = new SimpleDateFormat("MM/dd");
								time11com = sdf11com.format(date11com);
							}
							complete.put("time", time11com);
							map.put(saveRecord(listTime, date11com), complete);
							listTime.add(saveRecord(listTime, date11com));
						}
						break;
					default:
						break;
					}
				}
				
				Collections.sort(listTime);
				if(listTime!=null&&listTime.size()>0){
					for(int i = listTime.size()-1;i>=0;i--){
						arr.add(map.get(listTime.get(i)));
					}
				}
			}
			JSONArray image = new JSONArray();
			String channel = request.getHeader("channel");
			SysCarouselFigure carouselFigure = new SysCarouselFigure();
			carouselFigure.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			carouselFigure.setStatus(Constants.PICTRUE_SHOW);
			carouselFigure.setChannel(channel);
			List<SysCarouselFigure> list2 = carouselFigureService.selectListByCondition(carouselFigure);
			if(list2!=null&&list2.size()>0){
				for (int i = 0; i < list2.size(); i++) {
					String qiniuKey = list2.get(i).getQiniuKey();
					image.add(QiNiuUtil.getUrl(qiniuKey));
				}
			}
			retJson.put("image", image);
			retJson.put("data", arr);
			response.setStatus(200);
			retJson.put("errno", 0);
			return retJson;
			
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "查询错误！");
			response.setStatus(400);
			return retJson;
		}
		
		
	}
	
	public Date saveRecord(List<Date> listTime,Date date){
		if(listTime.contains(date)){
			Date date2 = new Date(date.getTime()+1000);
			return saveRecord(listTime,date2);
		}else{
			return date;
		}
	}
}
