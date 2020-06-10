<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		var mid = <%=request.getParameter("mid")%>;
		$('#sysAdmin_10482_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantterminalinfo_listMerchantTerminalInfoBmid.action?mid='+mid,
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmtid',
			sortOrder: 'desc',
			idField : 'bmtid',
			columns : [[{
				title : '唯一主键',
				field : 'bmtid',
				width : 100,
				hidden : true
			},{
				title :'终端',
				field :'tid',
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
				title :'报单日期',
				field :'maintainDateStr',
				width : 100
			},{
				title :'批单日期',
				field :'approveDateStr',
				width : 100
			},{
				title :'装机日期',
				field :'approveDateStr',
				width : 100
			}
			,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_merchantList_editFun('+index+')"/>'; 
				}
			}]]
		});
	});
	
	//修改
	function sysAdmin_merchantList_editFun(index){
		$('<div id="sysAdmin_merchantterminalinfo__editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改装机信息</span>', 
			width: 920,
		    height:300, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10486.jsp',
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10482_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchantterminalinfo_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantterminalinfo_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantterminalinfo_updateMerchantTerminalInfo.action',
						type:'POST',
						success:function(data) {
						var obj = eval('('+data+')');
						if (obj.success) {
							$.messager.show({
								title : '提示',
								msg : obj.msg
							});
							$('#sysAdmin_10482_datagrid').datagrid('unselectAll');
							$('#sysAdmin_10482_datagrid').datagrid('reload');
							$('#sysAdmin_merchantterminalinfo__editDialog').dialog('destroy');
						} else {
							$.messager.alert('提示', obj.msg);
							$('#sysAdmin_10482_datagrid').datagrid('unselectAll');
							$('#sysAdmin_merchantterminalinfo__editDialog').dialog('destroy');
						}
						}
					});
				}
			},{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantterminalinfo__editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10482_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10482_searchFun() {
		$('#sysAdmin_10482_datagrid').datagrid('load', serializeObject($('#sysAdmin_10482_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10482_cleanFun() {
		$('#sysAdmin_10482_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10482_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>终端编号</th>
					<td><input name="tid" style="width: 156px;" /></td>
					
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10482_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10482_cleanFun();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10482_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


