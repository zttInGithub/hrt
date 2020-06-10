<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
	$(function(){
		$("#check_transferProfit").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryFirstAgentTransferAndGetCash.action',
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
				title :'商户种类',
				field :'SETTMETHOD',
				width : 100
			},{
				title :'交易金额',
				field :'CASHAMT',
				width : 100
			},{
				title :'交易笔数',
				field :'COUNT',
				width : 100
			},{
				title :'提现手续费',
				field :'SECONDRATE',
				width : 100
			},{
				title :'提现转账分润',
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
					exportExcel_exportFirstAgentTransferAndGetCash();
				}
			}]
		});
	
	});
	function exportExcel_exportFirstAgentTransferAndGetCash(){
		var txnDay = $('#txnDay_20299').datebox('getValue');
   		var txnDay1=$('#txnDay1_20299').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31 || txnDay1.substring(0,7)!=txnDay.substring(0,7)){
	 		$.messager.alert('提示', "开始日期和截止日期之差不能超过31天,且为同一月！");
	    }else{
			$('#check_transferProfitForm').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportFirstAgentTransferAndGetCash.action'});
		}
	}
	
	function check_search_data_firstAgentTransfer(){
		var txnDay = $('#txnDay_20299').datebox('getValue');
   		var txnDay1=$('#txnDay1_20299').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31 || txnDay1.substring(0,7)!=txnDay.substring(0,7)){
	 		$.messager.alert('提示', "开始日期和截止日期之差不能超过31天,且为同一月！");
	    }else{
			$("#check_transferProfit").datagrid('load',serializeObject($("#check_transferProfitForm")));
		}
		
	}
	
	function check_close_data_firstAgentTransfer(){
		$('#check_transferProfitForm input').val('');
	} 
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="overflow:hidden; height:60px; padding-top:15px;" >
		<form id="check_transferProfitForm" style="padding-left: 3%">
			<table>
				<tr>
					<!--<th>机构号</th>
					<td>
						<input  type="text" name="unNo" style="width:150px">
					</td>
					<td style="width: 15px"></td>
					 <th>&nbsp;&nbsp;&nbsp;&nbsp;商户种类</th>
					<td>
						<select name="isM35" style="width: 150px;">
							<option value = "">全部</option>
							<option value="0">0.72秒到</option>
							<option value="1">非0.72秒到</option>
							<option value="2">理财</option>
							<option value="3">传统</option>
							<option value="4">扫码</option>
						</select>
					</td>
				</tr>
				<tr> -->
					<th>交易起始日期</th>
					<td>
						<input id="txnDay_20299" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<th>交易结束日期</th>
					<td>
						<input  id="txnDay1_20299" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_firstAgentTransfer();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_firstAgentTransfer();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="check_transferProfit" style="overflow:hidden"></table>
	</div>
</div>