<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单(代理) -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10530_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseOrder_listPurchaseOrdersM.action?editStatus=1',
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
				field :'status',
				width : 120,
				formatter : function(value,row,index) {
					if(row.status == 1 && row.approveStatus=='K'){
						return '退回'
					}else if(row.status == 1){
						return '待提交';
					}else if(row.status == 2){
						return '订单待审';
					}else if(row.status == 3){
						return '已审核';
					}else if(row.status == 4){
						return '结款中';
					}else if(row.status == 5){
						return '已结款';
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
						return '<img src="${ctx}/images/start.png" title="提交订单" style="cursor:pointer;" onclick="sysAdmin_10530_submitFun('+row.poid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="修改订单" style="cursor:pointer;" onclick="sysAdmin_10530_updateFun('+index+')"/>';
				}
			}]]	
		});
	});
	
	//提交审核
	function sysAdmin_10530_submitFun(poid){
		$.messager.confirm('确认','您确认要提交审核本次修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseOrder_submitEditPurchaseOrder.action",
					type:'post',
					data:{"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10530_datagrid').datagrid('reload');
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
	
	//修改采购订单
	function sysAdmin_10530_updateFun(index){
    	var rows = $('#sysAdmin_10530_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10530_updatePurchaseOrder"/>').dialog({
			title: '修改采购单',
			width: 900,   
		    height: 550,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10531.jsp?poid='+row.poid,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10531_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_10531_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_10531_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10531_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrder.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10530_datagrid').datagrid('reload');
					    			$('#sysAdmin_10530_updatePurchaseOrder').dialog('destroy');
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
					$('#sysAdmin_10530_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//表单查询
	function sysAdmin_10530_searchFun() {
		var start = $("#10530_cdate").datebox('getValue');
    	var end = $("#10530_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10530_datagrid').datagrid('load', serializeObject($('#sysAdmin_10530_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10530_cleanFun() {
		$('#sysAdmin_10530_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10530_searchForm" style="padding-left:10%">
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
					<td colspan="2"><input name="cdate" id="10530_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="cdateEnd" id="10530_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					
					<td colspan="3" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10530_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10530_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10530_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>