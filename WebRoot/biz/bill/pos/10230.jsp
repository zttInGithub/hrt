<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_machineApprove_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineApprove_initApp.action',
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
				title :'收货方式',
				field :'shipmeThod',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if(value=='1'){
						return "自提";
					}else{
						return "送货";
					}
				}
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
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/start.png" title="预购审批" style="cursor:pointer;" onclick="sysAdmin_machineApprove_editFun('+index+','+row.bmoID+')"/>';
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
				}] */ ,
				view: detailview,
			detailFormatter:function(index,row){
				return '<div id="detailForm-'+index+'" ></div>';
			},
			onExpandRow: function(index,row){
				 $('#detailForm-'+index).datagrid({
					url:'${ctx}/sysAdmin/machineApprove_find.action?ids='+row.bmoID,
					singleSelect:true,
					rownumbers:true,
					loadMsg:'',
					height:'auto',
					columns:[[
							{field:'MACHINEMODEL',title:'机具型号',width:100},
							{field:'MACHINETYPE',title:'机具类型',width:150,
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
							{field:'MACHINEPRICE',title:'机具单价',width:100},
							{field:'MACHINESTOCK',title:'机具库存',width:100},
							{field:'ACTIONPRICE',title:'实际执行单价',width:150},
							{field:'ORDERNUM',title:'订购数量',width:100},
							{field:'ORDERAMOUNT',title:'订单金额',width:150},
						]],
					onResize:function(){
							$('#sysAdmin_machineApprove_datagrid').datagrid('fixDetailRowHeight',index);
					},
					onLoadSuccess:function(){
							setTimeout(function(){
								$('#sysAdmin_machineApprove_datagrid').datagrid('fixDetailRowHeight',index);
							},0);
						}
						
					});
					$('#sysAdmin_machineApprove_datagrid').datagrid('fixDetailRowHeight',index);
				}
				
		});
	});
	
	
	//审批机具订单
	function sysAdmin_machineApprove_editFun(index,bmoid) {
		$('<div id="sysAdmin_machineApprove_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审批订单</span>',
			width: 800,   
		    height: 540, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10231.jsp?bmoID='+bmoid,  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_machineApprove_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_machineApprove_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_machineApprove_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineApprove_editApp.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				//$('fieldset').remove();
				    				$('#sysAdmin_machineApprove_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_machineApprove_datagrid').datagrid('reload');
					    			$('#sysAdmin_machineApprove_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_machineApprove_editDialog').dialog('destroy');
					    			$('#sysAdmin_machineApprove_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_machineApprove_editDialog').dialog('destroy');
					$('#sysAdmin_machineApprove_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	/* //删除机具订单
	function sysAdmin_machineApprove_delFun(bmaID) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/machineApprove_delete.action",
					type:'post',
					data:{"ids":bmaID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_machineApprove_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_machineApprove_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_machineApprove_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_machineApprove_datagrid').datagrid('unselectAll');
			}
		});
	} */
	
</script>

<table id="sysAdmin_machineApprove_datagrid" style="overflow: hidden;"></table>

