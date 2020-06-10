<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20311_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkSettleReturn_queryFillPayInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'CFPID',
			sortOrder: 'desc',
			idField : 'CFPID',
			columns : [[{
				field : 'CFPID',
				hidden:true
			},{
				title : '机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '机构名称',
				field : 'UN_NAME',
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 100,
				sortable :true
			},{
				title :'商户注册名称',
				field :'RNAME',
				width : 100,
				sortable :true
			},{
				title :'业务人员',
				field :'SALESNAME',
				width : 100
			},{
				title :'开户行名称',
				field :'BANKBRANCH',
				width : 100
			},{
				title :'入账账号',
				field :'BANKACCNO',
				width : 100
			},{
				title :'入账人名称',
				field :'BANKACCNAME',
				width : 100,
				sortable :true
			},{
				title :'补付金额',
				field :'PAYAMOUNT',
				width : 100,
				sortable :true
			},{
				title :'补付日期',
				field :'FILLPAYDATE',
				width : 100
			},{
			    title :'银行退回日期',
				field :'BACKPAYDATE',
				width : 100
			},{
				title :'状态',
				field :'STATUS',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(value==0){
						return '未补付';
					}else if(value==1){
						return '已补付';
						}
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_20311_exportFun();
				}
			}]
		});
	});

	
	//表单查询
	function sysAdmin_20311_searchFun80() {
		$('#sysAdmin_20311_datagrid').datagrid('load', serializeObject($('#sysAdmin_20311_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_20311_cleanFun80() {
		$('#sysAdmin_20311_searchForm input').val('');
	}

	function sysAdmin_20311_exportFun(){
		$('#sysAdmin_20311_searchForm').form('submit',{
    		url:'${ctx}/sysAdmin/checkSettleReturn_exportFillPayInfo.action'
    	});
		}
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20311_searchForm" style="padding-left:2%" method="post">
		<input type="hidden" id="ids" name="ids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 150px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 150px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入账人名称</th>
					<td><input name="bankAccName" style="width: 116px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入账人卡号</th>
					<td><input name="bankAccNo" style="width: 120px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行退回日期</th>
					<td>
						<input name="backPayDate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
				</tr> 
				<tr>
					<td></td><td></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20311_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20311_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20311_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


