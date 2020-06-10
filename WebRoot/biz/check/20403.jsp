<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function () {
		$('#sysAdmin_20403_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRefund_queryAdmTxnInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ,50],
			checkOnSelect:true,
			sortName: 'refid',
			sortOrder: 'desc',
			idField : 'refid',
			columns : [[{
				title : '唯一主键',
				field : 'refid',
				width : 100,
				checkbox:true
			},{
				title : '原交易id',
				field : 'pkId',
				width : 100,
				hidden:true
			},{
				title :'商户编号',
				field :'mid',
				width : 120
			},{
				title :'交易参考号',
				field :'rrn',
				width : 100
			},{
				title :'交易卡号',
				field :'cardPan',
				width : 150
			},{
				title :'交易时间',
				field :'txnDay',
				width : 70
			},{
				title :'原始交易金额',
				field :'samt',
				width : 120
			},{
				title :'差错金额',
				field :'ramt',
				width : 80
			},{
				title :'提交时间',
				field :'cdate',
				width : 150
			},{
				title :'审核状态',
				field :'status',
				width : 80,
				formatter : function(value,row,index) {
						    return "待提交";
				}
			},{
				title :'审核日期',
				field :'approvedate',
				width : 100
			},{
				title :'备注',
				field :'remarks',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 80,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/start.png" title="提交" style="cursor:pointer;" onclick="sysAdmin_refundconfirmC_editFun('+index+')"/>&nbsp;&nbsp';
				}
			}]]
			//,
			//toolbar:[{
			//	id:'btn_run',
			//	text:'差错文件导入',
			//	iconCls:'icon-query-export',
			//	handler:function(){
			//		sysAdmin_erfund20403_importFun();
			//	}
			//},{
			//	id:'btn_run',
			//	text:'批量提交',
			//	iconCls:'l-btn-text icon-start',
			//	handler:function(){
			//		sysAdmin_erfund20403_submitFun();
			//	}
			//}]
		});
	});
	
	//提交
	function sysAdmin_refundconfirmC_editFun(index) {

		//获取操作对象
		var rows = $('#sysAdmin_20403_datagrid').datagrid('getRows');
		var row = rows[index];
		
		$('<div id="sysAdmin_20403_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">申请退款信息</span>',
			width: 800,
		    height:310,
		    closed: false,
		    href: '${ctx}/biz/check/20404.jsp?refid='+row.refid+'&pkId='+row.pkId,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20403_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.refid);    
		    	$('#sysAdmin_20404_editForm').form('load', row);
			},
			buttons:[{
				text:'提交退款',
				iconCls:'icon-ok',
				handler:function() {
					//var inputs = document.getElementById("sysAdmin_10645_editForm").getElementsByTagName("input");
					//
		    		//for(var i=0;i<inputs.length;i++){
		    		//	inputs[i].value = $.trim(inputs[i].value);
		    		//}
					$('#sysAdmin_20404_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_saveRefundInfoInfoC.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20403_datagrid').datagrid('unselectAll');
				    				sysAdmin_20403_cleanFun10();
				    				//sysAdmin_20403_searchFun10();
				    				//$('#sysAdmin_20403_datagrid').datagrid('reload');
				    				$('#sysAdmin_20403_datagrid').datagrid('loadData', { total: 0, rows: [] });
					    			$('#sysAdmin_20403_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20403_editDialog').dialog('destroy');
					    			$('#sysAdmin_20403_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_20403_editDialog').dialog('destroy');
					$('#sysAdmin_20403_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20403_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//批量提交
	function sysAdmin_erfund20403_submitFun(){
		var checkedItems = $('#sysAdmin_20403_datagrid').datagrid('getChecked');
		var names = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.stutas == 'K'||item.stutas == 'Y'){
				result = false;
			}
			names.push(item.refid);
		});
		if(!result){
			$.messager.alert('提示','已有审批过的退款信息！');
			return;
		}
		var refids=names.join(",");
		if(refids==null||refids==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		$('<div id="sysAdmin_merchantjoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">确认提交</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    //href: '${ctx}/biz/bill/merchant/10421.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					//var validator = $('#sysAdmin_merchantjoin_editForm').form('validate');
		    		//if(validator){
		    		//	$.messager.progress();
		    		//}
					$('#sysAdmin_merchantjoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkErFund_updateErfundInfo.action?refids='+refids,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20403_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20403_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    			$('#sysAdmin_20403_datagrid').datagrid('reload');
					    			$('#sysAdmin_20403_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//差错文件导入
	function sysAdmin_erfund20403_importFun(){
		$('<div id="sysAdmin_2041_importFun"/>').dialog({
			title: '<span style="color:#157FCC;align:center">差错文件导入</span>',
			width: 600,
		    height:160,
		    closed: false,
		    href: '${ctx}/biz/check/20402.jsp',
		    modal: true,
		    onLoad:function() {
			}
			//,
			//buttons:[{
			//	text:'导入',
			//	iconCls:'icon-ok',
			//	handler:function() {
			//		$('#sysAdmin_20402_editForm').form('submit', {
			//			url:'${ctx}/sysAdmin/merchantAuthenticity_editTxnAuthenticity.action?flag=go',
			//			success:function(data) {
			//				var result = $.parseJSON(data);
			//				if (result.sessionExpire) {
			//    				window.location.href = getProjectLocation();
			//	    		} else {
			//	    			if (result.success) {
			//	    				$('#sysAdmin_2041_datagrid').datagrid('reload');
			//		    			$('#sysAdmin_2041_importFun').dialog('destroy');
			//		    			$.messager.show({
			//							title : '提示',
			//							msg : result.msg
			//						});
			//	    		} else {
			//		    			$('#sysAdmin_2041_importFun').dialog('destroy');
			//		    			$('#sysAdmin_2041_datagrid').datagrid('unselectAll');
			//		    			$.messager.alert('提示', result.msg);
			//			    	}
			//		    	}
			//			}
			//		});
			//	}
			//},{
			//	text:'取消',
			//	iconCls:'icon-cancel',
			//	handler:function() {
			//		$('#sysAdmin_2041_importFun').dialog('destroy');
			//		$('#sysAdmin_2041_datagrid').datagrid('unselectAll');
			//	}
			//}]
			,
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_2041_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	$.extend($.fn.validatebox.defaults.rules, {
	intOrFloat : {// 验证整数或小数   
       	validator : function(value) {
       	    return /^\d+(\.\d+)?$/i.test(value);   
       	},   
       	message : '请输入正确格式'   
   	}
   	});
	
    //表单查询
	function sysAdmin_20403_searchFun10() {
		$('#sysAdmin_20403_datagrid').datagrid('load', serializeObject($('#sysAdmin_20403_searchForm'))); 
		//获取操作对象
		var rows = $('#sysAdmin_20403_datagrid').datagrid('getRows');
		//var row = rows[0];
		if(rows==null||rows==''){
			$.messager.show({
				title : '提示',
				msg : '未匹配到原始交易请提交手工退款'
			});
		}
	}

	//清除表单内容
	function sysAdmin_20403_cleanFun10() {
		$('#sysAdmin_20403_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:30px;">
		<form id="sysAdmin_20403_searchForm" style="padding-left:4%">
			<input type="hidden" id="refids" name="refids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号:</th>
					<td><input name="mid" style="width: 100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/></td>
					<td width="15px"> </td>
					<th>交易参考号:</th>
					<td><input name="rrn" style="width: 100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/></td>
					<td width="15px"> </td>
					<th>交易卡号:</th>
					<td><input name="cardPan" style="width: 100px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/></td>
					<td width="15px"> </td>
					<th>交易日期:</th>
					<td><input name="txnDay" class="easyui-datebox" class="easyui-validatebox" style="width: 100px;" data-options="required:true"/></td>
					<td width="15px"> </td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20403_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20403_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_20403_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>




