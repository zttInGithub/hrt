<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--活动3/13机构汇总-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20541_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRebate_queryRebate3_unno.action',
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
				title :'一代机构',
				field :'UNNO1',
				width : 100
			},{
				title :'归属分公司',
				field :'UNNO2',
				width : 100
			},{
				title :'扣款汇总',
				field :'DEDUCT_AMT',
				width : 100
			},{
				title :'扣款SN台数',
				field :'DEDUCT_SN',
				width : 100
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 100
			},{
				title :'返利SN台数',
				field :'REBATENUM',
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
					exportExcel_20541();
				}
			}]	
		});
	});
	function exportExcel_20541(){
		var txnDay = $('#txnDay_20541').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
		    $("#sysAdmin_20541_searchForm").form('submit',{
				url:'${ctx}/sysAdmin/checkRebate_exportRebate3Excel_unno.action'
			});
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}
	//表单查询
	function sysAdmin_20541_searchFun80() {
		var txnDay = $('#txnDay_20541').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
			$('#sysAdmin_20541_datagrid').datagrid('load', serializeObject($('#sysAdmin_20541_searchForm')));
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}

	//清除表单内容
	function sysAdmin_20541_cleanFun80() {
		$('#sysAdmin_20541_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20541_searchForm" style="padding-left:15%" method="post">
			<table class="tableForm" >
				<tr>
					<th>交易月：</th>
					<td><input  class="easyui-datebox" id="txnDay_20541" name="txnDay"  style="width: 162px;"/></td>
					<th>&nbsp;&nbsp;机构号：</th>
					<td>
						<input type="text" name="unno" id="unno_20541" style="width:160px;"/>
					</td>
					<th>&nbsp;&nbsp;活动类型：</th>
					<td> <select class="easyui-combobox" name="rebateType" data-options="panelHeight:'auto',editable:false" style="width:126px;">
							<option value="">ALL</option> 
							<option value="3">3</option> 
							<option value="13">13</option>
					  	 </select>
					</td>
					<td>
						&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20541_searchFun80();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20541_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20541_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


