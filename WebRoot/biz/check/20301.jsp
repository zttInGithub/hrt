<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
	$(function(){
		$("#check_scanProfitDetail").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryFirstAgentScanProfitDetail.action',
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
				title :'交易类型',
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
					exportExcel_firstAgentProfitScanDetail();
				}
			}] 
		});
	
	});
	
	//导出
	function exportExcel_firstAgentProfitScanDetail(){
		var txnDay = $('#txnDay_20301').datebox('getValue');
   		var txnDay1=$('#txnDay1_20301').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
			$("#check_scanProfitDetailForm").form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportFirstAgentScanProfitDetail.action'});
		}
			
	}
	
	function check_search_data_firstAgentScanDetail(){
		var txnDay = $('#txnDay_20301').datebox('getValue');
   		var txnDay1=$('#txnDay1_20301').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
			$("#check_scanProfitDetail").datagrid('load',serializeObject($("#check_scanProfitDetailForm")));
		}
		
	}
	
	function check_close_data_firstAgentScanDetail(){
		$('#check_scanProfitDetailForm input').val('');
		$('#check_scanProfitDetailForm select').val('');
	} 
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="overflow:hidden; height:100px; padding-top:15px;" >
		<form id="check_scanProfitDetailForm" style="padding-left: 5%">
			<table>
				<tr>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;商户号</th>
					<td>
						<input type="text" name="mid">
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;商户类型</th>
					<td>
						<select name="isM35" style="width: 100px;">
							<option value="" selected="selected">全部</option>
							<option value="1">手刷</option>
							<option value="0">传统</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;交易起始日期</th>
					<td>
						<input id="txnDay_20301" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;交易结束日期</th>
					<td>
						<input id="txnDay1_20301" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
					
					<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_firstAgentScanDetail();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_firstAgentScanDetail();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="check_scanProfitDetail" style="overflow:hidden"></table>
	</div>
</div>
