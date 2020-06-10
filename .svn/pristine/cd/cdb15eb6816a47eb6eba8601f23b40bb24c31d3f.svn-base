<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10621_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_checkAddSn.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
	        striped: true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ,100 ],
			sortName: 'btID',
			sortOrder: 'desc',
			idField : 'btID',
			columns : [[{
				field : 'btID',
				checkbox:true
			},{
				title : '机构编号',
				field : 'unNO',
				width : 100,
				sortable:true
			},{
				title : '终端编号',
				field : 'termID',
				width : 100,
				sortable:true
			},{
				title : '当前状态',
				field : 'status',
				width : 100,
				formatter:function(value,row,index){
					if(value=="0"){
						return "未分配";
					}else if(value=="1"){
						return "已分配";
					}else{
						return "已使用";
					}
				}
			},{
				title :'sn号',
				field :'sn',
				width : 100
			},{
				title :'返利类型',
				field :'rebateType',
				width : 100,
				sortable:true
			},{
				title :'操作',
				field :'operation',
				width : 100,
				sortable:true,
				align : 'center',
				formatter:function(value,row,index){
					if(row.status=="3"){
						return '<img src="${ctx}/images/start.png" title="通过" style="cursor:pointer;" onclick="sysAdmin_10621_throughSN('+row.btID+')"/>&nbsp;&nbsp<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_10621_rejectSN('+row.btID+')"/>';
					}
				}
			}]] ,
			toolbar:[{
					id:'btn_ok',
					text:'批量通过',
					iconCls:'icon-start',
					handler:function(){
						sysAdmin_10621_throughFun();
					}
				},{
					id:'btn_no',
					text:'批量退回', 
					iconCls:'icon-close',
					handler:function(){
						sysAdmin_10621_rejectFun();
					}
				}]  
		});
	});
	
	//单条通过
	function sysAdmin_10621_throughSN(id) {
		$.messager.confirm('确认','您确认要通过该条记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_throughAllSn.action",
					type:'post',
					data:{"btids":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10621_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
						}
					}
				});
			} else {
				$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//单条退回
	function sysAdmin_10621_rejectSN(id) {
		$.messager.confirm('确认','您确认要退回该条记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_rejectAllSn.action",
					type:'post',
					data:{"btids":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10621_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
						}
					}
				});
			} else {
				$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//批量通过审批
	function sysAdmin_10621_throughFun() {
		var checkedItems = $('#sysAdmin_10621_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.btID);
		});               
		var btIDs=names.join(",");
		if(btIDs==null||btIDs==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要通过所有选中记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_throughAllSn.action",
					type:'post',
					data:{"btids":btIDs},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10621_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
					}
				});
			}
		});
	}
	//批量拒绝审批
	function sysAdmin_10621_rejectFun() {
		var checkedItems = $('#sysAdmin_10621_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.btID);
		});               
		var btIDs=names.join(",");
		if(btIDs==null||btIDs==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要退回所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_rejectAllSn.action",
					type:'post',
					data:{"btids":btIDs},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10621_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
						}
					}
				});
			} else {
				$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
			}
		});
	} 
	//批量通过
	function sysAdmin_10621_through() {
		var unNo = $("#10621_unNo").val();
		var snStart = $("#10621_snStart").val();
		var snEnd = $("#10621_snEnd").val();
		var keyConfirmDate = $("#10621_keyConfirmDate").val();
		if((snEnd==""||snStart=="")){
			$.messager.alert('提示','请输入完整起始Sn号');
			return;
		}
		$.messager.confirm('确认','您确认要通过所有Sn吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_throughSn.action",
					type:'post',
					data:serializeObject($('#sysAdmin_10621_updateForm')),
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10621_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
					}
				});
			}
		});
	}
	//批量退回
	function sysAdmin_10621_reject() {
		var unNo = $("#10621_unNo").val();
		var snStart = $("#10621_snStart").val();
		var snEnd = $("#10621_snEnd").val();
		var keyConfirmDate = $("#10621_keyConfirmDate").val();
		if(snStart==""||snEnd==""){
			$.messager.alert('提示','请输入完整起始Sn号');
			return;
		}
		$.messager.confirm('确认','您确认要退回所选Sn吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_rejectSn.action",
					type:'post',
					dataType:'json',
					data:serializeObject($('#sysAdmin_10621_updateForm')),
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10621_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
					}
				});
			}
		});
	}
	//表单查询
	function sysAdmin_10621_find() {
		$('#sysAdmin_10621_datagrid').datagrid('load', serializeObject($('#sysAdmin_10621_updateForm'))); 
	}
	//表单清空
	function sysAdmin_10621_clear(){
		$("#sysAdmin_10621_updateForm input").val("");
	}
	$(function(){
		$('#rebateType_10621').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=3',
			idField:'VALUEINTEGER',
			textField:'VALUEINTEGER',
			value:-1,
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'返利名称',width:150},
				{field:'VALUEINTEGER',title:'类型',width:150}
			]]
		});
	});
</script>

<div class="easyui-layout" data-options="fit:true, border:false"> 
 	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_10621_updateForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input id="10621_unNo" name="unNO" style="width: 117px;" />&nbsp;&nbsp;</td>
					<th>归属中心机构号</th>
					<td><input id="10621_maintainType" name="maintainType" style="width: 117px;" />&nbsp;&nbsp;</td>
					<th>订单号</th>
					<td><input id="10621_terOrderID" name="terOrderID" style="width: 117px;" />&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<th>返利类型：</th>
	   			<td><select id="rebateType_10621" name="rebateType"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 117px;"></select>
					</td>
					<th>Sn号</th>
					<td><input id="10621_snStart" name="snStart" style="width: 117px;" /></td>
					<th>-</th>
					<td><input id="10621_snEnd" name="snEnd" style="width: 117px;" />&nbsp;&nbsp;</td>
				    <th>导入日期:</th>
				    <td><input id="10621_keyConfirmDate" name="keyConfirmDate" type="text" class= "easyui-datebox" editable="false" style="width: 116px;"/>&nbsp;&nbsp;</td>
					
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true" 
						onclick="sysAdmin_10621_find();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true" 
						onclick="sysAdmin_10621_clear();">清空</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true" 
						onclick="sysAdmin_10621_through();">通过</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-close',plain:true" 
						onclick="sysAdmin_10621_reject();">退回</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10621_datagrid" style="overflow: hidden;"></table>
	</div>
</div>



