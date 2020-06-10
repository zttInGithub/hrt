<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">

	function exportMerExceldataxmh(){
	     $("#check_wechatdealdatailfrom_data").form('submit',{
			    		url:'${ctx}/sysAdmin/checkWechatTxnDetail_checkWechatTxnDetailExcelAll.action'
			    	});
	}
		//查询提交
	function check_search_20114(type){
		var txnDay = $('#20114_xmh_txnDay').datebox('getValue');
    	var txnDay1= $('#20114_xmh_txnDay1').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if((txnDay!=""&&txnDay1=="")||(txnDay1!=""&&txnDay=="")){
        	$.messager.alert('提示', "请选择开始日期 和 截止日期 ");
        	return;
        }
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
		    $('#sysAdmin_check_wechatdealdata').datagrid('load', serializeObject($('#check_wechatdealdatailfrom_data')));
		}
	}
		//清除表单内容
	function check_close_20114() {
		$('#check_wechatdealdatailfrom_data input').val('');
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
	var xmh_profitss=$("#20114_xmh_txnDay").val();
	     
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh_profitss);
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_wechatdealdata').datagrid({
			url :'${ctx}/sysAdmin/checkWechatTxnDetail_listCheckMerWechatdealData.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'unno',
			sortOrder: 'asc',
			idField : 'pwid',
			columns : [[{
				field : 'pwid',
				checkbox:true
			},{
				title : '机构号',
				field : 'UNNO',
				width : 70
				//hidden : true
			},{
				title :'商户编号',
				field :'MID',
				width : 120
				//hidden : true
			},{
				title :'商户名称',
				field :'RNAME',
				width : 180
			},{
				title :'渠道',
				field :'FIINFO1',
				width : 50
				/* formatter : function(value,row,index) {
						// 支付渠道(10：微信；11：支付宝)
						if (value=='10'){
						   return "微信";
						}else if(value=='11'){
							return "支付宝";
						}else if(value=='18'){
							return "立码付";
						}
					} */
			},{
				title :'消费笔数',
				field :'TXNCOUNT',
				width : 60
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'交易手续费',
				field :'MDA',
				width : 100
			},{
				title :'退款笔数',
				field :'REFUNDCOUNT',
				width : 60
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
					text:'导出查询所有',
					iconCls:'icon-query-export',
					handler:function(){
					exportMerExceldataxmh();
					                  }
				}]
					
		});
	});
	//初始化datagrid
	$(function() {
			$('#unno20114').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodesForSales.action',
			//url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="check_wechatdealdatailfrom_data" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
						<th>机构:</th>
						<td><select name="unno" id="unno20114" class="easyui-combogrid" style="width:100px;"></select>&nbsp;&nbsp;</td>
						<th>商户号:</th>
						<td><input id="xmh_mid" name="mid" style="width: 156px;"/>&nbsp;&nbsp;</td>
						<th>商户类型:</th>
						<td>
							<select name="isM35" style="width: 100px;">
								<option value="" selected="selected">全部</option>
								<option value="6">聚合</option>
								<option value="0">线下</option>
							</select>
						</td>
						<th>&nbsp;&nbsp;交易日期:</th>
						<td><input id="20114_xmh_txnDay" class="easyui-datebox" data-options="editable:false" name=cdateStart style="width: 120px;"/></td>
						<td>&nbsp;-&nbsp;</td>
						<td><input id="20114_xmh_txnDay1" class="easyui-datebox" data-options="editable:false" name="cdateEnd" style="width: 120px;"/>&nbsp;&nbsp;</td>
				       <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_20114();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_20114();">清空</a>	
					</td>
			    </tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_check_wechatdealdata" style="overflow: hidden;"></table>
     <!-- 
     <form action="${ctx}/sysAdmin/checkUnitwechatdealData_listCheckUnitwechatdealDataExcel.action" id="exportformdata_xmh_data"></form>
   -->
    </div> 
</div>


