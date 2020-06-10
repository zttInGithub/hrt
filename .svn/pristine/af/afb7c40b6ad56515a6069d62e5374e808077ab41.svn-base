<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$('#keyContext').combogrid({
			url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=SytRate',
			idField:'KEYCONTEXT',
			textField:'MINFO1',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'KEYCONTEXT',title:'费率-手续费',width:150,hidden:true},
				{field:'MINFO1',title:'描述',width:250}
			]] 
		});
	$.extend($.fn.validatebox.defaults.rules, {
		rateValidator:{
			validator : function(value) {
				if(value<0.38||value>0.6){
					return false;
				}
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '请正确输入费率为0.38%-0.60%！' 
		}
	});
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="sysAdmin_editRate_editForm" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
				<tr align="center">
					<th style="width:100px;">费率&手续费：</th>
   					<td style="width:270px;">
	   					<select id="keyContext" name="keyContext" class="easyui-combogrid" data-options="required:true,editable:false" style="width:250px;"></select><font color="red">&nbsp;*</font>
   					</td>
				</tr>
			</table>
		</form>
</div>


