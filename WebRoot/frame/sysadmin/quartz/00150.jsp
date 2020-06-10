<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--任务监控--%>
<script type="text/javascript">

	$(function(){
		$('#sysAdmin_00150_datagrid').datagrid({
			url : '${ctx}/quartz/quartz_queryQrtzTriggers.action',
			fit : true,
			frozen:true,
			striped:true,
			fitColumns:true,
			border:false,
			nowrap : true,
			rownumbers:true,
			columns:[[{
				title:'任务名称',
				field:'DISPLAY_NAME',
				width:100		
			},{
				title:'任务分组',
				field:'TRIGGER_GROUP',
				width:100
			},{
				title:'下次执行时间',
				field:'NEXT_FIRE_TIME',
				width:100
			},{
				title:'上次执行时间',
				field:'PREV_FIRE_TIME',
				width:100
			},{
				title:'任务状态',
				field:'DISPLAY_STATE',
				width:100
			},{
				title:'创建时间',
				field:'START_TIME',
				width:100
			},{
				title:'结束时间',
				field:'END_TIME',
				width:100
			}]]
		});
	});
	
</script>

<table id="sysAdmin_00150_datagrid" style="overflow:hidden;" ></table>