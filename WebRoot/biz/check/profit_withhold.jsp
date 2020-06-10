<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
			$('#unno_profit_withhold').combogrid({
				url : '${ctx}/sysAdmin/checkProfitwithholdAction_queryyidai.action',
				idField:'UNNO',
				textField:'UN_NAME',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'UN_NAME',title:'机构名称',width:150}			
				]]
			});
		$('#sysAdmin_profit_withhold_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkProfitwithholdAction_queryWithholdInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title :'申请方',
				field :'YUNYINGZHONGXINNAME',
				width : 100
			},{
                title :'一代机构号',
                field :'EXECUTINGUNNO',
                width : 100
            },{
                title :'一代机构名称',
                field :'EXECUTINGUNNONAME',
                width : 100
            },{
				title :'代扣金额',
				field :'WITHHOLDINGAMOUNT',
				width : 100
			},{
				title :'扣款来源',
				field :'DEDUCTIONSOURCE',
				width : 100,
				formatter : function(value,row,index) {
					if(value == null){
						return '一代分润';
					}else{
						return value;
					}
				}
			},{
				title :'提交时间',
				field :'MAINTAINDATE',
				width : 100
			},{
				title :'生效月',
				field :'EFFECTIVEDATE',
				width : 100
			},{
				title :'状态',
				field :'APPROVESTATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 1 && row.FLAG == 2){
						return '待确认';
					}else if(value == 1 && row.FLAG == 1){
						return '已过期未确认';
					}else if(value == 2){
						return '已驳回';
					}else{
						return '已确认';
					}
				}
			},{
				title :'归属中心机构号',
				field :'APPLICANTUNNO',
				width : 100
			}/* ,{
				title :'归属中心名称',
				field :'YUNYINGZHONGXINNAME',
				width : 100
			} */,{
				title :'一代操作确认',
				field :'ISYIDAI',
				width : 100,
				formatter : function(value,row,index) {
					if(value == 1 && row.FLAG == 2 && row.APPROVESTATUS == 1){
						return '<img src="${ctx}/images/start.png" title="确认" style="cursor:pointer;" onclick="sysAdmin_profit_withhold_editFun_true('+row.ID+')"/>&nbsp;&nbsp'+ 
						'<img src="${ctx}/images/close.png" title="驳回" style="cursor:pointer;" onclick="sysAdmin_profit_withhold_editFun_flase('+row.ID+')"/>';
					}else{
						return null;
					}
				}
			}]],
			toolbar:[{
				id:'btn_add',
				text:'添加代扣',
				iconCls:'icon-add',
				handler:function(){
					Add_profit_withhold();
				}
			},{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_profit_withhold();
			}}]	
		});
	});
	//添加模版
	function Add_profit_withhold(){
		//debugger;
		$('<div id="profit_withhold_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">代扣数据输入</span>',
			width: 400,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/check/profit_withhold_add.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_profit_withhold_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/checkProfitwithholdAction_addprofitWithholdInfo.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_profit_withhold_datagrid').datagrid('reload');
					    			$('#profit_withhold_addDialog').dialog('destroy');
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
					$('#profit_withhold_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	function exportExcel_profit_withhold(){
		$("#sysAdmin_profit_withhold_searchForm").form('submit',{
    		url:'${ctx}/sysAdmin/checkProfitwithholdAction_profitWithholdExport.action'
    	});
	}
	//表单查询
	function sysAdmin_profit_withhold_searchFun80() {
		$('#sysAdmin_profit_withhold_datagrid').datagrid('load', serializeObject($('#sysAdmin_profit_withhold_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_profit_withhold_cleanFun80() {
		$('#sysAdmin_profit_withhold_searchForm input').val('');
	}
	
	//一代驳回
	function sysAdmin_profit_withhold_editFun_flase(index){
			$.messager.confirm('确认','不认可中心扣款要求,确认驳回',function(result) {
				if (result) {
					$.ajax({
						url:"${ctx}/sysAdmin/checkProfitwithholdAction_yidaiProfitWithholdEdit?flag=2",
						type:'post',
						data:{"id":index},
						dataType:'json',
						success:function(data, textStatus, jqXHR) {
							if (data.success) {
								$.messager.show({
									title : '提示',
									msg : data.msg
								});
								$('#sysAdmin_profit_withhold_datagrid').datagrid('reload');
								$('#sysAdmin_profit_withhold_datagrid').datagrid('unselectAll');
							} else {
								$.messager.alert('提示', data.msg);
								$('#sysAdmin_profit_withhold_datagrid').datagrid('unselectAll');
							}
						},
						error:function() {
							$.messager.alert('提示', '驳回扣款出错！');
							$('#sysAdmin_profit_withhold_datagrid').datagrid('unselectAll');
						}
					});
				}
			});
	}
	
	//一代确认
	function sysAdmin_profit_withhold_editFun_true(index){
			$.messager.confirm('确认','认可中心扣款要求,确认',function(result) {
				if (result) {
					$.ajax({
						url:"${ctx}/sysAdmin/checkProfitwithholdAction_yidaiProfitWithholdEdit?flag=3",
						type:'post',
						data:{"id":index},
						dataType:'json',
						success:function(data, textStatus, jqXHR) {
							if (data.success) {
								$.messager.show({
									title : '提示',
									msg : data.msg
								});
								$('#sysAdmin_profit_withhold_datagrid').datagrid('reload');
								$('#sysAdmin_profit_withhold_datagrid').datagrid('unselectAll');
							} else {
								$.messager.alert('提示', data.msg);
								$('#sysAdmin_profit_withhold_datagrid').datagrid('unselectAll');
							}
						},
						error:function() {
							$.messager.alert('提示', '确认扣款失败！');
							$('#sysAdmin_profit_withhold_datagrid').datagrid('unselectAll');
						}
					});
				}
			});
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:110px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_profit_withhold_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>一代机构编号</th>
					<td>
						<!-- <select name="executingUnno" id="executingUnno_profit_withhold" class="easyui-combogrid" style="width:120px;"></select> -->
						<input type="text" name="executingUnno" id="executingUnno"  style="width:150px;"/></td>
					</td>
					<th>一代机构名称</th>
				   	<td ><input type="text" name="executingUnnoName" id="executingUnnoName"  style="width:150px;"/></td>
					<th>归属中心机构号</th>
					<td>
						<!-- <select name="applicantUnno" id="applicantUnno_profit_withhold" class="easyui-combogrid" style="width:120px;"></select> -->
						<input type="text" name="applicantUnno" id="applicantUnno"  style="width:120px;"/></td>
					</td>
				</tr>
				<tr>
					<th>生效月</th>
				   	<td >
				   		<input type="text" name="txnMoth1" id="txnMoth1"  style="width:100px;"/>&nbsp;至&nbsp;
				   		<input type="text" name="txnMoth2" id="txnMoth2"  style="width:100px;"/>
				   	</td>
				   	<th>申请日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_profit_withhold" name="txnDay"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_profit_withhold" name="txnDay1"  style="width: 162px;"/></td>
				   	<th>状态：</th>
				   	<td><select  name="approveStatus" style="width: 80px;" >
							<option value="">ALL</option>
							<option value="1">未确认</option>
							<option value="2">已驳回</option>
							<option value="3">已确认</option>
							<option value="4">过期未确认</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_profit_withhold_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_profit_withhold_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_profit_withhold_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


