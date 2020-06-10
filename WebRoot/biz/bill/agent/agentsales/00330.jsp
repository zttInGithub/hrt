<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_00330_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentsales_listAgentSales.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'busid',
			sortOrder: 'desc',
			idField : 'busid',
			columns : [[{
				field : 'busid',
				checkbox:true
			},{
				title :'机构号',
				field :'unno',
				width : 100
			},{
				title :'机构名称',
				field :'unnoName',
				width : 100
			},{
				title :'姓名',
				field :'saleName',
				width : 100
			},{
				title : '手机号码',
				field : 'phone',
				width : 100
			},{
				title :'电话',
				field :'telephone',
				width : 100
			},{
				title :'邮箱',
				field :'email',
				width : 100
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_agentsales_exportFun();
				}
			}]
		});
	});
	function sysAdmin_agentsales_exportFun() {
		var checkedItems = $('#sysAdmin_00330_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.busid);
		});               
		var busids=names.join(",");
		if(busids==null||busids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#busids").val(busids);
		$('#sysAdmin_00330_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentsales_export.action'
			    	});
	}
	
	//表单查询
	function sysAdmin_00330_searchFun() {
		$('#sysAdmin_00330_datagrid').datagrid('load', serializeObject($('#sysAdmin_00330_searchSalesForm'))); 
	}

	//清除表单内容
	function sysAdmin_00330_cleanFun() {
		$('#sysAdmin_00330_searchSalesForm input').val('');
	}
	
</script>

<form id="sysAdmin_00330_searchForm" style="padding-left:10%">
	<input type="hidden" id="busids" name="busids" />
</form>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_00330_searchSalesForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>姓名：</th>
					<td><input name="saleName" style="width: 316px;" /></td>
					
				    <td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_00330_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_00330_cleanFun();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_00330_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>

