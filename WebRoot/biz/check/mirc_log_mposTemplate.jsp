<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#mposTemplate_log_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMicroProfitMposTemplateLog.action',
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
					return '<img src="${ctx}/images/query_search.png" title="详情" style="cursor:pointer;" onclick="queryDetailMposTemplateLog('+index+')"/>';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
            		sysAdmin_MposTemplateLog_exportFun();
				}
			}]
		});
	});

  //导出
  function sysAdmin_MposTemplateLog_exportFun() {
      $('#ProfitMposTemplateLog_searchForm').form('submit',{
          url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportMicroProfitMposTemplateLog.action'
      });
  }

	//表单查询
	function sysAdmin_MposTemplateLog_searchFun() {
		$('#mposTemplate_log_datagrid').datagrid('load', serializeObject($('#ProfitMposTemplateLog_searchForm')));
	}

	//清除表单内容
	function sysAdmin_MposTemplateLog_cleanFun() {
		$('#ProfitMposTemplateLog_searchForm input').val('');
	}
	
	//成本详情
	function queryDetailMposTemplateLog(index){
		var rows = $('#mposTemplate_log_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_profitmicroUpdateMposTempLate"/>').dialog({
			title: '<span style="color:#157FCC;">MPOS活动月度成本详细信息</span>',
			width: 700,
		    height: 600,
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/check/mirc_log_mposTempDetail.jsp?tempname='+encodeURIComponent(encodeURIComponent(row.TEMPNAME))+'&unno='+row.UNNO+'&startDate='+row.STARTDATE+'&endDate='+row.ENDDATE,
		    modal: true, 
			/* onClose:function() {
		    	$('#Profitmicro').form('load', row);
			}, */buttons:[{
			    	text:'确认',
			    	iconCls:'icon-ok',
			    	handler:function() {
			    		$('#sysAdmin_profitmicroUpdateMposTempLate').dialog('destroy');
			    	}			    
				}],onClose:function() {
					$(this).dialog('destroy');
			}		
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="ProfitMposTemplateLog_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>下级代理机构号：</th>
					<td><input name="unno" style="width: 200px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;模板名称：</th>
					<td><input name="tempName" style="width: 200px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
							 onclick="sysAdmin_MposTemplateLog_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
							 onclick="sysAdmin_MposTemplateLog_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="mposTemplate_log_datagrid" style="overflow: hidden;"></table>
	</div>
</div>


