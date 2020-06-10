<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script type="text/javascript"> 
	
	$(function(){
		$("#scanRate").val("0.0049");
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
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '费率格式不正确！' 
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
	
	$("#UNNO").combogrid({
	/**
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action?unLvl=1,2',
			valueField : 'UNNO',
			textField : 'UNNO',
			mode:'remote',
			fitColumns:true,  
			editable:false,
			columns:[[ 
				{field:'UNNO',title:'机构号',width:50},
				{field:'UN_NAME',title:'机构名称',width:150},
			]]
			 **/
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			idField:'UNNO',
			textField:'UNNO',
			mode:'remote',
			width:350,
			fitColumns:true,
			//pagination : true,
            //rownumbers:true,  
            collapsible:false,  
            fit: false,  
           // pageSize: 10,  
            //pageList: [10,15],
			columns:[[ 
				{field:'UNNO',title:'机构号',width:50},
				{field:'UN_NAME',title:'机构名称',width:200}
			]]
		});
		
</script>
</head>
<div   data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:200px; padding-top:10px;">
		<form id="fenpeiInfo" style="padding-left:2%;padding-top:1%" method="post" >
				<table class="table">
			   		<tr>
	   					<th style="width:100px;">分配至机构：</th>
	   					<td style="width:400px;">
	   						<input type="text" name="UNNO" id="UNNO" style="width:350px;" class="easyui-validatebox"  data-options="validType:'unnoValidator',editable:false" maxlength="25"  /><font color="red">&nbsp;*</font>
	   					</td>
	   				</tr>
	   				<tr>
	   					<td colspan="2" align="center"><font color="red">您可以分配给您下级代理，分配之后即失去查看下级代理的邀请码权限</font></td>
	   				</tr>
				</table>
		</form>
	</div>
</div>
