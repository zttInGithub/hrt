<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_mposeDate_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_queryMposeUsage.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'joinConfirmDate',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				title : '机构编号',
				field : 'UNNO',
				width : 100
			},{
				title :'机构名称',
				field :'UN_NAME',
				width : 100,
				sortable :true
			},{
				title :'MPOS采购量',
				field :'COUNT1',
				width : 100,
				sortable :true
			},{
				title :'MPOS激活量',
				field :'COUNT3',
				width : 100
			},{
				title :'MPOS激活率',
				field :'ACTIVRATE',
				width : 100
			},{
				title :'MPOS日活跃量',
				field :'COUNT2',
				width : 100
			},{
				title :'MPOS日活跃率',
				field :'USAGE',
				width : 100
			}]]
		});
	});
	
	//表单查询
	function sysAdmin_mposeDate_searchFun80() {
		$('#sysAdmin_mposeDate_datagrid').datagrid('load', serializeObject($('#sysAdmin_mposeDate_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_mposeDate_cleanFun80() {
		$('#sysAdmin_mposeDate_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_mposeDate_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询日期</th>
					<td><input name="txnDay" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_mposeDate_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_mposeDate_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_mposeDate_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


