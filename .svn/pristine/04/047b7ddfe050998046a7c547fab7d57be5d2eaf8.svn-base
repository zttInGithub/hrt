<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 下级代理分润差额钱包开通 -->
<script type="text/javascript">
    $(function () {
        $('#rebateType_wallet_cash_switch').combogrid({
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
        $('#wallet_cash_switch').datagrid({
            url: '${ctx}/sysAdmin/checkWalletCashSwitch_listCheckWalletCashSwitch.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '机构号',
                    field: 'UNNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '机构名称',
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
                    title: '本月状态',
                    field: 'WALLETSTATUS',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter: function (value, row, index) {
                        // 取上个月数据修改记录
                        if(row.WALLETSTATUS2!="2"){
                            if (row.WALLETSTATUS2 == "0") {
                                return "关";
                            } else if(row.WALLETSTATUS2 == "1") {
                                return "开";
                            }
                        }else{
                            if (value == "0") {
                                return "关";
                            } else if(value=="1"){
                                return "开";
                            }
                        }
                    }
                }, {
                    title: '下月状态',
                    field: 'WALLETSTATUS1',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "关";
                        } else if(value == "1") {
                            return "开";
                        } else if(value == "2") {
                            if(row.WALLETSTATUS2!="2"){
                                if (row.WALLETSTATUS2 == "0") {
                                    return "关";
                                } else if(row.WALLETSTATUS2 == "1") {
                                    return "开";
                                }
                            }else{
                                if (row.WALLETSTATUS == "0") {
                                    return "关";
                                } else if(row.WALLETSTATUS=="1"){
                                    return "开";
                                }
                            }
                        }
                    }
                }, {
                    title: '操作(下月生效)',
                    field: 'operation',
                    width: 100,
                    align: 'center',
                    formatter: function (value, row, index) {
                        if("0"==row.WALLETSTATUS1){
                            return '<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="wallet_cash_switch_next_open_close(' + index + ',1)"/>';
                        }else{
                            return '<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="wallet_cash_switch_next_open_close(' + index + ',0)"/>';
                        }

                    }
                }]
            ], toolbar: [{
                id: 'btn_add',
                text: '单个开通或变更',
                iconCls: 'icon-add',
                handler: function () {
                    wallet_cash_switch_addFun();
                }
            }, {
                id: 'btn_batch_add',
                text: '批量开通或变更',
                iconCls: 'icon-query-export',
                handler: function () {
                    wallet_cash_switch_batchaddFun();
                }
            }]
        });
    });

    function wallet_cash_switch_batchaddFun() {
        $('<div id="wallet_cash_switch_uploadDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量开通或变更</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/wallet_cash_switch_upload.jsp',
            modal: true,
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#wallet_cash_switch_uploadFrom').form('submit', {
                        url: '${ctx}/sysAdmin/checkWalletCashSwitch_importWalletCashSwitchXls.action',
                        onSubmit: function () {
                            var contact = document.getElementById('upload').value;
                            if (contact == "" || contact == null) {
                                $.messager.show({
                                    title: '提示',
                                    msg: '请选择要上传的文件',
                                    timeout: 5000,
                                    showType: 'slide'
                                });
                                return false;
                            }
                            if (contact != "" && contact != null) {
                                var l = contact.split(".");
                                if (l[1] != "xls") {
                                    $.messager.show({
                                        title: '提示',
                                        msg: '请选择后缀名为.xls文件',
                                        timeout: 5000,
                                        showType: 'slide'
                                    });
                                    return false;
                                }
                                //如果格式正确，处理
                                if (l[1] == "xls") {
                                    document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                                    return true;
                                }
                            }
                        },
                        //成功返回数据
                        success: function (data) {
                            var resault = $.parseJSON(data);
                            if (resault.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (resault.success) {
                                    $('#wallet_cash_switch_uploadDialog').dialog('destroy');
                                    $('#wallet_cash_switch').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: resault.msg
                                    });
                                } else {
                                    $.messager.alert('提示', resault.msg);
                                    // $('#wallet_cash_switch_uploadDialog').dialog('destroy');
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#wallet_cash_switch_uploadDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    function wallet_cash_switch_addFun() {
        $('<div id="wallet_cash_switch_addDialog"/>').dialog({
            title: '<span style="color:#157FCC;">单个开通或变更</span>',
            width: 400,
            height: 250,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/wallet_cash_switch_add.jsp',
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var validator = $('#wallet_cash_switch_add_from').form('validate');
                    if (validator) {
                        $.messager.progress();
                    }
                    $('#wallet_cash_switch_add_from').form('submit', {
                        url: '${ctx}/sysAdmin/checkWalletCashSwitch_saveCheckWalletCashSwitch.action',
                        success: function (data) {
                            $.messager.progress('close');
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#wallet_cash_switch').datagrid('reload');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                    $('#wallet_cash_switch_addDialog').dialog('destroy');
                                } else {
                                    $.messager.alert('提示', result.msg);
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#wallet_cash_switch_addDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    function wallet_cash_switch_next_open_close(index, status) {
        var rows = $('#wallet_cash_switch').datagrid('getRows');
        var row = rows[index];
        if (status == row.WALLETSTATUS1) {
            $.messager.alert('提示', "修改状态与下月状态相同");
        } else {
            $.ajax({
                url: "${ctx}/sysAdmin/checkWalletCashSwitch_updateCheckWalletCashSwitchStatus.action",
                type: 'post',
                data: {"unno": row.UNNO, "rebateType": row.REBATETYPE, "walletStatus": status},
                dataType: 'json',
                success: function (data, textStatus, jqXHR) {
                    if (data.success) {
                        $.messager.show({
                            title: '提示',
                            msg: data.msg
                        });
                        $('#wallet_cash_switch').datagrid('load');
                    } else {
                        $.messager.alert('提示', data.msg);
                        $('#wallet_cash_switch').datagrid('unselectAll');
                    }
                },
                error: function () {
                    $.messager.alert('提示', '修改状态出错！');
                    $('#wallet_cash_switch').datagrid('unselectAll');
                }
            });
        }

    }

    function wallet_cash_switch_queryFun() {
        $('#wallet_cash_switch').datagrid('load', serializeObject($('#wallet_cash_switch_searchForm')));
    }

    function wallet_cash_switch_cleanFun() {
        $('#wallet_cash_switch_searchForm input').val('');
        $('#wallet_cash_switch_searchForm select').val('全部');
    }
</script>
<style type="text/css">
  .pdl20 {
    padding-left: 20px;
  }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
    <form id="wallet_cash_switch_searchForm" style="padding:0px;">
      <table class="tableForm pdl20">
        <tr>
          <th style="">机构号:</th>
          <td><input name="unno" style="width: 135px;"/></td>
          <th style="">机构名称:</th>
          <td><input name="remark1" style="width: 135px;"/></td>
          <th style="">当月钱包状态:</th>
          <td>
            <select name="walletStatus" style="width: 135px;">
              <option value="" selected="selected">All</option>
              <option value="0">关</option>
              <option value="1">开</option>
            </select>
          </td>
          <th>活动类型:</th>
          <td>
            <select id="rebateType_wallet_cash_switch" name="rebateType" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:135px;"></select>
            <%--<input name="rebateType" style="width: 135px;"/>--%>
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
               onclick="wallet_cash_switch_queryFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true" onclick="wallet_cash_switch_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="wallet_cash_switch"></table>
  </div>
</div>
