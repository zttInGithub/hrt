<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
%>
<script type="text/javascript">
 $(function(){
	var bmtkid1 = $("#bmtkid_10537").val(); 
	$.ajax({
		 url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail4.action',
		dataType:"json",  
		type:"post",
		data:{bmtkid:bmtkid1},
		 success:function(data) {
			var json=eval(data);
    			if (json!="") {  
	    			$("#mtype_10537").val(json[0].mtype);
					$("#scanSingleLimit_10537").val(json[0].singleLimit);
	    			$("#scanDailyLimit_10537").val(json[0].dailyLimit);
				} 
		}
	}); 
 	
	$.extend($.fn.validatebox.defaults.rules, {
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    	}
    }); 
});
    	
</script>
<form id="sysAdmin_merchant10537_editForm" method="post">
	<input type="hidden" name="mtype" id="mtype_10537"> 
	<input type="hidden" name="bmtkid" id="bmtkid_10537" value="<%=bmtkid %>">
	<table class="table">
		<tr>
			<th>商户号：</th>
			<td>
				<input type="text" name="mid" id="mid_10537" readonly="readonly" style="width:100px;" class="easyui-validatebox"  data-options="editable:true,required:true" maxlength="25"  /> 
			</td>
		</tr>
		<tr>
			<th>单日限额：</th>
			<td>
				<input type="text" name="dailyLimit" id="scanDailyLimit_10537" style="width:100px;" class="easyui-validatebox"  data-options="validType:'intOrFloat',editable:false,required:true" maxlength="25"  /> 元
			</td>
		</tr>
		<tr>
		   	<th>单笔限额：</th>
		   	<td>
				<input type="text" name="singleLimit" id="scanSingleLimit_10537" style="width:100px;" class="easyui-validatebox"  data-options="validType:'intOrFloat',editable:false,required:true" maxlength="25"  /> 元
		   	</td>
		</tr>
	</table>
</form>  

