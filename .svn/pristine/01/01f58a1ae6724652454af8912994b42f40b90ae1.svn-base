<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
    
<script type="text/javascript">

	$(function(){
		//归属地
		$('#provinceCode').combogrid({
			url : '${ctx}/sysAdmin/unit_searchProvince.action',
			idField:'PROVINCE_CODE',
			textField:'PROVINCE_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'PROVINCE_NAME',title:'归属地',width:150},
				{field:'PROVINCE_CODE',title:'id',width:150,hidden:true}
			]]
		});
	});

	$.extend($.fn.validatebox.defaults.rules,{
		unitCode:{
			validator : function(value) { 
	            return /^\d{2,3}$/i.test(value); 
	        }, 
	        message : '分公司请输入2位数字,其他选项请输入3位数字' 
		}
	});
</script>
    
<form id="sysAdmin_unit_editForm" method="post">  
    <table class="table">
    	<tr>
    		<th>上级机构：</th>
    		<td>
    			<select class="easyui-combotree" name="_parentId" id="_parentId" style="width:205px;" 
    				data-options="
	    				lines:true,
	    				required:true,
	    				url:'${ctx}/sysAdmin/unit_listTreeUnits.action'
    				">
    			</select><font color="red">&nbsp;*</font>
    		</td>
    		<th>省市：</th>
    		<td >
    			<select id="provinceCode" name="provinceCode" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
    		</td>
    	</tr>
    	<tr>
    		<th>机构级别：</th>
    		<td>
    			<select name="unLvl" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false"  style="width:205px;">
    				<option value="0">总公司</option>
    				<option value="1">分公司</option>
    				<option value="2">作业中心/代理机构</option>
    				<option value="3">二级作业中心/二级代理机构</option>
    			</select><font color="red">&nbsp;*</font>
    		</td>

    		<th>机构名称：</th>
    		<td ><input type="text" name="unitName" style="width:200px;" class="easyui-validatebox" data-options="required:true" /><font color="red">&nbsp;*</font></td>
    	</tr>
    	<tr>
    		<th>简码：</th>
    		<td>
    			<input type="text" name="unitCode" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'unitCode'" maxlength="3" /><font color="red">&nbsp;*</font>
    		</td>
		</tr>
    </table>
    <input type="hidden" name="unNo">
    <input type="hidden" name="bizCode">
    <input type="hidden" name="status">
    <input type="hidden" name="unAttr">
    <input type="hidden" name="seqNo">
    <input type="hidden" name="seqNo2">
    <input type="hidden" name="createDate" />
    <input type="hidden" name="createUser" />
</form>