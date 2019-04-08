var allMenuId;
$(document).ready(function(){

	getMenuList();
	
	//获取菜单数据
	function getMenuList()
	{
		$.ajax({
    	   type:'post',
    	   url: "/menu/navMenu1",
    	   data: {"status":"1"},
    	   success:function(data){
    		   init_navmenu(data);			      
    	   }
        });
	}
	
	//初始化导航菜单
	function init_navmenu(menu)
	{
		var htmlInfo=new StringBuffer();
		var menuobjs=eval(menu);
		//动态生成菜单
		for(var i=0;i<menuobjs.length;i++)
		{ 
			//添加一级菜单
			htmlInfo.append("<li><a href=\"javascript:void(0);\"");
			htmlInfo.append("onclick=\"childMenu('"+menuobjs[i].id+"')\"");
			htmlInfo.append("><i class=\"" +menuobjs[i].logoUrl+"\"></i><span class=\"nav-label\">"+menuobjs[i].text+"</span><span class=\"fa arrow\"></span></a>");
			htmlInfo.append("<ul class=\"nav nav-second-level collapse\"  id=\""+menuobjs[i].id+"\">");
			htmlInfo.append("</ul>");
			htmlInfo.append("</li>");
		}
		$("#side-menu").append(htmlInfo.toString());
		
		nav();
	}

	
	Array.prototype.unique = function()
	{
		var n = {},r=[]; //n为hash表，r为临时数组
		for(var i = 0; i < this.length; i++) //遍历当前数组
		{
			if (!n[this[i]]) //如果hash表中没有当前项
			{
				n[this[i]] = true; //存入hash表
				r.push(this[i]); //把当前数组的当前项push到临时数组里面
			}
		}
		return r;
	};
	//控制点击菜单
	function nav()
	{
		$('#side-menu').children("li").children("a").on("click",function(){
			var $li = $(this).parent("li");
			$li.toggleClass("active").children("ul").toggleClass("in");
			$li.siblings().removeClass("active").children("ul.in").collapse("hide");
		});
	}	
});
