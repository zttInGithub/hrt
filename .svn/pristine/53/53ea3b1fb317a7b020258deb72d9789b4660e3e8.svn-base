<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		var hbRate=<%=request.getParameter("hbRate")%>;
		var ysfRate=<%=request.getParameter("ysfRate")%>;
		if(hbRate && !ysfRate){
			$('.hb_hidden1').hide();
			$('.hb_show1').show();
		}else if(hbRate && ysfRate){
			$('.hb_hidden1').show();
			$('.hb_show1').show();
		}else{
			$('.hb_hidden1').show();
			$('.hb_show1').hide();
		}
		/*$('#rebateType_00325').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listRebateRate.action',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]]
		});*/
		$('#unno_00325').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listRebateRateForUnno.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			editable:false,
			columns:[[ 
				{field:'UNNO',title:'机构号',width:70},
				{field:'UN_NAME',title:'机构名称',width:70}
			]]
		});
	});
</script>
</head>
<form id="sysAdmin_00325_from" style="padding-left:2%;padding-top:1%" method="post">
	<fieldset style="width: 350px;">
		<legend>新增活动类型成本</legend>
		<table class="table">
			<tr>
				<th>机构</th>
				<td><select id="unno_00325" name="unno" class="easyui-combogrid" style="width:135px;"></select></td>
			</tr>
			<tr>
				<th>活动类型</th>
				<td><input type="text" name="txnDetail" readonly style="width:135px;"></input></td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="width: 300px;">
		<legend>活动成本信息</legend>
		<table class="table">
	    	<tr>
	    		<th>扫码1000以下（终端0.38）费率(%)：</th>
	    		<td><input type="text" id="rate_00325" name="rate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	<tr>
	    		<th>扫码1000以下（终端0.38）转账费：</th>
	    		<td><input type="text" id="cashRate_00325" name="cashRate" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
	    	</tr>


			<tr>
				<th>扫码1000以上（终端0.38）费率(%)：</th>
				<td><input type="text" id="wxUpRate_00325" name="wxUpRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>扫码1000以上（终端0.38）转账费：</th>
				<td><input type="text" id="wxUpCash_00325" name="wxUpCash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr>
				<th>扫码1000以上（终端0.45）费率(%)：</th>
				<td><input type="text" id="wxUpRate1_00325" name="wxUpRate1" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>扫码1000以上（终端0.45）转账费：</th>
				<td><input type="text" id="wxUpCash1_00325" name="wxUpCash1" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr>
				<th>扫码1000以下（终端0.45）费率(%)：</th>
				<td><input type="text" id="zfbRate_00325" name="zfbRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>扫码1000以下（终端0.45）转账费：</th>
				<td><input type="text" id="zfbCash_00325" name="zfbCash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr>
				<th>银联二维码费率(%)：</th>
				<td><input type="text" id="ewmRate_00325" name="ewmRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>银联二维码转账费：</th>
				<td><input type="text" id="ewmCash_00325" name="ewmCash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr class="hb_hidden1">
				<th>云闪付费率(%)：</th>
				<td><input type="text" id="ysfRate_00325" name="ysfRate" style="width:130px;"/></td>
			</tr>
			
			<tr class="hb_hidden1">
				<th>刷卡成本(%)：</th>
				<td><input type="text" id="remarks_00325" name="remarks" style="width:130px;" /></td>
			</tr>
			<tr class="hb_hidden1">
				<th>刷卡提现成本：</th>
				<td><input type="text" id="curAmt_00325" name="curAmt" style="width:130px;" /></td>
			</tr>
			<tr class="hb_show1">
				<th>花呗费率(%)：</th>
				<td><input type="text" id="hbRate_00325" name="hbRate" style="width:130px;" /></td>
			</tr>
			<tr class="hb_show1">
				<th>花呗转账费：</th>
				<td><input type="text" id="hbCash_00325" name="hbCash" style="width:130px;"/></td>
			</tr>
			<input type="hidden" name='hucid' id="hucid_00325"/>
		</table>
	</fieldset>
</form>
