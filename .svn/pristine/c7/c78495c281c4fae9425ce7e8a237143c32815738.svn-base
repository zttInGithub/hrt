<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantjoin_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantInfoWJoin1.action?isM35=0',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'bmid',
			columns : [[{
				title : '商户ID',
				field : 'bmid',
				width : 100,
				checkbox:true
			},{
				title : '机构名称',
				field : 'unitName',
				width : 100
			},{
				title : '机构性质',
				field : 'agentAttr',
				width : 100,
				hidden:true,
				formatter : function(value,row,index) {
					if(value==0){
					 return '自营';
					}else if(value==1){
						return '代理商';
					}else if(value==2){
						return '通道商';
					}
				}
			},{
				title :'商户编号',
				field :'mid',
				width : 100,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'商户类型',
				field :'isM35',
				width : 60,
				formatter : function(value,row,index) {
					if(value==6){
					   return "聚合商户";
					}else if (value==2){
					   return "个人商户";
					}else if (value==3){
					   return "企业商户";
					}else if (value==4){
					   return "优惠商户";
					}else if (value==5){
					   return "减免商户";
					}else{
					   return "标准商户";
					}
				},
				hidden:true
			},{
				title :'注册地址',
				field :'baddr',
				width : 100,
				hidden:true
			},{
				title :'申请时间',
				field :'maintainDate',
				width : 100,
				sortable :true
			},{
				title :'受理描述',
				field :'processContext',
				width : 140,
				sortable :true
			},{
				title :'操作',
				field :'operation',
				width : 200,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatus == 'C'&&row.isM35==6){
						return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantjoin_queryFun('+index+')"/>&nbsp;&nbsp'+
						'<input type="button" value="调整限额" onclick="sysAdmin_merchantY_limit('+index+')"/>';
					}if(row.approveStatus == 'C'){
						return '<input type="button" value="审批通过" onclick="sysAdmin_merchantY_editFun('+row.bmid+')"/>';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantjoin_queryFun('+index+')"/>&nbsp;&nbsp';
					}
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'审批',
					iconCls:'icon-start',
					handler:function(){
						sysAdmin_merchantjoin_editFun();
					}
				},{
					id:'btn_add',
					text:'退回',
					iconCls:'icon-close',
					handler:function(){
						sysAdmin_merchantjoink_editFun();
					}
				},{
					id:'btn_add',
					text:'审批通过',
					iconCls:'icon-start',
					handler:function(){
						sysAdmin_merchantjoinY_editFun();
					}
				}]  
		});
	});
	
	function sysAdmin_merchantY_editFun(bmid){
		$.messager.confirm('确认','您确认审批完成吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/merchant_updateMerchantInfoY.action',
		        	data: {"bmid": bmid},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	function sysAdmin_merchantY_limit(index){
		var rows = $('#sysAdmin_merchantjoin_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_merchantjoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">限额调整</span>',
			width: 380,   
		    height: 220, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10509.jsp?areaType='+row.areaType,
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantjoin_datagrid').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchant10509_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateAggMerchantLimit.action?mid='+row.mid,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_merchantjoin_queryFun(index){
		var rows = $('#sysAdmin_merchantjoin_datagrid').datagrid('getRows');
		var row  = rows[index];
		var bmid = row.bmid;
		var mid  = row.mid;
		$('<div id="sysAdmin_merchantjoin_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10481_1.jsp?bmid='+bmid+'&mid="'+mid+'"',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_merchantList_queryForm').form('load', row);
			},
			buttons:[{
				text:'审批',
				iconCls:'icon-ok',
				handler:function() {
					sysAdmin_merchantjoin_queryEditFun(bmid);
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					sysAdmin_merchantjoink_queryEditFun(bmid);
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_merchantjoin_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_merchantjoin_editFun(){
		var checkedItems = $('#sysAdmin_merchantjoin_datagrid').datagrid('getChecked');
		var names = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.approveStatus == 'C'){
				result = false;
			}
			names.push(item.bmid);
		});
		if(!result){
			$.messager.alert('提示','已有审批过的商户！');
			return;
		}
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		$('<div id="sysAdmin_merchantjoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10421.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantjoin_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchantjoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantInfoC.action?bmids='+bmids,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//二次审批 
	function sysAdmin_merchantjoinY_editFun(){
		var checkedItems = $('#sysAdmin_merchantjoin_datagrid').datagrid('getChecked');
		var names = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.approveStatus == 'Z' || item.approveStatus == 'W'){
				result = false;
			}
			names.push(item.bmid);
		});
		if(!result){
			$.messager.alert('提示','有未审批的商户！');
			return;
		}
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		$.messager.confirm('确认','您确认审批完成吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/merchant_updateBatchMerchantInfoY.action',
		        	data: {"bmids": bmids},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
				        	$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
	
	function sysAdmin_merchantjoink_editFun(){
		var checkedItems = $('#sysAdmin_merchantjoin_datagrid').datagrid('getChecked');
		var names = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.approveStatus == 'C'){
				result = false;
			}
			names.push(item.bmid);
		});
		if(!result){
			$.messager.alert('提示','已有审批过的商户不可退回！');
			return;
		}
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		$('<div id="sysAdmin_merchantjoinK_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10422.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantjoinK_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantInfoK.action?bmids='+bmids,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoinK_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoinK_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoinK_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	
	function sysAdmin_merchantjoin_queryEditFun(bmid){
		$('<div id="sysAdmin_merchantjoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10421.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantjoin_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchantjoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantInfoC.action?bmids='+bmid,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
				    				$('#sysAdmin_merchantjoin_queryDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoin_queryDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_merchantjoink_queryEditFun(bmid){
		$('<div id="sysAdmin_merchantjoinK_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10422.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantjoinK_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantInfoK.action?bmids='+bmid,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantjoin_datagrid').datagrid('reload');
				    				$('#sysAdmin_merchantjoin_queryDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoinK_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoin_queryDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoinK_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoinK_editDialog').dialog('destroy');
					$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantjoin_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//查看相同营业执照号的商户
	function sysAdmin_merchantBnoRname_queryFun(bno){
		$('<div id="sysAdmin_merchantBnoRname_run"/>').dialog({
			title: '查看相同营业执照号商户',
			width: 1000,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10423.jsp?bno='+bno,  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
		//表单查询
	function sysAdmin_merchantAuid_searchFun20() {
		$('#sysAdmin_merchantjoin_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchantAuid_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuid_cleanFun20() {
		$('#sysAdmin_merchantAuid_searchForm input').val('');
	}
	
</script>


	<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:25px;">
		<form id="sysAdmin_merchantAuid_searchForm" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;&nbsp;&nbsp;商户编号</th>
					<td><input name="mid" style="width: 120px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 116px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;审核状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:70px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="W">待审核</option>
		    				<option value="C">已审核</option>
		    			</select>
					</td>
					<td colspan="5" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuid_searchFun20();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuid_cleanFun20();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  

	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_merchantjoin_datagrid" style="overflow: hidden;"></table>
	</div>
