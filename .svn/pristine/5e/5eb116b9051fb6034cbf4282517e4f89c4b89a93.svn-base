<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {

		$('#sysAdmin_delterminal_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findNoUseSnList.action', 
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			/* ctrlSelect:true,
			checkOnSelect:true, */
			sortName: 'btID',
			sortOrder: 'asc',
			idField : 'btID',
			columns : [[{
				title :'唯一编号',
				field :'btID',
				width : 100,
				hidden:true
			},{
				title :'机构编号',
				field :'unNO',
				width : 100,
				sortable:true
			},{
				title :'SN号',
				field :'sn',
				width : 100,
				sortable:true
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
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/close.png" title="删除终端" style="cursor:pointer;" onclick="sysAdmin_deleteTerminalInfo_deleFun('+row.btID+')"/>&nbsp;&nbsp'; 
				}
			}]]		
		});
	});
	
	//删除该SN
	function sysAdmin_deleteTerminalInfo_deleFun(btID){
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_deleteSnTerminalInfo.action",
					type:'post',
					data:{"btID":btID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_delterminal_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
					}
				});
			}
		});
	}
	
	//表单查询
	function sysAdmin_10360_searchFun() {
		$('#sysAdmin_delterminal_datagrid').datagrid('load', serializeObject($('#sysAdmin_delterminal_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10360_cleanFun() {
		$('#sysAdmin_delterminal_searchForm input').val('');
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_delterminal_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<td style="width:150px;"></td>
					<th>SN号：</th>
					<td>
						<input name="sn" />
					</td>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10360_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10360_cleanFun();">清空</a>	
					</td>
				</tr>
				<tr>

				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_delterminal_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


