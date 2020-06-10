<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 来款记录表(销售端-来款匹配) -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10507_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseRecord_listPurchaseRecord.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'PRID',
			columns : [[{
				title : '来款ID',
				field : 'PRID',
				width : 100
			},{
				title : '订单号',
				field : 'MATCHORDERID',
				width : 120
			},{
				title : '来款金额',
				field : 'ARRAIVEAMT',
				width : 80
			},{
				title : '已匹配金额',
				field : 'RECORDAMT',
				width : 80
			},{
				title :'匹配金额',
				field :'MATCHAMT',
				width : 120,
			},{
				title : '来款人姓名',
				field : 'ARRAIVERNAME',
				width : 80
			},{
				title : '来款卡号',
				field : 'ARRAIVECARD',
				width : 100
			},{
				title : '来款日期',
				field : 'ARRAIVEDATE',
				width : 100
			},{
				title :'来款状态',
				field :'MATCHAPPROVESTATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value=='A'){
						return '销售匹配';
					}else if(value=='Y'){
						return '财务确认';
					}else if(value=='K'){
						return '退回';
					}else{
						return '财务新增';
					}
				}
			},{
				title :'来款方式',
				field :'ARRAIVEWAY',
				align : 'right',
				width : 100
			},{
				title :'代理机构号',
				field :'UNNO',
				align : 'right',
				width : 100
			},{
				title :'代理机构名称',
				field :'PURCHASERNAME',
				align : 'right',
				width : 100
			},{
				title :'登记时间',
				field :'RECORDCDATE',
				align : 'right',
				width : 100
			},{
				title :'登记人',
				field :'RECORDCBY',
				width : 100
			},{
				title :'匹配时间',
				field :'RECORDLMDATE',
				width : 120,
			},{
				title :'匹配人',
				field :'RECORDLMBY',
				width : 130
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.ARRAIVEAMT>row.RECORDAMT){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10507_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="匹配来款" style="cursor:pointer;" onclick="sysAdmin_10507_editFun('+index+')"/>&nbsp;&nbsp';
					}else if(row.ARRAIVEAMT<0&&row.ARRAIVEAMT<row.RECORDAMT){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10507_detailFun('+index+')"/>&nbsp;&nbsp'+
						'<img src="${ctx}/images/frame_pencil.png" title="匹配来款" style="cursor:pointer;" onclick="sysAdmin_10507_editFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10507_detailFun('+index+')"/>';
					}
				}
			}]], 
			toolbar:[{
				text:'来款导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10507_exportFun();
				}
			}]
		});
	});
	//导出Excel
	function sysAdmin_10507_exportFun() {
		$('#sysAdmin_10507_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/purchaseRecord_exportPurchaseRecord.action',
		});
	}
	//查看
	function sysAdmin_10507_detailFun(index){
		var rows = $('#sysAdmin_10507_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10507_searchDialog"/>').dialog({
			title: '<span style="color:#157FCC;">来款信息</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10512.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10512_addForm').form('load', row);
		    	if(row.ARRAIVESTATUS == 1){
		    		$('#arraiveStatus_10512').val('财务新增');
				}else if(row.ARRAIVESTATUS == 2){
					$('#arraiveStatus_10512').val('销售匹配');
				}else if(row.ARRAIVESTATUS == 3){
					$('#arraiveStatus_10512').val('财务确认');
				}else if(row.ARRAIVESTATUS == 4){
					$('#arraiveStatus_10512').val('退回');
				}
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10507_searchDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//匹配来款
	function sysAdmin_10507_editFun(index){
		var rows = $('#sysAdmin_10507_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10507_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">匹配来款</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10510.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#prid_10510').val(row.PRID)
			},
			buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		  var rows = $('#sysAdmin_10510_datagrid').datagrid('getSelections');
		    	      if(rows.length != 1){
		    	        $.messager.alert('提示','请选择订单!','error');
		    	       return false;
		    	      }else{
		    	    	  var poid =rows[0].poid;
				    	  $('#poid_10510').val(poid);
		    	      }
		    	      //判断匹配金额是否大于待匹配金额
		    	      var amt = $("#matchAmt_10510").val();
		    	      if(Math.abs(row.ARRAIVEAMT)<Math.abs(parseFloat(row.RECORDAMT))+Math.abs(parseFloat(amt))){
		    	    	  $.messager.alert('提示','匹配金额大于待匹配金额，请核实后再重新匹配');
		    	    	  return;
		    	      }
		    		$('#sysAdmin_10510_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/purchaseRecord_updatePurchaseRecord.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10507_datagrid').datagrid('reload');
					    			$('#sysAdmin_10507_addDialog').dialog('destroy');
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
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10507_addDialog').dialog('destroy');
					$('#sysAdmin_10507_datagrid').datagrid('reload');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10507_datagrid').datagrid('reload');
			}
		});
	}
	//表单查询
	function sysAdmin_10507_searchFun() {
		var start = $("#10507_cdate").datebox('getValue');
    	var end = $("#10507_cdateEnd").datebox('getValue');
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
		$('#sysAdmin_10507_datagrid').datagrid('load', serializeObject($('#sysAdmin_10507_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10507_cleanFun() {
		$('#sysAdmin_10507_searchForm input').val('');
	}
	$(function(){
		$('#arraiveWay_10507').combogrid({
			url : '${ctx}/sysAdmin/purchaseRecord_listArraiveWay.action',
			idField:'ARRAIVEWAY',
			textField:'ARRAIVEWAY',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'ARRAIVEWAY',title:'来款方式',width:150},
			]]
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height: 80px; overflow: hidden; padding-top: 15px;">
		<form id="sysAdmin_10507_searchForm" style="padding-left: 10%" method="post">
			<table class="tableForm">
				<tr>
					<th>&nbsp;&nbsp;&nbsp;订单号</th>
					<td><input name="orderID" style="width: 138px;" /></td>
										
					<th>&nbsp;&nbsp;&nbsp;卡号</th>
					<td><input name="arraiveCard" style="width: 138px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;来款方式</th>
					<td><select id="arraiveWay_10507" name="ARRAIVEWAY"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 155px;"></select></td>
					
					<th>&nbsp;&nbsp;&nbsp;状态</th>
					<td><select name="arraiveStatus" class="easyui-combobox"
						data-options="panelHeight:'auto',editable:false"
						style="width: 70px;">
							<option value="" selected="selected">查询所有</option>
							<option value="1">财务新增</option>
							<option value="2">销售匹配</option>
							<option value="3">财务确认</option>
							<option value="4">退回</option>
					</select></td>
					
				</tr>
				<tr>
				
					<th>&nbsp;&nbsp;&nbsp;来款人姓名</th>
					<td><input name="arraiverName" style="width: 138px;" /></td>
					
					<th>&nbsp;来款日期&nbsp;</th>
					<td><input name="arraiveDate" id="10507_cdate" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/></td>
					<th>&nbsp;至&nbsp;</th>
					<td><input name="arraiveDateEnd" id="10507_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 142px;"/>
					</td>
					
					<td colspan="4" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="sysAdmin_10507_searchFun();">查询</a> &nbsp; <a
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:true"
						onclick="sysAdmin_10507_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10507_datagrid" style="overflow: hidden;"></table>
	</div>
</div>