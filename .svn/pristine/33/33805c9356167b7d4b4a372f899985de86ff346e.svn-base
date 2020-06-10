<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="easyui-panel" style="height:130px; overflow: hidden; padding-top:5px;">
       <form method="post" enctype="multipart/form-data" id="frmBZ" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="fileContact1" id="fileContactBZ"/>
		<br/>
            <table align="center">
                <tr>
                    <th>文件：</th>
                    <td><input type="file" name="upload" id="uploads"></input></td>
                </tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
             <a href="javascript:void(0)" id="searchFun" class="easyui-linkbutton" onclick="data_data_searchFunBZ();">上传</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="data_data_cleanFunBZ();">取消</a> 
        </div>
        </form>
        </div>
        <script>
        function data_data_cleanFunBZ(){
            $('#frmBZ').form('clear');
        }
        
        function data_data_searchFunBZ(){
            $.messager.progress();	//开启进度条
        	$('#frmBZ').form('submit', { 
			url:'${ctx}/sysAdmin/trafficBankInsert_saveTrafficBankInsertExport.action', 
			onSubmit: function(){
				var contact = document.getElementById('uploads').value;
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
		var l = contact.split(".");
		if(l[1] != "txt"){
		$.messager.progress('close');   //关闭进度条 
		$.messager.show({
			title:'提示',
            msg:'请选择后缀名为.txt 的文件!',
            timeout:5000,
            showType:'slide'
            });
			return false;
		}
		if (l[1] == "txt") {
			document.getElementById("fileContactBZ").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
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
   
