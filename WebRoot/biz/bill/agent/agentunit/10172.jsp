<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- 定制活动返现数据报表 -->
<script type="text/javascript">
    $(function() {
        $('#sysAdmin_10172_datagrid').datagrid({
            url: '${ctx}/sysAdmin/pgCashbackSpecialAction_queryCustomizeActivityCashbackData.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '一代机构',
                    field: 'YIDAI',
                    width: 80
                },{
                    title: '一代机构名称',
                    field: 'YIDAINAME',
                    width: 80
                },{
                    title: '中心机构号',
                    field: 'ZHONGXIN',
                    width: 80
                },{
                    title: '中心机构名称',
                    field: 'ZHONGXINNAME',
                    width: 80
                },{
                    title: '最终机构',
                    field: 'ZUIZHONG',
                    width: 80
                },{
                    title: '最终机构名称',
                    field: 'ZUIZHONGNAME',
                    width: 80
                },{
                    title: '商户编号',
                    field: 'MID',
                    width: 80
                },{
                    title: '商户名称',
                    field: 'RNAME',
                    width: 80
                },{
                    title: '手机号',
                    field: 'CONTACTPHONE',
                    width: 80,
                    formatter : function(value,row,index) {
    					if(value == undefined || value == null || value == ''|| value.split('').length<11){
    						return "";
    					}
    					return value.substr(0,3)+"****"+value.substr(7,value.split('').length)
    				}
                },{
                    title: '入网日期',
                    field: 'JOINCONFIRMDATE',
                    width: 80
                },{
                	title: 'SN',
                    field: 'REMARKS1',
                    width: 80
                },{
                    title: '交易金额',
                    field: 'SUMMONEY',
                    width: 80
                },{
                    title: '返利类型',
                    field: 'REBATETYPE',
                    width: 80
                },{
                    title: '返利金额',
                    field: 'RETURNMONEY',
                    width: 80
                },{
                    title: '返利日期',
                    field: 'RETURNDAY',
                    width: 80
                },{
                    title: '返利次数',
                    field: 'RETURNTIME',
                    width: 80
                }
                ]
            ],toolbar : [ {
                id : 'btn_run',
                text : '定制活动返现数据报表导出',
                iconCls : 'icon-query-export',
                handler : function() {
                    sysAdmin_10172_exportFun();
                }
            }]
        });

    });

    function sysAdmin_10172_exportFun() {
        $('#bill_agentunit_10172purseForm').form('submit', {
            url : '${ctx}/sysAdmin/pgCashbackSpecialAction_exportCustomizeActivityCashbackData.action'
        });
    }

    function sysAdmin_10172_queryCash() {
    	var txnDay = $('#txnDay_10702').datebox('getValue');
		var txnDay1 = $('#txnDay1_10702').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
      			$('#sysAdmin_10172_datagrid').datagrid('load', serializeObject($('#bill_agentunit_10172purseForm')));
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
    }

    function bill_10172_cleanFun() {
        $('#bill_agentunit_10172purseForm input').val('');
        $('#bill_agentunit_10172purseForm select').val('全部');
    }
    
    $(function() {
		$('#rebatetype_10172').combogrid({
			url : '${ctx}/sysAdmin/pgCashbackSpecialAction_queryCustomizeActivityCashbackData_selectRebateType.action',
			idField:'VALUESTRING',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'活动类型',width:150}			
			]]
		});
	});
    
</script>
<style type="text/css">
    .pdl20 {
        padding-left: 20px;
    }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
        <form id="bill_agentunit_10172purseForm" style="padding:0px;">
            <table class="tableForm pdl20">
                <%--一代机构号：一代机构号精确查询（不设置默认为全部）--%>
                <%--一代机构名称：一代机构名称精确查询（不设置默认为全部）--%>
                <%--返利类型：ALL/93（不设置默认为全部）--%>
                <%--返现日期：日-日（不可跨月查询&最长一个自然月区间&必设置字段）--%>
                <tr>
                    <th style="">一代机构:</th>
                    <td><input name="yidai" style="width: 135px;" /></td>
                    <th>一代机构名称:</th>
                    <td><input name="yidaiName" style="width: 135px;" /></td>
                    <th>&nbsp;&nbsp;查询日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_10702" name="txnDay"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_10702" name="txnDay1"  style="width: 162px;"/></td>
                    <th>返利类型:</th>
                    <td><select name="rebatetype" id="rebatetype_10172" class="easyui-combogrid" style="width:135px;"></select></td>
                    <!-- <td><select name="rebatetype" style="width: 135px;" >
                        <option value="" selected="selected">All</option>
                        <option value="93">活动93</option>
                        <option value="94">活动94</option>
                    </select>
                    </td> -->
                    <td>
                        <a href="javascript:void(0);"
                             class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
                             onclick="sysAdmin_10172_queryCash();">查询</a> &nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-cancel',plain:true" onclick="bill_10172_cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
        <table id="sysAdmin_10172_datagrid"></table>
    </div>
</div>
