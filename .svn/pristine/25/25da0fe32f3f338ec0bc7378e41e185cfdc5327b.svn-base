<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_machinePrice_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineInfo_init.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'maintainDate',
			sortOrder: 'DESC',
			idField : 'bmaID',
			columns : [[{
				title : '唯一编号',
				field : 'bmaID',
				width : 100,
				formatter:function(value,row,index){
					value="00000"+value;
					return value.substring(value.length-6,value.length);
				}
			},{
				title :'机具型号',
				field :'machineModel',
				width : 100,
				sortable:true
			},{
				title :'机具类型',
				field :'machineType',
				width : 100,
				sortable:true,
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
			},{
				title :'机具单价',
				field :'machinePrice',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					return value+"元";
				}
			}/* , {
				title :'机具库存',
				field :'machineStock',
				width : 100,
				sortable:true
			} */,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_machinePrice_editFun('+index+')"/>';
				}
			}]]	
		});
	});
	
	
	//修改机具库存
	function sysAdmin_machinePrice_editFun(index) {
		$('<div id="sysAdmin_machinePrice_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改机具单价</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10271.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_machinePrice_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_machinePrice_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_machinePrice_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineInfo_edit.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_machinePrice_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_machinePrice_datagrid').datagrid('reload');
					    			$('#sysAdmin_machinePrice_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_machinePrice_editDialog').dialog('destroy');
					    			$('#sysAdmin_machinePrice_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_machinePrice_editDialog').dialog('destroy');
					$('#sysAdmin_machinePrice_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role_datagrid').datagrid('unselectAll');
			}
		});
	}
	/* //查看机具库存
	function sysAdmin_machineInfo_dialogFun(index){

		$('<div id="sysAdmin_machineInfo_descWindow"/>').dialog({
			title: '<span style="color:#157FCC;">查看机具库存</span>',
			width: 440,   
		    height: 340,
		    closed: false,
		    href: '${ctx}/frame/sysadmin/pos/10211.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_machineInfo_datagrid').datagrid('getRows');
				var row = rows[index];
				row.bmaID = stringToList(row.bmaID);
		    	$('#sysAdmin_machineInfo_showDiv').form('load', row);
			},
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
		
	} */
	
	//删除机具库存
	/* function sysAdmin_machinePrice_delFun(bmaID) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/machineInfo_delete.action",
					type:'post',
					data:{"ids":bmaID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_machineInfo_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_machineInfo_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_machineInfo_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_machineInfo_datagrid').datagrid('unselectAll');
			}
		});
	}
	 */
</script>

<table id="sysAdmin_machinePrice_datagrid" style="overflow: hidden;"></table>

