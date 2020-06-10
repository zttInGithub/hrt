<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">

	function exportExceldataxmh_bill(){
	    	     $("#check_dealdatailfrom_data_bill").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealData_billExcel.action'
			    	});
	}
		//查询提交
	function check_search_data_bill(type){
	     $('#sysAdmin_check_dealdata_bill').datagrid('load', serializeObject($('#check_dealdatailfrom_data_bill')));
	     var a=<%=request.getParameter("countsum")%>
         var b=<%=request.getParameter("mountsum")%>
	}
	
		//清除表单内容
	function check_close_data_bill() {
		$('#check_dealdatailfrom_data_bill input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_dealdata_bill').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealData_bill.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MaintainDate',
			sortOrder: 'asc',
			idField : 'cuid',
			columns : [[{
				title : '业务员对账总汇编号',
				field : 'cuid',
				width : 100,
				hidden : true
			},{
				title :'机构编号',
				field :'UNNO',
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'销售',
				field :'SALENAME',
				width : 100
			},{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'退款笔数',
				field :'REFUNDCOUNT',
				width : 100
			},{
				title :'退款金额',
				field :'REFUNDAMT',
				width : 100
			},{
				title :'结算金额',
				field :'MNAMT',
				width : 100
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExceldataxmh_bill();
			    }
				}]
//                ,
//				view: detailview,
//			detailFormatter:function(index,row){
//				return '<div id="detailForm1-'+index+'" ></div>';
//			},
//			onExpandRow: function(index,row){
//				 $('#detailForm1-'+index).datagrid({
//					url:'${ctx}/sysAdmin/checkUnitDealData_find1.action?id='+row.MID+'&txn='+$('#xmh_20151txnDay').datebox('getValue')+'&txn1='+$('#xmh_20151txnDay1').datebox('getValue'),
//					singleSelect:true,
//					rownumbers:true,
//					loadMsg:'',
//					height:'auto',
//					columns:[[
//					        {field:'TID',title:'终端号',width:100},
//					        {field:'SALENAME',title:'销售',width:100},
//					        {field:'TXNCOUNT',title:'交易笔数',width:100},
//							{field:'REFUNDCOUNT',title:'退款笔数',width:100},
//							{field:'REFUNDAMT',title:'退款金额',width:100},
//							{field:'TXNAMOUNT',title:'交易金额',width:100},
//							{field:'MNAMT',title:'商户结算金额',width:100}
//						]],
//					onResize:function(){
//							$('#sysAdmin_check_dealdata_bill').datagrid('fixDetailRowHeight',index);
//					},
//					onLoadSuccess:function(){
//							setTimeout(function(){
//								$('#sysAdmin_check_dealdata_bill').datagrid('fixDetailRowHeight',index);
//						},0);
//						}
//					});
//					$('#sysAdmin_check_dealdata_bill').datagrid('fixDetailRowHeight',index);
//				}	
								
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:5px;">
		<form id="check_dealdatailfrom_data_bill" style="padding-left:15%">
			<table class="tableForm" >
				<tr>
						<th>商户名称：</th>
						<td><input id="xmh_rname" name="rname" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>商户号：</th>
						<td><input id="xmh_mid" name="mid" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					    <th>机构编号：</th>
						<td><input id="xmh_unno" name="unno" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			    </tr>  
			    <tr>  
					    <th>交易日期：</th>
						<td><input id="xmh_20151txnDay" class="easyui-datebox" data-options="editable:false" name="txnDay" style="width: 162px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>-&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="xmh_20151txnDay1" class="easyui-datebox" data-options="editable:false" name="txnDay1" style="width: 162px;"/></td>
						
			   <td></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_bill();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_bill();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_check_dealdata_bill" style="overflow: hidden;"></table>
    </div> 
</div>


