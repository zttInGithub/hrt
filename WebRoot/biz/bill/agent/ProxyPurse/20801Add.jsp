<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="DebitUnnoAdj_addForm" method="post">
	<table style="padding-top: 10px;padding-left:50px">
		<tr>
			<td>机构编号：</td>
			<td><input name="UNNO" class="easyui-validatebox" data-options="required:true" style="width:200px;height:30px"/> </td>
		</tr>
		<tr>
			<td>调整日期：</td>
			<td><input name="SETTLEDAY" class="easyui-datebox" data-options="required:true" style="width:205px;height:30px"/> </td>
		</tr>
		<tr>
			<td>调整金额：</td>
			<td><input name="FEEAMT" class="easyui-validatebox" data-options="required:true" style="width:200px;height:30px"/> </td>
		</tr>
		<tr>
			<td>钱包类型：</td>
			<td><select name="walletType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
		    			<option value="1">返现</option>
		    			<option value="0">分润</option>
		    	</select>
		    </td>
		</tr>
		<tr>
			<td>调整原因(备注)：</td>
			<td><input name="FEENOTE" class="easyui-validatebox" style="width:200px;height:30px"/> </td>
		</tr>
	</table>
</form>

