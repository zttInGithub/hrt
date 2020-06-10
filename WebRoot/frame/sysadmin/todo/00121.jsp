<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
		
	$(function(){
		//菜单代码
		$('#tranCode').combogrid({
			url : '${ctx}/sysAdmin/menu_searchMenuTranCode.action',
			idField:'tranCode',
			textField:'text',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'text',title:'菜单名称',width:150},
				{field:'tranCode',title:'菜单代码',width:150}
			]]
		});
		
		//业务类别
		$('#bizType').combogrid({
			url : '${ctx}/sysAdmin/todo_searchBizType.action',
			idField:'BIZTYPE',
			textField:'BIZDESC',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'BIZDESC',title:'业务描述',width:150},
				{field:'BIZTYPE',title:'id',width:150,hidden:true}
			]]
		});
		
	});
</script>

<form id="sysAdmin_todo_addForm" method="post">  
    <table class="table">
    	<tr>
    		<th>接收机构：</th>
    		<td>
    			<select class="easyui-combotree" name="unNo" id="unNo" style="width:205px;" 
    				data-options="
	    				lines:true,
	    				url:'${ctx}/sysAdmin/unit_listTreeUnits.action'
    				">
    			</select>
    		</td>
    	</tr>
    	<tr>
    		<th>业务关键字：</th>
    		<td><input type="text" name="batchJobNo" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<th>发送主题：</th>
    		<td><input type="text" name="msgTopic" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<th>菜单代码：</th>
    		<td>
    			<select id="tranCode" name="tranCode" class="easyui-combogrid" style="width:205px;"></select>
    		</td>
    	</tr>
    	<tr>
    		<th>业务类别：</th>
    		<td>
    			<select id="bizType" name="bizType" class="easyui-combogrid" style="width:205px;"></select>
    		</td>
    	</tr>
    </table>
</form>  

