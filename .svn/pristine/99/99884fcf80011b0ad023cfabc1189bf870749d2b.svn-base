<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
		var type = <%=request.getParameter("areaType")%>;
		if(type=="4"){
			$("#dailyLimit").val("20000");
			$("#areaType").val("4");
		}else if (type=="5"){
			$("#dailyLimit").val("10000");
			$("#areaType").val("5");
		}else if (type=="8") {
			$("#dailyLimit").val("6000");
			$("#areaType").val("8");
		}else {
			$("#dailyLimit").val("6000");
			$("#areaType").val("6");
		}
		$("#txnLimit").val("0");
	});
	
	function searchareaType(){
		var type = $("#areaType").val();
		if(type=="4"){
			$("#dailyLimit").val("20000");
		}else if (type=="5"){
			$("#dailyLimit").val("10000");
		}else {
			$("#dailyLimit").val("6000");
		}
		$("#txnLimit").val("0");
	}
	
	$.extend($.fn.validatebox.defaults.rules, {
		intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        	    return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    	}
    });
    	
</script>
<form id="sysAdmin_merchant10509_editForm" method="post">
	<table class="table">
		<tr>
			<th style="width:100px">商户类型：</th>
			<td style="width:400px">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- <input type="text" name="areaType" id="areaType" readonly="readonly" style="width:100px;" class="easyui-validatebox"  data-options="editable:true,required:true" maxlength="25"  /> --> 
			<select name="areaType" id="areaType" style="width:110px;" onchange="searchareaType()" >
		   			<option value="4">实体</option>
		   			<option value="5">个人-实体</option>
		   			<option value="6">个人</option>
		   			<option value="8">快捷</option>
		   		</select>
			</td>
		</tr>
		<tr>
			<th style="width:100px">单日限额：</th>
			<td style="width:400px">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="dailyLimit" id="dailyLimit" style="width:100px;" class="easyui-validatebox"  data-options="validType:'intOrFloat',editable:false,required:true" maxlength="25"  /> 元
			</td>
		</tr>
		<tr>
		   	<th style="width:100px">单笔限额：</th>
		   	<td style="width:400px">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="txnLimit" id="txnLimit" style="width:100px;" class="easyui-validatebox"  data-options="validType:'intOrFloat',editable:false,required:true" maxlength="25"  /> 元
		   	</td>
		</tr>
		<tr>
		   	<th style="width:100px">退回原因：</th>
		   	<td style="width:400px">
		   		<input type="radio" name="remarks" value="1" checked="checked">
		   		<select name="processContext">
		   			<option value="照片不清晰">照片不清晰</option>
		   			<option value="入账非执照上标注经营者">入账非执照上标注经营者</option>
		   			<option value="复印件未加盖公章">复印件未加盖公章</option>
		   			<option value="未提供营业执照">未提供营业执照</option>
		   			<option value="未提供法人身份证信息">未提供法人身份证信息</option>
		   			<option value="入账账户名称错误">入账账户名称错误</option>
		   			<option value="商户名称不符合标准">商户名称不符合标准</option>
		   			<option value="经营范围错误">经营范围错误</option>
		   			<option value="请提供加公章的入账授权书（入非法人或入法人个人账户）">请提供加公章的入账授权书（入非法人或入法人个人账户）</option>
		   			<option value="单一营业执照只能申请最多5个聚合商户">单一营业执照只能申请最多5个聚合商户</option>
		   		</select>
		   	</td>
		</tr>
		<tr>
		   	<th style="width:100px"></th>
		   	<td style="width:400px">
		   		<input type="radio" name="remarks" value="2">
	   			<input type="text" name="processContext1"  style="width:330px;" class="easyui-validatebox"  data-options="editable:false" maxlength="50"  />
		   	</td>
		</tr>
		<tr>
		   	<td align="center" colspan="2">单笔限额为0时，默认不限单笔</td>
		</tr>
	</table>
</form>  

