<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentconfirm_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentConfirm.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				title : '代理商编号',
				field : 'buid',
				width : 100,
				align : 'center',
				formatter: function(value,row,index){
					value="00000"+value;
					var todoIDfo=value.substring(value.length-6,value.length);
					return todoIDfo;
				}
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
				title :'维护人员',
				field :'maintainUserName',
				width : 100
			},{
				title :'风险保证金',
				field :'riskAmount',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/start.png" title="确认缴款" style="cursor:pointer;" onclick="sysAdmin_agentconfirm_confirmFun('+row.buid+')"/>';
				}
			}]]	
		});
	});
	
	//确认缴款
	function sysAdmin_agentconfirm_confirmFun(buid){
		$.messager.confirm('确认','您确认该商户已缴款吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/agentunit_editAgentConfirm.action",
					type:'post',
					data:{"buid":buid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_agentconfirm_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_agentconfirm_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '确认缴款出错！');
						$('#sysAdmin_agentconfirm_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_agentconfirm_datagrid').datagrid('unselectAll');
			}
		});
	}

</script>

<table id="sysAdmin_agentconfirm_datagrid" style="overflow: hidden;"></table>

