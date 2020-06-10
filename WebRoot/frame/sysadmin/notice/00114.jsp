<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="sysAdmin_00114_showDiv" method="post">
	<table class="table">
    	<tr>
    		<th>发送机构：</th>
    		<td><input type="text" name="msgSendUnitName" data-options="required:true" readonly="readonly"/></td>
    	</tr>
    	<tr>
    		<th>发送者：</th>
    		<td><input type="text" name="msgSendName"  readonly="readonly"/></td>
    	</tr>
    	<tr>
    		<th>发送时间：</th>
    		<td>
    			<input type="text" name="msgSendTime"  readonly="readonly"/>
    		</td>
    	</tr>
    	<tr>
    		<th>消息标题：</th>
    		<td><input type="text" name="msgTopic" readonly="readonly"/></td>
    	</tr>
    	<tr>
    		<th>消息内容：</th>
			<td><input type="hidden" id="noticeID" name="noticeID"  value="<%=request.getParameter("noticeID") %>"/></td>
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
        readonlyMode : true,
        resizeType : 1,
        afterBlur: function(){this.sync();}
    });
    $(function(){
    	var noticeID=$("#noticeID").val();
    			$.ajax({
					url:"${ctx}/sysAdmin/notice_findNoticeMsgById.action",
					type:'post',
					data:{"noticeID":noticeID},
					dataType:'html',
					success:function(data, textStatus, jqXHR) {
						var json=eval(data);
						editor.html(json[0].msgDesc);
					},
					error:function() {
						$.messager.alert('提示', '请求信息异常！');
					}
				});
    });
</script>  