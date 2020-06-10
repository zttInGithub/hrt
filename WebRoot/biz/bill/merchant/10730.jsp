<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!-- 黑名单商户维护 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_hotmerchant_datagrid').datagrid({	
			url : '${ctx}/sysAdmin/hotCard_queryHotCardMerchant.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ,50 ,100 ],
			remoteSort:true,
			idField : 'phid',
			sortOrder : 'desc',
			columns : [[{
				title :'phid',
				field :'phid',
				width : 150,
				checkbox: true
			},{
				title :'商户名称',
				field :'tname',
				width : 200
			},{
				title :'法人身份证',
				field :'identificationNumber',
				width : 200
			},{
				title :'营业执照号',
				field :'license',
				width : 200
			},{
				title :'入账卡号',
				field :'bankAccNo',
				width : 200
			},{
				title :'SN号',
				field :'sn',
				width : 200
			},{
				title :'操作人',
				field :'cby',
				width : 200
			},{
				title :'备注',
				field :'remarks',
				width : 100
			}]],
			toolbar:[{
					text:'图标说明：'
				},{
					id:'btn_add',
					text:'添加黑名单商户',
					iconCls:'icon-add',
					handler:function(){
						hotMerchant_addFun();
					}
				},{
					id:'btn_edit',
					text:'修改黑名单商户',
					iconCls:'icon-edit',
					handler:function(){
						hotMerchant_EditFun();
					}
				},{
					id:'btn_edit',
					text:'勾选批量修改',
					iconCls:'icon-edit',
					handler:function(){
						hotMerchant_EditMoreFun();
					}
				},{
					id:'btn_del',
					text:'删除黑名单商户',
					iconCls:'icon-remove',
					handler:function(){
						hotMerchant_deleteFun();
					}
				},{
					id:'btn_addExcel',
					text:'批量导入黑名单商户',
					iconCls:'icon-add',
					handler:function(){
						hotMerchant_importExcelFun();
					}
				},{
					id:'btn_export',
					text:'勾选导出黑名单商户',
					iconCls:'icon-query-export',
					handler:function(){
						hotMerchant_exportExcelFun();
					}
				}]
		});

	});

	//表单查询
	function cardTable_searchFun() {
		$('#sysAdmin_hotmerchant_datagrid').datagrid('load', serializeObject($('#hotMerchant_searchForm')));
	}

	//清除表单内容
	function cardTable_cleanFun() {
		$('#hotMerchant_searchForm input').val('');
	}

	//取消
	 function data_cleanFun(){
            $('#hotMerchant_searchForm').form('clear');
        }
//添加黑名单商户
	function hotMerchant_addFun() {
		$('<div id="hotmerchant_add"/>').dialog({
			title: '添加黑名单商户信息',
			width: 500,   
		    height: 300, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10731.jsp',  
		    modal: true,
		    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function(){
		    		var dia = $('#hotmerchant_add');
					$('#hotmerchant_addForm').form('submit', {
						url:'${ctx}/sysAdmin/hotCard_addHotCardMerchant.action',
					    success:function(data){   
							var res = $.parseJSON(data);
							if (res.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if(res.success) {
									dia.dialog('destroy');
									$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
									$('#sysAdmin_hotmerchant_datagrid').datagrid('reload');
									
									$.messager.show({
										title : '提示',
										msg : res.msg
									});
								} else {
									dia.dialog('destroy');
									$.messager.alert('提示', res.msg);
								}  
							}
					    }   
					});
				}
			},{
				text:'取消',
				handler:function(){
					$('#hotmerchant_add').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}



//修改黑名单商户 
	function hotMerchant_EditFun(index) {
	  var rows = $('#sysAdmin_hotmerchant_datagrid').datagrid('getSelections');
	
      if(rows.length != 1){
        $.messager.alert('提示','请选择一条数据!','error');
       return false;
      }
      var row = rows[0];
		$('<div id="hotmerchant_edit"/>').dialog({
			title: '修改黑名单商户信息',
			width: 450,   
		    height: 300, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10732.jsp',  
		    modal: true,
		    onLoad: function() {
		    	row.roleIds = stringToList(row.roleIds);  
		    	$('#hotmerchant_editForm').form('load', row);
			},
			 buttons:[{ 
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var dia = $('#hotmerchant_edit');
						$('#hotmerchant_editForm').form('submit', {
							url:'${ctx}/sysAdmin/hotCard_updateHotCardMerchant.action',
						    success:function(data){   
								var res = $.parseJSON(data);
								if (res.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(res.success) {
										dia.dialog('destroy');
										$('#sysAdmin_hotmerchant_datagrid').datagrid('reload');
										$.messager.show({
											title : '提示',
											msg : res.msg
										});
									} else {
										dia.dialog('destroy');
										$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
										$.messager.alert('提示', res.msg);
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					handler:function(){
						$('#hotmerchant_edit').dialog('destroy');
						$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
				} 
		});
	}
	 
	//勾选批量修改
	function hotMerchant_EditMoreFun() {
		var checkedItems = $('#sysAdmin_hotmerchant_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.phid);
		});               
		var phids=names.join(",");
		if(phids==null||phids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		
		$('<div id="hotmerchant_edit"/>').dialog({
			title: '勾选批量修改黑名单商户信息',
			width: 450,   
		    height: 300, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10734.jsp',  
		    modal: true,
		    onLoad: function() {
		    	$("#phids_10734").val(phids); 
			},
			 buttons:[{ 
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var dia = $('#hotmerchant_edit');
						$('#hotmerchant_editForm').form('submit', {
							url:'${ctx}/sysAdmin/hotCard_updateBatchHotCardMerchant.action',
						    success:function(data){   
								var res = $.parseJSON(data);
								if (res.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(res.success) {
										dia.dialog('destroy');
										$('#sysAdmin_hotmerchant_datagrid').datagrid('reload');
										$.messager.show({
											title : '提示',
											msg : res.msg
										});
									} else {
										dia.dialog('destroy');
										$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
										$.messager.alert('提示', res.msg);
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					handler:function(){
						$('#hotmerchant_edit').dialog('destroy');
						$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
				} 
		});
	}

//删除卡表信息
	function hotMerchant_deleteFun() {
 		var rows = $('#sysAdmin_hotmerchant_datagrid').datagrid('getSelections');
      	if(rows.length != 1){
        $.messager.alert('提示','请选择一条数据!','error');
       return false;
        }
      var phid = rows[0].phid;
	 $.messager.confirm('确认','您确认要删除所选记录吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/hotCard_deleteHotCardMerchant.action',
		        	data: {"phid": phid},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_hotmerchant_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '删除记录出错！');
			        	$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_hotmerchant_datagrid').datagrid('unselectAll');
			}   
		}); 
	} 
	//导入黑名单
	function hotMerchant_importExcelFun(){
		$('<div id="sysAdmin_10730_uploadDialog"/>').dialog({
			title: '<span style="color:#157FCC;">导入黑名单商户</span>',
			width: 400,
		    height:150, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10733.jsp',  
		    modal: true,
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//导出EXCEL
	function hotMerchant_exportExcelFun(){
		var checkedItems = $('#sysAdmin_hotmerchant_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.phid);
		});               
		var phids=names.join(",");
		if(phids==null||phids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#phids").val(phids);
		$('#hotMerchant_searchForm').form('submit',{
			url:'${ctx}/sysAdmin/hotCard_exportHotCardMerchant.action'
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:9px;">
			<form id="hotMerchant_searchForm" style="padding-left:3%" method="post">
			<input type="hidden" id="phids" name=phids />
			<table class="tableForm" >				  
				<tr>
					<th>商户名称：</th>
					<td><input name="tname" style="width: 118px;" /></td>
					<th>&nbsp;法人身份证：</th>
					<td><input name="identificationNumber"  style="width: 118px;" /></td>
					<th>&nbsp;SN号：</th>
					<td><input name="sn"  style="width: 118px;" /></td>
					<th>&nbsp;入账卡号：</th>
					<td><input name="bankAccNo"  style="width: 118px;" /></td>
					<th>&nbsp;营业执照号：</th>
					<td><input name="license" style="width: 118px;" /></td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
					onclick="cardTable_searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
					onclick="cardTable_cleanFun();">清空</a>	
					</td>
				</tr>	
					
			</table>
		<br/>
		</form>
	</div>  
	
	
        <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_hotmerchant_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>