<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--活动9商户返利明细-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20542_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRebate_queryRebate9_mer.action',
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
				title :'激活阶段',
				field :'ACTIVA_TYPE',
				width : 100
			},{
				title :'返利月',
				field :'REBATEMONTH',
				width : 100
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 100
			},{
				title :'活动类型',
				field :'REBATETYPE',
				width : 100
			},{
				title :'交易金额',
				field :'SAMT',
				width : 100
			},{
				title :'交易笔数',
				field :'NUM',
				width : 100
			}]]	,
			toolbar:[{
				text:'导出CSV',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20542();
				}
			}]	
		});
	});
	function exportExcel_20542(){
		var keyDay = $('#keyDay_20542').datebox('getValue');
		var keyDay1 = $('#keyDay1_20542').datebox('getValue');
		var txnDay = $('#txnDay_20542').datebox('getValue');
		if(keyDay!=null && keyDay!='' && keyDay1!=null && keyDay1!='' && txnDay!=null && txnDay!=''){
		    $("#sysAdmin_20542_searchForm").form('submit',{
				url:'${ctx}/sysAdmin/checkRebate_exportRebate9Excel_mer.action'
			});
		}else{
			$.messager.alert('提示','出售月，交易月不可为空！');
		}
	}
	//表单查询
	function sysAdmin_20542_searchFun80() {
		var keyDay = $('#keyDay_20542').datebox('getValue');
		var keyDay1 = $('#keyDay1_20542').datebox('getValue');
		var txnDay = $('#txnDay_20542').datebox('getValue');
		if(keyDay!=null && keyDay!='' && keyDay1!=null && keyDay1!='' && txnDay!=null && txnDay!=''){
			$('#sysAdmin_20542_datagrid').datagrid('load', serializeObject($('#sysAdmin_20542_searchForm')));
		}else{
			$.messager.alert('提示','出售月，交易月不可为空！');
		}
	}

	//清除表单内容
	function sysAdmin_20542_cleanFun80() {
		$('#sysAdmin_20542_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20542_searchForm" style="padding-left:7%" method="post">
			<table class="tableForm" >
				<tr>
					<th>出售月：</th>
					<td><input  class="easyui-datebox" id="keyDay_20542" name="keyDay"  style="width: 110px;"/>&nbsp;至</td>
					<td><input  class="easyui-datebox" id="keyDay1_20542" name="keyDay1"  style="width: 110px;"/></td>
					
					<th>&nbsp;&nbsp;&nbsp;交易月：</th>
					<td><input  class="easyui-datebox" id="txnDay_20542" name="txnDay"  style="width: 110px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;机构号：</th>
					<td>
						<input type="text" name="unno" id="unno_20540" style="width:110px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;商户编号：</th>
					<td>
						<input type="text" name="mid" id="mid_20540" style="width:110px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;SN：</th>
					<td>
						<input type="text" name="sn" id="sn_20540" style="width:110px;"/>
					</td>
					<td>
						&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20542_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20542_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20542_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


