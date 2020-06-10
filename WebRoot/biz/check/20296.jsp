<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
			$('#unno_20296').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	
	
		$('#sysAdmin_20296_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitProfitTradit_queryUnitProfitTraditSumDataScan.action',
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
			},{
				title :'商户类型',
				field :'INDUSTRYTYPE',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='1'){
					   return "标准类";
					}else if(value=='2'){
						return "优惠类";
					}else if(value=='3'){
						return "减免类";
					} 
				}
			},{
				title :'扫码类型',
				field :'SCANTYPE',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='1'){
						return "1000以上";
					}else if(value=='2'){
						return "1000以下";
					}else if(value=='3'){
						return "扫码";
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
			}]]
		});
	});
	
	//表单查询
	function sysAdmin_20296_searchFun80() {
		var beginDate = $('#txnDay_20296').datebox('getValue');
		var endDate = $('#txnDay1_20296').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if((txnDay1-txnDay)/(24*3600*1000)>31){
			$.messager.alert('提示','查询日期之差不能大于31天');
		}else{
			$('#sysAdmin_20296_datagrid').datagrid('load', serializeObject($('#sysAdmin_20296_searchForm'))); 
		}
		
	}

	//清除表单内容
	function sysAdmin_20296_cleanFun80() {
		$('#sysAdmin_20296_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20296_searchForm" style="padding-left:5%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><select name="unno" id="unno_20296" class="easyui-combogrid" style="width:205px;"></select>
					</td>
						<th>&nbsp;&nbsp;&nbsp;查询日期：</th>
						<td><input  class="easyui-datebox" id="txnDay_20296" name="txnDay"  style="width: 162px;"/>&nbsp;至&nbsp;</td>
						<td><input  class="easyui-datebox" id="txnDay1_20296" name="txnDay1"  style="width: 162px;"/></td>
					<td colspan="5" style="text-align: center;">&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20296_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20296_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20296_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


