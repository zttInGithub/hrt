<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	//导出EXCEL
	function sysAdmin_20580_export() {
		var txnDay = $('#txnDay_20580').datebox('getValue');
		var txnDay1 = $('#txnDay1_20580').datebox('getValue');
		var start=new Date(txnDay.replace("-", "/").replace("-", "/"));
		var end=new Date(txnDay1.replace("-", "/").replace("-", "/"));
		var useLvl = "${sessionScope.user.useLvl}";
		// console.log(useLvl);
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 6) == txnDay1.substring(0, 6) && (useLvl==3 || ((end-start)/(24*60*60*1000))<7)) {
				$("#sysAdmin_20580_searchForm")
						.form(
								'submit',
								{
									url : '${ctx}/sysAdmin/checkUnitDealDetail_exportUnitDealDetail.action'
								});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月,且开始时间与结束时间不能大于7天');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	
	$('#saleName_20580').combogrid({
		url : '${ctx}/sysAdmin/agentsales_searchAgentSales3.action',
		idField : 'SALENAME',
		textField : 'SALENAME',
		mode : 'remote',
		fitColumns : true,
		columns : [ [ {
			field : 'SALENAME',
			title : '销售姓名',
			width : 150
		}, {
			field : 'UNNO',
			title : '所属机构',
			width : 150,
			hidden : true
		}, {
			field : 'BUSID',
			title : 'id',
			width : 150,
			hidden : true
		} ] ]
	});

	$(function() {
		$('#unno_20580').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodesForSales.action',
			idField : 'UNNO',
			textField : 'UNNO',
			mode : 'remote',
			fitColumns : true,
			collapsible : false,
			fit : false,
			columns : [ [ {
				field : 'UNNO',
				title : '机构号',
				width : 100
			}, {
				field : 'UN_NAME',
				title : '机构名称',
				width : 100
			} ] ]
		});
	});

	//查询提交
	function sysAdmin_20580_searchFun() {
		var txnDay = $('#txnDay_20580').datebox('getValue');
		var txnDay1 = $('#txnDay1_20580').datebox('getValue');
		var start=new Date(txnDay.replace("-", "/").replace("-", "/"));
		var end=new Date(txnDay1.replace("-", "/").replace("-", "/"));
		txnDay = txnDay.replace(/\-/gi, "");
		txnDay1 = txnDay1.replace(/\-/gi, "");
		var useLvl = "${sessionScope.user.useLvl}";
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 6) == txnDay1.substring(0, 6)  && (useLvl==3 ||((end-start)/(24*60*60*1000))<7)) {
				$('#sysAdmin_20580_datagrid').datagrid('load',
						serializeObject($('#sysAdmin_20580_searchForm')));
				sysAdmin_20580_listUnitDealDetailTotal();
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月,且开始时间与结束时间不能大于7天');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	//清除表单内容
	function sysAdmin_20580_cleanFun() {
		$('#sysAdmin_20580_searchForm input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20580_datagrid')
				.datagrid(
						{
							url : '${ctx}/sysAdmin/checkUnitDealDetail_listUnitDealDetail.action',
							fit : true,
							fitColumns : true,
							border : false,
							pagination : true,
							nowrap : true,
							pageSize : 10,
							toolbar : '#sysAdmin_20580_toolbar',
							pageList : [ 10, 15 ],
							idField : 'BDID',
							columns : [ [ {
								title : 'ID',
								field : 'BDID',
								width : 100,
								hidden : true
							}, {
								title : '机构号',
								field : 'UNNO',
								width : 100
							}, {
								title : '机构名称',
								field : 'UN_NAME',
								width : 100
							}, {
								title : '业务员',
								field : 'SALENAME',
								width : 100
							}, {
								title : '商户编号',
								field : 'MID',
								width : 100
							}, {
								title : '商户名称',
								field : 'RNAME',
								width : 100
							}, {
								title : '交易日期',
								field : 'TXNDAY',
								width : 100
							}, {
								title : '交易时间',
								field : 'TXNDATE',
								width : 100
							}, {
								title : '交易金额',
								field : 'TXNAMOUNT',
								width : 100
							}, {
								title : '手续费',
								field : 'MDA',
								width : 100
							}, {
								title : '交易渠道',
								field : 'ISMPOS',
								width : 100,
								formatter : function(value, row, index) {
									if (value == '1') {
										return '传统POS';
									} else if (value == '0') {
										return 'MPOS';
									} else if (value == '2') {
										return '会员宝收银台';
									} else if (value == '3'){
										return 'plus';
									} else if (value == '4'){
										return '会员宝Pro';
									}
								}
							}, {
								title : '交易方式',
								field : 'TYPE',
								width : 90,
								formatter : function(value, row, index) {
									if (value == '0') {
										return '银行卡';
									} else if (value == '1') {
										return '微信';
									} else if (value == '2') {
										return '支付宝';
									} else if (value == '3') {
										return '银联二维码';
									} else if (value == '4') {
										return '快捷支付';
									} else if (value == '5') {
										return '手机Pay';
									} else if (value == '6') {
										return '信用卡还款';
									} else if (value == '11') {
										return '花呗';
									} else if (value == '12') {
										return '花呗分期';
									}
								}
							}, {
								title : '交易类型',
								field : 'MTI',
								width : 100,
								formatter : function(value, row, index) {
									if (value == '0') {
										return '消费';
									} else if (value == '1') {
										return '预授权';
									} else if (value == '2') {
										return '预授权撤销';
									} else if (value == '3') {
										return '消费撤销';
									} else if (value == '4') {
										return '冲正';
									} else if (value == '5') {
										return '扫码退款';
									} else if (value == '6') {
										return '银行卡退货';
									}
								}
							}, {
								title : '交易卡号',
								field : 'CARDPAN',
								width : 100
							}, {
								title : '卡类型',
								field : 'CBRAND',
								width : 100,
								formatter : function(value, row, index) {
									if (value == '1') {
										return '借记卡';
									} else if (value == '2') {
										return '贷记卡';
									}
								}
							}, {
								title : '处理状态',
								field : 'TXNSTATE',
								width : 100,
								formatter : function(value, row, index) {
									if (value == '0') {
										return '成功';
									} else if (value == '1') {
										return '失败';
									} else if (value == '2') {
										return '待付款';
									}
								}
							}, {
								title : '授权码',
								field : 'AUTHCODE',
								width : 100
							}, {
								title : '流水号/商户订单号',
								field : 'STAN',
								width : 100
							}, {
								title : '参考号',
								field : 'RRN',
								width : 100
							}, {
								title : '终端编号',
								field : 'TID',
								width : 100,
								formatter : function(value, row, index) {
									if (value == null || value ==''||value.indexOf("null")==0) {
										return '空';
									} else {
										return value;
									}
								}
							},{
								title : 'SN号',
								field : 'INSTALLEDNAME',
								width : 100
							} ] ]
						});
		sysAdmin_20580_listUnitDealDetailTotal();
	});

	function sysAdmin_20580_listUnitDealDetailTotal() {
		$
				.ajax({
					url : '${ctx}/sysAdmin/checkUnitDealDetail_listUnitDealDetailTotal.action',
					type : 'post',
					data : serializeObject($('#sysAdmin_20580_searchForm')),
					dataType : 'json',
					success : function(data, textStatus, jqXHR) {
						if (data[1].msg != '' || data[1].msg != null) {
							$('#sysAdmin_20580_showTotal').html(data[1].msg)
						}
					}
				});
	}
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div id="sysAdmin_20580_toolbar">
		<a href="javascript:;" onclick="sysAdmin_20580_export()"
			class="easyui-linkbutton"
			data-options="iconCls:'icon-query-export',plain:true">导出Excel</a> <a
			id="sysAdmin_20580_showTotal" href="javascript:;"
			class="easyui-linkbutton" data-options="plain:true"></a>
	</div>
	<div data-options="region:'north',border:false"
		style="height: 100px; overflow: hidden; padding-top: 20px;">
		<form id="sysAdmin_20580_searchForm" style="padding-left: 3%">
			<table class="tableForm">
				<tr>
					<th>商户编号：</th>
					<td><input class="easyui-validatebox" name="mid"
						style="width: 126px;" /></td>
					<th>商户名称：</th>
					<td><input class="easyui-validatebox" name="rname"
						style="width: 126px;" /></td>
					<th>流水号/商户订单号：</th>
					<td><input class="easyui-validatebox" name="stan"
						style="width: 126px;" /></td>
					<th>交易卡号：</th>
					<td><input class="easyui-validatebox" name="cardPan"
						style="width: 126px;" /></td>
				</tr>
				<tr>
					<th>终端编号：</th>
					<td><input class="easyui-validatebox" name="tid"
						style="width: 126px;" /></td>
					<th>机构号：</th>
					<td><select name="unno" id="unno_20580"
						class="easyui-combogrid" style="width: 126px;"></select></td>
					<th>业务员：</th>
					<td><select id="saleName_20580" name="saleName"
						class="easyui-combogrid easyui-validatebox"
						data-options="editable:false" style="width: 126px;"></select></td>
					<th>交易渠道：</th>
					<td><select name="isMpos" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 126px;">
							<option value="" selected="selected">所有</option>
							<option value="1">传统POS</option>
							<option value="0">MPOS</option>
							<option value="2">会员宝收银台</option>
							<option value="3">会员宝PLUS</option>
							<option value="4">会员宝Pro</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_20580_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_20580_cleanFun();">清空</a>
					</td>
				</tr>
				<tr>
					<th>交易方式：</th>
					<td><select name="type" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 126px;">
							<option value="" selected="selected">所有</option>
							<option value="0">银行卡</option>
							<option value="1">微信</option>
							<option value="2">支付宝</option>
							<option value="3">银联二维码</option>
							<option value="4">快捷支付</option>
							<option value="5">手机Pay</option>
							<option value="6">信用卡还款</option>
							<option value="11">花呗</option>
							<option value="12">花呗分期</option>
					</select></td>
					<th>交易类型：</th>
					<td><select name="mti" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 126px;">
							<option value="" selected="selected">所有</option>
							<option value="0">消费</option>
							<option value="1">预授权</option>
							<option value="2">预授权撤销</option>
							<option value="3">消费撤销</option>
							<option value="4">冲正</option>
							<option value="5">扫码退款</option>
							<option value="6">银行卡退货</option>
					</select></td>
					<th>处理状态：</th>
					<td><select name="txnState" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 126px;">
							<option value="" selected="selected">所有</option>
							<option value="0">成功</option>
							<option value="1">失败</option>
							<option value="2">待付款</option>
					</select></td>
					<th>交易日期</th>
					<td><input class="easyui-datebox" id="txnDay_20580"
						name="txnDay" style="width: 126px;" /> &nbsp;&nbsp;-&nbsp;&nbsp;
						<input class="easyui-datebox" id="txnDay1_20580" name="txnDay1"
						style="width: 126px;" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_20580_datagrid" style="overflow: hidden;"></table>
	</div>
</div>