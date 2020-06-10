<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	$(function() {
		$('#check_20512_datagrid').datagrid({
			url:'${ctx}/sysAdmin/specialMerchant_queryPushMsgList1',
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
				title :'商户简称',
				field :'CUSNAME',
				width : 100
			},{
				title :'商户名称',
				field :'REGNAME',
				width : 100
			},{
				title :'法人证件类型',
				field :'DOCTYPE',
				width : 100
			},{
				title :'法人证件号码',
				field :'DOCCODE',
				width : 100
			},{
				title :'法定代表人姓名',
				field :'DOCNAME',
				width : 100
			},{
				title :'法定代表人证件类型,',
				field :'LEGDOCTYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "营业执照编码";
					 }else if(value=='02'){
					 	return "统一社会信息代码";
					 }else if(value=='03'){
					 	return "组织机构代码证";
					 }else if(value=='04'){
					 	return "经营许可证";
					 }else if(value=='05'){
					 	return "税务登记证";
					 }else if(value=='99'){
					 	return "其他";
					 }
				}
			},{
				title :'法定代表人证件号码',
				field :'LEGDOCCODE',
				width : 100
			},{
				title :'风险信息等级',
				field :'RISKLEVEL',
				width : 100
			},{
				title :'风险类型',
				field :'RISKTYPE',
				width : 100
			},{
				title :'有效期',
				field :'VALIDDATE',
				width : 100
			},{
				title :'有效性',
				field :'VALIDSTATUS',
				width : 100
			},{
				title :'推送日期',
				field :'PUSHDATE',
				width : 100
			}]]/* ,
			toolbar:[{
					text:'图标说明：'
				},{
					id:'btn_edit',
					text:'上传',
					iconCls:'icon-edit',
					handler:function(){
						check_20512_upload();
					}
				}] */
		});
	});
	function check_20512_Report() {
		//$('#check_20512_datagrid').datagrid({url:'${ctx}/sysAdmin/specialMerchant_queryPushMsgList'}); 
		$('#check_20512_datagrid').datagrid('load', serializeObject($('#check_20512_searchForm'))); 
	}

	 //清除表单内容
	function check_20512_cleanFun() {
		$('#check_20512_searchForm input').val('');
	} 
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:16px;">
		<form id="check_20512_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>商户简称</th>
					<td><input  name="CusName" style="width: 120px;" /></td>	
					<th>商户名称</th>
					<td><input  name="RegName" style="width: 120px;" /></td>
					<th>对方上送时间</th>
					<td><input  name="REPDATE" class="easyui-datebox" style="width: 83px;" /> <a>&nbsp;-&nbsp;</a>
						<input  name="REPDATE1" class="easyui-datebox" style="width: 83px;" />
					</td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20512_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20512_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20512_datagrid"></table>
    </div> 
</div>