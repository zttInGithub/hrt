<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$('#childUnno').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});

		$("#tempName").combobox({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_querySubTemplateByUnno.action',
			valueField : 'TEMPNAME',
			textField : 'TEMPNAME',
			onSelect:function(rec){
				$("#tempName").val(rec.TEMPNAME);
			}
		});
	});

</script>

<form id="sysAdmin_20514_addForm" method="post"  >
	
		<table class="table">
		   	<tr>
		   		<th>代理机构</th>
				<td>
		   		<select name="unno" id="childUnno" class="easyui-combogrid" data-options="required:true" style="width:150px;"></select>
		   		</td>
		   		<th>模板名称：</th>
	   			<td><input type="text" id="tempName" name="tempName" style="width:120px;"  class="easyui-combobox" editable="false"  data-options="required:true" /></td>
	   		</tr>	
	   	</table>
</form>  

