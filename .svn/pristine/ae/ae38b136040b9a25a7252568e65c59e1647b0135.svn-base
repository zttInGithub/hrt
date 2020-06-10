<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<script type="text/javascript"
	src="${ctx}/jquery/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<head>
<meta charset="UTF-8">
<!-- 适配全部屏幕-->
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<title>微信账户绑定</title>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<style>
/*全局的css样式*/
html {
	font-size: 625%;
	height: 100%;
	width: 100%;
}

body {
	height: 100%;
	width: 100%;
	font-family: "\5FAE\8F6F\96C5\9ED1", Helvetica, sans-serif;
	font-size: .14rem;
	background: #fff;
	position: fixed;
}

.containerc {
	width: 100%;
	height: 100%;
}

.bindTitle {
	text-align: center;
    padding: .5rem 0 0;
}
.bindTitle img{
	width: 126px;
    height: 40px;
} 
.bindContent h4 {
	text-align: center;
	font-size: .12rem;
	letter-spacing: 1px;
	font-weight: 600;
	color: #3A99FC;
	margin: 0 auto;
	padding: .1rem 0;
	border-bottom: 1px solid #ddd;
}

.bindTable table {
	width: 98%;
	margin: .1rem auto;
}

.bindTable table tr {
	height: .45rem;
}
.col-xs-9 {
    border-bottom: 1px solid #ddd;
}
.bindTable table tr td {
	padding: 0;
	font-size: .14rem;
	vertical-align: bottom;
    padding-bottom: 5px;
}

.bindTable table tr td em {
	color: #3A99FC;
	padding-left: 2px;
	padding-right: 2px;
}

.bindTable table tr td input {
	width: 100%;
	border: none;
	text-indent: .1rem;
}

.bindLeft {
	text-align: justify;
	text-align-last: justify;
}

#bindCode {
	width: 50%;
	float: left;
}

#codeDiv {
	width: .8rem;
    float: right;
    font-style: oblique;
    margin-right: .05rem;
    height: .25rem;
    line-height: .25rem;
    border-radius: 3px;
    text-align: center;
    background: #3A99FC;
    position: absolute;
    right: 0;
    bottom: 5px;
}
#phone_bindCode {
	width: 50%;
	float: left;
}
#phone_codeDiv {
	width: .8rem;
    float: right;
    font-style: oblique;
    margin-right: .05rem;
    height: .25rem;
    line-height: .25rem;
    border-radius: 3px;
    text-align: center;
    background: #3A99FC;
    position: absolute;
    right: 0;
    bottom: 5px;
}

.bindBtn {
	display: block;
	width: 96%;
	height: .35rem;
	border: none;
	letter-spacing: 4px;
	border-radius: 4px;
	margin: .4rem auto;
	color: #fff;
	background-color: #3A99FC;
}

.containerc p {
	text-align: center;
}
/* 切换样式开始 */
ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

.tab {
	width: 90%;
    margin: auto;
}

.tab .tab_menu {
	height: 30px;
	width: 72%;
    margin: 20px auto;
}

.tab .tab_menu ul li {
	float: left;
	width: 50%;
	line-height: 30px;
	text-align: center;
	letter-spacing : 1px;
	margin : 0 auto; 
	padding : .1rem 0;
}

.tab .tab_menu ul li:last-child {
	border-right: none;
}

.tab .tab_box>div {
	display: none;
}
.tab .tab_box{
	margin-top: 35px;
}
.tab .tab_box>div:first-child {
	display: block;
}
.on{
	border-bottom:solid 2px #3A99FC !important;
	color:#3A99FC !important;
}
input {
	outline: none;
	-webkit-appearance: none;
	border-radius: 0;
	background:none;  
}
input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px white inset !important;
}
</style>

</head>
<body>
	<div class="containerc">
		<div class="bindTitle">
			<img src="${ctx}/images/hyblogo_1.1.png">
		</div>

		<div class="tab">
			<div class="tab_menu">
				<ul>
					<li class="on">手机号登录</li>
					<li>商户编号登录</li>
				</ul>
			</div>
			<div class="tab_box">
				<div class="bindContent">
					<div class="bindTable">
						<form id="Phone_loginFrom_no1" method="post">
							<table>
								<tr>
									<td class="col-xs-3 bindLeft"
										style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">手机号：</td>
									<td class="col-xs-9"><input id="phone_phone"
										name="loginName" type="text" placeholder="注册手机号" />
										<!-- placeholder="" --></td>
								</tr>
								<tr>
									<td class="col-xs-3 bindLeft">密&nbsp;&nbsp;&nbsp;码：</td>
									<td class="col-xs-9"><input type="password" id="phone_password"
										name="password"/></td>
								</tr>
								<tr>
									<td class="col-xs-3 bindLeft">验证码：</td>
									<td class="col-xs-9" style="position: relative;"><input type="text" id="phone_bindCode"
										name="rand" />
										<div id="phone_codeDiv"></div></td>
								</tr>
							</table>
							<input type="hidden" id="phone_random" name="random" value="" />
						</form>
					</div>
					<button class="bindBtn" onclick="saveInfo1();">登录</button>
				</div>
				<div class="bindContent">
					<div class="bindTable">
						<form id="Phone_loginFrom_no2" method="post">
							<table>
								<tr>
									<td class="col-xs-3 bindLeft"
										style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">商户编号：</td>
									<td class="col-xs-9"><input id="loginName"
										name="loginName" type="text" placeholder="APP-个人资料-我的MID" />
										<!-- placeholder="" --></td>
								</tr>
								<tr>
									<td class="col-xs-3 bindLeft">密&nbsp;&nbsp;&nbsp;码：</td>
									<td class="col-xs-9"><input type="password" id="password"
										name="password" placeholder="默认商户号后六位" /></td>
								</tr>
								<tr>
									<td class="col-xs-3 bindLeft">验证码：</td>
									<td class="col-xs-9"  style="position: relative;"><input type="text" id="bindCode"
										name="rand" />
										<div id="codeDiv"></div></td>
								</tr>
							</table>
							<input type="hidden" id="random" name="random" value="" />
						</form>
					</div>
					<button class="bindBtn" onclick="saveInfo2();">登录</button>
				</div>
			</div>
		</div>

		<!--  <p>版权所有<a href="javascript:;">62139188</a></p> -->
	</div>
	<script type="text/javascript">
		$(function() {
			$(".tab_menu ul li").click(function() {
				$(this).addClass("on").siblings().removeClass("on"); //切换选中的按钮高亮状态
				var index = $(this).index(); //获取被按下按钮的索引值，需要注意index是从0开始的
				$(".tab_box > div").eq(index).show().siblings().hide(); //在按钮选中时在下面显示相应的内容，同时隐藏不需要的框架内容
			});
		});
		/* 全局变量  */
		var urlStr = String(window.document.location.href); //获取请求url
		var code=getParam("code"); //code

		/* 获取 url 后参数 value */
		function getParam(paramKey) 
		{	
			var rs = new RegExp("(^|)" + paramKey + "=([^&]*)(&|$)", "gi").exec(urlStr), tmp;
			if (rs) 
			{
				return rs[2];
			}
			else 
			{
				return rs;
			}
		}
		$(function() {
    		document.getElementById("code").value=code;
    	
    	});
    //验证邮箱
    function CheckMail(mail) {
        var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if (filter.test(mail)){
            return;
        }
        else {
            return false;
        }
    }
    function saveInfo1(){
        // var email = document.getElementById('email').value;
        var loginName = $('#phone_phone').val();
         var password = $('#phone_password').val();
          var bindCode = $('#phone_bindCode').val();
         if(loginName==null||loginName==""||password==null||password==""){
             alert("手机号，密码不可为空");
             return;
         } 
         if(bindCode==null||bindCode==""){
          alert("验证码不可为空");
             return;
         }
           	var logonForm = document.getElementById("Phone_loginFrom_no1");
                //	var url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe2f3635bde10c3f9&redirect_uri=http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action?loginName="+$("#loginName").val()+"&password="+$("#password").val()+"&response_type=code&scope=snsapi_base&state="+$("#password").val()+"#wechat_redirect";
                 //https://merch.hrtpayment.com
                // var url="http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action";
                 //logonForm.action=url;
                 //提交
                 //logonForm.submit();
                //var reqData = {"phone": phone, "openid": openId, "vcode": verCode,"action":"bindPhone"};
 		
 		$.ajax({
 			type: 'POST',
 			//async: false, //是否异步
 			//url: 'http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action',
 			url: '${ctx}/phone/phoneWechatPublicAcc_loginPhone.action',
 			data: $('#Phone_loginFrom_no1').serialize(),
 			dataType: 'json',
 			success:function(data) {
 				if (data.success) {
 					 $('#Phone_loginFrom_no1 input').val('');
 					phone_getCode();
 					alert(data.msg);
 				}else{
 					if(data.msg=="success"){
 						location.href = "${pageContext.request.contextPath}/WeiXinLoginSuccess.jsp";
 					}else if(data.msg=="success2"){
 						location.href = "${pageContext.request.contextPath}/WeiXinLoginSuccess2.jsp";
 					}else if(data.msg=="bound"){
 						//location.href = "${pageContext.request.contextPath}/WeiXinBound.jsp";
 						alert("登录成功！");
 						WeixinJSBridge.call('closeWindow');
 					}
 				}
 			}
 				    
 		});
     }
    function saveInfo2(){
       // var email = document.getElementById('email').value;
       var loginName = $('#loginName').val();
        var password = $('#password').val();
         var bindCode = $('#bindCode').val();
        if(loginName==null||loginName==""||password==null||password==""){
            alert("账户，密码不可为空");
            return;
        } 
        if(bindCode==null||bindCode==""){
         alert("验证码不可为空");
            return;
        }
          	var logonForm = document.getElementById("Phone_loginFrom_no2");
               //	var url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe2f3635bde10c3f9&redirect_uri=http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action?loginName="+$("#loginName").val()+"&password="+$("#password").val()+"&response_type=code&scope=snsapi_base&state="+$("#password").val()+"#wechat_redirect";
                //https://merch.hrtpayment.com
               // var url="http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action";
                //logonForm.action=url;
                //提交
                //logonForm.submit();
               //var reqData = {"phone": phone, "openid": openId, "vcode": verCode,"action":"bindPhone"};
		
		$.ajax({
			type: 'POST',
			//async: false, //是否异步
			//url: 'http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action',
			url: '${ctx}/phone/phoneWechatPublicAcc_login.action',
			data: $('#Phone_loginFrom_no2').serialize(),
			dataType: 'json',
			success:function(data) {
				if (data.success) {
					 $('#Phone_loginFrom_no2 input').val('');
					 getCode();
					alert(data.msg);
				}else{
					if(data.msg=="success"){
						location.href = "${pageContext.request.contextPath}/WeiXinLoginSuccess.jsp";
					}else if(data.msg=="success2"){
						location.href = "${pageContext.request.contextPath}/WeiXinLoginSuccess2.jsp";
					}else if(data.msg=="bound"){
						//location.href = "${pageContext.request.contextPath}/WeiXinBound.jsp";
						alert("登录成功！");
						WeixinJSBridge.call('closeWindow');
						
					}
				}
			}
				    
		});
    }


    //获取四位随机验证码
    var phone_codeDiv = document.getElementById("phone_codeDiv");
    var codeDiv = document.getElementById("codeDiv");

    
    var area = "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "0123456789";

    function phone_getCode() {
        var str = "";
        while (str.length < 4) {
            var ran = Math.round(Math.random() * 61);
            var temp = area[ran];
            if (str.indexOf(temp) > -1) {
                continue;
            }
            str += temp;
        }
  	
        phone_codeDiv.innerHTML = str;
        $('#phone_random').val(str);
    }
    function getCode() {
        var str = "";
        while (str.length < 4) {
            var ran = Math.round(Math.random() * 61);
            var temp = area[ran];
            if (str.indexOf(temp) > -1) {
                continue;
            }
            str += temp;
        }
  	
        codeDiv.innerHTML = str;
        $('#random').val(str);
    }
    phone_getCode();
    phone_codeDiv.onclick = phone_getCode;
    
    getCode();
    codeDiv.onclick = getCode;
    
    
</script>
</body>
</html>