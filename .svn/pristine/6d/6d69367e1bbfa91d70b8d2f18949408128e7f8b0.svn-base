<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

		function exportExcel_tranficcbank(){
	      $("#check_tranficcbank").form('submit',{
			    		url:'${ctx}/sysAdmin/trafficBankInsert_TrafficBankListExport.action'
			    	});
	}
	//查询提交
	function check_search_data_tranf(){
	 $('#tranficcbanklist').datagrid('load', serializeObject($('#check_tranficcbank')));
	}
		//清除表单内容
	function check_close_data_tranf() {
		$('#check_tranficcbank input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#tranficcbanklist').datagrid({
			url :'${ctx}/sysAdmin/trafficBankInsert_TrafficBankList.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MID',
			sortOrder: 'MID',
			idField : 'MID',
			columns : [[{
				title :'商户编号',
				field :'MID',
				width : 200
			},{
				title :'商户名称',
				field :'MERCHNAME',
				width : 100
			},{
				title :'销售员',
				field :'ASALENAME',
				width : 100
			},{
				title :'维护员',
				field :'BSALENAME',
				width : 100
			},{
				title :'内卡交易总金额',
				field :'TXNAMT',
				width : 100
			},{
				title :'内卡交易总笔数',
				field :'COUNTS',
				width : 100
			},{
				title :'外卡交易总金额',
				field :'WLTXNAMT',
				width : 100
			},{
				title :'外卡交易总笔数',
				field :'WLCOUNTS',
				width : 100
			},{
				title :'总交易量 ',
				field :'SUMTXNAMT',
				width : 100
			},{
				title :'交易日期  ',
				field :'TRADEDATE',
				width : 100
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExcel_tranficcbank();
					                  }
				}]			
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:5px;">
		<form id="check_tranficcbank" style="padding-left:15%">
				<table class="tableForm" >
				<tr>
						<th>商户名称：</th>
						<td><input name="merchname" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>商户号：</th>
						<td><input name="mid" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					 
				</tr>
				<tr>
				<th>交易日期：</th>
						<td><input name="createDate"  class="easyui-datebox" data-options="editable:false,required:true" style="width: 162px;"/>
						</td>
						<td>&nbsp;&nbsp;-&nbsp;&nbsp;</td>
						<td><input name="createDateone"  class="easyui-datebox" data-options="editable:false,required:true" style="width: 162px;"/>
						</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_tranf();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_tranf();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="tranficcbanklist" style="overflow: hidden;"></table>
  </div> 
</div>