<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 机型库存 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10560_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_listTerminalNumByMachineModel.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'ALLNUM',
			columns : [[{
				title : '机型',
				field : 'MACHINEMODEL',
				width : 100
			},{
				title : '库位',
				field : 'STORAGE',
				width : 125
			},{
				title : '库存数量',
				field : 'ALLNUM',
				width : 125
			}]], 
			toolbar:[]		
		});
	});
	
	//表单查询
	function sysAdmin_10560_searchFun() {
		$('#sysAdmin_10560_datagrid').datagrid('load', serializeObject($('#sysAdmin_10560_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10560_cleanFun() {
		$('#sysAdmin_10560_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10560_searchForm" style="padding-left:5%">
			<table class="tableForm" >
				<tr>
					<th>机型&nbsp;</th>
					<td><input name="machineModel" style="width: 138px;" /></td>
					
					<th>库位&nbsp;</th>
					<td>
						<select name="storage" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
							<option value="">查询所有</option>
		   					<option value="HRT">HRT</option>
		    				<option value="HYB">HYB</option>
		   				</select>
	   				</td>
					
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10560_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10560_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10560_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>