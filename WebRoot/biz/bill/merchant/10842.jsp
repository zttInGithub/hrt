<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_problemFeedback_datagrid').datagrid({
			url :'${ctx}/sysAdmin/problemFeedbackAction_listSYTDataGrid.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'PROBID',
			// 商户编号	商户名称	归属机构	运营中心	交易金额	交易时间	商户选择失败原因
            columns : [[{
				title : 'id',
				field : 'PROBID',
				width : 100,
                hidden : true
			},{
                title : '商户编号',
                field : 'MID',
                width : 100
            },{
				title : '商户名称',
				field : 'RNAME',
				width : 100
			},{
				title : '归属机构',
				field : 'UNNO',
				width : 100
			},{
                title : '归属机构名称',
                field : 'UN_NAME',
                width : 100
            },{
				title : '运营中心',
				field : 'YUNYING',
				width : 100
			},{
                title : '运营中心名称',
                field : 'YUNYINGNAME',
                width : 100
            },{
				title : '交易金额',
				field : 'AMOUNT',
				width : 100
			},{
				title : '交易时间',
				field : 'TRANTIME',
				width : 100
			},{
                title : '反馈日期',
                field : 'CREATETIME',
                width : 100
            },{
				title :'商户选择失败原因',
				field :'REMARK',
				width : 100,
				sortable :true
			}]]
			,
			toolbar:[{
					id:'btn_export',
					text:'反馈信息导出',
					iconCls:'icon-query-export',
					handler:function(){
						sysAdmin_problemFeedback_export();
					}
			}]
		});
	});
    //导出Excel
    function sysAdmin_problemFeedback_export() {
        $('#sysAdmin_problemFeedbackForm').form('submit',{
            url:'${ctx}/sysAdmin/problemFeedbackAction_exportSYTData.action',
        });
    }

	//表单查询
	function sysAdmin_problemFeedback_searchFun() {
		$('#sysAdmin_problemFeedback_datagrid').datagrid('load', serializeObject($('#sysAdmin_problemFeedbackForm')));
	}

	//清除表单内容
	function sysAdmin_problemFeedback_cleanFun() {
		$('#sysAdmin_problemFeedbackForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:40px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_problemFeedbackForm" style="padding-left:10%">
			<%--1、筛选条件：商户编号、反馈日期--%>
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>反馈日期</th>
					<td><input name="createTime" style="width: 316px;"  class="easyui-datebox" /></td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_problemFeedback_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_problemFeedback_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_problemFeedback_datagrid" style="overflow: hidden;"></table>
	</div>
</div>

