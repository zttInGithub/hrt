<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 审核盘盈盘亏 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10484_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseStorage_listPurchaseStorageWJoin.action?storageTypes=2',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'psid',
			columns : [[{
				title : '采购ID',
				field : 'psid',
				width : 100,
				hidden : true
			},{
				title : '单号',
				field : 'storageID',
				width : 100
			},{
				title : '类型',
				field : 'storageType',
				width : 100,
				formatter : function(value,row,index) {
					return value==2?"盘盈":"盘亏";
				}
			},{
				title : '时间',
				field : 'storageCdate',
				width : 100
			},{
				title :'品牌',
				field :'storageBrandName',
				width : 100
			},{
				title :'机型',
				field :'storageMachineModel',
				width : 100
			},{
				title :'数量',
				field :'storageMachineNum',
				width : 100
			},{
				title :'状态',
				field :'storageStatus',
				width : 100,
				formatter : function(value,row,index) {
					if(value==1){
						return "待提交";
					}else if(value==2){
						return "待审核";
					}else if(value==3){
						return "已审核";
					}
				}
			},{
				title :'创建人',
				field :'storageCby',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.storageStatus==2){
						return '<img src="${ctx}/images/start.png" title="审核通过" style="cursor:pointer;" onclick="sysAdmin_10484_queryEditFun('+row.psid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="审核退回" style="cursor:pointer;" onclick="sysAdmin_10484k_queryEditFun('+row.psid+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				text:'盘盈盘亏导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10484_exportFun();
				}
			}]		
		});
	});
	
	//导出Excel
	function sysAdmin_10484_exportFun() {
		$('#sysAdmin_10484_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseStorage_exportPurchaseStorage.action',
			    	});
	}
	
	//审核通过
	function sysAdmin_10484_queryEditFun(psid){
		$.messager.confirm('确认','您确认审核通过吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/purchaseStorage_updatePurchaseStorageY.action',
		        	data: {"psid": psid},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
			        		$('#sysAdmin_10484_queryDialog').dialog('destroy');
				        	$('#sysAdmin_10484_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_10484_queryDialog').dialog('destroy');
				        	$('#sysAdmin_10484_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_10484_queryDialog').dialog('destroy');
			        	$('#sysAdmin_10484_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_10484_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	//审核退回
	function sysAdmin_10484k_queryEditFun(psid){
		$('<div id="sysAdmin_10484K_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审核退回盘盈盘亏</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10485.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#psid_10485').val(psid);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10485_editForm').form('submit', {
						url:'${ctx}/sysAdmin/purchaseStorage_updatePurchaseStorageK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10484_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10484_datagrid').datagrid('reload');
					    			$('#sysAdmin_10484K_editDialog').dialog('destroy');
					    			$('#sysAdmin_10484_queryDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10484K_editDialog').dialog('destroy');
					    			$('#sysAdmin_10484_queryDialog').dialog('destroy');
					    			$('#sysAdmin_10484_datagrid').datagrid('reload');
					    			$('#sysAdmin_10484_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10484K_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10484_searchFun() {
		var start = $("#10484_cdate").datebox('getValue');
    	var end = $("#10484_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10484_datagrid').datagrid('load', serializeObject($('#sysAdmin_10484_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10484_cleanFun() {
		$('#sysAdmin_10484_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10484_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>类型&nbsp;</th>
					<td><select name="storageType" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
						<option value="">查询所有</option>
	   					<option value="2">盘盈</option>
	    				<option value="3">盘亏</option>
	   				</select></td>
				    
				    <th>&nbsp;时间&nbsp;</th>
					<td><input name="storageCdate" id="10484_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="storageCdateEnd" id="10484_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
				</tr> 
			
				<tr>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10484_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10484_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10484_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>