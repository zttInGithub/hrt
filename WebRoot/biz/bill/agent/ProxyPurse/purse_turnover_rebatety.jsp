<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%--钱包分润调整--%> 
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#purse_turnover_rebatety_datagrid').datagrid({	
			url:'${ctx}/sysAdmin/ProxyPurse_queryPurseTurnoverRebatety.action',
			fit:true,
			frozen:true,
			striped:true,
			fitColumns : true,
			//rownumbers:true,
			border : false,
			nowrap : true,
			singleSelect:true,
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title :'代理机构号',
				field :'UNNO',
				width : 100			
			},{
				title :'代理名称',
				field :'UNNAME',
				width : 100			
			},{
				title :'代理级别',
				field :'UNLEVEL',
				width : 130			
			},{
				title :'上级代理机构号',
				field :'UPPERUNNO',
				width : 130
			},{
				title :'归属一代机构号',
				field :'YIDAIUNNO',
				width : 100
			},{
				title :'归属运营中心机构号',
				field :'YUNYINGUNNO',
				width : 100
			},{
				title :'活动类型',
				field :'MINFO1',
				width : 100
			},{
				title :'分润金额',
				field :'FENRUNMDA',
				width : 100
			},{
				title :'返现金额',
				field :'FANXIANMDA',
				width : 100
			},{
				title :'扣款金额',
				field :'KOUKUANMDA',
				width : 100
			},{
				title :'交易日期/返现/扣款日期',
				field :'TXNDAY',
				width : 150
			},{
				title :'钱包类型',
				field :'WALLETTYPE',
				width : 100,
				formatter : function(value,row,index) {
                    if(value==1){
                        return "返现";
                    }else{
                        return "分润";
                    }
                }
			}]],
			toolbar:[{
					id:'btn_add',
					text:'导出',
					iconCls:'icon-xls',
					handler:function(){
						report_turnover_rebatety();
					}
				}]
		});
	});

	//表单查询
	function searchFun_purse_turnover_rebatety() {
		$('#purse_turnover_rebatety_datagrid').datagrid('load', serializeObject($('#searchForm_purse_turnover_rebatety')));
	}

	//清除表单内容
	function cleanFun_purse_turnover_rebatety() {
		$('#searchForm_purse_turnover_rebatety input').val('');
	}
	 //导出
    function report_turnover_rebatety(){
        $('#searchForm_purse_turnover_rebatety').form('submit',{
            url:'${ctx}/sysAdmin/ProxyPurse_reportPurseTurnoverRebatety.action'
        });
    }
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
		<form id="searchForm_purse_turnover_rebatety" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>代理机构号</th>
					<td><input name="UNNO" style="width: 140px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>活动类型</th>
					<td><input name="PROVINCE" style="width: 140px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>钱包类型</th>
					<td><select name="walletType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
		    				<option value="">全部</option>
		    				<option value="1">返现</option>
		    				<option value="0">分润</option>
		    			</select>
		    		</td>
					<th>交易日期</th>
				    <td><input name="SETTLEDAY" class="easyui-datebox"style="width: 100px;"/>&nbsp;-
				    <input name="CDATE" class="easyui-datebox"style="width: 100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>				
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="searchFun_purse_turnover_rebatety();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="cleanFun_purse_turnover_rebatety();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="purse_turnover_rebatety_datagrid"></table>
    </div> 
</div>