<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10300_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineTaskData_serchAddMachineTaskData.action',
			fit : true,
			fitColumns : true,  
			border : false,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true, 
			pagination : true,
			striped: true,
			pageSize : 10,
			pageList : [ 10,15 ],
			sortName: 'maintainDate',
			sortOrder: 'desc',
			idField : 'maintainDate',
			columns : [[{
				title : '主键',
				field : 'bmtdID',
				width : 100, 
				hidden: true
			},{
				title : '工单编号',
				field : 'taskID',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{ 
				title :'机构编号',
				field :'unno',
				width : 100,
				sortable:true
			},{ 
				title :'商户编号',
				field :'mid',
				width : 100,
				sortable:true
			},{
				title :'原终端编号',
				field :'bmtIDName',
				width : 100
			},{
				title :'新终端编号',
				field :'tid',
				width : 100
			},{
				title :'新终端sn号',
				field :'sn',
				width : 100
			},{
				title : '新机具型号',
				field : 'bmaIDName',
				width : 100
			},{
				title : '工单类别',
				field : 'type',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '装机';
					}else if(value=='2'){
						return '换机';
					}else if(value=='3'){
						return '撤机';
					}else{
						return '服务';
					}
				}
			},{
				title : '授权状态',
				field : 'approveStatus',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='Y'){
						return '已审批';
					}else if(value=='Z'){
						return '待审批';
					}else if(value=='K'){
						return '退回';
					}else{
						return '无需审批';
					}
				}
			},{
				title :'受理描述',
				field :'proCessconText',
				width : 100,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title :'备注',
				field :'remarks',
				width : 100,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{ 
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatus != 'K'){
						return '<img src="${ctx}/images/query_search.png" title="查看商户" style="cursor:pointer;" onclick="sysAdmin_10300_merchantInfoFun('+row.mid+')"/>';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看商户" style="cursor:pointer;" onclick="sysAdmin_10300_merchantInfoFun('+row.mid+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_remove.png" title="删除工单" style="cursor:pointer;" onclick="sysAdmin_10300_delFun('+row.bmtdID+')"/>';
					}
				}
			}]],
			toolbar:[{
				id:'btn_add',
				text:'工单申请',  
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10300_addFun();
				}
			}]  
		});  
	}); 

	function sysAdmin_10300_addFun(){
		$('<div id="sysAdmin_10300_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>',
			width: 850,
		    height:330, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10282.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_10282_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10282_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/machineTaskData_addMachineTaskData.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10300_datagrid').datagrid('reload');
					    			$('#sysAdmin_10300_addDialog').dialog('destroy');
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
					$('#sysAdmin_10300_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10300_merchantInfoFun(mid){
		$('<div id="sysAdmin_10300_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10281.jsp?mid='+mid,
		    modal: false,
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10300_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10300_delFun(bmtdID){
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:'${ctx}/sysAdmin/machineTaskData_deleteMachineTaskData.action',
					type:'post',
					data:{"bmtdID":bmtdID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10300_datagrid').datagrid('reload');
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
	function sysAdmin_10300_searchFun() {
		$('#sysAdmin_10300_datagrid').datagrid('load', serializeObject($('#sysAdmin_10300_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10300_cleanFun() {
		$('#sysAdmin_10300_searchForm input').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10300_searchForm" style="padding-left:10%">
			<table class="tableForm" >
			<tr>
				<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
					<td colspan="5" style="width: 316px;text-align: center;" >
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10300_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10300_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10300_datagrid" style="overflow: hidden; height:600px; width: 600px; "></table>
    </div> 
</div>

		