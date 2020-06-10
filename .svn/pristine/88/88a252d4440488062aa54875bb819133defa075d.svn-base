<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <!-- 代理商 分润冻结记录表 -->
<script type="text/javascript">
    $(function() {
        $('#sysAdmin_10163_datagrid').datagrid({
            url: '${ctx}/sysAdmin/agentunit_queryPusreFrozenRecord10163.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '机构号',
                    field: 'UNNO',
                    width: 80
                },{
                    title: '商户号',
                    field: 'MID',
                    width: 100
                },{
                    title: '交易金额',
                    field: 'SAMT',
                    width: 100
                },{
                    title: '交易卡号',
                    field: 'CARDPAN',
                    width: 100
                },{
                    title: '交易日期',
                    field: 'TXNDAY',
                    width: 100
                },{
                    title: '冻结分润金额',
                    field: 'FROZENAMT',
                    width: 100
                },{
                    title: '冻结日期',
                    field: 'FROZENDAY',
                    width: 100
                },{
                    title: '状态',
                    field: 'FROZENSTATUS',
                    width: 100,
                    formatter:function(val){
                        if(val=='0'){
                            return "冻结";
                        }else if(val=='1'){
                            return "解冻";
                        }else {
                            return val;
                        }
                    }
                },{
                    title: '备注',
                    field: 'REMARKS',
                    width: 100
                }
                ]
            ]
        });

    });


    function sysAdmin_10163_queryCash() {
        $('#sysAdmin_10163_datagrid').datagrid('load', serializeObject($('#bill_agentunit_10163purseForm')));
    }

    function bill_10163_cleanFun() {
        $('#bill_agentunit_10163purseForm input').val('');
        $('#bill_agentunit_10163purseForm select').val('全部');
    }
</script>
<style type="text/css">
    .pdl20 {
        padding-left: 20px;
    }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
        <form id="bill_agentunit_10163purseForm" style="padding:0px;">
           <table class="tableForm pdl20">
                 <!-- 商户编号，机构号，状态[0冻结 1解冻]，冻结日期 -->
                 <tr>
                    <th>商户编号:</th>
                    <td><input name="agentMid" style="width: 135px;" /></td>   
                    <th>状态:</th>
                    <td><select name="status" style="width: 135px;" > 
                        <option value="" selected="selected">全部</option>
                        <option value="0">冻结</option>
                        <option value="1">解冻</option>
                    </select>
                </td>
                    <td style="">冻结日期:</td>
                     <td>
                         <input name="cashDay" class="easyui-datebox" data-options="editable:false" style="width: 140px;" /> 到
                         <input name="cashDay1" class="easyui-datebox" data-options="editable:false" style="width: 140px;" />
                     </td>
                     <td > <a href="javascript:void(0);" 
                     class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
                     onclick="sysAdmin_10163_queryCash();">查询</a> &nbsp; 
                     <a href="javascript:void(0);" class="easyui-linkbutton" 
                     data-options="iconCls:'icon-cancel',plain:true" onclick="bill_10163_cleanFun();">清空</a> </td>
                 </tr>
             </table>
         </form>
    </div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
        <table id="sysAdmin_10163_datagrid"></table>
    </div>
</div>
