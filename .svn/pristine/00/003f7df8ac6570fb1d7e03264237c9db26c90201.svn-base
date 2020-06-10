<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10971_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_queryRegisteredMerchantActivities.action',
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
				title :'活动类型',
				field :'REBATETYPE',
				width : 75,
				sortable:true,
				formatter:function(value,row,index){
					if (value==null||value==''||value=='0'){
						return '无';
					}else if(value=='1'){
						return '循环送';
					}else if(value=='2'){
						return '激活返利';
					}else if(value=='3'){
						return '分期返利';
					}else if(value=='4'){
						return '购机返利';
					}else if(value=='5'){
						return '活动5';
					}else if(value=='6'){
						return '活动6';
					}else if(value=='7'){
						return '活动7';
					}else if(value=='8'){
						return '活动8';
					}else if(value=='9'){
						return '买断9';
					}else if(value=='10'){
						return '活动10';
					}else if(value=='11'){
						return '活动11';
					}else if(value=='12'){
						return '活动12';
					}else if(value=='13'){
						return '活动13';
					}else if(value==14){
						return '活动14';
					}else if(value==15){
						return '活动15';
					}else if(value==16){
						return '活动16';
					}else if(value==17){
						return '活动17';
					}else if(value==18){
						return '活动18';
					}else{
					   return "活动"+value;
					}
				}
			},
			{
				title :'运营中心机构号',
				field :'FIRTUNNO',
				width : 100
			},{
				title :'运营中心名称',
				field :'FIRSTUNNONAME',
				width : 100
			},{
				title :'数量',
				field :'TOTALCOUNT',
				width : 100
			},{
				title :'类别',
				field :'COUNTTYPE',
				width : 100
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin__registeredMerchantActivities_exportFun();
				}
			}]
		});
	});
	
	//表单查询
	function sysAdmin_10971_searchFun80() {
		var beginDate = $('#txnDay_10971').datebox('getValue');
		var endDate = $('#txnDay1_10971').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if(beginDate == "" || endDate == ""){
			$.messager.alert('提示','请选择查询日期');
			return;
		}
		//if((txnDay1-txnDay)/(24*3600*1000)>31 || beginDate.substring(0,7)!=endDate.substring(0,7)){
		//	$.messager.alert('提示','查询日期之差不能大于31天,且为同一月');
		//}else{
			$('#sysAdmin_10971_datagrid').datagrid('load', serializeObject($('#sysAdmin_10971_searchForm'))); 
		//}
		
	}

	//清除表单内容
	function sysAdmin_10971_cleanFun80() {
		$('#sysAdmin_10971_searchForm input').val('');
	}
	$(function() {
				$('#rebateType_10971').combogrid({
				url :'${ctx}/sysAdmin/terminalInfo_getListActivities.action',
				idField:'VALUEINTEGER',
				textField:'VALUEINTEGER',
				value:-1,
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'NAME',title:'返利名称',width:150},
					{field:'VALUEINTEGER',title:'类型',width:150}			
				]]
			});
	
	});
	
	function sysAdmin__registeredMerchantActivities_exportFun() {
		$('#sysAdmin_10971_searchForm').form('submit', {
			url : '${ctx}/sysAdmin/terminalInfo_exportRegisteredMerchantActivities.action'
		});
	}
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10971_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>查询日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_10971" name="keyConfirmDate"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_10971" name="keyConfirmDateEnd"  style="width: 162px;"/></td>
					<th>活动类型：</th>
					<td>
						<select name="rebateType" id="rebateType_10971" class="easyui-combogrid" style="width:205px;"></select>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10971_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10971_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10971_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


