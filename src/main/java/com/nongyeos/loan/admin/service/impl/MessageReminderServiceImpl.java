package com.nongyeos.loan.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusJpush;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusSmsTemplate;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.mapper.BusGuaranteeFeeInfoMapper;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusMemberLoginMapper;
import com.nongyeos.loan.admin.mapper.BusMessageReminderMapper;
import com.nongyeos.loan.admin.mapper.BusSmsTemplateMapper;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.mapper.SysPersonorgMapper;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
@Service("messageReminderService")
public class MessageReminderServiceImpl implements IMessageReminderService {
	@Autowired
	private BusMessageReminderMapper messageReminderMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private BusMemberLoginMapper memberLoginMapper;
	
	@Autowired
	private BusSmsTemplateMapper smsTemplateMapper;
	
	@Autowired
	private BusGuaranteeFeeInfoMapper guaranteeFeeInfoMapper;
	
	@Autowired
	private SysWebUserMapper webUserMapper;
	
	@Autowired
	private SysPersonMapper personMapper;
	
	@Autowired
	private SysPersonorgMapper personorgMapper;
	
	@Autowired
    private RootPointConfig rootPointConfig;

	@Override
	public List<BusMessageReminder> selectByCondition(
			BusMessageReminder messageReminder) throws Exception {
		if(messageReminder==null){
			throw new Exception("首页消息模板为空！");
		}
		try {
			return messageReminderMapper.selectByCondition(messageReminder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}


	@Override
	public void saveServiceRemind(String root, String intopieceId)
			throws Exception {
		try {
			BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(intopieceId);
			Map<String, String> memberIdChannel = new HashMap<String, String>();
			memberIdChannel.put("channel", ip.getChannel());
			memberIdChannel.put("memberId", ip.getMemberId());
			List<BusMemberLogin> list = memberLoginMapper.selectByMemberId(memberIdChannel);
			Date now = DateUtils.getNowDate();
			BusSmsTemplate smsTemplate = new BusSmsTemplate();
			//个人服务费扣费查询条件
			smsTemplate.setBaseOrgId(root);
			smsTemplate.setMsgCode(Constants.M_SERVICE_FEE_REMIND);
			smsTemplate.setIsOpen(1);
			BusSmsTemplate smstemplate = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoMapper.selectByIpId(intopieceId);
			//个人委托服务合同查询条件
//			smsTemplate.setBaseOrgId(root);
//			smsTemplate.setMsgCode(Constants.ENTRUSTED_DCONTRACT);
//			smsTemplate.setIsOpen(1);
//			BusSmsTemplate smstemplateec = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
			//商户服务费扣费查询条件
			smsTemplate.setBaseOrgId(root);
			smsTemplate.setMsgCode(Constants.U_SERVICE_FEE_REMIND);
			smsTemplate.setIsOpen(1);
			BusSmsTemplate smstemplateuser = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
			if(guaranteeFeeInfo!=null){
				//个人服务费提醒
				if(list !=null&&list.size()>0){
					 for (int j = 0; j < list.size(); j++) {
						 Map<String, String> value=new HashMap<String, String>();
						 value.put("CODE", guaranteeFeeInfo.getTotalAmount().toString());
						 //扣费提醒
						 insertreminder(smstemplate,value,now,intopieceId,list.get(j).getLoginId(),Constants.SERVICEDONE);
						 //托服务合同签署
						 /*insertreminder(smstemplateec, new HashMap<String, String>(), now, intopieceId, list.get(j).getLoginId(),Constants.SERVICEDONE);*/
					}
				 }
				
				//商户服务费提醒
				String personId = ip.getOperUserId();
				SysPerson person = personMapper.selectByPrimaryKey(personId);
				Map<String, String> value=new HashMap<String, String>();
				value.put("CODE", guaranteeFeeInfo.getTotalAmount().toString());
				value.put("MEMBER", ip.getMemberName());
				insertreminder(smstemplateuser,value,now,intopieceId,person.getUserId(),Constants.SERVICEDONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
		
	}


	public void insertreminder(BusSmsTemplate smstemplateuser,
			Map<String, String> value, Date now, String intopieceId, String loginId,Integer type) throws Exception {
		try {
			String variable = smstemplateuser.getVariable();
			String[] split = null;
			if(StringUtils.isNotEmpty(variable)){
				split=variable.split(",");
			}
			BusMessageReminder mr = new BusMessageReminder();
			mr.setId(UUIDUtil.getUUID());
			mr.setIntoPieceId(intopieceId);
			mr.setMemberLoginId(loginId);
			mr.setTitle(smstemplateuser.getMsgName());
			String msgContent = smstemplateuser.getMsgContent();
			if(split!=null){
				for (int i = 0; i < split.length; i++) {
					msgContent=msgContent.replace("#"+split[i]+"#", value.get(split[i]));
				}	
			}
			mr.setContent(msgContent);
			mr.setType(type);
			mr.setCreateDate(now);
			mr.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			messageReminderMapper.insert(mr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
		
	}


	@Override
	public void saveweixinpay(int type,String channel, String id,String serviceFee) throws Exception {
		try {
			BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(id);
			Date now = DateUtils.getNowDate();
			BusSmsTemplate smsTemplate = new BusSmsTemplate();
			String mark = rootPointConfig.getMark();
			JSONObject object = JSONObject.parseObject(mark);
			smsTemplate.setBaseOrgId(object.getString(channel));
			smsTemplate.setMsgCode(Constants.U_WEI_XIN_SERVICE_PAY);
			smsTemplate.setIsOpen(1);
			BusSmsTemplate smstemplateuser = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
			String personId = ip.getOperUserId();
			SysPerson person = personMapper.selectByPrimaryKey(personId);
			Map<String, String> value=new HashMap<String, String>();
			value.put("CODE", serviceFee);
			value.put("MEMBER", ip.getMemberName());
			BusMessageReminder reminder = new BusMessageReminder();
			reminder.setIntoPieceId(id);
			reminder.setType(type);
			reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			BusMessageReminder messageReminder = messageReminderMapper.queryMRByTypeAndDelete(reminder);
			if(messageReminder==null){
				insertreminder(smstemplateuser,value,now,id,person.getUserId(),type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
		
	}
	
	@Override
	public int updateByPrimaryKey(BusMessageReminder record) throws Exception {
		if(record==null){
			throw new Exception("消息模板为空");
		}
		try {
			return messageReminderMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}


	@Override
	public BusMessageReminder queryMRByTypeAndDelete(BusMessageReminder reminder) throws Exception {
		if(reminder==null){
			throw new Exception("消息模板为空");
		}
		try {
			return messageReminderMapper.queryMRByTypeAndDelete(reminder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询错误");
		}
	}
	
	@Override
	public void saveRefund(String root, String intopieceId,String refund)
			throws Exception {
		try {
			BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(intopieceId);
			Date now = DateUtils.getNowDate();
			BusSmsTemplate smsTemplate = new BusSmsTemplate();
			String mark = rootPointConfig.getMark();
			JSONObject object = JSONObject.parseObject(mark);
			smsTemplate.setBaseOrgId(object.getString(root));
			smsTemplate.setMsgCode(Constants.U_REFUND_REMIND);
			smsTemplate.setIsOpen(1);
			BusSmsTemplate smstemplateuser = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
			String personId = ip.getOperUserId();
			SysPerson person = personMapper.selectByPrimaryKey(personId);
			Map<String, String> value=new HashMap<String, String>();
			value.put("CODE", refund);
			value.put("MEMBER", ip.getMemberName());
			BusMessageReminder reminder = new BusMessageReminder();
			reminder.setIntoPieceId(intopieceId);
			reminder.setType(Constants.REFUND_SUCCESS);
			reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			BusMessageReminder messageReminder = messageReminderMapper.queryMRByTypeAndDelete(reminder);
			if(messageReminder==null){
				insertreminder(smstemplateuser,value,now,intopieceId,person.getUserId(),Constants.REFUND_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}

	
}
