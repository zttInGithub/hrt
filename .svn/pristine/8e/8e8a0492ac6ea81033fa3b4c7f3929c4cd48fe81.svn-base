<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 新增来款 -->
<script type="text/javascript">
	$(function(){
		$('#arraiveWay_10509').combogrid({
			url : '${ctx}/sysAdmin/purchaseRecord_listArraiveWay.action',
			idField:'ARRAIVEWAY',
			textField:'ARRAIVEWAY',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'ARRAIVEWAY',title:'来款方式',width:150},
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
<div class="easyui-layout" data-options="fit:true, border:false">
   	<form id="sysAdmin_10509_addForm" style="padding-left:20px;" method="post">
	   	<fieldset style="width: 800px;">
			<legend>来款信息</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>来款金额：</th>
	   				<td><input type="text" name="arraiveAmt" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="200"><font color="red">&nbsp;*</font></td>
	   				<th>来款人姓名：</th>
	   				<td><input type="text" name="arraiverName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" maxlength="200"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>来款卡号：</th>
	   				<td><input type="text" name="arraiveCard" id="arraiveCard" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"><font color="red">&nbsp;*</font></td>
	   				<th>来款日期：</th>
	   				<td><input type="text" name="arraiveDate" style="width: 150px;" class="easyui-datebox" data-options="validType:'spaceValidator',required:true" maxlength="20"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>来款方式：</th>
	   				<td><select id="arraiveWay_10509" name="ARRAIVEWAY"
						class="easyui-combogrid"
						data-options="editable:false,required:true" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>备注：</th>
	   				<td colspan="3">
			    		<textarea rows="6" cols="110" style="resize:none;" name="arraiveRemarks" maxlength="200"></textarea>
			    	</td>
	   			</tr>
	   		</table>
		</fieldset>
		<input type="hidden" name="prid" id="prid"/>
	</form>
</div>
