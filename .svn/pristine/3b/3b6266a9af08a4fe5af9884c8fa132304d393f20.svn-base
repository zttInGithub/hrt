<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10450_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDeliver_listPurchaseDeliver.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'pdlid',
			columns : [[{
				title : '发货ID',
				field : 'pdlid',
				width : 100,
				hidden : true
			},{
				title : '采购ID',
				field : 'poid',
				width : 125,
				hidden : true
			},{
				title : '明细ID',
				field : 'pdid',
				width : 125,
				hidden : true
			},{
				title : '订单号',
				field : 'deliverOrderID',
				width : 125
			},{
				title : '快递单号',
				field : 'deliverId',
				width : 125
			},{
				title : '发货数量',
				field : 'deliveNum',
				width : 80
			},{
				title : '发货时间',
				field : 'deliveDate',
				width : 60
			},{
				title : '已分配数量',
				field : 'allocatedNum',
				width : 120
			},{
				title : '状态',
				field : 'deliverStatus',
				width : 60,
				formatter : function(value,row,index) {
					if(value==1){
						return "待提交";
					}else if((value==2)){
						return "待发货";
					}else if((value==3)){
						return "已发货";
					}else if((value==4)){
						return "已分配";
					}
				}
			},{
				title : '采购机构名称',
				field : 'deliverPurName',
				width : 80
			},{
				title : '收货人',
				field : 'deliverContacts',
				width : 80
			},{
				title :'收货人电话',
				field :'deliverContactPhone',
				width : 80
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.deliverStatus == 1){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10450_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10450_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="提交订单" style="cursor:pointer;" onclick="sysAdmin_10450_submitFun('+row.pdlid+')"/>&nbsp;&nbsp';
					}else if(row.deliverStatus == 2||row.deliverStatus == 3){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10450_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10450_detailFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10450_detailFun('+index+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[]		
		});
	});
	
	//查看
	function sysAdmin_10450_detailFun(index){
		var rows = $('#sysAdmin_10450_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10450_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">发货信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10451.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10451_addForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10450_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//修改发货信息
	function sysAdmin_10450_updateFun(index){
    	var rows = $('#sysAdmin_10450_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10450_updatePurchaseOrder"/>').dialog({
			title: '修改发货信息',
			width: 900,   
		    height: 316,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10452.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10452_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_10452_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_10452_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10452_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDeliver_updateDeliverInfo.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
			    				$('#sysAdmin_10450_datagrid').datagrid('reload');
				    			$('#sysAdmin_10450_updatePurchaseOrder').dialog('destroy');
				    			if (result.success) {
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
					$('#sysAdmin_10450_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	//提交审核
	function sysAdmin_10450_submitFun(pdlid){
		$.messager.confirm('确认','您确认要提交发货信息吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseDeliver_submitPurchaseDeliver.action",
					type:'post',
					data:{"pdlid":pdlid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10450_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交发货信息出错！');
					}
				});
			}
		});
	}

	//表单查询
	function sysAdmin_10450_searchFun() {
		$('#sysAdmin_10450_datagrid').datagrid('load', serializeObject($('#sysAdmin_10450_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10450_cleanFun() {
		$('#sysAdmin_10450_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10450_searchForm" style="padding-left:5%">
			<table class="tableForm" >
				<tr>
					<th>订单号&nbsp;</th>
					<td><input name="deliverOrderID" style="width: 138px;" /></td>
				    
					<th>&nbsp;&nbsp;采购机构名称&nbsp;</th>
					<td><input name="deliverPurName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;状态&nbsp;</th>
					<td>
						<select name="deliverStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">所有</option>
		    				<option value="1">待提交</option>
		    				<option value="2">待发货</option>
		    				<option value="3">已发货</option>
		    				<option value="4">已分配</option>
		    			</select>
					</td>
				</tr> 
			
				<tr>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10450_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10450_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10450_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>