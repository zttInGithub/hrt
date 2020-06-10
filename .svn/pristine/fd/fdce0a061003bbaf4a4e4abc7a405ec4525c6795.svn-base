<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
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
   	<form id="sysAdmin_10492_addForm" style="padding-left:20px;" method="post" enctype="multipart/form-data">
   		<input type="hidden" name="file10492Name" id="file10492_Name"/>
	   	<fieldset style="width: 800px;">
			<legend>借样单信息</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>部门职务：</th>
	   				<td><input type="text" name="department" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50" readonly="readonly"><font color="red">&nbsp;*</font></td>
	   				<th>借机人：</th>
	   				<td><input type="text" name="lender" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50" readonly="readonly"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>品牌：</th>
	   				<td><input type="text" name="storageBrandName" id="storageBrandName_10492" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="50" readonly="readonly"><font color="red">&nbsp;*</font></td>
	   				<th>机型：</th>
	   				<td><input type="text" name="storageMachineModel" id="storageMachineModel_10492" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="10" readonly="readonly"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>借用事由：</th>
	   				<td><input type="text" name="storageRemark" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50" readonly="readonly"><font color="red">&nbsp;*</font></td>
	   				<th>已借出数量：</th>
	   				<td><input type="text" name="loadStorageNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="10" readonly="readonly"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>借用机构号：</th>
	   				<td><input type="text" name="storageUnno" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="10"><font color="red">&nbsp;*</font></td>
	   				<th>&nbsp;&nbsp;明细文件：</th>
                	<td><input type="file" name="upload" id="uploads10492"></input></td>
	   			</tr>
	   			<input type="hidden" name="psid"/>
	   			<input type="hidden" name="storageID"/>
	   			<input type="hidden" name="storageMachineNum"/>
	   		</table>
		</fieldset>
	</form>
