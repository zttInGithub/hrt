<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 添加MPOS活动分润模板 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	
	$.extend($.fn.validatebox.defaults.rules,{
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
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
	
	var allRebateType20288_1 = new Array();
	function sysAdmin_20288_1_addRebate(){
		$('<div id="sysAdmin_20288_1_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加MPOS活动类型</span>',
			width: 500,
		    height:560,
		    closed: false,
		    href: '${ctx}/biz/check/20288_1_1.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		sysAdmin_20288_1_edit();
		    		$('#sysAdmin_20288_1_addDialog').dialog('destroy');
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_20288_1_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	var j = 1;
	function sysAdmin_20288_1_edit(){
		var tr_list = $("#20288_1_table").children("tr");
		var id = tr_list.length;
		var rebateType = $('#rebateType_20288_1_1').combobox('getValue');
		
		var creditBankRate = $('#creditBankRate_20288_1_1').val();
		var cashRate = $('#cashRate_20288_1_1').val();
		var ruleThreshold = $('#ruleThreshold_20288_1_1').val();
		var startAmount = $('#startAmount_20288_1_1').val();
		var scanRate = $('#scanRate_20288_1_1').val();
		var cashAmt1 = $('#cashAmt1_20288_1_1').val();
		var scanRate1 = $('#scanRate1_20288_1_1').val();
		var profitPercent1 = $('#profitPercent1_20288_1_1').val();
		var scanRate2 = $('#scanRate2_20288_1_1').val();
		var cashAmt2 = $('#cashAmt2_20288_1_1').val();
		var subRate = $('#subRate_20288_1_1').val();
		var cashAmt = $('#cashAmt_20288_1_1').val();
		var cloudQuickRate = $('#cloudQuickRate_20288_1_1').val();
		
		var huabeiRate = $("#huabeiRate_20288_1_1").val();
		var huabeiFee = $("#huabeiFee_20288_1_1").val();
		
		/* var rate = $("#rate_20288_1_1").val();
		var scanRate = $("#scanRate_20288_1_1").val();
		var cash = $("#cash_20288_1_1").val();
		var cashAmt1 = $("#cashAmt1_20288_1_1").val(); */
		var validator = $('#sysAdmin_20288_1_1_from').form('validate');
		if(!validator){
			$.messager.alert('提示', "填写活动类成本信息有误");
			return ;
		}
		if(allRebateType20288_1.indexOf(rebateType)!=-1){
			$.messager.alert('提示', "已添加该活动的成本，请勿重复添加");
			return ;
		}
		allRebateType20288_1[allRebateType20288_1.length] = rebateType;
		var html = '<tr id="tr_20288_1_'+j+'" >'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">活动'+rebateType+'</th>'
			+'</tr>'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">扫码1000以上（终端0.38）费率</th>'
			+'<td><input type="text" name="creditBankRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+creditBankRate+'"  />%</td>'
			+'<th style="text-align: center;">扫码1000以上（终端0.38）转账费</th>'
			+'<td><input type="text" name="cashRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+cashRate+'"  /></td>'
			+'</tr>'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">扫码1000以上（终端0.45）费率</th>'
			+'<td><input type="text" name="ruleThreshold" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+ruleThreshold+'"  />%</td>'
			+'<th style="text-align: center;">扫码1000以上（终端0.45）转账费</th>'
			+'<td><input type="text" name="startAmount" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+startAmount+'"  /></td>'
			+'</tr>'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">扫码1000以下（终端0.38）费率</th>'
			+'<td><input type="text" name="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+scanRate+'"  />%</td>'
			+'<th style="text-align: center;">扫码1000以下（终端0.38）转账费</th>'
			+'<td><input type="text" name="cashAmt1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+cashAmt1+'"  /></td>'
			+'</tr>'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">扫码1000以下（终端0.45）费率</th>'
			+'<td><input type="text" name="scanRate1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+scanRate1+'"  />%</td>'
			+'<th style="text-align: center;">扫码1000以下（终端0.45）转账费</th>'
			+'<td><input type="text" name="profitPercent1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+profitPercent1+'"  /></td>'
			+'</tr>'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">银联二维码费率</th>'
			+'<td><input type="text" name="scanRate2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+scanRate2+'"  />%</td>'
			+'<th style="text-align: center;">银联二维码提现</th>'
			+'<td><input type="text" name="cashAmt2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+cashAmt2+'"  /></td>'
			+'</tr>'
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">刷卡费率</th>'
			+'<td><input type="text" name="subRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+subRate+'"  />%</td>'
			+'<th style="text-align: center;">刷卡转账费</th>'
			+'<td><input type="text" name="cashAmt" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+cashAmt+'"  /></td>'
			+'</tr>'
			
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">花呗费率</th>'
			+'<td><input type="text" name="huabeiRate" style="width:100px;" class="easyui-validatebox" data-options="required:false,validType:"spaceValidator" "  value="'+huabeiRate+'"  />%</td>'
			+'<th style="text-align: center;">花呗转账费</th>'
			+'<td><input type="text" name="huabeiFee" style="width:100px;" class="easyui-validatebox" data-options="required:false,validType:"spaceValidator" "  value="'+huabeiFee+'"  /></td>'
			+'</tr>'
			
			+'<tr id="tr_20288_1_'+j+'">'
			+'<th style="text-align: center;">云闪付</th>'
			+'<td><input type="text" name="cloudQuickRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+cloudQuickRate+'"  />%</td>'
			+'</tr>'
			
			/* +'<th style="text-align: center;">扫码费率</th>'
			+'<td><input type="text" name="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " readonly="readonly" value="'+scanRate+'"  />%</td>'
			+'<th style="tex3t-align: center;">扫码转账费</th>'
			+'<td><input type="text" name="cashAmt1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " readonly="readonly" value="'+cashAmt1+'"  /></td>' */
			+'<tr id="tr_20288_1_'+j+'">'
			+'<td><input type="hidden" name="profitRule" value="'+rebateType+'" /></td>'
			+'</tr>'
			+'</tr>';
			// console.log(html);
		$("#20288_1TRebateType").before(html);
		j ++;
	}
	
</script>
<form id="profitmicro_20288_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
	  <table class="table">
		 <tr>
		   	<th>模版名称：</th>
		   	<td><input type="text" name="tempName" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" /><font color="red">&nbsp;*</font></td>
		 </tr>
	  </table>
	</fieldset>
	<input type="hidden" name="Tempname" id="Tempname_20288_1" />
</form> 
	<fieldset>
		<legend>活动</legend>
	  <table class="table">
		<tbody id="20288_1_table">
			<tr id="20288_1TRebateType">
				<td style="text-align: center;" colspan="9">
					<a id="" onclick="sysAdmin_20288_1_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动</span></span></a>
				</td>
			</tr>
		</tbody>
	  </table>
	</fieldset>
	
 
