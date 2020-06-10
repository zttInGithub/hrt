<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>
<%
    Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession);
%>
   <!-- 代理商新增-渠道 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var allRebateType = new Array();//记录已添加的活动类型，防止重复添加
	$(function(){
		var buid=<%=request.getParameter("buid")%>;
		var html = ''
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
			+'<tbody id="00412_tab2_body">'
					+'<tr id="00412T1">'
						+'<th style="text-align: center;" rowspan="19">非活动</th>'
						+'<th style="text-align: center;" rowspan="13">手刷</th>'
						+'<th style="text-align: center;" rowspan="3">刷卡</th>'
						+'<th style="text-align: center;" rowspan="2">秒到</th>'
						+'<th style="text-align: center;">0.6秒到</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="1"/>'
						+'<input type="hidden" name="txnDetail" value="1"/>'
						+'<input type="hidden" name="hucid" id="H111"/>'
						+'<td style="text-align: center;">'
						+'<input id="K111A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K111C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K111D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T2">'
						+'<th style="text-align: center;">0.72秒到</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="1"/>'
						+'<input type="hidden" name="txnDetail" value="2"/>'
						+'<input type="hidden" name="hucid" id="H112"/>'
						+'<td style="text-align: center;">'
						+'<input id="K112A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K112C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K112D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T3">'
						+'<th style="text-align: center;">理财</th>'
						+'<th style="text-align: center;">理财</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="2"/>'
						+'<input type="hidden" name="txnDetail" value="3"/>'
						+'<input type="hidden" name="hucid" id="H123"/>'
						+'<td style="text-align: center;">'
						+'<input id="K123A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K123B" type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K123C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K123D" style="text-align: center;width: 70px" type="text" value="1" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K123E" style="text-align: center;width:70px" type="text" value="0.05" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					+'<tr id="00412T4">'
						+'<th style="text-align: center;">代还</th>'
						+'<th style="text-align: center;">代还</th>'
						+'<th style="text-align: center;">信用卡代还</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="3"/>'
						+'<input type="hidden" name="txnDetail" value="4"/>'
						+'<input type="hidden" name="hucid" id="H134"/>'
						+'<td style="text-align: center;">'
						+'<input id="K134A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K134C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T5">'
						+'<th style="text-align: center;">云闪付</th>'
						+'<th style="text-align: center;">云闪付</th>'
						+'<th style="text-align: center;">云闪付</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="4"/>'
						+'<input type="hidden" name="txnDetail" value="5"/>'
						+'<input type="hidden" name="hucid" id="H145"/>'
						+'<td style="text-align: center;">'
						+'<input id="K145A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K145C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T6">'
						+'<th style="text-align: center;" rowspan="2">快捷</th>'
						+'<th style="text-align: center;" rowspan="2">快捷</th>'
						+'<th style="text-align: center;">快捷支付成本VIP</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="5"/>'
						+'<input type="hidden" name="txnDetail" value="6"/>'
						+'<input type="hidden" name="hucid" id="H156"/>'
						+'<td style="text-align: center;">'
						+'<input id="K156A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K156C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K156D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T7">'
						+'<th style="text-align: center;">快捷支付成本完美账单</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="5"/>'
						+'<input type="hidden" name="txnDetail" value="7"/>'
						+'<input type="hidden" name="hucid" id="H157"/>'
						+'<td style="text-align: center;">'
						+'<input id="K157A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K157C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K157D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T8">'
						+'<th style="text-align: center;" rowspan="6">扫码</th>'
						+'<th style="text-align: center;" rowspan="3">T0</th>'
						+'<th style="text-align: center;">扫码1000以下</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="8"/>'
						+'<input type="hidden" name="hucid" id="H168"/>'
						+'<td style="text-align: center;">'
						+'<input id="K168A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K168C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K168D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T9">'
						+'<th style="text-align: center;">扫码1000以上</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="9"/>'
						+'<input type="hidden" name="hucid" id="H169"/>'
						+'<td style="text-align: center;">'
						+'<input id="K169A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K169C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K169D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T10">'
						+'<th style="text-align: center;">银联二维码</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="10"/>'
						+'<input type="hidden" name="hucid" id="H1610"/>'
						+'<td style="text-align: center;">'
						+'<input id="K1610A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K1610C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K1610D" type="text" name="cashCost" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T11">'
						+'<th style="text-align: center;" rowspan="3">T1</th>'
						+'<th style="text-align: center;">扫码1000以下</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="7"/>'
						+'<input type="hidden" name="txnDetail" value="8"/>'
						+'<input type="hidden" name="hucid" id="H178"/>'
						+'<td style="text-align: center;">'
						+'<input id="K178A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K178C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T12">'
						+'<th style="text-align: center;">扫码1000以上</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="7"/>'
						+'<input type="hidden" name="txnDetail" value="9"/>'
						+'<input type="hidden" name="hucid" id="H179"/>'
						+'<td style="text-align: center;">'
						+'<input id="K179A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K179C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T13">'
						+'<th style="text-align: center;">银联二维码</th>'
						+'<input type="hidden" name="machineType" value="1"/>'
						+'<input type="hidden" name="txnType" value="7"/>'
						+'<input type="hidden" name="txnDetail" value="10"/>'
						+'<input type="hidden" name="hucid" id="H1710"/>'
						+'<td style="text-align: center;">'
						+'<input id="K1710A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K1710C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<th style="text-align: center;">—</th>'
					+'</tr>'
					+'<tr id="00412T14">'
						+'<th style="text-align: center;" rowspan="6">传统</th>'
						+'<th style="text-align: center;" rowspan="3">扫码</th>'
						+'<th style="text-align: center;" rowspan="3">T1/T0</th>'
						+'<th style="text-align: center;">扫码1000以下</th>'
						+'<input type="hidden" name="machineType" value="2"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="8"/>'
						+'<input type="hidden" name="hucid" id="H268"/>'
						+'<td style="text-align: center;">'
						+'<input id="K268A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K268C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K268D" style="text-align: center;width:70px" name="cashCost" type="text" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K268E" style="text-align: center;width:70px" type="text" name="cashRate" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					+'<tr id="00412T15">'
						+'<th style="text-align: center;">扫码1000以上</th>'
						+'<input type="hidden" name="machineType" value="2"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="9"/>'
						+'<input type="hidden" name="hucid" id="H269"/>'
						+'<td style="text-align: center;">'
						+'<input id="K269A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K269C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K269D" style="text-align: center;width:70px" name="cashCost" type="text" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K269E" style="text-align: center;width:70px" type="text" name="cashRate" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					+'<tr id="00412T16">'
						+'<th style="text-align: center;">银联二维码</th>'
						+'<input type="hidden" name="machineType" value="2"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="10"/>'
						+'<input type="hidden" name="hucid" id="H2610"/>'
						+'<td style="text-align: center;">'
						+'<input id="K2610A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'<input id="K2610C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2610D" style="text-align: center;width: 70px" type="text" name="cashCost" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2610E" style="text-align: center;width: 70px" type="text" name="cashRate" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					+'<tr id="00412T17">'
						+'<th style="text-align: center;" rowspan="3">刷卡</th>'
						+'<th style="text-align: center;" rowspan="3">T1/T0</th>'
						+'<th style="text-align: center;">标准</th>'
						+'<input type="hidden" name="machineType" value="2"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="11"/>'
						+'<input type="hidden" name="hucid" id="H2611"/>'
						+'<td style="text-align: center;">'
						+'	<input id="K2611A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.41"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'	<input id="K2611B" type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="17"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'	<input id="K2611C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.53"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2611D" style="text-align: center;width: 70px" type="text" name="cashCost" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2611E" style="text-align: center;width:70px" type="text" name="cashRate" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					+'<tr id="00412T18">'
						+'<th style="text-align: center;">优惠</th>'
						+'<input type="hidden" name="machineType" value="2"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="12"/>'
						+'<input type="hidden" name="hucid" id="H2612"/>'
						+'<td style="text-align: center;">'
						+'	<input id="K2612A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.3"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'	<input id="K2612B" type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="14"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'	<input id="K2612C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.4"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2612D" style="text-align: center;width: 70px" type="text" name="cashCost" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2612E" style="text-align: center;width:70px" type="text" name="cashRate" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					+'<tr id="00412T19">'
						+'<th style="text-align: center;">减免</th>'
						+'<input type="hidden" name="machineType" value="2"/>'
						+'<input type="hidden" name="txnType" value="6"/>'
						+'<input type="hidden" name="txnDetail" value="13"/>'
						+'<input type="hidden" name="hucid" id="H2613"/>'
						+'<td style="text-align: center;">'
						+'	<input id="K2613A" type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.03"/>'
						+'</td>'
						+'<th style="text-align: center;">—</th>'
						+'<td style="text-align: center;">'
						+'	<input id="K2613C" type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="0.03"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2613D" style="text-align: center;width: 70px" type="text" name="cashCost" readonly="readonly"/>'
						+'</td>'
						+'<td style="text-align: center;">'
						+'<input id="K2613E" style="text-align: center;width:70px" type="text" name="cashRate" readonly="readonly"/>'
						+'</td>'
					+'</tr>'
					/*+'<tr id="00412TRebateType">'
						+'<th style="text-align: center;">活动</th>'
						+'<td style="text-align: center;" colspan="9"><a id="" onclick="sysAdmin_00412_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动成本</span></span></a></td>'
					+'</tr>'*/
			+'</tbody>'
		+'</table>'
	+'</fieldset>'
	<%-- <% if(user.getUnitLvl()==0){ %> --%>
		$("#00412_hrtCostForm").append(html);
		$('#00412T1').find("input[name='debitRate']").bind('blur',function(){
            var _debitRate_value = $('#00412T1').find("input[name='debitRate']").val();
            $('#00412T1').find("input[name='creditRate']").val(_debitRate_value)
   		});
		//带入成本信息,修改
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_queryHrtUnnoCostSingle.action',
			dataType:"json",  
	   		type:"post",
	   		data:{buid:buid},
	   		success:function(data){
	   			if(data.success){
	   				var obj = data.obj;
	   				var keys = [];
	   				for(var key in obj){
	   					keys.push(key);
	   				}
	   				$.each($("#00412_hrtCostForm").find('input'), function(index) {
	   					var id = $(this).attr('id');
	   					if('' != id && 'undefined' != typeof(id) && $.inArray(id,keys) != -1){
	   						$('#'+id).val(obj[id]);
	   					}
	   				});
	   				// 贷记卡成本借记卡成本除了理财做同步事件处理
   		    		var _trs = $("#00412_tab2_body").children("tr");
   		    		for(var i = 1; i < _trs.length+1; i ++){
   		    			if(i == 3||i == 17||i == 18||i == 19){
   		    				continue;
   		    			}
   		    			$('#00412T'+i+'').find("input[name='debitRate']").bind('blur',function(){
   		    	            var _debitRate_value = $(this).val();
   		    	            $(this).parent().parent().find("input[name='creditRate']").val(_debitRate_value);
   		    	   		});
   		    		}
   		    		//去加载活动成本
	   				// sysAdmin_00412_queryRebate(buid);
	   				sysAdmin_00412_queryRebate1(buid);
	   			}
	   		}
		});
	<%-- <%}%> --%>
		
		$('#signUserId').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID', 
			textField:'SALENAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'SALENAME',title:'销售姓名',width:150},
				{field:'UNNO',title:'所属机构',width:150},
				{field:'BUSID',title:'id',width:150,hidden:true}
			]]
		}); 
		
		//绑定图片选定后预览 
		bindImgPreview();
		//绑定图片选择后名称选择
		binddoImgCallbackFun();
		//初始化预览图片
		initImg();
		setTimeout(function(){
			if('K'==$('#approveStatus').val()){
				$('#returnReasonTr').show();
			}
		},1000);
	});

	function sysAdmin_00412_queryRebate(buid){
		//加载活动成本
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_queryHrtUnnoCostSingleRebate.action',
			dataType:"json",  
	   		type:"post",
	   		data:{buid:buid},
	   		success:function(data){
	   			if(data.success){
	   				var obj = data.list;
	   				if(obj.length != 0){
	   					for(var k=0;k<obj.length;k++){
	   						allRebateType[allRebateType.length] = obj[k].TXN_DETAIL+"";
	   						var tr_list = $("#00412_tab2_body").children("tr");
		   					var id = tr_list.length;
								var tt = '0';
								if(obj[k].DEBIT_FEEAMT){
									tt=obj[k].DEBIT_FEEAMT
								}
								var _readonly ='readonly="readonly"';
								if(obj[k].TXN_DETAIL>21){
									_readonly='';
								}

								var html = '<tr id="00412T'+id+'">'
			   					+'<th style="text-align: center;">活动</th>'
			   					+'<th style="text-align: center;" colspan="3">活动'+obj[k].TXN_DETAIL+'</th>'
			   					+'<td style="text-align: center;"><img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_agentunit_00412_delFun('+obj[k].HUCID+','+id+','+obj[k].TXN_DETAIL+')"/></td>'
			   					+'<input type="hidden" name="machineType" value="1"/>'
			   					+'<input type="hidden" name="txnType" value="1"/>'
			   					+'<input type="hidden" name="txnDetail" value="'+obj[k].TXN_DETAIL+'"/>'
			   					+'<input type="hidden" name="hucid" id="H2610" value="'+obj[k].HUCID+'"/>'
			   					+'<td style="text-align: center;">'
			   					+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].DEBIT_RATE+'" />'
			   					+'</td>'
			   					+'<td style="text-align: center;">'
									+'<input type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+tt+'"/>'
									+'</td>'
			   					+'<td style="text-align: center;">'
			   					+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].CREDIT_RATE+'" '+_readonly+'/>'
			   					+'</td>'
			   					+'<td style="text-align: center;">'
			   					+'	<input style="text-align: center;width:70px" name="cashCost" type="text" value="'+obj[k].CASH_COST+'" />'
			   					+'</td>'
			   					+'<th style="text-align: center;">—</th>'
			   				+'</tr>';
		   					$("#00412TRebateType").before(html);
		   					//添加事件
								if(obj[k].TXN_DETAIL<=21) {
									$('#00412T' + id + '').find("input[name='debitRate']").bind('blur', function () {
										var _debitRate_value = $(this).val();
										$(this).parent().parent().find("input[name='creditRate']").val(_debitRate_value);
									});
								}
	   					}
	   				}
	   			}
	   		}
		});
	}
	function sysAdmin_00412_queryRebate1(buid){
		//加载活动成本
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_queryHrtUnnoCostSingleRebate.action',
			dataType:"json",
			type:"post",
			data:{buid:buid},
			success:function(data){
				if(data.success){
					var obj = data.list;
					if(obj.length != 0){
						for(var k=0;k<obj.length;k++){
							allRebateType[allRebateType.length] = obj[k].TXN_DETAIL+"";
							var tr_list = $("#00412_ACT_TR").children("tr");
							var id = tr_list.length;
							var html = '<tr id="00412_ACTT'+id+'">'
									+'<td style="text-align: center;">'+obj[k].TXN_DETAIL+'</th>'
									+'<td style="text-align: center;"><img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_agentunit_00412_delFun('+obj[k].HUCID+','+id+','+obj[k].TXN_DETAIL+')"/></td>'
									+'<input type="hidden" name="machineType" value="1"/>'
									+'<input type="hidden" name="txnType" value="1"/>'
									+'<input type="hidden" name="txnDetail" value="'+obj[k].TXN_DETAIL+'"/>'
									+'<input type="hidden" name="hucid" id="H2610" value="'+obj[k].HUCID+'"/>'
									+'<td><input name="creditRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].CREDIT_RATE+'" /></td>'
									+'<td><input name="cashCost" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].CASH_COST+'"/></td>'
									+'<td><input name="wxUpRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].WX_UPRATE+'"/></td>'
									+'<td><input name="wxUpCash" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].WX_UPCASH+'" /></td>'
									+'<td><input name="wxUpRate1" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].WX_UPRATE1+'"/></td>'
									+'<td><input name="wxUpCash1" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].WX_UPCASH1+'" /></td>'
									+'<td><input name="zfbRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].ZFB_RATE+'" /></td>'
									+'<td><input name="zfbCash" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].ZFB_CASH+'" /></td>'
									+'<td><input name="ewmRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].EWM_RATE+'" /></td>'
									+'<td><input name="ewmCash" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].EWM_CASH+'" /></td>';

									var hb_html;
									// plus活动展示所有
									if(obj[k].SUBTYPE){
										if(obj[k].SUBTYPE==1){
											hb_html='<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>'
													+'<td><input name="hbRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].HB_RATE+'" /></td>'
													+'<td><input name="hbCash" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].HB_CASH+'" /></td>';
										}else if(obj[k].SUBTYPE==2){
											hb_html='<td><input name="ysfRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].YSF_RATE+'" /></td>'
													+'<td><input name="debitRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].DEBIT_RATE+'" /></td>'
													+'<td><input name="debitFeeamt" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].DEBIT_FEEAMT+'" /></td>'
													+'<td><input name="hbRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].HB_RATE+'" /></td>'
													+'<td><input name="hbCash" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].HB_CASH+'" /></td>';
										}
									}else{
										hb_html='<td><input name="ysfRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].YSF_RATE+'" /></td>'
												+'<td><input name="debitRate" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].DEBIT_RATE+'" /></td>'
												+'<td><input name="debitFeeamt" type="text" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].DEBIT_FEEAMT+'" /></td>'
										+'<th style="text-align: center;">—</th>'
										+'<th style="text-align: center;">—</th>';
									}
							html=html+hb_html+'</tr>';
							$("#00412_ACT_TRebateType").before(html);
						}
					}
				}
			}
		});
	}
	
	//删除已添加
	function sysAdmin_agentunit_00412_delFun(hucid,id,txnDetail){
		$.ajax({
 			url:'${ctx}/sysAdmin/agentunit_deleteRebateCost.action',
     		dataType:"json",  
     		type:"post",
     		data:{hucid:hucid},
    		success:function(data) {
    			// $("#00412T"+id).empty();
    			// $("#00412T"+id).hide();
					$("#00412_ACTT"+id).empty();
					$("#00412_ACTT"+id).hide();
    			var index = allRebateType.indexOf(txnDetail+"");
    			if (index > -1) {
    				allRebateType.splice(index, 1);
    			}
 	    	} 
 		});
	}
	function sysAdmin_00412_addRebate(){
		$('<div id="sysAdmin_00412_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">活动类型成本</span>',
			width: 450,
		    height:740,
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00322.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		sysAdmin_00412_edit1();
		    		// sysAdmin_00412_edit();
		    		$('#sysAdmin_00412_addDialog').dialog('destroy');
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_00412_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	function sysAdmin_00412_edit(){
		var tr_list = $("#00412_tab2_body").children("tr");
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
		allRebateType[allRebateType.length] = rebateType;
		var tt = rebateType>21?debitRate:rate;
		var html = '<tr id="00412T'+id+'">'
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
		$("#00412TRebateType").before(html);
	}

	function sysAdmin_00412_edit1(){
		var currentTrList = $("#00412_ACT_TR").children("tr");
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
		var html = '<tr id="00412_ACTT'+id+'">'+
				'<input type="hidden" name="machineType" value="1"/><input type="hidden" name="txnType" value="1"/><input type="hidden" name="txnDetail" value="'+rebateType+'" />'+
				'<td  style="text-align: center;">'+rebateType+'</td>'+
				'<td></td>'+
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
		$("#00412_ACT_TRebateType").before(html);
	}


	function initImg(){
 		var buid=<%=request.getParameter("buid")%>;
 		$.ajax({
 			url:'${ctx}/sysAdmin/agentunit_serachAgentInfoDetailed.action',
     		dataType:"json",  
     		type:"post",
     		data:{buid:buid},
    			success:function(data) {
    				var json=eval(data);
	 	    		if (json!="") { 
	 	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0]);
	 		    		var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
	 		    		var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
	 		    		var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
	 		    		var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
	 		    		var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
	 		    		var path7='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[6]);
	 		    		var path8='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[7]);
	 		    		var path9='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[8]);
	 		    		var pathA='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[9]);
	 		    		var pathB='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[10]);
	 	    			$('#ImgPr1').attr('src',path1);
	 	    			$('#ImgPr2').attr('src',path2);
	 	    			$('#ImgPr3').attr('src',path3);
	 	    			$('#ImgPr4').attr('src',path4);
	 	    			$('#ImgPr5').attr('src',path5);
	 	    			$('#ImgPr6').attr('src',path6);
	 	    			$('#ImgPr7').attr('src',path7);
	 	    			$('#ImgPr8').attr('src',path8);
	 	    			$('#ImgPr9').attr('src',path9);
	 	    			$('#ImgPrA').attr('src',pathA);
	 	    			$('#ImgPrB').attr('src',pathB);
	     			}  
 	    	} 
 		});
 	}

	function binddoImgCallbackFun(){
		$('#dealUpLoadFile').change(function() {doImgCallbackFun('dealUpLoadFile','dealUpLoad')});
		$('#busLicUpLoadFile').change(function() {doImgCallbackFun('busLicUpLoadFile','busLicUpLoad')});
		$('#proofOfOpenAccountUpLoadFile').change(function() {doImgCallbackFun('proofOfOpenAccountUpLoadFile','proofOfOpenAccountUpLoad')});
		$('#legalAUpLoadFile').change(function() {doImgCallbackFun('legalAUpLoadFile','legalAUpLoad')});
		$('#legalBUpLoadFile').change(function() {doImgCallbackFun('legalBUpLoadFile','legalBUpLoad')});
		$('#accountAuthUpLoadFile').change(function() {doImgCallbackFun('accountAuthUpLoadFile','accountAuthUpLoad')});
		$('#accountLegalAUpLoadFile').change(function() {doImgCallbackFun('accountLegalAUpLoadFile','accountLegalAUpLoad')});
		$('#accountLegalBUpLoadFile').change(function() {doImgCallbackFun('accountLegalBUpLoadFile','accountLegalBUpLoad')});
		$('#accountLegalHandUpLoadFile').change(function() {doImgCallbackFun('accountLegalHandUpLoadFile','accountLegalHandUpLoad')});
		$('#officeAddressUpLoadFile').change(function() {doImgCallbackFun('officeAddressUpLoadFile','officeAddressUpLoad')});
		$('#profitUpLoadFile').change(function() {doImgCallbackFun('profitUpLoadFile','profitUpLoad')});
	}

	function doImgCallbackFun(sourceId,targetId){
		var sourceVal = $("#"+sourceId).val();
		//获取jsp页面hidden的值  
		if(sourceVal)
			$("#"+targetId).val(sourceVal.replace(/.{0,}\\/, ""));	
	}

	function bindImgPreview(){ 
		$("#dealUpLoadFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#busLicUpLoadFile").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#proofOfOpenAccountUpLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#legalAUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		$("#legalBUpLoadFile").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		$("#accountAuthUpLoadFile").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		$("#accountLegalAUpLoadFile").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 });
		$("#accountLegalBUpLoadFile").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		$("#accountLegalHandUpLoadFile").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
		$("#officeAddressUpLoadFile").uploadPreview({ Img: "ImgPrA", Width: 120, Height: 120 });
		$("#profitUpLoadFile").uploadPreview({ Img: "ImgPrB", Width: 120, Height: 120 });
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
</script>
 
<style type="text/css">
	.hrt-label_pl15{
		padding-left:15px;
		color: red;
	}
</style>
 
<form id="sysAdmin_agentunit_00412_editForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>基础信息</legend>
		<table class="table">
			<tr>
	    		<th>代理商全称：</th>
	    		<td><input type="text" name="agentName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
				<th >代理商简称：</th>
				<td ><input type="text" name="agentShortNm" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator',validType:'spaceValidator'" maxlength="80"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
				<th>营业执照号：</th>
				<td><input type="text" name="bno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    		<th>注册地址：</th>
	    		<td >
					<input type="text" name="regAddress" id="regAddress" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
				</td>
				
			</tr>
	    	<tr>
				<th>营业地址：</th>
	    		<td >
					<input type="text" name="baddr" id="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
				</td>
	    		<th>法人：</th>
	    		<td><input type="text" name="legalPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
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
	    		<th>法人证件号码：</th>
	    		<td ><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
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
				<td><input type="text" name="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
				<th>开户行名称：</th>
				<td><input type="text" name="bankBranch" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="200"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
				<th>账号：</th>
				<td><input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
				<th>支付系统行号：</th>
	    		<td><input type="text" name="payBankID" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
    		</tr>
    		<tr>
	    		<th>开户类型：</th>
	    		<td>
	    			<input type="radio" name="accType" value="1" />对公
					<input type="radio" name="accType" value="2" />对私
	    		</td>
	    		<!-- <th>是否为交通银行：</th>
	    		<td>
					<input type="radio" name="bankType" value="1" checked="checked"/>交通银行
					<input type="radio" name="bankType" value="2" />非交通银行
				</td> -->
				<th>开户地：</th>
				<td><input type="text" name="bankArea" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
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
	<fieldset>
		<legend>分润信息</legend>
		<table class="table">
			<tr>
				<th>是否开通分润钱包：</th>
				<td><select name='purseType' class='easyui-combobox'
					data-options="panelHeight:'auto',editable:false"
					style="width: 155px;">
						<option value=""></option>
						<option value="1">是</option>
						<option value="0">否</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>分润结算周期：</th>
				<td><input name="cycle" class="easyui-validatebox"
					data-options="required:true,validType:'money'" style="width: 151px;" readonly="readonly"/></td>
			</tr>
			<tr>
				<th>分润税点（%）：</th>
				<td><input name="cashRate" class="easyui-validatebox"
					data-options="validType:'money'" style="width: 151px;" /></td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>签约信息</legend>
		<table class="table">
			<tr>
				<th>签约销售：</th>
	    		<td>
	    			<select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="required:true,editable:false" style="width:135px;"></select><font color="red">&nbsp;*</font>
	    		</td>
	    		<th>保证金：</th>
    			<td colspan="3"><input type="text" name="riskAmount" style="width:130px;" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'" readonly="readonly"/><font color="red">&nbsp;*</font></td>
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
   				<input type="file" name="dealUpLoadFile" id="dealUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr1" width="120" height="120" /></div>
   			</td>
	   		<th>营业执照：</th>
   			<td>
   				<input type="file" name="busLicUpLoadFile" id="busLicUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr2" width="120" height="120" /></div>
   			</td>
   		</tr>
   		<tr>
	   		<th>对公开户证明：</th>
	   		<td>
	   			<input type="file" name="proofOfOpenAccountUpLoadFile" id="proofOfOpenAccountUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			<div><img id="ImgPr3" width="120" height="120" /></div>
	   		</td>
	   		
				<th>法人身份证正面：</th>
   			<td>
   				<input type="file" name="legalAUpLoadFile" id="legalAUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr4" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>法人身份证反面：</th>
   			<td>
   				<input type="file" name="legalBUpLoadFile" id="legalBUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr5" width="120" height="120" /></div>
   			</td>
				<th>入账授权书：</th>
   			<td>
   				<input type="file" name="accountAuthUpLoadFile" id="accountAuthUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr6" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人身份证正面：</th>
   			<td>
   				<input type="file" name="accountLegalAUpLoadFile" id="accountLegalAUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr7" width="120" height="120" /></div>
   			</td>
				<th>入账人身份证反面：</th>
   			<td>
   				<input type="file" name="accountLegalBUpLoadFile" id="accountLegalBUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr8" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人手持身份证：</th>
   			<td>
   				<input type="file" name="accountLegalHandUpLoadFile" id="accountLegalHandUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr9" width="120" height="120" /></div>
   			</td>
				<th>办公地点照片：</th>
   			<td>
   				<input type="file" name="officeAddressUpLoadFile" id="officeAddressUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPrA" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>分润照片：</th>
				<td>
					<input type="file" name="profitUpLoadFile" id="profitUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
					<div><img id="ImgPrB" width="120" height="120" /></div>
				</td>
			</tr>
	   </table>
	</fieldset>
	<fieldset id="returnReasonTr"  style="display: none;">
		<legend>审核信息</legend>
		<table class="table">
			<tr>
				<th>退回原因：</th>
				<td colspan="3">
					<textarea rows="6" cols="38" style="resize:none;" name="returnReason" id="returnReason" readonly="readonly"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- 创建隐藏域 用于传递文件名 --> 
	<input type="hidden" name="dealUpLoad" id="dealUpLoad">
	<input type="hidden" name="busLicUpLoad" id="busLicUpLoad">
	<input type="hidden" name="proofOfOpenAccountUpLoad" id="proofOfOpenAccountUpLoad">
	<input type="hidden" name="legalAUpLoad" id="legalAUpLoad">
	<input type="hidden" name="legalBUpLoad" id="legalBUpLoad">
	<input type="hidden" name="accountAuthUpLoad" id="accountAuthUpLoad">
	<input type="hidden" name="accountLegalAUpLoad" id="accountLegalAUpLoad">
	<input type="hidden" name="accountLegalBUpLoad" id="accountLegalBUpLoad">
	<input type="hidden" name="accountLegalHandUpLoad" id="accountLegalHandUpLoad">
	<input type="hidden" name="officeAddressUpLoad" id="officeAddressUpLoad">
	<input type="hidden" name="profitUpLoad" id="profitUpLoad">
	
	<input type="hidden" name="approveStatus" id="approveStatus">
	<input type="hidden" name="buid" />
	<input type="hidden" name="parentUnno"/>
	<!-- 创建成本数据隐藏域 -->
	<input type="hidden" name="costData" id="00412_costData"/>
	
</form>
<fieldset>
	<legend>活动成本信息</legend>
	<table class="table" id="00412_ACT_TABLE">
		<thead>
		<tr>
			<th style="text-align: center;">活动类型</th>
			<th style="text-align: center;">操作</th>
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
		<tbody id="00412_ACT_TR">
		<tr id="00412_ACT_TRebateType">
			<th style="text-align: center;">活动</th>
			<td style="text-align: center;" colspan="9"><a id="" onclick="sysAdmin_00412_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动成本</span></span></a></td>
		</tr>
		</tbody>
	</table>
</fieldset>
<form id="00412_hrtCostForm" method="post">
	<!-- 成本信息修改 -->
	
</form>
