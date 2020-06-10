<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantmicrojoin_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMicroMerchantInfoWJoin1.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ,50,100],
			remoteSort:true,
			idField : 'bmid',
			columns : [[{
				title : '商户ID',
				field : 'bmid',
				width : 100,
				hidden : true 
			},{
				title : '机构编号',
				field : 'unno1',
				width : 100
			},{
				title : '机构名称',
				field : 'parentUnitName1',
				width : 100
			},{
				title : '一级机构名称',
				field : 'unitName',
				width : 100
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
				title :'审核备注',
				field :'bankArea',
				width : 180,
				sortable :true,
				hidden:true
			},{
				title :'申请时间',
				field :'maintainDate',
				width : 100,
				sortable :true
			},{
				title :'是否秒到',
				field :'settMethod',
				width : 60,
				formatter : function(value,row,index) {
					if (value=="100000"){
					   return '是';
					}else if (value=="200000"){
					   return '急速';
					}else{
					   return '否';
					}  
				},
				hidden:true
			},{
				title :'受理描述',
				field :'processContext',
				width : 140,
				sortable :true
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatus == 'W'){
						return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantmicrojoin_queryFun('+index+','+row.bmid+','+row.mid+')"/>&nbsp;&nbsp'+
	  						   '<img src="${ctx}/images/start.png" title="审核" onclick="sysAdmin_merchantmicroC_editFun('+row.bmid+')"/>&nbsp;&nbsp'+
	  						   '<img src="${ctx}/images/frame_remove.png" title="退回" onclick="sysAdmin_merchantmicrojoink_editFun('+row.bmid+')"/>';
					}else if(row.approveStatus=='C'){
						return  '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantmicrojoin_queryFun('+index+','+row.bmid+','+row.mid+')"/>&nbsp;&nbsp'+
						 '<input type="button" value="审批通过" onclick="sysAdmin_merchantmicroY_editFun('+row.bmid+')"/>&nbsp;&nbsp';
					}
				}
			}]],
			toolbar:[{
					id:'btn_adds',
					text:'审批',
					iconCls:'icon-start',
					handler:function(){
						sysAdmin_merchantmicrojoin_editFun();
						}
					},{
					id:'btn_add',
					text:'审批通过',
					iconCls:'icon-start',
					handler:function(){
						sysAdmin_merchantmicrojoinY_editFun();
					}
			}]  
		});
	});
	
	//批量审批
	function sysAdmin_merchantmicrojoin_editFun(){
		var checkedItems = $('#sysAdmin_merchantmicrojoin_datagrid').datagrid('getChecked');
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
		$('<div id="sysAdmin_merchantmicrojoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10712.jsp?bmids='+bmids,
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantmicrojoin_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchantmicrojoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantMicroInfos.action?bmids='+bmids,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantmicrojoin_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantmicrojoin_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantmicrojoin_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	//查看明细
	function sysAdmin_merchantmicrojoin_queryFun(index,bmid,mid){
		$('<div id="sysAdmin_merchantmicrojoin_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10711_1.jsp?bmid='+bmid+'&mid='+mid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantmicrojoin_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchantmicroList_queryForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_merchantmicrojoin_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
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
	
	//审批
	function sysAdmin_merchantmicroC_editFun(bmid){
		$('<div id="sysAdmin_merchantmicrojoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认</span>',
			width: 460,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10712.jsp?bmids='+bmid,
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantmicrojoin_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchantmicrojoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantMicroInfoC.action?bmids='+bmid,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantmicrojoin_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantmicrojoin_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantmicrojoin_editDialog').dialog('destroy');
					$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
			}
		});
	}

	//审批通过
	function sysAdmin_merchantmicroY_editFun(bmid){
		$.messager.confirm('确认','您确认审批完成吗?',function(r){   
		    if (r){   
		        $.ajax({
			        url:'${ctx}/sysAdmin/merchant_updateMerchantMicroInfoY.action',
		        	data: {"bmid": bmid},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
			}   
		}); 
	}

	//二次审批 
	function sysAdmin_merchantmicrojoinY_editFun(){
		var checkedItems = $('#sysAdmin_merchantmicrojoin_datagrid').datagrid('getChecked');
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
			        url:'${ctx}/sysAdmin/merchant_updateMerchantMicroBatchInfoY.action',
		        	data: {"bmids": bmids},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
				        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				    }
			    }); 
		    } else {
		    	$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
			}   
		}); 
	}

	//退回
	function sysAdmin_merchantmicrojoink_editFun(bmid){
		$('<div id="sysAdmin_merchantmicrojoinK_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10713.jsp?bmid='+bmid,
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantmicrojoinK_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_updateMerchantMicroInfoK.action', 
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantmicrojoinK_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantmicrojoinK_editDialog').dialog('destroy');
					    			$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantmicrojoinK_editDialog').dialog('destroy');
					$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('unselectAll');
			}
		});
	}


	//表单查询
	function sysAdmin_merchantmicroAuid_searchFun20() {
		$('#sysAdmin_merchantmicrojoin_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchantmicroAuid_searchForm')));  
	} 

	//清除表单内容
	function sysAdmin_merchantmicroAuid_cleanFun20() {
		$('#sysAdmin_merchantmicroAuid_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchantmicroAuid_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantmicroAuid_searchFun20();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantmicroAuid_cleanFun20();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  

	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_merchantmicrojoin_datagrid" style="overflow: hidden;"></table>
	</div>
