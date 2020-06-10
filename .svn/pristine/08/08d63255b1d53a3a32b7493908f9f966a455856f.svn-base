<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20240_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_searchYesDealDatas.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'totalTxnCount',
			sortOrder: 'DESC',
			idField : 'CUID',
			columns : [[{
				title : 'CUID',
				field : 'CUID',
				width : 200,
				hidden : true
			},{
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
	

	//表单查询
	function sysAdmin_20240_searchFun() {
	var txnDay = $('#xtxnDay').datebox('getValue');
    var txnDay1= $('#xtxnDay1').datebox('getValue');   
       if(txnDay==null||txnDay==""){
        $.messager.alert('提示', "请选择起始日期 ！");
       }
       else if(txnDay1==null||txnDay1==""){
        $.messager.alert('提示', "请选择结束日期 ！");
       }
       else{    
		$('#sysAdmin_20240_datagrid').datagrid('load', serializeObject($('#sysAdmin_20240_searchForm')));
		 }
	}

	//清除表单内容
	function sysAdmin_20240_cleanFun() {
		$('#sysAdmin_20240_searchForm input').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:25px;">
		<form id="sysAdmin_20240_searchForm" style="padding-left:10%">
			<input type="hidden" id="ids" name="ids" />
			<table class="tableForm" >
				<tr>
				<th>交易日期：</th>
					<td><input id="xtxnDay" name="txnDayStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input id="xtxnDay1" name="txnDayEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20240_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20240_cleanFun();">清空</a>	
					</td>
				</tr>		
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_20240_datagrid"></table>
    </div> 
</div>

