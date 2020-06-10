<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function logoutFun() {
		$.post("${ctx}/sysAdmin/user_logout.action", function(data){
			var url = getProjectLocation();
			window.location.href = url;
		});
	}
</script>
<style type="text/css">
	.north{
		width: 100%; 
		height: 100%;
		background: url(${ctx }/images/logBlue2.png) repeat-x;
	}
	.north_quit{
		width:100px;
		height:30px;
		float: right;
		color: white;
		margin-right:10px;
		margin-top:40px;
		cursor:pointer;	
	}
	.north_quit_img{
		float: left;
	}
	.north_quit_text{
		line-height: 40px;
		color: black;
	}
	.north_logo{
		width: 130px;
		height: 80px;
		margin-left: 25px;
		float: left;
	}
	.north_compart{
		float: left;
		height: 60px;
		margin-top: 10px;
		border-left:#000 1px solid;
	}
	.north_title{
		float: left;
		font-size: 24px;
		margin-left: 20px;
		margin-top: 15px;
	}
</style>
 
<div class="north">
	<div class="north_logo">
		<img src="images/logo1.png" width="120" height="60" style="margin-top: 10px;"/>
	</div>
	<div class="north_compart"></div>
	<div class="north_title">${title }<p style="font-size: 16px;margin: 0px;">${titleEnglish }</p></div>
	<div class="north_quit" onclick="logoutFun();">
		<div class="north_quit_img"><img src="images/quit.png"  title ="退出" /></div>
		<div class="north_quit_text">退出</div>
	</div>
</div>
