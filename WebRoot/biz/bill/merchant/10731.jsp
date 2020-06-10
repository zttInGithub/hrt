<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--添加黑名单商户信息 -->
 <script type="text/javascript">
 $("#bankAccNo").change(function(){
 	 document.getElementById("tiderror").innerText = '';
	 var cardPan = $("#bankAccNo").val();
	 if(isNaN(cardPan)){
	 alert("卡号只能输入数字");
	 return false;
	 } 
      
		})
</script>

<!-- 添加黑卡信息 -->
<form id="hotmerchant_addForm" method="post">
	<table style="padding-top: 5px;padding-left:20px">
		
		<tr>
			<td>商户名称：</td>
			<td><input name="tname" class="easyui-validatebox" data-options="required:true" style="width:200px;"/> </td>
		</tr>
		<tr>
			<td>SN号：</td>
			<td><input name="sn" class="easyui-validatebox"  style="width:200px;"/> </td>
		</tr>
		<tr>
			<td>法人身份证：</td>
			<td><input name="identificationNumber" class="easyui-validatebox"  style="width:200px;"/> </td>
		</tr>
		<tr>
			<td>营业执照号：</td>
			<td><input name="license" class="easyui-validatebox"  style="width:200px;"/> </td>
		</tr>
		
		<tr>
			<td style="width:135px;">入账卡号：</td>
			<td><input id = "bankAccNo" name="bankAccNo" class="easyui-validatebox"  style="width:200px;"/> <span id="tiderror" style="color:red;"></td>
		</tr>
		<tr>
			<td>备注：</td>
			<td><input name="remarks" class="easyui-validatebox"  style="width:200px;"/> </td>
		</tr>
	</table>
</form>

