<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="sysAdmin_menu_editForm" method="post">  
    <table class="table">
    	<tr>
    		<th>菜单名称：</th>
    		<td><input class="easyui-validatebox" type="text" name="text" data-options="required:true" style="width:180px;"/></td>
    	
    		<th>菜单路径：</th>
    		<td><input type="text" name="src"  style="width:180px;"/></td>
    	</tr>
    	<tr>
    		<th>菜单索引：</th>
    		<td><input class="easyui-numberspinner" type="text" name="seq" data-options="required:true,min:1,max:900,editable:false" style="width:180px;"/></td>
    	
    		<th>上级菜单：</th>
    		<td>
    			<select class="easyui-combotree" name="_parentId" style="width:180px;" 
    				data-options="
	    				lines:true,
	    				url:'${ctx}/sysAdmin/menu_listAllMenuTree.action'
    				">
    			</select>
    		</td>
    	</tr>
    	<tr>
    		<th>菜单代码：</th>
    		<td><input class="easyui-validatebox" type="text" name="tranCode" style="width:180px;"/></td>
    	
    		<th></th>
    		<td></td>
    	</tr>
    </table>
    <input type="hidden" name="menuID">
</form>  

