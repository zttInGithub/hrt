<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Integer  id=Integer.parseInt(request.getParameter("id"));
 %>
<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="cashbackTaxPoint_datagrid_update_from" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
				<tr align="center">
					<th style="width:100px;">代理：</th>
   					<td style="width:200px;">
	   					<input id="unno_1" name="unno" type="text" style="width:150px;" disabled="disabled"></input>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">活动类型：</th>
   					<td style="width:200px;">
	   					<input id="rebatetype" name="rebatetype" type="text" style="width:150px;" disabled="disabled"></input>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">返现类型：</th>
   					<td style="width:200px;">
	   					<input id="cashbacktype_1" name="cashbacktype" type="text" style="width:150px;" disabled="disabled"></input>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">返现税点：</th>
   					<td style="width:200px;">
	   					<input id="taxpoint" name="taxpoint" type="text" style="width:150px;" ></input><font color="red">*</font>
   					</td>
				</tr>
				<tr>
					<input name="nid" id="id" type="hidden">
				</tr>
			</table>
		</form>
</div>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:"${ctx}/sysAdmin/cashbackTemplate_queryCashbackTaxPointForId.action",
			type:"post",
			data:{"id":<%=id%>},
			dataType:'json',
			success:function(data){
				//debugger;
				if(data){
					if(data.UNNO != null){
						var a = data.UNNO;
						$("#unno_1").val(data.UNNO);
					}
					if(data.CASHBACKTYPE != null){
						var b = data.CASHBACKTYPE;
						$("#cashbacktype_1").val(data.CASHBACKTYPE);
					}
			    	if(data.REBATETYPE != null){
			    		$('#rebatetype').val(data.REBATETYPE);
			    	}
			    	if(data.TAXPOINT != null){
			    		$('#taxpoint').val(data.TAXPOINT);
			    	}
			    	$('#id').val(data.ID);
				}
			}
		});
	});
</script>
