<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	$(function(){
		//判断是否退回
		var go=<%=request.getParameter("go")%>;
		if(go=='true'){
			$("#backinfo").hide();
		}
		//显示照片
		var bmatid=<%=request.getParameter("bmatid")%>;
		var timestamp=new Date().getTime();
		$.ajax({
			url:'${ctx}/sysAdmin/merchantAuthenticity_serachTxnAuthInfoDetailed.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmatid:bmatid},
   			success:function(data) {
   				var json=eval(data);
	    		if (json!="") { 
	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json.authUpload)+'&timestamp='+timestamp;
	    			$("#authUpload").attr("src",path1);
    			}  
	    	} 
	    	
		});
	});
	
	//照片详情
	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_10645_editForm').after(imgDialog);
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 800,   
			    height: 600,
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
 
<form id="sysAdmin_10645_editForm" method="post">
	<fieldset>
		<legend>交易认证信息</legend>
		<table class="table">
			<tr>
	    		<th>APP用户名：</th>
	    		<td><input type="text" name="username" style="width:200px;" readonly="readonly" class="easyui-validatebox" maxlength="100"/><font color="red">&nbsp;</font></td>
	
	    		<th>商户编号：</th>
	    		<td><input type="text" name="mid" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="200"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>持卡人名称：</th>
	    		<td><input type="text" name="bankAccName" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;*</font></td>
	
	    		<th>卡号：</th>
	    		<td><input type="text" name="bankAccNo" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;*</font></td>
	    	</tr>
	    	<tr>
	    		<th>身份证号：</th>
	    		<td><input type="text" name="legalNum" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;*</font></td>
	
	    		<th>认证时间：</th>
	    		<td><input type="text" name="cdate" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
	    	<tr>
	    		<th>认证状态：</th>
	    		<td><input type="text" name="status1" value="认证失败" style="width:200px;"  readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    		
	    		<th>认证返回信息：</th>
	    		<td><input type="text" name="respinfo" style="width:200px;" readonly="readonly" class="easyui-validatebox" data-options="required:true" maxlength="20"/><font color="red">&nbsp;</font></td>
	    	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>照片信息</legend>
		<table class="table">
			<tr>
	    		<th>照片：</th>
	    		<td>
	    			<img id="authUpload" name="authUpload" src="" width="160" height="120" border="0" onclick="showBigImg(this.src);" />
	    		</td>
	    		<th></th>
				<td>
	    		</td>	    	
	    	</tr>
		</table>
	</fieldset>
	<fieldset id = "backinfo">
		<legend>退回信息</legend>
		<table class="table">
			<tr>
	    		<th>退回原因：</th>
	    		<td>
	    			<select id="approveNote" name="approveNote"  style="width:180px;height:25px" class="easyui-validatebox" data-options="required:true">
	    					<option selected="selected" >照片信息不符</option>
	    					<option>姓名输入有误</option>
	    					<option>身份证信息录入错误</option>
	    					<option>卡片不是交易卡片</option>
	    					<option>银行卡照片未上传</option>
	    					<option>身份证照片未上传</option>
	    					<option>请上传银行卡和身份证原件的正面照片</option>
	    					<option>照片内容不清晰,请重新上传</option>
	    			</select>
	    			<font color="red">&nbsp;*</font>
	    		</td>
	    		<th></th>
	    		<td>
	    		</td>
	    	</tr>
		</table>
	</fieldset>
	<input type="hidden" name="bmatid" id="bmatid">
	<input type="hidden" name="unno">
</form>  

