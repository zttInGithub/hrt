<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--代理分润管理-MPOS活动提现分润汇总--%>
<script>
	$(function(){
		$(function(){
			$('#rebateType_20309').combogrid({
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

		$("#check_transferProfit_20309").datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_queryTransferAndGetCashPlus.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'UNNO',
			sortOrder: 'UNNO',
			idField : 'UNNO',
			columns : [[{
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
				title :'机构名称',
				field :'UNNAME',
				width : 100
			}/*,{
				title :'交易金额',
				field :'CASHAMT',
				width : 100
			}*/,{
				title :'活动类型',
				field :'MINFO1',
				width : 100
			},{
				title :'交易类型',
				field :'SETTMETHOD',
				width : 100,
				formatter : function(value,row,index) {
					if(value==40){
						return '刷卡';
					}else if(value==41){
						return '微信0.38';
					}else if(value==42){
						return '微信0.45';
					}else if(value==43){
						return '微信（老）';
					}else if(value==44){
						return '支付宝';
					}else if(value==45){
						return '二维码';
					}else if(value==46){
						return '扫码1000以上（终端0.38）';
					}else if(value==47){
						return '扫码1000以上（终端0.45）';
					}else if(value==48){
						return '扫码1000以下（终端0.38）';
					}else if(value==49){
						return '扫码1000以下（终端0.45）';
					}else if(value==50){
						return '银联二维码';
					}else if(value==51){
						return '花呗';
					}else if(value == -1){
						return '汇总';
					}else{
						return '扫码';
					}
				}
			},{
				title :'提现笔数',
				field :'COUNT',
				width : 100
			},{
				title :'提现手续费',
				field :'SECONDRATE',
				width : 100
			},{
				title :'提现转账分润',
				field :'PROFIT',
				width : 100,
				formatter : function(value,row,index) {
					if (value==null){
					   return "0";
					}else{
						return value;
					}
				}
			}]], 
			toolbar:[{
				text:'导出Excel',
				iconCls:'icon-query-export',
				handler:function(){
					exportExcel_exportFirstAgentTransferAndGetCashPlus();
				}
			}]
		});
	
	});
	function exportExcel_exportFirstAgentTransferAndGetCashPlus(){
		var txnDay = $('#txnDay_20309').datebox('getValue');
   		var txnDay1=$('#txnDay1_20309').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31 || txnDay.substring(0,7)!=txnDay1.substring(0,7)){
	 		$.messager.alert('提示', "开始日期和截止日期之差不能超过31天,且为同一月！");
	    }else{
			$('#check_transferProfit_20309Form').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealDetail_exportFirstAgentTransferAndGetCashPlus.action'});
		}
	}
	
	function check_search_data_firstAgentTransferPlus(){
		var txnDay = $('#txnDay_20309').datebox('getValue');
   		var txnDay1=$('#txnDay1_20309').datebox('getValue');
	    var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
	    var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 	if(((end-start)/(24*60*60*1000))>31 || txnDay.substring(0,7)!=txnDay1.substring(0,7)){
	 		$.messager.alert('提示', "开始日期和截止日期之差不能超过31天,且为同一月！");
	    }else{
			$("#check_transferProfit_20309").datagrid('load',serializeObject($("#check_transferProfit_20309Form")));
		}
		
	}
	
	function check_close_data_firstAgentTransferPlus(){
		$('#check_transferProfit_20309Form input').val('');
		$('#check_transferProfit_20309Form select').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="overflow:hidden; height:60px; padding-top:15px;" >
		<form id="check_transferProfit_20309Form" style="padding-left: 3%">
			<table>
				<tr>
					<th>活动类型</th>
					<td>
						<select id="rebateType_20309" name="maintainType" class="easyui-combogrid" data-options="validType:'spaceValidator'" style="width:135px;"></select>
					</td>
					<th>交易起始日期</th>
					<td>
						<input id="txnDay_20309" class="easyui-datebox" name="txnDay" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<th>交易结束日期</th>
					<td>
						<input  id="txnDay1_20309" class="easyui-datebox" name="txnDay1" data-options="editable:false,required:true" style="width:155px">
					</td>
					<td style="width: 15px"></td>
					<th>交易类型</th>
					<td>
						<select name="txnType" style="width: 180px;">
							<option value="" selected="selected">ALL</option>
							<option value="46">扫码1000以上（终端0.38）</option>
							<option value="47">扫码1000以上（终端0.45）</option>
							<option value="48">扫码1000以下（终端0.38）</option>
							<option value="49">扫码1000以下（终端0.45）</option>
							<option value="50">银联二维码</option>
							<option value="40">刷卡(不含云闪付)</option>
							<option value="41">微信0.38</option>
							<option value="42">微信0.45</option>
							<option value="43">微信（老）</option>
							<option value="44">支付宝</option>
							<option value="45">二维码</option>
						</select>
					</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_firstAgentTransferPlus();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_firstAgentTransferPlus();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="check_transferProfit_20309" style="overflow:hidden"></table>
	</div>
</div>