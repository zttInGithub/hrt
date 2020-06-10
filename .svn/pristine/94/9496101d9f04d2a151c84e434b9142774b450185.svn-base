<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 来款匹配 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10510_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseOrder_listPurchaseOrdersAll.action?orderMethod=2',
			fit : true,
			fitColumns : true,
			border : false,
			singleSelect:true,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'poid',
			columns : [[{
				title : '采购ID',
				field : 'poid',
				width : 100,
				checkbox:true
			},{
				title : '订单号',
				field : 'orderID',
				width : 120
			},{
				title : '大类',
				field : 'orderMethod',
				width : 80,
				formatter : function(value,row,index) {
					if(value==2){
						return "代理单";
					}
				}
			},{
				title : '供应商名称',
				field : 'vendorName',
				width : 100
			},{
				title : '采购机构名称',
				field : 'purchaserName',
				width : 100
			},{
				title :'采购人',
				field :'purchaser',
				width : 100
			},{
				title :'总数量',
				field :'orderNum',
				align : 'right',
				width : 100
			},{
				title :'总金额',
				field :'orderAmt',
				align : 'right',
				width : 100
			},{
				title :'已匹配金额',
				field :'orderpayAmt',
				align : 'right',
				width : 100
			},{
				title :'状态',
				field :'status',
				width : 120,
				formatter : function(value,row,index) {
					if(row.status == 1 && row.approveStatus=='K'){
						return '退回'
					}else if(row.status == 1){
						return '待提交';
					}else if(row.status == 2){
						return '订单待审';
					}else if(row.status == 3){
						return '已审核';
					}else if(row.status == 4){
						return '结款中';
					}else if(row.status == 5){
						return '已结款';
					}
				}
			},{
				title :'创建时间',
				field :'cdate',
				width : 130
			}]]		
		});
	});
	//表单查询
	function sysAdmin_10510_searchFun() {
		$('#sysAdmin_10510_datagrid').datagrid('reload', serializeObject($('#sysAdmin_10510_addForm'))); 
	}
	//清除表单内容
	function sysAdmin_10510_cleanFun() {
		$('#sysAdmin_10510_addForm input').val('');
	}
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
	<div data-options="region:'north',border:false" style="height:170px;">
   	<form id="sysAdmin_10510_addForm" style="padding-left:20px;" method="post">
	   	<fieldset style="width: 800px;">
			<legend>匹配信息</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>订单ID：</th>
					<td><input type="text" name="orderID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator'" maxlength="200"></td>
<%-- 					<td><select class="easyui-combogrid" name="ORDERID" style="width:176px;" data-options="
			            panelWidth:156,
				        idField:'ORDERID',   
				        textField:'ORDERID', 
				        sortName: 'POID',		       
						sortOrder: 'desc',
				        url:'${ctx}/sysAdmin/purchaseRecord_searchPurchaseOrderByOID.action',
				        columns:[[   
								{field:'ORDERID',title:'订单ID',width:150},
								{field:'ORDERID',title:'ORDERDAY',width:150},
								{field:'POID',title:'id',width:150,hidden:true}
				        ]]"></select>
					</td> --%>
					<th>代理机构号：</th>
					<td><input type="text" name="unno" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator'" maxlength="6"></td>
	   			</tr>
	   			<tr>
					<th>匹配金额：</th>
					<td colspan="3"><input type="text" name="matchAmt" id="matchAmt_10510" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',required:true,precision:'2'"><font color="red">&nbsp;*</font></td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_10510_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_10510_cleanFun();">清空</a>
					</td>
				</tr>
	   		</table>
		</fieldset>
	   	<input type="hidden" name="prid" id="prid_10510"/>
	   	<input type="hidden" name="poid" id="poid_10510"/>
	</form>
	<span><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择订单进行匹配</font></span>
	</div>
	<div data-options="region:'center', border:false"
	style="overflow: hidden;">
		<table id="sysAdmin_10510_datagrid" style="overflow: hidden;"></table>
	</div>
</div>
