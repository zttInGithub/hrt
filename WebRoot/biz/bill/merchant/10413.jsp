<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantZK_datagrid1').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantInfoZK.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmid',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				title : '商户ID',
				field : 'bmid',
				width : 100,
				hidden : true
			},{
				title : '机构名称',
				field : 'unitName',
				width : 100
			},{
				title : '机构',
				field : 'unno',
				width : 100,
				hidden : true
			},{
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
			},{
				title :'业务人员',
				field :'busidName',
				width : 100
			},{
				title :'维护人员',
				field :'maintainUserIdName',
				width : 100
			},{
				title :'受理状态',
				field :'approveStatusName',
				width : 100
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
						return '<img src="${ctx}/images/start.png" title="修改终端" style="cursor:pointer;" onclick="sysAdmin_merchantterminalinfo_queryFun('+index+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_pencil.png" title="修改商户" style="cursor:pointer;" onclick="sysAdmin_merchant15_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+')"/>';
					}else{
						return '<img src="${ctx}/images/start.png" title="修改终端" style="cursor:pointer;" onclick="sysAdmin_merchantterminalinfo_queryFun('+index+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_pencil.png" title="修改商户" style="cursor:pointer;" onclick="sysAdmin_merchant15_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_remove.png" title="删除商户" style="cursor:pointer;" onclick="sysAdmin_merchant13_delFun('+row.bmid+')"/>';
					}
				}
			}]], 
			toolbar:[{
					id:'btn_add',
					text:'商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchant14_addFun();
					}
				}]		
		});
	});
	
	function sysAdmin_merchant14_addFun() {
		$('<div id="sysAdmin_merchant_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">商户签约</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10414.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_merchant14_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_merchant14_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		
		    		$('#sysAdmin_merchant14_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_addMerchantInfo.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantZK_datagrid1').datagrid('reload');
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
	
	function sysAdmin_merchant15_editFun(index,isForeign,merchantType,bmid){
		$('<div id="sysAdmin_merchant_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改商户信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10415.jsp?isForeign='+isForeign+'&merchantType='+merchantType+'&bmid='+bmid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantZK_datagrid1').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchant15_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_merchant15_editForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    		
					$('#sysAdmin_merchant15_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_editMerchantInfo.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantZK_datagrid1').datagrid('reload');
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
	
	function sysAdmin_merchant13_delFun(bmid){
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
							$('#sysAdmin_merchantZK_datagrid1').datagrid('reload');
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
	   	var rows = $('#sysAdmin_merchantZK_datagrid1').datagrid('getRows');
		var row = rows[index];
		var bmid=row.bmid;
		var unno=row.unno;
		var mid=row.mid;
		var baddr=row.baddr;
		var contactPhone=row.contactPhone;
		var contactPerson=row.contactPerson;
		var contactTel=row.contactTel;
		if(contactPhone==null){
			contactPhone=" ";
			}
		if(contactTel==null){
			contactTel=" ";
			}
		$('<div id="sysAdmin_merchantterminalinfo_run1"/>').dialog({
			title: '修改终端',
			width: 1000,   
		    height: 550,   
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10416.jsp?bmid='+bmid+'&unno='+unno+'&mid='+mid+'&baddr='+encodeURIComponent(encodeURIComponent(baddr))+'&contactPerson='+encodeURI(encodeURI(contactPerson))+'&contactPhone='+contactPhone+'&contactTel='+contactTel,  
		    modal: true, 
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
</script>

<table id="sysAdmin_merchantZK_datagrid1" style="overflow: hidden;"></table> 

