<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">

	$(function(){
		var isForeign = <%=request.getParameter("isForeign") %>;
		var merchantType = <%=request.getParameter("merchantType") %>;
		var accType=<%=request.getParameter("accType") %>;
		if(isForeign == 2){
			feeRateHide();
		}else{
			feeRateShow();
		}
		if(merchantType == 1){
			amtHide();
		}else{
			amtShow();
		}
		if(accType==1){
			accRequiredHiden();
		}else{
			accRequiredShow();
		}
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
		
		$('#mccid').combogrid({
			url : '${ctx}/sysAdmin/merchant_searchMCC.action',
			idField:'MCCCODE',
			textField:'MCCNAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'MCCCODE',title:'编码',width:150},
				{field:'MCCNAME',title:'名称',width:150}
			]]
		});
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
		
		$('#bankSendCode').combogrid({
			url : '${ctx}/sysAdmin/merchant_searchCUPSendCodeInfo.action',
			idField:'SEND_NAME',
			textField:'SEND_NAME',
			fitColumns:true,
			columns:[[
				{field:'SEND_NAME',title:'名称',width:150}
			]]
		});
	});
	
	$(function(){
		$('#areaCode').combogrid({
			url : '${ctx}/sysAdmin/merchant_searchAreaCode.action',
			idField:'AREA_CODE',
			textField:'AREA_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'AREA_CODE',title:'区号',width:150},
				{field:'AREA_NAME',title:'名称',width:150}
			]]
		});
		
		$('#industryType').combogrid({
			url : '${ctx}/sysAdmin/merchant_searchIndustryInfo.action',
			idField:'INDUSTRYTYPE',
			textField:'INAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'INDUSTRYTYPE',title:'行业类别',width:150,hidden:true},
				{field:'INAME',title:'行业名称',width:150}
			]]
		});
	});
	
	$(function(){
		$('#parentMID').combogrid({
			url : '${ctx}/sysAdmin/merchant_searchMerchantInfoTree.action',
			idField:'MID',
			textField:'RNAME',
			fitColumns:true,
			columns:[[
				{field:'MID',title:'MID',width:150,hidden:true},
				{field:'RNAME',title:'名称',width:150}
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
		feeRateShow();
	});
	
	function feeRateShow(){
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
	}
	
	$('#isForeignNo').change(function(){
		feeRateHide();
	});
	
	function feeRateHide(){
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
	}
	
	$('#merchantTypeBig').change(function(){
		amtHide()
	});
	
	function amtHide(){
		$("#amt").hide();
		$('#feeAmt').validatebox({
			required: false,
			validType:'intOrFloat'  
		});
		$('#dealAmt').validatebox({
			required: false  
		});
	}
	
	$('#merchantTypeSmall').change(function(){
		amtShow();
	});
	
	function amtShow(){
		$("#amt").show();
		$('#feeAmt').validatebox({
			required: true,
			validType:'intOrFloat'
		});
		$('#dealAmt').validatebox({
			required: false  
		});
	}

	$('#accContrary').change(function(){
		accRequiredHiden();
	});
	
	function accRequiredHiden(){
		$("#accRequired").hide();
		$('#accNum').validatebox({
			required: false 
		});
		$('#accExpdate').validatebox({
			required: false  
		});
	}

	$('#accPrivate').change(function(){
		accRequiredShow();
	});
	
	function accRequiredShow(){
		$("#accRequired").show();
		$('#accNum').validatebox({
			required: true
		});
		$('#accExpdate').validatebox({
			required: true  
		});
	}

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
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {   
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
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
	
	
	function dealAmtShow(){
 		if(($('#feeAmt').length>0 && $('#feeAmt').val()!="") && ($('#bankFeeRate').length>0 && $('#bankFeeRate').val()!="")){
			var bankFeeRate = $('#bankFeeRate').val() / 100;
			var dealAmt = $('#feeAmt').val() / bankFeeRate;
			$('#dealAmt').val(Math.floor(dealAmt));
		}
	}
	
	$(function(){
		var bmid=<%=request.getParameter("bmid")%>;
		$.ajax({
			url:'${ctx}/sysAdmin/merchant_serachMerchantInfoDetailed.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmid:bmid},
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
	    			$("#ImgPr1").attr("src",path1);
    				$("#ImgPr2").attr("src",path2);
    				$("#ImgPr3").attr("src",path3);
    				$("#ImgPr4").attr("src",path4);
    				$("#ImgPr5").attr("src",path5);
    				$("#ImgPr6").attr("src",path6);
    				$("#ImgPr7").attr("src",path7);
    				$("#ImgPr8").attr("src",path8);
    				$("#ImgPr9").attr("src",path9);
    				$("#ImgPrA").attr("src",pathA);
    				$("#ImgPrB").attr("src",pathB);
    				$("#ImgPrC").attr("src",pathC);
    			}  
	    	} 
	    	
		});
	});
	
	$('#baddr').change(function(){
		$('#raddr').val($('#baddr').val());
	}); 
	
</script>

<form id="sysAdmin_merchant12_editForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>商户信息</legend>
		<table class="table">
			<tr>
				<th>上级商户</th>
				<td colspan="3">
					<select name="parentMID" id="parentMID" class="easyui-combogrid" data-options="editable:true" style="width:205px;"></select>
				</td>
		   	</tr>
		   	<tr>	
		   		<th>商户注册名称：</th>
		   		<td><input type="text" name="rname" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/><font color="red">&nbsp;*</font></td>
		   	
		   		<th>商户账单名称：</th>
	   			<td><input type="text" name="shortName" style="width:200px;" maxlength="50" class="easyui-validatebox" data-options="validType:'spaceValidator'" /></td>
		   	</tr>
		   	<tr>
			   	<th>凭条打印名称：</th>
	   			<td><input type="text" name="printName" style="width:200px;" maxlength="50" class="easyui-validatebox" data-options="validType:'spaceValidator'" /></td>
	   			
	   			<th>商户所在地区码：</th>
	   			<td>
	   				<select name="areaCode" id="areaCode" data-options="required:true" style="width:205px;"></select><font color="red">&nbsp;*</font>
	   			</td>
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
		   		<td><input type="text" name="legalExpdate" style="width:200px;" class="easyui-validatebox"data-options="required:true" maxlength="30"/><font color="red">&nbsp;*</font></td>
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
	   			<td><input type="text" name="bnoExpdate" style="width:200px;" maxlength="30"/></td>
	   		</tr>
	   		<tr>
	   			<th>营业地址：</th>
	   			<td><input type="text" name="baddr" id="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font></td>
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
		   		<td ><input type="text" name="materialNo" style="width:200px;" maxlength="20"/></td>
				<th>注册资金：</th>
	   			<td><input type="text" name="regMoney" style="width:200px;" maxlength="20"/></td>
	   		</tr>
	   		<tr>
	   			<th>合同编号：</th>
		   		<td ><input type="text" name="contractNo" style="width:200px;" maxlength="20"/></td>
		   		<th>服务费：</th>
		   		<td><input type="text" name="charge" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="16"/></td>
	   		</tr>
	   	</table>
	</fieldset>
	
	<fieldset>
		<legend>上传文件</legend>
		<table class="table">
	   		<tr>
		   		<th>法人身份上传文件：</th>
	   			<td>
	   				<input type="file" name="legalUploadFile" id="legalUploadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   				<div><img id="ImgPr1" width="120" height="120" /></div>
	   				
	   			</td>
	   			
	   			<th>营业执照上传文件：</th>
	   			<td>
	   				<input type="file" name="bupLoadFile" id="bupLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   				<div><img id="ImgPr2" width="120" height="120" /></div>
	   			</td>
		   	</tr>
		   	<tr>
	   			<th>组织机构证上传文件：</th>
	   			<td>
	   				<input type="file" name="rupLoadFile" id="rupLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   				<div><img id="ImgPr3" width="120" height="120" /></div>
	   			</td>

	   			<th>税务登记证上传文件：</th>
	   			<td>
	   				<input type="file" name="registryUpLoadFile" id="registryUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" />
	   				<div><img id="ImgPr4" width="120" height="120" /></div>
	   			</td>
			</tr>
		   	<tr>
		   		<th>门面照片上传文件：</th>
	   			<td>
	   				<input type="file" name="photoUpLoadFile" id="photoUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'" /><font color="red">&nbsp;*</font>
	   				<div><img id="ImgPr6" width="120" height="120" /></div>
	   			</td>
		   	
		   		<th>大协议照片上传文件：</th>
		   		<td>
		   			<input type="file" name="bigdealUpLoadFile" id="bigdealUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
		   			<div><img id="ImgPr7" width="120" height="120" /></div>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料上传文件：</th>
		   		<td>
		   			<input type="file" name="materialUpLoadFile" id="materialUpLoadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
		   			<div><img id="ImgPr5" width="120" height="120" /></div>
		   		</td>
		   		
		   		<th>补充材料1上传文件：</th>
	   			<td>
	   				<input type="file" name="materialUpLoad1File" id="materialUpLoad1File" class="easyui-validatebox" data-options="validType:'imgValidator'" />
	   				<div><img id="ImgPr8" width="120" height="120" /></div>
	   			</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料2上传文件：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad2File" id="materialUpLoad2File" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
		   			<div><img id="ImgPr9" width="120" height="120" /></div>
		   		</td>
		   		
		   		<th>补充材料3上传文件：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad3File" id="materialUpLoad3File" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
		   			<div><img id="ImgPrA" width="120" height="120" /></div>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料4上传文件：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad4File" id="materialUpLoad4File" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
		   			<div><img id="ImgPrB" width="120" height="120" /></div>
		   		</td>

		   		<th>补充材料5上传文件：</th>
		   		<td>
		   			<input type="file" name="materialUpLoad5File" id="materialUpLoad5File" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
		   			<div><img id="ImgPrC" width="120" height="120" /></div>
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
		   			<select id="industryType" name="industryType" class="easyui-combogrid" required="true" style="width:205px;"></select><font color="red">&nbsp;*</font>
		   		</td>
		
		   		<th>行业编码：</th>
		   		<td>
		   			<select id="mccid" name="mccid" class="easyui-combogrid" required="true" style="width:205px;"></select><font color="red">&nbsp;*</font>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>结算周期：</th>
		   		<td colspan="3">
		   			T&nbsp;&nbsp;+&nbsp;&nbsp;
		   			<select name="settleCycle" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false" style="width:105px;">
		   				<option value="1">1</option>
		   				<option value="2">2</option>
		   				<option value="3">3</option>
		   				<option value="4">4</option>
		   				<option value="5">5</option>
		   				<option value="6">6</option>
		   				<option value="7">7</option>
		   				<option value="0">0</option>
		   			</select><font color="red">&nbsp;*T+0业务会产生额外提现费用，请认真核对</font>
		   		</td>
		   </tr>
		   	<tr>
		   		<th>结算时间点：</th>
		   		<td>
		   			<select name="settleRange" class="easyui-combobox" data-options="editable:false,required:true" style="width:105px;">
		   				<option value="00" selected="selected">00</option>
		   				<option value="23">23</option>
		   				<option value="22">22</option>
		   				<option value="21">21</option>
		   				<option value="20">20</option>
		   				<option value="19">19</option>
		   				<option value="18">18</option>
		   				<option value="17">17</option>
		   				<option value="16">16</option>
		   				<option value="15">15</option>
		   				<option value="14">14</option>
		   				<option value="13">13</option>
		   				<option value="12">12</option>
		   				<option value="11">11</option>
		   				<option value="10">10</option>
		   				<option value="09">09</option>
		   				<option value="08">08</option>
		   				<option value="07">07</option>
		   				<option value="06">06</option>
		   				<option value="05">05</option>
		   				<option value="04">04</option>
		   				<option value="03">03</option>
		   				<option value="02">02</option>
		   				<option value="01">01</option>
		   			</select>
		   		</td>
			</tr>
		   	<tr>
		   		<th>支付系统行号：</th>
		   		<td colspan="3"><input type="text" name="payBankId" style="width:200px;" class="easyui-validatebox"  data-options="required:true,validType:'payBankIdValidator'" maxlength="20"/><font color="red">&nbsp;*支付系统行号作为结算信息依据，填写错误会影响成功付款!</font></td>
			</tr>
		   	<!-- <tr>
				<th>是否大小额商户：</th>
		   		<td colspan="3">
		   			<input type="radio" name="merchantType" id="merchantTypeBig" value="1" checked="checked"/>小额商户
					<input type="radio" name="merchantType" id="merchantTypeSmall" value="2" />大额商户
		   		</td>
			</tr> -->
			<tr>
		   		<th>借记卡费率：</th>
		   		<td ><input type="text" name="bankFeeRate" id="bankFeeRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
		   		<th>借记卡封顶手续费：</th>
		   		<td><input type="text" name="feeAmt" id="feeAmt" style="width:200px;" maxlength="18" onblur="dealAmtShow()"/><font color="red">&nbsp;*</font></td>
		   		
		   	</tr>
		   	<!-- <tr id="amt" style="display: none">
		   		<th>借记卡封顶值：</th>
		   		<td><input type="text" name="dealAmt" id="dealAmt" style="width:200px;" maxlength="18" disabled="disabled"/></td>
		   	</tr> -->
		   	<tr>
		   		<th>贷记卡费率：</th>
		   		<td colspan="1"><input type="text" name="creditBankRate" id="creditBankRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
		   		<td><input type="radio" name="merchantType" id="merchantTypeSmall" value="2" style="display: none"/></td>
		   		<td><input type="text" name="dealAmt" id="dealAmt" style="width:200px;display: none" maxlength="18"/></td>
		   		<!-- <th>贷记卡手续费：</th>
		   		<td><input type="text" name="creditFeeamt" id="creditFeeamt" style="width:200px;" maxlength="18" /><font color="red">&nbsp;*</font></td> -->
		   	</tr>
		   	<tr>
		   		<th>扫码支付费率：</th>
		   		<td ><input type="text" name="scanRate" id="scanRate" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font></td>
		   		
		   	</tr>
		   	<tr>
		   		<th>开户类型：</th>
		   		<td colspan="3">
		   			<input type="radio" name="accType" value="1" id="accContrary" />对公
					<input type="radio" name="accType" value="2" id="accPrivate" checked="checked"/>对私
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行：</th>
		   		<td colspan="3">
		   			<input type="text" name="bankBranch" style="width:600px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行账号：</th>
		   		<td><input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'cardValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
			
		   		<th>开户账号名称：</th>
		   		<td><input type="text" name="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/><font color="red">&nbsp;*</font></td>
		   	</tr>
		   	<tr id="accRequired">
		   		<th>入账人身份证号：</th>
				<td><input type="text" name="accNum" id="accNum" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;*</font></td>
		   		<th>入账人身份证有效期：</th>
		   		<td><input type="text" name="accExpdate" id="accExpdate"  style="width:200px;" class="easyui-validatebox" data-options="required:true"   maxlength="50"/><font color="red">&nbsp;*</font></td>
		   	</tr>
			<tr>
		   		<th>是否开通外卡：</th>
		   		<td colspan="3">
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
	<input type="hidden" name="processContext" id="processContext">
	
	<input type="hidden" name="realtimeQueryTimes">
	<input type="hidden" name="contractPeriod">
	<input type="hidden" name="approveStatus">
	<input type="hidden" name="approveUid">
	<input type="hidden" name="approveDate">
	<input type="hidden" name="mid">
	<input type="hidden" name="bmid">
	<input type="hidden" name="exchangTime" id="exchangTime">
</form>
