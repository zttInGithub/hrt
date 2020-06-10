<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 本级及下级代理活动钱包生效记录 -->
<script type="text/javascript">
    $(function () {
        $('#wallet_cash_switch_log').datagrid({
            url: '${ctx}/sysAdmin/checkWalletCashSwitch_listCheckWalletCashSwitchLog.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '代理机构号',
                    field: 'UNNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '代理机构名称',
                    field: 'UN_NAME',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '活动类型',
                    field: 'REBATETYPE',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '开始时间',
                    field: 'CREATEDATE',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '结束时间',
                    field: 'ENDDATE',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter: function (value, row, index) {
                        // console.log(value)
                        if (value) {
                            return value;
                        } else {
                            return "至今";
                        }
                    }
                }, {
                    title: '钱包状态',
                    field: 'WALLETSTATUS',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "关";
                        } else {
                            return "开";
                        }
                    }
                }, {
                    title: '代理分类',
                    field: 'UN_LVL',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter: function (value, row, index) {
                        // console.log(value)
                        if ("${sessionScope.user.unitLvl}" === value + "") {
                            return "本级";
                        } else {
                            return "下级";
                        }
                    }
                }]
            ], toolbar: [{
                // id: 'btn_run',
                 text: '导出Excel',
                 iconCls: 'icon-query-export',
                 handler: function () {
                     wallet_cash_switch_log_exportFun();
                 }
            }]
        });

    });

    function wallet_cash_switch_log_exportFun() {
        $('#wallet_cash_switch_log_searchForm').form('submit', {
            url: '${ctx}/sysAdmin/checkWalletCashSwitch_exportCheckWalletCashSwitchLog.action'
        });
    }

    function wallet_cash_switch_log_queryFun() {
        $('#wallet_cash_switch_log').datagrid('load', serializeObject($('#wallet_cash_switch_log_searchForm')));
    }

    function wallet_cash_switch_log_cleanFun() {
        $('#wallet_cash_switch_log_searchForm input').val('');
        $('#wallet_cash_switch_log_searchForm select').val('全部');
    }

    $('#rebateType_wallet_cash_switch_log').combogrid({
        url : '${ctx}/sysAdmin/checkWalletCashSwitch_listAvailableRebateType.action',
        idField:'VALUEINTEGER',
        textField:'NAME',
        mode:'remote',
        value:-1,
        fitColumns:true,
        columns:[[
            {field:'NAME',title:'活动类型',width:70},
            {field:'VALUEINTEGER',title:'id',width:70,hidden:true}
        ]]
    });
</script>
<style type="text/css">
  .pdl20 {
    padding-left: 20px;
  }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
    <form id="wallet_cash_switch_log_searchForm" style="padding:0px;" method="post">
      <table class="tableForm pdl20">
        <tr>
          <th style="">机构号:</th>
          <td><input name="unno" style="width: 135px;"/></td>
          <th style="">机构名称:</th>
          <td><input name="remark1" style="width: 135px;"/></td>
          <th style="">钱包状态:</th>
          <td>
            <select name="walletStatus" style="width: 135px;">
              <option value="" selected="selected">All</option>
              <option value="0">关</option>
              <option value="1">开</option>
            </select>
          </td>
          <th>活动类型:</th>
          <td>
            <%--<input name="rebateType" style="width: 135px;"/>--%>
            <select id="rebateType_wallet_cash_switch_log" name="rebateType" class="easyui-combogrid"  style="width:135px;"></select>
            <%--<select name="rebateType" style="width: 135px;">
              <option value="" selected="selected">All</option>
              <option value="3">活动3</option>
              <option value="11">活动11</option>
              <option value="13">活动13</option>
              <option value="15">活动15</option>
              <option value="17">活动17</option>
              <option value="18">活动18</option>
              <option value="19">活动19</option>
              <option value="20">活动20</option>
            </select>--%>
          </td>
          <th>&nbsp;</th>
          <td>
            <a href="javascript:void(0);"
               class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="wallet_cash_switch_log_queryFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true" onclick="wallet_cash_switch_log_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="wallet_cash_switch_log"></table>
  </div>
</div>
