<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function(){
		 $('#rebateType_cashback_detail').combogrid({
			url : '${ctx}/sysAdmin/cashbackTemplate_listRebateRate.action',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]]
		}); 
	});
	function exportExcel_cashback_detail(){
		$("#query_cashback_detail_form").form('submit',{
    		url:'${ctx}/sysAdmin/cashbackTemplate_reportCashbackDetail.action'
    	});
	}
	//查询提交
	function query_cashback_detail(){
		$('#cashback_detail_datagrid').datagrid('load', serializeObject($('#query_cashback_detail_form')));
	}
	//清除表单内容
	function clean_cashback_detail() {
		$('#query_cashback_detail_form input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#cashback_detail_datagrid').datagrid({
			url :'${ctx}/sysAdmin/cashbackTemplate_queryCashbackDetail.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped: true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortOrder: 'asc',
			columns : [[{
				title : '归属本级机构号',
				field : 'UPP_UNNO',
				width : 100
			},{
				title : '归属下级机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '归属下级机构名称',
				field : 'UN_NAME',
				width : 100
			},{
				title : 'mid',
				field : 'MID',
				width : 100
			},{
				title : 'sn',
				field : 'SN',
				width : 100
			},{
				title : '交易金额',
				field : 'SAMT',
				width : 100
			},{
				title : '本级返现总盘金额',
				field : 'CASHBACK_AMT',
				width : 100
			},{
				title : '返现日期',
				field : 'CDATE',
				width : 100
			},{
				title : '活动编号',
				field : 'REBATETYPE',
				width : 100
			},{
				title : '返现类型',
				field : 'CASHBACKTYPE',
				width : 100
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_cashback_detail();
				}
			}] 	
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:5px;">
		<form id="query_cashback_detail_form" style="padding-left:3%" method="post">
			<table class="tableForm" >
				<tr>
						<th>下级代理机构号：</th>
						<td><input  name="unno" style="width: 130px;"/></td>
						<th>下级代理机构名称：</th>
						<td><input name="unnoname" style="width: 130px;"/></td>
						<th>返现类型：</th>
         				<td>
                         <select id="cashbacktype" name="cashbacktype" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:130px;">
				              <option value="" selected="selected">All</option>
				              <option value="1">刷卡</option>
				              <option value="2">押金</option>
				              <option value="3">花呗分期</option>
				         </select>
				         </td>
			    		<th>活动类型</th>
			    		<td><select id="rebateType_cashback_detail" name="rebatetype" style="width:130px;"></select></td>
				</tr>
				<tr>
					<th>商户编号：</th>
					<td><input name="mid" style="width: 130px;"/></td>
					<th>SN：</th>
					<td><input name="sn" style="width: 130px;"/></td>
					<th>返现日期：</th>
					<td><input  class="easyui-datebox" id="cashDay" name="cashDay" style="width: 130px;"/>至</td>
					<td><input  class="easyui-datebox" id="cashDay1" name="cashDay1" style="width: 130px;"/></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="query_cashback_detail();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="clean_cashback_detail();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="cashback_detail_datagrid" style="overflow: hidden;"></table>
  </div> 
</div>