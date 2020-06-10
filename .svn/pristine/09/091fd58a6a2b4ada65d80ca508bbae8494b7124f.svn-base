<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--设备状态统计 -->
<script type="text/javascript">

	//初始化datagrid
	$(function(){
		$('#sysAdmin_10978_datagrid').datagrid({
			url :'${ctx}/sysAdmin/producttypeInRebatetypeAction_queryTerminalStatisticsForRebatetype.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title :'活动类型',
				field :'REBATETYPE',
				width : 100
			},{
				title :'已发货待注册',
				field :'DELIVERYNOREGISTERNUMBER',
				width : 100
			},{
				title :'已注册待激活',
				field :'REGISTERNOACTIVATIONNUMBER',
				width : 100
			},{
				title :'激活量',
				field :'ACTIVATIONNUMBER',
				width : 100
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_10978();
				}
			}]
		});
	});
	
	//数据导出
	function exportExcel_10978(){
		var txnDay = $('#txnDay_10978').datebox('getValue');
		var txnDay1 = $('#txnDay1_10978').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
		    	$('#sysAdmin_10978_searchForm').form('submit',{url:'${ctx}/sysAdmin/producttypeInRebatetypeAction_exportTerminalStatisticsForRebatetype.action'});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	
	//表单查询
	function sysAdmin_10978_searchFun80() {
		var txnDay = $('#txnDay_10978').datebox('getValue');
		var txnDay1 = $('#txnDay1_10978').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$('#sysAdmin_10978_datagrid').datagrid('load', serializeObject($('#sysAdmin_10978_searchForm')));
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}

	//清除表单内容
	function sysAdmin_10978_cleanFun80() {
		$('#sysAdmin_10978_searchForm input').val('');
	}

	$(function(){
		$('#rebatetype_10978').combogrid({
			url : '${ctx}/sysAdmin/producttypeInRebatetypeAction_queryRebatetype.action',
			idField:'REBATETYPE',
			textField:'REBATETYPE',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'REBATETYPE',title:'活动类型',width:250}
			]] 
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10978_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;&nbsp;查询日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_10978" name="txnDay"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_10978" name="txnDay1"  style="width: 162px;"/></td>
					<th>&nbsp;&nbsp;活动类型</th>
	    			<td><select id="rebatetype_10978" name="rebatetype" class="easyui-combogrid" data-options="validType:'spaceValidator'" style="width:135px;"></select></td>	
					<td colspan="10">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10978_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10978_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10978_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


