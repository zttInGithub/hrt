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
		rateValidator:{
			validator : function(value) {
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '费率格式不正确！' 
		}
	});
</script>
<form id="profitmicro_20601_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
	  <table class="table" style="width: 900px">
		 <tr>
		   	<th>模版名称：</th>
		   	<td ><input type="text" name="tempName" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" /><font color="red">&nbsp;*</font></td>
		 </tr>
		 <tr>
	   	 <!-- 
	   	 <th>扫码押金费率：</th>
	   	 <td ><input type="text" name="subRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%</td>
	   	 <th>扫码押金转账费：</th>
	   	 <td ><input type="text" name="cashAmt" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" /></td> 
	   	 -->
	   	 <tr >
			 <th>扫码1000以上（终端0.38）费率：</th>
		   	 <td ><input type="text" id = "creditBankRate_20601" name="creditBankRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%</td>
		   	 <th>扫码1000以上（终端0.38）转账费：</th>
		   	 <td ><input type="text" id = "cashRate_20601" name="cashRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" /></td>
	     </tr>
		 <tr>
			 <th>扫码1000以上（终端0.45）费率：</th>
		   	 <td ><input type="text" id = "scanRate_20601" name="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%</td>
		   	 <th>扫码1000以上（终端0.45）转账费：</th>
		   	 <td ><input type="text" id = "profitPercent1_20601" name="profitPercent1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" /></td>
	     </tr>
		 <tr>
			 <th>扫码1000以下（终端0.38）费率：</th>
		   	 <td ><input type="text" id = "subRate_20601" name="subRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%</td>
		     <th>扫码1000以下（终端0.38）转账费：</th>
		   	 <td ><input type="text" id = "cashAmt_20601" name="cashAmt" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" /></td>
	     </tr>
		 <tr>
			 <th>扫码1000以下（终端0.45）费率：</th>
		   	 <td ><input type="text" id = "scanRate1_20601" name="scanRate1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%</td>
		   	 <th>扫码1000以下（终端0.45）转账费：</th>
		   	 <td ><input type="text" id = "cashAmt1_20601" name="cashAmt1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" /></td>
	   	 </tr>
		 <tr>
			 <th>银联二维码费率：</th>
		   	 <td ><input type="text" id = "scanRate2_20601" name="scanRate2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%</td>
		   	 <th>银联二维码转账费：</th>
		   	 <td ><input type="text" id = "cashAmt2_20601" name="cashAmt2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" /></td>
	   	 </tr>
	  </table>
	</fieldset>
</form>  

