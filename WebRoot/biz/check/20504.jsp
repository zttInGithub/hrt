<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

		$('#check_20504_datagrid').datagrid({
			//url:'${ctx}/sysAdmin/paymentRisk_queryMerchant',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : false,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			 pageSize : 9999,
			//pageList : [ 10, 15 ], 
			sortName: 'profit',
			sortOrder: 'desc',
			columns : [[{
				title :'客户属性',
				field :'CusProperty',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "个人";
					} else if (value == '02') {
						return "商户";
					}
				}
			},{
				title :'风险类型',
				field :'RiskType',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "虚假申请";
					 }else if(value=='02'){
					 	return "套现、套积分";
					 }else if(value=='03'){
					 	return "违法经营";
					 }else if(value=='04'){
					 	return "销赃";
					 }else if(value=='05'){
					 	return "买卖或租借银行账户";
					 }else if(value=='06'){
					 	return "侧录点";
					 }else if(value=='07'){
					 	return "伪卡集中使用点";
					 }else if(value=='08'){
					 	return "泄露账户及交易信息";
					 }else if(value=='09'){
					 	return "恶意倒闭";
					 }else if(value=='10'){
					 	return "恶意分单";
					 }else if(value=='11'){
					 	return "移机";
					 }else if(value=='12'){
					 	return "高风险商户";
					 }else if(value=='13'){
					 	return "商户合谋欺诈";
					 }else if(value=='14'){
					 	return "破产或停业商户";
					 }else if(value=='15'){
					 	return "强迫签单";
					 }else if(value=='17'){
					 	return "频繁变更服务机构";
					 }else if(value=='18'){
					 	return "关联商户涉险";
					 }else if(value=='19'){
					 	return "买卖银行卡信息";
					 }else if(value=='99'){
					 	return "其他";
					 }
				}
			},{
				title :'商户属性',
				field :'CusNature',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "个人";
					 }else if(value=='02'){
					 	return "商户";
					 }
					}
			},{
				title :'商户名称',
				field :'CusName',
				width : 100
			},{
				title :'营业注册名称',
				field :'RegName',
				width : 100
			},{
				title :'商户编码',
				field :'CusCode',
				width : 100
			},{
				title :'组织机构代码',
				field :'OrgCode',
				width : 100
			},{
				title :'营业执照编码',
				field :'BusLicCode',
				width : 100
			},{
				title :'社会信用代码',
				field :'SocialUnityCreditCode',
				width : 100
			},{
				title :'税务登记证',
				field :'TaxRegCer',
				width : 100
			},{
				title :'法定代表人',
				field :'LegRepName',
				width : 100
			},{
				title :'法人证件号码',
				field :'LegDocCode',
				width : 100
			},{
				title :'银行账号',
				field :'BankNo',
				width : 100
			},{
				title :'开户行',
				field :'OpenBank',
				width : 100
			},{
				title :'网址',
				field :'Url',
				width : 100
			},{
				title :'服务器IP',
				field :'ServerIp',
				width : 100
			},{
				title :'手机',
				field :'MobileNo',
				width : 100
			},{
				title :'地域',
				field :'Address',
				width : 100
			},{
				title :'ICP编号',
				field :'Icp',
				width : 100
			},{
				title :'信息级别',
				field :'Level',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "一级";
					 }else if(value=='02'){
					 	return "二级";
					 }else if(value=='03'){
					 	return "三级";
					 }
				}
			},{
				title :'风险事件发生时间',
				field :'Occurtimeb',
				width : 100
			},{
				title :'风险事件结束时间',
				field :'Occurtimee',
				width : 100
			},{
				title :'风险事件发生渠道',
				field :'Occurchan',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "线上";
					 }else if(value=='02'){
					 	return "线下";
					 }
				}
			},{
				title :'风险事件发生地域',
				field :'Occurarea',
				width : 100
			},{
				title :'备注',
				field :'Note',
				width : 100
			},{
				title :'有效期',
				field :'ValidDate',
				width : 100
			},{
				title :'有效性',
				field :'ValidStatus',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "有效";
					 }else if(value=='02'){
					 	return "失效";
					 }
				}
			}]]
		});
	

	//商户风险信息查询
	function check_20504_Query() {

		$.ajax({
			type:'post',
			url:'${ctx}/sysAdmin/paymentRisk_queryMerchant',
			//async : false,
			data:$('#check_20504_searchForm').serialize(),
			dataType : 'json',
			success : function(data) {
			if (data.success) {
				$.messager.alert('提示', data.msg);
			}else{
				 $("#check_20504_datagrid").datagrid("loadData", data.rows);  //动态取数据
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
	
	
	//清除表单内容
	function check_20504_cleanFun() {
		$('#check_20504_searchForm input').val('');
		$('#Scope2').combobox('setValue','01');
		
	}
	
	
	function check_20504_Report() {
			//不能全部为空：商户名称、商户营业执照注册名称、统一社会信用代码、组织机构代码、营业执照编码、法定代表人（负责人）身份证件号码、银行结算账号、网址、服务器IP、法定代表人手机号、ICP编号
			var CusName = $('#CusName4').val();
			var RegName = $('#RegName4').val();
			var SocialUnityCreditCode = $('#SocialUnityCreditCode4').val();
			var OrgCode = $('#OrgCode4').val();
			var BusLicCode = $('#BusLicCode4').val();
			var LegDocCode = $('#LegDocCode4').val();
			var BankNo = $('#BankNo4').val();
			var Url = $('#Url4').val();
			var ServerIp = $('#ServerIp4').val();
			var MobileNo = $('#MobileNo4').val();
			var Icp = $('#Icp4').val();
			if ( '' == CusName && RegName == '' && SocialUnityCreditCode == '' && OrgCode == ''&& Icp == ''
					&& BusLicCode == '' && LegDocCode == '' && BankNo == ''&& Url == ''&& ServerIp == ''&& MobileNo == '') {
				$.messager.alert('提示','商户名称、商户营业执照注册名称、统一社会信用代码、组织机构代码、营业执照编码、法定代表人身份证件号码、银行结算账号、网址、服务器IP、法定代表人手机号、ICP编号不能全部为空！ ');
			} else {
			check_20504_Query();
			}
		}
	 

</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:160px; overflow: hidden; padding-top:16px;">
		<form id="check_20504_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>风险类型</th>
					<td>
					 <select class="easyui-combobox" name="RiskType" data-options="panelHeight:'auto',editable:false" style="width:146px;">
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
						<select class="easyui-combobox" name="CusNature" data-options="panelHeight:'auto',editable:false" style="width:126px;">
							<option value=""></option> 
							<option value="01">实体商户</option> 
							<option value="02">网络商户</option>
						 </select>	
					</td>				
					
					<th>商户名称</th>
					<td><input id="CusName4" name="CusName" style="width: 120px;" /></td>				
					<th>营业注册名称</th>
					<td><input id="RegName4" name="RegName" style="width: 120px;" /></td>
					
					<th>商户编码</th>
					<td><input name="CusCode" style="width: 120px;" /></td>
				</tr>
				<tr>
					<th>组织机构代码</th>
					<td><input id="OrgCode4" name="OrgCode" style="width: 140px;" /></td>
					<th>营业执照编码</th>
					<td><input id="BusLicCode4"   name="BusLicCode" style="width: 120px;" /></td>
					<th>社会信用代码</th>
					<td><input id="SocialUnityCreditCode4" name="SocialUnityCreditCode" style="width: 120px;" /></td>
					<th>税务登记证</th>
					<td><input  name="TaxRegCer" style="width: 120px;"/></td>
					<th>法定代表人姓名</th>
					<td><input id="LegRepName4" name="LegRepName" style="width: 120px;" /></td>
				</tr>
				<tr>
					<th>法人身份证件号码</th>
					<td><input id="LegDocCode4" name="LegDocCode" style="width: 140px;" /></td>
					<th>银行结算账号</th>
					<td><input  id="BankNo4" name="BankNo" style="width: 120px;" /></td>
					<th>开户行</th>
					<td><input id="OpenBank4" name="OpenBank" style="width: 120px;"/></td>
					<th>网址</th>
					<td><input id="Url4" name="Url" style="width: 120px;"/></td>
					<!-- <th>风险事件结束时间</th> 
					<td><input id="Occurtimee" name="Occurtimeb" class="easyui-datebox"style="width: 120px;"/></td>-->
					<th>风险信息等级</th>
					<td> <select class="easyui-combobox" name="Level" data-options="panelHeight:'auto',editable:false" style="width:126px;">
							<option value=""></option> 
							<option value="01">一级</option> 
							<option value="02">二级</option>
							<option value="03">三级</option>
					   </select>
					  </td>
				</tr>
				<tr>
					<th>风险事件时间</th>
					<td><input id="Occurtimeb4" name="Occurtimeb" class="easyui-datebox"style="width: 83px;"/>
					<a>&nbsp;-&nbsp;</a>
						<input id="Occurtimee4" name="Occurtimee" class="easyui-datebox"style="width: 83px;"/>
					</td>
					<th>手机</th>
					<td><input id="MobileNo4" name="MobileNo" style="width: 120px;" /></td>
					<th>地域</th>
					<td><input name="Address" style="width: 120px;" /></td>
					<th>ICP编号</th>
					<td><input id="Icp4" name="Icp" style="width: 120px;" /></td>
					<th>风险事件发生渠道</th>
					<td>
						<select class="easyui-combobox" name="Occurchan" data-options="panelHeight:'auto',editable:false" style="width:126px;">
							<option value=""></option> 
							<option value="01">线上</option> 
							<option value="02">线下</option>
						 </select>
					</td>
					
				</tr>
				
				<tr>
					<th>风险事件发生地域</th>
					<td>
						<select class="easyui-combotree" id="Occurarea" name="Occurarea" multiple  style="width:146px;" 
	    				data-options="
		    				lines:true,
		    				cascadeCheck:false,
		    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
	    				">
	    				</select>
    				</td>
					<th>服务器IP</th>
					<td><input id="ServerIp4" name="ServerIp" style="width: 120px;" /></td>
					<th>查询范围</th>
					<td><select class="easyui-combobox" name="Scope" id="Scope2"
						data-options="panelHeight:'auto',editable:false"
						style="width:126px;">
							<option value="01">全部</option>
							<option value="02">本单位</option>
					</select></td>
					<th>有效期</th>
					<td><input id="ValidStatus4" name="ValidDate" class="easyui-datebox"style="width: 126px;"/></td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20504_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20504_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20504_datagrid"></table>
    </div> 
</div>