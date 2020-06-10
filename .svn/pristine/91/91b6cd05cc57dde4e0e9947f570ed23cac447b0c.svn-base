<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String  tempname=request.getParameter("tempname"); 
	String  tempName20602 = java.net.URLDecoder.decode(tempname,"UTF-8").trim();
	String  tempNameNow20602 = java.net.URLDecoder.decode(tempname,"UTF-8").trim();
 %>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
/* 查询当月生效模板展示 */
var k=1;
var allRebateTypeNow20602 = new Array();
$(function(){
	debugger;
		var tempnameNow=$('#tempNameNow_20602').val();
		$.ajax({
			url:'${ctx}/sysAdmin/CheckUnitProfitMicro_querySytCurrentMonthTemplate.action',
			dataType:"json",
			type:"post",
			data:{tempname:tempnameNow},
			success:function(data) {
				var json=eval(data);
				for(var i=0;i<json.length;i++){
					allRebateTypeNow20602[allRebateTypeNow20602.length] = json[i].profitRule.toString();
						var html = '<tr id="tr_20602Now_2_'+k+'" >'
						+'<th style="text-align: center;">活动'+json[i].profitRule+'</th>'
						
						+'<tr id="tr_20602Now_2_'+k+'" >'
						+'<th style="text-align: center;">扫码1000以上（终端0.38）费率%</th>'
						+'<td><input type="text" name="creditBankRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].creditBankRate+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以上（终端0.38）转账费</th>'
						+'<td><input type="text" name="cashRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].cashRate+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602Now_2_'+k+'" >'
						+'<th style="text-align: center;">扫码1000以上（终端0.45）费率%</th>'
						+'<td><input type="text" name="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].scanRate+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以上（终端0.45）转账费</th>'
						+'<td><input type="text" name="profitPercent1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" "  value="'+json[i].profitPercent1+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602Now_2_'+k+'" >'
						+'<th style="text-align: center;">扫码1000以下（终端0.38）费率%</th>'
						+'<td><input type="text" name="subRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" "  value="'+json[i].subRate+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以下（终端0.38）转账费</th>'
						+'<td><input type="text" name="cashAmt" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" "  value="'+json[i].cashAmt+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602Now_2_'+k+'" >'
						+'<th style="text-align: center;">扫码1000以下（终端0.45）费率%</th>'
						+'<td><input type="text" name="scanRate1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].scanRate1+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以下（终端0.45）转账费</th>'
						+'<td><input type="text" name="cashAmt1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].cashAmt1+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602Now_2_'+k+'" >'
						+'<th style="text-align: center;">银联二维码费率%</th>'
						+'<td><input type="text" name="scanRate2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].scanRate2+'"  />%</td>'
						+'<th style="text-align: center;">银联二维码提现</th>'
						+'<td><input type="text" name="cashAmt2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].cashAmt2+'"  /></td>'
						+'</tr>';
						if(json[i].ishuabei==1){
							html +='<tr id="tr_20602Now_2_'+k+'" >'
							+'<th style="text-align: center;">花呗费率%</th>'
							+'<td><input type="text" name="huabeiRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].huabeiRate+'"  />%</td>'
							+'<th style="text-align: center;">花呗转账费</th>'
							+'<td><input type="text" name="huabeiFee" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" readonly="true" " value="'+json[i].huabeiFee+'"  /></td>'
							+'</tr>';
						}
						html += '<tr id="tr_20602Now_2_'+k+'" >'
						+'<td><input type="hidden" name="profitRule" value="'+json[i].profitRule+'" /></td>'
						+'</tr>'
						+'</tr>';
						k++;
					$("#20602TRebateTypeNow").before(html);
				}
    		}
		});
		
		//查询下月生效
		var j=1;
		var allRebateType20602 = new Array();
		var tempname=$('#tempName_20602').val();
		$.ajax({
			url:'${ctx}/sysAdmin/CheckUnitProfitMicro_querySytNextMonthTemplate.action',
			dataType:"json",
			type:"post",
			data:{tempname:tempname},
			success:function(data) {
				var json=eval(data);
				for(var i=0;i<json.length;i++){
					allRebateType20602[allRebateType20602.length] = json[i].profitRule.toString();
						var html = '<tr id="tr_20602_'+j+'" >'
						+'<th style="text-align: center;">活动'+json[i].profitRule+'</th>'
						
						+'<tr id="tr_20602_'+j+'" >'
						+'<th style="text-align: center;">扫码1000以上（终端0.38）费率%</th>'
						+'<td><input type="text" name="creditBankRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].creditBankRate+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以上（终端0.38）转账费</th>'
						+'<td><input type="text" name="cashRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].cashRate+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602_'+j+'" >'
						+'<th style="text-align: center;">扫码1000以上（终端0.45）费率%</th>'
						+'<td><input type="text" name="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].scanRate+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以上（终端0.45）转账费</th>'
						+'<td><input type="text" name="profitPercent1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+json[i].profitPercent1+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602_'+j+'" >'
						+'<th style="text-align: center;">扫码1000以下（终端0.38）费率%</th>'
						+'<td><input type="text" name="subRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+json[i].subRate+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以下（终端0.38）转账费</th>'
						+'<td><input type="text" name="cashAmt" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+json[i].cashAmt+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602_'+j+'" >'
						+'<th style="text-align: center;">扫码1000以下（终端0.45）费率%</th>'
						+'<td><input type="text" name="scanRate1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].scanRate1+'"  />%</td>'
						+'<th style="text-align: center;">扫码1000以下（终端0.45）转账费</th>'
						+'<td><input type="text" name="cashAmt1" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].cashAmt1+'"  /></td>'
						+'</tr>'
						+'<tr id="tr_20602_'+j+'" >'
						+'<th style="text-align: center;">银联二维码费率%</th>'
						+'<td><input type="text" name="scanRate2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].scanRate2+'"  />%</td>'
						+'<th style="text-align: center;">银联二维码提现</th>'
						+'<td><input type="text" name="cashAmt2" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].cashAmt2+'"  /></td>'
						+'</tr>';
						if(json[i].ishuabei==1){
							html += '<tr id="tr_20602_'+j+'" >'
							+'<th style="text-align: center;">花呗费率%</th>'
							+'<td><input type="text" name="huabeiRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].huabeiRate+'"  />%</td>'
							+'<th style="text-align: center;">花呗转账费</th>'
							+'<td><input type="text" name="huabeiFee" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].huabeiFee+'"  /></td>'
							+'</tr>';
						}
						html += '<tr id="tr_20602_'+j+'" >'
						+'<td><input type="hidden" name="profitRule" value="'+json[i].profitRule+'" /></td>'
						+'<td><input type="hidden" name="aptId" value="'+json[i].aptId+'" /></td>'
						+'<td><input type="hidden" id= "mataintype_20602_'+j+'" name="matainType" value="'+json[i].matainType+'" /></td>'
						+'</tr>'
						+'</tr>';
						j++;
					$("#20602TRebateType").before(html);
				}
    		}
		});
		
		
		
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
		rateValidator:{
			validator : function(value) {
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '费率格式不正确！' 
		}
	});
	
</script>


<div data-options="region:'north',border:false"
	style="height: 500px; padding-top: 2px;">
	<div id = "20602_tabs" class="easyui-tabs" data-options = "fit:true,border:false">
		<div id = "profitmicro_20602" title="本月生效">		
			<form id="Profitmicro20602" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
				<fieldset>
				  <table class="table">
					 <tr>
					   	<th>模版名称：</th>
					   	<td ><input type="text" name="tempName" id="tempNameNow_20602"  style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" value="<%= tempNameNow20602 %>" readonly="true"/><font color="red">&nbsp;*</font></td>
					 </tr>
				  </table>
				</fieldset>
				<input type="hidden" id="tempnameNow20602" name="Tempname" />
			</form>
			<fieldset>
				<legend>活动</legend>
				<table class="table" id="tableNow_20602">
					<tbody id="20602Now_table">
				 		<tr id="20602TRebateTypeNow">
							<td style="text-align: center;" colspan="9">
								<!-- <a id="" onclick="sysAdmin_20602_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动</span></span></a> -->
							</td>
						</tr>
				</tbody>
			   </table>
			</fieldset>
		</div>
		
		<div id = "profitmicroNext_20602" title="次月生效">		
			<form id="ProfitmicroNext20602" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
				<fieldset>
				  <table class="table">
					 <tr>
					   	<th>模版名称：</th>
					   	<td ><input type="text" name="tempName" id="tempName_20602"  style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" value="<%= tempName20602 %>" readonly="true"/><font color="red">&nbsp;*</font></td>
					 </tr>
				  </table>
				</fieldset>
				<input type="hidden" id="tempname20602" name="Tempname" />
			</form>
			<fieldset>
				<legend>活动</legend>
				<table class="table" id="table_20602">
					<tbody id="20602_table">
				 		<tr id="20602TRebateType">
							<td style="text-align: center;" colspan="9">
							</td>
						</tr>
				</tbody>
			   </table>
			</fieldset>
		</div>
	</div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="aas" style="overflow: hidden;"></table>
    </div> 
</div>
