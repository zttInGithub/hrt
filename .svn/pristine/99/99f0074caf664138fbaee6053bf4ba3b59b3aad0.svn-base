<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20520_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryUnitSubTemplateDetailDataScan.action',
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
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'扣款金额',
				field :'TXNAMT',
				width : 100
			},{
				title :'手续费金额',
				field :'MDA',
				width : 100
			},{
				title :'扣款时间',
				field :'LMDATE',
				width : 100
			}]]
		});
	});
	
	//表单查询
	function sysAdmin_20520_searchFun80() {
		var beginDate = $('#txnDay_20520').datebox('getValue');
		var endDate = $('#txnDay1_20520').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if((txnDay1-txnDay)/(24*3600*1000)>31){
			$.messager.alert('提示','查询日期之差不能大于31天');
		}else{
			$('#sysAdmin_20520_datagrid').datagrid('load', serializeObject($('#sysAdmin_20520_searchForm'))); 
		}
	}

	//清除表单内容
	function sysAdmin_20520_cleanFun80() {
		$('#sysAdmin_20520_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20520_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td>
						<input name="mid" style="width:205px;">
					</td>
					<th>商户名称</th>
					<td>
						<input name="rname" style="width:205px;">
					</td>
					<th>查询日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_20520" name="txnDay"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_20520" name="txnDay1"  style="width: 162px;"/></td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20520_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20520_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20520_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


