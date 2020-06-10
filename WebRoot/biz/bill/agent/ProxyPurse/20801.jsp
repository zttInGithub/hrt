<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--报单系统-活动钱包-钱包调整记录--%>
<script type="text/javascript">
    $(function () {
        $('#20801_datagrid').datagrid({
            url: '${ctx}/sysAdmin/ProxyPurse_queryUnnoAdjtxn.action',
            fit: true,
            frozen: true,
            striped: true,
            fitColumns: false,
            rownumbers: true,
            border: false,
            nowrap: true,
            singleSelect: true,
            pagination: true,
            pagePosition: 'bottom',
            pageSize: 10,
            pageList: [10, 15],
            idField: 'PUAID',
            sortName: 'PUAID',
            sortOrder: 'desc',
            columns: [[{
                title: 'PUAID',
                field: 'PUAID',
                width: 100,
                hidden: true,
                // checkbox: true
            }, {
                title: '代理机构号',
                field: 'UNNO',
                width: 100
            }, {
                title: '代理名称',
                field: 'UN_NAME',
                width: 130
            }, {
                title: '代理级别',
                field: 'UNNOLVL',
                width: 100
            }, {
                title: '上级代理机构号',
                field: 'UPPER_UNIT',
                width: 100
            }, {
                title: '归属一代机构号',
                field: 'FIRSTUNNO',
                width: 100
            }, {
                title: '归属运营中心机构号',
                field: 'YUNYING',
                width: 100
            }, {
                title: '调整金额',
                field: 'FEEAMT',
                width: 100
            }, {
                title: '调整日期',
                field: 'SETTLEDAY',
                width: 130
            }, {
                title: '调整原因(备注)',
                field: 'FEENOTE',
                width: 100
            }/*, {
                title: '导入文件名称',
                field: 'REMARKS',
                width: 150
            }*/,{
            	title: '钱包类型',
                field: 'WALLETTYPE',
                width: 100,
                formatter : function(value,row,index) {
					if(value==1){
						return '返现';
					}else{
						return '分润';
					}
				}
            }]],
            toolbar: [{
                text: '图标说明：'
            }, {
                id: 'btn_add',
                text: '添加',
                iconCls: 'icon-add',
                handler: function () {
                    unnoAdj_addFun();
                }
            }, {
                id: 'btn_add',
                text: '批量调整',
                iconCls: 'icon-xls',
                handler: function () {
                    importUnnoAdjtxn();
                }
            }, {
                id: 'btn_add',
                text: '导出',
                iconCls: 'icon-xls',
                handler: function () {
                    reportUnnoAdj();
                }
            }]
        });
    });

    function searchFun_20801() {
        $('#20801_datagrid').datagrid('load', serializeObject($('#searchForm_20801')));
    }

    function cleanFun_20801() {
        $('#searchForm_20801 input').val('');
    }

    function reportUnnoAdj() {
        $('#searchForm_20801').form('submit', {
            url: '${ctx}/sysAdmin/ProxyPurse_reportUnnoAdj.action'
        });
    }

    function unnoAdj_addFun() {
        $('<div id="DebitUnnoAdj_add"/>').dialog({
            title: '添加',
            width: 430,
            height: 270,
            closed: false,
            href: '${ctx}/biz/bill/agent/ProxyPurse/20801Add.jsp',
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var dia = $('#DebitUnnoAdj_add');
                    $('#DebitUnnoAdj_addForm').form('submit', {
                        url: '${ctx}/sysAdmin/ProxyPurse_addUnnoAdj.action',
                        success: function (data) {
                            var res = $.parseJSON(data);
                            if (res.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (res.success) {
                                    $('#20801_datagrid').datagrid('unselectAll');
                                    $('#20801_datagrid').datagrid('reload');
                                    dia.dialog('destroy');
                                    $.messager.show({
                                        title: '提示',
                                        msg: res.msg
                                    });
                                } else {
                                    //dia.dialog('destroy');
                                    $.messager.alert('提示', res.msg);
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                handler: function () {
                    $('#DebitUnnoAdj_add').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

    function importUnnoAdjtxn() {
        $('<div id="UnnoAdj_upload"/>').dialog({
            title: '批量调整',
            width: 440,
            height: 200,
            closed: false,
            href: '${ctx}/biz/bill/agent/ProxyPurse/20801uplod.jsp',
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                disabled: false,
                id: 'unnoAdj',
                handler: function () {
                    $('#unnoAdj').linkbutton({disabled: true});
                    var dia = $('#UnnoAdj_upload');
                    $('#importUnnoAdjtxn_uploadForm').form('submit', {
                        url: '${ctx}/sysAdmin/ProxyPurse_importUnnoAdj.action',
                        onSubmit: function () {
                            var contact = document.getElementById('uploadUnnoAdj').value;
                            if (contact == "") {
                                $.messager.show({
                                    title: '提示',
                                    msg: '请选择要上传的文件!',
                                    timeout: 5000,
                                    showType: 'slide'
                                });
                                return false;
                            }
                            if (contact != "") {
                                var l = contact.split(".");
                                if (l[1] != "xls") {
                                    $.messager.show({
                                        title: '提示',
                                        msg: '请选择后缀名为.xls的文件!',
                                        timeout: 5000,
                                        showType: 'slide'
                                    });
                                    return false;
                                }
                                if (l[1] == "xls") {
                                    document.getElementById("fileUnnoAdj").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                                    return true;
                                }
                                if (l[1] == "xlsx") {
                                    $.messager.show({
                                        title: '提示',
                                        msg: '请将格式转换为：.xls 为后缀名!',
                                        timeout: 5000,
                                        showType: 'slide'
                                    });
                                    return false;
                                }
                            }
                        },
                        success: function (data) {
                            var res = $.parseJSON(data);
                            if (res.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (res.success) {
                                    dia.dialog('destroy');
                                    $('#20801_datagrid').datagrid('reload');
                                    $.messager.show({
                                        title: '提示',
                                        msg: res.msg

                                    });
                                } else {
                                    dia.dialog('destroy');
                                    $('#20801_datagrid').datagrid('reload');
                                    $.messager.show({
                                        title: '提示',
                                        msg: res.msg
                                    });
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                handler: function () {
                    $('#UnnoAdj_upload').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }

</script>

<div class="easyui-layout" data-options="fit:true, border:false">

  <div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
    <form id="searchForm_20801" style="padding-left:10%">
      <table class="tableForm">
        <tr>
          <th>代理机构号</th>
          <td><input name="UNNO" style="width: 140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <th>调整日期</th>
          <td><input name="SETTLEDAY" class="easyui-datebox" style="width: 100px;"/>&nbsp;-
            <input name="CDATE" class="easyui-datebox" style="width: 100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
          <th>钱包类型：</th>
		  <td><select name="walletType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
		    		<option value="">全部</option>
		    		<option value="1">返现</option>
		    		<option value="0">分润</option>
		      </select>
		  </td>
          <td>
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="searchFun_20801();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="cleanFun_20801();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="20801_datagrid"></table>
  </div>
</div>