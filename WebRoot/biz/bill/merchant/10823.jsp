<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantMicroPlusList_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantMicroToUnbidPlus.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'joinConfirmDate',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				field : 'bmid',
				checkbox:true
			},{
				title : '机构名称',
				field : 'unno',
				width : 100
			},{
				title :'商户编号',
				field :'mid',
				width : 100,
				sortable :true
			},{
				title :'SN号',
				field :'sn',
				width : 100,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'商户入网时间',
				field :'joinConfirmDate',
				width : 100,
				sortable :true
			},{
			    title :'受理状态',
				field :'approveStatus',
				width : 100,
				formatter:function(value,row,index){
					if(value=="K"){ 
						return '退回'; 
					}else if(value=="Z"){
						return '待审核';
					}else if(value=='W'){
						return '待审批'; 
					}else if(value=='Y'){
						return '审核通过'; 
					}else if(value=='C'){
						return '审批中';
					}
				} 
			},{
				title :'操作',
				field :'OPERATION',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看终端" style="cursor:pointer;" onclick="sysAdmin_10480_queryFun(\''+row.mid+'\')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="解绑终端" style="cursor:pointer;" onclick="sysAdmin_merchant_unbindSn('+index+')"/>';
				}
			}]]/* ,
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo_exportFun();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected_exportFun();
				}
			}] */
		});
	});
	
	function sysAdmin_merchantterminalinfo_exportFun() {
		$('#sysAdmin_merchantPlusUnbid_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMer.action'
			    	});
	}
	
	function sysAdmin_merchantterminalinfoSelected_exportFun() {
		var checkedItems = $('#sysAdmin_merchantMicroPlusList_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmids_10823").val(bmids);
		$('#sysAdmin_merchantPlusUnbid_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMerSelected.action'
			    	});
	}
	

	
	//表单查询
	function sysAdmin_merchantUnbid_searchFun80() {
		$('#sysAdmin_merchantMicroPlusList_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchantPlusUnbid_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantUnbid_cleanFun80() {
		$('#sysAdmin_merchantPlusUnbid_searchForm input').val('');
	}
	
	//查看终端
	function sysAdmin_10480_queryFun(mid){
		$('<div id="sysAdmin_10489_run"/>').dialog({
			title: '查看终端',
			width: 850,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10487.jsp?mid='+mid,  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	
	//解绑终端
	function sysAdmin_merchant_unbindSn(index){
		var rows = $('#sysAdmin_merchantMicroPlusList_datagrid').datagrid('getRows');
		var row = rows[index];
		var sn = row.sn;
		var mid = row.mid;
		$.messager.confirm('确认','您确认要解绑终端吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/merchant_unbindPlusSN.action",
					type:'post',
					data:{"Sn":sn,"mid":mid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_merchantMicroPlusList_datagrid').datagrid('unselectAll');
							$('#sysAdmin_merchantMicroPlusList_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '解绑终端出错！');
						$('#sysAdmin_merchantMicroPlusList_datagrid').treegrid('unselectAll');
					}
				});
			}
		});
	}
	


</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchantPlusUnbid_searchForm" style="padding-left:10%">
		<input type="hidden" id="bmids_10823" name="bmids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
				</tr> 
				<tr>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SN号</th>
					<td><input name="sn" style="width: 100px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="C">审批中</option>
		    				<option value="Y">审批通过</option>
		    				<option value="K">退回</option>
		    			</select>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantUnbid_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantUnbid_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantMicroPlusList_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


