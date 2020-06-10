<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 代理单 -->
<script type="text/javascript">
	var poid = <%=request.getParameter("poid")%>;
	var show = '<%=request.getParameter("show")%>';
	var status = '<%=request.getParameter("status")%>';
	if(show=='y'){
		$("#show_10416_approveNote").show();
	}else{
		$("#show_10416_approveNote").hide();
	}
	
	$(function() {
		$('#sysAdmin_10416_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDetail_listPurchaseDetail.action?poid='+poid,
			fit : true,
			fitColumns : true,
			idField : 'pdid',
			columns : [[{
				title : '唯一主键',
				field : 'pdid',
				width : 100,
				hidden : true
			},{
				title :'订单号',
				field :'detailOrderID',
				width : 100
			},{
				title :'品牌',
				field :'brandName',
				width : 100
			},{
				title :'订单类型',
				field :'orderType',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 1){
						return '采购订单';
					}else if(value == 2){
						return '赠品订单';
					}else if(value == 3){
						return '回购订单';
					}else if(value == 4){
						return '退货';
					}else if(value == 5){
						return '换购订单';
					}else if(value==7){
						return "自定义政策";
					}
				}
			},{
				title :'机型小类',
				field :'machineModel',
				width : 100
			},{
				title :'机型大类',
				field :'snType',
				align :'right',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 1){
						return '小蓝牙';
					}else if(value == 2){
						return '大蓝牙';
					}
				}
			},{
				title :'刷卡费率',
				field :'rate',
				width : 100
			},{
				title :'扫码1000以下费率',
				field :'scanRate',
				width : 100
			},{
				title :'扫码1000以上费率',
				field :'scanRateUp',
				width : 100
			},{
				title :'花呗费率',
				field :'huaBeiRate',
				width : 100
			},{
				title :'提现手续费',
				field :'secondRate',
				width : 100
			},{
				title :'返利类型',
				field :'rebateType',
				width : 100
			},{
				title :'返利押金',
				field :'depositAmt',
				width : 100
			},{
				title :'机具单价',
				field :'machinePrice',
				align :'right',
				width : 100
			},{
				title :'数量',
				field :'machineNum',
				align :'right',
				width : 100
			},{
				title :'金额',
				field :'detailAmt',
				align :'right',
				width : 100
			},{
				title :'发货状态',
				field :'detailStatus',
				align :'right',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 6){
						return '未完成';
					}else if(value == 7){
						return '完成中';
					}else if(value == 8){
						return '已完成';
					}
				}
			},{
				title :'创建时间',
				field :'detailCdate',
				align :'right',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(status ==3&&row.orderType!=4){
						return '<img src="${ctx}/images/frame_pencil.png" title="创建退货" style="cursor:pointer;" onclick="sysAdmin_10416_createReturn('+index+')"/>';
					}else if(status ==3&&row.orderType==4&&row.detailApproveStatus=='K'){
						return '<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10416_deleteReturn('+row.pdid+')"/>';
					}
				}
			}]]
		});
	});
	//删除退货退回
	function sysAdmin_10416_deleteReturn(pdid){
		$.messager.confirm('确认','您确认要删除这条退货吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseDetail_deletePurchaseReturn.action",
					type:'post',
					data:{"pdid":pdid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
		    				$('#sysAdmin_10416_datagrid').datagrid('reload');
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
						} else {
		    				$('#sysAdmin_10416_datagrid').datagrid('reload');
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除退回退货出错！');
					}
				});
			}
		});
	}
	//创建退货
	function sysAdmin_10416_createReturn(index){
    	var rows = $('#sysAdmin_10416_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10416_createReturn"/>').dialog({
			title: '创建退货',
			width: 300,   
		    height: 120,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10500.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10500_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var inputs = document.getElementById("sysAdmin_10500_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    		var returnNum=$('#returnNum').val();
		    		if(returnNum>row.machineNum){
		    			alert("退货数量不能超过已出库数量！");
		    			return false;
		    		}
		    		
		    		var validator = $('#sysAdmin_10500_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10500_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseDetail_addPurchaseDetailOrder.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10416_datagrid').datagrid('reload');
					    			$('#sysAdmin_10416_createReturn').dialog('destroy');
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
					$('#sysAdmin_10416_createReturn').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
</script>
<style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:251px;">
	   	<form id="sysAdmin_10416_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>采购单信息</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>采购单订单号：</th>
		   				<td><input type="text" name="orderID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>大类：</th>
		   				<td>
			    			<input type="text" name="orderMethod" id="orderMethod_10416" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
	   					</td>
		   			</tr>
		   			<tr>
		   				<th>供应商名称：</th>
		   				<td><input type="text" name="vendorName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>创建时间：</th>
		   				<td><input type="text" name="cdate" style="width: 150px;" class="easyui-validatebox" data-options="" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>采购机构号：</th>
		   				<td><input type="text" name="unno" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>采购机构名称：</th>
		   				<td><input type="text" name="purchaserName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>联系人：</th>
		   				<td><input type="text" name="contacts" id="qcontactPhone"  style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>联系电话：</th>
		   				<td><input type="text" name="contactPhone" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>销售组：</th>
		   				<td><input type="text" name="purchaserGroup" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   				<th>销售人员：</th>
		   				<td><input type="text" name="purchaser" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>总数量：</th>
		   				<td><input type="text" name="orderNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			
		   				<th>总金额：</th>
		   				<td><input type="text" name="orderAmt" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
		   			</tr>
		   			<tr>
		   				<th>业务日期：</th>
		   				<td><input type="text" name="busDate" style="width: 150px;" class="easyui-validatebox" data-options="" readonly="readonly"/></td>
		   			</tr>
		   			<tr>
		   				<th>备注：</th>
					   	<td colspan="3">
				    		<textarea rows="6" cols="110" style="resize:none;" name="remarks" maxlength="200" readonly="readonly"></textarea>
				    	</td>
		   			</tr>
		   			<tr id="show_10416_approveNote">
		   				<th>退回原因：</th>
					   	<td>
				    		<textarea rows="6" cols="38" style="resize:none;" name="approveNote" maxlength="100" readonly="readonly"></textarea>
				    	</td>
		   			</tr>
		   		</table>
			</fieldset>
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10416_datagrid"></table>
	</div> 
</div>
		