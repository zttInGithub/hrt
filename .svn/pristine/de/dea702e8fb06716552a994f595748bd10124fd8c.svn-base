<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
		$('#unno').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'UNNO',title:'机构号',width:150},
				{field:'UN_NAME',title:'机构名称',width:150}
			]]
		});

</script>
<form id="sysAdmin_gaveTerminal_serchForm" method="post">
	<table class="table">
		<tr>
		   		<th>代理商编号：</th>
		   		<td>
	   				<select id="unno" name="unNO" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
	   			</td>
		</tr>
	</table>
	<input type="hidden" name="termIDStart"  value="<%=request.getParameter("termIDStart")%>"/>
	<input type="hidden" name="termIDEnd"  value="<%=request.getParameter("termIDEnd")%>"/>
	<input type="hidden" name="snStart"  value="<%=request.getParameter("snStart")%>"/>
	<input type="hidden" name="snEnd"  value="<%=request.getParameter("snEnd")%>"/>
</form>  

