<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 运营中心激活情况明细表 -->
<script type="text/javascript">
    //初始化datagrid
    $(function () {
        $('#sysAdmin_10845_sum_datagrid').datagrid({
            url: '${ctx}/sysAdmin/merchant_listSytRebateTotal.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            remoteSort: true,
            idField: 'PROBID',
            // 机构名称 	新增出库已激活数 	当月出库数	当月激活数	累计激活数	累计出库数	激活率
            columns: [[{
                title: '机构',
                field: 'UNNO',
                width: 100
            }, {
                title: '机构名称',
                field: 'UN_NAME',
                width: 100
            }, {
                title: '新增出库已激活数',
                field: 'ADD_OUT_ACT',
                width: 100
            }, {
                title: '出库数',
                field: 'OUT_COUNT',
                width: 100
            }, {
                title: '激活数',
                field: 'ACT_COUNT',
                width: 100
            }, {
                title: '累计激活数',
                field: 'ACT_TOTAL',
                width: 100
            }, {
                title: '累计出库数',
                field: 'OUT_TOTAL',
                width: 100
            }, {
                title: '激活率',
                field: 'ACT_RATE',
                width: 100
            }]],
            toolbar:[ {
                text:'导出',
                iconCls:'icon-query-export',
                handler:function(){
                    sysAdmin_10845_sum_exportFun();
                }
            }]
        });
    });

    //导出
    function sysAdmin_10845_sum_exportFun() {
        $('#sysAdmin_10845_sum').form('submit',{
            url:'${ctx}/sysAdmin/merchant_exportListSytRebateTotal.action'
        });
    }

    function sysAdmin_10845_sum_searchFun() {
        var txnDay = $('#createDateStart_10845').datebox('getValue');
        var txnDay1= $('#createDateEnd_10845').datebox('getValue');
        if((txnDay!=""&&txnDay1=="") || (txnDay==""&&txnDay1!="")){
            $.messager.alert('提示', "开始日期和截止日期不可只填其一");
        }else{
            $('#sysAdmin_10845_sum_datagrid').datagrid('load', serializeObject($('#sysAdmin_10845_sum')));
        }
    }

    function sysAdmin_10845_sum_cleanFun() {
        $('#sysAdmin_10845_sum input').val('');
    }
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:40px; overflow: hidden; padding-top:10px;">
    <form id="sysAdmin_10845_sum" style="padding-left:10px">
      <table class="tableForm">
        <tr>
          <th>日期</th>
          <td><input name="createDateStart" id="createDateStart_10845" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
            <input name="createDateEnd" id="createDateEnd_10845" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
          </td>
          <td style="text-align: center;">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="sysAdmin_10845_sum_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_10845_sum_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_10845_sum_datagrid" style="overflow: hidden;"></table>
  </div>
</div>

