<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10480_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseAccount_listPurchaseAccountWJoin.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'paid',
			columns : [[{
				title : '付款单ID',
				field : 'paid',
				width : 100,
				hidden : true
			},{
				title : '采购单订单号',
				field : 'accountOrderID',
				width : 120
			},{
				title : '付款类型',
				field : 'accountType',
				width : 120,
				formatter: function(value,row,index) {
					if(value==1){
						return "付款核销";
					}else if(value==2){
						return "退款核销";
					}
				}
			},{
				title : '金额',
				field : 'accountAmt',
				width : 100
			},{
				title : '付款方式',
				field : 'payType',
				width : 100
			},{
				title :'状态',
				field :'accountStatus',
				width : 100,
				formatter: function(value,row,index) {
					if(value==3){
						return "发票核销";
					}else if(value==2){
						return "待审核 ";
					}else if(value==9){
						return "已审核 ";
					}
				}
			},{
				title :'备注',
				field :'accountRemark',
				width : 100
			},{
				title :'创建时间',
				field :'accountCdate',
				width : 100
			},{
				title :'创建人',
				field :'accountCby',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 60,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.accountStatus == 2){
						return '<img src="${ctx}/images/start.png" title="审核通过" style="cursor:pointer;" onclick="sysAdmin_10480_queryEditFun('+row.paid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="审核退回" style="cursor:pointer;" onclick="sysAdmin_10480k_queryEditFun('+row.paid+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				text:'付款单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10480_exportFun();
				}
			}]		
		});
	});
	
	//导出Excel
	function sysAdmin_10480_exportFun() {
		$('#sysAdmin_10480_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseAccount_exportPurchaseAccount.action',
			    	});
	}
	
	//审核通过
	function sysAdmin_10480_queryEditFun(paid){
		$.messager.confirm('确认','您确认审核通过吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/purchaseAccount_updatePurchaseAccountY.action',
		        	data: {"paid": paid},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
			        		$('#sysAdmin_10480_queryDialog').dialog('destroy');
				        	$('#sysAdmin_10480_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10480_queryDialog').dialog('destroy');
				        	$('#sysAdmin_10480_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10480_queryDialog').dialog('destroy');
			        	$('#sysAdmin_10480_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10480_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	//审核退回
	function sysAdmin_10480k_queryEditFun(paid){
		$('<div id="sysAdmin_10480K_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审核退回采购付款单</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10481.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#paid_10481').val(paid);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10481_editForm').form('submit', {
						url:'${ctx}/sysAdmin/purchaseAccount_updatePurchaseAccountK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10480_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10480_datagrid').datagrid('reload');
					    			$('#sysAdmin_10480K_editDialog').dialog('destroy');
					    			$('#sysAdmin_10480_queryDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10480K_editDialog').dialog('destroy');
					    			$('#sysAdmin_10480_queryDialog').dialog('destroy');
					    			$('#sysAdmin_10480_datagrid').datagrid('reload');
					    			$('#sysAdmin_10480_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10480K_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10480_searchFun() {
		var start = $("#10480_accountCdate").datebox('getValue');
    	var end = $("#10480_accountCdateEnd").datebox('getValue');
	   	if((!start&&end)||(!end&&start)){
	   		$.messager.alert('提示', "查询时间必须为时间段");
	   		return;
	   	}
	   	start = start.replace(/\-/gi, "/");
	   	end = end.replace(/\-/gi, "/");
	    var startTime = new Date(start).getTime();
	    var endTime = new Date(end).getTime();
	   	if ((endTime-startTime)>(1000*3600*24*30)){
	   	   	$.messager.alert('提示', "查询最长时间为30天");
	   	   	return;
	   	}
	   	if ((endTime-startTime)<0){
	   	   	$.messager.alert('提示', "起始日期需小于截止日期");
	   	   	return;
	   	}
		$('#sysAdmin_10480_datagrid').datagrid('load', serializeObject($('#sysAdmin_10480_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10480_cleanFun() {
		$('#sysAdmin_10480_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height: 80px; overflow: hidden; padding-top: 15px;">
		<form id="sysAdmin_10480_searchForm" style="padding-left: 10%" method="post">
			<table class="tableForm">
				<tr>
					<th>订单号&nbsp;&nbsp;</th>
					<td><input name="accountOrderID" style="width: 138px;" /></td>
				    
				    <th>&nbsp;&nbsp;状态&nbsp;&nbsp;</th>
					<td>
						<select name="accountStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="2">待审核</option>
		    				<option value="9">已审核</option>
		    			</select>
					</td>
					
				    <th>&nbsp;&nbsp;创建时间&nbsp;&nbsp;</th>
					<td><input name="accountCdate" id="10480_accountCdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					&nbsp;&nbsp;至&nbsp;&nbsp;<input name="accountCdateEnd" id="10480_accountCdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					
					<td colspan="4" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_10480_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_10480_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10480_datagrid" style="overflow: hidden;"></table>
	</div>
</div>
