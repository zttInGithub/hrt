<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  <div style="height:145px;padding-top:25px;padding-left: 30px">
       <form method="post" enctype="multipart/form-data" id="frmBjjz10973" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="file10973Name" id="file10973_Name"/>
		<br/>
            <table>
   				<tr>
                    <th>&nbsp;&nbsp;明细文件：</th>
                    <td colspan="5"><input type="file" name="upload" id="uploads10973"></input></td>
                </tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="javascript:void(0)" class="easyui-linkbutton" id='btn_10973_upload' onclick="data_10973_searchFunBZ5();">上传</a>
        </div>
     </form>
</div>
<script>
        function data_10973_searchFunBZ5(){
            //$.messager.progress();	//开启进度条
        	$('#frmBjjz10973').form('submit', { 
			url:'${ctx}/sysAdmin/merchant_ImportAuthType.action', 
			onSubmit: function(){
				var contact = document.getElementById('uploads10973').value;
				//alert('contact:'+contact);
				if (contact == "") {
				//$.messager.progress('close');   //关闭进度条 
					$.messager.show({
               		 title:'提示',
                	 msg:'请选择要上传的文件!',
                	 timeout:5000,
                	 showType:'slide'
            		});
				return false;
				}
				if (contact != "") {
				var l = contact.split(".");
				if(l[1] != "xls"){
				//$.messager.progress('close');   //关闭进度条 
				$.messager.show({
					title:'提示',
		            msg:'请选择后缀名为.xls 的文件!',
		            timeout:5000,
		            showType:'slide'
		            });
					return false;
				}
				if (l[1] == "xls") {
					document.getElementById("file10973_Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
					$('#btn_10973_upload').linkbutton({disabled:true});
					return true;
				}
			}
		}, 
		success:function(data){ 
			var res = $.parseJSON(data);
				if (res.sessionExpire) {
					window.location.href = getProjectLocation();
				} else {
					if(res.success) {
						$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
	    				$('#sysAdmin_10150_datagrid').datagrid('reload');
						$("#sysAdmin_10643_ImportAuthTypeFun").dialog('destroy');
						$.messager.show({
							title : '提示',
							msg : res.msg
						});
					} else {
						$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
	    				$('#sysAdmin_10150_datagrid').datagrid('reload');
						$.messager.show({
							title : '提示',
							msg : res.msg
							});
					}  
				}
			} 
		}); 
        $('#btn_10973_upload').linkbutton({disabled:false});
        $("#sysAdmin_10643_ImportAuthTypeFun").dialog('destroy');
	}
    </script>