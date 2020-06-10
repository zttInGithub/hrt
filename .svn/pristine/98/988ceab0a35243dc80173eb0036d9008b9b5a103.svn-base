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
<form id="profitmicro_20511_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
	  <table class="table">
		 <tr>
		   	<th>模版名称：</th>
		   	<td ><input type="text" name="tempName" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"  onblur="dealAmtShow()"/><font color="red">&nbsp;*</font></td>
		   	<th>代还费率：</th>
		   	<td ><input type="text" name="subRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%</td>
		   	</tr> 	
	   	</table>
	</fieldset>
</form>  

