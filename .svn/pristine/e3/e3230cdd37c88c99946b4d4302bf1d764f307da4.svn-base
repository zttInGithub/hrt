<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 库存调拨 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10496_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseStorage_listPurchaseStorageD.action',
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
				title : '调拨时间',
				field : 'storageCdate',
				width : 100
			},{
				title : '调出库位',
				field : 'outStorage',
				width : 100
			},{
				title : '调入库位',
				field : 'inStorage',
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
				title :'已调拨数量',
				field :'loadStorageNum',
				width : 100
			},{
				title :'状态',
				field :'storageStatus',
				width : 100,
				formatter : function(value,row,index) {
					if(value==1&&row.storageApproveStatus=="K"){
						return "退回";
					}else if(value==1){
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
					if(row.storageStatus==1){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10496_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="提交" style="cursor:pointer;" onclick="sysAdmin_10496_submitFun('+row.psid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10496_deleteFun('+row.psid+')"/>';
					}else if(row.storageStatus==3&&row.storageMachineNum>row.loadStorageNum){
						return '<img src="${ctx}/images/frame_add.png" title="库存调拨" style="cursor:pointer;" onclick="sysAdmin_10496_uploadFun('+index+')"/>';
					}
				}
			}]], 
			toolbar:[{
				id:'btn_add',
				text:'新增库存调拨',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10496_addPurchaseOrder();
				}
			}/*,{
				text:'采购单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10496_exportFun();
				}
			}*/]		
		});
	});
	
	 function sysAdmin_10496_uploadFun(index){
		 var rows = $('#sysAdmin_10496_datagrid').datagrid('getRows');
			var row = rows[index];
			$('<div id="sysAdmin_10496_updatePurchaseOrder"/>').dialog({
				title: '库存调拨',
				width: 600,   
			    height: 216,   
			    closed: false,
			    href:'${ctx}/biz/bill/tid/10499.jsp',
			    modal: true,
			    onLoad:function() {
			    	$('#frmBjjz10499').form('load', row);
				},
				buttons:[],
				onClose : function() {
					$(this).dialog('destroy');
				}  
			});
	 }
	
	//新增
	function sysAdmin_10496_addPurchaseOrder() {
		$('<div id="sysAdmin_10496_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增库存调拨</span>',
			width: 900,
		    height:260, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10497.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10497_addForm').form('validate');
		    		var storageMachineModel_10497 = $('#storageMachineModel_10497').combobox('getValue');
		    		var storageBrandName_10497 = $('#storageBrandName_10497').combobox('getValue');
		    		if(storageMachineModel_10497==""||storageBrandName_10497==""){
		    			$.messager.alert('提示', "请选择品牌和机型");
		    			return;
		    		}
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10497_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseStorage_savePurchaseStorageD.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10496_datagrid').datagrid('reload');
					    			$('#sysAdmin_10496_addDialog').dialog('destroy');
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
					$('#sysAdmin_10496_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//删除订单
	function sysAdmin_10496_deleteFun(psid){
		$.messager.confirm('确认','您确认要删除库存调拨信息吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseStorage_deletePurchaseStorage.action",
					type:'post',
					data:{"psid":psid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10496_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除库存调拨信息出错！');
					}
				});
			}
		});
	}
	
	//提交
	function sysAdmin_10496_submitFun(psid){
		$.messager.confirm('确认','您确认提交审核?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseStorage_submitPurchaseStorageD.action",
					type:'post',
					data:{"psid":psid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10496_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核出错！');
					}
				});
			}
		});
	}
	
	//修改
	function sysAdmin_10496_updateFun(index){
    	var rows = $('#sysAdmin_10496_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10496_updatePurchaseOrder"/>').dialog({
			title: '修改借样单',
			width: 900,   
		    height: 300,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10498.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10498_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10498_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10498_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseStorage_updatePurchaseStorageP.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10496_datagrid').datagrid('reload');
					    			$('#sysAdmin_10496_updatePurchaseOrder').dialog('destroy');
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
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10496_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//表单查询
	function sysAdmin_10496_searchFun() {
		var start = $("#10496_cdate").datebox('getValue');
    	var end = $("#10496_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10496_datagrid').datagrid('load', serializeObject($('#sysAdmin_10496_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10496_cleanFun() {
		$('#sysAdmin_10496_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10496_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>调出库位&nbsp;</th>
					<td><select name="outStorage" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
						<option value="">查询所有</option>
	   					<option value="HRT">HRT</option>
	    				<option value="HYB">HYB</option>
	   				</select></td>
	   				
	   				<th>调入库位&nbsp;</th>
					<td><select name="inStorage" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
						<option value="">查询所有</option>
	   					<option value="HRT">HRT</option>
	    				<option value="HYB">HYB</option>
	   				</select></td>
				    
				    <th>&nbsp;时间&nbsp;</th>
					<td><input name="storageCdate" id="10496_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="storageCdateEnd" id="10496_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
				</tr> 
			
				<tr>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10496_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10496_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10496_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>