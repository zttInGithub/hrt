<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化treegrid
	$(function() {
		$('#sysAdmin_unit_treegrid').treegrid({
			url : '${ctx}/sysAdmin/unit_listUnits.action',
			fit:true,
			fitColumns : true,
			border : false,
			animate : true,	
			idField : 'unNo',
			treeField : 'unitName',
			columns : [[{
				title : '机构名称',
				field : 'unitName',
				width : 150
			},{
				title : '机构编号',
				field : 'unNo',
				width : 50
			},{
				title : '所在区域',
				field : 'provinceCode',
				width : 50,
				hidden : true
			},{
				title : '机构级别',
				field : 'unLvl',
				width : 50,
				hidden : true
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
				title :'最近修改时间',
				field :'updateDate',
				width : 100,
				sortable : true
			},{
				title :'修改者',
				field :'updateUser',
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
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_unit_editFun('+row.unNo+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_unit_delFun('+row.unNo+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_unit_closeFun('+row.unNo+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_unit_startFun('+row.unNo+')"/>';
				}
			}]],
			toolbar:[{
					text:'添加机构',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_unit_addFun();
					}
				}]
		});
	});

	//添加用户窗口
	function sysAdmin_unit_addFun() {
		$('<div id="sysAdmin_unit_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加机构</span>',
			width: 680,   
		    height: 250,
		    closed: false,
		    href: '${ctx}/frame/sysadmin/unit/00221.jsp',  
		    modal: true,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$('#sysAdmin_unit_addForm').form('submit', {
						url:'${ctx}/sysAdmin/unit_addUnit.action',
					    success:function(data){   
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if(result.success) {
									$('#sysAdmin_unit_treegrid').treegrid('reload');
					    			$('#sysAdmin_unit_addDialog').dialog('destroy');
									$.messager.show({
										title : '提示',
										msg : result.msg
									});
								} else {
					    			$.messager.alert('提示', result.msg);
								}  
							}
					    }   
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#sysAdmin_unit_addDialog').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//修改机构窗口
	function sysAdmin_unit_editFun(id) {
		$('<div id="sysAdmin_unit_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改机构</span>',
			width: 680,   
		    height: 250, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/unit/00222.jsp',  
		    modal: true,
		    onLoad: function() {
		    	var data = $('#sysAdmin_unit_treegrid').treegrid('find', id);
		    	$('#sysAdmin_unit_editForm').form('load', data);
			},
			buttons:[{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						$('#sysAdmin_unit_editForm').form('submit', {
							url:'${ctx}/sysAdmin/unit_editUnit.action',
						    success:function(data){
								var result = $.parseJSON(data);
								if (result.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(result.success) {
										$('#sysAdmin_unit_treegrid').treegrid('reload');
					    				$('#sysAdmin_unit_editDialog').dialog('destroy');
										$.messager.show({
											title : '提示',
											msg : result.msg
										});
									} else {
										$('#sysAdmin_unit_editDialog').dialog('destroy');
					    				$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
					    				$.messager.alert('提示', result.msg);
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#sysAdmin_unit_editDialog').dialog('destroy');
						$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
				} 
		});
	}

	//删除机构
	function sysAdmin_unit_delFun(id) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result){   
		    if (result){
		    	var data = $('#sysAdmin_unit_treegrid').treegrid('find', id);
				if (data.children) {
					$.messager.alert('提示', '请先删除子菜单！');
					$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
				} else {
					 $.ajax({
						url: "${ctx}/sysAdmin/unit_deleteUnit.action",
						type:'post',
						data: {"unNo":id},
						dataType: 'json',
						success: function(data, textStatus, jqXHR) {
				        	if (data.success) {
				        		$.messager.show({
									title : '提示',
									msg : data.msg
								});
					        	$('#sysAdmin_unit_treegrid').treegrid('remove', id);
					        } else {
					        	$.messager.alert('提示', data.msg);
					        	$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
						    }
				        },
					    error: function () {
				        	$.messager.alert('提示', '删除记录出错！');
				        	$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
					    }
				    }); 
				}
		    } else {
		    	$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
			}   
		}); 
	}
	
	//停用机构
	function sysAdmin_unit_closeFun(unno){
		$.messager.confirm('确认','您确认要停用该机构吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/unit_closeUnit.action",
					type:'post',
					data:{"unNo":unno},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_unit_treegrid').treegrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '停用机构出错！');
						$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
					}
				});
			}
		});
	}
	
	//启用机构
	function sysAdmin_unit_startFun(unno){
		$.messager.confirm('确认','您确认要启用该机构吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/unit_startUnit.action",
					type:'post',
					data:{"unNo":unno},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_unit_treegrid').treegrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '启用机构出错！');
						$('#sysAdmin_unit_treegrid').treegrid('unselectAll');
					}
				});
			}
		});
	}
	
</script>


<table id="sysAdmin_unit_treegrid" style="overflow:hidden;"></table>