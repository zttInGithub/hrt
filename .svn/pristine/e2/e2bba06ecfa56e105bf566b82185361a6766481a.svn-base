<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
		<title>问题反馈</title>
		<script type="text/javascript" src="${ctx}/js/promoteinfo/rem.js"></script>
		<link href="${ctx}/css/promoteinfo/base.css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctx}/css/promoteinfo/common.css?v=1.0" />
		<link href="${ctx}/css/promoteinfo/promoteinfo.css?v=1.0" rel="stylesheet" />

	</head>

	<body>
		<div class="loading" v-show="loading">
			<div class="main">
				<img src="${ctx}/images/promoteinfo/loading4.gif" alt="">
			</div>
		</div>
		<!--弹框-->
		<!--orderid mid trantype amount trantime-->
		<div class="wrap">
			<div class="protitle" id="protitle">您好，我们发现您有一笔【微信/支付宝】交易未成功，交易金额为【0元】。请在以下列表中选择未成功的原因，谢谢您的反馈！</div>
     
			<ul>
				<li>提示商户收款存在异常</li>
				<li>提示谨防刷单、投资等</li>
				<li>无法选择花呗/信用卡支付</li>
				<li>提示不支持信用卡，请选择储蓄卡</li>
				<li>提示交易异常</li>
				<li>提示URL未注册</li>
				<li>密码输入错误/操作失误</li>
				<li>不想支付</li>
			</ul>
			<div class="other">其他:</div>
			<div><textarea placeholder="请输入…" id="other"></textarea></div>
			
  
			<div class="submitBtn" id="btnNext">确认提交</button>

			</div>

			<!--    错误提示框-->
			<div class="error_wrap error">
				<div class="errorwrap">
					<p style="font-size: 14px;padding-top: 10px;">提示</p>
					<img src="${ctx}/images/promoteinfo/com_line.png" />
					<p style="font-size: 13px;padding-top: 5px;padding-bottom: 15px;word-break: break-word;" class="errorp"></p>
				</div>
			</div>
		</div>
	</body>

	<form action="" id="paramsFrom" name="paramsFrom">
		<input id="orderid" type="hidden" name="orderid" value="${sessionScope.orderid}"/>
		<input id="mid" type="hidden" name="mid" value="${sessionScope.mid}"/>
		<input id="trantype" type="hidden" name="trantype" value="${sessionScope.trantype}"/>
		<input id="amount" type="hidden" name="amount" value="${sessionScope.amount}"/>
		<input id="trantime" type="hidden" name="trantime" value="${sessionScope.trantime}"/>
	</form>
	<script type="text/javascript" src="${ctx}/js/promoteinfo/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/promoteinfo/server.js"></script>
	<script type="text/javascript" src="${ctx}/js/promoteinfo/error.js?v=1.0"></script>
	<script type="text/javascript" src="${ctx}/js/promoteinfo/promoteinfo.js?v=1.2"></script>

</html>