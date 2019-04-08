package com.nongyeos.loan.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.entity.ScoreScvarcase;
import com.nongyeos.loan.app.mapper.ScoreScvarMapper;
import com.nongyeos.loan.app.mapper.ScoreScvarcaseMapper;
import com.nongyeos.loan.app.service.IScvarCaseService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("caseService")
public class ScvarCaseServiceImpl implements IScvarCaseService{
	
	@Autowired
	private ScoreScvarcaseMapper caseMapper;
	@Autowired
	private ScoreScvarMapper scvarMapper;
	
	@Override
	public PageBeanUtil<ScoreScvarcase> selectByPage(int limit, int offset, String scoreid) throws Exception {
		try {
			List<ScoreScvar> scvarList = scvarMapper.selectAll(scoreid);
			List<String> scvarIdList = new ArrayList<>();
			for (ScoreScvar scvar : scvarList) {
				scvarIdList.add(scvar.getScvarId());
			}
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<ScoreScvarcase> allItems = caseMapper.selectByPage(scvarIdList);
			int countNums = caseMapper.count(scvarIdList);//总记录数
			PageBeanUtil<ScoreScvarcase> pageData = new PageBeanUtil<ScoreScvarcase>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<ScoreScvarcase> selectAll(String scvarid) {
		try {
			List<ScoreScvarcase> list = caseMapper.selectAll(scvarid);
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreScvarcase varcase) throws Exception{
		try{
			caseMapper.insertSelective(varcase);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreScvarcase varcase) throws Exception{
		try{
			caseMapper.updateByPrimaryKey(varcase);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String caseId)throws Exception{
		try{
			if(caseId != null && !caseId.equals("")){
				caseMapper.deleteByPrimaryKey(caseId);
			}else{
				throw new Exception("评分卡变量判例Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public void deleteByScvarId(String scvarId) throws Exception {
		try{
			if(scvarId != null && !scvarId.equals("")){
				caseMapper.deleteByScvarId(scvarId);
			}else{
				throw new Exception("评分卡变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
