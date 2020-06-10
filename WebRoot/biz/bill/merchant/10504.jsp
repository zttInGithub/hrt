<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10504_datagrid').datagrid({
			url :'${ctx}/sysAdmin/aggPayTerminfo_listAggPayTerminalInfoZ.action?status1=0,1,2,3,4',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ,100],
			checkOnSelect:true,
			sortName: 'BAPID',
			sortOrder: 'desc',
			idField : 'BAPID',
			columns : [[{
				title : '唯一主键',
				field : 'BAPID',
				width : 100,
				checkbox:true
			},{
				title : '机构编号',
				field : 'UNNO',
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'聚合终端',
				field :'QRTID',
				width : 100
			},{
				title :'聚合终端店名',
				field :'SHOPNAME',
				width : 100
			},{
				title :'扫码费率',
				field :'SCANRATE',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					 return '';
					//return '<img src="${ctx}/images/start.png" title="审批" style="cursor:pointer;" onclick="sysAdmin_merchantconfirm10504Y_editFun10504('+index+')"/>&nbsp;&nbsp'+
					//'<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_merchantconfirm10504K_editFun10504('+index+')"/>';
				}
			}]],
			toolbar:[{
				id:'btn_shenSelected',
				text:'勾选审批',
				iconCls:'l-btn-text icon-start',
				handler:function(){
					sysAdmin_terminalinfoSelected10504_shenFun();
				}
			}]
		});
	});
	
	//全部导出
	function sysAdmin_terminalinfo10504_exportFun() {
		$('#sysAdmin_10504_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantterminalinfo_exportAddTerminalInfo.action'
			    	});
	}
	//勾选导出
	function sysAdmin_terminalinfoSelected10504_exportFun() {
		var checkedItems = $('#sysAdmin_10504_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.BAPID);
		});               
		var BAPIDs=names.join(",");
		if(BAPIDs==null||BAPIDs==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmtids").val(BAPIDs);
		$('#sysAdmin_10504_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantterminalinfo_exportAddTerminalInfoSelected.action?status1=0,1,2,3,4'
			    	});
	}
	//勾选审批
	function sysAdmin_terminalinfoSelected10504_shenFun() {
		var checkedItems = $('#sysAdmin_10504_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.BAPID);
		});               
		var BAPIDs=names.join(",");
		if(BAPIDs==null||BAPIDs==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#BAPIDs").val(BAPIDs);
		$('#sysAdmin_10504_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/aggPayTerminfo_AddAggPayTerminalInfoSelected.action',
			    		success:function(data){
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
			    					$('#sysAdmin_10504_datagrid').datagrid('reload');
			    					$.messager.show({
										title : '提示',
										msg : result.msg
									});
			    				}else {
					    			//$('#sysAdmin_merchantconfirm10504_openDialog').dialog('destroy');
					    			$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
			    			}
			    		}
			    	});
		}
	
	
	function sysAdmin_merchantconfirm10504Y_editFun10504(index){
		$('<div id="sysAdmin_merchantconfirm10504_openDialog"/>').dialog({
			title: '<span style="color:#157FCC;">开通</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10432.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10504_datagrid').datagrid('getRows');
				var row = rows[index];	
		    	$('#sysAdmin_merchantconfirm10504_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantconfirm10504_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchantconfirm10504_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantterminalinfo_updateMerchantTerminalinfoY.action',
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				//$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10504_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantconfirm10504_openDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantconfirm10504_openDialog').dialog('destroy');
					    			//$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantconfirm10504_openDialog').dialog('destroy');
					//$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				//$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_merchantconfirm10504K_editFun10504(index){
		$('<div id="sysAdmin_merchantconfirm10504_closeDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10431.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10504_datagrid').datagrid('getRows');
				var row = rows[index];	
		    	$('#sysAdmin_merchantconfirm10504_editForm2').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantconfirm10504_editForm2').form('submit', {
						url:'${ctx}/sysAdmin/merchantterminalinfo_updateMerchantTerminalinfoK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10504_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantconfirm10504_closeDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantconfirm10504_closeDialog').dialog('destroy');
					    			$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantconfirm10504_closeDialog').dialog('destroy');
					$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10504_datagrid').datagrid('unselectAll');
			}
		});
	}
	
    //表单查询
	function sysAdmin_10504_searchFun10() {
		$('#sysAdmin_10504_datagrid').datagrid('load', serializeObject($('#sysAdmin_10504_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10504_cleanFun10() {
		$('#sysAdmin_10504_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10504_searchForm" style="padding-left:10%">
			<input type="hidden" id="BAPIDs" name="BAPIDs" />
			<table class="tableForm" >
				<tr>
					<th>机构号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					<td width="20px"> </td>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10504_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10504_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10504_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>




