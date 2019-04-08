<!DOCTYPE html>
<html>

<head>
	<title>进件终审</title>
	<link href="/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="/resource/jquery/jquery.min.js?v=2.1.4"></script>
	<script src="/resource/bootstrap/js/bootstrap.min.js"></script>
	<script src="/admin/js/timeOut.js"></script>
</head>

<body>
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content clearfix">
						<form class="form-horizontal col-lg-8 m-t" onsubmit="return checkForm()" id="lastForm" enctype="multipart/form-data" method="post">
						<input type="hidden" name="id" id="id" value="${id}">

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


						



						<!--  <div class="form-group">
							<label class="col-sm-2 control-label">评分：</label>
							<div class="col-sm-8">
								<div id="creditCapital" style="float: left;padding-top: 10px;">
									<span style='margin-left: 5px;' id="score"></span>
									<span style='color: red;margin-left: 20px;' id="grade"></span>
									<span style='color: red;margin-left: 20px;' id="money"></span>
								</div>
								<div style="float: left">
									<button class="btn btn-info" type="button" onclick="getscore()" style="margin-left: 20px;" id="btn_score">
										<i></i>刷新
									</button>
								</div>
							</div>
						</div>  -->

						<div class="form-group">
							<label class="col-sm-2 control-label">审核结果：</label>
							<div class="col-sm-3">
								<select id="nodeType" name="nodeType" class="form-control" onchange="nodeChange()">
									<option value="1">提交</option>
									<option value="5">回退</option>
									<option value="10">驳回</option>
								</select>
							</div>
							<label class="col-sm-2 control-label">提交节点：</label>
							<div class="col-sm-3">
								<select id="nextNodeId" name="nextNodeId" class="form-control">

								</select>
								<input type="hidden" name="pcId" id="pcId" />
							</div>
						</div>

						<div style="display: none;" id="hide_show">
							<div class="form-group">
								<label class="col-sm-2 control-label">借款主体：</label>
								<div class="col-sm-3">
									<input type="text" value="${name}" readonly="readonly" class="form-control" />
								</div>

								<label class="col-sm-2 control-label">身份证号：</label>
								<div class="col-sm-3">
									<input type="text" value="${idCard}" readonly="readonly" class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">手机号：</label>
								<div class="col-sm-3">
									<input type="text" value="${phone}" readonly="readonly" class="form-control" />
								</div>

								<label class="col-sm-2 control-label">地址：</label>
								<div class="col-sm-3">
									<input type="text" value="${address}" readonly="readonly" class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">授信额度(元)：</label>
								<div class="col-sm-3">
									<input type="number" id="capital" name="capital" readonly="readonly" onchange="calculation('1')" value="${capital!''}" class="form-control" />
								</div>
									<!-- 从保函出具审核过来的件 -->
									<input type="hidden" id="capitalUsed" name="capitalUsed"   value="${capital}" />
								<label class="col-sm-2 control-label">借款期限(月)：</label>
								<div class="col-sm-3">
									<input type="number" id="term" name="term" onchange="calculation('2')" readonly="readonly" value="${term!''}" class="form-control">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">金融产品：</label>
								<div class="col-sm-3">
									<select id="product" name="product" class="form-control" disabled="disabled" onchange="calculation('3')">
										<option value="">--请选择--</option>
										<#list productList as one>
											<option value="${one.productId}">${one.name}</option>
										</#list>
									</select>
									<span id="detail"></span>
								</div>

								<label class="col-sm-2 control-label" hidden="hidden">首期还款额(元)：</label>
								<div class="col-sm-3">
									<input type="text" id="firstRepayment" style="display:none"  class="form-control">
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
														</td>
													</tr>
												</#if>
											</tbody>
										</table>
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
										<textarea class="form-control" readonly="readonly">授信额度：${opinion.capital}元；借款期限：${opinion.term}个月；金融产品：${opinion.productId}；审核人：${opinion.examineName}；审核时间：${opinion.examineDate?string("yyyy-MM-ddHH:mm:ss")}；意见：${opinion.examineOpinion}
										</textarea>
									</div>
								</div>
							</#list>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">终审意见：</label>
							<div class="col-sm-8">
								<textarea id="opinion" name="opinion" class="form-control"></textarea>
							</div>
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-12 text-center">
								<button class="btn btn-w-m" type="button" onclick="window.history.go(-1);">
									<i class="ace-icon fa fa-reply bigger-110"></i> 返回
								</button>

								<button class="btn btn-w-m btn-success" type="submit">
									<i class="ace-icon fa fa-check bigger-110"></i> 提交
								</button>
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
			</td>
		</tr>
	</tbody>
</table>
<script src="/resource/echarts/echarts.min.js"></script>
<script type="text/javascript">
	$(document).ready(function (e) {
		nodeChange();
		
		var hide_product = $("#hide_product").val();
		if (hide_product != '') {
			$("#product").val(hide_product);
			//计算还款
			calculation("3");
		}
		var id = $("#id").val();
		$.ajax({
			type: "POST",
			url: "/examine/historyscore",
			data: {
				"id": id
			},
			error: function (request) {},
			success: function (data) {
				if (data.code == 200) {
					var history = data.history;
					if (history.length == 0) {
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
				} else {
					parent.window.bootbox.alert(data.msg);
				}
			}
		});
	});
	
	
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
	function calculation(key) {
		var product = $("#product").val();
		if (product == '') {
			$("#detail").html("");
			return;
		}
		if (key == "3") {
			$.ajax({
				type: "POST",
				url: "/product/productdetail",
				data: {
					"productId": product
				},
				error: function (request) {},
				success: function (data) {
					$("#detail").html(data);
				}
			});
		}
		var capital = $("#capital").val();
		if (capital == '') {
			return;
		}
		if (key == "1") {
			var hide_capital = $("#hide_capital").val();
			if (hide_capital != '') {
				if (parseInt(hide_capital) < parseInt(capital)) {
					$("#capital").val(hide_capital);
					parent.window.bootbox.alert("授信额度不能大于上一步审核额度");
					return false;
				}
			}
		}
		var term = $("#term").val();
		if (term == '') {
			return;
		}
		$.ajax({
			type: "POST",
			url: "/examine/calculationloan",
			data: {
				"capital": capital,
				"term": term,
				"product": product
			},
			error: function (request) {},
			success: function (data) {
				if (data.code == 200) {
					$("#firstRepayment").val(data.firstRepayment);
				} else {
					alert(data.msg);
				}
			}
		});

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
	
	function checkForm() {
		var id = $("#id").val();
		var nodeType = $("#nodeType").val();	
		var pcId = $("#pcId").val();
		var opinion = $("#opinion").val();
		var nextNodeId = $("#nextNodeId").val();
		if(nextNodeId == ''){
			parent.window.bootbox.alert("请选择要提交的节点");
			return false;
		}
		if(opinion == ''){
			parent.window.bootbox.alert("请填写审核意见");
			return false;
		}
		$.ajax({
			type: "POST",
			url: "/examine/lastformsave",
			data: {
				"id":id,
				"nodeType": nodeType,
				"pcId": pcId,
				"opinion": opinion,
				"nextNodeId": nextNodeId
			},
			error: function (request) {
				parent.window.bootbox.alert("保存出错");
			},
			success: function (data) {
				parent.window.bootbox.alert(data.msg);
    	    	if(data.code==200){
    	    		window.history.go(-1);
    	    	}
			}
		});
		return false;
	}

	function nodeChange() {
		var id = $("#id").val();
		var result = $("#nodeType").val();
		if (result == 1) {
			$("#hide_show").show();
		} else {
			$("#hide_show").hide();
		}
		$.ajax({
			type: "POST",
			url: "/flowmgr/getnodes",
			data: {
				"intoPieceId": id,
				"type": result
			},
			error: function (request) {},
			success: function (data) {
				if (data.code == 200) {
					var arr = data.nodes;
					var html = "";
					if (arr != null) {
						for (var i = 0; i < arr.length; i++) {
							html = html + "<option value='" + arr[i].nodeId + "'>" + arr[i].cname + "</option>"
						}
					}
					$("#nextNodeId").empty();
					$("#nextNodeId").append(html);
					$("#pcId").val(data.pcId);
				} else {
					parent.window.bootbox.alert(data.msg);
				}
			}
		});
	}

	function getscore() {
		$("#btn_score").attr("disabled", "disabled");
		var id = $("#id").val();
		$.ajax({
			type: "POST",
			url: "/scoreMgr/scoreCalculateMgr",
			data: {
				"intoPieceId": id
			},
			error: function (request) {},
			success: function (data) {
				$("#btn_score").removeAttr("disabled");
				if (data.code == 200) {
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
				} else {
					parent.window.bootbox.alert(data.msg);
				}
			}
		});

		// echarts图表

	}
</script>
</body>

</html>