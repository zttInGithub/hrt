<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_serachAgentInfoDetailed2.action',
			dataType:"json",  
			type:"post",
			success:function(data) {
				var json=eval(data);
				if (json!="") { 
					$("#agentName").val(json[0].agentName);
					$("#legalType").val(json[0].legalType);
					$("#bno").val(json[0].bno);
					$("#baddr").val(json[0].baddr);
					$("#legalPerson").val(json[0].legalPerson);
					$("#legalNum").val(json[0].legalNum);
				} 
			} 
		});
		//商户基本信息工单申请图片预览
		$("#sysAdmin_00331_from #legalAUpLoadFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#sysAdmin_00331_from #legalBUpLoadFile").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#sysAdmin_00331_from #busLicUpLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#sysAdmin_00331_from #dealUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
	});
	$('#legalAUpLoadFile').change(function(){
		var contact = document.getElementById('legalAUpLoadFile').value;
		document.getElementById("legalAUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#legalBUpLoadFile').change(function(){
		var contact = document.getElementById('legalBUpLoadFile').value;
		document.getElementById("legalBUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#busLicUpLoadFile').change(function(){
		var contact = document.getElementById('busLicUpLoadFile').value;
		document.getElementById("busLicUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#dealUpLoadFile').change(function(){
		var contact = document.getElementById('dealUpLoadFile').value;
		document.getElementById("dealUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
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
		$('#sysAdmin_00331_from').after(imgDialog);
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
		<form id="sysAdmin_00331_from" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 850px;">
				<legend>基础信息</legend>
				<table class="table">
					<tr>
			    		<th>代理商全称：</th>
			    		<td><input type="text" name="agentName" id="agentName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
						<th>营业执照号：</th>
						<td><input type="text" name="bno" id="bno" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
					</tr>
			    	<tr>
						<th>营业地址：</th>
			    		<td >
							<input type="text" name="baddr" id="baddr" style="width:200px;" maxlength="100"  class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"/><font color="red">&nbsp;*</font>
						</td>
			    		<th>法人：</th>
			    		<td><input type="text" name="legalPerson" id="legalPerson" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    		
			    	</tr>
			    	<tr>
				    	<th>法人证件类型：</th>
				    	<td>
				    		<select name="legalType" id="legalType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
				    			<option value="1">身份证</option>
				    			<option value="2">军官证</option>
				    			<option value="3">护照</option>
				    			<option value="4">港澳通行证</option>
				    			<option value="5">其他</option>
				    		</select>
				    	</td>
			    		<th >法人证件号码：</th>
			    		<td ><input type="text" name="legalNum" id="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="20"/><font color="red">&nbsp;*</font></td>
			    	</tr>
					<tr>
			   			<th>法人证件正面：</th>
			   			<td>
			   				<input type="file"  id="legalAUpLoadFile" name="legalAUpLoadFile"  data-options="validType:'imgValidator'"/>
			   				<div><img id="ImgPr1" src="" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
						<th>法人证件反面：</th>
			   			<td>
			   				<input type="file"  id="legalBUpLoadFile" name="legalBUpLoadFile"  data-options="validType:'imgValidator'"/>
			   				<div><img id="ImgPr2" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<th>营业执照照片：</th>
				   		<td>
				   			<input type="file" name="busLicUpLoadFile" id="busLicUpLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr3" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
						<th>协议签章页照片：</th>
				   		<td>
				   			<input type="file" name="dealUpLoadFile" id="dealUpLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr4" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
				   	</tr>
				</table>
			</fieldset>
			<input type="hidden" name="legalAUpLoad" id="legalAUpLoad">
			<input type="hidden" name="legalBUpLoad" id="legalBUpLoad">
			<input type="hidden" name="busLicUpLoad" id="busLicUpLoad">
			<input type="hidden" name="dealUpLoad" id="dealUpLoad">
			<input type="hidden" name="taskType" value="1">
		</form>
