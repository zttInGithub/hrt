<%@ page language="java" import="com.hrt.frame.entity.pagebean.UserBean"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  Object userSession = request.getSession().getAttribute("user");
  UserBean user = ((UserBean) userSession);
%>
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_sales_unno_grid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_salesUnnoListGrid.action',
            fit: true,
            fitColumns: false,
            border: false,
            nowrap: true,
            pagination: true,
            singleSelect: true,
            pageList: [10, 15],
            idField: 'BUSID',
            onBeforeLoad: function () {
            },
            onLoadSuccess: function () {
            },
            columns: [[{
                title: '代理类别',
                field: 'TYPE',
                width: 100,
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return '非直营';
                    } else if (value == 1) {
                        return '自营';
                    }
                }
            }, {
                title: '代理级别',
                field: 'UN_LVL',
                width: 100
            }, {
                title: '机构号',
                field: 'UNNO',
                width: 160
            }, {
                title: '机构名称',
                field: 'UN_NAME',
                width: 160
            }, {
                title: '归属销售',
                field: 'SALENAME',
                width: 130
            }
            ]], toolbar: [{
                id: 'btn_export1',
                text: '导出',
                iconCls: 'icon-query-export',
                handler: function () {
                    sysAdmin_sales_unno_exportFun();
                }
            }, {
                id: 'btn_export2',
                text: '批量添加',
                iconCls: 'icon-add',
                handler: function () {
                    sysAdmin_sales_unno_batchAddFun();
                }
            }
            ]
        });
    });

    function sysAdmin_sales_unno_batchAddFun() {
        $('<div id="sysAdmin_sales_unno_ImportDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量添加</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/sales/sale_unno_import.jsp',
            modal: true,
            onLoad: function () {
            },
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#sysAdmin_sale_unno_import_upload').form('submit', {
                        url: '${ctx}/sysAdmin/agentunit_importSaleUnnoTempXls.action',
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
                                    $('#sysAdmin_sales_unno_ImportDialog').dialog('destroy');
                                    $('#sysAdmin_sales_unno_grid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                } else {
                                    $('#sysAdmin_sales_unno_ImportDialog').dialog('destroy');
                                    $('#sysAdmin_sales_unno_grid').datagrid('load');
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
                    $('#sysAdmin_sales_unno_ImportDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }


    function sysAdmin_sales_unno_exportFun() {
        $('#sysAdmin_sales_unno_searchForm').form('submit', {
            url: '${ctx}/sysAdmin/agentunit_exportSalesUnnoList.action',
        });
    }

    function sysAdmin_sales_unno_searchFun() {
        $('#sysAdmin_sales_unno_grid').datagrid('load', serializeObject($('#sysAdmin_sales_unno_searchForm')));
    }

    function sysAdmin_sales_unno_cleanFun() {
        $('#sysAdmin_sales_unno_searchForm input').val('');
    }

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
    <form id="sysAdmin_sales_unno_searchForm" style="padding-left:5%" method="post">
      <input type="hidden" name="FID" id="00319_fid"/>
      <table class="tableForm">
        <tr>
          <th>&nbsp;&nbsp;中心/自营代理机构号</th>
          <td>&nbsp;&nbsp;<input name="unno" style="width: 100px;"/></td>
          <th>&nbsp;&nbsp;中心/归属销售</th>
          <td>&nbsp;&nbsp;<input name="agentName" style="width: 100px;"/></td>

          <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="sysAdmin_sales_unno_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_sales_unno_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_sales_unno_grid"></table>
  </div>
</div>