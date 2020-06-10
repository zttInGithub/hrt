<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script>
    $(function () {
    	document.getElementById("addCommit").setAttribute("disabled", true);
        $('#status').combogrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
            idField: 'enumValue',
            textField: 'enumName',
            mode: 'remote',
            fitColumns: true,
            queryParams: {
                enumTable: 'SYS',
                enumColumn: 'STATUS'
            },
            columns: [[
                {field: 'enumValue', title: '值', width: 50, checkbox: true},
                {field: 'enumName', title: '状态', width: 150}
            ]]
        });
        $('#txntype').combogrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
            idField: 'enumValue',
            textField: 'enumName',
            mode: 'remote',
            fitColumns: true,
            queryParams: {
                enumTable: 'BILL_REBATE_INFO',
                enumColumn: 'TXNTYPE'
            },
            columns: [[
                {field: 'enumValue', title: '值', width: 50, checkbox: true},
                {field: 'enumName', title: '状态', width: 150}
            ]]
        });
        $('#walletStatus').combogrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
            idField: 'enumValue',
            textField: 'enumName',
            mode: 'remote',
            fitColumns: true,
            queryParams: {
                enumTable: 'BILL_REBATE_INFO',
                enumColumn: 'WALLETSTATUS'
            },
            columns: [[
                {field: 'enumValue', title: '值', width: 50, checkbox: true},
                {field: 'enumName', title: '状态', width: 150}
            ]]
        });
        $('#prodInfo').combogrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
            idField: 'enumValue',
            textField: 'enumName',
            mode: 'remote',
            fitColumns: true,
            queryParams: {
                enumTable: 'BILL_REBATE_INFO',
                enumColumn: 'PRODINFO'
            },
            columns: [[
                {field: 'enumValue', title: '值', width: 50, checkbox: true},
                {field: 'enumName', title: '状态', width: 150}
            ]]
        });
        $('#specialType').combogrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
            idField: 'enumValue',
            textField: 'enumName',
            mode: 'remote',
            fitColumns: true,
            queryParams: {
                enumTable: 'BILL_REBATE_INFO',
                enumColumn: 'SPECIALTYPE'
            },
            columns: [[
                {field: 'enumValue', title: '值', width: 50, checkbox: true},
                {field: 'enumName', title: '状态', width: 150}
            ]]
        });
        $('#ifFirCashfee').combogrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
            idField: 'enumValue',
            textField: 'enumName',
            mode: 'remote',
            fitColumns: true,
            queryParams: {
                enumTable: 'BILL_REBATE_INFO',
                enumColumn: 'IF_FIR_CASHFEE'
            },
            columns: [[
                {field: 'enumValue', title: '值', width: 50, checkbox: true},
                {field: 'enumName', title: '状态', width: 150}
            ]]
        });
        var allNames = [];
        $('#sysAdmin_rebate_infoRebateRule_grid').datagrid({
            url: '${ctx}/sysAdmin/rebateRuleAction_listRebateRulesYes.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
           remoteSort: true,
            idField: 'brrId',
            columns: [[{
                title: '活动规则ID',
                field: 'brrId',
                width: 100,
                // hidden: true,
                checkbox: true
            }, {
                title: '规则名称',
                field: 'brrName',
                width: 100
            }, {
                title: '规则类型',
                field: 'pointType',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|POINTTYPE|POINTTYPE|" + value]
                }
            }, {
                title: '交易类型',
                field: 'txnWay',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|TXNWAY|TXNWAY|" + value]
                }
            }, {
                title: '开始时间类型',
                field: 'timeType',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|TIMETYPE|TIMETYPE|" + value]
                }
            }, {
                title: '统计周期',
                field: 'cycle',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|CYCLE|CYCLE|" + value]
                }
            }, {
                title: '开始时间',
                field: 'startTime',
                width: 100
            }, {
                title: '结束时间',
                field: 'endTime',
                width: 100
            }, {
                title: '交易金额类型',
                field: 'txnType',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|TXNTYPE|TXNTYPE|" + value]
                }
            }, {
                title: '交易金额-刷卡',
                field: 'txnAmount',
                width: 100
            }, {
                title: '返现对象1',
                field: 'backType1',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|BACKTYPE1|BACKTYPE1|" + value]
                }
            }, {
                title: '返利金额1',
                field: 'backAmount1',
                width: 100
            }, {
                title: '返现对象2',
                field: 'backType2',
                width: 100,
                formatter: function (value, row, index) {
                   return json["2|BILL_REBATE_RULE|BACKTYPE2|BACKTYPE2|" + value]
                }
            }, {
                title: '返利金额2',
                field: 'backAmount2',
                width: 100
            }, {
                title: '交易月-销售月开始',
                field: 'startMonth',
                width: 100
            }, {
                title: '交易月-销售月结束',
                field: 'endMonth',
                width: 100
            }, {
                title: '扣款成功金额',
                field: 'paymentAmount',
                width: 100
            }, {
                title: '规则描述',
                field: 'remark1',
                width: 100
            }]],
            onLoadSuccess: function (row) {
                allNames = [];
                var remarkValues = $('#remark1').val() + ",";
                var rowData = row.rows;
                $.each(rowData, function (idx, val) {
                    if (remarkValues.indexOf(val.brrId + ",") != -1) {
                        //如果数据行为已选中则选中改行
                        $("#sysAdmin_rebate_infoRebateRule_grid").datagrid("selectRow", idx);
                        allNames.push(val.brrName);
                    }
                });
                $('#remark2').val(allNames);
                // console.log(allNames);
            }
        });

    });

    function addRebateRuleIds() {
        debugger;
        var items = $('#sysAdmin_rebate_infoRebateRule_grid').datagrid('getSelections');
        var brrIds = [];
        var brrNames = [];
        $(items).each(function () {
            brrIds.push(this.brrId);
            brrNames.push(this.brrName);
        });
        $('#remark2').val(brrNames);
        $('#remark3').val(brrNames);
        $('#remark1').val(brrIds);
    }

</script>
<div>
  <form id="s_rebate_info_audit_addForm" style="padding-left:20px;" method="post">
    <fieldset>
      <legend>活动定义详情</legend>
      <table class="table1">
        <tr>
        <tr>
          <th>活动：</th>
          <td><input type="text" name="rebateType" id="rebateType" style="width: 150px;" readonly="readonly"></td>
          <th>活动名称：</th>
          <td><input type="text" name="rebateName" id="rebateName" style="width: 150px;" readonly="readonly"></td>
          <th>状态：</th>
          <td><input type="text" name="status" id="status" style="width: 150px;"></td>
        </tr>
        <tr>
          <th>开始时间：</th>
          <td><input name="startDate" id="startDate" style="width: 150px;" readonly="readonly"></td>
          <th>结束时间：</th>
          <td><input name="endDate" id="endDate" style="width: 150px;" readonly="readonly"></td>
          <th>结算价：</th>
          <td><input type="text" name="settlement" id="settlement" readonly="readonly"></td>
        </tr>
        <tr>
          <th>身份证号返现次数：</th>
          <td><input type="text" name="cardBack" id="cardBack" readonly="readonly"></td>
          <th>新商户定义信息：</th>
          <td><input type="text" name="merDefine" id="merDefine" readonly="readonly"></td>
        </tr>
        <tr>
          <th>默认刷卡费率(例:0.0050)：</th>
          <td><input type="text" name="defaultTxnRate" id="defaultTxnRate" style="width: 150px;" readonly="readonly"></td>
          <th>默认刷卡转账手续费：</th>
          <td><input type="text" name="defaultTxnCashfee" id="defaultTxnCashfee" style="width: 150px;" readonly="readonly"></td>
          <th>交易需要累加月数：</th>
          <td><input type="text" name="sumMonth" id="sumMonth" style="width: 150px;" readonly="readonly"></td>
        </tr>
        <tr>
          <th>默认扫码费率(1000以下)：</th>
          <td><input type="text" name="defaultScanRate" id="defaultScanRate" style="width: 150px;" readonly="readonly"></td>
          <th>默认扫码转账手续费(1000以下)：</th>
          <td><input type="text" name="defaultScanCashfee" id="defaultScanCashfee" style="width: 150px;" readonly="readonly" ></td>
          <th>默认扫码费率(1000以上)：</th>
          <td><input type="text" name="defaultScanRateUp" id="defaultScanRateUp" style="width: 150px;" readonly="readonly"></td>
        </tr>
        <tr>
          <th>默认扫码转账手续费(1000以上)：</th>
          <td><input type="text" name="defaultScanCashfeeUp" id="defaultScanCashfeeUp" style="width: 150px;" readonly="readonly" ></td>
          <th>默认花呗费率：</th>
          <td><input type="text" name="defaultScanhbRate" id="defaultScanhbRate" style="width: 150px;" readonly="readonly"></td>
          <th>默认花呗转账手续费：</th>
          <td><input type="text" name="defaultScanhbCashfee" id="defaultScanhbCashfee" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>活动归属机构：</th>
          <td><input type="text" name="unno" id="unno" style="width: 150px;" readonly="readonly"></td>
          <th>产品类型：</th>
          <td><input type="text" name="prodInfo" id="prodInfo" style="width: 150px;" readonly="readonly"></td>
          <th>是否开通多级钱包：</th>
          <td><input type="text" name="walletStatus" id="walletStatus" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>首笔消费金额：</th>
          <td><input type="text" name="tradeAmt" id="tradeAmt" style="width: 150px;" readonly="readonly" ></td>
          <th>押金金额：</th>
          <td><input type="text" name="depositAmt" id="depositAmt" style="width: 150px;" readonly="readonly"></td>
          <th>首笔交易类型：</th>
          <td><input type="text" name="txntype" id="txntype" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>押金状态配置：</th>
          <td><input type="text" name="configStatus" id="configStatus" style="width: 150px;" readonly="readonly"></td>
          <th>押金首笔配置：</th>
          <td><input type="text" name="configAmt" id="configAmt" style="width: 150px;" readonly="readonly"></td>
          <th>是否存在花呗成本：</th>
          <td><input type="text" name="specialType" id="specialType" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>刷卡费率最低限制：</th>
          <td><input type="text" name="limitRate1" id="limitRate1" style="width: 150px;" readonly="readonly"></td>
          <th>刷卡费率最大限制：</th>
          <td><input type="text" name="limitRate2" id="limitRate2" style="width: 150px;" readonly="readonly"></td>
          <th>刷卡转账费最低限制：</th>
          <td><input type="text" name="limitAmt1" id="limitAmt1" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>刷卡转账费最大限制：</th>
          <td><input type="text" name="limitAmt2" id="limitAmt2" style="width: 150px;" readonly="readonly" ></td>
          <th>扫码1000以上费率最低限制：</th>
          <td><input type="text" name="limitScanUpRate1" id="limitScanUpRate1" style="width: 150px;" readonly="readonly"></td>
          <th>扫码1000以上费率最大限制：</th>
          <td><input type="text" name="limitScanUpRate2" id="limitScanUpRate2" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>扫码1000以下费率最低限制：</th>
          <td><input type="text" name="limitScanDownRate1" id="limitScanDownRate1" style="width: 150px;" readonly="readonly" ></td>
          <th>扫码1000以下费率最大限制：</th>
          <td><input type="text" name="limitScanDownRate2" id="limitScanDownRate2" style="width: 150px;" readonly="readonly" ></td>
          <th>花呗费率最低限制：</th>
          <td><input type="text" name="limitHbRate1" id="limitHbRate1" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>花呗费率最大限制：</th>
          <td><input type="text" name="limitHbRate2" id="limitHbRate2" style="width: 150px;" readonly="readonly" ></td>
          <th>押金限制：</th>
          <td><input type="text" name="limitDeposit" id="limitDeposit" style="width: 150px;" readonly="readonly"></td>
        </tr>
        <tr>
          <th>押金设备首笔激活是否收转账费：</th>
          <td><input type="text" name="ifFirCashfee" id="ifFirCashfee" style="width: 150px;" readonly="readonly"  ></td>
          <th>免收转账费周期(以首笔激活为准)：</th>
          <td><input type="text" name="noCashfeeMonth" id="noCashfeeMonth" style="width: 150px;" readonly="readonly" ></td>
        </tr>
        <tr>
          <th>活动执行机构or运营中心：</th>
          <td><input type="text" name="exeUnno" id="exeUnno" style="width: 150px;" readonly="readonly" ></td>
          <th>调价周期-自然月：</th>
          <td><input type="text" name="cycleMonth" id="cycleMonth" style="width: 150px;" readonly="readonly" ></td>
        </tr>
      </table>
    </fieldset>
    <input type="hidden" id="brId" name="brId" style="width: 150px;">
    <input type="hidden" id="remark1" name="remark1" style="width: 150px;">
    <input type="hidden" id="remark2" name="remark2" style="width: 150px;">
  </form>
  <fieldset>
    <legend>活动规则</legend>
    <table class="table1">
      <tr>
        <th>规则：</th>
        <td><input type="text" name="remark3" id="remark3" readonly="readonly"  style="width: 150px;"></td>
        <td colspan="2">
          <button onclick="addRebateRuleIds()" id="addCommit">确定添加</button>
        </td>
      </tr>
    </table>
  </fieldset>
  <div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'center', border:false" style="overflow: hidden;">
      <table id="sysAdmin_rebate_infoRebateRule_grid" style="overflow: hidden;"></table>
    </div>
  </div>
</div>