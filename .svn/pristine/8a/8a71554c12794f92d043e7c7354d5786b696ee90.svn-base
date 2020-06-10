<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 发货管理-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10440_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDetail_listPurchaseForDeliver.action',
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
				title : '采购ID',
				field : 'PDID',
				width : 100,
				hidden : true
			},{
				title : '采购ID',
				field : 'POID',
				width : 125,
				hidden : true
			},{
				title : '联系人',
				field : 'CONTACTS',
				width : 125,
				hidden : true
			},{
				title : '联系电话',
				field : 'CONTACTPHONE',
				width : 125,
				hidden : true
			},{
				title : '总数量',
				field : 'ORDERNUM',
				width : 125,
				hidden : true
			},{
				title : '订单号',
				field : 'ORDERID',
				width : 125
			},{
				title : '采购单日期',
				field : 'ORDERDAY',
				width : 80
			},{
				title : '大类',
				field : 'ORDERMETHOD',
				width : 60,
				formatter : function(value,row,index) {
					if(value==1){
						return "厂商单";
					}else{
						return "代理单";
					}
				}
			},{
				title : '采购机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '采购机构名称',
				field : 'PURCHASERNAME',
				width : 120
			},{
				title : '品牌',
				field : 'BRANDNAME',
				width : 60
			},{
				title : '类型',
				field : 'ORDERTYPE',
				width : 80,
				formatter : function(value,row,index) {
					if(value==1){
						return "采购订单";
					}else if((value==2)){
						return "赠品订单";
					}else if((value==3)){
						return "回购订单";
					}
				}
			},{
				title : '返利类型',
				field : 'REBATETYPE',
				width : 60
			},{
				title : '返利押金',
				field : 'DEPOSITAMT',
				width : 60
			},{
				title : '大类',
				field : 'SNTYPE',
				width : 80,
				formatter : function(value,row,index) {
					if(value==1){
						return "小蓝牙";
					}else if((value==2)){
						return "大蓝牙";
					}
				}
			},{
				title :'机型',
				field :'MACHINEMODEL',
				width : 80,
				sortable :true
			},{
				title :'数量',
				field :'MACHINENUM',
				align : 'right',
				width : 70
			},{
				title :'已出库数量',
				field :'IMPORTNUM',
				align : 'right',
				width : 70
			},{
				title :'采购人',
				field :'PURCHASER',
				width : 80
			},{
				title :'状态',
				field :'DETAILSTATUS',
				width : 80,
				formatter : function(value,row,index) {
					if(value == 6){
						return '未出库';
					}else if(value == 7){
						return '出库中';
					}else if(value == 8){
						return '已出库';
					}
				}
			},{
				title :'创建时间',
				field :'DETAILCDATE',
				width : 130
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.DETAILSTATUS == 6||row.DETAILSTATUS ==7){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10440_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="发货" style="cursor:pointer;" onclick="sysAdmin_10440_updateFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10440_detailFun('+index+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				id:'btn_run',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_Purchase_export();
				}
			}]	
		});
	});
	//导出
	function sysAdmin_Purchase_export(){
			$('#sysAdmin_10440_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDetail_emportPurchaseInfo.action'
			    });
	
	}
	
	//查看
	function sysAdmin_10440_detailFun(index){
		var rows = $('#sysAdmin_10440_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10440_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">采购单信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10441.jsp?poid='+row.POID,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10441_addForm').form('load', row);
		    	if(row.ORDERMETHOD==1){
		    		$('#orderMethod_10441').val("厂商单")
		    	}else if(row.ORDERMETHOD==2){
		    		$('#orderMethod_10441').val("代理单")
		    	}
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10440_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//发货
	function sysAdmin_10440_updateFun(index){
    	var rows = $('#sysAdmin_10440_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10440_updatePurchaseOrder"/>').dialog({
			title: '发货',
			width: 900,   
		    height: 316,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10442.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#frmBjjz10442').form('load', row);
		    	$("#deliverPurName_10442").val(row.PURCHASERNAME);
		    	$("#poid_10442").val(row.POID);
		    	$("#pdid_10442").val(row.PDID);
		    	$("#deliverOrderID_10442").val(row.ORDERID);
		    	$("#unno_10442").val(row.UNNO);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("frmBjjz10442").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#frmBjjz10442').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#frmBjjz10442').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDeliver_saveSendOutMachine.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
			    				$('#sysAdmin_10440_datagrid').datagrid('reload');
				    			$('#sysAdmin_10440_updatePurchaseOrder').dialog('destroy');
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
					$('#sysAdmin_10440_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//表单查询
	function sysAdmin_10440_searchFun() {
		$('#sysAdmin_10440_datagrid').datagrid('load', serializeObject($('#sysAdmin_10440_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10440_cleanFun() {
		$('#sysAdmin_10440_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10440_searchForm" style="padding-left:5%">
			<table class="tableForm" >
				<tr>
					<th>订单号&nbsp;</th>
					<td><input name="orderID" style="width: 138px;" /></td>
				    
					<th>&nbsp;&nbsp;采购机构名称&nbsp;</th>
					<td><input name="purchaserName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;状态&nbsp;</th>
					<td>
						<select name="detailStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
							<option value="" selected="selected">所有</option>
		    				<option value="6">未出库</option>
		    				<option value="7">出库中</option>
		    				<option value="8">已出库</option>
		    			</select>
					</td>
				</tr> 
			
				<tr>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10440_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10440_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10440_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>