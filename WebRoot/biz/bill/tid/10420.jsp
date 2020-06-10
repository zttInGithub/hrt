<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--采购单审核-->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10420_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseOrder_listPurchaseOrderWJoin.action',
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
					if(value==1){
						return "厂商单";
					}else if(value==3){
						return "代理商单-采购"
					}else{
						return "代理单";
					}
				}
			},{
				title : '供应商名称',
				field : 'vendorName',
				width : 100
			},{
				title : '采购机构名称',
				field : 'purchaserName',
				width : 100
			},{
				title :'采购代理机构号',
				field :'unno',
				width : 100,
				sortable :true
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
				title :'采购人',
				field :'purchaser',
				width : 100
			},{
				title :'状态',
				field :'status',
				width : 120,
				formatter : function(value,row,index) {
					if(row.status == 1){
						return '待提交';
					}else if(row.status == 2){
						return '订单待审';
					}else if(row.status == 3){
						return '已审核';
					}else if(row.status == 4){
						return '发票待审';
					}else if(row.status == 5){
						return '审核通过';
					}else if(row.status == 9){
						return '已结款';
					}else{
						return '已全部核销';
					}
				}
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
					if(row.status == 2){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10420_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="审核订单" style="cursor:pointer;" onclick="sysAdmin_10420_editFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10420_detailFun('+index+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				text:'采购单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10420_exportFun();
				}
			}]		
		});
	});
	
	//查看
	function sysAdmin_10420_detailFun(index){
		var rows = $('#sysAdmin_10420_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10420_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">采购单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10421.jsp?poid='+row.poid,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10421_addForm').form('load', row);
		    	if(row.orderMethod==1){
		    		$('#orderMethod_10421').val("厂商单")
		    	}else if(row.orderMethod==2){
		    		$('#orderMethod_10421').val("代理单")
		    	}else if(row.orderMethod==3){
		    		$('#orderMethod_10421').val("代理商单-采购")
		    	}
		    	
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10420_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10420_editFun(index){
		var rows = $('#sysAdmin_10420_datagrid').datagrid('getRows');
		var row  = rows[index];
		$('<div id="sysAdmin_10420_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审核采购单</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10422.jsp?poid='+row.poid,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10422_queryForm').form('load', row);
		    	if(row.orderMethod==1){
		    		$('#orderMethod_10422').val("厂商单")
		    	}else if(row.orderMethod==2){
		    		$('#orderMethod_10422').val("代理单")
		    	}else if(row.orderMethod==3){
		    		$('#orderMethod_10422').val("代理商单-采购")
		    	}
			},
			buttons:[{
				text:'通过',
				iconCls:'icon-ok',
				handler:function() {
					sysAdmin_10420_queryEditFun(row.poid);
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					sysAdmin_10420k_queryEditFun(row.poid);
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10420_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//导出Excel
	function sysAdmin_10420_exportFun() {
		$('#sysAdmin_10420_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseOrder_exportPurchaseWJoin.action',
			    	});
	}
	
	function sysAdmin_10420_queryEditFun(poid){
		$.messager.confirm('确认','您确认审批通过吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrderY.action',
		        	data: {"poid": poid},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
			        		$('#sysAdmin_10420_queryDialog').dialog('destroy');
				        	$('#sysAdmin_10420_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10420_queryDialog').dialog('destroy');
				        	$('#sysAdmin_10420_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10420_queryDialog').dialog('destroy');
			        	$('#sysAdmin_10420_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10420_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	function sysAdmin_10420k_queryEditFun(poid){
		$('<div id="sysAdmin_10420K_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审核退回采购单</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10423.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#poid_10423').val(poid);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10423_editForm').form('submit', {
						url:'${ctx}/sysAdmin/purchaseOrder_updatePurchaseOrderK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10420_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10420_datagrid').datagrid('reload');
					    			$('#sysAdmin_10420K_editDialog').dialog('destroy');
					    			$('#sysAdmin_10420_queryDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10420K_editDialog').dialog('destroy');
					    			$('#sysAdmin_10420_queryDialog').dialog('destroy');
					    			$('#sysAdmin_10420_datagrid').datagrid('reload');
					    			$('#sysAdmin_10420_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10420K_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
		//表单查询
	function sysAdmin_10420_searchFun() {
		var start = $("#10420_cdate").datebox('getValue');
    	var end = $("#10420_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10420_datagrid').datagrid('load', serializeObject($('#sysAdmin_10420_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10420_cleanFun() {
		$('#sysAdmin_10420_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height: 80px; overflow: hidden; padding-top: 15px;">
		<form id="sysAdmin_10420_searchForm" style="padding-left: 10%" method="post">
			<table class="tableForm">
				<tr>
					<th>&nbsp;&nbsp;&nbsp;订单号</th>
					<td><input name="orderID" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;供应商名称</th>
					<td><input name="vendorName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;采购机构名称</th>
					<td><input name="purchaserName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;类型</th>
					<td><select name="orderMethod" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 70px;">
							<option value="" selected="selected">查询所有</option>
							<option value="1">厂商单</option>
							<option value="2">代理单</option>
							<option value="3">代理商单-采购</option>
					</select></td>
					
					<th>&nbsp;&nbsp;&nbsp;状态</th>
					<td><select name="status" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 70px;">
							<option value="" selected="selected">查询所有</option>
							<option value="2">待审核</option>
							<option value="3">已审核</option>
					</select></td>
					
				</tr>
				<tr>
				
					
					<th>&nbsp;创建时间&nbsp;</th>
					<td><input name="cdate" id="10420_cdate" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/></td>
					<th>&nbsp;至&nbsp;</th>
					<td><input name="cdateEnd" id="10420_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/>
					</td>
					
					<td colspan="4" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_10420_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_10420_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10420_datagrid" style="overflow: hidden;"></table>
	</div>
</div>
