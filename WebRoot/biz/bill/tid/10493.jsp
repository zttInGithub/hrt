<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 盘盈盘亏 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10493_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseStorage_listPurchaseStorageP.action',
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
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10493_updateFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="提交" style="cursor:pointer;" onclick="sysAdmin_10493_submitFun('+row.psid+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10493_deleteFun('+row.psid+')"/>';
					}else if(row.storageStatus==3&&(row.storageMachineNum-row.loadStorageNum>0)){
						return '<img src="${ctx}/images/frame_add.png" title="盘盈盘亏" style="cursor:pointer;" onclick="sysAdmin_10493_addFun('+index+')"/>';
					}
				}
			}]], 
			toolbar:[{
				id:'btn_add',
				text:'新增盘盈盘亏',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10493_addPurchaseOrder();
				}
			}/*,{
				text:'采购单导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10493_exportFun();
				}
			}*/]		
		});
	});
	
	//新增
	function sysAdmin_10493_addPurchaseOrder() {
		$('<div id="sysAdmin_10493_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增盘盈盘亏</span>',
			width: 900,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10494.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10494_addForm').form('validate');
		    		var storageMachineModel_10494 = $('#storageMachineModel_10494').combobox('getValue');
		    		var storageBrandName_10494 = $('#storageBrandName_10494').combobox('getValue');
		    		if(storageMachineModel_10494==""||storageBrandName_10494==""){
		    			$.messager.alert('提示', "请选择品牌和机型");
		    			return;
		    		}
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10494_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseStorage_savePurchaseStorageP.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10493_datagrid').datagrid('reload');
					    			$('#sysAdmin_10493_addDialog').dialog('destroy');
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
					$('#sysAdmin_10493_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//设备盘盈盘亏
	function sysAdmin_10493_addFun(index){
    	var rows = $('#sysAdmin_10493_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10493_updatePurchaseOrder"/>').dialog({
			title: '借出设备',
			width: 900,   
		    height: 300,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10488.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10488_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10488_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$.messager.progress();	//开启进度条
		        	$('#sysAdmin_10488_addForm').form('submit', { 
		    		url:'${ctx}/sysAdmin/terminalInfo_updateM35ForInventory.action', 
		    		onSubmit: function(){
		    			var contact = document.getElementById('uploads10488').value;
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
		    					document.getElementById("file10488_Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
		    					//document.getElementById("frmBZ").submit();	//获取from值进行提交
		    					$('#btn_10488_upload').linkbutton({disabled:true});
		    					return true;
		    				}
		    			}
		    		}, 
		    		success:function(data){ 
		    		$.messager.progress('close');   //关闭进度条 
		    		$('#sysAdmin_10493_updatePurchaseOrder').dialog('destroy');
		    		$('#sysAdmin_10493_datagrid').datagrid('reload');
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
					$('#sysAdmin_10493_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//删除订单
	function sysAdmin_10493_deleteFun(psid){
		$.messager.confirm('确认','您确认要删除该盘盈盘亏单吗?',function(result) {
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
							$('#sysAdmin_10493_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除盘盈盘亏信息出错！');
					}
				});
			}
		});
	}
	
	//提交
	function sysAdmin_10493_submitFun(psid){
		$.messager.confirm('确认','您确认提交审核?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseStorage_submitPurchaseStorageP.action",
					type:'post',
					data:{"psid":psid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10493_datagrid').datagrid('reload');
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
	function sysAdmin_10493_updateFun(index){
    	var rows = $('#sysAdmin_10493_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10493_updatePurchaseOrder"/>').dialog({
			title: '修改盘盈盘亏信息',
			width: 900,   
		    height: 300,   
		    closed: false,
		    href:'${ctx}/biz/bill/tid/10495.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10495_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var validator = $('#sysAdmin_10495_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_10495_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseStorage_updatePurchaseStorageP.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10493_datagrid').datagrid('reload');
					    			$('#sysAdmin_10493_updatePurchaseOrder').dialog('destroy');
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
					$('#sysAdmin_10493_updatePurchaseOrder').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

	//表单查询
	function sysAdmin_10493_searchFun() {
		var start = $("#10493_cdate").datebox('getValue');
    	var end = $("#10493_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10493_datagrid').datagrid('load', serializeObject($('#sysAdmin_10493_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10493_cleanFun() {
		$('#sysAdmin_10493_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10493_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>类型&nbsp;</th>
					<td><select name="storageType" style="width:155px;" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" >
						<option value="">查询所有</option>
	   					<option value="2">盘盈</option>
	    				<option value="3">盘亏</option>
	   				</select></td>
				    
				    <th>&nbsp;时间&nbsp;</th>
					<td><input name="storageCdate" id="10493_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
			&nbsp;至&nbsp;<input name="storageCdateEnd" id="10493_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
				</tr> 
			
				<tr>
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10493_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10493_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10493_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>