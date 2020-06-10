<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<div>
		<form method="post" enctype="multipart/form-data" id="importHrtSninfo" onsubmit="subbutton.disabled=1">
			<input type="hidden" name="importHrtFile" id="importHrtInfonName">
			<br/>
				<table align="center">
					<tr>
						<td>设备类别</td>
						<td>
							<select name='merchantType' id='merchantType'>
								<option value='1'>微商户设备导入</option>
								<option value ='2'>智能设备导入</option>
								<option value ='3'>三级分销设备导入</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>文件：</th>
						<td><input type="file" name="upload" id="uploadHrtInfoFile"/></td>
					</tr>
				</table>
				<div style="padding-left:34%;vertical-align:middle;">
				<br/>
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				 	<a href="javascript:void(0)" id="hidenButton" class="easyui-linkbutton" onclick="data_data_searchHrtInfo();">上传</a>	
				 	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="data_data_cleanHrtInfo();">取消</a>
				</div>
		</form>
	</div>
	<script>
		function data_data_cleanHrtInfo(){
			$('#importHrtSninfo').form('clear');
		}
		function data_data_searchHrtInfo(){
			
			$('#importHrtSninfo').form('submit',{
				url:'${ctx}/sysAdmin/terminalInfo_addM35SnInfo.action',
				onSubmit:function(){
					var contact = document.getElementById('uploadHrtInfoFile').value;
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
							document.getElementById('importHrtInfonName').value = contact.replace(/.{0,}\\/, "");
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
