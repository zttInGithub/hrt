<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>    
<script type="text/javascript">

	$(function(){
		//显示照片
		var dpid=<%=request.getParameter("dpid")%>;
		var timestamp=new Date().getTime();
		$.ajax({
			url:'${ctx}/sysAdmin/checkMisTake_serachMisTake.action',
    		dataType:"json",  
    		type:"post",
    		data:{dpid:dpid},
   			success:function(data) {
   				var json=eval(data);
	    		if (json!="") { 
	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.orderUpload)+'&timestamp='+timestamp;
	    			$("#ImgPr1").attr("src",path1);
    			}  
	    	} 
	    	
		});

        // 显示商户联系人手机号
        var mid='<%=request.getParameter("mid")%>';
        $.ajax({
            url:'${ctx}/sysAdmin/merchant_serachMerchantInfoMid.action',
            dataType:"json",
            type:"post",
            data:{mid:mid},
            success:function(data) {
                var json=eval(data);
                if(json.rows.length>0){
                    if(typeof json.rows[0].contactPhone == "undefined" || json.rows[0].contactPhone == null || json.rows[0].contactPhone == ""){
                    }else{
                        $("#merPhone").val(json.rows[0].contactPhone);
                    }
                }
            }

        });
	});
	
	$(function(){
		 $("#orderUploadFile").uploadPreview({ Img: "ImgPr1", Width: 80, Height: 80 });
		 //alert('ww');
	});
	
	//照片详情
	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_20413_editForm').after(imgDialog);
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 500,   
			    height: 500,
			    resizable:true,
		    	maximizable:true, 
			    modal:false,
			    buttons:[{
					text:'顺时针',
					iconCls:'',
					handler:function() {
						rotateRight();
					}
				},{
					text:'逆时针',
					iconCls:'',
					handler:function() {
						rotateLeft();
					}
				},{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_imgDialog').dialog('destroy');
				}
				}],
				onClose:function() {
					$('#sysAdmin_imgDialog').remove();
					rotation=0;
					rotate=0;
				}
			});
	}
	
	var rotation=0;
	var rotate=0;   
	function rotateRight(){   
		rotation=(rotation==3)?0:++rotation;
		rotate=(rotate==270)?0:rotate+90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";   
	}
	
	function rotateLeft(){
		rotation=(rotation==0)?3:--rotation;
		rotate=(rotate==0)?270:rotate-90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";
	}
	
	$('#orderUploadFile').change(function(){
		var contact = document.getElementById('orderUploadFile').value;
		document.getElementById("orderUpload").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
</script>
 
<form id="sysAdmin_20413_editForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>交易信息</legend>
		<table class="table">
			<tr>
	    		<th>商户编号：</th>
	    		<td><input type="text" name="mid" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="200"/><font color="red">&nbsp;</font></td>
	    		<th>通知日期：</th>
	    		<td><input type="text" name="inportDate" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="100"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>商户名称：</th>
	    		<td><input type="text" name="rname1" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="200"/><font color="red">&nbsp;</font></td>
	    		<th>交易金额：</th>
	    		<td><input type="text" name="samt" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="100"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>交易卡号：</th>
	    		<td><input type="text" name="cardNo" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	
	    		<th>交易日期：</th>
	    		<td><input type="text" name="transDate" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>参考号：</th>
	    		<td><input type="text" name="rrn" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	
	    		<th>暂定回复日期：</th>
	    		<td><input type="text" name="finalDate" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
			<tr>
				<th>联系手机：</th>
				<td><input type="text" id="merPhone" name="merPhone" style="width:200px;" readonly="readonly" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
			</tr>
	    	<tr>
	    		<th>银联备注：</th>
	    		<td colspan="3"><input type="text" name="bankRemarks" style="width:600px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="100"/><font color="red">&nbsp;</font></td>
	    	</tr>
		</table>
	</fieldset>
	<fieldset id = "backinfo">
		<legend>回复信息</legend>
		<table class="table">
			<tr>
	    		<th>注册店名：</th>
	    		<td ><input type="text" name="rname" style="width:200px;" class="easyui-validatebox" data-options="required:false" maxlength="100"/><font color="red">&nbsp;</font></td>
	    		<th>营业地址：</th>
	    		<td ><input type="text" name="raddr" style="width:200px;" class="easyui-validatebox" data-options="required:false" maxlength="100"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="contactPhone" style="width:200px;"  class="easyui-validatebox" data-options="required:false" maxlength="20"/><font color="red">&nbsp;</font></td>
	
	    		<th>联系人：</th>
	    		<td><input type="text" name="contactPerson" style="width:200px;"  class="easyui-validatebox" data-options="required:false" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>回复备注：</th>
	    		<td colspan="3"><input type="text" name="agRemarks" style="width:600px;"  class="easyui-validatebox" data-options="required:false" maxlength="30"/><font color="red">&nbsp;</font></td>
	    	</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>上传单据</legend>
		<table class="table">
			<tr style="height: 100px">
	    		<th>单据照片：</th>
	   			<td>
	   				<input type="file" name="orderUploadFile" id="orderUploadFile" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr1" name="ImgPr1" width="120" height="90" onclick="showBigImg(this.src);"/></div>
	    		</td>	    	
	    	</tr>
		</table>
	</fieldset>
	<input type="hidden" name="dpid" id="dpid">
	<input type="hidden" name="orderUpload" id="orderUpload">
	 
</form>  

