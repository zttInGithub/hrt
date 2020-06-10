<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--待办队列--%>
<script type="text/javascript">

	$(function() {
		$('#sysAdmin_todo_datagrid').datagrid({
			url :'${ctx}/sysAdmin/todo_listTodos.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'todoID',
			sortOrder: 'desc',
			idField : 'todoID',
			columns : [[{
				title : '待办编号',
				field : 'todoID',
				width : 100,
				sortable : true,
				align : 'center',
				formatter: function(value,row,index){
					value="00000"+value;
					var todoIDfo=value.substring(value.length-6,value.length);
					return "<a style='color: #4169E1;text-decoration: underline;cursor:pointer;' onclick='sysAdmin_todo_urlFun("+row.todoID+")'>"+todoIDfo+"</a>";
				}
			},{
				title :'待办主题',
				field :'msgTopic',
				width : 100,
				sortable : true
			},{
				title :'业务类别',
				field :'bizTypeName',
				width : 100
			},{
				title :'审批单位',
				field :'unnoName',
				width : 100
			},{
				title :'经办单位',
				field :'msgSendUnitName',
				width : 100
			},{
				title :'经办用户',
				field :'msgSendName',
				width : 100
			},{
				title :'交易代码',
				field :'tranCode',
				width : 100,
				sortable : true
			},{
				title :'待办状态',
				field :'statusName',
				width : 100,
				sortable : true,
				formatter: function(value,row,index){
					if(value==1){
						return value;
					}else{
						return value;
					}
				}
			},{
				title :'提交时间',
				field :'msgSendTime',
				width : 100,
				sortable : true
			}]]	
		});
	});
	
	//添加待办任务
	function sysAdmin_todo_addFun() {
		$('<div id="sysAdmin_todo_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加待办任务</span>',
			width: 450,   
		    height:380, 
		    closed: false,
		    href: '${ctx}/frame/sysadmin/todo/00121.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_todo_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/todo_addTodo.action',
		    			success:function(data) {
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_todo_datagrid').datagrid('reload');
					    			$('#sysAdmin_todo_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_todo_addDialog').dialog('destroy');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
			    		}
			    	});
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_todo_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//页面跳转
	function sysAdmin_todo_urlFun(todoID){
 		$.ajax({
			url: '${ctx}/sysAdmin/todo_updateStruts.action',
			type:'post',
			data:{'todoID':todoID},
			dataType:'json',
			success: function(data, textStatus, jqXHR) {
	        	Center_addTabFun(data.text,data.src);
	        },
		    error: function () {
	        	$.messager.alert('提示', '页面跳转出错！');
		    }
	    });
	}
	
</script>

<table id="sysAdmin_todo_datagrid" style="overflow: hidden;"></table>

