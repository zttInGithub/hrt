<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	var bautdid=<%=request.getParameter("bautdid")%>;
	$(function(){
		$.ajax({
			url:'${ctx}/sysAdmin/agentUnitTask_queryAgentUnitTaskDetail.action',
			dataType:"json",  
			data:{bautdid:bautdid,taskType:3},
			type:"post",
			success:function(data) {
				var json=eval(data);
				console.log(json)
				if (json!="") { 
		    		$("#contact").val(json[0].contact); 
					$("#contactTel").val(json[0].contactTel);
					$("#businessContacts").val(json[0].businessContacts);
					$("#businessContactsPhone").val(json[0].businessContactsPhone);
					$("#businessContactsMail").val(json[0].businessContactsMail);
					$("#riskContact").val(json[0].riskContact);
					$("#riskContactPhone").val(json[0].riskContactPhone);
					$("#riskContactMail").val(json[0].riskContactMail);
				} 
			} 
		});
	});
</script>
</head>
		<form id="sysAdmin_00339_from" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 850px;">
				<legend>联系人信息</legend>
				<table class="table">
					<tr>	
			    		<th>负责人：</th>
			    		<td><input type="text" name="contact" id="contact" style="width:130px;"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    		<th>负责人联系电话：</th>
			    		<td><input type="text" name="contactTel" id="contactTel" style="width:130px;" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<th>业务联系人：</th>
			    		<td><input type="text" name="businessContacts" id="businessContacts" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>业务联系人电话：</th>
			    		<td><input type="text" name="businessContactsPhone" id="businessContactsPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<th>业务联系人邮箱：</th>
			    		<td><input type="text" name="businessContactsMail" id="businessContactsMail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			    		<th>风控联系人：</th>
			    		<td><input type="text" name="riskContact" id="riskContact" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<th>风控联系人电话：</th>
			    		<td><input type="text" name="riskContactPhone" id="riskContactPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>风控联系人邮箱：</th>
			    		<td><input type="text" name="riskContactMail" id="riskContactMail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
				</table>
			</fieldset>
			<input type="hidden" name="taskType" value="3">
			<input type="hidden" name="bautdid" id="bautdid">
		</form>
