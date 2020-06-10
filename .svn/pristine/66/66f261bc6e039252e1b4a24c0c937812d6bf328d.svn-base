<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_30020_datagrid').datagrid({
			url :'${ctx}/sysAdmin/loanApplication_queryLoanList.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'BLAID',
			columns : [[{
				title : 'ID',
				field : 'BLAID',
				width : 100,
				hidden : true
			},{
				title : '机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '运营中心',
				field : 'UN_NAME',
				width : 100
			},{
				title : '经营方式',
				field : 'MANAGETYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value == 1){
					   return "个人经营";
					}else if(value == 2){
						return "合伙经营";
					}else if(value == 3){
						return "个体工商经营";
					}else if(value == 4){
						return "企业经营";
					}else if(value == 5){
						return "其他方式";
					}  
				}
			},{
				title : '主营方式',
				field : 'MANAGEMODE',
				width : 100,
				formatter : function(value,row,index) {
					if (value == 1){
					   return "电销";
					}else if(value == 2){
						return "地推";
					}else if(value == 3){
						return "代理";
					}else if(value == 4){
						return "其他";
					}
				}
			},{
				title : '员工数量',
				field : 'STAFFNUM',
				width : 100
			},{
				title :'经营地址',
				field :'MANAGEADDR',
				width : 100
			},{
				title :'分润金额',
				field :'PROFITAMT',
				width : 100
			},{
				title :'申请人',
				field :'APPLICANT',
				width : 100
			},{
				title :'身份证',
				field :'LEGALNUM',
				width : 100
			},{
				title :'申请额度',
				field :'APPLYQUOTA',
				width : 100
			},{
				title :'联系电话',
				field :'PHONE',
				width : 100
			},{
				title :'信贷员姓名',
				field :'BUSNAME',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.APPROVESTATUS == 'A'){
						return '<img src="${ctx}/images/start.png" title="分配" style="cursor:pointer;" onclick="sysAdmin_30020_queryFun('+row.BLAID+')"/>&nbsp;&nbsp';
					}
				}
			}]], 
			toolbar:[{
					id:'btn_add',
					text:'导出',
					iconCls:'icon-query-export',
					handler:function(){
						sysAdmin_30020_export();
					}
				}]		
		});
	});
	//分配
	function sysAdmin_30020_queryFun(BLAID){
		$('<div id="sysAdmin_30021_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">填写信贷员姓名</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    href: '${ctx}/biz/credit/30021.jsp',
		    modal: true,
		    onLoad:function() {
		    	$('#blaid_30021').val(BLAID);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_30021_editForm').form('submit', {
						url:'${ctx}/sysAdmin/loanApplication_saveBusName.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_30020_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_30020_datagrid').datagrid('reload');
					    			$('#sysAdmin_30021_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_30021_editDialog').dialog('destroy');
					    			$('#sysAdmin_30020_datagrid').datagrid('reload');
					    			$('#sysAdmin_30020_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_30021_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//导出
	function sysAdmin_30020_export() {
		$('#sysAdmin_30020_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/loanApplication_exportLoan.action'
			    	});
	}
	//表单查询
	function sysAdmin_30020_searchFun10() {
		$('#sysAdmin_30020_datagrid').datagrid('load', serializeObject($('#sysAdmin_30020_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_30020_cleanFun10() {
		$('#sysAdmin_30020_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_30020_searchForm" method="post" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 170px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请人</th>
					<td><input name="applicant" style="width: 170px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间</th>
					<td><input name="cdate" class="easyui-datebox" data-options="editable:false" style="width: 170px;"/>
					</td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="A">未分配</option>
		    				<option value="Y">已分配</option>
		    			</select>
					</td>
				</tr> 
			
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_30020_searchFun10();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_30020_cleanFun10();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_30020_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

