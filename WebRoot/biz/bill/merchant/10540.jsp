<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10540_datagrid').datagrid({
			url :'${ctx}/sysAdmin/taskDataApprove_initChangeT0App.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
	        striped: true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmtkid',
			sortOrder: 'asc',
			idField : 'bmtkid',
			columns : [[{
				field : 'bmtkid',
				hidden:true
			},{
				title : '工单编号',
				field : 'taskId',
				width : 100,
				sortable:true
			},{
				title :'机构编码',
				field :'unno',
				width : 100,
				hidden:true
			},{
				title :'机构名称',
				field :'unName',
				width : 100,
				sortable:true
			},{
				title :'商户编号',
				field :'mid',
				width : 100
			},{
				title :'商户名称',
				field :'infoName',
				width : 100,
				sortable:true
			},{
				title :'工单类别',
				field :'type',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					return 'T+0工单申请';  
				} 
			},{
				
				title :'申请日期',
				field :'mainTainDate',
				width : 100 
			},{
				title :'工单描述',
				field :'taskContext',
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
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10540_editFun('+index+','+row.bmtkid+','+row.unno+','+row.mid+')"/>&nbsp;&nbsp';
				} 
			}]]  
		});
	});
	
	
	//单条审批/查看
	function sysAdmin_10540_editFun(index,id,unno,mid) {
		$('<div id="sysAdmin_10540_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看</span>',
			width: 800,   
		    height: 500, 
		    resizable:true,
		    maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10541.jsp?bmtkid1='+id+'&mid='+mid, 
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10540_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.bmtkid = stringToList(row.bmtkid);    	
		    	$('#sysAdmin_10541_addForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10541_addForm').form('submit', {
						url:'${ctx}/sysAdmin/taskDataApprove_auditChangeT0Single.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10540_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10540_datagrid').datagrid('reload');
					    			$('#sysAdmin_10540_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10540_editDialog').dialog('destroy');
					    			$('#sysAdmin_10540_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10540_editDialog').dialog('destroy');
					$('#sysAdmin_10540_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10540_searchFun10() {
		$('#sysAdmin_10540_datagrid').datagrid('load', serializeObject($('#sysAdmin_10540_searchForm')));  
	}

	//清除表单内容
	function sysAdmin_10540_cleanFun10() {
		$('#sysAdmin_10540_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10540_searchForm" style="padding-left:10%">
			<input type="hidden" id="ids" name="ids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 160px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="infoName" style="width: 160px;" /></td>
					<th>申请日期：</th>
						<td><input id="startDay" class="easyui-datebox" data-options="editable:false" name="startDay" style="width: 156px;"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="endDay" class="easyui-datebox" data-options="editable:false" name="endDay" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>	
				</tr> 
			
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10540_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10540_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10540_datagrid" style="overflow: hidden;"></table>
	</div>
</div>



