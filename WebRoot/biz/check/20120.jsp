<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">

	function exportExceldataxmh(){
	     $("#check_dealdatailfrom_data").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDataExcel.action'
			    	});
	}
		//查询提交
	function check_search_20120(type){
		var xmh_txnDay = $('#20120_xmh_txnDay').datebox('getValue');
		var xmh_txnDay1 = $('#20120_xmh_txnDay1').datebox('getValue');
		if(xmh_txnDay==null||xmh_txnDay==''||xmh_txnDay1==null||xmh_txnDay1==''){
			$.messager.alert('提示', "请输入查询起止日期！");
		}else{
			$('#check_dealdatailfrom_data').form('submit',{
	    		url:'${ctx}/sysAdmin/checkUnitDealData_queryCheckUnitDate.action',
				success:function(data) {
					var result = $.parseJSON(data);
	    			if (result.sessionExpire) {
	    				window.location.href = getProjectLocation();
		    		} else {
		    			if (result.success) {
		    			   		$('#sysAdmin_check_dealdata').datagrid('load', serializeObject($('#check_dealdatailfrom_data')));
			    		} else {
			    			$.messager.alert('提示', "日期输入有误，请从 "+result.msg+" 起，开始查询");
			    		}
			    	}
	    		 }
	    	});
		}
	}
		//清除表单内容
	function check_close_20120() {
		$('#check_dealdatailfrom_data input').val('');
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
	var xmh_profitss=$("#20120_xmh_txnDay").val();
	     
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
		$('#sysAdmin_check_dealdata').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealData.action',
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
				title :'机构编号',
				field :'UNNO',
				width : 100
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'商户类型',
				field :'ISM35',
				width : 100,
				formatter:function(value,row,index){
					if(value==1&&row.SETTMETHOD=='000000'){ 
						return '理财'; 
					}else if(value==1&&(row.SETTMETHOD=='100000'||row.SETTMETHOD=='200000')){
						return '秒到';
					}else if(value==0||value==2||value==3||value==4||value==5){
						return '传统';
					}
				} 
			},{
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
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExceldataxmh();
					                  }
				}]
				/* view: detailview,
			detailFormatter:function(index,row){
				return '<div id="detailForm2-'+index+'" ></div>';
			},
			onExpandRow: function(index,row){
				 $('#detailForm2-'+index).datagrid({
					url:'${ctx}/sysAdmin/checkUnitDealData_find.action?mid='+row.MID+'&txnDate='+$('#xmh_txnDay').datebox('getValue')+'&txnDate1='+$('#xmh_txnDay1').datebox('getValue'),
					singleSelect:true,
					rownumbers:true,
					loadMsg:'',
					height:'auto',
					columns:[[
					   //     {field:'TXNDAY',title:'时间',width:100},
					        {field:'UN_NAME',title:'机构名称',width:100},
					        {field:'TID',title:'终端号',width:100},
					        {field:'TXNCOUNT',title:'交易笔数',width:100},
							{field:'TXNAMOUNT',title:'交易金额',width:100},
							{field:'MDA',title:'交易手续费',width:100},
							{field:'REFUNDCOUNT',title:'退款笔数',width:100},
							{field:'REFUNDAMT',title:'退款金额',width:100},
							{field:'REFUNDMDA',title:'退款手续费',width:100},
							{field:'MNAMT',title:'结算金额',width:150}
						]],
					onResize:function(){
							$('#sysAdmin_check_dealdata').datagrid('fixDetailRowHeight',index);
					},
					onLoadSuccess:function(){
							setTimeout(function(){
								$('#sysAdmin_check_dealdata').datagrid('fixDetailRowHeight',index);
							},0);
						}
						
					});
					$('#sysAdmin_check_dealdata').datagrid('fixDetailRowHeight',index);
					$('.datagrid-view2').children('div.datagrid-body').css('position','relative');
				} */		
		});
	});
	
	
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="check_dealdatailfrom_data" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
						<th>机构号：</th>
						<td><select id="xmh_unno" name="unno" class="easyui-combogrid" style="width:100px;"></select>&nbsp;&nbsp;</td>
						<th>商户名称：</th>
						<td><input id="xmh_rname" name="rname" style="width: 156px;"/>&nbsp;&nbsp;</td>
						<th>商户号：</th>
						<td><input id="xmh_mid" name="mid" style="width: 120px;"/>&nbsp;&nbsp;</td>
						<th>商户类型：</th>
						<td>
							<select name="txnType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:100px;">
								<option selected="selected" value="">所有</option>
								<option value="1">传统</option>
								<option value="2">理财</option>
								<option value="3">秒到</option>
							</select>
						&nbsp;&nbsp;</td>
						<th>交易日期：</th>
						<td><input id="20120_xmh_txnDay" class="easyui-datebox" data-options="editable:false" name="txnDay" style="width: 100px;"/></td>
						<td>&nbsp;-&nbsp;</td>
						<td><input id="20120_xmh_txnDay1" class="easyui-datebox" data-options="editable:false" name="txnDay1" style="width: 100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 
				</tr>
				
				<tr>
				      <td></td><td></td><td></td><td></td>
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
     <table id="sysAdmin_check_dealdata" style="overflow: hidden;"></table>
     <!-- 
     <form action="${ctx}/sysAdmin/checkUnitDealData_listCheckUnitDealDataExcel.action" id="exportformdata_xmh_data"></form>
   -->
    </div> 
</div>


