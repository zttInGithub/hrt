<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- 钱包提现记录 -->
<script type="text/javascript">
    var pageobj;

    $(function() {
        $('#sysAdmin_10167_datagrid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_queryCashRc10167.action',
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
                    title: '提现时间',
                    field: 'cashdate',
                    width: 100
                },{
                    title: '提现金额',
                    field: 'cashfee2',
                    width: 100,
                    formatter: function(value, row, index) {
                        var cashamt= 2;
                        var cashfee= 2;
                        if(!isNaN(row['cashamt'])){
                            cashamt=  parseFloat(row['cashamt']);
                        }
                        if(!isNaN(row['cashfee'])){
                            cashfee= parseFloat(row['cashfee']);
                        }
                        return cashamt+cashfee;
                    }
                },{
                    title: '分润提现代扣税点金额',
                    field: 'cashfee',
                    width: 120
                },{
                    title: '结算金额',
                    field: 'cashamt',
                    width: 80
                },{
                    title: '入账卡号',
                    field: 'bankaccno',
                    width: 100
                },{
                    title: '入帐名称',
                    field: 'bankaccname',
                    width: 100
                },{
                    title: '开户行名称',
                    field: 'bankbranch',
                    width: 100
                },{
                    title: '支付系统行号',
                    field: 'paymentnumber',
                    width: 100
                },{
                    title: '提现状态',
                    field: 'cashstatus',
                    width: 70,
                    formatter: function(value, row, index) {
                        if (value == '1') {
                            return "审核中";
                        } else if (value == '3') {
                            return "提现失败";
                        } else if (value == '4') {
                            return "审核通过";
                        } else {
                            return value;
                        }
                    }
                }, {
                    title: '备注',
                    field: 'remarks',
                    width: 100
                }
                ]
            ]
        });

        refreshPurse();
    });

    function refreshPurse() {
        //初始化金额 和余额
        $.ajax({
            url: "sysAdmin/agentunit_queryPurseRc10167.action",
            dataType: "json",
            type: "post",
            error: function(err) {
                alert("错误");
            },
            success: function(data) {
                if (data.sessionExpire) {
                    window.location.href = getProjectLocation();
                } else {
                    if (data.success) {
                        pageobj=data.obj;
                        console.log(data.obj)
                        if(data.obj.cashStatus==0){
                            $("#errorInfoContainer").show();
                            $("#errorInfoContainer").siblings().hide();
                            $("#errorInfo").text(data.obj.message);
                        }else{
                            if(data.obj.status==0){
                                $("#balance").text(data.obj.balance); //账户余额
                                if(parseFloat(data.obj.curamt)<0){ //可提现金额
                                    $("#curamt").text(0);
                                }else{
                                    $("#curamt").text(data.obj.curamt);
                                }
                                $("#cycle").text(data.obj.cycle);  //结算周期  cycle   默认30天
                                $("#showInfo").show();
                                $("#showInfo").siblings().hide();
                            }else{
                                $("#errorInfoContainer").show();
                                $("#errorInfoContainer").siblings().hide();
                                $("#errorInfo").text(data.obj.message);
                            }
                        }
                    } else {
                        $.messager.show({
                            title: '提示',
                            msg: '查询金额失败'
                        });
                    }
                }
            }
        });
    }

    /**
     * 申请提现，金额不能大于可提现金额
     */
    function sysAdmin_10167_takeCurAmt() {
        $('<div id="sysAdmin_10167_popwindow"/>').dialog({
            title: '<span style="color:#157FCC;">申请提现</span>',
            width: 500,
            height: 278,
            resizable: true,
            maximizable: false,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/10167_1.jsp',
            onLoad:function() {
                $('#cash10167_1').form('load', pageobj);
            },
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function() {
                    var curamt = $("#curamt").text();
                    var pushCuramt = $("#pushCuramt").val();
                    if (parseFloat(pushCuramt) > parseFloat(curamt)) {
                        $.messager.alert('提示', "提现金额[" + pushCuramt + "]不能大于当前可提现金额[" + curamt + "]");
                        return;
                    }
                    if (parseFloat(pushCuramt) < 500.0) {
                        $.messager.alert('提示', "提现金额[" + pushCuramt + "]最少500元");
                        return;
                    }
                    $.messager.confirm('提示', '请确认信息是否无误，提交申请后将不能调整！', function(re) {
                        if (re) {
                            var validator = $('#cash10161').form('validate');
                            if (validator) {
                                $.messager.progress();
                            }
                            $('#cash10167_1').form('submit', {
                                url: '${ctx}/sysAdmin/agentunit_takeCurAmtRc10167.action',
                                success: function(data) {
                                    $.messager.progress('close');
                                    var result = $.parseJSON(data);
                                    if (result.sessionExpire) {
                                        window.location.href = getProjectLocation();
                                    } else {
                                        if (result.success == true) {
                                            $('#sysAdmin_10167_datagrid').datagrid('reload');
                                            $('#sysAdmin_10167_popwindow').dialog('destroy');
                                            $.messager.show({
                                                title: '提示',
                                                msg: result.msg
                                            });
                                            refreshPurse();
                                        } else {
                                            $('#sysAdmin_10167_popwindow').dialog('destroy');
                                            $.messager.alert('提示', result.msg);
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function() {
                    $('#sysAdmin_10167_popwindow').dialog('destroy');
                }
            }],
            onClose: function() {
                $(this).dialog('destroy');
            }
        });
    }

    function sysAdmin_10167_queryCash() {
        $('#sysAdmin_10167_datagrid').datagrid('load', serializeObject($('#bill_agentunit_10167purseForm')));
    }

    function bill_10167_cleanFun() {
        $('#bill_agentunit_10167purseForm input').val('');
    }


</script>
<style type="text/css">
    .pdl20 {
        padding-left: 20px;
    }

    .pdd10 {
        padding-bottom: 10px;
    }
    .font20{
        font-size:20px;
    }
    .tableForm .floatleft{
        float: right;
    }

    .hrt_toolbar {
        width: 100%;background: #EFEFEF;height: 34px;position: absolute;bottom: 0px;
    }
    .hrt_warmInfo {
        width: 100% ;text-align: center;display: inline-block;color: red;
    }
    .fontTable span{
        font-size:20px;
    }
    .errorInfoContainer{
        display: none;height: 75px;padding-top: 20px
    }
    .cash10167Form .easyui-linkbutton span{
        font-size:12px;
    }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;" class="cash10167Form">
        <div style="margin:0 auto;width: 100%;margin-bottom: 30px;">
            <table class="tableForm fontTable pdd10 pdl20" id="showInfo" style="display: none">
                <tr>
                    <th>
                        <span class="floatleft">可提现金额:</span>
                    </th>
                    <td>
                        <span id="curamt" >0</span><span >元</span>
                    </td>
                    <td style="width: 40px;"></td>
                    <th>
                        <span class="floatleft" >账户余额:</span>
                    </th>
                    <td>
                        <span id="balance" >0</span><span >元</span>
                    </td>
                    <td style="width: 40px;"></td>
                    <%--<th><span class="floatleft" >提现代扣税点:</span></th>
                    <td>
                        <span id="cashRate" ></span><span>%</span>
                    </td>
                    <td colspan="2"></td>--%>
                </tr>
                <tr>
                    <%--<th><span class="floatleft">分润冻结金额:</span></th>
                    <td> <span id="frozenamt">0</span><span >元</span></td>
                    <td style="width: 40px;"></td>--%>
                    <th><span class="floatleft">结算周期:</span></th>
                    <td> <span id="cycle" >30</span><span >天</span> </td>
                    <td style="width: 40px;"></td>
                    <%--<th><span class="floatleft">钱包状态:</span></th>
                    <td>
                        <span id="purseStatus" ></span>
                    </td>
                    <td style="width: 40px;"></td>--%>
                    <td> <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="sysAdmin_10167_takeCurAmt();">申请提现</a> </td>
                </tr>
            </table>
            <div id="errorInfoContainer" class="errorInfoContainer">
                <span class="font20 hrt_warmInfo" id="errorInfo"></span>
            </div>
            <div style="height: 75px;"></div>
        </div>
        <!-- 内部仿toolbar -->
        <div class="hrt_toolbar">
            <form id="bill_agentunit_10167purseForm" style="padding:0px;">
                <table class="tableForm pdl20">
                    <tr>
                        <td style="width: 78px;">提现日期：</td>
                        <td>
                            <input name="cashDay" class="easyui-datebox" data-options="editable:false" style="width: 140px;" /> 到
                            <input name="cashDay1" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                        </td>
                        <td colspan="2" style="padding-left: 50px"> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="sysAdmin_10167_queryCash();">查询</a> &nbsp; <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="bill_10167_cleanFun();">清空</a> </td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- 仿toolbar end -->
    </div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
        <table id="sysAdmin_10167_datagrid"></table>
    </div>
</div>
