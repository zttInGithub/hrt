<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 退货入库明细页面 -->
  <div style="height:145px;padding-top:25px;padding-left: 30px">
       <form method="post" enctype="multipart/form-data" id="frmBjjz10505" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="file10505Name" id="file10505_Name"/>
		<br/>
            <table>
                <tr>
               		<th>订单：</th>
	   				<td><input type="text" name="ORDERID" style="width: 120px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
	   				<th>&nbsp;&nbsp;数量：</th>
	   				<td>
		    			<input type="text" name="MACHINENUM" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
   					<th>&nbsp;&nbsp;已入：</th>
	   				<td>
		    			<input type="text" name="IMPORTNUM"  style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
   				</tr>
   				<tr>
   					<th>&nbsp;&nbsp;品牌：</th>
	   				<td>
		    			<input type="text" name="BRANDNAME" style="width: 120px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
                	<th>&nbsp;&nbsp;机型：</th>
	   				<td>
		    			<input type="text" name="MACHINEMODEL" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="SNTYPE" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="PDID" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="ORDERMETHOD" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="ORDERTYPE" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="UNNO" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
                    <th>&nbsp;&nbsp;明细文件：</th>
                    <td><input type="file" name="upload" id="uploads10505"></input></td>
                </tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="javascript:void(0)" class="easyui-linkbutton" id='btn_10505_upload' onclick="data_10505_searchFunBZ5();">上传</a>
        </div>
     </form>
</div>
<script>
        function data_10505_cleanFunBZ5(){
            $('#frmBjjz10505').form('clear');
        }
        
        function data_10505_searchFunBZ5(){
            //$.messager.progress();	//开启进度条
        	$('#frmBjjz10505').form('submit', { 
			url:'${ctx}/sysAdmin/terminalInfo_updateM35SnInfoPUR3.action', 
			onSubmit: function(){
				var contact = document.getElementById('uploads10505').value;
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
					document.getElementById("file10505_Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
					//document.getElementById("frmBZ").submit();	//获取from值进行提交
					$('#btn_10505_upload').linkbutton({disabled:true});
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