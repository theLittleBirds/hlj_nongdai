package com.nongyeos.loan.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusSmsChannel;
import com.nongyeos.loan.admin.entity.BusSmsHistory;
import com.nongyeos.loan.admin.entity.BusSmsTemplate;
import com.nongyeos.loan.admin.mapper.BusSmsChannelMapper;
import com.nongyeos.loan.admin.mapper.BusSmsHistoryMapper;
import com.nongyeos.loan.admin.mapper.BusSmsTemplateMapper;
import com.nongyeos.loan.admin.service.ISmsTemplateService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.SMSUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements ISmsTemplateService {
	
	@Autowired
	private BusSmsTemplateMapper smsTemplateMapper;
	
	@Autowired
	private BusSmsHistoryMapper smsHistoryMapper;
	
	@Autowired
	private BusSmsChannelMapper smsChannelMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addRecord(BusSmsTemplate record) throws Exception {
		if(record == null){
			throw new Exception("短信模板为空");
		}
		if(record.getMsgId() == null || "".equals(record.getMsgId())){
			throw new Exception("短信模板主键为空");
		}
		try {
			return smsTemplateMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	public BusSmsTemplate selectById(String smsId) throws Exception{
		if(smsId == null || "".equals(smsId)){
			throw new Exception("条件为空");
		}
		try {
			return smsTemplateMapper.selectByPrimaryKey(smsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusSmsTemplate record) throws Exception{
		if(record == null){
			throw new Exception("短信模板为空");
		}
		if(record.getMsgId() == null || "".equals(record.getMsgId())){
			throw new Exception("短信模板主键为空");
		}
		try {
			return smsTemplateMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteById(String smsId) throws Exception{
		if(smsId == null || "".equals(smsId)){
			throw new Exception("短信模板主键为空");
		}
		try {
			return smsTemplateMapper.deleteByPrimaryKey(smsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusSmsTemplate> selectByPage(int currentPage,
			int pageSize) throws Exception{
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusSmsTemplate> list = smsTemplateMapper.selectAll();
			int countNums = smsTemplateMapper.selectCount();
			PageBeanUtil<BusSmsTemplate> pageData = new PageBeanUtil<BusSmsTemplate>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	public BusSmsTemplate checkExist(BusSmsTemplate record) throws Exception{
		if(record.getMsgCode() == null || "".equals(record.getMsgCode())){
			throw new Exception("模板编码为空");
		}
		if(record.getBaseOrgId() == null || "".equals(record.getBaseOrgId())){
			throw new Exception("机构标识为空");
		}
		try {
			return smsTemplateMapper.checkExist(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}
	/**
	 * 发送短信
	 * @param msgCode
	 * @param map 参数集
	 * @param phone
	 * @return
	 */
	public int smsSend(String msgCode,String baseOrgId, Map<String,String> map,String phone) throws Exception{
		if(msgCode == null || "".equals(msgCode)){
			throw new Exception("模板编码为空");
		}
		BusSmsTemplate check = new BusSmsTemplate();
		check.setMsgCode(msgCode);
		check.setBaseOrgId(baseOrgId);
		BusSmsTemplate st = smsTemplateMapper.checkExist(check);
		if(st == null)
			throw new Exception("找不到短信模板");
		String content = st.getMsgContent();
		if(st.getVariable() !=null && !"".equals(st.getVariable())){
			String para[] = st.getVariable().split(",");
			for (int i = 0; i < para.length; i++) {
				content = content.replaceAll("#"+para[i]+"#", map.get(para[i]));
			}
		}
		System.err.println(content);
		BusSmsChannel bsc =null;
		try {
			bsc =smsChannelMapper.selectUsedChannel();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		if(bsc==null){
			throw new Exception("请联系管理员配置短信通道");
		}
		int result = SMSUtils.sendSMS(content, phone,bsc.getChannelName());
		BusSmsHistory sh = new BusSmsHistory();
		sh.setId(UUIDUtil.getUUID());
		sh.setBaseOrgId(baseOrgId);
		sh.setContent(content);
		sh.setPhone(phone);
		sh.setResult(result);
		sh.setSendDate(new Date());
		sh.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		smsHistoryMapper.insert(sh);
		return result;
	}
}
