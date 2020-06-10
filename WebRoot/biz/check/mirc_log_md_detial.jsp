<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String unno=request.getParameter("unno");
	String startDate=request.getParameter("startDate");
%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 

$(function(){
	 var beginDate = '<%=request.getParameter("startDate") %>';
	 var endDate = '2020-01-01'
	 var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
	 var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if((txnDay1-txnDay)/(24*3600*1000)>0 ){
			   $('.is_newRate').remove();
		}else{
			   $('.is_oldRate').remove();
		}
	
		$.ajax({
			url:'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMicroProfitMdLogDetail.action',
			dataType:"json",
			data:{"unno":'<%=unno%>',"txnDay":'<%=startDate%>'},
			success:function(data) {
				var json=eval(data);
				$("#tempName").val(json[0].tempName);
				if(json[0].startAmount) {
					$("#startAmount").val(json[0].startAmount);
				}
				// if(json[0].endAmount) {
				// 	$("#endAmount").val(json[0].endAmount);
				// }
				if(json[0].cashAmt) {
					$("#cashAmt").val(json[0].cashAmt);
				}
				if(json[0].cashAmt1) {
					$("#cashAmt1").val(json[0].cashAmt1);
				}
				if(json[0].ruleThreshold) {
					$("#ruleThreshold").val(parseFloat((json[0].ruleThreshold * 100).toFixed(4)));
				}
				if(json[0].profitPercent) {
					$("#profitPercent").val(parseFloat((json[0].profitPercent * 100).toFixed(4)));
				}
				if(json[0].profitPercent1) {
					$("#profitPercent1").val(parseFloat((json[0].profitPercent1 * 100).toFixed(4)));
				}
				if(json[0].profitPercent2) {
					$("#profitPercent2").val(parseFloat((json[0].profitPercent2 * 100).toFixed(4)));
				}
				if(json[0].profitPercent3) {
					$("#profitPercent3").val(parseFloat((json[0].profitPercent3 * 100).toFixed(4)));
				}
				if(json[0].creditBankRate) {
					$("#creditBankRate").val(parseFloat((json[0].creditBankRate * 100).toFixed(4)));
				}
				if(json[0].creditBankRate1) {
					$("#creditBankRate1").val(parseFloat((json[0].creditBankRate1 * 100).toFixed(4)));
				}
				if(json[0].creditBankRate2) {
					$("#creditBankRate2").val(parseFloat((json[0].creditBankRate2 * 100).toFixed(4)));
				}
				if(json[0].cashRate) {
					$("#cashRate").val(parseFloat((json[0].cashRate * 100).toFixed(4)));
				}
				if(json[0].scanRate) {
					$("#scanRate").val(parseFloat((json[0].scanRate * 100).toFixed(4)));
				}
				if(json[0].cloudQuickRate) {
					$("#cloudQuickRate").val(parseFloat((json[0].cloudQuickRate * 100).toFixed(4)));
				}
				if(json[0].startAmount2) {
					$("#startAmount2").val(parseFloat((json[0].startAmount2 * 100).toFixed(4)));
				}
				if(json[0].endAmount2) {
					$("#endAmount2").val(parseFloat((json[0].endAmount2 * 100).toFixed(4)));
				}
				if(json[0].ruleThreshold2) {
					$("#ruleThreshold2").val(json[0].ruleThreshold2);
				}
				//20180822新增//sl
				if(json[0].scanRate1) {
					$("#scanRate1").val(parseFloat((json[0].scanRate1 * 100).toFixed(4)));
				}
				if(json[0].scanRate2) {
					$("#scanRate2").val(parseFloat((json[0].scanRate2 * 100).toFixed(4)));
				}
				if(json[0].cashAmt2) {
					$("#cashAmt2").val(json[0].cashAmt2);
				}
				if(json[0].cashAmt3) {
					$("#cashAmt3").val(json[0].cashAmt3);
				}
				if(json[0].cashamtabove) {
					$("#cashamtabove").val(json[0].cashamtabove);
				}
				if(json[0].cashamtunder) {
					$("#cashamtunder").val(json[0].cashamtunder);
				}
			}
	});
 });
</script>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height:900px; padding-top:2px;">
			<form id="Profitmicro" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
	<fieldset>
	  <table class="table">
		 <tr>
		   	<th>模版名称：</th>
		   	<td ><input type="text" name="tempName" id="tempName" readonly="readonly"  style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"  /><font color="red">&nbsp;*</font></td>
			<th></th>
			<td></td>
		 </tr>
	  </table>
	</fieldset>
	<fieldset>
		<legend>理财商户：</legend>
		<table class="table">
		   	<tr>
		   		<th>借记卡封顶值：</th>
		   		<td><input type="text" name="endAmount" id="endAmount" style="width:100px;" value="4146" readonly="readonly" /><font color="red">*默认</font></td>
		   		<th>借记卡手续费：</th>
	   			<td><input type="text" name="startAmount" id="startAmount" style="width:100px;" readonly="readonly" /><font color="red"></font></td>
	   		</tr>
	   		<tr>
		   		<th>借记卡费率：</th>
		   		<td><input type="text" name="ruleThreshold" id="ruleThreshold" style="width:100px;" readonly="readonly" />%<font color="red"></font></td>
	   			<th></th>
		   		<td></td>
	   		</tr>
	   		<tr>
		   		<th>T0提现费率：</th>
		   		<td colspan="1"><input type="text" name="cashRate" id="cashRate" style="width:100px;" readonly="readonly"/>%<font color="red"></font></td>
		   		<th>转账费：</th>
		   		<td colspan="1"><input type="text" name="cashAmt" id="cashAmt" style="width:100px;" readonly="readonly" /><font color="red"></font></td>
		   	</tr>
		   	<tr>
		   		<th>贷记卡费率：</th>
		   		<td><input type="text" name="creditBankRate" id="creditBankRate" style="width:100px;" readonly="readonly" />%<font color="red"></font></td>
		   		<th>利润百分比：</th>
		   		<td colspan="1"><input type="text" name="profitPercent" id="profitPercent" style="width:100px;" readonly="readonly"/>%<font color="red"></font></td>
		   	</tr>
	   	</table>
	</fieldset>
	<fieldset>
		<legend>秒到商户</legend>
		<table class="table">
	   		<tr>
		   		<th>贷记卡0.72%费率：</th>
		   		<td ><input type="text" name="creditBankRate1" id="creditBankRate1" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   		<th>利润百分比：</th>
		   		<td colspan="1"><input type="text" name="profitPercent1" id="profitPercent1" style="width:100px;" readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
		   	</tr> 		
	   		<tr>
		   		<th>贷记卡非0.72%费率：</th>
		   		<td ><input type="text" name="creditBankRate2" id="creditBankRate2" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   		<th>利润百分比：</th>
		   		<td colspan="1"><input type="text" name="profitPercent2" id="profitPercent2" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   	</tr> 	
		   	<tr>
		   		<th>转账费：</th>
		   		<td ><input type="text" name="cashAmt1" id="cashAmt1" style="width:100px;" readonly="readonly"/><font color="red">&nbsp;*</font></td>
		   		<th>云闪付费率：</th>
		   		<td ><input type="text" name="cloudQuickRate" id="cloudQuickRate" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   	</tr>	
	   	</table>
	</fieldset>
	<fieldset>
		<legend>扫码支付商户</legend>
		<table class="table">
		  <tr id="oldRate"  class="is_oldRate">
		   		<th>微信费率：</th>
		   		<td><input type="text" name="scanRate" id="scanRate" style="width:100px;" readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
		   		<th>支付宝费率：</th>
		   		<td><input type="text" name="scanRate1" id="scanRate1" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		  </tr>
		  <tr id="oldRate" class="is_oldRate">
		   		<th>银联二维码费率：</th>
		   		<td><input type="text" name="scanRate2" id="scanRate2" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   		<th>转账费：</th>
		   		<td><input type="text" name="cashAmt2" id="cashAmt2" style="width:100px;" readonly="readonly" /><font color="red">&nbsp;*</font></td>
		  </tr>   		
		  <tr id="newRate" class="is_newRate">
		   		<th>扫码1000以下费率：</th>
		   		<td><input type="text" name="scanRate" id="scanRate" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   		<th>扫码1000以上费率：</th>
		   		<td><input type="text" name="scanRate1" id="scanRate1" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		  </tr>
		  <tr id="newRate" class="is_newRate">
		   		<th>扫码1000以下转账费：</th>
		   		<td><input type="text" name="cashamtunder" id="cashamtunder" style="width:100px;" readonly="readonly" /><font color="red">&nbsp;*</font></td>
		   		<th>扫码1000以上转账费：</th>
		   		<td><input type="text" name="cashamtabove" id="cashamtabove" style="width:100px;" readonly="readonly" /><font color="red">&nbsp;*</font></td>
		  </tr>
		   <tr id="newRate" class="is_newRate">
		   		<th>银联二维码费率：</th>
		   		<td><input type="text" name="scanRate2" id="scanRate2" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		   		<th>银联二维码转账费：</th>
		   		<td><input type="text" name="cashAmt2" id="cashAmt2" style="width:100px;" readonly="readonly" /><font color="red">&nbsp;*</font></td>
		  </tr>  		
	   	</table>
	</fieldset>
	<fieldset>
		<legend>快捷支付商户</legend>
		<table class="table">
		  <tr>
		   		<th>VIP用户费率：</th>
		   		<td><input type="text" name="startAmount2" id="startAmount2" style="width:100px;" readonly="readonly" />%<font color="red"></font></td>
		   		<th>完美账单费率：</th>
		   		<td colspan="1"><input type="text" name="endAmount2" id="endAmount2" style="width:100px;" readonly="readonly"/>%<font color="red"></font></td>
		  </tr>  		
		  <tr>
		  		<th>快捷分润比例：</th>
		   		<td colspan="1"><input type="text" name="profitPercent3" id="profitPercent3" style="width:100px;" readonly="readonly"/>%<font color="red"></font></td>
		  		<th>转账费：</th>
		   		<td colspan="1"><input type="text" name="cashAmt3" id="cashAmt3" style="width:100px;" readonly="readonly" /><font color="red"></font></td>
		  </tr>
	   	</table>
	</fieldset>
	</form>
	</div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
      <table id="aas" style="overflow: hidden;"></table>
    </div> 
</div>