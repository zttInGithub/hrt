<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--结算成本(活动)查询-->
<script type="text/javascript">
	$(function(){
		$('#rebateType_00319w').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listRebateType.action',
			idField:'VALUEINTEGER',
			textField:'VALUEINTEGER',
			columns:[[
				{field:'NAME',title:'活动名称',width:90},
				{field:'VALUEINTEGER',title:'活动类型',width:60}
			]],
			onLoadSuccess:function(){
                $("#rebateType_00319w").combogrid("setValue","ALL");
            }
		});
	});

	//初始化treegrid
	$(function() {
		$('#sysAdmin_unit00319w_treegrid').datagrid({
			url : '${ctx}/sysAdmin/agentunit_query00319w.action',
			fit : true,
			fitColumns : false,
			border : false,
			nowrap : true,
			pagination: true,
			pageList : [ 10, 15 ],
			idField : 'UNNO',
			columns : [[ {
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
				field : 'T10',
				title : '是否已生效',
				width : 100
			}, {
				field : 'T5',
				title : '活动类型',
				width : 100
			}, {
				field : 'T6',
				title : '刷卡成本(%)',
				width : 150
			}, {
				field : 'T7',
				title : '刷卡提现转账手续费',
				width : 150
			}, {
				field : 'T8',
				title : '扫码1000以下（终端0.38）费率(%)',
				width : 150
			}, {
				field : 'T9',
				title : '扫码1000以下（终端0.38）转账费',
				width : 150
			},{
				field : 'WXR1',
				title : '扫码1000以上（终端0.38）费率(%)',
				width : 150
			},{
				field : 'WXC1',
				title : '扫码1000以上（终端0.38）转账费',
				width : 150
			},{
				field : 'WXR2',
				title : '扫码1000以上（终端0.45）费率(%)',
				width : 150
			},{
				field : 'WXC2',
				title : '扫码1000以上（终端0.45）转账费',
				width : 150
			},{
				field : 'ZFBR1',
				title : '扫码1000以下（终端0.45）费率(%)',
				width : 150
			},{
				field : 'ZFBC1',
				title : '扫码1000以下（终端0.45）转账费',
				width : 150
			},{
				field : 'EWMR1',
				title : '银联二维码费率(%)',
				width : 150
			},{
				field : 'EWMC1',
				title : '银联二维码转账费',
				width : 150
			},{
				field : 'YSFR1',
				title : '云闪付费率(%)',
				width : 150
			},{
				field : 'HBR1',
				title : '花呗费率(%)',
				width : 150
			},{
				field : 'HBC1',
				title : '花呗转账费',
				width : 150
			}]],toolbar : [ {
				id : 'btn_run',
				text : '成本导出',
				iconCls : 'icon-query-export',
				handler : function() {
					sysAdmin_00319w_exportFun();
				}
			}]
		});
	});

	function sysAdmin_00319w_exportFun() {
		$('#bill_agentunit00319w_searchForm').form('submit', {
			url : '${ctx}/sysAdmin/agentunit_export00319w.action'
		});
	}

	//表单查询
	function bill_agentunit00319w_searchFun() {
		$('#sysAdmin_unit00319w_treegrid').datagrid('load',
				serializeObject($('#bill_agentunit00319w_searchForm')));
	}

	//清除表单内容
	function bill_agentunit00319w_cleanFun() {
		$('#bill_agentunit00319w_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="bill_agentunit00319w_searchForm" style="padding-left:9%"
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
					<th>&nbsp;&nbsp;活动类型:</th>
					<td><select id="rebateType_00319w" name="remarks" class="easyui-combogrid" data-options="editable:false" style="width: 155px;"></select>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="bill_agentunit00319w_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="bill_agentunit00319w_cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_unit00319w_treegrid"></table>
	</div>
</div>