<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 代理商资料 -->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_104101_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentUnitData1.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
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
				title :'开通状态',
				field :'openName',
				width : 100
			},{
				title :'开通日期',
				field :'openDate',
				width : 100,
				sortable : true
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
				id:'btn_run',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_agentunit_exportFun();
				}
			}]
		});
	});
	function sysAdmin_agentunit_exportAll(){
		$('#sysAdmin_104101_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_exportAgents1.action'
			    	});
	}
	
	function sysAdmin_agentunit_exportFun() {
		var checkedItems = $('#sysAdmin_104101_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.buid);
		});               
		var buids=names.join(",");
		if(buids==null||buids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#buids").val(buids);
		$('#sysAdmin_104101_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_export.action'
			    	});
	}
	
	//查看明细
	function sysAdmin_10140_editFun(index){
    	var rows = $('#sysAdmin_104101_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_104101_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 950,
		    height:430, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/10141.jsp?buid='+row['buid'],  
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10141_queryForm').form('load', row);
			},
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_104101_datagrid').datagrid('unselectAll');
			}
		});
	}
	function bill_agentunitdata_searchFun() {
		$('#sysAdmin_104101_datagrid').datagrid('load', serializeObject($('#sysAdmin_104101_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata_cleanFun() {
		$('#sysAdmin_104101_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_104101_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="buids" name="buids" />
			<table class="tableForm" >
				<tr>
					<th>代理商名称</th>
					<td><input name="agentName" style="width: 260px;" /></td>	
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="bill_agentunitdata_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="bill_agentunitdata_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_104101_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
