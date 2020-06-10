<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script>
	$(function(){
		$("#firstAgentProfit_Scan").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryFirstAgentProfitScan.action',
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
				field :'UN_NAME',
				width : 130
			},{
				title :'商户类型',
				field :'SETTMETHOD',
				width : 100
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
					exportExcel_firstAgentProfitScan();
				}
			}]
		});
	});
	
	//数据导出
	function exportExcel_firstAgentProfitScan(){
		var txnDay = $('#txnDay_20298').datebox('getValue');
   		var txnDay1=$('#txnDay1_20298').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
	    	$('#check_firstAgentProfitScan').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportFirstAgentProfitScan.action'});
		}
		
	}
	//查询提交
	function check_search_data_firstAgentScan(){
		var txnDay = $('#txnDay_20298').datebox('getValue');
   		var txnDay1=$('#txnDay1_20298').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
	    	$('#firstAgentProfit_Scan').datagrid('load', serializeObject($('#check_firstAgentProfitScan')));
		}
	 
	}
	//清除表单内容
	function check_close_data_firstAgentScan() {
		$('#check_firstAgentProfitScan input').val('');
		$('#check_firstAgentProfitScan select').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:15px;">
		<form id="check_firstAgentProfitScan" style="padding-left:3%">
			<table>
				<tr>
					<!--<th>机构号</th>
					<td>
						<input type="text" name="unNo" style="width:150px">
					</td>
					<td style="width: 15px"></td>
					 <th>机构名称</th>
					<td>
						<input type="text" name="unitName" style="width:150px">
					</td> 
					<td style="width: 15px"></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;交易类型</th>
					<td>
						<select name="fiid" style="width: 150px;">
							<option value="">全部</option>
							<option value="10">微信</option>
							<option value="11">支付宝</option>
							<option value="18">银联二维码</option>
						</select>
					</td>
				</tr>
				<tr>-->
					<th>交易起始日期</th>
					<td>
						<input id="txnDay_20298" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<th>交易结束日期</th>
					<td>
						<input id="txnDay1_20298" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_firstAgentScan();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_firstAgentScan();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="firstAgentProfit_Scan" style="overflow:hidden"></table>
	</div>
</div>