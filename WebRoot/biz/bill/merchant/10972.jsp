<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10972_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_queryExportLog.action',
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
				title :'导出类型',
				field :'TYPE',
				width : 75,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '商户入网资料导出';
					}else if(value=='2'){
						return '代理商资料导出';
					}else if(value=='3'){
						return '运营中心资料导出';
					}else{
						return '';
					}
				}
			},
			{
				title :'导出人员',
				field :'USERNAME',
				width : 100
			},{
				title :'导出时间',
				field :'CDATE',
				width : 100
			},{
				title :'导出数量',
				field :'TOTALS',
				width : 100
			}]],
			toolbar:[{
				id:'btn_run',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_exportFun10972();
				}
			}]
		});
	});
	
	//表单查询
	function sysAdmin_10972_searchFun80() {
		$('#sysAdmin_10972_datagrid').datagrid('load', serializeObject($('#sysAdmin_10972_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10972_cleanFun80() {
		$('#sysAdmin_10972_searchForm input').val('');
	}
	
	function sysAdmin_exportFun10972() {
		$('#sysAdmin_10972_searchForm').form('submit', {
			url : '${ctx}/sysAdmin/merchant_export10972.action'
		});
	}
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10972_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>导出日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_10972" name="startDate"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_10972" name="endDate"  style="width: 162px;"/></td>
					<th>导出类型：</th>
					<td>
						<select	name="merchantType" style="width: 150px;">
							<option value="">ALL</option>
							<option value="1">商户入网资料</option>
							<option value="2">代理商资料</option>
							<option value="3">运营中心资料</option>
						</select>
					</td>&nbsp;&nbsp;&nbsp;
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10972_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10972_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10972_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


