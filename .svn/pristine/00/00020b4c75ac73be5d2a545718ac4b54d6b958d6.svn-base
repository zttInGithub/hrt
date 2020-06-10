<%@ page language="java" import="java.util.*,java.sql.*,com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>  
<%  Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession); 
%>
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_user002401_datagrid').datagrid({	
			url : '${ctx}/sysAdmin/user_listUsers002401.action?useLvl=2',
			fit:true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 15 ],
			idField : 'userID',
 			sortName : 'USER_ID',
			sortOrder : 'desc',
			columns : [[{
				title : '用户ID',
				field : 'userID',
				width : 100,
				hidden : true
			},{
				title :'机构编号',
				field :'unNo',
				width : 100
			},{
				title :'所属机构',
				field :'unitName',
				width : 100,
				formatter : function(value,row,index) {
					return '<span title="'+value+'">'+value+'</span>';
				}
			},{
				title : '登陆名',
				field : 'loginName',
				width : 100
			},{
				title :'用户名称',
				field :'userName',
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
				title :'密码',
				field :'password',
				width : 100,
				hidden : true
			},{
				title :'所属角色',
				field :'roleNames',
				width : 100,
				formatter : function(value,row,index) {
					return '<span title="'+value+'">'+value+'</span>';
				}
			},{
				title :'角色ID',
				field :'roleIds',
				width : 100,
				hidden : true
			},{
				title :'操作',
				field :'operation',
				width : 200,
				align : 'center',
				formatter : function(value,row,index) {
					var a =1;
					var b =<%=user.getUserID()%>; 
					if (row.userID == 1) {
						return '';
					} else if(b==544924||b==1){//技术
							return '<input type="button" value="重置密码" onclick="sysAdmin_user_updatePwd('+row.userID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_user_editUserFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_user_delteUser('+row.userID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_user_closeFun('+row.userID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_user_startFun('+row.userID+')"/>';
					}else if(a==0||a==1){//公司
							return '<input type="button" value="重置密码" onclick="sysAdmin_user_updatePwd('+row.userID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_user_closeFun('+row.userID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_user_startFun('+row.userID+')"/>';
					}else{
						return '<input type="button" value="重置密码" onclick="sysAdmin_user_updatePwd('+row.userID+')"/>&nbsp;&nbsp'+
								'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
								'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_user_closeFun('+row.userID+')"/>';
								//'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_user_startFun('+row.userID+')"/>';
					}
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'添加用户',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_user_addUserFun();
					}
				}]
		});
	});

	//表单查询
	function sysAdmin_user_searchFun() {
		$('#sysAdmin_user002401_datagrid').datagrid('load', serializeObject($('#sysAdmin_user_searchForm')));
	}

	//清除表单内容
	function sysAdmin_user_cleanFun() {
		$('#sysAdmin_user_searchForm input').val('');
	}

	//添加用户窗口
	function sysAdmin_user_addUserFun() {
		$('<div id="sysAdmin_user_add"/>').dialog({
			title: '<span style="color:#157FCC;">添加用户</span>',
			width: 400,   
		    height: 310, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/user/00241.jsp',  
		    modal: true,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
		    		var dia = $('#sysAdmin_user_add');
					$('#sysAdmin_userAdd_addForm').form('submit', {
						url:'${ctx}/sysAdmin/user_addUser.action',
					    success:function(data){   
							var res = $.parseJSON(data);
							if (res.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if(res.success) {
									$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
									$('#sysAdmin_user002401_datagrid').datagrid('reload');
									dia.dialog('destroy');
									$.messager.show({
										title : '提示',
										msg : res.msg
									});
								} else {
									$.messager.alert('提示', res.msg);
								}  
							}
					    }   
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#sysAdmin_user_add').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//修改用户窗口
	function sysAdmin_user_editUserFun(index) {
		
		$('<div id="sysAdmin_user_edit"/>').dialog({
			title: '<span style="color:#157FCC;">修改用户</span>',
			width: 380,   
		    height: 300, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/user/00242.jsp',  
		    modal: true,
		    onLoad: function() {
		    	var rows = $('#sysAdmin_user002401_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.roleIds = stringToList(row.roleIds);
		    	row.unNo = stringToList(row.unNo);  
		    	$('#sysAdmin_userEdit_editForm').form('load', row);
			},
			 buttons:[{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var dia = $('#sysAdmin_user_edit');
						$('#sysAdmin_userEdit_editForm').form('submit', {
							url:'${ctx}/sysAdmin/user_editUser.action',
						    success:function(data){   
								var res = $.parseJSON(data);
								if (res.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(res.success) {
										dia.dialog('destroy');
										$('#sysAdmin_user002401_datagrid').datagrid('reload');
										$.messager.show({
											title : '提示',
											msg : res.msg
										});
									} else {
										dia.dialog('destroy');
										$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
										$.messager.alert('提示', res.msg);
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#sysAdmin_user_edit').dialog('destroy');
						$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
				} 
		});
	}

	//删除用户
	function sysAdmin_user_delteUser(id) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url: "${ctx}/sysAdmin/user_deleteUser.action",
		        	data: {"ids": id},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_user002401_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '删除记录出错！');
			        	$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	//重置密码
	function sysAdmin_user_updatePwd(id){
		$.messager.confirm('确认','您确认要重置用户密码吗?',function(r){
		    if (r){
		        $.ajax({
			        url: "${ctx}/sysAdmin/user_updatePwd.action",
		        	data: {"userID": id},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_user002401_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
					    }
			        }
			    }); 
		    } else {
		    	$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	//停用用户
	function sysAdmin_user_closeFun(id){
		$.messager.confirm('确认','您确认要停用该用户吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/user_closeUser.action",
					type:'post',
					data:{"userID":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_user002401_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '停用用户出错！');
						$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_user_startFun(id){
		$.messager.confirm('确认','您确认要启用该用户吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/user_startUser.action",
					type:'post',
					data:{"userID":id},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_user002401_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '启用用户出错！');
						$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_user002401_datagrid').datagrid('unselectAll');
			}
		});
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_user002401_datagrid"></table>
    </div> 
</div>



