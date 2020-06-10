<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

    <!-- 代理商新增页面 -->
<script type="text/javascript">
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
<div style='margin: 30px 0px'>
<form id="sysAdmin_30011_addForm" method="post">
	<p style='text-align:center;color:red;font-size:15px'>温馨提示：本系统展示的最高授信额度不代表最终授信额度，最终授信额度由贷款公司根据您的实际经营情况以及信用情况评估产生。</p>
		<table class="table">
			<tr>
	    		<th>经营性质：</th>
	    		<td>
					<select name="manageType" class="easyui-combobox" data-options="editable:false" style="width:205px;">
		    			<option value="1">个人经营</option>
		    			<option value="2">合伙经营</option>
		    			<option value="3">个体工商经营</option>
		    			<option value="4">企业经营</option>
		    			<option value="5">其他方式</option>
		    		</select>
				</td>
	    		<th >主营方式：</th>
	    		<td >
	    			<select name="manageMode" class="easyui-combobox" data-options="editable:false" style="width:205px;">
		    			<option value="1">电销</option>
		    			<option value="2">地推</option>
		    			<option value="3">代理</option>
		    			<option value="4">其他</option>
		    		</select>
	    		</td>
			</tr>
			<tr>
				<th>员工数量：</th>
				<td><input type="text" name="staffNum" style="width: 200px;" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'" /></td>
	    		<th>经营地址：</th>
	    		<td >
					<input type="text" name="manageAddr" style="width:200px;" maxlength="100" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/>
				</td>
			</tr>
	    	<tr>
				<th>近3个月平台分润金额：</th>
	    		<td ><input type="text" name="profitAmt" style="width: 200px;" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'"  />&nbsp;元</td>
	    		<th>借款人姓名：</th>
	    		<td><input type="text" name="applicant" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" /></td>
	    	</tr>
	    	<tr>
		    	<th>身份证号：</th>
		    	<td><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/></td>
	    		<th>申请额度（不可高于最高授信额度）：</th>
	    		<td ><input type="text" name="applyQuota" id='applyQuota' style="width: 200px;" class="easyui-numberbox" data-options="required:true,validType:'spaceValidator'"  maxlength="30"/>&nbsp;元</td>
	    	</tr>
	    	<tr>
	    		<th>联系电话（请准确填写）：</th>
	    		<td ><input type="text" name="phone" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/></td>
	    	</tr>
		</table>
</form>  
</div>