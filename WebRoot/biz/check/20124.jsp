<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">
	//清除表单内容
	function check_20124_data() {
		$('#check_20124_dealdatailfrom input').val('');
	}
	function exportExce_20124(){
  		$("#check_20124_dealdatailfrom").form('submit',{
	    	url:'${ctx}/sysAdmin/checkUnitDealData_exportIsM35Rebate.action'
	    });
	}
	//查询提交
	function check_20124_data(){
	   	$('#sysAdmin_20124_datagrid').datagrid('load', serializeObject($('#check_20124_dealdatailfrom')));
	}
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20124_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_queryIsM35Rebate.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'UNNO',
			columns : [[{
				title : 'ID',
				field : 'IRID',
				width : 100,
				hidden : true
			},{
				title :'商户号',
				field :'MID',
				width : 80
			},{
				title :'SN号',
				field :'SN',
				width : 130
			},{
				title :'返利日期',
				field :'REBATEDATE',
				width : 110
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 80
			},{
				title :'导入时间',
				field :'CDATE',
				width : 100
			}]],
			toolbar:[{
					text:'导出已查数据',
					iconCls:'icon-query-export',
					handler:function(){
						exportExce_20124();
					}
			}]	
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="check_20124_dealdatailfrom" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>导入日期：</th>
						<td colspan="3"><input id="xmh20123_txnDate" class="easyui-datebox" data-options="editable:false" name="txnDate" style="width: 140px;"/>
						&nbsp;-&nbsp;
						<input id="xmh20123_txnDate1" class="easyui-datebox" data-options="editable:false" name="txnDate1" style="width: 138px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				       <td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20124_data();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20124_data();">清空</a>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_20124_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

