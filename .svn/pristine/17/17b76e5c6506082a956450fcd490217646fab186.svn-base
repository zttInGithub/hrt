<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
	$(function(){
		$("#check_swipProfitDetail").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryFirstAgentSwipProfitDetail.action',
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
				title :'商户种类',
				field :'SETTMETHOD',
				width : 100
			},{
				title :'维护销售',
				field :'SALENAME',
				width : 100
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'交易笔数',
				field :'COUNT',
				width : 100
			},{
				title :'商户手续费',
				field :'BANKFEERATE',
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
				handler : function(){
					exportExcel_exportSwipProfitDetail();
				}
			}]
		});
	
	});
	
	function exportExcel_exportSwipProfitDetail(){
		var txnDay = $('#txnDay_20300').datebox('getValue');
   		var txnDay1=$('#txnDay1_20300').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
			$("#check_swipProfitDetailForm").form('submit',{
				url:'${ctx}/sysAdmin/checkUnitDealDetail_exportFirstAgentProfitCollectDetail.action'
			});
		}
		
	}
	
	function check_search_data_firstAgentSwip(){
		var txnDay = $('#txnDay_20300').datebox('getValue');
   		var txnDay1=$('#txnDay1_20300').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
			$("#check_swipProfitDetail").datagrid('load',serializeObject($("#check_swipProfitDetailForm")));
		}
		
	}
	
	function check_close_data_firstAgentASwip(){
		$('#check_swipProfitDetailForm input').val('');
		$('#check_swipProfitDetailForm select').val('');
	} 
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="overflow:hidden; height:100px; padding-top:15px;" >
		<form id="check_swipProfitDetailForm" style="padding-left: 5%">
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
					<td style="width: 15px"></td> -->
					<th>&nbsp;&nbsp;&nbsp;&nbsp;商户号</th>
					<td>
						<input type="text" name="mid">
					</td>
					<!-- <th>&nbsp;&nbsp;&nbsp;&nbsp;商户名</th>
					<td>
						<input type="text" name="rname">
					</td> -->
					<td style="width: 15px"></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;商户种类</th>
					<td>
						<select name="isM35" style="width: 150px;">
							<option value="" >全部</option>
							<option value="2">理财</option>
							<option value="3">传统</option>
							<option value="4">云闪付</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>交易起始日期</th>
					<td>
						<input id="txnDay_20300" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<th>交易结束日期</th>
					<td>
						<input id="txnDay1_20300" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
				
					<td style="width: 15px"></td>
					<td></td>
					<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_firstAgentSwip();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_firstAgentASwip();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="check_swipProfitDetail" style="overflow:hidden"></table>
	</div>
</div>
