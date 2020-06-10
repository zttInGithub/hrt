<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

<!-- 代理商编辑页面 2017-04-26 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	$(function(){
		$('#signUserId').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID',
			textField:'SALENAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'SALENAME',title:'销售姓名',width:150},
				{field:'UNNO',title:'所属机构',width:150},
				{field:'BUSID',title:'id',width:150,hidden:true}
			]]
		});
		
		//绑定图片选定后预览 
		bindImgPreview();
		//绑定图片选择后名称选择
		binddoImgCallbackFun();
		//初始化预览图片
		initImg();
		setTimeout(function(){
			if('K'==$('#approveStatus').val()){
				$('#returnReasonTr').show();
			}
		},1000);

	});
	
	function initImg(){
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
	 	    			$('#ImgPr1').attr('src',path1);
	 	    			$('#ImgPr2').attr('src',path2);
	 	    			$('#ImgPr3').attr('src',path3);
	 	    			$('#ImgPr4').attr('src',path4);
	 	    			$('#ImgPr5').attr('src',path5);
	 	    			$('#ImgPr6').attr('src',path6);
	 	    			$('#ImgPr7').attr('src',path7);
	 	    			$('#ImgPr8').attr('src',path8);
	 	    			$('#ImgPr9').attr('src',path9);
	 	    			$('#ImgPrA').attr('src',pathA);
	 	    			$('#ImgPrB').attr('src',pathB);
	     			}  
 	    	} 
 		});
 	}

	function binddoImgCallbackFun(){
		$('#dealUpLoadFile').change(function() {doImgCallbackFun('dealUpLoadFile','dealUpLoad')});
		$('#busLicUpLoadFile').change(function() {doImgCallbackFun('busLicUpLoadFile','busLicUpLoad')});
		$('#proofOfOpenAccountUpLoadFile').change(function() {doImgCallbackFun('proofOfOpenAccountUpLoadFile','proofOfOpenAccountUpLoad')});
		$('#legalAUpLoadFile').change(function() {doImgCallbackFun('legalAUpLoadFile','legalAUpLoad')});
		$('#legalBUpLoadFile').change(function() {doImgCallbackFun('legalBUpLoadFile','legalBUpLoad')});
		$('#accountAuthUpLoadFile').change(function() {doImgCallbackFun('accountAuthUpLoadFile','accountAuthUpLoad')});
		$('#accountLegalAUpLoadFile').change(function() {doImgCallbackFun('accountLegalAUpLoadFile','accountLegalAUpLoad')});
		$('#accountLegalBUpLoadFile').change(function() {doImgCallbackFun('accountLegalBUpLoadFile','accountLegalBUpLoad')});
		$('#accountLegalHandUpLoadFile').change(function() {doImgCallbackFun('accountLegalHandUpLoadFile','accountLegalHandUpLoad')});
		$('#officeAddressUpLoadFile').change(function() {doImgCallbackFun('officeAddressUpLoadFile','officeAddressUpLoad')});
		$('#profitUpLoadFile').change(function() {doImgCallbackFun('profitUpLoadFile','profitUpLoad')});
	}

	function doImgCallbackFun(sourceId,targetId){
		var sourceVal = $("#"+sourceId).val();
		//获取jsp页面hidden的值  
		if(sourceVal)
			$("#"+targetId).val(sourceVal.replace(/.{0,}\\/, ""));	
	}

	function bindImgPreview(){ 
		$("#dealUpLoadFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#busLicUpLoadFile").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#proofOfOpenAccountUpLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#legalAUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		$("#legalBUpLoadFile").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		$("#accountAuthUpLoadFile").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		$("#accountLegalAUpLoadFile").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 });
		$("#accountLegalBUpLoadFile").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		$("#accountLegalHandUpLoadFile").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
		$("#officeAddressUpLoadFile").uploadPreview({ Img: "ImgPrA", Width: 120, Height: 120 });
		$("#profitUpLoadFile").uploadPreview({ Img: "ImgPrB", Width: 120, Height: 120 });
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
	
</script>
 
<style type="text/css">
	.hrt-label_pl15{
		padding-left:15px;
		color: red;
	}
</style>

<form id="sysAdmin_agentunit_editForm" method="post" enctype="multipart/form-data">
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
					<input type="text" name="regAddress" id="regAddress" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
				</td>
				
			</tr>
	    	<tr>
				<th>营业地址：</th>
	    		<td >
					<input type="text" name="baddr" id="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
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
	    		<th>法人证件号码：</th>
	    		<td ><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<td colspan="4">
					<label class="hrt-label_pl15">注意：以上信息均为“必填”。如代理商身份为个人，“营业执照号”填写代理商身份证号，“注册地址”填写身份证住址</label>
	    		</td>
    		</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>签约信息</legend>
		<table class="table">
			<tr>
				<th>签约销售：</th>
	    		<td>
	    			<select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="required:true,editable:false,validType:'spaceValidator'" style="width:135px;"></select><font color="red">&nbsp;*</font>
	    		</td>
	    		<th>保证金：</th>
    			<td colspan="3"><input type="text" name="riskAmount" style="width:130px;" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'" readonly="readonly"/><font color="red">&nbsp;*</font></td>
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
   				<input type="file" name="dealUpLoadFile" id="dealUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr1" width="120" height="120" /></div>
   			</td>
	   		<th>营业执照：</th>
   			<td>
   				<input type="file" name="busLicUpLoadFile" id="busLicUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr2" width="120" height="120" /></div>
   			</td>
   		</tr>
   		<tr>
	   		<th>对公开户证明：</th>
	   		<td>
	   			<input type="file" name="proofOfOpenAccountUpLoadFile" id="proofOfOpenAccountUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			<div><img id="ImgPr3" width="120" height="120" /></div>
	   		</td>
	   		
				<th>法人身份证正面：</th>
   			<td>
   				<input type="file" name="legalAUpLoadFile" id="legalAUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr4" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>法人身份证反面：</th>
   			<td>
   				<input type="file" name="legalBUpLoadFile" id="legalBUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr5" width="120" height="120" /></div>
   			</td>
				<th>入账授权书：</th>
   			<td>
   				<input type="file" name="accountAuthUpLoadFile" id="accountAuthUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr6" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人身份证正面：</th>
   			<td>
   				<input type="file" name="accountLegalAUpLoadFile" id="accountLegalAUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr7" width="120" height="120" /></div>
   			</td>
				<th>入账人身份证反面：</th>
   			<td>
   				<input type="file" name="accountLegalBUpLoadFile" id="accountLegalBUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr8" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人手持身份证：</th>
   			<td>
   				<input type="file" name="accountLegalHandUpLoadFile" id="accountLegalHandUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPr9" width="120" height="120" /></div>
   			</td>
				<th>办公地点照片：</th>
   			<td>
   				<input type="file" name="officeAddressUpLoadFile" id="officeAddressUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
   				<div><img id="ImgPrA" width="120" height="120" /></div>
   			</td>
	   	</tr>
	   	<tr>
				<th>其他照片：</th>
				<td>
					<input type="file" name="profitUpLoadFile" id="profitUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
					<div><img id="ImgPrB" width="120" height="120" /></div>
				</td>
			</tr>
	   </table>
	</fieldset>
	<fieldset id="returnReasonTr"  style="display: none;">
		<legend>审核信息</legend>
		<table class="table">
			<tr>
				<th>退回原因：</th>
				<td colspan="3">
					<textarea rows="6" cols="38" style="resize:none;" name="returnReason" id="returnReason" readonly="readonly"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<!-- 创建隐藏域 用于传递文件名 --> 
	<input type="hidden" name="dealUpLoad" id="dealUpLoad">
	<input type="hidden" name="busLicUpLoad" id="busLicUpLoad">
	<input type="hidden" name="proofOfOpenAccountUpLoad" id="proofOfOpenAccountUpLoad">
	<input type="hidden" name="legalAUpLoad" id="legalAUpLoad">
	<input type="hidden" name="legalBUpLoad" id="legalBUpLoad">
	<input type="hidden" name="accountAuthUpLoad" id="accountAuthUpLoad">
	<input type="hidden" name="accountLegalAUpLoad" id="accountLegalAUpLoad">
	<input type="hidden" name="accountLegalBUpLoad" id="accountLegalBUpLoad">
	<input type="hidden" name="accountLegalHandUpLoad" id="accountLegalHandUpLoad">
	<input type="hidden" name="officeAddressUpLoad" id="officeAddressUpLoad">
	<input type="hidden" name="profitUpLoad" id="profitUpLoad">
	<input type="hidden" name="approveStatus" id="approveStatus">
	<input type="hidden" name="buid">
	<input type="hidden" name="parentUnno">
</form>  
