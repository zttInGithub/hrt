<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_machineFind_datagrid').datagrid({
			url :'${ctx}/sysAdmin/machineApprove_initFind.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'orderID',
			sortOrder: 'DESC',
			idField : 'bmoID',
			columns : [[{
				title : '唯一编号',
				field : 'bmoID',
				width : 100,
				hidden:true
			},{
				title :'订单编号',
				field :'orderID',
				width : 100,
				sortable:true
			},{
				title :'机构名称',
				field :'unName',
				width : 100
			},{
				title :'交易日期',
				field :'txnDay',
				width : 100,
				sortable:true
			},{
				title :'订单金额',
				field :'orderAmount',
				width : 100,
				sortable:true
			},{
				title :'缴款时间',
				field :'amountConfirmDate',
				width : 100,
				sortable:true
			},{
				title :'快递单号',
				field :'expressBill',
				width : 100
			},{
				title :'摘要信息',
				field :'minfo',
				width : 100
			},{
				title :'发货时间',
				field :'sendConfirmDate',
				width : 100
			},{
				title :'状态',
				field :'status',
				width : 100,
				formatter : function(value,row,index) {
					if(value==0){
						return "未发货";
					}else if(value==1){
						return "订单取消";
					}else if(value==2){
						return "审核通过";
					}else if(value==3){
						return "缴款成功";
					}else if(value==4){
						return "发货中";
					}else if(value==5){
						return "已收货";
					}else{
						return value;
					}
				}
			},{
				title :'受理描述',
				field :'processContext',
				width : 100
			}/* ,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="审批" style="cursor:pointer;" onclick="sysAdmin_machineFind_editFun('+index+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_machineFind_delFun('+row.bmaID+')"/>';
				}
			} */]]/* ,
			toolbar:[{
					text:'修改',
					iconCls:'icon-edit'
				},{
					text:'删除',
					iconCls:'icon-remove'
				},{
					id:'btn_add',
					text:'添加',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_machineFind_addFun();
					}
				}] */ ,
				view: detailview,
			detailFormatter:function(index,row){
				return '<div"><table class="ddv"></table></div>';
			},
			onExpandRow: function(index,row){
				var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
				ddv.datagrid({
					url:'${ctx}/sysAdmin/machineApprove_find.action?ids='+row.bmoID,
					singleSelect:true,
					rownumbers:true,
					loadMsg:'',
					height:'auto',
					columns:[[
							{field:'MACHINEMODEL',title:'机具型号',width:100},
							{field:'MACHINETYPE',title:'机具类型',width:150,
							formatter:function(value,row,index){
								if(value=='1'){
									return '有线(拨号)';
								}else if(value=='2'){
									return '有线(拨号和网络)';
								}else if(value=='3'){
									return '无线';
								}else if(value=='4'){
									return '手刷';
								}else{
									return '其它';
								}
							}
							},
							{field:'MACHINEPRICE',title:'机具单价',width:100},
							{field:'ACTIONPRICE',title:'实际执行单价',width:150},
							{field:'ORDERNUM',title:'订购数量',width:100},
							{field:'ORDERAMOUNT',title:'订单金额',width:100},
						]],
					onResize:function(){
							$('#sysAdmin_machineFind_datagrid').datagrid('fixDetailRowHeight',index);
					},
					onLoadSuccess:function(){
							setTimeout(function(){
								$('#sysAdmin_machineFind_datagrid').datagrid('fixDetailRowHeight',index);
							},0);
						}
					});
					$('#sysAdmin_machineFind_datagrid').datagrid('fixDetailRowHeight',index);
				}
				
		});
	});
	
	
	/* //审批机具订单
	function sysAdmin_machineFind_editFun(index) {
		$('<div id="sysAdmin_machineFind_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审批订单</span>',
			width: 380,   
		    height: 340, 
		    closed: false,
		    href: '${ctx}/biz/bill/pos/10231.jsp',  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_machineFind_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.menuIds);    	
		    	$('#sysAdmin_machineFind_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_machineFind_editForm').form('submit', {
						url:'${ctx}/sysAdmin/machineFind_editApp.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_machineFind_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_machineFind_datagrid').datagrid('reload');
					    			$('#sysAdmin_machineFind_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_machineFind_editDialog').dialog('destroy');
					    			$('#sysAdmin_machineFind_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_machineFind_editDialog').dialog('destroy');
					$('#sysAdmin_machineFind_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_role_datagrid').datagrid('unselectAll');
			}
		});
	} */
	
	/* //删除机具订单
	function sysAdmin_machineFind_delFun(bmaID) {
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/machineApprove_delete.action",
					type:'post',
					data:{"ids":bmaID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_machineFind_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_machineFind_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_machineFind_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_machineFind_datagrid').datagrid('unselectAll');
			}
		});
	} */
	
	//表单查询
	function sysAdmin_machineFind_searchFun() {
		$('#sysAdmin_machineFind_datagrid').datagrid('load', serializeObject($('#sysAdmin_machineFind_searchForm')));
	}

	//清除表单内容
	function sysAdmin_machineFind_cleanFun() {
		$('#sysAdmin_machineFind_searchForm input').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:110px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_machineFind_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>订单编号：</th>
					<td><input name="orderID" style="width: 316px;" /></td>
				</tr>
				<tr>
					<th>交易日期：</th>
					<td><input name="txnDayStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="txnDayEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_machineFind_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_machineFind_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_machineFind_datagrid"></table>
    </div> 
</div>

