<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20250_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRealtime_searchTodayDealDatas.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'TOTALTXNAMOUNT',
			sortOrder: 'DESC',
			idField : 'TOTALTXNAMOUNT',
			columns : [[{
				title :'机构',
				field :'UNNOLVL',
				width : 200,
				sortable:true,
			},{
				title :'交易总金额',
				field :'TOTALTXNAMOUNT',
				width : 200,
				sortable:true
			},{
				title :'交易总笔数',
				field :'TOTALTXNCOUNT',
				width : 200,				
				sortable:true
			},{
				title :'活跃商户数',
				field :'TOTALCOUNT',
				width : 200,
				sortable:true
			}]],
		}); 
	});
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false" >
    <div data-options="region:'center', border:false" style="overflow: hidden;padding-top:25px;">  
        <table id="sysAdmin_20250_datagrid"></table>
    </div> 
</div>

