<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/js/jquery.qrcode.min.js"></script>    
<script type="text/javascript">

	$(function() {

	var url = $('#url').val();
	url=url.replace("sign", "&sign");
	
		$("#ss").qrcode(url);
		}
	);
</script>

	<div id="ss" align="center">
	<br/><br/><br/>
	</div>  
	<input type= "hidden" id="url" name="url"  value="${param.url}"/>  

