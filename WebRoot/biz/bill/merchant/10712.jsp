<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
</script>
<form id="sysAdmin_merchantmicrojoin_editForm" method="post">
	<table class="table">
		<tr>
		   	<th>受理描述：</th>
		   	<td>
		   		<textarea id="textar" rows="6" cols="38" class="easyui-validatebox"  name="processContext"></textarea>
		   	</td>
		</tr>
	</table>
	<input type="hidden" name="bmid"  value="<%=request.getParameter("bmids")%>"/>
</form>  

