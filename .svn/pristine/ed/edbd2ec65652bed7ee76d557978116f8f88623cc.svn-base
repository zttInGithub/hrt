<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
	$(function(){
		var tid=<%=request.getParameter("bmtidname")%>;
		$.ajax({
			url:'${ctx}/sysAdmin/machineTaskData_serachMachineTaskDataSingleInfo.action',
	   		dataType:"json",  
	   		type:"post",
	   		data:{tid:tid},
  			success:function(data) {
	  			var obj = eval(data);
	  			$("#TID").val(obj[0].TID);
	  			$("#MACHINEMODEL").val(obj[0].MACHINEMODEL);
	  			$("#BADDR").val(obj[0].BADDR);
	  			$("#CONTACTPHONE").val(obj[0].CONTACTPHONE);
	  			$("#REMARKS").val(obj[0].REMARKS);
  				
    		} 
		});
	});
	
</script>    
<form id="sysAdmin_machineTaskData_queryForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>上次换机记录信息</legend>
		<table class="table">
			<tr>
		   		<th>终端编号：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="TID" style="width:200px;" />
		   		</td>
		
		   		<th>机型：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="MACHINEMODEL" style="width:200px;" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>地址：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="BADDR" style="width:200px;" />
		   		</td>
		   
		   		<th>联系电话：</th>
		   		<td>
		   			<input type="text" readonly="readonly" id="CONTACTPHONE" style="width:200px;" />
		   		</td>
			</tr>
				<tr>
				<th>受理描述</th>
				<td>
					<textarea rows="6" cols="38" style="resize:none;" id="REMARKS" readonly="readonly"></textarea>
				</td>
			</tr>
		   	
		</table> 
	</fieldset>
	
</form>
