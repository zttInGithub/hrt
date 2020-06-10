<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--活动11商户返利明细-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20545_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRebate_queryRebate11_mer.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title :'归属机构',
				field :'UNNO',
				width : 100,
				hidden:true
			},{
				title :'一代机构',
				field :'UNNO1',
				width : 100,
				formatter : function(value,row,index) {
					if(value==""||value==null){
						return row.UNNO;
					}
				}
			},{
				title :'归属分公司',
				field :'UNNO2',
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 120
			},{
				title :'SN号',
				field :'SN',
				width : 100
			},{
				title :'型号',
				field :'MACHINEMODEL',
				width : 100
			},{
				title :'类别',
				field :'SN_TYPE',
				width : 100,
				formatter : function(value,row,index) {
					if(value=="1"){
						return "小蓝牙";
					}else{
						return "大蓝牙";
					}
				}
			},{
				title :'出售日期',
				field :'KDATE',
				width : 100
			},{
				title :'出售月',
				field :'KMONTH',
				width : 100
			},{
				title :'激活日期',
				field :'UDATE',
				width : 100
			},{
				title :'A月+3扣款',
				field :'DEDUCT_A3',
				width : 70
			},{
				title :'A月+4扣款',
				field :'DEDUCT_A4',
				width : 70
			},{
				title :'A月+5扣款',
				field :'DEDUCT_A5',
				width : 70
			},{
				title :'扣款汇总',
				field :'DEDUCT_SUM',
				width : 70
			},{
				title :'是否可返',
				field :'ISREBATE',
				width : 70
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 100
			},{
				title :'返利月',
				field :'REBATEMONTH',
				width : 100
			},{
				title :'返利阶段1金额',
				field :'REBATE1_AMT',
				width : 100
			},{
				title :'返利阶段2金额',
				field :'REBATE2_AMT',
				width : 100
			},{
				title :'返利阶段1返利月份',
				field :'REBATE1_MONTH',
				width : 120
			},{
				title :'返利阶段2返利月份',
				field :'REBATE2_MONTH',
				width : 120
			},{
				title :'交易金额',
				field :'SAMT',
				width : 100
			},{
				title :'交易笔数',
				field :'NUM',
				width : 100
			},{
				title :'活动类型',
				field :'REBATETYPE',
				width : 100
			}]]	,
			toolbar:[{
				text:'导出CSV',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20545();
				}
			}]	
		});
	});
	function exportExcel_20545(){
		var keyDay = $('#keyDay_20545').datebox('getValue');
		var keyDay1 = $('#keyDay1_20545').datebox('getValue');
		var txnDay = $('#txnDay_20545').datebox('getValue');
		if(keyDay!=null && keyDay!='' && keyDay1!=null && keyDay1!='' && txnDay!=null && txnDay!=''){
		    $("#sysAdmin_20545_searchForm").form('submit',{
				url:'${ctx}/sysAdmin/checkRebate_exportRebate11Excel_mer.action'
			});
		}else{
			$.messager.alert('提示','出售月，交易月不可为空！');
		}
	}
	//表单查询
	function sysAdmin_20545_searchFun80() {
		var keyDay = $('#keyDay_20545').datebox('getValue');
		var keyDay1 = $('#keyDay1_20545').datebox('getValue');
		var txnDay = $('#txnDay_20545').datebox('getValue');
		if(keyDay!=null && keyDay!='' && keyDay1!=null && keyDay1!='' && txnDay!=null && txnDay!=''){
			$('#sysAdmin_20545_datagrid').datagrid('load', serializeObject($('#sysAdmin_20545_searchForm')));
		}else{
			$.messager.alert('提示','出售月，交易月不可为空！');
		}
	}

	//清除表单内容
	function sysAdmin_20545_cleanFun80() {
		$('#sysAdmin_20545_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20545_searchForm" style="padding-left:8%" method="post">
			<table class="tableForm" >
				<tr>
					<th>出售月：</th>
					<td><input  class="easyui-datebox" id="keyDay_20545" name="keyDay"  style="width: 90px;"/>&nbsp;至</td>
					<td><input  class="easyui-datebox" id="keyDay1_20545" name="keyDay1"  style="width: 90px;"/></td>
					
					<th>&nbsp;&nbsp;&nbsp;交易月：</th>
					<td><input  class="easyui-datebox" id="txnDay_20545" name="txnDay"  style="width: 90px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;返利月：</th>
					<td><input  class="easyui-datebox" id="txnDay1_20545" name="txnDay1"  style="width: 90px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;机构号：</th>
					<td>
						<input type="text" name="unno" id="unno_20545" style="width:80px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;商户编号：</th>
					<td>
						<input type="text" name="mid" id="mid_20545" style="width:110px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;SN：</th>
					<td>
						<input type="text" name="sn" id="sn_20545" style="width:90px;"/>
					</td>
					<td>
						&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20545_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20545_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20545_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


