<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 运营中心审核页面 -->
 <script type="text/javascript">
 	
 	$(function(){
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
	 		    		
	 	    			$('#dealUpLoadFile').attr('src',path1);
	 	    			$('#busLicUpLoadFile').attr('src',path2);
	 	    			$('#proofOfOpenAccountUpLoadFile').attr('src',path3);
	 	    			$('#legalAUpLoadFile').attr('src',path4);
	 	    			$('#legalBUpLoadFile').attr('src',path5);
	 	    			$('#accountAuthUpLoadFile').attr('src',path6);
	 	    			$('#accountLegalAUpLoadFile').attr('src',path7);
	 	    			$('#accountLegalBUpLoadFile').attr('src',path8);
	 	    			$('#accountLegalHandUpLoadFile').attr('src',path9);
	 	    			$('#officeAddressUpLoadFile').attr('src',pathA);
	 	    			$('#profitUpLoadFile').attr('src',pathB);
	     			}  
 	    	} 
 		});

 		//归属地
 		// $('#provinceCode').combogrid({
 		// 	url : '${ctx}/sysAdmin/unit_searchProvince.action',
 		// 	idField:'PROVINCE_CODE',
 		// 	textField:'PROVINCE_NAME',
 		// 	fitColumns:true,
 		// 	columns:[[ 
 		// 		{field:'PROVINCE_NAME',title:'省市',width:150},
 		// 		{field:'PROVINCE_CODE',title:'id',width:150,hidden:true}
 		// 	]]
 		// });
 		
 	});

 	function showBigImg(img){
 		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
 		$('#sysAdmin_00317_editForm').after(imgDialog);
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
 				},{
 				text:'关闭',
 				iconCls:'icon-ok',
 				handler:function() {
 					$('#sysAdmin_imgDialog').dialog('destroy');
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
 	
 	$.extend($.fn.validatebox.defaults.rules,{
 		unitCode:{
 			validator : function(value) { 
 	            return /^\d{2}$/i.test(value); 
 	        }, 
 	        message : '只能输入2位数字' 
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
 	$.extend($.fn.validatebox.defaults.rules,{
 		parentUnno:{
 			validator : function(value) { 
 	            return /^\d{6}$/i.test(value); 
 	        }, 
 	        message : '只能输入6位数字' 
 		}
 	});
 </script>
 <style type="text/css">
 	.hrt-label_pl15{
 		padding-left:15px;
 		color: red;
 	}
 </style>
<form id="sysAdmin_00317_editForm" method="post">
	<!-- 基础信息 -->
	<fieldset>
		<legend>基础信息</legend>
		<table class="table">
			<tr>
	    		<th>代理商全称：</th>
	    		<td ><input type="text" name="agentName" style="width:200px;"  readonly="readonly" /></td>
	    	<!-- 	<th>归属机构号：</th>
	    		<td>
	    			<input type="text" name="parentUnno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'parentUnno'" maxlength="6"/><font color="red">&nbsp;*</font>
	    		</td> -->
	    		<th>代理商级别：</th>
	    		<td >
	    			<input type="text" style="width:200px;"  readonly="readonly" value="分公司/运营中心"/>
	    			<input type="hidden" name="agentAttr" value="1">
	    		</td>
			</tr>
			<tr>
				
				<!-- <th>代理商简称：</th>
				<td><input type="text" name="agentShortNm" id="agentShortNm" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="80" /><font color="red">&nbsp;*</font></td> -->
				<th>营业执照号：</th>
				<td><input type="text" name="bno" style="width:200px;"  readonly="readonly" /></td>
	    		<th>注册地址：</th>
    			<td >
					<input type="text" name="regAddress" style="width:200px;" id="regAddress"  readonly="readonly"/>
				</td>
			</tr>
			<tr>
	    		<th>营业地址：</th>
    			<td >
					<input type="text" name="baddr" style="width:200px;" id="baddr"  readonly="readonly" />
	   		</td>
	   		<th>法人：</th>
	   		<td><input type="text" name="legalPerson" style="width:200px;"  readonly="readonly" /></td>
    		</tr>
    		<tr>
    			<th>法人证件号码：</th>
    			<td><input type="text" name="legalNum" style="width:200px;"  readonly="readonly" /></td>
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
				<td><input type="text" name="bankAccName" style="width:200px;"  readonly="readonly" /></td>
				<th>开户行名称：</th>
				<td><input type="text" name="bankBranch" style="width:200px;"  readonly="readonly" /></td>
			</tr>
			<tr>
				<th>账号：</th>
				<td><input type="text" name="bankAccNo" style="width:200px;"  readonly="readonly" /></td>
				<th>支付系统行号：</th>
	    		<td><input type="text" name="payBankID" style="width:200px;"  readonly="readonly" /></td>
    		</tr>
    		<tr>
	    		<th>开户类型：</th>
	    		<td>
	    			<input type="radio" name="accType" value="1" disabled="disabled"/>对公
					<input type="radio" name="accType" value="2" disabled="disabled"/>对私
	    		</td>
	    		<!-- <th>是否为交通银行：</th>
	    		<td>
					<input type="radio" name="bankType" value="1" checked="checked"/>交通银行
					<input type="radio" name="bankType" value="2" />非交通银行
				</td> -->
	    	</tr>
	    	<tr>
	    		<!-- <th>开户银行所在地类别：</th>
	    		<td>
	    			<input type="radio" name="areaType" value="1" checked="checked"/>北京
					<input type="radio" name="areaType" value="2" />非北京
	    		</td> -->
	    		<th>开户地：</th>
	    		<td><input type="text" name="bankArea" style="width:200px;"  readonly="readonly" /></td>
	    	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>签约信息</legend>
		<table class="table">
			<tr>
				<th>签约销售：</th>
	    		<td>
	    			<!-- <select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="editable:false" style="width:135px;" readonly="readonly"></select> -->
	    			<input type="text" name="signUserIdName" style="width:130px;" readonly="readonly"/>
	    		</td>
	    		<th>保证金：</th>
    			<td colspan="3"><input type="text" name="riskAmount" style="width:130px;" readonly="readonly"/></td>
			</tr>
			<tr>
    			<th>合同编号：</th>
	    		<td colspan="5"><input type="text" name="contractNo" style="width:130px;" maxlength="20" readonly="readonly"/></td>
	    	</tr>
	    	<tr>	
	    		<th>代理商负责人：</th>
	    		<td><input type="text" name="contact" style="width:130px;" readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="contactTel" style="width:130px;" readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="contactPhone" style="width:130px;" readonly="readonly"/></td>
	    	</tr>
	    	<tr>
	    		<th>业务联系人：</th>
	    		<td><input type="text" name="businessContacts" style="width:130px;" readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="businessContactsPhone" style="width:130px;"   readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="businessContactsMail" style="width:130px;"  readonly="readonly"/></td>
	    	</tr>
	    	<tr>	
	    		<th>风控联系人：</th>
	    		<td><input type="text" name="riskContact" style="width:130px;" readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="riskContactPhone" style="width:130px;" readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="riskContactMail" style="width:130px;"  readonly="readonly"/></td>
	    	</tr>
	    	<tr>
	    		<th>结算联系人：</th>
	    		<td><input type="text" name="secondContact" style="width:130px;" readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="secondContactTel" style="width:130px;" readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="secondContactPhone" style="width:130px;" readonly="readonly"/></td>
			</tr>
			<tr>	
				<th>分润联系人：</th>
				<td><input type="text" name="profitContactPerson" style="width:130px;" readonly="readonly"/></td>
				<th>联系电话：</th>
				<td><input type="text" name="profitContactPhone" style="width:130px;" readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="profitContactEmail" style="width:130px;" readonly="readonly"/></td>
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
   				<img id="dealUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   		<th>营业执照：</th>
   			<td>
   				<img id="busLicUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
   		</tr>
   		<tr>
	   		<th>对公开户证明：</th>
	   		<td>
	   			<img id="proofOfOpenAccountUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   		</td>
	   		
				<th>法人身份证正面：</th>
   			<td>
   				<img id="legalAUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>法人身份证反面：</th>
   			<td>
   				<img id="legalBUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
				<th>入账授权书：</th>
   			<td>
   				<img id="accountAuthUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人身份证正面：</th>
   			<td>
   				<img id="accountLegalAUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
				<th>入账人身份证反面：</th>
   			<td>
   				<img id="accountLegalBUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人手持身份证：</th>
   			<td>
   				<img id="accountLegalHandUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
				<th>办公地点照片：</th>
   			<td>
   				<img id="officeAddressUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>分润照片：</th>
				<td>
					<img id="profitUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
				</td>
			</tr>
	   </table>
	</fieldset>
	<!-- 审核信息 -->
	<!-- <fieldset>
		<legend>审核信息</legend>
		<table class="table">
			<tr>
				<th>省市：</th>
				<td>
					<select id="provinceCode" name="provinceCode" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
				</td>
				<th>简码：</th>
				<td><input type="text" name="unitCode" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'unitCode'" maxlength="3"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr id="returnReasonTr" style="display: none;">
				<th>退回原因：</th>
				<td colspan="3">
					<textarea rows="6" cols="38" style="resize:none;" name="returnReason" id="returnReason"  ></textarea>
				</td>
			</tr>
		</table>
	</fieldset> -->
	<input type="hidden" name="buid" id="buid">
</form>  

