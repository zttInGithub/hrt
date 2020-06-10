<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  <div class="easyui-panel" style="height:200px; overflow: hidden; padding-top:25px;">
       <form method="post" enctype="multipart/form-data" id="frmBjjz20122" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="file20122Name" id="file20122_Name"/>
		<br/>
            <table align="center">
                <tr>
                    <th>手刷机具返利文件：</th>
                    <td><input type="file" name="upload_20122" id="uploads20122"></input></td>
                </tr>
                <tr>
                	<td>&nbsp;</td>
                	<td>&nbsp;</td>
                </tr>
                <tr>
					<th>返利类别：</th>
					<td>
						<select id='rebateType' name='rebateType' style="color: red">
							<option selected="selected" value="1">返机具</option>
							<option value="2">返款/分期返利/激活返利2</option>
							<option value="4">购机返利</option>
						</select>&nbsp;&nbsp;<font color="red">*</font>
					</td>
				</tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
             <a href="javascript:void(0)" class="easyui-linkbutton" id='btn_20122_upload' onclick="data_20122_searchFunBZ5();">上传</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="data_20122_cleanFunBZ5();">取消</a> 
        </div>
     </form>
</div>
<script>
        function data_20122_cleanFunBZ5(){
            $('#frmBjjz20122').form('clear');
        }
        
        function data_20122_searchFunBZ5(){
            //$.messager.progress();	//开启进度条
        	$('#frmBjjz20122').form('submit', { 
			url:'${ctx}/sysAdmin/checkUnitDealData_addIsM35RebateCheckData.action', 
			onSubmit: function(){
				var contact = document.getElementById('uploads20122').value;
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
					document.getElementById("file20122_Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
					//document.getElementById("frmBZ").submit();	//获取from值进行提交
					$('#btn_20122_upload').linkbutton({disabled:true});
					return true;
				}
			}
		}, 
		success:function(data){ 
		//$.messager.progress('close');   //关闭进度条 
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