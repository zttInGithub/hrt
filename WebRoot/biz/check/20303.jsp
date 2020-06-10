<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--刷卡交易分如汇总表-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#firstAgentProfitCollect_20303').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryfirstAgentProfitCollect_20303.action',
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
				field :'MDA',
				width : 100
			},{
				title :'交易笔数',
				field :'COUNT',
				width : 100
			},{
				title :'手续费',
				field :'CHARGE',
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
					exportExcel_swipProfitSummarySheet_20303();
				}
			}]
		});
	});
	/* $("#unno_20297").combogrid({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action?unLvl=1,2',
			idField:'UNNO',
			textField:'UNNO',
			mode:'remote',
			
			fitColumns:true,
			//pagination : true,
            //rownumbers:true,  
            collapsible:false,  
            fit: false,  
           // pageSize: 10,  
            //pageList: [10,15],
			columns:[[ 
				{field:'UNNO',title:'机构号',width:50},
				{field:'UN_NAME',title:'机构名称',width:100}
			]]
		}); */
	
	 function exportExcel_swipProfitSummarySheet_20303(){
	    var txnDay = $('#txnDay_20303').datebox('getValue');
   		var txnDay1=$('#txnDay1_20303').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
	    	$('#check_firstAgentProfitCollect_20303').form('submit',{
				url :'${ctx}/sysAdmin/checkUnitDealDetail_exportFirstAgentProfitCollect20303.action'
			});
		}
		
	} 
		
	//查询提交
	function check_search_data_firstCollection_20303(){
		var txnDay = $('#txnDay_20303').datebox('getValue');
   		var txnDay1=$('#txnDay1_20303').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else if(((end-start)/(24*60*60*1000))<0){
	    	$.messager.alert('提示', "开始日期  和   截止日期  选择有误");
	    }else{
	    	$('#firstAgentProfitCollect_20303').datagrid('load', serializeObject($('#check_firstAgentProfitCollect_20303')));
		}
	 
	}
	//清除表单内容
	function check_close_data_firstCollection_20303() {
		$('#check_firstAgentProfitCollect_20303 input').val('');
		$('#check_firstAgentProfitCollect_20303 select').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:15px;">
		<form id="check_firstAgentProfitCollect_20303" style="padding-left:3%">
			<table>
				<tr>
					<!--<th>机构号</th>
					<td >
						<input type="text"  name="unNo" style="width: 150px;" >
					</td>
					 <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构名</th>
					<td>
						<input type ="text" name="unitName" style="width:150px;">
					</td>
					<th style="width: 100px">商户种类</th>
					<td>
						<select name="isM35" style="width: 245px;">
							<option value="">全部</option>
							<option value="0">0.72秒到</option>
							<option value="1">非0.72秒到</option>
							<option value="2">理财</option>
							<option value="3">传统</option>
							<option value="4">云闪付</option>
						</select>
					</td>
				</tr>
				<tr> -->
					<th>&nbsp;&nbsp;&nbsp;&nbsp;交易开始日期</th>
					<td>
						<input id="txnDay_20303" name="txnDay" class="easyui-datebox" data-options="editable:false,required:true" style="width: 155px">
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;交易结束日期</th>
					<td>
						<input id="txnDay1_20303" name="txnDay1" class="easyui-datebox" data-options="editable:false,required:true" style="width: 155px">
					</td>
					<td></td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_firstCollection_20303();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_firstCollection_20303();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="firstAgentProfitCollect_20303" style="overflow: hidden"></table>
	</div>
</div>