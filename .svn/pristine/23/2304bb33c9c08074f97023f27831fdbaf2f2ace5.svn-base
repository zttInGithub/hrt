<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   

<script type="text/javascript">
	$(function(){
	var myDate = new Date();
	var mytime=myDate.toLocaleTimeString()+myDate.getMilliseconds();     //获取当前时间
		$(document).ready(function(){
			$.ajax({
	            url: "sysAdmin/menu_listMenuTreeByUser.action?time="+mytime,
	            dataType: "json",
	            success: function(data){
	            	InitLeftMenu(data);
		        }
	        });
		});
	});
	
	//初始化左侧
	function InitLeftMenu(data) {
		$("#navFirst").accordion({animate:false,fit:true,border:false});
		var selectedPanelname = '';
		$.each(data, function(i, n){
			var menulist = '';
			menulist +='<ul class="navFirst_ul">';
			if(typeof(n.children) != "undefined" && n.children.length>0)
			{
		        $.each(n.children, function(j, o) {
					menulist += '<li><div id="navSecond"><a ref="'+o.id+'" href="#" rel="' + o.attributes.url + '" ><span class="icon ' + o.iconCls + '" >&nbsp;</span><span class="navFirst">' + o.text + '</span></a></div> ';
					if(typeof(o.children) != "undefined" && o.children.length>0)
					{
						menulist += '<ul class="navThird_ul">';
						$.each(o.children,function(k,p){
							menulist += '<li><div id="navThird"><a ref="'+p.id+'" href="#" rel="' + p.attributes.url + '" ><span class="icon ' + p.iconCls + '" >&nbsp;</span><span class="navFirst">' + p.text + '</span></a></div></li>'
						});
						menulist += '</ul>';
					}
		
					menulist+='</li>';
		        });
	        }
	        menulist += '</ul>';
	
			$('#navFirst').accordion('add', {
	            title: '<span style="margin-left: 5px;">'+n.text+'</span>',
	            content: menulist,
				border:false,
				iconCls: 'icon1 ' + n.iconCls
	        });
	
			if(i==0)
				selectedPanelname =n.text;
	
	    });
	    
		$('#navFirst').accordion('select',selectedPanelname);
	
		$('.navFirst_ul li a').click(function(){
			var tabTitle = $(this).children('.navFirst').text();
			
			var url = $(this).attr("rel");
			var menuid = $(this).attr("ref");
			var icon = $(this).find('.icon').attr('class');
	
			var third = find(menuid,data);
			if(third && third.children && third.children.length>0)
			{
				$('.navThird_ul').slideUp();
	
				var ul =$(this).parent().next();
				if(ul.is(":hidden"))
					ul.slideDown();
				else
					ul.slideUp();
			}
			else{
				Center_addTabFun(tabTitle,url);
			}
		}).hover(function(){
			$(this).parent().addClass("hover");
		},function(){
			$(this).parent().removeClass("hover");
		});
	
		//选中第一个
		var panels = $('#navFirst').accordion('panels');
		var t = panels[0].panel('options').title;
	    $('#navFirst').accordion('select', t);
	    $('.navThird_ul').slideUp();
	}
	
	function find(menuid,data){
		var obj=null;
		$.each(data, function(i, n) {
			if(typeof(n.children) != "undefined" && n.children.length>0)
			{
			 	$.each(n.children, function(j, o) {
			 		if(o.id==menuid){
						obj = o;
					}
				 });
			}
		});
		return obj;
	}
</script>

<style type="text/css">
	*{
		font-size:12px; 
		font-family:Tahoma,Verdana,微软雅黑,新宋体;
	}
	a{
		color:Black; 
		text-decoration:none;
		outline:none;
		blr:expression(this.onFocus=this.blur());
	}
	.navFirst_ul{
		list-style-type:none;
		margin:0px; 
		padding:10px;
	}
	.navFirst_ul li a{
		line-height:24px;
	}
	.navFirst_ul li div{
		margin:2px 0px;
		padding-left:10px;
		border:1px dashed #ffffff;
		height: 20px;
	}
	.navFirst_ul li div.hover{
		border:1px dashed #99BBE8; 
		background:#E0ECFF;
		cursor:pointer;
	}
	.navFirst_ul li div.hover a{
		color:#416AA3;
	}
	.icon{
		width:12px;
		height: 12px; 
		line-height:18px; 
		display:inline-block;
		margin-right: 5px;
		vertical-align: middle;
	}
	.icon1{
		width:20px;
		height: 20px;
	}
	.navThird_ul{
		list-style-type:none;
		margin:0px; 
		padding: 0px;
	}
	.navThird_ul li a{
		line-height:24px;
	}
	.navThird_ul li div{
		margin:2px 0px;
		padding-left:20px;
		border:1px dashed #ffffff;
		height: 20px;
	}
	.navThird_ul li div.hover{
		border:1px dashed #99BBE8; 
		background:#E0ECFF;
		cursor:pointer;
	}
	.navThird_ul li div.hover a{
		color:#416AA3;
	}
	#navThird{
		margin-left: 10px;
	}
</style>

<div id="navFirst">

</div>
