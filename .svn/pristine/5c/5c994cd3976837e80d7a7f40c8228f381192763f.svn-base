<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
 %>
 <script type="text/javascript">
	$(function(){
	$("#sysAdmin_imgDialog").hide();
		var bmtkid1=$("#bmtkid1").val();
		$.ajax({
	   		url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail3.action',
	   		data:{bmtkid:bmtkid1},
			success:function(data) {
			var json=eval(data); 
    				if (json!="") { 
    					var path='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
	    			$("#bankFeeRate").val(parseFloat((json[0].bankFeeRate*100).toFixed(4))); 
	    			$("#creditBankRate").val(parseFloat((json[0].creditBankRate*100).toFixed(4))); 
	    			$("#scanRate").val(parseFloat((json[0].scanRate*100).toFixed(4))); 
	    			$("#scanRate1").val(parseFloat((json[0].scanRate1*100).toFixed(4)));
	    			$("#scanRate2").val(parseFloat((json[0].scanRate2*100).toFixed(4)));
	    			$("#feeAmt").val(parseFloat((json[0].feeAmt).toFixed(4)));
	    			var bankFeeRate = json[0].bankFeeRate;
					var dealAmt = json[0].feeAmt / bankFeeRate;
	    			//$("#maxAmt").val(parseFloat(((json[0].dealAmt*100)*(json[0].bankFeeRate)).toFixed(4)));
	    			$("#maxAmt").val(Math.floor(dealAmt));
	    			if(json[0].secondRate!=null&&json[0].secondRate!=''){
	    				$("#secondRate").val(parseFloat((json[0].secondRate).toFixed(4)));
	    			}else{
	    				$("#secondRate1").hide();
	    			}
	    			if(json[0].scanRate1<=0){
	    				$("#scanRate_wx").html("扫码费率：");
	    				$("#scanRate_ct").hide();
	    			}
	    			$("#isForeign").val(json[0].isForeign);
	    			$("#feeUpLoad").attr("src",path);
	    			if($("#isForeign").val()=="1"){
	    				$("#feeRateV").val(parseFloat((json[0].feeRateV*100).toFixed(4)));
		    			$("#feeRateM").val(parseFloat((json[0].feeRateM*100).toFixed(4)));
		    			$("#feeRateD").val(parseFloat((json[0].feeRateD*100).toFixed(4)));
		    			$("#feeRateJ").val(parseFloat((json[0].feeRateJ*100).toFixed(4)));
		    			$("#feeRateA").val(parseFloat((json[0].feeRateA*100).toFixed(4)));
	    				$("#isHide1").show();
	    				$("#isHide2").show();
	    				$("#isHide3").show();
		    			} else{
		    				$("#isHide1").hide();
		    				$("#isHide2").hide();
		    				$("#isHide3").hide();
			    		}

	    		}
    		}
});
		});

	function showBigImg(img){
	$("#sysAdmin_imgDialog").show();
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 1000,
			    height: 600,
			     resizable:true,
		    	maximizable:true,
			    modal:false,
				onClose:function() {
				$("#sysAdmin_imgDialog").hide();
				}
			});
	}

</script>

		<form id="merchantTaskDetail3"  method="post" enctype="multipart/form-data">
			<fieldset style="width: 800px;">
				<legend>商户费率信息明细</legend>
				<table class="table1">
					<tr>
						<th>银联卡费率：</th>
						<td><input id="bankFeeRate" readonly="readonly" name="bankFeeRate" style="width: 150px;"/>%
						</td>
						<th>封顶手续费：</th>
						<td><input readonly="readonly" id="feeAmt" name=feeAmt"	style="width: 150px;" ></td>
					</tr>
					<tr>
						<th>是否开通外卡：</th>
						<td>
							<select  id="isForeign" name="isForeign" style="width: 156px;">
							<option value="1" selected="selected">是</option>
							<option value="2">否</option>
							</select>
						</td>
						<!-- <th>封顶值：</th>
						<td><input readonly="readonly" id="maxAmt" name="nono" 	style="width: 150px;" ></td> -->
					</tr>
					<tr >
						<th>贷记卡费率：</th>
						<td><input type="text" id="creditBankRate" name="creditBankRate"	style="width: 150px;" >%</td>
					</tr>
					<tr >
						<th id="scanRate_wx">微信费率：</th>
						<td><input type="text" id="scanRate" name="scanRate"	style="width: 150px;" >%</td>
					</tr>
					<tr id="scanRate_ct">
						<th>银联二维码费率：</th>
						<td><input type="text" id="scanRate1" name="scanRate1"	style="width: 150px;" >%</td>
						<th>支付宝费率：</th>
						<td><input type="text" id="scanRate2" name="scanRate2"	style="width: 150px;" >%</td>
					</tr>
					<tr id="secondRate1">
						<th>秒到手续费：</th>
						<td><input type="text" id="secondRate" name="secondRate"	style="width: 150px;" ></td>
					</tr>
					<tr id="isHide3">
						<th>大莱费率：</th>
						<td><input type="text" id="feeRateD" name="feeRateD"	style="width: 150px;" >%</td>
					</tr>
					<tr id="isHide1">
						<th>VISA费率：</th>
						<td><input type="text" id="feeRateV" name="feeRateV"	style="width: 150px;" >%<font color="red">&nbsp;*</font></td>
						<th>MASTER费率：</th>
						<td><input type="text" id="feeRateM" name="feeRateM"	style="width: 150px;" >%<font color="red">&nbsp;*</font></td>
					</tr>
					<TR id="isHide2">
						<th>JCB费率：</th>
						<td><input type="text" id="feeRateJ" name="feeRateJ"	style="width: 150px;" >%</td>
						<th>美运费率：</th>
						<td><input type="text" id="feeRateA" name="feeRateA"	style="width: 150px;" >%</td>
					</TR>
					<tr>
							<th>开户证明上传图片预览:</th>
						<td>
							<img id="feeUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
						 
					</tr>  
				</table>
					<input type="hidden" name="bmtkid" id="bmtkid1" value="<%=bmtkid %>"> 
			</fieldset> 
		</form>
<div id="sysAdmin_imgDialog" style="padding:10px;">
	<img id="img" alt="" src=""> 
</div>