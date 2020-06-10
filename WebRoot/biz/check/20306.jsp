<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
//大额扫码分润明细
	$(function(){
		$(function(){
			$('#rebateType_20306').combogrid({
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
		
		$("#sysAdmin_20306_datagrid").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryBigScanProfitDetail.action',
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
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
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
					}else if(value==1 && row.IFCARD=="1"){
						return "微信0.38";
					}else if(value==1 && row.IFCARD=="2"){
						return "微信0.45";
					}else if(value==1 && row.IFCARD=="3"){
						return "微信(老)";
					}else if(value==1){
						return "扫码";
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
				width : 100,
				formatter : function(value,row,index) {
					if (value==null){
					   return "0";
					}else{
						return value;
					}
				}
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20306();
				}
			}] 
		});
	
	});
	
	//导出
	function exportExcel_20306(){
		var txnDay = $('#txnDay_20306').datebox('getValue');
		var txnDay1 = $('#txnDay1_20306').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$('#sysAdmin_20306_searchForm').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportBigScanProfitDetail.action'});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	
	function check_20306_search(){
		var txnDay = $('#txnDay_20306').datebox('getValue');
		var txnDay1 = $('#txnDay1_20306').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$("#sysAdmin_20306_datagrid").datagrid('load',serializeObject($("#sysAdmin_20306_searchForm")));
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	
	function check_20306_close(){
		$('#sysAdmin_20306_searchForm input').val('');
		$('#sysAdmin_20306_searchForm select').val('');
	} 
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="overflow:hidden; height:100px; padding-top:15px;" >
		<form id="sysAdmin_20306_searchForm" style="padding-left: 5%">
			<table>
				<tr>
					<th>商户号</th>
					<td>
						<input type="text" name="mid">
					</td>
					<th>交易起始日期</th>
					<td>
						<input id="txnDay_20306" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<th>交易结束日期</th>
					<td>
						<input id="txnDay1_20306" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
					<th>活动类型</th>
					<td>
						<select id="rebateType_20306" name="maintainType" class="easyui-combogrid" data-options="validType:'spaceValidator'" style="width:135px;"></select>
					</td>
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20306_search();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20306_close();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="sysAdmin_20306_datagrid" style="overflow:hidden"></table>
	</div>
</div>
