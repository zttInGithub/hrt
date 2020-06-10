<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
	
</script>
<form id="sysAdmin_merchantjoin_editForm" method="post">
	<table class="table">
		<tr>
			<th>上送区域码：</th>
			<td>
				<select id="sendCode" name="sendCode" class="easyui-combogrid" data-options=" 
							url : '${ctx}/sysAdmin/merchant_searchCUPSendCodeInfo.action',
							idField:'SEND_CODE',
							textField:'SEND_NAME',
							mode:'remote',
							width:260,
							fitColumns:true,
		                    collapsible:false,  
		                    fit: false,  
							columns:[[
									{field:'SEND_CODE',title:'区号',width:150},
									{field:'SEND_NAME',title:'名称',width:200}
									]]" style="width:250px;"></select>
			</td>
		</tr>
		<tr>
		   	<th>受理描述：</th>
		   	<td>
		   		<textarea rows="6" cols="38" style="resize:none;" name="processContext"></textarea>
		   	</td>
		</tr>
		
	</table>
	<input type="hidden" name="bmid" />
</form>  

