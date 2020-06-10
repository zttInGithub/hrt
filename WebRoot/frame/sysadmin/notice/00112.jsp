<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<form id="sysAdmin_notice_showDiv" method="post">
	<table class="table">
    	<tr>
    		<th>发送机构：</th>
    		<td><input type="text" name="msgSendUnitName" data-options="required:true" style="width:250px;"/></td>
    	</tr>
    	<tr>
    		<th>发送者：</th>
    		<td><input type="text" name="msgSendName" style="width:250px;"/></td>
    	</tr>
    	<tr>
    		<th>发送时间：</th>
    		<td>
    			<input type="text" name="msgSendTime" style="width:250px;"/>
    		</td>
    	</tr>
    	<tr>
    		<th>消息标题：</th>
    		<td><input type="text" name="msgTopic" style="width:250px;"/></td>
    	</tr>
    	<tr>
    		<th>消息内容：</th>
    		<td><textarea rows="5" cols="30" style="resize:none;" name="msgDesc"></textarea></td>
    	</tr>
    </table>
    <input type="hidden" name="noticeID">
</form>