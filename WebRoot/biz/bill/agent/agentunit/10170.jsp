<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- 活动扣款月度报表 -->
<script type="text/javascript">
    $(function() {
        $('#sysAdmin_10170_datagrid').datagrid({
            url: '${ctx}/sysAdmin/checkDeductionAction_queryCheckDeductionListInfo10170.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '归属一代机构',
                    field: 'YIDAI',
                    width: 80
                },{
                    title: '归属一代机构名称',
                    field: 'YIDAINAME',
                    width: 80
                },{
                    title: '归属下级机构号',
                    field: 'ERDAI',
                    width: 80
                },{
                    title: '归属下级机构名称',
                    field: 'ERDAINAME',
                    width: 80
                }/* ,{
                    title: '最终机构',
                    field: 'UNNO',
                    width: 80
                } ,{
                    title: '最终机构名称',
                    field: 'UNNONAME',
                    width: 80
                }*/,{
                    title: '归属中心机构号',
                    field: 'YUNYING',
                    width: 80
                },{
                    title: '商户编号',
                    field: 'MID',
                    width: 80
                },{
                    title: 'SN号',
                    field: 'SN',
                    width: 80
                }/* ,{
                    title: '型号',
                    field: 'SNTYPE',
                    width: 80,
                    formatter : function(value,row,index) {
                        if(value==1){
                            return "小蓝牙";
                        }else if(value == 2){
                            return "大蓝牙";
                        }
                    }
                },{
                    title: '激活日期',
                    field: 'USEDDATE',
                    width: 80
                } */,{
                    title: '销售日期',
                    field: 'KEYCONFIRMDATE',
                    width: 80
                },{
                    title: '出库日期',
                    field: 'OUTDATE',
                    width: 80
                },{
                    title: '扣款金额',
                    field: 'DEDUCTION',
                    width: 80
                },{
                    title: '扣款日期',
                    field: 'MAINTAINDATE',
                    width: 80
                }/* ,{
                    title: '活动类型',
                    field: 'REBATETYPE',
                    width: 80
                } */
                ]
            ],toolbar : [ {
                id : 'btn_run',
                text : '扣款报表导出',
                iconCls : 'icon-query-export',
                handler : function() {
                    sysAdmin_10170_exportFun();
                }
            }]
        });

    });

    function sysAdmin_10170_exportFun() {
        $('#bill_agentunit_10170purseForm').form('submit', {
            url : '${ctx}/sysAdmin/checkDeductionAction_export10170.action'
        });
    }

    function sysAdmin_10170_queryCash() {
        $('#sysAdmin_10170_datagrid').datagrid('load', serializeObject($('#bill_agentunit_10170purseForm')));
    }

    function bill_10170_cleanFun() {
        $('#bill_agentunit_10170purseForm input').val('');
        $('#bill_agentunit_10170purseForm select').val('全部');
    }
    
    $(function() {
		$('#rebateType_10170').combogrid({
			url : '${ctx}/sysAdmin/checkDeductionAction_queryCheckDeductionListInfo10170_selectRebateType.action',
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
    <div data-options="region:'north',border:false" style="height:110px; overflow: hidden; padding-top:5px;">
        <form id="bill_agentunit_10170purseForm" style="padding-left:3%" method="post">
            <table class="tableForm pdl20">
                <%--交易月：选择任何当日代表当月--%>
                <%--出售月：选择任何当日代表当月--%>
                <%--出库月：选择任何当日代表当月--%>
                <%--一代机构：一代机构号精确查询--%>
                <%--归属：一代机构号简称精确查询--%>
                <%--归属分公司：中心机构号精确查询--%>
                <%--活动类型：活动3、活动11、活动13、活动15、活动17、活动18、活动19、活动21、ALL--%>
                <%--商户编码：MID精确查询--%>
                <%--SN：SN精确查询--%>
                <%--功能按钮：查询、清空、导出--%>
                <tr>
                    <th>扣款日期:</th>
                    <td><input  class="easyui-datebox" id="maintainDate" name="maintainDate"  style="width: 130px;"/>至</td>
					<td><input  class="easyui-datebox" id="maintainDate1" name="maintainDate1"  style="width: 130px;"/></td>
                   <!--  <th style="">出售月:</th>
                    <td>
                        <input id="keyConfirmDate" name="keyConfirmDate" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                    </td>
                    <th style="">出库月:</th>
                    <td>
                        <input id="outDate" name="outDate" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                    </td> -->
                    <th>归属一代机构:</th>
                    <td><input name="yiDai" style="width: 130px;" /></td>
                    <th>归属下级机构号:</th>
                    <td><input name="erDai" style="width: 130px;" /></td>
                </tr>
                 <tr>
                    <th>归属中心机构号:</th>
                    <td><input name="yunYing" style="width: 130px;" /></td>
                    <th>活动类型:</th>
                    <td><select name="rebateType" id="rebateType_10170" class="easyui-combogrid" style="width:130px;"></select></td>
                    <!-- 
                    <td><select name="rebateType" style="width: 135px;" >
                        <option value="" selected="selected">All</option>
                        <option value="3">活动3</option>
                        <option value="11">活动11</option>
                        <option value="13">活动13</option>
                        <option value="15">活动15</option>
                        <option value="17">活动17</option>
                        <option value="18">活动18</option>
                        <option value="19">活动19</option>
                        <option value="20">活动20</option>
                    </select>
                    </td>
                     -->
                    <th>商户编码:</th>
                    <td><input name="mid" style="width: 130px;" /></td>
                    <th>SN:</th>
                    <td><input name="sn" style="width: 130px;" /></td>
                     <th>&nbsp;</th>
                    <td>
                        <a href="javascript:void(0);"
                             class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
                             onclick="sysAdmin_10170_queryCash();">查询</a> &nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-cancel',plain:true" onclick="bill_10170_cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
        <table id="sysAdmin_10170_datagrid"></table>
    </div>
</div>
