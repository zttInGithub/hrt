<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentsalesGroup_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentsales_listAgentSalesGroup.action',
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
				title :'登陆账号',
				field :'LOGIN_NAME',
				width : 100
			},{
				title :'销售姓名',
				field :'SALENAME',
				width : 100
			},{
				title : '职能',
				field : 'ISLEADER',
				width : 100,
				formatter: function(value,row,index){
					if(value == 1){
						return '组长';
					}else{
						return '组员';
					}
				}
			},{
				title :'组名',
				field :'SALESGROUP',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_agentsalesGroup_editFun('+index+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_agentsalesGroup_delFun('+row.BUSID+')"/>';
				}
			}]],
			
			toolbar:[{
					id:'btn_add',
					text:'添加公司组别销售',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_agentsalesGroup_addFun();
					}
				}]		
		});
	});
	
	//添加
	function sysAdmin_agentsalesGroup_addFun() {
		$('<div id="sysAdmin_agentsalesGroup_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">公司销售添加</span>',
			width: 350,
		    height:320, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentsales/SalesGroupInfoAdd.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_agentsalesGroup_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_agentsalesGroup_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentsales_addAgentSalesGroup.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentsalesGroup_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentsalesGroup_addDialog').dialog('destroy');
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
					$('#sysAdmin_agentsalesGroup_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

    //修改
	function sysAdmin_agentsalesGroup_editFun(index){
		var rows = $('#sysAdmin_agentsalesGroup_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_agentsalesGroup_update"/>').dialog({
			title: '<span style="color:#157FCC;">公司销售组别信息修改</span>',
			width: 400,    
			    height: 200,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/bill/agent/agentsales/SalesGroupInfoUpdate.jsp?busid='+row.BUSID,  
			    modal: true,
			    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$.messager.confirm('操作提示','您确定要修改该销售组别信息吗？',function(data){
					    if (data){
							$('#sysAdmin_agentsalesGroupUpdate').form('submit', {
								url:'${ctx}/sysAdmin/agentsales_updateAgentSalesGroup.action',
								success:function(data) {
									var result = $.parseJSON(data);
									if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_agentsalesGroup_datagrid').datagrid('reload');
							    			$('#sysAdmin_agentsalesGroup_update').dialog('destroy');
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
					});
					
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_agentsalesGroup_update').dialog('destroy');
				}
			}],onClose:function() {
				$(this).dialog('destroy');
			}		
		});
	}
	
	//删除业务员
	function sysAdmin_agentsalesGroup_delFun(busid) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/agentsales_deleteAgentSales.action",
					type:'post',
					data:{"busids":busid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_agentsalesGroup_datagrid').datagrid('reload');
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
	function sysAdmin_agentsalesGroup_searchFun() {
		$('#sysAdmin_agentsalesGroup_datagrid').datagrid('load', serializeObject($('#sysAdmin_agentsalesGroup_searchSalesForm'))); 
	}

	//清除表单内容
	function sysAdmin_agentsalesGroup_cleanFun() {
		$('#sysAdmin_agentsalesGroup_searchSalesForm input').val('');
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_agentsalesGroup_searchSalesForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>姓名：</th>
					<td><input name="saleName" style="width: 200px;" /></td>
					
					<th>职能</th>
					<td>
						<select name="isleader" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
							<option value="">查询所有</option>
		   					<option value="1">组长</option>
		    				<option value="2">组员</option>
		   				</select>
					</td>
					
					<th>组别：</th>
					<td style="width:270px;">
	   					<select id="salesgroup" name="salesgroup" class="easyui-combogrid" style="width:250px;"></select>
   					</td>
					
				    <td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_agentsalesGroup_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_agentsalesGroup_cleanFun();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_agentsalesGroup_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>
<script type="text/javascript">
	$('#salesgroup').combogrid({
			url : '${ctx}/sysAdmin/agentsales_queryAgentsalesGroup.action',
			idField:'SALESGROUP',
			textField:'SALESGROUP',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'SALESGROUP',title:'组名',width:250}
			]] 
		});
</script>





