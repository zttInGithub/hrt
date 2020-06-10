<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#dh_log_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMicroProfitDhLog.action',
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
				title : '代还费率',
				field : 'SUBRATE',
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
			}]],toolbar:[{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
            sysAdmin_dhLog_exportFun();
				}
			}]
		});
	});

  //导出
  function sysAdmin_dhLog_exportFun() {
      $('#dh_ProfitDhLog_searchForm').form('submit',{
          url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportMicroProfitDhLog.action'
      });
  }

	//表单查询
	function sysAdmin_DhLog_searchFun() {
		$('#dh_log_datagrid').datagrid('load', serializeObject($('#dh_ProfitDhLog_searchForm')));
	}

	//清除表单内容
	function sysAdmin_DhLog_cleanFun() {
		$('#dh_ProfitDhLog_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="dh_ProfitDhLog_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>下级代理机构号：</th>
					<td><input name="unno" style="width: 200px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;模板名称：</th>
					<td><input name="tempName" style="width: 200px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
							 onclick="sysAdmin_DhLog_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
							 onclick="sysAdmin_DhLog_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="dh_log_datagrid" style="overflow: hidden;"></table>
	</div>
</div>


