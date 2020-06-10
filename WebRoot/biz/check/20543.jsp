<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--活动9机构汇总-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20543_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRebate_queryRebate9_unno.action',
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
				width : 100
			},{
				title :'归属分公司',
				field :'UNNO2',
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
					exportExcel_20543();
				}
			}]	
		});
	});
	function exportExcel_20543(){
		var txnDay = $('#txnDay_20543').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
		    $("#sysAdmin_20543_searchForm").form('submit',{
				url:'${ctx}/sysAdmin/checkRebate_exportRebate9Excel_unno.action'
			});
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}
	//表单查询
	function sysAdmin_20543_searchFun80() {
		var txnDay = $('#txnDay_20543').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
			$('#sysAdmin_20543_datagrid').datagrid('load', serializeObject($('#sysAdmin_20543_searchForm')));
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}

	//清除表单内容
	function sysAdmin_20543_cleanFun80() {
		$('#sysAdmin_20543_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20543_searchForm" style="padding-left:20%" method="post">
			<table class="tableForm" >
				<tr>
					<th>交易月：</th>
					<td><input  class="easyui-datebox" id="txnDay_20543" name="txnDay"  style="width: 160px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;机构号：</th>
					<td>
						<input type="text" name="unno" id="unno_20543" style="width:160px;"/>
					</td>
					<td>
						&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20543_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20543_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20543_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


