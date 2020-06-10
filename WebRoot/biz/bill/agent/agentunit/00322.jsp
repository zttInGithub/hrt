<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		$("#hrt_cost_info").hide();
		$('#rebateType_00322').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listRebateRate.action',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]],
			// 可能存在,成本填写后,将活动类型修改
			onChange:function(n,o){
				$("#hrt_cost_info").hide();
			}
		});
	});
	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});

	function hrtCostInfoShow() {
		var g = $('#rebateType_00322').combogrid('grid');
		var r = g.datagrid('getSelected');
		if(r && r.SUBTYPE==1){
			$('#hrt_cost_rebate_subType').val(r.SUBTYPE);
			$('.hb_info_hidden').hide();
			$('.hb_info_show').show();
		}else if(r && r.SUBTYPE==2){
			$('#hrt_cost_rebate_subType').val(r.SUBTYPE);
			$('.hb_info_hidden').show();
			$('.hb_info_show').show();
		}else{
			$('.hb_info_hidden').show();
			$('.hb_info_show').hide();
		}
		$("#hrt_cost_info").show();
	}
</script>
</head>
<form id="sysAdmin_00322_from" style="padding-left:2%;padding-top:1%" method="post">
	<fieldset style="width: 350px;">
		<legend>活动类型成本</legend>
		<table class="table">
			<tr>
				<th>活动类型</th>
				<td><select id="rebateType_00322" name="rebateType" class="easyui-combogrid" data-options="required:true,editable:false,validType:'spaceValidator'" style="width:135px;"></select></td>
			</tr>
			<tr>
				<th>操作</th>
				<td><input type="button" value="成本信息设置" onclick="hrtCostInfoShow()"></td>
			</tr>
		</table>
	</fieldset>

	<fieldset style="width: 350px;" id="hrt_cost_info">
		<legend>活动类型成本信息</legend>
		<table class="table">
	    	<tr>
	    		<th>扫码1000以下（终端0.38）费率(%)：</th>
	    		<td><input type="text" id="rate_00322" name="rate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>
	    	<tr>
	    		<th>扫码1000以下（终端0.38）转账费：</th>
	    		<td><input type="text" id="cash_00322" name="cash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
	    	</tr>

			<tr>
				<th>扫码1000以上（终端0.38）费率(%)：</th>
				<td><input type="text" id="wxUpRate_00322" name="wxUpRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>扫码1000以上（终端0.38）转账费：</th>
				<td><input type="text" id="wxUpCash_00322" name="wxUpCash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr>
				<th>扫码1000以上（终端0.45）费率(%)：</th>
				<td><input type="text" id="wxUpRate1_00322" name="wxUpRate1" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>扫码1000以上（终端0.45）转账费：</th>
				<td><input type="text" id="wxUpCash1_00322" name="wxUpCash1" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr>
				<th>扫码1000以下（终端0.45）费率(%)：</th>
				<td><input type="text" id="zfbRate_00322" name="zfbRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>扫码1000以下（终端0.45）转账费：</th>
				<td><input type="text" id="zfbCash_00322" name="zfbCash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr>
				<th>银联二维码费率(%)：</th>
				<td><input type="text" id="ewmRate_00322" name="ewmRate" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/></td>
			</tr>
			<tr>
				<th>银联二维码转账费：</th>
				<td><input type="text" id="ewmCash_00322" name="ewmCash" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>

			<tr class="hb_info_hidden">
				<th>云闪付费率(%)：</th>
				<td><input type="text" id="ysfRate_00322" name="ysfRate" style="width:130px;" /></td>
			</tr>

			<tr class="hb_info_hidden">
				<th>刷卡成本(%)：</th>
				<td><input type="text" id="debitRate_00322" name="debitRate" style="width:130px;" /></td>
			</tr>
			<tr class="hb_info_hidden">
				<th>刷卡提现成本：</th>
				<td><input type="text" id="debitFeeamt_00322" name="debitFeeamt" style="width:130px;"/></td>
			</tr>

			<tr class="hb_info_show">
				<th>花呗成本(%)：</th>
				<td><input type="text" id="hbRate_00322" name="hbRate" style="width:130px;" /></td>
			</tr>
			<tr class="hb_info_show">
				<th>花呗提现成本：</th>
				<td><input type="text" id="hbCash_00322" name="hbCash" style="width:130px;" /></td>
			</tr>
		</table>
	</fieldset>
</form>
<input type="hidden" id="hrt_cost_rebate_subType" />