<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_machineSup_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineApprove_initSup.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'maintainDate',
			sortOrder: 'asc',
			idField : 'bmoID',
			columns : [[{
				title : '唯一编号',
				field : 'bmoID',
				width : 100,
				hidden:true
			},{
				title :'机构编码',
				field :'unNO',
				width : 100,
				hidden:true
			},{
				title :'订单编号',
				field :'orderID',
				width : 100,
				sortable:true
			},{
				title :'机构名称',
				field :'unName',
				width : 100,
				sortable:true
			},{
				title :'订单金额',
				field :'orderAmount',
				width : 100,
				sortable:true
			},{
				title :'收货人',
				field :'consigneeName',
				width : 100,
				sortable:true
			},{
				title :'收货人手机',
				field :'consigneePhone',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/start.png" title="汇款确认" style="cursor:pointer;" onclick="sysAdmin_machineSup_editFun('+index+')"/>&nbsp;&nbsp';
				}
			}]]/* ,
			toolbar:[{
					text:'修改',
					iconCls:'icon-edit'
				},{
					text:'删除',
					iconCls:'icon-remove'
				},{
					id:'btn_add',
					text:'添加',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_machineApprove_addFun();
					}
				}] */
				
		});
	});
	
	
	//订单汇款核对 
	function sysAdmin_machineSup_editFun(index) {
		$('<div id="sysAdmin_machineSup_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">汇款核对</span>',
			width: 380,   
		    height: 340, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10241.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_machineSup_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.menuIds);    	
		    	$('#sysAdmin_machineSup_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_machineSup_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineApprove_editSup.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_machineSup_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_machineSup_datagrid').datagrid('reload');
					    			$('#sysAdmin_machineSup_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_machineSup_editDialog').dialog('destroy');
					    			$('#sysAdmin_machineSup_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_machineSup_editDialog').dialog('destroy');
					$('#sysAdmin_machineSup_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_machineSup_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	
</script>

<table id="sysAdmin_machineSup_datagrid" style="overflow: hidden;"></table>

