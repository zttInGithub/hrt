<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
 %>
<script type="text/javascript">
	$(function(){
		$("#sysAdmin_imgDialog").css("display","none");
		$('#busid').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID',
			textField:'SALENAME',
			mode:'remote',
			fitColumns:true, 
			columns:[[  
				{field:'SALENAME',title:'销售姓名',width:150},
				{field:'UNNO',title:'所属机构',width:150},
				{field:'BUSID',title:'id',width:150,hidden:true}
			]]
		});
			var bmtkid1=$("#bmtkid1").val(); 
			$.ajax({
				url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail1.action',
	    		dataType:"json",  
	    		type:"post",
	    		data:{bmtkid:bmtkid1},
    			success:function(data) {
	    			var json=eval(data);
		    			if (json!="") {  
		    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
		    			var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
		    			var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
		    			var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
		    			var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
			    		$("#bt1id").val(json[0].bt1id); 
			    		$("#busid").combogrid("setValue",json[0].busid);
		    			$("#rname").val(json[0].rname);
		    			$("#legalPerson").val(json[0].legalPerson);
		    			$("#contactAddress").val(json[0].contactAddress);
		    			// $("#contactPhone").val(json[0].contactPhone);
						if(json[0].contactPhone!=""&&json[0].contactPhone!=null && json[0].contactPhone.length==11){
							var contactPhone=json[0].contactPhone.substring(0,3)+'****'
								+
								json[0].contactPhone.substring(json[0].contactPhone.length-4,json[0].contactPhone.length);
							$("#contactPhone").val(contactPhone);
						}else {
							$("#contactPerson").val(json[0].contactPerson);
						}
		    			$("#contactPerson").val(json[0].contactPerson);
		    			$("#contactTel").val(json[0].contactTel);
		    			$("#legalType").val(json[0].legalType); 
		    			$("#legalNum").val(json[0].legalNum); 
		    			$("#legalUpload").attr("src",path1);
	    				$("#bUpLoad").attr("src",path2);
	    				$("#rupload").attr("src",path3);
	    				$("#registryUpLoad").attr("src",path4);
	    				$("#materialUpLoad").attr("src",path5); 
	    				} 
	    	} 
});

	}); 
	function showBigImg(img){
			$("#sysAdmin_imgDialog").css("display","block");
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 1000,   
			    height: 600,
			    resizable:true,
		    	maximizable:true, 
			    modal:false,
				onClose:function() {
			    	$("#sysAdmin_imgDialog").css("display","none");
				}
			});
	}

</script>
		<form id="merchantTaskDetail1" method="post" enctype="multipart/form-data">
			<fieldset style="width: 800px;">
				<legend>商户基本信息明细</legend>
				<table class="table1" >
					<!-- <tr>
						<th>所属销售：</th>  
						<td>
	   						<select id="busid" name="busid" class="easyui-combogrid" required="true" style="width:205px;"></select>
	   					</td>
	   					<td><input id="bt1id" name="bt1id"  type="hidden"/> 
						</td>
					</tr> -->
					<tr>
						<th>商户全称：</th>
						<td><input id="rname" name="rname" readonly="readonly"  style="width: 156px;"
							 />
						</td>
						<th>法人：</th>
						<td><input id="legalPerson" name="legalPerson" readonly="readonly"  style="width: 156px;"
							/></td>
					</tr>
					<tr>
						<th>法人证件类型：</th>
						<td>
		   				<select id="legalType" name="legalType"  style="width:156px;" >
	    					<option value="1" >身份证</option>
	    					<option value="2">军官证</option>
	    					<option value="3">护照</option>
	    					<option value="4">港澳通行证</option>
	    					<option value="5">其他</option>
	    				</select>
		   				</td>
		   				
						<th>法人证件号码：</th>
						<td><input  id="legalNum" name="legalNum" readonly="readonly"  style="width: 150px;"
							class=" easyui-validatebox" data-options="required:true">
						</td>
					</tr>
					
					<tr>
						<th>联系地址：</th>
						<td><input id="contactAddress" name="contactAddress" readonly="readonly"  style="width: 156px;"/></td>
						<th>联系人：</th>
						<td><input id="contactPerson" name="contactPerson" readonly="readonly"  style="width: 156px;"/></td>
					</tr>
					<tr>
						<th>联系手机：</th>
						<td><input id="contactPhone" name="contactPhone" readonly="readonly"  style="width: 156px;"/></td>
						<th>联系电话：</th>
						<td><input id="contactTel" name="contactTel" readonly="readonly"  style="width: 156px;"/></td>
					</tr> 
					<tr>
						<th>法人身份上传图片预览:</th>
						<td>
						
			         	<img id="legalUpload" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
					
						</td> 
						<th>营业执照上传图片预览:</th>
						<td>
							<img id="bUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
					</tr> 
					<tr>
							<th>组织结构证上传图片预览:</th>
						<td>
						<img id="rupload" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
							<th>税务登记证上传图片预览:</th>
						<td>
							<img id="registryUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
					</tr> 
					<tr>
							<th>补充材料上传图片预览:</th>
						<td>
							<img id="materialUpLoad" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
						</td>
						
					</tr> 
				</table>
			</fieldset>  
			<input type="hidden" name="bmtkid" id="bmtkid1" value="<%=bmtkid %>">   
		</form> 
		<div id="sysAdmin_imgDialog"  >
			<img id="img" alt="" src="">
</div>
		