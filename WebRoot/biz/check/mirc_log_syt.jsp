<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#syt_log_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMicroProfitSytLog.action',
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
				width : 150
			},{
				title : '下级代理名称',
				field : 'UN_NAME',
				width : 150
			},{
				title : '模板名称',
				field : 'TEMPNAME',
				width : 150
			},{
				title : '模板开始时间',
				field : 'STARTDATE',
				width : 150
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
			}/* ,{
				title : '扫码1000以上（终端0.38）费率',
				field : 'CREDITBANKRATE',
				width : 150
			},{
				title : '扫码1000以上（终端0.38）转账费',
				field : 'CASHRATE',
				width : 150
			},{
				title : '扫码1000以上（终端0.45）费率',
				field : 'SCANRATE',
				width : 150
			},{
				title : '扫码1000以上（终端0.45）转账费',
				field : 'PROFITPERCENT1',
				width : 150
			},{
				title : '扫码1000以下（终端0.38）费率',
				field : 'SUBRATE',
				width : 150
			},{
				title : '扫码1000以下（终端0.38）转账费',
				field : 'CASHAMT',
				width : 150
			},{
				title : '扫码1000以下（终端0.45）费率',
				field : 'SCANRATE1',
				width : 150
			},{
				title : '扫码1000以下（终端0.45）转账费',
				field : 'CASHAMT1',
				width : 150
			},{
				title : '银联二维码费率',
				field : 'SCANRATE2',
				width : 150
			},{
				title : '银联二维码转账费',
				field : 'CASHAMT2',
				width : 150
			},{
				title : '花呗费率',
				field : 'HUEBEIRATE',
				width : 150
			},{
				title : '花呗转账费',
				field : 'HUABEIFEE',
				width : 150
			},{
				title : '活动类型',
				field : 'PROFITRULE',
				width : 150
			} */,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="详情" style="cursor:pointer;" onclick="queryDetailSytTemplateLog('+index+')"/>';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
           			 sysAdmin_sytLog_exportFun();
				}
			}]
		});
	});

  //导出
  function sysAdmin_sytLog_exportFun() {
      $('#ProfitSytLog_searchForm').form('submit',{
          url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportMicroProfitSytLog.action'
      });
  }

	//表单查询
	function sysAdmin_SytLog_searchFun() {
		$('#syt_log_datagrid').datagrid('load', serializeObject($('#ProfitSytLog_searchForm')));
	}

	//清除表单内容
	function sysAdmin_SytLog_cleanFun() {
		$('#ProfitSytLog_searchForm input').val('');
	}
	//成本详情
	function queryDetailSytTemplateLog(index){
		var rows = $('#syt_log_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_profitmicroUpdateSytTempLate"/>').dialog({
			title: '<span style="color:#157FCC;">收银台活动月度成本详细信息</span>',
			width: 700,
		    height: 600,
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/check/mirc_log_sytDetail.jsp?tempname='+encodeURIComponent(encodeURIComponent(row.TEMPNAME))+'&unno='+row.UNNO+'&startDate='+row.STARTDATE+'&endDate='+row.ENDDATE,
		    modal: true, 
			/* onClose:function() {
		    	$('#Profitmicro').form('load', row);
			}, */buttons:[{
			    	text:'确认',
			    	iconCls:'icon-ok',
			    	handler:function() {
			    		$('#sysAdmin_profitmicroUpdateSytTempLate').dialog('destroy');
			    	}			    
				}],onClose:function() {
					$(this).dialog('destroy');
			}		
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="ProfitSytLog_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>下级代理机构号：</th>
					<td><input name="unno" style="width: 200px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;模板名称：</th>
					<td><input name="tempName" style="width: 200px;" /></td>
					
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
							 onclick="sysAdmin_SytLog_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
							 onclick="sysAdmin_SytLog_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="syt_log_datagrid" style="overflow: hidden;"></table>
	</div>
</div>

