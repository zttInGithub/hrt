<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10434_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDeliver_queryPurchaseDeliverAndDetail.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'PDID',
			columns : [[{
				title : '发货ID',
				field : 'PDLID',
				width : 100,
				hidden : true
			},{
				title : '采购ID',
				field : 'POID',
				width : 125,
				hidden : true
			},{
				title : '机构号',
				field : 'DELIVERUNNO',
				width : 50
			},{
				title : '机构名称',
				field : 'DELIVERPURNAME',
				width : 100
			},{
				title : '订单类型',
				field : 'ORDERMETHOD',
				width : 100,
				formatter : function(value,row,index) {
					if(value==1){
						return "厂商单";
					}else if((value==2)){
						return "代理单 	";
					}
				}
			},{
				title : '快递单号',
				field : 'DELIVERID',
				width : 85
			},{
				title : '快递公司',
				field : 'DELIVERNAME',
				width : 65
			},{
				title : '联系人',
				field : 'DELIVERCONTACTS',
				width : 65
			},{
				title : '联系电话',
				field : 'DELIVERCONTACTPHONE',
				width : 125,
				hidden:true
			},{
				title : '联系邮箱',
				field : 'DELIVERCONTACTMAIL',
				width : 125,
				hidden:true
			},{
				title : '联系地址',
				field : 'DELIVERRECEIVEADDR',
				width : 125,
				hidden:true
			},{
				title : '邮编',
				field : 'POSTCODE',
				width : 125,
				hidden:true
			},{
				title : '采购单',
				field : 'ORDERID',
				width : 120
			},{
				title : '采购单日期',
				field : 'ORDERDAY',
				width : 70
			},{
				title : '订单类型',
				field : 'ORDERTYPE',
				width : 80,
				formatter : function(value,row,index) {
					if(value==1){
						return "采购订单";
					}else if((value==2)){
						return "赠品订单";
					}else if((value==3)){
						return "回购订单";
					}else if(value==4){
						return "退货";
					}else if(value == 5){
						return '换购订单';
					}else if(value == 7){
						return "自定义政策";
					}
				},
				hidden:true
			},{
				title :'机型',
				field :'MACHINEMODEL',
				width : 45,
				sortable :true
			},{
				title :'返利类型',
				field :'REBATETYPE',
				width : 60,
				sortable :true
			},{
				title :'返利押金',
				field :'DEPOSITAMT',
				width : 60,
				sortable :true
			},{
				title :'刷卡费率',
				field :'RATE',
				width : 60,
				sortable :true
			},{
				title :'扫码1000以下费率',
				field :'SCANRATE',
				width : 60,
				sortable :true
			},{
				title :'扫码1000以上费率',
				field :'SCANRATEUP',
				width : 60,
				sortable :true
			},{
				title :'花呗费率',
				field :'HUABEIRATE',
				width : 60,
				sortable :true
			},{
				title :'提现费',
				field :'SECONDRATE',
				width : 50,
				sortable :true
			},{
				title :'数量',
				field :'DELIVENUM',
				width : 55
			},{
				title :'已出数量',
				field :'ALLOCATEDNUM',
				width : 60
			},{
				title :'采购人',
				field :'DELIVERCBY',
				width : 60,
				hidden:true
			},{
				title :'状态',
				field :'DELIVERSTATUS',
				width : 55,
				formatter : function(value,row,index) {
				//1待提交;2待发货;3已发货待分配;4已分配;5已退货
					if(value == 1){
						return '待提交';
					}else if(value == 2){
						return '待发货';
					}else if(value == 3){
						return '已发货';
					}else if(value == 4){
						return '已分配';
					}else if(value == 5&&-row.DELIVENUM>row.ALLOCATEDNUM){
						return '退货中';
					}else if(value == 5&&-row.DELIVENUM==row.ALLOCATEDNUM){
						return '已退货';
					}
				}
			},{
				title :'发货创建时间',
				field :'DELIVERCDATE',
				width : 90
			},{
				title :'操作',
				field :'operation',
				width : 60,
				align : 'center',
				formatter : function(value,row,index) {
					//1待提交;2待发货;3已发货待分配;4已分配
					if(row.ORDERTYPE==4&&-row.DELIVENUM>row.ALLOCATEDNUM){
						return '<img src="${ctx}/images/query_search.png" title="补充快递" style="cursor:pointer;" onclick="sysAdmin_10434_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="退货" style="cursor:pointer;" onclick="sysAdmin_10434_returnFun('+index+')"/>&nbsp;&nbsp';
					}else if(row.DELIVERSTATUS == 2||row.DELIVERSTATUS ==3){
						return '<img src="${ctx}/images/query_search.png" title="补充快递" style="cursor:pointer;" onclick="sysAdmin_10434_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="出库" style="cursor:pointer;" onclick="sysAdmin_10434_updateFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="补充快递" style="cursor:pointer;" onclick="sysAdmin_10434_detailFun('+index+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				text:'出库信息导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10434_exportFun();
				}
			}]		
		});
	});
	
	//导出Excel
	function sysAdmin_10434_exportFun() {
		$('#sysAdmin_10434_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDeliver_exportPurchaseDeliver.action',
			    	});
	}
	
	//新增
	function sysAdmin_10434_addPurchaseOrder() {
		$('<div id="sysAdmin_10434_addDialog"/>').dialog({
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
				    				$('#sysAdmin_10434_datagrid').datagrid('reload');
					    			$('#sysAdmin_10434_addDialog').dialog('destroy');
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
					$('#sysAdmin_10434_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//查看
	function sysAdmin_10434_detailFun(index){
		var rows = $('#sysAdmin_10434_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10434_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">发货单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10435.jsp?poid='+row.POID,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10435_addForm').form('load', row);
		    	if(row.ORDERMETHOD==1){
		    		$('#orderMethod_10431').val("厂商单")
		    	}else if(row.ORDERMETHOD==2){
		    		$('#orderMethod_10431').val("代理单")
		    	}
			},
			buttons:[{
		    	text:'确认快递信息',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		/**
		    		var inputs = document.getElementById("sysAdmin_10411_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}**/
		    		var validator = $('#sysAdmin_10435_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_10435_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDeliver_addDeliverInfo.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10434_datagrid').datagrid('reload');
					    			$('#sysAdmin_10434_searchDialog').dialog('destroy');
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
					$('#sysAdmin_10434_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//删除订单
	function sysAdmin_10434_deleteFun(poid){
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
							$('#sysAdmin_10434_datagrid').datagrid('reload');
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
	function sysAdmin_10434_updateFun(index){
    	var rows = $('#sysAdmin_10434_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10434_updatePurchaseOrder"/>').dialog({
			title: '出库',
			width: 670,   
		    height: 185,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10433.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#frmBj10433').form('load', row);
			},
			buttons:[],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//退货
	function sysAdmin_10434_returnFun(index){
    	var rows = $('#sysAdmin_10434_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10434_updatePurchaseOrder"/>').dialog({
			title: '退货',
			width: 670,   
		    height: 185,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10437.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#frmBj10437').form('load', row);
			},
			buttons:[],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//提交审核
	function sysAdmin_10434_submitFun(poid){
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
							$('#sysAdmin_10434_datagrid').datagrid('reload');
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
	function sysAdmin_10434_searchFun() {
		var start = $("#10434_cdate").datebox('getValue');
    	var end = $("#10434_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10434_datagrid').datagrid('load', serializeObject($('#sysAdmin_10434_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10434_cleanFun() {
		$('#sysAdmin_10434_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10434_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;&nbsp;机构号&nbsp;</th>
					<td><input name="deliverUnno" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;快递单号&nbsp;</th>
					<td><input name="deliverId" style="width: 138px;" /></td>
					
					<th>采购单号&nbsp;</th>
					<td><input name="orderID" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;状态&nbsp;</th>
					<td>
						<select name="deliverStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">全部</option>
		    				<option value="1">待提交</option>
		    				<option value="2">待发货</option>
		    				<option value="3">已发货</option>
		    				<option value="4">已分配</option>
		    				<option value="5">退货</option>
								<option value="7">自定义政策</option>
		    			</select>
					</td>
				</tr>
				<tr>
					<th>&nbsp;&nbsp;机型&nbsp;</th>
					<td><input name="machineModel" style="width: 138px;" /></td>
					
					<th>&nbsp;创建时间&nbsp;</th>
					<td><input name="deliverCdate" id="10434_cdate" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/></td>
					<th>&nbsp;至&nbsp;</th>
					<td><input name="deliverCdateEnd" id="10434_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/>
					</td>
				    
					<td colspan="3" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10434_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10434_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10434_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>