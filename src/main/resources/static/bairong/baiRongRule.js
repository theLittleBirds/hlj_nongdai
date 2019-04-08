
    //反欺诈评分
    var Rule_final_weight = baiRongRule.Rule_final_weight;
    //最终决策结果
    var Rule_final_decision = baiRongRule.Rule_final_decision;
    if(Rule_final_decision){
    	Rule_final_decision = Rule_final_decision=="Accept"?"通过":Rule_final_decision=="Reject"?"拒绝":"复议";
    }
    
    if(Rule_final_weight && Rule_final_decision){
    	$("#isReject").html("反欺诈评分： "+ Rule_final_weight+"，  决策结果： "+ Rule_final_decision);
    }
    if(Rule_final_weight && !Rule_final_decision){
    	$("#isReject").html("反欺诈评分： "+ Rule_final_weight+"，  决策结果： 无");
    }
    if(!Rule_final_weight && Rule_final_decision){
    	$("#isReject").html("反欺诈评分： 无，  决策结果： "+ Rule_final_decision);
    }
    	
    //非银客群特殊名单规则-银行中风险
    var Rule_name_QJS010 = baiRongRule.Rule_name_QJS010;
    //法院被执行人规则-法院失信人	
    var Rule_name_QJE010 = baiRongRule.Rule_name_QJE010;
    //法院被执行人规则-法院被执行人	
    var Rule_name_QJE020 = baiRongRule.Rule_name_QJE020;
    //非银客群多次申请月度版规则-近期在非银机构有过申请	
    var Rule_name_QAM010 = baiRongRule.Rule_name_QAM010;
    
    if(!Rule_name_QJS010 && !Rule_name_QJE010 
    		&& !Rule_name_QJE020 && !Rule_name_QAM010){
    	$("#dangerSummary").html("风险汇总： 无");
    }
    if(Rule_name_QJS010){
    	$("#dangerSummary").html("风险汇总： "+Rule_name_QJS010);
    }
    if(Rule_name_QJE010){
    	$("#dangerSummary").html("风险汇总： "+Rule_name_QJE010);
    }
    if(Rule_name_QJE020){
    	$("#dangerSummary").html("风险汇总： "+Rule_name_QJE020);
    }
    if(Rule_name_QAM010){
    	$("#dangerSummary").html("风险汇总： "+Rule_name_QAM010);
    }