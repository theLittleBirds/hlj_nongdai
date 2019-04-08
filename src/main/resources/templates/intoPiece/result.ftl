<!DOCTYPE html>
<html>
<head>
    <title>审核结论</title>
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
                    <div class="form-horizontal col-lg-8 m-t">
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
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">借款主体：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${name}"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">身份证号：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${idCard}"
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">手机号：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${phone}"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">地址：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${address}"
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">授信额度(元)：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${capital!''}"
                                         class="form-control"/>                                 
                             </div>

                             <label class="col-sm-2 control-label">借款期限(月)：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${term!''}"
                                        class="form-control">
                             </div>
                        </div>
                        
                        <div class="form-group">
                             <label class="col-sm-2 control-label">金融产品：</label>
                             <div class="col-sm-3">
                                 <input type="text" value="${product!''}"
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
															<input type="button" id="Button2" class="btn btn-primary"  onClick="deltr(this)" value="删行">
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
														<input type="button" id="Button2" class="btn btn-primary"  onClick="deltr(this)" value="删行">
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
                                 <textarea class="form-control">授信额度：${opinion.capital}元；借款期限：${opinion.term}个月；金融产品：${opinion.productId}；审核人：${opinion.examineName}；审核时间：${opinion.examineDate?string("yyyy-MM-dd HH:mm:ss")}；意见：${opinion.examineOpinion}</textarea>                              
                             </div>
                        	</div>
                        </#list> 
                                                
                        <div class="clearfix form-actions">
                            <div class="col-md-12 text-center">
                            <button class="btn btn-w-m" type="button" onclick="goTo()">
                                <i class="ace-icon fa fa-reply bigger-110"></i>  返回
                            </button>                           
                            </div>
                        </div>
                    </div>                
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/resource/echarts/echarts.min.js"></script>

<script type="text/javascript">
$(document).ready(function(e){
	
	var id = $("#id").val();
	$.ajax({
		type: "POST",
		url: "/examine/historyscore",
		data:{"id":id},
	    error: function(request) {			    	
	    },
	    success: function(data) {
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

function goTo(){
	parent.window.jsPageChange("intopiece_id");
}
</script>
</body>
</html>

