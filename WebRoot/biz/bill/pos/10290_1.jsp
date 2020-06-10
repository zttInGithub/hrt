<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10290_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineTaskData_serchMachineTaskDataJudgebaodan.action',
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
				checkbox:true
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
				title :'新终端SN',
				field :'sn',
				width : 100 
			},{
				title : '新机具型号',
				field : 'bmaIDName',
				width : 100
			},{
				title :'换机类型',
				field :'changeType',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '加号换机';
					}else if(value=='2'){
						return '不加号换机';
					}else{
						return '';
					}
				}
			},{ 
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看商户" style="cursor:pointer;" onclick="sysAdmin_10290_merchantInfoFun('+row.mid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="审批" style="cursor:pointer;" onclick="sysAdmin_10290_start('+row.bmtdID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_10290_close('+row.bmtdID+')"/>';
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10290_exportFun();
				}
			}]
		});  
	}); 
	
	function sysAdmin_10290_start(bmtdID){
		$('<div id="sysAdmin_10290_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">受理描述</span>',
			width: 450,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10286.jsp',
		    modal: false,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_10286_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_10286_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineTaskData_updateMachineTaskDataY.action?strBmtdID='+bmtdID,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10290_datagrid').datagrid('reload');
					    			$('#sysAdmin_10290_queryDialog').dialog('destroy');
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
					$('#sysAdmin_10290_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10290_close(bmtdID){
		$('<div id="sysAdmin_10290_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">受理描述</span>',
			width: 450,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10286.jsp',
		    modal: false,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10286_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineTaskData_updateMachineTaskDataK.action?strBmtdID='+bmtdID,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10290_datagrid').datagrid('reload');
					    			$('#sysAdmin_10290_queryDialog').dialog('destroy');
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
					$('#sysAdmin_10290_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	function sysAdmin_10290_merchantInfoFun(mid){
		$('<div id="sysAdmin_10290_queryDialog"/>').dialog({
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
					$('#sysAdmin_10290_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	
	function sysAdmin_10290_exportFun(){
		var checkedItems = $('#sysAdmin_10290_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtdID);
		});               
		var bmtdids=names.join(",");
		if(bmtdids==null||bmtdids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmtdids").val(bmtdids);
		$('#sysAdmin_10290_searchForm').form('submit',{
			url :'${ctx}/sysAdmin/machineTaskData_exportMachineTaskDataJudge.action'
    	});
	}
	
			//表单查询
	function sysAdmin_10290_searchFun() {
		$('#sysAdmin_10290_datagrid').datagrid('load', serializeObject($('#sysAdmin_10290_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10290_cleanFun() {
		$('#sysAdmin_10290_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
 <div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10290_searchForm" style="padding-left:10%">
			<input type="hidden" id="bmtdids" name="bmtdids" />
			<table class="tableForm" >
			<tr>
				<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
					<td colspan="5" style="width: 316px;text-align: center;" >
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10290_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10290_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10290_datagrid" style="overflow: hidden; height:600px; width: 600px; "></table>
    </div> 
</div>

		