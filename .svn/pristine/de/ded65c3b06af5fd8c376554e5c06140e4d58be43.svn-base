<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	
		$('#check_20507_datagrid').datagrid({
			//url:'${ctx}/sysAdmin/paymentRisk_queryPersonalChange',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : false,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			singleSelect:true,//单选
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
				title : '客户属性',
				field : 'CusProperty',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "个人";
					} else if (value == '02') {
						return "商户";
					}
				}
			}, {
				title : '风险类型',
				field : 'RiskType',
				width : 100,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "虚假申请（受害人信息）";
					} else if (value == '02') {
						return "虚假申请（嫌疑人信息）";
					} else if (value == '03') {
						return "伪卡（受害人信息）";
					} else if (value == '04') {
						return "失窃卡（受害人信息）";
					} else if (value == '05') {
						return "未达卡（受害人信息）";
					} else if (value == '06') {
						return "银行卡转移（受害人信息）";
					} else if (value == '07') {
						return "盗用银行卡（嫌疑人信息）";
					} else if (value == '08') {
						return "银行卡网络欺诈（受害人信息）";
					} else if (value == '09') {
						return "银行卡网络欺诈（嫌疑人信息）";
					} else if (value == '10') {
						return "虚拟账户被盗（受害人信息）";
					} else if (value == '11') {
						return "盗用虚拟账户（嫌疑人信息）";
					} else if (value == '12') {
						return "套现、套积分（嫌疑人信息）";
					} else if (value == '13') {
						return "协助转移赃款";
					} else if (value == '14') {
						return "买卖或租借银行（支付）账户";
					} else if (value == '15') {
						return "专案风险信息";
					} else if (value == '17') {
						return "买卖银行卡信息";
					}else if (value == '99') {
						return "其它";
					}
				}
			}, {
				title : '手机号',
				field : 'MobileNo',
				width : 100
			}, {
				title : 'MAC地址',
				field : 'Mac',
				width : 100
			}, {
				title : 'Imei',
				field : 'Imei',
				width : 100
			}, {
				title : '银行帐/卡号',
				field : 'BankNo',
				width : 100
			}, {
				title : '开户行',
				field : 'OpenBank',
				width : 100
			}, {
				title : '客户姓名',
				field : 'CusName',
				width : 100
			}, {
				title : '身份证件号码',
				field : 'DocCode',
				width : 100
			}, {
				title : 'IP地址',
				field : 'Ip',
				width : 100
			}, {
				title : '收货地址',
				field : 'Address',
				width : 100
			}, {
				title : '固定电话',
				field : 'Telephone',
				width : 100
			}, {
				title : '收款银行卡号',
				field : 'RecBankNo',
				width : 100
			}, {
				title : '收款卡号开户行',
				field : 'RecOpenBank',
				width : 100
			}, {
				title : '邮箱',
				field : 'Email',
				width : 100
			}, {
				title : '有效期',
				field : 'ValidDate',
				width : 100
			}, {
				title : '风险信息等级',
				field : 'Level',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "一级";
					} else if (value == '02') {
						return "二级";
					} else if (value == '03') {
						return "三级";
					}
				}
			}, {
				title : '风险事件发生时间',
				field : 'Occurtimeb',
				width : 100
			}, {
				title : '风险事件结束时间',
				field : 'Occurtimee',
				width : 100
			}, {
				title : '风险事件发生渠道',
				field : 'Occurchan',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "线上";
					} else if (value == '02') {
						return "线下";
					}
				}
			}, {
				title : '风险事件发生地域',
				field : 'OccurareaTxt',
				width : 100
			} ,{
				title :'备注',
				field :'Note',
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
				title :'上传方式',
				field :'RepType',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "批量导入";
					} else if (value == '02') {
						return "人工录入";
					}else if (value == '03') {
						return "接口导入";
					}
				}
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
						check_20507_Edit();
					}
				}]
		});
	
	//修改卡表窗口
	function check_20507_Edit() {
	
	  var rows = $('#check_20507_datagrid').datagrid('getSelections');
      if(rows.length != 1){
        $.messager.alert('提示','请选择一条数据!','error');
       return false;
      }
      var row = rows[0];	
		$('<div id="check_20507_edit"/>').dialog({
			title: '修改个人风险信息',
			width: 650,   
		    height: 400, 
		    closed: false,
		    href: '${ctx}/biz/check/20507Edit.jsp',  
		    modal: true,
		    onLoad: function() {
		   	    //var rowss = $('#check_20507_datagrid').datagrid('getRows');
				//var row = rowss[index];
		    	row.Occurarea = stringToList(row.Occurarea); 
		    	//row.roleIds = stringToList(row.roleIds);    	
		    	$('#check_20507_editForm').form('load', row);
			},
			 buttons:[{ 
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var dia = $('#check_20507_edit');
						$('#check_20507_editForm').form('submit', {
							url:'${ctx}/sysAdmin/paymentRisk_updatePersonalChange',
						    success:function(data){   
								var res = $.parseJSON(data);
								if (res.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(res.success) {
										dia.dialog('destroy');
										$('#check_20507_datagrid').datagrid('unselectAll');
										//$('#check_20507_datagrid').datagrid('reload');
										$.messager.show({
										title : '提示',
										msg : res.msg
									});
									} else {
										$('#check_20507_datagrid').datagrid('unselectAll');
										$.messager.alert('提示', res.msg); 
										
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					handler:function(){
						$('#check_20507_edit').dialog('destroy');
						//$('#check_20507_datagrid').datagrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					//$('#check_20507_datagrid').datagrid('unselectAll');
				} 
		});
	}
	 
	//表单jiaoyan
	function check_20507_jiaoyan() {
			var RiskType = $('#RiskType7').datebox('getValue');
			//var OrigSender = $('#OrigSender7').val();
			var MobileNo = $('#MobileNo7').val();
			var BankNo = $('#BankNo7').val();
			var Mac = $('#Mac7').val();
			var Imei = $('#Imei7').val();
			//alert(RiskType);alert(MobileNo);alert(BankNo);alert(Mac);alert(Imei);
			if ( '' == RiskType  && MobileNo == ''
					&& BankNo == '' && Mac == '' && Imei == '') {
				$.messager.alert('提示','风险类型、手机号、MAC、Imei、银行卡号不能全部为空！ ');
			} else {
				return true;
			}
		}
	
	//个人风险信息变更查询
	function check_20507_Report() {
	$('#check_20507_datagrid').datagrid('unselectAll');
		if(check_20507_jiaoyan()){
			$.ajax({
				type:'post',
				url:'${ctx}/sysAdmin/paymentRisk_queryPersonalChange',
				//async : false,
				data:$('#check_20507_searchForm').serialize(),
				dataType : 'json',
				success : function(data) {
				if (data.success) {
					$.messager.alert('提示', data.msg);
				}else{
				 $("#check_20507_datagrid").datagrid("loadData", data.rows);  //动态取数据
					}
				},
				error : function() {
					alert('error');
				}
			});
		}
	}
				//清除表单内容
	function check_20507_cleanFun() {
		$('#check_20507_searchForm input').val('');
	} 
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:16px;">
		<form id="check_20507_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>风险类型</th>
					<td>
					 <select class="easyui-combobox" id="RiskType7" name="RiskType" data-options="panelHeight:'auto',editable:false" style="width:180px;">
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
					<th>Imei</th>
					<td><input id="Imei7"  name="Imei" style="width: 150px;"/></td>			
					<th>手机号</th>
					<td><input id="MobileNo7" name="MobileNo" style="width: 130px;" /></td>
				</tr>
				<tr>
					<th>银行卡账号</th>
					<td><input id="BankNo7" name="BankNo" style="width: 174px;" /></td>
					<th>MAC地址</th>
					<td><input id="Mac7"  name="Mac" style="width: 150px;"/></td>
					<td></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20507_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20507_cleanFun();">清空</a>
					</td>
				</tr>
			</table>  
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20507_datagrid"></table>
    </div> 
</div>