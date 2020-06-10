<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_serachAgentInfoDetailed2.action',
			dataType:"json",  
			type:"post",
			success:function(data) {
				var json=eval(data);
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
	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
				return util(value); 
	      }, 
	      message : '图片格式不正确' 
		}
	});
	
	function util(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".jpg" && ename!=".png" && ename!=".gif" && ename!=".jpeg" ){
				return false;
			}else{ 
				return true;
			}
		}
	}

	$.extend($.fn.validatebox.defaults.rules,{
		phoneValidator:{
			validator : function(value) { 
				return /\d+/.test(value); 
	      }, 
	      message : '电话号码格式不正确' 
		}
	});

 	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
</script>
</head>
		<form id="sysAdmin_00333_from" style="padding-left:2%;padding-top:1%" method="post">
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
		</form>
