<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10570_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseOrder_queryPOSIncome.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 12,
			pageList : [ 12 ],
			remoteSort:true,
			idField : 'MONTH',
			columns : [[{
				title : '月份',
				field : 'MONTH',
				width : 50
			},{
				title : '销售数量',
				field : 'MACNUM',
				width : 100
			},{
				title : '销售金额',
				field : 'OUTAMT',
				width : 100
			},{
				title : '成本金额',
				field : 'INAMT',
				width : 100
			},{
				title : '亏损金额',
				field : 'LOSSAMT',
				width : 100
			},{
				title : '赠品数量',
				field : 'GIVENUM',
				width : 100
			},{
				title : '赠品金额',
				field : 'GIVEAMT',
				width : 100
			},{
				title : '返利金额',
				field : 'REBATEAMT',
				width : 100
			},{
				title : '亏损合计',
				field : 'ALLLOSSAMT',
				width : 100
			},{
				title : '回款数',
				field : 'INCOME',
				width : 100
			},{
				title : '回购款及差价',
				field : 'BACKAMT',
				width : 100
			},{
				title : '扣分润数',
				field : 'PROFIT',
				width : 100
			},{
				title : '其他扣款',
				field : 'OTHERAMT',
				width : 100
			},{
				title : '激活奖励抵欠款',
				field : 'ACHAMT',
				width : 100
			},{
				title : '每月累计应收数',
				field : 'TOTALAMT',
				width : 100
			}]],
			toolbar:[ {
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10570_exportFun();
				}
			}]	
		});
	});
	//导出
	function sysAdmin_10570_exportFun() {
		$('#sysAdmin_10570_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseOrder_exportPOSIncome.action'
			    	});
	}
	//表单查询
	function sysAdmin_10570_searchFun() {
		$('#sysAdmin_10570_datagrid').datagrid('load', serializeObject($('#sysAdmin_10570_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10570_cleanFun() {
		$('#sysAdmin_10570_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10570_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>查询年份：</th>
					<td><input type="text" name="year" id="year" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator'" maxlength="4"></td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10570_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10570_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10570_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>