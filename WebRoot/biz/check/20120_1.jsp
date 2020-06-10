<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">

	function exportExceldataxmh(){
	     $("#check_dealdatailfrom201201_data").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDataExcel.action'
			    	});
	}
		//查询提交
	function check_search_20120(type){
	   	var xmh_txnDay = $('#201201_xmh_txnDay').datebox('getValue');
	   	var xmh_txnDay1 = $('#201201_xmh_txnDay1').datebox('getValue');
	   	var start=new Date(xmh_txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(xmh_txnDay1.replace("-", "/").replace("-", "/"));
	   	if(xmh_txnDay==null||xmh_txnDay==''||xmh_txnDay1==null||xmh_txnDay1==''){
	   		$.messager.alert('提示', "请输入查询起止日期！");
	   	}else{
	  	 	if(((end-start)/(24*60*60*1000))>=7){
	 			$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过7天！");
	   	 	}else{
		   		$('#sysAdmin_check201201_dealdata').datagrid('load', serializeObject($('#check_dealdatailfrom201201_data')));
	   	 	}
	   	}
	}
		//清除表单内容
	function check_close_20120() {
		$('#check_dealdatailfrom201201_data input').val('');
	}
	
	//验证
	function check_amount1_data(){
		var xmh_profit1=$("#xmh_profit1_data").val();
	     if(xmh_profit1!=null&&!xmh_profit1.match(/^\d+(\.\d+)?$/)&&xmh_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！");
	         $("#xmh_profit1_data").val('');
	    }
	}
	function check_amount_data(){
	var xmh_profit=$("#xmh_profit_data").val();
	     if(xmh_profit!=null&&!xmh_profit.match(/^\d+(\.\d+)?$/)&&xmh_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh_profit);
	         $("#xmh_profit_data").val('');
	    }
	}
		function check_amount_data_1(){
	var xmh_profitss=$("#201201_xmh_txnDay").val();
	     
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh_profitss);
	}
	//初始化datagrid
	$(function() {
			$('#xmh_unno').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	});
	
	//初始化datagrid
	$(function() {
		$('#sysAdmin_check201201_dealdata').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDatabaodan.action',
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
				title :'交易日期',
				field :'TXNDAY',
				width : 100,
				hidden : true
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},/**{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},**/{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'交易手续费',
				field :'MDA',
				width : 100
			},		
			{
				title :'退款笔数',
				field :'REFUNDCOUNT',
				width : 100
			},{
				title :'退款金额',
				field :'REFUNDAMT',
				width : 100
			},{
				title :'退款手续费',
				field :'REFUNDMDA',
				width : 100
			},
			{
				title :'结算金额',
				field :'MNAMT',
				width : 100
			}]],
			toolbar:[/**{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExceldataxmh();
					                  }
				}**/]
		});
	});
	
	
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="check_dealdatailfrom201201_data" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
						<th>商户号：</th>
						<td><input id="xmh_mid" name="mid" style="width: 120px;"/>&nbsp;&nbsp;</td>
						<th>交易日期：</th>
						<td><input id="201201_xmh_txnDay" class="easyui-datebox" data-options="editable:false" name="txnDay" style="width: 100px;"/></td>
						<td>&nbsp;-&nbsp;</td>
						<td><input id="201201_xmh_txnDay1" class="easyui-datebox" data-options="editable:false" name="txnDay1" style="width: 100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				       <td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_20120();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_20120();">清空</a>	
					</td>
			    </tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_check201201_dealdata" style="overflow: hidden;"></table>
     <!-- 
     <form action="${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDataExcel.action" id="exportformdata_xmh_data"></form>
   -->
    </div> 
</div>


