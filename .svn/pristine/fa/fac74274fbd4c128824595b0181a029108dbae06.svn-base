<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_TraditionCashTransferSum_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitProfitTradit_queryTraditionCashTransferSum.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'profit',
			sortOrder: 'desc',
			columns : [[{
				title :'机构名称',
				field :'UNITNAME',
				width : 100
			},
			{
				title :'结算类型',
				field :'CASHMODE',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='4'){
						return "刷卡";
					}else if(value=='5'){
						return "扫码";
					}
				}
			},{
				title :'提现总笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'提现总金额',
				field :'CASHFEE',
				width : 100
			},{
				title :'手续费总金额',
				field :'CASHAMT',
				width : 100
			},{
				title :'分润总金额',
				field :'PROFIT',
				width : 100
			}]]
		});
	});
	
	//表单查询
	function sysAdmin_TraditionCashTransferSum_searchFun80() {
		var beginDate = $('#txnDay_TraditionCashTransferSum').datebox('getValue');
		var endDate = $('#txnDay1_TraditionCashTransferSum').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if((txnDay1-txnDay)/(24*3600*1000)>31 || beginDate.substring(0,7)!=endDate.substring(0,7)){
			$.messager.alert('提示','查询日期之差不能大于31天,且为同一月');
		}else{
			$('#sysAdmin_TraditionCashTransferSum_datagrid').datagrid('load', serializeObject($('#sysAdmin_TraditionCashTransferSum_searchForm'))); 
		}
		
	}

	//清除表单内容
	function sysAdmin_TraditionCashTransferSum_cleanFun80() {
		$('#sysAdmin_TraditionCashTransferSum_searchForm input').val('');
	}
$(function() {
			$('#unno_TraditionCashTransferSum').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});

});
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_TraditionCashTransferSum_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td>
						<select name="unno" id="unno_TraditionCashTransferSum" class="easyui-combogrid" style="width:205px;"></select>
					</td>
						<th>查询日期：</th>
						<td><input  class="easyui-datebox" id="txnDay_TraditionCashTransferSum" name="txnDay"  style="width: 162px;"/>至</td>
						<td><input  class="easyui-datebox" id="txnDay1_TraditionCashTransferSum" name="txnDay1"  style="width: 162px;"/></td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_TraditionCashTransferSum_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_TraditionCashTransferSum_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_TraditionCashTransferSum_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


