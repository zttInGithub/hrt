<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantList10631_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMicroMerchantInfoY10631.action',
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
				field : 'unitName',
				width : 120
			},{
				title :'商户编号',
				field :'mid',
				width : 130,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'手机号',
				field :'hybPhone',
				width : 130,
				formatter : function(value,row,index) {
                    if(value!=""&&value!=null && value.length==11){
                        return value.substring(0,3)+'****'+value.substring(value.length-4,value.length);
                    }else{
                    	return "";
                    }
                }
			},{
				title :'归属机构',
				field :'parentUnitName',
				width : 100
			},{
				title :'业务人员',
				field :'busidName',
				width : 100
			},{
				title :'维护人员',
				field :'maintainUserIdName',
				width : 100
			},{
				title :'终端数量',
				field :'tidCount',
				width : 80
			},{
				title :'商户审批时间',
				field :'approveDate',
				width : 100,
				sortable :true
			},{
				title :'商户入网时间',
				field :'joinConfirmDate',
				width : 100,
				sortable :true
			},{
				title :'受理人员',
				field :'approveUidName',
				width : 100
			},{
			    title :'受理状态',
				field :'approveStatusName',
				width : 100
			},{
			    title :'当前状态',
				field :'nonConnection',
				width : 100,
				formatter : function(value,row,index) {
					if(value=="D"){
						return "已注销";
					}else{
						return "正常";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/close.png" title="注销" style="cursor:pointer;" onclick="sysAdmin_merchantList10631_editFun('+index+')"/>';
				}
			}]]
		});
	});
	function sysAdmin_merchantList10631_editFun(index){
		var rows = $('#sysAdmin_merchantList10631_datagrid').datagrid('getRows');
		var row = rows[index];
		var mid = row.mid;
		$.messager.confirm('确认','您确认注销当前商户?',function(result) {
			if (result) {
				$.ajax({
					url:'${ctx}/sysAdmin/merchant_deleteMposMer.action',
					type:'post',
					data:{"mid":mid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$('#sysAdmin_merchantList10631_datagrid').datagrid('reload');
			    			$.messager.show({
								title : '提示',
								msg : data.msg
							});
						} else {
							$.messager.alert('提示', data.msg);
						}
					}
				});
			}
		});
	}
	//表单查询
	function sysAdmin_merchant_searchFun10631() {
		$('#sysAdmin_merchantList10631_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchant10631_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchant_cleanFun10631() {
		$('#sysAdmin_merchant10631_searchForm input').val('');
	}
	

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchant10631_searchForm" style="padding-left:5%" method="post">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 190px;" /></td>
					<th>手机号</th>
					<td><input name="hybPhone" style="width: 190px;" /></td>
				    
					<td colspan="8" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchant_searchFun10631();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchant_cleanFun10631();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantList10631_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


