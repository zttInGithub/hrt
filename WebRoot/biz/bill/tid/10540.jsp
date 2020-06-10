<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10540_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_queryTerminalStorage.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'MACHINEMODEL',
			columns : [[{
				title : '机型',
				field : 'MACHINEMODEL',
				width : 100
			},{
				title : '数量',
				field : 'MACNUM',
				width : 100
			}]],
			toolbar:[ {
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10540_exportFun();
				}
			}]		
		});
	});
	
	//导出
	function sysAdmin_10540_exportFun() {
		$('#sysAdmin_10540_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/terminalInfo_exportTerminalStorage.action'
			    	});
	}
	
	//表单查询
	function sysAdmin_10540_searchFun() {
		$('#sysAdmin_10540_datagrid').datagrid('load', serializeObject($('#sysAdmin_10540_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10540_cleanFun() {
		$('#sysAdmin_10540_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10540_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>入库时间：</th>
					<td><input name="keyConfirmDate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;</td>
					<td><input name="keyConfirmDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/></td>
					<th>出库时间：</th>
					<td><input name="outDate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;</td>
					<td><input name="outDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/></td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10540_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10540_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10540_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>