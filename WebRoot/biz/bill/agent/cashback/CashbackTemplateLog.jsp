<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#unno_cashbackTemplateLog').combogrid({
		url : '${ctx}/sysAdmin/cashbackTemplate_getChildrenUnit.action',
		idField:'UNNO',
		textField:'UN_NAME',
		mode:'remote',
		fitColumns:true,
		columns:[[
			{field:'UN_NAME',title:'机构名称',width:150}			
		]]
	});
	
		$('#sysAdmin_cashbackTemplateLog_datagrid').datagrid({
			url :'${ctx}/sysAdmin/cashbackTemplate_queryCashbackTemplateLog.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			singleSelect:true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'UNNO',
			sortOrder: 'desc',
			columns : [[{
				title :'下级代理机构号',
				field :'UNNO',
				width : 100
			},{
				title :'下级代理机构名称',
				field :'UNNONAME',
				width : 100
			},{
				title :'活动编号',
				field :'REBATETYPE',
				width : 100
			},{
				title :'返现类型',
				field :'CASHBACKTYPE',
				width : 100
			},{
				title :'下级代理可分比例',
				field :'CASHBACKRATIO',
				width : 100
			},{
				title :'起始时间',
				field :'STARTDATE',
				width : 100
			},{
				title :'结束时间',
				field :'ENDDATE',
				width : 100,
				formatter : function(value,row,index) {
					if(value==null){
						return "-";
					}
					return value;
				}
			}]
			],toolbar: [{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-xls',
				handler:function(){
					report_cashbackTemplateLog();
				}
			}]	
		});
	});
	
	//表单查询
	function sysAdmin_cashbackTemplateLog_searchFun80() {
		$('#sysAdmin_cashbackTemplateLog_datagrid').datagrid('load', serializeObject($('#sysAdmin_cashbackTemplateLog_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_cashbackTemplateLog_cleanFun80() {
		$('#sysAdmin_cashbackTemplateLog_searchForm input').val('');
	}
	
	//导出
    function report_cashbackTemplateLog(){
        $('#sysAdmin_cashbackTemplateLog_searchForm').form('submit',{
            url:'${ctx}/sysAdmin/cashbackTemplate_reportCashbackTemplateLog.action'
        });
    }
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_cashbackTemplateLog_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<td style="width: 250px;"></td>
					<th>机构编号</th>
					<!-- <td>
						<select name="unno" id="unno_cashbackTemplateLog" class="easyui-combogrid" style="width:205px;"></select>
					</td> -->
					 <td><input id="unno" name="unno" style="width: 135px;"/></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_cashbackTemplateLog_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_cashbackTemplateLog_cleanFun80();">清空</a>	
					</td>
				</tr>
				<tr>

				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_cashbackTemplateLog_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


