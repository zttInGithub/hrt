<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 来款记录表(财务端-新增来款) -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10508_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseRecord_listPurchaseRecord.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'PRID',
			columns : [[{
				title : '来款ID',
				field : 'PRID',
				width : 100
			},{
				title : '订单号',
				field : 'MATCHORDERID',
				width : 120
			},{
				title : '来款金额',
				field : 'ARRAIVEAMT',
				width : 80
			},{
				title : '已匹配金额',
				field : 'RECORDAMT',
				width : 80
			},{
				title :'匹配金额',
				field :'MATCHAMT',
				width : 120,
			},{
				title : '来款人姓名',
				field : 'ARRAIVERNAME',
				width : 80
			},{
				title : '来款卡号',
				field : 'ARRAIVECARD',
				width : 100
			},{
				title : '来款日期',
				field : 'ARRAIVEDATE',
				width : 100
			},{
				title :'来款状态',
				field :'MATCHAPPROVESTATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value=='A'){
						return '销售匹配';
					}else if(value=='Y'){
						return '财务确认';
					}else if(value=='K'){
						return '退回';
					}else{
						return '财务新增';
					}
				}
			},{
				title :'来款方式',
				field :'ARRAIVEWAY',
				align : 'right',
				width : 100
			},{
				title :'代理机构号',
				field :'UNNO',
				align : 'right',
				width : 100
			},{
				title :'代理机构名称',
				field :'PURCHASERNAME',
				align : 'right',
				width : 100
			},{
				title :'登记时间',
				field :'RECORDCDATE',
				align : 'right',
				width : 100
			},{
				title :'登记人',
				field :'RECORDCBY',
				width : 100
			},{
				title :'匹配时间',
				field :'RECORDLMDATE',
				width : 120,
			},{
				title :'匹配人',
				field :'RECORDLMBY',
				width : 130
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.MATCHAPPROVESTATUS=='K'){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10508_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="删除来款" style="cursor:pointer;" onclick="sysAdmin_10508_deleteFun('+row.PRID+')"/>&nbsp;&nbsp';
					}else if(row.MATCHAPPROVESTATUS=='A'){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10508_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="财务确认" style="cursor:pointer;" onclick="sysAdmin_10508_confirmFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/close.png" title="退回来款" style="cursor:pointer;" onclick="sysAdmin_10508_returnFun('+row.PRID+','+row.PMID+')"/>&nbsp;&nbsp';
					}else if(row.ARRAIVESTATUS==1){//未分配，可删除
						return '<img src="${ctx}/images/close.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_10508_deleteFun('+row.PRID+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10508_detailFun('+index+')"/>';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10508_detailFun('+index+')"/>';
					}
				}
			}]],
			toolbar:[{
				id:'btn_add',
				text:'新增来款',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10508_addPurchaseRecord();
				}
			},{
				id:'btn_add',
				text:'来款导入',
				iconCls:'icon-xls',
				handler:function(){
					shangchuan_10508();
				}
			},{
				text:'来款导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10508_exportFun();
				}
			}]		
		});
	});
	//导出Excel
	function sysAdmin_10508_exportFun() {
		$('#sysAdmin_10508_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/purchaseRecord_exportPurchaseRecord.action',
		});
	}
	//查看
	function sysAdmin_10508_detailFun(index){
		var rows = $('#sysAdmin_10508_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10508_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">来款信息</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10512.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10512_addForm').form('load', row);
		    	if(row.ARRAIVESTATUS == 1){
		    		$('#arraiveStatus_10512').val('财务新增');
				}else if(row.ARRAIVESTATUS == 2){
					$('#arraiveStatus_10512').val('销售匹配');
				}else if(row.ARRAIVESTATUS == 3){
					$('#arraiveStatus_10512').val('财务确认');
				}else if(row.ARRAIVESTATUS == 4){
					$('#arraiveStatus_10512').val('退回');
				}
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10508_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//退回来款
	function sysAdmin_10508_returnFun(prid,pmid){
		$.messager.confirm('确认','您确认要退回来款吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseRecord_returnPurchaseRecord.action",
					type:'post',
					data:{"prid":prid,"pmid":pmid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10508_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '退回来款出错！');
					}
				});
			}
		});
	}
	
	//来款导入
 	function shangchuan_10508() {
	$('<div id="10508_upload"/>').dialog({
		title: '上传窗口',
		width: 380,   
	    height: 200, 
	    closed: false,
	    href: '${ctx}/biz/bill/tid/10511.jsp',
	    modal: true,
	    buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			id:'adds',
			handler:function(){
			$('#adds').linkbutton({disabled:true});
			var dia = $('#10508_upload');
			$('#exportFile_10511').form('submit', {
			url:'${ctx}/sysAdmin/purchaseRecord_addPurchaseRecordbyFile.action', 
			onSubmit: function(){
			var contact = document.getElementById('upload').value;
			if (contact == "") {
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
		$.messager.show({
			title:'提示',
	        msg:'请选择后缀名为.xls的文件!',
	        timeout:5000,
	        showType:'slide'
	        });
			return false;
		}
		if (l[1] == "xls") {
			document.getElementById("file10511Name").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
			return true;
		}
		if (l[1] == "xlsx") {
			$.messager.show({
	            title:'提示',
	            msg:'请将格式转换为：.xls 为后缀名!',
	            timeout:5000,
	            showType:'slide'
	        });
			return false;
		}
	}
	}, 
		success:function(data){ 
			var res = $.parseJSON(data);
				if (res.sessionExpire) {
					window.location.href = getProjectLocation();
				} else {
							if(res.success) {
								dia.dialog('destroy');
								$.messager.show({
									title : '提示',
									msg : res.msg
								});
								$('#sysAdmin_10508_datagrid').datagrid('reload');
							} else {
								dia.dialog('destroy');
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
				text:'取消',
				handler:function(){
					$('#10508_upload').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	//财务确认
	function sysAdmin_10508_confirmFun(index){
		var rows = $('#sysAdmin_10508_datagrid').datagrid('getRows');
		var row = rows[index];
		$.messager.confirm('确认','您确认要通过来款吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseRecord_confirmPurchaseRecord.action",
					type:'post',
					data:{"prid":row.PRID,"arraiveAmt":row.ARRAIVEAMT,"pmid":row.PMID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10508_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '财务确认来款出错！');
					}
				});
			}
		});
	}

	//删除来款
	function sysAdmin_10508_deleteFun(prid){
		$.messager.confirm('确认','您确认要删除来款吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/purchaseRecord_deletePurchaseRecord.action",
					type:'post',
					data:{"prid":prid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10508_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除来款出错！');
					}
				});
			}
		});
	}
	//修改来款
	function sysAdmin_10508_editFun(index){
		var rows = $('#sysAdmin_10508_datagrid').datagrid('getRows');
		var row = rows[index];
		alert(row.prid);
		$('<div id="sysAdmin_10508_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">匹配来款</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10509.jsp',
		    modal: true,
		    onLoad:function() {
			    	$('#sysAdmin_10509_addForm').form('load', row);
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_10509_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseRecord_updatePurchaseRecord2.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10508_datagrid').datagrid('reload');
					    			$('#sysAdmin_10508_addDialog').dialog('destroy');
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
					$('#sysAdmin_10508_addDialog').dialog('destroy');
					$('#sysAdmin_10508_datagrid').datagrid('reload');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10508_datagrid').datagrid('reload');
			}
		});
	}
	//新增
	function sysAdmin_10508_addPurchaseRecord() {
		$('<div id="sysAdmin_10508_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增来款</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10509.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_10509_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseRecord_addPurchaseRecord.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10508_datagrid').datagrid('reload');
					    			$('#sysAdmin_10508_addDialog').dialog('destroy');
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
					$('#sysAdmin_10508_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//表单查询
	function sysAdmin_10508_searchFun() {
		var start = $("#10508_cdate").datebox('getValue');
    	var end = $("#10508_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10508_datagrid').datagrid('load', serializeObject($('#sysAdmin_10508_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10508_cleanFun() {
		$('#sysAdmin_10508_searchForm input').val('');
	}
	$(function(){
		$('#arraiveWay_10508').combogrid({
			url : '${ctx}/sysAdmin/purchaseRecord_listArraiveWay.action',
			idField:'ARRAIVEWAY',
			textField:'ARRAIVEWAY',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'ARRAIVEWAY',title:'来款方式',width:150},
			]]
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height: 80px; overflow: hidden; padding-top: 15px;">
		<form id="sysAdmin_10508_searchForm" style="padding-left: 10%" method="post">
			<table class="tableForm">
				<tr>
					<th>&nbsp;&nbsp;&nbsp;订单号</th>
					<td><input name="orderID" style="width: 138px;" /></td>
										
					<th>&nbsp;&nbsp;&nbsp;卡号</th>
					<td><input name="arraiveCard" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;来款方式</th>
					<td><select id="arraiveWay_10508" name="ARRAIVEWAY"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select></td>
					
					<th>&nbsp;&nbsp;&nbsp;状态</th>
					<td><select name="arraiveStatus" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 70px;">
							<option value="" selected="selected">查询所有</option>
							<option value="1">财务新增</option>
							<option value="2">销售匹配</option>
							<option value="3">财务确认</option>
							<option value="4">退回</option>
					</select></td>
					
				</tr>
				<tr>
				
					
					<th>&nbsp;来款日期&nbsp;</th>
					<td><input name="arraiveDate" id="10508_cdate" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/></td>
					<th>&nbsp;至&nbsp;</th>
					<td><input name="arraiveDateEnd" id="10508_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;备注</th>
					<td><input name="arraiveRemarks" style="width: 138px;" /></td>
					
					<td colspan="4" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_10508_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_10508_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10508_datagrid" style="overflow: hidden;"></table>
	</div>
</div>