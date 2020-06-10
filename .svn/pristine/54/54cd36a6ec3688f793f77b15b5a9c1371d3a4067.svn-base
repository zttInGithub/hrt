<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	$(function() {
		$('#check_20511_datagrid').datagrid({
			url:'${ctx}/sysAdmin/specialMerchant_queryPushMsgList',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			//singleSelect:true,
			checkOnSelect:true,
			 pageSize : 9999,
			pageList : [ 10, 15 ], 
			idField : 'Id',
			sortName : 'Id',
			sortOrder: 'desc',
			columns : [[{
				title :'id',
				field :'ID',
				width : 100,
				hidden :true
			},{
				title :'商户代码',
				field :'CODE',
				width : 100
			},{
				title :'商户名称',
				field :'NAME',
				width : 100
			},{
				title :'法定代表人姓名',
				field :'LEGALNAME',
				width : 100
			},{
				title :'插入时间',
				field :'INDATE',
				width : 100
			}]]/* ,
			toolbar:[{
					text:'图标说明：'
				},{
					id:'btn_edit',
					text:'上传',
					iconCls:'icon-edit',
					handler:function(){
						check_20511_upload();
					}
				}] */
		});
	});
	function check_20511_Report() {
		//$('#check_20511_datagrid').datagrid({url:'${ctx}/sysAdmin/specialMerchant_queryPushMsgList'}); 
		$('#check_20511_datagrid').datagrid('load', serializeObject($('#check_20511_searchForm'))); 
	}

	 //清除表单内容
	function check_20511_cleanFun() {
		$('#check_20511_searchForm input').val('');
	} 
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:16px;">
		<form id="check_20511_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>商户代码</th>
					<td><input  name="code" style="width: 120px;" /></td>	
					<th>商户名称</th>
					<td><input  name="name" style="width: 120px;" /></td>
					 <th>法定代表人姓名</th>
					<td><input  name="legalname" style="width: 120px;" /></td>
					<th>插入时间</th>
					<td><input  name="REPDATE" class="easyui-datebox" style="width: 83px;" /> <a>&nbsp;-&nbsp;</a>
						<input  name="REPDATE1" class="easyui-datebox" style="width: 83px;" />
					</td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20511_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20511_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20511_datagrid"></table>
    </div> 
</div>