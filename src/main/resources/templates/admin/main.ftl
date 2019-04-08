<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
  <meta name="format-detection" content="telephone=no" />
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>${tittle}</title>
  <meta name="keywords" content="${tittle}">
  <meta name="description" content="">
  <link rel="shortcut icon" href="/admin/image/${ico}">
  <meta name="author" content="">
  <title></title>
  <!--[if lt IE 9]>
        <meta http-equiv="refresh" content="0;ie.ftl" />
    <![endif]-->
  <!-- Bootstrap core CSS -->
  <link href="/resource/bootstrap/css/bootstrap.css" rel="stylesheet">
  <link href="/resource/bootstrap/css/font-awesome.min.css" rel="stylesheet">
  <link href="/resource/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
  <link href="/resource/bootstrap/css/bootstrap-editable.css" rel="stylesheet">
  <link href="/resource/bootstrap/css/datapicker/datepicker3.css" rel="stylesheet">
  <link href="/resource/bootstrap/css/bootstrap-datetimepicker.css" rel="stylesheet"> 
  <link href="/resource/bootstrap/css/bootstrap-select.min.css" rel="stylesheet">
  <!-- <link href="/resource/multipleselect/bootstrap-select.css" rel="stylesheet" > -->
  <!-- <link href="/resource/multipleselect/bootstrap.min.css" rel="stylesheet"> -->
  <!-- <link href="/resource/bootstrap/css/bootstrap-theme.css" rel="stylesheet"> -->
  <link href="/resource/bootstrap/css/jquery.treegrid.min.css" rel="stylesheet">
  <link href="/resource/hplus/dynamic-data.css" rel="stylesheet">
  <link href="/resource/viewer/viewer.min.css" rel="stylesheet">
  <link href="/resource/jNotify/css/jNotify.jquery.css" rel="stylesheet">
  <link href="/resource/fileinput/css/fileinput.min.css" rel="stylesheet">
  <!-- Custom styles for this template -->
  <link href="/admin/css/animate.css" rel="stylesheet">
  <link href="/admin/css/style.css?v=4.1.0" media="screen" rel="stylesheet" />
  <link href="/resource/layer/laydate/laydate.css" rel="stylesheet">
    
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden" id="jsid">
  <div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
      <div class="nav-close">
        <i class="fa fa-times-circle"></i>
      </div>
      <div class="sidebar-collapse" id="navbar">
       <ul class="nav" id="side-menu">
        <li class="nav-header">
         <div class="dropdown profile-element">
              <span><img alt="image" class="img-circle" src="/admin/image/touxiang.jpg" /></span>
               <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0);">
                 <span class="clear">
                  <span class="block m-t-xs"><strong class="font-bold"></strong></span>
                  <span class="text-muted text-xs block" style="display:block;max-height:20px;overflow:hidden;"><#if personName?exists>${personName}<#else></#if><b class="caret"></b></span>
                 </span>
               </a>
                <ul class="dropdown-menu animated fadeInRight m-t-xs">
                  <li>
                     <a id="upd_password" class="J_menuItem" href="javascript:void(0);">修改个人信息</a>
                  </li>                  
                </ul>
            </div>
              <div class="logo-element">NL+
              </div>
         </li>
        <!-- <li>
            <a href="javascript:void(0)" onclick="location.reload()" data-id="index_v1.html">
            <i class="fa fa-home"></i>
            <span class="nav-label">主页</span>
            </a>
         </li>-->
      </ul>
      </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
      <div class="row border-bottom">
        <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
          <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-success " href="#">
              <i class="fa fa-bars"></i>
            </a>
          </div>
          <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
              <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                <i class="fa fa-bell"></i>
                <span class="label label-primary">8</span>
              </a>
            </li>
            <li class="dropdown hidden-xs">
              <a class="right-sidebar-toggle" aria-expanded="false">
                <i class="fa fa-tasks"></i> 主题
              </a>
            </li>
            <li class="hidden-xs">
            <a href="/" class="J_tabExit">
          <i class="fa fa fa-sign-out"></i> 退出
          </a>
            </li>
          </ul>
        </nav>
      </div>
      <div class="row content-tabs">
        <button class="roll-nav roll-left J_tabLeft">
          <i class="fa fa-backward"></i>
        </button>
        <nav class="page-tabs J_menuTabs">
          <div class="page-tabs-content">
            <a href="javascript:;" class="active J_menuTab" id="main_shouye" data-id="index_v1.html">首页</a>
          </div>
        </nav>
        <button class="roll-nav roll-right J_tabRight">
          <i class="fa fa-forward"></i>
        </button>
        <div class="btn-group roll-nav roll-right">
          <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作
            <span class="caret"></span>

          </button>
          <ul role="menu" class="dropdown-menu dropdown-menu-right">
            <li class="J_tabShowActive">
              <a>定位当前选项卡</a>
            </li>
            <li class="divider"></li>
            <li class="J_tabCloseAll">
              <a>关闭全部选项卡</a>
            </li>
            <li class="J_tabCloseOther">
              <a>关闭其他选项卡</a>
            </li>
          </ul>
        </div>
      </div>
      <!-- 主显示区域开始 -->     
      <div class="row J_mainContent" id="content-main" style="overflow-y:scroll;margin-left: 0px;">
     	
      </div>
      <!-- 模态框（Modal） -->
			 <div class="initmodal">
			 </div>
      <!-- 主显示区域结束 -->
      <#--  <div class="footer">
        <div class="pull-right">&copy; 2018 - <a href="#" target="_blank">nongloan</a>
        </div>
      </div>    
    </div>  -->
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
      <div class="sidebar-container">
        <div class="tab-content">
          <div id="tab-1" class="tab-pane active">
            <div class="sidebar-title">
              <h3>
                <i class="fa fa-comments-o"></i> 主题设置</h3>
              <small>
                <i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
            </div>
            <div class="skin-setttings">
              <div class="title">主题设置</div>
              <div class="setings-item">
                <span>收起左侧菜单</span>
                <div class="switch">
                  <div class="onoffswitch">
                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                    <label class="onoffswitch-label" for="collapsemenu">
                      <span class="onoffswitch-inner"></span>
                      <span class="onoffswitch-switch"></span>
                    </label>
                  </div>
                </div>
              </div>
              <div class="setings-item">
                <span>固定顶部</span>

                <div class="switch">
                  <div class="onoffswitch">
                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                    <label class="onoffswitch-label" for="fixednavbar">
                      <span class="onoffswitch-inner"></span>
                      <span class="onoffswitch-switch"></span>
                    </label>
                  </div>
                </div>
              </div>
              <div class="setings-item">
                <span>
                  固定宽度
                </span>

                <div class="switch">
                  <div class="onoffswitch">
                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                    <label class="onoffswitch-label" for="boxedlayout">
                      <span class="onoffswitch-inner"></span>
                      <span class="onoffswitch-switch"></span>
                    </label>
                  </div>
                </div>
              </div>
              <div class="title">皮肤选择</div>
              <div class="setings-item default-skin nb">
                <span class="skin-name ">
                  <a href="#" class="s-skin-0">
                    默认皮肤
                  </a>
                </span>
              </div>
              <div class="setings-item blue-skin nb">
                <span class="skin-name ">
                  <a href="#" class="s-skin-1">
                    蓝色主题
                  </a>
                </span>
              </div>
              <div class="setings-item yellow-skin nb">
                <span class="skin-name ">
                  <a href="#" class="s-skin-3">
                    黄色/紫色主题
                  </a>
                </span>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <!--右侧边栏结束-->
  </div>
  <script src="/resource/jquery/jquery-3.2.1.js?v=2.1.4"></script>
  <script src="/resource/fileinput/js/fileinput.min.js"></script>
  <!--   <script src="/resource/fileinput/js/fileinput_locale_zh.js"></script> -->
  <script src="/resource/bootstrap/js/bootstrap.min.js"></script>
  <script src="/resource/bootstrap/js/datapicker/bootstrap-datepicker.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-table.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-editable.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-table-editable.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-treeview.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-table-treegrid.js"></script>
  <script src="/resource/bootstrap/js/jquery.treegrid.min.js"></script>
  <script src="/resource/bootstrap/js/moment.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-datetimepicker.js"></script>
  <script src="/resource/bootstrap/js/bootstrap-select.min.js"></script>
  <script src="/resource/bootbox/bootbox.js"></script>
  <script src="/resource/jNotify/js/jNotify.jquery.js"></script>
  <script src="/resource/util/StringBuffer.js"></script>
  <script src="/resource/util/lockandkey.js"></script>
  <script src="/resource/bootstrap/js/ie-emulation-modes-warning.js"></script>
  <script src="/resource/bootstrap/js/ie10-viewport-bug-workaround.js"></script>
  <script src="/resource/metisMenu/jquery.metisMenu.js"></script>
  <script src="/resource/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="/admin/js/main.js"></script>
  <script src="/resource/viewer/viewer.min.js"></script>
  <script src="/resource/echarts/echarts.min.js"></script>
  <script src="/resource/layer/laydate/laydate.js"></script>
  <script src="/resource/layer/layer.min.js"></script>
  <script src="/resource/qiniu/qiniu.js"></script>
  <!-- 自定义js -->
  <script src="/admin/js/hplus.js?v=4.1.0"></script>
  <script src="/admin/js/contabs.js"></script>
  <script src="/admin/js/intopiece_mng.js"></script>
  <script src="/admin/js/upd_pwd.js"></script>
  <script src="/admin/js/timeOut.js"></script>
  <!-- 第三方插件 -->
  <script src="/resource/pace/pace.min.js"></script>


  <script type="text/javascript">
  
    var curpersonname = "${userName}";
    function showError(tips, TimeShown, autoHide) {
      jError(
        tips, {
          autoHide: false, // 是否自动隐藏提示条
          clickOverlay: true, // 是否单击遮罩层才关闭提示条
          HorizontalPosition: 'center',
          MinWidth: 300, // 最小宽度
          VerticalPosition: 'top',
          ShowOverlay: true,
          ColorOverlay: '#000',
          onCompleted: function () { // added in v2.0
          }
        }
      );
    }
	
    function jsPageChange(pageId){
    	$("#"+pageId).click();
    }
    function iframeChange(actionUrl){
    	$.ajax({
	      		type: "POST",
	      		url: "/main/timeout",
	      	    error: function(request) {			    	
	      	    },
	      	    success: function(data) {
	      	     	if(data != null){
	      	     		$("#content-main").empty();
    					$("#content-main").append("<iframe id='myFrameId' scrolling='yes' frameborder='0' src='"+actionUrl+"' height='100%' width='100%'></iframe>");
	      	     	}	      	    	
	      	    }
	     });
    	
    }
     var menuLock = true;
     function childMenu(menuId){
		 if(menuLock == true && $("#"+menuId).find("li").length == 0){
		    menuLock = false;
			$.ajax({
		      		type: "POST",
		      		data: {"menuId":menuId},
		      		url: "/menu/getChildMenu",
		      	    error: function(request) {			    	
		      	    },
		      	    success: function(data) {
		      	        var menuchildren = eval(data);
		      	        if(menuchildren != undefined && menuchildren.length > 0){
				      	    for(var i=0;i<menuchildren.length;i++)
			             	{
				             	  if(menuchildren[i].actionUrl != ""){
				             	     $("#"+menuId).append("<li><a href='javascript:void(0);' class='J_menuItem' data-index='" +(new Date()).getTime()+i+ "' id='"+menuchildren[i].htmlid+"' onclick='iframeChange(\""+menuchildren[i].actionUrl+"\")'>"+menuchildren[i].text+"</a></li>");
				             	  }else{
				             	     $("#"+menuId).append("<li><a href='javascript:void(0);' class='J_menuItem' data-index='" +(new Date()).getTime()+i+ "' id='"+menuchildren[i].htmlid+"'>"+menuchildren[i].text+"</a></li>");
				             	  }
				             	  if(menuchildren[i].action_js != ""){
				      	            loadJS(menuchildren[i].action_js);
				      	          }
			      	        }
		      	        }
		      	        menuLock = true;
		      	    }
		     });
		  }
	 }
     
      function loadJS(action_js){
		var script_dom = document.createElement('script');
		script_dom.src = action_js;
		script_dom.language = 'javascript';
		var head = document.getElementsByTagName('head').item(0);
		$("#navbar").append(script_dom);
      }
     
      $('#main_shouye').click(function(){
        init_global77();
      })
      
      function init_global77()
	  {
		$("#content-main").empty();
		$(".initmodal").empty();
		loadechart();
	  }
		
      function loadechart(){
    	var htmlInfo=new StringBuffer();
    	htmlInfo.append("<div style=\"width:100%;height:200px;margin-top:20px\"  class=\"panel panel-default\">");
    	htmlInfo.append("<div style=\"width:10%;height:60px;margin-top:70px;float:left\"></div>");
    	htmlInfo.append("<div style=\"width:15%;height:60px;margin-top:70px;float:left\"><h4 id=\"detail1\"></h4></div>");
    	htmlInfo.append("<div style=\"width:15%;height:60px;margin-top:70px;float:left\"><h4 id=\"detail2\"></h4></div>");
    	htmlInfo.append("<div style=\"width:15%;height:60px;margin-top:70px;float:left\"><h4 id=\"detail3\"></h4></div>");
    	htmlInfo.append("<div style=\"width:15%;height:60px;margin-top:70px;float:left\"><h4 id=\"detail4\"></h4></div>");
    	htmlInfo.append("<div style=\"width:15%;height:60px;margin-top:70px;float:left\"><h4 id=\"detail5\"></h4></div>");
    	htmlInfo.append("</div>");
    	htmlInfo.append("<div style=\"width:100%;height:400px;margin-top:20px\">");
    	htmlInfo.append("<div id=\"intoPiece_chart\" style=\"width:50%;height:100%;margin-left:0px;padding-top: 10px;float:left\" class=\"panel panel-default\"></div>");
		htmlInfo.append("<div id=\"money_chart\" style=\"width:50%;height:100%;margin-right:0px;padding-top: 10px;float:left\" class=\"panel panel-default\"></div>");
    	htmlInfo.append("</div>");
		
		$("#content-main").append(htmlInfo.toString());
			$.ajax({
	      		type: "POST",
	      		url: "/main/todaystatistics",
	      	    error: function(request) {			    	
	      	    },
	      	    success: function(data) {
	      	    	if(data.code == 200){
	      	    		$("#detail1").html("今日进件："+data.cre);
	      	    		$("#detail2").html("待核资料："+data.status1);
	      	    		$("#detail3").html("电审中："+data.other);
	      	    		$("#detail4").html("今日拒绝："+data.status0);
	      	    		$("#detail5").html("今日放款："+data.money+"/"+data.moneytimes);
	      	    	}else{
	      	    		bootbox.alert(data.msg);
	      	    	}
	      	    }
	      	});

    	  // 基于准备好的dom，初始化echarts实例
          var intoPiece_chart = echarts.init(document.getElementById('intoPiece_chart'));
    	
          var money_chart = echarts.init(document.getElementById('money_chart'));
          
          $.ajax({
      		type: "POST",
      		url: "/main/weekipstatistics",
      	    error: function(request) {			    	
      	    },
      	    success: function(data) {
      	    	if(data.code == 200){
      	    		var day = data.day;
      	    		var times = data.times;
      	    		option = {
      	    				title: {
      	    					text: '一周进件统计'
      	    				},
      	    				legend: {
      	    		            data: ['日期', '进件数']
      	    		        },
      	         		    xAxis: {
      	         		    	name: '日期',
      	         		        type: 'category',
      	         		        data: day
      	         		    },
      	         		    yAxis: {
      	         		        type: 'value',
      	         		        minInterval : 1
      	         		    },
      	         		    series: [{
      	         		    	name: '进件数',
      	         		        data: times,
      	         		        type: 'line'
      	         		    }]
      	         		};

      	            // 使用刚指定的配置项和数据显示图表。
      	            intoPiece_chart.setOption(option);
      	    	}else{
      	    		bootbox.alert(data.msg);
      	    	}
      	    }
      	});
          
         $.ajax({
       		type: "POST",
       		url: "/main/weekmoneystatistics",
       	    error: function(request) {			    	
       	    },
       	    success: function(data) {
       	    	if(data.code == 200){
       	    		var day = data.day;
       	    		var money = data.money;
       	    		option = {
       	    				title: {
       	    					text: '一周放款统计'
       	    				},
       	    				legend: {
       	    		            data: ['日期', '放款额']
       	    		        },
       	         		    xAxis: {
       	         		    	name: '日期',
       	         		        type: 'category',
       	         		        data: day
       	         		    },
       	         		    yAxis: {
       	         		        type: 'value'
       	         		    },
       	         		    series: [{
       	         		    	name: '放款额',
       	         		        data: money,
       	         		        type: 'line'
       	         		    }]
       	         		};

       	            // 使用刚指定的配置项和数据显示图表。
       	            money_chart.setOption(option);
       	    	}else{
       	    		bootbox.alert(data.msg);
       	    	}
       	    }
       	});
         
      }

      loadechart();
      
      function removeBootBox(name){
    	  $('.'+name).remove();
    	  $(".modal-backdrop").remove();
      }
  </script>
</body>

</html>
