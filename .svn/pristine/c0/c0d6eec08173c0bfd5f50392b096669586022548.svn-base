<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	
	$(function(){
		var type = <%=request.getParameter("type")%>;
		if(type == '1'){
			$('#add').hide();
			$('#city').hide();
			$('#province').hide();
			$('#baddr').attr('readonly',true);
			$('#raddr').val($('#baddr').val());
			var bmid=<%=request.getParameter("bmid")%>;
			$.ajax({
				url:'${ctx}/sysAdmin/merchant_serachMerchantInfoDetailed.action',
	    		dataType:"json",  
	    		type:"post",
	    		data:{bmid:bmid},
	   			success:function(data) {
	   				var json=eval(data);
		    		if (json!="") { 
			    		
		    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[8]);
			    		var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[9]);
			    		var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[10]);
			    		var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
			    		var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
			    		var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[6]);
			    		var path7='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[8]);
			    		var path8='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[14]);
			    		var path9='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
		    			$("#ImgPr1").attr("src",path1);
	    				$("#ImgPr2").attr("src",path2);
	    				$("#ImgPr3").attr("src",path3);
	    				$("#ImgPr4").attr("src",path4);
	    				$("#ImgPr5").attr("src",path5);
	    				$("#ImgPr6").attr("src",path6);
	    				$("#ImgPr7").attr("src",path7);
	    				$("#ImgPr8").attr("src",path8);
	    				$("#ImgPr9").attr("src",path9);
	    			}  
		    	} 
		    	
			});
		}else{
			$('#edit').hide();
		}
		$('#busid').combogrid({
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
		$('#mccid').combogrid({
			url : '${ctx}/sysAdmin/merchant_searchMCC.action',
			idField:'MCCCODE',
			textField:'MCCNAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'MCCCODE',title:'id',width:150},
				{field:'MCCNAME',title:'名称',width:150}
			]]
		});
		//商户基本信息工单申请图片预览
		$("#materialUpLoad3File").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#materialUpLoad4File").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#materialUpLoad5File").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#registryUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		$("#photoUpLoadFile").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		$("#bigdealUpLoadFile").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		$("#materialUpLoad2File").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 }); 
		$("#posBackImgFile").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		$("#bupLoadFile").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
	}); 
	
	$('#registryUpLoadFile').change(function(){
		var contact = document.getElementById('registryUpLoadFile').value;
		document.getElementById("registryUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#photoUpLoadFile').change(function(){
		var contact = document.getElementById('photoUpLoadFile').value;
		document.getElementById("photoUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#bigdealUpLoadFile').change(function(){
		var contact = document.getElementById('bigdealUpLoadFile').value;
		document.getElementById("bigdealUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
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

	$('#materialUpLoad6File').change(function(){
		var contact = document.getElementById('materialUpLoad6File').value;
		document.getElementById("materialUpLoad6").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad7File').change(function(){
		var contact = document.getElementById('materialUpLoad7File').value;
		document.getElementById("materialUpLoad7").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#posBackImgFile').change(function(){
		var contact = document.getElementById('posBackImgFile').value;
		document.getElementById("posBackImg").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#bupLoadFile').change(function(){
		var contact = document.getElementById('bupLoadFile').value;
		document.getElementById("bupLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});

	function searchProvince(){
		$("#city").empty();
		searchCity();
		$("#province").append("<option value='"+$("#province").find("option:selected").text()+"' style='display: none'>"+$("#province").find("option:selected").text()+"</option>");
		$("#province").val($("#province").find("option:selected").text());
		//alert($("#province").val()+" 8 "+$("#province").find("option:selected").text());
	}

	
	function searchCity(){
		//alert($("#province").val()+" 1 "+$('#sendInfo #province option:selected').text()+" 2 "+$('#province').find("option:selected").text()+' v');
		if($("#province").val()==null||$("#province").val()==''){
			$.messager.confirm('提醒','请先输入省份',function(result) {
						if (result) {
							//$("#sendInfo").show();
						}
			});
		}else{
			//class="easyui-combogrid"
			//$("#city").attr("class","easyui-combogrid");
			$('#city').combogrid({
			url : '${ctx}/phone/phoneMerchantInfo_searchAreaCode.action?PROVINCE_CODE='+$("#province").val()+'&type=1',
			idField:'AREA_NAME',
			textField:'AREA_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				//{field:'AREA_CODE',title:'区号',width:150},
				{field:'AREA_NAME',title:'名称',width:150}
			]]
		});
		}
	}
	
	function bankProvince(){
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
	}	
	
	function dealAmtShow(){
 		if(($('#feeAmt').length>0 && $('#feeAmt').val()!="") && ($('#bankFeeRate').length>0 && $('#bankFeeRate').val()!="")){
			var bankFeeRate = $('#bankFeeRate').val() / 100;
			var dealAmt = $('#feeAmt').val() / bankFeeRate;
			$('#maxAmt').val(Math.floor(dealAmt));
		}
	}
	
	$.extend($.fn.validatebox.defaults.rules,{
		payBankIdValidator:{
			validator : function(value) {
	            return /^\d{12}$/i.test(value); 
	        }, 
	        message : '必须是十二位数字！' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
		return utilImg(value); 
	        }, 
	        message : '图片格式不正确' 
		}
	});
	function utilImg(value){
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
	$.extend($.fn.validatebox.defaults.rules, {
		idValidator : {// 验证身份证号 
        	validator : function(value) {   
        	    return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);   
        	},   
        	message : '身份证号格式不正确！'   
    	}
	});

	$.extend($.fn.validatebox.defaults.rules, {
		mobile : { // 验证手机号码
        validator : function(value) {
            return /^(13|15|18|14|17)\d{9}$/i.test(value);
        },
        message : '手机号码格式不正确！'
    }
	});

	$.extend($.fn.validatebox.defaults.rules, {
		telephoneValidator:{ // 验证固定电话号码
			validator : function(value) {
	            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
	        }, 
	        message : '固定电话号码格式不正确！正确格式如：010-88888888' 
		}
		});
	$.extend($.fn.validatebox.defaults.rules, {
		rateValidator:{
			validator : function(value) {
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '费率格式不正确！' 
		}
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

	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
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

	$.extend($.fn.validatebox.defaults.rules, {
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    	}
    	});

	$('#baddr').change(function(){
		$('#raddr').val($('#baddr').val());
	});
    
</script>
</head>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:500px; padding-top:10px;">
		<%-- 
			传统商户基本信息工单from表单
		--%>
		<form id="sendInfo" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 900px;">
				<legend>商户基本信息</legend>
				<table class="table">
					<tr>
						<th>上级商户：</th>
						<td colspan="3">
							<select name="parentMID" id="parentMID" class="easyui-combogrid" data-options="  
						            url : '${ctx}/sysAdmin/merchant_searchMerchantInfo.action',
									idField:'mid',
									textField:'rname',
									mode:'remote',
									width:350,
									fitColumns:true,
									pagination : true,
				                    rownumbers:true,  
				                    collapsible:false,  
				                    fit: false,  
				                    pageSize: 10,  
				                    pageList: [10,15],
									columns:[[ 
										{field:'mid',title:'MID',width:150,hidden:true},
										{field:'rname',title:'名称',width:150}
									]]">
							</select>
						</td>
	   				</tr>
					<tr>
	   					<th>商户经营名称: </th>
			   			<td>
			   				<input type="text" name="rname" style="width:200px;" class="easyui-validatebox" maxlength="50" data-options="validType:'spaceValidator',required:true"/><font color="red">&nbsp;*</font>
			   			</td>
	   					<th>实际经营项目：</th>
	   					<td>
	   						<input type="text" name="businessScope" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="25"/><font color="red">&nbsp;*</font>
	   					</td>
	   				</tr>
					<tr>
						<th>行业编码：</th>
		   				<td>
		   					<select id="mccid" name="mccid" data-options="editable:false" class="easyui-combogrid easyui-validatebox" required="true" style="width:205px;"></select><font color="red">&nbsp;*</font>
		   				</td>
		   				<th>申请人姓名：</th>
	   					<td>
	   						<input type="text" name="bankAccName" style="width:200px;"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="10"/><font color="red">&nbsp;*</font>
	   					</td>
		   			</tr>
		   			<tr>
		   				<th>申请人身份证号码：</th>
			   			<td>
			   				<input type="text" name="accNum" style="width:200px;"  class="easyui-validatebox" data-options="required:true,validType:'idValidator'" maxlength="20"/><font color="red">&nbsp;*</font>
			   			</td>
			   			<th>申请人身份证有效期：</th>
				   		<td>
				   			<input type="text" name="accExpdate" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font>
				   		</td>
		   			</tr>
		   			<tr>
						<th>营业地址：</th>
						<td>
						省：<select id="province" name="province"  data-options="editable:false,required:true" style="width:80px;" onchange="searchProvince()">
								<option value=""></option>
								<option value="11">北京市</option>
								<option value="90">北分交行</option>
								<option value="12">天津市</option>
								<option value="13">河北省</option>
								<option value="14">山西省</option>
								<option value="15">内蒙古自治区</option>
								<option value="21">辽宁省</option>
								<option value="22">吉林省</option>
								<option value="23">黑龙江省</option>
								<option value="31">上海市</option>
								<option value="32">江苏省</option>
								<option value="33">浙江省</option>
								<option value="34">安徽省</option>
								<option value="35">福建省</option>
								<option value="36">江西省</option>
								<option value="37">山东省</option>
								<option value="41">河南省</option>
								<option value="42">湖北省</option>
								<option value="43">湖南省</option>
								<option value="44">广东省</option>
								<option value="45">广西壮族自治区</option>
								<option value="46">海南省</option>
								<option value="50">重庆市</option>
								<option value="51">四川省</option>
								<option value="52">贵州省</option>
								<option value="53">云南省</option>
								<option value="54">西藏自治区</option>
								<option value="61">陕西省</option>
								<option value="62">甘肃省</option>
								<option value="63">青海省</option>
								<option value="64">宁夏回族自治区</option>
								<option value="65">新疆维吾尔自治区</option>
								<option value="66">新疆兵团外经贸局</option>
								<option value="99">其他</option>
							</select>
	   					市：<select id="city" name="city"  data-options="editable:false,required:true" style="width:120px" onclick="searchCity()"></select>
	   						<input type="text" name="baddr" id="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font>
	   					</td> 
	   					<th>合同编号：</th>
		   				<td >
		   					<input type="text" name="contractNo" style="width:200px;" maxlength="20"/>
		   				</td>
					</tr>
					<tr>
						<th>会员宝登陆账号：</th>
						<td><input type="text" name="hybPhone" style="width:200px;" maxlength="20"/></td> 
					</tr>
					<tr>
		   				<th>备注：</th>
		   					<td colspan="3">
	    					<textarea rows="6" cols="38" style="resize:none;"  name="remarks" maxlength="100"></textarea>
	    				</td>
					 </tr>
				</table>
			</fieldset>
			<fieldset style="width: 900px;">
				<legend>结算信息</legend>
				<table class="table">
					<tr>
						<th>结算时间点：</th>
				   		<td>
				   			<select name="settleRange" class="easyui-combobox" data-options="editable:false" style="width:105px;">
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
				   		<th>结算账号：</th>
				   		<td>
				   			<input type="text" name="bankAccNo" style="width:182px;" class="easyui-validatebox" data-options="required:true,validType:'cardValidator'" maxlength="30"/><font color="red">&nbsp;*</font>
				   		</td>
					</tr>
					<tr id="add"> 
						<th>开户行名称：</th>
				   		<td colspan="3">
				   			<select name="bankSendCode"  data-options="editable:false,required:true" style="width:205px;">
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
				   			<select id="bankProvinceCode" name="bankProvinceCode"  data-options="editable:false,required:true" style="width:205px;" onclick="bankProvince()"></select>
				   			<input type="text" name="bankName" style="width:200px;"  data-options="required:true" maxlength="100"/><font color="red">&nbsp;*</font>
				   		</td>  
					</tr>
					<tr id="edit">
						<th>开户银行：</th>
				   		<td colspan="3">
				   			<input type="text" id="bankBranch" name="bankBranch" style="width:600px;"  data-options="required:true" maxlength="100"/><font color="red">&nbsp;*</font>
				   		</td>
				   	</tr>
					<tr>
						<th>商户结算费率：</th>
						<td colspan="3">
							借记卡费率：<input type="text" name="bankFeeRate" id="bankFeeRate" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							&nbsp;&nbsp;
							借记卡手续费：<input type="text" name="feeAmt" id="feeAmt" style="width:50px;"  class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/>元<font color="red">&nbsp;*</font>
								&nbsp;&nbsp;&nbsp;
							贷记卡费率：<input type="text" name="creditBankRate" id="creditBankRate" style="width:50px;"  class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealcreditShow()"/>%<font color="red">&nbsp;*</font>
							<br/><br/>
							微信费率：<input type="text" name="scanRate" id="scanRate" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							&nbsp;&nbsp;
							银联二维码费率：<input type="text" name="scanRate1" id="scanRate1" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							&nbsp;&nbsp;
							支付宝费率：<input type="text" name="scanRate2" id="scanRate2" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							<!-- 贷记卡手续费：<input type="text" name="creditFeeamt" id="creditFeeamt" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/>元<font color="red">&nbsp;*</font>   -->
						</td>
					</tr>
				   	<tr>
						<th>支付系统行号：</th>
				   		<td colspan="3">
				   		<input type="text" name="payBankId" style="width:200px;" class="easyui-validatebox"  data-options="required:true,validType:'payBankIdValidator'" maxlength="20"/><font color="red">&nbsp;*支付系统行号作为结算信息依据，填写错误会影响成功付款!</font>
				   		</td>
					</tr>
				</table>
			</fieldset>
			<fieldset style="width: 900px;">
				<legend>通讯信息</legend>
				<table class="table">
					<tr>
						<th>联系人：</th>
				   		<td>
				   			<input type="text" name="contactPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="10"/><font color="red">&nbsp;*</font>
				   		</td>
				   		<th>职务：</th>
				   		<td>
				   			<input type="text" name="contactAddress" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font>
				   		</td>
					</tr>
					<tr>
						<th>固话：</th>
		   				<td>
		   					<input type="text" name="contactTel" style="width:200px;" maxlength="20"  class="easyui-validatebox" data-options="validType:'telephoneValidator'"/>
		   				</td>
						<th>手机号：</th>
		   				<td>
		   					<input type="text" name="contactPhone" style="width:200px;" maxlength="20" class="easyui-numberbox easyui-validatebox" data-options="required:true,validType:'mobile'"/><font color="red">&nbsp;*</font>
		   				</td>
					</tr>
					<tr>
						<th>拓展销售：</th>
	   					<td>
	   						<select id="busid" name="busid" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
	   					</td>
					</tr>
				</table>
			</fieldset> 
			<fieldset style="width:900px;">
				<legend>图片信息</legend>
				<table class="table">
					<tr>
						<th>申请人身份证正面照片：</th>
			   			<td>
			   				<input type="file" name="materialUpLoad3File" id="materialUpLoad3File"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr1" width="120" height="120" /></div>
			   			</td>
			   			<th>申请人身份证反面照片：</th>
			   			<td>
			   				<input type="file" name="materialUpLoad4File" id="materialUpLoad4File"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr2" width="120" height="120" /></div>
			   			</td>
					</tr>
					<tr>
						<th>申请人手持身份证照片：</th>
				   		<td>
				   			<input type="file" name="materialUpLoad5File" id="materialUpLoad5File"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr3" width="120" height="120" /></div>
				   		</td>
				   		<th>店面门头照片：</th>
			   			<td>
			   				<input type="file" name="registryUpLoadFile" id="registryUpLoadFile"  data-options="validType:'imgValidator'" /><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr4" width="120" height="120" /></div>
			   			</td>
					</tr>
					<tr>
						<th>店内经营照片：</th>
			   			<td>
			   				<input type="file" name="photoUpLoadFile" id="photoUpLoadFile"  data-options="validType:'imgValidator'" /><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr5" width="120" height="120" /></div>
			   			</td>
			   			<th>大协议照片：</th>
				   		<td>
				   			<input type="file" name="bigdealUpLoadFile" id="bigdealUpLoadFile"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr6" width="120" height="120" /></div>
				   		</td>
					</tr>
					<tr>
						<th>结算卡正面照片：</th>
				   		<td>
				   			<input type="file" name="materialUpLoad2File" id="materialUpLoad2File"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr7" width="120" height="120" /></div>
				   		</td>
				   		<th>安装POS机背面照片：</th>
				   		<td>
				   			<input type="file" name="posBackImgFile" id="posBackImgFile"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr8" width="120" height="120" /></div>
				   		</td>
					</tr>
					<tr>
						<th>租赁协议照片：</th>
				   		<td>
				   			<input type="file" name="bupLoadFile" id="bupLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr9" width="120" height="120" /></div>
				   		</td>
					</tr>
				</table>
			</fieldset>
			<input type="hidden" name="registryUpLoad" id="registryUpLoad">
			<input type="hidden" name="photoUpLoad" id="photoUpLoad">
			<input type="hidden" name="bigdealUpLoad" id="bigdealUpLoad">
			<input type="hidden" name="materialUpLoad2" id="materialUpLoad2">
			<input type="hidden" name="materialUpLoad3" id="materialUpLoad3">
			<input type="hidden" name="materialUpLoad4" id="materialUpLoad4">
			<input type="hidden" name="materialUpLoad5" id="materialUpLoad5">
			<input type="hidden" name="bupLoad" id="bupLoad">
			<input type="hidden" name="posBackImg" id="posBackImg">
			<input type="hidden" name="materialUpLoad6" id="materialUpLoad6">
			<input type="hidden" name="materialUpLoad7" id="materialUpLoad7">
			<input type="hidden" name="areaCode" id="areaCode">
			<input type="hidden" name="legalType" id="legalType" value="1">
			<input type="hidden" name="bno" id="bno" value="未知">
			<input type="hidden" name="legalPerson" id="legalPerson" value="无">
			<input type="hidden" name="legalNum" id="legalNum" value="无">
			<input type="hidden" name="legalExpdate" id="legalExpdate" value="无">
			<input type="hidden" name="bmid" id="bmid">
			<input type="hidden" name="mid" id="mid">
			<input type="hidden" name="approveStatus" id="approveStatus">
			<input type="hidden" name="isM35" id="isM35" value="2">
			<input type="hidden" name="valuationType" id="valuationType" value="1">
			<input type="hidden" name="raddr" id="raddr">
			<input type="hidden" name="merchantType" id="merchantType" value="2">
			<input type="hidden" name="isForeign" id="isForeign" value="2">
			<input type="hidden" name="accType" id="accType" value="2">
			<input type="hidden" name="exchangTime" id="exchangTime">
		</form>
	</div>
	
	<%-- 
			商户基本信息工单申请div
	--%>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_TaskDetail1_datagrid"></table>
	</div>
	
	<%-- 
			银行基本信息工单申请div
	--%>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_TaskDetail2_datagrid"></table>
	</div>
	
	<%-- 
			商户费率信息工单申请div
	--%>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_user_datagrid"></table>
	</div>
</div>

