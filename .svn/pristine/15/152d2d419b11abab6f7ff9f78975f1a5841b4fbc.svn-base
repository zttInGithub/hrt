<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--报单系统-活动钱包-钱包实时余额--%>
<script type="text/javascript">
    $(function () {
        $('#unno_purse_balance_grid').datagrid({
            url: '${ctx}/sysAdmin/ProxyPurse_queryUnnoPurseBalanceGrid.action',
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
            idField: 'PUPID',
            sortName: 'PUPID',
            sortOrder: 'desc',
            columns: [[{
                title: 'PUPID',
                field: 'PUPID',
                width: 100,
                // checkbox: true
            }, {
                title: '代理机构号',
                field: 'UNNO',
                width: 100
            }, {
                title: '代理名称',
                field: 'UN_NAME',
                width: 100
            }, {
                title: '代理级别',
                field: 'UN_LVL',
                width: 130
            }, {
                title: '上级代理机构号',
                field: 'UPPER_UNIT',
                width: 130
            }, {
                title: '归属一代机构号',
                field: 'YIDAI',
                width: 100
            }, {
                title: '归属运营中心机构号',
                field: 'YUNYING',
                width: 100
            }, {
                title: '实时余额',
                field: 'BALANCE',
                width: 100
            }, {
                title: '钱包类型',
                field: 'WALLETTYPE',
                width: 100,
                formatter : function(value,row,index) {
                    if(value==1){
                        return "返现";
                    }else{
                        return "分润";
                    }
                }
            }]],
            toolbar: [{
                text: '图标说明：'
            }, {
                id: 'btn_add',
                text: '导出',
                iconCls: 'icon-xls',
                handler: function () {
                    export_unno_purse_balance();
                }
            }]
        });
    });

    function searchFun_unno_purse_balance() {
        $('#unno_purse_balance_grid').datagrid('load', serializeObject($('#searchForm_unno_purse_balance')));
    }

    function cleanFun_unno_purse_balance() {
        $('#searchForm_unno_purse_balance input').val('');
    }

    function export_unno_purse_balance() {
        $('#searchForm_unno_purse_balance').form('submit', {
            url: '${ctx}/sysAdmin/ProxyPurse_exportUnnoPurseBalanceLists.action'
        });
    }
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
    <form id="searchForm_unno_purse_balance" style="padding-left:10%">
      <table class="tableForm">
        <tr>
          <th>机构编号</th>
          <td><input name="UNNO" style="width: 140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <th>钱包类型：</th>
		  <td><select name="walletType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
		  			<option value="">全部</option>
		  			<option value="1">返现</option>
		    		<option value="0">分润</option>
		      </select>
		  </td>
          <td>
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="searchFun_unno_purse_balance();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="cleanFun_unno_purse_balance();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="unno_purse_balance_grid"></table>
  </div>
</div>