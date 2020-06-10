<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_beiJingArea_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_findBeiJingAreaDealData.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MAINTAINDATE',
			sortOrder: 'desc',
			idField : 'MAINTAINDATE',
			columns : [[{
				title : '安装日期',
				field : 'MAINTAINDATE',
				width : 100
			},{
				title : '商户名称',
				field : 'RNAME',
				width : 100
			},{
				title : '商户编号',
				field : 'MID',
				width : 100
			},{
				title : '终端编号',
				field : 'TID',
				width : 100
			},{
				title : '装机地址',
				field : 'INSTALLEDADDRESS',
				width : 200
			},{
				title :'交易额',
				field :'TXNAMOUNT',
				width : 100,
				sortable :true
			}]],
			toolbar:[{
				id:'btn_run',
				text:'导出交易量',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_exportBeiJingAreaData_exportFun();
				}
			}]	
		});
	});

	function sysAdmin_exportBeiJingAreaData_exportFun(){
		$('#sysAdmin_beiJingArea_searchForm').form('submit',{
    		url:'${ctx}/sysAdmin/checkUnitDealData_exportBeiJingAreaData.action'
    	});			
	}

	//表单查询
	function sysAdmin_beiJingArea_searchFun80() {
		$('#sysAdmin_beiJingArea_datagrid').datagrid('load', serializeObject($('#sysAdmin_beiJingArea_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_beiJingArea_cleanFun80() {
		$('#sysAdmin_beiJingArea_searchForm input').val('');
	}

</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_beiJingArea_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
				</tr> 
				<tr>
					<th>终端编号</th>
					<td><input name="tid" style="width: 216px;" /></td>
					<th>交易日期</th>
					<td><input name="txnDay" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>至
						<input name="txnDay1" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_beiJingArea_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_beiJingArea_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    	 <table id="sysAdmin_beiJingArea_datagrid" style="overflow: hidden;"></table> 
    </div> 


