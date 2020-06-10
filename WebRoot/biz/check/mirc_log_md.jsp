<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#md_log_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMicroProfitMdLog.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title : '下级代理机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '下级代理名称',
				field : 'UN_NAME',
				width : 100
			},{
				title : '模板名称',
				field : 'TEMPNAME',
				width : 100
			},{
				title : '模板开始时间',
				field : 'STARTDATE',
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
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="queryMicrMdLogDetail('+index+')"/>&nbsp;&nbsp';
				}
			}]],toolbar:[{
					id:'btn_add',
					text:'导出20200101前',
					iconCls:'icon-query-export',
					handler:function(){
            			sysAdmin_mdLog_exportFun1();
					}
			},{
					id:'btn_addnew',
					text:'导出20200101后',
					iconCls:'icon-query-export',
					handler:function(){
	            		sysAdmin_mdLog_exportFun2();
					}
			}]
		});
	});

	//查看明细
	function queryMicrMdLogDetail(index){
		var rows = $('#md_log_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_mic_log_md_run"/>').dialog({
			title: '查看明细',
			width: 900,
			height: 700,
			closed: false,
			href:'${ctx}/biz/check/mirc_log_md_detial.jsp?unno='+row.UNNO+'&startDate='+row.STARTDATE,
			modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}

  //导出20200101前
  function sysAdmin_mdLog_exportFun1() {
      $('#md_ProfitmdLog_searchForm').form('submit',{
          url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportMicroProfitmdLog.action'
      });
  }
  //导出20200101后
  function sysAdmin_mdLog_exportFun2() {
      $('#md_ProfitmdLog_searchForm').form('submit',{
          url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportMicroProfitmdLogNew.action'
      });
  }

	//表单查询
	function sysAdmin_mdLog_searchFun() {
		$('#md_log_datagrid').datagrid('load', serializeObject($('#md_ProfitmdLog_searchForm')));
	}

	//清除表单内容
	function sysAdmin_mdLog_cleanFun() {
		$('#md_ProfitmdLog_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="md_ProfitmdLog_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>下级代理机构号：</th>
					<td><input name="unno" style="width: 200px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;模板名称：</th>
					<td><input name="tempName" style="width: 200px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
							 onclick="sysAdmin_mdLog_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
							 onclick="sysAdmin_mdLog_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="md_log_datagrid" style="overflow: hidden;"></table>
	</div>
</div>

