<%@ page language="java" import="com.hrt.frame.entity.pagebean.UserBean"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  Object userSession = request.getSession().getAttribute("user");
  UserBean user = ((UserBean) userSession);
%>
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_change_tm_grid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_queryBillBpFile3Grid.action',
            fit: true,
            fitColumns: false,
            border: false,
            nowrap: true,
            pagination: true,
            singleSelect: true,
            pageList: [10, 15],
            idField: 'FID',
            onBeforeLoad: function () {
            },
            onLoadSuccess: function () {
            },
            columns: [[{
                title: 'FID',
                field: 'FID',
                checkbox: true
            }, {
                title: '序号',
                field: 'FIDS',
                width: 100
            }, {
                title: '文件名称',
                field: 'FNAME',
                width: 160
            }, {
                title: '提交日期',
                field: 'CDATE',
                width: 130
            }, {
                title: '审核日期',
                field: 'ADATE',
                width: 140
            }, {
                title: '修改类型',
                field: 'COSTTYPE',
                width: 80,
                formatter: function (value, rowData, rowIndex) {
                    if (value == 1) {
                        return "SN";
                    } else if (value == 2) {
                        return "商户";
                    } else {
                        return "";
                    }
                }
            }, {
                title: '处理状态',
                field: 'STATUS',
                width: 100,
                formatter: function (value, rowData, rowIndex) {
                    if (value == 0) {
                        return "待审核";
                    }
                    if (value == 1) {
                        return "审核通过";
                    }
                    if (value == -1) {
                        return "退回";
                    }
                    if (value == -2) {
                        return "失败";
                    }
                }
            }, {
                title: '备注/退回原因',
                field: 'REMARKS',
                width: 200
            }, {
                title: '数据变更单',
                field: 'IMGUPLOAD',
                width: 200,
                // hidden: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<a style="color: #4169E1;text-decoration: underline;cursor:pointer;" href="${ctx}/sysAdmin/agentunit_downloadImgFile.action?FID=' + row.FID + '">下载</a>';
                    } else {
                        return "";
                    }
                }
            }, {
                title: '操作',
                field: 'operation',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.STATUS == -1 || row.STATUS == -2) {
                        return '<img src="${ctx}/images/frame_pencil.png" title="重新上传" style="cursor:pointer;" onclick="sysAdmin_change_tm_editImportXls(' + index + ')"/>&nbsp;&nbsp';
                    } else if (row.STATUS == 0) {
                        return '<img src="${ctx}/images/frame_pencil.png" title="数据变更单上传" style="cursor:pointer;" onclick="sysAdmin_change_tm_ImportImg(' + index + ')"/>&nbsp;&nbsp';
                    }
                }
            }
            ]], toolbar: [{
                id: 'btn_export1',
                text: '勾选导出',
                iconCls: 'icon-query-export',
                handler: function () {
                    sysAdmin_change_tm_exportFun();
                }
            }, {
                id: 'btn_export2',
                text: 'sn修改导入',
                iconCls: 'icon-add',
                handler: function () {
                    sysAdmin_change_tm_ImportXls(1, 0, 0);
                }
            }, {
                id: 'btn_export3',
                text: '商户费率修改导入',
                iconCls: 'icon-add',
                handler: function () {
                    sysAdmin_change_tm_ImportXls(2, 0, 0);
                }
            }
            ]
        });
    });

    function sysAdmin_change_tm_ImportImg(index) {
        var rows = $('#sysAdmin_change_tm_grid').datagrid('getRows');
        var row = rows[index];
        $('<div id="sysAdmin_change_tm_snImgDialog"/>').dialog({
            title: '<span style="color:#157FCC;">数据变更单上传</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/file/change_term_Img.jsp',
            modal: true,
            onLoad: function () {
                $('#sysAdmin_change_term_img_upload').form('load', row);
            },
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#sysAdmin_change_term_img_upload').form('submit', {
                        url: '${ctx}/sysAdmin/agentunit_saveOrUpdateSnMerImg.action',
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
                            document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                            return true;
                            // if (contact != "" && contact != null) {
                            //     var l = contact.split(".");
                            //     if (l[1] != "xls") {
                            //         $.messager.show({
                            //             title: '提示',
                            //             msg: '请选择后缀名为.xls文件',
                            //             timeout: 5000,
                            //             showType: 'slide'
                            //         });
                            //         return false;
                            //     }
                            //     //如果格式正确，处理
                            //     if (l[1] == "xls") {
                            //         document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                            //         return true;
                            //     }
                            // }
                        },
                        success: function (data) {
                            console.log($.parseJSON(data))
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#sysAdmin_change_tm_snImgDialog').dialog('destroy');
                                    $('#sysAdmin_change_tm_grid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                } else {
                                    $('#sysAdmin_change_tm_snImgDialog').dialog('destroy');
                                    $('#sysAdmin_change_tm_grid').datagrid('load');
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
                    $('#sysAdmin_change_tm_snImgDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    function sysAdmin_change_tm_editImportXls(index) {
        var rows = $('#sysAdmin_change_tm_grid').datagrid('getRows');
        var row = rows[index];
        sysAdmin_change_tm_ImportXls(row.COSTTYPE, -2, row);
    }

    function sysAdmin_change_tm_ImportXls(type, status, row) {
        var titleDialog;
        var url;
        if (type == 1) {
            titleDialog = "sn修改导入";
            url = '${ctx}/sysAdmin/agentunit_importSnModifyTempXls.action';
        } else if (type == 2) {
            titleDialog = "商户费率修改导入";
            url = '${ctx}/sysAdmin/agentunit_importMerRateModifyTempXls.action';
        } else if (type == 3) {
            titleDialog = "数据变更单导入";
        } else {
            titleDialog = "批量导入";
        }
        $('<div id="sysAdmin_change_tm_snImportDialog"/>').dialog({
            title: '<span style="color:#157FCC;">' + titleDialog + '</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/file/change_term_import.jsp?type=' + type,
            modal: true,
            onLoad: function () {
                if (status == -2) {
                    $('#sysAdmin_change_term_import_upload').form('load', row);
                }
            },
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#sysAdmin_change_term_import_upload').form('submit', {
                        url: url,
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
                        success: function (data) {
                            console.log($.parseJSON(data))
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#sysAdmin_change_tm_snImportDialog').dialog('destroy');
                                    $('#sysAdmin_change_tm_grid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                } else {
                                    $('#sysAdmin_change_tm_snImportDialog').dialog('destroy');
                                    $('#sysAdmin_change_tm_grid').datagrid('load');
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
                    $('#sysAdmin_change_tm_snImportDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    function sysAdmin_change_tm_exportFun() {
        var rows = $('#sysAdmin_change_tm_grid').datagrid('getChecked');
        var row = "";
        if (rows.length == 0) {
            $.messager.alert('提示', '请勾选数据!', 'error');
        } else {
            row = rows[0];
            $('#sysAdmin_change_tm_searchForm').form('submit', {
                url: '${ctx}/sysAdmin/agentunit_exportChangeTermMerXls.action?fid=' + row.FID + '&fType=' + row.FTYPE + '&costType=' + row.COSTTYPE,
            });
        }
    }

    function sysAdmin_change_tm_searchFun() {
        var start = $("#adate").datebox('getValue');
        var end = $("#zdate").datebox('getValue');
        if((!start&&end)||(!end&&start)){
            $.messager.alert('提示', "查询时间必须为时间段");
            return;
        }
        start = start.replace(/\-/gi, "/");
        end = end.replace(/\-/gi, "/");
        var startTime = new Date(start).getTime();
        var endTime = new Date(end).getTime();
        if ((endTime-startTime)>(1000*3600*24*30)){
            $.messager.alert('提示', "查询最长时间为30天");
            return;
        }
        if ((endTime-startTime)<0){
            $.messager.alert('提示', "起始日期需小于截止日期");
            return;
        }
        $('#sysAdmin_change_tm_grid').datagrid('load', serializeObject($('#sysAdmin_change_tm_searchForm')));
    }

    function sysAdmin_change_tm_cleanFun() {
        $('#sysAdmin_change_tm_searchForm input').val('');
    }

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
    <form id="sysAdmin_change_tm_searchForm" style="padding-left:5%" method="post">
      <input type="hidden" name="FID" id="00319_fid"/>
      <table class="tableForm">
        <tr>
          <th>&nbsp;&nbsp;文件名称</th>
          <td>&nbsp;&nbsp;<input name="cbUpLoad" style="width: 100px;"/></td>

          <th>&nbsp;&nbsp;提交时间</th>
          <td><input name="adate" id="adate" class="easyui-datebox" data-options="editable:false" style="width: 110px;"/>&nbsp;至&nbsp;
            <input name="zdate" id="zdate" class="easyui-datebox" data-options="editable:false" style="width: 110px;"/>
          </td>

          <th>&nbsp;&nbsp;修改类型</th>
          <td>
            <select name="baddr" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"
                    style="width:105px;">
              <option value="" selected="selected">查询所有</option>
              <option value="1">sn</option>
              <option value="2">商户</option>
            </select>
          </td>

          <th>&nbsp;&nbsp;处理状态</th>
          <td>
            <select name="status" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"
                    style="width:105px;">
              <option value="" selected="selected">all</option>
              <option value="-1">退回</option>
              <option value="0">待审核</option>
              <option value="1">审核通过</option>
              <option value="-2">失败</option>
            </select>
          </td>

          <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="sysAdmin_change_tm_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_change_tm_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_change_tm_grid"></table>
  </div>
</div>