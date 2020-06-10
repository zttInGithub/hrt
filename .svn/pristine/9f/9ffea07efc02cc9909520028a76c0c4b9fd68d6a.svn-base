<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10960_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantThreeUpload_queryMerchantThreeUploadFor.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'BMTHID',
			columns : [[{
				title :'ID',
				field :'BMTHID',
				width : 100,
				hidden:true
			},{
				title :'商户号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'终端号',
				field :'TID',
				width : 100
			},{
				title : 'SN号',
				field : 'SN',
				width : 100
			},{
				title :'营业执照名称',
				field :'LICENSE_NAME',
				width : 100
			},{
				title :'经营名称',
				field :'R_NAME',
				width : 100
			},{
				title :'经营地址',
				field :'R_ADDR',
				width : 100
			},{
				title :'是否支持非接',
				field :'ISCONNECT',
				width : 100,
				formatter : function(value,row,index) {
					if (value==0){
						return "不支持";
					}else if (value==1){
						return "支持";
					}
				}
			},{
				title :'是否支持双免',
				field :'ISIMMUNITY',
				width : 100,
				formatter : function(value,row,index) {
					if (value==0){
						return "不支持";
					}else if (value==1){
						return "支持";
					}
				}
			},{
				title :'是否支持银联二维码',
				field :'ISUNIONPAY',
				width : 100,
				formatter : function(value,row,index) {
					if (value==0){
						return "不支持";
					}else if (value==1){
						return "支持";
					}
				}
			},{
				title :'操作时间',
				field :'MAINTAINDATE',
				width : 100
			},{
				title : '审核状态',
				field : 'APPROVESTATUS',
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
				title : '退回原因',
				field : 'APPROVENOTE',
				width : 100
			},{ 
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.APPROVESTATUS=='W'){
						return '<img src="${ctx}/images/query_search.png" style="cursor:pointer;" title="查看" onclick="sysAdmin_10960_editFun('+index+')"/>';
					}else{
						return '<img src="${ctx}/images/query_search.png" style="cursor:pointer;" title="查看" onclick="sysAdmin_10960_serchFun('+index+')"/>';
					}
				}
			}]]
		});
	});
	
	//查看
	function sysAdmin_10960_editFun(index){
		var rows = $('#sysAdmin_10960_datagrid').datagrid('getRows');
		var row  = rows[index];
		$('<div id="sysAdmin_10960_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10962.jsp?BMTHID='+row.BMTHID,
		    modal: true,
		    onLoad:function() {
		    	if (row.ISCONNECT==0){
		    		row.ISCONNECT="不支持";
				}else if (row.ISCONNECT==1){
					row.ISCONNECT="支持";
				}
		    	if (row.ISIMMUNITY==0){
		    		row.ISIMMUNITY="不支持";
				}else if (row.ISIMMUNITY==1){
					row.ISIMMUNITY="支持";
				}
		    	if (row.ISUNIONPAY==0){
		    		row.ISUNIONPAY="不支持";
				}else if (row.ISUNIONPAY==1){
					row.ISUNIONPAY="支持";
				}
		    	$('#sysAdmin_10962_from').form('load', row);
			},
			buttons:[{
				text:'审批',
				iconCls:'icon-ok',
				handler:function() {
					sysAdmin_10960_updateGo(row.BMTHID);
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					sysAdmin_10960_updateBack(row.BMTHID);
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10960_Dialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//通过
	function sysAdmin_10960_updateGo(bmthid) {
		$.messager.confirm('确认','您确认要通过该信息吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/merchantThreeUpload_updateMerchantThreeUploadGo.action",
					type:'post',
					data:{"bmthid":bmthid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
			    			$('#sysAdmin_10960_Dialog').dialog('destroy');
							$('#sysAdmin_10960_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_10960_datagrid').datagrid('unselectAll');
						}
					}
				});
			} else {
				$('#sysAdmin_10960_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//退回
	function sysAdmin_10960_updateBack(bmthid){
		$('<div id="sysAdmin_10960_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回原因</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10961.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#bmthid_10961').val(bmthid);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10961_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantThreeUpload_updateMerchantThreeUploadBack.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10960_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10960_datagrid').datagrid('reload');
					    			$('#sysAdmin_10960_editDialog').dialog('destroy');
					    			$('#sysAdmin_10960_Dialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10960_datagrid').datagrid('reload');
					    			$('#sysAdmin_10960_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10960_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//查看
	function sysAdmin_10960_serchFun(index){
		var rows = $('#sysAdmin_10960_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10960_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10962.jsp?BMTHID='+row.BMTHID,
		    modal: true,
		    onLoad:function() {
		    	if (row.ISCONNECT==0){
		    		row.ISCONNECT="不支持";
				}else if (row.ISCONNECT==1){
					row.ISCONNECT="支持";
				}
		    	if (row.ISIMMUNITY==0){
		    		row.ISIMMUNITY="不支持";
				}else if (row.ISIMMUNITY==1){
					row.ISIMMUNITY="支持";
				}
		    	if (row.ISUNIONPAY==0){
		    		row.ISUNIONPAY="不支持";
				}else if (row.ISUNIONPAY==1){
					row.ISUNIONPAY="支持";
				}
		    	$('#sysAdmin_10962_from').form('load', row);
			},
			buttons:[{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function() {
						$('#sysAdmin_10960_Dialog').dialog('destroy');
					}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10960_searchFun() {
		$('#sysAdmin_10960_datagrid').datagrid('load', serializeObject($('#sysAdmin_10960_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10960_cleanFun() {
		$('#sysAdmin_10960_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10960_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;商户号&nbsp;</th>
					<td><input name="mid" style="width: 138px;" /></td>
					<th>&nbsp;SN号&nbsp;</th>
					<td><input name="sn" style="width: 138px;" /></td>
					<th>&nbsp;审核状态&nbsp;</th>
					<td>
						<select name="approveStatus" style="width: 138px;">
							<option value="" >所有</option>
							<option value="W" >待审核</option>
							<option value="K" >退回</option>
							<option value="Y" >通过</option>
						</select>
					</td>
					
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10960_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10960_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10960_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>
