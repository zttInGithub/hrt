<%@ page language="java" import="com.hrt.frame.entity.pagebean.UserBean"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!--结算成本查询-->
<%    
  Object userSession = request.getSession().getAttribute("user");
  UserBean user = ((UserBean) userSession);
%>
<script type="text/javascript">
	/* $('#saveBtn').hide(); */
	//初始化treegrid
	$(function() {
		var userLvl = <%=user.getUseLvl()%>
		$('#sysAdmin_unit00321_datagrid').datagrid({
			url : '${ctx}/sysAdmin/agentunit_queryUnnoCostMultiply.action',
			fit : true,
			fitColumns : false,
			border : false,
			nowrap : true,
			pagination: true,
			singleSelect:true,
			pageList : [ 10, 15 ],
			idField : 'FID',
			onBeforeLoad:function(){
				if(userLvl == 1){
					$("div.datagrid-toolbar [id ='btn_pass']").eq(0).hide();
				}else if(userLvl > 1 ){
					$("div.datagrid-toolbar [id ='btn_pass']").eq(0).hide();
					$("div.datagrid-toolbar [id ='btn_update']").eq(0).hide();
				}
			},
			onLoadSuccess: function(){
	            function bindRowsEvent(){
	                var panel = $('#sysAdmin_unit00321_datagrid').datagrid('getPanel');
	                var rows = panel.find('tr[datagrid-row-index]');
	                rows.unbind('click').bind('click',function(e){
	                    return false;
	                });
	                rows.find('div.datagrid-cell-check input[type=checkbox]').unbind().bind('click', function(e){
	                    var index = $(this).parent().parent().parent().attr('datagrid-row-index');
	                    if ($(this).attr('checked')){
	                        $('#sysAdmin_unit00321_datagrid').datagrid('selectRow', index);
	                    } else {
	                        $('#sysAdmin_unit00321_datagrid').datagrid('unselectRow', index);
	                    }
	                    e.stopPropagation();
	                });
	            }
	            setTimeout(function(){
	                bindRowsEvent();
	            }, 10);    
    		},
			columns : [[{
				title :'fid',
				field :'FID',
				checkbox: true
			},{
				title : '序号',
				field : 'FIDS',
				width : 100
			},{
				title : '文件名称',
				field : 'FNAME',
				width : 160
			},{
				title : '提交日期',
				field : 'CDATE',
				width : 130
			},{
				title :'运营中心机构号',
				field :'CBY',
				width : 100
			},{
				title :'运营中心名称',
				field :'UN_NAME',
				width : 130
			},{
				title :'处理日期',
				field :'ADATE',
				width : 140
			},{
				title :'处理状态',
				field :'STATUS',
				width : 100,
				formatter:function(value,rowData,rowIndex){	
					if(value == 0){		
						return "待审核";
					}
					if(value == 1){
						return "审核通过";
					}
					if(value == -1){
						return "退回";
					}
				}
			},{
				title :'退回原因',
				field :'REMARKS',
				width : 200
			},{
				title :'imgUpload',
				field :'IMGUPLOAD',
				width : 200,
				hidden:true
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(userLvl == 0){//总公司
						if(row.STATUS==-1 || row.STATUS == 1){
							return '<img src="${ctx}/images/zip.png" title="下载ZIP文件" style="cursor:pointer;" onclick="updateUC('+index+')"/>';
						}else{
							return '<img src="${ctx}/images/zip.png" title="下载ZIP文件" style="cursor:pointer;" onclick="updateUC('+index+')"/>&nbsp;&nbsp;&nbsp;&nbsp;'+
							'<img src="${ctx}/images/rollback.png" title="退回" style="cursor:pointer;" onclick="updateUT('+index+')"/>';
						}
					}else {
						return "";
					}
				}
			}
			]],toolbar:[{
				id:'btn_run',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_00321_exportFun();
				}
			},{
				id:'btn_update',
				text:'成本变更',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_00321_bg();
				}
			},{
				id:'btn_pass',
				text:'勾选通过',
				iconCls:'icon-ok',
				handler:function(){
					sysAdmin_00321_tg();
				}
			  }
			]
		});
	});
	
	//下载
	function updateUC(index) {
		$.messager.confirm('确认','您确认要下载ZIP文件吗?',function(result) {
			if (result) {
				var rows = $('#sysAdmin_unit00321_datagrid').datagrid('getRows');
				var row = rows[index];
				$('#bill_agentunit00321_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/agentunit_zipFile.action?imgUrl='+row.IMGUPLOAD,
					success:function(data){
						var _rsdata = $.parseJSON(data);
						$.messager.alert("提示",_rsdata.msg,"info");
					}
				});
			}
		});
	}
	//退回
	function updateUT(index) {
		$('<div id="updateUnnoT_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 460,
		    height:210, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00321u.jsp',  
		    modal: true,
 		    onLoad:function() {
		    	var rows = $('#sysAdmin_unit00321_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.QCID);    	
		    	$('#updateUnnoT_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				id:'umfPay_Mdj',
				handler:function() {
					$('#updateUnnoT_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentunit_TUnnoCost.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_unit00321_datagrid').datagrid('reload');
				    				$('#sysAdmin_unit00321_datagrid').datagrid('unselectAll');
					    			$('#updateUnnoT_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#updateUnnoT_editDialog').dialog('destroy');
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
					$('#updateUnnoT_editDialog').dialog('destroy');
					$('#sysAdmin_unit00321_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_unit00321_datagrid').datagrid('unselectAll');
			}
		});
/* 	
		$.messager.confirm('确认','您确认要退回吗?',function(result) {
			if (result) {
				var rows = $('#sysAdmin_unit00321_datagrid').datagrid('getRows');
				var row = rows[index];
				$('#bill_agentunit00321_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/agentunit_TUnnoCost.action?fid='+row.FID,
					success:function(data) {
		    			$.messager.progress('close'); 
		    			var result = $.parseJSON(data);
			    		if (result.sessionExpire) {
			    			window.location.href = getProjectLocation();
				    	} else {
				    		if (result.success) {
				    			$('#sysAdmin_unit00321_datagrid').datagrid('reload');
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
		}); */
	}
	//勾选导出
	function sysAdmin_00321_exportFun() {
		var rows = $('#sysAdmin_unit00321_datagrid').datagrid('getChecked');
	 	var row= "";
	 	if(rows.length == 0){
	 		$.messager.alert('提示','请勾选数据!','error');
	 	}else{
	 		row=rows[0];
			$('#bill_agentunit00321_searchForm').form('submit',{
				url:'${ctx}/sysAdmin/agentunit_reportUnnoCost.action?fid='+row.FID,
			});
		}
	}
	
	//成本变更
	function sysAdmin_00321_bg() {
		var rows = $("#sysAdmin_unit00321_datagrid").datagrid("getRows");
		for(var index in rows){
			if(rows[index].STATUS == 0){
				$.messager.alert('提示',"有未处理成本变更，请等待处理完再提交新变更",'warning');
				return ;
			}
		}
		$('<div id="sysAdmin_agentunit_bgDialog"/>').dialog({
			title: '<span style="color:#157FCC;">变更申请</span>',
			width: 650,
		    height:400, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00321s.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_agentunit_bgForm').form('validate');
		    		if(validator){
		    			/* $.messager.progress(); */
		    		}
		    		document.getElementById("zipUpLoad").value = document.getElementById('upLoadZip').value.replace(/.{0,}\\/, "");
		    		document.getElementById("cbUpLoad").value = document.getElementById('upLoadCB').value.replace(/.{0,}\\/, "");
		    		$('#sysAdmin_agentunit_bgForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_addUnnoCost.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_unit00321_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_bgDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$.messager.alert('提示', result.msg,"info");
						    	}
					    	}
			    		}
			    	});
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_agentunit_bgDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//成本审批通过
	function sysAdmin_00321_tg() {
		var row = $("#sysAdmin_unit00321_datagrid").datagrid("getChecked");
		if(row.length == 0){
			$.messager.alert("提示","请勾选操作列",'info');
			return ;
		}
		if(row[0].STATUS == -1){
			$.messager.alert("警告","退回状态变更不能通过!",'error');
			return ;
		}
		if(row[0].STATUS == 1){
			$.messager.alert("警告","该文件已做过变更，禁止重复变更!",'error');
			return ;
		}
		$.messager.confirm('提示','确认执行变更吗?',function(r){
		    if (r){
		    	$('#00321_fid').val(row[0].FID);
		    	$.messager.progress();
		    	$('#bill_agentunit00321_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/agentunit_editPassUnits.action',
					success:function(result){
						$.messager.progress('close');
						var data = $.parseJSON(result);
						if (data.success) {
		    				$('#sysAdmin_unit00321_datagrid').datagrid('reload');
			    			$.messager.show({
								title : '提示',
								msg : data.msg
							});
			    		} else {
			    			$.messager.alert('提示', data.msg);
				    	}
			        }
				});
		    }
		});
	}
	
	//表单查询
	function bill_agentunit00321_searchFun() {
		$('#sysAdmin_unit00321_datagrid').datagrid('load', serializeObject($('#bill_agentunit00321_searchForm')));
	}

	//清除表单内容
	function bill_agentunit00321_cleanFun() {
		$('#bill_agentunit00321_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="bill_agentunit00321_searchForm" style="padding-left:5%" method="post">
			<input type="hidden" name="FID" id="00321_fid" />
			<table class="tableForm" >
				<tr>
					<th>运营中心机构号</th>
					<td><input name="unno" style="width: 100px;" /></td>
					<th>文件名称</th>
					<td><input name="cbUpLoad" style="width: 100px;" /></td>
					<th>审批状态</th>
					<td>
						<select name="status" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="-1">退回</option>
		    				<option value="0">待审核</option>
		    				<option value="1">审核通过</option>
		    			</select>
					</td>
					<th>提交时间</th>
					<td><input name="adate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="zdate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="bill_agentunit00321_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="bill_agentunit00321_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	<div data-options="region:'center', border:false" style="overflow: hidden;">   
		<table id="sysAdmin_unit00321_datagrid" ></table>
	</div>
</div>