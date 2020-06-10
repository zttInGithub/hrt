<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_riskTransCard_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkReceiptsOpreation_queryTransReceiptsAuditList.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			nowrap : true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'PKID',
			sortOrder: 'desc',
			idField : 'PKID',
			columns : [[{
				field : 'PKID',
				width : 100,
				checkbox:true
			},{
				title : '所属机构',
				field : 'UNNO',
				width : 80
			},{
				title :'商户类型',
				field :'ISM35',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(value==1){ 
						return '手刷商户';
					}else{
						return '传统商户';
					}
				} 
			},{
				title : '商户编号',
				field : 'MID',
				width : 150
			},{
				title : '商户名称',
				field : 'RNAME',
				width : 150
			},{
				title : '卡号',
				field : 'CARDPAN',
				width : 150,
				formatter:function(value,row,index){
					var aa=value+row.CARDPAN2;
					return aa.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
				} 
			},{
				title : '交易金额',
				field : 'TXNAMOUNT',
				width : 100
			},{
				title :'交易日期',
				field :'TXNDAY',
				width : 100,
				sortable :true
			},{
				title :'上传照片日期',
				field :'UPLOADDATE',
				width : 150,
				sortable :true
			},{
				title :'审核日期',
				field :'RISKDAY',
				width : 150,
				sortable :true
			},{
				title :'风控状态',
				field :'STATUS',
				width : 100,
				sortable :true,
				formatter:function(value,row,index){
					if(value==0){ 
						return '正常';
					}else{
						return '风险';
					}
				} 
			},{
				title :'照片是否上传',
				field :'IFUPLOAD',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==0){ 
						return '未上传';
					}else if(value==1){
						return '已上传';
					}else{
						return '退回'
						}
				} 
			},{
				title :'是否通过',
				field :'IFSETTLEFLAG',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==0){ 
						return '未通过';
					}else if(value==1){
						return '已通过';
					}
				} 
			},{
				title :'备注',
				field :'MINFO1',
				width : 100,
				sortable :true
			},{
				title :'操作',
				field :'operation',
				width : 200,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.IFSETTLEFLAG=='0'){
						return '<img src="${ctx}/images/query_search.png" title="预览风控交易卡片" style="cursor:pointer;" onclick="sysAdmin_riskreceiptsAudit_queryImage('+row.PKID+','+row.TXNDAY+','+row.CARDPAN+','+row.CARDPAN2+')"/>&nbsp;&nbsp'+
						   '<input type="button" value="审批通过" onclick="sysAdmin_riskselltle_editFun('+index+')"/>&nbsp;&nbsp'+
						   '<input type="button" value="审批不通过" onclick="sysAdmin_risknoUploadReceipts_editFun('+row.PKID+')"/>'; 
						}else{
							return '<img src="${ctx}/images/query_search.png" title="预览风控交易卡片" style="cursor:pointer;" onclick="sysAdmin_riskreceiptsAudit_queryImage('+row.PKID+','+row.TXNDAY+','+row.CARDPAN+','+row.CARDPAN2+')"/>'; 
							}
}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'导出调单风控不合规商户',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_exportRiskReceiptsNoPassData_exportFun();
				}
			},{
				id:'btn_run2',
				text:'导出调单风控审批通过商户',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_exportRiskReceiptsPassData_exportFun();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出不合规商户',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_riskmerchantterminalinfoSelected_exportFun();
				}
			}]	
		});
	});

	function sysAdmin_exportRiskReceiptsNoPassData_exportFun(){
		//导出不合格商户
			$('#sysAdmin_riskreceiptsAudit_searchForm').form('submit',{
	    		url:'${ctx}/sysAdmin/checkReceiptsOpreation_exportRiskAuditReceiptsNoMerchantInfo.action'
	    	});
		}

	function sysAdmin_exportRiskReceiptsPassData_exportFun(){
		//导出合格商户
			$('#sysAdmin_riskreceiptsAudit_searchForm').form('submit',{
	    		url:'${ctx}/sysAdmin/checkReceiptsOpreation_exportRiskAuditReceiptsMerchantInfo.action'
	    	});
		}

	function sysAdmin_riskmerchantterminalinfoSelected_exportFun() {
		//勾选导出
		var checkedItems = $('#sysAdmin_riskTransCard_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.PKID);
		});               
		var ids=names.join(",");
		if(ids==null||ids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#ids").val(ids);
		$('#sysAdmin_riskreceiptsAudit_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/checkReceiptsOpreation_exportRiskTransCardInfo.action'
			    	});
	}
	
	function sysAdmin_riskreceiptsAudit_queryImage(pkid,txnday,card1,card2) { 
		var card=card1+""+card2;
		card=card.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ")
		var timestamp= (new Date()).valueOf();
		var img='${ctx}/sysAdmin/checkReceiptsOpreation_queryRiskReceiptsImageShow.action?pkid='+pkid+'&imageType=1&txnday='+txnday+'&timestamp='+timestamp;
		var img2='${ctx}/sysAdmin/checkReceiptsOpreation_queryRiskReceiptsImageShow.action?pkid='+pkid+'&imageType=2&txnday='+txnday+'&timestamp='+timestamp;
		showBigImg(img,img2,card);
	}
	
	function sysAdmin_riskselltle_editFun(index){
		var rows = $('#sysAdmin_riskTransCard_datagrid').datagrid('getRows');
		var money=rows[index].TXNAMOUNT;
		var mid=rows[index].MID;
		var pkid=rows[index].PKID;
		//审批通过 修改 表的状态为1即可
		$.messager.confirm('确认','您确认要通过所选记录吗?',function(result) {
			if (result) {
				$.messager.progress();
				$.ajax({
					url:'${ctx}/sysAdmin/checkReceiptsOpreation_auditReceiptsYes.action?mid='+mid+'&money='+money,
					type:'post',
					data:{"pkid":pkid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						$.messager.progress('close');
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_riskTransCard_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.progress('close');
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
			}
		});
	}

	function sysAdmin_risknoUploadReceipts_editFun(pkid){
		//不同过需要填写原因
		$('<div id="sysAdmin_riskReceiptsAudit_openDialog"/>').dialog({
			title: '<span style="color:#157FCC;">开通</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10652.jsp?pkid='+pkid,
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var validator = $('#sysAdmin_receiptsAuditNo_editForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$('#sysAdmin_receiptsAuditNo_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkReceiptsOpreation_auditReceiptsNo.action',
						success:function(data) {
							$.messager.progress('close');
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_riskTransCard_datagrid').datagrid('reload');
					    			$('#sysAdmin_riskReceiptsAudit_openDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_riskReceiptsAudit_openDialog').dialog('destroy');
					    			$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_riskReceiptsAudit_openDialog').dialog('destroy');
					$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_riskTransCard_datagrid').datagrid('unselectAll');
			}
		});
	}

	//表单查询
	function sysAdmin_riskReceiptsAudit_searchFun80() {
		$('#sysAdmin_riskTransCard_datagrid').datagrid('load', serializeObject($('#sysAdmin_riskreceiptsAudit_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_riskReceiptsAudit_cleanFun80() {
		$('#sysAdmin_riskreceiptsAudit_searchForm input').val('');
	}


	function showBigImg(img,img2,cc){
		var imgDialog = "<div id='sysAdmin_imgDialog2' style='padding:10px;'><th><h1 style='font-size:15pt;'>卡号：</h1></th><h1 id='cardId'>1111111111</h1><img id='img' style='width:480px;height:550px'><div style='height:50px'></div><img id='img2' style='width:480px;height:550px'></div>";
		$('#sysAdmin_riskTransCard_datagrid').after(imgDialog); 
		$("#cardId").text(cc);
		$("#img").attr("src",img);
		$("#img2").attr("src",img2);
		$('#sysAdmin_imgDialog2').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 600,   
			    height: 600,
			    resizable:true,
		    	maximizable:true, 
			    modal:true,
			    buttons:[{
					text:'顺时针',
					iconCls:'',
					handler:function() {
						rotateRight();
					}
				},{
					text:'逆时针',
					iconCls:'',
					handler:function() {
						rotateLeft();
					}
				}],
				onClose:function() {
					$('#sysAdmin_imgDialog2').remove();
					rotation=0;
					rotate=0;
				}
			});
	}
	
	var rotation=0;
	var rotate=0;   
	function rotateRight(){   
		rotation=(rotation==3)?0:++rotation;
		rotate=(rotate==270)?0:rotate+90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";   
		$("#img2").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img2").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")"; 
	}
	
	function rotateLeft(){
		rotation=(rotation==0)?3:--rotation;
		rotate=(rotate==0)?270:rotate-90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";
		$("#img2").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img2").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_riskreceiptsAudit_searchForm" style="padding-left:5%" method="post">
		<input type="hidden" id="ids" name="ids"/>
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
										
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传状态</th>
					<td>
						<select name="uploadStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">已上传</option>
		    				<option value="2">未上传</option>
		    			</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批状态</th>
					<td>
						<select name="settleFlagStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="3">已通过</option>
		    				<option value="4">未通过</option>
		    			</select>
					</td>
				</tr> 
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询日期</th>
					<td><input name="txnday" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>至
					<input name="txnday1" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>
					</td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期类型</th>
					<td>
						<select name="dateType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="1" selected="selected">按交易日期</option>
		    				<option value="2">按审批通过日期</option>
		    			</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;风控状态</th>
					<td>
						<select name="riskType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:80px;">
		    				<option value="0" >正常</option>
		    				<option value="1" selected="selected">风险</option>
		    			</select>
					</td>

				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_riskReceiptsAudit_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_riskReceiptsAudit_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    	 <table id="sysAdmin_riskTransCard_datagrid" style="overflow: hidden;"></table> 
    </div> 


