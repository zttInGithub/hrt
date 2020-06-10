<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
	
		$("#unNo").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action?unLvl=1,2',
			valueField : 'UNNO',
			textField : 'UN_NAME',
			onSelect:function(rec){
				$("#unNo").val(rec.UNNO);
				$('#sysAdmin_terminalAllot_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalAllot_searchForm')));
			}
		});
		$('#sysAdmin_terminalAllot_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findAllot.action',
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			sortName: 'termID',
			sortOrder: 'asc',
			idField : 'btID',
			columns : [[{
				field : 'btID',
				checkbox:true
			},{
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
				title :'合成情况',
				field :'keyConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未合成';
					}else{
						return '已合成';
					}
				}
			},{
				title :'分配情况',
				field :'allotConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未分配';
					}else{
						return '已分配';
					}
				}
			}/* ,{
				title :'密钥明文',
				field :'keyContext',
				width : 100,
				sortable:true
			} */]],
			toolbar:[ {
					id:'btn_add',
					text:'终端分配',
					iconCls:'icon-confirm',
					handler:function(){
						sysAdmin_terminalAllot_allotFun();
					}
				}]			
		});
	});
	
	//分配
	function sysAdmin_terminalAllot_allotFun() {
	var checkedItems = $('#sysAdmin_terminalAllot_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.btID);
		});               
		var btids=names.join(",");
		if(btids==null||btids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$.messager.confirm('确认','您确认所选终端号都分配正确了吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/terminalInfo_editAllot.action",
					type:'post',
					data:{"btids":btids},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_terminalAllot_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_terminalAllot_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '修改记录出错！');
						$('#sysAdmin_terminalAllot_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_terminalAllot_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10340_searchFun() {
		$('#sysAdmin_terminalAllot_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalAllot_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10340_cleanFun() {
		$('#sysAdmin_terminalAllot_searchForm input').val('');
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_terminalAllot_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构名称</th>
					<td><input id="unNo" name="unNO" class="easyui-combobox" style="width: 300px;" />
						<input id="btids" name="btids" type="hidden">
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
						onclick="sysAdmin_10340_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10340_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_terminalAllot_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


