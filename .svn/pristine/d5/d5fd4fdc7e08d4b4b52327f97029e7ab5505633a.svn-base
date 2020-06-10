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
	$.extend($.fn.validatebox.defaults.rules, {
		barValidator:{
			validator : function(value) { 
	            return /^[^\-|^\s|^\.]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格、横杠和点' 
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
<div class="easyui-layout" data-options="fit:true, border:false">
   	<form id="sysAdmin_10931_addForm" style="padding-left:20px;" method="post">
	   		<table class="table1">
	   			<tr>
	   				<th>归属机构号：</th>
	   				<td><input type="text" name="unNO" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" maxlength="6"></td>
	   				<th>名称：</th>
	   				<td><input type="text" name="unitName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" maxlength="50"></td>
	   			</tr>
	   			<tr>
	   				<th>sn号开头：</th>
	   				<td><input type="text" name="snStart" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" maxlength="50"></td>
	   				<th>sn号结尾：</th>
	   				<td><input type="text" name="snEnd" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"></td>
	   			</tr>
	   			<tr>
	   				<th>出库数量：</th>
	   				<td><input type="text" name="deliverNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="20"></td>
	   				<th>机型：</th>
	   				<td><input type="text" name="machineModel" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="20"></td>
	   			</tr>
	   			<tr>
	   				<th>日期：</th>
	   				<td><input type="text" name="maintainDate" style="width: 150px;" class="easyui-datebox" data-options="editable:false,required:true" maxlength="30"></td>
	   			</tr>
	   		</table>
	</form>
</div>
