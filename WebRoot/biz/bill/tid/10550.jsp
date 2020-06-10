<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10550_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseOrder_queryPOSAccounts.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'UNNO',
			columns : [[{
				title : '机构号',
				field : 'UNNO',
				width : 80
			},{
				title : '机构名称',
				field : 'PURCHASERNAME',
				width : 100
			},{
				title : '期初应收',
				field : 'ORDERAMT',
				width : 100
			},{
				title : '销售出库数量',
				field : 'NUMS',
				width : 100
			},{
				title : '销售金额',
				field : 'AMT',
				width : 100
			},{
				title : '回款金额',
				field : 'REMITAMT',
				width : 100
			},{
				title : '已扣分润',
				field : 'SHAREAMT',
				width : 100
			},{
				title : '激活奖励抵扣',
				field : 'ACTIVEAMT',
				width : 100
			},{
				title : '回购款及其他',
				field : 'BACKAMT',
				width : 100
			},{
				title : '期末应收账款',
				field : 'SHOULDAMT',
				width : 100
			}]],
			toolbar:[ {
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10550_exportFun();
				}
			}]	
		});
	});
	//导出
	function sysAdmin_10550_exportFun() {
		$('#sysAdmin_10550_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseOrder_exportPOSAccounts.action'
			    	});
	}
	//表单查询
	function sysAdmin_10550_searchFun() {
		$('#sysAdmin_10550_datagrid').datagrid('load', serializeObject($('#sysAdmin_10550_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10550_cleanFun() {
		$('#sysAdmin_10550_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10550_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>查询时间：</th>
					<td><input name="cdate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;</td>
					<td><input name="cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/></td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10550_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10550_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10550_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>