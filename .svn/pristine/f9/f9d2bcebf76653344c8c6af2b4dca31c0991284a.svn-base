<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10150_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentUnitDataForUpdate.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				field : 'buid',
				title : '代理商编号',
				checkbox:true
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
				title :'签约人员',
				field :'signUserIdName',
				width : 100
			},{
				title :'机构状态',
				field :'statusName',
				width : 100,
				formatter : function(value,row,index) {
					if(value==0){
						return "停用";
					}else if(value==1){
						return "启用";
					}	
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_10150_searchFun('+index+')"/>&nbsp;&nbsp'+ 
					'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10150_editFun('+index+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="bill_agentunitdata10150_closeFun('+row.unno+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="bill_agentunitdata10150_startFun('+row.unno+')"/>';
				}
			}]]	,
			toolbar:[
				//{
				//	id:'btn_runAll',
				//	text:'资料导出',
				//	iconCls:'icon-query-export',
				//	handler:function(){
				//		sysAdmin_10150_exportAgents();
				//	}
				//},{
				//	id:'btn_run',
				//	text:'勾选导出',
				//	iconCls:'icon-query-export',
				//	handler:function(){
				//		sysAdmin_10150_exportFun();
				//	}
				//},
				{
					id:'btn_export',
					text:'一级代理归属资料',
					iconCls:'icon-query-export',
					handler:function(){
						sysAdmin_10150_exportAgentsAndSalesFun();
					}
				}]
		});
	});
	function sysAdmin_10150_exportAgentsAndSalesFun(){
		$('#sysAdmin_10150_searchForm').form('submit',{
    		url:'${ctx}/sysAdmin/agentunit_exportAgentsAndSales.action'
    	});
	}
	function sysAdmin_10150_exportAgents(){
		$('#sysAdmin_10150_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_exportAgents.action'
			    	});
	}
	function sysAdmin_10150_exportFun() {
		var checkedItems = $('#sysAdmin_10150_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.buid);
		});               
		var ids=names.join(",");
		if(ids==null||ids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#searchids").val(ids);
		$('#sysAdmin_10150_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_export1.action'
			    	});
	}
	
	//查看明细
	function sysAdmin_10150_searchFun(index){
		var rows = $('#sysAdmin_10150_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10410_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 900,
		    height:430, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/10141.jsp?buid='+row['buid']+'&unno='+row['unno'],  
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10141_queryForm').form('load', row);
			},
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
			}
		});
	}
	function sysAdmin_10150_editFun(index) {
		$('<div id="sysAdmin_10151_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改代理商信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/10151.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10150_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.buid);    	
		    	$('#sysAdmin_10151_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_10151_editForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_10151_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentunit_editAgentUnit.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10150_datagrid').datagrid('reload');
					    			$('#sysAdmin_10151_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10151_editDialog').dialog('destroy');
					    			$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10151_editDialog').dialog('destroy');
					$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function bill_agentunitdata10150_searchFun() {
		$('#sysAdmin_10150_datagrid').datagrid('load', serializeObject($('#sysAdmin_10150_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata10150_cleanFun() {
		$('#sysAdmin_10150_searchForm input').val('');
		$('#sysAdmin_10150_searchForm select').val('All');
	}
	
	//启用代理商
	function bill_agentunitdata10150_startFun(unno){
		$.messager.confirm('确认','您确认要启用该代理商吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/unit_startUnit.action",
					type:'post',
					data:{"unNo":unno},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10150_datagrid').datagrid('reload');
							$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '启用代理商出错！');
						$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
					}
				});
			}
		});
	}
	
	//停用代理商
	function bill_agentunitdata10150_closeFun(unno){
		$.messager.confirm('确认','您确认要停用该代理商吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/unit_closeUnit.action",
					type:'post',
					data:{"unNo":unno},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10150_datagrid').datagrid('reload');
							$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '停用代理商出错！');
						$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
					}
				});
			}
		});
	}
</script> 

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_10150_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="searchids" name="searchids" />
			<table class="tableForm" >
					<tr>
						<th>机构号</th>
						<td><input name="unno" style="width: 160px;" /></td>
						<td width="15px"></td>	
						<th>代理商名称</th>
						<td><input name="agentName" style="width: 160px;" /></td>	
						<td width="15px"></td>
						<!-- <th>代理商级别</th>
						<td>
							<select	name="unLvl" style="width: 200px;">
								<option value="" selected="selected">ALL</option>
								<option value="0">0-总公司</option>
								<option value="1">1-分公司</option>
								<option value="2">2-作业中心/代理机构</option>
								<option value="3">3-二级作业中心/二级代理机构</option>
							</select>
						</td> -->	
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="bill_agentunitdata10150_searchFun();">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="bill_agentunitdata10150_cleanFun();">清空</a>	
						</td>
					</tr>
				</table>
			</form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10150_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
