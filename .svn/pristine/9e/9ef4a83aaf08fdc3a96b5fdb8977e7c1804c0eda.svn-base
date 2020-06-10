<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

		//商户风险信息上报
	function check_20502_Report(){
		if(check_20502_jiaoyan()&&check_20502_jiaoyan2()){
			$('#sysAdmin_check_20502').form('submit',{
				   url:'${ctx}/sysAdmin/paymentRisk_reportMerchant',
				  	success:function(msg) {
			    		var result = $.parseJSON(msg);
					    if (result.success) {
						  $.messager.show({ 
							title : '提示',
							msg : result.msg});
						 } else {
						   $.messager.alert('提示', result.msg);
								}
				    	}
				});
		}
	}
	
	
	 //表单查询
	function check_20502_jiaoyan2() {
		var UserToken="${sessionScope.UserToken}";
		if(UserToken==''){
			$.messager.alert('提示','请先登录！ ');
		}else{
			//不能全部为空：商户名称、商户营业执照注册名称、统一社会信用代码、组织机构代码、营业执照编码、法定代表人（负责人）身份证件号码、银行结算账号、网址、服务器IP、法定代表人手机号、ICP编号
			var CusName = $('#CusName').val();
			var RegName = $('#RegName').val();
			var SocialUnityCreditCode = $('#SocialUnityCreditCode').val();
			var OrgCode = $('#OrgCode').val();
			var BusLicCode = $('#BusLicCode').val();
			var LegDocCode = $('#LegDocCode').val();
			var BankNo = $('#BankNo').val();
			var Url = $('#Url').val();
			var ServerIp = $('#ServerIp').val();
			var MobileNo = $('#MobileNo').val();
			var Icp = $('#Icp').val();
			if ( '' == CusName && RegName == '' && SocialUnityCreditCode == '' && OrgCode == ''&& Icp == ''
					&& BusLicCode == '' && LegDocCode == '' && BankNo == ''&& Url == ''&& ServerIp == ''&& MobileNo == '') {
				$.messager.alert('提示','商户名称、商户营业执照注册名称、统一社会信用代码、组织机构代码、营业执照编码、法定代表人身份证件号码、银行结算账号、网址、服务器IP、法定代表人手机号、ICP编号不能全部为空！ ');
			} else {
				return true;
			}
		}
	}
	function check_20502_jiaoyanzzzz(){
			flag=false;
			var RiskType = $('#RiskType2').datebox('getValue');
			
				if(RiskType==''){
					$.messager.alert('提示','请选择风险类型!'); 
					return flag; 
				}else{
					flag=true;
				}
	
			var flag=false;
			var MobileNo = $('#MobileNo2').val();
			var re1=/^((13\d|14[57]|15[^4,\D]|17[678]|18\d)\d{8}|170[059]\d{7})$/;   
				if(!MobileNo.match(re1)&&!MobileNo==''){   
					$.messager.alert('提示','请输入正确的手机号码!'); 
					return flag; 
				}else{
					flag=true;
				}
				
				
			flag=false;
			var Level = $('#Level2').datebox('getValue');
				if(Level==''){
					$.messager.alert('提示','请选择风险信息级别!'); 
					return flag; 
				}else{
					flag=true;
				}
				
			flag=false;
			var OrgId = $('#OrgId2').val();	
			var re4=/^([0-9A-Za-z_]{1,32})$/;
				if(!OrgId.match(re4)){   
					$.messager.alert('提示','请输入正确的上报机构!'); 
					return flag; 
				}else{
					flag=true;
				}
				
			flag=false;
			var RepPerson = $('#RepPerson2').val();	
				if(RepPerson==''){
					$.messager.alert('提示','上传人不可为空!'); 
					return flag; 
				}else{
					flag=true;
				}
				
			flag=false;
			var ValidDate = $('#ValidDate2').datebox('getValue');	
				if(ValidDate==''){
					$.messager.alert('提示','有效期不可为空!'); 
					return flag; 
				}else{
					flag=true;
				}
				
			return flag;
	}
</script>

<!-- <div  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:400px; overflow: hidden; padding-top:16px;"> -->
		<form id="sysAdmin_check_20502" method="post">
			<table align="center" valign="center" >
				<tr>
					<th>风险类型<font color="red">&nbsp;*</font></th>
					<td>
					 <select class="easyui-combobox" id="RiskType2" name="RiskType"  data-options="required:true"  style="width:186px;"><!-- data-options="panelHeight:'auto',editable:false" -->
							<option value=""></option> 
							<option value="01">虚假申请</option> 
							<option value="02">套现、套积分</option>
							<option value="03">违法经营</option>
							<option value="04">销赃</option>
							<option value="05">买卖或租借银行账户</option> 
							<option value="06">侧录点</option>
							<option value="07">伪卡集中使用点</option>
							<option value="08">泄露账户及交易信息</option>
							<option value="09">恶意倒闭</option> 
							<option value="10">恶意分单</option>
							<option value="11">移机</option>
							<option value="12">高风险商户</option>
							<option value="13">商户合谋欺诈</option> 
							<option value="14">破产或停业商户</option>
							<option value="15">强迫签单</option>
							<option value="17">频繁变更服务机构</option> 
							<option value="18">关联商户涉险</option>
							<option value="19">买卖银行卡信息</option>
							<option value="99">其他</option>
					   </select>
					</td>	
					<th>商户属性</th>
					<td> 
						<select class="easyui-combobox" name="CusNature" data-options="panelHeight:'auto',editable:false" style="width:186px;">
							<option value=""></option> 
							<option value="01">实体商户</option> 
							<option value="02">网络商户</option>
						 </select>	
					</td>				
				</tr>
				<tr>
					<th>商户名称</th>
					<td><input id="CusNameup" name="CusName" style="width: 180px;" /></td>				
					<th>营业注册名称</th>
					<td><input id="RegNameup" name="RegName" style="width: 180px;" /></td>
					
				</tr>
				<tr>
					<th>商户编码</th>
					<td><input name="CusCode" style="width: 180px;" /></td>
					<th>组织机构代码</th>
					<td><input id="OrgCodeup" name="OrgCode" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>营业执照编码</th>
					<td><input id="BusLicCodeup" name="BusLicCode" style="width: 180px;" /></td>
					<th>社会信用代码</th>
					<td><input id="SocialUnityCreditCodeup" name="SocialUnityCreditCode" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>税务登记证</th>
					<td><input  name="TaxRegCer" style="width: 180px;"/></td>
					<th>法定代表人姓名</th>
					<td><input name="LegRepName" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>法人身份证件号码</th>
					<td><input id="LegDocCodeup" name="LegDocCode" style="width: 180px;" /></td>
					<th>银行结算账号</th>
					<td><input id="BankNoup" name="BankNo" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>开户行</th>
					<td><input id="OpenBank" name="OpenBank" style="width: 180px;"/></td>
					<th>风险事件时间</th>
					<td><input id="Occurtimeb" name="Occurtimeb" class="easyui-datebox"style="width: 83px;"/>
					<a>&nbsp;-&nbsp;&nbsp;</a><input id="Occurtimee" name="Occurtimee" class="easyui-datebox"style="width: 83px;"/>
					</td>
				</tr>
				<tr>
					<th>网址</th>
					<td><input id="Urlup" name="Url" style="width: 180px;"/></td>
					<!-- <th>风险事件结束时间</th> 
					<td><input id="Occurtimee" name="Occurtimeb" class="easyui-datebox"style="width: 180px;"/></td>-->
					<th>风险信息等级<font color="red">&nbsp;*</font></th>
					<td> <select class="easyui-combobox" id="Level2" name="Level"  data-options="required:true"  style="width:186px;">
							<option value=""></option> 
							<option value="01">一级</option> 
							<option value="02">二级</option>
							<option value="03">三级</option>
					   </select>
					  </td>
					
				</tr>
				<tr>
					<th>手机</th>
					<td><input id="MobileNo2up" name="MobileNo" style="width: 180px;" /></td>
					<th>地域</th>
					<td><input name="Address" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>ICP编号</th>
					<td><input id="Icpup" name="Icp" style="width: 180px;" /></td>
					
					<th>上报机构<font color="red">&nbsp;*</font></th>
					<td><input id="OrgId2" name="OrgId"  class="easyui-validatebox" data-options="required:true"  style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>上传人<font color="red">&nbsp;*</font></th>
					<td><input id="RepPerson2" name="RepPerson"  class="easyui-validatebox" data-options="required:true"  style="width: 180px;"/></td>
					<th>风险事件发生渠道</th>
					<td>
						<select class="easyui-combobox" name="Occurchan" data-options="panelHeight:'auto',editable:false" style="width:186px;">
							<option value=""></option> 
							<option value="01">线上</option> 
							<option value="02">线下</option>
						 </select>
					</td>
				</tr>
				<tr>
					<th>风险事件发生地域</th>
					<td>
						<select class="easyui-combotree" id="Occurarea" name="Occurarea" multiple  style="width:186px;" 
	    				data-options="
		    				lines:true,
		    				cascadeCheck:false,
		    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
	    				">
	    				</select>
    				</td>
					<th>服务器IP</th>
					<td><input id="ServerIpup"  name="ServerIp" style="width: 180px;" /></td>
				</tr>
				<tr>
					<th>风险事件描述</th>
					<td><input name="Note" style="width: 180px;" /></td>
					<th>有效期<font color="red">&nbsp;*</font></th>
					<td><input id="ValidDate2" name="ValidDate" class="easyui-datebox"  data-options="required:true" style="width: 186px;"/></td>
				</tr>
			<!-- 	<tr>
					
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20502_Report();">上报</a> 
					</td>
				</tr> -->
			</table>
		</form>
<!-- 	</div>  
</div> -->