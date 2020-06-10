<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 代理商开通 -->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentopen_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentOpen.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				title : '代理商编号',
				field : 'buid',
				width : 100,
				align : 'center',
				formatter: function(value,row,index){
					value="00000"+value;
					var todoIDfo=value.substring(value.length-6,value.length);
					return todoIDfo;
				}
			},{
				title : '机构编号',
				field : 'unno',
				width : 100
			},{
				title : '代理商名称',
				field : 'agentName',
				width : 100
			},{
				title :'经营地址',
				field :'baddr',
				width : 100
			},{
				title :'开通状态',
				field :'openName',
				width : 100
			},{
				title :'缴款状态',
				field :'amountConfirmName',
				width : 100
			},{
				title :'维护人员',
				field :'maintainUserName',
				width : 100
			},{
				title :'类别',
				field :'agentLvl',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(value==1){
						return '运营中心';
					}else if(value==2){
						return '分销运营中心';
					}else {
						return '代理商';
					}
				}
			},{
				title :'开通状态',
				field :'approveStatus',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(value=="W"){
						return "待审核";
					}else if(value=="Y"){
						return "审核通过";
					}else if(value=="C"){
						return "审核中";
					}else if(value == "K"){
						return "退回";
					}else {
						return "";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					var status=row['approveStatus'];
					if(status != "K" && status != "Y"){
						return '<img src="${ctx}/images/start.png" title="确认开通" style="cursor:pointer;" onclick="sysAdmin_00314open_openFun('+index+')"/>';
					}
				}
			}]]	
		});
	});
	
	function sysAdmin_00314open_openFun(index){
    	var rows = $('#sysAdmin_agentopen_datagrid').datagrid('getRows');
		var row = rows[index];
		var agentLvl = row['agentLvl'];
		if(agentLvl&&agentLvl==1 || agentLvl==2){
			sysAdmin_operatecenter_open_openFun(index);
		}else{
			sysAdmin_agentopen_openFun(index);
		}
	}

	//确认开通-运营中心
	function sysAdmin_operatecenter_open_openFun(index){
    	var rows = $('#sysAdmin_agentopen_datagrid').datagrid('getRows');
		var row = rows[index];
		var buid = row['buid'];
		$('<div id="sysAdmin_agentopen_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">信息审核</span>',
			width: 950,
		   height:430, 
		   closed: false,
		   href: '${ctx}/biz/bill/agent/agentunit/00317.jsp?buid='+buid,  
		   modal: true,
		   onLoad:function() {
		    	row.menuIds = stringToList(row.buid);    
		    	$('#sysAdmin_00317_editForm').form('load', row);
			},
			buttons:[{
				text:'审批通过',
				iconCls:'icon-ok',
				handler:function() {
					var imgDialog = "<div id='sysAdmin_00413_editDialog' style='padding:10px;'></div>";
					$('#sysAdmin_agentopen_editDialog').after(imgDialog);
					$('#sysAdmin_00413_editDialog').dialog({
						title: '<span style="color:#157FCC;">开通信息</span>',
						width: 450,
					    height:300, 
					    closed: false,
					    href: '${ctx}/biz/bill/agent/agentunit/00413.jsp',  
					    modal: true,
					    onLoad:function() {
					    	$('#sysAdmin_00413_editForm').form('load', row);
						},
						buttons:[{
							text:'提交',
							iconCls:'icon-ok',
							handler:function() {
								var validator = $('#sysAdmin_00413_editForm').form('validate');
					    		if(validator){
					    			$.messager.progress();
					    		}
								$('#sysAdmin_00413_editForm').form('submit', {
									url:'${ctx}/sysAdmin/agentunit_editAgentOpen.action',
									success:function(data) {
										$.messager.progress('close'); 
										var result = $.parseJSON(data);
										if (result.sessionExpire) {
						    				window.location.href = getProjectLocation();
							    		} else {
							    			if (result.success) {
							    				$('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
							    				$('#sysAdmin_agentopen_datagrid').datagrid('reload');
								    			$('#sysAdmin_00413_editDialog').dialog('destroy');
								    			$('#sysAdmin_agentopen_editDialog').dialog('destroy');
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
							}}],
						onClose:function() {
							$(this).dialog('destroy');
							// $('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
						}
					});
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					pushBack(buid);
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
			}
		});
	}

	function pushBack(buid){
		var imgDialog = "<div id='sysAdmin_00414_editDialog' style='padding:10px;'></div>";
		$('#sysAdmin_agentopen_editDialog').after(imgDialog);
		$('#sysAdmin_00414_editDialog').dialog({
			title: '<span style="color:#157FCC;">退回信息</span>',
			width: 450,
		    height:300, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00414.jsp?buid='+buid,  
		    modal: true,
		    onLoad:function() {
			},
			buttons:[{
				text:'提交',
				iconCls:'icon-ok',
				handler:function() {
					/*------**/
					$.messager.progress();
					var returnReason = $("#returnReason").val();
					returnReason=returnReason.replace(/(^\s*)|(\s*$)/g, "");
					//post to k
			 		$.ajax({
			 			url:'${ctx}/sysAdmin/agentunit_editAgentToK.action',
			     		dataType:"json",  
			     		type:"post",
			     		data:{buid:buid,
			     			returnReason:returnReason},
			    		success:function(data) {
			    			$.messager.progress('close');
			    			var json=eval(data);
			    			$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_00414_editDialog').dialog('destroy');
    	            		$('#sysAdmin_agentopen_editDialog').dialog('destroy');
    				    	$('#sysAdmin_agentopen_datagrid').datagrid('reload');	
    				    	$('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
			 	    	},
	    	            error:function(XMLHttpRequest, textStatus, errorThrown){
	    	            	$.messager.progress('close');
	    	            	$.messager.alert('提示', '操作退回出错！');
	    	            	$('#sysAdmin_00414_editDialog').dialog('destroy');
	    	            	$('#sysAdmin_agentopen_editDialog').dialog('destroy');
    				    	$('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
	    	            }
			 		});
			 		/*------**/
				}}],
			onClose:function() {
				$(this).dialog('destroy');
				// $('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
			}
		});
	}

	//确认开通-代理商
	function sysAdmin_agentopen_openFun(index){
    	var rows = $('#sysAdmin_agentopen_datagrid').datagrid('getRows');
		var row = rows[index];
		var buid= row['buid'];
		$('<div id="sysAdmin_agentopen_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认</span>',
			width: 950,
		   height:430, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00315.jsp?buid='+buid,  
		    modal: true,
		    onLoad:function() {
		    	row.menuIds = stringToList(row.buid);    	
		    	$('#sysAdmin_agentopen_editForm').form('load', row);
			},
			buttons:[{
				text:'审批通过',
				iconCls:'icon-ok',
				handler:function() {
					/*------*/
					var imgDialog = "<div id='sysAdmin_00415_editDialog' style='padding:10px;'></div>";
					$('#sysAdmin_agentopen_editDialog').after(imgDialog);
					$('#sysAdmin_00415_editDialog').dialog({
						title: '<span style="color:#157FCC;">开通信息</span>',
						width: 450,
					    height:300, 
					    closed: false,
					    href: '${ctx}/biz/bill/agent/agentunit/00415.jsp',  
					    modal: true,
					    onLoad:function() {
					    	$('#sysAdmin_00415_editForm').form('load', row);
						},
						buttons:[{
							text:'提交',
							iconCls:'icon-ok',
							handler:function() {
								var validator = $('#sysAdmin_00415_editForm').form('validate');
					    		if(validator){
					    			$.messager.progress();
					    		}
								$('#sysAdmin_00415_editForm').form('submit', {
									url:'${ctx}/sysAdmin/agentunit_editAgentOpen.action',
									success:function(data) {
										$.messager.progress('close'); 
										var result = $.parseJSON(data);
										if (result.sessionExpire) {
						    				window.location.href = getProjectLocation();
							    		} else {
							    			if (result.success) {
							    				$('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
							    				$('#sysAdmin_agentopen_datagrid').datagrid('reload');
								    			$('#sysAdmin_00415_editDialog').dialog('destroy');
								    			$('#sysAdmin_agentopen_editDialog').dialog('destroy');
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
							}}],
						onClose:function() {
							$(this).dialog('destroy');
							// $('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
						}
					});
					/*------*/
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					pushBack(buid);
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_agentopen_datagrid').datagrid('unselectAll');
			}
		});
	}

</script>

<table id="sysAdmin_agentopen_datagrid" style="overflow: hidden;"></table>