<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		$('#rebateType_20288_1_1').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listRebateRate.action?status=plus',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]],
			onSelect: function (rowIndex, rowData){
					//debugger;
					if(rowData.SUBTYPE==null||rowData.SUBTYPE!=2||rowData.SUBTYPE==''){
						$('#tr1').hide();
						$('#tr2').hide();
					}else{
						$('#tr1').show();
						$('#tr2').show();
					}
				}
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
</head>
<form id="sysAdmin_20288_1_1_from" style="padding-left:2%;padding-top:1%" method="post">
	<fieldset style="width: 480px; height: 600px;">
		<legend>活动类型成本</legend>
		<table class="table">
			<tr>	
	    		<th>活动类型</th>
	    		<td><select id="rebateType_20288_1_1" name="rebateType" class="easyui-combogrid" data-options="required:true,editable:false,validType:'spaceValidator'" style="width:135px;"></select></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>扫码1000以上（终端0.38）费率：</th>
	    		<td><input type="text" id="creditBankRate_20288_1_1" name="creditBankRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr>
	    		<th>扫码1000以上（终端0.38）转账费：</th>
	    		<td><input type="text" id="cashRate_20288_1_1" name=cashRate style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>扫码1000以上（终端0.45）费率：</th>
	    		<td><input type="text" id="ruleThreshold_20288_1_1" name="ruleThreshold" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr>
	    		<th>扫码1000以上（终端0.45）转账费：</th>
	    		<td><input type="text" id="startAmount_20288_1_1" name="startAmount" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>扫码1000以下（终端0.38）费率：</th>
	    		<td><input type="text" id="scanRate_20288_1_1" name="scanRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr>
	    		<th>扫码1000以下（终端0.38）转账费：</th>
	    		<td><input type="text" id="cashAmt1_20288_1_1" name="cashAmt1" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>扫码1000以下（终端0.45）费率：</th>
	    		<td><input type="text" id="scanRate1_20288_1_1" name="scanRate1" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr>
	    		<th>扫码1000以下（终端0.45）转账费：</th>
	    		<td><input type="text" id="profitPercent1_20288_1_1" name="profitPercent1" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>银联二维码费率：</th>
	    		<td><input type="text" id="scanRate2_20288_1_1" name="scanRate2" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr>
	    		<th>银联二维码转账费：</th>
	    		<td><input type="text" id="cashAmt2_20288_1_1" name="cashAmt2" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	
	    	
	    	<tr>
	    		<th>刷卡费率：</th>
	    		<td><input type="text" id="subRate_20288_1_1" name="subRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr>
	    		<th>刷卡转账费：</th>
	    		<td><input type="text" id="cashAmt_20288_1_1" name="cashAmt" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>云闪付：</th>
	    		<td><input type="text" id="cloudQuickRate_20288_1_1" name="cloudQuickRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
	    	</tr>
	    	<tr id="tr1">
				<th>花呗费率：</th>
				<td><input type="text" id="huabeiRate_20288_1_1" name="huabeiRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:false,validType:'spaceValidator'"/>%</td>
			</tr>
			<tr id='tr2'>
				<th>花呗转账费：</th>
				<td><input type="text" id="huabeiFee_20288_1_1" name="huabeiFee" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:false,validType:'spaceValidator'"/></td>
			</tr> 
			<!-- <tr>
				<th>扫码费率：</th>
				<td><input type="text" id="scanRate_20288_1_1" name="scanRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>%</td>
			</tr>
			<tr>
				<th>扫码转账费：</th>
				<td><input type="text" id="cashAmt1_20288_1_1" name="cashAmt1" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr> -->
		</table>
	</fieldset>
</form>
