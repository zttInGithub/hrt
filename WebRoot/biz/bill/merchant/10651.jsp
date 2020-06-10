<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_riskTransCard2_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkReceiptsOpreation_queryRiskReceiptsAuditList.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'PKID',
			sortOrder: 'desc',
			idField : 'PKID',
			columns : [[{
				field : 'PKID',
				width : 100,
				checkbox:true
			},{
				title : '所属机构',
				field : 'UNNO',
				width : 100
			},{
				title :'商户类型',
				field :'ISM35',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==1){ 
						return '手刷商户';
					}else{
						return '传统商户';
					}
				} 
			},{
				title : '商户编号',
				field : 'MID',
				width : 100
			},{
				title : '商户名称',
				field : 'RNAME',
				width : 100
			},{
				title : '卡号',
				field : 'CARDPAN',
				width : 100
			},{
				title : '交易金额',
				field : 'TXNAMOUNT',
				width : 100
			},{
				title :'交易日期',
				field :'TXNDAY',
				width : 100,
				sortable :true
			},{
				title :'上传照片日期',
				field :'UPLOADDATE',
				width : 150,
				sortable :true
			},{
				title :'审核日期',
				field :'RISKDAY',
				width : 150,
				sortable :true
			},{
				title :'小票是否上传',
				field :'IFUPLOAD',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==0){ 
						return '未上传';
					}else if(value==1){
						return '已上传';
					}else{
						return '退回'
						}
				} 
			},{
				title :'是否通过',
				field :'IFSETTLEFLAG',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==0){ 
						return '未通过';
					}else if(value==1){
						return '已通过';
					}
				} 
			},{
				title :'备注',
				field :'MINFO1',
				width : 100,
				sortable :true
			}]]
		});
	});


	//表单查询
	function sysAdmin_riskReceiptsAudit2_searchFun80() {
		$('#sysAdmin_riskTransCard2_datagrid').datagrid('load', serializeObject($('#sysAdmin_riskreceiptsAudit2_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_riskReceiptsAudit2_cleanFun80() {
		$('#sysAdmin_riskreceiptsAudit2_searchForm input').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_riskreceiptsAudit2_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
										
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传状态</th>
					<td>
						<select name="uploadStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">已上传</option>
		    				<option value="2">未上传</option>
		    			</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批状态</th>
					<td>
						<select name="settleFlagStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="3">已通过</option>
		    				<option value="4">未通过</option>
		    			</select>
					</td>
				</tr> 
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询日期</th>
					<td><input name="txnday" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>至
					<input name="txnday1" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>
					</td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期类型</th>
					<td>
						<select name="dateType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="1" selected="selected">按交易日期</option>
		    				<option value="2">按审批通过日期</option>
		    			</select>
					</td>

				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_riskReceiptsAudit2_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_riskReceiptsAudit2_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    	 <table id="sysAdmin_riskTransCard2_datagrid" style="overflow: hidden;"></table> 
    </div> 


