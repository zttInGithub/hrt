<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script type="text/javascript"> 
	
	$(function(){
		$("#scanRate").val("0.0038");
	});
	
	$.extend($.fn.validatebox.defaults.rules,{
		unnoValidator:{
			validator : function(value) {
	            return /^\d{6}$/i.test(value); 
	        }, 
	        message : '必须是六位数字！' 
		}
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		rateValidator:{
			validator : function(value) {
				if(value<0.0038||0.006<value){
					return false;
				}
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '请正确输入费率为0.0038-0.0060！' 
		}
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
<div   data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:200px; padding-top:10px;">
		<form id="createInfo" style="padding-left:2%;padding-top:1%" method="post" >
				<table class="table">
					<tr>
	   					<th >机构号: </th>
			   			<td>
			   				<input type="text" name="unno" id="unno" style="width:200px;" class="easyui-validatebox" maxlength="50" data-options="validType:'unnoValidator',required:true"/><font color="red">&nbsp;*</font>
			   			</td>
			   		</tr>
					<tr>
	   					<th >扫码消费费率: </th>
			   			<td>
			   				<input type="text" name="scanRate" id="scanRate" style="width:200px;" class="easyui-validatebox" maxlength="50" data-options="validType:'rateValidator',required:true"/><font color="red">&nbsp;*</font>
			   			</td>
			   		</tr>
			   		<tr>
	   					<th>生产数量：</th>
	   					<td>
	   						<input type="text" name="qrnum" name="qrnum" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="25"/><font color="red">&nbsp;*</font>
	   					</td>
	   				</tr>
				</table>
		</form>
	</div>
</div>