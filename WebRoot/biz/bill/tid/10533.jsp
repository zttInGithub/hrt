<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单(代理) -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10533_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseOrder_listPurchaseOrdersM.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'poid',
			columns : [[{
				title : '采购ID',
				field : 'poid',
				width : 100,
				hidden : true
			},{
				title : '订单号',
				field : 'orderID',
				width : 120
			},{
				title : '大类',
				field : 'orderMethod',
				width : 80,
				formatter : function(value,row,index) {
					if(value==2){
						return "代理单";
					}
				},
				hidden : true
			},{
				title : '供应商名称',
				field : 'vendorName',
				width : 100
			},{
				title : '采购机构名称',
				field : 'purchaserName',
				width : 100
			},{
				title :'销售组',
				field :'purchaserGroup',
				width : 100
			},{
				title :'销售人员',
				field :'purchaser',
				width : 100
			},{
				title :'总数量',
				field :'orderNum',
				align : 'right',
				width : 100
			},{
				title :'总金额',
				field :'orderAmt',
				align : 'right',
				width : 100
			},{
				title :'状态',
				field :'editStatus',
				width : 120,
				formatter : function(value,row,index) {
					if(value == 1){
						return '待提交'
					}else if(value == 2){
						return '待审核';
					}else if(value == 3){
						return '退回';
					}else if(value == 4){
						return '通过';
					}else{
						return '无';
					}
				}
			},{
				title :'取消数量',
				field :'cancelNum',
				align : 'right',
				width : 80
			},{
				title :'取消时间',
				field :'cancelDate',
				width : 100
			},{
				title :'创建时间',
				field :'cdate',
				width : 130
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.editStatus==2){
						return '<img src="${ctx}/images/query_search.png" title="审核" style="cursor:pointer;" onclick="sysAdmin_10533_editFun('+index+')"/>';
					}else if(row.editStatus==1){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10533_detailFun('+index+')"/>';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10533_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="变更为可修改" style="cursor:pointer;" onclick="sysAdmin_10533_submitFun('+row.poid+')"/>';
					}
						
				}
			}]]	
		});
	});
	
	//查看
	function sysAdmin_10533_editFun(index){
		var rows = $('#sysAdmin_10533_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10533_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">采购单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10416.jsp?show=n&poid='+row.poid+'&status='+row.status,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10416_addForm').form('load', row);
		    	if(row.orderMethod==1){
		    		$('#orderMethod_10416').val("厂商单")
		    	}else if(row.orderMethod==2){
		    		$('#orderMethod_10416').val("代理单")
		    	}
		    	
			},
			buttons:[{
				text:'通过',
				iconCls:'icon-cancel',
				handler:function() {
					sysAdmin_10533_goFun(row.poid);
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					sysAdmin_10533_backFun(row.poid);
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10533_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//通过
	function sysAdmin_10533_goFun(poid){
		$.messager.confirm('确认','您确认要通过此修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrderAgainY.action",
					type:'post',
					data:{"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10533_datagrid').datagrid('reload');
							$('#sysAdmin_10533_searchDialog').dialog('destroy');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '变更订单为可修改出错！');
					}
				});
			}
		});
	}
	
	//退回
	function sysAdmin_10533_backFun(poid){
		$.messager.confirm('确认','您确认要退回此修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrderAgainK.action",
					type:'post',
					data:{"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10533_datagrid').datagrid('reload');
							$('#sysAdmin_10533_searchDialog').dialog('destroy');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '变更订单为可修改出错！');
					}
				});
			}
		});
	}
	
	//查看
	function sysAdmin_10533_detailFun(index){
		var rows = $('#sysAdmin_10533_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10533_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">采购单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10416.jsp?show=n&poid='+row.poid+'&status='+row.status,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10416_addForm').form('load', row);
		    	if(row.orderMethod==1){
		    		$('#orderMethod_10416').val("厂商单")
		    	}else if(row.orderMethod==2){
		    		$('#orderMethod_10416').val("代理单")
		    	}
		    	
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10533_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	//变更可修改
	function sysAdmin_10533_submitFun(poid){
		$.messager.confirm('确认','您确认要变更此订单为可修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrderStatusToEdit.action",
					type:'post',
					data:{"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10533_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '变更订单为可修改出错！');
					}
				});
			}
		});
	}

	//表单查询
	function sysAdmin_10533_searchFun() {
		var start = $("#10533_cdate").datebox('getValue');
    	var end = $("#10533_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10533_datagrid').datagrid('load', serializeObject($('#sysAdmin_10533_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10533_cleanFun() {
		$('#sysAdmin_10533_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10533_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>订单号&nbsp;</th>
					<td><input name="orderID" style="width: 138px;" /></td>
				    
					<th>&nbsp;&nbsp;供应商名称&nbsp;</th>
					<td><input name="vendorName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;采购机构名称&nbsp;</th>
					<td><input name="purchaserName" style="width: 138px;" /></td>
				</tr>
				<tr>
					<th>&nbsp;&nbsp;销售人员&nbsp;</th>
					<td><input name="purchaser" style="width: 138px;" /></td>
					
					<th>&nbsp;创建时间&nbsp;</th>
					<td colspan="2"><input name="cdate" id="10533_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="cdateEnd" id="10533_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					
					<td colspan="3" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10533_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10533_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10533_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>