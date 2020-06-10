<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 退货审核查询 -->
<script type="text/javascript">
	var pdid = <%=request.getParameter("pdid")%>;
	var show = '<%=request.getParameter("show")%>';
	if(show=='K'){
		$("#show_10502_detailApproveNote").show();
	}else{
		$("#show_10502_detailApproveNote").hide();
	}
	$(function() {
		$('#sysAdmin_10502_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDetail_listPurchaseDetailById.action?pdid='+pdid,
			fit : true,
			fitColumns : true,
			idField : 'pdid',
			columns : [[{
				title : '唯一主键',
				field : 'pdid',
				width : 100,
				hidden : true
			},{
				title :'订单号',
				field :'detailOrderID',
				width : 100
			},{
				title :'品牌',
				field :'brandName',
				width : 100
			},{
				title :'订单类型',
				field :'orderType',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 1){
						return '采购订单';
					}else if(value == 2){
						return '赠品订单';
					}else if(value == 3){
						return '回购订单';
					}else if(value == 4){
						return '退货';
					}else if(value == 4){
						return '退货';
					}else if(value == 5){
						return '换购订单';
					}else if(value == 7){
						return "自定义政策";
					}
				}
			},{
				title :'机型小类',
				field :'machineModel',
				width : 100
			},{
				title :'机型大类',
				field :'snType',
				align :'right',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 1){
						return '小蓝牙';
					}else if(value == 2){
						return '大蓝牙';
					}
				}
			},{
				title :'返利类型',
				field :'rebateType',
				align :'right',
				width : 100
			},{
				title :'机具单价',
				field :'machinePrice',
				align :'right',
				width : 100
			},{
				title :'数量',
				field :'machineNum',
				align :'right',
				width : 100
			},{
				title :'金额',
				field :'detailAmt',
				align :'right',
				width : 100
			},{
				title :'状态',
				field :'detailStatus',
				align :'right',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 6&&row.orderType!=4){
						return '未入库';
					}else if(value == 7&&row.orderType!=4){
						return '入库中';
					}else if(value == 8){
						return '已入库';
					}else if(value == 6&&row.orderType==4&&row.detailApproveStatus!='K'){
						return '未审核';
					}else if(value == 7&&row.orderType==4&&row.detailApproveStatus!='K'){
						return '已审核';
					}else if(row.orderType==4&&row.detailApproveStatus=='K'){
						return '已退回';
					}
				}
			},{
				title :'创建时间',
				field :'detailCdate',
				align :'right',
				width : 100
			},{
				title :'审核人',
				field :'detailApprover',
				align :'right',
				width : 100
			},{
				title :'审核时间',
				field :'detailApproveDate',
				align :'right',
				width : 100
			}]]
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
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:251px;">
	   	<form id="sysAdmin_10502_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>采购单信息</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>采购单订单号：</th>
		   				<td><input type="text" name="ORDERID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>大类：</th>
		   				<td>
			    			<input type="text" name="ORDERMETHOD" id="orderMethod_10502" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
	   					</td>
		   			</tr>
		   			<tr>
		   				<th>供应商名称：</th>
		   				<td><input type="text" name="VENDORNAME" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>采购机构名称：</th>
		   				<td><input type="text" name="PURCHASERNAME" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>采购代理机构号：</th>
		   				<td><input type="text" name="UNNO" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>联系人：</th>
		   				<td><input type="text" name="CONTACTS" id="qcontactPhone"  style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>联系电话：</th>
		   				<td><input type="text" name="CONTACTPHONE" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>联系邮箱：</th>
		   				<td><input type="text" name="CONTACTMAIL" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>总数量：</th>
		   				<td><input type="text" name="ORDERNUM" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			
		   				<th>总金额：</th>
		   				<td><input type="text" name="ORDERAMT" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>采购人：</th>
		   				<td><input type="text" name="PURCHASER" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			
		   				<th>创建时间：</th>
		   				<td><input type="text" name="CDATE" style="width: 150px;" class="easyui-validatebox" data-options="" readonly="readonly"></td>
		   			</tr>
		   			<tr id="show_10502_detailApproveNote">
		   				<th>退回原因：</th>
					   	<td>
				    		<textarea rows="6" cols="38" style="resize:none;" name="DETAILAPPROVENOTE" maxlength="100" readonly="readonly"></textarea>
				    	</td>
		   			</tr>
		   		</table>
			</fieldset>
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10502_datagrid"></table>
	</div> 
</div>