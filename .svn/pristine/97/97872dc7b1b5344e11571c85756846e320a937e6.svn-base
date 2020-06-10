<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_1001011').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_listTerminalInfoUnno.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'allotConfirmDate',
			sortOrder: 'desc',
			idField : 'allotConfirmDate',
			columns : [[{
				title : '编号',
				field : 'btID',
				width : 10,
				hidden : true
			},{
				title :'机构名称',
				field :'unitName',
				width : 140
			},{
				title :'终端编号',
				field :'termID',
				width : 90
			},{
				title :'密钥类型',
				field :'keyType',
				width : 100,
				formatter:function(value,row,index){
					if(value=='1'){
						return '短密钥';
					}else if(value=='2'){
						return '长密钥';
					}else{
						return value;
					}
				}
			},{
				title :'密钥明文',
				field :'keyContext',
				width : 100
			},{
				title :'合成时间',
				field :'keyConfirmDate',
				width : 150
			},{
				title :'分配时间',
				field :'allotConfirmDate',
				width : 150
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_1001011_editFun('+row.btID+')"/>';
				}
			}
			]]		
		});
	});
	
	
	function sysAdmin_1001011_editFun(bid) {
	    
		$('<div id="sysAdmin_1001011_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改业务员信息</span>',
			width: 350,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10351.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_1001012_editForm').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.BTID);  
		    	$('#sysAdmin_1001012_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
				var statuss= $('#selectvalue').combobox('getValue');
					$('#sysAdmin_1001012_editForm').form('submit', {
						url:'${ctx}/sysAdmin/terminalInfo_updateTerminalInfoUnno.action?status2='+statuss+'&bids='+bid,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_1001011').datagrid('reload');
					    			$('#sysAdmin_1001011_editDialog').dialog('destroy');
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
					$('#sysAdmin_1001011_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10350_searchFun() {
		$('#sysAdmin_1001011').datagrid('load', serializeObject($('#sysAdmin_10350_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10350_cleanFun() {
		$('#sysAdmin_10350_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10350_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th >终端编号</th>
					<td><input name="termID" style="width: 316px;" /></td>
					
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端编号：</th>
					<td>
						<input name="termIDStart" />&nbsp;至&nbsp;
						<input name="termIDEnd" />
					</td>
				</tr> 
				<tr>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10350_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10350_cleanFun();">清空</a>		
					</td>
				</tr>
			</table>
		</form>
	</div>  
	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_1001011" style="height:170px; overflow: hidden;"></table>
	</div>
</div>
