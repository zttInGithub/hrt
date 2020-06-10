<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10494_datagrid').datagrid({
			url :'${ctx}/sysAdmin/qrInvitationInfo_queryMerQRInvitationInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ,100],
			sortName: 'ICID',
			sortOrder: 'desc',
			idField : 'ICID',
			columns : [[{
				field : 'ICID',
				checkbox:true
			},{
				title : '机构编号',
				field : 'UNNO',
				width : 60
			},{
				title : '邀请码',
				field : 'INVITATIONCODE',
				width : 100
			},{
				title : '密码',
				field : 'ICPASSWORD',
				width : 100
			},{
				title : '扫码消费费率',
				field : 'SCANRATE',
				width : 80
			},{
				title :'使用状态',
				field :'STATUS',
				width : 50,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='0'){
					   return "未使用";
					}else if(value=='1'){
						return "已使用";
					}else{
						return "停用";
					}
				}
			},{
				title : '绑定商户编号',
				field : 'MID',
				width : 80
			},{
				title :'绑定时间',
				field :'USEDCONFIRMDATE',
				width : 120
				//sortable :true
			},{
				title :'创建时间',
				field :'MAINTAINDATE',
				width : 120,
				sortable :true
			}
			/**,{
				title :'查看二维码',
				field :'maintainUserId',
				width : 70,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_10494_datagrid').datagrid('getData').rows[index];
					if(rowData.qrUrl!=null&&rowData.qrUpload!=null){
						return '<img src="${ctx}/images/query_search.png" title="查看二维码" style="cursor:pointer;" onclick="sysAdmin_10494_queryFun('+index+')"/>&nbsp;&nbsp';
					}
			}
			}**/]],
			toolbar:[{
				id:'btn_add',
				text:'分配邀请码',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected10494_fenpei();
				}
			},{
				id:'btn_run',
				text:'导出查询所有',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected10494_exportFunAll();
				}
			}/**,{
				id:'btn_run',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected10494_exportFun();
				}
			}**/]
		});
	});

	function sysAdmin_merchantterminalinfoSelected10494_exportFunAll() {
		$('#sysAdmin_10494_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/qrInvitationInfo_exportMerQRAll.action'
			    	});
	}
	
	function sysAdmin_merchantterminalinfoSelected10494_fenpei(){
		var checkedItems = $('#sysAdmin_10494_datagrid').datagrid('getChecked');
		var names = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.STATUS != 0){
				result = false;
			}
			names.push(item.ICID);
		});
		if(!result){
			$.messager.alert('提示','已有使用或停用的邀请码！');
			return;
		}
		var icids=names.join(",");
		if(icids==null||icids==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		$.messager.confirm('确认','您确认所选邀请码都正确了吗?',function(result) {
			if (result) {
				$('<div id="sysAdmin_fen494_datagrid"/>').dialog({
					title: '<span style="color:#157FCC;">选择机构</span>',
					width: 550,   
				    height: 330, 
				    closed: false,
				    href: '${ctx}/biz/bill/merchant/10496.jsp?icids='+icids,
				    modal: true,
					buttons:[{
						text:'确认',
						iconCls:'icon-ok',
						handler:function() {
							var validator = $('#fenpeiInfo').form('validate');
				    		if(validator){
				    			$.messager.progress();
				    		}
							$('#fenpeiInfo').form('submit', {
								url:'${ctx}/sysAdmin/qrInvitationInfo_updateQRIUnno.action?icids='+icids,
								success:function(data) {
									$.messager.progress('close');
									var result = $.parseJSON(data);
									if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#sysAdmin_10494_datagrid').datagrid('unselectAll');
						    				$('#sysAdmin_10494_datagrid').datagrid('reload');
							    			$('#sysAdmin_fen494_datagrid').dialog('destroy');
							    			$.messager.show({
												title : '提示',
												msg : result.msg
											});
							    		} else {
							    			$('#sysAdmin_fen494_datagrid').dialog('destroy');
							    			$('#sysAdmin_10494_datagrid').datagrid('reload');
							    			$('#sysAdmin_10494_datagrid').datagrid('unselectAll');
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
							$('#sysAdmin_fen494_datagrid').dialog('destroy');
						}
					}],
					onClose:function() {
						$(this).dialog('destroy');
					}
				});
			}else{
				$('#sysAdmin_10494_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_merchantterminalinfoSelected10494_create(){
		$('<div id="sysAdmin_add494_datagrid"/>').dialog({
			title: '<span style="color:#157FCC;">生成邀请码</span>', 
			width: 500,    
		    height: 300,  
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10495.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#createInfo').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		//$('#areaCode').val($('#city').val());
		    		$('#createInfo').form('submit',{
			    		url:'${ctx}/sysAdmin/qrInvitationInfo_addMerQRInvitationInfo.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10494_datagrid').datagrid('reload');
					    			$('#sysAdmin_add494_datagrid').dialog('destroy');
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
				$('#sysAdmin_add494_datagrid').dialog('destroy');
				}  
			}], 
			onClose:function() {
			$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_merchantterminalinfo10494_exportFun() {
		$('#sysAdmin_10494_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_updateMerchantWithQR.action'
			    	});
	}
	
	function sysAdmin_merchantterminalinfoSelected10494_exportFun() {
		var checkedItems = $('#sysAdmin_10494_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmids_10494").val(bmids);
		$('#sysAdmin_10494_searchForm').form('submit',{
			url:'${ctx}/sysAdmin/merchant_updateMerchantWithQR.action',
			success:function(data){
			    $('#sysAdmin_10494_datagrid').datagrid('load', serializeObject($('#sysAdmin_10494_searchForm'))); 
			    var result = $.parseJSON(data);
			    $.messager.show({
					title : '提示',
					msg : result.msg
				});
			},
			error:function(err){
				$('#sysAdmin_10642_wait').dialog('destroy');
				var result = $.parseJSON(err);
				$.messager.show({
					title : '提示',
					msg : result.msg
				});
			}
		});
	}

	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun10494() {
		$('#sysAdmin_10494_datagrid').datagrid('load', serializeObject($('#sysAdmin_10494_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun10494() {
		$('#sysAdmin_10494_searchForm input').val('');
		$('#sysAdmin_10494_searchForm #status').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10494_searchForm" style="padding-left:2%" method="post">
		<input type="hidden" id="bmids_10494" name="bmids" />
			<table class="tableForm" >
				<tr>
					<th>机构编号:</th>
					<td><input name="unno" style="width: 150px;" /></td>
					<td width="10px"></td>
					<th>商户编号:</th>
					<td><input name="mid" style="width: 150px;" /></td>
					<td width="10px"></td>
					<th>绑定状态:</th>
					<td>
						<select name="status" id="status" style="width: 80px;">
							<option value="" >所有</option>
							<option value="1" >已使用</option>
							<option value="0" >未使用</option>
							<option value="2" >停用</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>邀请码:</th>
					<td><input name="invitationCode" style="width: 150px;" /></td>
					<td width="10px"></td>
					<th>创建时间:起</th>
					<td colspan="4"><input name="createDateStart" class="easyui-datebox" style="width: 150px;"  />
						止 <input name="createDateEnd" class="easyui-datebox" style="width: 150px;"  />
					</td>
					<td width="10px"></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun10494();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun10494();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10494_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>