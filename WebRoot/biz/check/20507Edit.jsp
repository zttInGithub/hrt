<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	function findCardLeng(){
 		//if( ($('#cardAnd').length>0 && $('#cardAnd').val()!="") ){
 		//var cardAnd = $('#cardAnd').val();
 		//var cardLeng=$('#cardLeng').val();
			 $.ajax({
	                url: '${ctx}/sysAdmin/paymentRisk_updatePersonalChange',
	                type: 'post',
	              //  data : "cardAnd="+cardAnd, 
	                success: function(msg){
	                	var result = $.parseJSON(msg);
	                	if (result.success){
		               		$.messager.show({
								title : '提示',
								msg : res.msg
							});
		               		//$('#cardLeng').val(result.msg); 
							//$('#cardLeng').attr("readonly","readonly");
						}else{
							$.messager.alert('提示', res.msg);
							//$('#cardLeng').attr("value",cardLeng); 
							//$('#cardLeng').removeAttr("readonly");
						}
	                }
	            });
		}
	//}
</script>
<form id="check_20507_editForm" method="post">
	<table style="padding-top: 5px;padding-left:20px">
			<tr>
				<th>唯一标识</th>
				<td><input name="Id" style="width: 180px;" readonly="readonly" /></td>
				<th>变更类型<font color="red">&nbsp;*</font></th><!-- 多选 -->
				<td>
				 <select class="easyui-combobox" data-options="required:true" name="UpdateType"  style="width:186px;">
							<option value=""></option>
							<option value="01">补录</option> 
							<option value="02">失效</option>
					   </select>
				</td>
			</tr>
			<tr>
				<th>客户属性<font color="red">&nbsp;*</font></th>
				<td>
				 	<select class="easyui-combobox" name="CusProperty" data-options="required:true" style="width:186px;">
							<option value="01">个人</option> 
							<option value="02">商户</option>
					 </select>
				</td>
				<th>风险类型<font color="red">&nbsp;*</font></th>
				<td>
					 <select class="easyui-combobox" name="RiskType" data-options="required:true" style="width:186px;">
							<option value=""></option> 
							<option value="01">虚假申请（受害人信息）</option> 
							<option value="02">虚假申请（嫌疑人信息）</option>
							<option value="03">伪卡（受害人信息）</option>
							<option value="04">失窃卡（受害人信息）</option>
							<option value="05">未达卡（受害人信息）</option> 
							<option value="06">银行卡转移（受害人信息）</option>
							<option value="07">盗用银行卡（嫌疑人信息）</option>
							<option value="08">银行卡网络欺诈（受害人信息）</option>
							<option value="09">银行卡网络欺诈（嫌疑人信息）</option> 
							<option value="10">虚拟账户被盗（受害人信息）</option>
							<option value="11">盗用虚拟账户（嫌疑人信息）</option>
							<option value="12">套现、套积分（嫌疑人信息）</option>
							<option value="13">协助转移赃款</option> 
							<option value="14">买卖或租借银行（支付）账户</option>
							<option value="15">专案风险信息</option>
							<option value="17">买卖银行卡信息</option>
							<option value="99">其他</option>
					   </select>
					</td>	
				</tr>
				<tr>
					<th>手机号</th>
					<td><input name="MobileNo" style="width: 180px;" /></td>
					<th>MAC地址</th>
					<td><input  name="Mac" style="width: 180px;"/></td>
				</tr>
				<tr>
					<th>Imei</th>
					<td><input  name="Imei" style="width: 180px;"/></td>
					<th>银行卡账号</th>
					<td><input name="BankNo" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>开户行</th>
					<td><input name="OpenBank" style="width: 180px;" /></td>
					<th>客户姓名</th>
					<td><input name="CusName" style="width: 180px;" /></td>	
				</tr>
				<tr>			
					<th>身份证</th>
					<td><input name="DocCode" style="width: 180px;" /></td>
					<th>IP地址</th>
					<td><input name="Ip" style="width: 180px;" /></td>
				</tr>
				<tr>	
					<th>收货地址</th>
					<td><input name="Address" style="width: 180px;" /></td>
					<th>固话</th>
					<td><input  name="Telephone" style="width: 180px;"/></td>
				</tr>
				<tr>
					<th>收款银卡号</th>
					<td><input name="RecBankNo" style="width: 180px;" /></td>
					<th>收款卡号开户行</th>
					<td><input name="RecOpenBank" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>Email</th>
					<td><input name="Email" style="width: 180px;" /></td>
					<th>有效期</th><!-- yyyy-MM-DD -->
					<td><input name="ValidDate" class="easyui-datebox"  data-options="required:true" style="width: 186px;" /></td>
				</tr>
				<tr>	
					<th>信息级别<font color="red">&nbsp;*</font></th>
					<td> <select class="easyui-combobox" id="Level" name="Level"  data-options="required:true"  style="width:186px;">
							<option value=""></option> 
							<option value="01">一级</option> 
							<option value="02">二级</option>
							<option value="03">三级</option>
					   </select>
					  </td>
					<th>事件发生时间</th>
					<td><input id="Occurtimeb" name="Occurtimeb" class="easyui-datebox"style="width: 83px;"/>
					<a>&nbsp;-&nbsp;&nbsp;</a><input id="Occurtimee" name="Occurtimee" class="easyui-datebox"style="width: 83px;"/>
					</td>
				</tr>
				<tr>
					<th>事件发生渠道</th>
					<td>
						<select class="easyui-combobox" name="Occurchan"  style="width:186px;">
							<option value=""></option> 
							<option value="01">线上</option> 
							<option value="02">线下</option>
						 </select>	
					</td>
					<th>事件发生地域</th><!-- 多选 -->
					<td>
						<select class="easyui-combotree" id="Occurarea" name="Occurarea" multiple  style="width:186px;" 
	    				data-options="
		    				lines:true,
		    				cascadeCheck:false,
		    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
	    				">
	    				</select>
    				</td>
				</tr>
				<tr>
					<th>上报机构<font color="red">&nbsp;*</font></th>
					<td><input id="OrgId" name="OrgId" class="easyui-validatebox" data-options="required:true" style="width: 180px;" /></td>
					<th>上传人<font color="red">&nbsp;*</font></th>
					<td><input id="RepPerson" name="RepPerson" class="easyui-validatebox" data-options="required:true" style="width: 180px;" /></td>
				</tr>
	</table>
</form>

