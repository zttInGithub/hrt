<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Date date=new Date();
%>
<script type="text/javascript">

	function exportExceldataxmh(){
	     $("#check_wechatunitdealdatailfrom_data").form('submit',{
			    		url:'${ctx}/sysAdmin/checkWechatTxnDetail_checkWechatTxnUnitDetailExcelAll.action'
			    	});
	}
		//查询提交
	function check_search_data(type){
		var txnDay = $('#xmh_txnDay').datebox('getValue');
    	var txnDay1= $('#xmh_txnDay1').datebox('getValue');
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
		    $('#sysAdmin_check_wechatunitdealdata').datagrid('load', serializeObject($('#check_wechatunitdealdatailfrom_data')));
		}
	}
		//清除表单内容
	function check_close_data() {
		$('#check_wechatunitdealdatailfrom_data input').val('');
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
	var xmh_profitss=$("#xmh_txnDay").val();
	     
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh_profitss);
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_wechatunitdealdata').datagrid({
			url :'${ctx}/sysAdmin/checkWechatTxnDetail_listCheckUnitwechatunitdealData.action',
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
				field : 'YIDAI',
				width : 70
				//hidden : true
			},{
				title : '机构名称',
				field : 'UN_NAME',
				width : 70
				//hidden : true
			},{
				title :'渠道',
				field :'FIINFO1',
				width : 30
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
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 60
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'活跃商户数',
				field :'HOTMERCOUNT',
				width : 100
			}]],
			toolbar:[{
					text:'导出查询所有',
					iconCls:'icon-query-export',
					handler:function(){
					exportExceldataxmh();
					                  }
				}],	
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="check_wechatunitdealdatailfrom_data" style="padding-left:3%" method="post">
			<table class="tableForm" >
				<tr>
						<th>机构号:</th>
						<td><input id="xmh_unno" name="unno" style="width: 100px;"/>&nbsp;&nbsp;</td>
						<th>&nbsp;&nbsp;商户类型:</th>
						<td>
							<select name="isM35" style="width: 100px;">
								<option value="" selected="selected">全部</option>
								<option value="6">聚合</option>
								<option value="0">线下</option>
							</select>
						</td>
						<th>&nbsp;&nbsp;交易类型:</th>
						<td>
							<select name="trantype" style="width: 100px;">
								<option value="0" selected="selected">全部</option>
								<option value="8">快捷</option>
								<option value="1">扫码</option>
							</select>
						</td>
						<th>&nbsp;&nbsp;交易日期:</th>
						<td><input id="xmh_txnDay" class="easyui-datebox" data-options="editable:false" name=cdateStart style="width: 120px;"/></td>
						<td>&nbsp;-&nbsp;</td>
						<td><input id="xmh_txnDay1" class="easyui-datebox" data-options="editable:false" name="cdateEnd" style="width: 120px;"/>&nbsp;&nbsp;</td>
				       <td>
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
     <table id="sysAdmin_check_wechatunitdealdata" style="overflow: hidden;"></table>
     <!-- 
     <form action="${ctx}/sysAdmin/checkUnitwechatunitdealData_listCheckUnitwechatunitdealDataExcel.action" id="exportformdata_xmh_data"></form>
   -->
    </div> 
</div>


