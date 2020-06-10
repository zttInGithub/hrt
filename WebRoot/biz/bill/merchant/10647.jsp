<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10647_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantAuthenticity_listHrtAuthenticity.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmatid',
			sortOrder: 'desc',
			idField : 'bmatid',
			columns : [[{
				field : 'bmatid',
				checkbox:true,
				hidden:true
			},{
				title : '机构号',
				field : 'unno',
				width : 90
			},{
				title :'身份证号',
				field :'legalNum',
				width : 120,
				sortable :true
			},{
				title :'持卡人名称',
				field :'bankAccName',
				width : 70,
				sortable :true
			},{
				title :'卡号',
				field :'bankAccNo',
				width : 130
			},{
				title :'通道',
				field :'cardName',
				width : 70
			},{
				title :'认证状态',
				field :'status',
				width : 50,
				formatter : function(value,row,index) {
						if (value=='00'){
						   return "已认证";
						}else if(value=='01'){
							return "认证中";
						}else if(value==''||value==null){
							   return "未认证";
						}
					}
			},{
				title :'认证返回信息',
				field :'respinfo',
				width : 190
				//sortable :true
			},{
				title :'认证时间',
				field :'cdate',
				width : 120,
				sortable :true
			}]],
			toolbar:[{
				id:'btn_run',
				text:'导出资料',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10647_exportFun();
				}
			}]
		});
	});

	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun10647() {
		var start = $("#10647_cdate").datebox('getValue');
    	var end = $("#10647_cdate1").datebox('getValue');
	   	if((!start&&end)||(!end&&start)){
	   		$.messager.alert('提示', "查询时间必须为时间段");
	   		return;
	   	}
	   	start = start.replace(/\-/gi, "/");
	   	end = end.replace(/\-/gi, "/");
	    var startTime = new Date(start).getTime();
	    var endTime = new Date(end).getTime();
	   	if ((endTime-startTime)>(1000*3600*24*30)){
	   	   	$.messager.alert('提示', "查询最长时间为30天");
	   	   	return;
	   	}
	   	if ((endTime-startTime)<0){
	   	   	$.messager.alert('提示', "起始日期需小于截止日期");
	   	   	return;
	   	}
		$('#sysAdmin_10647_datagrid').datagrid('load', serializeObject($('#sysAdmin_10647_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun10647() {
		$('#sysAdmin_10647_searchForm input').val('');
		$('#sysAdmin_10647_searchForm select').val('全部');
	}
	
	function sysAdmin_10647_exportFun(){
		$('#sysAdmin_10647_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantAuthenticity_exportHrtAuthenticity.action'
			    	});
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_10647_searchForm" style="padding-left:7%" method="post">
		<input type="hidden" id="bmatids" name="bmatids" />
			<table class="tableForm" >
				<tr>
					<th>机构号&nbsp;&nbsp;</th>
					<td><input name="unno" style="width: 100px;" /></td>
					<th>&nbsp;&nbsp;持卡人姓名&nbsp;&nbsp;</th>
					<td><input name=bankAccName style="width: 100px;" /></td>
					
					<th>&nbsp;&nbsp;卡号&nbsp;&nbsp;</th>
					<td><input name="bankAccNo" style="width: 150px;" /></td>
					<th>&nbsp;&nbsp;认证状态&nbsp;&nbsp;</th>
					<td>
						<select name="status" style="width: 100px;">
							<option value="" selected="selected">全部</option>
							<option value="00">认证成功</option>
							<option value="01">认证失败</option>
						</select>
					</td>
					<th>&nbsp;&nbsp;认证日期&nbsp;&nbsp;</th>
					<td><input id="10647_cdate" name="cdate" class="easyui-datebox" style="width: 100px;"  /></td>
					
					<td>&nbsp;-&nbsp;<input id="10647_cdate1" name="cdate1" class="easyui-datebox" style="width: 100px;"  /></td>
				</tr>
				<tr>
					<td colspan="11" style="padding-top:10px;text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun10647();">查询&nbsp;&nbsp;</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun10647();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10647_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


