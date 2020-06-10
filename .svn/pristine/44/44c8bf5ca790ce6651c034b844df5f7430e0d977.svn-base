<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>    
<script type="text/javascript">

	$(function(){
		//显示照片
		var roid=<%=request.getParameter("roid")%>;
		var timestamp=new Date().getTime();
		$.ajax({
			url:'${ctx}/sysAdmin/checkReOrder_serachReOrder.action',
    		dataType:"json",  
    		type:"post",
    		data:{roid:roid},
   			success:function(data) {
   				var json=eval(data);
	    		if (json!="") {
	    			if(json.status=="1"||json.status=="3") {
	    				$("#reOrderUploadFile417").attr("disabled",true);
	    				$("#reOrderUploadFile1417").attr("disabled",true);
	    				$("#reOrderUploadFile2417").attr("disabled",true);
	    				$("#reOrderUploadFile3417").attr("disabled",true);
	    				$("#reOrderUploadFile4417").attr("disabled",true);
	    				$("#reOrderUploadFile5417").attr("disabled",true);
	    				
	    			}else{
	    				$("#rebackField").attr("hidden","hidden");
	    			}
	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.reOrderUpload)+'&timestamp='+timestamp;
	    			$("#ImgPr2417").attr("src",path1);
	    			var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.reOrderUpload1)+'&timestamp='+timestamp;
	    			$("#ImgPr3417").attr("src",path2);
	    			var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.reOrderUpload2)+'&timestamp='+timestamp;
	    			$("#ImgPr4417").attr("src",path3);
	    			var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.reOrderUpload3)+'&timestamp='+timestamp;
	    			$("#ImgPr5417").attr("src",path4);
	    			var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.reOrderUpload4)+'&timestamp='+timestamp;
	    			$("#ImgPr6417").attr("src",path5);
	    			var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.reOrderUpload5)+'&timestamp='+timestamp;
	    			$("#ImgPr7417").attr("src",path6);
    			}  
	    	} 
	    	
		});
	});
	
	$(function(){
		 $("#reOrderUploadFile417").uploadPreview({ Img: "ImgPr2", Width: 80, Height: 80 });
		 //alert('ww');
	});
	$(function(){
		 $("#reOrderUploadFile1417").uploadPreview({ Img: "ImgPr3", Width: 80, Height: 80 });
		 //alert('ww');
	});
	$(function(){
		 $("#reOrderUploadFile2417").uploadPreview({ Img: "ImgPr4", Width: 80, Height: 80 });
		 //alert('ww');
	});
	$(function(){
		 $("#reOrderUploadFile3417").uploadPreview({ Img: "ImgPr5", Width: 80, Height: 80 });
		 //alert('ww');
	});
	$(function(){
		 $("#reOrderUploadFile4417").uploadPreview({ Img: "ImgPr6", Width: 80, Height: 80 });
		 //alert('ww');
	});
	$(function(){
		 $("#reOrderUploadFile5417").uploadPreview({ Img: "ImgPr7", Width: 80, Height: 80 });
		 //alert('ww');
	});
	
	//照片详情
	function showBigImg1(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_20417_editForm').after(imgDialog);
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
	
	/* $('#reOrderUploadFile').change(function(){
		var contact = document.getElementById('reOrderUploadFile').value;
		document.getElementById("reOrderUpload").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	$('#reOrderUploadFile1').change(function(){
		var contact = document.getElementById('reOrderUploadFile1').value;
		document.getElementById("reOrderUpload1").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	$('#reOrderUploadFile2').change(function(){
		var contact = document.getElementById('reOrderUploadFile2').value;
		document.getElementById("reOrderUpload2").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); */
	
</script>
 
<form id="sysAdmin_20417_editForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>退单信息</legend>
		<table class="table">
			<tr>
	    		<th>商户编号：</th>
	    		<td><input type="text" name="mid" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="200"/><font color="red">&nbsp;</font></td>
	    		<th>交易日期：</th>
	    		<td><input type="text" name="txnDayStr" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>交易卡号：</th>
	    		<td><input type="text" name="cardPan" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    		<th>交易金额：</th>
	    		<td><input type="text" name="samt" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	
	    	</tr>
	    	<tr>
	    		<th>参考号：</th>
	    		<td><input type="text" name="rrn" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	
	    		<th>调账类型 ：</th>
	    		<td><input type="text" name="refundType" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>原因码 ：</th>
	    		<td ><input type="text" name="reason" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="100"/><font color="red">&nbsp;</font></td>
	    		<th>状态：</th>
	    		<td ><input type="text" name="status" style="width:200px;" readonly="readonly"  data-options="required:true" maxlength="100"/><font color="red">&nbsp;</font></td>
	    	</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>上传单据</legend>
		<table class="table">
			<tr style="height: 100px">
	    		<th>签购单：</th>
	   			<td>
	   				<input type="file" name="reOrderUploadFile" id="reOrderUploadFile417" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr2417" name="ImgPr2" width="120" height="90" onclick="showBigImg1(this.src);"/></div>
					
	    		</td>	
	    		
	    	</tr>
	    	<tr style="height: 100px">
	    		<th>交易流水：</th>
	   			<td>
	   				<input type="file" name="reOrderUploadFile1" id="reOrderUploadFile1417" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr3417" name="ImgPr3" width="120" height="90" onclick="showBigImg1(this.src);"/></div>
	    		</td>	    	
	    	</tr>
	    	<tr style="height: 100px">
	    		<th>身份证正面：</th>
	   			<td>
	   				<input type="file" name="reOrderUploadFile2" id="reOrderUploadFile2417" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr4417" name="ImgPr4" width="120" height="90" onclick="showBigImg1(this.src);"/></div>
	    		</td>	    	
	    	</tr>
	    	
	    	
	    	<tr style="height: 100px">
	    		<th>身份证反面：</th>
	   			<td>
	   				<input type="file" name="reOrderUploadFile3" id="reOrderUploadFile3417" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr5417" name="ImgPr5" width="120" height="90" onclick="showBigImg1(this.src);"/></div>
	    		</td>	    	
	    	</tr>
	    	<tr style="height: 100px">
	    		<th>交易卡正面：</th>
	   			<td>
	   				<input type="file" name="reOrderUploadFile4" id="reOrderUploadFile4417" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr6417" name="ImgPr6" width="120" height="90" onclick="showBigImg1(this.src);"/></div>
	    		</td>	    	
	    	</tr>
	    	<tr style="height: 100px">
	    		<th>持卡人撤销退单声明：</th>
	   			<td>
	   				<input type="file" name="reOrderUploadFile5" id="reOrderUploadFile5417" class="easyui-validatebox" data-options="validType:'imgValidator'"/>
	   			</td>
	    		<th></th>
				<td>
					<div><img id="ImgPr7417" name="ImgPr7" width="120" height="90" onclick="showBigImg1(this.src);"/></div>
	    		</td>	    	
	    	</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>备注</legend>
		<table>
			<tr>
				<td>
					<textarea readonly="readonly" name="commentContext" rows="5" cols="105"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset id="rebackField417"  style="">
		<legend>受理描述</legend>
		<table>
			<tr>
				<td>
					<input type="radio" name="processContext" value="1.小票与实际交易不符！" />1.小票与实际交易不符
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="processContext" value="2.上传资料不合格！" />2.上传资料不合格
				</td>
			</tr>
			<tr>
				<td>
					<textarea name="processContextCommon" rows="5" cols="100"></textarea>
				</td>
			</tr>
		</table>
		
	</fieldset>
	<input type="hidden" name="roid" id="roid417">
	<input type="hidden" name="reOrderUpload" id="reOrderUpload417">
	<input type="hidden" name="reOrderUpload1" id="reOrderUpload1417">
	<input type="hidden" name="reOrderUpload2" id="reOrderUpload2417">
	<input type="hidden" name="reOrderUpload3" id="reOrderUpload3417">
	<input type="hidden" name="reOrderUpload4" id="reOrderUpload4417">
	<input type="hidden" name="reOrderUpload5" id="reOrderUpload5417">
	 
</form>  

