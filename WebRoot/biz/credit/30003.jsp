<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_30003_datagrid').datagrid({
			url :"${ctx}/sysAdmin/loanRepay_queryRepayInfo.action",
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'contractPeriod',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				field : 'PROTOCOLNO',
				title : '协议编号',
				width : 120
				//hidden : true ,
				//checkbox:false
			},{
				title : '还款单据号',
				field : 'POSINVOICENO',
				width : 100
			},{
				title : '还款日期',
				field : 'REPAYTIME',
				width : 100
			},{
				title :'还款金额',
				field :'REPAYAMT',
				width : 100
			},{
				title :'开户银行账号',
				field :'PAYNO',
				width : 100
			},{
				title :'申请人',
				field :'PROPOSER',
				width : 100
			},{
				title :'申请人邮箱',
				field :'PROPEMAIL',
				width : 100
			},{
				title :'申请人电话',
				field :'PROPHONE',
				width : 100
			},{
				title :'审批标志',
				field :'APPROVEFLAG',
				width : 100,
				formatter:function(value,row,index){
					// 0:待审批1：已审批2：已拒绝
					if(value=='0'){
						return "待审批";
					}else if(value=='1'){
						return "已审批";
					}else if(value=='2'){
						return "已拒绝";
					}
				}
			},{
				title :'审批时间',
				field :'APPROVEDTIME',
				width : 100
			},{
				title :'拒绝原因',
				field :'REFUSALREASON',
				width : 100
			},{
				title :'备注',
				field :'REMARKS',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 80,
				align : 'center',
				formatter : function(value,row,index) {
					//var rowData = $('#sysAdmin_30003_datagrid').datagrid('getData').rows[index];
					//if(rowData.APPROVEFLAG==1&&rowData.LOANFLAG==1&&rowData.SURPLUSAMT>0){
					//	return '<img src="${ctx}/images/start.png" title="还款" style="cursor:pointer;" onclick="sysAdmin_30003_edit('+index+')"/>&nbsp;&nbsp';
					//}
				}
			}]]/**, 
			toolbar:[{
				id:'btn_add',
				text:'申请贷款',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_30003_add();
				}
			}]**/
		});
	});

	//申请贷款
	function sysAdmin_30003_add(){
		//$.messager.confirm('继续申请','尊敬的用户：',function(result) {
		//	if (result) {
				$('<div id="sysAdmin_add30003_datagrid"/>').dialog({
					title: '<span style="color:#157FCC;">贷款申请</span>', 
					width: 1000,
				    height: 330,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/credit/30001.jsp',  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    		$.messager.confirm('提示','请确认信息是否无误，提交申请后将不能调整！',function(re) {
			    			if (re) {
					    		var validator = $('#credit30001').form('validate');
					    		if(validator){
					    			$.messager.progress();
					    		}
					    		$('#credit30001').form('submit',{
						    		url:'${ctx}/sysAdmin/loan_addLoanApply.action',
					    			success:function(data) {
					    				$.messager.progress('close'); 
					    				alert(result.status);
					    				var result = $.parseJSON(data);
						    			if (result.sessionExpire) {
						    				window.location.href = getProjectLocation();
							    		} else {
							    			if (result.status=='0') {
							    				$('#sysAdmin_30003_datagrid').datagrid('reload');
								    			$('#sysAdmin_add30003_datagrid').dialog('destroy');
								    			$.messager.show({
													title : '提示',
													msg : '提交成功'
												});
								    		} else {
								    			$.messager.alert('提示', '提交失败');
								    		}
								    	}
						    		 }
						    	   });
			    				}
			    			});
				 	   	}	    
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function() {
						$('#sysAdmin_add30003_datagrid').dialog('destroy');
						}  
					}], 
					onClose:function() {
						$(this).dialog('destroy');
					}
					});
			 //}
		//});
	}
	
	//还款
	function sysAdmin_30003_edit(index){
		//获取操作对象
		var rows = $('#sysAdmin_30003_datagrid').datagrid('getRows');
		var rowData = rows[index];
		$('<div id="sysAdmin_edit30003_edit"/>').dialog({
			title: '<span style="color:#157FCC;">申请还款</span>', 
			width: 1000,
		    height: 500,  
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/credit/30002.jsp?protocolNo='+rowData.PROTOCOLNO,  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$.messager.confirm('提示','请确认信息是否无误，提交申请后将不能调整！',function(re) {
	    			if (re) {
			    		var validator = $('#credit30002').form('validate');
			    		if(validator){
			    			$.messager.progress();
			    		}
			    		$('#credit30002').form('submit',{
				    		url:'${ctx}/sysAdmin/loanRepay_LoanRepay.action',
			    			success:function(data) {
			    				$.messager.progress('close'); 
			    				alert(result.status);
			    				var result = $.parseJSON(data);
				    			if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.status=='0') {
					    				$('#sysAdmin_30003_datagrid').datagrid('reload');
						    			$('#sysAdmin_edit30003_edit').dialog('destroy');
						    			$.messager.show({
											title : '提示',
											msg : '提交成功'
										});
						    		} else {
						    			$.messager.alert('提示', '提交失败');
						    		}
						    	}
				    		 }
				    	   });
	    				}
	    			});
		 	   	}	    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_edit30003_edit').dialog('destroy');
				}  
			}], 
			onClose:function() {
				$(this).dialog('destroy');
			}
			});
	}
	function bill_agentunitdata30003_searchFun() {
		$('#sysAdmin_30003_datagrid').datagrid('load', serializeObject($('#sysAdmin_30003_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata30003_cleanFun() {
		$('#sysAdmin_30003_searchForm input').val('');
	}
	
</script> 

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:75px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_30003_searchForm" style="padding-left:5%;padding-top:10px;" method="post">
			<table class="tableForm" >
					  <tr>
						<th>协议编号:</th>
						<td><input id="protocolNo" name="protocolNo" style="width: 120px;" /></td>
						<td width="10px"></td>
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="bill_agentunitdata30003_searchFun();">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="bill_agentunitdata30003_cleanFun();">清空</a>	
						</td>
					</tr> 
				</table>
			</form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_30003_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
