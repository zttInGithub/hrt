<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	

	$.extend($.fn.validatebox.defaults.rules,{
		payBankIdValidator:{
			validator : function(value) {
	            return /^\d{?}$/i.test(value); 
	        }, 
	        message : '必须是整数！' 
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
		ints : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数，并确保格式正确'   
    	}
    	});

</script>
</head>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:200px; padding-top:10px;">
		<form id="credit30001" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 900px;">
				<legend>授信申请基本信息</legend>
				<table class="table">
					<tr>
	   					<th>贷款方式: </th>
			   			<td>
			   				<select id="creType" name="creType"  data-options="editable:false,required:true" style="width:80px;">
								<option value="0">现金</option>
								<option value="1" selected="selected">设备</option>
							</select>
			   			</td>
	   					<th>贷款金额：</th>
	   					<td>
	   						<input type="text" name="creAmt" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="25"/><font color="red">&nbsp;*</font>
	   					</td>
	   				</tr>
					<tr>
						<th>贷款期限：</th>
		   				<td>
		   					<!-- <input type="text" name="creTimeLimit" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'ints'" maxlength="25"/>/月</input><font color="red">&nbsp;*</font> -->
		   					<select name="creTimeLimit" style="width:200px;" data-options="required:true">
		   						<option value="1" selected="selected">1</option>
		   						<option value="3" >3</option>
		   						<option value="6" >6</option>
		   						<option value="12" >12</option>
		   					</select>
		   					/月</input><font color="red">&nbsp;*</font>
		   				</td>
		   				<th>申请人姓名：</th>
	   					<td>
	   						<input type="text" name="proposer" style="width:200px;"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="10"/><font color="red">&nbsp;*</font>
	   					</td>
		   			</tr>
		   			<tr>
						<th>申请人邮箱：</th>
		   				<td>
		   					<input type="text" name="propemail" style="width:200px;" maxlength="20"  class="easyui-validatebox" data-options="validType:'spaceValidator'"/>
		   				</td>
						<th>申请人手机号：</th>
		   				<td>
		   					<input type="text" name="prophone" style="width:200px;" maxlength="20" class="easyui-numberbox easyui-validatebox" data-options="required:true,validType:'mobile'"/><font color="red">&nbsp;*</font>
		   				</td>
					</tr>
				</table>
			</fieldset> 
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

