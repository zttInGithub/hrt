<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		//商户基本信息工单申请图片预览
		$("#sysAdmin_10435_from #legalNameFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#sysAdmin_10435_from #legalName2File").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#sysAdmin_10435_from #bupLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#sysAdmin_10435_from #bigdealUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
		$("#sysAdmin_10435_from #laborContractImgFile").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
	});
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
		$('#sysAdmin_10435_from').after(imgDialog);
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
		<form id="sysAdmin_10435_from" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 900px;">
				<legend>照片信息</legend>
				<table class="table">
					<tr>
						<th>商户号</th>
						<td colspan="3"><input type="text" name="mid" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"   maxlength="50"/></td>
					</tr>
					<tr>
			   			<th>法人证件正面：</th>
			   			<td>
			   				<input type="file"  id="legalNameFile" name="legalNameFile"  data-options="validType:'imgValidator'"/>
			   				<div><img id="ImgPr1" src="" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
						<th>法人证件反面：</th>
			   			<td>
			   				<input type="file"  id="legalName2File" name="legalName2File"  data-options="validType:'imgValidator'"/>
			   				<div><img id="ImgPr2" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<th>营业执照照片：</th>
				   		<td>
				   			<input type="file" name="bupLoadFile" id="bupLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr3" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
						<th>大协议照片：</th>
				   		<td>
				   			<input type="file" name="bigdealUpLoadFile" id="bigdealUpLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr4" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
				   	</tr>
				   	<tr>
				   		<th>含银联云闪付标贴的店内经营照片：</th>
				   		<td>
				   			<input type="file" name="laborContractImgFile" id="laborContractImgFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr5" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
					</tr>
				</table>
			</fieldset>
			<input type="hidden" name="legalName" id="legalName">
			<input type="hidden" name="legalName2" id="legalName2">
			<input type="hidden" name="bupLoad" id="bupLoad">
			<input type="hidden" name="bigdealUpLoad" id="bigdealUpLoad">
			<input type="hidden" name="laborContractImg" id="laborContractImg">
		</form>
