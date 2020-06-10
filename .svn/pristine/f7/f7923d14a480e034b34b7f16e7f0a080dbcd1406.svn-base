<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
var specialRate1;
var specialRate2;
var specialAmt1;
var specialAmt2;
var SCANSPECIALRATE1;
var SCANSPECIALRATE2;
var SCANSPECIALRATE3;
var SCANSPECIALRATE4;
var HUABEIRATE1;
var HUABEIRATE2;
//debugger;
$(function(){
	var limitunno = '<%=request.getParameter("limitunno") %>'
	var limitrebatetype = <%=request.getParameter("limitrebatetype") %>
	
	$.ajax({
		url:'${ctx}/sysAdmin/terminalInfo_querySpecialRateDetail.action?limitunno='+limitunno+'&limitrebatetype='+limitrebatetype,
		type:"post",
		async:false,
		//data:{"limitunno":limitunno,"limitrebatetype":limitrebatetype},
		dataType:'json',
		success:function(data){
			if(data){
				if(data.ISDEPOSITAMT != 1){
					$(".is_specialUnno_desposit").remove();
				}else{
					$('#depositAmt').combogrid({
						url : '${ctx}/sysAdmin/terminalInfo_querySpecialDepoist.action?limitunno='+limitunno+'&limitrebatetype='+limitrebatetype,
						idField:'depositAmt',
						textField:'depositAmt',
						mode:'remote',
						fitColumns:true,  
						columns:[[ 
							{field:'depositAmt',title:'押金金额',width:150},
						]] 
					}); 
				}
				if(data.ISHUABEI != 1){
					$(".is_specialUnno_huabei").remove();
				}else{
					if(data.HUABEIRATE1){
			    		HUABEIRATE1 = data.HUABEIRATE1;
			    	}
			    	if(data.HUABEIRATE2){
			    		HUABEIRATE2 = data.HUABEIRATE2;
			    	}
				}
				if(data.RATETYPE != null){
					if(data.RATETYPE == 1){
						$(".is_specialUnno_scan").remove();
						if(data.SPECIALRATE1 != null){
				    		specialRate1 = data.SPECIALRATE1;
				    	}
				    	if(data.SPECIALRATE2 != null){
				    		specialRate2 = data.SPECIALRATE2;
				    	}
				    	if(data.SPECIALAMT1 != null){
				    		specialAmt1 = data.SPECIALAMT1;
				    	}
				    	if(data.SPECIALAMT2 != null){
				    		specialAmt2 = data.SPECIALAMT2;
				    	}
					}else if(data.RATETYPE == 2){
						$(".is_specialUnno").remove();
						if(data.SCANSPECIALRATE1){
				    		SCANSPECIALRATE1 = data.SCANSPECIALRATE1;
				    	}
				    	if(data.SCANSPECIALRATE2){
				    		SCANSPECIALRATE2 = data.SCANSPECIALRATE2;
				    	}
				    	if(data.SCANSPECIALRATE3){
				    		SCANSPECIALRATE3 = data.SCANSPECIALRATE3;
				    	}
				    	if(data.SCANSPECIALRATE4){
				    		SCANSPECIALRATE4 = data.SCANSPECIALRATE4;
				    	}
					}else{
						if(data.SPECIALRATE1 != null){
				    		specialRate1 = data.SPECIALRATE1;
				    	}
				    	if(data.SPECIALRATE2 != null){
				    		specialRate2 = data.SPECIALRATE2;
				    	}
				    	if(data.SPECIALAMT1 != null){
				    		specialAmt1 = data.SPECIALAMT1;
				    	}
				    	if(data.SPECIALAMT2 != null){
				    		specialAmt2 = data.SPECIALAMT2;
				    	}
				    	if(data.SCANSPECIALRATE1){
				    		SCANSPECIALRATE1 = data.SCANSPECIALRATE1;
				    	}
				    	if(data.SCANSPECIALRATE2){
				    		SCANSPECIALRATE2 = data.SCANSPECIALRATE2;
				    	}
				    	if(data.SCANSPECIALRATE3){
				    		SCANSPECIALRATE3 = data.SCANSPECIALRATE3;
				    	}
				    	if(data.SCANSPECIALRATE4){
				    		SCANSPECIALRATE4 = data.SCANSPECIALRATE4;
				    	}
					}
					
				}else{
					$(".is_specialUnno_scan").remove();
					$(".is_specialUnno").remove();
				}
		    	
			}
			
			$("#ishuabei").val(data.ISHUABEI);
			$("#isdepositamt").val(data.ISDEPOSITAMT);
			$("#ratetype").val(data.RATETYPE);
			
		}
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		selfrateValidator: {
			validator: function (value) {
				if(/^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value)){
					if(value< specialRate1 || value>specialRate2){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			},
			message: "请正确输入费率为"+specialRate1+"%-"+specialRate2+"%！"
		} ,
		range:{
			validator:function(value){
				if(/^[0-9]\d*$/.test(value)){
					return value >= specialAmt1 && value <= specialAmt2
				}else{
					return false;
				}
			},
			message:"请正确输入的手续费在"+specialAmt1+"-"+specialAmt2+"！"
		},
		upScanrateValidator: {
			validator: function (value) {
				if(/^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value)){
					if(value< SCANSPECIALRATE1 || value>SCANSPECIALRATE2){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			},
			message: "请正确输入费率为"+SCANSPECIALRATE1+"%-"+SCANSPECIALRATE2+"%！"
		},
		downScanrateValidator: {
			validator: function (value) {
				if(/^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value)){
					if(value< SCANSPECIALRATE3 || value>SCANSPECIALRATE4){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			},
			message: "请正确输入费率为"+SCANSPECIALRATE3+"%-"+SCANSPECIALRATE4+"%！"
		},
		huabeiScanrateValidator: {
			validator: function (value) {
				if(/^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value)){
					if(value< HUABEIRATE1 || value > HUABEIRATE2){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			},
			message: "请正确输入费率为"+HUABEIRATE1+"%-"+HUABEIRATE2+"%！"
		}
	});
})



</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="sysAdmin_editRate_editForm" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
        	<tr class="is_specialUnno">
          		<th style="width:100px;">刷卡费率：</th>
          		<td style="width:270px;">
            		<input type="text"  name="termIDStart" id="termIDStart" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'selfrateValidator',required:true"/><font color="red">&nbsp;%*</font>
          		</td>
        	</tr>
			<tr><td>&nbsp;</td></tr>
			<tr class="is_specialUnno_scan">
				<th style="width:160px;">扫码1000以上费率：</th>
  				<td >
  					<input type="text" name="scanRateUp" id="scanRateUp" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'upScanrateValidator',required:true"/><font color="red">&nbsp;%*</font>
  				</td>
			</tr> 
			<tr class="is_specialUnno_scan">
				<th style="width:160px;">扫码1000以下费率：</th>
  				<td >
  					<input type="text" name="scanRate" id="scanRate" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'downScanrateValidator',required:true"/><font color="red">&nbsp;%*</font>
  				</td>
			</tr>
			<tr class="is_specialUnno_huabei">
				<th style="width:120px;">花呗费率：</th>
  				<td >
  					<input type="text" name="huabeiRate" id="huabeiRate" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'huabeiScanrateValidator',required:true"/><font color="red">&nbsp;%*</font>
  				</td>
			</tr> 
			<tr><td>&nbsp;</td></tr>
        	<tr class="is_specialUnno">
	            <th style="width:100px;">手续费：</th>
	            <td style="width:270px;">
	            	<input type="text" name="termIDEnd" id="termIDEnd" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'range[0,3]',required:true"/><font color="red">&nbsp;*</font>
	            </td>
        	</tr>
			<tr><td>&nbsp;</td></tr>
        	<tr class="is_specialUnno_desposit">
	            <th style="width:100px;">押金：</th>
	            <td style="width:270px;">
	            	<select id="depositAmt" name="depositAmt"  data-options="editable:false" style="width: 100px;" required="required">
					</select><font color="red">&nbsp;*</font>
	            </td>
        	</tr>
        	<tr>
				<td><input id="isdepositamt" name="isdepositamt" style="width: 156px;"  type="hidden"/></td>
				<td><input id="ishuabei" name="ishuabei" style="width: 156px;"  type="hidden"/></td> 
				<td><input id="ratetype" name="ratetype" style="width: 156px;"  type="hidden"/></td> 
			</tr>
			</table>
		</form>
</div>


