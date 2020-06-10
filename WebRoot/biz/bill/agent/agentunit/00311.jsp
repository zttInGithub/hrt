<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>
<%
    Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession);
%>
    <!-- 代理商新增页面 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var allRebateType = new Array();
	$(function(){
		var tab2 =
			'<div id="00311_tab_2" title="代理商非活动成本信息录入">'
			+'<fieldset>'
				+'<legend>非活动成本信息</legend>'
				+'<table class="table">'
					+'<thead>'
						+'<tr>'
							+'<th style="text-align: center;" rowspan="2">是否活动</th>'
							+'<th style="text-align: center;" rowspan="2">机器类型</th>'
							+'<th style="text-align: center;" rowspan="2">交易类型</th>'
							+'<th style="text-align: center;" rowspan="2">产品类型</th>'
							+'<th style="text-align: center;" rowspan="2">产品细分</th>'
							+'<th style="text-align: center;" colspan="3">交易成本</th>'
							+'<th style="text-align: center;" colspan="2">提现成本</th>'
						+'</tr>'
						+'<tr>'
							+'<th style="text-align: center;">借记卡成本（%）</th>'
							+'<th style="text-align: center;">借记卡封项手续费</th>'
							+'<th style="text-align: center;">贷记卡成本（%）</th>'
							+'<th style="text-align: center;">提现成本</th>'
							+'<th style="text-align: center;">提现手续费成本（%）</th>'
						+'</tr>'
					+'</thead>'
					+'<tbody id="00311_tab2_body">'
							+'<tr id="00311T1">'
								+'<th style="text-align: center;" rowspan="19">非活动</th>'
								+'<th style="text-align: center;" rowspan="13">手刷</th>'
								+'<th style="text-align: center;" rowspan="3">刷卡</th>'
								+'<th style="text-align: center;" rowspan="2">秒到</th>'
								+'<th style="text-align: center;">0.6秒到</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="1"/>'
								+'<input type="hidden" name="txnDetail" value="1"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T2">'
								+'<th style="text-align: center;">0.72秒到</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="1"/>'
								+'<input type="hidden" name="txnDetail" value="2"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T3">'
								+'<th style="text-align: center;">理财</th>'
								+'<th style="text-align: center;">理财</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="2"/>'
								+'<input type="hidden" name="txnDetail" value="3"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width: 70px" name="cashCost" type="text" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width:70px" name="cashRate" type="text" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							+'<tr id="00311T4">'
								+'<th style="text-align: center;">代还</th>'
								+'<th style="text-align: center;">代还</th>'
								+'<th style="text-align: center;">信用卡代还</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="3"/>'
								+'<input type="hidden" name="txnDetail" value="4"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T5">'
								+'<th style="text-align: center;">云闪付</th>'
								+'<th style="text-align: center;">云闪付</th>'
								+'<th style="text-align: center;">云闪付</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="4"/>'
								+'<input type="hidden" name="txnDetail" value="5"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T6">'
								+'<th style="text-align: center;" rowspan="2">快捷</th>'
								+'<th style="text-align: center;" rowspan="2">快捷</th>'
								+'<th style="text-align: center;">快捷支付成本VIP</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="5"/>'
								+'<input type="hidden" name="txnDetail" value="6"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T7">'
								+'<th style="text-align: center;">快捷支付成本完美账单</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="5"/>'
								+'<input type="hidden" name="txnDetail" value="7"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T8">'
								+'<th style="text-align: center;" rowspan="6">扫码</th>'
								+'<th style="text-align: center;" rowspan="3">T0</th>'
								+'<th style="text-align: center;">扫码1000以下</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="8"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T9">'
								+'<th style="text-align: center;">扫码1000以上</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="9"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T10">'
								+'<th style="text-align: center;">银联二维码</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="10"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T11">'
								+'<th style="text-align: center;" rowspan="3">T1</th>'
								+'<th style="text-align: center;">扫码1000以下</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="7"/>'
								+'<input type="hidden" name="txnDetail" value="8"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T12">'
								+'<th style="text-align: center;">扫码1000以上</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="7"/>'
								+'<input type="hidden" name="txnDetail" value="9"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T13">'
								+'<th style="text-align: center;">银联二维码</th>'
								+'<input type="hidden" name="machineType" value="1"/>'
								+'<input type="hidden" name="txnType" value="7"/>'
								+'<input type="hidden" name="txnDetail" value="10"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<th style="text-align: center;">—</th>'
							+'</tr>'
							+'<tr id="00311T14">'
								+'<th style="text-align: center;" rowspan="6">传统</th>'
								+'<th style="text-align: center;" rowspan="3">扫码</th>'
								+'<th style="text-align: center;" rowspan="3">T1/T0</th>'
								+'<th style="text-align: center;">扫码1000以下</th>'
								+'<input type="hidden" name="machineType" value="2"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="8"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input style="text-align: center;width:70px" name="cashCost" type="text" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input style="text-align: center;width:70px" type="text" name="cashRate" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							+'<tr id="00311T15">'
								+'<th style="text-align: center;">扫码1000以上</th>'
								+'<input type="hidden" name="machineType" value="2"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="9"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input style="text-align: center;width:70px" name="cashCost" type="text" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input style="text-align: center;width:70px" type="text" name="cashRate" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							+'<tr id="00311T16">'
								+'<th style="text-align: center;">银联二维码</th>'
								+'<input type="hidden" name="machineType" value="2"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="10"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input style="text-align: center;width: 70px" type="text" name="cashCost" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input style="text-align: center;width: 70px" type="text" name="cashRate" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							+'<tr id="00311T17">'
								+'<th style="text-align: center;" rowspan="3">刷卡</th>'
								+'<th style="text-align: center;" rowspan="3">T1/T0</th>'
								+'<th style="text-align: center;">标准</th>'
								+'<input type="hidden" name="machineType" value="2"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="11"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.41"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="17"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.53"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width: 70px" type="text" name="cashCost" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width:70px" type="text" name="cashRate" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							+'<tr id="00311T18">'
								+'<th style="text-align: center;">优惠</th>'
								+'<input type="hidden" name="machineType" value="2"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="12"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.3"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="14"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.4"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width: 70px" type="text" name="cashCost" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width:70px" type="text" name="cashRate" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							+'<tr id="00311T19">'
								+'<th style="text-align: center;">减免</th>'
								+'<input type="hidden" name="machineType" value="2"/>'
								+'<input type="hidden" name="txnType" value="6"/>'
								+'<input type="hidden" name="txnDetail" value="13"/>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.03"/>'
								+'</td>'
								+'<th style="text-align: center;">—</th>'
								+'<td style="text-align: center;">'
								+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.03"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width: 70px" type="text" name="cashCost" value="1" readonly="readonly"/>'
								+'</td>'
								+'<td style="text-align: center;">'
								+'<input style="text-align: center;width:70px" type="text" name="cashRate" value="0.05" readonly="readonly"/>'
								+'</td>'
							+'</tr>'
							// +'<tr id="00311TRebateType">'
							// 	+'<th style="text-align: center;">活动</th>'
							// 	+'<td style="text-align: center;" colspan="9"><a id="" onclick="sysAdmin_00311_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动成本</span></span></a></td>'
							// +'</tr>'
					+'</tbody>'
				+'</table>'
			+'</fieldset>'
		+'</div>'
		
		var fenrun = ''
					+'<fieldset>'
					+'	<legend>分润信息</legend>'
					+'	<table class="table">'
					+'		<tr>'
					+'			<th>是否开通分润钱包：</th>'
					+'			<td><select name="purseType" class="easyui-combobox"'
					+'				data-options="panelHeight:\'auto\',editable:false"'
					+'				style="width: 155px;">'
					+'					<option value="" selected="selected"></option>'
					+'					<option value="1">是</option>'
					+'					<option value="0">否</option>'
					+'			</select>'
					+'			</td>'
					+'		</tr>'
					+'		<tr>'
					+'			<th>分润结算周期：</th>'
					+'			<td><input name="cycle" class="easyui-validatebox"'
					+'				data-options="required:true,validType:\'money\'" value="30" style="width: 151px;" readonly="readonly" /></td>'
					+'		</tr>'
					+'		<tr>'
					+'			<th>分润税点（%）：</th>'
					+'			<td><input name="cashRate" class="easyui-validatebox"'
					+'				data-options="validType:\'money\'" style="width: 151px;" /></td>'
					+'		</tr>'
					+'	</table>'
					+'</fieldset>';
		<% if(user.getUnitLvl()==1||user.getUnitLvl()==0){ %>//签一代
			$("#00311_tabs").append(tab2);
			$("#fenrun_00311").append(fenrun);
			// 贷记卡成本借记卡成本除了理财做同步事件处理
    		var _trs = $("#00311_tab2_body").children("tr");
    		for(var i = 1; i < _trs.length+1; i ++){
    			if(i == 3||i == 17||i == 18||i == 19){
    				continue;
    			}
    			$('#00311T'+i+'').find("input[name='debitRate']").bind('blur',function(){
    	            var _debitRate_value = $(this).val();
    	            $(this).parent().parent().find("input[name='creditRate']").val(_debitRate_value);
    	   		});
    		}
    		/* console.log($('#00311T1'));
			$('#00311T1').find("input[name='debitRate']").bind('blur',function(){
	            var _debitRate_value = $('#00311T1').find("input[name='debitRate']").val();
	            $('#00311T1').find("input[name='creditRate']").val(_debitRate_value)
	   		});  */
			$('#00311_tabs').tabs({
			    onSelect:function(title,index){
			        if(index == 2){
			        	// console.log(title+' ' + index)
			        	$("#00311_tab2_select").val(1);
			        }
			    }
			});
		<%}else{%>
			$("#00311_tab_3").remove();
		<%}%>
		
		$('#signUserId').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID',
			textField:'SALENAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'SALENAME',title:'销售姓名',width:70},
				{field:'UNNO',title:'所属机构',width:70},
				{field:'BUSID',title:'id',width:70,hidden:true}
			]]
		});

		//绑定图片选定后预览 
		bindImgPreview();
		//绑定图片选择后名称选择
		binddoImgCallbackFun();
	});

	function sysAdmin_00311_addRebate(){
		$('<div id="sysAdmin_00311_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">活动类型成本</span>',
			width: 500,
		    height:650,
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00322.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		sysAdmin_00311_edit1();
		    		$('#sysAdmin_00311_addDialog').dialog('destroy');
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_00311_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_00311_edit(){
		var tr_list = $("#00311_tab2_body").children("tr");
		var id = tr_list.length;
		var rebateType = $('#rebateType_00322').combobox('getValue');
		var rate = $("#rate_00322").val();
		var debitRate = $("#debitRate_00322").val();
		var cash = $("#cash_00322").val();
		var debitFeeamt = $("#debitFeeamt_00322").val();
		var validator = $('#sysAdmin_00322_from').form('validate');
		if(!validator){
			$.messager.alert('提示', "填写活动类成本信息有误");
			return ;
		}
		if(allRebateType.indexOf(rebateType)!=-1){
			$.messager.alert('提示', "已添加该活动的成本，请勿重复添加");
			return ;
		}
		var tt = rebateType>21?debitRate:rate;
		allRebateType[allRebateType.length] = rebateType;
		var html = '<tr id="00311T'+id+'">'
				+'<th style="text-align: center;">活动</th>'
				+'<th style="text-align: center;" colspan="4">活动'+rebateType+'</th>'
				+'<input type="hidden" name="machineType" value="1"/>'
				+'<input type="hidden" name="txnType" value="1"/>'
				+'<input type="hidden" name="txnDetail" value="'+rebateType+'"/>'
				+'<td style="text-align: center;">'
				+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+tt+'" readonly="readonly"/>'
				+'</td>'
				+'<td style="text-align: center;">'
        +'<input type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+debitFeeamt+'" readonly="readonly"/>'
        +'</td>'
				+'<td style="text-align: center;">'
				+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+rate+'" readonly="readonly"/>'
				+'</td>'
				+'<td style="text-align: center;">'
				+'	<input style="text-align: center;width:70px" name="cashCost" type="text" value="'+cash+'" readonly="readonly"/>'
				+'</td>'
				+'<th style="text-align: center;">—</th>'
			+'</tr>';
		$("#00311TRebateType").before(html);
	}

	function sysAdmin_00311_edit1(){
		var currentTrList = $("#00311_ACT_TR").children("tr");
		var id = currentTrList.length;
		var rebateType = $('#rebateType_00322').combobox('getValue');
		var rate = $("#rate_00322").val();
		var debitRate = $("#debitRate_00322").val();
		var cash = $("#cash_00322").val();
		var debitFeeamt = $("#debitFeeamt_00322").val();
		var wxUpRate = $("#wxUpRate_00322").val();
		var wxUpCash = $("#wxUpCash_00322").val();
		var wxUpRate1 = $("#wxUpRate1_00322").val();
		var wxUpCash1 = $("#wxUpCash1_00322").val();
		var zfbRate = $("#zfbRate_00322").val();
		var zfbCash = $("#zfbCash_00322").val();
		var ewmRate = $("#ewmRate_00322").val();
		var ewmCash = $("#ewmCash_00322").val();
		var ysfRate = $("#ysfRate_00322").val();
		var hbRate = $("#hbRate_00322").val();
		var hbCash = $("#hbCash_00322").val();
		var subType = $("#hrt_cost_rebate_subType").val();


		var validator = $('#sysAdmin_00322_from').form('validate');
		if(!validator){
			$.messager.alert('提示', "填写活动类成本信息有误");
			return ;
		}
		if(allRebateType.indexOf(rebateType)!=-1){
			$.messager.alert('提示', "已添加该活动的成本，请勿重复添加");
			return ;
		}
		var hb_html;
		// debugger;
		if(subType){
			if(subType==1){
				if(!hbRate || !hbCash){
					$.messager.alert('提示', "填写活动类成本信息有误");
					return ;
				}
				// TODO @author:lxg-20200316 活动是否有花呗成本
				hb_html='<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>'+
						'<td><input type="text" name="hbRate" value="'+hbRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+'<td><input type="text" name="hbCash" value="'+hbCash+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>';
			}else if(subType==2){
				hb_html='<td><input type="text" name="ysfRate" value="'+ysfRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
						'<td><input type="text" name="debitRate" value="'+debitRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
						'<td><input type="text" name="debitFeeamt" value="'+debitFeeamt+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
						'<td><input type="text" name="hbRate" value="'+hbRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+'<td><input type="text" name="hbCash" value="'+hbCash+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>';
			}

		}else{
			if(!ysfRate || !debitRate || !debitFeeamt){
				$.messager.alert('提示', "填写活动类成本信息有误");
				return ;
			}
			hb_html='<td><input type="text" name="ysfRate" value="'+ysfRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
					'<td><input type="text" name="debitRate" value="'+debitRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
					'<td><input type="text" name="debitFeeamt" value="'+debitFeeamt+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
					'<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>';
		}
		allRebateType[allRebateType.length] = rebateType;
		var html = '<tr id="00311TACT'+id+'">'+
        '<input type="hidden" name="machineType" value="1"/><input type="hidden" name="txnType" value="1"/>'+
				'<td><input type="text" name="txnDetail" value="'+rebateType+'" style="width:130px;" readonly="readonly" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="creditRate" value="'+rate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="cashCost" value="'+cash+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="wxUpRate" value="'+wxUpRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="wxUpCash" value="'+wxUpCash+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="wxUpRate1" value="'+wxUpRate1+'"  style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="wxUpCash1" value="'+wxUpCash1+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="zfbRate" value="'+zfbRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="zfbCash" value="'+zfbCash+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="ewmRate" value="'+ewmRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				'<td><input type="text" name="ewmCash" value="'+ewmCash+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				// '<td><input type="text" name="ysfRate" value="'+ysfRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				// '<td><input type="text" name="debitRate" value="'+debitRate+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				// '<td><input type="text" name="debitFeeamt" value="'+debitFeeamt+'" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:\'money\'"/></td>'+
				hb_html+
				'</tr>';
		$("#00311_ACT_TR").append(html);
	}

	function binddoImgCallbackFun(){
		$('#dealUpLoadFile2').change(function() {doImgCallbackFun('dealUpLoadFile2','dealUpLoad2')});
		$('#busLicUpLoadFile2').change(function() {doImgCallbackFun('busLicUpLoadFile2','busLicUpLoad2')});
		$('#proofOfOpenAccountUpLoadFile2').change(function() {doImgCallbackFun('proofOfOpenAccountUpLoadFile2','proofOfOpenAccountUpLoad2')});
		$('#legalAUpLoadFile2').change(function() {doImgCallbackFun('legalAUpLoadFile2','legalAUpLoad2')});
		$('#legalBUpLoadFile2').change(function() {doImgCallbackFun('legalBUpLoadFile2','legalBUpLoad2')});
		$('#accountAuthUpLoadFile2').change(function() {doImgCallbackFun('accountAuthUpLoadFile2','accountAuthUpLoad2')});
		$('#accountLegalAUpLoadFile2').change(function() {doImgCallbackFun('accountLegalAUpLoadFile2','accountLegalAUpLoad2')});
		$('#accountLegalBUpLoadFile2').change(function() {doImgCallbackFun('accountLegalBUpLoadFile2','accountLegalBUpLoad2')});
		$('#accountLegalHandUpLoadFile2').change(function() {doImgCallbackFun('accountLegalHandUpLoadFile2','accountLegalHandUpLoad2')});
		$('#officeAddressUpLoadFile2').change(function() {doImgCallbackFun('officeAddressUpLoadFile2','officeAddressUpLoad2')});
		$('#profitUpLoadFile2').change(function() {doImgCallbackFun('profitUpLoadFile2','profitUpLoad2')});
	}

	function doImgCallbackFun(sourceId,targetId){
		var sourceVal = $("#"+sourceId).val();
		//获取jsp页面hidden的值  
		if(sourceVal)
			$("#"+targetId).val(sourceVal.replace(/.{0,}\\/, ""));	
	}

	function bindImgPreview(){ 
		$("#dealUpLoadFile2").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#busLicUpLoadFile2").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#proofOfOpenAccountUpLoadFile2").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#legalAUpLoadFile2").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		$("#legalBUpLoadFile2").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		$("#accountAuthUpLoadFile2").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		$("#accountLegalAUpLoadFile2").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 });
		$("#accountLegalBUpLoadFile2").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		$("#accountLegalHandUpLoadFile2").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
		$("#officeAddressUpLoadFile2").uploadPreview({ Img: "ImgPrA", Width: 120, Height: 120 });
		$("#profitUpLoadFile2").uploadPreview({ Img: "ImgPrB", Width: 120, Height: 120 });
	}

	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
				return util(value); 
	      }, 
	      message : '图片格式不正确' 
		}
	});
	
	function util(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".jpg" && ename!=".png" && ename!=".gif" && ename!=".jpeg" ){
				return false;
			}else{ 
				return true;
			}
		}
	}

	$.extend($.fn.validatebox.defaults.rules,{
		phoneValidator:{
			validator : function(value) { 
				return /\d+/.test(value); 
	      }, 
	      message : '电话号码格式不正确' 
		}
	});

 	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		parentUnno:{
			validator : function(value) { 
	            return /^\d{6}$/i.test(value); 
	        }, 
	        message : '只能输入6位数字' 
		}
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
 		money:{
			validator : function(value) {
				var isNum = /^(([1-9][0-9]*)|(([0]\.\d{1,4}|[1-9][0-9]*\.\d{1,4})))$/;
	            return isNum.test(value); 
	        }, 
	        message : '请输入正确数值(小数最多四位)' 
		}
	});
</script>

<style type="text/css">
	.hrt-label_pl15{
		padding-left:15px;
		color: red;
	}
</style>

<div id="00311_tabs" class="easyui-tabs" data-options="fit:true,border:false">
	<div id="00311_tab_1" title="代理商部分信息录入">
		<form id="sysAdmin_agentunit_addForm" method="post" enctype="multipart/form-data">
			<fieldset>
				<legend>基础信息</legend>
				<table class="table">
					<tr>
			    		<th>代理商全称：</th>
			    		<td><input type="text" name="agentName" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="70"/><font color="red">&nbsp;*</font></td>
			    		<th >代理商简称：</th>
			    		<td ><input type="text" name="agentShortNm" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="80"/><font color="red">&nbsp;*</font></td>
					</tr>
					<tr>
						<th>营业执照号：</th>
						<td><input type="text" name="bno" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    		<th>注册地址：</th>
			    		<td >
							<input type="text" name="regAddress" id="regAddress" style="width:70px;" maxlength="70" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
						</td>
					</tr>
			    	<tr>
						<th>营业地址：</th>
			    		<td >
							<input type="text" name="baddr" id="baddr" style="width:70px;" maxlength="70"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
						</td>
			    		<th>法人：</th>
			    		<td><input type="text" name="legalPerson" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    		
			    	</tr>
			    	<tr>
				    	<th>法人证件类型：</th>
				    	<td>
				    		<select name="legalType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
				    			<option value="1">身份证</option>
				    			<option value="2">军官证</option>
				    			<option value="3">护照</option>
				    			<option value="4">港澳通行证</option>
				    			<option value="5">其他</option>
				    		</select>
				    	</td>
			    		<th >法人证件号码：</th>
			    		<td ><input type="text" name="legalNum" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<td colspan="4">
							<label class="hrt-label_pl15">注意：以上信息均为“必填”。如代理商身份为个人，“营业执照号”填写代理商身份证号，“注册地址”填写身份证住址</label>
			    		</td>
		    		</tr>
				</table>
			</fieldset>
			
			<fieldset>
				<legend>账户信息</legend>
				<table class="table">
					<tr>
						<th>账户名称：</th>
						<td><input type="text" name="bankAccName" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="70"/><font color="red">&nbsp;*</font></td>
						<th>开户行名称：</th>
						<td><input type="text" name="bankBranch" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="70"/><font color="red">&nbsp;*</font></td>
					</tr>
					<tr>
						<th>账号：</th>
						<td><input type="text" name="bankAccNo" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
						<th>支付系统行号：</th>
			    		<td><input type="text" name="payBankID" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
		    		</tr>
		    		<tr>
			    		<th>开户类型：</th>
			    		<td>
			    			<input type="radio" name="accType" value="1" checked="checked"/>对公
							<input type="radio" name="accType" value="2" />对私
			    		</td>
			    		<!-- <th>是否为交通银行：</th>
			    		<td>
							<input type="radio" name="bankType" value="1" checked="checked"/>交通银行
							<input type="radio" name="bankType" value="2" />非交通银行
						</td> -->
						<th>开户地：</th>
						<td><input type="text" name="bankArea" style="width:70px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="70"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<!-- <th>开户银行所在地类别：</th>
			    		<td>
			    			<input type="radio" name="areaType" value="1" checked="checked"/>北京
							<input type="radio" name="areaType" value="2" />非北京
			    		</td> -->
			    		<td colspan="4">
							<label class="hrt-label_pl15">注意：以上信息均为“必填”，个体工商户或个人主体代理商结算账户必须为代理经营者本人，只支持企业商户授权入账。</label>
			    		</td>
			    	</tr>
				</table>
			</fieldset>
			<div id="fenrun_00311"></div>
			<fieldset>
				<legend>签约信息</legend>
				<table class="table">
					<tr>
						<th>签约销售：</th>
			    		<td>
			    			<select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="required:true,editable:false,validType:'spaceValidator'" style="width:135px;"></select><font color="red">&nbsp;*</font>
			    		</td>
			    		<th>保证金：</th>
		    			<td colspan="3"><input type="text" name="riskAmount" style="width:130px;" value="0" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'" maxlength="18" readonly="readonly"/><font color="red">&nbsp;*</font></td>
					</tr>
					<tr>
		    			<th>合同编号：</th>
			    		<td colspan="5"><input type="text" name="contractNo" style="width:130px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"/></td>
			    	</tr>
			    	<tr>	
			    		<th>代理商负责人：</th>
			    		<td><input type="text" name="contact" style="width:130px;"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    		<th>联系电话：</th>
			    		<td><input type="text" name="contactTel" style="width:130px;" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>邮箱：</th>
			    		<td><input type="text" name="contactPhone" style="width:130px;" maxlength="50" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<th>业务联系人：</th>
			    		<td><input type="text" name="businessContacts" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>联系电话：</th>
			    		<td><input type="text" name="businessContactsPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>邮箱：</th>
			    		<td><input type="text" name="businessContactsMail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>	
			    		<th>风控联系人：</th>
			    		<td><input type="text" name="riskContact" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>联系电话：</th>
			    		<td><input type="text" name="riskContactPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>邮箱：</th>
			    		<td><input type="text" name="riskContactMail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<th>结算联系人：</th>
			    		<td><input type="text" name="secondContact" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>联系电话：</th>
			    		<td><input type="text" name="secondContactTel" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    	
			    		<th>邮箱：</th>
			    		<td><input type="text" name="secondContactPhone" style="width:130px;" maxlength="50" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
					</tr>
					<tr>	
						<th>分润联系人：</th>
						<td><input type="text" name="profitContactPerson" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
						<th>联系电话：</th>
						<td><input type="text" name="profitContactPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
			    		<th>邮箱：</th>
			    		<td><input type="text" name="profitContactEmail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			    		<td colspan="4">
							<label class="hrt-label_pl15">注意：请准确填写联系信息，以免由于收不到业务通知造成经济损失。</label>
			    		</td>
			    	</tr>
				</table>
			</fieldset>
			<!-- 图片信息 -->
			<fieldset>
				<legend>图片信息</legend>
				<table class="table">
					<tr>
						<th>协议签章页照片：</th>
		   			<td>
		   				<input type="file" name="dealUpLoadFile" id="dealUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr1" width="120" height="120" /></div>
		   			</td>
			   		<th>营业执照：</th>
		   			<td>
		   				<input type="file" name="busLicUpLoadFile" id="busLicUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr2" width="120" height="120" /></div>
		   			</td>
		   		</tr>
		   		<tr>
			   		<th>对公开户证明：</th>
			   		<td>
			   			<input type="file" name="proofOfOpenAccountUpLoadFile" id="proofOfOpenAccountUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
			   			<div><img id="ImgPr3" width="120" height="120" /></div>
			   		</td>
			   		
						<th>法人身份证正面：</th>
		   			<td>
		   				<input type="file" name="legalAUpLoadFile" id="legalAUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr4" width="120" height="120" /></div>
		   			</td>
			   	</tr>
			   	<tr>
						<th>法人身份证反面：</th>
		   			<td>
		   				<input type="file" name="legalBUpLoadFile" id="legalBUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr5" width="120" height="120" /></div>
		   			</td>
						<th>入账授权书：</th>
		   			<td>
		   				<input type="file" name="accountAuthUpLoadFile" id="accountAuthUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr6" width="120" height="120" /></div>
		   			</td>
			   	</tr>
			   	<tr>
						<th>入账人身份证正面：</th>
		   			<td>
		   				<input type="file" name="accountLegalAUpLoadFile" id="accountLegalAUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr7" width="120" height="120" /></div>
		   			</td>
						<th>入账人身份证反面：</th>
		   			<td>
		   				<input type="file" name="accountLegalBUpLoadFile" id="accountLegalBUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr8" width="120" height="120" /></div>
		   			</td>
			   	</tr>
			   	<tr>
						<th>入账人手持身份证：</th>
		   			<td>
		   				<input type="file" name="accountLegalHandUpLoadFile" id="accountLegalHandUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPr9" width="120" height="120" /></div>
		   			</td>
						<th>办公地点照片：</th>
		   			<td>
		   				<input type="file" name="officeAddressUpLoadFile" id="officeAddressUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPrA" width="120" height="120" /></div>
		   			</td>
			   	</tr>
			   	<tr>
						<th>分润照片：</th>
		   			<td>
		   				<input type="file" name="profitUpLoadFile" id="profitUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
		   				<div><img id="ImgPrB" width="120" height="120" /></div>
		   			</td>
		   		</tr>
			   </table>
			</fieldset>
			<!-- 创建隐藏域 用于传递文件名 --> 
			<input type="hidden" name="dealUpLoad" id="dealUpLoad2">
			<input type="hidden" name="busLicUpLoad" id="busLicUpLoad2">
			<input type="hidden" name="proofOfOpenAccountUpLoad" id="proofOfOpenAccountUpLoad2">
			<input type="hidden" name="legalAUpLoad" id="legalAUpLoad2">
			<input type="hidden" name="legalBUpLoad" id="legalBUpLoad2">
			<input type="hidden" name="accountAuthUpLoad" id="accountAuthUpLoad2">
			<input type="hidden" name="accountLegalAUpLoad" id="accountLegalAUpLoad2">
			<input type="hidden" name="accountLegalBUpLoad" id="accountLegalBUpLoad2">
			<input type="hidden" name="accountLegalHandUpLoad" id="accountLegalHandUpLoad2">
			<input type="hidden" name="officeAddressUpLoad" id="officeAddressUpLoad2">
			<input type="hidden" name="profitUpLoad" id="profitUpLoad2">
			<!-- 创建成本数据隐藏域 -->
			<input type="hidden" name="costData" id="00311_costData"/>
		</form>
		<!-- 保证tab2 被选中 -->
		<input type="hidden" id="00311_tab2_select"/>
	</div>

	<div id="00311_tab_3" title="代理商活动成本信息录入">
		<fieldset>
			<legend>代理商活动成本信息</legend>
			<table class="table">
				<tr>
					<th>添加活动类型：</th>
					<td colspan="5" style="text-align: center">
						<a id="" onclick="sysAdmin_00311_addRebate()" class="l-btn" href="javascript:void(0)">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动成本</span>
							</span>
						</a>
					</td>
				</tr>

			</table>
			<br/>
			<table class="table" id="00311_act">
				<thead>
					<tr>
						<th style="text-align: center;">活动类型</th>
						<th style="text-align: center;">扫码1000以下（终端0.38）费率(%)</th>
						<th style="text-align: center;">扫码1000以下（终端0.38）转账费</th>
						<th style="text-align: center;">扫码1000以上（终端0.38）费率(%)</th>
						<th style="text-align: center;">扫码1000以上（终端0.38）转账费</th>
						<th style="text-align: center;">扫码1000以上（终端0.45）费率(%)</th>
						<th style="text-align: center;">扫码1000以上（终端0.45）转账费</th>
						<th style="text-align: center;">扫码1000以下（终端0.45）费率(%)</th>
						<th style="text-align: center;">扫码1000以下（终端0.45）转账费</th>
						<th style="text-align: center;">银联二维码费率(%)</th>
						<th style="text-align: center;">银联二维码转账费</th>
						<th style="text-align: center;">云闪付费率(%)</th>
						<th style="text-align: center;">刷卡成本(%)</th>
						<th style="text-align: center;">刷卡提现成本</th>
						<th style="text-align: center;">花呗成本(%)</th>
						<th style="text-align: center;">花呗提现成本</th>
					</tr>
				</thead>
				<tbody id="00311_ACT_TR"></tbody>
			</table>
		</fieldset>

	</div>
</div>