<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		$.extend($.fn.validatebox.defaults.rules,{
			rebateTypeValidator:{
				validator : function(value) {
					if(value<20 && value!=0){
						return false;
					}else{
						return true;
					}
				},
				message : '活动输入错误(活动需为0或大于等于20)'
			}
		});

		$('#rebateType_wallet_cash_switch_add').combogrid({
			url : '${ctx}/sysAdmin/checkWalletCashSwitch_listAvailableRebateType.action',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			value:-1,
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]],
			onChange: function (newValue, oldValue) {
				getSubUnno();
			}
		});
	});
	function getSubUnno() {
		// console.log($('#rebateType_wallet_cash_switch_add').combobox('getValue'));
		var rebateTypeValue=$('#rebateType_wallet_cash_switch_add').combobox('getValue');
		if(rebateTypeValue==-1){
			$.messager.alert('提示', '请先选择活动类型');
		}else{
			$('#unno_wallet_cash_switch_add').combogrid({
				url : '${ctx}/sysAdmin/checkWalletCashSwitch_listSubUnno.action?rebateType='+rebateTypeValue,
				idField:'UNNO',
				textField:'UN_NAME',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'UNNO',title:'机构号',width:70},
					{field:'UN_NAME',title:'机构名称',width:70}
				]]
			});
		}
	}
</script>
</head>
<form id="wallet_cash_switch_add_from" style="padding-left:2%;padding-top:1%" method="post">
	<fieldset style="width: 300px;">
		<legend>开通/变更</legend>
		<table class="table">
			<tr>
				<th>活动类型：</th>
				<td>
					<select id="rebateType_wallet_cash_switch_add" name="rebateType" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:135px;"></select>
					<%--<input name="rebateType" style="width: 135px;" class="easyui-validatebox" data-options="required:true,validType:'rebateTypeValidator'" maxlength="2"/>--%>
				</td>
			</tr>
			<%-- class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'"  --%>
			<tr>	
	    		<th>机构号：</th>
	    		<td><select id="unno_wallet_cash_switch_add" name="unno" style="width:135px;" editable="false" onclick="getSubUnno()"></select></td>
	    	</tr>
	    	<tr>
	    		<th>钱包状态：</th>
	    		<td>
						<select id="walletStatus_wallet_cash_switch_add" name="walletStatus" style="width: 135px;">
							<option value="0">关</option>
							<option value="1">开</option>
						</select>
					</td>
	    	</tr>
		</table>
	</fieldset>
</form>
