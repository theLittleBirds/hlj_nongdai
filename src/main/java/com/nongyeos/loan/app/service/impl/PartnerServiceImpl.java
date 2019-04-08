package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.IntPartner;
import com.nongyeos.loan.app.mapper.IntPartnerMapper;
import com.nongyeos.loan.app.service.IPartnerService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("partnerService")
public class PartnerServiceImpl implements IPartnerService {

	@Autowired
	private IntPartnerMapper partnerMapper;

	// 合作伙伴删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePartner(Integer partnerId) throws Exception {
		try {
			if(partnerId==null || partnerId.equals("")){
				throw new Exception("partnerId为空");
			}
			partnerMapper.deleteByPrimaryKey(partnerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 合作伙伴重名
	@Override
	public boolean existedSameName(IntPartner partner) throws Exception {
		try {
			if (partner == null) {
				throw new Exception("partner为空");
			} else {
				IntPartner partner1 = partnerMapper.selectPartnerByName(partner
						.getCname());
				if (partner1 != null) {
					if (partner.getPartnerId() == null
							|| partner.getPartnerId().equals("")) {
						return true;
					}
					if (!partner.getPartnerId().equals(partner1.getPartnerId())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 合作伙伴添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addPartner(IntPartner partner) throws Exception {
		try {
			if (partner == null) {
				throw new Exception("partner为空");
			}
			partnerMapper.insert(partner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 合作伙伴更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatePartner(IntPartner partner) throws Exception {
		try {
			if (partner == null) {
				throw new Exception("partner为空");
			}
			partnerMapper.updateByPrimaryKey(partner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageBeanUtil<IntPartner> selectAll(int limit, int offset) throws Exception{
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<IntPartner> allItems = partnerMapper.selectAll();
			int countNums = partnerMapper.count();// 总记录数
			PageBeanUtil<IntPartner> pageData = new PageBeanUtil<IntPartner>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}

}