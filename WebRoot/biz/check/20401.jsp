<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,java.sql.*,com.hrt.frame.entity.pagebean.UserBean"
    pageEncoding="UTF-8"%>
<%
	Object userSession = request.getSession().getAttribute("user");
  	UserBean user = new UserBean();
  	if(userSession!=null){
	  user = ((UserBean) userSession);
  	}
 %>
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20401_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRefund_queryRefundInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ,50],
			checkOnSelect:true,
			sortName: 'refId',
			sortOrder: 'desc',
			idField : 'refId',
			columns : [[{
				title : '唯一主键',
				field : 'refId',
				width : 100,
				checkbox:true
			},{
				title :'商户编号',
				field :'mid',
				width : 120
			},{
				title :'参考号/聚合退款单号',
				field :'rrn',
				width : 100
			},{
				title :'原订单号',
				field :'oriOrderId',
				width : 150
			},{
				title :'交易卡号',
				field :'cardPan',
				width : 150
			},{
				title :'交易日期',
				field :'txnDayStr',
				width : 70
			},{
				title :'原交易金额',
				field :'samt',
				width : 70
			},{
				title :'退款金额',
				field :'ramt',
				width : 60
			},{
				title :'结算方式',
				field :'settlement',
				width : 60,
				formatter : function(value,row,index) {
						if (value==1){
						   return "汇款";
						}else if(value==2){
							return "抵扣结算";
						}
					}
			},{
				title :'备注',
				field :'remarks',
				width : 80
			},{
				title :'提交时间',
				field :'maintainDate',
				width : 70
			},{
				title :'审核状态',
				field :'status',
				width : 60,
				formatter : function(value,row,index) {
						if (value=='W'){
						   return "待提交";
						}else if(value=='C'){
							return "审核中";
						}else if(value=='Y'){
							   return "审核成功";
						}else if(value=='F'){
							return "审核失败";
						}else if(value=='K'){
							   return "退回";
						}
					}
			},{
				title :'审核日期',
				field :'approveDate',
				width : 70
			},{
				title :'操作',
				field :'operation',
				width : 50,
				align : 'center',
				formatter : function(value,row,index) {
					var lvl =<%=user.getUnitLvl() %>;
					if(row.pkId=="2"&&row.status=='C'){
						return '<img src="${ctx}/images/start.png" title="审批" style="cursor:pointer;" onclick="sysAdmin_20401_editFun2('+index+')"/>';
					}else if(row.pkId=="2"&&row.status=='F'){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_20401_viewFun('+index+')"/>';
					}else if((row.status=='W'||row.status=='C')&&row.oriOrderId!=null&&lvl<1){
						return '<img src="${ctx}/images/start.png" title="审批" style="cursor:pointer;" onclick="sysAdmin_20401_editFun('+index+')"/>';
					}else if(row.oriOrderId!=null&&(row.status=='Y'||row.status=='K')){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_20401_viewFun('+index+')"/>';
					}
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo_export20401();
				}
			}
			/**,{
				id:'btn_run',
				text:'聚合退款批量审核',
				iconCls:'icon-start',
				handler:function(){
					sysAdmin_erfund20401_submitFun();
				}
			}**/]
		});
	});
	
	function sysAdmin_merchantterminalinfo_export20401() {
		var txnDay = $('#txnDay20401').datebox('getValue');
    	var txnDay1= $('#txnDay20401_1').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_20401_searchForm').form('submit',{
			    url:'${ctx}/sysAdmin/checkRefund_exportRefundInfo.action'
			});
	    }
	}
	//退款审核
	function sysAdmin_20401_editFun(index) {
		//获取操作对象
		var rows = $('#sysAdmin_20401_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_20401_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">退款审核</span>',
			width: 800,
		    height:550,
		    closed: false,
		    href: '${ctx}/biz/check/20405.jsp?refids='+row.refId,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20401_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.refId);    
		    	$('#sysAdmin_20405_editForm').form('load', row);
			},
			buttons:[{
				text:'通过',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_20405_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_updateErfundInfo.action?status=Y&refid='+row.refId,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20401_datagrid').datagrid('reload');
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'退回',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_20405_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_updateErfundInfo.action?status=K&refid='+row.refId,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20401_datagrid').datagrid('reload');
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_20401_editDialog').dialog('destroy');
					$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
			}
		});
	}
	//车辆代缴退款审核
	function sysAdmin_20401_editFun2(index) {
		//获取操作对象
		var rows = $('#sysAdmin_20401_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_20401_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">退款审核</span>',
			width: 800,
		    height:550,
		    closed: false,
		    href: '${ctx}/biz/check/20405.jsp?refids='+row.refId,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20401_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.refId);    
		    	$('#sysAdmin_20405_editForm').form('load', row);
			},
			buttons:[{
				text:'通过',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_20405_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_updateErfundInfo2.action?status=Y&refid='+row.refId,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20401_datagrid').datagrid('reload');
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'退回',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_20405_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_updateErfundInfo2.action?status=K&refid='+row.refId,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20401_datagrid').datagrid('reload');
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20401_editDialog').dialog('destroy');
					    			$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_20401_editDialog').dialog('destroy');
					$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
			}
		});
	}
	//退款
	function sysAdmin_20401_viewFun(index) {
		//获取操作对象
		var rows = $('#sysAdmin_20401_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_20401_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">退款审核</span>',
			width: 800,
		    height:550,
		    closed: false,
		    href: '${ctx}/biz/check/20405.jsp?refids='+row.refId,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20401_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.refId);    
		    	$('#sysAdmin_20405_editForm').form('load', row);
			},
			buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_20401_editDialog').dialog('destroy');
					$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//批量提交
	function sysAdmin_erfund20401_submitFun(){
		var checkedItems = $('#sysAdmin_20401_datagrid').datagrid('getChecked');
		var names = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.stutas == 'K'||item.stutas == 'Y'){
				result = false;
			}
			names.push(item.refId);
		});
		if(!result){
			$.messager.alert('提示','已有审批过的退款信息！');
			return;
		}
		var refIds=names.join(",");
		alert(refIds);
		if(refIds==null||refIds==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		$('<div id="sysAdmin_merchantjoin_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">批量审核</span>',
			width: 300,   
		    height: 150, 
		    closed: false,
		    //href: '${ctx}/biz/bill/merchant/10421.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantjoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_updateErfundInfo.action?status=Y&refIds='+refIds,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			//if (result.success) {
				    				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20401_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    		//	$.messager.show({
								//		title : '提示',
								//		msg : result.msg
								//	});
					    		//} else {
					    		//	$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
					    		//	$('#sysAdmin_20401_datagrid').datagrid('reload');
					    		//	$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
					    		//	$.messager.alert('提示', result.msg);
						    	//}
					    	}
						}
					});
				}
			},{
				text:'退回',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantjoin_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkRefund_updateErfundInfo.action?status=K&refIds='+refIds,
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
			    				$('#sysAdmin_20401_datagrid').datagrid('unselectAll');
			    				$('#sysAdmin_20401_datagrid').datagrid('reload');
				    			$('#sysAdmin_merchantjoin_editDialog').dialog('destroy');
				    			$.messager.show({
									title : '提示',
									msg : result.msg
								});
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
	
    //表单查询
	function sysAdmin_20401_searchFun10() {
		$('#sysAdmin_20401_datagrid').datagrid('load', serializeObject($('#sysAdmin_20401_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_20401_cleanFun10() {
		$('#sysAdmin_20401_searchForm input').val('');
		$('#sysAdmin_20401_searchForm select').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_20401_searchForm" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
					<th>商户编号:</th>
					<td><input name="mid" style="width: 100px;" /></td>
					<td width="10px"> </td>
					<th>交易参考号:</th>
					<td><input name="rrn" style="width: 100px;" /></td>
					<td width="10px"> </td>
					<th>交易卡号&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; <input name="cardPan" style="width: 150px;" /></td>
					<td width="10px"> </td>
					<th>交易时间:</th>
					<td><input name="txnDay" id="txnDay20401" class="easyui-datebox" style="width: 105px;" /> - <input name="txnDay1" id="txnDay20401_1" class="easyui-datebox" style="width: 105px;" /></td>
				</tr>
				<tr>
					<th>审核状态:</th>
					<td><select  name="status" style="width: 105px;" >
							<option selected="selected" value="">ALL</option>
							<option value="C">审核中</option>
							<option value="Y">审核成功</option>
							<option value="F">审核失败</option>
						</select>
					</td>
					<td width="10px"> </td>
					<th>退款类型:</th>
					<td><select  name="isM35" style="width: 105px;" >
							<option value="6" selected="selected">聚合</option>
							<option value="1">线下</option>
							<option value="2">车辆违章</option>
						</select>
					</td>
					<td width="10px"> </td>
					<th>原交易订单号: <input name="oriOrderId" style="width: 150px;" /></th>
					<td width="10px"> </td>
					<th>提交时间:</th>
					<td><input name="approveDate" class="easyui-datebox" style="width: 105px;" /> - <input name="approveDate1"  class="easyui-datebox" style="width: 105px;" /></td>
					
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20401_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20401_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_20401_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>




