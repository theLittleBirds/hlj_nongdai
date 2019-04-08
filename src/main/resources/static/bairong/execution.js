var ex_bad1_name              =execution.ex_bad1_name              ;//姓名                                  
var ex_bad1_cid               =execution.ex_bad1_cid               ;//身份证号/组织机构代码                 
var ex_bad1_cidtype           =execution.ex_bad1_cidtype           ;//证件类型                              
var ex_bad1_datatime          =execution.ex_bad1_datatime          ;//数据时间                              
var ex_bad1_datatypeid        =execution.ex_bad1_datatypeid        ;//数据类型编码                          
var ex_bad1_datatype          =execution.ex_bad1_datatype          ;//数据类型                              
var ex_bad1_leader            =execution.ex_bad1_leader            ;//法定代表人/负责人                     
var ex_bad1_address           =execution.ex_bad1_address           ;//住所地                                
var ex_bad1_court             =execution.ex_bad1_court             ;//执行法院                              
var ex_bad1_time              =execution.ex_bad1_time              ;//立案时间                              
var ex_bad1_casenum           =execution.ex_bad1_casenum           ;//执行案号                              
var ex_bad1_money             =execution.ex_bad1_money             ;//执行标的                              
var ex_bad1_base              =execution.ex_bad1_base              ;//执行依据文号                          
var ex_bad1_basecompany       =execution.ex_bad1_basecompany       ;//做出执行依据单位                      
var ex_bad1_obligation        =execution.ex_bad1_obligation        ;//生效法律文书确定的义务                
var ex_bad1_lasttime          =execution.ex_bad1_lasttime          ;//生效法律文书确定的最后履行义务截止时间
var ex_bad1_performance       =execution.ex_bad1_performance       ;//被执行人的履行情况                    
var ex_bad1_concretesituation =execution.ex_bad1_concretesituation ;//失信被执行人行为具体情形              
var ex_bad1_breaktime         =execution.ex_bad1_breaktime         ;//认定失信时间                          
var ex_bad1_posttime          =execution.ex_bad1_posttime          ;//发布时间                              
var ex_bad1_performedpart     =execution.ex_bad1_performedpart     ;//已履行                                
var ex_bad1_unperformpart     =execution.ex_bad1_unperformpart     ;//未履行                                
var ex_execut1_name           =execution.ex_execut1_name           ;//姓名                                  
var ex_execut1_cid            =execution.ex_execut1_cid            ;//身份证号/组织机构代码                 
var ex_execut1_cidtype        =execution.ex_execut1_cidtype        ;//证件类型                              
var ex_execut1_datatime       =execution.ex_execut1_datatime       ;//数据时间                              
var ex_execut1_datatypeid     =execution.ex_execut1_datatypeid     ;//数据类型编码                          
var ex_execut1_datatype       =execution.ex_execut1_datatype       ;//数据类型                              
var ex_execut1_court          =execution.ex_execut1_court          ;//执行法院                              
var ex_execut1_time           =execution.ex_execut1_time           ;//立案时间                              
var ex_execut1_casenum        =execution.ex_execut1_casenum        ;//执行案号                              
var ex_execut1_money          =execution.ex_execut1_money          ;//执行标的                              
var ex_execut1_statute        =execution.ex_execut1_statute        ;//案件状态                              
var ex_execut1_basic          =execution.ex_execut1_basic          ;//执行依据                              
var ex_execut1_basiccourt     =execution.ex_execut1_basiccourt     ;//做出执行依据的机构     

/*
var ex_bad10_name             =individualCourt.ex_bad10_name             ;//姓名                                  
var ex_bad10_cid              =individualCourt.ex_bad10_cid              ;//身份证号/组织机构代码                 
var ex_bad10_cidtype          =individualCourt.ex_bad10_cidtype          ;//证件类型                              
var ex_bad10_datatime         =individualCourt.ex_bad10_datatime         ;//数据时间                              
var ex_bad10_datatypeid       =individualCourt.ex_bad10_datatypeid       ;//数据类型编码                          
var ex_bad10_datatype         =individualCourt.ex_bad10_datatype         ;//数据类型                              
var ex_bad10_leader           =individualCourt.ex_bad10_leader           ;//法定代表人/负责人                     
var ex_bad10_address          =individualCourt.ex_bad10_address          ;//住所地                                
var ex_bad10_court            =individualCourt.ex_bad10_court            ;//执行法院                              
var ex_bad10_time             =individualCourt.ex_bad10_time             ;//立案时间                              
var ex_bad10_casenum          =individualCourt.ex_bad10_casenum          ;//执行案号                              
var ex_bad10_money            =individualCourt.ex_bad10_money            ;//执行标的                              
var ex_bad10_base             =individualCourt.ex_bad10_base             ;//执行依据文号                          
var ex_bad10_basecompany      =individualCourt.ex_bad10_basecompany      ;//做出执行依据单位                      
var ex_bad10_obligation       =individualCourt.ex_bad10_obligation       ;//生效法律文书确定的义务                
var ex_bad10_lasttime         =individualCourt.ex_bad10_lasttime         ;//生效法律文书确定的最后履行义务截止时间
var ex_bad10_performance      =individualCourt.ex_bad10_performance      ;//被执行人的履行情况                    
var ex_bad10_concretesituation=individualCourt.ex_bad10_concretesituation;//失信被执行人行为具体情形              
var ex_bad10_breaktime        =individualCourt.ex_bad10_breaktime        ;//认定失信时间                          
var ex_bad10_posttime         =individualCourt.ex_bad10_posttime         ;//发布时间                              
var ex_bad10_performedpart    =individualCourt.ex_bad10_performedpart    ;//已履行                                
var ex_bad10_unperformpart    =individualCourt.ex_bad10_unperformpart    ;//未履行                                
var ex_execut10_name          =individualCourt.ex_execut10_name          ;//姓名                                  
var ex_execut10_cid           =individualCourt.ex_execut10_cid           ;//身份证号/组织机构代码                 
var ex_execut10_cidtype       =individualCourt.ex_execut10_cidtype       ;//证件类型                              
var ex_execut10_datatime      =individualCourt.ex_execut10_datatime      ;//数据时间                              
var ex_execut10_datatypeid    =individualCourt.ex_execut10_datatypeid    ;//数据类型编码                          
var ex_execut10_datatype      =individualCourt.ex_execut10_datatype      ;//数据类型                              
var ex_execut10_court         =individualCourt.ex_execut10_court         ;//执行法院                              
var ex_execut10_time          =individualCourt.ex_execut10_time          ;//立案时间                              
var ex_execut10_casenum       =individualCourt.ex_execut10_casenum       ;//执行案号                              
var ex_execut10_money         =individualCourt.ex_execut10_money         ;//执行标的                              
var ex_execut10_statute       =individualCourt.ex_execut10_statute       ;//案件状态                              
var ex_execut10_basic         =individualCourt.ex_execut10_basic         ;//执行依据                              
var ex_execut10_basiccourt    =individualCourt.ex_execut10_basiccourt    ;//做出执行依据的机构                    
*/

//失信
if(ex_bad1_address
	||ex_bad1_base
	||ex_bad1_basecompany
	||ex_bad1_breaktime
	||ex_bad1_casenum
	||ex_bad1_cidtype
	||ex_bad1_cid
	||ex_bad1_concretesituation
	||ex_bad1_court
	||ex_bad1_datatime
	||ex_bad1_lasttime
	||ex_bad1_leader
	||ex_bad1_money
	||ex_bad1_name
	||ex_bad1_obligation
	||ex_bad1_performance
	||ex_bad1_unperformpart
	||ex_bad1_posttime
	){
	if(ex_bad1_address          ){
		ex_bad1_address          = execution.ex_bad1_address           ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >住所地："+ex_bad1_address+"</label>");
	}
	if(ex_bad1_base             ){
		ex_bad1_base             = execution.ex_bad1_base              ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >执行依据文号："+ex_bad1_base+"</label>");
	}
	if(ex_bad1_basecompany      ){
		ex_bad1_basecompany      = execution.ex_bad1_basecompany       ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >做出执行依据单位："+ex_bad1_basecompany+"</label>");
	}
	if(ex_bad1_breaktime        ){
		ex_bad1_breaktime        = execution.ex_bad1_breaktime         ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >认定失信时间："+ex_bad1_breaktime+"</label>");
	}
	if(ex_bad1_casenum          ){
		ex_bad1_casenum          = execution.ex_bad1_casenum           ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >执行案号："+ex_bad1_casenum+"</label>");
	}

	if(ex_bad1_cidtype == '1'   ){
		if(ex_bad1_cid              ){
			ex_bad1_cid              = execution.ex_bad1_cid               ;
			$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >身份证号："+ex_bad1_cid+"</label>");
		}
	}
	if(ex_bad1_cidtype == '11'  ){
		if(ex_bad1_cid              ){
			ex_bad1_cid              = execution.ex_bad1_cid               ;
			$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >组织机构代码："+ex_bad1_cid+"</label>");
		}
	}
	if(ex_bad1_concretesituation){
		ex_bad1_concretesituation= execution.ex_bad1_concretesituation ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >失信被执行人行为具体情形："+ex_bad1_concretesituation+"</label>");
	}
	if(ex_bad1_court            ){
		ex_bad1_court            = execution.ex_bad1_court             ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >执行法院："+ex_bad1_court+"</label>");
	}
	if(ex_bad1_datatime         ){
		ex_bad1_datatime         = execution.ex_bad1_datatime          ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >失信被执行人信息时间："+ex_bad1_datatime+"</label>");
	}
	if(ex_bad1_lasttime         ){
		ex_bad1_lasttime         = execution.ex_bad1_lasttime          ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >生效法律文书确定的最后履行义务截止时间："+ex_bad1_lasttime+"</label>");
	}
	if(ex_bad1_leader           ){
		ex_bad1_leader           = execution.ex_bad1_leader            ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >法定代表人/负责人："+ex_bad1_leader+"</label>");
	}
	if(ex_bad1_money            ){
		ex_bad1_money            = execution.ex_bad1_money             ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >执行金额："+ex_bad1_money+"</label>");
	}
	if(ex_bad1_name             ){
		ex_bad1_name             = execution.ex_bad1_name              ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >姓名："+ex_bad1_name+"</label>");
	}
	if(ex_bad1_obligation       ){
		ex_bad1_obligation       = execution.ex_bad1_obligation        ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >生效法律文书确定的义务："+ex_bad1_obligation+"</label>");
	}
	if(ex_bad1_performance      ){
		ex_bad1_performance      = execution.ex_bad1_performance       ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >被执行人的履行情况："+ex_bad1_performance+"</label>");
	}
	if(ex_bad1_unperformpart    ){
		ex_bad1_unperformpart      = execution.ex_bad1_unperformpart       ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >被执行人的履行情况："+ex_bad1_unperformpart+"</label>");
	}
	if(ex_bad1_posttime         ){
		ex_bad1_posttime         = execution.ex_bad1_posttime          ;
		$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >发布时间："+ex_bad1_posttime+"</label>");
	}
}else{
	$("#unFaithCourt").append("<label class=\"col-sm-4 control-label\" >无风险</label>");;
}



//被执行人
if(ex_execut1_basic
	||ex_execut1_basiccourt
	||ex_execut1_casenum
	||ex_execut1_cidtype
	||ex_execut1_cid
	||ex_execut1_court
	||ex_execut1_datatime
	||ex_execut1_money
	||ex_execut1_name
	||ex_execut1_statute
	||ex_execut1_time
){
	if(ex_execut1_basic         ){
		ex_execut1_basic         = execution.ex_execut1_basic          ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >执行依据："+ex_execut1_basic+"</label>");
	}
	if(ex_execut1_basiccourt    ){
		ex_execut1_basiccourt    = execution.ex_execut1_basiccourt     ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >做出执行依据的机构："+ex_execut1_basiccourt+"</label>");
	}
	if(ex_execut1_casenum       ){
		ex_execut1_casenum       = execution.ex_execut1_casenum        ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >执行案号："+ex_execut1_casenum+"</label>");
	}
	if(ex_execut1_cidtype  == '1'  ){
		if(ex_execut1_cid           ){
			ex_execut1_cid           = execution.ex_execut1_cid            ;
			$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >身份证号："+ex_execut1_cid+"</label>");
		}
	}
	if(ex_execut1_cidtype  == '11' ){
		if(ex_execut1_cid           ){
			ex_execut1_cid           = execution.ex_execut1_cid            ;
			$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >组织机构代码："+ex_execut1_cid+"</label>");
		}
	}
	if(ex_execut1_court         ){
		ex_execut1_court         = execution.ex_execut1_court          ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >执行法院："+ex_execut1_court+"</label>");
	}
	if(ex_execut1_datatime      ){
		ex_execut1_datatime      = execution.ex_execut1_datatime       ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >最高法执行时间："+ex_execut1_datatime+"</label>");
	}
	if(ex_execut1_money         ){
		ex_execut1_money         = execution.ex_execut1_money          ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >执行金额："+ex_execut1_money+"</label>");
	}
	if(ex_execut1_name          ){
		ex_execut1_name          = execution.ex_execut1_name           ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >姓名："+ex_execut1_name+"</label>");
	}
	if(ex_execut1_statute       ){
		ex_execut1_statute       = execution.ex_execut1_statute        ;
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >案件状态："+ex_execut1_statute+"</label>");
	}
	if(ex_execut1_time          ){
		ex_execut1_time          = execution.ex_execut1_time           ; 
		$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >立案时间："+ex_execut1_time+"</label>");
	}	
}else{
	$("#individualCourt").append("<label class=\"col-sm-4 control-label\" >无风险</label>");
}



