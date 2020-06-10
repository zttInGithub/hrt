<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">

	function exportExcel20125(){
	     $("#check_dealdatailfrom_20125").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealData_listMerchantRebate_Excel.action'
			    	});
	}
	//查询提交
	function check_search_data(type){
	   	var xmh20125_txnDay = $('#xmh20125_txnDay').datebox('getValue');
	   	var xmh20125_txnDay1 = $('#xmh20125_txnDay1').datebox('getValue');
	   	var xmh20125_txnDate = $('#xmh20125_txnDate').datebox('getValue');
	   	var xmh20125_txnDate = $('#xmh20125_txnDate1').datebox('getValue');
	   	if(xmh20125_txnDay==null||xmh20125_txnDay==''||xmh20125_txnDay1==null||xmh20125_txnDay1==''){
	   		$.messager.alert('提示', "请输入出售起止日期！");
	   	}else if(xmh20125_txnDate==null||xmh20125_txnDate==''||xmh20125_txnDate1==null||xmh20125_txnDate1==''){
	   		$.messager.alert('提示', "请输入交易起止日期！");
	   	}else{
	   		$('#sysAdmin_check_dealdata20125').datagrid('load', serializeObject($('#check_dealdatailfrom_20125')));
	   	}
	}
		//清除表单内容
	function check_close_data() {
		$('#check_dealdatailfrom_20125 input').val('');
	}
	
	//验证
	function check_amount1_data(){
		var xmh20125_profit1=$("#xmh20125_profit1_data").val();
	     if(xmh20125_profit1!=null&&!xmh20125_profit1.match(/^\d+(\.\d+)?$/)&&xmh20125_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！");
	        $("#xmh20125_profit1_data").val('');
	    }
	}
	
	function check_amount_data(){
		var xmh20125_profit=$("#xmh20125_profit_data").val();
	    if(xmh20125_profit!=null&&!xmh20125_profit.match(/^\d+(\.\d+)?$/)&&xmh20125_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh20125_profit);
	         $("#xmh20125_profit_data").val('');
	    }
	}
	
	function check_amount_data_1(){
		var xmh20125_profitss=$("#xmh20125_txnDay").val();
      	$.messager.alert('提示', "您输入金额格式有误！"+xmh20125_profitss);
	}
	
	
	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_dealdata20125').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_listMerchantRebateCheckData.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MaintainDate',
			sortOrder: 'asc',
			idField : 'cuid',
			columns : [[{
				title : '对账总汇编号',
				field : 'cuid',
				width : 100,
				hidden : true
			},{
				title :'一代机构',
				field :'YIDAI',
				width : 80
			},{
				title :'机构编号',
				field :'UNNO',
				width : 80
			},{
				title :'商户编号',
				field :'HRT_MID',
				width : 130
			},{
				title :'SN号',
				field :'SN1',
				width : 110
			},{
				title :'型号',
				field :'MACHINEMODEL',
				width : 80
			},{
				title :'出售日期',
				field :'KEYCONFIRMDATE',
				width : 100
			},{
				title :'入网日期',
				field :'USEDCONFIRMDATE',
				width : 100
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100,
				hidden:true
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 80
			},{
				title :'商户名',
				field :'RNAME',
				width : 100
			},{
				title :'入账卡',
				field :'BANKACCNO',
				width : 100
			},{
				title :'入账人',
				field :'BANKACCNAME',
				width : 100
			},{
				title :'开户行',
				field :'BANKBRANCH',
				width : 100
			},{
				title :'行号',
				field :'PAYBANKID',
				width : 100
			}]],
			toolbar:[{
						text:'导出已查数据',
						iconCls:'icon-query-export',
						handler:function(){
							exportExcel20125();
						}
					}
			]	
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:85px; overflow: hidden; padding-top:20px;">
		<form id="check_dealdatailfrom_20125" style="padding-left:2%; ">
			<table class="tableForm" >
				<tr>
						<th>&nbsp;&nbsp;&nbsp;&nbsp;归属机构:</th>
						<td><input name="unno" style="width: 120px;"/>&nbsp;&nbsp;</td>
						<th>出售时间(零点):
						<input id="xmh20125_txnDay" class="easyui-datebox" data-options="editable:false" name="txnDay" style="width: 140px;"/>
						&nbsp;-&nbsp;
						<input id="xmh20125_txnDay1" class="easyui-datebox" data-options="editable:false" name="txnDay1" style="width: 140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</th>
						
						<td></td>
						<th> &nbsp;&nbsp;&nbsp;&nbsp;返利状态:</th>
						<td>
							<select name="txnType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:200px;">
								<option selected="selected" value="">所有</option>
								<option value="1">已返利</option>
								<option value="2">未返利</option>
								<option value="3">部分返利</option>
							</select>
						&nbsp;&nbsp;</td>
				</tr>
				<tr>
						<th>商户号:</th>
						<td><input id="xmh20125_mid" name="mid" style="width: 120px;"/>&nbsp;&nbsp;</td>
						<th>&nbsp;&nbsp;&nbsp;&nbsp;交易日期:&nbsp;&nbsp;&nbsp;&nbsp;
						<input  id="xmh20125_txnDate" class="easyui-datebox" data-options="editable:false" name="txnDate" style="width: 140px;"/>
						&nbsp;-&nbsp;
						<input id="xmh20125_txnDate1" class="easyui-datebox" data-options="editable:false" name="txnDate1" style="width: 140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>
				       <td colspan="5">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data();">清空</a>	
					</td>
			    </tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_check_dealdata20125" style="overflow: hidden;"></table>
     <!-- 
     <form action="${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDataExcel.action" id="exportformdata_xmh20125_data"></form>
   -->
    </div> 
</div>


