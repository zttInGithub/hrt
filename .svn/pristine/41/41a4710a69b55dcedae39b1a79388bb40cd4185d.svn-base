<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 代理商开通-结算部 -->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentopen_00320_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentDlUnit3.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'agentMid',
			sortOrder: 'desc',
			idField : 'agentMid',
			columns : [[{
				title : '代理商编号',
				field : 'agentMid',
				width : 100,
				align : 'center',
				checkbox:true
			},{
				title : '机构编号',
				field : 'unno',
				width : 100,
				align : 'center'
			},{
				title : '代理商名称',
				field : 'agentName',
				width : 100
			},{
				title : '银行',
				field : 'bankBranch',
				width : 100
			},{
				title : '创建时间',
				field : 'createDate',
				width : 100
			},{
				title : '创建者',
				field : 'createUser',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					var status=row['approveStatus'];
					if(status != "K" && status != "Y"){
						return '<img src="${ctx}/images/start.png" title="确认开通" style="cursor:pointer;" onclick="sysAdmin_00320open_openFun('+row.agentMid+')"/>';
					}
				}
			}]],
			toolbar:[{
				id:'btn_republishAll',
				text:'批量审批',
				iconCls:'icon-start',
				handler:function(){
					sysAdmin_agentunit_00320_republish();
				}
			}
			]
		});
	});
	function sysAdmin_agentunit_00320_republish(){
		var checkedItems = $('#sysAdmin_agentopen_00320_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.agentMid);
		});               
		var agentMid=names.join(",");
		if(agentMid==null||agentMid==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#agentMid_00320").val(agentMid);
		$('#sysAdmin_00320_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_rePublishToZH.action'
		});
	}
	function bill_agentunitdata_10142_searchFun() {
		$('#sysAdmin_agentopen_00320_datagrid').datagrid('load', serializeObject($('#sysAdmin_00320_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata_10142_cleanFun() {
		$('#sysAdmin_agentopen_00320_datagrid input').val('');
	}
	//确认缴款
		function sysAdmin_00320open_openFun(agentMid){
			$.messager.confirm('确认','您确认开通该代理商?',function(result) {
				if (result) {
					$.ajax({
						url:"${ctx}/sysAdmin/agentunit_rePublishToZH.action",
						type:'post',
						data:{"agentMid":agentMid},
						dataType:'json',
						success:function(data, textStatus, jqXHR) {
							if (data.success) {
								$.messager.show({
									title : '提示',
									msg : data.msg
								});
								$('#sysAdmin_agentopen_00320_datagrid').datagrid('reload');
							} else {
								$.messager.alert('提示', data.msg);
								$('#sysAdmin_agentopen_00320_datagrid').datagrid('unselectAll');
							}
						},
						error:function() {
							$.messager.alert('提示', '确认缴款出错！');
							$('#sysAdmin_agentopen_00320_datagrid').datagrid('unselectAll');
						}
					});
				} else {
					$('#sysAdmin_agentopen_00320_datagrid').datagrid('unselectAll');
				}
			});
		}
	

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_00320_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="agentMid_00320" name="agentMid" />
			<table class="tableForm" >
					<tr>
						<th>代理商名称</th>
						<td><input name="agentName" style="width: 260px;" /></td>	
						<th>机构号</th>
						<td><input name="unno" style="width: 260px;" /></td>	
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="bill_agentunitdata_00320_searchFun();">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="bill_agentunitdata_10142_cleanFun();">清空</a>	
						</td>
					</tr>
				</table>
			</form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_agentopen_00320_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
