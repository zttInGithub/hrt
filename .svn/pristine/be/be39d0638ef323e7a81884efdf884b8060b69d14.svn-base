<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$("#unName").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action?unLvl=1,2',
			valueField : 'UNNO',
			textField : 'UN_NAME',
			onSelect:function(rec){
				$("#unName").val(rec.UNNO);
				$('#sysAdmin_terminalConfirm_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalConfirm_searchForm')));
			}
		});
		$('#sysAdmin_terminalConfirm_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findConfirm.action',
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'termID',
			sortOrder: 'asc',
			idField : 'btID',
			columns : [[{
				title :'机构名称',
				field :'unitName',
				width : 100
			},{
				title :'终端编号',
				field :'termID',
				width : 100,
				sortable:true
			},{
				title :'密钥类型',
				field :'keyType',
				width : 100,
				sortable:true,
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
				width : 100,
				sortable:true
			},{
				title :'生成时间',
				field :'maintainDate',
				width : 100,
				sortable:true
			}]],
			toolbar:[ {
					text:'密钥全部导出',
					iconCls:'icon-query-export',
					handler:function(){
						sysAdmin_terminalConfirm_exportFun();
					}
				},/*{
					text:'删除',
					iconCls:'icon-remove'
				}, */{
					id:'btn_add',
					text:'密钥确认',
					iconCls:'icon-confirm',
					handler:function(){
						sysAdmin_terminalConfirm_conFun();
					}
				}]			
		});
	});
	
	//确认
	function sysAdmin_terminalConfirm_conFun() {
		var unno=$("#unName").combobox('getValue');
		if(unno==null||unno==""){
			$.messager.alert('提示',"请选择机构");
			return;
		}
		$.messager.confirm('确认','您确认密钥都已导出，机构号输入正确?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_editConfirm.action",
					type:'post',
					data:{"unNO":unno},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_terminalConfirm_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_terminalConfirm_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '修改记录出错！');
						$('#sysAdmin_terminalConfirm_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_terminalConfirm_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//导出
	function sysAdmin_terminalConfirm_exportFun() {
		var unno=$("#unName").combobox('getValue');
		if(unno==null||unno==""){
			$.messager.alert('提示',"请选择机构");
			return;
		}
		$('#sysAdmin_terminalConfirm_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/terminalInfo_exportConfirm.action'
			    	});
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:40px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_terminalConfirm_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th > 机构名称</th>
					<td><input id="unName" name="unNO" class="easyui-combobox" style="width: 300px;"/>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_terminalConfirm_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


