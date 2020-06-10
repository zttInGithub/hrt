<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20564_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkRebate_queryRebate12Detail.action',
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
				title :'一代机构',
				field :'YIDAI',
				width : 100,
				formatter : function(value,row,index) {
					if(value==""||value==null){
						return row.UNNO;
					}
				}
			},{
				title :'归属分公司',
				field :'YUNYING',
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'SN号',
				field :'SN',
				width : 100
			},{
				title :'型号',
				field :'MACHINEMODEL',
				width : 100
			},{
				title :'类别',
				field :'SN_TYPE',
				width : 100,
				formatter : function(value,row,index) {
					if(value=="1"){
						return "小蓝牙";
					}else{
						return "大蓝牙";
					}
				}
			},{
				title :'出售日期',
				field :'KEYCONFIRMDATE',
				width : 100
			},{
				title :'激活月',
				field :'USEDCONFIRMMONTH',
				width : 100
			},{
				title :'激活日期',
				field :'USEDCONFIRMDATE',
				width : 100
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 100
			},{
				title :'活动类型',
				field :'REBATETYPE',
				width : 100
			},{
				title :'返利月',
				field :'REBATEMONTH',
				width : 100
			},{
				title :'交易金额',
				field :'SAMT',
				width : 100
			},{
				title :'交易笔数',
				field :'NUM',
				width : 100
			}]]	,
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
				exportExcel_20564();
				                  }
			}]	
		});
	});
	function exportExcel_20564(){
		var txnDay = $('#txnDay_20564').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
		    $("#sysAdmin_20564_searchForm").form('submit',{
				url:'${ctx}/sysAdmin/checkRebate_exportRebate12Detail.action'
			});
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}
	//表单查询
	function sysAdmin_20564_searchFun80() {
		var txnDay = $('#txnDay_20564').datebox('getValue');
		if(txnDay!=null && txnDay!=''){
			$('#sysAdmin_20564_datagrid').datagrid('load', serializeObject($('#sysAdmin_20564_searchForm'))); 
		}else{
			$.messager.alert('提示','交易月不可为空！');
		}
	}

	//清除表单内容
	function sysAdmin_20564_cleanFun80() {
		$('#sysAdmin_20564_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20564_searchForm" style="padding-left:8%" method="post">
			<table class="tableForm" >
				<tr>
					<th>一代机构</th>
					<td>
						<input name="unno" class="easyui-validatebox" style="width:205px;"/>
					</td>
					<th>商户编号</th>
					<td>
						<input name="mid" class="easyui-validatebox" style="width:205px;"/>
					</td>
					<th>SN号</th>
					<td>
						<input name="sn" class="easyui-validatebox" style="width:205px;"/>
					</td>
					<th>交易月：</th>
					<td><input id="txnDay_20564" class="easyui-datebox" name="txnDay"  style="width: 162px;"/></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20564_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20564_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20564_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


