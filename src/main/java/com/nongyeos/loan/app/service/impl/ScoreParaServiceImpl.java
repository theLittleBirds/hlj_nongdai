package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.ScorePara;
import com.nongyeos.loan.app.mapper.ScoreParaMapper;
import com.nongyeos.loan.app.service.IScoreParaService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("scoreParaService")
public class ScoreParaServiceImpl implements IScoreParaService {

	@Autowired
	private ScoreParaMapper scoreParaMapper;

	// 全查
	@Override
	public PageBeanUtil<ScorePara> selectAll(int limit, int offset) throws Exception{
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<ScorePara> allItems = scoreParaMapper.selectAll();
			int countNums = scoreParaMapper.count();//总记录数
			PageBeanUtil<ScorePara> pageData = new PageBeanUtil<ScorePara>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScorePara scorePara) throws Exception{
		try {
			if(scorePara==null){
				throw new Exception("scorePara为空");
			}
			scoreParaMapper.insertSelective(scorePara);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScorePara scorePara) throws Exception{
		try {
			if(scorePara==null){
				throw new Exception("scorePara为空");
			}
			scoreParaMapper.updateByPrimaryKey(scorePara);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer scoreParaId) throws Exception{
		try {
			if(scoreParaId==null){
				throw new Exception("scoreParaId为空");
			}
			scoreParaMapper.deleteByPrimaryKey(scoreParaId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ScorePara> getParaDS() throws Exception {
		try {
			List<ScorePara> list = scoreParaMapper.selectParaDS();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
