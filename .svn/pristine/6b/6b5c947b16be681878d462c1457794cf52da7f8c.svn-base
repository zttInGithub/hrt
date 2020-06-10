<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="easyui-panel" style="height:130px; overflow: hidden; padding-top:5px;">
       <form method="post" enctype="multipart/form-data" id="frmBj" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="importMerchantFile2" id="importMerchantFile2"/>
		<br/>
            <table align="center">
                <tr>
                    <th>文件：</th>
                    <td><input type="file" name="upload" id="uploads2"></input></td>
                </tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="data_data_searchFunBZ2();">上传</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="data_data_cleanFunBZ2();">取消</a> 
        </div>
        </form>
        </div>
        <script>
        function data_data_cleanFunBZ2(){
            $('#frmBj').form('clear');
        }
        
        function data_data_searchFunBZ2(){
              $.messager.progress();	//开启进度条
        	$('#frmBj').form('submit', { 
			url:'${ctx}/sysAdmin/merchant_updateMerchantToUnit.action', 
			onSubmit: function(){
				var contact = document.getElementById('uploads2').value;
				//alert('contact:'+contact);
				if (contact == "") {
				$.messager.progress('close');   //关闭进度条 
					$.messager.show({
               		 title:'提示',
                	 msg:'请选择要上传的文件!',
                	 timeout:5000,
                	 showType:'slide'
            		});
				return false;
				}
				
				if (contact != "") {
				//判断后缀名
					var l = contact.split(".");
					if(l[1] != "xls"){
							$.messager.progress('close');   //关闭进度条 
							$.messager.show({
								title:'提示',
					            msg:'请选择后缀名为.xls 的文件!',
					            timeout:5000,
					            showType:'slide'
					            });
								return false;
							}
							if (l[1] == "xls") {
								document.getElementById("importMerchantFile2").value = contact.replace(/.{0,}\\/, "");//获取jsp页面hidden的值
								//document.getElementById("frmBZ").submit();	//获取from值进行提交
								return true;
							}
								 
					}
				}, 
				success:function(data){ 
					$.messager.progress('close');   //关闭进度条 
					var res = $.parseJSON(data);
						if (res.sessionExpire) {
							window.location.href = getProjectLocation();
						} else {
							if(res.success) {
								$.messager.show({
									title : '提示',
									msg : res.msg
								});
							} else {
								$.messager.show({
									title : '提示',
									msg : res.msg
								});
									}  
								}
						} 
				}); 
		}
    </script>
   
