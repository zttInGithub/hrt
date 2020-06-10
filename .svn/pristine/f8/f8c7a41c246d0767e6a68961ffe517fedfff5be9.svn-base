<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	$(function() {
		//查询额度
		$.ajax({ 
			url:"sysAdmin/creditAgent_queryAvailableLimit.action", 
			dataType:"json",
			type:"post",
			error:function(err){
				alert("错误");
			},
			success:function(data){
				//alert(data.success+";  "+data.obj.status+";  "+data.obj.msg+";  "+data.obj.data[0].BASELIMIT+";  "+data.obj.data.length);
				if (data.sessionExpire) {
    				window.location.href = getProjectLocation();
	    		} else {
	    			if (data.success) {
	    				$("#BASELIMIT").html(data.obj.data[0].BASELIMIT);
	    				$("#USEDLIMIT").html(data.obj.data[0].USEDLIMIT);
	    				$("#FREEZELIMIT").html(data.obj.data[0].FREEZELIMIT);
	    				$("#USEDLIMIT").html(data.obj.data[0].USEDLIMIT);
	    				$("#CURRENTINTERESTSUM").html(data.obj.data[0].CURRENTINTERESTSUM);
	    				$("#SURPLUSAMTSUM").html(data.obj.data[0].SURPLUSAMTSUM);
	    				$("#LESSINTEREST").html(data.obj.data[0].LESSINTEREST);
		    			for(var i=0;i<data.obj.data[0].rateList.length;i++){
			    			if(data.obj.data[0].rateList[i].RATETYPE==0){//现金
			    				$("#RATE0").html(data.obj.data[0].rateList[i].RATE*10000);
					    	}else if (data.obj.data[0].rateList[i].RATETYPE==1){//pos机
			    				$("#RATE1").html(data.obj.data[0].rateList[i].RATE*10000);
							}
			    		}
		    		} else {
		    			//$.messager.alert('提示','额度查询失败');
		    			$.messager.show({
							title : '提示',
							msg : '额度查询失败'
						});
			    	}
		    	}	
			}
		});
	});

	//初始化datagrid
	$(function() {
		$('#sysAdmin_30000_datagrid').datagrid({
			url :"${ctx}/sysAdmin/creditAgent_queryCreditInfoData.action",
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
				width : 130
				//hidden : true ,
				//checkbox:false
			},{
				field : 'AGENTNAME',
				title : '代理商名称',
				width : 150,
				hidden : true 
			},{
				title : '贷款方式',
				field : 'CRETYPE',
				width : 80,
				formatter:function(value,row,index){
					// 0：现金 1：购买pos机
					if(value=='0'){
						return "现金";
					}else{
						return "设备";
					}
				}
			},{
				title : '贷款金额',
				field : 'CREAMT',
				width : 100
			},{
				title : '未还金额',
				field : 'SURPLUSAMT',
				width : 100
			},{
				title :'贷款利率',
				field :'CRERATE',
				width : 100,
				hidden : true 
			},{
				title :'贷款日期',
				field :'CREDATE',
				width : 100,
				hidden : true 
			},{
				title :'贷款期限',
				field :'CRETIMELIMIT',
				width : 80
			},{
				title :'下一还款日',
				field :'NEXTREPAYDAY',
				width : 100,
				hidden : true 
			},{
				title :'到期日期',
				field :'REPAYTIME',
				width : 100
			},{
				title :'审批标志',
				field :'APPROVEFLAG',
				width : 80,
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
				width : 100,
				hidden : true 
			},{
				title :'申请人',
				field :'PROPOSER',
				width : 100,
				hidden : true 
			},{
				title :'是否放款',
				field :'LOANFLAG',
				width : 80,
				formatter:function(value,row,index){
					//  0:未放款1：已放款
					if(value=='0'){
						return "未放款";
					}else if(value=='1'){
						return "已放款";
					}else if(value=='2'){
						return "已拒绝";
					}
				}	
			},{
				title :'利息合计',
				field :'TOTALACCRUAL',
				width : 80
			},{
				title :'pos机单号',
				field :'POSINVOICENO',
				width : 100,
				hidden : true 
			},{
				title :'汇款单号',
				field :'PAYNO',
				width : 100,
				hidden : true 
			},{
				title :'备注',
				field :'REMARKS',
				width : 150
			},{
				title :'协议是否超期',
				field :'ISVALID',
				width : 100,//"ISVALID": "0"//协议是否超期，"0"-有效  "1"-过期
				formatter:function(value,row,index){
					//  0:未放款1：已放款
					if(value=='0'){
						return "有效";
					}else if(value=='1'){
						return "过期";
					}
				}
			},{
				title :'是否逾期',
				field :'OVERDUEFLAG',
				width : 80,//"OVERDUEFLAG":1,//是否逾期   0：正常1：逾期
				formatter:function(value,row,index){
					//  0:未放款1：已放款
					if(value=='0'){
						return "正常";
					}else if(value=='1'){
						return "逾期";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 160,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_30000_datagrid').datagrid('getData').rows[index];
					var src='';
					src += '<img src="${ctx}/images/query_search.png" title="查看贷款" style="cursor:pointer;" onclick="sysAdmin_30000_view('+index+')"/>&nbsp;&nbsp';
					if(rowData.APPROVEFLAG==1&&rowData.ISVALID==0&&rowData.LOANFLAG!=2){
						src += '<img src="${ctx}/images/frame_reload.png" title="下载协议" style="cursor:pointer;" onclick="sysAdmin_30000_download('+index+')"/>&nbsp;&nbsp';
					}
					if(rowData.APPROVEFLAG==1&&rowData.LOANFLAG==1&&rowData.SURPLUSAMT>0){
						src += '<img src="${ctx}/images/start.png" title="还款" style="cursor:pointer;" onclick="sysAdmin_30000_edit('+index+')"/>&nbsp;&nbsp';
					}
					if(rowData.APPROVEFLAG==1&&rowData.LOANFLAG==1){
						src += '<img src="${ctx}/images/frame_pencil.png" title="还款明细" style="cursor:pointer;" onclick="sysAdmin_30000_loanRepay('+index+')"/>&nbsp;&nbsp';
						src += '<img src="${ctx}/images/query_search2.png" title="扣款明细" style="cursor:pointer;" onclick="sysAdmin_30000_DeAmtHist('+index+')"/>&nbsp;&nbsp';
					}
					return src;
				}
			}]], 
			toolbar:[{
				id:'btn_add',
				text:'申请贷款',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_30000_add();
				}
			}]
		});
	});

	//查看还款明细
	function sysAdmin_30000_loanRepay(index){
	 	var rows = $('#sysAdmin_30000_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_30000_loanRepay"/>').dialog({
			title: '查看还款明细',
			width: 1100,   
		    height: 350, 
		    closed: false,
		    href:'${ctx}/biz/credit/30005.jsp?PROTOCOLNO30005='+row.PROTOCOLNO,
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//查看扣款明细
	function sysAdmin_30000_DeAmtHist(index){
	 	var rows = $('#sysAdmin_30000_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_30000_DeAmtHist"/>').dialog({
			title: '查看扣款明细',
			width: 900,   
		    height: 350, 
		    closed: false,
		    href:'${ctx}/biz/credit/30006.jsp?PROTOCOLNO30006='+row.PROTOCOLNO,
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	//查看明细
	function sysAdmin_30000_view(index,bmid,mid){
		//获取操作对象
		var rows = $('#sysAdmin_30000_datagrid').datagrid('getRows');
		var rowData = rows[index];
		$('<div id="sysAdmin_30000_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看贷款明细</span>',
			width: 900,
		    height:560, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/credit/30004.jsp',
		    onLoad:function() {
		    	$('#sysAdmin_30000_queryForm').form('load', rowData);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_30000_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_30000_datagrid').datagrid('unselectAll');
			}
		});
	}

	//下载协议
	function sysAdmin_30000_download(index){
		var rows = $('#sysAdmin_30000_datagrid').datagrid('getRows');
		var rowData = rows[index];
		$('#creditword').form('submit', {    
	    	url:'${ctx}/sysAdmin/creditAgent_listCreditAgentExcel.action?PROTOCOLNO='+rowData.PROTOCOLNO   
		});  
	}
	
	//申请贷款
	function sysAdmin_30000_add(){
		//$.messager.confirm('继续申请','尊敬的用户：',function(result) {
		//	if (result) {
				$('<div id="sysAdmin_add30000_datagrid"/>').dialog({
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
					    				var result = $.parseJSON(data);
						    			if (result.sessionExpire) {
						    				window.location.href = getProjectLocation();
							    		} else {
							    			if (result.success==true) {
							    				$('#sysAdmin_30000_datagrid').datagrid('reload');
								    			$('#sysAdmin_add30000_datagrid').dialog('destroy');
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
			    			});
				 	   	}	    
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function() {
						$('#sysAdmin_add30000_datagrid').dialog('destroy');
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
	function sysAdmin_30000_edit(index){
		//获取操作对象
		var rows = $('#sysAdmin_30000_datagrid').datagrid('getRows');
		var rowData = rows[index];
		$('<div id="sysAdmin_edit30000_edit"/>').dialog({
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
			    				var result = $.parseJSON(data);
				    			if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success==true) {
					    				$('#sysAdmin_30000_datagrid').datagrid('reload');
						    			$('#sysAdmin_edit30000_edit').dialog('destroy');
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
	    			});
		 	   	}	    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
				$('#sysAdmin_edit30000_edit').dialog('destroy');
				}  
			}], 
			onClose:function() {
				$(this).dialog('destroy');
			}
			});
	}
	function bill_agentunitdata30000_searchFun() {
		$('#sysAdmin_30000_datagrid').datagrid('load', serializeObject($('#sysAdmin_30000_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata30000_cleanFun() {
		$('#sysAdmin_30000_searchForm input').val('');
	}
	
</script> 

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:75px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_30000_searchForm" style="padding-left:5%;padding-top:10px;" method="post">
			<input type="hidden" id="searchids" name="searchids" />
			<table class="tableForm" >
					<tr>
						<th>可用额度:</th>
						<td><font id="BASELIMIT" style="width: 120px;" ></font></td>
						<td width="10px"></td>
						<th>冻结额度:</th>
						<td><font id="FREEZELIMIT" style="width: 120px;" ></font></td>
						<td width="10px"></td>
						<th>已使用额度:</th>
						<td><font id="USEDLIMIT" style="width: 120px;" ></font></td>
						<td width="10px"></td>
						<th>未还贷总金额:</th>
						<td><font id="SURPLUSAMTSUM" style="width: 120px;" ></font></td>
						<td width="10px"></td>
						<!-- <th>未支付总利息:</th>
						<td><font id="CURRENTINTERESTSUM" style="width: 90px;" ></font></td> -->
						<th>应还利息:</th>
						<td><font id="LESSINTEREST" style="width: 90px;" ></font></td>
						<td width="10px"></td>
						<th>设备日利率:</th>
						<td><font id="RATE1" style="width: 90px;" ></font>‱</td>
						<td width="10px"></td>
						<th>现金日利率:</th>
						<td><font id="RATE0" style="width: 90px;" ></font>‱</td>
						<td width="10px"></td>
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="bill_agentunitdata30000_searchFun();">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="bill_agentunitdata30000_cleanFun();">清空</a>	
						</td>
					</tr>
				</table>
			</form>
			<form action="" method="post" id="creditword"></form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_30000_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
