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
		    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
			    		var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
			    		var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
			    		var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0]);
			    		var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
			    		var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[9]);
			    		var path7='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[10]);
			    		var path8='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[11]);
			    		var path9='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
			    		var path10='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[7]);
			    		var path11='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[6]);
			    		var path12='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[8]);
		    			$("#ImgPr8").attr("src",path1);
	    				$("#ImgPr9").attr("src",path2);
	    				$("#ImgPr10").attr("src",path3);
	    				$("#ImgPr11").attr("src",path4);
	    				$("#ImgPr12").attr("src",path5);
	    				$("#ImgPr13").attr("src",path6);
	    				$("#ImgPr14").attr("src",path7);
	    				$("#ImgPr15").attr("src",path8);
	    				$("#ImgPr16").attr("src",path9);
	    				$("#ImgPr17").attr("src",path10);
	    				$("#ImgPr18").attr("src",path11);
	    				$("#ImgPr19").attr("src",path12);
	    			}  
		    	} 
		    	
			});
			
		}else{
			$('#edit').hide();
		}

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
		$("#sendInfo2 #registryUpLoadFile").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });
		$("#sendInfo2 #photoUpLoadFile").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
		$("#sendInfo2 #bupLoadFile").uploadPreview({ Img: "ImgPr10", Width: 120, Height: 120 });
		$("#sendInfo2 #legalUploadFile").uploadPreview({ Img: "ImgPr11", Width: 120, Height: 120 });
		$("#sendInfo2 #materialUpLoadFile").uploadPreview({ Img: "ImgPr12", Width: 120, Height: 120 });
		$("#sendInfo2 #materialUpLoad3File").uploadPreview({ Img: "ImgPr13", Width: 120, Height: 120 });
		$("#sendInfo2 #materialUpLoad4File").uploadPreview({ Img: "ImgPr14", Width: 120, Height: 120 });
		$("#sendInfo2 #materialUpLoad5File").uploadPreview({ Img: "ImgPr15", Width: 120, Height: 120 });
		$("#sendInfo2 #rupLoadFile").uploadPreview({ Img: "ImgPr16", Width: 120, Height: 120 });
		$("#sendInfo2 #materialUpLoad1File").uploadPreview({ Img: "ImgPr17", Width: 120, Height: 120 });
		$("#sendInfo2 #bigdealUpLoadFile").uploadPreview({ Img: "ImgPr18", Width: 120, Height: 120 });
		$("#sendInfo2 #materialUpLoad2File").uploadPreview({ Img: "ImgPr19", Width: 120, Height: 120 });
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

	$('#materialUpLoad6File').change(function(){
		var contact = document.getElementById('materialUpLoad6File').value;
		document.getElementById("materialUpLoad6").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#materialUpLoad7File').change(function(){
		var contact = document.getElementById('materialUpLoad7File').value;
		document.getElementById("materialUpLoad7").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
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
            return /^(13|15|18)\d{9}$/i.test(value);
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

</script>
</head>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:500px; padding-top:10px;">	
		<%-- 
			企业商户基本信息工单from表单
		--%>
		<form id="sendInfo2" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 900px;">
				<legend>商户基本信息修改</legend>
				<table class="table">
					<!--<tr>
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
	   				</tr>-->
	   				<tr>
	   					<th>营业执照名称：</th>
		   				<td><input name="csrftoken" type="hidden" value=<%=(String)request.getSession().getAttribute("csrftoken") %>/>
		   					<input type="text" name="rname" style="width:200px;" class="easyui-validatebox easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/><font color="red">&nbsp;*</font>
		   				</td>
		   				<th>商户经营（小票）名称：</th>
			   			<td>
			   				<input type="text" name="printName" style="width:200px;" maxlength="50" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" /><font color="red">&nbsp;*</font>
			   			</td>
	   				</tr>
					<tr>
	   					<th>实际经营项目：</th>
	   					<td>
	   						<input type="text" name="businessScope" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="25"/><font color="red">&nbsp;*</font>
	   					</td>
	   					<th>行业编码：</th>
		   				<td>
		   					<select id="mccid" name="mccid" data-options="editable:false" class="easyui-combogrid easyui-validatebox" data-options="required:true" style="width:205px;"></select><font color="red">&nbsp;*</font>
		   				</td>
	   				</tr>
	   				<tr>
	   					<th>营业执照编号：</th>
			   			<td>
			   				<input type="text" name="bno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font>
			   			</td>
	   					<th>营业执照有效期：</th>
	   					<td>
	   						<input type="text" name="bnoExpdate" style="width:200px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
	   					</td>
	   				</tr>
	   				<tr>
	   					<th>商户注册地址：</th>
			   			<td>
			   				<input type="text" name="raddr" id="raddr" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/><font color="red">&nbsp;*</font>
			   			</td>
	   					<th>营业地址：</th>
						<td>
						省：<select id="province" name="province"  data-options="editable:false,required:true" style="width:80px;" onchange="searchProvince()">
								<option value="11">北京市</option>
							</select>
	   					市：<select id="city" name="city"  data-options="editable:false,required:true" style="width:120px" onclick="searchCity()"></select>
	   						<input type="text" name="baddr" id="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="50"/><font color="red">&nbsp;*</font>
	   					</td>
	   				</tr>
	   				<tr>
	   					<th>法人姓名：</th>
			   			<td>
			   				<input type="text" name="legalPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="10"/><font color="red">&nbsp;*</font>
			   			</td>
			   			<th>法人证件类型：</th>
				   		<td>
				   			<select name="legalType" class="easyui-combobox easyui-validatebox" data-options="required:true,panelHeight:'auto',editable:false"  style="width:205px;">
			    				<option value="1" selected="selected">身份证</option>
			    				<option value="2">军官证</option>
			    				<option value="3">护照</option>
			    				<option value="4">港澳通行证</option>
			    				<option value="5">其他</option>
			    			</select>
			    			<font color="red">&nbsp;*</font>
				   		</td>
	   				</tr>
	   				<tr>
	   					<th>法人证件号码：</th>
			   			<td>
			   				<input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font>
			   			</td>
			   			<th>法人证件有效期：</th>
				   		<td>
				   			<input type="text" name="legalExpdate" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font>
				   		</td>
	   				</tr>
	   				<tr>
	   					<th>合同编号：</th>
				   		<td>
				   			<input type="text" name="contractNo" style="width:200px;" maxlength="20"/>
				   		</td>
						<th>智能POS登陆账号：</th>
						<td>
							<input type="text" name="hybPhone" style="width:200px;" maxlength="20"/>
						</td> 
	   				</tr>
	   				<tr>
		   				<th>备注：</th>
		   					<td colspan="3">
	    					<textarea rows="6" cols="38" style="resize:none;" name="remarks" maxlength="100" ></textarea>
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
				   			<select name="settleRange" class="easyui-combobox" data-options="editable:false" style="width:110px;">
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
				   		<th>账户性质（对公/对私）：</th>
				   		<td colspan="3">
				   			<input type="radio" name="accType" id="accContrary" value="1" class="easyui-validatebox" data-options="required:true"/>对公
							<input type="radio" name="accType" id="accPrivate" value="2" checked="checked" />对私
							<font color="red">&nbsp;*</font>
				   		</td>
					</tr>
					<tr>
						<th> 账户名称：</th>
				   		<td>
				   			<input type="text" name="bankAccName"  id="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"   maxlength="50"/><font color="red">&nbsp;*</font>
				   		</td>
				   		<th>开户账号：</th>
				   		<td>
				   			<input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'cardValidator'" maxlength="30"/><font color="red">&nbsp;*</font>
				   		</td>
					</tr>
					<tr id="add">
						<th>开户行名称：</th>
				   		<td colspan="3">
				   			<select name="bankSendCode" id="bankSendCode"  data-options="editable:false,required:true" style="width:205px;">
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
				   			<input type="text" name="bankName" style="width:200px;"   data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font>
				   		</td>
					</tr>
					<tr id="edit">
						<th>开户银行：</th>
				   		<td colspan="3">
				   			<input type="text" id="bankBranch" name="bankBranch" style="width:600px;" data-options="required:true" maxlength="100"/><font color="red">&nbsp;*</font>
				   		</td>
				   	</tr>
					<tr id="accRequired">
				   		<th>入账人证件号：</th>
						<td><input type="text" name="accNum" id="accNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'idValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
				   		<th>入账人证件有效期：</th>
				   		<td><input type="text" name="accExpdate" id="accExpdate"  style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"   maxlength="50"/><font color="red">&nbsp;*</font></td>
				   	</tr>
				   	<tr>
						<th>结算费率：</th>
						<td colspan="3">
							借记卡费率：<input type="text" name="bankFeeRate" id="bankFeeRate" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							借记卡手续费：<input type="text" name="feeAmt" id="feeAmt" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/>元<font color="red">&nbsp;*</font>
								&nbsp;&nbsp;&nbsp;
							贷记卡费率：<input type="text" name="creditBankRate" id="creditBankRate" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							扫码支付费率：<input type="text" name="scanRate" id="scanRate" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'" maxlength="18" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
							<!--贷记卡手续费：<input type="text" name="creditFeeamt" id="creditFeeamt" style="width:50px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/>元<font color="red">&nbsp;*</font>-->
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
		   					<input type="text" name="contactTel" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="validType:'telephoneValidator'"/>
		   				</td>
						<th>手机号：</th>
		   				<td>
		   					<input type="text" name="contactPhone" style="width:200px;" maxlength="20" class="easyui-numberbox easyui-validatebox" data-options="required:true,validType:'mobile'"/><font color="red">&nbsp;*</font>
		   				</td>
					</tr>
					<tr>
						<th>拓展编号：</th>
	   					<td>
	   						<select class="easyui-combogrid" name="unno" id="unno" style="width:200px;" maxlength="6" data-options="  
				            required:true,
				            url : '${ctx}/sysAdmin/merchant_queryUnnoCodeInfobaodan.action',
							idField:'CODE',
							textField:'CODE',
							mode:'remote',
							width:300,
							fitColumns:true,
							pagination : true,
		                    rownumbers:true,  
		                    collapsible:false,  
		                    fit: false,  
		                    pageSize: 10,  
		                    pageList: [10,15],
							columns:[[ 
								{field:'CODENAME',title:'拓展名称',width:150},
								{field:'CODE',title:'拓展编码',width:150},
								{field:'ACID',title:'id',width:150,hidden:true}
							]]"></select><font color="red"></font>
	   					</td>
	   					<th>拓展名称：</th>
	   					<td>
		   					<input type="text" name="parentMID" style="width:200px;" maxlength="15" />
	   					</td>
					</tr>
				</table>
			</fieldset> 
			<fieldset style="width: 900px;">
				<legend>照片信息</legend>
				<table class="table">
					<tr>
						<th>店面门头照片：</th>
			   			<td>
			   				<input type="file" id="registryUpLoadFile" name="registryUpLoadFile"  data-options="validType:'imgValidator'" /><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr8" width="120" height="120" /></div>
			   			</td>
			   			<th>店内经营照片：</th>
			   			<td>
			   				<input type="file"  id="photoUpLoadFile" name="photoUpLoadFile"  data-options="required:true,validType:'imgValidator'" /><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr9" width="120" height="120" /></div>
			   			</td>
					</tr>
					<tr>
						<th>营业执照：</th>
			   			<td>
			   				<input type="file" id="bupLoadFile" name="bupLoadFile"  data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr10" width="120" height="120" /></div>
			   			</td>
			   			<th>法人证件正面：</th>
			   			<td>
			   				<input type="file"  id="legalUploadFile" name="legalUploadFile"  data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr11" width="120" height="120" /></div>
			   			</td>
					</tr>
					<tr>
						<th>法人证件反面：</th>
			   			<td>
			   				<input type="file"  id="materialUpLoadFile" name="materialUpLoadFile"  data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr12" width="120" height="120" /></div>
			   			</td>
			   			<th>入账人证件正面：</th>
				   		<td>
				   			<input type="file" name="materialUpLoad3File" id="materialUpLoad3File"  data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr13" width="120" height="120" /></div>
				   		</td>
					</tr>
					<tr>
						<th>入账人证件反面：</th>
			   			<td>
			   				<input type="file" name="materialUpLoad4File" id="materialUpLoad4File"  data-options="required:true,validType:'imgValidator'" /><font color="red">&nbsp;*</font>
			   				<div><img id="ImgPr14" width="120" height="120" /></div>
			   			</td>
			   			<th>入账人手持身份证正面：</th>
				   		<td>
				   			<input type="file" name="materialUpLoad5File" id="materialUpLoad5File"  data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr15" width="120" height="120" /></div>
				   		</td>
					</tr>
					<tr>
						<th>结算账户证明（银行卡）正面：</th>
				   		<td>
				   			<input type="file" name="rupLoadFile" id="rupLoadFile"  data-options="required:true,validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr16" width="120" height="120" /></div>
				   		</td>
				   		<th>POS结算授权书：</th>
				   		<td>
				   			<input type="file" name="materialUpLoad1File" id="materialUpLoad1File"  data-options="required:true,validType:'imgValidator'"/>
				   			<div><img id="ImgPr17" width="120" height="120" /></div>
				   		</td>
					</tr>
					<tr>
						<th>大协议照片：</th>
				   		<td>
				   			<input type="file" name="bigdealUpLoadFile" id="bigdealUpLoadFile"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr18" width="120" height="120" /></div>
				   		</td>
				   		<th>企业信用网截图：</th>
				   		<td>
				   			<input type="file" name="materialUpLoad2File" id="materialUpLoad2File"  data-options="validType:'imgValidator'"/><font color="red">&nbsp;*</font>
				   			<div><img id="ImgPr19" width="120" height="120" /></div>
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
			<input type="hidden" name="materialUpLoad6" id="materialUpLoad6">
			<input type="hidden" name="materialUpLoad7" id="materialUpLoad7">
			<input type="hidden" name="areaCode" id="areaCode">
			<input type="hidden" name="bmid" id="bmid">
			<input type="hidden" name="mid" id="mid">
			<input type="hidden" name="approveStatus" id="approveStatus">
			<input type="hidden" name="isM35" id="isM35" value="3">
			<input type="hidden" name="merchantType" id="merchantType" value="2">
			<input type="hidden" name="isForeign" id="isForeign" value="2">
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