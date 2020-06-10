<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--活动成本审核--%>
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_00326_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listRebateRateForW.action?status=0',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'HUCID',
			sortOrder: 'desc',
			idField : 'HUCID',
			columns : [[{
				title : 'ID',
				field : 'HUCID',
				width : 100,
				hidden:true
			},{
				title : '机构编号',
				field : 'UNNO',
				width : 100
			},{
				title : '机构名称',
				field : 'UN_NAME',
				width : 100
			},{
				title : '活动类型',
				field : 'TXN_DETAIL',
				width : 100
			},{
				title : '状态',
				field : 'STATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value==0){
						return "待审核";
					}else if(value==1){
						return "已通过";
					}else if(value == 2){
						return "退回";
					}else {
						return "";
					}
				}
			},{
				title : '刷卡成本(%)',
				field : 'DEBIT_RATE',
				width : 100
			},{
				title :'刷卡提现转账手续费',
				field :'DEBIT_FEEAMT',
				width : 100
			},{
				title :'扫码1000以下（终端0.38）费率(%)',
				field :'CREDIT_RATE',
				width : 100
			},{
				title :'扫码1000以下（终端0.38）转账费',
				field :'CASH_COST',
				width : 100
			},{
				title :'扫码1000以上（终端0.38）费率(%)',
				field :'WX_UPRATE',
				width : 100
			},{
				title :'扫码1000以上（终端0.38）转账费',
				field :'WX_UPCASH',
				width : 100
			},{
				title :'扫码1000以上（终端0.45）费率(%)',
				field :'WX_UPRATE1',
				width : 100
			},{
				title :'扫码1000以上（终端0.45）转账费',
				field :'WX_UPCASH1',
				width : 100
			},{
				title :'扫码1000以下（终端0.45）费率(%)',
				field :'ZFB_RATE',
				width : 100
			},{
				title :'扫码1000以下（终端0.45）转账费',
				field :'ZFB_CASH',
				width : 100
			},{
				title :'银联二维码费率(%)',
				field :'EWM_RATE',
				width : 100
			},{
				title :'银联二维码转账费',
				field :'EWM_CASH',
				width : 100
			},{
				title :'云闪付费率(%)',
				field :'YSF_RATE',
				width : 100
			},{
				title :'花呗费率(%)',
				field :'HB_RATE',
				width : 100
			},{
				title :'花呗转账费',
				field :'HB_CASH',
				width : 100
			},{
				title :'申请时间',
				field :'LMDATE',
				width : 100
			},{
				title :'操作时间',
				field :'CDATE',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.STATUS==0){
						return '<img src="${ctx}/images/start.png" title="通过" style="cursor:pointer;" onclick="sysAdmin_00326_goFun('+row.HUCID+')"/>&nbsp;&nbsp;'+
						'<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_00326_backFun('+row.HUCID+')"/>&nbsp;&nbsp;';	
					}
				}
			}]]	
		});
	});

	//表单查询
	function sysAdmin_00326_searchFun() {
		$('#sysAdmin_00326_datagrid').datagrid('load', serializeObject($('#sysAdmin_00326_searchForm')));
	}

	//清除表单内容
	function sysAdmin_00326_cleanFun() {
		$('#sysAdmin_00326_searchForm input').val('');
	}
	
	//通过
	function sysAdmin_00326_goFun(hucid){
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_updateUnnoRebateRateGo.action',
			type:'post',
			data:{hucid:hucid},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					$('#sysAdmin_00326_datagrid').datagrid('unselectAll');
    				$('#sysAdmin_00326_datagrid').datagrid('reload');
	    			$.messager.show({
						title : '提示',
						msg : data.msg
					});
				} else {
					$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
	    			$('#sysAdmin_00326_datagrid').datagrid('unselectAll');
	    			$.messager.alert('提示', data.msg);
				}
			},
			error:function() {
				$.messager.alert('提示', '通过记录出错！');
				$('#sysAdmin_00326_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//退回
	function sysAdmin_00326_backFun(hucid){
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_updateUnnoRebateRateBack.action',
			type:'post',
			data:{hucid:hucid},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					$('#sysAdmin_00326_datagrid').datagrid('unselectAll');
    				$('#sysAdmin_00326_datagrid').datagrid('reload');
	    			$.messager.show({
						title : '提示',
						msg : data.msg
					});
				} else {
					$('#sysAdmin_agentTaskDetail_datagrid').dialog('destroy');
	    			$('#sysAdmin_00326_datagrid').datagrid('unselectAll');
	    			$.messager.alert('提示', data.msg);
				}
			},
			error:function() {
				$.messager.alert('提示', '通过记录出错！');
				$('#sysAdmin_00326_datagrid').datagrid('unselectAll');
			}
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_00326_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 130px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>申请时间</th>
					<td><input name="adate" style="width: 130px;"  class="easyui-datebox" data-options="editable:false"/></td>
					<th>-</th>
					<td><input name="zdate" style="width: 130px;"  class="easyui-datebox" data-options="editable:false"/></td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_00326_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_00326_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_00326_datagrid"></table>
    </div> 
</div>

