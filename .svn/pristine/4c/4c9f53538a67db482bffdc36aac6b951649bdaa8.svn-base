<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_receiptsAudit_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkReceiptsOpreation_queryReceiptsAuditList.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'PKID',
			sortOrder: 'desc',
			idField : 'PKID',
			columns : [[{
				title : 'PKID',
				field : 'PKID',
				width : 100,
				hidden : true
			},{
				title : '所属机构',
				field : 'UNNO',
				width : 100
			},{
				title : '商户编号',
				field : 'MID',
				width : 100
			},{
				title : '商户名称',
				field : 'RNAME',
				width : 100
			},{
				title : '卡号',
				field : 'CARDPAN',
				width : 100
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
				title :'小票是否上传',
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
						return '<img src="${ctx}/images/query_search.png" title="预览小票" style="cursor:pointer;" onclick="sysAdmin_receiptsAudit_queryImage('+row.PKID+','+row.TXNDAY+')"/>&nbsp;&nbsp'+
						   '<input type="button" value="审批通过" onclick="sysAdmin_selltle_editFun('+row.PKID+')"/>&nbsp;&nbsp'+
						   '<input type="button" value="审批不通过" onclick="sysAdmin_noUploadReceipts_editFun('+row.PKID+')"/>'; 
						}else{
							return '<img src="${ctx}/images/query_search.png" title="预览小票" style="cursor:pointer;" onclick="sysAdmin_receiptsAudit_queryImage('+row.PKID+','+row.TXNDAY+')"/>'; 
							}
}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'导出调单不合规商户',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_exportReceiptsNoPassData_exportFun();
				}
			}]	
		});
	});

	function sysAdmin_exportReceiptsNoPassData_exportFun(){
		//导出不合格商户
			$('#sysAdmin_receiptsAudit_searchForm').form('submit',{
	    		url:'${ctx}/sysAdmin/checkReceiptsOpreation_exportAuditReceiptsNoMerchantInfo.action'
	    	});
		}
	
	function sysAdmin_receiptsAudit_queryImage(pkid,txnday) { 
		var timestamp= (new Date()).valueOf();
		var img='${ctx}/sysAdmin/checkReceiptsOpreation_queryReceiptsImageShow.action?pkid='+pkid+'&txnday='+txnday+'&timestamp='+timestamp;
		showBigImg(img);
	}
	
	function sysAdmin_selltle_editFun(pkid){
		//审批通过 修改 表的状态为1即可
		$.messager.confirm('确认','您确认要通过所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/checkReceiptsOpreation_auditReceiptsYes.action",
					type:'post',
					data:{"pkid":pkid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_receiptsAudit_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '通过记录出错！');
						$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
			}
		});
	}

	function sysAdmin_noUploadReceipts_editFun(pkid){
		//不同过需要填写原因
		$('<div id="sysAdmin_receiptsAudit_openDialog"/>').dialog({
			title: '<span style="color:#157FCC;">开通</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10611.jsp?pkid='+pkid,
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
				    				$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_receiptsAudit_datagrid').datagrid('reload');
					    			$('#sysAdmin_receiptsAudit_openDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_receiptsAudit_openDialog').dialog('destroy');
					    			$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_receiptsAudit_openDialog').dialog('destroy');
					$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_receiptsAudit_datagrid').datagrid('unselectAll');
			}
		});
	}

	//表单查询
	function sysAdmin_receiptsAudit_searchFun80() {
		$('#sysAdmin_receiptsAudit_datagrid').datagrid('load', serializeObject($('#sysAdmin_receiptsAudit_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_receiptsAudit_cleanFun80() {
		$('#sysAdmin_receiptsAudit_searchForm input').val('');
	}


	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog2' style='padding:10px;'><img id='img' style='width:800px;height:400px'></div>";
		$('#sysAdmin_receiptsAudit_datagrid').after(imgDialog); 
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog2').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 800,   
			    height: 500,
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
	}
	
	function rotateLeft(){
		rotation=(rotation==0)?3:--rotation;
		rotate=(rotate==0)?270:rotate-90;
		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_receiptsAudit_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
										
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理状态</th>
					<td>
						<select name="uploadStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="1">已上传</option>
		    				<option value="2">未上传</option>
		    				<option value="3">已通过</option>
		    				<option value="4">未通过</option>
		    			</select>
					</td>
				</tr> 
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易日期</th>
					<td><input name="txnday" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>至
					<input name="txnday1" class="easyui-datebox" data-options="editable:false,required:true" style="width: 150px;"/>
					</td>

				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_receiptsAudit_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_receiptsAudit_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    	 <table id="sysAdmin_receiptsAudit_datagrid" style="overflow: hidden;"></table> 
    </div> 


