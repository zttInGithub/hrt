<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- MPOS活动分润汇总 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20287_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryMposProfit.action',
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
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
				title :'机构名称',
				field :'UN_NAME',
				width : 100
			},{
				title :'活动类型',
				field :'MINFO1',
				width : 100
			},{
				title :'交易类型',
				field :'TXNTYPE',
				width : 100,
				formatter:function(value,row,index){
					if(value==1){
						return '刷卡';
					}else if(value==4){
						return '云闪付';
					}else if(value==22){
						return '支付宝';
					}else if(value==33){
						return '银联二维码';
					}else if(value==41){
						return '微信0.38';
					}else if(value==42){
						return '微信0.45';
					}else if(value==43){
						return '微信(老)';
					}else if(value==44){
						return '扫码1000以上（终端0.38）';
					}else if(value==45){
						return '扫码1000以上（终端0.45）';
					}else if(value==46){
						return '扫码1000以下（终端0.38）';
					}else if(value==47){
						return '扫码1000以下（终端0.45）';
					}else if(value==48){
						return '花呗';
					}else{
						return '扫码';
					}
				}
			},{
				title :'交易总笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'交易总金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'商户手续费',
				field :'MDA',
				width : 100
			},{
				title :'手续费分润',
				field :'PROFIT',
				width : 100
			}]],
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_20287();
				}
			}]
		});
	});
	
	//数据导出
	function exportExcel_20287(){
		var txnDay = $('#txnDay_20287').datebox('getValue');
		var txnDay1 = $('#txnDay1_20287').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
		    	$('#sysAdmin_20287_searchForm').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportMposProfit.action'});
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}
	
	$(function(){
		$('#rebateType_20287').combogrid({
			url : '${ctx}/sysAdmin/agentunit_listRebateRate.action?status=plus',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]]
		});
	});
	
	//表单查询
	function sysAdmin_20287_searchFun80() {
		var txnDay = $('#txnDay_20287').datebox('getValue');
		var txnDay1 = $('#txnDay1_20287').datebox('getValue');
		if (txnDay != null && txnDay != '' && txnDay1 != null && txnDay1 != '') {
			if (txnDay.substring(0, 7) == txnDay1.substring(0, 7)) {
				$('#sysAdmin_20287_datagrid').datagrid('load', serializeObject($('#sysAdmin_20287_searchForm')));
			} else {
				$.messager.alert('提示', '交易日期必须在同一自然月');
			}
		} else {
			$.messager.alert('提示', '请选择交易日期');
		}
	}

	//清除表单内容
	function sysAdmin_20287_cleanFun80() {
		$('#sysAdmin_20287_searchForm input').val('');
		$('#sysAdmin_20287_searchForm select').val('');
	}
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20287_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<th>活动类型</th>
					<td>
						<select id="rebateType_20287" name="maintainType" class="easyui-combogrid" data-options="validType:'spaceValidator'" style="width:135px;"></select>
					</td>
					<td width="10px"></td>
					<th>交易日期：</th>
					<td><input  class="easyui-datebox" id="txnDay_20287" name="txnDay"  style="width: 162px;"/>至</td>
					<td><input  class="easyui-datebox" id="txnDay1_20287" name="txnDay1"  style="width: 162px;"/></td>
					<td width="10px"></td>
					<th>交易类型</th>
					<td>
						<select name="txnType" style="width: 180px;">
							<option value="" selected="selected">ALL</option>
							<option value="1">刷卡(不含云闪付)</option>
							<option value="4">云闪付</option>
							<option value="44">扫码1000以上（终端0.38）</option>
							<option value="45">扫码1000以上（终端0.45）</option>
							<option value="46">扫码1000以下（终端0.38）</option>
							<option value="47">扫码1000以下（终端0.45）</option>
							<option value="33">银联二维码</option>
							<option value="41">微信0.38</option>
							<option value="42">微信0.45</option>
							<option value="43">微信(老)</option>
							<option value="22">支付宝</option>
						</select>
					</td>
					<td colspan="5" style="text-align: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20287_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20287_cleanFun80();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20287_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


