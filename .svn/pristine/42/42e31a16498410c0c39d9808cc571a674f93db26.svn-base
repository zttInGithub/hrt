<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#feeRate").hide();
		$("#amt").hide();
		$('#bankFeeRate').val("0.65");	
		$('#feeAmt').val("35");	
		$('#creditBankRate').val("0.65");	
		$('#dealAmt').val("5385");
	});
	
	$(function(){
		$('#bankProvinceCode').combogrid({
			url : '${ctx}/sysAdmin/unit_searchProvince.action',
			idField:'PROVINCE_NAME',
			textField:'PROVINCE_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'PROVINCE_NAME',title:'归属地',width:150}
			]]
		});
	});


	$(function(){
		$('#busid').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID',
			textField:'SALENAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'SALENAME',title:'销售姓名',width:150},
				{field:'BUSID',title:'id',width:150,hidden:true}
			]]
		});
	});
	
	
	$('#legalUploadFile').change(function(){
		var contact = document.getElementById('legalUploadFile').value;
		document.getElementById("legalUploadFileName").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#bupLoadFile').change(function(){
		var contact = document.getElementById('bupLoadFile').value;
		document.getElementById("bupLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#rupLoadFile').change(function(){
		var contact = document.getElementById('rupLoadFile').value;
		document.getElementById("rupLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#registryUpLoadFile').change(function(){
		var contact = document.getElementById('registryUpLoadFile').value;
		document.getElementById("registryUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#materialUpLoadFile').change(function(){
		var contact = document.getElementById('materialUpLoadFile').value;
		document.getElementById("materialUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#photoUpLoadFile').change(function(){
		var contact = document.getElementById('photoUpLoadFile').value;
		document.getElementById("photoUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#bigdealUpLoadFile').change(function(){
		var contact = document.getElementById('bigdealUpLoadFile').value;
		document.getElementById("bigdealUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad1File').change(function(){
		var contact = document.getElementById('materialUpLoad1File').value;
		document.getElementById("materialUpLoad1").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad2File').change(function(){
		var contact = document.getElementById('materialUpLoad2File').value;
		document.getElementById("materialUpLoad2").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad3File').change(function(){
		var contact = document.getElementById('materialUpLoad3File').value;
		document.getElementById("materialUpLoad3").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad4File').change(function(){
		var contact = document.getElementById('materialUpLoad4File').value;
		document.getElementById("materialUpLoad4").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad5File').change(function(){
		var contact = document.getElementById('materialUpLoad5File').value;
		document.getElementById("materialUpLoad5").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#isForeignYes').change(function(){
		$("#feeRate").show();
		$('#feeRateV').validatebox({  
		    required: true,
		    validType:'intOrFloat'
		});
		$('#feeRateM').validatebox({  
		    required: true,
		    validType:'intOrFloat'
		});
		$('#feeRateJ').validatebox({  
			required: false,
			validType:'intOrFloat'
		});  
		$('#feeRateA').validatebox({
			required: false,
			validType:'intOrFloat'
		});  
		$('#feeRateD').validatebox({
			required: false,
			validType:'intOrFloat'
		});
	});
	
	$('#isForeignNo').change(function(){
		$("#feeRate").hide();
		$('#feeRateV').validatebox({  
		    required: false,
		    validType:'intOrFloat'
		});
		$('#feeRateM').validatebox({  
		    required: false,
		    validType:'intOrFloat'
		});
		$('#feeRateJ').validatebox({  
			required: false,
			validType:'intOrFloat'
		});  
		$('#feeRateA').validatebox({
			required: false,
			validType:'intOrFloat'
		});  
		$('#feeRateD').validatebox({
			required: false,
			validType:'intOrFloat'
		});
	});
	
	$('#merchantTypeBig').change(function(){
		$("#amt").hide();
		$("#settle").show();
		$('#feeAmt').validatebox({
			required: false,
			validType:'intOrFloat'  
		});
		$('#dealAmt').validatebox({
			required: false  
		});
	});

	$('#settleMthod1').change(function(){
		$('#bankFeeRate').val("0.65");	
		$('#feeAmt').val("35");	
		$('#creditBankRate').val("0.65");	
		$('#dealAmt').val("5385");
		});
	$('#settleMthod2').change(function(){
		$('#bankFeeRate').val("0.72");
		$('#feeAmt').val("0");
		$('#creditBankRate').val("0.72");
		$('#dealAmt').val("0");
		});
	
	$('#merchantTypeSmall').change(function(){
		$("#amt").show();	
		$("#settle").hide();		
		$('#feeAmt').validatebox({	
			required: true,
			validType:'intOrFloat' 
		});
		$('#dealAmt').validatebox({
			required: true,
			validType:'intOrFloat' 
		});
	});

	$('#accContrary').change(function(){
		$("#accRequired").hide();
		$('#accNum').validatebox({
			required: false 
		});
		$('#accExpdate').validatebox({
			required: false  
		});
	});

	$('#accPrivate').change(function(){
		$("#accRequired").show();
		$('#accNum').validatebox({
			required: true
		});
		$('#accExpdate').validatebox({
			required: true  
		});
	});

	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
		return util(value); 
	        }, 
	        message : '图片格式不正确' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules, {
		cardValidator : {// 验证卡号 
        	validator : function(value) {   
        	    return /^\d{8,35}$/i.test(value);   
        	},   
        	message : '卡号格式不正确！'   
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
		
	$(function(){
		 $("#legalUploadFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		 $("#bupLoadFile").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		 $("#rupLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		 $("#registryUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		 $("#materialUpLoadFile").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		 $("#photoUpLoadFile").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		 $("#bigdealUpLoadFile").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 });
		 $("#materialUpLoad1File").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		 $("#materialUpLoad2File").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
		 $("#materialUpLoad3File").uploadPreview({ Img: "ImgPrA", Width: 120, Height: 120 });
		 $("#materialUpLoad4File").uploadPreview({ Img: "ImgPrB", Width: 120, Height: 120 });
		 $("#materialUpLoad5File").uploadPreview({ Img: "ImgPrC", Width: 120, Height: 120 });
	});
	$.extend($.fn.validatebox.defaults.rules, {
		ss : {
        	validator : function(value) {
        	    return /^[0-9]*[1-9][0-9]*$/i.test(value);   
        	},   
        	message : '不为0且为数字'   
    	}
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
	            return /^\d{12}$/i.test(value); 
	        }, 
	        message : '必须是十二位数字！' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		phoneValidator:{
			validator : function(value) {
	            return /^\d{11}$/i.test(value); 
	        }, 
	        message : '必须是十一位数字！' 
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

	function dealAmtShow(){
 		if(($('#feeAmt').length>0 && $('#feeAmt').val()!="") && ($('#bankFeeRate').length>0 && $('#bankFeeRate').val()!="")){
			var bankFeeRate = $('#bankFeeRate').val() / 100;
			var dealAmt = $('#feeAmt').val() / bankFeeRate;
			$('#dealAmt').val(Math.floor(dealAmt));
		}
	}
	
	$('#baddr').change(function(){
		$('#raddr').val($('#baddr').val());
	}); 
	
</script>

<form id="sysAdmin_merchantMicro11_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>商户信息</legend>
		<table class="table">
		   	<tr>
		   		<th>商户注册名称：</th>
		   		<td><input type="text" name="rname" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/><font color="red">&nbsp;*</font></td>
		   		
		   		<th>商户账单名称：</th>
	   			<td><input type="text" name="shortName" style="width:200px;" maxlength="50" class="easyui-validatebox" data-options="validType:'spaceValidator'" /></td>
	   		</tr>
	   		<tr>
	   			<th>经营范围：</th>
	   			<td><input type="text" name="businessScope" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="25"/><font color="red">&nbsp;*</font></td>
		   	
		   		<th>电子邮箱：</th>
	   			<td><input type="text" name="email" class="easyui-validatebox" data-options="validType:'email'" style="width:200px;" maxlength="30"/></td>
	   		</tr>
		   	<tr>	
		   		<th>业务人员：</th>
	   			<td>
	   				<select id="busid" name="busid" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
	   			</td>
	   			<th>押金：</th>
		   		<td><input type="text" name="deposit" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="16"/></td>
	   		</tr>
		   	<tr>
	   			<th>法人：</th>
	   			<td><input type="text" name="legalPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="10"/><font color="red">&nbsp;*</font></td>
		   		<th>法人身份证有效期：</th>
		   		<td><input type="text" name="legalExpdate" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="30"/><font color="red">&nbsp;*</font></td>
		   	</tr>
		   	<tr>
		   		<th>法人证件号码：</th>
	   			<td><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;*</font></td>
		   		<th>法人证件类型：</th>
		   		<td>
		   			<select name="legalType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
	    				<option value="1" selected="selected">身份证</option>
	    				<option value="2">军官证</option>
	    				<option value="3">护照</option>
	    				<option value="4">港澳通行证</option>
	    				<option value="5">其他</option>
	    			</select>
		   		</td>
	   		</tr>
	   		<tr>
	   			<th>营业执照编号：</th>
	   			<td><input type="text" name="bno" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;*</font></td>
	   			<th>营业执照有效期：</th>
	   			<td><input type="text" name="bnoExpdate" style="width:200px;" /></td>	   	
			</tr>
	   		<tr>
	   			<th>营业地址：</th>
	   			
	   			<td>省：<input type="text" name="province" style="width:50px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/>
	   			市：<input type="text" name="city" style="width:50px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/>
	   			<input type="text" name="baddr" id="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font></td>
	   			
	   			<th>注册地址：</th>
	   			<td><input type="text" name="raddr" id="raddr" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font></td>
	   		</tr>
		   	<tr>
	   			<th>组织机构代码：</th>
	   			<td><input type="text" name="rno" style="width:200px;" maxlength="20"/></td>
	   			
	   			<th>税务登记证号：</th>
	   			<td><input type="text" name="registryNo" style="width:200px;" maxlength="20"/></td>
	   		</tr>
	   		<tr>
	   			<th>补充材料编号：</th>
		   		<td><input type="text" name="materialNo" style="width:200px;" maxlength="20"/></td>
		   		<th>凭条打印名称：</th>
	   			<td><input type="text" name="printName" style="width:200px;" maxlength="50" class="easyui-validatebox" data-options="validType:'spaceValidator'" /></td>
	   		
	   		</tr>
	   		<tr>
	   			<th>合同编号：</th>
		   		<td ><input type="text" name="contractNo" style="width:200px;" maxlength="20"/></td>
		   		<th>会员宝注册手机号：</th>
		   		<td ><input type="text" name="hybPhone" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'phoneValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	   		</tr>
	   		<tr>
	   			<th>服务费：</th>
		   		<td><input type="text" name="charge" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="16"/></td>
		   		<th>注册资金：</th>
	   			<td><input type="text" name="regMoney" style="width:200px;" maxlength="20"/></td>
	   		
		   	</tr>
	   	</table>
	</fieldset>
	
	<fieldset>
		<legend>上传文件</legend>
		<table class="table">
	   		<tr>
		   		<th>法人身份上传文件：</th>
	   			<td>
	   				<input type="file" name="legalUploadFile" id="legalUploadFile" class="easyui-validatebox" data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
	   				<div><img id="ImgPr1" width="120" height="120" /></div>
	   			</td>
	   			
	   			<th>法人身份证反面：</th>
		   		<td>
		   			<input type="file" name="materialUpLoadFile" id="materialUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
		   			<div><img id="ImgPr5" width="120" height="120" /></div>
		   		</td>
		   	</tr>
		   	
		   	<tr>
		   		<th>个人照片：</th>
	   			<td>
	   				<input type="file" name="materialUpLoad1File" id="materialUpLoad1File" class="easyui-validatebox" data-options="validType:'imgValidator'" /><font color="red">&nbsp;*</font>
	   				<div><img id="ImgPr8" width="120" height="120" /></div>
	   			</td>
	   			
	   			<th>银行卡正面照片：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad2File" id="materialUpLoad2File" class="easyui-validatebox" data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
		   			<div><img id="ImgPr9" width="120" height="120" /></div>
		   		</td>
		   		
		   	</tr>
		   	<tr>
		   	    <th>银行卡反面照片：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad3File" id="materialUpLoad3File" class="easyui-validatebox" data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
		   			<div><img id="ImgPrA" width="120" height="120" /></div>
		   		</td>
	   			<th>营业执照照片：</th>
	   			<td>
	   				<input type="file" name="bupLoadFile" id="bupLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   				<div><img id="ImgPr2" width="120" height="120" /></div>
	   			</td>
			</tr>
			<tr>
		   	    <th>信用卡正面照片：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad5File" id="materialUpLoad5File" class="easyui-validatebox" data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
		   			<div><img id="ImgPrC" width="120" height="120" /></div>
		   		</td>
			</tr>
		</table>
	</fieldset>
	
	
	<fieldset>
		<legend>结算信息</legend>
		<table class="table">
		   	<tr>
		   		<th>支付系统行号：</th>
		   		<td colspan="3">
		   		<input type="text" name="payBankId" style="width:200px;" class="easyui-validatebox"  data-options="required:true,validType:'payBankIdValidator'" maxlength="20"/><font color="red">&nbsp;*支付系统行号作为结算信息依据，填写错误会影响成功付款!</font>
		   		</td>
			</tr>
		   <!-- 	<tr>
				<th>是否大小额商户：</th>
		   		<td colspan="3">
		   			<input type="radio" name="merchantType" id="merchantTypeBig" value="1" checked="checked"/>小额商户
					<input type="radio" name="merchantType" id="merchantTypeSmall" value="2" />大额商户
		   		</td>
			</tr> -->
			<tr id="settle">
				<th>结算方式：</th>
		   		<td colspan="3">
		   			<input type="text" name="merchantType" id="merchantType" value="2" style="display: none"/>
		   			<input type="radio" name="settMethod" id="settleMthod1" value="000000" checked="checked" "/>日结
					<input type="radio" name="settMethod" id="settleMthod2" value="100000" "/>实时
		   		</td>
			</tr>
			<tr>
		   		<th>借记卡费率：</th>
		   		<td ><input type="text" name="bankFeeRate" id="bankFeeRate" style="width:100px;" readonly="readonly" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
		   		<th>借记卡封顶手续费：</th>
		   		<td><input type="text" name="feeAmt" id="feeAmt" style="width:200px;"  maxlength="18" readonly="readonly" data-options="required:true"/><font color="red">&nbsp;*</font></td>
		   	</tr>
		   <!-- <tr id="amt"> -->	
		   <tr>
		   		<th>贷记卡费率：</th>
		   		<td ><input type="text" value="" name="creditBankRate" id="creditBankRate" style="width:100px;" readonly="readonly" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
		   	<!--
		   		<th>封顶值：</th>-->
		   		<td><input type="text" name="dealAmt" id="dealAmt" style="width:200px;display: none" maxlength="18"/></td>	
		   	</tr>
		   	<tr>
		   		<th>开户类型：</th>
		   		<td colspan="3">
		   			<input type="radio" name="accType" id="accContrary" value="1" />对公
					<input type="radio" name="accType" id="accPrivate" value="2" checked="checked"/>对私
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行：</th>
		   		<td colspan="3">
		   			<select name="bankSendCode" class="easyui-combobox" data-options="editable:false" style="width:205px;">
		   				<option value=""></option>
		   				<option value="中信银行">中信银行</option>
		   				<option value="中国农业银行">中国农业银行</option>
		   				<option value="中国建设银行">中国建设银行</option>
		   				<option value="中国银行">中国银行</option>
		   				<option value="上海浦东发展银行">上海浦东发展银行</option>
		   				<option value="中国交通银行">中国交通银行</option>
		   				<option value="中国工商银行">中国工商银行</option>
		   				<option value="中国民生银行">中国民生银行</option>
		   				<option value="农村商业银行">农村商业银行</option>
		   				<option value="华夏银行">华夏银行</option>
		   				<option value="中国平安银行">中国平安银行</option>
		   				<option value="中国建设银行">中国建设银行</option>
		   				<option value="中国兴业银行">中国兴业银行</option>
		   				<option value="中国招商银行">中国招商银行</option>
		   				<option value="北京银行">北京银行</option>
		   				<option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
		   				<option value="中国光大银行">中国光大银行</option>
		   				<option value="广发银行">广发银行</option>
		   				<option value="其他">其他</option>
		   			</select>
		   			<select id="bankProvinceCode" name="bankProvinceCode" class="easyui-combogrid" data-options="editable:false" style="width:205px;"></select>
		   			<input type="text" name="bankName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行账号：</th>
		   		<td><input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'cardValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
			
		   		<th>开户账号名称：</th>
		   		<td><input type="text" name="bankAccName"  id="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"   maxlength="50"/><font color="red">&nbsp;*</font></td>
		   	</tr>
		   	<tr id="accRequired">
		   		<th>入账人身份证号：</th>
		   		<td><input type="text"  name="accNum" id="accNum" style="width:200px;"class="easyui-validatebox" data-options="required:true"   maxlength="20"/><font color="red">&nbsp;*</font></td>
			
		   		<th>入账人身份证有效期：</th>
		   		<td><input type="text"  name="accExpdate" id="accExpdate" style="width:200px;" class="easyui-validatebox" data-options="required:true"   maxlength="50"/><font color="red">&nbsp;*</font></td>
		   	</tr>
			<tr>
		   		<th>是否开通外卡：</th>
		   		<td>
		   			<input type="radio" name="isForeign" id="isForeignYes" value="1" />是
					<input type="radio" name="isForeign" id="isForeignNo" value="2" checked="checked"/>否
		   		</td>
		   	</tr>
		</table>
	</fieldset>
	
	<fieldset id='feeRate'>
		<legend>外卡费率</legend>
		<table class='table'>
			<tr>
				<th>VISA外卡费率：</th>
				<td>
					<input type='text' id='feeRateV' name='feeRateV' style='width:100px;' maxlength='18'/>%<font color='red'>&nbsp;*</font>
				</td>
			
				<th> MASTER外卡费率：</th>
				<td>
					<input type='text' id='feeRateM' name='feeRateM' style='width:100px;'  maxlength='18'/>%<font color='red'>&nbsp;*</font>
				</td>
			</tr>
			
			<tr>
				<th>JCB外卡费率：</th>
				<td>
					<input type='text' name='feeRateJ' id='feeRateJ' style='width:100px;' maxlength='18'/>%
				</td>
				
				<th>美运外卡费率：</th>
				<td>
					<input type='text' name='feeRateA' id='feeRateA' style='width:100px;' maxlength='18'/>%
				</td>
			</tr>
			
			<tr>
				<th>大莱外卡费率：</th>
				<td colspan="3">
					<input type='text' name='feeRateD' id='feeRateD' style='width:100px;' maxlength='18'/>%
				</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>联系人信息</legend>
		<table class="table">
			<tr>
		   		<th>联系人：</th>
		   		<td><input type="text" name="contactPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="10"/><font color="red">&nbsp;*</font></td>
		   		
		   		<th>联系地址：</th>
		   		<td><input type="text" name="contactAddress" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font></td>
			</tr>
		   	<tr>
		   		<th>联系手机：</th>
		   		<td><input type="text" name="contactPhone" style="width:200px;" maxlength="20" class="easyui-numberbox" data-options="required:true"/><font color="red">&nbsp;*</font></td>
	
		   		<th>联系固话：</th>
		   		<td><input type="text" name="contactTel" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="required:true"/><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
		   		<th>备注：</th>
		   		<td  colspan="3">
	    			<textarea rows="6" cols="38" style="resize:none;" name="remarks" maxlength="100"></textarea>
	    		</td>
			</tr>
		</table>
	</fieldset>
	<input type="hidden" name="legalUploadFileName" id="legalUploadFileName">
	<input type="hidden" name="bupLoad" id="bupLoad">
	<input type="hidden" name="rupLoad" id="rupLoad">
	<input type="hidden" name="registryUpLoad" id="registryUpLoad">
	<input type="hidden" name="materialUpLoad" id="materialUpLoad">
	<input type="hidden" name="photoUpLoad" id="photoUpLoad">
	<input type="hidden" name="bigdealUpLoad" id="bigdealUpLoad">
	<input type="hidden" name="materialUpLoad1" id="materialUpLoad1">
	<input type="hidden" name="materialUpLoad2" id="materialUpLoad2">
	<input type="hidden" name="materialUpLoad3" id="materialUpLoad3">
	<input type="hidden" name="materialUpLoad4" id="materialUpLoad4">
	<input type="hidden" name="materialUpLoad5" id="materialUpLoad5">
</form>  

