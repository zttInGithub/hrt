<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 钱包提现记录 -->
<script type="text/javascript">

	//导出
	function exportExceldata(){
	    $("#dealdatailfrom_data_gen01").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealDetail_exportWalletCash.action'
			    	});
	}
		//清除表单内容
	function check_close_data_gen(){
		$('#dealdatailfrom_data_gen01 input').val('');
	}
	//查询提交
	function check_search_data_gen(){
	    $('#sysAdmin_dealdata_gen01').datagrid('load', serializeObject($('#dealdatailfrom_data_gen01')));
	}
	//初始化datagrid
	$(function() {
		$('#sysAdmin_dealdata_gen01').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryWalletCash.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'PCID',
			sortOrder: 'asc',
			idField : 'PCID',
			columns : [[{
				title :'MID',
				field :'MID',
				width : 90			
			},{
				title :'提现金额',
				field :'CASHAMT',
				width : 90
			},{
				title :'提现日期',
				field :'CASHDAY',
				width : 90
			},{
				title :'提现手续费',
				field :'CASHFEE',
				width : 140
			},{
				title :'提现时间',
				field :'CASHDATE',
				width : 90
			}]]	,
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
				exportExceldata();
				                  }
			}]
		});
	});
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="dealdatailfrom_data_gen01" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
					    <th>MID：</th>
						<td><input id="mid"  class="easyui-validatebox" name="mid" style="width: 156px;"/></td>			        
					    <th>提现日期：</th>
						<td><input id="txnDay" class="easyui-datebox" data-options="editable:false" name="txnDay" style="width: 162px;"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="txnDay1" class="easyui-datebox" data-options="editable:false" name="txnDay1" style="width: 162px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>	
				<td></td><td></td><td></td>		      
				     <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_gen();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_gen();">清空</a>	
					</td>
			    </tr>				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_dealdata_gen01" style="overflow: hidden;"></table>
    </div> 
</div>