<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		//商户基本信息工单申请图片预览
		$("#sysAdmin_10952_from #snImgFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
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
		$('#sysAdmin_10952_from').after(imgDialog);
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
<form id="sysAdmin_10952_from" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
	<table class="table">
		<tr>
   			<th>机构号：</th>
   			<td>
   				<input type="text" name="UNNO" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/>
   			</td>
			<th>商户号：</th>
   			<td>
   				<input type="text" name="MID" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/>
   			</td>
   		</tr>
   		<tr>
   			<th>终端号：</th>
	   		<td>
   				<input type="text" name="TID" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/>
	   		</td>
			<th>TUSN号：</th>
	   		<td>
   				<input type="text" name="SN" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="50"/>
	   		</td>
	   	</tr>
	   	<tr>
	   		<th>TUSN照片：</th>
	   		<td>
	   			<input type="file" name="snImgFile" id="snImgFile"  data-options="validType:'imgValidator'"/>
	   			<div><img id="ImgPr1" width="120" height="120" onclick="showBigImg(this.src);"/></div>
	   		</td>
		</tr>
	</table>
	<input type="hidden" name="snImg" id="snImg">
</form>