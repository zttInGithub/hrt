<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  <div style="height:145px;padding-top:25px;padding-left: 30px">
       <form method="post" enctype="multipart/form-data" id="frmBjjz10436" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="file10436Name" id="file10436_Name"/>
		<br/>
            <table>
                <tr>
               		<th>订单：</th>
	   				<td><input type="text" name="ORDERID" style="width: 120px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly"></td>
	   				<th>&nbsp;&nbsp;数量：</th>
	   				<td>
		    			<input type="text" name="MACHINENUM" id="orderMethod_10431" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
   					<th>&nbsp;&nbsp;已入：</th>
	   				<td>
		    			<input type="text" name="IMPORTNUM" id="orderMethod_10431" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
   				</tr>
   				<tr>
   					<th>&nbsp;&nbsp;品牌：</th>
	   				<td>
		    			<input type="text" name="BRANDNAME" id="orderMethod_10431" style="width: 120px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
                	<th>&nbsp;&nbsp;机型：</th>
	   				<td>
		    			<input type="text" name="MACHINEMODEL" id="orderMethod_10431" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="SNTYPE" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="PDID" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="ORDERMETHOD" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		    			<input type="hidden" name="ORDERTYPE" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">		    			
   					</td>
   					<th>&nbsp;&nbsp;库位：</th>
	   				<td>
	   					<input type="text" name="storage" id="storage_10436" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
	   				</td>
   				</tr>
   				<tr>
                    <th>&nbsp;&nbsp;分段文件：</th>
                    <td colspan="5"><input type="file" name="upload" id="uploads10436"></input></td>
                </tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="javascript:void(0)" class="easyui-linkbutton" id='btn_10436_upload' onclick="data_10436_searchFunBZ5();">上传</a>
        </div>
     </form>
</div>
<script>
        function data_10436_cleanFunBZ5(){
            $('#frmBjjz10436').form('clear');
        }
        
        function data_10436_searchFunBZ5(){
            //$.messager.progress();	//开启进度条
        	$('#frmBjjz10436').form('submit', { 
			url:'${ctx}/sysAdmin/terminalInfo_addM35SnInfoPUR2.action', 
			onSubmit: function(){
				var contact = document.getElementById('uploads10436').value;
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
					document.getElementById("file10436_Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
					//document.getElementById("frmBZ").submit();	//获取from值进行提交
					$('#btn_10436_upload').linkbutton({disabled:true});
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