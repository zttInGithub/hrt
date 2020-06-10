<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
%>
<script type="text/javascript">
	$(function(){
		var bmtkid1=$("#bmtkid1").val(); 
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail4.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmtkid:bmtkid1},
			success:function(data) {
    			var json=eval(data);
	    			if (json!="") {  
	    			$("#singleLimit").val(json[0].singleLimit);
	    			$("#dailyLimit").val(json[0].dailyLimit);
	    			var authorized=json[0].isAuthorized;
	    			if(authorized==0)
	    				$("#isAuthorized").val("是");
    				}
    				if(authorized==1){
    					$("#isAuthorized").val("否");
        				}
    	} 
	});
});
</script>
 
<form id="sysAdmin_10532_addForm" method="post">
	<fieldset>
		<legend>工单信息</legend>
		<table class="table">
			<tr>
	    		<th>是否开通预授权：</th>
	    		<td>
	    			<input type="text" name="isAuthorized" id="isAuthorized" readonly="readonly" value=""/>
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>单笔限额：</th>
	    		<td><input type="text" name="singleLimit" id="singleLimit" style="width:200px;" readonly="readonly"   class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
	    	</tr>
	    	<tr>
	    		<th>单日限额：</th>
	    		<td><input type="text" name="dailyLimit" id="dailyLimit" style="width:200px;" readonly="readonly"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
			</tr>
			<input type="hidden" name="bmtkid" id="bmtkid1" value="<%=bmtkid %>">   
		</table>
	</fieldset>
</form>  

