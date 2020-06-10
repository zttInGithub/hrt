<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单(代理) -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10582_datagrid').datagrid({
			url :'${ctx}/sysAdmin/changeTerRate_queryChangeTerRate.action?approveStatus=W',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'bctrid',
			columns : [[{
				field : 'bctrid',
				checkbox:true
			},{
				title : 'SN起始',
				field : 'snStart',
				width : 120
			},{
				title : 'SN截止',
				field : 'snEnd',
				width : 80
			},{
				title : '费率',
				field : 'rate',
				width : 120
			},{
				title : '扫码费率',
				field : 'scanRate',
				width : 80
			},{
				title : '手续费',
				field : 'secondRate',
				width : 120
			},{
				title : '总数量',
				field : 'totalNum',
				width : 100
			},{
				title : '申请时间',
				field : 'maintainDate',
				width : 100
			},{
				title :'状态',
				field :'approveStatus',
				width : 100,
				formatter : function(value,row,index) {
					if(value=='W'){
						return "待审核";
					}else if(value=='Y'){
						return "通过";
					}else if(value=='K'){
						return "退回";
					}
				}
			},{
				title :'审核时间',
				field :'approvedate',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatus=='W'){
						return '<img src="${ctx}/images/start.png" title="通过" style="cursor:pointer;" onclick="sysAdmin_10582_goFun('+row.bctrid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_10582_backFun('+row.bctrid+')"/>';
					}
				}
			}]], 
			toolbar:[{
				id:'btn_run',
				text:'勾选通过',
				iconCls:'icon-start',
				handler:function(){
					sysAdmin_10582_updateAllGo();
				}
			},{
				id:'btn_run',
				text:'勾选退回',
				iconCls:'icon-close',
				handler:function(){
					sysAdmin_10582_updateAllBack();
				}
			}]
		});
	});
	
	function sysAdmin_10582_updateAllGo() {
		var checkedItems = $('#sysAdmin_10582_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bctrid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要通过所有吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeAllTerRateGo.action",
					type:'post',
					data:{"bctrids":bmids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10582_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}
	
	function sysAdmin_10582_updateAllBack() {
		var checkedItems = $('#sysAdmin_10582_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bctrid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认要退回所有吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeAllTerRateBack.action",
					type:'post',
					data:{"bctrids":bmids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10582_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}
	
	//通过
	function sysAdmin_10582_goFun(bctrid){
		$.messager.confirm('确认','您确认要通过本次修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeTerRateGo.action",
					type:'post',
					data:{"bctrid":bctrid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10582_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}
	
	//通过
	function sysAdmin_10582_goFun(bctrid){
		$.messager.confirm('确认','您确认要通过本次修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeTerRateGo.action",
					type:'post',
					data:{"bctrid":bctrid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10582_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}
	
	//退回
	function sysAdmin_10582_backFun(bctrid){
		$.messager.confirm('确认','您确认要退回本次修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeTerRateBack.action",
					type:'post',
					data:{"bctrid":bctrid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10582_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}

	//表单查询
	function sysAdmin_10582_searchFun() {
		var start = $("#10582_cdate").datebox('getValue');
    	var end = $("#10582_cdateEnd").datebox('getValue');
	   	if((!start&&end)||(!end&&start)){
	   		$.messager.alert('提示', "查询时间必须为时间段");
	   		return;
	   	}
	   	start = start.replace(/\-/gi, "/");
	   	end = end.replace(/\-/gi, "/");
	    var startTime = new Date(start).getTime();
	    var endTime = new Date(end).getTime();
	   	if ((endTime-startTime)>(1000*3600*24*30)){
	   	   	$.messager.alert('提示', "查询最长时间为30天");
	   	   	return;
	   	}
	   	if ((endTime-startTime)<0){
	   	   	$.messager.alert('提示', "起始日期需小于截止日期");
	   	   	return;
	   	}
		$('#sysAdmin_10582_datagrid').datagrid('load', serializeObject($('#sysAdmin_10582_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10582_cleanFun() {
		$('#sysAdmin_10582_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10582_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
				    <th>&nbsp;&nbsp;状态&nbsp;</th>
					<td>
						<select name="status" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="W">待审核</option>
		    				<option value="K">退回</option>
		    				<option value="Y">通过</option>
		    			</select>
					</td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10582_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10582_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10582_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>