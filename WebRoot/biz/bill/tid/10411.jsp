<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
		$('#machineModel_10411').combogrid({
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
		$('#brandName_10411').combogrid({
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
	
	var row = 1;
	function addPd_10411() {
		console.log(row)
		if(row>10){
			$.messager.alert('提示', "一次添加不要大于10条");
			return;
		}
		var html = "<tr id='trm"+row+"'>";
		var td = "<td style='text-align: center;'>";
		
		var snType = $("#snType_10411").combobox('getValue');
		var orderType = $("#orderType_10411").combobox('getValue');
		var brandName = $("#brandName_10411").combobox('getValue');
		var machineModel = $("#machineModel_10411").combobox('getValue');
		var machinePrice = $("#machinePrice_10411").val();
		var machineNum = $("#machineNum_10411").val();
		
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
        }else if(orderType==7){
        	orderType1 = "自定义政策";
        }
		
		var samt1 = machineNum*machinePrice;
		
		if(snType==""||brandName==""||machineModel==""||machinePrice==""||machineNum==""||orderType==""){
			$.messager.alert('提示', "请确认采购单详情已填写");
			return;
		}
		//赠品订单金额默认为0
		if(orderType==2){
			machinePrice=0.00;
			samt1=0.00;
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
				+ "</td><td style='width: 65px;text-align: center;'><a href='#' class='easyui-linkbutton 1-btn' onclick='delPd_10411(\"trm"+row+"\")'  >"
				+ "删除"
				+ "</a></td>";
		$("#10411_body").append(html);
		row++;
	}
	
	function delPd_10411(id) {
		var tr=$("#"+id);
		tr.remove();
		row--;
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
   	<form id="sysAdmin_10411_addForm" style="padding-left:20px;" method="post">
	   	<fieldset style="width: 800px;">
			<legend>采购单信息</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>大类：</th>
	   				<td><select name="orderMethod" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	    				<option value="1" selected="selected">厂商单</option>
	    				<option value="3">代理商单</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				<th>采购公司(库位)：</th>
	   				<td><select name="purchaserName" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	    				<option value="1" selected="selected">和融通</option>
	    				<option value="2">会员宝</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				</tr>
	   			<tr>
	   				<th>采购订单号：</th>
	   				<td><input type="text" name="orderID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" maxlength="200"><font color="red">&nbsp;*</font></td>
	   				<th>供应商名称：</th>
	   				<td><input type="text" name="vendorName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="200"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>联系人：</th>
	   				<td><input type="text" name="contacts" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"></td>
	   				<th>联系电话：</th>
	   				<td><input type="text" name="contactPhone" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="30"></td>
	   			</tr>
	   			<tr>
	   				<th>联系邮箱：</th>
	   				<td><input type="text" name="contactMail" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"></td>
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
		<!-- 订单明细 -->
		<fieldset style="width: 800px;">
			<legend>采购单详情</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>订单类型：</th>
	   				<td><select name="orderType" id="orderType_10411" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	   					<option value="1">采购订单</option>
	    				<option value="2">赠品订单</option>
	    				<option value="3">回购订单</option>
						<option value="6">返厂换新</option>
						<option value="7">自定义政策</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				<th>品牌：</th>
					<td><select id="brandName_10411" name="brandName"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   				
	   				
	   				
	   				<td style="width: 65px;"><a href="#" class="easyui-linkbutton" onclick="addPd_10411()">添加</a></td>
	   			</tr>
				<tr>
					<th>机型大类：</th>
	   				<td><select name="snType" id="snType_10411" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	   					<option value="1">小蓝牙</option>
	    				<option value="2">大蓝牙</option>
	   				</select><font color="red">&nbsp;*</font></td>
					<th>机型：</th>
					<td><select id="machineModel_10411" name="machineModel"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
				</tr>
				<tr>
	   				<th>机具单价：</th>
	   				<td><input type="text" name="machinePrice" id="machinePrice_10411" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'2'"  maxlength="30"><font color="red">&nbsp;*</font></td>
	   			
	   				<th>数量：</th>
	   				<td><input type="text" name="machineNum" id="machineNum_10411" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator'" maxlength="20"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   		</table>
	   		<table class="table1">
				<thead>
					<tr>
						<th style="text-align: center;">订单类型</th>
						<th style="text-align: center;">品牌</th>
						<th style="text-align: center;">机型大类</th>
						<th style="text-align: center;">机型</th>
						<th style="text-align: center;">单价</th>
						<th style="text-align: center;">数量</th>
						<th style="text-align: center;">金额</th>
						<th style="text-align: center; width: 65px;">操作</th>
					</tr>
				</thead>
				<tbody id="10411_body">
				</tbody>
			</table>
		</fieldset>
	</form>
</div>
