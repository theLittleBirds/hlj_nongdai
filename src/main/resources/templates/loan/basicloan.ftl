<!DOCTYPE html>
<html>
<head>
    <title>借款详情</title>
    
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
   <!--  <link href="/resource/bootstrap/css/bootstrap-datetimepicker.css" rel="stylesheet"> -->
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/moment.js"></script> 
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>   
	<script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
	<script src="/admin/js/timeOut.js"></script>
	<!-- <script src="/resource/bootstrap/js/bootstrap-datetimepicker.js"></script> -->
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <form class="form-horizontal col-lg-8 m-t" id="loanForm" onsubmit="return checkForm()"
                          method="post">
                        <input type="hidden" name="id" id="id" value="${loan.id}">

                        <div class="form-group">
                             <label class="col-sm-2 control-label">主借人姓名：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="name" name="name" value="${loan.memberName}"
                                         class="form-control"/>                                 
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">主借人身份证号：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="idCard" name="idCard" value="${ip.idCard}"
                                         class="form-control"/>                                 
                             </div>
                             <label class="col-sm-2 control-label">主借人手机号：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="phone" name="phone" value="${ip.phone}"
                                         class="form-control"/>                                 
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">收款银行卡开户行：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="bank" name="bank" value="${ip.bank}"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">收款银行卡账户：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="bankCardNo" name="bankCardNo" value="${ip.bankCardNo}"
                                        class="form-control">
                             </div>
                        </div> 
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">住址：</label>
                             <div class="col-sm-8">
                                 <input type="text" id="address" name="address" value="${ip.address}"
                                         class="form-control"/>                                 
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">共同借款人：</label>
                             <div class="col-sm-8">
                             	<#list family as one>   
                					  <label style="padding-left: 0;"><input type="checkbox" name="coFamily" value="${one.value}" onclick="editPerson()">${one.name}</label>         
            					</#list>
                                                                
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">借款主体：</label>
                             <div class="col-sm-8">
                                 <input type="text" id="co_name" name="co_name" value="${loan.memberName}"
                                         class="form-control"/>                                 
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">身份证号：</label>
                             <div class="col-sm-8">
                                 <input type="text" id="co_idCard" name="co_idCard" value="${ip.idCard}"
                                         class="form-control"/>                                 
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">手机号：</label>
                             <div class="col-sm-8">
                                 <input type="text" id="co_phone" name="co_phone" value="${ip.phone}"
                                         class="form-control"/>                                 
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">出借人：</label>
                             <div class="col-sm-3">
                                    <input type="text" name="lender" value="${lender!''}" readonly
                                         class="form-control"/>                         
                             </div>
                        </div>
                        <div class="form-group">
                             <label class="col-sm-2 control-label">借款金额(元)：</label>
                             <div class="col-sm-3">
                                 <input type="number" value="${loan.capital?c}" readonly
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">借款期限(月)：</label>
                             <div class="col-sm-3">
                                 <input type="number" value="${loan.term?c}" id="term" readonly
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">月利率(%)：</label>
                             <div class="col-sm-3">
                                 <input type="number" value="${loan.rate?c}" readonly
                                         class="form-control"/>                                  
                             </div>

                             <label class="col-sm-2 control-label">逾期天利率(%)：</label>
                             <div class="col-sm-3">
                                 <input type="number" value="${loan.overdueRate?c}" readonly
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">月服务费率(%)：</label>
                             <div class="col-sm-3">
                                 <input type="number" value="${loan.serviceRate?c}" readonly
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">总服务费(元)：</label>
                             <div class="col-sm-3">
                                 <input type="number" value="${loan.serviceFee?c}" readonly
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">还款方式：</label>
                             <div class="col-sm-3">
                                   <select class="form-control" disabled="disabled">
                                   ${REPAYMENT_WAY}
                                   </select>                              
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">合同编号：</label>
                             <div class="col-sm-3">
                                  <input type="text" name="contactNo" id="contactNo"
                                         class="form-control"/>                      
                             </div>
                             <label class="col-sm-2 control-label">担保费：</label>
                             <div class="col-sm-3">
                                  <input type="text" name="guarantee" id="guarantee"
                                         class="form-control"/>                      
                             </div>
                        </div>
                        
                        <div class="form-group">                       	
                        	<div class='col-sm-5'>	
	                        	<label class="col-sm-5 control-label">借款开始日期：</label>
	                            <div class="col-sm-7 input-group date" id ='startDate'>
	                                    <input id="start_date" type="text" name="start_date" onchange="startDate()" class="form-control required" />
	                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></i></span>
	                            </div>
							</div>
							<label class="col-sm-2 control-label">还款日：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="repaymentDay" name="repaymentDay"
                                         onchange="startDate()" class="form-control"/>                                 
                             </div>
                        </div>                            
                        
                        <div class="form-group">
                             <div class='col-sm-5'>
	                            <label class="col-sm-5 control-label">借款结束日期：</label>
	                            <div class="col-sm-7 input-group date"  id ='endDate'>
	                                    <input id="end_date" type="text" name="end_date"  class="form-control required" readonly="readonly"/>
	                                    <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar" ></span></span>
	                            </div>	
                            </div>	      
                        </div>               
						
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                            <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                            </button>

                            <button class="btn btn-w-m btn-success" type="submit">
                                <i class="ace-icon fa fa-check bigger-110"></i> 提交
                            </button>                            
                            </div>
                        </div>
                    </form> 
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
$(document).ready(function(e){ 
	$("#startDate").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	/* $('#datetimepicker1').datetimepicker({ 
        format: 'YYYY-MM-DD',  
        locale: moment.locale('zh-cn')
    }).on('changeMonth',function(ev){
        alert(11);
    });
    $('#datetimepicker2').datetimepicker({  
        format: 'YYYY-MM-DD',  
        locale: moment.locale('zh-cn')  
    }); */
}); 

function startDate(){
	var start_date= $("#start_date").val();
	var repaymentDay= $("#repaymentDay").val();
	if(start_date!=""&&repaymentDay!=""){
		var startDate = new Date(start_date);
		var endDate = new Date(startDate.getFullYear(), startDate.getMonth() + parseInt('${loan.term}'), $("#repaymentDay").val(), startDate.getHours(), startDate.getMinutes(), startDate.getSeconds());
	    var y = endDate.getFullYear();
	    var m = endDate.getMonth() + 1;
	    m = m < 10 ? ('0' + m) : m;
	    var d = endDate.getDate();
	    d = d < 10 ? ('0' + d) : d;
	    $("#end_date").val(y + '-' + m + '-' + d);
	}
	
}

function checkForm(){
	var name = $("#name").val();
	if(name == ""){
		parent.window.bootbox.alert("客户姓名不能为空");
		return false;
	}
	var idCard = $("#idCard").val();
	if(idCard == ""){
		parent.window.bootbox.alert("客户身份证号不能为空");
		return false;
	}
	var phone = $("#phone").val();
	if(phone == ""){
		parent.window.bootbox.alert("客户手机号不能为空");
		return false;
	}
	var bank = $("#bank").val();
	if(bank == ""){
		parent.window.bootbox.alert("开户行不能为空");
		return false;
	}
	var bankCardNo = $("#bankCardNo").val();
	if(bankCardNo == ""){
		parent.window.bootbox.alert("银行卡账户不能为空");
		return false;
	}
	var address = $("#address").val();
	if(address == ""){
		parent.window.bootbox.alert("住址不能为空");
		return false;
	}
	var co_name = $("#co_name").val();
	if(co_name == ""){
		parent.window.bootbox.alert("借款主体不能为空");
		return false;
	}
	var contactNo = $("#contactNo").val();
	if(contactNo == ""){
		parent.window.bootbox.alert("合同编号不能为空");
		return false;
	}
	var co_idCard = $("#co_idCard").val();
	if(co_idCard == ""){
		parent.window.bootbox.alert("借款主体身份证号不能为空");
		return false;
	}
	var co_phone = $("#co_phone").val();
	if(co_phone == ""){
		parent.window.bootbox.alert("借款主体手机号不能为空");
		return false;
	}
	var lender = $("#lender").val();
	if(lender == ""){
		parent.window.bootbox.alert("出借人不能为空");
		return false;
	}
	var start_date = $("#start_date").val();
	if(start_date == ""){
		parent.window.bootbox.alert("借款开始日期不能为空");
		return false;
	}
	var end_date = $("#end_date").val();
	if(end_date == ""){
		parent.window.bootbox.alert("借款结束日期不能为空");
		return false;
	}
	var repaymentDay = $("#repaymentDay").val();
	if(repaymentDay == ""){
		parent.window.bootbox.alert("还款日不能为空");
		return false;
	}
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/loan/basicloansave",
		data:$('#loanForm').serialize(),
	    error: function(request) {	
	    },
	    success: function(data) {
	    	parent.window.bootbox.alert(data.msg);
	    	if(data.code == 200){
	    		window.location.href="/loan/contractmaking";
	    	}
	    }
	});
	return false;
}

function editPerson(){
	var name = $("#name").val();
	var idCard = $("#idCard").val();
	var phone = $("#phone").val();
	$('input[name="coFamily"]:checked').each(function(){
        var value = $(this).val().split(",");
        if(value[1] == ""){
        	parent.window.bootbox.alert(value[0]+"的身份证号为空");
        }
        if(value[2] == ""){
        	parent.window.bootbox.alert(value[0]+"的手机号为空");
        }
        name = name+" "+value[0];
        idCard = idCard+" "+value[1];
        phone = phone+" "+value[2];
    });
	$("#co_name").val(name);
	$("#co_idCard").val(idCard);
	$("#co_phone").val(phone);
}
</script>
</body>
</html>

