package com.nongyeos.loan.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.DecisionStrategy;
import com.nongyeos.loan.app.entity.DecisionStrule;
import com.nongyeos.loan.app.mapper.DecisionStrategyMapper;
import com.nongyeos.loan.app.mapper.DecisionStruleMapper;
import com.nongyeos.loan.base.util.ApplicationContextProvider;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.DecisionRuleMgr;
import com.nongyeos.loan.core.service.DecisionStrategyMgr;

@Service("StrategyMgrImpl")
@EnableTransactionManagement(proxyTargetClass = true)
public class DecisionStrategyMgrImpl implements DecisionStrategyMgr {

	@Autowired
	private DecisionRuleMgr ruleMgrService;
	@Autowired
	private DecisionStrategyMapper strategyMapper;
	@Autowired
	private DecisionStruleMapper struleMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean doStrategy(BusinessObject bo, String strategyId) throws Exception{
		boolean doStrategy = false;
		try {
			DecisionStrategy strategy = strategyMapper.selectByPrimaryKey(strategyId);
			//标准型
			if (strategy.getType()==1) {
				List<DecisionStrule> list = struleMapper.selectByStrategyId(strategyId);
				for (DecisionStrule strule : list) {
					doStrategy = ruleMgrService.doRule(bo, strule.getRuleId());
				}
			}else {
				//决策树-暂无
			}
			return doStrategy;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception (e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean doStrategy2(BusinessObject bo, String strategyId, SqlSession session) throws Exception{
		boolean doStrategy = false;
		try {
			DecisionStrategy strategy = session.getMapper(DecisionStrategyMapper.class).selectByPrimaryKey(strategyId);
			//标准型
			if (strategy.getType()==1) {
				List<DecisionStrule> list = session.getMapper(DecisionStruleMapper.class).selectByStrategyId(strategyId);
				for (DecisionStrule strule : list) {
//					doStrategy = session.getMapper(DecisionRuleMgrImpl.class).doRule(bo, strule.getRuleId());
					doStrategy = ApplicationContextProvider.getBean(DecisionRuleMgrImpl.class).doRule(bo, strule.getRuleId());
				}
			}else {
				//决策树-暂无
			}
			return doStrategy;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception (e);
		}
	}

}
