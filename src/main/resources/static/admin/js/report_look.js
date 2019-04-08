$(document).ready(function () {
	var aformId = getUrlParam("aformId");
	var rptId = getUrlParam("rptId");
	var appId = getUrlParam("appId");
	loadSyncDS4Body();
	getData();
	var totalCount = 11;
	var countSyncDS = 0;
	var titleDS;	//职称
	var marryDS;    //婚姻状况
	var educationDS; //教育状况
	var occupationDS //职业
	var compDesgnDS  //职务
	var cardDS       //证件类型
	var compStructureDS  //单位性质
	var bussSicDS        //单位行业类别
	var billMediaCdDS    //账单介质类型
	var payCredentialDS  //消费凭密
	var billAddrCdDS     //账单寄送地址
	function loadSyncDS4Body()
	{			//事件类型
		if(titleDS == undefined)
		{
	    var appParaGroupName = "TITLE";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	titleDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(marryDS == undefined)
		{
	    var appParaGroupName = "MARRY";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	marryDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(educationDS == undefined)
		{
	    var appParaGroupName = "EDUCATION";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	educationDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(occupationDS == undefined)
		{
	    var appParaGroupName = "OCCUPATION";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	occupationDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(compDesgnDS == undefined)
		{
	    var appParaGroupName = "COMP_DESGN";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	compDesgnDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(cardDS == undefined)
		{
	    var appParaGroupName = "CARD_TYPE";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	cardDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(compStructureDS == undefined)
		{
	    var appParaGroupName = "COMPANY";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	compStructureDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(bussSicDS == undefined)
		{	
	    var appParaGroupName = "INDUSTRY_CATEGORY";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	bussSicDS = eval(data);
		    	getData();
		    }
		});
		}

		if(billMediaCdDS == undefined)
		{	
	    var appParaGroupName = "BILLING_MEDIA_TYPE";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	billMediaCdDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(payCredentialDS == undefined)
		{	
	    var appParaGroupName = "CONSUMLTION_BY_SECRET";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	payCredentialDS = eval(data);
		    	getData();
		    }
		});
		}
		
		if(billAddrCdDS == undefined)
		{	
	    var appParaGroupName = "AD_BILL_ADDR_CD";
		$.ajax({
			type: "POST",
			//dataType: "json",
			url: "/appPara/getNodeStatusDS",
			data:{"appParaGroupName":appParaGroupName,"appId":appId},
		    error: function(request) {					
		    },
		    success: function(data) {
		    	countSyncDS ++;
		    	billAddrCdDS = eval(data);
		    	getData();
		    }
		});
		}
		
	}
	function getData()
	{
		if(countSyncDS < totalCount){
			return;
		}
		$.ajax({
			type: "POST",
			//			dataType: "json",
			url: "/applyform/queryApplyform",
			data: {
				"aformId": aformId
			},
			error: function (request) {},
			success: function (data) {
				//截取流程动态字符串
				var htmlInfo = new StringBuffer();
				$("#aformId").html(data.aformId);
				$("#adFullname").html(data.adFullname);
				$("#adIdno").html(data.adIdno);
				$("#adMobileno").html(data.adMobileno);
				$("#adEmail").html(data.adEmail);
				for(var i=0;i<cardDS.length;i++)
				{
					if(cardDS[i].parameterValue==data.adIdType)
					{
						$("#adIdType").html(cardDS[i].parameterName);
					}
				}
				$("#adIdExpired").html(data.adIdExpired);
				$("#adEmbSurname").html(data.adEmbSurname);
				$("#adEmbGivenname").html(data.adEmbGivenname);
				$("#adNationality").html(data.adNationality);
				for(var i=0;i<marryDS.length;i++)
				{
					if(marryDS[i].parameterValue==data.adMaritalStatus)
					{
						$("#adMaritalStatus").html(marryDS[i].parameterName);
					}
				}
				for(var i=0;i<educationDS.length;i++)
				{
					if(educationDS[i].parameterValue==data.adQualification)
					{
						$("#adQualification").html(educationDS[i].parameterName);
					}
				}
				$("#adYearIncome").html(data.adYearIncome);
				for(var i=0;i<occupationDS.length;i++)
				{
					if(occupationDS[i].parameterValue==data.adOccupation)
					{
						$("#adOccupation").html(occupationDS[i].parameterName);
					}
				}
				for(var i=0;i<compDesgnDS.length;i++)
				{
					if(compDesgnDS[i].parameterValue==data.adCompDesgn)
					{
						$("#adCompDesgn").html(compDesgnDS[i].parameterName);
					}
				}		
				for(var i=0;i<titleDS.length;i++)
				{
					if(titleDS[i].parameterValue==data.adTitleOfTechnical)
					{
						$("#adTitleOfTechnical").html(titleDS[i].parameterName);
					}
				}				
				$("#adRelName").html(data.adRelName);
				$("#adRelRelationToCh").html(data.adRelRelationToCh);
				$("#adRelMobilePhone").html(data.adRelMobilePhone);
				$("#adHomeState").html(data.adHomeState);
				$("#adHomeCity").html(data.adHomeCity);
				$("#adHomeDistrict").html(data.adHomeDistrict);
				$("#adHomeAddr").html(data.adHomeAddr);
				$("#adHomeZip").html(data.adHomeZip);
				$("#adHomeCity").html(data.adHomeCity);
				$("#adCompName").html(data.adCompName);
				for(var i=0;i<compStructureDS.length;i++)
				{
					if(compStructureDS[i].parameterValue==data.adCompStructure)
					{
						$("#adCompStructure").html(compStructureDS[i].parameterName);
					}
				}	
				for(var i=0;i<bussSicDS.length;i++)
				{
					if(bussSicDS[i].parameterValue==data.adBussSic)
					{
						$("#adBussSic").html(bussSicDS[i].parameterName);
					}
				}	
				$("#adLengthSrv").html(data.adLengthSrv);
				$("#adCompName").html(data.adCompName);
				$("#adCompState").html(data.adCompState);
				$("#adCompCity").html(data.adCompCity);
				$("#adCompDistrict").html(data.adCompDistrict);
				$("#adCompAddr").html(data.adCompAddr);
				$("#adCompZip").html(data.adCompZip);
				for(var i=0;i<billAddrCdDS.length;i++)
				{
					if(billAddrCdDS[i].parameterValue==data.adBillAddrCd)
					{
						$("#adBillAddrCd").html(billAddrCdDS[i].parameterName);
					}
				}
				for(var i=0;i<payCredentialDS.length;i++)
				{
					if(payCredentialDS[i].parameterValue==data.adPayCredential)
					{
						$("#adPayCredential").html(payCredentialDS[i].parameterName);
					}
				}	
				$("#adCardDeliveryAddrCd").html(data.adCardDeliveryAddrCd);
				$("#adBillMediaCd").html(data.adBillMediaCd);
				for(var i=0;i<billMediaCdDS.length;i++)
				{
					if(billMediaCdDS[i].parameterValue==data.adBillMediaCd)
					{
						$("#adBillMediaCd").html(billMediaCdDS[i].parameterName);
					}
				}	
				$("#adApplyChannel").html(data.adApplyChannel);
				$("#adProductId").html(data.adProductId);
				$("#adNumTerm").html(data.adNumTerm);
				$("#adAmountPerterm").html(data.adAmountPerterm);
				$("#adGoodsId").html(data.adGoodsId);
				$("#idDelete").html(data.idDelete);
				$("#creOperId").html(data.creOperId);
				$("#creOperName").html(data.creOperName);
				$("#creOrgId").html(data.creOrgId);
				$("#creDate").html(data.creDate);
				$("#updOperId").html(data.updOperId);
				$("#updOperName").html(data.updOperName);
				$("#updOrgId").html(data.updOrgId);
				$("#updDate").html(data.updDate);
				
				var d = new Date(new Date(data.tradeDate));
				var tradeDate = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
				$("#tradeDate").html(tradeDate);
				var html = template("tmpl", data);
				$('tbody>tr').html(html);
				// document.getElementById('varietyName').innerHTML = data.varietyName;
				// document.getElementById('tradeVolume').innerHTML = data.tradeVolume;
				// document.getElementById('tradeCash').innerHTML = data.tradeCash;
				// document.getElementById('hasAttachment').innerHTML = data.hasAttachment;
			}
		});
//		$.ajax({
//			type: "POST",
//			//			dataType: "json",
//			url: "/order/getFileList",
//			data: {
//				"parentId": orderNo
//			},
//			error: function (request) {},
//			success: function (data) {
//				var a;
//				var br;
//				var fujian = document.getElementById("fujian");
//				for (var i = 0; i < data.length; i++) {
//					a = document.createElement('a');
//					br = document.createElement('br');
//					a.name = data[i].fileLocalUrl; //增加a标签的href属性
//					a.id = "fujian" + i;
//					a.innerHTML = data[i].fileName; //给a标签添加内容
//					a.onclick = function () {  
//						$.download(this);  
//					} 
//					fujian.appendChild(a);
//					fujian.appendChild(br);
//				}
//			}
//		});
//
//		jQuery.download = function(obj){
//			var url = '/order/downLoadFile';
//			var method = 'post';
//			jQuery('<form action="'+url+'" method="'+(method||'post')+'">' +  // action请求路径及推送方法
//						'<input type="text" name="filedir" value="'+obj.name+'"/>' + // 文件路径
//						'<input type="text" name="fileName" value="'+obj.innerText+'"/>' + // 文件路径
//						'<input type="text" name="orderId" value="'+orderId+'"/>' + // 文件路径
//						'<input type="text" name="orderNo" value="'+orderNo+'"/>' + // 文件路径
//					'</form>')
//			.appendTo('body').submit().remove();	
//		};
			
	}
	//url参数的方法方法：
	  function getUrlParam(name) {
		  var searchStr = window.location.search;
	      //解码
	      searchStr = decodeURIComponent(searchStr);
	      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	      var r = searchStr.substr(1).match(reg);
	      if (r != null) return unescape(r[2]); return null;
	  }
});