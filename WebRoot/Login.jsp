<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/js/sysutil.js"></script>
		<script type="text/javascript" src="${ctx}/js/writeObject.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/objectStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/jquery/jquery-easyui-1.3.1/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/jquery/jquery-easyui-1.3.1/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/sysutil.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/reset.css" />
		<title>${title }</title>
		
		<script type="text/javascript">
		//window.open ('${ctx}/popup.html','会员宝简介','height=500,width=740,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
			//获取服务器时间戳
			var ts="<%=System.currentTimeMillis()%>";	
			function doLogin(){
				
				var browser={  
			      versions:function(){  
			            var u = navigator.userAgent, app = navigator.appVersion;  
			            return{//移动终端浏览器版本信息  
			                   trident: u.indexOf('Trident') > -1,//IE内核  
			                   presto: u.indexOf('Presto') > -1,//opera内核  
			                   webKit: u.indexOf('AppleWebKit') > -1,//苹果、谷歌内核  
			                   gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
			                   mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/),//是否为移动终端  
			                   ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),//ios终端  
			                   android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
			                   iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器  
			                   iPad: u.indexOf('iPad') > -1,//是否iPad  
			                   webApp: u.indexOf('Safari') == -1//是否web应该程序，没有头部与底部  
			            };  
			          }(),  
			          language:(navigator.browserLanguage || navigator.language).toLowerCase()  
			    }
				if($('#loginName').val() == '') {
					$.messager.show({
						title : '提示',
						msg : "用户名不可以为空！"
					});
					//$.messager.alert("提示","用户名不可以为空！");
					return;
				}
				if ($('#rand').val() == '') {
					$.messager.show({
						title : '提示',
						msg : "验证码不可以为空！"
					});
					//$.messager.alert("提示","验证码不可以为空！");
					return;
				}
			    var logonForm = document.getElementById("myfrom");
			    //alert(!browser.versions.mobile);
				//if(!browser.versions.mobile){
					//将获取到的密文赋值到页面中的form表单
					if (navigator.userAgent.indexOf("Window")>0) {
						var password =getPassInput("powerpass", ts, "密码输入错误：");
						if(password==null){
							return;
						}
						logonForm.password.value = password;
						if($('#pass').val() == '') {
							$.messager.show({
								title : '提示',
								msg : "密码不可以为空！"
							});
							//$.messager.alert("提示","密码不可以为空！");
							return false;
						}
				    }else{
						if($('#pass').val() == '') {
							$.messager.show({
								title : '提示',
								msg : "密码不可以为空！"
							});
							return false;
						}
						if($('#pass').val().length<6) {
							$.messager.show({
								title : '提示',
								msg : "密码长度不足！"
							});
							return false;
						}
				    }
				//}else{
				//}
				//提交
				logonForm.submit();
			}
			//表单验证
			function checkFrom() {
				if($('#loginName').val() == '') {
					$.messager.alert("提示","用户名不可以为空！");
					return false;
				}else if ($('#rand').val() == '') {
					$.messager.alert("提示","验证码不可以为空！");
					return false;
				}
				return true;
			}
			
			//验证码
			function changeValidateCode(obj) {   
				//获取当前的时间作为参数，读取时就不会读取缓存中的内容   
				var timenow = new Date().getTime();   
				//每次请求需要一个不同的参数，否则可能会返回同样的验证码   
				//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
				obj.src="${ctx}/sysAdmin/rand_randImage.action?d="+timenow;
			}
			
			function load()
			{
			    //判断是否为移动端
				var browser={  
			      versions:function(){  
			            var u = navigator.userAgent, app = navigator.appVersion;  
			            return{//移动终端浏览器版本信息  
			                   trident: u.indexOf('Trident') > -1,//IE内核  
			                   presto: u.indexOf('Presto') > -1,//opera内核  
			                   webKit: u.indexOf('AppleWebKit') > -1,//苹果、谷歌内核  
			                   gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
			                   mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/),//是否为移动终端  
			                   ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),//ios终端  
			                   android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
			                   iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器  
			                   iPad: u.indexOf('iPad') > -1,//是否iPad  
			                   webApp: u.indexOf('Safari') == -1//是否web应该程序，没有头部与底部  
			            };  
			          }(),  
			          language:(navigator.browserLanguage || navigator.language).toLowerCase()  
			    }  
			    //alert("登陆为移动终端: "+browser.versions.mobile);
				//if(browser.versions.mobile){
					//alert("登陆为移动终端,不能下载安全控件");
					//$('#pass').attr('type','password');
					//document.getElementById("pass").type="password";
				//}
				$('#loginName').empty();
				$('#rand').empty();
				if (!isIE())
					//如果是非IE浏览器，则调用此函数，为控件添加事件处理函数。
					doAdd();
			}
			
			function doAdd()
			{	
			    //获取对象
			    var powerpass = document.getElementById("powerpass");
			  	//添加Password控件的Tab事件，如果收到此事件，则触发OnPassEventTab()函数
			    addEvent(powerpass, "EventTab",OnPassEventTab);
			}
			
			function addEvent(obj, name, func)
			{
			    obj.addEventListener(name, func, false); 
			}
			
			function OnPassEventTab()
			{
				//在收到Password控件上的Tab事件时，将焦点放在id为login的标签上。
				$('#rand').focus();
			}
			
			function mailBlur(){
				$("#unitName").empty();
				$("#unNo").empty();
				var loginName = $('#loginName').val();
				if($.trim(loginName) != ''){
					$.ajax({
						url:"${ctx}/sysAdmin/user_loginNameUnit.action",
						type:'post',
						data:{"loginName":loginName},
						dataType:'json',
						success:function(data, textStatus, jqXHR) {
							if (data) {
								var unitName = data.unitName.substring(0, data.unitName.length-1);
								var unNo = data.unNo.substring(0, data.unNo.length-1);
								var unitNames = unitName.split(",");
								var unNos = unNo.split(",");
								$("#unitName").append("选择机构："); 
								for (var i = 0; i < unitNames.length; i++){
									var radio = "<input type='radio' value='"+unNos[i]+"' name='unNo' style='border:0px; width:50px;'>"+unitNames[i]+"<br>";
									$("#unNo").append(radio);
									$("#unNo").find("input[type='radio'][value='"+unNos[0]+"']").attr("checked","true");
								}
							}
						}
					});
				}
			}
			
			function getMesRand(){
				var loginName = $('#loginName').val();
				//var password = $('#password').val();
				if($.trim(loginName) != ''){
					$.ajax({
						url:"${ctx}/sysAdmin/user_getMesRand.action",
						type:'post',
						data:{"loginName":loginName},
						dataType:'json',
						success:function(data, textStatus, jqXHR) {
							if (data.success) {
								//alert("data："+data);
							}else{
								$.messager.show({
									title : '提示',
									msg : data.msg
								});
								//$.messager.alert('提示', data.msg);
							}
						}
					});
				}else{
					$.messager.show({
						title : '提示',
						msg : "请输入用户名"
					});
					//$.messager.alert('提示',"请输入登录名");		
				}
			}
			//短信
			$(function() {
				$.ajax({
					url:"${ctx}/sysAdmin/user_findIfAuto.action",
					type:'post',
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							//$.messager.alert('提示', data.msg);
						} else {
							$("#tr_mesRand").hide();
						}
					},
					error:function() {
						//$.messager.alert('提示', '请刷新页面！');
					}
				});
			});
			$(function(){
				/*var winWidth = $(window).width();
				var winHeight = $(window).height();
				$("#warp").attr("width",winWidth);
				$("#warp").attr("height",winHeight);
				var wH = window.innerHeight > 0 ? window.innerHeight : document.documentElement.clientHeight;
				document.body.style.height = wH + 'px';*/
			});
		</script>
		<!-- 密码控件Tab事件处理 -->
		<script language="javascript" for='powerpass'   event='EventTab'>
			//将焦点放在id为login的标签上。
			//login.focus();
			$('#rand').focus();
		</script>
<style type="text/css">
body{
 	font-size: 15px;
}
#top{
    width: 100%;
    height: 110px;
    position: absolute;
    background-color: #fff;
    z-index: 1;
}
#top img{
    display: block;
    margin: 30px auto;
}
#warp{
    width: 100%;
    height: 100%;
	background: url(${ctx}/images/warp_bg.png) no-repeat;
	background-size: 100% 100%;
    overflow:hidden;
    position: fixed;
    top: 0;
    left: 0;
}
#main{
	width: 664px;
	margin: 14% auto;
	margin-top: 180px;
}
.content-warp{
	width: 360px;
	margin: 0 auto;
}
.cont-warp-top,.content img{
	width: 100%;
}
.content img{
	margin-bottom: 30px;
}
.content {
    background-color: #fff;
    margin-top: 20px;
    border-radius: 4px;
}
.cont-list {
    width: 300px;
    height:39px;
    margin: 0 auto;
    border-radius: 4px;
    margin-bottom: 15px;
    background-color:rgb(241,245,251);
}
.cont-input {
    border: solid 1px #e4e9ef;
}
.cont-input span,.icon-radio{
    display: inline-block;
    width: 40px;
    border-right: solid 1px #ddd;
    height: 40px;
    float: left;
}
.cont-input input{
	width: 238px;
    font-size: 14px;
    padding-left:20px;
    padding-top:12px;
    background-color:rgb(241,245,251) !important;
}
.cont-btn{
    background-color: #e7922b;
    margin: 22px auto;
}
.cont-btn button{
	width: 100%;
    line-height: 39px;
    background-color: #3375d5;
    color: #fff;
    font-size: 16px;
    border-radius: 4px;
}
.icon-user{
	background: url(${ctx}/images/login_user.png) no-repeat center;
}
.icon-password{
	background: url(${ctx}/images/pass_word.png) no-repeat center;
}
.icon-radio{
	background: url(${ctx}/images/cont_no.png) no-repeat center;
	border: 0;
	margin-left: 20px;
	height: 48px;
}
.icon-test{
    background: url(${ctx}/images/test_num.png) no-repeat center;
    border: 0;
    height: 48px;
}
.cont-test input{
    width: 110px;
}
.cont-test img{
    width: 120px;
    height:39px;
    margin: auto 0;
}
.cont-btm .cont-list{
	width: 100%;
	background-color: #eef7fe;
    line-height: 50px;
}
.content-btm{
	color: #d8e7f7;
    margin-top:90px;
}
.cont-btm-ac{
	color: #5ea0ff;
}
.icon-radio-bg{
	background: url(${ctx}/images/cont_yes.png) no-repeat center;
}
.download_install{
	width: 255px;
    font-size: 14px;
    border: solid 1px #F1F5FB;
    background-color:rgb(241,245,251);
}
.cont-input object{
    margin-left:17px;
    margin-top:10px;
    font-size: 14px;
    background-color:rgb(241,245,251);
}
.mesRand{
	width: 300px;
    height:39px;
    margin: 0 auto;
    border-radius: 4px;
    margin-bottom: 15px;
    background-color:rgb(241,245,251);
}
.mesRand_input{
	width: 134px;
    font-size: 14px;
    padding-left:20px;
    background-color:rgb(241,245,251);
}
.mesRand_btn{
	margin-top: 5px;
	margin-left:20px;
	width:80px;
	height:28px;
	border:0px;
	background:url(${ctx }/images/login_btn1.png);
	color:#FFF;
	font-size:14px;
	font-family:"微软雅黑";
	cursor:pointer;
	text-align:center;
}
#loginName:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px rgb(241,245,251) inset;
    border: 1px solid rgb(241,245,251);
}
.msg_span{
	display: inline-block;
    width: 40px;
    border-right: solid 1px #ddd;
    height: 40px;
    float: left;
    background: url(${ctx}/images/test_num.png) no-repeat center;
}

</style>
		
</head>
	
<body onload="load()">
    <div id="top">
        <img src="${ctx}/images/login_top.png" />
    </div>
    <div id="warp">
        <div id="main">
            <div class="content-warp">
                <div class="content cf">
                	<form id="myfrom" method="post" action="${ctx}/sysAdmin/user_login.action">
	                    <img src="${ctx}/images/cont_top.png" />
	                    <div class="cont-list cont-input">
	                        <span class="icon-user"></span>
	                        <input type="text" placeholder="请输入用户名" id="loginName" name="loginName" value="" onblur="mailBlur();"  />
	                    </div>
	                    <div class="cont-list cont-input" style="height:40px;background-color: rgb(241,245,251)">
	                        <span class="icon-password"></span>
	                        <input type="password" placeholder="请输入密码" id="pass" name="password" value="" />
	                        <input type="hidden" id="macTry" name="macTry" value="" />
	                    	<script type="text/javascript">
	                    	if (navigator.userAgent.indexOf("Window")>0) {
	                			$("#pass").hide();
	                	    	$("#macTry").val("");
	                    		writePassObject("powerpass",{"width":220,"height":30,"maxLength":30,"minLength":4,"borderColor":"#F1F5FB","softkbdRandom":"false","textColor":"#000000","backColor":"#F1F5FB","accepts":"[:graph:]+"});
	                	    }else{
	                			$("#pass").show();
	                			$("#macTry").val("MAC");
	                		}
	                    	</script>
	                    </div>
	                    <!-- 
	                    <div id="tr_mesRand cont-list cont-test cont-input" class="mesRand">
	                    	<span class="icon-test"></span>
	                    	<input class="mesRand_input" type="text" name="mesRand" id="mesRand" size="5" value=""/>
	                    	<input type="button" onclick="getMesRand()" class="mes_input_btn" id="getMesbutton" value="获取" />
    					</div>
    					 -->
    					<div id="tr_mesRand" class="cont-list" style="margin-bottom: 15px;">
	                        <span class="msg_span"></span>
	                        <input type="text" placeholder="请输入短信验证码" name="mesRand" id="mesRand" class="mesRand_input" size="5" value=""  />
	                        <input type="button" onclick="getMesRand()" class="mesRand_btn" id="getMesbutton" value="获取" />
    					</div>
	                    <div class="cont-list cont-input cont-test" style="margin-bottom: 5px">
	                        <span class="icon-test"></span>
	                        <input type="text" placeholder="请输入验证码" name="rand" id="rand" size="5" value="" />
	                        <img  src="${ctx }/sysAdmin/rand_randImage.action" onclick="changeValidateCode(this)" title="点击图片刷新验证码" class="rand" name="rand"/>
	                    </div>
	                    <div align="center" style="margin: 0px;height:20px;width:auto">
	                    	<span style="color:red"><s:property value="errors.errorInfo[0]"/></span>
	                    </div>
	                    <div class="cont-list cont-btn" style="margin-top: 5px">
	                        <button type="button" onclick="doLogin()">登录</button>
	                    </div>
                    </form>
                    
                </div>
            </div>
            <div class="content-btm">
                <p>北京和融通支付科技有限公司&emsp;|&emsp;地址：北京市海淀区中关村南大街乙12号天作国际B座23层-25层</p>
            </div>
        </div>
    </div>
</body>
</html>
