<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10409_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDetail_listPurchaseOrderAndDetailForTongJi.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			width : 2000, 
			pageSize : 10,
			pageList : [ 10, 15,100 ],
			remoteSort:true,
			idField : 'PDID',
			columns : [[{
				title : '采购明细ID',
				field : 'PDID',
				width : 100,
				hidden : true
			},{
				title : '订单号',
				field : 'ORDERID',
				width : 120
			},{
				title : '采购日期',
				field : 'ORDERDAY',
				width : 80
			},{
				title : '大类',
				field : 'ORDERMETHOD',
				width : 50,
				formatter : function(value,row,index) {
					if(value==1){
						return "厂商单";
					}else if(value==2){
						return "代理单";
					}else if(value==3){
						return "代理商单-采购";
					}
				}
			},{
				title : '供应商名称',
				field : 'VENDORNAME',
				width : 100
			},{
				title : '采购机构名称',
				field : 'PURCHASERNAME',
				width : 100
			},{
				title :'品牌',
				field :'BRANDNAME',
				width : 45
			},{
				title : '类型',
				field : 'ORDERTYPE',
				width : 45,
				formatter : function(value,row,index) {
				//小类  1 采购订单;2 赠品订单;3 回购订单
					if(value==1){
						return "采购";
					}else if(value==2){
						return "赠品";
					}else if(value==3){
						return "回购";
					}else if(value==4){
						return "退货";
					}else if(value==5){
						return "换购";
					}else if(value==7){
						return "自定义政策";
					}
				}
			},{
				title :'机型',
				field :'MACHINEMODEL',
				width : 45
			},{
				title : '蓝牙类型',
				field : 'SNTYPE',
				width : 65,
				formatter : function(value,row,index) {
				//机型大类1小蓝牙，2大蓝牙
					if(value==1){
						return "小蓝牙";
					}else if(value==2){
						return "大蓝牙";
					}
				}
			},{
				title :'返利类型',
				field :'REBATETYPE',
				width : 65
			},{
				title :'总数量',
				field :'ORDERNUM',
				align : 'right',
				width : 60
			},{
				title :'总金额',
				field :'ORDERAMT',
				align : 'right',
				width : 60
			},{
				title :'单价',
				field :'MACHINEPRICE',
				align : 'right',
				width : 60
			},{
				title :'已付款金额',
				field :'ORDERPAYAMT',
				align : 'right',
				width : 60
			},{
				title :'刷卡费率',
				field :'RATE',
				align :'right',
				width : 100
			},{
				title :'扫码费率',
				field :'SCANRATE',
				align :'right',
				width : 100
			},{
				title :'付款状态',
				field :'STATUS',
				width : 60,
				formatter : function(value,row,index) {
					if(value == 3){
						return '未付款';
					}else if(value == 4){
						return '结款中';
					}else if(value == 5){
						return '已结款';
					}
				}
			},{
				title :'已核销金额',
				field :'WRITEOFFAMT',
				align : 'right',
				width : 60
			},{
				title :'已核销数量',
				field :'WRITEOFFNUM',
				align : 'right',
				width : 60
			},{
				title :'发票状态',
				field :'WRITEOFFSTATUS',
				width : 60,
				formatter : function(value,row,index) {
				//1未核销2核销中3已全部核销
					if(row.WRITEOFFSTATUS == 1){
						return '未核销';
					}else if (row.WRITEOFFSTATUS == 2){
						return '核销中';
					}else if (row.WRITEOFFSTATUS == 3){
						return '已全部核销';
					}
				}
			},{
				title :'明细金额',
				field :'DETAILAMT',
				align : 'right',
				width : 60
			},{
				title :'明细数量',
				field :'MACHINENUM',
				align : 'right',
				width : 60
			},{
				title :'已入出数量',
				field :'OUTNUM',
				align : 'right',
				width : 80
			},{
				title :'库房状态',
				field :'DETAILSTATUS',
				width : 70,
				formatter : function(value,row,index) {
					/*if(row.ORDERMETHOD==1&&row.DETAILSTATUS == 8){
						return '已入库';
					}else if(row.ORDERMETHOD==1){
						return '入库中';
					}else if (row.ORDERMETHOD==2&&row.DETAILSTATUS == 8&&row.ORDERTYPE==4){
					//小类  1 采购订单;2 赠品订单;3 回购订单
						return '已入库';
					}else if (row.ORDERMETHOD==2&&row.ORDERTYPE==4){
						return '入库中';
					}else if (row.ORDERMETHOD==2&&row.DETAILSTATUS == 8){
						return '已出库';
					}else if (row.ORDERMETHOD==2){
						return '出库中';
					}*/
					if(row.ORDERMETHOD==1){
						if(row.DETAILSTATUS==8){
							return '已入库';
						}else if(row.DETAILSTATUS==7){
							return '入库中';
						}else if(row.DETAILSTATUS==6){
							return '未入库';
						}
					}else{
						if(row.DETAILSTATUS==8){
							return '已出库';
						}else if(row.DETAILSTATUS==7){
							return '出库中';
						}else if(row.DETAILSTATUS==6){
							return '未出库';
						}
					}
				}
			},{
				title :'创建时间',
				field :'DETAILCDATE',
				width : 70
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				hidden : true ,
				formatter : function(value,row,index) {
					if(row.status == 1 && row.approveStatus=='K'){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10409_detailFun2('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="修改订单" style="cursor:pointer;" onclick="sysAdmin_10409_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="提交订单" style="cursor:pointer;" onclick="sysAdmin_10409_submitFun('+row.poid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除订单" style="cursor:pointer;" onclick="sysAdmin_10409_deleteFun('+row.poid+')"/>';
					}else if(row.status == 1){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10409_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="修改订单" style="cursor:pointer;" onclick="sysAdmin_10409_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="提交订单" style="cursor:pointer;" onclick="sysAdmin_10409_submitFun('+row.poid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除订单" style="cursor:pointer;" onclick="sysAdmin_10409_deleteFun('+row.poid+')"/>';
					}else if(row.status == 2){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10409_detailFun('+index+')"/>&nbsp;&nbsp';
					}else if((row.status == 3||row.status == 4) && row.orderpayAmt<row.orderAmt){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10409_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_add.png" title="付款" style="cursor:pointer;" onclick="sysAdmin_10409_accountFun('+index+')"/>';
					}
				}
			}]], 
			toolbar:[{
				text:'订单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10409_exportFun();
				}
			}]		
		});
	});
	
	//导出Excel
	function sysAdmin_10409_exportFun() {
		$('#sysAdmin_10409_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDetail_exportPurchaseOrderAndDetailForTongJi.action',
			    	});
	}
	
	//新增
	function sysAdmin_10409_addPurchaseOrder() {
		$('<div id="sysAdmin_10409_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增采购</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10411.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_10411_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_10411_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10411_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseOrder_addPurchaseOrder.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10409_datagrid').datagrid('reload');
					    			$('#sysAdmin_10409_addDialog').dialog('destroy');
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
					$('#sysAdmin_10409_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//查看
	function sysAdmin_10409_detailFun(index){
		var rows = $('#sysAdmin_10409_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10409_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">采购单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10412.jsp?show=n&poid='+row.poid,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10412_addForm').form('load', row);
		    	if(row.orderMethod==1){
		    		$('#orderMethod_10412').val("厂商单")
		    	}else if(row.orderMethod==2){
		    		$('#orderMethod_10412').val("代理单")
		    	}
		    	
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10409_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//查看(退回的)
	function sysAdmin_10409_detailFun2(index){
		var rows = $('#sysAdmin_10409_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10409_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">采购单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10412.jsp?show=y&poid='+row.poid,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10412_addForm').form('load', row);
		    	if(row.orderMethod==1){
		    		$('#orderMethod_10412').val("厂商单")
		    	}else if(row.orderMethod==2){
		    		$('#orderMethod_10412').val("代理单")
		    	}
		    	
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10409_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//删除订单
	function sysAdmin_10409_deleteFun(poid){
		$.messager.confirm('确认','您确认要删除采购单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseOrder_deletePurchaseOrder.action",
					type:'post',
					data:{"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10409_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除采购单出错！');
					}
				});
			}
		});
	}
	
	//修改采购订单
	function sysAdmin_10409_updateFun(index){
    	var rows = $('#sysAdmin_10409_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10409_updatePurchaseOrder"/>').dialog({
			title: '修改采购单',
			width: 900,   
		    height: 550,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10413.jsp?poid='+row.poid+'&orderM=c',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10413_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_10413_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_10413_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10413_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrder.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10409_datagrid').datagrid('reload');
					    			$('#sysAdmin_10409_updatePurchaseOrder').dialog('destroy');
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
					$('#sysAdmin_10409_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//提交审核
	function sysAdmin_10409_submitFun(poid){
		$.messager.confirm('确认','您确认要提交审核该采购单吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseOrder_submitPurchaseOrder.action",
					type:'post',
					data:{"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10409_datagrid').datagrid('reload');
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

	//导出
	/*function sysAdmin_10409_exportFun() {
		$('#sysAdmin_10409_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDetail_exportPurchaseOrder.action',
			    	});
	}*/
	
	//付款
	function sysAdmin_10409_accountFun(index){
		var rows = $('#sysAdmin_10409_datagrid').datagrid('getRows');
		var row = rows[index];
		var waitAmt = row.orderAmt-row.orderpayAmt
		$('<div id="sysAdmin_10409_accountPurchaseOrder"/>').dialog({
			title: '采购单付款',
			width: 900,   
		    height: 300,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10418.jsp?waitAmt='+waitAmt,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10418_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10418_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10418_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseAccount_savePurchaseAccount.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10409_datagrid').datagrid('reload');
					    			$('#sysAdmin_10409_accountPurchaseOrder').dialog('destroy');
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
					$('#sysAdmin_10409_accountPurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//表单查询
	function sysAdmin_10409_searchFun() {
		var start = $("#10409_cdate").datebox('getValue');
    	var end = $("#10409_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10409_datagrid').datagrid('load', serializeObject($('#sysAdmin_10409_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10409_cleanFun() {
		$('#sysAdmin_10409_searchForm input').val('');
	}
	$(function(){
		$('#machineModel_10409').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listMachineModel.action',
			idField:'MACHINEMODEL',
			textField:'MACHINEMODEL',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'MACHINEMODEL',title:'机型名称',width:150},
			]]
		});
	});
	
	$(function(){
		$('#brandName_10409').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=2',
			idField:'VALUESTRING',
			textField:'VALUESTRING',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'VALUESTRING',title:'品牌名称',width:150},
			]]
		});
	});
	$(function(){
		$('#rebateType_10409').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=3',
			idField:'VALUEINTEGER',
			textField:'VALUEINTEGER',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'返利名称',width:150},
				{field:'VALUEINTEGER',title:'类型',width:150}
			]]
		});
	});
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10409_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>订单号&nbsp;</th>
					<td><input name="orderID" style="width: 138px;" /></td>
				    
				    <th>&nbsp;品牌名称&nbsp;</th>
					<td>
						<select id="brandName_10409" name="brandName"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 105px;"></select>
					</td>
					<th>&nbsp;订单类型&nbsp;</th>
					<td>
						<select name="orderType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">采购</option>
		    				<option value="2">赠品</option>
		    				<option value="3">回购</option>
		    				<option value="4">退货</option>
		    				<option value="5">换购</option>
		    				<option value="7">自定义政策</option>
		    			</select>
					</td>
					<th>&nbsp;订单来源&nbsp;</th>
					<td>
						<select name="orderMethod" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">厂商订单</option>
		    				<option value="2">代理订单</option>
		    				<option value="3">代理商订单-采购</option>
		    			</select>
					</td>
				    <th>&nbsp;创建时间&nbsp;</th>
					<td><input name="detailCdate" id="10409_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="detailCdateEnd" id="10409_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
				</tr> 
				<tr>
					<th>&nbsp;采购机构名称&nbsp;</th>
					<td><input name="purchaserName" style="width: 138px;" /></td>
					<th>&nbsp;机型类型&nbsp;</th>
					<td>
						<select id="machineModel_10409" name="machineModel"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 105px;"></select>
					</td>
					<th>&nbsp;蓝牙类型&nbsp;</th>
					<td>
						<select name="snType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="2">大蓝牙</option>
		    				<option value="1">小蓝牙</option>
		    			</select>
					</td>
					<th>&nbsp;库房状态&nbsp;</th>
					<td>
						<select name="detailStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="6">待完成</option>
		    				<option value="7">进行中</option>
		    				<option value="8">已完成</option>
		    			</select>
					</td>
				</tr>
				<tr>
					<th>&nbsp;供应商名称&nbsp;</th>
					<td><input name="vendorName" style="width: 138px;" /></td>
					<th>&nbsp;返利类型&nbsp;</th>
					<td>
						<select id="rebateType_10409" name="rebateType"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 105px;"></select>
					</td>
					<th>&nbsp;付款状态&nbsp;</th>
					<td>
						<select name="status" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="3">待付款</option>
		    				<option value="4">结款中</option>
		    				<option value="5">已结款</option>
		    			</select>
					</td>
					<th>&nbsp;发票状态&nbsp;</th>
					<td>
						<select name="writeoffStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
							<option value="" selected="selected">查询所有</option>
		    				<option value="1">未核销</option>
		    				<option value="2">核销中</option>
		    				<option value="3">已核销</option>	    			
		    			</select>
					</td>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10409_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10409_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10409_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>