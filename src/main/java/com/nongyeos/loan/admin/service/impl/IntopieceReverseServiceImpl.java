package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusIntopieceReverse;
import com.nongyeos.loan.admin.entity.BusRefund;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.mapper.BusIntopieceReverseMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.service.IIntopieceReverseService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("intopieceReverseService")
public class IntopieceReverseServiceImpl implements IIntopieceReverseService {

	@Autowired
	private BusIntopieceReverseMapper intopieceReverseMapper;
	
	@Autowired
	private SysOrgMapper orgMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("进件反担保金标识为空");
		}
		try {
			return intopieceReverseMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除错误");
		}
	}

	@Override
	public int insert(BusIntopieceReverse record) throws Exception {
		if(record==null){
			throw new Exception("进件反担金保金模板为空");
		}
		try {
			return intopieceReverseMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存错误");
		}
	}

	@Override
	public int insertSelective(BusIntopieceReverse record) throws Exception {
		if(record==null){
			throw new Exception("进件反担金保金模板为空");
		}
		try {
			return intopieceReverseMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存错误");
		}
	}

	@Override
	public BusIntopieceReverse selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("进件反担金保标识为空");
		}
		try {
			return intopieceReverseMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询错误");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusIntopieceReverse record)
			throws Exception {
		if(record==null){
			throw new Exception("进件反担金保金模板为空");
		}
		try {
			return intopieceReverseMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存错误");
		}
	}

	@Override
	public int updateByPrimaryKey(BusIntopieceReverse record) throws Exception {
		if(record==null){
			throw new Exception("进件反担金保金模板为空");
		}
		try {
			return intopieceReverseMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存错误");
		}
	}

	@Override
	public BusIntopieceReverse selectByIpId(String intoPieceId)
			throws Exception {
		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("进件id为空");
		}
		try {
			return intopieceReverseMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询错误");
		}
	}

	@Override
	public PageBeanUtil<BusIntopieceReverse> selectByPage(String personId,
			Map<String, Object> map, int currentPage, int pageSize)
			throws Exception {
		try {
			List<SysOrg> orgList = orgMapper.selectOrgsByPerson(personId);
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < orgList.size(); i++) {
				idList.add(orgList.get(i).getOrgId());
			}
			map.put("orgIds", idList);
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusIntopieceReverse> list= intopieceReverseMapper.selectByCondition(map);
			int countNums =  intopieceReverseMapper.selectCountByCondition(map);
			PageBeanUtil<BusIntopieceReverse> pageData = new PageBeanUtil<BusIntopieceReverse>(currentPage, pageSize, countNums);
			pageData.setItems(list);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
