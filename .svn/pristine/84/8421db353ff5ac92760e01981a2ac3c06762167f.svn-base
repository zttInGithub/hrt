<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
	$(function(){
		var mid=<%=request.getParameter("mid")%>;
		$.ajax({
			url:'${ctx}/sysAdmin/merchant_serachMerchantInfoMid.action',
	   		dataType:"json",  
	   		type:"post",
	   		data:{mid:mid},
  			success:function(data) {
  				var json=eval(data);
  				$('#parentMIDName').val(json.rows[0].parentMIDName);
  				$('#rname').val(json.rows[0].rname);
  				$('#shortName').val(json.rows[0].shortName);
  				$('#printName').val(json.rows[0].printName);
  				$('#areaCodeName').val(json.rows[0].areaCodeName);
  				$('#businessScope').val(json.rows[0].businessScope);
  				$('#email').val(json.rows[0].email);
  				$('#busidName').val(json.rows[0].busidName);
  				$('#maintainUserIdName').val(json.rows[0].maintainUserIdName);
  				$('#legalPerson').val(json.rows[0].legalPerson);
  				var abc = '';
  				if(json.rows[0].legalNum != null && '' != json.rows[0].legalNum){
  					abc = json.rows[0].legalNum.substring(0,4)+'****'+json.rows[0].legalNum.substring(json.rows[0].legalNum.length-4,json.rows[0].legalNum.length);
  				}
  				$('#legalNum').val(abc);
  				$('#legalTypeName').val(json.rows[0].legalTypeName);
  				$('#bno').val(json.rows[0].bno);
  				$('#regMoney').val(json.rows[0].regMoney);
  				$('#baddr').val(json.rows[0].baddr);
  				$('#raddr').val(json.rows[0].raddr);
  				$('#rno').val(json.rows[0].rno);
  				$('#registryNo').val(json.rows[0].registryNo);
  				$('#materialNo').val(json.rows[0].materialNo);
  				$('#contractNo').val(json.rows[0].contractNo);
  				$('#contractPeriod').val(json.rows[0].contractPeriod);
  				$('#deposit').val(json.rows[0].deposit);
  				$('#charge').val(json.rows[0].charge);
  				$('#industryTypeName').val(json.rows[0].industryTypeName);
  				$('#mccidName').val(json.rows[0].mccidName);
  				$('#settleCycle').val(json.rows[0].settleCycle);
  				$('#settleRange').val(json.rows[0].settleRange);
  				$('#payBankId').val(json.rows[0].payBankId);
  				$('#settleMergerName').val(json.rows[0].settleMergerName);
  				$('#merchantTypeName').val(json.rows[0].merchantTypeName);
  				$('#bankFeeRate').val(json.rows[0].bankFeeRate);
  				$('#feeAmt').val(json.rows[0].feeAmt);
  				$('#dealAmt').val(json.rows[0].dealAmt);
  				$('#accTypeName').val(json.rows[0].accTypeName);
  				$('#bankBranch').val(json.rows[0].bankBranch);
  				$('#bankAccNo').val(json.rows[0].bankAccNo);
  				$('#bankAccName').val(json.rows[0].bankAccName);
  				$('#feeRateV').val(json.rows[0].feeRateV);
  				$('#feeRateM').val(json.rows[0].feeRateM);
  				$('#feeRateJ').val(json.rows[0].feeRateJ);
  				$('#feeRateA').val(json.rows[0].feeRateA);
  				$('#feeRateD').val(json.rows[0].feeRateD);
  				$('#contactPerson').val(json.rows[0].contactPerson);
  				$('#contactAddress').val(json.rows[0].contactAddress);
  				var bcd = '';
  				if(json.rows[0].contactPhone != null && '' != json.rows[0].contactPhone){
  					bcd = json.rows[0].contactPhone.substring(0,3)+'****'+json.rows[0].contactPhone.substring(json.rows[0].contactPhone.length-4,json.rows[0].contactPhone.length);
  				}
  				$('#contactPhone').val(bcd);
  				var cde = '';
  				if(json.rows[0].contactTel != null && '' != json.rows[0].contactTel){
  					cde = json.rows[0].contactTel.substring(0,3)+'****'+json.rows[0].contactTel.substring(json.rows[0].contactTel.length-4,json.rows[0].contactTel.length);
  				}
  				$('#contactTel').val(cde);
  				$('#processContext').val(json.rows[0].processContext);
  				
  				
  				$.ajax({
					url:'${ctx}/sysAdmin/merchant_serachMerchantInfoDetailed.action',
		    		dataType:"json",  
		    		type:"post",
		    		data:{bmid:json.rows[0].bmid},
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
				    		var pathC='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[11]);
			    			$("#legalUploadFile").attr("src",path1);
		    				$("#bupLoadFile").attr("src",path2);
		    				$("#rupLoadFile").attr("src",path3);
		    				$("#registryUpLoadFile").attr("src",path4);
		    				$("#materialUpLoadFile").attr("src",path5);
		    				$("#photoUpLoadFile").attr("src",path6);
		    				$("#bigdealUpLoadFile").attr("src",path7);
		    				$("#materialUpLoad1File").attr("src",path8);
		    				$("#materialUpLoad2File").attr("src",path9);
		    				$("#materialUpLoad3File").attr("src",pathA);
		    				$("#materialUpLoad4File").attr("src",pathB);
		    				$("#materialUpLoad5File").attr("src",pathC);
		    			}  
			    	}
			    });
    		} 
		});
	});
	
<%-- 	$(function(){
		var bmid=<%=request.getParameter("bmid")%>;
		
	    	
		});
	}); --%>

	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_merchantList_queryForm').after(imgDialog);
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 800,   
			    height: 500,
			    resizable:true,
		    	maximizable:true, 
			    modal:false,
			    buttons:[{
					text:'顺时针',
					iconCls:'',
					handler:function() {
						rotateRight();
					}
				},{
					text:'逆时针',
					iconCls:'',
					handler:function() {
						rotateLeft();
					}
				}],
				onClose:function() {
					$('#sysAdmin_imgDialog').remove();
					rotation=0;
					rotate=0;
				}
			});
	}
	
	var rotation=0;
	var rotate=0;   
	function rotateRight(){   
		rotation=(rotation==3)?0:++rotation;
		rotate=(rotate==270)?0:rotate+90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";   
	}
	
	function rotateLeft(){
		rotation=(rotation==0)?3:--rotation;
		rotate=(rotate==0)?270:rotate-90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";
	}
	
</script>    
<form id="sysAdmin_merchantList_queryForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>商户信息</legend>
		<table class="table">
			<tr>
				<th>上级商户</th>
				<td colspan="3">
					<input type="text" readonly="readonly" id="parentMIDName" style="width:205px;"/>
				</td>
		   	</tr>
		   	<tr>
		   		<th>商户注册名称：</th>
		   		<td><input type="text" readonly="readonly" id="rname" style="width:200px;"/></td>
		   	
		   		<th>商户账单名称：</th>
	   			<td><input type="text" readonly="readonly" id="shortName" style="width:200px;"/></td>
		   	</tr>
		   	<tr>
			   	<th>凭条打印名称：</th>
	   			<td><input type="text" readonly="readonly" id="printName" style="width:200px;"/></td>
	   			
	   			<th>商户所在地区码：</th>
	   			<td>
	   				<input type="text" readonly="readonly" id="areaCodeName" style="width:200px;" />
	   			</td>
	   		</tr>
	   		<tr>
	   			<th>经营范围：</th>
	   			<td><input type="text" readonly="readonly" id="businessScope" style="width:200px;"/></td>
		   	
		   		<th>电子邮箱：</th>
	   			<td><input type="text" readonly="readonly" id="email" style="width:200px;"/></td>
	   		</tr>
		   	<tr>	
		   		<th>业务人员：</th>
	   			<td>
	   				<input type="text" readonly="readonly" id="busidName" style="width:200px;" />
	   			</td>
	   			
	   			<th>维护人员：</th>
	   			<td>
	   				<input type="text" readonly="readonly" id="maintainUserIdName" style="width:200px;" />
	   			</td>
	   		</tr>
		   	<tr>
	   			<th>法人：</th>
	   			<td colspan="3"><input type="text" readonly="readonly" id="legalPerson" style="width:200px;"/></td>
		   	</tr>
		   	<tr>
		   		<th>法人证件号码：</th>
	   			<td><input type="text" readonly="readonly" id="legalNum" style="width:200px;" /></td>
		   	
		   		<th>法人证件类型：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="legalTypeName" style="width:200px;"/>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>营业执照编号：</th>
	   			<td><input type="text" readonly="readonly" id="bno" style="width:200px;" /></td>
		   	
		   		<th>注册资金：</th>
	   			<td><input type="text" readonly="readonly" id="regMoney" style="width:200px;" /></td>
		   	</tr>
		   	<tr>
		   		<th>营业地址：</th>
	   			<td><input type="text" readonly="readonly" id="baddr" style="width:200px;" /></td>
	   			
		   		<th>注册地址：</th>
	   			<td><input type="text" readonly="readonly" id="raddr" style="width:200px;" /></td>
	   		</tr>
		   	<tr>
		   		<th>组织机构代码：</th>
	   			<td><input type="text" readonly="readonly" id="rno" style="width:200px;" /></td>
	   		
		   		<th>税务登记证号：</th>
	   			<td><input type="text" readonly="readonly" id="registryNo" style="width:200px;" /></td>
	   		</tr>
		   	<tr>
		   		<th>补充材料编号：</th>
		   		<td colspan="3"><input type="text" readonly="readonly" id="materialNo" style="width:200px;" /></td>
			</tr>
			<tr>
		   		<th>合同编号：</th>
		   		<td><input type="text" readonly="readonly" id="contractNo" style="width:200px;" /></td>
		   		
		   		<th>合同起始时间：</th>
		   		<td><input type="text" readonly="readonly" id="contractPeriod" style="width:200px;" /></td>
		   	</tr>
		   	<tr>
		   		<th>押金：</th>
		   		<td><input type="text" id="deposit" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="100"/></td>
		   		
		   		<th>服务费：</th>
		   		<td><input type="text" id="charge" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="100"/></td>
		   	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>上传文件</legend>
		<table class="table">
	   		<tr>
		   		<th>法人身份上传文件：</th>
	   			<td>
			         	<img id="legalUploadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
	   			
	   			<th>营业执照上传文件：</th>
	   			<td>
	   				<img id="bupLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	</tr>
		   	<tr>
	   			<th>组织机构证上传文件：</th>
	   			<td>
	   			<img id="rupLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>

	   			<th>税务登记证上传文件：</th>
	   			<td>
	   			<img id="registryUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
			</tr>
		   	<tr>
		   		<th>门面照片上传文件：</th>
	   			<td>
	   			<img id="photoUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	
		   		<th>大协议照片上传文件：</th>
		   		<td>
		   		<img id="bigdealUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料上传文件：</th>
		   		<td>
		   		<img id="materialUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   		
		   		<th>补充材料1上传文件：</th>
	   			<td>
	   			<img id="materialUpLoad1File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料2上传文件：</th>
	   			<td>
	   			<img id="materialUpLoad2File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
	   			
		   		<th>补充材料3上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad3File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料4上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad4File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>

		   		<th>补充材料5上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad5File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>结算信息</legend>
		<table class="table">
			<tr>
		   		<th>行业选择：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="industryTypeName" style="width:200px;" />
		   		</td>
		
		   		<th>行业编码：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="mccidName" style="width:200px;" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>结算周期：</th>
		   		<td>
		   			T&nbsp;&nbsp;+&nbsp;&nbsp;
		   			<input type="text" readonly="readonly" id="settleCycle" style="width:160px;" />
		   		</td>
		   
		   		<th>结算时间点：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="settleRange" style="width:200px;" />
		   		</td>
			</tr>
		   	<tr>
		   		<th>支付系统行号：</th>
		   		<td><input type="text" readonly="readonly" id="payBankId" style="width:200px;"/></td>
		   		
		   		<th>节假日是否合并结账：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="settleMergerName" style="width:200px;"/>
		   		</td>
			</tr>
		   	<tr>
				<th>是否大小额商户：</th>
		   		<td colspan="3">
		   			<input type="text" readonly="readonly" id="merchantTypeName" style="width:200px;" />
		   		</td>
			</tr>
			<tr>
		   		<th>银联卡费率：</th>
		   		<td colspan="3"><input type="text" readonly="readonly" id="bankFeeRate" style="width:100px;" />%</td>
		   	</tr>
		   	<tr>
		   		<th>封顶手续费：</th>
		   		<td><input type="text" readonly="readonly" id="feeAmt" style="width:200px;" class="easyui-numberbox"/></td>
		   	
		   		<th>封顶值：</th>
		   		<td><input type="text" readonly="readonly" id="dealAmt" style="width:200px;" /></td>
		   	</tr>
		   	<tr>
		   		<th>开户类型：</th>
		   		<td colspan="3">
		   			<input type="text" readonly="readonly" id="accTypeName" style="width:200px;" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行：</th>
		   		<td colspan="3">
		   			<input type="text" readonly="readonly" id="bankBranch" style="width:640px;"/>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行账号：</th>
		   		<td><input type="text" readonly="readonly" id="bankAccNo" style="width:200px;"/></td>
			
		   		<th>开户账号名称：</th>
		   		<td><input type="text" readonly="readonly" id="bankAccName" style="width:200px;"/></td>
		   	</tr>
		</table> 
	</fieldset>
	
	<fieldset>
		<legend>外卡费率</legend>
		<table class='table'>
			<tr>
				<th>VISA外卡费率：</th>
				<td>
					<input type='text' id='feeRateV' id='feeRateV' style='width:100px;' maxlength='18' readonly="readonly"/>%<font color='red'>&nbsp;*</font>
				</td>
			
				<th> MASTER外卡费率：</th>
				<td>
					<input type='text' id='feeRateM' id='feeRateM' style='width:100px;'  maxlength='18' readonly="readonly"/>%<font color='red'>&nbsp;*</font>
				</td>
			</tr>
			
			<tr>
				<th>JCB外卡费率：</th>
				<td>
					<input type='text' id='feeRateJ' id='feeRateJ' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
				
				<th>美运外卡费率：</th>
				<td>
					<input type='text' id='feeRateA' id='feeRateA' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
			</tr>
			
			<tr>
				<th>大莱外卡费率：</th>
				<td colspan="3">
					<input type='text' id='feeRateD' id='feeRateD' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
			</tr>
		</table>
	</fieldset>
		
	<fieldset>
		<legend>联系人信息</legend>
		<table class="table">
			<tr>
				<th>联系人：</th>
		   		<td><input type="text" readonly="readonly" id="contactPerson" style="width:200px;"/></td>
		   		
		   		<th>联系地址：</th>
		   		<td><input type="text" readonly="readonly" id="contactAddress" style="width:200px;"/></td>
			</tr>
		   	<tr>
		   		<th>联系手机：</th>
		   		<td><input type="text" readonly="readonly" id="contactPhone" style="width:200px;"/></td>
	
		   		<th>联系固话：</th>
		   		<td><input type="text" readonly="readonly" id="contactTel" style="width:200px;"/></td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>受理描述</legend>
		<table class='table'>
			<tr>
				<th>受理描述</th>
				<td>
					<textarea rows="6" cols="38" style="resize:none;" id="processContext" readonly="readonly"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
