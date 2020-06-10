<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
		//根据机构选择用户
		$('#msgReceUnit').combotree({
			onSelect:function(node){
				$('#msgReceUID').combogrid({
					url : '${ctx}/sysAdmin/unit_searchUnitUser.action?unitUnno='+node.id,
					idField:'userID',
					textField:'userName',
					mode:'remote',
					fitColumns:true,
					columns:[[ 
						{field:'userName',title:'姓名',width:150},
						{field:'loginName',title:'用户名',width:150},
						{field:'userID',title:'id',width:150,hidden:true}
					]]
				});
			}
		});
	});
	
</script>

<form id="sysAdmin_notice_addForm" method="post">  
    <table class="table">
    	<tr>
    		<th>接收机构：</th>
    		<td>
    			<select class="easyui-combotree" name="msgReceUnit" id="msgReceUnit" style="width:205px;" 
    				data-options="
	    				lines:true,
	    				required:true,
	    				url:'${ctx}/sysAdmin/unit_listTreeUnits.action'
    				">
    			</select>
    		</td>
    	</tr>
    	<tr>
    		<th>接受者：</th>
    		<td>
    			<select id="msgReceUID" name="msgReceUID" class="easyui-combogrid" data-options="editable:false" style="width:205px;"></select>
    		</td>
    	</tr>
    	<tr>
    		<th>消息标题：</th>
    		<td><input class="easyui-validatebox" type="text" required="true" name="msgTopic" data-options="required:true" style="width:205px;"/></td>
		</tr>
    	<tr>
    		 <th>内容：</th>
    	</tr>
    </table>
    		<tr>
    			<td>
    				<textarea id="editor_id" name="msgDesc" style="height:400px" >
					</textarea>
    			</td>
    		</tr>

</form>
    <script>
	var	editor =KindEditor.create('textarea[name="msgDesc"]', {
        allowFileManager : true,
        resizeType : 1,
        afterBlur: function(){this.sync();}
    });
    function f1(){
    	editor.html(editor.text());
    }
    
</script>  

