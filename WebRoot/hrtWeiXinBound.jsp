<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
    <!-- 适配全部屏幕-->
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>微信账户解绑</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <style>
        /*全局的css样式*/
        html{
            font-size: 625%;
            height: 100%;
            width: 100%;
        }
        body{
            height: 100%;
            width: 100%;
            font-family: "\5FAE\8F6F\96C5\9ED1", Helvetica, sans-serif;
            font-size: .16rem;
            background:#fff ;
            position: fixed;
        }
        .containerc{
            width: 100%;
            height: 100%;
        }
        .bindTitle {
			text-align: center;
		    padding: .5rem 0 0;
		}
        .bindTitle h3{
            color: #fff;
            width: 40%;
            font-size: .2rem;
            margin: 0 auto;
            padding: .1rem 0;
        }
        .bindTitle div{
            width: .6rem;
            height:.2rem;
            position: absolute;
            top: .1rem;
            right: .05rem;
        }
        .bindTitle img {
		    width: 33.8%;
    		height: 6%;
		}
        .bindContent h4{
            text-align: center;
		    font-size: .14rem;
		    margin: .3rem auto;
		    padding: .1rem 0;
        }
        .bindTable table {
            width: 90%;
    		margin: auto;
        }
        .bindTable table tr{
            height: .45rem;
        }
        .bindTable table tr td{
            padding: 0;
            vertical-align: bottom;
   	 		padding-bottom: 5px;
        }
        .bindTable table tr td em{
            color: #ff5614;
            padding-left:2px;
            padding-right: 2px;
        }
        .bindTable table tr td input{
            width: 100%;
            height: 100%;
            border: none;
            text-indent: .1rem;
        }
        .bindLeft{
            text-align:justify;
            text-align-last:justify;
        }
        #bindCode{
            width: 50%;
            height: .35rem;
            float: left;
        }

        .bindBtn{
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
        .containerc p{
            text-align: center;
        }
        .col-xs-9 {
		    border-bottom: 1px solid #ddd;
		}
		.bindContent{
		    width: 80%;
    		margin: auto;
    	}
    </style>


</head>
<body style="text-align: center">
<div class="containerc">
    <div class="bindTitle">
       <img src="${ctx}/images/hyblogo_1.1.png">
    </div>
    <div class="bindContent">
        <h4>微商户客户</h4>
        <div class="bindTable">
        <form id="boundFrom" method="post">
            <table>
                <tr>
                    <td class="col-xs-3 bindLeft" style="white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">商户编号：</td>
                    <td class="col-xs-9">
                        <input id="loginName" name="loginName" type="text"  placeholder="${sessionScope.mid}" readonly="true" value="${sessionScope.mid}"/>
                    </td>
                </tr>
                <tr>
                    <td class="col-xs-3 bindLeft">用户名：</td>
                    <td class="col-xs-9">
                        <input type="text" placeholder="${sessionScope.rname}" readonly="true"/>
                    </td>
                </tr>
            </table>
           <input type="text" id="code" name="code" style="display: none"/>
            </form>
        </div>
        <button class="bindBtn" onclick="checkLogin();">解绑</button>
    </div>
    <!-- <p>版权所有<a href="javascript:;">62139188</a></p> -->
</div>

    <script type="text/javascript">

		
        function checkLogin(){
             	var mid='${sessionScope.mid}'+"";
                //logonForm.action="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc427f61bd6079427&redirect_uri=http://test.hybunion.com/HrtApp/phone/phoneWechatPublicAcc_login.action?loginName="+$("#loginName").val()+"&password="+$("#password").val()+"&response_type=code&scope=snsapi_base&state="+$("#password").val()+"#wechat_redirect"
               	var boundFrom = document.getElementById("boundFrom");
               	var url="${ctx}/phone/phoneWechatPublicAcc_Unbundling2.action?mid="+mid;
                //https://merch.hrtpayment.com
                boundFrom.action=url;
                //提交
                boundFrom.submit();
              
        }  
    

			
    </script>
</body>
 
</html>