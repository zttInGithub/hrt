<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	var bautdid=<%=request.getParameter("bautdid")%>;
	$(function(){
		$.ajax({
			url:'${ctx}/sysAdmin/agentUnitTask_queryAgentUnitTaskDetail.action',
			dataType:"json",  
			data:{bautdid:bautdid,taskType:2},
			type:"post",
			success:function(data) {
				var json=eval(data);
				console.log(json)
				if (json!="") { 
					var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
					var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
					var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
					var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
					$("#bankBranch").val(json[0].bankBranch);
					$("#bankAccNo").val(json[0].bankAccNo);
					$("#bankAccName").val(json[0].bankAccName);
					$("#bankArea").val(json[0].bankArea);
					if(json[0].accType==1){
					}else{
						$("#accType2").attr("checked","checked");  
					    $("#accType1").removeAttr("checked");
					}
					if(json[0].bankType==1){
					}else{
						$("#bankType2").attr("checked","checked");  
					    $("#bankType1").removeAttr("checked");
					}
					if(json[0].areaType==1){
					}else{
						$("#areaType2").attr("checked","checked");  
					    $("#areaType1").removeAttr("checked");
					}
					$("#ImgPr1").attr("src",path1);
					$("#ImgPr2").attr("src",path2);
					$("#ImgPr3").attr("src",path3);
					$("#ImgPr4").attr("src",path4);
				} 
			} 
		});
		
		//商户基本信息工单申请图片预览
		$("#sysAdmin_00338_from #accountLegalAUpLoadFile").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });
		$("#sysAdmin_00338_from #accountLegalBUpLoadFile").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });
		$("#sysAdmin_00338_from #accountLegalHandUpLoadFile").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });
		$("#sysAdmin_00338_from #accountAuthUpLoadFile").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });
	});
	$('#accountLegalAUpLoadFile').change(function(){
		var contact = document.getElementById('accountLegalAUpLoadFile').value;
		document.getElementById("accountLegalAUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#accountLegalBUpLoadFile').change(function(){
		var contact = document.getElementById('accountLegalBUpLoadFile').value;
		document.getElementById("accountLegalBUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#accountLegalHandUpLoadFile').change(function(){
		var contact = document.getElementById('accountLegalHandUpLoadFileaccountLegalHandUpLoadFile').value;
		document.getElementById("accountLegalHandUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
	}); 
	
	$('#accountAuthUpLoadFile').change(function(){
		var contact = document.getElementById('accountAuthUpLoadFile').value;
		document.getElementById("accountAuthUpLoad").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
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
		$('#sysAdmin_00338_from').after(imgDialog);
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
		<form id="sysAdmin_00338_from" style="padding-left:2%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 850px;">
				<legend>结算信息</legend>
				<table class="table">
					<tr>
						<th>开户类型：</th>
			    		<td>
			    			<input type="radio" name="accType" id="accType1" value="1" checked="checked"/>对公
							<input type="radio" name="accType" id="accType2" value="2" />对私
			    		</td>
						<th>开户行名称：</th>
						<td><input type="text" name="bankBranch" id="bankBranch" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="200"/><font color="red">&nbsp;*</font></td>
					</tr>
					<tr>
						<th>账号：</th>
						<td><input type="text" name="bankAccNo" id="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="30"/><font color="red">&nbsp;*</font></td>
						<th>账户名称：</th>
						<td><input type="text" name="bankAccName" id="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
		    		</tr>
		    		<tr>
			    		<th>是否为交通银行：</th>
			    		<td>
							<input type="radio" name="bankType" id="bankType1" value="1" checked="checked"/>交通银行
							<input type="radio" name="bankType" id="bankType2" value="2" />非交通银行
						</td>
						<th>开户银行所在地类别：</th>
			    		<td>
			    			<input type="radio" name="areaType" id="areaType1" value="1" checked="checked"/>北京
							<input type="radio" name="areaType" id="areaType2" value="2" />非北京
			    		</td>
					</tr>
					<tr>
						<th>开户地：</th>
						<td><input type="text" name="bankArea" id="bankArea" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="100"/><font color="red">&nbsp;*</font></td>
			    	</tr>
			    	<tr>
			   			<th>入账人身份证正面：</th>
			   			<td>
			   				<input type="file"  id="accountLegalAUpLoadFile" name="accountLegalAUpLoadFile"  data-options="validType:'imgValidator'"/>
			   				<div><img id="ImgPr1" src="" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
						<th>入账人身份证反面：</th>
			   			<td>
			   				<input type="file"  id="accountLegalBUpLoadFile" name="accountLegalBUpLoadFile"  data-options="validType:'imgValidator'"/>
			   				<div><img id="ImgPr2" width="120" height="120" onclick="showBigImg(this.src);"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<th>结算银行卡：</th>
				   		<td>
				   			<input type="file" name="accountLegalHandUpLoadFile" id="accountLegalHandUpLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr3" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
						<th>入账授权书：</th>
				   		<td>
				   			<input type="file" name="accountAuthUpLoadFile" id="accountAuthUpLoadFile"  data-options="validType:'imgValidator'"/>
				   			<div><img id="ImgPr4" width="120" height="120" onclick="showBigImg(this.src);"/></div>
				   		</td>
				   	</tr>
				</table>
			</fieldset>
			<input type="hidden" name="accountLegalAUpLoad" id="accountLegalAUpLoad">
			<input type="hidden" name="accountLegalBUpLoad" id="accountLegalBUpLoad">
			<input type="hidden" name="accountLegalHandUpLoad" id="accountLegalHandUpLoad">
			<input type="hidden" name="accountAuthUpLoad" id="accountAuthUpLoad">
			<input type="hidden" name="taskType" value="2">
			<input type="hidden" name="bautdid" id="bautdid">
		</form>
