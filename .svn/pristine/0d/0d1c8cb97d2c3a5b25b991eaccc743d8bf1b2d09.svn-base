<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10473_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseInvoice_listPurchaseInvoice.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'piid',
			columns : [[{
				title : '发票核销ID',
				field : 'piid',
				width : 100,
				hidden : true
			},{
				title : '采购单ID',
				field : 'poid',
				width : 120,
				hidden : true
			},{
				title : '采购单订单号',
				field : 'invoiceOrderID',
				width : 120
			},{
				title : '核销类型',
				field : 'invoiceType',
				width : 100,
				formatter: function(value,row,index) {
					if(value==1){
						return "付款核销";
					}else if(value==2){
						return "退款核销";
					}
				}
			},{
				title : '发票号码',
				field : 'invoiceId',
				width : 100
			},{
				title : '申请核销金额',
				field : 'invoiceAmt',
				width : 100
			},{
				title :'状态',
				field :'invoiceStatus',
				width : 100,
				formatter: function(value,row,index) {
					if(value==1&&row.invoiceStatus=='K'){
						return "退回";
					}else if(value==1){
						return "待提交";
					}else if(value==2){
						return "待审核 ";
					}else if(value==10){
						return "已审核 ";
					}
				}
			},{
				title :'发票项目',
				field :'text',
				width : 100
			},{
				title :'数量',
				field :'invoiceNum',
				width : 100
			},{
				title :'未税金额',
				field :'noTax',
				width : 100
			},{
				title :'税金',
				field :'tax',
				width : 100
			},{
				title :'含税金额',
				field :'haveTax',
				width : 100
			},{
				title :'备注',
				field :'invoiceRemark',
				width : 100
			},{
				title :'退回原因',
				field :'invoiceApproveNote',
				width : 100
			},{
				title :'创建时间',
				field :'invoiceCdate',
				width : 100
			},{
				title :'创建人',
				field :'invoiceCby',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 60,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.invoiceStatus == 1){
						return '<img src="${ctx}/images/start.png" title="提交" style="cursor:pointer;" onclick="sysAdmin_10473_submitFun('+row.piid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10473_deleteFun('+row.piid+')"/>';
					}
				}
			}]], 
			toolbar:[/*,{
				text:'采购单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10473_exportFun();
				}
			}*/]		
		});
	});
	
	//删除发票核销
	function sysAdmin_10473_deleteFun(piid){
		$.messager.confirm('确认','您确认要删除发票核销单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseInvoice_deletePurchaseInvoice.action",
					type:'post',
					data:{"piid":piid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10473_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除发票核销单出错！');
					}
				});
			}
		});
	}
	
	//提交审核
	function sysAdmin_10473_submitFun(piid){
		$.messager.confirm('确认','您确认要提交审核发票核销吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseInvoice_submitPurchaseInvoice.action",
					type:'post',
					data:{"piid":piid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10473_datagrid').datagrid('reload');
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
	/*function sysAdmin_10473_exportFun() {
		$('#sysAdmin_10473_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDetail_exportPurchaseOrder.action',
			    	});
	}*/
	
	//表单查询
	function sysAdmin_10473_searchFun() {
		var start = $("#10473_accountCdate").datebox('getValue');
    	var end = $("#10473_accountCdateEnd").datebox('getValue');
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
		$('#sysAdmin_10473_datagrid').datagrid('load', serializeObject($('#sysAdmin_10473_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10473_cleanFun() {
		$('#sysAdmin_10473_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:25px;">
		<form id="sysAdmin_10473_searchForm" style="padding-left:5%">
			<table class="tableForm" >
				<tr>
					<th>订单号&nbsp;&nbsp;</th>
					<td><input name="invoiceOrderID" style="width: 138px;" /></td>
				    
				    <th>&nbsp;&nbsp;状态&nbsp;&nbsp;</th>
					<td>
						<select name="invoiceStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">待提交</option>
		    				<option value="2">待审核</option>
		    				<option value="9">已审核</option>
		    			</select>
					</td>
					
				    <th>&nbsp;&nbsp;创建时间&nbsp;&nbsp;</th>
					<td><input name="invoiceCdate" id="10473_accountCdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;&nbsp;至&nbsp;&nbsp;<input name="invoiceCdateEnd" id="10473_accountCdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					<td style="text-align: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10473_searchFun();">查询</a> &nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10473_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10473_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>