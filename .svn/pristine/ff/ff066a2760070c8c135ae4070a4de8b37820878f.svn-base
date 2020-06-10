<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules,{
		nameValidator:{
			validator : function(value) { 
	            return /^[^\u4e00-\u9fa5]+$/i.test(value); 
	        }, 
	        message : '禁止输入中文汉字' 
		}
	});
</script>

<form id="sysAdmin_userAdd_addForm" method="post">
	<table class="table">
		<tr>
			<th>登录名：</th>
			<td><input name="loginName" class="easyui-validatebox" data-options="required:true,validType:'nameValidator'" style="width:200px;" maxlength="20"/> </td>
		</tr>
		<tr>
			<th>用户名称：</th>
			<td><input name="userName" class="easyui-validatebox" data-options="required:true" style="width:200px;"/> </td>
		</tr>
		<tr>
			<th>角色：</th>
			<td>
				<select class="easyui-combogrid" name="roleIds" style="width:205px;" multiple="multiple" data-options="  
		            panelWidth:450,
		            idField:'roleID',   
		            textField:'roleName', 
		            required:true,
		            editable:false,
		            sortName: 'roleID',
					sortOrder: 'desc',
		            url:'${ctx}/sysAdmin/role_listRoleCombogrid.action',  
		            columns:[[   
		                {field:'roleID',title:'角色ID',width:50, checkbox:true},   
		                {field:'roleName',title:'角色名称',width:150},   
		                {field:'description',title:'描述',width:250}
		            ]]   
		        "></select>	
			</td>
		</tr>
		<tr>
			<th>机构：</th>
			<td>
				<select class="easyui-combogrid" name="unNo" style="width:205px;" multiple="multiple" data-options="  
		            panelWidth:450,
		            idField:'unNo',   
		            textField:'unitName', 
		            required:true,
		            editable:false,
		            sortName: 'unNo',
					sortOrder: 'desc',
		            url:'${ctx}/sysAdmin/unit_listUnitsCombogrid.action',  
		            columns:[[   
		                {field:'unNo',title:'机构ID',width:50, checkbox:true},   
		                {field:'unitName',title:'机构名称',width:250}
		            ]]   
		        "></select>	
			</td>
		</tr>
	</table>
</form>

