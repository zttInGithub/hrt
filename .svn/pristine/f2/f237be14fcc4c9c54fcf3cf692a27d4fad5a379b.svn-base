<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_unit_total_terminal_grid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_queryTotalTerminalGrid.action',
            fit: true,
            fitColumns: false,
            border: false,
            nowrap: true,
            pagination: true,
            pageList: [10, 15],
            // idField: 'BTTID',
            columns: [[/*{
                title: 'ID',
                field: 'BTTID',
                width: 200,
                hidden: true
            }, {
                title: '日期',
                field: 'TXNDAY',
                width: 200,
                align: 'center',
                formatter : function(value,row,index) {
                    if(value!=null){
                        return value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6,8);
                    }
                }
            },*/ {
                title: '活动',
                field: 'REBATETYPE',
                width: 200,
                align: 'center'
            }, {
                title: '设备总数',
                field: 'ALLCOUNT',
                width: 200,
                align: 'center'
            }, {
                title: '激活数',
                field: 'ALLCOUNT1',
                width: 200,
                align: 'center'
            }, {
                title: '使用数',
                field: 'ALLCOUNT2',
                width: 200,
                align: 'center'
            }]]
        });
    });

    //表单查询
    function bill_agentunit_total_terminal_searchFun() {
        var txnDay = $('#total_termianl_adate').datebox('getValue');
        var txnDay1 = $('#total_termianl_zdate').datebox('getValue');
        if(txnDay!=null && txnDay!='' && txnDay1!=null && txnDay1!=''){
            $('#sysAdmin_unit_total_terminal_grid').datagrid('load',
                serializeObject($('#bill_agentunit_total_terminal_searchForm')));
        }else{
            $.messager.alert('提示','查询起始时间不能为空！');
        }
    }

    //清除表单内容
    function bill_agentunit_total_terminal_cleanFun() {
        $('#bill_agentunit_total_terminal_searchForm input').val('');
    }
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false"
       style="height:50px; overflow: hidden; padding-top:5px;">
    <form id="bill_agentunit_total_terminal_searchForm" style="padding-left:5%"
          method="post">
      <table class="tableForm">
        <tr>
          <th>时间</th>
          <td><input name="adate" id="total_termianl_adate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
            <input name="zdate" id="total_termianl_zdate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
          </td>
          <th>&nbsp;&nbsp;&nbsp;&nbsp;活动类型</th>
          <td><input name="maintainType" style="width: 200px;" /></td>
          
          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-search',plain:true"
               onclick="bill_agentunit_total_terminal_searchFun();">
              查询
            </a>
            &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true"
               onclick="bill_agentunit_total_terminal_cleanFun();">
              清空
            </a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false"
       style="overflow: hidden;">
    <table id="sysAdmin_unit_total_terminal_grid"></table>
  </div>
</div>