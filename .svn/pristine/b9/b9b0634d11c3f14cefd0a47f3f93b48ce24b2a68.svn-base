<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10644_datagrid').datagrid({
			url :"${ctx}/sysAdmin/merchant_listNoMerchant.action",
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
				field : 'bmid',
				title : '商户id',
				hidden : true ,
				checkbox:false
			},{
				title : '商户编号',
				field : 'mid',
				width : 100
			},{
				title : '归属',
				field : 'unitName',
				width : 100
			},{
				title : '商户名称',
				field : 'rname',
				width : 200
			},{
				title :'法人',
				field :'legalPerson',
				width : 100
			},{
				title :'联系人电话',
				field :'contactPhone',
				width : 100
			},{
				title :'合同开始时间',
				field :'contractPeriod',
				width : 100
			},{
				title :'状态',
				field :'authType',
				width : 100,
				formatter:function(value,row,index){
					//0成功  1失败  2 补入账中 3入账失败
					if(value=='0'){
						return "未认证";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_10644_datagrid').datagrid('getData').rows[index];
					return '<img src="${ctx}/images/start.png" title="认证通过" style="cursor:pointer;" onclick="sysAdmin_10644_edit('+index+')"/>&nbsp;&nbsp';
				}
			}]]
		});
	});
	
	//手录
	function sysAdmin_10644_edit(index){
		//获取操作对象
		var rows = $('#sysAdmin_10644_datagrid').datagrid('getRows');
		var rowData = rows[index];
		$('<div id="sysAdmin_10644_editDialog"><div align="center" style="color:#157FCC;">确认商户 '+rowData.rname+' 认证通过？</div></div>').dialog({
			title: '<span style="color:#157FCC;align:center">提示</span>',
			width: 300,
		    height:100, 
		    closed: false,
		    //href: "<span style='color:#157FCC;'>确认再次认证？</span>",  
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					//等待窗口
					$('#sysAdmin_10644_editDialog').dialog('destroy');
					$('<div id="sysAdmin_10644_wait"><div align="center" style="color:#157FCC;">请稍等,正在认证中...</div></div>').dialog({
						title: '<span style="color:#157FCC;align:center">提示</span>',
						width: 200,
					    height:100,
					    //closed: false,
					    modal: true
					});
					//认证
					$.ajax({ 
						url:"sysAdmin/merchant_updateMerchantGoByMid.action?mid="+rowData.mid, 
						dataType:"json",
						type:"post",
						error:function(err){
							$('#sysAdmin_10644_wait').dialog('destroy');
							alert("错误");
						},
						success:function(data){
							//var result = $.parseJSON(data);
							//var result = JSON.parse(data);
							$('#sysAdmin_10644_wait').dialog('destroy');
							if (data.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (data.success) {
									$('#sysAdmin_10644_datagrid').datagrid('unselectAll');
					    			$('#sysAdmin_10644_datagrid').datagrid('reload');
					    			$.messager.show({
										title : '提示',
										msg : data.msg
									});
					    		} else {
									$('#sysAdmin_10644_datagrid').datagrid('unselectAll');
									$('#sysAdmin_10644_datagrid').datagrid('reload');
					    			$.messager.alert('提示',data.msg);
						    	}
					    	}	
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10644_editDialog').dialog('destroy');
					$('#sysAdmin_10644_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10644_datagrid').datagrid('unselectAll');
			}
		});
	}
	function bill_agentunitdata10644_searchFun() {
		$('#sysAdmin_10644_datagrid').datagrid('load', serializeObject($('#sysAdmin_10644_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata10644_cleanFun() {
		$('#sysAdmin_10644_searchForm input').val('');
	}
	
</script> 

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_10644_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="searchids" name="searchids" />
			<table class="tableForm" >
					<tr>
						<th>商户编号</th>
						<td><input name="mid" style="width: 150px;" /></td>
						<td width="10px"></td>
						<th>商户名称</th>
						<td><input name="rname" style="width: 150px;" /></td>
						<td width="10px"></td>
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="bill_agentunitdata10644_searchFun();">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="bill_agentunitdata10644_cleanFun();">清空</a>	
						</td>
					</tr>
				</table>
			</form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10644_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
