<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	
</script>
<form id="check_20510_uploadForm" method="post">
	<!-- <input type="hidden" id="occurarea" name="Occurarea" />  -->
	<fieldset>
		<table class="table">
			<!-- class="tableForm"  -->
			<tr>
				<th>商户类型<font color="red">&nbsp;*</font>
				</th>
				<td><select id="CusType" name="CusType" class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<!-- <option value="01">自然人</option> -->
						<option value="02">企业商户</option>
						<option value="03">个体商户</option>
				</select></td>
				<th>商户属性<font color="red">&nbsp;*</font>
				</th>
				<td><select class="easyui-combobox" name="CusNature"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<option value=""></option>
						<option value="01">实体特约商户</option>
						<option value="02">网络特约商户</option>
						<option value="03">实体兼网络特约商户</option>
				</select></td>
			</tr>
			<tr>
				<th>商户名称</th>
				<td><input id="RegName" name="RegName" style="width: 180px;" />
				</td>
				<th>商户简称</th>
				<td><input id="CusName" name="CusName" style="width: 180px;" />
				</td>
			</tr>
			<tr>
				<th>商户英文名称</th>
				<td><input id="CusNameEn" name="CusNameEn"
					style="width: 180px;" /></td>
				<th>商户代码<font color="red">&nbsp;*</font>
				</th>
				<td><input name="CusCode" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>证件类型<font color="red">&nbsp;*</font>
				</th>
				<td><select id="DocType" name="DocType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">身份证</option>
						<option value="02">军官证</option>
						<option value="03">护照</option>
						<option value="04">户口簿</option>
						<option value="05">士兵证</option>
						<option value="06">港澳来往内地通行证</option>
						<option value="07">台湾同胞来往内地通行证</option>
						<option value="08">临时身份证</option>
						<option value="09">外国人居留证</option>
						<option value="10">警官证</option>
						<option value="99">其他</option>
				</select></td>
				<th>证件号码<font color="red">&nbsp;*</font>
				</th>
				<td><input name="DocCode" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>法定代表人姓名<font color="red">&nbsp;*</font>
				</th>
				<td><input id="DocName" name="DocName" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" /></td>
				<th>法定代表人证件类型<font color="red">&nbsp;*</th>
				<!-- <td><input id="LegDocType" name="LegDocType" style="width: 180px;" /> -->
				<td><select id="LegDocType" name="LegDocType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">营业执照编码</option>
						<option value="02">统一社会信息代码</option>
						<option value="03">组织机构代码证</option>
						<option value="04">经营许可证</option>
						<option value="05">税务登记证</option>
						<option value="99">其他</option>
						<option value="99">其他</option>
				</select></td>
			</tr>
			<tr>
				<th>法定代表人证件号码<font color="red">&nbsp;*</font>
				</th>
				<td><input id="LegDocCode" name="LegDocCode" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" /></td>
				
			<tr>
				<th>行业类别<font color="red">&nbsp;*</font>
				</th>
				<td><input id="InduType" name="InduType" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" /></td>
				<th>ICP备案编号</th>
				<td><input id="Icp" name="Icp" style="width: 180px;" />
			</tr>
			<tr>
				<th>收款开户行<font color="red">&nbsp;*</font>
				</th>
				<td><input name="OpenBank" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" />
				</td>
				<th>收款卡号<font color="red">&nbsp;*</font>
				</th>
				<td><input id="BankNo" name="BankNo" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>商户注册地址<font color="red">&nbsp;*</font>
				</th>
					<td><select class="easyui-combotree" id="RegAddrProv"
					name="RegAddrProv" multiple style="width:186px;"
					data-options="
    				lines:true,
    				cascadeCheck:false,
    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
   				">
				</select></td>
				<th>商户注册地址<font color="red">&nbsp;*</font>
				</th>
				<td><input id="RegAddrDetail" name="RegAddrDetail"
					class="easyui-validatebox" data-options="required:true"
					style="width: 180px;" />
				</td>
			</tr>
			<tr>
				<th>商户经营地址<font color="red">&nbsp;*</font>
				</th>
				<td><select class="easyui-combotree" id="AddrProv"
					name="AddrProv" multiple style="width:186px;"
					data-options="
    				lines:true,
    				cascadeCheck:false,
    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
   				">
				</select></td>
				<th>商户经营地址<font color="red">&nbsp;*</font>
				</th>
				<td><input id="AddrDetail" name="AddrDetail"
					class="easyui-validatebox" data-options="required:true"
					style="width: 180px;" />
				</td>
			</tr>
			<tr>
				<th>网址</th>
				<td><input id="Url" name="Url" style="width: 180px;" />
				<th>服务器IP</th>
				<td><input id="ServerIp" name="ServerIp" style="width: 180px;" />
			</tr>
			<tr>
				<th>商户联系人<font color="red">&nbsp;*</font>
				</th>
				<td><input id="ContName" name="ContName" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" />
				<th>商户联系电话<font color="red">&nbsp;*</font>
				</th>
				<td><input id="ContPhone" name="ContPhone"
					style="width: 180px;" class="easyui-validatebox"
					data-options="required:true" />
			</tr>
			<tr>
				<th>商户E-mail</th>
				<td><input id="CusEmail" name="CusEmail" style="width: 180px;" />
				<th>商户经营地区范围<font color="red">&nbsp;*</font>
				</th>
				<td><select class="easyui-combotree" id="Occurarea"
					name="Occurarea" multiple style="width:186px;"
					data-options="
    				lines:true,
    				cascadeCheck:false,
    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
   				">
				</select></td>
			</tr>
			<tr>
				<th>清算网络<font color="red">&nbsp;*</font>
				</th>
				<td><select id="NetworkType" name="NetworkType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">中国银联</option>
						<option value="02">网络支付清算平台</option>
						<option value="03">清算总中心</option>
						<option value="04">农信银</option>
						<option value="05">城商行</option>
						<option value="06">同城结算中心</option>
						<option value="99">其他</option>
				</select></td>
				<th>商户状态<font color="red">&nbsp;*</font>
				</th>
				<td><select id="Status" name="Status" class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">启用</option>
						<option value="02">关闭（暂停）</option>
						<option value="03">注销</option>
				</select></td>
			</tr>
			<tr>
				<th>事件发生时间</th>
				<td><input id="StartTime" name="StartTime"
					class="easyui-datebox" style="width: 83px;" /> <a>&nbsp;-&nbsp;&nbsp;</a><input
					id="EndTime" name="EndTime" class="easyui-datebox"
					style="width: 83px;" /></td>
				<th>股东信息</th>
				<td><input id="ShareHolder" name="ShareHolder" style="width: 180px;" />
					
			<tr>
			<tr>
				<th>合规风险状况<font color="red">&nbsp;*</font>
				</th>
				<td><select id="RishStatus" name="RishStatus"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">合规</option>
						<option value="02">风险</option>
				</select></td>
				<th>开通业务种类<font color="red">&nbsp;*</font>
				</th>
				<td><select id="OpenType" name="OpenType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">POS</option>
						<option value="02">条码</option>
				</select></td>
			</tr>
			<tr>
				<th>计费类型<font color="red">&nbsp;*</font>
				</th>
				<td><select id="ChageType" name="ChageType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">标准</option>
						<option value="02">优惠</option>
						<option value="03">减免</option>
				</select></td>
				<th>支持账户类型<font color="red">&nbsp;*</font>
				</th>
				<td><select id="AccountType" name="AccountType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">借记卡</option>
						<option value="02">贷记卡</option>
						<option value="03">支付账户</option>
				</select></td>
			</tr>
			<tr>
				<th>拓展方式<font color="red">&nbsp;*</font>
				</th>
				<td><select id="ExpandType" name="ExpandType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">自主拓展</option>
						<option value="02">外包服务机构推荐</option>
				</select></td>
				<th>外包服务机构名称<font color="red">&nbsp;*</font>
				</th>
				<td><input id="OutServiceName" name="OutServiceName"
					class="easyui-validatebox" data-options="required:true"
					style="width: 180px;" /></td>
			</tr>
			<tr>
				<th>外包服务机构法人证件类型<font color="red">&nbsp;*</font>
				</th>
				<td><select id="OutServiceCardType" name="OutServiceCardType"
					class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">营业执照编码</option>
						<option value="02">统一社会信息代码</option>
						<option value="03">组织机构代码证</option>
						<option value="04">经营许可证</option>
						<option value="05">税务登记证</option>
						<option value="99">其他</option>
				</select></td>
				<th>外包服务机构法人证件号码<font color="red">&nbsp;*</font>
				</th>
				<td><input id="OutServiceCardCode" name="OutServiceCardCode"
					class="easyui-validatebox" data-options="required:true"
					style="width: 180px;" />
				</td>
			</tr>
			<tr>
				<th>外包服务机构法定代表人证件类型<font color="red">&nbsp;*</font>
				</th>
				<td><select id="OutServiceLegCardType"
					name="OutServiceLegCardType" class="easyui-combobox"
					data-options="required:true,panelHeight:'auto',editable:false"
					style="width:186px;">
						<!-- data-options="required:true,panelHeight:'auto',editable:false" -->
						<option value=""></option>
						<option value="01">营业执照编码</option>
						<option value="02">统一社会信息代码</option>
						<option value="03">组织机构代码证</option>
						<option value="04">经营许可证</option>
						<option value="05">税务登记证</option>
						<option value="99">其他</option>
				</select></td>
				<th>外包服务机构法定代表人证件号码<font color="red">&nbsp;*</font>
				</th>
				<td><input id="OutServiceLegCardCode"
					name="OutServiceLegCardCode" class="easyui-validatebox"
					data-options="required:true" style="width: 180px;" />
				</td>
			</tr>
			<tr>
				<th>上报机构<font color="red">&nbsp;*</font>
				</th>
				<td><input name="OrgId" style="width: 180px;"
					class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>

			</tr>
		</table>
	</fieldset>
</form>
