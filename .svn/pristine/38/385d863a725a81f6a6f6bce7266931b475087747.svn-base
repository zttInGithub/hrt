<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
	
		$("#unNO").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			valueField : 'UNNO',
			textField : 'UN_NAME',
			onSelect:function(rec){
				$("#unNO").val(rec.UNNO);
				$('#sysAdmin_exportTid_datagrid').datagrid('load', serializeObject($('#sysAdmin_exportTid_searchForm')));
			}
		});
		$('#sysAdmin_exportTid_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findExportList.action',
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			/* ctrlSelect:true,
			checkOnSelect:true, */
			sortName: 'termID',
			sortOrder: 'asc',
			idField : 'btID',
			columns : [[{
				title :'唯一编号',
				field :'btID',
				width : 100,
				hidden:true
			},{
				title :'终端编号',
				field :'termID',
				width : 100,
				sortable:true
			},{
				title :'密钥类型',
				field :'keyType',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '短密钥';
					}else if(value=='2'){
						return '长密钥';
					}else{
						return 'Mpos密钥';
					}
				}
			},{
				title :'SN',
				field :'sn',
				width : 100,
				sortable:true
			},{
				title :'分配情况',
				field :'allotConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未分配';
					}else{
						return '已分配';
					}
				}
			},{
				title :'使用情况',
				field :'usedConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未使用';
					}else{
						return '已使用';
					}
				}
			},{
				title :'ISM35',
				field :'isM35',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '是';
					}else{
						return '否';
					}
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_tid_export();
				}
			}]		
		});
	});
	
	//表单查询
	function sysAdmin_10360_searchFun() {
		$('#sysAdmin_exportTid_datagrid').datagrid('load', serializeObject($('#sysAdmin_exportTid_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10360_cleanFun() {
		$('#sysAdmin_exportTid_searchForm input').val('');
	}
	
	//终端导出
	function sysAdmin_tid_export(){
			$('#sysAdmin_exportTid_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/terminalInfo_emportTidInfo.action'
			    });
	
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_exportTid_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构名称：</th>
						<td><input id="unNO" name="unNO" class="easyui-combobox" style="width: 300px;"/>
					</td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端编号：</th>
					<td>
						<input name="termIDStart" />&nbsp;至&nbsp;
						<input name="termIDEnd" />
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10360_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10360_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_exportTid_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


