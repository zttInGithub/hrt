<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10439_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantTwoUpload_listMerchantTwoUpload.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [10, 15 ],
			remoteSort:true,
			idField : 'BMTUID',
			columns : [[{
				title : '商户ID',
				field : 'BMTUID',
				width : 100,
				hidden : true
			},{
				title : '商户号',
				field : 'MID',
				width : 100
			},{
				title : '商户名称',
				field : 'RNAME',
				width : 100
			},{
				title : '法人身份证',
				field : 'LEGALNAME',
				width : 100,
				formatter : function(value,row,index) {
					if(value == null){
						return "未上传";
					}else{
						return "已上传"
					}
				}
			},{
				title : '法人身份证反面',
				field : 'LEGALNAME2',
				width : 100,
				formatter : function(value,row,index) {
					if(value == null){
						return "未上传";
					}else{
						return "已上传"
					}
				}
			},{
				title : '营业执照',
				field : 'BUPLOAD',
				width : 100,
				formatter : function(value,row,index) {
					if(value == null){
						return "未上传";
					}else{
						return "已上传"
					}
				}
			},{
				title :'大协议',
				field :'BIGDEALUPLOAD',
				width : 100,
				formatter : function(value,row,index) {
					if(value == null){
						return "未上传";
					}else{
						return "已上传"
					}
				}
			},{
				title :'含银联云闪付标贴的店内经营照片',
				field :'LABORCONTRACTIMG',
				width : 150,
				formatter : function(value,row,index) {
					if(value == null){
						return "未上传";
					}else{
						return "已上传"
					}
				}
			},{
				title :'申请时间',
				field :'MAINTAINDATE',
				width : 100
			},{
				title :'状态',
				field :'APPROVESTATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 'W'){
						return "待审核";
					}else if(value == 'K'){
						return "退回"
					}else if(value == 'Y'){
						return "通过";
					}
				}
			},{
				title :'退回原因',
				field :'APPROVENOTE',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 80,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.APPROVESTATUS == 'W'){
						return '<img src="${ctx}/images/start.png" title="审核" style="cursor:pointer;" onclick="sysAdmin_10439_queryFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10439_queryFun2('+index+')"/>&nbsp;&nbsp';
					}
					
				} 
			}]]	
		});
	});
	//查看
	function sysAdmin_10439_queryFun(index){
		var rows = $('#sysAdmin_10439_datagrid').datagrid('getRows');
		var row  = rows[index];
		var bmtuid = row.BMTUID;
		$('<div id="sysAdmin_10439_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10436.jsp?bmtuid='+bmtuid,
		    modal: true,
			buttons:[{
				text:'审批',
				iconCls:'icon-ok',
				handler:function() {
					sysAdmin_10439_queryEditFunY(bmtuid);
				}
			},{
				text:'退回',
				iconCls:'icon-cancel',
				handler:function() {
					sysAdmin_10439_queryEditFun(bmtuid);
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10439_Dialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//查看
	function sysAdmin_10439_queryFun2(index){
		var rows = $('#sysAdmin_10439_datagrid').datagrid('getRows');
		var row  = rows[index];
		var bmtuid = row.BMTUID;
		$('<div id="sysAdmin_10439_Dialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10436.jsp?bmtuid='+bmtuid,
		    modal: true,
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10439_Dialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//通过
	function sysAdmin_10439_queryEditFunY(bmtuid){
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTwoUpload_updateMerchantInfoY.action',
			type:'post',
			data:{bmtuid:bmtuid},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
    				$('#sysAdmin_10439_datagrid').datagrid('reload');
    				$('#sysAdmin_10439_Dialog').dialog('destroy');
	    			$.messager.show({
						title : '提示',
						msg : result.msg
					});
				} else {
					$('#sysAdmin_10439_Dialog').dialog('destroy');
	    			$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
	    			$.messager.alert('提示', result.msg);
				}
			},
			error:function() {
				$.messager.alert('提示', '通过记录出错！');
				$('#sysAdmin_10621_datagrid').datagrid('unselectAll');
			}
		});
	}
	//退回
	function sysAdmin_10439_queryEditFun(bmtuid){
		$('<div id="sysAdmin_10439_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    onLoad:function() {
		    	$('#bmtuid_10438').val(bmtuid);
			},
		    href: '${ctx}/biz/bill/merchant/10438.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10438_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantTwoUpload_updateMerchantInfoK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10439_datagrid').datagrid('reload');
				    				$('#sysAdmin_10439_Dialog').dialog('destroy');
					    			$('#sysAdmin_10439_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10439_Dialog').dialog('destroy');
					    			$('#sysAdmin_10439_editDialog').dialog('destroy');
					    			$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10439_editDialog').dialog('destroy');
					$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10439_datagrid').datagrid('unselectAll');
			}
		});
	}
		
	//表单查询
	function sysAdmin_10439_searchFun10() {
		$('#sysAdmin_10439_datagrid').datagrid('load', serializeObject($('#sysAdmin_10439_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10439_cleanFun10() {
		$('#sysAdmin_10439_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:25px;">
		<form id="sysAdmin_10439_searchForm" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 116px;" /></td>
				    
					<th>申请时间</th>
					<td><input name="maintaindate" class="easyui-datebox" style="width: 150px;"  /></td>
					
					<th>状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:150px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="W">待审核</option>
		    				<option value="K">退回</option>
		    				<option value="Y">已审核</option>
		    			</select>
		    		</td>
					
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10439_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10439_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10439_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

