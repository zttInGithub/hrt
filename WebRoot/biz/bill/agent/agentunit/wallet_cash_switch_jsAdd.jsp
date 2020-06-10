<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		$('#rebateType_wallet_cash_switch_jsAdd').combogrid({
			url : '${ctx}/sysAdmin/checkWalletCashSwitch_listAvailableRebateType.action',
			idField:'VALUEINTEGER',
			textField:'NAME',
			value:-1,
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]]
		});
		$('#unno').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listUnits.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'UNNO',title:'机构号',width:70},
				{field:'UN_NAME',title:'机构名称',width:70}
			]]
		});
	});
</script>
</head>
<form id="wallet_cash_switch_js_singleFrom" style="padding-left:2%;padding-top:1%" method="post">
	<fieldset style="width: 300px;">
		<!-- <legend>新增钱包开通</legend> -->
		<table class="table">
			<tr>	
	    		<th>机构：</th>
	    		<!-- <td><select id="unno" name="unno" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:145px;"></select></td> -->
	    		<td>
	    			<input id="unno" name="unno" style="width:145px;" maxlength="100"/>
	    		</td>
	    	</tr>
			<tr>	
	    		<th>活动类型：</th>
	    		<td>
						<select id="rebateType_wallet_cash_switch_jsAdd" name="rebateType" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:135px;"></select>
	    			<%--<input id="rebateType" type="text" name="rebateType" style="width:145px;" data-options="required:true,validType:'spaceValidator'" maxlength="100"/>--%>
	    		</td>
	    	</tr>
		</table>
	</fieldset>
</form>
