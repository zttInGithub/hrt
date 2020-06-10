<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10950_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantUpdateTer_queryMerUpdateTer.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'BMUTID',
			columns : [[{
				title :'ID',
				field :'BMUTID',
				width : 100,
				hidden:true
			},{
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
				title :'商户号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'类型',
				field :'TYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value==1){
					   return "换机";
					}else if (value==2){
					   return "撤机";
					}else if(value==3){
						return "关闭商户";
					}
				}
			},{
				title :'终端号',
				field :'TID',
				width : 100
			},{
				title : 'SN号',
				field : 'SN',
				width : 100
			},{
				title :'操作时间',
				field :'MAINTAINDATE',
				width : 100
			},{
				title : '审核状态',
				field : 'APPROVETYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='W'){
						return "待审核";
					}else if (value=='Y'){
						return "已通过";
					}else if(value=='K'){
						return "退回";
					}
				}
			},{
				title : '审核时间',
				field : 'APPROVEDATE',
				width : 100
			},{ 
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.TYPE==1){
						return '<img src="${ctx}/images/query_search.png" style="cursor:pointer;" title="查看" onclick="sysAdmin_10950_serchFun('+index+')"/>';
					}
				}
			}]], 
			toolbar:[/**{
				id:'btn_add',
				text:'换机申请',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10950_apply1();
				}
			},{
				id:'btn_add',
				text:'撤机申请',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10950_apply2();
				}
			},{
				id:'btn_add',
				text:'关闭商户',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10950_apply3();
				}
			}**/]
		});
	});
	
	function sysAdmin_10950_apply1(){
		$('<div id="sysAdmin_10950_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">换机申请</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10954.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
			   	handler:function() {
					$('#sysAdmin_10954_from').form('submit',{
						url:'${ctx}/sysAdmin/merchantUpdateTer_addMerUpdateTer1.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if (result.success) {
									$('#sysAdmin_10950_datagrid').datagrid('reload');
									$('#sysAdmin_10950_Dialog').dialog('destroy');
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
				$('#sysAdmin_10950_Dialog').dialog('destroy');
				}  
			}], 
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_10950_apply2(){
		$('<div id="sysAdmin_10950_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">撤机申请</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10955.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
			   	handler:function() {
					$('#sysAdmin_10955_from').form('submit',{
						url:'${ctx}/sysAdmin/merchantUpdateTer_addMerUpdateTer2.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if (result.success) {
									$('#sysAdmin_10950_datagrid').datagrid('reload');
									$('#sysAdmin_10950_Dialog').dialog('destroy');
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
				$('#sysAdmin_10950_Dialog').dialog('destroy');
				}  
			}], 
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	function sysAdmin_10950_apply3(){
		$('<div id="sysAdmin_10950_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">关闭商户</span>',
			width: 500,
		    height:150, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10956.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
			   	handler:function() {
					$('#sysAdmin_10956_from').form('submit',{
						url:'${ctx}/sysAdmin/merchantUpdateTer_addMerUpdateTer3.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
								window.location.href = getProjectLocation();
							} else {
								if (result.success) {
									$('#sysAdmin_10950_datagrid').datagrid('reload');
									$('#sysAdmin_10950_Dialog').dialog('destroy');
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
				$('#sysAdmin_10950_Dialog').dialog('destroy');
				}  
			}], 
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//修改
	function sysAdmin_10950_updateFun(index){
		var rows = $('#sysAdmin_10950_datagrid').datagrid('getRows');
		var row  = rows[index];
		$('<div id="sysAdmin_10950_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改信息</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10952.jsp?bmutID='+row.BMUTID,
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
			   	handler:function() {
				   	if($('#legalNameFile').val()!=""){
						var validator = $('#sysAdmin_10437_from').form('validate');
						if(validator){
							$.messager.progress();
						}
						$('#sysAdmin_10437_from').form('submit',{
							url:'${ctx}/sysAdmin/merchantTwoUpload_updateMerchantInfo.action?bmtuid='+bmtuid,
							success:function(data) {
								$.messager.progress('close'); 
								var result = $.parseJSON(data);
								if (result.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if (result.success) {
										$('#sysAdmin_10950_datagrid').datagrid('reload');
										$('#sysAdmin_10950_Dialog').dialog('destroy');
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
				    }else{
				    	$.messager.alert('提示', '请上传TUSN照片！');
				    }
				}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_10950_Dialog').dialog('destroy');
				}  
			}], 
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//查看
	function sysAdmin_10950_serchFun(index){
		var rows = $('#sysAdmin_10950_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10950_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10951.jsp?BMUTID='+row.BMUTID,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10951_from').form('load', row);
			},
			buttons:[{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function() {
						$('#sysAdmin_10950_Dialog').dialog('destroy');
					}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10950_searchFun() {
		$('#sysAdmin_10950_datagrid').datagrid('load', serializeObject($('#sysAdmin_10950_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10950_cleanFun() {
		$('#sysAdmin_10950_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10950_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;商户号&nbsp;</th>
					<td><input name="mid" style="width: 138px;" /></td>
					<th>&nbsp;类型&nbsp;</th>
					<td>
						<select name="type" style="width: 138px;">
							<option value="" >所有</option>
							<option value="1" >换机</option>
							<option value="2" >撤机</option>
							<option value="3" >关闭商户</option>
						</select>
					</td>
					<th>&nbsp;审核状态&nbsp;</th>
					<td>
						<select name="approveType" style="width: 138px;">
							<option value="" >所有</option>
							<option value="W" >待审核</option>
							<option value="K" >退回</option>
							<option value="Y" >通过</option>
						</select>
					</td>
					<th>&nbsp;操作时间&nbsp;</th>
					<td><input name="approveDate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					&nbsp;至&nbsp;<input name="approveDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10950_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10950_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10950_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>
