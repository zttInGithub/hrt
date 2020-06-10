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
		bindImgPreview_10531();
	});
	
	function bindImgPreview_10531(){ 
		$("#bnoImgFile_10531").uploadPreview({ Img: "ImgBno_10531", Width: 120, Height: 120 });
		 $("#identityImgFile_10531").uploadPreview({ Img: "ImgIdentity_10531", Width: 120, Height: 120 });
		$("#bankCardImgFile_10531").uploadPreview({ Img: "ImgBankCard_10531", Width: 120, Height: 120 });
		$("#activityImgFile_10531").uploadPreview({ Img: "ImgActivity_10531", Width: 120, Height: 120 });
		$("#orderImgFile_10531").uploadPreview({ Img: "ImgOrder_10531", Width: 120, Height: 120 });
		$("#ortherImgFile_10531").uploadPreview({ Img: "ImgOrther_10531", Width: 120, Height: 120 }); 
	}
</script>
 
<form id="sysAdmin_10531_addForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>工单信息</legend>
		<input type="text" hidden="hidden" name="mtype" value="0">
		<table class="table">
			<tr>
				<th >商户编号：</th>
				<td><input name="mid" style="width:200px"  type="text"/> </td>
				<th></th>
	    		<td>
	    			<input type="hidden" name="isAuthorized" value="0" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>贷记卡单笔限额：</th>
	    		<td><input type="text" name="singleLimit" style="width:100px;"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/>元</td>
	    		<th>贷记卡单日限额：</th>
	    		<td><input type="text" name="dailyLimit" style="width:100px;"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/>元</td>
<!-- 				<th></th>
	    		<td>
	    			<input type="hidden" name="isAuthorized" value="0" />
	    		</td> -->
			</tr>
			<tr>
	    		<th>借记卡单笔限额：</th>
	    		<td><input type="text" name="singleLimit1" style="width:100px;"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/>元</td>
	    		<th>借记卡单日限额：</th>
	    		<td><input type="text" name="dailyLimit1" style="width:100px;"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/>元</td>
<!-- 				<th></th>
	    		<td>
	    			<input type="hidden" name="isAuthorized" value="0" />
	    		</td> -->
			</tr>
			<tr>
				<th style="height: 150px">营业执照：</th>
				<td>
					<input type="file" name="bnoImgFile"  id="bnoImgFile_10531">
					<div><img id="ImgBno_10531" width="120" height="120" /></div>
				</td>
				<th style="height: 150px">法人手持身份证照片：</th>
				<td>
					<input type="file" name="identityImgFile"  id="identityImgFile_10531">
					<div><img id="ImgIdentity_10531" width="120" height="120" /></div>
				</td>
			</tr>
			 <tr>
				<th>门头照：</th>
				<td>
					<input type="file" name="activityImgFile" id="activityImgFile_10531">
					<div><img id="ImgActivity_10531" width="120" height="120" /></div>
				</td>
				<th style="height: 150px">大额交易小票：</th>
				<td>
					<input type="file" name="orderImgFile" id="orderImgFile_10531">
					<div><img id="ImgOrder_10531" width="120" height="120" /></div>
				</td>
			</tr>
			<tr>
				<th>内部经营照：</th>
				<td>
					<input type="file" name="ortherImgFile" id="ortherImgFile_10531">
					<div><img id="ImgOrther_10531" width="120" height="120" /></div>
				</td>
				<th style="height: 150px">银行卡/对公账户</th>
				<td>
					<input type="file" name="bankCardImgFile" id="bankCardImgFile_10531">
					<div><img id="ImgBankCard_10531" width="120" height="120" /></div>
				</td>
			</tr> 
		</table>
	</fieldset>
</form>  

