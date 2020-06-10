<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	var bmtuid=<%=request.getParameter("bmtuid")%>;
	$(function(){
		find_10437_img();
	});
	function find_10437_img(){
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTwoUpload_serachMerchantDetailed.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmtuid:bmtuid},
   			success:function(data) {
   				var json=eval(data);
	    		if (json!="") { 
	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0]);
		    		var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
		    		var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
		    		var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
		    		var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
	    			$("#ImgPr1").attr("src",path1);
    				$("#ImgPr2").attr("src",path2);
    				$("#ImgPr3").attr("src",path3);
    				$("#ImgPr4").attr("src",path4);
    				$("#ImgPr5").attr("src",path5);
    			}  
	    	} 
	    	
		});

	//商户基本信息工单申请图片预览
	$("#sysAdmin_10437_from #legalNameFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
	$("#sysAdmin_10437_from #legalName2File").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
	$("#sysAdmin_10437_from #bupLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
	$("#sysAdmin_10437_from #bigdealUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
	$("#sysAdmin_10437_from #laborContractImgFile").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
	}
	$('#legalNameFile').change(function(){
		var contact = document.getElementById('legalNameFile').value;
		document.getElementById("legalName").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#legalName2File').change(function(){
		var contact = document.getElementById('legalName2File').value;
		document.getElementById("legalName2").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#bupLoadFile').change(function(){
		var contact = document.getElementById('bupLoadFile').value;
		document.getElementById("bupLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#bigdealUpLoadFile').change(function(){
		var contact = document.getElementById('bigdealUpLoadFile').value;
		document.getElementById("bigdealUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	});
	
	$('#laborContractImgFile').change(function(){
		var contact = document.getElementById('laborContractImgFile').value;
		document.getElementById("laborContractImg").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
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
		$('#sysAdmin_10437_from').after(imgDialog);
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
	
	function sysAdmin_10436_delete(index){
		var img = "";
		if(index==1){
			img = {bmtuid:bmtuid,legalName:"delete"}
		}else if(index==2){
			img = {bmtuid:bmtuid,legalName2:"delete"}
		}else if(index==3){
			img = {bmtuid:bmtuid,bupLoad:"delete"}
		}else if(index==4){
			img = {bmtuid:bmtuid,bigdealUpLoad:"delete"}
		}else if(index==5){
			img = {bmtuid:bmtuid,laborContractImg:"delete"}
		}
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTwoUpload_deleteUploadImg.action',
    		dataType:"json",  
    		type:"post",
    		data:img,
   			success:function(data) {
   				if (data.success) {
   					find_10437_img();
				} else {
	    			$.messager.alert('提示', result.msg);
				}
	    	} 
		});
	}
</script>
</head>
		<form id="sysAdmin_10437_from" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
				<table class="table">
					<tr>
			   			<th>法人证件正面：</th>
			   			<td>
			   				<input type="file"  id="legalNameFile" name="legalNameFile"  data-options="validType:'imgValidator'"/>
			   				<input type="button" value="删除上传" onclick="sysAdmin_10436_delete(1)">
			   				<div><img id="ImgPr1" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
						<th>法人证件反面：</th>
			   			<td>
			   				<input type="file"  id="legalName2File" name="legalName2File"  data-options="validType:'imgValidator'"/>
			   				<input type="button" value="删除上传" onclick="sysAdmin_10436_delete(2)">
			   				<div><img id="ImgPr2" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<th>营业执照照片：</th>
				   		<td>
				   			<input type="file" name="bupLoadFile" id="bupLoadFile"  data-options="validType:'imgValidator'" />
				   			<input type="button" value="删除上传" onclick="sysAdmin_10436_delete(3)">
				   			<div><img id="ImgPr3" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
						<th>大协议照片：</th>
				   		<td>
				   			<input type="file" name="bigdealUpLoadFile" id="bigdealUpLoadFile"  data-options="validType:'imgValidator'" />
				   			<input type="button" value="删除上传" onclick="sysAdmin_10436_delete(4)">
				   			<div><img id="ImgPr4" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
				   	</tr>
				   	<tr>
				   		<th>含银联云闪付标贴的店内经营照片：</th>
				   		<td>
				   			<input type="file" name="laborContractImgFile" id="laborContractImgFile"  data-options="validType:'imgValidator'" />
				   			<input type="button" value="删除上传" onclick="sysAdmin_10436_delete(5)">
				   			<div><img id="ImgPr5" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
					</tr>
				</table>
			<input type="hidden" name="legalName" id="legalName">
			<input type="hidden" name="legalName2" id="legalName2">
			<input type="hidden" name="bupLoad" id="bupLoad">
			<input type="hidden" name="bigdealUpLoad" id="bigdealUpLoad">
			<input type="hidden" name="laborContractImg" id="laborContractImg">>
			<input type="hidden" name="mid" id="mid">
		</form>