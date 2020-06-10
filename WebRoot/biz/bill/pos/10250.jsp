<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_machineSend_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineApprove_initSend.action',
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
				title :'收货人',
				field :'consigneeName',
				width : 100,
				sortable:true
			},{
				title :'收货人地址',
				field :'consigneeAddress',
				width : 100
			},{
				title :'收货人手机',
				field :'consigneePhone',
				width : 100
			},{
				title :'收货人固定电话',
				field :'consigneeTel',
				width : 100
			},{
				title :'邮编',
				field :'postCode',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/start.png" title="灌装发货" style="cursor:pointer;" onclick="sysAdmin_machineSend_editFun('+index+')"/>&nbsp;&nbsp';
				}
			}]],
				view: detailview,
			detailFormatter:function(index,row){
				return '<div"><table class="ddv"></table></div>';
			},
			onExpandRow: function(index,row){
				var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
				ddv.datagrid({
					url:'${ctx}/sysAdmin/machineApprove_find.action?ids='+row.bmoID,
					singleSelect:true,
					rownumbers:true,
					loadMsg:'',
					height:'auto',
					columns:[[
							{field:'MACHINEMODEL',title:'机具型号',width:200},
							{field:'MACHINETYPE',title:'机具类型',width:200,
							formatter:function(value,row,index){
								if(value=='1'){
									return '有线(拨号)';
								}else if(value=='2'){
									return '有线(拨号和网络)';
								}else if(value=='3'){
									return '无线';
								}else if(value=='4'){
									return '手刷';
								}else{
									return '其它';
								}
							}
							},
							{field:'ORDERNUM',title:'订购数量',width:200}
						]],
					onResize:function(){
							$('#sysAdmin_machineSend_datagrid').datagrid('fixDetailRowHeight',index);
					},
					onLoadSuccess:function(){
							setTimeout(function(){
								$('#sysAdmin_machineSend_datagrid').datagrid('fixDetailRowHeight',index);
							},0);
						}
					});
					$('#sysAdmin_machineSend_datagrid').datagrid('fixDetailRowHeight',index);
				}
		});
	});
	
	
	//灌装发货
	function sysAdmin_machineSend_editFun(index) {
		$('<div id="sysAdmin_machineSend_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">灌装发货</span>',
			width: 380,   
		    height: 340, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10251.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_machineSend_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.menuIds);    	
		    	$('#sysAdmin_machineSend_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_machineSend_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineApprove_editSend.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_machineSend_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_machineSend_datagrid').datagrid('reload');
					    			$('#sysAdmin_machineSend_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_machineSend_editDialog').dialog('destroy');
					    			$('#sysAdmin_machineSend_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_machineSend_editDialog').dialog('destroy');
					$('#sysAdmin_machineSend_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role_datagrid').datagrid('unselectAll');
			}
		});
	}
	
</script>

<table id="sysAdmin_machineSend_datagrid" style="overflow: hidden;"></table>

