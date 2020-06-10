<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="sysAdmin_machineSend_editForm" method="post">
	<table class="table">
    	<tr>
    		<th>机构编号：</th>
    		<td><input readonly="readonly" type="text" name="unNO" data-options="required:true" class="easyui-validatebox" style="width:250px;"/></td>
    	</tr>
    	<tr>
    		<th>订单号：</th>
    		<td>
    		<input readonly="readonly" type="text" name="orderID" data-options="required:true" class="easyui-validatebox" style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>收件人：</th>
    		<td>
    			<input readonly="readonly" type="text" name="consigneeName"  style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>快递单号：</th>
    		<td>
    			<input type="text" name="expressBill"  style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>发货状态：</th>
    		<td>
			<select name="status" id="status">
    		<option value="3">未发货</option>
    		<option value="4">已发货</option>
    		</select>
		</td>
    	</tr>
    	<tr>
    		<th>摘要信息：</th>
    		<td>
    		<textarea rows="3" cols="35" name="minfo"  ></textarea>
    		</td>
    	</tr>
    </table>
    <input type="hidden" name="bmoID">
    <input type="hidden" name="txnDay">
    <input type="hidden" name="orderAmount">
    <input type="hidden" name="approveStatus">
    <input type="hidden" name="consigneeAddress">
    <input type="hidden" name="consigneePhone">
    <input type="hidden" name="postCode">
    <input type="hidden" name="processContext">
    <input type="hidden" name="amountConfirmDate">
    <input type="hidden" name="consigneeTel">
    <input type="hidden" name="sendConfirmDate">
    <input type="hidden" name="recvConfirmDate">
    <input type="hidden" name="maintainUID">
    <input type="hidden" name="maintainDate">
    <input type="hidden" name="maintainType">
    <input type="hidden" name="approveUID">
    <input type="hidden" name="approveDate">
    <input type="hidden" name="shipmeThod">
</form>