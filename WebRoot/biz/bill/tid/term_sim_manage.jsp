<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!-- SIM卡管理 -->
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_term_sim_m_datagrid').datagrid({
            url: '${ctx}/sysAdmin/billTerminalSim_listTerminalSimGrid.action',
            fit: true,
            fitColumns: true,
            border: false,
            nowrap: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 15],
            sortName: 'BTSID',
            sortOrder: 'asc',
            idField: 'BTSID',
            columns: [[{
                title: '唯一编号',
                field: 'BTSID',
                width: 100,
                hidden: true
            }, {
                title: '商户编号',
                field: 'MID',
                width: 70
            }, {
                title: 'SIM卡号',
                field: 'SIM',
                width: 80
            }, {
                title: 'SN号',
                field: 'SN',
                width: 80
            }, {
                title: 'SIM卡状态',
                field: 'STATUS',
                width: 80,
                //1:正常;2:欠费;3:注销'
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "初始状态";
                    } else if (value == 1) {
                        return "正常";
                    } else if (value == 2) {
                        return "欠费";
                    } else if (value == 3) {
                        return "注销";
                    }
                }
            }, {
                title: 'sn使用状态',
                field: 'SNSTATUS',
                width: 110,
                formatter: function (value, row, index) {
                    if (value == 2) {
                        return "已使用";
                    } else {
                        return "未使用";
                    }
                }
            }, {
                title: '测试期结束时间',
                field: 'TRYDATE',
                width: 110
            }, {
                title: 'SIM卡出厂日期',
                field: 'INITDATE',
                width: 70
            }, {
                title: '续费日期',
                field: 'PAYDATE',
                width: 70
            }, {
                title: '扣费日期',
                field: 'DEDUCTDATE',
                width: 70
            }, {
                title: '激活日期',
                field: 'ACTDATE',
                width: 75
            }, {
                title: '操作',
                field: 'operation',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_term_sim_m_editFun(' + index + ',' + row.BTSID + ')"/>';
                }
            }
            ]],
            toolbar:[ {
                id: 'btn_export1',
                text:'导出',
                iconCls:'icon-query-export',
                handler:function(){
                    sysAdmin_term_sim_m_exportFun();
                }
            }, {
                id: 'btn_batch_modify2',
                text: '批量修改',
                iconCls: 'icon-add',
                handler: function () {
                    sysAdmin_term_sim_m_batchModifyFun();
                }
            }, {
                id: 'btn_export3',
                text: '匹配导入/导出',
                iconCls: 'icon-add',
                handler: function () {
                    sysAdmin_term_sim_m_matchExportFun();
                }
            }]
        });
    });

    function sysAdmin_term_sim_m_matchExportFun() {
        $('<div id="sysAdmin_term_sim_m_matchExporDialog"/>').dialog({
            title: '<span style="color:#157FCC;">匹配导入/导出</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/tid/term_sim_manage_match_export.jsp',
            modal: true,
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#term_sim_manage_match_export_upload').form('submit', {
                        url: '${ctx}/sysAdmin/billTerminalSim_importTerminalSimMatchExport.action',
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
                                if (l[1] == "xls") {
                                    document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                                    return true;
                                }
                            }
                        },
                        success: function (data) {
                            console.log($.parseJSON(data))
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#sysAdmin_term_sim_m_matchExporDialog').dialog('destroy');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                } else {
                                    $('#sysAdmin_term_sim_m_matchExporDialog').dialog('destroy');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#sysAdmin_term_sim_m_matchExporDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }


    function sysAdmin_term_sim_m_batchModifyFun() {
        $('<div id="sysAdmin_term_sim_m_batchModifyDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量修改</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/tid/term_sim_manage_modify_import.jsp',
            modal: true,
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#term_sim_manage_modify_import_upload').form('submit', {
                        url: '${ctx}/sysAdmin/billTerminalSim_importTermSimModifyXls.action',
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
                                if (l[1] == "xls") {
                                    document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                                    return true;
                                }
                            }
                        },
                        success: function (data) {
                            console.log($.parseJSON(data))
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#sysAdmin_term_sim_m_batchModifyDialog').dialog('destroy');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                } else {
                                    $('#sysAdmin_term_sim_m_batchModifyDialog').dialog('destroy');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#sysAdmin_term_sim_m_batchModifyDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }
    
    function sysAdmin_term_sim_m_exportFun() {
        $('#sysAdmin_term_sim_m_searchForm').form('submit',{
            url:'${ctx}/sysAdmin/billTerminalSim_exportTerminalSimGrid.action'
        });
    }

    function sysAdmin_term_sim_m_editFun(index) {
        var rows = $('#sysAdmin_term_sim_m_datagrid').datagrid('getRows');
        var row = rows[index];
        $('<div id="sysAdmin_term_sim_m_editDialog"/>').dialog({
            title: '<span style="color:#157FCC;">SIM卡管理修改</span>',
            width: 380,
            height: 400,
            closed: false,
            href: '${ctx}/biz/bill/tid/term_sim_manage_up.jsp',
            modal: true,
            onLoad: function () {
                $('#readonly_mid').val(row.MID);
                $('#readonly_sim').val(row.SIM);
                $('#readonly_sn').val(row.SN);
                $('#readonly_snStatus').val(row.SNSTATUS==2?"已使用":"未使用");
                $('#modify_status').val(row.STATUS);
                $('#modify_tryDate').val(row.TRYDATE);
                $('#modify_payDate').val(row.PAYDATE);
                $('#static_btsId').val(row.BTSID);
            },
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#sysAdmin_term_sim_m_modifyForm2').form('submit', {
                        url: '${ctx}/sysAdmin/billTerminalSim_modifyTerminalSim.action',
                        success: function (data) {
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('unselectAll');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('reload');
                                    $('#sysAdmin_term_sim_m_editDialog').dialog('destroy');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                } else {
                                    $('#sysAdmin_term_sim_m_editDialog').dialog('destroy');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('reload');
                                    $('#sysAdmin_term_sim_m_datagrid').datagrid('unselectAll');
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
                    $('#sysAdmin_term_sim_m_editDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    function sysAdmin_term_sim_m_searchFun() {
        var start = $("#term_sim_m_start").datebox('getValue');
        var end = $("#term_sim_m_end").datebox('getValue');
        /*if(start=='' || end==''){
            $.messager.alert('提示', "请输入续费日期区间！！");
            return;
        }*/
        /*if((!start&&end)||(!end&&start)){
            $.messager.alert('提示', "查询时间必须为时间段");
            return;
        }*/
        start = start.replace(/\-/gi, "/");
        end = end.replace(/\-/gi, "/");
        var startTime = new Date(start).getTime();
        var endTime = new Date(end).getTime();
        if (start.substr(0,7)!=end.substr(0,7)){
            $.messager.alert('提示', "查询时间区间不在同一月！");
            return;
        }
        if ((endTime-startTime)<0){
            $.messager.alert('提示', "起始日期需小于截止日期");
            return;
        }
        $('#sysAdmin_term_sim_m_datagrid').datagrid('load', serializeObject($('#sysAdmin_term_sim_m_searchForm')));
    }

    function sysAdmin_term_sim_m_cleanFun() {
        $('#sysAdmin_term_sim_m_searchForm input').val('');
        $('#sysAdmin_term_sim_m_searchForm select').val('');
    }
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
    <form id="sysAdmin_term_sim_m_searchForm" style="padding-left:2%">
      <table class="tableForm">
        <tr>
          <th>SIM卡号：</th>
          <td><input type="text" name="sim" style="width: 150px;"/></td>&nbsp;&nbsp;&nbsp;
          <th>SN：</th>
          <td><input type="text" name="sn" style="width: 150px;"/></td>&nbsp;&nbsp;&nbsp;

          <th>续费日期：</th>
          <td><input type="text" name="start" id="term_sim_m_start" class="easyui-datebox" style="width: 150px;"/></td>
          <th>&nbsp;至&nbsp;</th>
          <td><input type="text" name="end" id="term_sim_m_end" class="easyui-datebox" style="width: 150px;"/>
          </td>&nbsp;&nbsp;&nbsp;

          <th>SIM卡状态：</th>
          <td>
            <select name="status" style="width:150px;">
              <option value="">all</option>
              <option value="1">正常</option>
              <option value="2">欠费</option>
              <option value="3">注销</option>
            </select>
          </td>&nbsp;&nbsp;&nbsp;
          <th>sn使用状态：</th>
          <td>
            <select name="snStatus" style="width:150px;">
              <option value="">all</option>
              <option value="2">已使用</option>
              <option value="1">未使用</option>
            </select>
          </td>&nbsp;&nbsp;&nbsp;
          <td style="text-align: center;">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="sysAdmin_term_sim_m_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_term_sim_m_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_term_sim_m_datagrid" style="overflow: hidden;"></table>
  </div>
</div>


