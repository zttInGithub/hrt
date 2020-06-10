<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10920_datagrid').datagrid({
			url :'#',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				field : 'buid',
				title : '代理商编号',
				checkbox:true
			},{
				title : '机构编号',
				field : 'unno',
				width : 100
			},{
				title : '代理商名称',
				field : 'agentName',
				width : 100
			},{
				title :'经营地址',
				field :'baddr',
				width : 100
			},{
				title :'开通状态',
				field :'openName',
				width : 100
			},{
				title :'缴款状态',
				field :'amountConfirmName',
				width : 100
			},{
				title :'签约人员',
				field :'signUserIdName',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_10140_editFun('+index+')"/>';
				}
			}]]	,
			toolbar:[{
				id:'btn_runAll',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10920_exportAll();
				}
			}]
		});
	});
	function sysAdmin_10920_exportAll(){
		$('#sysAdmin_10920_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportTidUseCount.action'
			    	});
	}

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_10920_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="buids" name="buids" />
			<table class="tableForm" >
					<tr>
						<th>代理商名称</th>
						<td><input name="agentName" style="width: 260px;" /></td>	
						<th>机构号</th>
						<td><input name="unno" style="width: 260px;" /></td>	
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="javascript:void(0);">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="javascript:void(0);">清空</a>	
						</td>
					</tr>
				</table>
			</form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10920_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
