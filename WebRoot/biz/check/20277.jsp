<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String  tempname=request.getParameter("tempname"); 
 %>
 <!-- 模版修改（传统） -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
		var tempname=$("#tempname1").val();
		$.ajax({
			//url:'${ctx}/sysAdmin/checkUnitProfitTradit_QUERYProfitTradit.action',
			url:'${ctx}/sysAdmin/checkUnitProfitTradit_queryProfitTraditNext.action',
			dataType:"json",
			data:{"tempName":tempname,"matainUserId":1},
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

	$.ajax({
		url:'${ctx}/sysAdmin/checkUnitProfitTradit_queryProfitTraditNext.action',
		dataType:"json",
		data:{"tempName":tempname,"matainUserId":2},
		success:function(data) {
			var json=eval(data);
			$("#tempNameNext").val(json[0].tempName);
			$("#unnoNext").val(json[0].unno);
			$("#feeAmtNext").val(json[0].feeAmt);
			// $("#dealAmt").val(json[0].dealAmt);
			$("#costRateNext").val(parseFloat((json[0].costRate*100).toFixed(4)));
			$("#creditBankRateNext").val(parseFloat((json[0].creditBankRate*100).toFixed(4)));
			$("#profitPercentNext").val(parseFloat((json[0].profitPercent*100).toFixed(4)));
			$("#cashRateNext").val(parseFloat((json[0].cashRate*100).toFixed(4)));
			$("#scanRateNext").val(parseFloat((json[0].scanRate*100).toFixed(4)));
			$("#scanRateNextUp").val(parseFloat((json[0].scanRateUp*100).toFixed(4)));
			$("#cashAmtNext").val(json[0].cashAmt);

			$("#feeAmt1Next").val(json[0].feeAmt1);
			// $("#dealAmt1").val(json[0].dealAmt1);
			$("#costRate1Next").val(parseFloat((json[0].costRate1*100).toFixed(4)));
			$("#creditBankRate1Next").val(parseFloat((json[0].creditBankRate1*100).toFixed(4)));

			// $("#feeAmt2").val(json[0].feeAmt2);
			// $("#dealAmt2").val(json[0].dealAmt2);
			$("#costRate2Next").val(parseFloat((json[0].costRate2*100).toFixed(4)));
			$("#creditBankRate2Next").val(parseFloat((json[0].creditBankRate2*100).toFixed(4)));

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
		style="height:500px; padding-top:10px;">
		<div id="20277_tabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div id="20277_tab_1" title="本月生效">
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
								<td style="width:830px;"><input type="text" name="scanRateUp" id="scanRateUp" style="width:40px;" class="easyui-validatebox" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
								</tr>
							</table>
					</fieldset>
					<input type="hidden" id="tempname1" name="Tempname" value="<%=tempname %>">
				</form>
			</div>
			<div id="20277_tab_Next" title="下月生效">
				<form id="ProfitTradit_Next" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
					<fieldset>
						<table class="table">
							<tr>
								<th>机构编号：</th>
								<td style="width:250px;">
									<input type="text" id="unnoNext" name="unno" readonly="readonly" style="width:100px;" class="easyui-validatebox"/>
								</td>
								<th>总利润比例：</th>
								<td><input type="text" name="profitPercent" id="profitPercentNext" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'payBankIdValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>标准类成本</legend>
						<table class="table">
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="costRate" id="costRateNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额手续费：</th>
								<td style="width:150px;"><input type="text" name="feeAmt" id="feeAmtNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18"/><font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额封顶值：</th>
								<td style="width:150px;"><input type="text" name="dealAmt" id="dealAmtNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" readonly="readonly" value="4146"/><font color="red">&nbsp;*(默认4146)</font></td>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate" id="creditBankRateNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>优惠类成本</legend>
						<table class="table">
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="costRate1" id="costRate1Next" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额手续费：</th>
								<td style="width:150px;"><input type="text" name="feeAmt1" id="feeAmt1Next" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" /><font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额封顶值：</th>
								<td style="width:150px;"><input type="text" name="dealAmt1" id="dealAmt1Next" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18" readonly="readonly" value="4146"/><font color="red">&nbsp;*(默认4146)</font></td>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate1" id="creditBankRate1Next" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend>减免类成本</legend>
						<table class="table">
							<tr>
								<th>借记卡费率：</th>
								<td><input type="text" name="costRate2" id="costRate2Next" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" />%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >借卡大额手续费：</th>
								<td style="width:150px;"><input type="text" name="feeAmt2" id="feeAmt2Next" style="width:40px;" class="easyui-validatebox" readonly="readonly"/><font color="red">&nbsp;*(不可填)</font></td>
								<th style="width:310px;" >借卡大额封顶值：</th>
								<td style="width:150px;"><input type="text" name="dealAmt2" id="dealAmt2Next" style="width:40px;" class="easyui-validatebox" readonly="readonly"/><font color="red">&nbsp;*(不可填)</font></td>
								<th>贷记卡费率：</th>
								<td><input type="text" name="creditBankRate2" id="creditBankRate2Next" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
							</tr>
						</table>
					</fieldset>
					<fieldset>
						<legend></legend>
						<table class="table">
							<tr>
								<th>T0提现费率：</th>
								<td><input type="text" name="cashRate" id="cashRateNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >转账费：</th>
								<td style="width:150px;"><input type="text" name="cashAmt" id="cashAmtNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18"/><font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >传统扫码1000以下费率：</th>
								<td style="width:830px;"><input type="text" name="scanRate" id="scanRateNext" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
								<th style="width:310px;" >传统扫码1000以上费率：</th>
								<td style="width:830px;"><input type="text" name="scanRateUp" id="scanRateNextUp" style="width:40px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="18"/>%<font color="red">&nbsp;*</font></td>
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

