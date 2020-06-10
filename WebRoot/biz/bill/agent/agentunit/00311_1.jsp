<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

    <!-- 代理商新增页面 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	$(function(){
		//绑定图片选定后预览 
		bindImgPreview();
		//绑定图片选择后名称选择
		binddoImgCallbackFun();
	});


	function binddoImgCallbackFun(){
		$('#dealUpLoadFile2').change(function() {doImgCallbackFun('dealUpLoadFile2','dealUpLoad2')});
		$('#busLicUpLoadFile2').change(function() {doImgCallbackFun('busLicUpLoadFile2','busLicUpLoad2')});
		$('#proofOfOpenAccountUpLoadFile2').change(function() {doImgCallbackFun('proofOfOpenAccountUpLoadFile2','proofOfOpenAccountUpLoad2')});
		$('#legalAUpLoadFile2').change(function() {doImgCallbackFun('legalAUpLoadFile2','legalAUpLoad2')});
		$('#legalBUpLoadFile2').change(function() {doImgCallbackFun('legalBUpLoadFile2','legalBUpLoad2')});
		$('#accountAuthUpLoadFile2').change(function() {doImgCallbackFun('accountAuthUpLoadFile2','accountAuthUpLoad2')});
		$('#accountLegalAUpLoadFile2').change(function() {doImgCallbackFun('accountLegalAUpLoadFile2','accountLegalAUpLoad2')});
		$('#accountLegalBUpLoadFile2').change(function() {doImgCallbackFun('accountLegalBUpLoadFile2','accountLegalBUpLoad2')});
		$('#accountLegalHandUpLoadFile2').change(function() {doImgCallbackFun('accountLegalHandUpLoadFile2','accountLegalHandUpLoad2')});
		$('#officeAddressUpLoadFile2').change(function() {doImgCallbackFun('officeAddressUpLoadFile2','officeAddressUpLoad2')});
		$('#profitUpLoadFile2').change(function() {doImgCallbackFun('profitUpLoadFile2','profitUpLoad2')});
	}

	function doImgCallbackFun(sourceId,targetId){
		var sourceVal = $("#"+sourceId).val();
		//获取jsp页面hidden的值  
		if(sourceVal)
			$("#"+targetId).val(sourceVal.replace(/.{0,}\\/, ""));	
	}

	function bindImgPreview(){ 
		$("#dealUpLoadFile2").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#busLicUpLoadFile2").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#proofOfOpenAccountUpLoadFile2").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#legalAUpLoadFile2").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		$("#legalBUpLoadFile2").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		$("#accountAuthUpLoadFile2").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		$("#accountLegalAUpLoadFile2").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 });
		$("#accountLegalBUpLoadFile2").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		$("#accountLegalHandUpLoadFile2").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
		$("#officeAddressUpLoadFile2").uploadPreview({ Img: "ImgPrA", Width: 120, Height: 120 });
		$("#profitUpLoadFile2").uploadPreview({ Img: "ImgPrB", Width: 120, Height: 120 });
	}

	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
				return util(value); 
	      }, 
	      message : '图片格式不正确' 
		}
	});
	
	function util(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".jpg" && ename!=".png" && ename!=".gif" && ename!=".jpeg" ){
				return false;
			}else{ 
				return true;
			}
		}
	}

	$.extend($.fn.validatebox.defaults.rules,{
		phoneValidator:{
			validator : function(value) { 
				return /\d+/.test(value); 
	      }, 
	      message : '电话号码格式不正确' 
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
 
<form id="sysAdmin_agentunit_addForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>基础信息</legend>
		<table class="table">
			<tr>
	    		<th>代理商全称：</th>
	    		<td><input type="text" name="agentName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
	    		<th >代理商简称：</th>
	    		<td ><input type="text" name="agentShortNm" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="80"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
				<th>营业执照号：</th>
				<td><input type="text" name="bno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    		<th>注册地址：</th>
	    		<td >
					<input type="text" name="regAddress" id="regAddress" style="width:200px;" maxlength="100" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
				</td>
			</tr>
	    	<tr>
				<th>营业地址：</th>
	    		<td >
					<input type="text" name="baddr" id="baddr" style="width:200px;" maxlength="100"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
				</td>
	    		<th>法人：</th>
	    		<td><input type="text" name="legalPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    		
	    	</tr>
	    	<tr>
		    	<th>法人证件类型：</th>
		    	<td>
		    		<select name="legalType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
		    			<option value="1">身份证</option>
		    			<option value="2">军官证</option>
		    			<option value="3">护照</option>
		    			<option value="4">港澳通行证</option>
		    			<option value="5">其他</option>
		    		</select>
		    	</td>
	    		<th >法人证件号码：</th>
	    		<td ><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	
		</table>
	</fieldset>
	
	<fieldset>
		<legend>账户信息</legend>
		<table class="table">
			<tr>
				<th>账户名称：</th>
				<td><input type="text" name="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
				<th>开户行名称：</th>
				<td><input type="text" name="bankBranch" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="200"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
				<th>账号：</th>
				<td><input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
				<th>支付系统行号：</th>
	    		<td><input type="text" name="payBankID" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
    		</tr>
    		<tr>
	    		<th>开户类型：</th>
	    		<td>
	    			<input type="radio" name="accType" value="1" checked="checked"/>对公
					<input type="radio" name="accType" value="2" />对私
	    		</td>
	    		<!-- <th>是否为交通银行：</th>
	    		<td>
					<input type="radio" name="bankType" value="1" checked="checked"/>交通银行
					<input type="radio" name="bankType" value="2" />非交通银行
				</td> -->
				<th>开户地：</th>
				<td><input type="text" name="bankArea" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	
		</table>
	</fieldset>
	
	<fieldset>
		<legend>签约信息</legend>
		<table class="table">
			<tr>
				<th>签约销售：</th>
	    		<td>
	    			<!-- <select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="required:true,editable:false,validType:'spaceValidator'" style="width:135px;"></select><font color="red">&nbsp;*</font> -->
	    			
	    			<input type="text" name="signUserId" style="width:130px;" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'" maxlength="18"/><font color="red">&nbsp;*</font>
				</td>
	    		<th>保证金：</th>
    			<td colspan="3"><input type="text" name="riskAmount" style="width:130px;" value="0" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'" maxlength="18" readonly="readonly"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
    			<th>合同编号：</th>
	    		<td colspan="5"><input type="text" name="contractNo" style="width:130px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"/></td>
	    	</tr>
	    	<tr>	
	    		<th>代理商负责人：</th>
	    		<td><input type="text" name="contact" style="width:130px;"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="contactTel" style="width:130px;" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="contactPhone" style="width:130px;" maxlength="50" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>业务联系人：</th>
	    		<td><input type="text" name="businessContacts" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="businessContactsPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="businessContactsMail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>	
	    		<th>风控联系人：</th>
	    		<td><input type="text" name="riskContact" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="riskContactPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="riskContactMail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>结算联系人：</th>
	    		<td><input type="text" name="secondContact" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="secondContactTel" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
	    	
	    		<th>邮箱：</th>
	    		<td><input type="text" name="secondContactPhone" style="width:130px;" maxlength="50" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>	
				<th>分润联系人：</th>
				<td><input type="text" name="profitContactPerson" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
				<th>联系电话：</th>
				<td><input type="text" name="profitContactPhone" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'"/><font color="red">&nbsp;*</font></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="profitContactEmail" style="width:130px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
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
   				<input type="file" name="dealUpLoadFile" id="dealUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr1" width="120" height="120" /></div>
   			</td>
	   		<th>营业执照：</th>
   			<td>
   				<input type="file" name="busLicUpLoadFile" id="busLicUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr2" width="120" height="120" /></div>
   			</td>
   		</tr>
   		<tr>
	   		<th>对公开户证明：</th>
	   		<td>
	   			<input type="file" name="proofOfOpenAccountUpLoadFile" id="proofOfOpenAccountUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			<div><img id="ImgPr3" width="120" height="120" /></div>
	   		</td>
	   		
				<th>法人身份证正面：</th>
   			<td>
   				<input type="file" name="legalAUpLoadFile" id="legalAUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr4" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>法人身份证反面：</th>
   			<td>
   				<input type="file" name="legalBUpLoadFile" id="legalBUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr5" width="120" height="120" /></div>
   			</td>
				<th>入账授权书：</th>
   			<td>
   				<input type="file" name="accountAuthUpLoadFile" id="accountAuthUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr6" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人身份证正面：</th>
   			<td>
   				<input type="file" name="accountLegalAUpLoadFile" id="accountLegalAUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr7" width="120" height="120" /></div>
   			</td>
				<th>入账人身份证反面：</th>
   			<td>
   				<input type="file" name="accountLegalBUpLoadFile" id="accountLegalBUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr8" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人手持身份证：</th>
   			<td>
   				<input type="file" name="accountLegalHandUpLoadFile" id="accountLegalHandUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr9" width="120" height="120" /></div>
   			</td>
				<th>办公地点照片：</th>
   			<td>
   				<input type="file" name="officeAddressUpLoadFile" id="officeAddressUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPrA" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>其他照片：</th>
   			<td>
   				<input type="file" name="profitUpLoadFile" id="profitUpLoadFile2" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPrB" width="120" height="120" /></div>
   			</td>
   		</tr>
	   </table>
	</fieldset>
	<!-- 创建隐藏域 用于传递文件名 --> 
	<input type="hidden" name="dealUpLoad" id="dealUpLoad2">
	<input type="hidden" name="busLicUpLoad" id="busLicUpLoad2">
	<input type="hidden" name="proofOfOpenAccountUpLoad" id="proofOfOpenAccountUpLoad2">
	<input type="hidden" name="legalAUpLoad" id="legalAUpLoad2">
	<input type="hidden" name="legalBUpLoad" id="legalBUpLoad2">
	<input type="hidden" name="accountAuthUpLoad" id="accountAuthUpLoad2">
	<input type="hidden" name="accountLegalAUpLoad" id="accountLegalAUpLoad2">
	<input type="hidden" name="accountLegalBUpLoad" id="accountLegalBUpLoad2">
	<input type="hidden" name="accountLegalHandUpLoad" id="accountLegalHandUpLoad2">
	<input type="hidden" name="officeAddressUpLoad" id="officeAddressUpLoad2">
	<input type="hidden" name="profitUpLoad" id="profitUpLoad2">
</form>  
