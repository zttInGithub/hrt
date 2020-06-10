<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div style="height:130px; overflow: hidden; padding-top:5px;">
       <form method="post" enctype="multipart/form-data" id="frmBj10437" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="file10488Name" id="file10488Name"/>
		<input type="hidden" name="PDLID" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
		<br/>
            <table align="center">
                <tr>
               		<th>订单：</th>
	   				<td><input type="text" name="ORDERID" style="width: 120px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" readonly="readonly"></td>
	   				<th>&nbsp;&nbsp;数量：</th>
	   				<td>
		    			<input type="text" name="DELIVENUM" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" readonly="readonly">
   					</td>
   					<th>&nbsp;&nbsp;已出：</th>
	   				<td>
		    			<input type="text" name="ALLOCATEDNUM" style="width: 70px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" readonly="readonly">
   					</td>
   				</tr>
   				<tr>
   					<th>&nbsp;&nbsp;机型：</th>
   					<td>
		    			<input type="text" name="MACHINEMODEL" style="width: 120px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true" readonly="readonly">
   					</td>
                    <th>&nbsp;&nbsp;文件：</th>
                    <td colspan="3"><input type="file" name="upload" id="upload"></input></td>
                </tr>
            </table>
            <div style="padding-left:34%;vertical-align:middle;">
             <br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="javascript:void(0)" id="hidenButton10437" class="easyui-linkbutton" onclick="data_data_searchFunBZ10437();">上传</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" onclick="data_data_cleanFunBZ10437();">取消</a> 
        </div>
        </form>
        </div>
        <script>
        function data_data_cleanFunBZ10437(){
            $('#frmBj10437').form('clear');
        }
        
        function data_data_searchFunBZ10437(){
              $.messager.progress();	//开启进度条
        	$('#frmBj10437').form('submit', { 
			url:'${ctx}/sysAdmin/terminalInfo_updateM35ForReturn.action', 
			onSubmit: function(){
				var contact = document.getElementById('upload').value;
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
							if (l[1] == "xls"){
								document.getElementById("file10488Name").value = contact.replace(/.{0,}\\/, "");//获取jsp页面hidden的值
								$.messager.progress('close');
								$('#hidenButton10437').linkbutton({disabled:true});
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
   
