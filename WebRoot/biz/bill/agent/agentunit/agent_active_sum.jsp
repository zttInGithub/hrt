<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function exportExcel_agent_active_sum(){
		var txnDay = $('#cashDay').datebox('getValue');
		var txnDay1 = $('#cashDay1').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$("#query_agent_active_sum_form").form('submit',{
		    		url:'${ctx}/sysAdmin/agentunit_exportQueryAgentActiveSum.action'
		    	});
			} else {
				$.messager.alert('提示', '查询日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择查询日期');
		}
	}
	//查询提交
	function query_agent_active_sum(){
		var beginDate = $('#cashDay').datebox('getValue');
		var endDate = $('#cashDay1').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if((txnDay1-txnDay)/(24*3600*1000)>31 || beginDate.substring(0,7)!=endDate.substring(0,7)){
			$.messager.alert('提示','查询日期之差不能大于31天,且为同一月');
		}else{
			$('#sysAdmin_agent_active_sum').datagrid('load', serializeObject($('#query_agent_active_sum_form')));
		}
	}
		//清除表单内容
	function clean_agent_active_sum() {
		$('#query_agent_active_sum_form input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agent_active_sum').datagrid({
			url :'${ctx}/sysAdmin/agentunit_queryAgentActiveSum.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped: true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortOrder: 'asc',
			idField : 'YIDAI',
			columns : [[{
				title : '机构编号',
				field : 'YIDAI',
				width : 100
			},{
				title :'机构名称',
				field :'AGENTNAME',
				width : 100
			},{
				title :'产品类型',
				field :'PRODUCTTYPE',
				width : 100
			},{
				title :'注册商户数',
				field :'FLAG1',
				width : 100
			},{
				title :'注册终端数',
				field :'FLAG2',
				width : 100
			},{
				title :'激活数',
				field :'FLAG3',
				width : 100
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_agent_active_sum();
				}
			}] 	
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:5px;">
		<form id="query_agent_active_sum_form" style="padding-left:15%" method="post">
			<table class="tableForm" >
				<tr>
						<th>机构名称：</th>
						<td><input  name=agentName style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>机构编号：</th>
						<td><input name="unno" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
						<th>注册日期：</th>
						<td><input  class="easyui-datebox" id="cashDay" name="cashDay" required="true" style="width: 162px;"/>至</td>
						
						<td><input  class="easyui-datebox" id="cashDay1" name="cashDay1" required="true" style="width: 162px;"/></td>
						<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="query_agent_active_sum();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="clean_agent_active_sum();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="sysAdmin_agent_active_sum" style="overflow: hidden;"></table>
  </div> 
</div>