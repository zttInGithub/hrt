<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var waitAmt = <%=request.getParameter("waitAmt")%>;
	
	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
	$(function(){
		$('#payType10418').combogrid({
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
	   	<form id="sysAdmin_10418_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>采购付款单</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>总金额</th>
		   				<td><input type="text" name="orderAmt" id="orderAmt_10418" style="width: 150px;" class="easyui-numberbox" readonly="readonly"></td>
		   				<th>已付款金额</th>
		   				<td><input type="text" name="orderpayAmt" id="orderpayAmt_10418" style="width: 150px;" class="easyui-numberbox" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>类型：</th>
		   				<td><select name="accountType" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
		   					<option value="1">付款</option>
		    				<option value="2">退款</option>
		   				</select><font color="red">&nbsp;*</font></td>
	   					<th>付款方式：</th>
	   					<td><select id="payType10418" name="payType"
						class="easyui-combogrid"
						data-options="editable:false,required:true" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   				</tr>
		   			<tr>
	   					<th>金额：</th>
	   					<td><input type="text" name="accountAmt" id="accountAmt_10418" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2'" maxlength="16"><font color="red">&nbsp;*</font></td>
	   				</tr>
	   				<tr>
	   					<th>备注：</th>
		   				<td colspan="3">
				    		<textarea rows="6" cols="100" style="resize:none;" name="accountRemark" maxlength="100"></textarea>
				    	</td>
	   				</tr>
		   		</table>
			</fieldset>
		<input type="hidden" name="poid" />
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10418_datagrid"></table>
	</div> 
</div>
		