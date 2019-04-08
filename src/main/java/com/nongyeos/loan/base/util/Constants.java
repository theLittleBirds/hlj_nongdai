package com.nongyeos.loan.base.util;

public class Constants {

	public static boolean DEBUG = true;										//调试信息显示
	
	public static final String LANGUAGE_SIMPLIFIEDCHINESE = "zh_CN";		//简体中文
	public static final String LANGUAGE_USENGLISH = "en_US";				//美国英文
	
	public static final String SESSION_LOCALE = "WW_TRANS_I18N_LOCALE";		//用户选择的Locale对应的key值	
	public static final String SESSION_USERCD = "UserCD";					//用户账号编号
	public static final String SESSION_PERSONCD = "PersonCD";				//人员编号
	public static final String SESSION_PERSONTYPE = "PersonType";			//人员机构权限
	public static final String SESSION_ORGCD = "OrgCD";						//所属机构编号
	public static final String SESSION_ORGNM = "OrgNM";						//所属机构名称
	public static final String SESSION_ORGCDBASE = "OrgCDBase";				//所属根机构编号
	public static final String SESSION_PERSONNM = "PersonNM";				//人员姓名
	public static final String SESSION_USERNM = "UserNM";				    //用户账号名称
	public static final String SESSION_ORGLIST = "OrgList";	                //登陆人有权限的机构
	public static final String SESSION_CLIENTIP = "ClientIP";				//客户端IP地址
	public static final int SESSION_TIMEOUT_TIME = 30;						//会话超时时间：分钟
	
	public static final short USER_STATUS_ENABLED = 1;						//用户状态：正常
	public static final short USER_TYPE_PRINCIPAL = 1;						//用户类别：主体人员
	
	public static final short PERSON_ROLE_ADMIN = 1;                        //登陆人机构权限是普通人员
	public static final short PERSON_ROLE_ORG = 2;                          //登陆人机构权限是机构管理员
	
	public static final short COMMON_STATUS_ENABLED = 1;					//通用状态：启用
	public static final short COMMON_STATUS_DISABLED = 0;					//通用状态：禁用
	public static final short COMMON_STATUS_ALL = 2;						//通用状态：所有
	
	public static final short COMMON_YES = 1;								//通用是否：是
	public static final short COMMON_NO = 0;								//通用是否：否
	
	public static final short COMMON_IS_DELETE = 1;							//软删除标志：是（删除，失效）
	public static final short COMMON_ISNOT_DELETE = 0;						//软删除标志：否（没删除，有效）
	
	public static final short SCORE_STATUS_ENABLED = 1;						//用户状态：启用
	
	public static final String ROLE_ADMIN = "R000000001";					//系统管理员角色编号
	public static final String ROLE_BUSINESS = "R000000002";					//业务管理员角色编号
	public static final String ROLE_SECURITY = "R000000003";					//安全保密员角色编号
	public static final String ROLE_AUDIT = "R000000004";                    //安全审计员角色编号
	public static final String ROLE_ORG = "R000000005";                      //机构管理员角色编号
	
	public static final short MENU_ADMIN = 1;								//系统管理菜单类
	public static final short MENU_BUSINESS = 2;							//业务管理菜单类
	public static final short MENU_SECURITY = 3;							//安全保密菜单类
	public static final short MENU_AUDIT = 4;								//安全审计菜单类
	
	public static final short MAIN_ENTITYTYPE=1;							//主实体
	public static final short SON_ENTITYTYPE=2;								//子实体
	
	public static final short ENTRY_SCOPE_ALL = 1;							//数据范围_所有
	public static final short ENTRY_SCOPE_DONE = 2;							//数据范围_已完成
	public static final short ENTRY_SCOPE_DOING = 3;						//数据范围_审批中
	public static final short ENTRY_SCOPE_COMPOSING = 4;					//数据范围_起草中
	public static final short ENTRY_SCOPE_TODO = 6;							//数据范围_待审批
	public static final short ENTRY_SCOPE_MYCOMPOSED = 7;					//数据范围_我起草
	
	public static final short NODE_TYPE_BATCHSTART = 5;						//节点类型（状态）_批量导入起始
	public static final short NODE_TYPE_AUTO = 90;							//节点类型（状态）_自动
	public static final short NODE_TYPE_END = 100;							//节点类型（状态）_完成
	public static final short NODE_TYPE_MANUALPREDECISION = 80;				//节点类型（状态）_决策前人工处理
	public static final short NODE_TYPE_MANUALPOSTDECISION = 85;			//节点类型（状态）_决策后人工处理
	
	public static final short NEVENT_RUNTIME_ARRIVED = 10;					//节点事件运行时间_到达
	public static final short NEVENT_RUNTIME_START = 20;					//节点事件运行时间_开始
	public static final short NEVENT_RUNTIME_OPEN = 30;						//节点事件运行时间_打开
	public static final short NEVENT_RUNTIME_SAVE = 40;						//节点事件运行时间_保存
	public static final short NEVENT_RUNTIME_CLOSE = 50;					//节点事件运行时间_关闭
	public static final short NEVENT_RUNTIME_END = 100;						//节点事件运行时间_结束
	
	public static final short NEVENT_TYPE_AUTHENTICATION = 1;				//节点事件类型_身份认证
	public static final short NEVENT_TYPE_AUTHENTICATIONPHOTO = 2;			//节点事件类型_身份认证（带照片）
	public static final short NEVENT_TYPE_PHONEVERIFICATION = 10;			//节点事件类型_手机实名认证
	public static final short NEVENT_TYPE_UPAYDATA = 20;					//节点事件类型_查询银联消费变量数据
	public static final short NEVENT_TYPE_CRC = 30;							//节点事件类型_征信验证
	public static final short NEVENT_TYPE_SOCIAL = 40;						//节点事件类型_社保验证
	public static final short NEVENT_TYPE_POLICE = 50;						//节点事件类型_公安验证
	public static final short NEVENT_TYPE_LAW = 60;							//节点事件类型_司法验证
	public static final short NEVENT_TYPE_SCOREONLY = 70;					//节点事件类型_信用评分
	public static final short NEVENT_TYPE_SCOREDECISION = 80;				//节点事件类型_评分决策
	public static final short NEVENT_TYPE_DECISIONONLY = 90;				//节点事件类型_信用决策	
	
	public static final short DECISION_STATUS_PASS = 1;						//审贷结果_通过
	public static final short DECISION_STATUS_DECLINE = 2;					//审贷结果_拒绝
	public static final short DECISION_STATUS_UNDETERMINED = 3;				//审贷结果_待定
	
	public static final short APPROVER_TYPE_ROLE = 1;						//节点审批实体类型_角色
	public static final short APPROVER_TYPE_PERSON = 2;						//节点审批实体类型_人员
	public static final short APPROVER_TYPE_ORG = 3;						//节点审批实体类型_机构
	
	public static final short RULECATEGORY_ALL = 0;							//决策规则类别_所有
	public static final short RULECATEGORY_OVERRIDE_CREDITCLASS = 1;		//决策规则类别_修正信用等级
	public static final short RULECATEGORY_IMPLEMENT_CONTROL = 2;			//决策规则类别_实施与控制
	
	public static final String TABLE_NAME_USER = "T_SYS_USER";				//表名：用户账号
	public static final String TABLE_NAME_PERSON = "T_SYS_PERSON";			//表名：主体人员
	public static final String TABLE_NAME_ROLE = "T_SYS_ROLE";				//表名：角色
	public static final String TABLE_NAME_MENU = "T_SYS_MENU";				//表名：菜单
	public static final String TABLE_NAME_PARAGROUP = "T_SYS_PARAGROUP";		//表名：参数类别
	public static final String TABLE_NAME_PARA = "T_SYS_PARA";				//表名：参数
	public static final String TABLE_NAME_ORG = "T_SYS_ORG";					//表名：机构
	public static final String TABLE_NAME_FINS = "T_APP_FINS";				//表名：金融机构
	public static final String TABLE_NAME_AFORM = "T_APP_AFORM";			//表名：申请管理
	public static final String TABLE_NAME_ENTRY = "T_APP_ENTRY";			//表名：申请管理
	public static final String TABLE_NAME_VAR = "T_APP_VAR";				//表名：基础变量
	public static final String TABLE_NAME_ENTITY = "T_APP_ENTITY";			//表名：数据实体变量
	public static final String TABLE_NAME_SECTION = "T_APP_SECTION";		//表明：页面区段
	public static final String TABLE_NAME_SCORE = "T_APP_SCORE";			//表名：评分卡
	public static final String TABLE_NAME_SCVAR = "T_APP_SCVAR";			//表名：评分卡变量
	public static final String TABLE_NAME_SCVARCASE = "T_APP_SCVARCASE";	//表名：评分卡变量判例
	public static final String TABLE_NAME_APPLICATION = "T_APP_APPLICATION";//表名：应用
	public static final String TABLE_NAME_APPGROUP = "T_APP_APPGROUP";      //表名：应用分类
	public static final String TABLE_NAME_RPTGROUP = "T_APP_RPTGROUP";      //表名：统计报表分类
	public static final String TABLE_NAME_RPTENTRY = "T_APP_RPTENTRY";      //表名：统计报表基本记录
	public static final String TABLE_NAME_RPTTEMPLATE = "T_APP_RPTTEMPLATE";      //表名：统计报表模板	
	public static final String TABLE_NAME_RPTSOURCESQL = "T_APP_RPTSOURCESQL";      //表名：统计报表数据源
	public static final String TABLE_NAME_ITEM = "T_APP_ITEM";				//表名：数据项
	public static final String TABLE_NAME_MAPPING = "T_APP_MAPPING";		//表名：数据映射
	public static final String TABLE_NAME_FLOW = "T_APP_FLOW";				//表名：流程
	public static final String TABLE_NAME_NODE = "T_APP_NODE";				//表名：节点
	public static final String TABLE_NAME_NEVENT = "T_APP_NEVENT";			//表名：节点事件
	public static final String TABLE_NAME_POLICY = "T_APP_POLICY";			//表名：策略
	public static final String TABLE_NAME_DECISION = "T_APP_DECISION";		//表名：决策
	public static final String TABLE_NAME_CLASS = "T_APP_CLASS";			//表名：评分等级
	public static final String TABLE_NAME_COMVALUE = "T_APP_COMVALUE";			//表名：多变量组合
	public static final String TABLE_NAME_RULE = "T_APP_RULE";			    //表名：规则等级
	public static final String TABLE_NAME_STRATEGY = "T_APP_STRATEGY";	    //表名：规则策略
	public static final String TABLE_NAME_POLICYCASE = "T_APP_POLICYCASE";  //表名：决策判例
	public static final String TABLE_NAME_POLICYBASE = "T_APP_POLICYBASE";  //表名：基本判例
	public static final String TABLE_NAME_POLICYRULE = "T_APP_POLICYRULE";  //表名：决策规则
	public static final String TABLE_NAME_PROVIDER = "T_APP_PROVIDER";  	//表名：服务提供者
	public static final String TABLE_NAME_SERVICEIFA = "T_APP_SERVICEIFA";  //表名：抽象服务
	public static final String TABLE_NAME_SERVICEIMPL = "T_APP_SERVICEIMPL";//表名：服务实现
	public static final String TABLE_NAME_SERVICERESULT = "T_APP_SERVICERESULT";//表名：服务结果
	public static final String TABLE_NAME_NODENEVENT = "T_FLOW_NEVENT";//表名：节点事件
	public static final String TABLE_NAME_APPSCORE = "T_APP_APPSCORE";//
	public static final String TABLE_NAME_DECISIONACTION = "T_DECISION_ACTION";//表名：执行措施
	public static final String TABLE_NAME_SRVUNIT = "T_APP_SRVUNIT";//表名：执行措施
	
	public static final String UPLOAD_FILE_ROOT_Windows = "E:/aus/file";		//上传文件根文件夹Windows
	public static final String UPLOAD_FILE_ROOT_Linux = "/home/appnj2/local/upload/aus";	//上传文件根文件夹Linux
	
	public static final short PERSON_IS_DEFAULT = 1;						//人员默认身份
	
	public static final short OBJECT_TYPE_ROLE = 1;							//授权对象类别：角色
	public static final short OBJECT_TYPE_PERSON = 2;						//授权对象类别：人员
	public static final short OBJECT_TYPE_ORG = 3;							//授权对象类别：机构
	
	public static final short ENTRY_FROM_TERMINAL = 1;						//数据来源：业务终端
	public static final short ENTRY_FROM_ONLINE = 2;						//数据来源：线上获客
	public static final short ENTRY_FROM_BATCH = 9;							//数据来源：批量导入
	
	public static final String SERVICE_RETCODE_SUCCESS = "0000";			//服务成功代码
	public static final String SERVICE_RETMSG_SUCCESS = "服务成功";			//服务成功描述
	public static final String SERVICE_RETCODE_STOP = "2000";				//服务禁用代码
	public static final String SERVICE_RETMSG_STOP = "服务暂停";				//服务禁用描述
	public static final String SERVICE_RETCODE_EXCEPTION = "1001";			//系统异常代码
	public static final String SERVICE_RETMSG_EXCEPTION = "系统异常";			//系统异常描述
	public static final String SERVICE_RETCODE_PARAERR = "1002";			//参数错误代码
	public static final String SERVICE_RETMSG_PARAERR = "参数错误";			//参数错误描述
	public static final String SERVICE_RETCODE_SIGNERR = "1003";			//签名错误代码
	public static final String SERVICE_RETMSG_SIGNERR = "签名错误";			//签名错误描述
	public static final String SERVICE_RETCODE_PUBKEYERR = "1005";			//公钥异常代码
	public static final String SERVICE_RETMSG_PUBKEYERR = "公钥异常";			//公钥异常描述
	public static final String SERVICE_RETCODE_PATNINVALID = "1007";			//合作商无效或不存在代码
	public static final String SERVICE_RETMSG_PATNINVALID = "合作商无效或不存在";	//合作商无效或不存在描述
	public static final String SERVICE_RETCODE_REQMORE = "1008";			//超过请求次数代码
	public static final String SERVICE_RETMSG_REQMORE = "超过请求次数";		//超过请求次数描述
	public static final String SERVICE_RETCODE_PRDINVALID = "1010";							//该产品不存在或没有该产品权限代码
	public static final String SERVICE_RETMSG_PRDINVALID = "该产品不存在或没有该产品权限";		//该产品不存在或没有该产品权限描述
	public static final String SERVICE_RETCODE_LESSBALANCE = "1011";		//余额不足代码
	public static final String SERVICE_RETMSG_LESSBALANCE = "余额不足";		//余额不足描述
	
	public static final short MEMBERLOGIN_STATUS_ENABLED = 0;				//app账户状态：启用
	public static final short MEMBERLOGIN_STATUS_DISABLED = 1;				//app账户状态：禁用
	
	public static final short SMS_STATUS_ENABLED = 1;						//短信模板状态：启用
	public static final short SMS_STATUS_DISABLED = 0;						//短信模板状态：禁用
	
	public static final short SMS_SUCCESS = 1;								//短信发送成功
	public static final short SMS_FAIL = 0;									//短信发送失败
	
	public static final String NJ_TOKEN_MEMBER = "1";						//农鲸个人用户
	public static final String NJ_TOKEN_USER = "0";							//农鲸商户用户
	public static final String WEB_USER = "3";								//web端商户
	
	public static final String GATE_RESULT_SUCCESS = "1";					//接口执行成功
	public static final String GATE_RESULT_FAIL = "0";						//接口执行失败
	public static final String GATE_RESULT_JSON = "third_platform";			//获取同盾百融时数据的key
	public static final String COUNT_JSON = "countJson";					//获取鹏元数据时的key
	public static final Integer REJECT_FLAG_YES = 1;						//被拒绝标志
	public static final Integer REJECT_FLAG_NO = 0;							//未被拒绝
	public static final String TONGDUN = "1";								//同盾
	public static final String BAIRONG = "2";								//百融
	public static final String UPLOAD_BASEDIR = "/upload/images/";			//图片上传路径
	
	public static final String MEDIATYPE_IMAGE = "1";						//文件类型：图片
	public static final String MEDIATYPE_VOICE = "2";						//文件类型：语音
	public static final String MEDIATYPE_VIDEO = "3";						//文件类型：视频
	public static final String MEDIATYPE_FILE = "4"; 						//文件类型：文档
	
	//图片分类
	public static final Integer IDCARD_P = 1;								//图片类型：身份证正面
	public static final Integer IDCARD_N = 2;								//图片类型：身份证背面
	public static final Integer PERSON = 3;									//图片类型：人物类
	public static final Integer ASSETS = 4;									//图片类型：文档类
	public static final Integer CERTIFICATES = 5;							//图片类型：证件类
	public static final Integer ASSET = 6;								    //图片类型：资产类
	public static final Integer SPOUSEFRONTCARD = 7;						//图片类型：配偶身份证正面
	public static final Integer SPOUSEBACKCARD = 8;							//图片类型：配偶身份证反面
	public static final Integer OPERATE = 9;								//图片类型：经营类
	public static final Integer DELIVERYORDER = 10;							//图片类型：提货单
	public static final Integer LAND = 11;									//图片类型：土地证件类
	public static final Integer OTHER = 12;							        //图片类型：其他
	public static final Integer AFTERLOAN = 13;								//图片类型：贷后
	public static final Integer CREDIT = 14;                                //图片类型：信用报告类
	//业务表名字
	public static final String T_BUS_INTOPIECE = "t_bus_intopiece";			//进件表
	public static final String T_BUS_TB_CONTRACT = "t_bus_tb_contract";		//合同表
	public static final String T_BUS_LOAN = "t_bus_loan";					//贷款表
	
	//流程节点
	public static final String FLOW_REFUSE = "0";			//已拒件
	public static final String FLOW_COMPLETE = "1";			//进件补全中
	public static final String FLOW_FIRST_TRIAL = "2";		//进件初审
	public static final String FLOW_SECOND_TRIAL = "3";		//进件复审
	public static final String FLOW_THIRD_TRIAL = "4";		//保函出具中
	public static final String FLOW_FORCH_TRIAL = "5";		//资方终审
	public static final String FLOW_MAKE = "6";				//合同制作
	public static final String FLOW_SIGN = "7";				//合同签署
	public static final String FLOW_PENDING = "8";			//待放款
	public static final String FLOW_ALREADY = "9";			//已放款
	public static final String FLOW_REPAYMENT = "10";		//还款中
	public static final String FLOW_OVERDUE = "11";			//已逾期
	public static final String FLOW_LOST_DEBT = "12";		//已转呆/坏账
	public static final String FLOW_REPAY_COMPLETE = "13";	//还款完成
	public static final String FLOW_ABANDONING = "14";	    //放弃贷款中
	public static final String FLOW_ABANDONED = "15";	    //已放弃贷款

	
	//流向类型
	public static final Short FLOW_ORDER = 1;			//顺序
	public static final Short FLOW_BACK = 5;			//回退
	public static final Short FLOW_REJECT = 10;			//驳回
	public static final Short FLOW_EVENT = 20;			//事件
	public static final Short FLOW_ABANDON = 25;		//放弃
	
	//还款方式
	public static final Short AVERAGE_CAPITAL_INTEREST = 1;	//等额本息
	public static final Short INTEREST_THEN_CAPITAL = 2;	//先息后本
	public static final Short COMBINATION_LOAN = 3;			//组合贷款
	public static final Short HUI_NONG_TONG = 4;			//惠农通
	public static final Short QUARTERLY_REPAYMENT = 5;		//按季度还息、到期还本
	public static final Short SHOUXIN_YEARS_REPAYMENT = 6;	//一次还清本息
	public static final Short AVERAGE_CAPITAL_INTEREST_HN = 7;	//等额本息(惠农)
	public static final Short INTEREST_THEN_CAPITAL_HN = 8;	//先息后本(惠农)
	
	//决策关系
	public static final Short RELATION_OR = 1;			//或关系
	public static final Short RELATION_AND = 1;			//与关系
	
	//决策基本条件  描述名字
	public static final String RELATION_PARENT_AGE = "parent_age";		//父母年龄
	public static final String RELATION_CHILD_AGE = "child_age";		//子女年龄
	
	//家庭成员
	public static final Integer FAMILY_FATHER = 1;				//父亲：1
	public static final Integer FAMILY_MOTHER = 2;				//母亲：2
	public static final Integer FAMILY_DAILY = 3;				//配偶：3
	public static final Integer FAMILY_FIRST_CHILD = 4;			//第一子女：4
	public static final Integer FAMILY_SECOND_CHILD = 5;		//第二子女：5
	public static final Integer FAMILY_THIRD_CHILD = 6;			//第三子女：6
	public static final Integer FAMILY_OTHER = 7;				//其他家属（儿媳、女婿）：7
	
	//父母是否去世（1，是  2，否）
	public static final Integer FAMILY_DEAD = 1;				//去世：1
	public static final Integer FAMILY_NO_DEAD = 2;				//没去世：2

	//应用_数据项
	public static final Short APPITEM_BASE = 1;			//基本型：1
	public static final Short APPITEM_EXCUTION = 2;		//扩展型：2
	public static final Short APPITEM_NOT_EMPTY = 1;	//为空：1
	public static final Short APPITEM_EMPTY = 0;		//不为空：0
	//操作系统
	public static final int IOS = 1;					//ios系统：1
	public static final int ANDROID = 2;				//安卓系统：2
	
	//同盾贷后监控
	public static final String TONGDUN_POST_RISK = "success";	//是否添加贷后监控
	public static final String TONGDUN_OBTAINED = "1";		//获的风险
	public static final String TONGDUN_NO_OBTAINED = "2";	//未获的风险
	
	//消息类型
	public static final int WELCOME = 0;				//欢迎使用
	public static final int ADD_INFORMATION = 1;		//补充申请材料
	public static final int REPAYMENT = 2;				//放款完成
	public static final int REPAYMENTED = 3;			//本期还款完成
	public static final int LAST = 4;					//终审结论
	public static final int PROTECTFEE = 5;				//保费支付通知
	public static final int CONFIRM = 6;				//确认保费已支付
	public static final int RECEIVED = 7;				//提醒合作商担保人收到
	public static final int SERVICEDONE = 8;			//服务费扣费完成提醒
	public static final int LAST_SIGN_REMIND = 9;		//终审签约提醒
	public static final int MAKE_CONTRACT_REMIND = 10;	//合同制作提醒
	public static final int PENDING_SIGN_REMIND = 11;	//待放款签约提醒
	public static final int WEI_XIN_SERVICE_PAY = 12;	//微信付款提醒
	public static final int SIGN_RELATIVE_CONTRACT = 13;	//请签署相关合同
	public static final int PENDING_BANK_BACK = 14;		//上传银行回单
	public static final int REFUND_SUCCESS = 15;		//上传银行回单
	public static final int XIAO_CHENG_XU_SERVICE_PAY = 16;		//小程序支付服务费
	public static final int UGUATANTEE_REVERSE = 17;		//商户支付反担保金消息
	public static final int MGUATANTEE_REVERSE = 18;		//个人支付饭担保金
	
	public static final int SIGN_STATUS1 = 1;		//待签约
	public static final int SIGN_STATUS2 = 2;		//签约中
	public static final int SIGN_STATUS3 = 3;		//签约完成
	
	//还款明细状态
	public static final int WAIT_REPAYMENT = 1;			//待还款
	public static final int REPAYMENTING = 2;			//还款中
	public static final int REPAYMENT_FAIL = 3;			//还款失败
	public static final int COMPLETE = 4;				//还款完成
	
	
	//轮播图是否显示
	public static final int PICTRUE_SHOW = 1;			//显示
	public static final int PICTRUE_NOT_SHOW = 0;		//不显示
	//评分变量
	public static final Short SCORE_SCVAR_NUM = 1;			//数值型
	public static final Short SCORE_SCVAR_VAR = 2;			//字符型
	//评分变量的变量类型
	public static final Short SCORE_SCVAR_BASE = 1;			//1.基本型 
	public static final Short SCORE_SCVAR_GROUP = 2;		//2.组合型 
	//模板状态
	public static final Short MODEL_STATUS_OPEN = 1;			//启用
	public static final Short MODEL_STATUS_COLSE = 0;			//禁用
	//数据源状态
	public static final Short SQL_STATUS_OPEN = 1;			//启用
	public static final Short SQL_STATUS_COLSE = 0;			//禁用
	
	public static final int MONEY_OUT = 1;				//放款
	public static final int MONEY_IN = 2;				//还款
	
	public static final int LOAN_FINISH = 0;			//完成
	public static final int LOAN_NORMAL = 1;			//正常
	public static final int LOAN_OVERDUE = 2;			//逾期
	public static final int LOAN_BAD = 3;				//坏账

	//评分判例 类型
	public static final Short SCORE_SCVARCASE_PHONE = 5;		//手机号
	public static final Short SCORE_SCVARCASE_CHECKOUT = 16;	//多选
	public static final Short SCORE_SCVARCASE_TIMES = 17;	    //数量次数
	public static final String SCORE_SCVARCASE_VALUE = "value";	//评分判例不为空
	public static final String SCORE_SCVARCASE_EMPTY = "empty";	//评分判例为空
	public static final String SCORE_SCVARCASE_HIGH_LIMIT = "0";//上包括
	public static final String SCORE_SCVARCASE_HIGH_NO_LIMIT = "1";//上不包括
	
	/**
	 * 下限值  0：包括  ，1：不包括，2：多次数，3：动态数据多选，4：数据项不为空，5：数据项为空
	 */
	public static final String SCORE_SCVARCASE_LOW_LIMIT = "0";	//下包括
	public static final String SCORE_SCVARCASE_LOW_NO_LIMIT = "1";//下不包括
	public static final String SCORE_SCVARCASE_LOW_TIMES = "2";	//多次数
	public static final String SCORE_SCVARCASE_LOW_DYNAMIC = "3";//动态数据多选
	public static final String SCORE_SCVARCASE_LOW_VALUE = "4";	//数据项不为空
	public static final String SCORE_SCVARCASE_LOW_EMPTY = "5";//数据项为空
	public static final String SCORE_SCVARCASE_LOW_VARCHAR = "6";//字符型
	
	//贷款还款记录状态
	public static final Integer LOAN_DETIAL_REPAYMENT_REPAYMENTING = 1;	 //还款中
	public static final Integer LOAN_DETIAL_REPAYMENT_VERIFYING = 2;	 //复审中
	public static final Integer LOAN_DETIAL_REPAYMENT_REPAYMENT_FAIL = 3;	 //还款失败
	public static final Integer LOAN_DETIAL_REPAYMENT_COMPLETE = 4;	 //还款完成
	public static final Integer LOAN_DETIAL_REPAYMENT_RECALLED = 5;	 //已撤回
	
	//利率/服务费率类型
	public static final Short MONTH = 1; //月
	public static final Short YEAR = 2; //年
	//服务费扣费提示
	public static final String U_SERVICE_FEE_REMIND = "uservicefeeremind";//商户
	public static final String M_SERVICE_FEE_REMIND = "mservicefeeremind";//个人
	public static final String ENTRUSTED_DCONTRACT = "entrustedcontract";//个人
	public static final String U_WEI_XIN_SERVICE_PAY = "weixinservicepay";//商户微信支付消息
	public static final String U_YIXIANG_REMIND = "yixiang";//商户微信支付消息
	public static final String U_PENDING_REMIND = "pendingsignremind";//待放款签约消息\
	public static final String U_REFUND_REMIND = "refundremind";//退款成功提示
	public static final String U_GREVERSE_REMIND = "uguaranteeremind";//商户反担保金支付提示
	public static final String M_GREVERSE_REMIND = "mguaranteeremind";//个人反担保金支付提示
	
	//组织机构标识
	public static final String SERVICE_STATION = "1";			//服务站
	public static final String SELLER = "2";					//商户
	
	//商品分类
	public static final String PRODUCT_CATEGORY = "PRODUCT_CATEGORY";//商品分类
	
	//商品品牌
	public static final String PRODUCT_BRAND = "PRODUCT_BRAND";//商品品牌
	
	//商品状态， 1：上架，2：待上架，3：驳回
	public static final String PRODUCT_GROUD = "1";//上架
	public static final String PRODUCT_SHELF = "2";//待上架
	public static final String PRODUCT_REJECT = "3";//驳回
	
	//套餐状态，  1：上架，2：下架
	public static final String ORDER_GROUD = "1";//上架
	public static final String ORDER_REJECT = "2";//驳回
	
	//选择商品种类
	public static final String PRODUCT = "1";//上架
	public static final String ORDER = "2";//驳回
	
	//贷款类型
	public static final String LOAN_LAND = "1";//土地抵押贷
	public static final String LOAN_LAND_PRODUCT = "2";//土地+农资贷
	public static final String LOAN_LAND_PRODUCT_GRAIN = "3";//土地+农资贷+粮食

	//-----------------------新加状态（做区分）-------------------------
	//服务站订单状态
	public static final String SERVICESITE_WAIT_ORDER = "1";//已下单，待确认
	public static final String SERVICESITE_SURE_ORDER = "2";//已确认
	public static final String SERVICESITE_FINISH_ORDER = "3";//已收货，订单已完成
	//商户订单状态(新改的)
	public static final String MER_NEW_WAIT_OERDER = "1";//已付定金，商户待接单
	public static final String MER_NEW_MER_ORDER = "2";//商户已接单，待付尾款
	public static final String MER_NEW_DELIVER_ORDER = "3";//已付尾款，商户待确认
	public static final String MER_NEW_TOBESHIP_ORDER = "4";//商户待发货
	public static final String MER_NEW_FINISH_ORDER = "5";//商户已发货
	public static final String MER_NEW_SIGN_ORDER = "6";//用户已签收
	//-------------------------------------------------------------------

	//联合社订单状态
	public static final String ASSOCIATE_PENDING_ORDER = "1";//待下单
	public static final String ASSOCIATE_WAIT_ORDER = "2";//已付定金，商户待接单
	public static final String ASSOCIATE_MER_ORDER = "3";//商户已接单，待付尾款
	public static final String ASSOCIATE_DELIVER_ORDER = "4";//商户已发货
	public static final String ASSOCIATE_FINISH_ORDER = "5";//已完成
	
	//商户订单状态
	public static final String MER_PENDING_ORDER = "1";//待接单
	public static final String MER_WAIT_ORDER = "2";//待发货
	public static final String MER_DELIVER_ORDER = "3";//已发货
	public static final String MER_FINISH_ORDER = "4";//已完成
	
	
	public static final Integer PICTURE_REMIT = 1;//打款图片
	public static final Integer PICTURE_BOND = 2;//担保金图片
	public static final Integer PICTURE_OTHER = 10;//其他图片
}
