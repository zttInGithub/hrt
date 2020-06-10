<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		var bmthid=<%=request.getParameter("BMTHID")%>;
		$.ajax({
			url:'${ctx}/sysAdmin/merchantThreeUpload_queryDetail.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmthid:bmthid},
   			success:function(data) {
   				var json=eval(data);
	    		if (json!="") {
		    		var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
		    		var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
		    		var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
		    		var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
		    		var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
		    		var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[6]);
		    		var path7='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[7]);
	    			$("#ImgPr1").attr("src",path1);
	    			$("#ImgPr2").attr("src",path2);
	    			$("#ImgPr3").attr("src",path3);
	    			$("#ImgPr4").attr("src",path4);
	    			$("#ImgPr5").attr("src",path5);
	    			$("#ImgPr6").attr("src",path6);
	    			$("#ImgPr7").attr("src",path7);
	    			if(json[8]==''||json[8]==null){
	    				$("#hidden_109621").hide();
	    				$("#hidden_10962").hide();
	    			}else{
	    				$("#download_10962").attr('href','${ctx}/sysAdmin/merchantThreeUpload_downloadVideo.action?merUpload8='+(json[8])+'&mid='+(json[0].mid));
	    			}
	    			
    			}  
	    	} 
	    	
		});
	});
	$('#snImgFile').change(function(){
		var contact = document.getElementById('snImgFile').value;
		document.getElementById("snImg").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
		return utilImg(value); 
	        }, 
	        message : '图片格式不正确' 
		}
	});

	function utilImg(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".jpg" && ename!=".png" && ename!=".gif" && ename!=".jpeg" ){
				return false;
			}else{ 
				return true;
				}
			}
		}
	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_10962_from').after(imgDialog);
		$("#img").attr("src",img);
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
</script>
</head>
<form id="sysAdmin_10962_from" style="padding-left:2%;padding-top:1%" method="post">
	<table class="table">
		<tr>
			<th>商户号：</th>
   			<td>
   				<input type="text" name="MID" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
   			</td>
   			<th>终端号：</th>
	   		<td>
   				<input type="text" name="TID" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   	</tr>
	   	<tr>
			<th>SN号：</th>
	   		<td>
   				<input type="text" name="SN" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   		<th>营业执照名称：</th>
	   		<td>
   				<input type="text" name="LICENSE_NAME" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   	</tr>
	   	<tr>
			<th>经营名称：</th>
	   		<td>
   				<input type="text" name="R_NAME" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   		<th>经营地址：</th>
	   		<td>
   				<input type="text" name="R_ADDR" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   	</tr>
	   	<tr>
			<th>是否支持非接：</th>
	   		<td>
   				<input type="text" name="ISCONNECT" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   		<th>是否支持双免：</th>
	   		<td>
   				<input type="text" name="ISIMMUNITY" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   	</tr>
	   	<tr>
			<th>是否支持银联二维码：</th>
	   		<td>
   				<input type="text" name="ISUNIONPAY" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" readonly="readonly" maxlength="50"/>
	   		</td>
	   	</tr>
	   	<tr>
	   		<th>接交易小票：</th>
	   		<td>
	   			<div><img id="ImgPr1" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
	   		<th>银联二维码交易小票：</th>
	   		<td>
	   			<div><img id="ImgPr2" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
		</tr>
		<tr>
	   		<th>pos机反面照片：</th>
	   		<td>
	   			<div><img id="ImgPr3" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
	   		<th>门店照片：</th>
	   		<td>
	   			<div><img id="ImgPr4" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
		</tr>
		<tr>
	   		<th>店内经营照片：</th>
	   		<td>
	   			<div><img id="ImgPr5" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
	   		<th>云闪付照片：</th>
	   		<td>
	   			<div><img id="ImgPr6" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
		</tr>
		<tr>
	   		<th>银联标识照片：</th>
	   		<td>
	   			<div><img id="ImgPr7" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
	   		<th id="hidden_10962">非接改造操作视频：</th>
	   		<td id="hidden_109621">
	   			<a style="color: #4169E1;text-decoration: underline;cursor:pointer;" id='download_10962' href="">点击下载</a>
	   		</td>
		</tr>
	</table>
</form>