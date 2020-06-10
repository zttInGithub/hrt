<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_enum_grid').datagrid({
            url: '${ctx}/sysAdmin/enumAction_getEnumGridList.action',
            fit: true,
            fitColumns: true,
            border: false,
            nowrap: true,
            pagination: true,
            pagePosition: 'bottom',
            pageSize: 10,
            pageList: [10, 15],
            idField: 'enumId',
            sortName: 'enumTable',
            sortOrder: 'desc',
            columns: [[{
                title: 'enumId',
                field: 'enumId',
                width: 100,
                hidden: true
            }, {
                title: '枚举类型',
                field: 'enumType',
                width: 100,
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return "系统通用(SYS)";
                    } else {
                        return "单表定义";
                    }
                }
            }, {
                title: '枚举表名',
                field: 'enumTable',
                width: 100
            }, {
                title: '枚举列名',
                field: 'enumColumn',
                width: 100
            }, {
                title: '枚举编码',
                field: 'enumCode',
                width: 100
            }, {
                title: '枚举描述',
                field: 'enumDesc',
                width: 100
            }, {
                title: '枚举值名称',
                field: 'enumName',
                width: 100
            }, {
                title: '枚举值(数字)',
                field: 'enumValue',
                width: 100
            }, {
                title: '枚举信息(字符串)',
                field: 'enumInfo',
                width: 100
            }, {
                title: '枚举状态',
                field: 'enumStatus',
                width: 100,
                formatter: function (value, row, index) {
                    if (1 == value) {
                        return "开启";
                    } else {
                        return "关闭";
                    }
                }
            }, {
                title: '操作',
                field: 'operation',
                width: 200,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_enum_deleteFun(' + row.enumId + ')"/>&nbsp;&nbsp;' +
                        '<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_enum_editStatusFun(' + row.enumId + ',0)"/>&nbsp;&nbsp;' +
                        '<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_enum_editStatusFun(' + row.enumId + ',1)"/>&nbsp;&nbsp;' +
                        '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_enum_editFun(' + index + ')"/>&nbsp;&nbsp;';
                }
            }]],
            toolbar: [{
                id: 'btn_add',
                text: '添加枚举定义',
                iconCls: 'icon-add',
                handler: function () {
                    sysAdmin_enum_add_addFun();
                }
            }]
        });
    });

    function sysAdmin_enum_editFun(index) {
        $('<div id="sysAdmin_enum_edit"/>').dialog({
            title: '<span style="color:#157FCC;">修改枚举定义</span>',
            width: 400,
            height: 400,
            closed: false,
            href: '${ctx}/frame/sysadmin/user/enum_modify.jsp',
            modal: true,
            onLoad: function () {
                var rows = $('#sysAdmin_enum_grid').datagrid('getRows');
                var row = rows[index];
                $('#sysAdmin_enum_modifyForm').form('load', row);
            },
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var dia = $('#sysAdmin_enum_edit');
                    $('#sysAdmin_enum_modifyForm').form('submit', {
                        url: '${ctx}/sysAdmin/enumAction_modifyEnumInfo.action',
                        success: function (data) {
                            var res = $.parseJSON(data);
                            if (res.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (res.success) {
                                    dia.dialog('destroy');
                                    $('#sysAdmin_enum_grid').datagrid('reload');
                                    $.messager.show({
                                        title: '提示',
                                        msg: res.msg
                                    });
                                } else {
                                    dia.dialog('destroy');
                                    $('#sysAdmin_enum_grid').datagrid('unselectAll');
                                    $.messager.alert('提示', res.msg);
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#sysAdmin_enum_edit').dialog('destroy');
                    $('#sysAdmin_enum_grid').datagrid('unselectAll');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
                $('#sysAdmin_enum_grid').datagrid('unselectAll');
            }
        });
    }

    function sysAdmin_enum_editStatusFun(enumId, status) {
        var titleName='';
        if(status==1){
            titleName='启用';
        }else{
            titleName='禁用';
        }
        $.messager.confirm('确认', '您确认要'+titleName+'该枚举信息吗?', function (result) {
            if (result) {
                $.ajax({
                    url: "${ctx}/sysAdmin/enumAction_modifyEnumStatus.action",
                    type: 'post',
                    data: {"enumId": enumId, "enumStatus": status},
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({
                                title: '提示',
                                msg: data.msg
                            });
                            $('#sysAdmin_enum_grid').datagrid('reload');
                        } else {
                            $.messager.alert('提示', data.msg);
                            $('#sysAdmin_enum_grid').datagrid('unselectAll');
                        }
                    },
                    error: function () {
                        $.messager.alert('提示', '停用用户出错！');
                        $('#sysAdmin_enum_grid').datagrid('unselectAll');
                    }
                });
            } else {
                $('#sysAdmin_enum_grid').datagrid('unselectAll');
            }
        });
    }

    function sysAdmin_enum_deleteFun(enumId) {
        $.messager.confirm('确认', '您确认要删除所选记录吗?', function (r) {
            if (r) {
                $.ajax({
                    url: "${ctx}/sysAdmin/enumAction_removeEnumInfo.action",
                    data: {"enumId": enumId},
                    dataType: 'json',
                    success: function (data, textStatus) {
                        if (data.success) {
                            $.messager.show({
                                title: '提示',
                                msg: data.msg
                            });
                            $('#sysAdmin_enum_grid').datagrid('reload');
                        } else {
                            $.messager.alert('提示', data.msg);
                            $('#sysAdmin_enum_grid').datagrid('unselectAll');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $.messager.alert('提示', '删除记录出错！');
                        $('#sysAdmin_enum_grid').datagrid('unselectAll');
                    }
                });
            } else {
                $('#sysAdmin_enum_grid').datagrid('unselectAll');
            }
        });
    }

    function sysAdmin_enum_add_addFun() {
        $('<div id="sysAdmin_enum_addDialog"/>').dialog({
            title: '<span style="color:#157FCC;">添加枚举定义</span>',
            width: 400,
            height: 400,
            closed: false,
            href: '${ctx}/frame/sysadmin/user/enum_add.jsp',
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#sysAdmin_enum_add_addFrom').form('submit', {
                        url: '${ctx}/sysAdmin/enumAction_saveEnumInfo.action',
                        success: function (data) {
                            var res = $.parseJSON(data);
                            if (res.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (res.success) {
                                    $('#sysAdmin_enum_grid').datagrid('unselectAll');
                                    $('#sysAdmin_enum_grid').datagrid('reload');
                                    $('#sysAdmin_enum_addDialog').dialog('destroy');
                                    $.messager.show({
                                        title: '提示',
                                        msg: res.msg
                                    });
                                } else {
                                    $.messager.alert('提示', res.msg);
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#sysAdmin_enum_addDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    //表单查询
    function sysAdmin_enum_grid_searchFun() {
        $('#sysAdmin_enum_grid').datagrid('load', serializeObject($('#sysAdmin_enum_grid_searchForm')));
    }

    //清除表单内容
    function sysAdmin_enum_grid_cleanFun() {
        $('#sysAdmin_enum_grid_searchForm input').val('');
    }

</script>

<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
    <form id="sysAdmin_enum_grid_searchForm" style="padding-left:10%">
      <table class="tableForm">
        <tr>
          <th>枚举表名</th>
          <td><input name="enumTable" style="width: 150px;"/></td>
          <th>&nbsp;&nbsp;&nbsp;&nbsp;枚举列名</th>
          <td><input name="enumColumn" style="width: 150px;"/></td>
          <th>&nbsp;&nbsp;&nbsp;&nbsp;枚举编码</th>
          <td><input name="enumCode" style="width: 150px;"/></td>
          <th>&nbsp;&nbsp;&nbsp;&nbsp;状态</th>
          <td>
            <select name="enumStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"
                    style="width:105px;">
              <option value="" selected="selected">查询所有</option>
              <option value="1">开启</option>
              <option value="0">关闭</option>
            </select>
          </td>
          <td colspan="4" style="text-align: center;">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="sysAdmin_enum_grid_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_enum_grid_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_enum_grid"></table>
  </div>
</div>



