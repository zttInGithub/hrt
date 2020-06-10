<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_00330_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentUnitTask_queryAgentUnitTask.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'BAUTID',
			sortOrder: 'desc',
			idField : 'BAUTID',
			columns : [[{
				title : 'ID',
				field : 'BAUTID',
				width : 100,
				hidden:true
			},{
				title : 'BAUTDID',
				field : 'BAUTDID',
				width : 100,
				hidden:true
			},{
				title : '机构编号',
				field : 'UNNO',
				width : 100
			},{
				title : '机构名称',
				field : 'UN_NAME',
				width : 100
			},{
				title : '工单类型',
				field : 'TASKTYPE',
				width : 100,
				formatter : function(value,row,index) {
					if(value==1){
						return "代理商基础信息变更申请";
					}else if(value==2){
						return "代理商结算信息变更申请";
					}else if(value == 3){
						return "代理商联系人变更申请";
					}else {
						return "";
					}
				}
			},{
				title : '工单状态',
				field : 'APPROVESTATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value=="W"){
						return "待审核";
					}else if(value=="Y"){
						return "审核通过";
					}else if(value == "K"){
						return "退回";
					}else {
						return "";
					}
				}
			},{
				title :'申请时间',
				field :'MAINTAINDATE',
				width : 100
			},{
				title :'审核时间',
				field :'APPROVEDATE',
				width : 100
			},{
				title :'退回原因',
				field :'APPROVENOTE',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.APPROVESTATUS=="W"){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_00330_queryFun('+index+')"/>&nbsp;&nbsp;&nbsp;'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_00330_deleteFun('+index+')"/>';
					}else if(row.APPROVESTATUS=="K"){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_00330_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_00330_deleteFun('+index+')"/>';
					}else if(row.APPROVESTATUS=="Y"){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_00330_queryFun('+index+')"/>';
					}
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'基础信息变更申请',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_00330_addFun();
					}
				},{
					id:'btn_add',
					text:'结算信息变更申请',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_00330_addFun2();
					}
				},{
					id:'btn_add',
					text:'联系人变更申请',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_00330_addFun3();
					}
				}]		
		});
	});
	
	//查看明细 
	function sysAdmin_00330_queryFun(index) {
		var rows = $('#sysAdmin_00330_datagrid').datagrid('getRows');
		var row  = rows[index];
		//商户基本信息明细
		if(row.TASKTYPE=="1"){
			$('<div id="sysAdmin_agentTaskDetail1_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">基本信息</span>', 
			width: 950,   
			    height: 500, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    href: '${ctx}/biz/bill/agent/agentunit/00334.jsp?bautdid='+row.BAUTDID,  
			    modal: true,
			    buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
			$('#sysAdmin_agentTaskDetail1_datagrid').dialog('destroy');
			} 
			}],
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
			}
		//商户银行信息明细
		if(row.TASKTYPE=="2"){
			$('<div id="sysAdmin_agentTaskDetail2_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">结算信息</span>', 
				width: 950,   
				    height: 500, 
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/agent/agentunit/00335.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_agentTaskDetail2_datagrid').dialog('destroy');
				}  
				}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
		 //商户费率信息查询
		if(row.TASKTYPE=="3"){
			$('<div id="sysAdmin_agentTaskDetail3_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">联系人信息</span>', 
				width: 950,   
				    height: 500,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/agent/agentunit/00336.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_agentTaskDetail3_datagrid').dialog('destroy');
				}  
				}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
	}
	//修改信息 
	function sysAdmin_00330_editFun(index) {
		var rows = $('#sysAdmin_00330_datagrid').datagrid('getRows');
		var row  = rows[index];
		//商户基本信息明细
		if(row.TASKTYPE=="1"){
			$('<div id="sysAdmin_agentTaskDetail1_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">基本信息</span>', 
			width: 950,   
			    height: 500, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    onLoad:function() {
			    	$('#bautdid').val(row.BAUTDID);
				},
			    href: '${ctx}/biz/bill/agent/agentunit/00337.jsp?bautdid='+row.BAUTDID,  
			    modal: true,
			    buttons:[{
			    	text:'确认',
			    	iconCls:'icon-ok',
			    	handler:function() {
			    		var validator = $('#sysAdmin_00337_from').form('validate');
			    		if(validator){
			    			$.messager.progress();
			    		}
			    		$('#sysAdmin_00337_from').form('submit',{
				    		url:'${ctx}/sysAdmin/agentUnitTask_updateAgentUnitTask.action',
			    			success:function(data) {
			    				$.messager.progress('close'); 
			    				var result = $.parseJSON(data);
				    			if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
					    				$('#sysAdmin_00330_datagrid').datagrid('reload');
						    			$('#sysAdmin_agentTaskDetail1_datagrid').dialog('destroy');
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
					handler:function() {
					$('#sysAdmin_agentTaskDetail1_datagrid').dialog('destroy');
					} 
				}],
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
			}
		//商户银行信息明细
		if(row.TASKTYPE=="2"){
			$('<div id="sysAdmin_agentTaskDetail2_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">结算信息</span>', 
				width: 950,   
				    height: 500, 
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    onLoad:function() {
				    	$('#bautdid').val(row.BAUTDID);
					},
				    href: '${ctx}/biz/bill/agent/agentunit/00338.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    		var validator = $('#sysAdmin_00338_from').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
				    		$('#sysAdmin_00338_from').form('submit',{
					    		url:'${ctx}/sysAdmin/agentUnitTask_updateAgentUnitTask.action',
				    			success:function(data) {
				    				$.messager.progress('close'); 
				    				var result = $.parseJSON(data);
					    			if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_00330_datagrid').datagrid('reload');
							    			$('#sysAdmin_agentTaskDetail2_datagrid').dialog('destroy');
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
						handler:function() {
						$('#sysAdmin_agentTaskDetail2_datagrid').dialog('destroy');
						} 
					}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
		 //商户费率信息查询
		if(row.TASKTYPE=="3"){
			$('<div id="sysAdmin_agentTaskDetail3_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">联系人信息</span>', 
				width: 950,   
				    height: 500,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    onLoad:function() {
				    	$('#bautdid').val(row.BAUTDID);
					},
				    href: '${ctx}/biz/bill/agent/agentunit/00339.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    		var validator = $('#sysAdmin_00339_from').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
				    		$('#sysAdmin_00339_from').form('submit',{
					    		url:'${ctx}/sysAdmin/agentUnitTask_updateAgentUnitTask.action',
				    			success:function(data) {
				    				$.messager.progress('close'); 
				    				var result = $.parseJSON(data);
					    			if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_00330_datagrid').datagrid('reload');
							    			$('#sysAdmin_agentTaskDetail3_datagrid').dialog('destroy');
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
						handler:function() {
						$('#sysAdmin_agentTaskDetail3_datagrid').dialog('destroy');
						} 
					}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
	}
	//表单查询
	function sysAdmin_00330_searchFun() {
		$('#sysAdmin_00330_datagrid').datagrid('load', serializeObject($('#sysAdmin_00330_searchForm')));
	}

	//清除表单内容
	function sysAdmin_00330_cleanFun() {
		$('#sysAdmin_00330_searchForm input').val('');
	}
	
	function sysAdmin_00330_addFun() {
		$('<div id="sysAdmin_agentunit_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>',
			width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00331.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		if($('#legalAUpLoadFile').val()!="" && $('#legalBUpLoadFile').val()!="" && $('#busLicUpLoadFile').val()!="" && $('#dealUpLoadFile').val()!="" ){
			    		var validator = $('#sysAdmin_00331_from').form('validate');
			    		if(validator){
			    			$.messager.progress();
			    		}
			    		$('#sysAdmin_00331_from').form('submit',{
				    		url:'${ctx}/sysAdmin/agentUnitTask_addAgentUnitTask.action',
			    			success:function(data) {
			    				$.messager.progress('close'); 
			    				var result = $.parseJSON(data);
				    			if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
					    				$('#sysAdmin_00330_datagrid').datagrid('reload');
						    			$('#sysAdmin_agentunit_addDialog').dialog('destroy');
						    			$.messager.show({
											title : '提示',
											msg : result.msg
										});
						    		} else {
						    			//$('#sysAdmin_agentunit_addDialog').dialog('destroy');
						    			$.messager.alert('提示', result.msg);
							    	}
						    	}
				    		}
				    	});
		    		}else{
		    			$.messager.alert('提示', '请上传所需文件！');
		    		}
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_agentunit_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	function sysAdmin_00330_addFun2() {
		$('<div id="sysAdmin_agentunit_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>',
			width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00332.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		if($('#accountLegalAUpLoadFile').val()!="" && $('#accountLegalBUpLoadFile').val()!="" && $('#accountLegalHandUpLoadFile').val()!="" && $('#accountAuthUpLoadFile').val()!="" ){
					   	var validator = $('#sysAdmin_00332_from').form('validate');
			    		if(validator){
			    			$.messager.progress();
			    		}
			    		$('#sysAdmin_00332_from').form('submit',{
				    		url:'${ctx}/sysAdmin/agentUnitTask_addAgentUnitTask.action',
			    			success:function(data) {
			    				$.messager.progress('close'); 
			    				var result = $.parseJSON(data);
				    			if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
					    				$('#sysAdmin_00330_datagrid').datagrid('reload');
						    			$('#sysAdmin_agentunit_addDialog').dialog('destroy');
						    			$.messager.show({
											title : '提示',
											msg : result.msg
										});
						    		} else {
						    			//$('#sysAdmin_agentunit_addDialog').dialog('destroy');
						    			$.messager.alert('提示', result.msg);
							    	}
						    	}
				    		}
				    	});
		    		}else{
		    			$.messager.alert('提示', '请上传所需文件！');
		    		}
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_agentunit_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	function sysAdmin_00330_addFun3() {
		$('<div id="sysAdmin_agentunit_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>',
			width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00333.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_00333_from').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_00333_from').form('submit',{
			    		url:'${ctx}/sysAdmin/agentUnitTask_addAgentUnitTask.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_00330_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			//$('#sysAdmin_agentunit_addDialog').dialog('destroy');
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
					$('#sysAdmin_agentunit_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	function sysAdmin_agentunit_editFun(index) {
    	var rows = $('#sysAdmin_00330_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_agentunit_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改代理商信息</span>',
			width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00312.jsp?buid='+row['buid'],  
		    modal: true,
		    onLoad:function() {
		    	row.menuIds = stringToList(row.buid);    	
		    	$('#sysAdmin_agentunit_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_agentunit_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentunit_editAgentUnit.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_00330_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			// $('#sysAdmin_agentunit_editDialog').dialog('destroy');
					    			$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_agentunit_editDialog').dialog('destroy');
					$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_00330_deleteFun(index){
		var rows = $('#sysAdmin_00330_datagrid').datagrid('getRows');
		var row  = rows[index];
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/agentUnitTask_deleteAgentUnitTask.action",
					type:'post',
					data:{"bautid":row.BAUTID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_00330_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_00330_datagrid').datagrid('unselectAll');
			}
		});
	}

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_00330_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 130px;" /></td>
					<th>&nbsp;&nbsp;工单类型</th>
					<td>
						<select name="taskType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:165px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">代理商基础信息变更申请</option>
		    				<option value="2">代理商结算信息变更申请</option>
		    				<option value="3">代理商联系人变更申请</option>
		    			</select>
					</td>
					<th>&nbsp;&nbsp;工单状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="W">待审核</option>
		    				<option value="K">退回</option>
		    				<option value="Y">已审核</option>
		    			</select>
					</td>		
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_00330_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_00330_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_00330_datagrid"></table>
    </div> 
</div>

