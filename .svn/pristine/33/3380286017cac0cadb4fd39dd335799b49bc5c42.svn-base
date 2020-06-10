<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="sysAdmin_role_editForm" method="post">  
    <table class="table">
    	<tr>
    		<th>角色名称：</th>
    		<td><input class="easyui-validatebox" type="text" name="roleName" data-options="required:true" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<th>描述：</th>
    		<td><input class="easyui-validatebox" type="text" name="description" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<th>权限：</th>
    		<td>
    			<select class="easyui-combotree" name="menuIds" multiple  style="width:205px;" 
    				data-options="
	    				lines:true,
	    				required:true,
	    				url:'${ctx}/sysAdmin/menu_listAllMenuTree.action'
    				">
    			</select>
    		</td>
    	</tr>
    	<tr>
    		<th>级别：</th>
    		<td>
    			<select name="roleLevel" id="roleLevel" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
    				<option value="0">总公司</option>
    				<option value="1">分公司</option>
    				<option value="2">作业处理/代理机构</option>
    				<option value="3">二级作业中心/二级代理机构</option>
    				<option value="5">三级作业中心/三级代理机构</option>
    				<option value="6">四级作业中心/四级代理机构</option>
    				<option value="7">五级作业中心/五级代理机构</option>
    				<option value="8">六级作业中心/六级代理机构</option>
    				<option value="9">七级作业中心/七级代理机构</option>
    				<option value="10">八级作业中心/八级代理机构</option>
    				<option value="11">九级作业中心/九级代理机构</option>
    				<option value="12">十级作业中心/十级代理机构</option>
    				<option value="4">收单商户</option>
    			</select>
    		</td>
    	</tr>
    </table>
    <input type="hidden" name="roleID">
</form>  

