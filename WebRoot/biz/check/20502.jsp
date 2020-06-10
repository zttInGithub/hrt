<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
//初始化datagrid
	//$(function() {
		$('#check_20502_datagrid').datagrid({
		//	url:'${ctx}/sysAdmin/paymentRisk_queryBeenMerchant',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			 pageSize : 9999,
			pageList : [ 10, 15 ], 
			sortName: 'profit',
			sortOrder: 'desc',
			columns : [[{
				title :'客户属性',
				field :'CUSPROPERTY',
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
				field :'RISKTYPE',
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
				title :'商户属性',//01实体商户 02 网络商户
				field :'CUSNATURE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "实体商户";
					 }else if(value=='02'){
					 	return "网络商户";
					 }
					}
			},{
				title :'商户名称',
				field :'CUSNAME',
				width : 100
			},{
				title :'营业注册名称',
				field :'REGNAME',
				width : 100
			},{
				title :'商户编码',
				field :'CUSCODE',
				width : 100
			},{
				title :'组织机构代码',
				field :'ORGCODE',
				width : 100
			},{
				title :'营业执照编码',
				field :'BUSLICCODE',
				width : 100
			},{
				title :'社会信用代码',
				field :'SOCIALUNITYCREDITCODE',
				width : 100
			},{
				title :'税务登记证',
				field :'TAXREGCER',
				width : 100
			},{
				title :'法定代表人',
				field :'LEGREPNAME',
				width : 100
			},{
				title :'法人证件号码',
				field :'LEGDOCCODE',
				width : 100
			},{
				title :'银行账号',
				field :'BANKNO',
				width : 100
			},{
				title :'开户行',
				field :'OPENBANK',
				width : 100
			},{
				title :'网址',
				field :'URL',
				width : 100
			},{
				title :'服务器IP',
				field :'SERVERIP',
				width : 100
			},{
				title :'手机',
				field :'MOBILENO',
				width : 100
			},{
				title :'地域',
				field :'ADDRESS',
				width : 100
			},{
				title :'ICP编号',
				field :'ICP',
				width : 100
			},{
				title :'信息级别',
				field :'INFOLEVEL',
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
				field :'OCCURTIMEB',
				width : 100
			},{
				title :'风险事件结束时间',
				field :'OCCURTIMEE',
				width : 100
			},{
				title :'风险事件发生渠道',
				field :'OCCURCHAN',
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
				field :'OCCURAREA',
				width : 100
			},{
				title :'备注',
				field :'NOTE',
				width : 100
			},{
				title :'有效期',
				field :'VALIDDATE',
				width : 100
			},{
				title :'有效性',
				field :'VALIDSTATUS',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "有效";
					 }else if(value=='02'){
					 	return "失效";
					 }
				}
			}]],
				toolbar:[{
					text:'上报',
					iconCls:'icon-add',
					handler:function(){
					up_check_20502();
					                  }
				}]	
		});
	
//});
	
	
	function check_20502_jiaoyan() {
			//不能全部为空：商户名称、商户营业执照注册名称、统一社会信用代码、组织机构代码、营业执照编码、法定代表人（负责人）身份证件号码、银行结算账号、网址、服务器IP、法定代表人手机号、ICP编号
			var CusName = $('#CusNameup').val();
			var RegName = $('#RegNameup').val();
			var SocialUnityCreditCode = $('#SocialUnityCreditCodeup').val();
			var OrgCode = $('#OrgCodeup').val();
			var BusLicCode = $('#BusLicCodeup').val();
			var LegDocCode = $('#LegDocCodeup').val();
			var BankNo = $('#BankNoup').val();
			var Url = $('#Urlup').val();
			var ServerIp = $('#ServerIpup').val();
			var MobileNo = $('#MobileNo2up').val();
			var Icp = $('#Icpup').val();
			if ( '' == CusName && RegName == '' && SocialUnityCreditCode == '' && OrgCode == ''&& Icp == ''
					&& BusLicCode == '' && LegDocCode == '' && BankNo == ''&& Url == ''&& ServerIp == ''&& MobileNo == '') {
				$.messager.alert('提示','商户名称、商户营业执照注册名称、统一社会信用代码、组织机构代码、营业执照编码、法定代表人身份证件号码、银行结算账号、网址、服务器IP、法定代表人手机号、ICP编号不能全部为空！ ');
				return false;
			} else {
				return true;
			}
		}
	//上报
	function up_check_20502() {
		$('<div id="check_20502_up"/>').dialog({
			title: '上报',
			width: 650,   
		    height: 450, 
		    closed: false,
		    href: '${ctx}/biz/check/20502up.jsp',  
		    modal: true,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
				  
				if(check_20502_jiaoyan()){
		    		var dia = $('#check_20502_up');
					$('#sysAdmin_check_20502').form('submit', {
						url:'${ctx}/sysAdmin/paymentRisk_reportMerchant', 
					    success:function(data){   
							var res = $.parseJSON(data);
							if (res.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if(res.success) {
									$('#check_20502_datagrid').datagrid('unselectAll');
									$('#check_20502_datagrid').datagrid('reload');
									dia.dialog('destroy');
									$.messager.show({
										title : '提示',
										msg : res.msg
									});
								} else {
									//dia.dialog('destroy');
									$.messager.alert('提示', res.msg);
								}  
							}
					    }   
					});
				  }	
				}
			},{
				text:'取消',
				handler:function(){
					$('#check_20502_up').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	
		//表单查询
	function check_20502_Query() {
	$('#check_20502_datagrid').datagrid({url:'${ctx}/sysAdmin/paymentRisk_queryBeenMerchant'}); 
		$('#check_20502_datagrid').datagrid('load', serializeObject($('#check_20502_searchForm'))); 
	}

	
	//清除表单内容
	function check_20502_Report_cleanFun() {
		$('#check_20502_searchForm input').val('');
	}
	
	//商户风险信息查询
	function check_20502_Report111zzz() {

		$.ajax({
			type:'post',
			url:'${ctx}/sysAdmin/paymentRisk_queryMerchant',
			//async : false,
			data:$('#check_20502_searchForm').serialize(),
			dataType : 'json',
			success : function(data) {
			if (data.success) {
				$.messager.alert('提示', data.msg);
			}else{
				var rows = data.rows;
					for ( var machine in rows) {
						//alert(rows[machine].MobileNo);
						var a = [ {
							'CusProperty' : rows[machine].CusProperty,
							'RiskType' : rows[machine].RiskType,
							'CusNature' : rows[machine].CusNature,
							'CusName' : rows[machine].CusName,
							'RegName' : rows[machine].RegName,
							
							'CusCode' : rows[machine].CusCode,
							'OrgCode' : rows[machine].OrgCode,
							'BusLicCode' : rows[machine].BusLicCode,
							'SocialUnityCreditCode' : rows[machine].SocialUnityCreditCode,
							'TaxRegCer' : rows[machine].TaxRegCer,
							
							'LegRepName' : rows[machine].LegRepName,
							'LegDocCode' : rows[machine].LegDocCode,
							'bankNo' : rows[machine].bankNo,
							'OpenBank' : rows[machine].OpenBank,
							'Url' : rows[machine].Url,
							
							'ServerIp' : rows[machine].ServerIp,
							'MobileNo' : rows[machine].MobileNo,
							'Address' : rows[machine].Address,
							'Icp' : rows[machine].Icp,
							'Level' : rows[machine].Level,
							
							'Occurtimeb' : rows[machine].Occurtimeb,
							'Occurtimee' : rows[machine].Occurtimee,
							'Occurchan' : rows[machine].Occurchan,
							'Occurarea' : rows[machine].Occurarea,
							'Note' : rows[machine].Note,
							
							'ValidDate' : rows[machine].ValidDate,
							'ValidStatus' : rows[machine].ValidStatus
	
						} ];
						$('#check_20502_datagrid').datagrid('loadData', a);
					}
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
	


</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:160px; overflow: hidden; padding-top:16px;">
		<form id="check_20502_searchForm" method="post">
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
					<td><input id="CusName" name="CusName" style="width: 120px;" /></td>				
					<th>营业注册名称</th>
					<td><input id="RegName" name="RegName" style="width: 120px;" /></td>
					
					<th>商户编码</th>
					<td><input name="CusCode" style="width: 120px;" /></td>
				</tr>
				<tr>
					<th>组织机构代码</th>
					<td><input id="OrgCode" name="OrgCode" style="width: 140px;" /></td>
					<th>营业执照编码</th>
					<td><input id="BusLicCode"   name="BusLicCode" style="width: 120px;" /></td>
					<th>社会信用代码</th>
					<td><input id="SocialUnityCreditCode" name="SocialUnityCreditCode" style="width: 120px;" /></td>
					<th>税务登记证</th>
					<td><input  name="TaxRegCer" style="width: 120px;"/></td>
					<th>法定代表人姓名</th>
					<td><input id="LegRepName" name="LegRepName" style="width: 120px;" /></td>
				</tr>
				<tr>
					<th>法人身份证件号码</th>
					<td><input id="LegDocCode" name="LegDocCode" style="width: 140px;" /></td>
					<th>银行结算账号</th>
					<td><input  id="BankNo" name="BankNo" style="width: 120px;" /></td>
					<th>开户行</th>
					<td><input id="OpenBank" name="OpenBank" style="width: 120px;"/></td>
					<th>网址</th>
					<td><input id="Url" name="Url" style="width: 120px;"/></td>
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
					<td><input id="Occurtimeb" name="Occurtimeb" class="easyui-datebox"style="width: 83px;"/>
					<a>&nbsp;-&nbsp;</a>
						<input id="Occurtimee" name="Occurtimee" class="easyui-datebox"style="width: 83px;"/>
					</td>
					<th>手机</th>
					<td><input id="MobileNo" name="MobileNo" style="width: 120px;" /></td>
					<th>地域</th>
					<td><input name="Address" style="width: 120px;" /></td>
					<th>ICP编号</th>
					<td><input id="Icp" name="Icp" style="width: 120px;" /></td>
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
						<select class="easyui-combotree" id="Occurarea" name="Occurarea" multiple  style="width:126px;" 
	    				data-options="
		    				lines:true,
		    				cascadeCheck:false,
		    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
	    				">
	    				</select>
    				</td>
					<th>服务器IP</th>
					<td><input id="ServerIp" name="ServerIp" style="width: 120px;" /></td>
					
					<th>有效期</th>
					<td><input id="ValidStatus" name="ValidDate" class="easyui-datebox"style="width: 126px;"/></td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20502_Query();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20502_Report_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20502_datagrid"></table>
    </div> 
</div>