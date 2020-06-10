<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	
		$('#check_20508_datagrid').datagrid({
			//url:'${ctx}/sysAdmin/paymentRisk_queryMerchantChange',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : false,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			singleSelect:true,
			checkOnSelect:true,
			 pageSize : 9999,
			//pageList : [ 10, 15 ], 
			idField : 'Id',
			sortName : 'Id',
			sortOrder: 'desc',
			columns : [[{
				title :'id',
				field :'Id',
				width : 100,
				checkbox: true
			},{
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
				field :'OccurareaTxt',
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
				title :'上报机构',
				field :'OrgId',
				width : 100
			},{
				title :'上报日期',
				field :'RepDate',
				width : 100
			},{
				title :'上传人',
				field :'RepPerson',
				width : 100
			}]],
			toolbar:[{
					text:'图标说明：'
				},{
					id:'btn_edit',
					text:'修改',
					iconCls:'icon-edit',
					handler:function(){
						check_20508_Edit();
					}
				}]
		});

	//表单jiaoyan
	function check_20508_jiaoyan() {
			var RiskType = $('#RiskType8').datebox('getValue');
			//var OrigSender = $('#OrigSender7').val();
			var CusName = $('#CusName8').val();
			var OrgCode = $('#OrgCode8').val();
			var BusLicCode = $('#BusLicCode8').val();
			//alert(RiskType);alert(MobileNo);alert(BankNo);alert(Mac);alert(Imei);
			if ( '' == RiskType && BusLicCode == '' && CusName == '' && OrgCode == '') {
				$.messager.alert('提示','风险类型、客户名称、组织机构代码、营业执照编码 不能全部为空！ ');
			} else {
				return true;
			}
		}
	
	//商户风险信息变更查询
	function check_20508_Report() {
		$('#check_20508_datagrid').datagrid('unselectAll');
		if(check_20508_jiaoyan()){
			$.ajax({
				type:'post',
				url:'${ctx}/sysAdmin/paymentRisk_queryMerchantChange',
				//async : false,
				data:$('#check_20508_searchForm').serialize(),
				dataType : 'json',
				success : function(data) {
				if (data.success) {
					$.messager.alert('提示', data.msg);
				}else{
					 $("#check_20508_datagrid").datagrid("loadData", data.rows);  //动态取数据
					}
				},
				error : function() {
					alert('error');
				}
			});
		}
	}

//修改卡表窗口
	function check_20508_Edit() {
	  var rows = $('#check_20508_datagrid').datagrid('getSelections');
      if(rows.length != 1){
        $.messager.alert('提示','请选择一条数据!','error');
       return false;
      }
      var row = rows[0];	
		$('<div id="check_20508_edit"/>').dialog({
			title: '修改个人风险信息',
			width: 650,   
		    height: 450, 
		    closed: false,
		    href: '${ctx}/biz/check/20508Edit.jsp',  
		    modal: true,
		    onLoad: function() {
		    	row.Occurarea = stringToList(row.Occurarea);   	
		    	$('#check_20508_editForm').form('load', row);
			},
			 buttons:[{ 
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var dia = $('#check_20508_edit');
						$('#check_20508_editForm').form('submit', {
							url:'${ctx}/sysAdmin/paymentRisk_updateMerchantChange',
						    success:function(data){   
								var res = $.parseJSON(data);
								if (res.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(res.success) {
										dia.dialog('destroy');
										$('#check_20508_datagrid').datagrid('unselectAll');
										//$('#check_20508_datagrid').datagrid('reload');
										//$.messager.alert("提示", res.msg,"info"); 
										$.messager.show({
										title : '提示',
										msg : res.msg
									}); 
									} else {
										$('#check_20508_datagrid').datagrid('unselectAll');
										$.messager.alert('提示', res.msg); 
										
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					handler:function(){
						$('#check_20508_edit').dialog('destroy');
						//$('#check_20508_datagrid').datagrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					//$('#check_20508_datagrid').datagrid('unselectAll');
				} 
		});
	}
	 				//清除表单内容
	function check_20508_cleanFun() {
		$('#check_20508_searchForm input').val('');
	} 
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:16px;">
		<form id="check_20508_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>风险类型</th>
					<td>
					 <select class="easyui-combobox" id="RiskType8" name="RiskType" data-options="panelHeight:'auto',editable:false" style="width:130px;">
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
					<th>商户名称</th>
					<td><input id="CusName8" name="CusName" style="width: 120px;" /></td>				
					<th>组织机构代码</th>
					<td><input id="OrgCode8" name="OrgCode" style="width: 130px;" /></td>
					<th>营业执照编码</th>
					<td><input id="BusLicCode8" name="BusLicCode" style="width: 120px;" /></td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20508_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20508_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20508_datagrid"></table>
    </div> 
</div>