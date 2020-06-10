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
	<div data-options="region:'north',border:false" style="height:451px;">
	   	<form id="sysAdmin_10472_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>采购单发票核销</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>类型：</th>
		   				<td><select name="invoiceType" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
		   					<option value="1">付款核销</option>
		    				<option value="2">退款核销</option>
		   				</select><font color="red">&nbsp;*</font></td>
	   					<th>核销金额：</th>
		   				<td><input type="text" name="invoiceAmt" id="invoiceAmt_10472" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2'" maxlength="16"><font color="red">&nbsp;*</font></td>
		   			</tr>
		   			<tr>
	   					<th>发票号码：</th>
		   				<td><input type="text" name="invoiceId" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"><font color="red">&nbsp;*</font></td>
		   				<th>数量：</th>
		   				<td><input type="text" name="invoiceNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="6"><font color="red">&nbsp;*</font></td>
		   				</tr>
	   				<tr>
		   				<th>未税金额：</th>
		   				<td><input type="text" name="noTax" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2'" maxlength="16"><font color="red">&nbsp;*</font></td>
		   				<th>含税金额：</th>
	   					<td><input type="text" name="haveTax" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2'" maxlength="16"><font color="red">&nbsp;*</font></td>
	   				</tr>
		   			<tr>
		   				<th>税金：</th>
		   				<td><input type="text" name="tax" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2'" maxlength="16"><font color="red">&nbsp;*</font></td>
		   			</tr>
		   			<tr>
	   					<th>发票项目：</th>
	   					<td colspan="3">
				    		<textarea rows="6" cols="100" style="resize:none;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" name="text" maxlength="100"></textarea><font color="red">&nbsp;*</font>
				    	</td>
	   				</tr>
	   				<tr>
	   					<th>备注：</th>
		   				<td colspan="3">
				    		<textarea rows="6" cols="100" style="resize:none;" name="invoiceRemark" maxlength="100"></textarea>
				    	</td>
	   				</tr>
		   		</table>
			</fieldset>
		<input type="hidden" name="poid" />
		<input type="hidden" name="invoiceOrderID" id="invoiceOrderID_10472" />
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10472_datagrid"></table>
	</div> 
</div>
		