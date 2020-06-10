<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function exportExcel_user_login_active_detail(){
		var txnDay = $('#cashDay').datebox('getValue');
		var txnDay1 = $('#cashDay1').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$("#query_user_login_active_detail_form").form('submit',{
		    		url:'${ctx}/sysAdmin/agentunit_exportUserLoginActiveDetail.action'
		    	});
			} else {
				$.messager.alert('提示', '查询日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择查询日期');
		}
	}
	//查询提交
	function query_user_login_active_detail(){
		var beginDate = $('#cashDay').datebox('getValue');
		var endDate = $('#cashDay1').datebox('getValue');
		var txnDay=new Date(beginDate.replace("-", "/").replace('-','/'));
		var txnDay1 = new Date(endDate.replace("-","/").replace("-","/"));
		//debugger;
		if((txnDay1-txnDay)/(24*3600*1000)>31 || beginDate.substring(0,7)!=endDate.substring(0,7)){
			$.messager.alert('提示','查询日期之差不能大于31天,且为同一月');
		}else{
			$('#user_login_active_detail_datagrid').datagrid('load', serializeObject($('#query_user_login_active_detail_form')));
		}
	}
		//清除表单内容
	function clean_user_login_active_detail() {
		$('#query_user_login_active_detail_form input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#user_login_active_detail_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_queryUserLoginActiveDetail.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped: true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortOrder: 'asc',
			idField : 'unno',
			columns : [[{
				title : '代理机构号',
				field : 'UNNO',
				width : 100
			},{
				title :'代理机构名称',
				field :'AGENTNAME',
				width : 100
			},{
				title :'归属上级代理机构号',
				field :'GUISHU',
				width : 100
			},{
				title :'归属一代机构号',
				field :'YIDAI',
				width : 100
			},{
				title :'归属中心机构号',
				field :'YUNYING',
				width : 100
			},{
				title :'代理级别',
				field :'UNLEVEL',
				width : 100
			},{
				title :'活跃',
				field :'ACTIVE',
				width : 100
			},{
				title :'登录App',
				field :'APP',
				width : 100
			},{
				title :'登录PC',
				field :'PINTAI',
				width : 100
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_user_login_active_detail();
				}
			}] 	
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:5px;">
		<form id="query_user_login_active_detail_form" style="padding-left:15%" method="post">
			<table class="tableForm" >
				<tr>
						<th>代理机构号：</th>
						<td><input  name=unno style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>归属上级机构编号：</th>
						<td><input name="parentUnno" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>归属一代机构编号：</th>
						<td><input name="bno" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
						<th>归属运营中心机构编号：</th>
						<td><input name="rno" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>查询日期：</th>
						<td><input  class="easyui-datebox" id="cashDay" name="cashDay" required="true" style="width: 162px;"/>至</td>
						
						<td><input  class="easyui-datebox" id="cashDay1" name="cashDay1" required="true" style="width: 162px;"/></td>
						<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="query_user_login_active_detail();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="clean_user_login_active_detail();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="user_login_active_detail_datagrid" style="overflow: hidden;"></table>
  </div> 
</div>