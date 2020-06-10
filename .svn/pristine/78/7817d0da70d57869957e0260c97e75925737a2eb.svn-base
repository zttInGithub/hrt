<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

		$('#check_205052_datagrid').datagrid({
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
	function check_205052_Report() {
	  var KeyWord = $('#KeyWord52').datebox('getValue');
	  var Infos = $('#Infos52').val();
		if(KeyWord==''||Infos==''){
			$.messager.alert('提示','关键字，查询条件信息不可为空！');
			return ;
		}
		$.ajax({
			type:'post',
			url:'${ctx}/sysAdmin/paymentRisk_queryBatchImport',
			//async : false,
			data:$('#check_205052_searchForm').serialize(),
			dataType : 'json',
			success : function(data) {
			if (data.success) {
				$.messager.alert('提示', data.msg);
			}else{
				 $("#check_205052_datagrid").datagrid("loadData", data.rows);  //动态取数据
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
	
			//清除表单内容
	function check_205052_cleanFun() {
		$('#check_205052_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:16px;">
		<form id="check_205052_searchForm" method="post">
		<input type="hidden" name="CusProperty" value="02" /> 
			<table class="tableForm" >
				<tr>
					<th>关键字<font color="red">&nbsp;*</font></th>
					<td>
						<select id="KeyWord52" name="KeyWord" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:150px;"> <!--  data-options="required:true" -->
							<option value=""></option> 
							<option value="01">商户名称</option> 
							<option value="02">组织机构代码</option>
							<option value="03">营业执照编码</option> 
							<option value="04">服务器IP</option>
							<option value="05">手机</option> 
							<option value="06">客户营业执照注册名称</option>
							<option value="07">法人身份证号码</option> 
							<option value="08">银行账号</option>
							<option value="09">网址</option>
							<option value="10">ICP编号</option>
						 </select>
					</td>
					<th>查询条件信息<font color="red">&nbsp;*</font></th>
					<td><input id="Infos52" name="Infos"class="easyui-validatebox" data-options="required:true" style="width: 250px;" /></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_205052_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_205052_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_205052_datagrid"></table>
    </div> 
</div>