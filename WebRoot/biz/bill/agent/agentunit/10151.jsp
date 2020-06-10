<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
		
		$('#maintainUserId').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID',
			textField:'SALENAME',
			fitColumns:true,
			columns:[[ 
				{field:'SALENAME',title:'销售姓名',width:150},
				{field:'UNNO',title:'所属机构',width:150},
				{field:'BUSID',title:'id',width:150,hidden:true}
			]]
		});
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
 
<form id="sysAdmin_10151_editForm" method="post">
	<fieldset>
		<legend>基础信息</legend>
		<table class="table">
			<tr>
	    		<th>代理商名称：</th>
	    		<td><input type="text" name="agentName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
	
	    		<th>经营地址：</th>
	    		<td><input type="text" name="baddr" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="200"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>法人：</th>
	    		<td><input type="text" name="legalPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	
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
	    	</tr>
	    	<tr>
	    		<th>法人证件号码：</th>
	    		<td><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
				
				<th>签约人员：</th>
	    		<td>
	    			<select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="required:false,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
	    		</td> 
	    	</tr>
	    	<tr>
	    		<th>营业执照编号：</th>
	    		<td><input type="text" name="bno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	
	    		<th>组织机构代码：</th>
	    		<td><input type="text" name="rno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>税务登记证号：</th>
	    		<td><input type="text" name="registryNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	
	    		<th>银行开户许可证号：</th>
	    		<td><input type="text" name="bankOpenAcc" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>风险保障金：</th>
    			<td><input type="text" name="riskAmount" style="width:200px;" value="0" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="18"/></td>
    		
    			<th>签约合同编号：</th>
	    		<td><input type="text" name="contractNo" style="width:200px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"/></td>
	    	</tr>
    		 <tr>
	    		<th>维护人员：</th>
	    		<td>
	    			<select id="maintainUserId" name="maintainUserId" class="easyui-combogrid" data-options="required:false,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
	    		</td>
	    	</tr> 
		</table>
	</fieldset>
	
	<fieldset>
		<legend>结算信息</legend>
		<table class="table">
			<tr>
	    		<th>开户类型：</th>
	    		<td>
	    			<input type="radio" name="accType" value="1" checked="checked"/>对公
					<input type="radio" name="accType" value="2" />对私
	    		</td>

	    		<th>开户银行：</th>
	    		<td><input type="text" name="bankBranch" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="200"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>开户银行账号：</th>
	    		<td><input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>

	    		<th>开户账号名称：</th>
	    		<td><input type="text" name="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>是否为交通银行：</th>
	    		<td>
					<input type="radio" name="bankType" value="1" checked="checked"/>交通银行
					<input type="radio" name="bankType" value="2" />非交通银行
				</td>

	    		<th>开户银行所在地类别：</th>
	    		<td>
	    			<input type="radio" name="areaType" value="1" checked="checked"/>北京
					<input type="radio" name="areaType" value="2" />非北京
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>开户地：</th>
	    		<td><input type="text" name="bankArea" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
	    	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>联系人信息</legend>
		<table class="table">
			<tr>
	    		<th>负责人：</th>
	    		<td><input type="text" name="contact" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	
	    		<th>负责人固定电话：</th>
	    		<td><input type="text" name="contactTel" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="validType:'spaceValidator'" /></td>
	    	</tr>
	    	<tr>
	    		<th>负责人手机：</th>
	    		<td><input type="text" name="contactPhone" style="width:200px;" maxlength="20" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	
	    		<th>第二联系人：</th>
	    		<td><input type="text" name="secondContact" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>第二联系人固定电话：</th>
	    		<td><input type="text" name="secondContactTel" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="validType:'spaceValidator'"/></td>
	
	    		<th>第二联系人手机：</th>
	    		<td><input type="text" name="secondContactPhone" style="width:200px;" maxlength="20" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>风险调单联系人：</th>
	    		<td><input type="text" name="riskContact" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	
	    		<th>风险调单联系手机：</th>
	    		<td><input type="text" name="riskContactPhone" style="width:200px;" maxlength="20" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>风险调单联系邮箱：</th>
	    		<td><input type="text" name="riskContactMail" style="width:200px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
	
	    		<th>业务联系人：</th>
	    		<td><input type="text" name="businessContacts" style="width:200px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>业务联系手机：</th>
	    		<td><input type="text" name="businessContactsPhone" style="width:200px;" maxlength="20" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font></td>
	
	    		<th>业务联系邮箱：</th>
	    		<td><input type="text" name="businessContactsMail" style="width:200px;" maxlength="30" class="easyui-validatebox" data-options="required:true,validType:'email'"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>备注：</th>
	    		<td colspan="3">
	    			<textarea rows="6" cols="38" style="resize:none;" name="remarks" maxlength="100" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"></textarea><font color="red">&nbsp;*</font>
	    		</td>
	    	</tr>
		</table>
	</fieldset>
	<input type="hidden" name="buid">
	<input type="hidden" name="unno">
</form>  
