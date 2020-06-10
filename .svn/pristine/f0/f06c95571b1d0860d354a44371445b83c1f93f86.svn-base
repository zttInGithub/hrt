<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">

	function exportExcel20123(){
		var xmh20123_txnDay = $('#xmh20123_txnDate').datebox('getValue');
	   	var xmh20123_txnDay1 = $('#xmh20123_txnDate1').datebox('getValue');
	   	var xmh20123_txnDayStart=xmh20123_txnDay.replace("-", "/").replace("-", "/");
        var xmh20123_txnDayEnd=xmh20123_txnDay1.replace("-", "/").replace("-", "/");
        if(xmh20123_txnDay==null||xmh20123_txnDay==''||xmh20123_txnDay1==null||xmh20123_txnDay1==''){
	   		$.messager.alert('提示', "请输入返利起止日期！");
	   	}else if(xmh20123_txnDayStart.substring(0, 7) != xmh20123_txnDayEnd.substring(0, 7)){
	   		$.messager.alert('提示', '返利日期必须在同一自然月');
	   	}else{
  			$("#check_dealdatailfrom_20123").form('submit',{
	    		url:'${ctx}/sysAdmin/checkUnitDealData_listIsM35RebateCheckData_Excel1.action'
	    	});
	    }
	}
	//查询提交
	function check_search_data(){
	   	var xmh20123_txnDay = $('#xmh20123_txnDate').datebox('getValue');
	   	var xmh20123_txnDay1 = $('#xmh20123_txnDate1').datebox('getValue');
	   	var xmh20123_txnDayStart=xmh20123_txnDay.replace("-", "/").replace("-", "/");
        var xmh20123_txnDayEnd=xmh20123_txnDay1.replace("-", "/").replace("-", "/");
        if(xmh20123_txnDay==null||xmh20123_txnDay==''||xmh20123_txnDay1==null||xmh20123_txnDay1==''){
	   		$.messager.alert('提示', "请输入返利起止日期！");
	   	}else if(xmh20123_txnDayStart.substring(0, 7) != xmh20123_txnDayEnd.substring(0, 7)){
	   		$.messager.alert('提示', '返利日期必须在同一自然月');
	   	}else{
	   		$('#sysAdmin_check_dealdata20123').datagrid('load', serializeObject($('#check_dealdatailfrom_20123')));
	   	}
	}
		//清除表单内容
	function check_close_data() {
		$('#check_dealdatailfrom_20123 input').val('');
	}
	
	//验证
	function check_amount1_data(){
		var xmh20123_profit1=$("#xmh20123_profit1_data").val();
	     if(xmh20123_profit1!=null&&!xmh20123_profit1.match(/^\d+(\.\d+)?$/)&&xmh20123_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！");
	         $("#xmh20123_profit1_data").val('');
	    }
	}
	function check_amount_data(){
	var xmh20123_profit=$("#xmh20123_profit_data").val();
	     if(xmh20123_profit!=null&&!xmh20123_profit.match(/^\d+(\.\d+)?$/)&&xmh20123_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh20123_profit);
	         $("#xmh20123_profit_data").val('');
	    }
	}
		function check_amount_data_1(){
	var xmh20123_profitss=$("#xmh20123_txnDay").val();
	     
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh20123_profitss);
	}
	//初始化datagrid
	$(function() {
			$('#xmh20123_unno').combogrid({
			//url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes3.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
			$('#rebateType_20123').combogrid({
				url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=3',
				idField:'VALUEINTEGER',
				textField:'VALUEINTEGER',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'NAME',title:'返利名称',width:150},
					{field:'VALUEINTEGER',title:'类型',width:100}
				]]
			});
	});
	
	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_dealdata20123').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_listIsM35RebateCheckData1.action',
			fit : true,
			fitColumns : true,
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
				title :'最终机构号',
				field :'UNNO',
				width : 80
			},{
				title :'最终机构名称',
				field :'UN_NAME',
				width : 130
			},{
				title :'一代机构号',
				field :'YIDAI',
				width : 80
			},{
				title :'一代机构名称',
				field :'YIDAINAME',
				width : 130
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
				title :'活动类型',
				field :'REBATETYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value==null||value==''||value=='0'){
						return '无';
					}else if (value==1){
					   return "1";
					}else if (value==2){
					   return "2";
					}else if (value==3){
					   return "3";
					}else if (value==4){
					   return "4";
					}else if (value==5){
					   return "5";
					}else if (value==6){
					   return "6";
					}else if (value==7){
					   return "7";
					}else if(value==8){
						return '活动8';
					}else if(value==9){
						return '买断9';
					}else if(value==10){
						return '活动10';
					}else if(value==11){
						return '活动11';
					}else if(value==12){
						return '活动12';
					}else if(value==13){
						return '活动13';
					}else if(value==14){
						return '活动14';
					}else if(value==15){
						return '活动15';
					}else if(value==16){
						return '活动16';
					}else if(value==17){
						return '活动17';
					}else if(value==18){
						return '活动18';
					}else{
					   return "活动"+value;
					}
				}
			},{
				title :'返利日期',
				field :'REBATEDATE',
				width : 80
			},{
				title :'返利金额',
				field :'REBATEAMT',
				width : 80
			},{
				title :'返利导入时间',
				field :'CDATE',
				width : 150
			}]],
			toolbar:[{
					text:'导出已查数据',
					iconCls:'icon-query-export',
					handler:function(){
					exportExcel20123();
					                  }
				}]	
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:85px; overflow: hidden; padding-top:20px;">
		<form id="check_dealdatailfrom_20123" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
						<th>最终机构号：</th>
						<td><input  name="unno"  style="width:100px;"/>&nbsp;&nbsp;</td>
						<th>商户号：</th>
						<td><input id="xmh20123_mid" name="mid" style="width: 120px;"/>&nbsp;&nbsp;</td>
						<!--
						<th>返利状态：</th>
						<td>
							<select name="txnType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:100px;">
								<option selected="selected" value="">所有</option>
								<option value="1">已返利</option>
								<option value="2">未返利</option>
							</select>
						&nbsp;&nbsp;</td>
						 -->
						<th>活动类型：</th>
						<td>
							<select id="rebateType_20123" name="cbrand"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 100px;"></select>
						&nbsp;&nbsp;</td>
						<!--
						<th>出售/绑定时间(零点)：</th>
						<td colspan="3"><input id="xmh20123_txnDay" class="easyui-datebox" data-options="editable:false" name="txnDay" style="width: 140px;"/>
						&nbsp;-&nbsp;
						<input id="xmh20123_txnDay1" class="easyui-datebox" data-options="editable:false" name="txnDay1" style="width: 140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>交易日期：</th>
						<td colspan="3"><input id="xmh20123_txnDate" class="easyui-datebox" data-options="editable:false" name="txnDate" style="width: 140px;"/>
						&nbsp;-&nbsp;
						<input id="xmh20123_txnDate1" class="easyui-datebox" data-options="editable:false" name="txnDate1" style="width: 138px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						-->
						<th>返利日期：</th>
						<td colspan="3"><input id="xmh20123_txnDate" class="easyui-datebox" data-options="editable:false" name="txnDate" style="width: 140px;"/>
						&nbsp;-&nbsp;
						<input id="xmh20123_txnDate1" class="easyui-datebox" data-options="editable:false" name="txnDate1" style="width: 138px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				        <td colspan="2">
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
     <table id="sysAdmin_check_dealdata20123" style="overflow: hidden;"></table>
     <!-- 
     <form action="${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDataExcel.action" id="exportformdata_xmh20123_data"></form>
   -->
    </div> 
</div>


