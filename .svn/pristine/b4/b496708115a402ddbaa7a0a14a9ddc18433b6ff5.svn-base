<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 运营中心审批通过页面 -->
<script type="text/javascript">
 	$(function(){
 		//归属地
 		$('#provinceCode').combogrid({
 			url : '${ctx}/sysAdmin/unit_searchProvince.action',
 			idField:'PROVINCE_CODE',
 			textField:'PROVINCE_NAME',
 			fitColumns:true,
 			columns:[[ 
 				{field:'PROVINCE_NAME',title:'省市',width:150},
 				{field:'PROVINCE_CODE',title:'id',width:150,hidden:true}
 			]]
 		});
 		
 	});
</script>

<form id="sysAdmin_00413_editForm" method="post">
	<fieldset>
		<legend>开通信息</legend>
		<table class="table">
			<tr>
	    		<th>归属机构号：</th>
	    		<td>
	    			<input type="text" name="parentUnno" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="6"/><font color="red">&nbsp;*</font>
	    		</td>
    		</tr>
    		<tr>
	    		<th>代理商简称：</th>
	    		<td><input type="text" name="agentShortNm" id="agentShortNm" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="80" /><font color="red">&nbsp;*</font></td>
			</tr>
			<tr>
				<th>省市：</th>
				<td>
					<select id="provinceCode" name="provinceCode" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
				</td>
			</tr>
			<tr>
				<th>简码：</th>
				<td><input type="text" name="unitCode" style="width:200px;" class="easyui-validatebox" data-options="validType:'unitCode'" maxlength="3"/><font color="red">&nbsp;*</font></td>
			</tr>
		</table>
	</fieldset>
	<input hidden="hidden" name="agentLvl">
	<input type="hidden" name="agentAttr" value="1">
	<!-- value='<%=request.getParameter("buid")%>' -->
	<input type="hidden" name="buid" >
</form>