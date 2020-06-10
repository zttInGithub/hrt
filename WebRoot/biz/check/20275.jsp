<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 模版添加 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    	}
	});
	$(function(){
		$('#unno').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	});
	$.extend($.fn.validatebox.defaults.rules,{
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
	
	$.extend($.fn.validatebox.defaults.rules,{
		payBankIdValidator:{
			validator : function(value) {
	            return /^(?:0|[1-9][0-9]?|100)$/i.test(value); 
	        }, 
	        message : '利润百分比在0到100之间' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		rateValidator:{
			validator : function(value) {
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '费率格式不正确！' 
		}
	});
</script>
<form id="profittradit_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
	  <table class="table">
		 <tr>
			<th>机构编号：</th>
	   		<td style="width:250px;">
	   			<select name="unno" id="unno" class="easyui-combogrid" data-options="required:true" style="width:205px;"></select><font color="red">&nbsp;*</font>
	   		</td>
	   		<th>总利润比例：</th>
		   	<td><input type="text" name="profitPercent" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'payBankIdValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
		 </tr>
	  </table>
	</fieldset>
	<fieldset>
		<legend>标准类成本</legend>
		<table class="table">
	   		<tr>
	   			<th>借记卡费率：</th>
		   		<td><input type="text" name="costRate" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>		   	
		   		<th style="width:310px;" >借卡大额手续费：</th>
		   			<td style="width:150px;"><input type="text" name="feeAmt" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()"/><font color="red">&nbsp;*</font></td>
		   		<th style="width:310px;" >借卡大额封顶值：</th>
	   				<td style="width:150px;"><input type="text" name="dealAmt" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()" value="4146" readonly="readonly"/><font color="red">&nbsp;*(默认4146)</font></td>
		   		<th>贷记卡费率：</th>
		   		<td><input type="text" name="creditBankRate" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
	   		</tr>  		
	   	</table>
	</fieldset>
	<fieldset>
		<legend>优惠类成本</legend>
		<table class="table">
	   		<tr>
	   			<th>借记卡费率：</th>
		   		<td><input type="text" name="costRate1" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>		   	
		   		<th style="width:310px;" >借卡大额手续费：</th>
		   			<td style="width:150px;"><input type="text" name="feeAmt1" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()"/><font color="red">&nbsp;*</font></td>
		   		<th style="width:310px;" >借卡大额封顶值：</th>
	   				<td style="width:150px;"><input type="text" name="dealAmt1" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()" value="4146" readonly="readonly"/><font color="red">&nbsp;*(默认4146)</font></td>
		   		<th>贷记卡费率：</th>
		   		<td><input type="text" name="creditBankRate1" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
	   		</tr>  	
	   	</table>
	</fieldset>
	<fieldset>
		<legend>减免类成本</legend>
		<table class="table">
	   		<tr>
	   			<th>借记卡费率：</th>
		   		<td><input type="text" name="costRate2" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>		   	
		   		<th style="width:310px;" >借卡大额手续费：</th>
		   			<td style="width:150px;"><input type="text" name="feeAmt2" style="width:40px;" class="easyui-validatebox" onblur="dealAmtShow()" readonly="readonly"/><font color="red">&nbsp;*(不可填)</font></td>
		   		<th style="width:310px;" >借卡大额封顶值：</th>
	   				<td style="width:150px;"><input type="text" name="dealAmt2" style="width:40px;" class="easyui-validatebox" onblur="dealAmtShow()" readonly="readonly"/><font color="red">&nbsp;*(不可填)</font></td>
		   		<th>贷记卡费率：</th>
		   		<td><input type="text" name="creditBankRate2" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
	   		</tr>  		
	   	</table>
	</fieldset>
	<fieldset>
		<legend></legend>
		<table class="table">
			<tr>
	   			<th style="width:310px;" >T0提现费率：</th>
		   		<td><input type="text" name="cashRate" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>		   	
		   		<th style="width:310px;" >转账费：</th>
		   			<td style="width:150px;"><input type="text" name="cashAmt" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()"/><font color="red">&nbsp;*</font></td>
		   		<th style="width:310px;" >传统扫码1000以下费率：</th>
				<td style="width:830px;"><input type="text" name="scanRate" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
				<th style="width:310px;" >传统扫码1000以上费率：</th>
				<td style="width:900px;"><input type="text" name="scanRateUp" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
	   		</tr>  
	   	</table>
	</fieldset>
</form>  

