<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10622_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_queryUpdateAddSn.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
	        striped: true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'btID',
			sortOrder: 'desc',
			idField : 'btID',
			columns : [[{
				field : 'btID',
				hidden:true
			},{
				title : '机构编号',
				field : 'unNO',
				width : 100,
				sortable:true
			},{
				title : '终端编号',
				field : 'termID',
				width : 100,
				sortable:true
			},{
				title : '当前状态',
				field : 'status',
				width : 100,
				formatter:function(value,row,index){
					if(value=="4"){
						return "可修改";
					}
				}
			},{
				title :'sn号',
				field :'sn',
				width : 100
			},{
				title :'返利类型',
				field :'rebateType',
				width : 100,
				sortable:true
			},{
				title :'操作',
				field :'operation',
				width : 100,
				sortable:true,
				align : 'center',
				formatter:function(value,row,index){
					if(row.status=="4"){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_10622_updateSN('+index+')"/>';
					}
				}
			}]]
		});
	});
	
	//修改SN状态
	function sysAdmin_10622_updateSN(index) {
		var rows = $('#sysAdmin_10622_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10622_datagrid1"/>').dialog({
			title: '<span style="color:#157FCC;">修改返利类型</span>', 
			width: 600,    
		    height: 150,  
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10623.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var rebateType_10623 = $("#sysAdmin_10623_rebateType").val();
		    		if(rebateType_10623==""){
		    			$.messager.alert('提示','请输入Sn返利类型');
		    			return;
		    		}
		    		$.messager.confirm('提示','确认修改？',function(re) {
		    			if (re) {
		    				$.ajax({
		    					url:'${ctx}/sysAdmin/terminalInfo_updateAddSn.action',
		    					data:serializeObject($('#sysAdmin_10623_updateForm')),
		    					type:'post',
		    					success:function(data) {
		    	    				var result = $.parseJSON(data);
		    	    				$('#sysAdmin_10622_datagrid1').dialog('destroy');
		    		    			if (result.sessionExpire) {
		    		    				window.location.href = getProjectLocation();
		    			    		} else {
		    			    			if (result.success) {
		    			    				$('#sysAdmin_10622_datagrid').datagrid('reload');
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
		    		});
		    		
	    		}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_10622_datagrid1').dialog('destroy');
				}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			},
			onLoad:function(){
				$('#sysAdmin_10623_btID').val(row.btID);
				$('#sysAdmin_10623_rebateType').val(row.rebateType);
			}
		});
	}
	//批量修改
	function sysAdmin_10622_update() {
		var snStart = $("#10622_snStart").val();
		var snEnd = $("#10622_snEnd").val();
		if(snEnd==""||snStart==""){
			$.messager.alert('提示','请输入完整起始Sn号');
			return;
		}
		$('<div id="sysAdmin_10622_datagrid1"/>').dialog({
			title: '<span style="color:#157FCC;">修改返利类型</span>', 
			width: 600,    
		    height: 150,  
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    //点击发货弹出的页面
		    href: '${ctx}/biz/bill/merchant/10623.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var rebateType_10623 = $("#sysAdmin_10623_rebateType").val();
		    		if(rebateType_10623==""){
		    			$.messager.alert('提示','请输入Sn返利类型');
		    			return;
		    		}
    				$.messager.confirm('确认','您确认要修改所有Sn吗?',function(result) {
		    			if (result) {
		    				var rebateType_10623 = $("#sysAdmin_10623_rebateType").val();
		    				$("#sysAdmin_10622_rebateType").val(rebateType_10623);
		    				$.ajax({
		    					url:"${ctx}/sysAdmin/terminalInfo_updateAddSn.action",
		    					type:'post',
		    					dataType:'json',
		    					data:serializeObject($('#sysAdmin_10622_updateForm')),
		    					success:function(data, textStatus, jqXHR) {
		    						$('#sysAdmin_10622_datagrid1').dialog('destroy');
		    						if (data.success) {
		    							$.messager.show({
		    								title : '提示',
		    								msg : data.msg
		    							});
		    							$('#sysAdmin_10622_datagrid').datagrid('reload');
		    						} else {
		    							$.messager.alert('提示', data.msg);
		    						}
		    					},
		    					error:function() {
		    						$.messager.alert('提示', '通过记录出错！');
		    						$('#sysAdmin_10622_datagrid').datagrid('unselectAll');
		    					}
		    				});
		    			}
		    		});
		    		
	    		}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_10622_datagrid1').dialog('destroy');
				}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			},
			onLoad:function(){
				$('#sysAdmin_10623_btID').val(row.id);
				$('#sysAdmin_10623_rebateType').val(row.rebateType);
			}
		});
		
	}
	//表单查询
	function sysAdmin_10622_find() {
		$('#sysAdmin_10622_datagrid').datagrid('load', serializeObject($('#sysAdmin_10622_updateForm'))); 
	}
	//表单清空
	function sysAdmin_10622_clear(){
		$("#sysAdmin_10622_updateForm input").val("");
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false"> 
 	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_10622_updateForm" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input id="10622_unNo" name="unNO" style="width: 157px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>Sn号</th>
					<td><input id="10622_snStart" name="snStart" style="width: 157px;" /></td>
					<th>-</th>
					<td><input id="10622_snEnd" name="snEnd" style="width: 157px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    <th>导入日期:</th>
				    <td><input id="10622_keyConfirmDate" name="keyConfirmDate" type="text" class= "easyui-datebox" editable="false" style="width: 176px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true" 
						onclick="sysAdmin_10622_find();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true" 
						onclick="sysAdmin_10622_clear();">清空</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true" 
						onclick="sysAdmin_10622_update();">修改</a> &nbsp;	
					</td>
				</tr>
			</table>
			<input id="sysAdmin_10622_rebateType" type="hidden" name="rebateType"  />
		</form>
	</div>
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10622_datagrid" style="overflow: hidden;"></table>
	</div>
</div>



