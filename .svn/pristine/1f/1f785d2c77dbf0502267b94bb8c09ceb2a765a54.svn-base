<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_RealTimeDate_top').datagrid({
			url :'${ctx}/sysAdmin/checkRealtime_queryRealTimeTop10.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MID',
			sortOrder: 'asc',
			idField : 'MID',
			columns : [[{
				title :'商户编号',
				field :'MID',
				width : 140
			},{
				title :'所属机构',
				field :'UN_NAME',
				width : 90
			},{
				title :'商户名称',
				field :'RNAME',
				width : 90
			},{
				title :'交易日期',
				field :'TXNDAY',
				width : 90
			},{
				title :'交易金额',
				field :'TXNAMOUNT', 
				width : 80
			}]],toolbar:[]
		});
	});
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:0px; overflow: hidden; padding-top:0px;">
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_check_RealTimeDate_top" style="overflow: hidden;"></table>
    </div> 
</div>



