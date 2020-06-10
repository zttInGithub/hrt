<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--退货审核-->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10501_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDetail_listReturnOrderAndDetail.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'PDID',
			columns : [[{
				title : '采购ID',
				field : 'PDID',
				width : 100,
				hidden : true
			},{
				title : '订单号',
				field : 'ORDERID',
				width : 120
			},{
				title : '大类',
				field : 'ORDERMETHOD',
				width : 80,
				formatter : function(value,row,index) {
					if(value==1){
						return "厂商单";
					}else if(value==2){
						return "代理单";
					}else if(value==3){
						return "代理商单-采购";
					}
				}
			},{
				title : '供应商名称',
				field : 'VENDORNAME',
				width : 100
			},{
				title : '采购机构名称',
				field : 'PURCHASERNAME',
				width : 100
			},{
				title :'采购代理机构号',
				field :'UNNO',
				width : 100,
				sortable :true
			},{
				title :'退货数量',
				field :'MACHINENUM',
				align : 'right',
				width : 100
			},{
				title :'退货金额',
				field :'DETAILAMT',
				align : 'right',
				width : 100
			},{
				title :'退货人',
				field :'DETAILCBY',
				width : 100
			},{
				title :'状态',
				field :'DETAILSTATUS',
				width : 120,
				formatter : function(value,row,index) {
					if(value == 6&&row.DETAILAPPROVESTATUS != 'K'){
						return '待审核';
					}else if((value == 7||value ==8)&&row.DETAILAPPROVESTATUS != 'K'){
						return '已审核';
					}else if(row.DETAILAPPROVESTATUS == 'K'){
						return '已退回';
					}
				}
			},{
				title :'创建时间',
				field :'DETAILCDATE',
				width : 130
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.DETAILSTATUS == 6&&row.DETAILAPPROVESTATUS != 'K'){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10501_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/start.png" title="审核退货" style="cursor:pointer;" onclick="sysAdmin_10501_editFun('+index+')"/>&nbsp;&nbsp';
					}else if(row.DETAILSTATUS == 7||row.DETAILAPPROVESTATUS == 'K'||row.DETAILSTATUS == 8){
						return '<img src="${ctx}/images/query_search.png" title="查看订单" style="cursor:pointer;" onclick="sysAdmin_10501_detailFun('+index+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
				text:'退货导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10501_exportFun();
				}
			}]		
		});
	});
	
	//审核退货
	function sysAdmin_10501_editFun(index){
		var rows = $('#sysAdmin_10501_datagrid').datagrid('getRows');
		var row  = rows[index];
		$('<div id="sysAdmin_10501_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">审核采购单</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10503.jsp?poid='+row.POID+'&orderMethod='+row.ORDERMETHOD,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10503_queryForm').form('load', row);
		    	if(row.ORDERMETHOD==1){
		    		$('#orderMethod_10503').val("厂商单")
		    	}else if(row.ORDERMETHOD==2){
		    		$('#orderMethod_10503').val("代理单")
		    	}else if(row.ORDERMETHOD==3){
		    		$('#orderMethod_10503').val("代理商单-采购")
		    	}
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10501_queryDialog').dialog('destroy');
					$('#sysAdmin_10501_datagrid').datagrid('reload');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10501_datagrid').datagrid('reload');
			}
		});
	}
	//查看
	function sysAdmin_10501_detailFun(index){
		var rows = $('#sysAdmin_10501_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10501_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退货信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10502.jsp?pdid='+row.PDID+'&show='+row.DETAILAPPROVESTATUS,
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10502_addForm').form('load', row);
		    	if(row.ORDERMETHOD==1){
		    		$('#orderMethod_10502').val("厂商单")
		    	}else if(row.ORDERMETHOD==2){
		    		$('#orderMethod_10502').val("代理单")
		    	}else if(row.ORDERMETHOD==3){
		    		$('#orderMethod_10502').val("代理商单-采购")
		    	}
		    	
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10501_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//退货导出
	function sysAdmin_10501_exportFun(){
		$('#sysAdmin_10501_searchForm').form('submit', { 
			url:'${ctx}/sysAdmin/purchaseDetail_exportReturnOrderAndDetail.action',
			success:function(msg){ 
			var res = $.parseJSON(msg);
				if(res.success) {
					$.messager.show({
						title : '提示',
						msg : res.msg
					});
				}
			}
		});
	}
	//表单查询
	function sysAdmin_10501_searchFun() {
		var start = $("#10501_cdate").datebox('getValue');
    	var end = $("#10501_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10501_datagrid').datagrid('load', serializeObject($('#sysAdmin_10501_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10501_cleanFun() {
		$('#sysAdmin_10501_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height: 80px; overflow: hidden; padding-top: 15px;">
		<form id="sysAdmin_10501_searchForm" style="padding-left: 10%" method="post">
			<table class="tableForm">
				<tr>
					<th>&nbsp;&nbsp;&nbsp;订单号</th>
					<td><input name="orderID" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;供应商名称</th>
					<td><input name="vendorName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;采购机构名称</th>
					<td><input name="purchaserName" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;类型</th>
					<td><select name="orderMethod" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 70px;">
							<option value="" selected="selected">查询所有</option>
							<option value="1">厂商单</option>
							<option value="2">代理单</option>
							<option value="3">代理商单-采购</option>
					</select></td>
					
					<th>&nbsp;&nbsp;&nbsp;状态</th>
					<td><select name="detailStatus" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 70px;">
							<option value="" selected="selected">查询所有</option>
							<option value="6">待审核</option>
							<option value="7">已审核</option>
							<option value="10">已退回</option>
					</select></td>
					
				</tr>
				<tr>
				
					
					<th>&nbsp;创建时间&nbsp;</th>
					<td><input name="detailCdate" id="10501_cdate" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/></td>
					<th>&nbsp;至&nbsp;</th>
					<td><input name="detailCdateEnd" id="10501_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/>
					</td>
					
					<td colspan="4" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_10501_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_10501_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10501_datagrid" style="overflow: hidden;"></table>
	</div>
</div>