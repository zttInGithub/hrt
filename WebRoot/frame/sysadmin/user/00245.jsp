<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_00245_datagrid').datagrid({	
			url : '${ctx}/sysAdmin/user_listUserLoginLog.action',
			fit:true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 15 ],
			idField : 'slid',
 			sortName : 'slid',
			sortOrder : 'desc',
			columns : [[{
				title : 'slid',
				field : 'slid',
				width : 100,
				hidden : true
			},{
				title :'用户名',
				field :'login_name',
				width : 100
			},{
				title :'登陆/注销',
				field :'login_type',
				width : 100,
				formatter : function(value,row,index) {
					if("L"==value){
						return "登陆";
					}else{
						return "注销";
					}
				}
			},{
				title : '提示信息',
				field : 'login_message',
				width : 100
			},{
				title :'操作时间',
				field :'logintime',
				width : 100
			}]]
		});
	});

	//表单查询
	function sysAdmin_00245_searchFun() {
		$('#sysAdmin_00245_datagrid').datagrid('load', serializeObject($('#sysAdmin_00245_searchForm')));
	}

	//清除表单内容
	function sysAdmin_00245_cleanFun() {
		$('#sysAdmin_00245_searchForm input').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_00245_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>用户名</th>
					<td><input name="loginName" style="width: 168px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;登陆/注销</th>
					<td>
						<select name="login_type" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="L">登陆</option>
		    				<option value="Q">注销</option>
		    			</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;操作时间</th>
					<td>
						<input name="logintime" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					<td colspan="4" style="text-align: center;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_00245_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_00245_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_00245_datagrid"></table>
    </div> 
</div>



