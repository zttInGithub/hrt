<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	
		$('#check_205051_datagrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : false,//分页
			nowrap : true,
			striped : true,
			ctrlSelect : true,
			checkOnSelect : true,
			pageSize : 9999,
			//pageList : [ 10, 15 ], 
			sortName : 'profit',
			sortOrder : 'desc',
			columns : [ [ {
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
					}else if (value == '17') {
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
				field : 'Occurarea',
				width : 100
			} , {
				title : '风险事件描述',
				field : 'Note',
				width : 100
			}, {
				title : '有效性',
				field : 'ValidStatus',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '01') {
						return "有效";
					} else if (value == '02') {
						return "失效";
					}
				}
			}] ]
		});
	

	

	//$('#ValidDate').datebox('getValue');

	//个人风险信息查询
	function check_205051_Report() {
	 var KeyWord = $('#KeyWord51').datebox('getValue');
	 var Infos = $('#Infos51').val();
		if(KeyWord==''||Infos==''){
			$.messager.alert('提示','关键字，查询条件信息不可为空！');
			return ;
		}
		$.ajax({
			type:'post',
			url:'${ctx}/sysAdmin/paymentRisk_queryBatchImport',
			//async : false,
			data:$('#check_205051_searchForm').serialize(),
			dataType : 'json',
			success : function(data) {
			//alert(data.success);
			if (data.success) {
				$.messager.alert('提示', data.msg);
			}else{
				$("#check_205051_datagrid").datagrid("loadData", data.rows);  //动态取数据
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
	 
	 
		$.extend($.fn.validatebox.defaults.rules,{
		KeyWord:{
			validator : function(value) { 
			
	            return /^\s+|\s+$/g.test(value); 
	        }, 
	        message : '该选项不可为空！' 
		}
	}); 
	
		//清除表单内容
	function check_205051_cleanFun() {
		$('#check_205051_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:16px;">
		<form id="check_205051_searchForm" method="post">
		<input type="hidden" name="CusProperty" value="01" /> 
			<table class="tableForm" >
				<tr>
					<th>关键字<font color="red" >&nbsp;*</font></th>
					<td>
						<select class="easyui-combobox"  data-options="required:true" id="KeyWord51" name="KeyWord" style="width:120px;">
							<option value=""></option> 
							<option value="01">手机</option> 
							<option value="02">Imei</option>
							<option value="03">MAC</option> 
							<option value="04">银行卡号</option>
							<option value="05">证件号码</option> 
							<option value="06">收款银行卡号</option>
							<option value="07">邮箱</option> 
							<option value="08">IP</option>
						 </select>
					</td>
					<th>查询条件信息<font color="red">&nbsp;*</font></th>
					<td><input id="Infos51" name="Infos"class="easyui-validatebox" data-options="required:true" style="width: 250px;" /></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_205051_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_205051_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_205051_datagrid"></table>
    </div> 
</div>