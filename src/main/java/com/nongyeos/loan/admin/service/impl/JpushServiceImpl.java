package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusJpush;
import com.nongyeos.loan.admin.mapper.BusJpushMapper;
import com.nongyeos.loan.admin.service.IJpushService;
@Service("jpushService")
public class JpushServiceImpl implements IJpushService {
	@Autowired
	private BusJpushMapper jpushMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(BusJpush jpush) throws Exception {
		if(jpush==null){
			throw new Exception("推送模板为空！");
		}
		try {
			jpushMapper.insert(jpush);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
	}

	@Override
	public List<BusJpush> selectAllPush() throws Exception {
		try {
			return jpushMapper.selectAllPush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		try {
			jpushMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除失败！");
		}
		
	}
	
	
}
