<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var poid = <%=request.getParameter("poid")%>;

	$(function() {
		$('#sysAdmin_10413_datagrid').datagrid({
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
					}else if(value == 6){
						return '返厂换新';
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
				title :'状态',
				field :'detailStatus',
				align :'right',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 6){
						return '未入库';
					}else if(value == 7){
						return '入库中';
					}else if(value == 8){
						return '已入库';
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
					return	'<img src="${ctx}/images/frame_remove.png" title="删除订单" style="cursor:pointer;" onclick="sysAdmin_10413_deleteFun('+row.pdid+','+row.poid+')"/>';
				}
			}]]
		});
		
		$(function(){
			$('#brandName_10413').combogrid({
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
		
		$('#machineModel_10413').combogrid({
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
	
	var row = 1;
	function addPd_10413() {
		console.log(row)
		if(row>10){
			$.messager.alert('提示', "一次添加不要大于10条");
			return;
		}
		var html = "<tr id='trm"+row+"'>";
		var td = "<td style='text-align: center;'>";
		
		var snType = $("#snType_10413").combobox('getValue');
		var orderType = $("#orderType_10413").combobox('getValue');
		var brandName = $("#brandName_10413").combobox('getValue');
		var machineModel = $("#machineModel_10413").combobox('getValue');
		var machinePrice = $("#machinePrice_10413").val();
		var machineNum = $("#machineNum_10413").val();
		
		var snType1 = "";
		var orderType1 = "";
		if(snType==1){
			snType1 = "小蓝牙";
		}else if(snType==2){
			snType1 = "大蓝牙";
		}
		if(orderType==1){
			orderType1 = "采购订单";
		}else if(orderType==2){
			orderType1 = "赠品订单";
		}else if(orderType==3){
			orderType1 = "回购订单";
		}else if(orderType==6){
            orderType1 = "返厂换新";
        }else if(orderType == 7){
        	orderType1 = "自定义政策";
        }
		
		var samt1 = machineNum*machinePrice;
		
		if(snType==""||brandName==""||machineModel==""||machinePrice==""||machineNum==""||orderType==""){
			$.messager.alert('提示', "请确认采购单详情已填写");
			return;
		}
		
		html += "<input type='hidden' name='ADDNEWPD' value='"+snType+"#separate#"+orderType+"#separate#"+brandName+"#separate#"+machineModel+"#separate#"+machinePrice+"#separate#"+machineNum+"#separate#"+samt1+"'/>";
		html += td
				+ orderType1
				+ "</td>"
				+ td
				+ brandName
				+ "</td>"
				+ td
				+ snType1
				+ "</td>"
				+ td
				+ machineModel
				+ "</td>"
				+ td
				+ machinePrice
				+ "</td>"
				+ td
				+ machineNum
				+ "</td>"
				+ td
				+ samt1
				+ "</td><td style='width: 65px;text-align: center;'><a href='#' class='easyui-linkbutton 1-btn' onclick='delPd_10413(\"trm"+row+"\")'  >"
				+ "删除"
				+ "</a></td>";
		$("#10413_body").append(html);
		row++;
	}
	
	function delPd_10413(id) {
		var tr=$("#"+id);
		tr.remove();
		row--;
	}
	
	function sysAdmin_10413_deleteFun(pdid,poid){
		$.messager.confirm('确认','您确认要删除采购详情吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseDetail_deletePurchaseDetail.action",
					type:'post',
					data:{"pdid":pdid,"poid":poid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10413_datagrid').datagrid('reload');
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
	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
	$.extend($.fn.validatebox.defaults.rules, {
		barValidator:{
			validator : function(value) { 
	            return /^[^\-|^\s|^\.]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格、横杠和点' 
		}
	});
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
	   	<form id="sysAdmin_10413_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 800px;">
				<legend>采购单信息</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>大类：</th>
		   				<td>
			    			<select name="orderMethod" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
			    				<option value="1" id="orderMethod_1">厂商单</option>
			    				<option value="3" id="">代理商单</option>
	   						</select><font color="red">&nbsp;*</font>
	   					</td>
		   				<th>采购订单号：</th>
	   					<td><input type="text" name="orderID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" readonly="readonly" maxlength="200"><font color="red">&nbsp;*</font></td>
	   				</tr>
		   			<tr>
		   				<th>供应商名称：</th>
		   				<td><input type="text" name="vendorName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" ></td>
		   				<th>联系人：</th>
		   				<td><input type="text" name="contacts" id="qcontactPhone"  style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" ></td>
		   			</tr>
		   			<tr>
		   				<th>联系电话：</th>
		   				<td><input type="text" name="contactPhone" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" ></td>
		   				<th>联系邮箱：</th>
		   				<td><input type="text" name="contactMail" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" ></td>
		   			</tr>
		   			<tr>
		   				<th>业务日期：</th>
		   				<td><input name="busDate" class="easyui-datebox" data-options="editable:false,required:true" style="width: 155px;"/><font color="red">&nbsp;*</font></td>
		   			</tr>
		   			<tr>
	   				<th>备注：</th>
	   				<td colspan="3">
			    		<textarea rows="6" cols="110" style="resize:none;" name="remarks" maxlength="200"></textarea>
			    	</td>
	   			</tr>
		   		</table>
			</fieldset>
			<fieldset style="width: 800px;">
			<legend>采购单详情</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>订单类型：</th>
	   				<td><select name="orderType" id="orderType_10413" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	   					<option value="1">采购订单</option>
	    				<option value="2">赠品订单</option>
	    				<option value="3">回购订单</option>
	    				<option value="6">返厂换新</option>
	    				<option value="7">自定义政策</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				<th>品牌：</th>
	   				<td><select id="brandName_10413" name="brandName"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   				<td style="width: 65px;"><a href="#" class="easyui-linkbutton" onclick="addPd_10413()">添加</a></td>
	   			</tr>
	   			<tr>
	   				<th>机型大类：</th>
	   				<td><select name="snType" id="snType_10413" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	   					<option value="1">小蓝牙</option>
	    				<option value="2">大蓝牙</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				<th>机型：</th>
	   				<td><input type="text" name="machineModel" id="machineModel_10413" style="width: 155px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="200"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>机具单价：</th>
	   				<td><input type="text" name="machinePrice" id="machinePrice_10413" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'2'"  maxlength="30"></td>
	   			
	   				<th>数量：</th>
	   				<td><input type="text" name="machineNum" id="machineNum_10413" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator'" maxlength="20"></td>
	   			</tr>
	   		</table>
	   		<table class="table1">
				<thead>
					<tr>
						<th style="text-align: center;">机型大类</th>
						<th style="text-align: center;">订单类型</th>
						<th style="text-align: center;">品牌</th>
						<th style="text-align: center;">机型小类名称</th>
						<th style="text-align: center;">机具单价</th>
						<th style="text-align: center;">数量</th>
						<th style="text-align: center;">金额</th>
						<th style="text-align: center; width: 65px;">操作</th>
					</tr>
				</thead>
				<tbody id="10413_body">
				</tbody>
			</table>
		</fieldset>
		<input type="hidden" name="poid" />
		</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10413_datagrid"></table>
	</div> 
</div>
		