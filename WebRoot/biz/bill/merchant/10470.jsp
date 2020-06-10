<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_taskDataApprove_datagrid').datagrid({
			url :'${ctx}/sysAdmin/taskDataApprove_initApp.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
	        striped: true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmtkid',
			sortOrder: 'asc',
			idField : 'bmtkid',
			columns : [[{
				field : 'bmtkid',
				checkbox:true
			},{
				title : '工单编号',
				field : 'taskId',
				width : 100,
				sortable:true
			},{
				title :'机构编码',
				field :'unno',
				width : 100,
				hidden:true
			},{
				title :'机构名称',
				field :'unName',
				width : 100,
				sortable:true
			},{
				title :'商户编号',
				field :'mid',
				width : 100
			},{
				title :'商户名称',
				field :'infoName',
				width : 100,
				sortable:true
			},{
				title :'商户类型',
				field :'isM35',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(row.mid!=''&&row.mid!=null&&row.mid.indexOf("HRTSYT") != -1){
						return '收银台';
					}else if(value==1&&row.settmethod=='000000'){
						return '理财';
					}else if(value==1&&(row.settmethod=='100000'||row.settmethod=='200000')){
						return '秒到';
					}else{
						return '传统'; 
					}
				}
			},{
				title :'工单类别',
				field :'type',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==1){ 
						return '商户基本信息变更申请';
					}else if(value==2){
						return '商户账户/名称变更申请';
					}else if(value==3){
						return '商户费率变更申请'; 
					}else if(value==4){
						return '终端绑定变更申请'; 
					}else if(value==5){
						return '预授权业务开通申请'; 
					}else if(value==6){
						return '商户换机申请';  
					}else{
						return '商户撤机申请'; 
					}
				} 
			},{
				title :'工单描述',
				field :'taskContext',
				width : 100,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_taskDataApprove_editFun('+index+')"/>&nbsp;&nbsp';
				} 
			}]] ,
			toolbar:[{
					id:'btn_add',
					text:'审批',
					iconCls:'icon-start',
					handler:function(){
						sysAdmin_taskDataApprove_throughFun();
					}
				},{
					id:'btn_add',
					text:'退回', 
					iconCls:'icon-close',
					handler:function(){
						sysAdmin_taskDataApprove_rejectFun();
					}
				}]  
		});
	});
	
	
	//单条审批/查看
	function sysAdmin_taskDataApprove_editFun(index) {
		var rows = $('#sysAdmin_taskDataApprove_datagrid').datagrid('getRows');
		var row  = rows[index];
		var id = row.bmtkid;
		$('<div id="sysAdmin_taskDataApprove_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看</span>',
			width: 800,   
		    height: 600, 
		    resizable:true,
		    maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10471.jsp?id='+id+'&unno='+row.unno,  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_taskDataApprove_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.bmtkid = stringToList(row.bmtkid);    	
		    	$('#sysAdmin_taskDataApprove_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_taskDataApprove_editForm').form('submit', {
						url:'${ctx}/sysAdmin/taskDataApprove_auditSingle.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_taskDataApprove_datagrid').datagrid('reload');
					    			$('#sysAdmin_taskDataApprove_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_taskDataApprove_editDialog').dialog('destroy');
					    			$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_taskDataApprove_editDialog').dialog('destroy');
					$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role_datagrid').datagrid('unselectAll');
			}
		});
	}
	//批量通过审批
	function sysAdmin_taskDataApprove_throughFun() {
		var checkedItems = $('#sysAdmin_taskDataApprove_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtkid);
		});               
		var bmtkids=names.join(",");
		if(bmtkids==null||bmtkids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要通过所选记录吗?',function(result) {
			if (result) {
				$.messager.progress();
				$.ajax({
					url:"${ctx}/sysAdmin/taskDataApprove_auditThrough.action",
					type:'post',
					data:{"bmtkids":bmtkids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						$.messager.progress('close'); 
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_taskDataApprove_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.progress('close'); 
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
			}
		});
	}
	//批量拒绝审批
	function sysAdmin_taskDataApprove_rejectFun() {
		var checkedItems = $('#sysAdmin_taskDataApprove_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtkid);
		});               
		var bmtkids=names.join(",");
		if(bmtkids==null||bmtkids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要拒绝所选记录吗?',function(result) {
			if (result) {
				$.messager.progress();
				$.ajax({
					url:"${ctx}/sysAdmin/taskDataApprove_auditReject.action",
					type:'post',
					data:{"bmtkids":bmtkids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						$.messager.progress('close'); 
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_taskDataApprove_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.progress('close'); 
						$.messager.alert('提示', '拒绝记录出错！');
						$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_taskDataApprove_datagrid').datagrid('unselectAll');
			}
		});
	} 
	
	
	//表单查询
	function sysAdmin_taskDataApprove_searchFun10() {
		$('#sysAdmin_taskDataApprove_datagrid').datagrid('load', serializeObject($('#sysAdmin_taskDataApprove_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_taskDataApprove_cleanFun10() {
		$('#sysAdmin_taskDataApprove_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_taskDataApprove_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="infoName" style="width: 316px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工单类型</th>
					<td>
						<select name="type">
							<option selected="selected" value="1">商户基本信息变更申请</option>
							<option value="2">商户账户/名称变更申请</option>
							<option value="3">商户费率变更申请</option>
							<option value="4">终端绑定变更申请</option>
							<option value="5">预授权业务开通申请</option>
							<option value="6">商户换机/撤机申请</option>
						</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户类型</th>
					<td>
						<select name="isM35">
							<option selected="selected" value="">所有</option>
							<option value="1">刷卡</option>
							<option value="2">收银台</option>
						</select>
					</td>
				</tr> 
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_taskDataApprove_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_taskDataApprove_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_taskDataApprove_datagrid" style="overflow: hidden;"></table>
	</div>
</div>



