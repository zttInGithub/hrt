<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	
	//上传
	function sysAdmin_10733_uploadDate(){
    	$('#hotMerchant_upload_form').form('submit', {
    		url:'${ctx}/sysAdmin/hotCard_uploadHotCardMerchant.action',
			onSubmit: function(){
				var contact = document.getElementById('hotMerchant_upload').value;
				if (contact == "") {
						$.messager.show({
		           		 title:'提示',
		            	 msg:'请选择要导入的文件!',
		            	 timeout:5000,
		            	 showType:'slide'
		        		});
					return false;
				}
				
				if (contact != "") {
				var l = contact.split(".");
				if(l[1] != "xls"){
				$.messager.show({
					title:'提示',
			        msg:'请选择后缀名为.xls 的文件!',
			        timeout:5000,
			        showType:'slide'
			        });
					return false;
				}
				if (l[1] == "xls") {
					document.getElementById("hotMerchant_file").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
					return true;
				}
				 
				}
			}, 
			success:function(data){
				$("#sysAdmin_10730_uploadDialog").dialog('destroy'); 
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
<form method="post" enctype="multipart/form-data" id="hotMerchant_upload_form" onsubmit="subbutton.disabled=1">
	<input type="hidden" name="hotMerchant_file" id="hotMerchant_file"/>
	<br/><br/>
    <table align="center">
		<tr>
			<th>文件：</th>
			<td><input type="file" name="upload" id="hotMerchant_upload"></input></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="sysAdmin_10733_uploadDate();">导入</a></td>
        </tr>
    </table>
		
</form>
