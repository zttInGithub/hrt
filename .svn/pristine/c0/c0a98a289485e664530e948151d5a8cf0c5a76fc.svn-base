<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
 	 	$(function(){
		$('#userID').combogrid({
			onSelect:function(){
				$('#unno').combogrid('clear');
				var userID = $('#userID').combogrid('getValues');
				$('#unno').combogrid({
					idField:'unNo',   
		            textField:'unitName', 
		            url:'${ctx}/sysAdmin/unit_listUnitsUserCombogrid.action?userID='+userID,
		            fitColumns:true,  
		            columns:[[   
		                {field:'unNo',title:'机构ID',width:150},   
		                {field:'unitName',title:'机构名称',width:150}
		            ]]
				});
			}
		});
	});
	
</script>
<form id="sysAdmin_agentsales_addForm" method="post">  
    <table class="table">
    	<tr>
    		<th>所属用户：</th>
    		<td>
    			<select class="easyui-combogrid" name="userID" id="userID" style="width:205px;" data-options="  
		            required:true,
		            url : '${ctx}/sysAdmin/user_searchUser.action',
					idField:'USER_ID',
					textField:'USER_NAME',
					mode:'remote',
					fitColumns:true,
					editable:false,
					columns:[[
						{field:'USER_ID',title:'ID',width:150,hidden:true},
						{field:'USER_NAME',title:'名称',width:150}
					]]
		        "></select><font color="red">&nbsp;*</font>
    		</td>
    	</tr>
    	<tr>
    		<th>所属机构：</th>
    		<td>
    			<select class="easyui-combogrid" name="unno" id="unno" style="width:205px;" data-options="required:true,editable:false"></select><font color="red">&nbsp;*</font>
    		</td>
    	</tr>
    	<tr>
    		<th>手机号码：</th>
    		<td><input type="text" name="phone" style="width:200px;" class="easyui-numberbox"/></td>
    	</tr>
    	<tr>
    		<th>电话：</th>
    		<td><input type="text" name="telephone" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<th>邮箱：</th>
    		<td><input type="text" name="email" style="width:200px;" class="easyui-validatebox" data-options="validType:'email'"/></td>
    	</tr>
    </table>
</form>  

