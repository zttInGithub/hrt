<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function(){
		$('#storageMachineModel_10491').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listMachineModel.action',
			idField:'MACHINEMODEL',
			textField:'MACHINEMODEL',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'MACHINEMODEL',title:'机型名称',width:150},
			]]
		});
	});
	
	$(function(){
		$('#storageBrandName_10491').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=2',
			idField:'VALUESTRING',
			textField:'VALUESTRING',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'VALUESTRING',title:'品牌名称',width:150},
			]]
		});
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
<style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
<form id="sysAdmin_10491_addForm" style="padding-left:20px;" method="post">
   	<fieldset style="width: 800px;">
		<legend>借样单信息</legend>
   		<table class="table1">
   			<tr>
   				<th>部门和职务：</th>
   				<td><input type="text" name="department" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"><font color="red">&nbsp;*</font></td>
   				<th>借机人：</th>
   				<td><input type="text" name="lender" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"><font color="red">&nbsp;*</font></td>
   			</tr>
   			<tr>
   				<th>品牌：</th>
   				<td><input type="text" name="storageBrandName" id="storageBrandName_10491" style="width: 155px;" class="easyui-validatebox" data-options="validType:'spaceValidator',editable:false" maxlength="50"><font color="red">&nbsp;*</font></td>
   				<th>机型：</th>
   				<td><input type="text" name="storageMachineModel" id="storageMachineModel_10491" style="width: 155px;" class="easyui-validatebox" data-options="validType:'spaceValidator',editable:false" maxlength="10"><font color="red">&nbsp;*</font></td>
   			</tr>
   			<tr>
   				<th>借用事由：</th>
   				<td><input type="text" name="storageRemark" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"><font color="red">&nbsp;*</font></td>
   				<th>数量：</th>
   				<td><input type="text" name="storageMachineNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="10"><font color="red">&nbsp;*</font></td>
   			</tr>
   			<tr>
   				<th>出借日期：</th>
   				<td><input name="lendDate" class="easyui-datebox" data-options="editable:false,required:true" style="width: 155px;"/><font color="red">&nbsp;*</font></td>
   			</tr>
   			<input type="hidden" name="storageType" value="1" />
   		</table>
	</fieldset>
</form>