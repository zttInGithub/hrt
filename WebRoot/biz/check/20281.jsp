<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
			$('#unno2').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}
			]]
		});

	function exportExcel_20230(){
	      $("#sysAdmin_20281_searchForm").form('submit',{
			    		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_exportUnitProfitMicroDetailData.action'
			    	});
	}

		$('#sysAdmin_20281_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryUnitProfitMicroDetailData.action',
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
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
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
			},{
				title :'结算类型',
				field :'T',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='2'){
					   return "秒到(0.72%)";
					}else if(value=='1'){
						return "理财";
					}else if (value=='3'){
						return "秒到(非0.72%)";
					}
				}
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
				title :'手续费金额',
				field :'MDA',
				width : 100
			},{
				title :'分润金额',
				field :'PROFIT',
				width : 100
			}]]	,
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
				exportExcel_20230();
				                  }
			}]
		});
	});

	//表单查询
	function sysAdmin_20281_searchFun80() {
		var txnDay = $('#txnDay_20281').datebox('getValue');
   		var txnDay1=$('#txnDay1_20281').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/"));
	 	if(((end-start)/(24*60*60*1000))>31){
	 		$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    31  天！");
	    }else{
	    	$('#sysAdmin_20281_datagrid').datagrid('load', serializeObject($('#sysAdmin_20281_searchForm')));
		}
	}

	//清除表单内容
	function sysAdmin_20281_cleanFun80() {
		$('#sysAdmin_20281_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20281_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td>
						<select name="unno" id="unno2" class="easyui-combogrid" style="width:205px;"></select>
					</td>
						<th>查询日期：</th>
						<td><input  class="easyui-datebox" id="txnDay_20281" name="txnDay"  style="width: 162px;"/>至</td>
						<td><input  class="easyui-datebox" id="txnDay1_20281" name="txnDay1"  style="width: 162px;"/></td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_20281_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_20281_cleanFun80();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">
      <table id="sysAdmin_20281_datagrid" style="overflow: hidden;"></table>
    </div>
</div>

