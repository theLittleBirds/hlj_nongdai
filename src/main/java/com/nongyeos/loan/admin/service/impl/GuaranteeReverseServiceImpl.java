package com.nongyeos.loan.admin.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusSmsTemplate;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.mapper.BusGuaranteeReverseMapper;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.mapper.BusSmsTemplateMapper;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("guaranteeReverseService")
public class GuaranteeReverseServiceImpl implements IGuaranteeReverseService {

	@Autowired
	private BusGuaranteeReverseMapper guaranteeReverseMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private SysPersonMapper personMapper;
	
	@Autowired
	private IMessageReminderService reminderService;
	
	@Autowired
    private RootPointConfig rootPointConfig;
	
	@Autowired
	private BusSmsTemplateMapper smsTemplateMapper;
	
	@Autowired
	private AppApplicationMapper applicationMapper;
	
	@Autowired
	private IMemberLoginService loginService;
	
	@Autowired
	private BusLoanMapper loanMapper;
	 
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("反担保金标识为空");
		}
		try {
			return guaranteeReverseMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除出错");
		}
		
	}

	@Override
	public int insert(BusGuaranteeReverse record) throws Exception {
		if(record==null){
			throw new Exception("反担保金模板为空");
		}
		try {
			return guaranteeReverseMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public int insertSelective(BusGuaranteeReverse record) throws Exception {
		if(record==null){
			throw new Exception("反担保金模板为空");
		}
		try {
			return guaranteeReverseMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public BusGuaranteeReverse selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("反担保金标识为空");
		}
		try {
			return guaranteeReverseMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusGuaranteeReverse record)
			throws Exception {
		if(record==null){
			throw new Exception("反担保金模板为空");
		}
		try {
			return guaranteeReverseMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	public int updateByPrimaryKey(BusGuaranteeReverse record) throws Exception {
		if(record==null){
			throw new Exception("反担保金模板为空");
		}
		try {
			return guaranteeReverseMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存出错");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void savePreReverseMessage(String intoPieceId, String personId) throws Exception {

		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("进件标识为空");
		}
		try {
			BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(intoPieceId);
			//操作人
			SysPerson person = personMapper.selectByPrimaryKey(ip.getOperUserId());
			AppApplication appApplication = applicationMapper.selectByPrimaryKey(ip.getModelId());
			List<BusGuaranteeReverse> list = guaranteeReverseMapper.selectByIntoPieceId(intoPieceId);
			if(StringUtils.isEmpty(appApplication.getStationRate())){
				throw new Exception("未配置担保金比例");
			}
			BusLoan loan = loanMapper.selectByIpId(intoPieceId);
			BigDecimal ureverce = loan.getCapital().multiply(new BigDecimal(appApplication.getStationRate())).divide(new BigDecimal(100), 0);
			BigDecimal mreverce = loan.getCapital().multiply(new BigDecimal(appApplication.getFarmerRate())).divide(new BigDecimal(100), 0);
			if(list==null||list.size()<1){
				Date nowDate = DateUtils.getNowDate();
				//个人
				BusGuaranteeReverse guaranteeReversem = new BusGuaranteeReverse();
				guaranteeReversem.setId(UUIDUtil.getUUID());
				guaranteeReversem.setOrgId(ip.getOrgId());
				guaranteeReversem.setIntoPieceId(intoPieceId);
				//1表示正常，2表示没有移动端账号
				guaranteeReversem.setUse(1);
				guaranteeReversem.setTotalAmount(mreverce);
				guaranteeReversem.setPayer(ip.getMemberName());
				guaranteeReversem.setPayerBankcardno(ip.getBankCardNo());
				guaranteeReversem.setPayerIdcard(ip.getIdCard());
				guaranteeReversem.setPayerMobile(ip.getPhone());
				guaranteeReversem.setType(1);
				guaranteeReversem.setCreateBy(personId);
				guaranteeReversem.setCreateDate(nowDate);
				if(mreverce.compareTo(new BigDecimal("0"))==0){
					guaranteeReversem.setStatus("S");
				}
				//如果反担保金不为0，则发送首页消息
				if(mreverce.compareTo(new BigDecimal("0"))==1){
					Date now = DateUtils.getNowDate();
					BusSmsTemplate smsTemplate = new BusSmsTemplate();
					String mark = rootPointConfig.getMark();
					JSONObject object = JSONObject.parseObject(mark);
					smsTemplate.setBaseOrgId(object.getString(ip.getChannel()));
					smsTemplate.setMsgCode(Constants.U_GREVERSE_REMIND);
					smsTemplate.setIsOpen(1);
					BusSmsTemplate smstemplateuser = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
					Map<String, String> value=new HashMap<String, String>();
					value.put("CODE", mreverce.toString());
					value.put("MEMBER", ip.getMemberName());
					BusMessageReminder reminder = new BusMessageReminder();
					reminder.setIntoPieceId(intoPieceId);
					reminder.setType(Constants.UGUATANTEE_REVERSE);
					reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
					BusMessageReminder messageReminder = reminderService.queryMRByTypeAndDelete(reminder);
					if(messageReminder==null){
						reminderService.insertreminder(smstemplateuser,value,now,intoPieceId,person.getUserId(),Constants.UGUATANTEE_REVERSE);
					}
					BusSmsTemplate smsTemplate1 = new BusSmsTemplate();
					smsTemplate1.setBaseOrgId(object.getString(ip.getChannel()));
					smsTemplate1.setMsgCode(Constants.M_GREVERSE_REMIND);
					smsTemplate1.setIsOpen(1);
					BusSmsTemplate smstemplatemember = smsTemplateMapper.selectByCodeAndRoot(smsTemplate);
					BusMessageReminder reminder1 = new BusMessageReminder();
					reminder1.setIntoPieceId(intoPieceId);
					reminder1.setType(Constants.MGUATANTEE_REVERSE);
					reminder1.setIsDelete(Constants.COMMON_ISNOT_DELETE);
					BusMessageReminder messageReminderm = null;
					try {
						messageReminderm =reminderService.queryMRByTypeAndDelete(reminder1);
					} catch (Exception e) {
						List<BusMessageReminder> list1 = reminderService.selectByCondition(reminder1);
						messageReminderm=list1.get(0);
					}
					if(messageReminderm==null){
						Map<String, String> memberIdChannel = new HashMap<String, String>();
						memberIdChannel.put("memberId", ip.getMemberId());
						memberIdChannel.put("channel", ip.getChannel());
						List<BusMemberLogin> listm = loginService.selectByMemberId(memberIdChannel);
						if(listm!=null&&listm.size()>0){
							for (int i = 0; i < listm.size(); i++) {
								reminderService.insertreminder(smstemplatemember,value,now,intoPieceId,listm.get(i).getLoginId(),Constants.MGUATANTEE_REVERSE);
							}
						}else{
							guaranteeReversem.setUse(2);
						}
					}
				}
				guaranteeReverseMapper.insert(guaranteeReversem);
				//商户
				BusGuaranteeReverse guaranteeReverseu = new BusGuaranteeReverse();
				guaranteeReverseu.setId(UUIDUtil.getUUID());
				guaranteeReverseu.setOrgId(ip.getOrgId());
				guaranteeReverseu.setIntoPieceId(intoPieceId);
				guaranteeReverseu.setUse(1);
				guaranteeReverseu.setTotalAmount(ureverce);
				guaranteeReverseu.setPayer(person.getNameCn());
				//人员的银行卡号
				guaranteeReverseu.setPayerBankcardno(person.getNameEn());
				guaranteeReverseu.setPayerIdcard(person.getCardNo());
				guaranteeReverseu.setPayerMobile(person.getMobile());
				guaranteeReverseu.setType(2);
				if(mreverce.compareTo(new BigDecimal("0"))==0){
					guaranteeReverseu.setStatus("S");
				}
				guaranteeReverseu.setCreateBy(personId);
				guaranteeReverseu.setCreateDate(nowDate);
				guaranteeReverseMapper.insert(guaranteeReverseu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	@Override
	public List<BusGuaranteeReverse> selectByIntoPieceId(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("进件标识为空");
		}
		try {
			return guaranteeReverseMapper.selectByIntoPieceId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
		
	}

	@Override
	public BusGuaranteeReverse selectByTypeAndIpId(BusGuaranteeReverse record)
			throws Exception {
		if(record==null){
			throw new Exception("反担保金模板为空");
		}
		try {
			return guaranteeReverseMapper.selectByTypeAndIpId(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

}
