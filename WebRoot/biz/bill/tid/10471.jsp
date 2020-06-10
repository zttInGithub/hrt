<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
	$(function(){
		$('#payType10471').combogrid({
			url : '${ctx}/sysAdmin/purchaseRecord_listArraiveWay.action',
			idField:'ARRAIVEWAY',
			textField:'ARRAIVEWAY',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'ARRAIVEWAY',title:'付款方式',width:150},
			]]
		});
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
	<div data-options="region:'north',border:false" style="height:251px;">
	   	<form id="sysAdmin_10471_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>修改采购付款单</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>金额：</th>
	   					<td><input type="text" name="accountAmt" id="accountAmt_10471" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2',min:0.01" maxlength="16"><font color="red">&nbsp;*</font></td>
	   					<th>付款方式：</th>
	   					<td><select id="payType10471" name="payType"
						class="easyui-combogrid"
						data-options="editable:false,required:true" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   				</tr>
		   			<tr>
	   					<th>备注：</th>
		   				<td colspan="3">
				    		<textarea rows="6" cols="100" style="resize:none;" name="accountRemark" maxlength="100"></textarea>
				    	</td>
	   				</tr>
		   		</table>
			</fieldset>
		<input type="hidden" name="paid" />
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10471_datagrid"></table>
	</div> 
</div>
		