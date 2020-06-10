<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#tradit_log_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitProfitTradit_queryTraditProfitLog.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			// ctrlSelect:true,
			// checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title : '下级代理机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '下级代理名称',
				field : 'TEMPNAME',
				width : 100
			},{
				title : '模板开始时间',
				field : 'MATAINDATE',
				width : 100
			},{
				title : '模板结束时间',
				field : 'ENDDATE',
				width : 100,formatter : function(value,row,index) {
					if(!value){
						return "至今";
					}else{
						return value;
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="queryFunTraditLogDetail('+index+')"/>&nbsp;&nbsp';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
            sysAdmin_traditLog_exportFun();
				}
			}]
		});
	});

  //导出
  function sysAdmin_traditLog_exportFun() {
      $('#ProfitTraditLog_searchForm').form('submit',{
          url:'${ctx}/sysAdmin/checkUnitProfitTradit_exportProfitTraditLog.action'
      });
  }

	//表单查询
	function sysAdmin_ProfitTraditLog_searchFun() {
		$('#tradit_log_datagrid').datagrid('load', serializeObject($('#ProfitTraditLog_searchForm')));
	}

	//清除表单内容
	function sysAdmin_ProfitTraditLog_cleanFun() {
		$('#ProfitTraditLog_searchForm input').val('');
	}

	//查看明细
	function queryFunTraditLogDetail(index){
		var rows = $('#tradit_log_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_tradot_log_run"/>').dialog({
			title: '查看明细',
			width: 900,
			height: 500,
			closed: false,
			href:'${ctx}/biz/check/tradit_log_detail.jsp?unno='+row.UNNO+'&startDate='+row.MATAINDATE,
			modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="ProfitTraditLog_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>下级代理机构号：</th>
					<td><input name="unno" style="width: 200px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;下级代理名称：</th>
					<td><input name="tempName" style="width: 200px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
							 onclick="sysAdmin_ProfitTraditLog_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
							 onclick="sysAdmin_ProfitTraditLog_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="tradit_log_datagrid" style="overflow: hidden;"></table>
	</div>
</div>


