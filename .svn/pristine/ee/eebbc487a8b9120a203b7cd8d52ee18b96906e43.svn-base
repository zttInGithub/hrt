<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20565_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRebate_queryRebate12Summary.action',
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
				title :'一代机构',
				field :'YIDAI',
				width : 100
			},{
				title :'归属分公司',
				field :'YUNYING',
				width : 100
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 100
			},{
				title :'返利台数',
				field :'REBATENUM',
				width : 100
			},{
				title :'活动类型',
				field :'REBATETYPE',
				width : 100
			}]]	,
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
				exportExcel_20565();
				                  }
			}]	
		});
	});
	function exportExcel_20565(){
		var txnDay = $('#txnDay_20565').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
		    $("#sysAdmin_20565_searchForm").form('submit',{
				url:'${ctx}/sysAdmin/checkRebate_exportRebate12Summary.action'
			});
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}
	//表单查询
	function sysAdmin_20565_searchFun80() {
		var txnDay = $('#txnDay_20565').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
			$('#sysAdmin_20565_datagrid').datagrid('load', serializeObject($('#sysAdmin_20565_searchForm'))); 
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}

	//清除表单内容
	function sysAdmin_20565_cleanFun80() {
		$('#sysAdmin_20565_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20565_searchForm" style="padding-left:20%" method="post">
			<table class="tableForm" >
				<tr>
					<th>一代机构</th>
					<td>
						<input name="unno" class="easyui-validatebox" style="width:205px;"/>
					</td>
					<th>交易月：</th>
					<td><input id="txnDay_20565" class="easyui-datebox" name="txnDay"  style="width: 162px;"/></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20565_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20565_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20565_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


