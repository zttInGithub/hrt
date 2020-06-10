<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10285_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineTaskData_serchMachineTaskDataTCD.action',
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
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title :'原终端编号',
				field :'bmtIDName',
				width : 100
			},{
				title :'新终端编号',
				field :'tid',
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
				title : '机具SN号',
				field : 'machinesn',
				width : 100,
				sortable:true
			},{
				title : '机具SIM卡号',
				field : 'simtel',
				width : 100,
				sortable:true
			},{
				title : '开始时间',
				field : 'taskStartDate',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
					}  
				}
			},{
				title : '测试时间',
				field : 'taskStep1Date',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
					}  
				}
			},{
				title : '派工时间',
				field : 'taskStep2Date',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
					}  
				}
			},{
				title : '完成时间',
				field : 'taskConfirmDate',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
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
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10285_exportFun();
				}
			}]
		});  
	});
	
	//查询提交
	function sysAdmin_10285_searchFun(){
	    $('#sysAdmin_10285_datagrid').datagrid('load', serializeObject($('#sysAdmin_10285_searchForm')));
	}
	//清除表单内容
	function sysAdmin_10285_cleanFun() {
		$('#sysAdmin_10285_searchForm input').val('');
	}
	
	function sysAdmin_10285_exportFun() {
		var checkedItems = $('#sysAdmin_10285_datagrid').datagrid('getChecked');
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
		$('#sysAdmin_10285_searchForm').form('submit',{
			url :'${ctx}/sysAdmin/machineTaskData_exportMer.action'
    	});
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_10285_searchForm" style="padding-left:10%">
			<input type="hidden" id="bmtdids" name="bmtdids" />
			<table class="tableForm" >
				<tr>
					<th >商户编号：</th>
					<td><input name="mid" style="width: 316px;" /></td>
					<th >原终端编号：</th>
					<td><input name="bmtIDName" style="width: 316px;" /></td>
				</tr>
				<tr>
					<th>工单完成时间 ：</th>
					<td><input name="createDateStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="createDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10285_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10285_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10285_datagrid" style="overflow: hidden; height:600px; width: 600px; "></table>
    </div> 
</div>


		