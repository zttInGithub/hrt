<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_00340_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentUnitTask_queryAgentUnitTask.action?approveStatus=W',
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
						return '<img src="${ctx}/images/start.png" title="审核" style="cursor:pointer;" onclick="sysAdmin_00340_queryFun('+index+')"/>';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_00340_queryFun2('+index+')"/>';
					}
				}
			}]]
		});
	});
	//查看明细 
	function sysAdmin_00340_queryFun2(index) {
		var rows = $('#sysAdmin_00340_datagrid').datagrid('getRows');
		var row  = rows[index];
		var bautid = row.BAUTID;
		//商户基本信息明细
		if(row.TASKTYPE=="1"){
			$('<div id="sysAdmin_agentTaskDetail_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">基本信息</span>', 
			width: 950,   
			    height: 500, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    href: '${ctx}/biz/bill/agent/agentunit/00334.jsp?bautdid='+row.BAUTDID,  
			    modal: true,
			    buttons:[{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function() {
						$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
					}
				}],
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
			}
		//商户银行信息明细
		if(row.TASKTYPE=="2"){
			$('<div id="sysAdmin_agentTaskDetail_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">结算信息</span>', 
				width: 950,   
				    height: 500, 
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/agent/agentunit/00335.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
						text:'关闭',
						iconCls:'icon-cancel',
						handler:function() {
							$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
						}
					}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
		 //商户费率信息查询
		if(row.TASKTYPE=="3"){
			$('<div id="sysAdmin_agentTaskDetail_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">联系人信息</span>', 
				width: 950,   
				    height: 500,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/agent/agentunit/00336.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
						text:'关闭',
						iconCls:'icon-cancel',
						handler:function() {
							$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
						}
					}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
	}
	//查看明细 
	function sysAdmin_00340_queryFun(index) {
		var rows = $('#sysAdmin_00340_datagrid').datagrid('getRows');
		var row  = rows[index];
		var bautid = row.BAUTID;
		//商户基本信息明细
		if(row.TASKTYPE=="1"){
			$('<div id="sysAdmin_agentTaskDetail_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">基本信息</span>', 
			width: 950,   
			    height: 500, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    href: '${ctx}/biz/bill/agent/agentunit/00334.jsp?bautdid='+row.BAUTDID,  
			    modal: true,
			    buttons:[{
					text:'审批',
					iconCls:'icon-ok',
					handler:function() {
						sysAdmin_00340_queryEditFunY(bautid);
					}
				},{
					text:'退回',
					iconCls:'icon-cancel',
					handler:function() {
						sysAdmin_00340_queryEditFun(bautid);
					}
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function() {
						$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
					}
				}],
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
			}
		//商户银行信息明细
		if(row.TASKTYPE=="2"){
			$('<div id="sysAdmin_agentTaskDetail_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">结算信息</span>', 
				width: 950,   
				    height: 500, 
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/agent/agentunit/00335.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
						text:'审批',
						iconCls:'icon-ok',
						handler:function() {
							sysAdmin_00340_queryEditFunY(bautid);
						}
					},{
						text:'退回',
						iconCls:'icon-cancel',
						handler:function() {
							sysAdmin_00340_queryEditFun(bautid);
						}
					},{
						text:'关闭',
						iconCls:'icon-cancel',
						handler:function() {
							$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
						}
					}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
		 //商户费率信息查询
		if(row.TASKTYPE=="3"){
			$('<div id="sysAdmin_agentTaskDetail_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">联系人信息</span>', 
				width: 950,   
				    height: 500,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/agent/agentunit/00336.jsp?bautdid='+row.BAUTDID,  
				    modal: true,
				    buttons:[{
						text:'审批',
						iconCls:'icon-ok',
						handler:function() {
							sysAdmin_00340_queryEditFunY(bautid);
						}
					},{
						text:'退回',
						iconCls:'icon-cancel',
						handler:function() {
							sysAdmin_00340_queryEditFun(bautid);
						}
					},{
						text:'关闭',
						iconCls:'icon-cancel',
						handler:function() {
							$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
						}
					}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
	}
	//通过
	function sysAdmin_00340_queryEditFunY(bautid){
		$.ajax({
			url:'${ctx}/sysAdmin/agentUnitTask_updateAgentUnitTaskY.action',
			type:'post',
			data:{bautid:bautid},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					$('#sysAdmin_00340_datagrid').datagrid('unselectAll');
    				$('#sysAdmin_00340_datagrid').datagrid('reload');
    				$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
	    			$.messager.show({
						title : '提示',
						msg : data.msg
					});
				} else {
					$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
	    			$('#sysAdmin_00340_datagrid').datagrid('unselectAll');
	    			$.messager.alert('提示', data.msg);
				}
			},
			error:function() {
				$.messager.alert('提示', '通过记录出错！');
				$('#sysAdmin_00340_datagrid').datagrid('unselectAll');
			}
		});
	}
	//退回
	function sysAdmin_00340_queryEditFun(bautid){
		$('<div id="sysAdmin_00340_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    onLoad:function() {
		    	$('#bautid_00341').val(bautid);
			},
		    href: '${ctx}/biz/bill/agent/agentunit/00341.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_00341_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentUnitTask_updateAgentUnitTaskK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_00340_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_00340_datagrid').datagrid('reload');
				    				$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
					    			$('#sysAdmin_00340_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
					    			$('#sysAdmin_00340_editDialog').dialog('destroy');
					    			$('#sysAdmin_00340_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_00340_editDialog').dialog('destroy');
					$('#sysAdmin_00340_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
			}
		});
	}
	//表单查询
	function sysAdmin_00340_searchFun() {
		$('#sysAdmin_00340_datagrid').datagrid('load', serializeObject($('#sysAdmin_00340_searchForm')));
	}

	//清除表单内容
	function sysAdmin_00340_cleanFun() {
		$('#sysAdmin_00340_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_00340_searchForm" style="padding-left:10%">
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
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_00340_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_00340_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_00340_datagrid"></table>
    </div> 
</div>

