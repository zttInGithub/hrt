<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String  tempname=request.getParameter("tempname"); 
 %>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
		var tempname=$("#tempname1").val();
		$.ajax({
			<%--url:'${ctx}/sysAdmin/CheckUnitProfitMicro_QUERYprofitmicro.action',--%>
			url:'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMdNextMonthTemplate.action',
			dataType:"json",
			data:{"tempName":tempname,"txnType":1},
			success:function(data) {
				var json=eval(data);
				// console.log(json)
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
				//20200226-ztt
				if(json[0].cashamtabove) {
					$("#cashamtabove").val(json[0].cashamtabove);
				}
				if(json[0].cashamtunder) {
					$("#cashamtunder").val(json[0].cashamtunder);
				}
			}
	});

	$.ajax({
		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMdNextMonthTemplate.action',
		dataType:"json",
		data:{"tempName":tempname,"txnType":2},
		success:function(data) {
			var json=eval(data);
			// console.log(json);
			$("#tempNameNext").val(json[0].tempName);
			if(json[0].startAmount) {
				$("#startAmountNext").val(json[0].startAmount);
			}
			// if(json[0].endAmount) {
			// 	$("#endAmount").val(json[0].endAmount);
			// }
			if(json[0].cashAmt) {
				$("#cashAmtNext").val(json[0].cashAmt);
			}
			if(json[0].cashAmt1) {
				$("#cashAmt1Next").val(json[0].cashAmt1);
			}
			if(json[0].ruleThreshold) {
				$("#ruleThresholdNext").val(parseFloat((json[0].ruleThreshold * 100).toFixed(4)));
			}
			if(json[0].profitPercent) {
				$("#profitPercentNext").val(parseFloat((json[0].profitPercent * 100).toFixed(4)));
			}
			if(json[0].profitPercent1) {
				$("#profitPercent1Next").val(parseFloat((json[0].profitPercent1 * 100).toFixed(4)));
			}
			if(json[0].profitPercent2) {
				$("#profitPercent2Next").val(parseFloat((json[0].profitPercent2 * 100).toFixed(4)));
			}
			if(json[0].profitPercent3) {
				$("#profitPercent3Next").val(parseFloat((json[0].profitPercent3 * 100).toFixed(4)));
			}
			if(json[0].creditBankRate) {
				$("#creditBankRateNext").val(parseFloat((json[0].creditBankRate * 100).toFixed(4)));
			}
			if(json[0].creditBankRate1) {
				$("#creditBankRate1Next").val(parseFloat((json[0].creditBankRate1 * 100).toFixed(4)));
			}
			if(json[0].creditBankRate2) {
				$("#creditBankRate2Next").val(parseFloat((json[0].creditBankRate2 * 100).toFixed(4)));
			}
			if(json[0].cashRate) {
				$("#cashRateNext").val(parseFloat((json[0].cashRate * 100).toFixed(4)));
			}
			if(json[0].scanRate) {
				$("#scanRateNext").val(parseFloat((json[0].scanRate * 100).toFixed(4)));
			}
			if(json[0].cloudQuickRate) {
				$("#cloudQuickRateNext").val(parseFloat((json[0].cloudQuickRate * 100).toFixed(4)));
			}
			if(json[0].startAmount2) {
				$("#startAmount2Next").val(parseFloat((json[0].startAmount2 * 100).toFixed(4)));
			}
			if(json[0].endAmount2) {
				$("#endAmount2Next").val(parseFloat((json[0].endAmount2 * 100).toFixed(4)));
			}
			if(json[0].ruleThreshold2) {
				$("#ruleThreshold2Next").val(json[0].ruleThreshold2);
			}
			//20180822新增//sl
			if(json[0].scanRate1) {
				$("#scanRate1Next").val(parseFloat((json[0].scanRate1 * 100).toFixed(4)));
			}
			if(json[0].scanRate2) {
				$("#scanRate2Next").val(parseFloat((json[0].scanRate2 * 100).toFixed(4)));
			}
			if(json[0].cashAmt2) {
				$("#cashAmt2Next").val(json[0].cashAmt2);
			}
			if(json[0].cashAmt3) {
				$("#cashAmt3Next").val(json[0].cashAmt3);
			}
			//20200226-ztt
			if(json[0].cashamtabove) {
				$("#cashamtaboveNext").val(json[0].cashamtabove);
			}
			if(json[0].cashamtunder) {
				$("#cashamtunderNext").val(json[0].cashamtunder);
			}
		}
	});
 });

	$.extend($.fn.validatebox.defaults.rules, {
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    	}
	});
	
	$.extend($.fn.validatebox.defaults.rules,{
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
	
	$.extend($.fn.validatebox.defaults.rules,{
		payBankIdValidator:{
			validator : function(value) {
	            return /^(?:0|[1-9][0-9]?|100)$/i.test(value); 
	        }, 
	        message : '利润百分比在0到100之间' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		rateValidator:{
			validator : function(value) {
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '费率格式不正确！' 
		}
	});
	
</script>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height:500px; padding-top:2px;">
		<div id="20273_tabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div id="20273_tab_1" title="本月生效">
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
		  <tr>
		   		<th>扫码1000以下费率：</th>
		   		<td><input type="text" name="scanRate" id="scanRate" style="width:100px;" readonly="readonly"/>%<font color="red">&nbsp;*</font></td>
		   		<th>扫码1000以上费率：</th>
		   		<td><input type="text" name="scanRate1" id="scanRate1" style="width:100px;" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
		  </tr>
		  <tr>
		   		<th>扫码1000以下转账费：</th>
		   		<td><input type="text" name="cashamtunder" id="cashamtunder" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18"  readonly="readonly"/><font color="red">&nbsp;*</font></td>
		   		<th>扫码1000以上转账费：</th>
		   		<td><input type="text" name="cashamtabove" id="cashamtabove" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18"  readonly="readonly"/><font color="red">&nbsp;*</font></td>
		  </tr>
		  <tr>
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
	<input type="hidden" id="tempname1" name="Tempname" value="<%=tempname %>">
	</form>
			</div>
			<div id="20273_tab_Next" title="下月生效">
				<form id="Profitmicro_Next" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
					<fieldset>
						<table class="table">
							<tr>
								<th>模版名称：</th>
								<td ><input type="text" name="tempName" id="tempNameNext" readonly="readonly"  style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"  /><font color="red">&nbsp;*</font></td>
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
								<td><input type="text" name="endAmount" id="endAmountNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18" value="4146" readonly="readonly" /><font color="red">*默认</font></td>
								<th>借记卡手续费：</th>
								<td><input type="text" name="startAmount" id="startAmountNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18" /><font color="red"></font></td>
							</tr>
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="ruleThreshold" id="ruleThresholdNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18" />%<font color="red"></font></td>
								<th></th>
								<td></td>
							</tr>
							<tr>
								<th>T0提现费率：</th>
								<td colspan="1"><input type="text" name="cashRate" id="cashRateNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18" />%<font color="red"></font></td>
								<th>转账费：</th>
								<td colspan="1"><input type="text" name="cashAmt" id="cashAmtNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18" /><font color="red"></font></td>
							</tr>
							<tr>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate" id="creditBankRateNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18" />%<font color="red"></font></td>
								<th>利润百分比：</th>
								<td colspan="1"><input type="text" name="profitPercent" id="profitPercentNext" style="width:100px;" class="easyui-validatebox" data-options="validType:'payBankIdValidator'" maxlength="18"/>%<font color="red"></font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>秒到商户</legend>
						<table class="table">
							<tr>
								<th>贷记卡0.72%费率：</th>
								<td ><input type="text" name="creditBankRate1" id="creditBankRate1Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
								<th>利润百分比：</th>
								<td colspan="1"><input type="text" name="profitPercent1" id="profitPercent1Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'payBankIdValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
							</tr>
							<tr>
								<th>贷记卡非0.72%费率：</th>
								<td ><input type="text" name="creditBankRate2" id="creditBankRate2Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
								<th>利润百分比：</th>
								<td colspan="1"><input type="text" name="profitPercent2" id="profitPercent2Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'payBankIdValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
							</tr>
							<tr>
								<th>转账费：</th>
								<td ><input type="text" name="cashAmt1" id="cashAmt1Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
								<th>云闪付费率：</th>
								<td ><input type="text" name="cloudQuickRate" id="cloudQuickRateNext" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>扫码支付商户</legend>
						<table class="table">
							<tr>
								<th>扫码1000以下费率：</th>
								<td><input type="text" name="scanRate" id="scanRateNext" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
								<th>扫码1000以上费率：</th>
								<td><input type="text" name="scanRate1" id="scanRate1Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
							</tr>
							<tr>
						   		<th>扫码1000以下转账费：</th>
						   		<td><input type="text" name="cashamtunder" id="cashamtunderNext" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
						   		<th>扫码1000以上转账费：</th>
						   		<td><input type="text" name="cashamtabove" id="cashamtaboveNext" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
						    </tr>
							<tr>
								<th>银联二维码费率：</th>
								<td><input type="text" name="scanRate2" id="scanRate2Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
								<th>银联二维码转账费：</th>
								<td><input type="text" name="cashAmt2" id="cashAmt2Next" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>快捷支付商户</legend>
						<table class="table">
							<tr>
								<th>VIP用户费率：</th>
								<td><input type="text" name="startAmount2" id="startAmount2Next" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18" />%<font color="red"></font></td>
								<th>完美账单费率：</th>
								<td colspan="1"><input type="text" name="endAmount2" id="endAmount2Next" style="width:100px;" class="easyui-validatebox" data-options="validType:'rateValidator'" maxlength="18" />%<font color="red"></font></td>
							</tr>
							<tr>
								<th>快捷分润比例：</th>
								<td colspan="1"><input type="text" name="profitPercent3" id="profitPercent3Next" style="width:100px;" class="easyui-validatebox" data-options="validType:'payBankIdValidator'" maxlength="18"/>%<font color="red"></font></td>
								<th>转账费：</th>
								<td colspan="1"><input type="text" name="cashAmt3" id="cashAmt3Next" style="width:100px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="18" /><font color="red"></font></td>
							</tr>
						</table>
					</fieldset>
					<input type="hidden" id="tempname1Next" name="Tempname" value="<%=tempname %>">
				</form>
			</div>
		</div>
	</div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
      <table id="aas" style="overflow: hidden;"></table>
    </div> 
</div>
