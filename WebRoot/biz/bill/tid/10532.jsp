<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
<script type="text/javascript">
$(function(){
	$('#rebateType_10532').combogrid({
		url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=3',
		idField:'VALUEINTEGER',
		textField:'VALUEINTEGER',
		mode:'remote',
		fitColumns:true,
		columns:[[
			{field:'NAME',title:'返利名称',width:150},
			{field:'VALUEINTEGER',title:'类型',width:150}
		]]
	});
});
</script>
	<div style="height:125px;padding-top:10px;padding-left: 30px">
		<form id="sysAdmin_10532_addForm" method="post">
 			<table class="table1">
 				<tr>
					<th>数量：</th>
					<td>
						<input type="text" name="machineNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" >
					</td>
				</tr>
				<tr>
					<th>单价：</th>
					<td>
						<input type="text" name="machinePrice" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" >
					</td>
				</tr>
				<tr>
					<th>返利类型：</th>
					<td><select id="rebateType_10532" name="rebateType"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 150px;"></select><font
						color="red">&nbsp;*</font></td>
				</tr>
				<input type="hidden" name="pdid" id="pdid_10532" >
				<input type="hidden" name="poid" id="pdid_10532" >
			</table>
	</form>
</div>