<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_role002301_datagrid').datagrid({
			url :'${ctx}/sysAdmin/role_listRoles002301.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'roleID',
			sortOrder: 'desc',
			idField : 'roleID',
			columns : [[{
				title : '角色ID',
				field : 'roleID',
				width : 100,
				hidden : true
			},{
				title : '角色名称',
				field : 'roleName',
				width : 100
			},{
				title :'描述',
				field :'description',
				width : 100
			},{
				title :'创建时间',
				field :'createDate',
				width : 100,
				sortable : true,
				hidden : true
			},{
				title :'创建者',
				field :'createUser',
				width : 100,
				sortable : true
			},{
				title :'状态',
				field :'statusName',
				width : 50,
				sortable : true
			},{
				title :'权限',
				field :'menuTexts',
				width : 250,
				formatter : function(value,row,index) {
					return '<span title="'+value+'">'+value+'</span>';
				}
			},{
				title :'权限ID',
				field :'menuIds',
				width : 100,
				hidden : true
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_role_delFun('+row.roleID+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_role_closeFun('+row.roleID+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_role_startFun('+row.roleID+')"/>';;
				}
			}]],
			
			toolbar:[/**{
					id:'btn_add',
					text:'添加角色',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_role_addFun();
					}
				}**/]			
		});
	});

	//添加角色
	function sysAdmin_role_addFun() {
		$('<div id="sysAdmin_role_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加角色</span>',
			width: 380,   
		    height:240, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/role/00231.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_role_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/role_addRole.action',
		    			success:function(data) {
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_role002301_datagrid').datagrid('reload');
					    			$('#sysAdmin_role_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_role_addDialog').dialog('destroy');
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
					$('#sysAdmin_role_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	//修改角色
	function sysAdmin_role_editFun(index) {
		$('<div id="sysAdmin_role_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改角色</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/role/00232.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_role002301_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.menuIds);    	
		    	$('#sysAdmin_role_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_role_editForm').form('submit', {
						url:'${ctx}/sysAdmin/role_editRole.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_role002301_datagrid').datagrid('reload');
					    			$('#sysAdmin_role_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_role_editDialog').dialog('destroy');
					    			$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_role_editDialog').dialog('destroy');
					$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
			}
		});
	}

	//删除角色
	function sysAdmin_role_delFun(roleID) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/role_deleteRole.action",
					type:'post',
					data:{"ids":roleID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_role002301_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//启用角色
	function sysAdmin_role_startFun(id){
		$.messager.confirm('确认','您确认要启用该角色吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/role_startRole.action",
					type:'post',
					data:{"roleID":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_role002301_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '启用角色出错！');
						$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//停用角色
	function sysAdmin_role_closeFun(id){
		$.messager.confirm('确认','您确认要停用该角色吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/role_closeRole.action",
					type:'post',
					data:{"roleID":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_role002301_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '停用角色出错！');
						$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_role002301_datagrid').datagrid('unselectAll');
			}
		});
	}
	
</script>

<table id="sysAdmin_role002301_datagrid" style="overflow: hidden;"></table>

