<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <!-- 代理商提现 历史记录 -->
<script type="text/javascript">
	$(function() {
		$('#sysAdmin_10180_datagrid').datagrid({
			url : '${ctx}/sysAdmin/agentUnitTask_queryLoanUnit.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			idField : 'UNNO',
			columns : [ [ {
				title : '机构号',
				field : 'UNNO',
				width : 80
			}, {
				title : '添加时间',
				field : 'MAINTAINDATE',
				width : 80
			}, {
				title : '添加人',
				field : 'MAINTAINUSER',
				width : 80
			},{
				title :'操作',
				field :'operation',
				width : 60,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.MAINTAINTYPE=="A"){
						return '<img src="${ctx}/images/close.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10180_deleteFun('+index+')"/>';
					}
				}
			} ] ],
			toolbar:[{
				id:'btn_add',
				text:'添加贷款机构',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10180_addFun();
				}
			}]
		});
	});
	
	function sysAdmin_10180_addFun(){
		$('<div id="sysAdmin_10180_addFun"/>').dialog({
			title: '导入贷款机构',
			width: 670,   
		    height: 216,   
		    closed: false,
		    href:'${ctx}/biz/bill/agent/agentunit/10181.jsp',
		    modal: true,
			buttons:[],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//删除
	function sysAdmin_10180_deleteFun(index){
		var rows = $('#sysAdmin_10180_datagrid').datagrid('getRows');
		var row  = rows[index];
		$.ajax({
			url:'${ctx}/sysAdmin/agentUnitTask_deleteLoanUnno.action',
			type:'post',
			data:{unno:row.UNNO},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					$('#sysAdmin_10180_datagrid').datagrid('unselectAll');
    				$('#sysAdmin_10180_datagrid').datagrid('reload');
	    			$.messager.show({
						title : '提示',
						msg : data.msg
					});
				} else {
	    			$('#sysAdmin_10180_datagrid').datagrid('unselectAll');
	    			$.messager.alert('提示', data.msg);
				}
			},
			error:function() {
				$.messager.alert('提示', '通过记录出错！');
				$('#sysAdmin_10180_datagrid').datagrid('unselectAll');
			}
		});
	}

	function sysAdmin_10180_searchFun() {
		$('#sysAdmin_10180_datagrid').datagrid('load',
				serializeObject($('#sysAdmin_10180_searchForm')));
	}

	function sysAdmin_10180_cleanFun() {
		$('#sysAdmin_10180_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_10180_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 130px;" /></td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10180_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10180_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_10180_datagrid"></table>
    </div> 
</div>
