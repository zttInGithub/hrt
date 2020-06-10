<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Integer  busid=Integer.parseInt(request.getParameter("busid"));
 %>
<script type="text/javascript">
	$('#SALESGROUP').combogrid({
			url :'${ctx}/sysAdmin/agentsales_queryAgentsalesGroup.action',
			idField:'SALESGROUP',
			textField:'SALESGROUP',
			mode:'remote',
			fitColumns:true,
			onSelect:function(index,row){
				$("#SALESGROUP").val(row.SALESGROUP);
            },
            columns:[[ 
				{field:'SALESGROUP',title:'组名',width:250}
			]] 
		});
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="sysAdmin_agentsalesGroupUpdate" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
				<tr align="center">
					<th style="width:100px;">销售姓名：</th>
   					<td style="width:200px;">
	   					<input id="SALENAME_VIEW" name="saleName_view" type="text" style="width:200px;" disabled="disabled"></input>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">职能：</th>
   					<td style="width:200px;">
	   					<select name="isleader" style="width:200px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
		   					<option value="1">组长</option>
		    				<option value="2" selected="selected">组员</option>
		   				</select>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">组名：</th>
   					<td style="width:200px;">
	   					<select id="SALESGROUP" name="salesgroup" class="easyui-combogrid" data-options="required:true,editable:false" style="width:200px;">
	   					</select>
   					</td>
				</tr>
				<tr>
					<input name="busid" id="busid" type="hidden">
					<input name="saleName" id="SALENAME" type="hidden">
				</tr>
			</table>
		</form>
</div>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:"${ctx}/sysAdmin/agentsales_queryAgentsalesGroupForId.action",
			type:"post",
			data:{"busid":<%=busid%>},
			dataType:'json',
			success:function(data){
				if(data){
			    	if(data.SALESGROUP != null){
			    		 $("#SALESGROUP").combogrid("setValue",data.SALESGROUP);
			    	}
			    	if(data.SALENAME != null){
			    		$('#SALENAME').val(data.SALENAME);
			    		$('#SALENAME_VIEW').val(data.SALENAME);
			    	}
			    	$('#busid').val(data.BUSID);
				}
			}
		});
	});
</script>
