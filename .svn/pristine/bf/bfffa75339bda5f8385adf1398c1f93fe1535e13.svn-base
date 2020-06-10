<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10510_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantterminalinfo_listMerchantTerminalInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			ctrlSelect:true,
			checkOnSelect:true,
			idField : 'bmtid',
			columns : [[{
				field : 'bmtid',
				checkbox:true
			},{
				title :'机构名称',
				field :'unitName',
				width : 100
			},{
				title :'商户编号',
				field :'mid',
				width : 100
			},{
				title :'终端',
				field :'tid',
				width : 100
			},{
				title :'机型',
				field :'bmaidName',
				width : 50
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
				width : 100
			},{
				title :'联系人电话',
				field :'contactPhone',
				width : 100,
                formatter : function(value,row,index) {
                    if(value!=""&&value!=null && value.length==11){
                        return value.substring(0,3)+'****'+value.substring(value.length-4,value.length);
                    }
                }
			},{
				title :'申请日期',
				field :'maintainDate',
				width : 150
			},{
				title :'受理状态',
				field :'approveStatusName',
				width : 100
			},{
				title :'受理描述',
				field :'processContext',
				width : 100,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title :'受理人员',
				field :'approveUidName',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 50,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatusName=="退回"){
						return '<img src="${ctx}/images/frame_pencil.png" title="编辑" style="cursor:pointer;" onclick="sysAdmin_10510_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;';
					}
					
			}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo_exportFun1();
				}
			}]
		});
	});
	
	function sysAdmin_10510_editFun(index){
		$('<div id="sysAdmin_merchant_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">商户终端退回</span>',
			width: 900,
		    height:350, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10511.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10510_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchantterminalinfo_addForm1').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
				
					$('#sysAdmin_merchantterminalinfo_addForm1').form('submit', {
						url:'${ctx}/sysAdmin/merchantterminalinfo_merchantTerminalInfoEdit.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10510_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchant_editDialog').dialog('destroy');
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
					$('#sysAdmin_merchant_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
		
	}
	
	function sysAdmin_merchantterminalinfo_exportFun1() {
		var checkedItems = $('#sysAdmin_10510_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmtid);
		});               
		var id=names.join(",");
		if(id==null||id==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#id").val(id);
		$('#sysAdmin_10510_searchForm').form('submit',{
			url:'${ctx}/sysAdmin/merchantterminalinfo_export.action'
		});
	}

	//表单查询
	function sysAdmin_10510_searchFun() {
		$('#sysAdmin_10510_datagrid').datagrid('load', serializeObject($('#sysAdmin_10510_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10510_cleanFun() {
		$('#sysAdmin_10510_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
	<form id="sysAdmin_10510_searchForm" style="padding-left:10%">
		<input type="hidden" id="id" name="id" />
		<table class="tableForm" >
			<tr>
				<th >商户编号</th>
				<td><input name="mid" style="width: 240px;" /></td>
				<th >终端编号</th>
				<td><input name="tid" style="width: 240px;" /></td>
				
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
					onclick="sysAdmin_10510_searchFun();">查询</a> &nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
					onclick="sysAdmin_10510_cleanFun();">清空</a>	
				</td>
			</tr>
		</table>
	</form>
</div>

 <div data-options="region:'center', border:false" style="overflow: hidden;">  
<table id="sysAdmin_10510_datagrid" style="overflow: hidden;"></table>
</div>
</div>