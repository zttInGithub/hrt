<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
		$('#sysAdmin_quartz_datagrid').datagrid({
			url : '${ctx}/quartz/quartz_queryQrtzTriggers.action',
			fit : true,
			frozen:true,
			striped:true,
			fitColumns:true,
			border:false,
			nowrap : true,
			rownumbers:true,
			columns:[[{
				title:'任务名称',
				field:'DISPLAY_NAME',
				width:100		
			},{
				title:'任务分组',
				field:'TRIGGER_GROUP',
				width:100
			},{
				title:'下次执行时间',
				field:'NEXT_FIRE_TIME',
				width:100
			},{
				title:'上次执行时间',
				field:'PREV_FIRE_TIME',
				width:100
			},{
				title:'任务状态',
				field:'DISPLAY_STATE',
				width:100
			},{
				title:'创建时间',
				field:'START_TIME',
				width:100
			},{
				title:'结束时间',
				field:'END_TIME',
				width:100
			},{
				title:'动作命令',
				field:'operation',
				width:250,
				align:'center',
				formatter:function(value,row,index){
					return '<input type="button" value="暂停" id="pause" onclick="doCmd(\'pause\',\''+row.TRIGGER_NAME+'\',\''+row.TRIGGER_GROUP+'\',\''+row.TRIGGER_STATE+'\')"/>&nbsp;&nbsp;'+
					'<input type="button" value="恢复" id="resume" onclick="doCmd(\'resume\',\''+row.TRIGGER_NAME+'\',\''+row.TRIGGER_GROUP+'\',\''+row.TRIGGER_STATE+'\')"/>&nbsp;&nbsp;'+
					'<input type="button" value="删除" id="remove" onclick="doCmd(\'remove\',\''+row.TRIGGER_NAME+'\',\''+row.TRIGGER_GROUP+'\',\''+row.TRIGGER_STATE+'\')"/>';
				}
			}]],
			toolbar:[{
				id:'btn_add',
				text:'添加调度任务',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_quartz_addQuartz();
				}
			}]
		});
	});
	
	//添加
	function sysAdmin_quartz_addQuartz(){
		$('<div id="sysAdmin_quartz_addDialog"/>').dialog({
			title:'<span style="color:#157FCC;">添加任务信息</span>',
			width:700,
			height:400,
			href:'${ctx}/frame/sysadmin/quartz/00251.jsp',
			closed: false,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
					$('#sysAdmin_quartz_addForm').form('submit',{
						url:'${ctx}/quartz/quartz_insertQrtzTrigger.action',
						onSubmit: function(){
							var temp=$('#p_triggerName').val();    
							if($.trim(temp)==""){
								$('#p_triggerName').val("").focus();
								return false;
							}
							//cron
							temp=window.cron.getvalues();
							$('#sysAdmin_quartz_addForm').append('<input type="hidden" id="p_cron" name="p_cron">');
							$('#p_cron').val(temp);
							//开始时间
							temp=window.cron.kssj();
							$('#sysAdmin_quartz_addForm').append('<input type="hidden" id="p_kssj" name="p_kssj">');
							$('#p_kssj').val(temp);
							//结束时间
							temp=window.cron.jssj();
							$('#sysAdmin_quartz_addForm').append('<input type="hidden" id="p_jssj" name="p_jssj">');
							$('#p_jssj').val(temp);
					    },
						success:function(data){
							var res = $.parseJSON(data);
							if (res.sessionExpire) {
								window.location.href = getProjectLocation();
							}else{
								if(res.success) {
					    			$.messager.show({
										title : '提示',
										msg : res.msg
									});
					    		}else{
									$.messager.alert('提示', res.msg);
					    		}
					    	}
					    	$('#sysAdmin_quartz_datagrid').datagrid('unselectAll');
							$('#sysAdmin_quartz_datagrid').datagrid('reload');
					    	$('#sysAdmin_quartz_addDialog').dialog('destroy');
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#sysAdmin_quartz_addDialog').dialog('destroy');
				}
			}]
		});
	}
	
	//暂停、恢复或删除
	function doCmd(state,triggerName,group,triggerState){
		if(state == 'pause' && triggerState == 'PAUSED'){
			$.messager.alert('提示', '该任务己经暂停！');
			return;
		}
	    if(state == 'resume' && triggerState != 'PAUSED'){
			$.messager.alert('提示', '该任务正在运行中！');
			return;
		}
		
		//客户端两次编码，服务端再解码，否测中文乱码 
		triggerName = encodeURIComponent(encodeURIComponent(triggerName));
		group = encodeURIComponent(encodeURIComponent(group));
		
        $.ajax({
            url: '${ctx}/quartz/quartz_isTrigdger.action?action='+state+'&triggerName='+triggerName+'&group='+group,
            dataType:'json',
            timeout: 30000,
            cache: false,
            success: function(data, textStatus){
            	if(data.success){
            		$.messager.show({
            			title:'提示',
            			msg:data.msg
            		});
            		$('#sysAdmin_quartz_datagrid').datagrid('reload');
            	}else {
		        	$.messager.alert('提示', data.msg);
		        	$('#sysAdmin_quartz_datagrid').datagrid('unselectAll');
			    }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
            	$.messager.alert('提示', '操作记录出错！');
			    $('#sysAdmin_quartz_datagrid').datagrid('unselectAll');
            }
        });
	}
	
</script>

<table id="sysAdmin_quartz_datagrid" style="overflow:hidden;" ></table>