<!DOCTYPE html>
<html>
<head>
    <title>保函出具</title>
    <link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resource/bootstrap/css/datapicker/datepicker3.css">
    <script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
    <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
    <script src="/admin/js/timeOut.js"></script>
    <script src="/resource/bootbox/bootbox.js"></script>
    <script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <form class="form-horizontal col-lg-8 m-t" id="guaranteeForm" onsubmit="return checkForm()"
                          method="post">
                        <input type="hidden" name="id" id="id" value="${id}">	
                        <input type="hidden" name="serviceValue" id="serviceValue" value="${serviceFeeWay}">	
                        <!-- 农资或土地抵押标记 -->
                        <input type="hidden" name="applyType" id="applyType" value="${applyType}">
                        
                        <!-- <div class="form-group" style="position:relative;">
								<div class="col-sm-4" style="height:380px;" id="score1"></div>
								<div class="col-sm-4" style="height:380px;" id="grade1"></div>
								<div class="col-sm-4" style="height:380px;" id="money1"></div>
								<div class="col-sm-12" style="position: absolute;left:0;top:320px;border:1px solide #ddd;text-align:center;color:red;font-size:20px;">
								  <div id="span_capital" style="display:inline-block">
									<label>授信额度：</label><span id="span_capital"></span>
								  </div>
							  	<div style="display:inline-block;">
										<button class="btn btn-info" type="button" onclick="getscore()" style="margin-left: 20px;" id="btn_score">刷新</button>
								  </div>
								</div>	
								<div class="col-sm-12" style="position: absolute;left:0;top:360px;border:1px solide #ddd;text-align:center;color:red;font-size:20px;">
								  <div id="span_landCapital" style="display:inline-block">
									土地授信额度：${creditCapital}元
								  </div>
								</div>							
							</div> -->
							
							<div class="form-group" style="position:relative;">
								<div class="col-sm-4" style="height:340px;" id="score1"></div>
								<div class="col-sm-4" style="height:340px;" id="grade1"></div>
								<div class="col-sm-4" style="height:340px;" id="money1"></div>
								<div class="col-sm-12" style="position: absolute;left:0;top:300px;border:1px solide #ddd;text-align:center;color:red;font-size:20px;">
								  <div id="span_capital" style="display:inline-block">
									<label>授信额度：</label><span id="span_capital"></span>
								  </div>
							  	<div style="display:inline-block;">
										<button class="btn btn-info" type="button" onclick="getscore()" style="margin-left: 20px;" id="btn_score">刷新</button>
								  </div>
								</div>							
							</div>
                                 
                        <div class="form-group">
                             <label class="col-sm-2 control-label">借款主体：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${name}" readonly="readonly"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">身份证号：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${idCard}" readonly="readonly"
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">手机号：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${phone}" readonly="readonly"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">地址：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${address}" readonly="readonly"
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">授信额度(元)：</label>
                             <div class="col-sm-3">
                                 <input type="number" id="capital" name="capital"  onchange="javascript:calculation('1');calculatePrice(this);" value="${capital!''}"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">借款期限(月)：</label>
                             <div class="col-sm-3">
                                 <input type="number" id="term" name="term"  onchange="calculation('2');" value="${term!''}"
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">金融产品：</label>
                             <div class="col-sm-3">
                                 <select  id="product" name="product" class="form-control"  onchange="calculation('3');">
                                 	<option value="">--请选择--</option>
                                 	<#list productList as one>   
                					  <option value="${one.productId}">${one.name}</option>         
            						</#list>                                         
                                  </select>
                                  <span id="detail"></span>
                             </div>
                             
                          	<label class="col-sm-2 control-label" hidden="hidden">首期还款额(元)：</label>
                             <div class="col-sm-3">
                                 <input type="text" id="firstRepayment"  style="display:none"
                                        class="form-control">
                             </div>
                        </div>  
                        
                        <#if applyType==2 || applyType==3>
	                        <div class="form-group" >
		                    	<label class="col-sm-2 control-label">套餐商品：</label>
			                    <div class="col-sm-10">
									<table id="dynamicTable" >
										<thead>
											<tr>
												<td height="30" width="350" bgcolor="#CCCCCC">商品名称</td>
												<td height="30" width="100" bgcolor="#CCCCCC">商品单价（元）</td>
												<td height="30" width="100" bgcolor="#CCCCCC">数量（件）</td>
												<td height="30" width="200" bgcolor="#CCCCCC">预计使用时间</td>
												<td></td>
											</tr>
										</thead>
										<tbody>
											<#if productmel?? && (productmel?size > 0)>	
											<!-- 遍历已选商品 -->
												<#list productmel as productf>
													<#assign price=-1>
													<tr>
														<td height="30" width="350">
															<select class="form-control" name="productId" onchange="calculatePrice(this);">
							                                 	<option value="">--请选择--</option>
							                                 	<#list productListp as product>
							                                 		<#if product.id==productf.productId!''>
							                                 			<#assign price=product.price>
									                        			<option value="${product.id}" productType="${product.type}" price="${product.price}" selected="selected">${product.productName}</option>
									                        		<#else>
									                         			<option productType="${product.type}" price="${product.price}" value="${product.id}">${product.productName}</option>
									                         		</#if>
							                                 	</#list>                                       
							                                </select>
														</td>
														<td height="30" width="100">
														<#if price!=-1>
															<input type="number" name="price" class="form-control" readonly="readonly" value="${price}"/>
														<#else>
						                         			<input type="number" name="price" class="form-control" readonly="readonly" value=""/>
						                         		</#if>
														</td>
														<td height="30" width="100">
														<#if price!=-1>
															<input type="text" name="productNum" class="form-control" onchange="calculatePrice(this)" value="${productf.productNum}"/>
														<#else>
						                         			<input type="text" name="productNum" class="form-control" onchange="calculatePrice(this)" value=""/>
						                         		</#if>
														</td>
														<td height="30" width="200">
															<div class="input-group date" id ='receiveDate'>
																<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></i></span>
																<#if price!=-1>
																	<input id="receive_date" type="text" name="receive_date" value="${productf.productTime}" class="form-control" />
																<#else>
								                         			<input id="receive_date" type="text" name="receive_date" value="" class="form-control" />
								                         		</#if>
															</div>
														</td>
														<td>
															<input type="button" id="Button3" class="btn btn-primary"  onClick="prodetail(this)" value="详情">
															<input type="button" id="Button2" class="btn btn-danger"  onClick="deltr(this)" value="删行">
														</td>
													</tr>
												</#list>					
											<#else>
												<tr>
													<td height="30" width="350">
														<select class="form-control" name="productId" onchange="calculatePrice(this);">
						                                 	<option value="">--请选择--</option>
						                                 	<#list productListp as product>
						                                 		<option productType="${product.type}" price="${product.price}" value="${product.id}">${product.productName}</option>
						                                 	</#list>                                       
						                                </select>
													</td>
													<td height="30" width="100">
														<input type="number" name="price" class="form-control" readonly="readonly" value=""/>
													</td>
													<td height="30" width="100">
														<input type="text" name="productNum" onchange="calculatePrice(this)" class="form-control" />
													</td>
													<td height="30" width="200">
														<div class="input-group date" id ='receiveDate'>
															<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></i></span>
															<input id="receive_date" type="text" name="receive_date" class="form-control" />
														</div>
													</td>
													<td>
														<input type="button" id="Button3" class="btn btn-primary"  onClick="prodetail(this)" value="详情">
														<input type="button" id="Button2" class="btn btn-danger"  onClick="deltr(this)" value="删行">
													</td>
												</tr>
											</#if>
										</tbody>
									</table>
									<input id="btn_addtr" onclick="addTr()" type="button" class="btn btn-primary" value="增行">
								</div>
		                    </div>
		                    <div class="form-group">
	                             <label class="col-sm-2 control-label">到手农资：</label>
	                             <div class="col-sm-3">
	                             	<div class="input-group">
	                             		<input type="text" readonly="readonly" id="recieveNongZi" value="${recieveNongZi}"  class="form-control "/>
	                             		<div class="input-group-addon">元</div>
	                             	</div>
	                             </div>
	                             <label class="col-sm-2 control-label">到手现金：</label>
	                             <div class="col-sm-3">
	                             	<div class="input-group"> 
	                             		<input type="text" readonly="readonly" id="recieveCash" value="${recieveCash}"  class="form-control "/>
	                             		<div class="input-group-addon">元</div>
	                             	</div>
	                             </div>
	                        </div>
	                    </#if>
                        
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">工作站意见：</label>
                             <div class="col-sm-8">
                                 <textarea id="trail_opinion"  name="trail_opinion"
                                         class="form-control" disabled="disabled">${trail_opinion}</textarea>                              
                             </div>
                        </div>                      
                        
                        <div class="form-group">
                       	 	<label class="col-sm-2 control-label">服务费收取方式：</label>
                            <div class="col-sm-3">
                                <select  id="serviceFeeWay" name="serviceFeeWay" class="form-control" onchange="changeWay()">
                                	<!-- <option value="">--请选择--</option>
               					  	<option value="1">站长代付</option>         
               					  	<option value="2">微信支付</option>  -->        
               					  	<option value="4">银行代收</option>         
                                 </select>
                            </div>
                            <div id="labelShow" class="col-sm-6" style="display:none">
	                            <label  class="col-sm-4 control-label" >首期服务费(元)：</label>
	                             <div class="col-sm-6">
	                                 <input type="text" id="serviceFee"  placeholder="请确保正确金额" value="${serviceFee}"
	                                        class="form-control">
	                             </div>
                            </div>
                          	<input type="hidden" id="serviceFeeFirst"  name="serviceFee" value="${serviceFee}"
	                                        class="form-control">
                        </div>
                        
                        <#list opinionList as opinion>
                        	<div class="form-group">
                        		<label class="col-sm-2 control-label">
                        		<#if opinion.node == 2>  
								   		 总站意见 
								<#elseif opinion.node == 3>  
								    	公司审核意见
								<#elseif opinion.node == 4>  
								    	公司审批意见
								<#elseif opinion.node == 5> 
										终审意见
								<#else>  
								    ${opinion.node}
								</#if> 
                        		</label>
                        		<div class="col-sm-8">
                                 <textarea class="form-control" readonly="readonly">授信额度：${opinion.capital}元；借款期限：${opinion.term}个月；金融产品：${opinion.productId}；审核人：${opinion.examineName}；审核时间：${opinion.examineDate?string("yyyy-MM-dd HH:mm:ss")}；意见：${opinion.examineOpinion}</textarea>                              
                             </div>
                        	</div>
                        </#list>                                                                                       
						
						<#if showButton == 1> 						
						<div class="form-group">
                             <label class="col-sm-2 control-label">公司审批意见：</label>
                             <div class="col-sm-8">
                                 <textarea id="opinion" name="opinion"
                                         class="form-control"></textarea>                              
                             </div>
                        </div>
                        </#if>
                        
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                            <button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
                                <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                            </button>
							<#if showButton == 1> 
							
                            <button class="btn btn-w-m btn-success" type="submit">
                                <i class="ace-icon fa fa-check bigger-110"></i> 提交
                            </button> 
                            
                            </#if>                           
                            </div>
                        </div>                    
                    </form>   
                    <input type="hidden" id="hide_capital" value="${capital!''}">  
                    <input type="hidden" id="hide_product" value="${product!''}">                   
                </div>
            </div>
        </div>
    </div>
</div>
<table id="tab11" style="display: none">
	<tbody>
		<tr>
			<td height="30" width="350">
				<select class="form-control" name="productId" onchange="calculatePrice(this);">
                  	<option value="">--请选择--</option>
                  	<#list productListp as product>
                  		<option productType="${product.type}" price="${product.price}" value="${product.id}">${product.productName}</option>
                  	</#list>                                       
                 </select>
			</td>
			<td height="30" width="100">
				<input type="number" name="price" class="form-control" readonly="readonly" value=""/>
			</td>
			<td height="30" width="100">
				<input type="text" name="productNum" onchange="calculatePrice(this)" class="form-control" />
			</td>
			<td height="30" width="200">
				<div class="input-group date" id ='receiveDate'>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></i></span>
					<input id="receive_date" type="text" name="receive_date" class="form-control" />
				</div>
			</td>
			<td>
				<input type="button" id="Button3" class="btn btn-primary"  onClick="prodetail(this)" value="详情">
				<input type="button" id="Button2" class="btn btn-danger"  onClick="deltr(this)" value="删行">
			</td>
		</tr>
	</tbody>
</table>
<script src="/resource/echarts/echarts.min.js"></script>
<script type="text/javascript">
$(document).ready(function(e){
	$(".date").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	var hide_product = $("#hide_product").val();
	var serviceValue = $("#serviceValue").val();
	if(serviceValue==2||serviceValue==1||serviceValue==3){
		$("#serviceFeeWay").val(serviceValue);
		$("#serviceFeeWay").prop('disabled',true);
		$("#serviceFee").prop('disabled',true);
		$("#labelShow").show();
	}else if(serviceValue==4){
		$("#serviceFeeWay").val(serviceValue);
		$("#serviceFeeWay").prop('disabled',true);
	}
	var serviceFee = $("#serviceFee").val();
	if(serviceFee != ''){
		$("#serviceFee").prop('disabled',true);
	}
	
	if(hide_product != ''){
		$("#product").val(hide_product);
		//计算还款
		calculation("3");
	}
	
	var id = $("#id").val();
	$.ajax({
		type: "POST",
		url: "/examine/historyscore",
		data:{"id":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		var history = data.history;
	    		if(history.length == 0){
	    			getscore();
	    			return;
	    		}
	    		        score1 = history[0].scoreTotal
						grade1 = history[1].scoreTotal
						money1 = history[2].scoreTotal
						var myChart1 = echarts.init(document.getElementById('score1'));
						var myChart2 = echarts.init(document.getElementById('grade1'));
						var myChart3 = echarts.init(document.getElementById('money1'));
						var option1 = {
							title: {
								text: history[0].className + '级',
								left: "center",
								bottom: "14%",
								textStyle: {									
									color: '#e4d536',									
									fontStyle: 'normal',									
									fontWeight: 'bold',									
									fontFamily: 'sans-serif',
									fontSize: 28
								}
							},
							tooltip: {
								formatter: "{a} <br/>{b} : {c}分"
							},

							series: [{
								name: '业务指标',
								type: 'gauge',
								min: -100,
								max: 100,
								axisLine: { 
									lineStyle: { 
										width: 10
									}
								},
								splitLine: { 
									length: 10,
									lineStyle: { 
										color: 'auto'
									}
								},
								detail: {
									formatter: '{value}分'
								},
								data: [{
									value: score1,
									name: '违约成本'
								}]
							}]
						};
						var option2 = {
								title: {
								text: history[1].className + '级',
								left: "center",
								bottom: "14%",
								textStyle: {
									
									color: '#e4d536',									
									fontStyle: 'normal',									
									fontWeight: 'bold',									
									fontFamily: 'sans-serif',
									fontSize: 28
								}
							},
							tooltip: {
								formatter: "{a} <br/>{b} : {c}分"
							},

							series: [{
								name: '业务指标',
								type: 'gauge',
								min: -100,
								max: 100,
								axisLine: { 
									lineStyle: { 
										width: 10
									}
								},
								detail: {
									formatter: '{value}分'
								},
								data: [{
									value: grade1,
									name: '还款意愿'
								}]
							}]
						};
						var option3 = {
								title: {
								text: history[2].className + '级',
								left: "center",
								bottom: "14%",
								textStyle: {								
									color: '#e4d536',									
									fontStyle: 'normal',									
									fontWeight: 'bold',									
									fontFamily: 'sans-serif',
									fontSize: 28
								}
							},
							tooltip: {
								formatter: "{a} <br/>{b} : {c}分"
							},

							series: [{
								name: '业务指标',
								type: 'gauge',
								min: -100,
								max: 100,
								axisLine: { 
									lineStyle: { 
										width: 10,
									}
								},
								detail: {
									formatter: '{value}分'
								},
								data: [{
									value: money1,
									name: '还款能力'
								}]
							}]
						};


						myChart2.setOption(option2);
						myChart3.setOption(option3);
						myChart1.setOption(option1);
						var money = data.money;
						$("#span_capital").html("授信额度：" + money + "元");
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});
});

function addTr(){
	var length = $("#dynamicTable tbody tr").length;
	var show_count = 20;   //要显示的条数
	//alert(length);
	if (length < show_count)    //点击时候，如果当前的数字小于递增结束的条件
	{
		$("#tab11 tbody tr").clone().appendTo("#dynamicTable tbody");   //在表格后面添加一行
		//changeIndex();//更新行号
	}
	$(".date").datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
	
}

function calculatePrice(_this){
	var select=  $(_this).parent().find("select");
	if(select!=undefined&&select.attr("name")== "productId" ){
		var price = select.find("option:selected").attr("price");
		$(_this).parent().parent().find("input[name='price']").val(price);
	}
	
	var appType = $("#applyType").val();
	if(appType==2||appType==3){
		var capital= $("#capital").val();
		if(capital!=''){
			var productArray = [];
			var flag = true;
			$("#dynamicTable tbody tr").each(function () {
				//判断不过不计算价格
				if(!flag){
					return;
				}
				
				var productId = $(this).find("select").val();
				if(!productId){
					flag = false;
					return;
				}
				//判断不过不计算价格
				if(!flag){
					return;
				}
				
				var productNum = $(this).find("input[name='productNum']").val();
				if(!productNum){
					flag = false;
					return;
				}
				if(productNum*1<1){
					parent.window.bootbox.alert("不能少于1件");
					return;
				}
				//判断不过不计算价格
				if(!flag){
					return;
				}
			
				var arrayObj = {"productId":productId, "productNum":productNum};
				productArray.push(arrayObj);
			});
			//判断不过不计算价格
			if(!flag){
				return;
			}
			$.ajax({
			    url: '/intopiece/caculateNongZi',
			    type: 'POST', 
			    async: true,
			    data: {"capital":capital,"list":JSON.stringify(productArray)},
			    dataType: 'json', 
			    success:function(data){
			    	if(data.code==200){
			    		//计算结算价格 
				    	$("#recieveNongZi").val(data.recieveNongZi);
				    	$("#recieveCash").val(data.recieveCash);
			    	}else{
			    		parent.window.bootbox.alert(data.msg);
			    	}
			    }
			});
		}
	}
}

//计算还款
function calculation(key){
	var product = $("#product").val();
	if(product == ''){
		$("#detail").html("");
		return ;
	}
	if(key == "3"){
		$.ajax({
			type: "POST",
			url: "/product/productdetail",
			data:{"productId":product},
		    error: function(request) {			    	
		    },
		    success: function(data) {
		    	$("#detail").html(data);
		    }
		});
	}
	var capital = $("#capital").val();
	if(capital == ''){
		return ;
	}
	if(key == "1"){
		var hide_capital = $("#hide_capital").val();
		if(hide_capital !=''){
			if(parseInt(hide_capital) < parseInt(capital)){
				$("#capital").val(hide_capital);
				parent.window.bootbox.alert("授信额度不能大于上一步审核额度");
				return ;
			}
		}
	}
	var term = $("#term").val();
	if(term == ''){
		return ;
	}
	$.ajax({
		type: "POST",
		url: "/examine/calculationloan",
		data:{"capital":capital,"term":term,"product":product},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	if(data.code == 200){
	    		$("#firstRepayment").val(data.firstRepayment);
	    		$("#serviceFeeFirst").val(data.serviceFeeFirst);
	    		$("#serviceFee").val(data.serviceFeeFirst);
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});
	
}

function changeWay(){
	var serviceFeeWay = $("#serviceFeeWay").val();
	if(serviceFeeWay==2||serviceFeeWay==1){
		$("#labelShow").show();
	}else{
		$("#labelShow").hide();
	}
}

function prodetail(opp) {
	var id = $(opp).parent().parent().find("select[name='productId']").find("option:selected").val();
	//1为商品，2为套餐
	var productType = $(opp).parent().parent().find("select[name='productId']").find("option:selected").attr("productType");
	if(id!=''&&id!=undefined){
		$.ajax({
		    url: '/intopiece/findProDetail',
		    type: 'POST', 
		    async: true,
		    data: {"id":id,"productType":productType},
		    dataType: 'json', 
		    success:function(data){
		    	if(data.code==200){
		    		parent.window.bootbox.alert(data.msg);
		    	}else{
		    		parent.window.bootbox.alert(data.msg);
		    	}
		    }
		});
	}else{
		alert("请先选择商品");
	}
}

function checkForm(){
	var capital = $("#capital").val();
	var term = $("#term").val();
	var product = $("#product").val();
	var intoPieceId = $("#id").val();
	var pcId = $("#pcId").val();
	var appType = $("#applyType").val();
	var opinion = $("#opinion").val();
	var serviceFee = $("#serviceFee").val();
	var serviceFeeWay = $("#serviceFeeWay").val();
	if(capital == ""){
		parent.window.bootbox.alert("授信额度必填");
		return false;
	}
	if(term == ""){
		parent.window.bootbox.alert("借款期限必填");
		return false;
	}
	if(product == ""){
		parent.window.bootbox.alert("金融产品必填");
		return false;
	}		
	if(opinion == ""){
		parent.window.bootbox.alert("审核意见必填");
		return false;
	}
	if(serviceFeeWay == ""){
		parent.window.bootbox.alert("扣款方式必选");
		return false;
	}
	if(serviceFeeWay==1&&serviceFee == ""){
		parent.window.bootbox.alert("服务费必填");
		return false;
	}
	if(appType==2||appType==3){
		var productArray = [];
		var flag = true;
		$("#dynamicTable tbody tr").each(function () {
			
			var productId = $(this).find("select").val();
			if(!productId){
				parent.window.bootbox.alert("请选择商品名称");
				flag = false;
				return false;
			}
			//判断不过不计算价格
			if(!flag){
				return false;
			}
			
			var productNum = $(this).find("input[name='productNum']").val();
			if(!productNum){
				flag = false;
				parent.window.bootbox.alert("请选择商品数量");
				return false;
			}
			if(productNum*1<1){
				parent.window.bootbox.alert("不能少于1件");
				return;
			}
			//判断不过不计算价格
			if(!flag){
				return false;
			}
			var receiveDate = $(this).find("input[name='receive_date']").val();
			var productType = $(this).find("select").find("option:selected").attr("productType");
			var arrayObj = {"productId":productId, "productNum":productNum , "productTime":receiveDate , "productType":productType};
			productArray.push(arrayObj);
		});
		if(!flag){
			return false;
		}
		if(productArray.length==0){
			parent.window.bootbox.alert("请选择商品");
			return false;
		}
		
		var recieveNongZi= $("#recieveNongZi").val();
		var recieveCash=$("#recieveCash").val();
		var formData =  new FormData();
		
		formData.append('capital',capital);
		formData.append('id',intoPieceId);
		formData.append('product',product);
		formData.append('type',appType);
		formData.append('recieveNongZi',recieveNongZi);
		formData.append('term',term);
		formData.append('serviceFeeWay',serviceFeeWay);
		formData.append('serviceFee',serviceFee);
		formData.append('opinion',opinion);
		formData.append('recieveCash',recieveCash);
		formData.append('productListInfo', JSON.stringify(productArray) );
		
		if(serviceFeeWay!=4 && serviceFee != "" && serviceFee != 0 && $("#serviceFeeWay").prop("disabled")==false){
			
			parent.window.bootbox.confirm({  
				buttons: {  
		        cancel: {  
		        	label: '否'
		        },
		        confirm: {
					label: '是'
		        }
		        },  
		        message: '确认服务费金额为:'+serviceFee+'元吗?如确定将会向客户发起扣款',  
		        callback: function(result) {  
		            if(result)
		            {  
		            	parent.window.bootbox.dialog({
		            		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>保存审核信息中</span>",
		            		closeButton: false,
		            		className: "bootboxModal",
		            	});
		            	
		            	$.ajax({
		        			type: 'POST', 
		        		    url: '/examine/guaranteeformsave',
		        		    data:formData ,
		        		    processData : false, 
		        		    contentType : false,
		        		    dataType: 'json', 
		        		    error: function(data) {
		        		    	parent.removeBootBox("bootboxModal");
		            	    	parent.window.bootbox.alert("系统出错了");
		        		    },
		        		    success:function(data){
		        		    	parent.removeBootBox("bootboxModal");
		            	    	parent.window.bootbox.alert(data.msg);
		            	    	if(data.code==200){
		            	    		window.history.go(-1);
		            	    	}
		        		    }
		        		});
		            }
		            else
		            {  
		            }  
		        }
		    });
		}else{
			parent.window.bootbox.dialog({
        		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>保存审核信息中</span>",
        		closeButton: false,
        		className: "bootboxModal",
        	});
        	
        	$.ajax({
    			type: 'POST', 
    		    url: '/examine/guaranteeformsave',
    		    data:formData ,
    		    processData : false, 
    		    contentType : false,
    		    dataType: 'json', 
    		    error: function(data) {
    		    	parent.removeBootBox("bootboxModal");
        	    	parent.window.bootbox.alert("系统出错了");
    		    },
    		    success:function(data){
    		    	parent.removeBootBox("bootboxModal");
        	    	parent.window.bootbox.alert(data.msg);
        	    	if(data.code==200){
        	    		window.history.go(-1);
        	    	}
    		    }
    		}); 
		}
//		{"capital" : capital , "id" : intoPieceId , "product" : jinrongPro , "term" : term , "type" : appType ,"recieveNongZi" : recieveNongZi , "recieveCash" : recieveCash , "productListInfo" : JSON.stringify(productArray) }
		
		return false;
	}else{
		if(serviceFeeWay!=4 && serviceFee != "" && serviceFee != 0 && $("#serviceFeeWay").prop("disabled")==false){
			parent.window.bootbox.confirm({  
				buttons: {  
		        cancel: {  
		        	label: '否'
		        },
		        confirm: {
					label: '是'
		        }
		        },  
		        message: '确认服务费金额为:'+serviceFee+'元吗?如确定将会向客户发起扣款',  
		        callback: function(result) {  
		            if(result)
		            {  
		            	parent.window.bootbox.dialog({
		            		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>保存审核信息中</span>",
		            		closeButton: false,
		            		className: "bootboxModal",
		            	});
		            	
		            	$.ajax({
		            		url: "/examine/guaranteeformsave", 
		                    type: "POST", 
		                    data: $('#guaranteeForm').serialize(),
		            	    error: function(request) {
		            	    	parent.removeBootBox("bootboxModal");
		            	    	parent.window.bootbox.alert("系统出错了");
		            	    },
		            	    success: function(data) {
		            	    	parent.removeBootBox("bootboxModal");
		            	    	parent.window.bootbox.alert(data.msg);
		            	    	if(data.code==200){
		            	    		window.history.go(-1);
		            	    	}
		            	    }
		            	});
		            }
		            else
		            {  
		            }  
		        }
		    });
		}else{
			parent.window.bootbox.dialog({
        		message: "<img alt='loading' style='margin-top:-15px;' src='/admin/image/loading.gif'><span style='font-size:30px;'>保存审核信息中</span>",
        		closeButton: false,
        		className: "bootboxModal",
        	});
			$.ajax({
	    		url: "/examine/guaranteeformsave", 
	            type: "POST", 
	            data: $('#guaranteeForm').serialize(),
	    	    error: function(request) {
	    	    	parent.removeBootBox("bootboxModal");
	    	        //alert("Connection error");
	    	    },
	    	    success: function(data) {
	    	    	parent.removeBootBox("bootboxModal");
	    	    	parent.window.bootbox.alert(data.msg);
	    	    	if(data.code==200){
	    	    		window.history.go(-1);
	    	    	}
	    	    }
	    	}); 
		}
		return false;
	}
	return false;
}

function getscore(){
	$("#btn_score").attr("disabled", "disabled");
	var id = $("#id").val();
	$.ajax({
		type: "POST",
		url: "/scoreMgr/scoreCalculateMgr",
		data:{"intoPieceId":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
	    	$("#btn_score").removeAttr("disabled");
	    	if(data.code == 200){
		        var scoreCalculate = data.scoreCalculate;
				score1 = scoreCalculate[0].scoreTotal
				grade1 = scoreCalculate[1].scoreTotal
				money1 = scoreCalculate[2].scoreTotal
                var myChart1 = echarts.init(document.getElementById('score1'));
				var myChart2 = echarts.init(document.getElementById('grade1'));
				var myChart3 = echarts.init(document.getElementById('money1'));
				var option1 = {
					title: {
						text: scoreCalculate[0].scoreClass.name + '级',
						left: "center",
						bottom: "14%",
						textStyle: {									
							color: '#e4d536',									
							fontStyle: 'normal',									
							fontWeight: 'bold',									
							fontFamily: 'sans-serif',
							fontSize: 28
						}
					},
					tooltip: {
						formatter: "{a} <br/>{b} : {c}分"
					},

					series: [{
						name: '业务指标',
						type: 'gauge',
						min: -100,
						max: 100,
						axisLine: { 
							lineStyle: { 
								width: 10
							}
						},
						splitLine: { 
							length: 10,
							lineStyle: { 
								color: 'auto'
							}
						},
						detail: {
							formatter: '{value}分'
						},
						data: [{
							value: score1,
							name: '违约成本'
						}]
					}]
				};
				var option2 = {
						title: {
						text: scoreCalculate[1].scoreClass.name + '级',
						left: "center",
						bottom: "14%",
						textStyle: {
							
							color: '#e4d536',									
							fontStyle: 'normal',									
							fontWeight: 'bold',									
							fontFamily: 'sans-serif',
							fontSize: 28
						}
					},
					tooltip: {
						formatter: "{a} <br/>{b} : {c}分"
					},

					series: [{
						name: '业务指标',
						type: 'gauge',
						min: -100,
						max: 100,
						axisLine: { 
							lineStyle: { 
								width: 10
							}
						},
						detail: {
							formatter: '{value}分'
						},
						data: [{
							value: grade1,
							name: '还款意愿'
						}]
					}]
				};
				var option3 = {
						title: {
						text: scoreCalculate[2].scoreClass.name + '级',
						left: "center",
						bottom: "14%",
						textStyle: {
							
							color: '#e4d536',									
							fontStyle: 'normal',									
							fontWeight: 'bold',									
							fontFamily: 'sans-serif',
							fontSize: 28
						}
					},
					tooltip: {
						formatter: "{a} <br/>{b} : {c}分"
					},

					series: [{
						name: '业务指标',
						type: 'gauge',
						min: -100,
						max: 100,
						axisLine: { 
							lineStyle: { 
								width: 10,
							}
						},
						detail: {
							formatter: '{value}分'
						},
						data: [{
							value: money1,
							name: '还款能力'
						}]
					}]
				};

				myChart2.setOption(option2);
				myChart3.setOption(option3);
				myChart1.setOption(option1);
				$("#span_capital").html("授信额度：" + data.creditCapital + "元");
	    	}else{
	    		parent.window.bootbox.alert(data.msg);
	    	}
	    }
	});
}
</script>
</body>
</html>

