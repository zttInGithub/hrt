<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String  tempname=request.getParameter("tempname"); 
	String  tempName20288 = java.net.URLDecoder.decode(tempname,"UTF-8").trim();
	String  startDate = request.getParameter("startDate");
	String  endDate = request.getParameter("endDate");
	String  unno = request.getParameter("unno");
 %>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 

/* 查询当月生效模板展示 */
$(function(){
	//debugger;
	var j=1;
	var allRebateTypeMposTemplateLog = new Array();
	var tempname=$('#tempName_MposTemplateLog').val();
	$.ajax({
		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_QUERYMposprofitmicroLogDetail.action',
		dataType:"json",
		type:"post",
		data:{"unno":'<%=unno%>',"txnDay":'<%=startDate%>',"txnDay1":'<%=endDate%>',"tempName":tempname},
		success:function(data) {
			var json=eval(data);
			for(var i=0;i<json.length;i++){
				allRebateTypeMposTemplateLog[allRebateTypeMposTemplateLog.length] = json[i].profitRule.toString();
					var html = '<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">活动'+json[i].profitRule+'</th>'
					
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">扫码1000以上（终端0.38）费率%</th>'
					+'<td><input type="text" name="creditBankRate" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].creditBankRate+'"  />%</td>'
					+'<th style="text-align: center;">扫码1000以上（终端0.38）转账费</th>'
					+'<td><input type="text" name="cashRate" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].cashRate+'"  /></td>'
					+'</tr>'
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">扫码1000以上（终端0.45）费率%</th>'
					+'<td><input type="text" name="ruleThreshold" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].ruleThreshold+'"  />%</td>'
					+'<th style="text-align: center;">扫码1000以上（终端0.45）转账费</th>'
					+'<td><input type="text" name="startAmount" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+json[i].startAmount+'"  /></td>'
					+'</tr>'
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">扫码1000以下（终端0.38）费率%</th>'
					+'<td><input type="text" name="scanRate" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+json[i].scanRate+'"  />%</td>'
					+'<th style="text-align: center;">扫码1000以下（终端0.38）转账费</th>'
					+'<td><input type="text" name="cashAmt1" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" "  value="'+json[i].cashAmt1+'"  /></td>'
					+'</tr>'
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">扫码1000以下（终端0.45）费率%</th>'
					+'<td><input type="text" name="scanRate1" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].scanRate1+'"  />%</td>'
					+'<th style="text-align: center;">扫码1000以下（终端0.45）转账费</th>'
					+'<td><input type="text" name="profitPercent1" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].profitPercent1+'"  /></td>'
					+'</tr>'
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">银联二维码费率%</th>'
					+'<td><input type="text" name="scanRate2" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].scanRate2+'"  />%</td>'
					+'<th style="text-align: center;">银联二维码转账费</th>'
					+'<td><input type="text" name="cashAmt2" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].cashAmt2+'"  /></td>'
					+'</tr>'
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">刷卡费率%</th>'
					+'<td><input type="text" name="subRate" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].subRate+'"  />%</td>'
					+'<th style="text-align: center;">刷卡转账费</th>'
					+'<td><input type="text" name="cashAmt" style="width:100px;" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" " value="'+json[i].cashAmt+'"  /></td>'
					+'</tr>';
					
					if(json[i].ishuabei==1){
						html += '<tr id="tr_MposTemplateLog_'+j+'" >'
						+'<th style="text-align: center;">花呗费率%</th>'
						+'<td><input type="text" name="huabeiRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" disabled="disabled" " value="'+json[i].huabeiRate+'"  />%</td>'
						+'<th style="text-align: center;">花呗转账费</th>'
						+'<td><input type="text" name="huabeiFee" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"spaceValidator" disabled="disabled" " value="'+json[i].huabeiFee+'"  /></td>'
						+'</tr>';
					}
					
					
					html += '<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<th style="text-align: center;">云闪付</th>'
					+'<td><input type="text" name="cloudQuickRate" style="width:100px;" disabled="disabled" class="easyui-validatebox" value="'+json[i].cloudQuickRate+'" />%</td>'
					+'</tr>'
					+'<tr id="tr_MposTemplateLog_'+j+'" >'
					+'<td><input type="hidden" name="profitRule" value="'+json[i].profitRule+'" /></td>'
					+'<td><input type="hidden" name="aptId" value="'+json[i].aptId+'" /></td>'
					+'<td><input type="hidden" id= "mataintype_MposTemplateLog_'+j+'" name="matainType" value="'+json[i].matainType+'" /></td>'
					+'</tr>'
					+'</tr>';
					j++;
				$("#MposTemplateLogTRebateType").before(html);
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
<div data-options="region:'north',border:false"
	style="height: 500px; padding-top: 2px;">
	<div id = "MposTemplateLog_tabs" title="模板成本详情" class="easyui-tabs" >
		<form id="ProfitmicroNextMposTemplateLog" style="padding-left:1%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset>
			  <table class="table">
				 <tr>
				   	<th>模版名称：</th>
				   	<td ><input type="text" name="tempName" id="tempName_MposTemplateLog"  style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" value="<%= tempName20288 %>" readonly="true"/><font color="red">&nbsp;*</font></td>
				 </tr>
			  </table>
			</fieldset>
			<input type="hidden" id="tempnameMposTemplateLog" name="Tempname" />
		</form>
		<fieldset>
			<legend>活动</legend>
			<table class="table" id="table_MposTemplateLog">
				<tbody id="MposTemplateLog_table">
			 		<tr id="MposTemplateLogTRebateType">
						<td style="text-align: center;" colspan="9">
							<!-- <a id="" onclick="sysAdmin_MposTemplateLog_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动</span></span></a> -->
						</td>
					</tr>
			</tbody>
		   </table>
		</fieldset>
	</div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="aas" style="overflow: hidden;"></table>
    </div> 
</div>

