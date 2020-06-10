<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    	}
    	});
	
	
</script>
 
<form id="sysAdmin_20404_editForm" method="post">
	<fieldset>
		<legend>申请退款信息</legend>
		<table class="table">
			<tr>
	    		<th>商户编号：</th>
	    		<td><input type="text" name="mid" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="200"/><font color="red">&nbsp;</font></td>
	
	    		<th>交易时间：</th>
	    		<td><input type="text" name="txnDay" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>交易卡号：</th>
	    		<td><input type="text" name="cardPan"  style="width:200px;"  readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    		
	    		<th>交易参考号：</th>
	    		<td><input type="text" name="rrn" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>原始交易金额：</th>
	    		<td><input type="text" name="samt"  style="width:200px;"  readonly="readonly"  class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    		
	    		<th>退款金额：</th>
	    		<td><input type="text" name="ramt"  style="width:200px;"   class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>	
				<th>结算方式：</th>
	    		<td>
	    			<select id="settlement"  name="settlement"  style="width:180px;height:25px" class="easyui-validatebox" data-options="required:true">
	    					<option selected="selected" value='2'>抵扣结算</option>
	    					<option value='1'>汇款</option>
	    			</select>
	    			<font color="red">&nbsp;</font>
	    		</td>	    	
	    	</tr>
		</table>
	</fieldset>
	
	<input type="hidden" name="refid" id="refid">
	<input type="hidden" name="pkId">
</form>  

