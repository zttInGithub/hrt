<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- 查询调整项 -->
<script type="text/javascript">
    $(function() {
        $('#sysAdmin_10168_datagrid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_queryAdjtxnRc10168.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            sortName: 'buid',
            sortOrder: 'desc',
            idField: 'buid',
            columns: [
                [{
                    title: '机构号',
                    field: 'unno',
                    width: 80
                },{
                    title: '机构名称',
                    field: 'agentname',
                    width: 100
                },{
                    title: '调整日期',
                    field: 'settleday',
                    width: 100
                },{
                    title: '调整金额',
                    field: 'feeamt',
                    width: 100
                },{
                    title: '备注',
                    field: 'feenote',
                    width: 100
                }
                ]
            ]
        });

    });


    function sysAdmin_10168_queryCash() {
        $('#sysAdmin_10168_datagrid').datagrid('load', serializeObject($('#bill_agentunit_10168purseForm')));
    }

    function bill_10168_cleanFun() {
        $('#bill_agentunit_10168purseForm input').val('');
    }
</script>
<style type="text/css">
    .pdl20 {
        padding-left: 20px;
    }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
        <form id="bill_agentunit_10168purseForm" style="padding:0px;">
            <table class="tableForm pdl20">
                <tr>
                    <td style="width: 78px;">调整日期：</td>
                    <td>
                        <input name="cashDay" class="easyui-datebox" data-options="editable:false" style="width: 140px;" /> 到
                        <input name="cashDay1" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                    </td>
                    <td colspan="2" style="padding-left: 50px">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="sysAdmin_10168_queryCash();">查询</a> &nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="bill_10168_cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
        <table id="sysAdmin_10168_datagrid"></table>
    </div>
</div>
