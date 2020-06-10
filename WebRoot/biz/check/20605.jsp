<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$(function(){
			$('#rebateType_20605').combogrid({
				url : '${ctx}/sysAdmin/agentunit_listRebateRate.action?status=syt',
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
			$('#unno_20605').combogrid({
				url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
				idField:'UNNO',
				textField:'UN_NAME',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'UN_NAME',title:'机构名称',width:150}			
				]]
			});
			
				
		$('#sysAdmin_20605_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryUnitSytTemplateDetailDataScan2.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
                title :'机构名称',
                field :'UN_NAME',
                width : 100
            },{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'手续费',
				field :'MDA',
				width : 100
			},{
				title :'分润金额',
				field :'PROFIT',
				width : 100,
				formatter : function(value,row,index) {
					if(value <= 0){
						return 0;
					}else{
						return value;
					}
				}
			},{
				title :'活动类型',
				field :'MAINTAINUID',
				width : 100
			},{
				title :'交易类型',
				field :'SETTMETHOD',
				width : 100,align : 'center'
				/* formatter : function(value,row,index) {
					if(value==1 && row.IFCARD == '1'){
						return '微信0.38';
					}else if(value==1 && row.IFCARD == '2'){
						return '微信0.45';
					}else if(value==1 && row.IFCARD == '3'){
						return '微信(老)';
					}else if(value==2){
						return '支付宝';
					}else if(value==3){
						return '二维码';
					}else if(value==1){
						return '微信';
					}
				} */
			}]]	,
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20605();
				}
			}]	
		});
	});
	function exportExcel_20605(){
		var txnDay = $('#txnDay_20605').datebox('getValue');
		var txnDay1 = $('#txnDay1_20605').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$("#sysAdmin_20605_searchForm").form('submit',{
		    		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportUnitProfitMicroDetailDataScan3.action'
		    	});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	//表单查询
	function sysAdmin_20605_searchFun80() {
		var txnDay = $('#txnDay_20605').datebox('getValue');
		var txnDay1 = $('#txnDay1_20605').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$('#sysAdmin_20605_datagrid').datagrid('load', serializeObject($('#sysAdmin_20605_searchForm'))); 
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}

	//清除表单内容
	function sysAdmin_20605_cleanFun80() {
		$('#sysAdmin_20605_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20605_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td>
						<select name="unno" id="unno_20605" class="easyui-combogrid" style="width:205px;"></select>
					</td>
					<th>查询日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_20605" name="txnDay"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_20605" name="txnDay1"  style="width: 162px;"/></td>
					<!-- <th>活动类型：</th>
					<td><select id="profitRuleSelect" name="profitRule" class="easyui-combobox" style="width: 162px;height:auto;"> 
							 <option value ="">ALL</option>
  							<option value ="21">21</option>
 							<option value="75">75</option> 
 						 </select></td> -->
 						  <th>活动类型</th>
						<td>
							<select id="rebateType_20605" name="profitRule" class="easyui-combogrid" data-options="validType:'spaceValidator'" style="width:135px;"></select>
						</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20605_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20605_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20605_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>

