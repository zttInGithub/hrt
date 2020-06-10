<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!--中心成本人工设置-->
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_unit00319h_treegrid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_query00319h.action',
            fit: true,
            fitColumns: false,
            border: false,
            nowrap: true,
            pagination: true,
            pageList: [10, 15],
            idField: 'HUCID',
            columns: [[{
                title : 'ID',
                field : 'HUCID',
                width : 0,
                hidden : true
            },{
                title: '运营中心名称',
                field: 'UN_NAME',
                width: 80,
                align: 'center'
            }, {
                title: '运营中心机构号',
                field: 'UNNO',
                width: 80,
                align: 'center'
            }, {
                title: '机器类型',
                field: 'MACHINE_TYPE',
                width: 80,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == "1") {
                        return "手刷";
                    } else if (value == "2") {
                        return "传统";
                    } else {
                        return "";
                    }
                }
            }, {
                title: '交易类型',
                field: 'TXN_TYPE',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == "1") {
                        return "秒到";
                    } else if (value == "2") {
                        return "理财";
                    } else if (value == "3") {
                        return "信用卡代还";
                    } else if (value == "4") {
                        return "云闪付";
                    } else if (value == "5") {
                        return "快捷";
                    } else if (value == "6") {
                        return "T0";
                    } else if (value == "7") {
                        return "T1";
                    } else {
                        return "";
                    }
                }
            }, {
                title: '产品类型',
                field: 'TXN_DETAIL',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == "1") {
                        return "0.6秒到";
                    } else if (value == "2") {
                        return "0.72秒到";
                    } else if (value == "3") {
                        return "理财";
                    } else if (value == "4") {
                        return "信用卡代还";
                    } else if (value == "5") {
                        return "云闪付";
                    } else if (value == "6") {
                        return "快捷VIP";
                    } else if (value == "7") {
                        return "快捷完美";
                    } else if (value == "8") {
                        return "扫码1000以下";
                    } else if (value == "9") {
                        return "扫码1000以上";
                    } else if (value == "10") {
                        return "银联二维码";
                    } else if (value == "11") {
                        return "传统-标准";
                    } else if (value == "12") {
                        return "传统-优惠";
                    } else if (value == "13") {
                        return "传统-减免";
                    } else if ('' != value) {
                        return "活动" + value;
                    } else {
                        return '';
                    }
                }
            }, {
                title: '刷卡成本(%)',
                field: 'DEBIT_RATE',
                width: 80,
                align: 'center'
            }, {
                title: '刷卡提现转账手续费',
                field: 'DEBIT_FEEAMT',
                width: 80,
                align: 'center'
            }, {
                title: '扫码1000以下（终端0.38）费率(%)',
                field: 'CREDIT_RATE',
                width: 80,
                align: 'center'
            }, {
                title: '扫码1000以下（终端0.38）转账费',
                field: 'CASH_COST',
                width: 80,
                align: 'center'
            }, {
                title: '提现手续费成本（%）',
                field: 'CASH_RATE',
                width: 80,
                align: 'center'
            },{
                title :'扫码1000以上（终端0.38）费率(%)',
                field :'WX_UPRATE',
                width : 80
            },{
                title :'扫码1000以上（终端0.38）转账费',
                field :'WX_UPCASH',
                width : 80
            },{
                title :'扫码1000以上（终端0.45）费率(%)',
                field :'WX_UPRATE1',
                width : 80
            },{
                title :'扫码1000以上（终端0.45）转账费',
                field :'WX_UPCASH1',
                width : 80
            },{
                title :'扫码1000以下（终端0.45）费率(%)',
                field :'ZFB_RATE',
                width : 80
            },{
                title :'扫码1000以下（终端0.45）转账费',
                field :'ZFB_CASH',
                width : 80
            },{
                title :'银联二维码费率(%)',
                field :'EWM_RATE',
                width : 80
            },{
                title :'银联二维码转账费',
                field :'EWM_CASH',
                width : 80
            },{
                title :'云闪付费率(%)',
                field :'YSF_RATE',
                width : 80
            },{
                title :'花呗费率(%)',
                field :'HB_RATE',
                width : 80
            },{
                title :'花呗转账费',
                field :'HB_CASH',
                width : 80
            },{
                title :'操作',
                field :'operation',
                width : 80,
                align : 'center',
                formatter : function(value,row,index) {
                    return	'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_00319h_deleteFun('+row.HUCID+')"/>';
                }
            }]],
            rownumbers: true,
            toolbar: [{
                id: 'btn_run',
                text: '成本导入',
                iconCls: 'icon-query-export',
                handler: function () {
                    sysAdmin_00319h_importFun();
                }
            }, {
                id: 'btn_run',
                text: '成本导出',
                iconCls: 'icon-query-export',
                handler: function () {
                    sysAdmin_00319h_exportFun();
                }
            }]
        });
    });

    // 删除
    function sysAdmin_00319h_deleteFun(hucid){
        // console.log(hucid)
        $.messager.confirm('确认','您确认要删除该条人工成本设置吗?',function(result) {
            if (result) {
                $.ajax({
                    url:"${ctx}/sysAdmin/agentunit_deleteRGSZHrtCost.action",
                    type:'post',
                    data:{"hucid":hucid},
                    dataType:'json',
                    success:function(data) {
                        if (data.success) {
                            $.messager.show({
                                title : '提示',
                                msg : data.msg
                            });
                            $('#sysAdmin_unit00319h_treegrid').datagrid('reload');
                        } else {
                            $.messager.alert('提示', data.msg);
                        }
                    },
                    error:function() {
                        $.messager.alert('提示', '删除成本设置出错！');
                    }
                });
            }
        });
    }

    // 成本导入
    function sysAdmin_00319h_importFun() {
        $('<div id="sysAdmin_00319h_uploadDialog"/>').dialog({
            title: '<span style="color:#157FCC;">人工成本设置导入</span>',
            width: 600,
            height: 200,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/00319h_01.jsp',
            modal: true,
            buttons: [{
                text: '导入',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#sysAdmin_00319h_01_upload_addForm').form('submit', {
                        url: '${ctx}/sysAdmin/agentunit_import00319hHrtCost.action',
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
                                    $('#sysAdmin_00319h_uploadDialog').dialog('destroy');
                                    $('#sysAdmin_unit00319h_treegrid').datagrid('load');
                                    $.messager.show({
                                        title: '提示',
                                        msg: resault.msg
                                    });
                                } else {
                                    $('#sysAdmin_00319h_uploadDialog').dialog('destroy');
                                    $.messager.show({
                                        title: '提示',
                                        msg: resault.msg
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
                    $('#sysAdmin_00319h_uploadDialog').dialog('destroy');
                    $('#sysAdmin_unit00319h_treegrid').datagrid('unselectAll');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
                $('#sysAdmin_unit00319h_treegrid').datagrid('unselectAll');
            }
        });
    }

    // 成本导出
    function sysAdmin_00319h_exportFun() {
        $('#bill_agentunit00319h_searchForm').form('submit', {
            url: '${ctx}/sysAdmin/agentunit_export00319h.action'
        });
    }

    //表单查询
    function bill_agentunit00319h_searchFun() {
        $('#sysAdmin_unit00319h_treegrid').datagrid('load',
            serializeObject($('#bill_agentunit00319h_searchForm')));
    }

    //清除表单内容
    function bill_agentunit00319h_cleanFun() {
        $('#bill_agentunit00319h_searchForm input').val('');
    }
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false"
       style="height:50px; overflow: hidden; padding-top:5px;">
    <form id="bill_agentunit00319h_searchForm" style="padding-left:5%"
          method="post">
      <table class="tableForm">
        <tr>
          <th>运营中心机构号:</th>
          <td><input name="unno" style="width: 100px;"/>
          </td>
          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-search',plain:true"
               onclick="bill_agentunit00319h_searchFun();">
              查询
            </a>
            &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true"
               onclick="bill_agentunit00319h_cleanFun();">
              清空
            </a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false"
       style="overflow: hidden;">
    <table id="sysAdmin_unit00319h_treegrid"></table>
  </div>
</div>