<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 借样单 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10490_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseStorage_listPurchaseStorage.action',
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
				title : '借样单号',
				field : 'storageID',
				width : 100
			},{
				title : '出借日期',
				field : 'lendDate',
				width : 100
			},{
				title : '归还日期',
				field : 'returnDate',
				width : 100
			},{
				title : '借机人',
				field : 'lender',
				width : 100
			},{
				title :'部门和职务',
				field :'department',
				width : 100
			},{
				title :'借用事由',
				field :'storageRemark',
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
						return "未借出";
					}else if(value==2){
						return "未归还";
					}else{
						return "已归还";
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
					if(row.storageStatus==1 && row.loadStorageNum==0){
						return '<img src="${ctx}/images/frame_add.png" title="借出设备" style="cursor:pointer;" onclick="sysAdmin_10490_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10490_deleteFun('+row.psid+')"/>';
					}else if(row.storageStatus==1){
						return '<img src="${ctx}/images/frame_add.png" title="借出设备" style="cursor:pointer;" onclick="sysAdmin_10490_updateFun('+index+')"/>&nbsp;&nbsp';
						
					}else if(row.storageStatus==2){
						return '<img src="${ctx}/images/start.png" title="确认归还" style="cursor:pointer;" onclick="sysAdmin_10490_submitFun('+row.psid+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				id:'btn_add',
				text:'新增借样单',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10490_addPurchaseOrder();
				}
			}/*,{
				text:'采购单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10490_exportFun();
				}
			}*/]		
		});
	});
	
	//新增
	function sysAdmin_10490_addPurchaseOrder() {
		$('<div id="sysAdmin_10490_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增借样单</span>',
			width: 900,
		    height:300, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10491.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10491_addForm').form('validate');
		    		var storageMachineModel_10491 = $('#storageMachineModel_10491').combobox('getValue');
		    		var storageBrandName_10491 = $('#storageBrandName_10491').combobox('getValue');
		    		if(storageMachineModel_10491==""||storageBrandName_10491==""){
		    			$.messager.alert('提示', "请选择品牌和机型");
		    			return;
		    		}
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10491_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseStorage_savePurchaseStorage.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10490_datagrid').datagrid('reload');
					    			$('#sysAdmin_10490_addDialog').dialog('destroy');
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
					$('#sysAdmin_10490_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//删除订单
	function sysAdmin_10490_deleteFun(psid){
		$.messager.confirm('确认','您确认要删除采购单吗?',function(result) {
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
							$('#sysAdmin_10490_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除采购单出错！');
					}
				});
			}
		});
	}
	
	//确认归还
	function sysAdmin_10490_submitFun(psid){
		$.messager.confirm('确认','您确认已经归还?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseStorage_submitPurchaseStorage.action",
					type:'post',
					data:{"psid":psid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10490_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '确认归还出错！');
					}
				});
			}
		});
	}
	
	//借出
	function sysAdmin_10490_updateFun(index){
    	var rows = $('#sysAdmin_10490_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10490_updatePurchaseOrder"/>').dialog({
			title: '借出设备',
			width: 900,   
		    height: 300,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10492.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10492_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10492_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$.messager.progress();	//开启进度条
		        	$('#sysAdmin_10492_addForm').form('submit', { 
		    		url:'${ctx}/sysAdmin/terminalInfo_updateM35ForLend.action', 
		    		onSubmit: function(){
		    			var contact = document.getElementById('uploads10492').value;
		    			//alert('contact:'+contact);
		    			if (contact == "") {
		    			$.messager.progress('close');   //关闭进度条 
		    				$.messager.show({
		               		 title:'提示',
		                	 msg:'请选择要上传的文件!',
		                	 timeout:5000,
		                	 showType:'slide'
		            		});
		    			return false;
		    			}
		    			if (contact != "") {
		    				var l = contact.split(".");
		    				if(l[1] != "xls"){
		    				$.messager.progress('close');   //关闭进度条 
		    				$.messager.show({
		    					title:'提示',
		    		            msg:'请选择后缀名为.xls 的文件!',
		    		            timeout:5000,
		    		            showType:'slide'
		    		            });
		    					return false;
		    				}
		    				if (l[1] == "xls") {
		    					document.getElementById("file10492_Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
		    					//document.getElementById("frmBZ").submit();	//获取from值进行提交
		    					$('#btn_10492_upload').linkbutton({disabled:true});
		    					return true;
		    				}
		    			}
		    		}, 
		    		success:function(data){ 
		    		$.messager.progress('close');   //关闭进度条 
		    		$('#sysAdmin_10490_updatePurchaseOrder').dialog('destroy');
		    		$('#sysAdmin_10490_datagrid').datagrid('reload');
		    			var res = $.parseJSON(data);
		    				if (res.sessionExpire) {
		    					window.location.href = getProjectLocation();
		    				} else {
		    					if(res.success) {
		    						$.messager.show({
		    							title : '提示',
		    							msg : res.msg
		    						});
		    					} else {
		    						$.messager.show({
		    							title : '提示',
		    							msg : res.msg
		    						});
		    							}  
		    						}
		    				} 
		    		}); 
		    	}			    
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10490_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//表单查询
	function sysAdmin_10490_searchFun() {
		var start = $("#10490_cdate").datebox('getValue');
    	var end = $("#10490_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10490_datagrid').datagrid('load', serializeObject($('#sysAdmin_10490_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10490_cleanFun() {
		$('#sysAdmin_10490_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10490_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>借样单号&nbsp;</th>
					<td><input name="storageID" style="width: 138px;" /></td>
					<th>借机人&nbsp;</th>
					<td><input name="lender" style="width: 138px;" /></td>
				    
					<th>&nbsp;部门职务&nbsp;</th>
					<td><input name="department" style="width: 138px;" /></td>
					
				    <th>&nbsp;借出日期&nbsp;</th>
					<td><input name="lendDate" id="10490_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="lendDateEnd" id="10490_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
				</tr> 
			
				<tr>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10490_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10490_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10490_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>