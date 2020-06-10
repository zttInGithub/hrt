<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var count=0;
$(function(){
	var id=<%=request.getParameter("bmoID")%>;
	$.ajax({
		url:"${ctx}/sysAdmin/machineApprove_find.action",
		type:'post',
		data:{"ids":id},
		dataType:'json',
		success:function(data, textStatus, jqXHR) {
			var json=eval(data);
			var html="";
			$.each(json,function(idx,item){
				var typename="";
				if(item.MACHINETYPE==1){
					typename="有线(拨号)";
				}else if(item.MACHINETYPE==2){
					typename="有线(拨号和网络)";
				}else if(item.MACHINETYPE==3){
					typename="无线";
				}else{
					typename=item.MACHINETYPE;
				}
				html+="<fieldset><legend>机具信息"+(idx+1)+"</legend><table class='table'>"
				+"<tr><th>机具型号：</th><td>"
    			+"<input readonly='readonly' type='text' id='MACHINEMODEL"+idx+"' value='"+item.MACHINEMODEL+"' style='width:250px;'/>"
    			+"</td><th>机具类型：</th><td>"
    			+"<input readonly='readonly' type='text' id='MACHINETYPE"+idx+"' value='"+typename+"' style='width:250px;'/>"
    			+"</td></tr><tr><th>机具库存：</th><td>"
    			+"<input readonly='readonly' type='text' id='MACHINESTOCK"+idx+"' value='"+item.MACHINESTOCK+"' style='width:250px;'/>"
    			+"</td><th>订购数量：</th><td>"
    			+"<input readonly='readonly' type='text' id='ORDERNUM"+idx+"' value='"+item.ORDERNUM+"' style='width:250px;'/>"
    			+"</td></tr><tr><th>机具单价：</th><td>"
    			+"<input readonly='readonly' type='text' id='MACHINEPRICE"+idx+"' value='"+item.MACHINEPRICE+"' style='width:250px;'/>"
    			+"</td><th>金额：</th><td>"
    			+"<input readonly='readonly' type='text' id='ORDERAMOUNT"+idx+"' value='"+item.ORDERAMOUNT+"' style='width:250px;'/>"
    			+"</td></tr><tr><th>执行单价：</th><td>"
    			+"<input type='text' id='ACTIONPRICE"+idx+"'  style='width:250px;' value='"+item.ACTIONPRICE+"' onchange='setActionPrice()' />"
    			+"<input type='hidden' id='BMDID"+idx+"' name='BMDID"+idx+"' value='"+item.BMDID+"'>"
    			+"</td></tr></table></fieldset>";
				count=idx+1;
			});
			$("#machenId").append(html);
			setActionPrice();
		},
		error:function() {
		
		}
	});
});
function setActionPrice(){
	var bmid="";
	var price="";
	var actionID="";
	for(var i=0;i<count;i++){
		bmid=$("#BMDID"+i).val();
		price=$("#ACTIONPRICE"+i).val();
		actionID+=bmid+"#"+price+",";
	}
	$("#actionID").val(actionID);
}
</script>   
<form id="sysAdmin_machineApprove_editForm" method="post">
	<fieldset>
	<legend>基本信息</legend>
	<table class="table">
    	<tr>
    		<th>机构名称：</th>
    		<td><input readonly="readonly" type="text" name="unName" data-options="required:true" class="easyui-validatebox" style="width:250px;"/></td>
    		<th>订单号：</th>
    		<td>
    		<input readonly="readonly" type="text" name="orderID" data-options="required:true" class="easyui-validatebox" style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>收件人：</th>
    		<td>
    			<input readonly="readonly" type="text" name="consigneeName"  style="width:250px;"/>
    		</td>
    		<th>收件人地址：</th>
    		<td>
    			<input readonly="readonly" type="text" name="consigneeAddress"  style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>收货人手机：</th>
    		<td>
    			<input readonly="readonly" type="text" name="consigneePhone"  style="width:250px;"/>
    		</td>
    		<th>固定电话：</th>
    		<td>
    			<input readonly="readonly" type="text" name="consigneeTel" style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>订单总额：</th>
    		<td>
    			<input readonly="readonly" type="text" name="orderAmount"  style="width:250px;"/>
    		</td>
    		<th>邮政编码：</th>
    		<td>
    			<input readonly="readonly" type="text" name="postCode"  style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>受理状态：</th>
    		<td>
		<select name="approveStatus" id="approveStatus">
    		<option value="Z">待受理</option>
    		<option value="Y">已受理</option>
    		<option value="K">不受理</option>
    		</select>
		</td>
    		<th>交易日期：</th>
    		<td>
    			<input readonly="readonly" type="text" name="txnDay"  style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>受理描述：</th>
    		<td>
    		<textarea rows="3" cols="40" name="processContext"></textarea>
    		<th>收货方式：</th>
    		<td>
    		<select name="shipmeThod"  disabled="disabled">
    		<option value="1">自提</option>
    		<option value="2">送货</option>
    		</select>
    	</tr>
    </table>
    </fieldset>
    <div id="machenId">
    </div>
    <input type="hidden" id="actionID" name="actionID">
    <input type="hidden" name="bmoID">
    <input type="hidden" name="unNO">
    <input type="hidden" name="status">
    <input type="hidden" name="shipmeThod">
    <input type="hidden" name="amountConfirmDate">
    <input type="hidden" name="maintainUID">
    <input type="hidden" name="maintainDate">
    <input type="hidden" name="maintainType">
</form>