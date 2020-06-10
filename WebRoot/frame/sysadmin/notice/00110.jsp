<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--发布公告--%>
<script type="text/javascript">
	$(function() {
		$('#sysAdmin_notice_datagrid').datagrid({
			url :'${ctx}/sysAdmin/notice_listNoticeRelease.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'msgSendTime',
			sortOrder: 'desc',
			idField : 'noticeID',
			columns : [[{
				title : '消息编号',
				field : 'noticeID',
				width : 100,
				hidden : true
			},{
				title :'发布日期',
				field :'msgSendTime',
				width : 100
			},{
				title :'消息标题',
				field :'msgTopic',
				width : 100,
				formatter: function(value,row,index){
					return "<a onclick='sysAdmin_notice_dialogFun("+index+","+row.noticeID+")'>"+value+"</a>";
				}
			},{
				title :'接收机构',
				field :'msgReceUnitName',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_notice_dialogFun('+index+','+row.noticeID+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_notice_delFun('+row.noticeID+')"/>';
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'添加公告',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_notice_addFun();
					}
				}]			
		});
	});
	
	//添加公告
	function sysAdmin_notice_addFun() {
		var de_left;
		var de_top;
	
		$('<div id="sysAdmin_notice_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加公告</span>',
			width: 700,   
		    height: 600,
		    closed: false,
		    href: '${ctx}/frame/sysadmin/notice/00111.jsp',  
		    modal: true,
		    onOpen:function(){ 
				  //dialog原始left
				  de_left=$('#sysAdmin_notice_addDialog').panel('options').left; 
				     //dialog原始top
				   de_top=$('#sysAdmin_notice_addDialog').panel('options').top;  
			},
		    onMove:function(left,top){ //鼠标拖动时事件
			   var body_width1=document.body.offsetWidth;//body的宽度
			   var body_height1=document.body.offsetHeight;//body的高度
			   var dd_width= $('#sysAdmin_notice_addDialog').panel('options').width;//dialog的宽度
			   var dd_height= $('#sysAdmin_notice_addDialog').panel('options').height;//dialog的高度    
			   if(left<1||left>(body_width1-dd_width)||top<1||top>(body_height1-dd_height)){
			      $('#sysAdmin_notice_addDialog').dialog('move',{  
			   left:de_left,  
			   top:de_top  
			   }); 
			    }
			},
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_notice_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/notice_addNotice.action',
		    			success:function(data) {
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_notice_datagrid').datagrid('reload');
					    			$('#sysAdmin_notice_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_notice_addDialog').dialog('destroy');
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
					$('#sysAdmin_notice_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//修改公告
	function sysAdmin_notice_dialogFun(index,noticeID){
		var de_left;
		var de_top;
		$('<div id="sysAdmin_notice_descWindow"/>').dialog({
			title: '<span style="color:#157FCC;">查看公告</span>',
			width: 740,   
		    height: 600,
		    closed: false,
		    href: '${ctx}/frame/sysadmin/notice/00114.jsp?noticeID='+noticeID,
		    modal: true,
		    onOpen:function(){ 
				  //dialog原始left
				  de_left=$('#sysAdmin_notice_descWindow').panel('options').left; 
				     //dialog原始top
				   de_top=$('#sysAdmin_notice_descWindow').panel('options').top;  
			},
		    onMove:function(left,top){ //鼠标拖动时事件
			   var body_width1=document.body.offsetWidth;//body的宽度
			   var body_height1=document.body.offsetHeight;//body的高度
			   var dd_width= $('#sysAdmin_notice_descWindow').panel('options').width;//dialog的宽度
			   var dd_height= $('#sysAdmin_notice_descWindow').panel('options').height;//dialog的高度    
			   if(left<1||left>(body_width1-dd_width)||top<1||top>(body_height1-dd_height)){
			      $('#sysAdmin_notice_descWindow').dialog('move',{  
			   left:de_left,  
			   top:de_top  
			   }); 
			    }
			},
		    onLoad:function() {
		    	var rows = $('#sysAdmin_notice_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_00114_showDiv').form('load', row);
			},
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//删除公告
	function sysAdmin_notice_delFun(noticeID) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/notice_deleteNotice.action",
					type:'post',
					data:{"ids":noticeID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_notice_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_notice_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_notice_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_notice_datagrid').datagrid('unselectAll');
			}
		});
	}
	
</script>

<table id="sysAdmin_notice_datagrid" style="overflow: hidden;"></table>

