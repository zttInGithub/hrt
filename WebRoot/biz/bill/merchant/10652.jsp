<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="sysAdmin_receiptsAuditNo_editForm" method="post">
	<table class="table">
		<tr>
			<input type="hidden" name="pkid" value="<%=request.getParameter("pkid")%>"/>
			<th>不符合原因：</th>
		</tr>
		<tr>
			<td>
				<textarea name="minfo1" rows="5" cols="50" maxlength=25 ></textarea>
			</td>
		</tr>
				
	</table>
	<input type="hidden" name="bmtid" />
</form>  

