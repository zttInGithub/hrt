<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10470_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseAccount_listPurchaseAccount.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'paid',
			columns : [[{
				title : '付款单ID',
				field : 'paid',
				width : 100,
				hidden : true
			},{
				title : '采购单订单号',
				field : 'accountOrderID',
				width : 120
			},{
				title : '付款类型',
				field : 'accountType',
				width : 120,
				formatter: function(value,row,index) {
					if(value==1){
						return "付款";
					}else if(value==2){
						return "退款";
					}
				}
			},{
				title : '金额',
				field : 'accountAmt',
				width : 100
			},{
				title : '付款方式',
				field : 'payType',
				width : 100
			},{
				title :'状态',
				field :'accountStatus',
				width : 100,
				formatter: function(value,row,index) {
					if(value==1&&row.accountApproveStatus=='K'){
						return "退回";
					}else if(value==1){
						return "待提交";
					}else if(value==2){
						return "待审核 ";
					}else if(value==3){
						return "发票核销 ";
					}else if(value==9){
						return "已审核 ";
					}
				}
			},{
				title :'备注',
				field :'accountRemark',
				width : 100
			},{
				title :'退回原因',
				field :'accountApproveNote',
				width : 100
			},{
				title :'创建时间',
				field :'accountCdate',
				width : 100
			},{
				title :'创建人',
				field :'accountCby',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 60,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.accountStatus == 1){
						return '<img src="${ctx}/images/start.png" title="提交" style="cursor:pointer;" onclick="sysAdmin_10470_submitFun('+row.paid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10470_deleteFun('+row.paid+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[/*,{
				text:'采购单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10470_exportFun();
				}
			}*/]		
		});
	});
	
	//删除订单
	function sysAdmin_10470_deleteFun(paid){
		$.messager.confirm('确认','您确认要删除付款单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseAccount_deletePurchaseAccount.action",
					type:'post',
					data:{"paid":paid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10470_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除付款单出错！');
					}
				});
			}
		});
	}
	
	
	
	//修改采购付款单
	function sysAdmin_10470_updateFun(index){
    	var rows = $('#sysAdmin_10470_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10470_updatePurchaseOrder"/>').dialog({
			title: '修改采购单',
			width: 900,   
		    height: 300,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10471.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10471_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_10471_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10471_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseAccount_updatePurchaseOrder.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10470_datagrid').datagrid('reload');
					    			$('#sysAdmin_10470_updatePurchaseOrder').dialog('destroy');
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
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10470_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//提交审核
	function sysAdmin_10470_submitFun(paid){
		$.messager.confirm('确认','您确认要提交审核该付款单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseAccount_submitPurchaseAccount.action",
					type:'post',
					data:{"paid":paid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10470_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核付款单出错！');
					}
				});
			}
		});
	}

	//导出
	/*function sysAdmin_10470_exportFun() {
		$('#sysAdmin_10470_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDetail_exportPurchaseOrder.action',
			    	});
	}*/
	
	//表单查询
	function sysAdmin_10470_searchFun() {
		var start = $("#10470_accountCdate").datebox('getValue');
    	var end = $("#10470_accountCdateEnd").datebox('getValue');
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
		$('#sysAdmin_10470_datagrid').datagrid('load', serializeObject($('#sysAdmin_10470_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10470_cleanFun() {
		$('#sysAdmin_10470_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:25px;">
		<form id="sysAdmin_10470_searchForm" style="padding-left:5%">
			<table class="tableForm" >
				<tr>
					<th>订单号&nbsp;&nbsp;</th>
					<td><input name="accountOrderID" style="width: 138px;" /></td>
				    
				    <th>&nbsp;&nbsp;状态&nbsp;&nbsp;</th>
					<td>
						<select name="accountStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">待提交</option>
		    				<option value="2">待审核</option>
		    				<option value="3">发票审核</option>
		    				<option value="9">已审核</option>
		    			</select>
					</td>
					
				    <th>&nbsp;&nbsp;创建时间&nbsp;&nbsp;</th>
					<td><input name="accountCdate" id="10470_accountCdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;&nbsp;至&nbsp;&nbsp;<input name="accountCdateEnd" id="10470_accountCdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					<td style="text-align: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10470_searchFun();">查询</a> &nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10470_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10470_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>