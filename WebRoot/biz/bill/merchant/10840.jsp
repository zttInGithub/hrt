<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantMicroInfoZKSyt.action',
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
			},{
				title : '机构名称',
				field : 'unitName',
				width : 100
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
				width : 100,
                formatter : function(value,row,index) {
                    if(value!=""&&value!=null && value.length==11){
                        return value.substring(0,3)+'****'+value.substring(value.length-4,value.length);
                    }
                }
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
					    return "";
					}else if(row.approveStatus != 'K'){
						//return '<img src="${ctx}/images/start.png" title="修改终端" style="cursor:pointer;" onclick="sysAdmin_merchantMicro_terminalinfo_queryYsbFun('+index+')"/>&nbsp;&nbsp';
						//'<img src="${ctx}/images/frame_pencil.png" title="修改商户" style="cursor:pointer;" onclick="sysAdmin_merchantMicro12Ysb_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+','+row.accType+')"/>';
					}else{
						return '<img src="${ctx}/images/frame_pencil.png" title="修改商户" style="cursor:pointer;" onclick="sysAdmin_merchantMicro12Ysb_editFun('+index+','+row.isForeign+','+row.merchantType+','+row.bmid+','+row.accType+')"/>&nbsp;&nbsp'+
							'<img src="${ctx}/images/frame_remove.png" title="删除商户" style="cursor:pointer;" onclick="sysAdmin_merchantMicro10_delYsbFun('+row.bmid+')"/>';
					}
				}
			}]]
			/**, 
			toolbar:[{
					id:'btn_add',
					text:'商户签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_merchantMicro11_addFun();
					}
				}]	**/	
		});
	});
	
	function sysAdmin_merchantMicro11_addFun() {
		$('<div id="sysAdmin_merchantMicro_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">商户签约</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10811.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$.messager.confirm('提示','请确认信息是否无误，提交申请后将不能调整！',function(re) {
				    if (re) {
			    		var inputs = document.getElementById("sysAdmin_merchantMicro11_addForm").getElementsByTagName("input");
			    		for(var i=0;i<inputs.length;i++){
			    	    	if(inputs[i].type=="file"){continue;}
			    			inputs[i].value = $.trim(inputs[i].value);
			    		}
			    	
			    		var validator = $('#sysAdmin_merchantMicro11_addForm').form('validate');
			    		
			    		if(validator){
			    			$.messager.progress();
			    		}
			    		$('#sysAdmin_merchantMicro11_addForm').form('submit',{
				    		url:'${ctx}/sysAdmin/merchant_addMerchantMicroInfo.action',
			    			success:function(data) {
			    				$.messager.progress('close'); 
			    				var result = $.parseJSON(data);
				    			if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
					    				$('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid('reload');
						    			$('#sysAdmin_merchantMicro_addDialog').dialog('destroy');
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
					$('#sysAdmin_merchantMicro_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_merchantMicro12Ysb_editFun(index,isForeign,merchantType,bmid,accType){
		$('<div id="sysAdmin_merchantMicro_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改商户信息</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10813.jsp?isForeign='+isForeign+'&merchantType='+merchantType+'&bmid='+bmid+'&accType='+accType,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchantMicro_deditForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_merchantMicro_deditForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){ 
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    		
					$('#sysAdmin_merchantMicro_deditForm').form('submit', {
						url:'${ctx}/sysAdmin/merchant_editMerchantMicroInfo.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid('reload');
					    			$('#sysAdmin_merchantMicro_editDialog').dialog('destroy');
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
					$('#sysAdmin_merchantMicro_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_merchantMicro10_delYsbFun(bmid){
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
							$('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid('reload');
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
	function sysAdmin_merchantMicro_terminalinfo_queryYsbFun(index){
	   	var rows = $('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid('getRows');
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
		$('<div id="sysAdmin_merchantterminalinfo1_run"/>').dialog({
			title: '修改终端',
			width: 1000,   
		    height: 550,   
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10812.jsp?bmid='+bmid+'&unno='+unno+'&mid='+mid+'&baddr='+encodeURIComponent(encodeURIComponent(baddr))+'&contactPerson='+encodeURI(encodeURI(contactPerson))+'&contactPhone='+contactPhone+'&contactTel='+contactTel,  
		    modal: true, 
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}


	//表单查询
	function sysAdmin_merchantMicroZK_searchFun10() {
		$('#sysAdmin_merchantMicroZKYsb_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchantMicroZK_searchYsbForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantMicroZK_cleanFun10() {
		$('#sysAdmin_merchantMicroZK_searchYsbForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchantMicroZK_searchYsbForm" style="padding-left:10%">
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
		    				<option value="Z">待挂终端</option>
		    			</select>
					</td>
				</tr> 
			
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantMicroZK_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantMicroZK_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_merchantMicroZKYsb_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

