<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
 %>
<script type="text/javascript">
	$(function(){
		$("#sysAdmin_imgDialog").hide();
		var bmtkid1=$("#bmtkid1").val();
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail2.action',
			dataType:"json",
			data:{bmtkid:bmtkid1},
			success:function(data) {
				var json=eval(data);
    			if (json!="") {
    				var path='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
    				var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
    				var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
    				var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
	    			$("#bankBranch").val(json[0].bankBranch);
	    			if(json[0].accType==1){
	    				$("#accType").val("对公");
	    			}else{
	    				$("#accType").val("对私"); 
	    			}
	    			$("#bankAccNo").val(json[0].bankAccNo);
	    			$("#bankAccName").val(json[0].bankAccName);
	    			$("#settleCycle").val(json[0].settleCycle);
	    			$("#areaType").val(json[0].areaType);
	    			$("#bankType").val(json[0].bankType);
	    			$("#payBankId").val(json[0].payBankId);
	    			$("#accUpLoad").attr("src",path);
	    			$("#authUpLoad").attr("src",path2);
	    			$("#DUpLoad").attr("src",path3);
	    			$("#openUpLoad").attr("src",path4);
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
<form id="merchantTaskDetail2"  method="post" enctype="multipart/form-data">
			<fieldset style="width: 800px;">
				<legend>商户银行信息明细</legend> 
				<table class="table1">
					<tr> 
						<th>对公对私：</th>
						<td colspan="3"><input id="accType"  readonly="readonly"  name="merchantTaskDetail2.accType" style="width: 156px;"/>
						</td>
					</tr>
					<tr> 
						<th>开户银行：</th>
						<td><input id="bankBranch"  readonly="readonly"  name="merchantTaskDetail2.bankBranch" style="width: 156px;"
							 />
						</td>
						<th>开户银行账号：</th>
						<td><input id="bankAccNo"  readonly="readonly"  name="merchantTaskDetail2.bankAccNo" style="width: 156px;"
							/></td>
					</tr>
					<tr>
						<th>开户账号名称：</th>
						<td><input  readonly="readonly"  id="bankAccName"  name="merchantTaskDetail2.bankAccName" style="width: 150px;" ></td>
						<th>结算周期：</th>
						<td><input id="settleCycle" name="merchantTaskDetail2.settleCycle"  readonly="readonly"  style="width: 156px;"/></td>
					</tr>
					<tr>
						<th>开户银行所在地：</th>
						<td><select id="areaType" name="aa">
								<option value="1" >北京</option>
								<option value="2">非北京</option>
							</select>
						</td>
						<th>开户银行是否为交通银行：</th> 
						<td><select id="bankType" name="bb">
								<option value="1" >交通银行</option>
								<option value="2">非交通银行</option>
							</select>
						</td>
					</tr>  
					<tr>
				   		<th>支付系统行号：</th>
				   		<td colspan="3">
				   		<input type="text" id="payBankId" readonly="readonly"  style="width:200px;"  maxlength="20"/>
				   		</td>
					</tr>
					<tr>
						<th>入账人身份证正反面加盖公章(开户证明上传文件名)：</th>
						<td> 
							<img id="accUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
						<th>入账法人/非法人授权书加盖公章：</th>
						<td> 
							<img id="authUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
						
					</tr>  
					<tr>
						<th>入账卡正反面加盖公章：</th>
						<td> 
							<img id="DUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
						<th>开户许可证加盖公章/一般账户证明加盖公章：</th>
						<td> 
							<img id="openUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
					</tr>  
				</table>
			</fieldset>
			<input type="hidden" name="bmtkid" id="bmtkid1" value="<%=bmtkid %>"> 
		</form> 
<div id="sysAdmin_imgDialog" style="padding:10px;">
	<img id="img" alt="" src="">
</div>
