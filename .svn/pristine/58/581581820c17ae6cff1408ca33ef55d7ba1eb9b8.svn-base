<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

		function exportExcel_20230(){
	      $("#check_dealdatailfrom_20230").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealDetail_exportCheckUnitTxnSumData.action'
			    	});
	}
	//查询提交
	function check_search_data_20230(type){
		$('#sysAdmin_check_unitdealdetail_20230').datagrid('load', serializeObject($('#check_dealdatailfrom_20230')));

	}
		//清除表单内容
	function check_close_data_20230() {
		$('#check_dealdatailfrom_20230 input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_unitdealdetail_20230').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_listCheckUnitTxnSum.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped: true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortOrder: 'asc',
			idField : 'UNNO',
			columns : [[{
				title : '机构编号',
				field : 'UNNO',
				width : 100
			},{
				title :'机构名称',
				field :'UNAME',
				width : 100
			},/* {
				title :'交易日期',
				field :'TXNDAY',
				width : 100
			}, */{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100,
				sortable :true
			},{
				title :'交易金额',
				field :'SUMAMT',
				width : 90,
				sortable :true
			},{
				title :'活跃商户数',
				field :'ACTIVEMERCH',
				width : 100,
				sortable :true
			},{
				title :'利润',
				field :'PROFIT',
				width : 100,
				sortable :true
			},{
				title :'收益率(‰)',
				field :'RATE',
				width : 100,
				sortable :true
			}]]	,
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
				exportExcel_20230();
				                  }
			}]		
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:5px;">
		<form id="check_dealdatailfrom_20230" style="padding-left:15%">
			<table class="tableForm" >
				<tr>
						<th>机构名称：</th>
						<td><input  name="unitName" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>机构编号：</th>
						<td><input name="unNo" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
						<th>交易日期：</th>
						<td><input  class="easyui-datebox" id="txnDay" name="txnDay" required="true" style="width: 162px;"/>至</td>
						
						<td><input  class="easyui-datebox" id="txnDay1" name="txnDay1" required="true" style="width: 162px;"/></td>
						<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_20230();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_20230();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="sysAdmin_check_unitdealdetail_20230" style="overflow: hidden;"></table>
  </div> 
</div>