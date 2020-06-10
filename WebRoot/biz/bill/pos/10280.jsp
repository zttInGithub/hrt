<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10280_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineTaskData_serchMachineTaskData.action',
			fit : true,
			fitColumns : true,  
			border : false,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true, 
			pagination : true,
			striped: true,
			pageSize : 10,
			pageList : [ 10,15 ],
			sortName: 'TASKID',
			sortOrder: 'desc',
			idField : 'maintainDate',
			columns : [[{
				title : '主键',
				field : 'BMTDID',
				width : 100, 
				hidden: true
			},{
				title : '主键一',
				field : 'BMTID',
				width : 100, 
				hidden: true
			},{
				title : '工单编号',
				field : 'TASKID',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{ 
				title :'商户编号',
				field :'MID',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'原终端编号',
				field :'BMTIDNAME',
				width : 100
			},{
				
				title :'新终端编号',
				field :'TID',
				width : 100 
			},{
				title : '新机具型号',
				field : 'BMAIDNAME',
				width : 100
			},{
				title : '工单类别',
				field : 'TYPE',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '装机';
					}else if(value=='2'){
						return '换机';
					}else if(value=='3'){
						return '撤机';
					}else{
						return '服务';
					}
				}
			},{
				title : '机具SN号',
				field : 'MACHINESN',
				width : 100,
				sortable:true
			},{
				title : '机具SIM卡号',
				field : 'SIMTEL',
				width : 100,
				sortable:true
			},{
				title : '开始时间',
				field : 'TASKSTARTDATE',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
					}  
				}
			},{
				title : '测试时间',
				field : 'TASKSTEP1DATE',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
					}  
				}
			},{
				title : '派工时间',
				field : 'TASKSTEP2DATE',
				width : 100,
				sortable:true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title='"+value+"'>"+value+"</span>";
					}  
				}
			},{
				title : '授权状态',
				field : 'APPROVESTATUS',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='Y'){
						return '已审批';
					}else if(value=='Z'){
						return '待审批';
					}else if(value=='K'){
						return '退回';
					}else{
						return '无需审批';
					}
				}
			},{
				title :'换机类型',
				field :'CHANGETYPE',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '加号换机';
					}else if(value=='2'){
						return '不加号换机';
					}else{
						return '';
					}
				}
			},{
				title : '备注',
				field : 'REMARKS',
				width : 100,
				sortable:true
			},{ 
				title :'操作',
				field :'operation',
				width : 180,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.TASKSTEP1DATE == null){
						if(row.TYPE==1){
							return '<img src="${ctx}/images/frame_remove.png" title="取消装机" style="cursor:pointer;" onclick="sysAdmin_10280_chejiconfir('+row.BMTDID+','+row.BMTID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/1.png" title="测试确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation1('+row.BMTDID+')"/>';
						}else if(row.TYPE==2){
							return  '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/query_search2.png" title="上次换机记录" style="cursor:pointer;" onclick="sysAdmin_10280_MachineTaskDataInfoFun('+row.BMTIDNAME+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords1('+row.BMTDID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/1.png" title="测试确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation1('+row.BMTDID+')"/>';
						}else if(row.TYPE==3){
							return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords2('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/1.png" title="测试确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation1('+row.BMTDID+')"/>';
						}else{
							return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords3('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/1.png" title="测试确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation1('+row.BMTDID+')"/>';
						}
					}
					if(row.TASKSTEP2DATE == null){
						if(row.TYPE==1){
							return '<img src="${ctx}/images/frame_remove.png" title="取消装机" style="cursor:pointer;" onclick="sysAdmin_10280_chejiconfir('+row.BMTDID+','+row.BMTID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/2.png" title="派工确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation2('+row.BMTDID+')"/>';
						}else if(row.TYPE==2){
							return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/query_search2.png" title="上次换机记录" style="cursor:pointer;" onclick="sysAdmin_10280_MachineTaskDataInfoFun('+row.BMTIDNAME+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords1('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/2.png" title="派工确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation2('+row.BMTDID+')"/>';
						}else if(row.TYPE==3){
							return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords2('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/2.png" title="派工确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation2('+row.BMTDID+')"/>';
						}else{
							return  '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords3('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/images/2.png" title="派工确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation2('+row.BMTDID+')"/>';
						}
					}
					if(row.TASKCONFIRMDATE == null){
						if(row.TYPE==1){
							return '<img src="${ctx}/images/frame_remove.png" title="取消装机" style="cursor:pointer;" onclick="sysAdmin_10280_chejiconfir('+row.BMTDID+','+row.BMTID+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords('+row.BMTDID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/3.png" title="完成确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation('+row.BMTDID+')"/>';
						}else if(row.TYPE==2){
							return  '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/query_search2.png" title="上次换机记录" style="cursor:pointer;" onclick="sysAdmin_10280_MachineTaskDataInfoFun('+row.BMTIDNAME+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords1('+row.BMTDID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/3.png" title="完成确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation('+row.BMTDID+')"/>';
						}else if(row.TYPE==3){
							return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords2('+row.BMTDID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/3.png" title="完成确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation('+row.BMTDID+')"/>';
						}else{
							return  '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10280_merchantInfoFun('+row.MID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10280_trues('+row.BMTDID+')"/>&nbsp;&nbsp'+
								 	'<img src="${ctx}/jquery/jquery-easyui-1.3.1/themes/icons/ok.png" title="打印工单" onclick="exportwords3('+row.BMTDID+')"/>&nbsp;&nbsp'+
									'<img src="${ctx}/images/3.png" title="完成确认" style="cursor:pointer;" onclick="sysAdmin_10280_confirmation('+row.BMTDID+')"/>';
						}
					}
				}
			}]]
		});  
	}); 
	//装机 
	function exportwords(bmtdID){
		$('#wordexports').form('submit', {    
	    	url:'${ctx}/sysAdmin/machineTaskData_exportMachineTaskDataOne.action?bmtdID='+bmtdID    
		});  
	}
	//换机
	function exportwords1(bmtdID){
		$('#wordexports1').form('submit', {    
	    	url:'${ctx}/sysAdmin/machineTaskData_exportMachineTaskDataOne1.action?bmtdID='+bmtdID   
		});  
	}
	//撤机
	function exportwords2(bmtdID){
		$('#wordexports2').form('submit', {    
	    	url:'${ctx}/sysAdmin/machineTaskData_exportMachineTaskDataOne2.action?bmtdID='+bmtdID    
		});  
	}
	//服务
	function exportwords3(bmtdID){
		$('#wordexports3').form('submit', {    
	    	url:'${ctx}/sysAdmin/machineTaskData_exportMachineTaskDataOne3.action?bmtdID='+bmtdID    
		});  
	}
		    
	function sysAdmin_10280_merchantInfoFun(mid){
		$('<div id="sysAdmin_10281_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10281.jsp?mid='+mid,
		    modal: false,
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10281_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10280_MachineTaskDataInfoFun(bmtidname){
		$('<div id="sysAdmin_10287_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">上次换机记录</span>',
			width: 800,
		    height:350, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10287.jsp?bmtidname='+bmtidname,
		    modal: false,
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10287_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10280_trues(bmtdID){
		$('<div id="sysAdmin_10284_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认工单申请</span>',
			width: 450,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10284.jsp',
		    modal: false,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantList_10284').form('submit', {
						url:'${ctx}/sysAdmin/machineTaskData_updateMachineTaskData.action?bmtdID='+bmtdID,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10280_datagrid').datagrid('reload');
					    			$('#sysAdmin_10284_queryDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
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
					$('#sysAdmin_10284_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10280_confirmation(bmtdID){
		$.messager.confirm('确认','您确认完成吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/machineTaskData_updateMachineTaskDataTask.action',
		        	data: {"bmtdID": bmtdID},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_10280_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	//确认1
	function sysAdmin_10280_confirmation1(bmtdID){
		$.messager.confirm('确认','您确认完成吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/machineTaskData_updateMachineTaskDataTask1.action',
		        	data: {"bmtdID": bmtdID},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_10280_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	//确认2
	function sysAdmin_10280_confirmation2(bmtdID){
		$.messager.confirm('确认','您确认完成吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/machineTaskData_updateMachineTaskDataTask2.action',
		        	data: {"bmtdID": bmtdID},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_10280_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	//表单查询
	function sysAdmin_10280_searchFun() {
		var txnDay = $('#createDateStart_10280').datebox('getValue');
    	var txnDay1= $('#createDateEnd_10280').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if((txnDay!=""&&txnDay1=="")||(txnDay1!=""&&txnDay=="")){
        	$.messager.alert('提示', "请选择开始日期 和 截止日期 ");
        	return;
        }
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_10280_datagrid').datagrid('load', serializeObject($('#sysAdmin_10280_searchForm'))); 
		}
	}

	//清除表单内容
	function sysAdmin_10280_cleanFun() {
		$('#sysAdmin_10280_searchForm input').val('');
	}
	
	
	//是否撤机
	function sysAdmin_10280_chejiconfir(bmtdID,bmtID){
		$.messager.confirm('确认','您确认取消装机吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/machineTaskData_updateMaintainType.action',
		        	data: {"bmtdID": bmtdID,"bmtID":bmtID},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_10280_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10280_datagrid').datagrid('unselectAll');
			}   
		}); 
	}			
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:110px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10280_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>商户编号：</th>
					<td><input name="mid" style="width: 316px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原终端号：</th>
					<td><input name="tidBmtID" style="width: 316px;" /></td>
				</tr>
				<tr>
					<th>商户名称：</th>
					<td><input name="rname" style="width: 316px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间：</th>
					<td><input name="createDateStart" id="createDateStart_10280" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="createDateEnd" id="createDateEnd_10280" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
				</tr>
				<tr>
					<th>流程时间：</th>
					<td>
						<select name="taskStartDateName" id="taskStartDateName" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
							<option value=""></option>
			   				<option value="1">待测试工单</option>
			   				<option value="2">待派工工单</option>
			   				<option value="3">待完成工单</option>
			   			</select>
					</td>
					
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10280_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10280_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false" style="overflow: hidden;">
		<table id="sysAdmin_10280_datagrid" style="overflow: hidden; height:600px; width: 600px; "></table>
		<form action="" method="post" id="wordexports"></form>
		<form action="" method="post" id="wordexports1"></form>
		<form action="" method="post" id="wordexports2"></form>
		<form action="" method="post" id="wordexports3"></form>
    </div> 
</div>

		