<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String unno=request.getParameter("unno");
	String startDate=request.getParameter("startDate");
%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:'${ctx}/sysAdmin/checkUnitProfitTradit_queryProfitTraditLogDetail.action',
			dataType:"json",
			data:{"unno":'<%=unno%>',"txnDay":'<%=startDate%>'},
			success:function(data) {
				var json=eval(data);
				$("#tempName").val(json[0].tempName);
				$("#unno").val(json[0].unno);
				$("#feeAmt").val(json[0].feeAmt);
				// $("#dealAmt").val(json[0].dealAmt);
				$("#costRate").val(parseFloat((json[0].costRate*100).toFixed(4)));
				$("#creditBankRate").val(parseFloat((json[0].creditBankRate*100).toFixed(4)));
				$("#profitPercent").val(parseFloat((json[0].profitPercent*100).toFixed(4)));
				$("#cashRate").val(parseFloat((json[0].cashRate*100).toFixed(4)));
				$("#scanRate").val(parseFloat((json[0].scanRate*100).toFixed(4)));
				$("#scanRateUp").val(parseFloat((json[0].scanRateUp*100).toFixed(4)));
				$("#cashAmt").val(json[0].cashAmt);

				$("#feeAmt1").val(json[0].feeAmt1);
				// $("#dealAmt1").val(json[0].dealAmt1);
				$("#costRate1").val(parseFloat((json[0].costRate1*100).toFixed(4)));
				$("#creditBankRate1").val(parseFloat((json[0].creditBankRate1*100).toFixed(4)));

				// $("#feeAmt2").val(json[0].feeAmt2);
				// $("#dealAmt2").val(json[0].dealAmt2);
				$("#costRate2").val(parseFloat((json[0].costRate2*100).toFixed(4)));
				$("#creditBankRate2").val(parseFloat((json[0].creditBankRate2*100).toFixed(4)));

			}
		});
	});
</script>
<div class="easyui-layout"  data-options="fit:true, border:false">

	<div data-options="region:'north',border:false"
			 style="height:500px; padding-top:10px;">
		<form id="ProfitTradit" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
					<fieldset>
						<table class="table">
							<tr>
								<th>机构编号：</th>
								<td style="width:250px;">
									<input type="text" id="unno" name="unno" readonly="readonly" style="width:100px;" class="easyui-validatebox"/>
								</td>
								<th>总利润比例：</th>
								<td><input type="text" name="profitPercent" id="profitPercent" style="width:100px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>标准类成本</legend>
						<table class="table">
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="costRate" id="costRate" style="width:40px;" class="easyui-validatebox" readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额手续费：</th>
								<td style="width:150px;"><input type="text" name="feeAmt" id="feeAmt" style="width:40px;" class="easyui-validatebox" readonly="readonly"/><font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额封顶值：</th>
								<td style="width:150px;"><input type="text" name="dealAmt" id="dealAmt" style="width:40px;" class="easyui-validatebox" readonly="readonly" value="4146"/><font color="red">&nbsp;*(默认4146)</font></td>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate" id="creditBankRate" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>优惠类成本</legend>
						<table class="table">
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="costRate1" id="costRate1" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额手续费：</th>
								<td style="width:150px;"><input type="text" name="feeAmt1" id="feeAmt1" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/><font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额封顶值：</th>
								<td style="width:150px;"><input type="text" name="dealAmt1" id="dealAmt1" style="width:40px;" class="easyui-validatebox" readonly="readonly" value="4146"/><font color="red">&nbsp;*(默认4146)</font></td>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate1" id="creditBankRate1" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>减免类成本</legend>
						<table class="table">
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="costRate2" id="costRate2" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额手续费：</th>
								<td style="width:150px;"><input type="text" name="feeAmt2" id="feeAmt2" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/><font color="red">&nbsp;*(不可填)</font></td>
								<th style="width:310px;" >借卡大额封顶值：</th>
								<td style="width:150px;"><input type="text" name="dealAmt2" id="dealAmt2" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/><font color="red">&nbsp;*(不可填)</font></td>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate2" id="creditBankRate2" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend></legend>
						<table class="table">
							<tr>
								<th>T0提现费率：</th>
								<td><input type="text" name="cashRate" id="cashRate" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >转账费：</th>
								<td style="width:150px;"><input type="text" name="cashAmt" id="cashAmt" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/><font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >传统扫码1000以下费率：</th>
								<td style="width:830px;"><input type="text" name="scanRate" id="scanRate" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >传统扫码1000以上费率：</th>
								<td style="width:830px;"><input type="text" name="scanRateUp" id="scanRateUp" style="width:40px;" class="easyui-validatebox"  readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<input type="hidden" id="tempname" name="tempname">
				</form>
	</div>
	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="aas" style="overflow: hidden;"></table>
	</div>
</div>

