<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--上传文件--%>
<script type="text/javascript">
	$(function() {
		$('#sysAdmin_upload_datagrid').datagrid({
			url :'${ctx}/sysAdmin/file_listFiles.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'fileID',
			sortOrder: 'desc',
			idField : 'fileID',
			columns : [[{
				title : 'ID',
				field : 'fileID',
				width : 100,
				hidden : true
			},{
				title : '上传时间',
				field : 'createDate',
				width : 100
			},{
				title : '文件名称',
				field : 'fileName',
				width : 100
			},{
				title : '文件描述',
				field : 'fileDesc',
				width : 100
			},{
				title : '上传人',
				field : 'createUser',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_file_delteFile('+row.fileID+')"/>';
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'上传文件',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_upload_addFun();
					}
				}]		
		});
	});
	
	function sysAdmin_upload_addFun(){
		$('<div id="sysAdmin_upload_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">上传文件</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/upload/00131.jsp',  
		    modal: true,
			buttons:[{
				text:'上传',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_upload_addForm').form('submit', {
						url:'${ctx}/sysAdmin/file_uploadFile.action',
						onSubmit: function(){
							var contact = document.getElementById('upload').value;
							var condesc = document.getElementById('fileDesc').value;
							if (contact == "") {
								$.messager.show({
				               		 title:'提示',
				                	 msg:'请选择要上传的文件!',
				                	 timeout:5000,
				                	 showType:'slide'
			            		});
								return false;
							}
				
							if (condesc == "") {
								$.messager.alert('提示','请填写描述！');
								return false;
							}
							if (contact != "") {
								document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
							}
						}, 
						success:function(data) {
							var res = $.parseJSON(data);
							if (res.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if(res.success) {
									$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_upload_datagrid').datagrid('reload');
					    			$('#sysAdmin_upload_editDialog').dialog('destroy');
									$.messager.show({
										title : '提示',
										msg : res.msg
									});
								} else {
									$('#sysAdmin_upload_editDialog').dialog('destroy');
					    			$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', '文件名称已存在');
								}  
							}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_upload_editDialog').dialog('destroy');
					$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	
	//删除文件
	function sysAdmin_file_delteFile(fileID) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/file_deleteFile.action",
					type:'post',
					data:{"ids":fileID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_upload_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_upload_datagrid').datagrid('unselectAll');
			}
		});
	}
</script>

<table id="sysAdmin_upload_datagrid" style="overflow: hidden;"></table>
