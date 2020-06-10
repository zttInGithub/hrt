<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	//初始化datagrid
	
		$('#check_20503_datagrid').datagrid({
			//url : '${ctx}/sysAdmin/paymentRisk_queryPersonal',
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
	

	
	function check_20503_Report() {
			var MobileNo = $('#MobileNo3').val();
			var Mac = $('#macqq3').val();
			var Imei = $('#Imei3').val();
			var BankNo = $('#bankNo3').val();
			var DocCode = $('#DocCode3').val();
			var IpAddress = $('#IpAddress').val();
			var RecBankNumber = $('#RecBankNumber').val();
			if (  MobileNo == ''&& BankNo == '' && Mac == '' && Imei == ''&& DocCode == '' && IpAddress == '' && RecBankNumber == '') {
				$.messager.alert('提示','手机号、MAC、Imei、银行卡号、身份证号码、IP地址、收款银行卡号不能全部为空！ ');
			} else {
				check_20503_Query();
			}
		}

	//个人风险信息查询
	function check_20503_Query() {
		$.ajax({
			type:'post',
			url : '${ctx}/sysAdmin/paymentRisk_queryPersonal',
			//async : false,
			data:$('#check_20503_searchForm').serialize(),
			dataType : 'json',
			success : function(data) {
			if (data.success) {
			
				$.messager.alert('提示', data.msg);
			}else{
			 //var msg = $.parseJSON(data);
			  $("#check_20503_datagrid").datagrid("loadData", data.rows);  //动态取数据
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
	//清除表单内容
	function check_20503_cleanFun() {
		$('#check_20503_searchForm input').val('');
		$('#Scope').combobox('setValue','01');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false"
		style="height:140px; overflow: hidden; padding-top:16px;">
		<form id="check_20503_searchForm" method="post">
			<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm">
				<tr>
					<th>风险类型</th>
					<td><select class="easyui-combobox" name="RiskType"
						id="RiskType" data-options="panelHeight:'auto',editable:false"
						style="width:180px;">
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
					</select></td>
					<th>客户姓名</th>
					<td><input id="CusName" name="CusName" style="width: 120px;" />
					</td>
					<th>手机号</th>
					<td><input id="MobileNo3" name="MobileNo" style="width: 130px;" />
					</td>
					<th>身份证</th>
					<td><input id="DocCode3" name="DocCode" style="width: 130px;" />
					</td>
					<th>事件发生地域</th>
					<!-- 多选 -->
					<td>
						<select class="easyui-combotree" id="Occurarea" name="Occurarea" multiple  style="width:136px;" 
	    				data-options="
		    				lines:true,
		    				cascadeCheck:false,
		    				url:'${ctx}/sysAdmin/paymentRisk_queryDictionary.action'
	    				">
	    				</select>
    				</td>
				</tr>
				<tr>
					<th>银行卡账号</th>
					<td><input id="bankNo3" name="BankNo" style="width: 174px;" />
					</td>
					<th>开户行</th>
					<td><input id="OpenBank" name="OpenBank" style="width: 120px;" />
					</td>
					<th>收货地址</th>
					<td><input id="Address" name="Address" style="width: 130px;" />
					</td>
					<th>固话</th>
					<td><input id="Telephone" name="Telephone"
						style="width: 130px;" />
					</td>
					<th>IP地址</th>
					<td><input id="IpAddress" name="Ip" style="width: 130px;" />
					</td>
				</tr>
				<tr>
					<th>MAC地址</th>
					<td><input id="macqq3" name="Mac" style="width: 174px;" />
					</td>
					<th>Imei</th>
					<td><input id="Imei3" name="Imei" style="width: 120px;" />
					</td>
					<th>收款银卡号</th>
					<td><input id="RecBankNumber" name="RecBankNo" style="width: 130px;" />
					</td>
					<th>收款卡号开户行</th>
					<td><input id="RecOpenBank" name="RecOpenBank"
						style="width: 130px;" />
					</td>
					<th>发生渠道</th>
					<td><select class="easyui-combobox" name="Occurchan"
						id="Occurchan" data-options="panelHeight:'auto',editable:false"
						style="width:136px;">
							<option value=""></option>
							<option value="01">线上</option>
							<option value="02">线下</option>
					</select></td>
				</tr>
				<tr>
					<th>事件发生时间</th>
					<td><input id="Occurtimeb" name="Occurtimeb" class="easyui-datebox" style="width: 83px;" /> <a>&nbsp;-&nbsp;</a>
						<input id="Occurtimee" name="Occurtimee" class="easyui-datebox" style="width: 83px;" />
					</td>
					<th>Email</th>
					<td><input name="Email" style="width: 120px;" />
					</td>
					<!-- <th>风险事件结束时间</th> 
					<td><input id="Occurtimee" name="Occurtimeb" class="easyui-datebox"style="width: 120px;"/></td>-->
					
					<th>查询范围</th>
					<td><select class="easyui-combobox" name="Scope" id="Scope"
						data-options="panelHeight:'auto',editable:false"
						style="width:136px;">
							<option value="01">全部</option>
							<option value="02">本单位</option>
					</select></td>
					<td></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="check_20503_Report();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20503_cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="check_20503_datagrid"></table>
	</div>
</div>
