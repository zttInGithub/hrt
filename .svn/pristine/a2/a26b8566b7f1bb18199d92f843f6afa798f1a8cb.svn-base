<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>    
<script type="text/javascript">

	$(function(){
		 $("#refundImg").attr("disabled",true);	
		//显示照片
		var refids=<%=request.getParameter("refids")%>;
		var timestamp=new Date().getTime();
		$.ajax({
			url:'${ctx}/sysAdmin/checkRefund_queryRefundInfo.action?refId='+refids,
    		dataType:"json",  
    		type:"post",
    		data:{refid:refids},
   			success:function(data) {
   				var json=eval(data);
	    		if (json!="") {
	    			//var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+$("#refundImgName").val()+'&timestamp='+timestamp;
					$("#refundImg").uploadPreview({ Img: "ImgPr2", Width: 90, Height: 120 });
	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.rows[0].refundImgName)+'&timestamp='+timestamp;
	    			$("#ImgPr2").attr("src",path1);
    			}  
	    	} 
	    	
		});
	});
	
	//照片详情
	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_20405_editForm').after(imgDialog);
		$("#img").attr("src",img);
		$("#img").attr("style","width:750px;height:420px");
		$('#sysAdmin_imgDialog').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 800,
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
	
	$('#refundImg').change(function(){
		var contact = document.getElementById('refundImg').value;
		document.getElementById("reOrderUpload").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
</script>
 
<form id="sysAdmin_20405_editForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>退款信息</legend>
		<table class="table">
			<tr>
	    		<th>商户编号：</th>
	    		<td><input type="text" name="mid" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="200"/><font color="red">&nbsp;</font></td>
	    		<th>交易日期：</th>
	    		<td><input type="text" name="txnDayStr" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>交易金额：</th>
	    		<td><input type="text" name="samt" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    		<th>退款金额：</th>
	    		<td><input type="text" name="ramt" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>退款单号：</th>
	    		<td><input type="text" name="rrn" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	
	    		<th>原订单号 ：</th>
	    		<td><input type="text" name="oriOrderId" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>照片信息</legend>
		<table class="table">
			<tr style="height: 100px">
	    		<th>电子签名：</th>
	   			<td>
	   				<input type="file" name="refundImg" id="refundImg" class="easyui-validatebox" data-options="validType:'imgValidator'"/><font color="red">*</font>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr2" name="ImgPr2" width="120" height="90" onclick="showBigImg(this.src);"/></div>
	    		</td>	
	    	</tr>
		</table>
	</fieldset>
	<fieldset id="rebackField"  style="">
		<legend>受理描述</legend>
		<table>
			<tr>
				<td>
					<textarea name="remarks" rows="5" cols="105"></textarea>
				</td>
			</tr>
		</table>
		
	</fieldset>
	<input type="hidden" name="refids" id="refids">
	<input type="hidden" name="refundImgName" id="refundImgName">
</form>  

