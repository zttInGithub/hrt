<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8" %>
<script type="text/javascript">
	$(function () {
		$('#sysAdmin_unit_total_sp_txn_grid').datagrid({
			url: '${ctx}/sysAdmin/checkUnitDealData_listTotalSpTxn.action',
			fit: true,
			fitColumns: false,
			border: false,
			nowrap: true,
			pagination: true,
			pageList: [10, 15],
			// idField: 'BTTID',
			columns: [[/*{
				title: 'ID',
				field: 'BTTID',
				width: 0,
				hidden: true
			}, {
				title: '日期',
				field: 'TXNDAY',
				width: 300,
				align: 'center',
				formatter : function(value,row,index) {
					if(value!=null){
						return value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6,8);
					}
				}
			},*/ {
				title: '交易类型',
				field: 'SUBTYPE',
				width: 300,
				align: 'center',
				formatter : function(value,row,index) {
					//'1-1.扫码1000以上；1-2.扫码1000以下；1-3.刷卡；1-4.云闪付，1-5.银联二维码'
					if(value==1){
						return '扫码1000以上';
					}else if(value==2){
						return '扫码1000以下';
					}else if(value==3){
						return '刷卡';
					}else if(value==4){
						return '云闪付';
					}else if(value==5){
						return '银联二维码';
					}else if(value=="-1"){
						return '全部';
					}else{
						return '其它';
					}
				}
			}, {
				title: '交易金额',
				field: 'ALLAMOUNT',
				width: 300,
				align: 'center'
			}]]
		});
	});

	//表单查询
	function bill_agentunit_total_sp_txn_searchFun() {
		var txnDay = $('#sp_Txn_txnDay').datebox('getValue');
		var txnDay1 = $('#sp_Txn_txnDay1').datebox('getValue');
		if(txnDay!=null && txnDay!='' && txnDay1!=null && txnDay1!=''){
			$('#sysAdmin_unit_total_sp_txn_grid').datagrid('load', serializeObject($('#bill_agentunit_total_sp_txn_searchForm')));
		}else{
			$.messager.alert('提示','查询起始时间不能为空！');
		}
	}

	//清除表单内容
	function bill_agentunit_total_sp_txn_cleanFun() {
		$('#bill_agentunit_total_sp_txn_searchForm input').val('');
		$('#bill_agentunit_total_sp_txn_searchForm select').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
			 style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="bill_agentunit_total_sp_txn_searchForm" style="padding-left:5%"
					method="post">
			<table class="tableForm">
				<tr>
					<th>时间</th>
					<td><input name="txnDay" id="sp_Txn_txnDay" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="txnDay1" id="sp_Txn_txnDay1" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;交易类型</th>
					<td>
						<select id="txnType" name="txnType" style="width:150px;">
							<option value="">全部</option>
							<option value="1">扫码1000以上</option>
							<option value="2">扫码1000以下</option>
							<option value="3">刷卡</option>
							<option value="4">云闪付</option>
							<option value="5">银联二维码</option>
						</select>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
							 data-options="iconCls:'icon-search',plain:true"
							 onclick="bill_agentunit_total_sp_txn_searchFun();">
							查询
						</a>
						&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
							 data-options="iconCls:'icon-cancel',plain:true"
							 onclick="bill_agentunit_total_sp_txn_cleanFun();">
							清空
						</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
			 style="overflow: hidden;">
		<table id="sysAdmin_unit_total_sp_txn_grid"></table>
	</div>
</div>