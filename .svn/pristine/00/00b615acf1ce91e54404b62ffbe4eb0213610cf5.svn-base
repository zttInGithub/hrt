<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules,{
		payBankIdValidator:{
			validator : function(value) {
	            return /^\d{12}$/i.test(value); 
	        }, 
	        message : '必须是十二位数字！' 
		}
	});

</script>
    
<form id="sysAdmin_merBankCard_editForm" method="post">
	<table class="table">
    	<tr>
    		<th>银行卡号：</th>
    		<td><input type="text" name="BANKACCNO" readonly="readonly" style="width:250px;"/></td>
    		
    	</tr>
		   	<tr>
		   		<th>支付系统行号：</th>
		   		<td colspan="3">
		   		<input type="text" name="PAYBANKID" style="width:150px;" class="easyui-validatebox"  data-options="required:true,validType:'payBankIdValidator'" maxlength="20"/>
		   		<br><font color="red">&nbsp;*支付系统行号作为结算信息依据，填写错误会影响成功付款!</font>
		   		</td>
			</tr>
    	<tr>
    	    <th>入账人名称：</th>
    		<td> <input type="text" name="BANKACCNAME" readonly="readonly" style="width:250px;"/></td>
    	</tr>
    </table>
    <input type="hidden" name="MBCID">
</form>