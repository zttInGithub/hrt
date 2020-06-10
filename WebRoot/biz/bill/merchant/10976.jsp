<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Integer  id=Integer.parseInt(request.getParameter("id"));
 %>
<script type="text/javascript">
	$('#producttype_10976').combogrid({
			url : '${ctx}/sysAdmin/producttypeInRebatetypeAction_queryProducttype.action',
			idField:'PRODUCTTYPE',
			textField:'PRODUCTTYPE',
			mode:'remote',
			fitColumns:true,
			onSelect:function(index,row){
				$("#producttype_10976").val(row.PRODUCTTYPE);
            },
            columns:[[ 
				{field:'PRODUCTTYPE',title:'产品类型',width:250}
			]] 
		});
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="Producttype_10976_update" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
				<tr align="center">
					<th style="width:100px;">产品类型：</th>
   					<td style="width:270px;">
	   					<select id="producttype_10976" name="producttype" class="easyui-combogrid" data-options="required:true,editable:false" style="width:250px;">
	   						<!-- <option id="producttypeOp" selected="selected"></option> -->
	   					</select><font color="red">&nbsp;*</font>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">活动类型：</th>
   					<td style="width:270px;">
	   					<input id="rebatetype_10976" name="rebatetype_10976" type="text" style="width:250px;" disabled="disabled"></input><font color="red">&nbsp;*</font>
   					</td>
				</tr>
				<tr>
					<input name="rebatetype" id="rebatetype" type="hidden">
					<input name="id" id="id" type="hidden">
				</tr>
			</table>
		</form>
</div>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:"${ctx}/sysAdmin/producttypeInRebatetypeAction_queryProducttypeForId.action",
			type:"post",
			data:{"id":<%=id%>},
			dataType:'json',
			success:function(data){
				//debugger;
				if(data){
			    	if(data.PRODUCTTYPE != null){
			    		 $("#producttype_10976").combogrid("setValue",data.PRODUCTTYPE);
			    	}
			    	if(data.REBATETYPE != null){
			    		$('#rebatetype').val(data.REBATETYPE);
			    		$('#rebatetype_10976').val(data.REBATETYPE);
			    		//document.getElementById('rebatetype').value=data.REBATETYPE;
			    	}
			    	$('#id').val(data.ID);
				}
			}
		});
	});
</script>