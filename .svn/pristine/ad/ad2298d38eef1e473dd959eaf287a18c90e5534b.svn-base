<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
 
		$('#sysAdmin_merchantTaskData_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantTaskOperate_serchMerchantTaskData.action',
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
				width : 100, 
				hidden: true
			},{
				title : '工单编号',
				field : 'taskId',
				width : 100
			},{ 
				title :'商户编号',
				field :'mid',
				width : 100
			},{ 
				title :'机构编号',
				field :'unno',
				width : 100
			},{
				
				title :'商户全称',
				field :'rname',
				width : 100 
			},{
				title :'工单类别',
				field :'type',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==1){ 
						return '商户基本信息变更申请';
					}else if(value==2){
						return '商户账户/名称变更申请';
					}else if(value==3){
						return '商户费率变更申请'; 
					}else if(value==4){
						return '终端绑定变更申请'; 
					}else if(value==5){
						return '预授权业务开通申请'; 
					}else if(value==6){
						return '商户换机申请';  
					}else{
						return ''; 
					}
				} 
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
						return '<img src="${ctx}/images/query_search.png" title="查看" onclick="sysAdmin_machineInfo_serchFun('+row.type+","+row.bmtkid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="取消" onclick="sysAdmin_machineInfo_removeFun('+row.index+","+row.bmtkid+')"/>&nbsp;&nbsp'; 
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看" onclick="sysAdmin_machineInfo_serchFun('+row.type+","+row.bmtkid+')"/>&nbsp;&nbsp';
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
			}]  
		});  
		  
	}); 
	//查看明细 
	function sysAdmin_machineInfo_serchFun(type,bmtkid1) {
		//商户基本信息明细
		if(type=="1"){
			$('<div id="sysAdmin_merchantTaskDataDelete1_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">商户基本信息明细</span>', 
			width: 800,   
			    height: 500, 
			    resizable:true,
		    	maximizable:true, 
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10461.jsp?bmtkid1='+bmtkid1,  
			    modal: true,
			    buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
			$('#sysAdmin_merchantTaskDataDelete1_datagrid').dialog('destroy');
			} 
			}],
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
			}
		//商户银行信息明细
		if(type=="2"){
			$('<div id="sysAdmin_merchantTaskDataDelete2_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">商户银行信息明细</span>', 
				width: 800,   
				    height: 500, 
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/merchant/10462.jsp?bmtkid1='+bmtkid1,  
				    modal: true,
				    buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_merchantTaskDataDelete2_datagrid').dialog('destroy');
				}  
				}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
		 //商户费率信息查询
		if(type=="3"){
			$('<div id="sysAdmin_merchantTaskDataDelete3_datagrid"/>').dialog({
				title: '<span style="color:#157FCC;">商户费率信息明细</span>', 
				width: 800,   
				    height: 500,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/merchant/10463.jsp?bmtkid1='+bmtkid1,  
				    modal: true,
				    buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_merchantTaskDataDelete3_datagrid').dialog('destroy');
				}  
				}],
				onClose:function() {
				$(this).dialog('destroy');
				}
				});
		}
		if(type=="4"){
			$.messager.alert('提示', "此项申请没有更多明细。");
			}
		if(type=="5"){
			$.messager.alert('提示', "此项申请没有更多明细。");
		}
		if(type=="6"){
			$.messager.alert('提示', "此项申请没有更多明细。");
		}
		if(type=="7"){
			$.messager.alert('提示', "此项申请没有更多明细。");
		}
		
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
							$('#sysAdmin_merchantTaskData_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_merchantTaskData_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '取消记录出错！'); 
						$('#sysAdmin_merchantTaskData_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_merchantTaskData_datagrid').datagrid('unselectAll');
			}
		});
		}


	//清除表单内容
	function check_close_data() {
		$('#check_serch_dataForm input').val('');
	}
	function check_search_data(){
	    $('#sysAdmin_merchantTaskData_datagrid').datagrid('load', serializeObject($('#check_serch_dataForm')));
	} 

	//商户工单申请
	function sysAdmin_taskDetailAdd(){
		$('<div id="sysAdmin_addMerchantTaskDetail_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">工单申请</span>', 
			width: 1000,    
			    height: 600,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10450.jsp',  
			    modal: true,
			    buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
			$('#sysAdmin_addMerchantTaskDetail_datagrid').dialog('destroy');
			}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
		}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:20px;">
		<form id="check_serch_dataForm" style="padding-left:5%">
			<table class="tableForm" >
				<tr>
						<th>商户编号：</th>
						<td><input id="mid" name="mid" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>申请日期：</th>
						<td><input id="startDay" class="easyui-datebox" data-options="editable:false" name="startDay" style="width: 156px;"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="endDay" class="easyui-datebox" data-options="editable:false" name="endDay" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>授权状态：</th>
						<td>
							<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:155px;">
			    				<option value="" selected="selected">查询全部</option>
								<option value="Y">审核通过</option>
								<option value="Z">待审核</option>
								<option value="K">退回</option>
			    			</select>
						</td> 
				</tr>
				
				<tr>
				      <td></td><td></td><td></td>
				       <td>
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
      <table id="sysAdmin_merchantTaskData_datagrid" style="overflow: hidden; height:600px; width: 600px; "></table>
    </div> 
</div>
		