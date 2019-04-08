package com.nongyeos.loan.base.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntity;
import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.entity.AppItemScvar;
import com.nongyeos.loan.app.entity.AppItemsection;
import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.app.entity.AppScore;
import com.nongyeos.loan.app.entity.AppSection;
import com.nongyeos.loan.app.entity.AppSrvunit;
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.entity.DecisionCasebase;
import com.nongyeos.loan.app.entity.DecisionPolicybase;
import com.nongyeos.loan.app.entity.DecisionPolicycase;
import com.nongyeos.loan.app.entity.DecisionRule;
import com.nongyeos.loan.app.entity.DecisionRuleact;
import com.nongyeos.loan.app.entity.DecisionStrategy;
import com.nongyeos.loan.app.entity.DecisionStrule;
import com.nongyeos.loan.app.entity.FlowData;
import com.nongyeos.loan.app.entity.FlowDirection;
import com.nongyeos.loan.app.entity.FlowEntrance;
import com.nongyeos.loan.app.entity.FlowNevent;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.entity.ScoreClass;
import com.nongyeos.loan.app.entity.ScoreComcase;
import com.nongyeos.loan.app.entity.ScoreComvalue;
import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.entity.ScoreScvarcase;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.AppEntityMapper;
import com.nongyeos.loan.app.mapper.AppItemMapper;
import com.nongyeos.loan.app.mapper.AppItemScvarMapper;
import com.nongyeos.loan.app.mapper.AppItemsectionMapper;
import com.nongyeos.loan.app.mapper.AppParaMapper;
import com.nongyeos.loan.app.mapper.AppScoreMapper;
import com.nongyeos.loan.app.mapper.AppSectionMapper;
import com.nongyeos.loan.app.mapper.AppSrvunitMapper;
import com.nongyeos.loan.app.mapper.DecisionActionMapper;
import com.nongyeos.loan.app.mapper.DecisionCasebaseMapper;
import com.nongyeos.loan.app.mapper.DecisionPolicybaseMapper;
import com.nongyeos.loan.app.mapper.DecisionPolicycaseMapper;
import com.nongyeos.loan.app.mapper.DecisionRuleMapper;
import com.nongyeos.loan.app.mapper.DecisionRuleactMapper;
import com.nongyeos.loan.app.mapper.DecisionStrategyMapper;
import com.nongyeos.loan.app.mapper.DecisionStruleMapper;
import com.nongyeos.loan.app.mapper.FlowDataMapper;
import com.nongyeos.loan.app.mapper.FlowDirectionMapper;
import com.nongyeos.loan.app.mapper.FlowEntranceMapper;
import com.nongyeos.loan.app.mapper.FlowNeventMapper;
import com.nongyeos.loan.app.mapper.FlowNodeMapper;
import com.nongyeos.loan.app.mapper.ScoreClassMapper;
import com.nongyeos.loan.app.mapper.ScoreComcaseMapper;
import com.nongyeos.loan.app.mapper.ScoreComvalueMapper;
import com.nongyeos.loan.app.mapper.ScoreScoreMapper;
import com.nongyeos.loan.app.mapper.ScoreScvarMapper;
import com.nongyeos.loan.app.mapper.ScoreScvarcaseMapper;
import com.nongyeos.loan.base.util.Constants;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DataCopy {
	
	@Autowired
	private DecisionPolicybaseMapper baseMapper;
	@Autowired
	private DecisionPolicycaseMapper caseMapper;
	@Autowired
	private DecisionCasebaseMapper cabaMapper;
	@Autowired
	private DecisionActionMapper actionMapper;
	@Autowired
	private DecisionRuleMapper ruleMapper;
	@Autowired
	private DecisionStrategyMapper strategyMapper;
	@Autowired
	private AppApplicationMapper applicationMapper;
	@Autowired
	private AppEntityMapper entityMapper;
	@Autowired
	private AppSectionMapper sectionMapper;
	@Autowired
	private DecisionRuleactMapper ruleActMapper;
	@Autowired
	private DecisionStruleMapper stRuleMapper;
	@Autowired
	private AppScoreMapper appScoreMapper;
	@Autowired
	private ScoreScoreMapper scoreMapper;
	@Autowired
	private ScoreScvarMapper scvarMapper;
	@Autowired
	private ScoreScvarcaseMapper scvarCaseMapper;
	@Autowired
	private ScoreClassMapper scoreClassMapper;
	@Autowired
	private ScoreComvalueMapper comValueMapper;
	@Autowired
	private ScoreComcaseMapper comCaseMapper;
	@Autowired
	private AppItemScvarMapper itemScvarMapper;
	@Autowired
	private AppItemMapper itemMapper;
	@Autowired
	private AppItemsectionMapper itemSectionMapper;
	@Autowired
	private AppSrvunitMapper srvunitMapper;
	@Autowired
	private FlowNodeMapper nodeMapper;
	@Autowired
	private FlowNeventMapper neventMapper;
	@Autowired
	private FlowDirectionMapper directionMapper;
	@Autowired
	private FlowDataMapper dataMapper;
	@Autowired
	private FlowEntranceMapper entranceMapper;
	@Autowired
	private AppParaMapper paraMapper;
	@Resource
	private ISysSenoService sysSenoService;
	
	Map<String,String> entityMap = new HashMap<String,String>();  //旧实体和新实体的数组
	Map<String,String> itemMap = new HashMap<String,String>();    //旧数据项和新数据项的数组
	Map<String,String> sectionMap = new HashMap<String,String>(); //区段数组
	Map<String,String> baseMap = new HashMap<String,String>();    //旧基本条件和新基本条件的数组
	Map<String,String> caseMap = new HashMap<String,String>();    //旧决策条件和新决策条件的数组
	Map<String,String> actionMap = new HashMap<String,String>();  //旧执行措施和新执行措施的数组
	Map<String,String> ruleMap = new HashMap<String,String>();    //旧决策规则和新决策规则的数组
	Map<String,String> strategyMap = new HashMap<String,String>();//旧策略和新策略数组
	Map<String,String> scoreMap = new HashMap<String,String>();   //评分卡数组
	Map<String,String> scvarMap = new HashMap<String,String>();   //评分变量数组
	Map<String,String> comValueMap = new HashMap<String,String>();//组合值数组
	Map<String,String> appScoreMap = new HashMap<String,String>();   //应用评分卡关联数组
	Map<String,String> nodeMap = new HashMap<String,String>();    //节点数组
	Map<String,String> srvunMap = new HashMap<String,String>();
	//@Test
	public void appCopy(){
		String OappId = "A000000001";
		String finsId = "F00000000003";
		String groupId = "AG00000022";
		
		AppApplication appBean = applicationMapper.selectByPrimaryKey(OappId);
		
		if(appBean != null){
			appBean.setAppId(sysSenoService.getNextString(
					Constants.TABLE_NAME_APPLICATION, 10, "A", 1));
			appBean.setFinsCode(finsId);
			appBean.setGroupId(groupId);
			applicationMapper.insertSelective(appBean);
			this.entityCopy(OappId, appBean.getAppId());  //复制实体
			this.sectionCopy(OappId, appBean.getAppId()); //复制区段
			this.paraCopy(OappId, appBean.getAppId());    //复制参数
		    this.baseCopy(OappId, appBean.getAppId());    //复制基本条件
		    this.caseCopy(OappId, appBean.getAppId());    //复制决策条件
		    this.actionCopy(OappId, appBean.getAppId());  //复制措施
		    this.ruleCopy(OappId, appBean.getAppId());    //复制决策规则
		    this.strategyCopy(OappId, appBean.getAppId());//复制策略
			this.srvunitCopy(OappId, appBean.getAppId()); //复制通用服务
		    this.scoreCopy(OappId, appBean.getAppId(), finsId); //评分卡复制
		    this.nodeCopy(OappId, appBean.getAppId());    //流程节点复制
		    this.entranceCopy(OappId, appBean.getAppId());//复制流程入口
		}
	}
	
	//实体复制
	public void entityCopy(String OappId, String NappId){
		String OentityId = "";
		String parentId = "";
		String parentIds = "";
		String parentItemId = "";
		String[] parentIds2;
		List<AppEntity> entityList = entityMapper.selectAll(OappId);     
		if(entityList != null && entityList.size() > 0){
			for(AppEntity entityBean : entityList){
				OentityId = entityBean.getEntityId();
				entityBean.setEntityId(sysSenoService.getNextString(Constants.TABLE_NAME_ENTITY, 10, "ET", 1));
				entityBean.setAppId(NappId);
				entityMapper.insertSelective(entityBean);
				entityMap.put(OentityId, entityBean.getEntityId());   //旧实体和新实体放入数组
				this.itemCopy(OentityId,entityBean.getEntityId());
			}
		}
		List<AppEntity> entityList2 = entityMapper.selectAll(NappId);
		if(entityList2 != null && entityList2.size() > 0){
			for(AppEntity entityBean : entityList2){
				StringBuffer pBuffer = new StringBuffer();
				parentId = entityBean.getParentId();
				parentIds = entityBean.getParentIds();
				parentItemId = entityBean.getParentItemId();
				if(parentId != null && !parentId.equals("")){
					entityBean.setParentId(entityMap.get(parentId));
				}
				if(parentIds != null && !parentIds.equals("")){
					parentIds2 = parentIds.split(",");
					for(String parentId2 : parentIds2){
						pBuffer.append(entityMap.get(parentId2)+",");
					}
					entityBean.setParentIds(pBuffer.toString());
				}
				if(parentItemId != null && !parentItemId.equals("")){
					entityBean.setParentItemId(itemMap.get(parentItemId));
				}
				entityMapper.updateByPrimaryKey(entityBean);
			}
		}
		System.out.println("实体复制完成");
	}
	
	//数据项复制
	public void itemCopy(String OentityId, String NentityId){
		String OitemId = "";
		List<AppItem> itemList = itemMapper.selectByEntityId(OentityId);
		if(itemList != null && itemList.size() > 0){
			for(AppItem itemBean : itemList){
				OitemId = itemBean.getItemId();
				itemBean.setItemId(sysSenoService.getNextString(Constants.TABLE_NAME_ITEM, 12, "IE", 1));
				itemBean.setEntityId(NentityId);
				itemMapper.insertSelective(itemBean);
				itemMap.put(OitemId, itemBean.getItemId());
			}
		}
	}
	
	//区段复制
	public void sectionCopy(String OappId, String NappId){
		String OsectionId = "";
		List<AppSection> sectionList = sectionMapper.selectAll(OappId);
		if(sectionList != null && sectionList.size() > 0){
			for(AppSection sectionBean : sectionList){
				OsectionId = sectionBean.getSectionId();
				sectionBean.setSectionId(sysSenoService.getNextString(Constants.TABLE_NAME_SECTION, 10, "SE", 1));
				sectionBean.setAppId(NappId);
				sectionMapper.insertSelective(sectionBean);
				sectionMap.put(OsectionId, sectionBean.getSectionId());
				//区段数据项关联复制
				List<AppItemsection> itemSectionList = itemSectionMapper.selectBySectionId(OsectionId);
				if(itemSectionList != null && itemSectionList.size() > 0){
					for(AppItemsection itemSectionBean : itemSectionList){
						itemSectionBean.setSeitId(null);
						itemSectionBean.setSectionId(sectionBean.getSectionId());
						itemSectionBean.setItemId(itemMap.get(itemSectionBean.getItemId()));
						itemSectionMapper.insertSelective(itemSectionBean);
					}
				}
			}
		}
		System.out.println("区段复制完成");
	}
	
	//通用服务复制
	public void srvunitCopy(String OappId, String NappId){
		String OsrvunId = "";
		List<AppSrvunit> srvunitList = srvunitMapper.selectByAppid(OappId);
		if(srvunitList != null && srvunitList.size() > 0){
			for(AppSrvunit srvunitBean : srvunitList){
				OsrvunId = srvunitBean.getSrvunId();
				srvunitBean.setSrvunId((sysSenoService.getNextString(Constants.TABLE_NAME_SRVUNIT, 10, "SU", 1)));
				srvunitBean.setAppId(NappId);
				srvunitBean.setTardataId(strategyMap.get(srvunitBean.getTardataId()));
				srvunitMapper.insertSelective(srvunitBean);
				srvunMap.put(OsrvunId, srvunitBean.getSrvunId());
			}
		}
		System.out.println("通用服务复制完成");
	}
	
	//数据参数复制
	public void paraCopy(String OappId, String NappId){
		List<AppPara> paraList = paraMapper.selectAll(OappId);
		if(paraList != null && paraList.size() > 0){
			for(AppPara paraBean : paraList){
				paraBean.setId(null);
				paraBean.setAppId(NappId);
				paraMapper.insertSelective(paraBean);
			}
		}
		System.out.println("数据参数复制完成");
	}
	
	//决策基本条件复制
	public void baseCopy(String OappId, String NappId){
		String ObaseId = "";
		List<DecisionPolicybase> baseList = baseMapper.selectAllByAppId(OappId);
		if(baseList != null && baseList.size() > 0){
			for(DecisionPolicybase baseBean : baseList){
				ObaseId = baseBean.getBaseId();
				baseBean.setBaseId(sysSenoService.getNextString(Constants.TABLE_NAME_POLICYBASE, 10, "BS", 1));
				baseBean.setAppId(NappId);
				baseBean.setItemId(itemMap.get(baseBean.getItemId()));
				baseMapper.insertSelective(baseBean);
				baseMap.put(ObaseId, baseBean.getBaseId());   
			}
		}
		System.out.println("基本条件复制完成");
	}
	
	//决策条件复制
	public void caseCopy(String OappId, String NappId){
		String OcaseId = "";
		List<DecisionPolicycase> caseList = caseMapper.selectByAppId2(OappId);
		if(caseList != null && caseList.size() > 0){
			for(DecisionPolicycase caseBean : caseList){
				OcaseId = caseBean.getCaseId();
				caseBean.setCaseId(sysSenoService.getNextString(Constants.TABLE_NAME_POLICYCASE, 10, "CS", 1));
				caseBean.setAppId(NappId);
				caseMapper.insertSelective(caseBean);
				caseMap.put(OcaseId, caseBean.getCaseId());
				//基本条件与决策条件关联表
				List<DecisionCasebase> cabaList = cabaMapper.selectByCaseId(OcaseId);
				if(cabaList != null && cabaList.size() > 0){
					for(DecisionCasebase cabaBean : cabaList){
						cabaBean.setCsbaseId(null);
						cabaBean.setCaseId(caseBean.getCaseId());
						cabaBean.setBaseId(baseMap.get(cabaBean.getBaseId()));
						cabaMapper.insertSelective(cabaBean);
					}
				}
			}
		}
		System.out.println("决策条件复制完成");
	}
	
	//执行措施复制
	public void actionCopy(String OappId, String NappId){
		String OactionId = "";
		String checkItemIds = "";
		String [] checkItemId;
		List<DecisionAction> actionList = actionMapper.selectByAppId(OappId);
		if(actionList != null && actionList.size() > 0){
			for(DecisionAction actionBean : actionList){
				StringBuffer checkBuffer = new StringBuffer();
				OactionId = actionBean.getActionId();
				actionBean.setActionId((sysSenoService.getNextString(Constants.TABLE_NAME_APPSCORE, 12, "ACT", 1)));
				actionBean.setAppId(NappId);
				actionBean.setFromItemId(itemMap.get(actionBean.getFromItemId()));
				actionBean.setToItemId(itemMap.get(actionBean.getToItemId()));
				checkItemIds = actionBean.getCheckItemIds();
				if(checkItemIds != null && !checkItemIds.equals("")){
					checkItemId = checkItemIds.split(",");
					for(String itemId : checkItemId){
						checkBuffer.append(itemMap.get(itemId) + ",");
					}
					actionBean.setCheckItemIds(checkBuffer.toString());
				}
				actionMapper.insertSelective(actionBean);
				actionMap.put(OactionId, actionBean.getActionId());
			}
		}
		System.out.println("措施复制完成");
	}
	
	//决策规则复制
	public void ruleCopy(String OappId, String NappId){
		String OruleId = "";
		List<DecisionRule> ruleList = ruleMapper.selectByAppId(OappId);
		if(ruleList != null && ruleList.size() > 0){
			for(DecisionRule ruleBean : ruleList){
				OruleId = ruleBean.getRuleId();
				ruleBean.setRuleId(sysSenoService.getNextString(Constants.TABLE_NAME_RULE, 10, "RL", 1));
				ruleBean.setAppId(NappId);
				ruleBean.setCaseId(caseMap.get(ruleBean.getCaseId()));
				ruleMapper.insertSelective(ruleBean);
				ruleMap.put(OruleId, ruleBean.getRuleId());
				//规则措施关联表复制
				List<DecisionRuleact> ruleActList = ruleActMapper.selectAllByRuleId(OruleId);
				if(ruleActList != null && ruleActList.size() > 0){
					for(DecisionRuleact ruleActBean : ruleActList){
						ruleActBean.setId(null);
						ruleActBean.setRuleId(ruleBean.getRuleId());
						ruleActBean.setActionId(actionMap.get(ruleActBean.getActionId()));
						ruleActMapper.insertSelective(ruleActBean);
					}
				}
			}
		}
		System.out.println("规则复制完成");
	}
	
	//决策策略复制
	public void strategyCopy(String OappId, String NappId){
		String OstrategyId = "";
		List<DecisionStrategy> strategyList = strategyMapper.selectByAppId(OappId);
		if(strategyList != null && strategyList.size() > 0){
			for(DecisionStrategy strategyBean : strategyList){
				OstrategyId = strategyBean.getStrategyId();
				strategyBean.setStrategyId(sysSenoService.getNextString(Constants.TABLE_NAME_STRATEGY, 10, "DS", 1));
				strategyBean.setAppId(NappId);
				strategyMapper.insertSelective(strategyBean);
				strategyMap.put(OstrategyId, strategyBean.getStrategyId());
				//策略规则复制
				List<DecisionStrule> stRuleList = stRuleMapper.selectByStrategyId(OstrategyId);
				if(stRuleList != null && stRuleList.size() > 0){
					for(DecisionStrule stRuleBean : stRuleList){
						stRuleBean.setId(null);
						stRuleBean.setStrategyId(strategyBean.getStrategyId());
						stRuleBean.setRuleId(ruleMap.get(stRuleBean.getRuleId()));
						stRuleMapper.insertSelective(stRuleBean);
					}
				}
			}
		}
		System.out.println("策略复制完成");
	}
	
	//复制评分卡
	public void scoreCopy(String OappId, String NappId, String finsId){
		String OscoreId = "";
		String OappScId = "";
		//评分卡应用关联表
		List<AppScore> appScoreList = appScoreMapper.selectByAppId(OappId);
		if(appScoreList != null && appScoreList.size() > 0){
			for(AppScore appScoreBean : appScoreList){
				OappScId = appScoreBean.getAppscId();
				OscoreId = appScoreBean.getScoreId();
				ScoreScore scoreBean = scoreMapper.selectByPrimaryKey(OscoreId);
				if(scoreBean != null){
					scoreBean.setScoreId(sysSenoService.getNextString(Constants.TABLE_NAME_SCORE, 10, "SC", 1));
					scoreBean.setFinsId(finsId);
					scoreMapper.insertSelective(scoreBean);
					scoreMap.put(OscoreId, scoreBean.getScoreId());
					this.scvarCopy(OscoreId, scoreBean.getScoreId());
					this.scoreClassCopy(OscoreId, scoreBean.getScoreId());
				}
				appScoreBean.setAppscId(sysSenoService.getNextString(Constants.TABLE_NAME_APPSCORE, 12, "ASC", 1));
				appScoreBean.setAppId(NappId);
				appScoreBean.setScoreId(scoreBean.getScoreId());
				appScoreMapper.insertSelective(appScoreBean);
				appScoreMap.put(OappScId, appScoreBean.getAppscId());
				//数据项评分变量关联表
				List<AppItemScvar> itemScvarList = itemScvarMapper.selectByAppscId(OappScId);
				if(itemScvarList != null && itemScvarList.size() > 0){
					for(AppItemScvar itemScvarBean : itemScvarList){
						itemScvarBean.setItemscvarId(null);
						itemScvarBean.setAppscId(appScoreBean.getAppscId());
						itemScvarBean.setItemId(itemMap.get(itemScvarBean.getItemId()));
						itemScvarBean.setScoreId(scoreBean.getScoreId());
						itemScvarBean.setScvarId(scvarMap.get(itemScvarBean.getScvarId()));
						itemScvarBean.setPcId(caseMap.get(itemScvarBean.getPcId()));
						itemScvarBean.setAppId(NappId);
						itemScvarMapper.insertSelective(itemScvarBean);
					}
				}
			}
		}
		System.out.println("评分卡复制完成");
	}
	
	//复制评分变量
	public void scvarCopy(String OscoreId, String NscoreId){
		String OscvarId = "";
		String componentVarIds = "";
		String[] componentVarId;
		List<ScoreScvar> scvarList = scvarMapper.selectAll(OscoreId);
		if(scvarList != null && scvarList.size() > 0){
			for(ScoreScvar scvarBean : scvarList){
				OscvarId = scvarBean.getScvarId();
				scvarBean.setScvarId(sysSenoService.getNextString(Constants.TABLE_NAME_SCVAR, 10, "SV", 1));
				scvarBean.setScoreId(NscoreId);
				scvarMapper.insertSelective(scvarBean);
				scvarMap.put(OscvarId, scvarBean.getScvarId());
				this.scvarCaseCopy(OscvarId, scvarBean.getScvarId());
				this.comValueCopy(OscvarId, scvarBean.getScvarId());
			}
		}
		List<ScoreScvar> scvarList2 = scvarMapper.selectAll(NscoreId);
		if(scvarList2 != null && scvarList2.size() > 0){
			for(ScoreScvar scvarBean : scvarList2){
				StringBuffer sBuffer = new StringBuffer();
				componentVarIds = scvarBean.getComponentVarIds();
				if(componentVarIds != null && !componentVarIds.equals("")){
					componentVarId = componentVarIds.split(",");
					for(String componentVarId2 : componentVarId){
						sBuffer.append(scvarMap.get(componentVarId2)+",");
					}
					scvarBean.setComponentVarIds(sBuffer.toString());
					scvarMapper.updateByPrimaryKey(scvarBean);
				}
			}
		}
	}
	
	//复制评分实例
	public void scvarCaseCopy(String OscvarId, String NscvarId){
		List<ScoreScvarcase> scvarCaseList = scvarCaseMapper.selectAll(OscvarId);
		if(scvarCaseList != null && scvarCaseList.size() > 0){
			for(ScoreScvarcase scvarCaseBean : scvarCaseList){
				scvarCaseBean.setCaseId(sysSenoService.getNextString(Constants.TABLE_NAME_SCVARCASE, 10, "SCC", 1));
				scvarCaseBean.setScvarId(NscvarId);
				scvarCaseMapper.insertSelective(scvarCaseBean);
			}
		}
	}
	
	//复制评分等级
	public void scoreClassCopy(String OscoreId, String NscoreId){
		List<ScoreClass> scoreClassList = scoreClassMapper.selectAll(OscoreId);
		if(scoreClassList != null && scoreClassList.size() > 0){
			for(ScoreClass scoreClassBean : scoreClassList){
				scoreClassBean.setClassId(sysSenoService.getNextString(Constants.TABLE_NAME_CLASS, 10, "CL", 1));
				scoreClassBean.setScoreId(NscoreId);
				scoreClassMapper.insertSelective(scoreClassBean);
			}
		}
	}
	
	//复制组合值
	public void comValueCopy(String OscvarId, String NscvarId){
		String OcvId = "";
		List<ScoreComvalue> comValueList = comValueMapper.selectAll(OscvarId);
		if(comValueList != null && comValueList.size() > 0){
			for(ScoreComvalue comValueBean : comValueList){
				OcvId = comValueBean.getCvId();
				comValueBean.setCvId(sysSenoService.getNextString(Constants.TABLE_NAME_COMVALUE, 10, "CV", 1));
				comValueBean.setScvarId(NscvarId);
				comValueMapper.insertSelective(comValueBean);
				comValueMap.put(OcvId, comValueBean.getCvId());
				List<ScoreComcase> comCaseList = comCaseMapper.selectAll(OcvId);
				if(comCaseList != null && comCaseList.size() > 0){
					for(ScoreComcase comCaseBean : comCaseList){
						comCaseBean.setId(null);
						comCaseBean.setCvId(comValueBean.getCvId());
						comCaseBean.setScvarId(scvarMap.get(comCaseBean.getScvarId()));
						comCaseMapper.insertSelective(comCaseBean);
					}
				}
			}
		}
	}
	
	//流程节点复制
	public void nodeCopy(String OappId, String NappId){
		String OnodeId = "";
		String toNodeIds = "";
		String [] toNodeId;
		List<FlowNode> nodeList = nodeMapper.selectByAppId(OappId);
		if(nodeList != null && nodeList.size() > 0){
			for(FlowNode nodeBean : nodeList){
				OnodeId = nodeBean.getNodeId();
				nodeBean.setNodeId(sysSenoService.getNextString(Constants.TABLE_NAME_NODE, 10, "N", 1));
				nodeBean.setAppId(NappId);
				nodeMapper.insertSelective(nodeBean);
				nodeMap.put(OnodeId, nodeBean.getNodeId());
				this.neventCopy(OnodeId, nodeBean.getNodeId());
				this.dataCopy(OnodeId, nodeBean.getNodeId());
				//节点流向复制
				List<FlowDirection> directionList = directionMapper.selectByNodeId(OnodeId);
				if(directionList != null && directionList.size() > 0){
					for(FlowDirection directionBean : directionList){
						directionBean.setDirectionId(null);
						directionBean.setNodeId(nodeBean.getNodeId());
						directionBean.setPcId(caseMap.get(directionBean.getPcId()));
						directionMapper.insertSelective(directionBean);
					}
				}
			}
			//节点流向更新
			List<FlowNode> nodeList2 = nodeMapper.selectByAppId(NappId);
			if(nodeList2 != null && nodeList2.size() > 0){
				for(FlowNode nodebean : nodeList2){
					List<FlowDirection> directionList2 = directionMapper.selectByNodeId(nodebean.getNodeId());
					if(directionList2 != null && directionList2.size() > 0){
						for(FlowDirection directionBean : directionList2){
							StringBuffer nodeBuffer = new StringBuffer();
							toNodeIds = directionBean.getToNodeIds();
							if(toNodeIds != null && !toNodeIds.equals("")){
								toNodeId = toNodeIds.split(",");
								for(String toNodeId2 : toNodeId){
									nodeBuffer.append(nodeMap.get(toNodeId2)+",");
								}
							}
							directionBean.setToNodeIds(nodeBuffer.toString());
							directionMapper.updateByPrimaryKey(directionBean);
						}
					}
				}
			}
		}
	}
	
	//节点事件复制
	public void neventCopy(String OnodeId, String NnodeId){
		List<FlowNevent> neventList = neventMapper.selectByNodeId(OnodeId);
		if(neventList != null && neventList.size() > 0){
			for(FlowNevent neventBean : neventList){
				neventBean.setNeventId(sysSenoService.getNextString(Constants.TABLE_NAME_NODENEVENT, 12, "NE", 1));
				neventBean.setNodeId(NnodeId);
				neventBean.setSrvunId(srvunMap.get(neventBean.getSrvunId()));
				neventMapper.insertSelective(neventBean);
			}
		}
	}
	
	//数据权限复制
	public void dataCopy(String OnodeId, String NnodeId){
		List<FlowData> dataList = dataMapper.selectByNodeId(OnodeId);
		if(dataList != null && dataList.size() > 0){
			for(FlowData dataBean : dataList){
				dataBean.setDataId(null);
				dataBean.setNodeId(NnodeId);
				if(dataBean.getObjectType() == 1){
					dataBean.setObjectId(sectionMap.get(dataBean.getObjectId()));
				}
				if(dataBean.getObjectType() == 2){
					dataBean.setObjectId(itemMap.get(dataBean.getObjectId()));
				}
				dataMapper.insertSelective(dataBean);
			}
		}
	}
	
	//流程入口复制
	public void entranceCopy(String OappId, String NappId){
		List<FlowEntrance> entranceList = entranceMapper.selectByAppId(OappId);
		if(entranceList != null && entranceList.size() > 0){
			for(FlowEntrance entranceBean : entranceList){
				entranceBean.setEntranceId(null);
				entranceBean.setAppId(NappId);
				entranceBean.setPcId(caseMap.get(entranceBean.getPcId()));
				entranceBean.setStartNodeId(nodeMap.get(entranceBean.getStartNodeId()));
				entranceMapper.insertSelective(entranceBean);
			}
		}
	}
	

}
