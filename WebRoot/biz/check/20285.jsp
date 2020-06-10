<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20285_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryUnitProfitMicroSumDataScan.action',
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
			sortName: 'profit',
			sortOrder: 'desc',
			columns : [[{
				title :'机构名称',
				field :'UNITNAME',
				width : 100
			},
			/**{
				title :'商户类型',
				field :'MERCHANTTYPE',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value==1){
					   return "小额商户";
					}else if(value==2){
						return "大额商户";
					}  
				}
			},**/{
				title :'结算类型',
				field :'SETTMETHOD',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='000001'){
					   return "理财微信";
					}else if (value=='000002'){
					   return "理财支付宝";
					}else if (value=='000003'){
					   return "理财银联二维码";
					}else if((value=='100001')){
						return "秒到微信";
					}else if((value=='100002')){
						return "秒到支付宝";
					}else if((value=='100003')){
						return "秒到银联二维码";
					}else if((value=='800000')){
						return "快捷VIP";
					}else if((value=='800001')){
						return "快捷完美";
					}else if((value=='100004')){
						return "扫码1000以下";
					}else if((value=='100005')){
						return "扫码1000以上";
					}
				}
			},{
				title :'交易总笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'交易总金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'分润金额',
				field :'PROFIT',
				width : 100
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20285();
				}
			}]
		});
	});
	//导出
	function exportExcel_20285(){
		var txnDay = $('#txnDay_20285').datebox('getValue');
		var txnDay1 = $('#txnDay1_20285').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$("#sysAdmin_20285_searchForm").form('submit',{
		    		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportUnitProfitMicroSumDataScan.action'
		    	});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	//表单查询
	function sysAdmin_20285_searchFun80() {
		var txnDay = $('#txnDay_20285').datebox('getValue');
		var txnDay1 = $('#txnDay1_20285').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$('#sysAdmin_20285_datagrid').datagrid('load', serializeObject($('#sysAdmin_20285_searchForm')));  
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
		
	}

	//清除表单内容
	function sysAdmin_20285_cleanFun80() {
		$('#sysAdmin_20285_searchForm input').val('');
	}
$(function() {
			$('#unno_20285').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});

});
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20285_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td>
						<select name="unno" id="unno_20285" class="easyui-combogrid" style="width:205px;"></select>
					</td>
						<th>查询日期：</th>
						<td><input  class="easyui-datebox" id="txnDay_20285" name="txnDay"  style="width: 162px;"/>至</td>
						<td><input  class="easyui-datebox" id="txnDay1_20285" name="txnDay1"  style="width: 162px;"/></td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20285_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20285_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20285_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


