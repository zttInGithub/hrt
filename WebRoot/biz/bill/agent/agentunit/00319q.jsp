<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--结算成本(非活动)查询-->
<script type="text/javascript">
	//初始化treegrid
	$(function() {
		$('#sysAdmin_unit00319q_treegrid').datagrid({
			url : '${ctx}/sysAdmin/agentunit_query00319q.action',
			fit : true,
			fitColumns : false,
			border : false,
			nowrap : true,
			pagination: true,
			pageList : [ 10, 15 ],
			idField : 'UNNO',
			frozenColumns : [ [ {
				title : '运营中心名称',
				field : 'T1',
				width : 100
			}, {
				title : '运营中心机构号',
				field : 'T2',
				width : 100
			},{
				title : '一级代理商/运营中心简称',
				field : 'T3',
				width : 160
			}, {
				title : '一级代理/运营中心机构号',
				field : 'T4',
				width : 160
			}, {
				title : '是否已生效',
				field : 'T42',
				width : 80
			} ] ],
			columns : [ [ {
				title : '手刷',
				colspan : 18
			}, {
				title : '代还',
				colspan : 1
			}, {
				title : '快捷',
				colspan : 3
			}, {
				title : '云闪付',
				colspan : 1
			}, {
				title : '传统',
				colspan : 12
			}, {
				title : '分润结算周期',
				field : 'T40',
				rowspan : 3
			}, {
				title : '分润税点（%）',
				field : 'T41',
				rowspan : 3
			}],[ {
				title : '刷卡T0',
				colspan : 4
			}, {
				title : '刷卡理财TO/T1',
				colspan : 5
			}, {
				title : '扫码T0',
				colspan : 6
			}, {
				title : '扫码T1',
				colspan : 3
			}, {
				title : '代还',
				colspan : 1
			}, {
				title : '快捷',
				colspan : 3
			}, {
				title : '云闪付',
				colspan : 1
			}, {
				title : '刷卡标准TO/T1',
				colspan : 3
			}, {
				title : '刷卡优惠TO/T1',
				colspan : 3
			}, {
				title : '刷卡减免TO/T1',
				colspan : 1
			}, {
				title : '扫码T0/T1',
				colspan : 3
			}, {
				title : '传统T0提现成本',
				colspan : 2
			}], [ {
				field : 'T5',
				title : '0.72秒到-借/贷成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T6',
				title : '0.72秒到转账成本',
				rowspan : 1,
				width : 100
			}, {
				field : 'T7',
				title : '0.6秒到-借/贷成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T8',
				title : '0.6秒到转账成本',
				rowspan : 1,
				width : 100
			}, {
				field : 'T9',
				title : '借记卡成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T10',
				title : '借记卡手续费封顶（元）',
				rowspan : 1,
				width : 100
			}, {
				field : 'T11',
				title : '贷记卡成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T12',
				title : '提现成本',
				rowspan : 1,
				width : 100
			}, {
				field : 'T13',
				title : '提现手续费成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T14',
				title : '扫码1000以下成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T15',
				title : '扫码1000以下转账成本',
				rowspan : 1,
				width : 100
			}, {
				field : 'T16',
				title : '扫码1000以上成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T17',
				title : '扫码1000以上转账成本 ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T18',
				title : '银联二维码成本(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T19',
				title : '银联二维码提现成本（元） ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T20',
				title : '扫码1000以下成本(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T21',
				title : ' 扫码1000以上成本(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T22',
				title : '银联二维码成本(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T23',
				title : '信用卡代还成本(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T24',
				title : '快捷支付成本VIP(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T25',
				title : '快捷支付成本完美账单(%) ',
				rowspan : 1,
				width : 100
			}, {
				field : 'T26',
				title : '快捷转账成本',
				rowspan : 1,
				width : 100
			}, {
				field : 'T27',
				title : '云闪付成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T28',
				title : '借记卡成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T29',
				title : '借记卡手续费封顶（元）',
				rowspan : 1,
				width : 100
			}, {
				field : 'T30',
				title : '贷记卡成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T31',
				title : '借记卡成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T32',
				title : '借记卡手续费封顶（元）',
				rowspan : 1,
				width : 100
			}, {
				field : 'T33',
				title : '贷记卡成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T34',
				title : '借/贷成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T35',
				title : '扫码1000以下成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T36',
				title : '扫码1000以上成本(%)',
				rowspan : 1,
				width : 100
			}, {
				field : 'T37',
				title : '银联二维码成本（%）',
				rowspan : 1,
				width : 100
			}, {
				field : 'T38',
				title : '提现成本',
				rowspan : 1,
				width : 100
			}, {
				field : 'T39',
				title : '提现手续费成本(%)',
				rowspan : 1,
				width : 100
			} ] ],
			rownumbers : true,
			toolbar : [ {
				id : 'btn_run',
				text : '成本导出',
				iconCls : 'icon-query-export',
				handler : function() {
					sysAdmin_00319q_exportFun();
				}
			}]
		});
	});

	function sysAdmin_00319q_exportFun() {
		$('#bill_agentunit00319q_searchForm').form('submit', {
			url : '${ctx}/sysAdmin/agentunit_export00319q.action'
		});
	}

	//表单查询
	function bill_agentunit00319q_searchFun() {
		$('#sysAdmin_unit00319q_treegrid').datagrid('load',
				serializeObject($('#bill_agentunit00319q_searchForm')));
	}

	//清除表单内容
	function bill_agentunit00319q_cleanFun() {
		$('#bill_agentunit00319q_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="bill_agentunit00319q_searchForm" style="padding-left:15%"
			method="post">
			<table class="tableForm">
				<tr>
					<th>运营中心机构号:</th>
					<td><input name="unno" style="width: 100px;" />
					</td>
					<th>&nbsp;&nbsp;运营中心名称:</th>
					<td><input name="agentName" style="width: 100px;" />
					</td>
					<th>&nbsp;&nbsp;一代机构号:</th>
					<td><input name="accType" style="width: 100px;" />
					</td>
					<th>&nbsp;&nbsp;一代机构名称:</th>
					<td><input name="accTypeName" style="width: 100px;" />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="bill_agentunit00319q_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="bill_agentunit00319q_cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_unit00319q_treegrid"></table>
	</div>
</div>