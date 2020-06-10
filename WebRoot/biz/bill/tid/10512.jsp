<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
	   	<form id="sysAdmin_10512_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>来款信息</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>订单号：</th>
		   				<td><input type="text" name="ORDERID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>匹配人：</th>
		   				<td><input type="text" name="RECORDLMBY" style="width: 150px;" class="easyui-validatebox" data-options="" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>来款金额：</th>
		   				<td><input type="text" name="ARRAIVEAMT" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>已匹配金额：</th>
		   				<td><input type="text" name="RECORDAMT" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>来款人姓名：</th>
		   				<td><input type="text" name="ARRAIVERNAME" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>来款卡号：</th>
		   				<td><input type="text" name="ARRAIVECARD" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>来款日期：</th>
		   				<td><input type="text" name="ARRAIVEDATE" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>来款状态：</th>
		   				<td><input type="text" name="ARRAIVESTATUS" id="arraiveStatus_10512" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>来款方式：</th>
		   				<td><input type="text" name="ARRAIVEWAY" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			
		   				<th>代理机构号：</th>
		   				<td><input type="text" name="UNNO" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>代理机构名称：</th>
		   				<td><input type="text" name="PURCHASERNAME" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			
		   				<th>登记人：</th>
		   				<td><input type="text" name="RECORDCDATE" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>登记时间：</th>
		   				<td><input type="text" name="RECORDCBY" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			
		   				<th>匹配时间：</th>
		   				<td><input type="text" name="RECORDLMDATE" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>备注：</th>
					   	<td colspan="3">
				    		<textarea rows="6" cols="110" style="resize:none;" name="ARRAIVEREMARKS" maxlength="200" readonly="readonly"></textarea>
				    	</td>
		   			</tr>
		   		</table>
			</fieldset>
		</form>
		