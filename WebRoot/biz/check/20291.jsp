<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
			$('#unno_20291').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	
	function exportExcel_20291(){
	      $("#sysAdmin_20291_searchForm").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitProfitTradit_exportUnitProfitTraditDetailData.action'
			    	});
	}
	
		$('#sysAdmin_20291_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitProfitTradit_queryUnitProfitTraditDetailData.action',
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
			sortName: 'PROFIT',
			sortOrder: 'desc',
			columns : [[
			{
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
				title :'卡类别',
				field :'CBRAND',
				width : 100,
				formatter : function(value,row,index) {
						if (value=='1'){
						   return "借记卡";
						}else if(value=='2'){
							return "贷记卡";
						}else{
							return "其他";
						}
					}
			},{
				title :'交易笔数',
				field :'TXNCOUNT',
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
				exportExcel_20291();
				                  }
			}]	
		});
	});
	
	//表单查询
	function sysAdmin_20291_searchFun80() {
		var beginDate = $('#txnDay_20291').datebox('getValue');
		var endDate =   $('#txnDay1_20291').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		if((txnDay1-txnDay)/(24*3600*1000)>31){
			$.messager.alert('提示','查询日期之差不能大于31天');
		}else{
			$('#sysAdmin_20291_datagrid').datagrid('load', serializeObject($('#sysAdmin_20291_searchForm'))); 
		}
		
	}

	//清除表单内容
	function sysAdmin_20291_cleanFun80() {
		$('#sysAdmin_20291_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20291_searchForm" style="padding-left:5%" method="post">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><select name="unno" id="unno_20291" class="easyui-combogrid" style="width:205px;"></select>
					</td>
						<th>&nbsp;&nbsp;&nbsp;查询日期：</th>
						<td><input  class="easyui-datebox" id="txnDay_20291" name="txnDay"  style="width: 162px;"/>&nbsp;至&nbsp;</td>
						<td><input  class="easyui-datebox" id="txnDay1_20291" name="txnDay1"  style="width: 162px;"/></td>
				
					<td colspan="5" style="text-align: center;">&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20291_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20291_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20291_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


