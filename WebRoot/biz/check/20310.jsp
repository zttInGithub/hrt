<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<div>
		<form method="post" enctype="multipart/form-data" id="import_fillpay_info" onsubmit="subbutton.disabled=1">
			<input type="hidden" name="importFillPayFile" id="import_fillpay_Name">
			<br/>
				<table align="center">
					<tr>
						<th>文件：</th>
						<td><input type="file" name="upload" id="upload_fillpay_File"/></td>
					</tr>
				</table>
				<div style="padding-left:34%;vertical-align:middle;">
				<br/>
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				 	<a href="javascript:void(0)" id="hidenButton" class="easyui-linkbutton" onclick="insert_merchantInfo();">上传</a>	
				 	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="insert_clean_merchantInfo();">取消</a>
				</div>
		</form>
	</div>
	<script>
		function insert_clean_merchantInfo(){
			$('#import_fillpay_info').form('clear');
		}
		function insert_merchantInfo(){
			$('#import_fillpay_info').form('submit',{
				url:'${ctx}/sysAdmin/checkSettleReturn_addMoreFillPayInfoFromExcel.action',
				onSubmit:function(){
					var contact = document.getElementById('upload_fillpay_File').value;
					if(contact == "" || contact == null){
						$.messager.show({
							title:'提示',
							msg:'请选择要上传的文件',
							timeout:5000,
							showType:'slide'
						});
						return false;
					}
					
					if(contact != "" && contact != null){
						var l = contact.split(".");
						if(l[1] != "xls"){
							$.messager.show({
								title:'提示',
								msg:'请选择后缀名为.xls文件',
								timeout:5000,
								showType:'slide'
							});
							return false;
						}
						//如果格式正确，处理
						if(l[1] == "xls"){
							document.getElementById('import_fillpay_Name').value = contact.replace(/.{0,}\\/, "");
							$('#hidenButton').linkbutton({disabled:true});
							return true;
						}
					}
				},
				//成功返回数据
				success:function(data){
					var resault = $.parseJSON(data);
					if(resault.sessionExpire){
						window.location.href = getProjectLocation();
					}else{
						if(resault.success){
							$.messager.show({
								title:'提示',
								msg  :resault.msg
							});
						}else{
							$.messager.show({
								title:'提示',
								msg  :resault.msg
							});
						}
					}
				}
			});
		}
	</script>
