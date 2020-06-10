<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_30022_datagrid').datagrid({
			url :'${ctx}/sysAdmin/loanApplication_queryLoanApplication.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'SLID',
			columns : [[{
				title : 'ID',
				field : 'SLID',
				width : 100,
				hidden : true
			},{
				title : '用户ID',
				field : 'USER_ID',
				width : 100
			},{
				title : '机构号',
				field : 'LOGIN_NAME',
				width : 100
			},{
				title : '运营中心',
				field : 'UN_NAME',
				width : 100
			},{
				title :'时间',
				field :'LOGINTIME',
				width : 100
			}]], 
			toolbar:[{
					id:'btn_add',
					text:'导出',
					iconCls:'icon-query-export',
					handler:function(){
						sysAdmin_30022_export();
					}
				}]		
		});
	});
	
	//导出
	function sysAdmin_30022_export() {
		$('#sysAdmin_30022_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/loanApplication_exportLoanApplication.action'
			    	});
	}
	//表单查询
	function sysAdmin_30022_searchFun10() {
		$('#sysAdmin_30022_datagrid').datagrid('load', serializeObject($('#sysAdmin_30022_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_30022_cleanFun10() {
		$('#sysAdmin_30022_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_30022_searchForm" method="post" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间</th>
					<td><input name="cdate" class="easyui-datebox" data-options="editable:false" style="width: 170px;"/>
					</td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_30022_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_30022_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_30022_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

