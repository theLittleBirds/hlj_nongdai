package com.nongyeos.loan.core.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusIntopieceScore;
import com.nongyeos.loan.admin.service.IBusIntopieceScoreService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.entity.AppItemScvar;
import com.nongyeos.loan.app.entity.AppScore;
import com.nongyeos.loan.app.entity.ScoreClass;
import com.nongyeos.loan.app.entity.ScoreComcase;
import com.nongyeos.loan.app.entity.ScoreComvalue;
import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.entity.ScoreScvarcase;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IAppItemScvarService;
import com.nongyeos.loan.app.service.IAppItemService;
import com.nongyeos.loan.app.service.IAppScoreService;
import com.nongyeos.loan.app.service.IClassService;
import com.nongyeos.loan.app.service.IScoreComcaseService;
import com.nongyeos.loan.app.service.IScoreComvalueService;
import com.nongyeos.loan.app.service.IScoreService;
import com.nongyeos.loan.app.service.IScvarCaseService;
import com.nongyeos.loan.app.service.IScvarService;
import com.nongyeos.loan.base.util.ApplicationContextProvider;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.ItemMgr;
import com.nongyeos.loan.core.service.ScoreMgr;

@Service("scoreMgr")
public class ScoreMgrImpl implements ScoreMgr{
	
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	@Autowired
	private IAppScoreService appScoreService;//应用_应用评分卡关联表
	@Autowired
	private IScoreService scoreService;//业务_评分卡
	@Autowired
  	private IScvarService scvarService;//业务_评分变量
	@Autowired
	private IScvarCaseService scvarCaseService;//业务_评分实例
	@Autowired
	private IAppItemScvarService appItemScvarService;//应用_数据项评分变量关联
	@Autowired
	private IAppItemService appItemService;//应用_数据项
	@Autowired
	private ItemMgr itemMgr;//数据项服务
	@Autowired
	private IClassService classService;//评分等级
	@Autowired
	private IBusIntopieceScoreService busIntopieceScoreService;//进件-评分卡   子实体
	@Autowired
	private IScoreComvalueService scoreComvalueService;//评分_组合值
	@Autowired
	private IScoreComcaseService scoreComcaseService;//评分_组合实例
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ScoreScore> calculate2(BusinessObject scoreMgr, String appscId, SqlSession session) throws Exception{
        return ApplicationContextProvider.getBean(ScoreMgrImpl.class).calculate(scoreMgr, appscId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ScoreScore> calculate(BusinessObject scoreMgr, String appscId) throws Exception{
		//BusinessObject 的AppEntry entry，Object bean 和 AppApplication curApp在controller赋值
		try {
			if(scoreMgr == null){
				throw new Exception("评分对象为空");
			}
			//获的appEntry
			AppEntry entry = scoreMgr.getEntry();
			if(entry == null){
				throw new Exception("业务_状态记录对象AppEntry为空");
			}
			
			//所有评分卡
			List<ScoreScore> scoreScoreList = new ArrayList<ScoreScore>();
			if(StringUtils.isEmpty(appscId)){
				AppEntry appEntry = appEntryService.queryByAppModeId(entry.getModeId());
				
				//通过AppId查找T_APP_APPSCORE
				List<AppScore> appScoreList = appScoreService.selectByAppId(appEntry.getAppId());
				if(appScoreList==null || appScoreList.size()<=0){
					throw new Exception("没有关联的评分卡");
				}
				
				for (AppScore appScore : appScoreList) {
					//获的评分卡
					ScoreScore scoreScore = scoreService.getScoreById(appScore.getScoreId());
					if(scoreScore == null){
						throw new Exception("没有配置评分卡");
					}
					//评分变量
					List<ScoreScvar> _scoreScvarList = scvarService.selectAll(scoreScore.getScoreId());
					if(_scoreScvarList == null || _scoreScvarList.size()<=0){
						throw new Exception("评分卡没有评分变量");
					}
					//相关评分卡的评分变量
					scoreScore.setScoreScvarList(_scoreScvarList);
					//相关评分卡的 相关应用id
					scoreScore.setAppId(appEntry.getAppId());
					
					scoreScoreList.add(scoreScore);
				}
			}else{
				/**
				 * 应用_应用评分卡关联表的APPSC_ID
				 */
				//获的应用_应用评分卡关联表对象
				AppScore appScore = appScoreService.selectByAppscId(appscId);
				if(appScore == null){
					throw new Exception("产品应用没有关联评分卡");
				}
				//获的评分卡
				ScoreScore scoreScore = scoreService.getScoreById(appScore.getScoreId());
				if(scoreScore == null){
					throw new Exception("没有配置评分卡");
				}
				//评分变量
				List<ScoreScvar> _scoreScvarList = scvarService.selectAll(scoreScore.getScoreId());
				if(_scoreScvarList == null || _scoreScvarList.size()<=0){
					throw new Exception("评分卡没有评分变量");
				}
				//相关评分卡的评分变量
				scoreScore.setScoreScvarList(_scoreScvarList);
				//相关评分卡的 相关应用id
				scoreScore.setAppId(entry.getAppId());
				
				scoreScoreList.add(scoreScore);
			}
			
			//获的每张评分卡的总分
			for(ScoreScore scoreScore : scoreScoreList){
				//每张评分卡的评分变量
				List<ScoreScvar> scoreScvarList = scoreScore.getScoreScvarList();
				
				//初始化总分
				int scoreTotal = 0;
				for (ScoreScvar scoreScvar : scoreScvarList) {
					//不是特殊类型的判断评分变量的变量类型  VAR_TYPE
					//基本型
					if(Constants.SCORE_SCVAR_BASE.equals(scoreScvar.getVarType())){
						//应用_数据项评分变量关联
						AppItemScvar scvar = appItemScvarService.queryByScvarId(scoreScvar.getScvarId(),scoreScore.getScoreId(),scoreScore.getAppId());
						
						if(scvar !=  null){
							//评分变量关联的数据项
							AppItem appItem = appItemService.selectItem(scvar.getItemId());
							
							//数据项服务    反射取的 “主实体”及“子实体”的属性值
							Object value = itemMgr.getObjectValue(scoreMgr, appItem.getItemId());
							
							//查询关联的评分变量评分判例
							List<ScoreScvarcase> ScoreScvarcaseList = scvarCaseService.selectAll(scvar.getScvarId());
							
							if(value != null){
								//评分判例
								for (ScoreScvarcase scoreScvarcase : ScoreScvarcaseList) {
									/**
									 * 下限值  0：包括  ，1：不包括，2：多次数，3：动态数据多选，4：数据项不为空，5：数据项为空
									 */
									//根据下限值判断是否为特殊类型（下限值不包括，包括不为特殊类型）
									// 0：包括  ，1：不包括 ，6：字符型
									if(Constants.SCORE_SCVARCASE_LOW_LIMIT.equals(scoreScvarcase.getLowLimit()) || 
											Constants.SCORE_SCVARCASE_LOW_NO_LIMIT.equals(scoreScvarcase.getLowLimit()) ||
											Constants.SCORE_SCVARCASE_LOW_VARCHAR.equals(scoreScvarcase.getLowLimit())){
										//不是特殊类型的判断评分变量的变量类型  VAR_TYPE
										//基本型
										if(Constants.SCORE_SCVAR_BASE.equals(scoreScvar.getVarType())){
											//数据项取值为null,评分不处理
											if(value != null && !StringUtils.isEmpty(String.valueOf(value))){
												//评分变量是数值型
												if(Constants.SCORE_SCVAR_NUM == scoreScvar.getDataType()){
													/**
													 *  找“起始”和“截止”的值，要求在这个区间
													 *  起始：start， 截止：end
													 */
													if(scoreScvarcase.getStart() == null){
														throw new Exception("请配置起始值");
													}
													if(scoreScvarcase.getEnd() == null){
														throw new Exception("请配置截止值");
													}
													if(scoreScvarcase.getStart() > scoreScvarcase.getEnd()){
														throw new Exception("起始值不能大于截止值");
													}
													//下包括，上不包括
													if(Constants.SCORE_SCVARCASE_LOW_LIMIT.equals(scoreScvarcase.getLowLimit()) && 
															Constants.SCORE_SCVARCASE_HIGH_NO_LIMIT.equals(scoreScvarcase.getHighLimit())){
														if(Double.valueOf(String.valueOf(value)) >= scoreScvarcase.getStart() && Double.valueOf(String.valueOf(value)) < scoreScvarcase.getEnd()){
															scoreTotal = scoreTotal + scoreScvarcase.getScore();
															System.out.println(scoreScvar.getCname()+":"+scoreTotal);
															break;
														}
													}
													
													//上包括，下不包括
													if(Constants.SCORE_SCVARCASE_LOW_NO_LIMIT.equals(scoreScvarcase.getLowLimit()) && 
															Constants.SCORE_SCVARCASE_HIGH_LIMIT.equals(scoreScvarcase.getHighLimit())){
														if(Double.valueOf(String.valueOf(value)) > scoreScvarcase.getStart() && Double.valueOf(String.valueOf(value)) <= scoreScvarcase.getEnd()){
															scoreTotal = scoreTotal + scoreScvarcase.getScore();
															System.out.println(scoreScvar.getCname()+":"+scoreTotal);
															break;
														}
													}
													
													//上下都包括
													if(Constants.SCORE_SCVARCASE_LOW_LIMIT.equals(scoreScvarcase.getLowLimit()) && 
															Constants.SCORE_SCVARCASE_HIGH_LIMIT.equals(scoreScvarcase.getHighLimit())){
														if(Double.valueOf(String.valueOf(value)) >= scoreScvarcase.getStart() && Double.valueOf(String.valueOf(value)) <= scoreScvarcase.getEnd()){
															scoreTotal = scoreTotal + scoreScvarcase.getScore();
															System.out.println(scoreScvar.getCname()+":"+scoreTotal);
															break;
														}
													}
													
													//上下都不包括
													if(Constants.SCORE_SCVARCASE_LOW_NO_LIMIT.equals(scoreScvarcase.getLowLimit()) && 
															Constants.SCORE_SCVARCASE_HIGH_NO_LIMIT.equals(scoreScvarcase.getHighLimit())){
														if(Double.valueOf(String.valueOf(value)) > scoreScvarcase.getStart() && Double.valueOf(String.valueOf(value)) < scoreScvarcase.getEnd()){
															scoreTotal = scoreTotal + scoreScvarcase.getScore();
															System.out.println(scoreScvar.getCname()+":"+scoreTotal);
															break;
														}
													}
												}
												
												//评分变量是字符型
												if(Constants.SCORE_SCVAR_VAR == scoreScvar.getDataType()){
													//找“起始文本”和“截止文本”的值，配置的时候“起始文本”和“截止文本”要一致
													//起始文本：startText， 截止文本：endText
													if(StringUtils.isEmpty(scoreScvarcase.getStartText())){
														throw new Exception("请配置起始文本");
													}
													if(StringUtils.isEmpty(scoreScvarcase.getEndText())){
														throw new Exception("请配置截止文本");
													}
													if(!scoreScvarcase.getStartText().equals(scoreScvarcase.getEndText())){
														throw new Exception("起始文本和截止文本不一致");
													}
													if(value.toString().equals(scoreScvarcase.getStartText())){
														//获的该评分变量的
														scoreTotal = scoreTotal + scoreScvarcase.getScore();
														System.out.println(scoreScvar.getCname()+":"+scoreTotal);
														break;
													}
												}
											}
										}
										
									}
									
									//2：多次数
									if(Constants.SCORE_SCVARCASE_LOW_TIMES.equals(scoreScvarcase.getLowLimit())){
										//评分变量是数值型    防止配成字符型导致不能计算分数
										if(Constants.SCORE_SCVAR_NUM == scoreScvar.getDataType()){
											//新总分 = 原总分 + 次数*评分判例得分
											scoreTotal = scoreTotal + (Integer.valueOf(String.valueOf(value))*scoreScvarcase.getScore());
											System.out.println(scoreScvar.getCname()+":"+scoreTotal);
											break;
										}
										
									}
									
									//3：动态数据多选
									if(Constants.SCORE_SCVARCASE_LOW_DYNAMIC.equals(scoreScvarcase.getLowLimit())){
										//评分变量是字符型
										if(Constants.SCORE_SCVAR_VAR == scoreScvar.getDataType()){
											//找“起始文本”和“截止文本”的值，配置的时候“起始文本”和“截止文本”要一致
											//起始文本：startText， 截止文本：endText
											if(StringUtils.isEmpty(scoreScvarcase.getStartText())){
												throw new Exception("请配置起始文本");
											}
											if(StringUtils.isEmpty(scoreScvarcase.getEndText())){
												throw new Exception("请配置截止文本");
											}
											if(!scoreScvarcase.getStartText().equals(scoreScvarcase.getEndText())){
												throw new Exception("起始文本和截止文本不一致");
											}
											
											String[] checkValue = value.toString().split(",");
											for (String _checkValue : checkValue) {
												if(_checkValue.equals(scoreScvarcase.getStartText())){
													//获的该评分变量的
													scoreTotal = scoreTotal + scoreScvarcase.getScore();
													System.out.println(scoreScvar.getCname()+":"+scoreTotal);
												}
											}
										}
									}							
									
									//4：数据项不为空
									if(Constants.SCORE_SCVARCASE_LOW_VALUE.equals(scoreScvarcase.getLowLimit())){
										if(value != null && !StringUtils.isEmpty(String.valueOf(value))){
											//获的该评分变量的
											scoreTotal = scoreTotal + scoreScvarcase.getScore();
											System.out.println(scoreScvar.getCname()+":"+scoreTotal);
											break;
										}
									}
									
									//5：数据项为空
									if(Constants.SCORE_SCVARCASE_LOW_EMPTY.equals(scoreScvarcase.getLowLimit())){
										if(value == null || StringUtils.isEmpty(String.valueOf(value))){
											//获的该评分变量的
											scoreTotal = scoreTotal + scoreScvarcase.getScore();
											System.out.println(scoreScvar.getCname()+":"+scoreTotal);
											break;
										}
									}
									System.out.println(scoreScvar.getCname()+":"+scoreTotal);
								
								}
							}
							//每张评分卡的总分
							scoreScore.setScoreTotal(scoreTotal);
						}
					}

					//组合型
					if(Constants.SCORE_SCVAR_GROUP.equals(scoreScvar.getVarType())){
						//评分_组合值List
						List<ScoreComvalue> ScoreComvalueList = scoreComvalueService.selectByPage(scoreScvar.getScvarId());
						
						for (ScoreComvalue scoreComvalue : ScoreComvalueList) {
							//如果组合实例和数据项取值对应上，flag更改为true，已经所有判断后flag依然为true，即该组合变量的组合值为评分判例判分的标准
							boolean flag = false;
							//评分_组合实例
							List<ScoreComcase> ScoreComcaseList= scoreComcaseService.selectByPage(scoreComvalue.getCvId());
							for (ScoreComcase scoreComcase : ScoreComcaseList) {
								//查找该评分变量
								ScoreScvar _scoreScvar = scvarService.getScvarById(scoreComcase.getScvarId());
								
								//应用_数据项评分变量关联
								AppItemScvar _scvar = appItemScvarService.queryByScvarId(scoreComcase.getScvarId(),scoreScore.getScoreId(),scoreScore.getAppId());
							
								if(_scvar !=  null){
									//评分变量关联的数据项
									AppItem _appItem = appItemService.selectItem(_scvar.getItemId());
									
									//数据项服务    反射取的 “主实体”及“子实体”的属性值
									Object _value = itemMgr.getObjectValue(scoreMgr, _appItem.getItemId());
									
									//评分变量是数值型
									if(Constants.SCORE_SCVAR_NUM == _scoreScvar.getDataType()){
										/**
										 *  找“起始”和“截止”的值，要求在这个区间
										 *  起始：start， 截止：end
										 */
										if(scoreComcase.getStart() == null){
											throw new Exception("请配置起始值");
										}
										if(scoreComcase.getEnd() == null){
											throw new Exception("请配置截止值");
										}
										if(scoreComcase.getStart() > scoreComcase.getEnd()){
											throw new Exception("起始值不能大于截止值");
										}
										if(_value!=null){
											//下包括，上不包括
											if(Constants.SCORE_SCVARCASE_LOW_LIMIT.equals(scoreComcase.getLowLimit()) && 
													Constants.SCORE_SCVARCASE_HIGH_NO_LIMIT.equals(scoreComcase.getHighLimit())){
												if(Integer.valueOf(String.valueOf(_value)) >= scoreComcase.getStart() && Integer.valueOf(String.valueOf(_value)) < scoreComcase.getEnd()){
													flag = true;
												}else{
													flag = false;
													break;
												}
											}
											
											//上包括，下不包括
											if(Constants.SCORE_SCVARCASE_LOW_NO_LIMIT.equals(scoreComcase.getLowLimit()) && 
													Constants.SCORE_SCVARCASE_HIGH_LIMIT.equals(scoreComcase.getHighLimit())){
												if(Integer.valueOf(String.valueOf(_value)) > scoreComcase.getStart() && Integer.valueOf(String.valueOf(_value)) <= scoreComcase.getEnd()){
													flag = true;
												}else{
													flag = false;
													break;
												}
											}
											
											//上下都包括
											if(Constants.SCORE_SCVARCASE_LOW_LIMIT.equals(scoreComcase.getLowLimit()) && 
													Constants.SCORE_SCVARCASE_HIGH_LIMIT.equals(scoreComcase.getHighLimit())){
												if(Integer.valueOf(String.valueOf(_value)) >= scoreComcase.getStart() && Integer.valueOf(String.valueOf(_value)) <= scoreComcase.getEnd()){
													flag = true;
												}else{
													flag = false;
													break;
												}
											}
											
											//上下都不包括
											if(Constants.SCORE_SCVARCASE_LOW_NO_LIMIT.equals(scoreComcase.getLowLimit()) && 
													Constants.SCORE_SCVARCASE_HIGH_NO_LIMIT.equals(scoreComcase.getHighLimit())){
												if(Integer.valueOf(String.valueOf(_value)) > scoreComcase.getStart() && Integer.valueOf(String.valueOf(_value)) < scoreComcase.getEnd()){
													flag = true;
												}else{
													flag = false;
													break;
												}
											}
										}
									}
									//评分变量是字符型
									if(Constants.SCORE_SCVAR_VAR == _scoreScvar.getDataType()){
										//找“起始文本”和“截止文本”的值，配置的时候“起始文本”和“截止文本”要一致
										//起始文本：startText， 截止文本：endText
										if(StringUtils.isEmpty(scoreComcase.getStartText())){
											throw new Exception("请配置组合变量起始文本");
										}
										if(StringUtils.isEmpty(scoreComcase.getEndText())){
											throw new Exception("请配置组合变量截止文本");
										}
										if(!scoreComcase.getStartText().equals(scoreComcase.getEndText())){
											throw new Exception("组合变量的起始文本和截止文本不一致");
										}
										if(_value!=null && _value.toString().equals(scoreComcase.getStartText())){
											flag = true;
										}else{
											flag = false;
											break;
										}
									}
								}
							}
							
							if(flag){
								//查询关联的评分变量评分判例
								List<ScoreScvarcase> scoreComvalueList = scvarCaseService.selectAll(scoreComvalue.getScvarId());
								for (ScoreScvarcase _scoreComvalue : scoreComvalueList) {
									//评分变量是字符型
									if(Constants.SCORE_SCVAR_VAR == scoreScvar.getDataType()){
										//找“起始文本”和“截止文本”的值，配置的时候“起始文本”和“截止文本”要一致
										//起始文本：startText， 截止文本：endText
										if(StringUtils.isEmpty(_scoreComvalue.getStartText())){
											throw new Exception("请配置起始文本");
										}
										if(StringUtils.isEmpty(_scoreComvalue.getEndText())){
											throw new Exception("请配置截止文本");
										}
										if(!_scoreComvalue.getStartText().equals(_scoreComvalue.getEndText())){
											throw new Exception("起始文本和截止文本不一致");
										}
										if(StringUtils.isEmpty(scoreComvalue.getComValue())){
											throw new Exception("组合变量没有组合值");
										}
										if(scoreComvalue.getComValue().equals(_scoreComvalue.getStartText())){
											//获的该评分变量的
											scoreTotal = scoreTotal + _scoreComvalue.getScore();
											System.out.println(scoreScvar.getCname()+":"+scoreTotal);
											break;
										}
									}	
								}
								//每张评分卡的总分
								scoreScore.setScoreTotal(scoreTotal);
							}
						}
					}
					
				}
				//删除旧历史评分
				busIntopieceScoreService.deleteByIpId(entry.getModeId(),scoreScore.getScoreId());
			}
		
			//通过评分卡分数得到评分等级
			for(ScoreScore scoreScore : scoreScoreList){
				List<ScoreClass> scoreClassList = classService.selectAll(scoreScore.getScoreId());
				if(scoreClassList == null || scoreClassList.size()<=0){
					System.out.println(scoreScore.getScoreId());
					throw new Exception("请配置评分卡的评分等级");
				}
				for(ScoreClass scoreClass : scoreClassList){
					Float startScore = scoreClass.getStartScore();
					Float endScore = scoreClass.getEndScore();
					Float score = (float) scoreScore.getScoreTotal();
					if(score >= startScore && score <= endScore){
						//评分等级
						scoreScore.setScoreClass(scoreClass);
						
						BusIntopieceScore intopieceScore = new BusIntopieceScore();
						intopieceScore.setId(UUIDUtil.getUUID());//主键id
						intopieceScore.setIntoPieceId(entry.getModeId());//进件id
						intopieceScore.setScoreId(scoreScore.getScoreId());//评分卡id
						intopieceScore.setClassId(scoreClass.getClassId());//评分等级id
						intopieceScore.setClassName(scoreClass.getName());//评分等级名称
						intopieceScore.setScoreTotal(String.valueOf(scoreScore.getScoreTotal()));//评分卡总分数
						intopieceScore.setCreatetime(DateUtils.getNowDate());
						busIntopieceScoreService.addIntopieceScoreSelective(intopieceScore);
					}
				}
			}
			
			return scoreScoreList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public static void main(String[] args) throws Exception{
		BusIntoPiece intoPiece = new BusIntoPiece();
		intoPiece.setName("zhang san");
		
		System.out.println(intoPiece.getClass());
		System.out.println(intoPiece.getClass().getName());
		
		Field field = intoPiece.getClass().getDeclaredField("name");
		field.setAccessible(true); 
		Object val = field.get(intoPiece);
		System.out.println( "name:" +field.getName()+ ", value=" +val);
		
		Float f = 10.11111111f;
		Float s = 20.2f;
		Float i = (float) 15;
		System.out.println(f);
		System.out.println(s);
		System.out.println(i > f);
		System.out.println(i < s);
	}
}
