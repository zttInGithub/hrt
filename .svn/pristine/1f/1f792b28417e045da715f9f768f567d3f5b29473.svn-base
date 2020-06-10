<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantZK2_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantInfoZKbaodan.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'bmid',
			columns : [[{
				title : '商户ID',
				field : 'bmid',
				width : 100,
				hidden : true
			},/**{
				title : '机构名称',
				field : 'unitName',
				width : 100
			},**/{
				title : '装机地址',
				field : 'baddr',
				width : 100
			},{
				title : '联系人',
				field : 'contactPerson',
				width : 100
			},{
				title : '联系电话',
				field : 'contactPhone',
				width : 100
			},{
				title : '联系固话',
				field : 'contactTel',
				width : 100
			},{
				title :'商户编号',
				field :'mid',
				width : 100,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},/**{
				title :'商户类型',
				field :'isM35',
				width : 100,
				formatter : function(value,row,index) {
					if (value==2){
					   return "个人商户";
					}else if (value==3){
					   return "企业商户";
					}else if (value==4){
					   return "优惠商户";
					}else if (value==5){
					   return "减免商户";
					}else{
					   return "标准商户";
					}
				}
			},{
				title :'业务人员',
				field :'busidName',
				width : 100
			},{
				title :'维护人员',
				field :'maintainUserIdName',
				width : 100
			},**/{
				title :'受理状态',
				field :'approveStatusName',
				width : 100
			},{
				title :'受理时间',
				field :'approveDate',
				width : 120
			},{
				title :'受理描述',
				field :'processContext',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (typeof value != "undefined"){
					   return "<span title="+value+">"+value+"</span>";
					}  
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.approveStatus == 'C'){
						return '';
					}else if(row.approveStatus != 'K'){
						return '<img src="${ctx}/images/start.png" title="修改终端" style="cursor:pointer;" onclick="sysAdmin_merchantterminalinfo_queryFun('+index+')"/>&nbsp;&nbsp';
							//'<img src="${ctx}/images/frame_pencil.png" title="修改商户" style="cursor:pointer;" onclick="sysAdmin_merchant12_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+','+row.accType+')"/>&nbsp;&nbsp'+
							//'<img src="${ctx}/images/frame_pencil.png" title="修改商户(费改)" style="cursor:pointer;" onclick="sysAdmin_merchant13_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+','+row.accType+','+row.isM35+')"/>';
					}else{
						return '<img src="${ctx}/images/start.png" title="修改终端" style="cursor:pointer;" onclick="sysAdmin_merchantterminalinfo_queryFun('+index+')"/>&nbsp;&nbsp'+
							//'<img src="${ctx}/images/frame_pencil.png" title="修改商户" style="cursor:pointer;" onclick="sysAdmin_merchant12_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+','+row.accType+')"/>&nbsp;&nbsp'+
							//'<img src="${ctx}/images/frame_pencil.png" title="修改商户(费改)" style="cursor:pointer;" onclick="sysAdmin_merchant13_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+','+row.accType+','+row.isM35+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_remove.png" title="删除商户" style="cursor:pointer;" onclick="sysAdmin_merchant10_delFun('+row.bmid+')"/>';
					}
				}
			}]], 
			toolbar:[/**{
					id:'btn_add',
					text:'商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchant11_addFun();
					}
				},{
					id:'btn_add',
					text:'个体商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchant12_addFun();
					}
				},**/{
					id:'btn_add',
					text:'商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchant13_addFun();
					}
				}/**,{
					id:'btn_add',
					text:'优惠商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchant14_addFun();
					}
				},{
					id:'btn_add',
					text:'减免商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchant15_addFun();
					}
				}**/]		
		});
	});
	
	function sysAdmin_merchant11_addFun() {
		$('<div id="sysAdmin_merchant_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">商户签约</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/welcomeAction_getU.action?action=/biz/bill/merchant/10411.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_merchant11_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_merchant11_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_merchant11_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_addMerchantInfo.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchant_addDialog').dialog('destroy');
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
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_merchant_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//传统商户签约（费改）
	function sysAdmin_merchant12_addFun(){
		$.messager.confirm('继续申请','尊敬的用户：通过 个体商户入网 报单的商户，单日累计交易限额为1万元，如不能满足您的使用需求，请选择其他入口。',function(result) {
			if (result) {
				$('<div id="sysAdmin_addMerchant12_datagrid"/>').dialog({
					title: '<span style="color:#157FCC;">个体商户签约</span>', 
					width: 1000,    
				    height: 600,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/welcomeAction_getU.action?action=/biz/bill/merchant/10424.jsp',  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    	if($('#materialUpLoad3File').val()!="" && $('#materialUpLoad4File').val()!="" && $('#materialUpLoad5File').val()!="" && $('#registryUpLoadFile').val()!="" && $('#photoUpLoadFile').val()!="" && $('#bigdealUpLoadFile').val()!="" && $('#materialUpLoad2File').val()!=""){
				    		var validator = $('#sendInfo').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
				    		//$('#areaCode').val($('#city').val());
				    		$('#sendInfo').form('submit',{
					    		url:'${ctx}/sysAdmin/merchant_addMerchantInfo.action',
				    			success:function(data) {
				    				$.messager.progress('close'); 
				    				var result = $.parseJSON(data);
					    			if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
							    			$('#sysAdmin_addMerchant12_datagrid').dialog('destroy');
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
				    		}else{
				    			$.messager.alert('提示', '请上传必要的文件！');
				    		}
			    		}			    
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function() {
						$('#sysAdmin_addMerchant12_datagrid').dialog('destroy');
						}  
					}], 
					onClose:function() {
					$(this).dialog('destroy');
					}
				});
			}
		});
	}

	//企业商户签约（费改）
	function sysAdmin_merchant13_addFun(){
				$('<div id="sysAdmin_addMerchant13_datagrid"/>').dialog({
					title: '<span style="color:#157FCC;">商户签约</span>', 
					width: 1000,    
				    height: 600,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/merchant/10425_2.jsp',  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    	if($('#registryUpLoadFile').val()!="" && $('#photoUpLoadFile').val()!="" && $('#bupLoadFile').val()!="" && $('#legalUploadFile').val()!="" && $('#materialUpLoadFile').val()!="" && $('#materialUpLoad3File').val()!="" && $('#materialUpLoad4File').val()!="" && $('#materialUpLoad5File').val()!="" && $('#rupLoadFile').val()!=""){
				    		var validator = $('#sendInfo2').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
				    		//$('#sendInfo2 #areaCode').val($('#sendInfo2 #city').val());
				    		$('#sendInfo2').form('submit',{
					    		url:'${ctx}/sysAdmin/merchant_addMerchantInfobaodan.action',
				    			success:function(data) {
				    				$.messager.progress('close'); 
				    				var result = $.parseJSON(data);
					    			if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
							    			$('#sysAdmin_addMerchant13_datagrid').dialog('destroy');
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
				    		}else{
				    			$.messager.alert('提示', '请上传必要的文件！');
				    		}
				    	}		    
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function() {
						$('#sysAdmin_addMerchant13_datagrid').dialog('destroy');
						}  
					}], 
					onClose:function() {
					$(this).dialog('destroy');
					}
				});
	}
	
	//优惠商户签约（费改）
	function sysAdmin_merchant14_addFun(){
		$.messager.confirm('继续申请','尊敬的用户：通过“优惠商户入网”接口报单的商户，需按银联评估标准提交特殊资质证明，且最终计价标准以银联审核结果为准！',function(result) {
			if (result) {
				$('<div id="sysAdmin_addMerchant14_datagrid"/>').dialog({
					title: '<span style="color:#157FCC;">优惠商户签约</span>', 
					width: 1000,    
				    height: 600,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/merchant/10426.jsp',  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    	if($('#registryUpLoadFile').val()!="" && $('#photoUpLoadFile').val()!="" && $('#bupLoadFile').val()!="" && $('#legalUploadFile').val()!="" && $('#materialUpLoadFile').val()!="" && $('#materialUpLoad3File').val()!="" && $('#materialUpLoad4File').val()!="" && $('#materialUpLoad5File').val()!="" && $('#rupLoadFile').val()!="" && $('#materialUpLoad6File').val()!=""){
				    		var validator = $('#sendInfo3').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
				    		$('#sendInfo3').form('submit',{
					    		url:'${ctx}/sysAdmin/merchant_addMerchantInfo.action',
				    			success:function(data) {
				    				$.messager.progress('close'); 
				    				var result = $.parseJSON(data);
					    			if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
							    			$('#sysAdmin_addMerchant14_datagrid').dialog('destroy');
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
				    		}else{
				    			$.messager.alert('提示', '请上传必要的文件！');
				    		}
				   		 }		    
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function() {
						$('#sysAdmin_addMerchant14_datagrid').dialog('destroy');
						}  
					}], 
					onClose:function() {
					$(this).dialog('destroy');
					}
				});
			}
		}); 
	}
	//减免商户签约（费改）
	function sysAdmin_merchant15_addFun(){
		$.messager.confirm('继续申请','尊敬的用户：通过“减免商户入网”接口报单的商户，需按银联评估标准提交特殊资质证明，且最终计价标准以银联审核结果为准！',function(result) {
			if (result) {
				$('<div id="sysAdmin_addMerchant15_datagrid"/>').dialog({
					title: '<span style="color:#157FCC;">减免商户签约</span>', 
					width: 1000,    
				    height: 600,  
				    resizable:true,
		    		maximizable:true,
				    closed: false,
				    href: '${ctx}/biz/bill/merchant/10427.jsp',  
				    modal: true,
				    buttons:[{
				    	text:'确认',
				    	iconCls:'icon-ok',
				    	handler:function() {
				    	if($('#registryUpLoadFile').val()!="" && $('#photoUpLoadFile').val()!="" && $('#bupLoadFile').val()!="" && $('#legalUploadFile').val()!="" && $('#materialUpLoadFile').val()!="" && $('#materialUpLoad3File').val()!="" && $('#materialUpLoad4File').val()!="" && $('#materialUpLoad5File').val()!="" && $('#rupLoadFile').val()!="" && $('#materialUpLoad6File').val()!="" && $('#materialUpLoad7File').val()!=""){
				    		var validator = $('#sendInfo4').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
				    		$('#sendInfo4').form('submit',{
					    		url:'${ctx}/sysAdmin/merchant_addMerchantInfo.action',
				    			success:function(data) {
				    				$.messager.progress('close'); 
				    				var result = $.parseJSON(data);
					    			if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
							    			$('#sysAdmin_addMerchant15_datagrid').dialog('destroy');
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
				    		}else{
				    			$.messager.alert('提示', '请上传必要的文件！');
				    		}	
				 	   }	    
					},{
						text:'取消',
						iconCls:'icon-cancel',
						handler:function() {
						$('#sysAdmin_addMerchant15_datagrid').dialog('destroy');
						}  
					}], 
					onClose:function() {
					$(this).dialog('destroy');
					}
					});
			 }
		});
	}
	/**
	//商户签约（费改）
	function sysAdmin_merchant12_addFun(){
		$('<div id="sysAdmin_addMerchant12_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">传统商户签约</span>', 
			width: 1000,    
			    height: 600,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10424.jsp',  
			    modal: true,
			    buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
			$('#sysAdmin_addMerchant12_datagrid').dialog('destroy');
			}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			}
			});
	}
	**/
	function sysAdmin_merchant12_editFun(index,isForeign,merchantType,bmid,accType){
		$('<div id="sysAdmin_merchant_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改商户信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10412.jsp?isForeign='+isForeign+'&merchantType='+merchantType+'&bmid='+bmid+'&accType='+accType,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantZK2_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchant12_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_merchant12_editForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    		
					$('#sysAdmin_merchant12_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_editMerchantInfo.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchant_editDialog').dialog('destroy');
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
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_merchant_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	function sysAdmin_merchant13_editFun(index,isForeign,merchantType,bmid,accType,isM35){
		//页面编号
		var pageNumber;
		var pageForm;
		if(isM35 == '2'){
			pageNumber = 10424;
			pageForm='sendInfo';
		}
		else if(isM35 == '3'){
			pageNumber = 10425;
			pageForm='sendInfo2';
		}
		else if(isM35 == '4'){
			pageNumber = 10426;
			pageForm='sendInfo3';
		}
		else if(isM35 == '5'){
			pageNumber = 10427;
			pageForm='sendInfo4';
		}else if(isM35 == '0'){
			pageNumber = 10412;
			pageForm='sysAdmin_merchant12_editForm';
		}
		$('<div id="sysAdmin_merchant_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改商户信息</span>',
			width: 1000,
		    height:600, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/'+pageNumber+'.jsp?type=1&bmid='+bmid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantZK2_datagrid').datagrid('getRows');
				var row = rows[index];
			    $("#"+pageForm).form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById(pageForm).getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    		var validator = $("#"+pageForm).form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
					$("#"+pageForm).form('submit', {
						url:'${ctx}/sysAdmin/merchant_editMerchantInfo.action',
						success:function(data) {
							$.messager.progress('close'); 
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchant_editDialog').dialog('destroy');
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
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_merchant_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	function sysAdmin_merchant10_delFun(bmid){
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/merchant_deleteMerchantInfo.action",
					type:'post',
					data:{"bmid":bmid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_merchantZK2_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
					}
				});
			}
		});
	}
	
	//申请终端
	function sysAdmin_merchantterminalinfo_queryFun(index){
	   	var rows = $('#sysAdmin_merchantZK2_datagrid').datagrid('getRows');
		var row = rows[index];
		var bmid=row.bmid;
		var unno=row.unno;
		var mid=row.mid;
		var baddr=row.baddr;
		var contactPhone=row.contactPhone;
		var contactPerson=row.contactPerson;
		//var contactTel=row.contactTel;
		var contactTel='';
		if(contactPhone==null){
			contactPhone=" ";
			}
		if(contactTel==null){
			contactTel=" ";
			}
		$('<div id="sysAdmin_merchantterminalinfo_run"/>').dialog({
			title: '修改终端',
			width: 1000,   
		    height: 550,   
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10491_1.jsp?bmid='+bmid+'&unno='+unno+'&mid='+mid+'&baddr='+encodeURIComponent(encodeURIComponent(baddr))+'&contactPerson='+encodeURI(encodeURI(contactPerson))+'&contactPhone='+contactPhone+'&contactTel='+contactTel,  
		    modal: true, 
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}


	//表单查询
	function sysAdmin_merchantZK_searchFun10() {
		$('#sysAdmin_merchantZK2_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchantZK2_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantZK_cleanFun10() {
		$('#sysAdmin_merchantZK2_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchantZK2_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="K">退回</option>
		    				<option value="W">待审核</option>
		    				<option value="Z">待复核</option>
		    			</select>
					</td>
				</tr> 
			
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantZK_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantZK_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_merchantZK2_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

