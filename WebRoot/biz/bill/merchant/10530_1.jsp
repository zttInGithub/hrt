<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10530_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantTaskOperate_queryMerchantRiskTaskDatabaodan.action',
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
			sortName: 'bmtkid',
			sortOrder: 'desc',
			idField : 'bmtkid',
			columns : [[{
				title : '审批BMTKID',
				field : 'bmtkid',
				checkbox:true
			},{
				title : '工单编号',
				field : 'taskId',
				width : 200
			},{ 
				title :'商户编号',
				field :'mid',
				width : 130
			},{
				title :'商户全称',
				field :'rname',
				width : 100 
			},{
				title :'申请日期',
				field :'mainTainDate',
				width : 140 
			},{
				title :'受理日期',
				field :'approveDate',
				width : 140 
			},{ 
				title :'授权状态',
				field :'approveStatus',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=="K"){ 
						return '退回'; 
					}else if(value=="Z"){
						return '待审核';
					}else{
						return '审核通过'; 
					}
				} 
			},{
				title : '工单描述',
				field : 'taskContext',
				width : 100,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title : '受理描述',
				field : 'processContext',
				width : 100,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{ 
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatus=='Z'){
							return '<img src="${ctx}/images/query_search.png" title="查看" onclick="sysAdmin_machineInfoScan_serchFun('+row.type+","+row.bmtkid+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_remove.png" title="取消" onclick="sysAdmin_machineInfo_removeFun('+row.index+","+row.bmtkid+')"/>&nbsp;&nbsp'; 
					}else{
							return '<img src="${ctx}/images/query_search.png" title="查看" onclick="sysAdmin_machineInfoScan_serchFun('+row.type+","+row.bmtkid+')"/>&nbsp;&nbsp';
					}
					
				} 
			}]],
			toolbar:[{
				id:'btn_add',
				text:'工单申请',  
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_taskDetailAdd();
				}
			}/**,{
				id:'btn_scanAdd',
				text:'扫码工单申请',  
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_taskScanDetailAdd();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10530Selected_exportFun();
			    }
		}**/]  
		});  
		  
	}); 
	//查看明细 
	function sysAdmin_machineInfo_serchFun(type,bmtkid1) {
		//商户基本信息明细
			$('<div id="sysAdmin_10530_dialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单明细</span>', 
			width: 500,   
			    height: 300, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10532.jsp?bmtkid1='+bmtkid1,  
			    modal: true,
			    buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
			$('#sysAdmin_10530_dialog').dialog('destroy');
			} 
			}],
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
		
	}
	//查看扫码商户工单明细
	function sysAdmin_machineInfoScan_serchFun(type,bmtkid1) {
		//商户基本信息明细
			$('<div id="sysAdmin_10534_dialog"/>').dialog({
				title: '<span style="color:#157FCC;">工单明细</span>', 
				width: 730,  
			    height: 520, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10534.jsp?bmtkid1='+bmtkid1,  
			    modal: true,
			    buttons:[{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function() {
					$('#sysAdmin_10534_dialog').dialog('destroy');
					} 
				}],
			onClose:function() {
				$(this).dialog('destroy');
				}
			});
		
	}

	//商户工单取消
	function sysAdmin_machineInfo_removeFun(index,bmtkid1){
		$.messager.confirm('确认','您确认要取消所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/merchantTaskOperate_deleteMerchantTaskDetail.action",
					type:'post',
					data:{"bmtkid":bmtkid1},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10530_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '取消记录出错！'); 
						$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
			}
		});
		}


	//清除表单内容
	function check_close_data() {
		$('#check_10530_dataForm input').val('');
	}
	function check_search_data(){
	    $('#sysAdmin_10530_datagrid').datagrid('load', serializeObject($('#check_10530_dataForm')));
	} 
	
	//扫码商户工单申请
	function sysAdmin_taskScanDetailAdd(){
		$('<div id="sysAdmin_10533_dialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>', 
			width: 900,    
			    height: 600,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10533.jsp',  
			    modal: true,
			    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_10533_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_10533_addForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantTaskDetail4_addMerchantRiskTaskData.action',
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10530_datagrid').datagrid('reload');
					    			$('#sysAdmin_10533_dialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10533_dialog').dialog('destroy');
					    			$('#sysAdmin_10530_datagrid').datagrid('reload');
					    			$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
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
			$('#sysAdmin_10533_dialog').dialog('destroy');
			}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			}
			});	
	}

	//商户工单申请
	function sysAdmin_taskDetailAdd(){
		$('<div id="sysAdmin_10531_dialog"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>', 
			width: 900,    
			    height: 600,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10531.jsp',  
			    modal: true,
			    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_10531_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_10531_addForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantTaskDetail4_addMerchantRiskTaskData.action',
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10530_datagrid').datagrid('reload');
					    			$('#sysAdmin_10531_dialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10531_dialog').dialog('destroy');
					    			$('#sysAdmin_10530_datagrid').datagrid('reload');
					    			$('#sysAdmin_10530_datagrid').datagrid('unselectAll');
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
			$('#sysAdmin_10531_dialog').dialog('destroy');
			}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
		}

	function sysAdmin_10530Selected_exportFun(){
	 	var checkedItems = $('#sysAdmin_10530_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtkid);
		});               
		var bmtkids=names.join(",");
		if(bmtkids==null||bmtkids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmtkids").val(bmtkids);
		$('#check_10530_dataForm').form('submit',{
			    		url:'${ctx}/sysAdmin/taskDataApprove_exportMerchantTaskRiskSelectedData.action'
			    	});
	}
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:20px;">
		<form id="check_10530_dataForm" style="padding-left:3%">
			<input type="hidden" id="bmtkids" name="bmtkids" />
			<table class="tableForm" >
				<tr>
						<th>商户编号：</th>
						<td><input id="mid" name="mid" style="width: 146px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>授权状态：</th>
						<td>
							<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:140px;">
			    				<option value="" selected="selected">查询全部</option>
								<option value="Y">审核通过</option>
								<option value="Z">待审核</option>
								<option value="K">退回</option>
			    			</select>&nbsp;&nbsp;&nbsp;
						</td> 
				       <td colspan="10" align="center">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data();">清空</a>	
					</td>
			    </tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10530_datagrid" style="overflow: hidden; height:600px; width: 600px; "></table>
    </div> 
</div>
		