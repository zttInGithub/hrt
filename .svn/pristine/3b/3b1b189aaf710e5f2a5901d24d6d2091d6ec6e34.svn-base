<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化菜单表格树
	$(function() {
		$('#sysAdmin_menu_treegrid').treegrid({
			url : '${ctx}/sysAdmin/menu_listMenusTreegrid.action',
			fit : true,
			fitColumns : true,
			border : false,
			animate : true,		
			idField : 'menuID',
			treeField : 'text',
			columns : [[{
				title : '菜单ID',
				field : 'menuID',
				width : 100,
				hidden : true
			},{
				title : '菜单名称',
				field : 'text',
				width : 150
			},{
				title : '菜单路径',
				field : 'src',
				width : 200,
				formatter : function(value,row,index) {
					if(value) {
						return '<span title="'+value+'">'+value+'</span>';
					} else {
						return '';
					}
				}
			},{
				title : '菜单索引',
				field : 'seq',
				width : 60
			},{
				title :'创建时间',
				field :'createDate',
				width : 100,
				sortable : true
			},{
				title :'创建者',
				field :'createUser',
				width : 100,
				sortable : true
			},{
				title :'状态',
				field :'statusName',
				width : 100,
				sortable : true
			},{
				title :'操作',
				field :'operation',
				width : 150,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_menu_editFun('+row.menuID+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_menu_delFun('+row.menuID+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_menu_closeFun('+row.menuID+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_menu_startFun('+row.menuID+')"/>';
				}
			}]],
			toolbar : [{
				text : '添加功能',
				iconCls : 'icon-add',
				handler : function() {
					sysAdmin_menu_addFun();
				}
			}]
		});
	});

	//添加菜单
	function sysAdmin_menu_addFun() {
		$('<div id="sysAdmin_menu_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加菜单</span>',
			width: 800,   
		    height: 200, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/menu/00211.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_menu_addForm').form('submit', {
		    			url:'${ctx}/sysAdmin/menu_addMenu.action',
		    			success:function(data) {
		    				var result = $.parseJSON(data);
		    				if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_menu_treegrid').treegrid('reload');
					    			$('#sysAdmin_menu_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_menu_addDialog').dialog('destroy');
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
					$('#sysAdmin_menu_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		}); 
	}

	//修改菜单
	function sysAdmin_menu_editFun(id) {
		$('<div id="sysAdmin_menu_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改菜单</span>',
			width: 800,   
		    height: 200, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/menu/00212.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var data = $('#sysAdmin_menu_treegrid').treegrid('find', id);
		    	$('#sysAdmin_menu_editForm').form('load', data);
			},
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_menu_editForm').form('submit', {
		    			url:'${ctx}/sysAdmin/menu_editMenu.action',
		    			success:function(data) {
		    				var result = $.parseJSON(data);
		    				if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_menu_treegrid').treegrid('reload');
					    			$('#sysAdmin_menu_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_menu_editDialog').dialog('destroy');
					    			$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
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
					$('#sysAdmin_menu_editDialog').dialog('destroy');
					$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
			}
		});
	}

	//删除菜单
	function sysAdmin_menu_delFun(id) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				var data = $('#sysAdmin_menu_treegrid').treegrid('find', id);
				if (data.children) {
					$.messager.alert('提示', '请先删除子菜单！');
					$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
				} else {
					$.ajax({
						url:"${ctx}/sysAdmin/menu_deleteMenu.action",
						type:'post',
						data:{"menuID":id},
						dataType:'json',
						success:function(data, textStatus, jqXHR) {
							if (data.success) {
								$.messager.show({
									title : '提示',
									msg : data.msg
								});
								$('#sysAdmin_menu_treegrid').treegrid('remove', id);
							} else {
								$.messager.alert('提示', data.msg);
								$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
							}
						},
						error:function() {
							$.messager.alert('提示', '删除记录出错！');
							$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
						}
					});
				}
			} else {
				$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
			}
		});
	}
	
	//启用账号
	function sysAdmin_menu_startFun(id){
		$.messager.confirm('确认','您确认要启用该菜单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/menu_startMenu.action",
					type:'post',
					data:{"menuID":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_menu_treegrid').treegrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '启用菜单出错！');
						$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
					}
				});
			}
		});
	}
	
	//停用菜单
	function sysAdmin_menu_closeFun(id){
		$.messager.confirm('确认','您确认要停用该菜单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/menu_closeMenu.action",
					type:'post',
					data:{"menuID":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_menu_treegrid').treegrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '停用菜单出错！');
						$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
					}
				});
			}
		});
	}
	
</script>

<table id="sysAdmin_menu_treegrid" style="overflow:hidden;"></table>