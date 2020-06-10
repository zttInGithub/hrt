<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>   
<script type="text/javascript">
	$(function(){
		$.extend($.fn.validatebox.defaults.rules, {
			intOrFloat : {// 验证整数或小数   
        	validator : function(value) {
        		 return /^\d+(\.\d+)?$/i.test(value);   
        	},   
        	message : '请输入整数或小数，并确保格式正确'   
    		}
		});
		//绑定图片选定后预览 
		bindImgPreview();
		
	});
	
	function bindImgPreview(){ 
		$("#bnoImgFile").uploadPreview({ Img: "ImgBno", Width: 120, Height: 120 });
		$("#identityImgFile").uploadPreview({ Img: "ImgIdentity", Width: 120, Height: 120 });
		$("#bankCardImgFile").uploadPreview({ Img: "ImgBankCard", Width: 120, Height: 120 });
		$("#activityImgFile").uploadPreview({ Img: "ImgActivity", Width: 120, Height: 120 });
		$("#orderImgFile").uploadPreview({ Img: "ImgOrder", Width: 120, Height: 120 });
		$("#ortherImgFile").uploadPreview({ Img: "ImgOrther", Width: 120, Height: 120 });
	}
</script>
 
<form id="sysAdmin_10533_addForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>扫码工单信息</legend>
		<input  type="hidden" name="mtype" value="1">
		<table class="table">
			<tr>
				<th style="width: 100px">商户编号：</th>
				<td><input name="mid" style="width:150px"  type="text"/> </td> 
	    		<th></th>
	    		<td>
	    			<input type="hidden" name="isAuthorized" value="0" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>单笔限额：</th>
	    		<td><input type="text" name="singleLimit" style="width:100px;"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/> 元</td>
	    		<th>单日限额：</th>
	    		<td><input type="text" name="dailyLimit" style="width:100px;"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/> 元</td>
			</tr>
			<tr>
				<th style="height: 150px">营业执照：</th>
				<td>
					<input type="file" name="bnoImgFile"  id="bnoImgFile">
					<div><img id="ImgBno" width="120" height="120" /></div>
				</td>
				<th style="height: 150px">法人手持身份证照片：</th>
				<td>
					<input type="file" name="identityImgFile"  id="identityImgFile">
					<div><img id="ImgIdentity" width="120" height="120" /></div>
				</td>
			</tr>
			<tr>
				<th>店面经营照片：</th>
				<td>
					<input type="file" name="activityImgFile" id="activityImgFile">
					<div><img id="ImgActivity" width="120" height="120" /></div>
				</td>
				<th style="height: 150px">交易流水证明：</th>
				<td>
					<input type="file" name="orderImgFile" id="orderImgFile">
					<div><img id="ImgOrder" width="120" height="120" /></div>
				</td>
			</tr>
			<tr>
				<th>补充证明材料：</th>
				<td>
					<input type="file" name="ortherImgFile" id="ortherImgFile">
					<div><img id="ImgOrther" width="120" height="120" /></div>
				</td>
				<th style="height: 150px"></th>
				<td>
				</td>
			</tr>
		</table>
	</fieldset>
</form>  

