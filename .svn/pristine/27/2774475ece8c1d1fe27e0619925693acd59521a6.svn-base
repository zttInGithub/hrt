<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script>
//大额扫码分润汇总
	$(function(){
		$(function(){
			$('#rebateType_20305').combogrid({
				url : '${ctx}/sysAdmin/agentunit_listRebateRate.action?status=syt',
				idField:'VALUEINTEGER',
				textField:'NAME',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'NAME',title:'活动类型',width:70},
					{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
				]]
			});
		});
		
		$("#sysAdmin_20305_datagrid").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryBigScanProfit.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'UNNO',
			sortOrder: 'UNNO',
			idField : 'UNNO',
			columns : [[{
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
				title :'机构名称',
				field :'UNNAME',
				width : 130
			},{
				title :'活动类型',
				field :'REBATETYPE',
				width : 100
			},{
				title :'类型',
				field :'SETTMETHOD',
				width : 130/* ,
				formatter : function(value,row,index) {
					if (value==2){
						return "支付宝";
					}else if(value==3){
						return "银联二维码";
					}else if(value==1 && row.MINFO2=="1"){
						return "微信0.38";
					}else if(value==1 && row.MINFO2=="2"){
						return "微信0.45";
					}else if(value==1 && row.MINFO2=="3"){
						return "微信(老)";
					}else if(value==1){
						return "微信";
					}
				} */
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'手续费分润',
				field :'PROFIT',
				width : 100
			}]], 
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20305();
				}
			}]
		});
	});
	
	//数据导出
	function exportExcel_20305(){
		var txnDay = $('#txnDay_20305').datebox('getValue');
		var txnDay1 = $('#txnDay1_20305').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
		    	$('#sysAdmin_20305_searchForm').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportBigScanProfit.action'});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	//查询提交
	function check_20305_search(){
		var txnDay = $('#txnDay_20305').datebox('getValue');
		var txnDay1 = $('#txnDay1_20305').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$('#sysAdmin_20305_datagrid').datagrid('load', serializeObject($('#sysAdmin_20305_searchForm')));
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	//清除表单内容
	function check_20305_close() {
		$('#sysAdmin_20305_searchForm input').val('');
		$('#sysAdmin_20305_searchForm select').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_20305_searchForm" style="padding-left:3%">
			<table>
				<tr>
					<th>交易起始日期</th>
					<td>
						<input id="txnDay_20305" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<th>交易结束日期</th>
					<td>
						<input id="txnDay1_20305" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
					<th>活动类型</th>
					<td>
						<select id="rebateType_20305" name="maintainType" class="easyui-combogrid" data-options="validType:'spaceValidator'" style="width:135px;"></select>
					</td>
					<td style="width: 15px"></td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20305_search();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20305_close();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="sysAdmin_20305_datagrid" style="overflow:hidden"></table>
	</div>
</div>