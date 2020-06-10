<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10430_datagrid').datagrid({
			url :'',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ,100],
			checkOnSelect:true,
			sortName: 'bmtid',
			sortOrder: 'desc',
			idField : 'bmtid',
			columns : [[{
				title : '唯一主键',
				field : 'bmtid',
				width : 100,
				checkbox:true
			},{
				title : '机构编号',
				field : 'unno',
				width : 100
			},{
				title :'商户编号',
				field :'mid',
				width : 100
			},{
				title :'商户类型',
				field :'isM35',
				width : 100,
				formatter : function(value,row,index) {
					if(value==1){
						return "手刷";
					}else if (value==2){
					   return "传统个人";
					}else if (value==3){
					   return "传统企业";
					}else if (value==4){
					   return "传统优惠";
					}else if (value==5){
					   return "传统减免";
					}else{
					   return "传统标准";
					}
				}
			},{
				title :'终端',
				field :'tid',
				width : 100
			},{
				title :'入库sn号',
				field :'sn',
				width : 100
			},{
				title :'TUSN',
				field :'merSn',
				width : 100
			},{
				title :'机型',
				field :'bmaidName',
				width : 100
			},{
				title :'装机地址',
				field :'installedAddress',
				width : 100
			},{
				title :'装机名称',
				field :'installedName',
				width : 100
			},{
				title :'联系人',
				field :'contactPerson',
				width : 80
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/start.png" title="审批" style="cursor:pointer;" onclick="sysAdmin_merchantconfirmY_editFun('+index+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_merchantconfirmK_editFun('+index+')"/>';
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_terminalinfo10430_exportFun();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_terminalinfoSelected10430_exportFun();
				}
			},{
				id:'btn_shenSelected',
				text:'勾选审批',
				iconCls:'l-btn-text icon-start',
				handler:function(){
					sysAdmin_terminalinfoSelected10430_shenFun();
				}
			}]
		});
	});
	
	//全部导出
	function sysAdmin_terminalinfo10430_exportFun() {
		$('#sysAdmin_10430_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantterminalinfo_exportAddTerminalInfo.action'
			    	});
	}
	//勾选导出
	function sysAdmin_terminalinfoSelected10430_exportFun() {
		var checkedItems = $('#sysAdmin_10430_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtid);
		});               
		var bmtids=names.join(",");
		if(bmtids==null||bmtids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmtids").val(bmtids);
		$('#sysAdmin_10430_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantterminalinfo_exportAddTerminalInfoSelected.action'
			    	});
	}
	//勾选审批
	function sysAdmin_terminalinfoSelected10430_shenFun() {
		var checkedItems = $('#sysAdmin_10430_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtid);
		});               
		var bmtids=names.join(",");
		if(bmtids==null||bmtids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmtids").val(bmtids);
		$('#sysAdmin_10430_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantterminalinfo_shenAddTerminalInfoSelected.action',
			    		success:function(data){
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
			    					$('#sysAdmin_10430_datagrid').datagrid('reload');
			    					$.messager.show({
										title : '提示',
										msg : result.msg
									});
			    				}else {
					    			//$('#sysAdmin_merchantconfirm_openDialog').dialog('destroy');
					    			$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
			    			}
			    		}
			    	});
		}
	
	
	function sysAdmin_merchantconfirmY_editFun(index){
		$('<div id="sysAdmin_merchantconfirm_openDialog"/>').dialog({
			title: '<span style="color:#157FCC;">开通</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10432.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10430_datagrid').datagrid('getRows');
				var row = rows[index];	
		    	$('#sysAdmin_merchantconfirm_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_merchantconfirm_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_merchantconfirm_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantterminalinfo_updateMerchantTerminalinfoY.action',
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				//$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10430_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantconfirm_openDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantconfirm_openDialog').dialog('destroy');
					    			//$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantconfirm_openDialog').dialog('destroy');
					//$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				//$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_merchantconfirmK_editFun(index){
		$('<div id="sysAdmin_merchantconfirm_closeDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10431.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10430_datagrid').datagrid('getRows');
				var row = rows[index];	
		    	$('#sysAdmin_merchantconfirm_editForm2').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantconfirm_editForm2').form('submit', {
						url:'${ctx}/sysAdmin/merchantterminalinfo_updateMerchantTerminalinfoK.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10430_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantconfirm_closeDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantconfirm_closeDialog').dialog('destroy');
					    			$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantconfirm_closeDialog').dialog('destroy');
					$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10430_datagrid').datagrid('unselectAll');
			}
		});
	}
	
    //表单查询
	function sysAdmin_10430_searchFun10() {
		/* $('#sysAdmin_10430_datagrid').datagrid('load', serializeObject($('#sysAdmin_10430_searchForm')));  */
		    var opts = $("#sysAdmin_10430_datagrid").datagrid("options");
		    opts.url = "${ctx}/sysAdmin/merchantterminalinfo_listMerchantTerminalInfoZ.action";
		    $("#sysAdmin_10430_datagrid").datagrid("load", serializeObject($('#sysAdmin_10430_searchForm')));
    }

	//清除表单内容
	function sysAdmin_10430_cleanFun10() {
		$('#sysAdmin_10430_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10430_searchForm" style="padding-left:10%">
			<input type="hidden" id="bmtids" name="bmtids" />
			<table class="tableForm" >
				<tr>
					<th>机构号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					<td width="20px"> </td>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10430_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10430_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10430_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>



