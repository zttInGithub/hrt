<%@ page language="java" import="com.hrt.frame.entity.pagebean.UserBean"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  Object userSession = request.getSession().getAttribute("user");
  UserBean user = ((UserBean) userSession);
%>
<script type="text/javascript">
    $(function () {
        $('#sysAdmin_sales_act_sum_grid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_salesActTermSummaryGrid.action',
            fit: true,
            fitColumns: false,
            border: false,
            nowrap: true,
            pagination: true,
            singleSelect: true,
            pageList: [10, 15],
            idField: 'PP',
            onBeforeLoad: function () {
            },
            onLoadSuccess: function () {
            },
            // 中心机构号/直营一代机构号	中心机构名称/直营一代机构号	设备总量	未绑定设备量	已绑定设备量	激活设备量	活动类型	归属销售
            columns: [[{
                title: '中心机构号/直营一代机构号',
                field: 'PP',
                width: 200
            }, {
                title: '中心机构名称/直营一代机构号',
                field: 'UN_NAME',
                width: 200
            }, {
                title: '设备总量',
                field: 'ALLCOUNT',
                width: 130
            }, {
                title: '未绑定设备量',
                field: 'NOCOUNT',
                width: 100
            }, {
                title: '已绑定设备量',
                field: 'USECOUNT',
                width: 100
            }, {
                title: '激活设备量',
                field: 'ACTCOUNT',
                width: 100
            }, {
                title: '活动类型',
                field: 'REBATETYPE',
                width: 100
            }, {
                title: '归属销售',
                field: 'SALENAME',
                width: 100,
            }
            ]], toolbar: [{
                id: 'btn_export1',
                text: '导出',
                iconCls: 'icon-query-export',
                handler: function () {
                    sysAdmin_sales_act_sum_exportFun();
                }
            }
            ]
        });
    });


    function sysAdmin_sales_act_sum_exportFun() {
          $('#sysAdmin_sales_act_sum_searchForm').form('submit', {
              url: '${ctx}/sysAdmin/agentunit_exportSalesActTermSummary',
          });
    }

    function sysAdmin_sales_act_sum_searchFun() {
        var start = $("#adate").datebox('getValue');
        var end = $("#zdate").datebox('getValue');
        if(start=='' || end==''){
            $.messager.alert('提示', "请输入激活日期区间！！");
            return;
        }
        if((!start&&end)||(!end&&start)){
            $.messager.alert('提示', "查询时间必须为时间段");
            return;
        }
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
        $('#sysAdmin_sales_act_sum_grid').datagrid('load', serializeObject($('#sysAdmin_sales_act_sum_searchForm')));
    }

    function sysAdmin_sales_act_sum_cleanFun() {
        $('#sysAdmin_sales_act_sum_searchForm input').val('');
    }

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:20px;">
    <form id="sysAdmin_sales_act_sum_searchForm" style="padding-left:5%" method="post">
      <input type="hidden" name="FID" id="00319_fid"/>
      <table class="tableForm">
        <tr>
          <th>&nbsp;&nbsp;活动类型</th>
          <td>&nbsp;&nbsp;<input name="accType" style="width: 100px;"/></td>

          <th>&nbsp;&nbsp;中心机构号/直营一代机构号</th>
          <td>&nbsp;&nbsp;<input name="legalNum" style="width: 100px;"/></td>
        </tr>
        <tr>
          <th>&nbsp;&nbsp;归属销售</th>
          <td>&nbsp;&nbsp;<input name="accTypeName" style="width: 100px;"/></td>
          <th>&nbsp;&nbsp;激活日期</th>
          <td><input name="adate" id="adate" class="easyui-datebox" data-options="editable:false" style="width: 110px;"/>&nbsp;至&nbsp;
            <input name="zdate" id="zdate" class="easyui-datebox" data-options="editable:false" style="width: 110px;"/>
          </td>
          <td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="sysAdmin_sales_act_sum_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_sales_act_sum_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_sales_act_sum_grid"></table>
  </div>
</div>