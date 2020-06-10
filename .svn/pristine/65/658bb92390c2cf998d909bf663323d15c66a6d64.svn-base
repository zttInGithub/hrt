<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentsales_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentsales_listAgentSales.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'busid',
			sortOrder: 'desc',
			idField : 'busid',
			columns : [[{
				title : 'ID',
				field : 'busid',
				width : 100,
				hidden : true
			},{
				title :'机构号',
				field :'unno',
				width : 100
			},{
				title :'机构名称',
				field :'unnoName',
				width : 100
			},{
				title :'姓名',
				field :'saleName',
				width : 100
			},{
				title : '手机号码',
				field : 'phone',
				width : 100
			},{
				title :'电话',
				field :'telephone',
				width : 100,
				hidden: true
			},{
				title :'邮箱',
				field :'email',
				width : 100,
				hidden: true
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_agentsales_editFun('+index+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_agentsales_delFun('+row.busid+')"/>';
				}
			}]],
			
			toolbar:[{
					id:'btn_add',
					text:'添加业务员',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_agentsales_addFun();
					}
				}]		
		});
	});
	
	//添加
	function sysAdmin_agentsales_addFun() {
		$('<div id="sysAdmin_agentsales_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">业务员添加</span>',
			width: 350,
		    height:250, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentsales/00321.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_agentsales_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_agentsales_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentsales_addAgentSales.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentsales_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentsales_addDialog').dialog('destroy');
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
					$('#sysAdmin_agentsales_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}


	function sysAdmin_agentsales_editFun(index) {
		$('<div id="sysAdmin_agentsales_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改业务员信息</span>',
			width: 350,
		    height:300, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentsales/00322.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_agentsales_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.buid);    	
		    	$('#sysAdmin_agentsales_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_agentsales_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentsales_editAgentSales.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentsales_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentsales_editDialog').dialog('destroy');
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
					$('#sysAdmin_agentsales_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//删除业务员
	function sysAdmin_agentsales_delFun(busid) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/agentsales_deleteAgentSales.action",
					type:'post',
					data:{"ids":busid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_agentsales_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
					}
				});
			}
		});
	}
	
	//表单查询
	function sysAdmin_agentsales_searchFun() {
		$('#sysAdmin_agentsales_datagrid').datagrid('load', serializeObject($('#sysAdmin_agentsales_searchSalesForm'))); 
	}

	//清除表单内容
	function sysAdmin_agentsales_cleanFun() {
		$('#sysAdmin_agentsales_searchSalesForm input').val('');
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_agentsales_searchSalesForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>姓名：</th>
					<td><input name="saleName" style="width: 316px;" /></td>
					
				    <td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_agentsales_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_agentsales_cleanFun();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_agentsales_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>





