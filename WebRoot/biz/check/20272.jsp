<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<form id="profitmicro_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
	  <table class="table">
		 <tr>
		   	<th>模版名称：</th>
		   	<td ><input type="text" name="tempName" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" /><font color="red">&nbsp;*</font></td>
			<th></th>
			<td></td>
		 </tr>
	  </table>
	</fieldset>
	<fieldset>
		<legend>理财商户</legend>
		<table class="table">
		   	<tr>
		   		<th>借记卡封顶值：</th>
		   		<td><input type="text" name="endAmount" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18" value="4146" readonly="readonly" /><font color="red">*默认</font></td>
		   		<th>借记卡手续费：</th>
	   			<td><input type="text" name="startAmount" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18"/><font color="red"></font></td>
	   		</tr>
	   		<tr>
		   		<th>借记卡费率：</th>
		   		<td><input type="text" name="ruleThreshold" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18"/>%<font color="red"></font></td>
		   		<th></th>
				<td></td>
	   		</tr>
	   		<tr>
		   		<th>T0提现费率：</th>
		   		<td colspan="1"><input type="text" name="cashRate" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18"/>%<font color="red"></font></td>
		   		<th>转账费：</th>
		   		<td colspan="1"><input type="text" name="cashAmt" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18"/><font color="red"></font></td>
		   	</tr>
		   	<tr>
		   		<th>贷记卡费率：</th>
		   		<td><input type="text" name="creditBankRate" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18"/>%<font color="red"></font></td>
		   		<th>利润百分比：</th>
		   		<td colspan="1"><input type="text" name="profitPercent" style="width:100px;" class="easyui-validatebox" data-options="validType:'payBankIdValidator'" maxlength="18"/>%<font color="red"></font></td>
		   	</tr>
	   	</table>
	</fieldset>
	<fieldset>
		<legend>秒到商户</legend>
		<table class="table">
	   		<tr>
		   		<th>贷记卡0.72%费率：</th>
		   		<td ><input type="text" name="creditBankRate1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
		   		<th>利润百分比：</th>
		   		<td colspan="1"><input type="text" name="profitPercent1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'payBankIdValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
			</tr> 
	   		<tr>
		   		<th>贷记卡非0.72%费率：</th>
		   		<td ><input type="text" name="creditBankRate2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
		   		<th>利润百分比：</th>
		   		<td colspan="1"><input type="text" name="profitPercent2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'payBankIdValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
			</tr> 
		    <tr>
		   		<th>转账费：</th>
		   		<td ><input type="text" name="cashAmt1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
		   		<th>云闪付费率：</th>
		   		<td ><input type="text" name="cloudQuickRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
		   	</tr> 		
	   	</table>
	</fieldset>
	<fieldset>
		<legend>扫码支付商户</legend>
		<table class="table">
		  <tr>
		   		<th>扫码1000以下费率：</th>
		   		<td><input type="text" name="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
		   		<th>扫码1000以上费率：</th>
		   		<td><input type="text" name="scanRate1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
		  </tr>
		  <tr>
		   		<th>扫码1000以下转账费：</th>
		   		<td><input type="text" name="cashamtunder" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
		   		<th>扫码1000以上转账费：</th>
		   		<td><input type="text" name="cashamtabove" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
		  </tr>
		   <tr>
		   		<th>银联二维码费率：</th>
		   		<td><input type="text" name="scanRate2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
		   		<th>银联二维码转账费：</th>
		   		<td><input type="text" name="cashAmt2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
		  </tr>    		
	   	</table>
	</fieldset>
	<fieldset>
		<legend>快捷支付商户</legend>
		<table class="table">
		  <tr>
		   		<th>VIP用户费率：</th>
		   		<td><input type="text" name="startAmount2" id="startAmount2" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18" />%<font color="red"></font></td>
		   		<th>完美账单费率：</th>
		   		<td colspan="1"><input type="text" name="endAmount2" id="endAmount2" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18"/>%<font color="red"></font></td>
		  </tr>  		
		  <tr>
		  		<th>快捷分润比例：</th>
		   		<td colspan="1"><input type="text" name="profitPercent3" style="width:100px;" class="easyui-validatebox" data-options="validType:'payBankIdValidator'" maxlength="18" />%<font color="red"></font></td>
		  		<th>转账费：</th>
		   		<td colspan="1"><input type="text" name="cashAmt3" id="cashAmt3" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18"/><font color="red"></font></td>
		  </tr>
	   	</table>
	</fieldset>
</form>  

