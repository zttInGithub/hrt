<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$('#DELIVERNAME_10435').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=1',
			idField:'VALUESTRING',
			textField:'VALUESTRING',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'VALUESTRING',title:'快递公司',width:150},
			]]
		});
	});
</script>
<style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
<div data-options="region:'north',border:false" style="height: 200px;">
	<form id="sysAdmin_10435_addForm" style="padding-left: 20px;"
		method="post">
		<fieldset style="width: 800px;">
			<legend>订单信息</legend>
			<table class="table1">
				<tr>
					<th>采购单订单号：</th>
					<td><input type="text" name="ORDERID" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"><input type="hidden" name="PDLID" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>采购日期：</th>
					<td><input type="text" name="ORDERDAY" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
				</tr>
				<tr>
					<th>采购机构号：</th>
					<td><input type="text" name="DELIVERUNNO" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>采购机构名称：</th>
					<td><input type="text" name="DELIVERPURNAME" style="width: 150px;"
						class="easyui-validatebox" data-options="" readonly="readonly"></td>
				</tr>
				<tr>
					<th>已入数量：</th>
					<td><input type="text" name="ALLOCATEDNUM" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>总数量：</th>
					<td><input type="text" name="DELIVENUM" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
				</tr>
				
				<tr>
					<th>联系人：</th>
					<td><input type="text" name="DELIVERCBY" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>发货创建时间：</th>
					<td><input type="text" name="DELIVERCDATE" style="width: 150px;"
						class="easyui-validatebox" readonly="readonly"></td>
				</tr>
				
				
			</table>
		</fieldset>
		
		<fieldset style="width: 800px;">
			<legend>设备信息</legend>
			<table class="table1">
				
				<tr>
					<th>机型：</th>
					<td><input type="text" name="MACHINEMODEL" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>返利类型：</th>
					<td><input type="text" name="REBATETYPE"
						id="orderMethod_10435" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>刷卡费率：</th>
					<td><input type="text" name="RATE" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>扫码1000以下费率：</th>
					<td><input type="text" name="SCANRATE" style="width: 150px;"
						class="easyui-validatebox" data-options="" readonly="readonly"></td>
				</tr>
				<tr>
					<th>扫码1000以上费率：</th>
					<td><input type="text" name="SCANRATEUP" style="width: 150px;"
										 class="easyui-validatebox"
										 data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>花呗费率：</th>
					<td><input type="text" name="HUABEIRATE" style="width: 150px;"
										 class="easyui-validatebox" data-options="" readonly="readonly"></td>
				</tr>
				<tr>
					<th>提现手续费：</th>
					<td><input type="text" name="SECONDRATE" style="width: 150px;" readonly="readonly"></td>
					<th>品牌：</th>
					<td><input type="text" name="BRANDNAME"
						id="orderMethod_10435" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>活动押金：</th>
					<td><input type="text" name="DEPOSITAMT" style="width: 150px;" readonly="readonly"></td>
					</td>
				</tr>
				
				
			</table>
		</fieldset>
		
		
		<fieldset style="width: 800px;">
			<legend>发货信息</legend>
			<table class="table1">
				<tr>
					<th>快递单号：</th>
					<td><input type="text" name="DELIVERID" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator',required:true" ></td>
					<th>快递公司：</th>
					<td><select id="DELIVERNAME_10435" name="DELIVERNAME"
						class="easyui-combogrid"
						data-options="editable:false,required:true" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
				</tr>
				<tr>
					<th>发货数量：</th>
					<td><input type="text" name="DELIVENUM" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>发货时间：</th>
					<td><input type="text" name="DELIVEDATE" style="width: 150px;"
						class="easyui-validatebox" data-options="" readonly="readonly"></td>
				</tr>
				<tr>
					<th>收货联系人：</th>
					<td><input type="text" name="DELIVERCONTACTS" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>收货联系电话：</th>
					<td><input type="text" name="DELIVERCONTACTPHONE"
						style="width: 150px;" class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
				</tr>
				<tr>
					<th>收货联系邮箱：</th>
					<td><input type="text" name="DELIVERCONTACTMAIL" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
					<th>收货地址：</th>
					<td><input type="text" name="DELIVERRECEIVEADDR" style="width: 150px;"
						class="easyui-validatebox"
						data-options="validType:'spaceValidator'" readonly="readonly"></td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
