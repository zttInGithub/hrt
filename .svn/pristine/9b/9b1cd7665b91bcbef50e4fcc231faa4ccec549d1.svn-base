<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	function insert_clean_merchantInfo2(){
			$('#sysAdmin_20402_editForm').form('clear');
		}
		//差错文件导入
	function insert_merchantInfo2(){
			var url='${ctx}/sysAdmin/checkErFund_importRefundInfo.action';
			$('#sysAdmin_20402_editForm').form('submit',{
				url:url,
				onSubmit:function(){
					var contact = document.getElementById('upload_refile').value;
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
						//if(l[1] == "xls" || l[1]=="csv"){
						if(l[1] == "xls"){
							document.getElementById('upload_refile_name').value = contact.replace(/.{0,}\\/, "");
							$('#hidenButton').linkbutton({disabled:true});
							return true;
						}else{
							$.messager.show({
								title:'提示',
								msg:'请选择后缀名为.xls文件',
								timeout:5000,
								showType:'slide'
							});
							return false;
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
 
<form id="sysAdmin_20402_editForm" method="post" enctype="multipart/form-data" onsubmit="subbutton.disabled=1">
	<fieldset>
		<table class="table">
			<tr style="height: 50px">
	    		<th width="15%">浏览：</th>
	    		<td width="55%">
	    			<input type="file" name="upload" id="upload_refile" style="width:300px;"  data-options="required:true" />
					<input type="hidden" name="upload_refile" id="upload_refile_name"></td>
				<td width="15%" style="padding-left: 20px"><input type="button" name="sumbit1" id="sumbit1" value="上传" onclick="insert_merchantInfo2()"></td>
				<td width="15%" style="padding-left: 20px"><input type="button" name="clear1" id="clear1" value="取消" onclick="insert_clean_merchantInfo2()"></td>
	    	</tr>
		</table>
	</fieldset>
</form>  

