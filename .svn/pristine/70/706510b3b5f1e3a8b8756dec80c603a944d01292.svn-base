<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- 活动返现日报表 -->
<script type="text/javascript">
    $(function() {
        $('#sysAdmin_10171_datagrid').datagrid({
            url: '${ctx}/sysAdmin/checkCashbackDayAction_queryCheckDeductionListInfo10171.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                    [{
                        title: '一代机构',
                        field: 'UPPER1_UNIT',
                        width: 80
                    },{
                        title: '一代机构名称',
                        field: 'UPPER1_NAME',
                        width: 80
                    },{
                        title: '二代机构',
                        field: 'UPPER2_UNIT',
                        width: 80
                    },{
                        title: '二代机构名称',
                        field: 'UPPER2_NAME',
                        width: 80
                    },{
                        title: '最终机构',
                        field: 'UNNO',
                        width: 80
                    },{
                        title: '最终机构名称',
                        field: 'UNNO_NAME',
                        width: 80
                    },{
                        title: '归属分公司',
                        field: 'UPPER_UNIT',
                        width: 80
                    },{
                        title: '商户编号',
                        field: 'MID',
                        width: 80
                    },{
                        title: 'SN号',
                        field: 'SN',
                        width: 80
                    },{
                        title: '型号',
                        field: 'SN_TYPE',
                        width: 80,
                        formatter : function(value,row,index) {
                            if(value==1){
                                return "小蓝牙";
                            }else if(value == 2){
                                return "大蓝牙";
                            }
                        }
                    },{
                        title: '出售日期',
                        field: 'KEYCONFIRMDATE',
                        width: 80
                    },{
                        title: '出售月',
                        field: 'KEYMONTH',
                        width: 80
                    },{
                        title: '入网日期',
                        field: 'USEDCONFIRMDATE',
                        width: 80
                    },{
                        title: '入网月',
                        field: 'USEMONTH',
                        width: 80
                    },{
                        title: '返利金额1',
                        field: 'REBATE1_AMT',
                        width: 80
                    },{
                        title: '返利日期1',
                        field: 'REBATE1_MONTH',
                        width: 80
                    },{
                        title: '返利金额2',
                        field: 'REBATE2_AMT',
                        width: 80
                    },{
                        title: '返利日期2',
                        field: 'REBATE2_MONTH',
                        width: 80
                    },{
                        title: '返利金额3',
                        field: 'REBATE3_AMT',
                        width: 80
                    },{
                        title: '返利日期3',
                        field: 'REBATE3_MONTH',
                        width: 80
                    },{
                        title: '交易金额',
                        field: 'TXNAMOUNT',
                        width: 80
                    },{
                        title: '交易笔数',
                        field: 'TXNCOUNT',
                        width: 80
                    },{
                        title: '活动类型',
                        field: 'REBATETYPE',
                        width: 80
                    }
                    ]
            ],toolbar : [ {
                id : 'btn_run',
                text : '活动返现日报表导出',
                iconCls : 'icon-query-export',
                handler : function() {
                    sysAdmin_10171_exportFun();
                }
            }]
        });
    });

    function sysAdmin_10171_exportFun() {
        $('#bill_agentunit_10171purseForm').form('submit', {
            url : '${ctx}/sysAdmin/checkCashbackDayAction_export10171.action'
        });
    }

    function sysAdmin_10171_queryCash() {
        $('#sysAdmin_10171_datagrid').datagrid('load', serializeObject($('#bill_agentunit_10171purseForm')));
    }

    function bill_10171_cleanFun() {
        $('#bill_agentunit_10171purseForm input').val('');
        $('#bill_agentunit_10171purseForm select').val('全部');
    }
</script>
<style type="text/css">
    .pdl20 {
        padding-left: 20px;
    }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
        <form id="bill_agentunit_10171purseForm" style="padding:0px;">
            <table class="tableForm pdl20">
                <%--筛选条件
                交易日：可自由选择某一日，代表交易月1日至选择日累计期间段
                出售月：可自由选择某一日，代表选择日所在月
                一代机构：一代机构号精确查询
                归属：一代机构号简称精确查询
                归属分公司：中心机构号精确查询
                活动类型：活动2、活动3、活动9、活动10、活动11、活动12、活动13、活动14、活动15、活动16、活动17、活动18、活动19、活动20、活动21、ALL
                商户编码：MID精确查询
                SN：SN精确查询
                功能按钮：查询、清空、导出--%>

                <tr>
                    <th style="">交易日:</th>
                    <td>
                        <input name="lmDate" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                    </td>
                    <th style="">出售月:</th>
                    <td>
                        <input name="keyMonth" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                    </td>
                    <th style="">一代机构:</th>
                    <td><input name="upper1Unit" style="width: 135px;" /></td>
                    <th>归属:</th>
                    <td><input name="unno" style="width: 135px;" /></td>
                    <th>归属分公司:</th>
                    <td><input name="upperUnit" style="width: 135px;" /></td>
                </tr>
                 <tr>
                    <th>活动类型:</th>
                    <td>
                        <select name="rebateType" style="width: 135px;" >
                            <option value='' selected="selected">All</option>
                            <option value=2>活动2</option>
                            <option value=3>活动3</option>
                            <option value=9>活动9</option>
                            <option value=10>活动10</option>
                            <option value=11>活动11</option>
                            <option value=12>活动12</option>
                            <option value=13>活动13</option>
                            <option value=14>活动14</option>
                            <option value=15>活动15</option>
                            <option value=16>活动16</option>
                            <option value=17>活动17</option>
                            <option value=18>活动18</option>
                            <option value=19>活动19</option>
                            <option value=20>活动20</option>
                            <option value=21>活动21</option>
                        </select>
                    </td>
                    <th>商户编码:</th>
                    <td><input name="mid" style="width: 135px;" /></td>
                    <th>SN:</th>
                    <td><input name="sn" style="width: 135px;" /></td>
                     <th>&nbsp;</th>
                    <td>
                        <a href="javascript:void(0);"
                             class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
                             onclick="sysAdmin_10171_queryCash();">查询</a> &nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-cancel',plain:true" onclick="bill_10171_cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
        <table id="sysAdmin_10171_datagrid"></table>
    </div>
</div>
