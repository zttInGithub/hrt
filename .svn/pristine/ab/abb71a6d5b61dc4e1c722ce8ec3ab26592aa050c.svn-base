<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 代理单新增 -->
<script type="text/javascript">

	$(function(){
		$('#machineModel_10415').combogrid({
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
		$('#brandName_10415').combogrid({
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
		$('#rebateType_10415').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=3',
			idField:'VALUEINTEGER',
			textField:'VALUEINTEGER',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'返利名称',width:150},
				{field:'VALUEINTEGER',title:'类型',width:150}
			]],
			// 活动变化后,清空费率信息
			onChange:function(n,o){
				$("#rate_10415").val('');
				$("#scanRate_10415").val('');
				$("#secondRate_10415").val('');
				$("#scanRateUp_10415").val('');
				$("#huaBeiRate_10415").val('');
				$('#depositAmt_10415').combobox('clear');
				// TODO @author:lxg-20200508 默认费率获取
				var rebateConfigGrid = $('#rebateType_10415').combogrid('grid');
				var rebateConfig = rebateConfigGrid.datagrid('getSelected');
				$("#rate_10415").val((rebateConfig.RATE*100).toFixed(2));
				$("#scanRate_10415").val((rebateConfig.SCANRATE*100).toFixed(2));
				$("#secondRate_10415").val(rebateConfig.SECONDRATE);
				if(rebateConfig.SCANRATEUP) {
					$("#scanRateUp_10415").val((rebateConfig.SCANRATEUP * 100).toFixed(2));
				}
				if(rebateConfig.HUABEIRATE){
					$("#huaBeiRate_10415").val((rebateConfig.HUABEIRATE*100).toFixed(2));
				}
				if(rebateConfig.VALUEINTEGER){
					depositAmtCombo(rebateConfig.VALUEINTEGER);
				}
			}
		});
	});

	function depositAmtCombo(rebate) {
		$('#depositAmt_10415').combogrid({
			url : '${ctx}/sysAdmin/purchaseOrder_listDepositAmtByPurConfigure?remarks='+rebate,
			idField:'VALUEINTEGER',
			textField:'VALUEINTEGER',
			mode:'remote',
			fitColumns:true,
			data:[],
			columns:[[
				{field:'VALUEINTEGER',title:'押金金额',width:150}
			]]
		});
	}
	
	var row = 1;
	function addPd_10415() {
		if(row>10){
			$.messager.alert('提示', "一次添加不要大于10条");
			return;
		}
		var html = "<tr id='trm"+row+"'>";
		var td = "<td style='text-align: center;'>";
		
		var snType = $("#snType_10415").combobox('getValue');
		var orderType = $("#orderType_10415").combobox('getValue');
		var brandName = $("#brandName_10415").combobox('getValue');
		var machineModel = $("#machineModel_10415").combobox('getValue');
		var machinePrice = $("#machinePrice_10415").val();
		var machineNum = $("#machineNum_10415").val();
		var rate = $("#rate_10415").val();
		var scanRate = $("#scanRate_10415").val();
		var scanRateUp = $("#scanRateUp_10415").val();
		var huaBeiRate = $("#huaBeiRate_10415").val();
		var secondRate = $("#secondRate_10415").val();
		var rebateType = $("#rebateType_10415").combobox('getValue');
		var depositAmt = $("#depositAmt_10415").combobox('getValue');
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
		}else if(orderType==4){
			orderType1 = "退货";
		}else if(orderType==5){
			orderType1 = "换购订单";
		}else if(orderType==6){
			orderType1 = "返厂换新";
		}else if(orderType==7){
			orderType1 = "自定义政策";
		}
		
		var samt1 = machineNum*machinePrice;
		
		if(rebateType==""||secondRate==""||scanRate==""||rate==""||snType==""||brandName==""||machineModel==""||machinePrice==""||machineNum==""||orderType==""){
			$.messager.alert('提示', "请确认采购单详情已填写");
			return;
		}
		//赠品订单金额默认为0
		if(orderType==2){
			machinePrice=0.00;
			samt1=0.00;
		}
		html += "<input type='hidden' name='ADDNEWPD' value='"+snType+"#separate#"+orderType+"#separate#"+brandName+"#separate#"+machineModel+"#separate#"+machinePrice+"#separate#"+machineNum+"#separate#"+samt1+"#separate#"+rate+"#separate#"+scanRate+"#separate#"+secondRate+"#separate#"+rebateType+"#separate#"+scanRateUp+"#separate#"+huaBeiRate+"#separate#"+depositAmt+"'/>";
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
				+ scanRateUp
				+ "</td>"
				+ td
				+ huaBeiRate
				+ "</td>"
				+ td
				+ rate
				+ "</td>"
				+ td
				+ scanRate
				+ "</td>"
				+ td
				+ secondRate
				+ "</td>"
				+ td
				+ rebateType
				+ "</td>"
				+ td
				+ depositAmt
				+ "</td>"
				+ td
				+ machinePrice
				+ "</td>"
				+ td
				+ machineNum
				+ "</td>"
				+ td
				+ samt1
				+ "</td><td style='width: 65px;text-align: center;'><a href='#' class='easyui-linkbutton 1-btn' onclick='delPd_10415(\"trm"+row+"\")'  >"
				+ "删除"
				+ "</a></td>";
		$("#10415_body").append(html);
		row++;
	}
	
	function delPd_10415(id) {
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
   	<form id="sysAdmin_10415_addForm" style="padding-left:20px;" method="post">
	   	<fieldset style="width: 800px;">
			<legend>采购单信息</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>大类：</th>
	   				<td><select name="orderMethod" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	    				<!-- <option value="1" selected="selected">厂商单</option> -->
	    				<option value="2">代理单</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				<th>采购订单号：</th>
	   				<td><input type="text" name="orderID" style="width: 150px;" class="easyui-validatebox" data-options="validType:'barValidator',required:true" maxlength="200"><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>采购机构号：</th>
	   				<td><input type="text" name="unno" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="50"><font color="red">&nbsp;*</font></td>
	   				<th>销售公司</th>
	   				<td><select name="vendorName" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	    				<option value="1" selected="selected">和融通公司</option>
	    				<option value="2">会员宝公司</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>联系人：</th>
	   				<td><input type="text" name="contacts" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"></td>
	   				<th>联系电话：</th>
	   				<td><input type="text" name="contactPhone" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="30"></td>
	   			</tr>
	   			<tr>
	   				<th>销售组：</th>
	   				<td><input type="text" name="purchaserGroup" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="20"><font color="red">&nbsp;*</font></td>
	   				<th>销售人员姓名：</th>
	   				<td><input type="text" name="purchaser" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" maxlength="20"><font color="red">&nbsp;*</font></td>
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
		<!-- 订单明细 -->
		<fieldset style="width: 800px;">
			<legend>采购单详情</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>订单类型：</th>
	   				<td><select name="orderType" id="orderType_10415" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	   					<option value="1">采购订单</option>
	    				<option value="2">赠品订单</option>
	    				<option value="5">换购订单</option>
	    				<option value="6">返厂换新</option>
	    				<option value="7">自定义政策</option>
	   				</select><font color="red">&nbsp;*</font></td>
	   				<th>品牌：</th>
	   				<td><select id="brandName_10415" name="brandName"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   				
	   				
	   				<td style="width: 65px;"><a href="#" class="easyui-linkbutton" onclick="addPd_10415()">添加</a></td>
	   			</tr>
				<tr>
					<th>机型大类：</th>
	   				<td><select name="snType" id="snType_10415" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true" >
	   					<option value="1">小蓝牙</option>
	    				<option value="2">大蓝牙</option>
	   				</select><font color="red">&nbsp;*</font></td>
					<th>机型：</th>
					<td><select id="machineModel_10415" name="machineModel"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
				</tr>
					<tr>
						<th>花呗费率：</th>
						<td><input type="text" name="huaBeiRate" id="huaBeiRate_10415" style="width: 150px;"><font>&nbsp;%</font></td>
						<th>扫码1000以上费率：</th>
						<td><input type="text" name="scanRateUp" id="scanRateUp_10415" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'6',min:0,max:1" maxlength="16" style="width: 150px;" ><font>&nbsp;%</font><font color="red">&nbsp;*</font></td>
					</tr>
				<tr>
					<th>刷卡费率：</th>
	   				<td><input type="text" name="rate" id="rate_10415" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'6',min:0,max:1" maxlength="16" style="width: 150px;"><font>&nbsp;%</font><font color="red">&nbsp;*</font></td>
	   				<th>扫码1000以下费率：</th>
	   				<td><input type="text" name="scanRate" id="scanRate_10415" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'6',min:0,max:1" maxlength="16" style="width: 150px;" ><font>&nbsp;%</font><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>提现手续费：</th>
	   				<td><input type="text" name="secondRate" id="secondRate_10415" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'6'" maxlength="16" style="width: 150px;"><font color="red">&nbsp;*</font></td>
	   				<th>返利类型：</th>
	   				<td><select id="rebateType_10415" name="rebateType"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select><font
						color="red">&nbsp;*</font></td>
	   			</tr>
				<tr>
	   				<th>机具单价：</th>
	   				<td><input type="text" name="machinePrice" id="machinePrice_10415" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator',precision:'2'"  maxlength="30"><font color="red">&nbsp;*</font></td>
	   			
	   				<th>数量：</th>
	   				<td><input type="text" name="machineNum" id="machineNum_10415" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator'" maxlength="20"><font color="red">&nbsp;*</font></td>
	   			</tr>
					<tr>
						<th>押金金额：</th>
						<td>
							<select type="text" name="depositAmt" id="depositAmt_10415" class="easyui-combogrid" data-options="editable:false" style="width: 150px;" />
						</td>
					</tr>
	   		</table>
	   		<table class="table1">
				<thead>
					<tr>
						<th style="text-align: center;">订单类型</th>
						<th style="text-align: center;">品牌</th>
						<th style="text-align: center;">机型大类</th>
						<th style="text-align: center;">机型</th>
						<th style="text-align: center;">扫码1000以上费率%</th>
						<th style="text-align: center;">花呗费率%</th>
						<th style="text-align: center;">刷卡费率%</th>
						<th style="text-align: center;">扫码1000以下费率%</th>
						<th style="text-align: center;">提现手续费</th>
						<th style="text-align: center;">返利类型</th>
						<th style="text-align: center;">返利押金</th>
						<th style="text-align: center;">单价</th>
						<th style="text-align: center;">数量</th>
						<th style="text-align: center;">金额</th>
						<th style="text-align: center; width: 65px;">操作</th>
					</tr>
				</thead>
				<tbody id="10415_body">
				</tbody>
			</table>
		</fieldset>
	</form>
</div>
