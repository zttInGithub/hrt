<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
<div data-options="region:'north',border:false" style="height: 200px;">
	<form id="sysAdmin_10452_addForm" style="padding-left: 20px;"
		method="post">
		<fieldset style="width: 800px;">
			<legend>发货信息</legend>
			<table class="table1">
				<tr>
					<th>发货数量：</th>
					<td><input type="text" name="deliveNum" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>发货时间：</th>
					<td><input type="text" name="deliveDate" style="width: 150px;"
						class="easyui-validatebox" data-options="" readonly="readonly"></td>
				</tr>
				<tr>
					<th>收货联系人：</th>
					<td><input type="text" name="deliverContacts" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'"></td>
					<th>收货联系电话：</th>
					<td><input type="text" name="deliverContactPhone"
						style="width: 150px;" class="easyui-validatebox"
						data-options="validType:'spaceValidator'"></td>
				</tr>
				<tr>
					<th>收货联系邮箱：</th>
					<td><input type="text" name="deliverContactMail" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'"></td>
					<th>收货地址：</th>
					<td><input type="text" name="deliverReceiveaddr" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'"></td>
				</tr>
			</table>
		</fieldset>
		<input type="hidden" name="pdlid">
	</form>
</div>
