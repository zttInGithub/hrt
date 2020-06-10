<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10535_datagrid').datagrid({
			url :'${ctx}/sysAdmin/taskDataApprove_initRiskAppbaodan.action',
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
			sortName: 'BMTKID',
			sortOrder: 'desc',
			idField : 'BMTKID',
			columns : [[{
				field : 'BMTKID',
				checkbox:true
			},{
				title : '工单编号',
				field : 'TASKID',
				width : 100,
				sortable:true
			},{
				title :'机构编码',
				field :'UNNO',
				width : 100,
				hidden:true
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				
				title :'申请日期',
				field :'MAINTAINDATE',
				width : 100 
			},{
				title :'工单描述',
				field :'TASKCONTEXT',
				width : 100,
				formatter : function(value,row,index) {
					if(value!=""&&value!=null){
						return value;
					}else{
						return "无";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_taskDataApprove_editFun('+index+','+row.BMTKID+','+row.UNNO+')"/>&nbsp;&nbsp';
					
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
	//调整限额
	function sysAdmin_merchantY_limit1(index){
		var rows = $('#sysAdmin_10535_datagrid').datagrid('getRows');
		var row = rows[index];
		var id = row.bmtkid;
		$('<div id="sysAdmin_merchantjoin1_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">限额调整</span>',
			width: 380,   
		    height: 220, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10537.jsp?bmtkid1='+id,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10535_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.bmtkid = stringToList(row.bmtkid);    	
		    	$('#sysAdmin_merchant10537_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantjoin_datagrid').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchant10537_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantTaskDetail4_updateMerchantLimit.action',
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10535_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin1_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoin1_editDialog').dialog('destroy');
					    			$('#sysAdmin_10535_datagrid').datagrid('reload');
					    			$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoin1_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//单条审批/查看
	function sysAdmin_taskDataApprove_editFun(index,id,unno) {
		$('<div id="sysAdmin_10535_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看</span>',
			width: 800,   
		    height: 500, 
		    resizable:true,
		    maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10536_1.jsp?bmtkid1='+id,  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10535_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$("#sysAdmin_10536_addForm #taskId").val(row.TASKID);
		    	$("#sysAdmin_10536_addForm #unName").val(row.UNNAME);
		    	$("#sysAdmin_10536_addForm #infoName").val(row.INFONAME);
		    	$("#sysAdmin_10536_addForm #mid").val(row.MID);
		    	$("#sysAdmin_10536_addForm #approveStatus").val(row.APPROVESTATUS);
		    	$("#sysAdmin_10536_addForm #taskContext").val(row.TASKCONTEXT);
		    	$("#sysAdmin_10536_addForm #processContext").val(row.PROCESSCONTEXT);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10536_addForm').form('submit', {
						url:'${ctx}/sysAdmin/taskDataApprove_auditRiskSingle.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10535_datagrid').datagrid('reload');
					    			$('#sysAdmin_10535_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10535_editDialog').dialog('destroy');
					    			$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10535_editDialog').dialog('destroy');
					$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
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
		var checkedItems = $('#sysAdmin_10535_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.BMTKID);
		});               
		var bmtkids=names.join(",");
		if(bmtkids==null||bmtkids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要通过所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/taskDataApprove_auditRiskThrough.action",
					type:'post',
					data:{"bmtkids":bmtkids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10535_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
			}
		});
	}
	//批量拒绝审批
	function sysAdmin_taskDataApprove_rejectFun() {
		var checkedItems = $('#sysAdmin_10535_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.BMTKID);
		});               
		var bmtkids=names.join(",");
		if(bmtkids==null||bmtkids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要拒绝所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/taskDataApprove_auditReject.action",
					type:'post',
					data:{"bmtkids":bmtkids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10535_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '拒绝记录出错！');
						$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_10535_datagrid').datagrid('unselectAll');
			}
		});
	} 
	
	//表单查询
	function sysAdmin_10535_searchFun10() {
		$('#sysAdmin_10535_datagrid').datagrid('load', serializeObject($('#sysAdmin_10535_searchForm')));  
	}

	//清除表单内容
	function sysAdmin_10535_cleanFun10() {
		$('#sysAdmin_10535_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_10535_searchForm" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
					<th>商户编号：</th>
					<td><input name="mid" style="width: 140px;" /></td>
				    
					<th>申请日期：</th>
						<td><input id="startDay" class="easyui-datebox" data-options="editable:false" name="startDay" style="width: 100px;"/></td>
						<td>&nbsp;-&nbsp;</td>
						<td><input id="endDay" class="easyui-datebox" data-options="editable:false" name="endDay" style="width: 100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>	
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10535_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10535_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10535_datagrid" style="overflow: hidden;"></table>
	</div>
</div>



