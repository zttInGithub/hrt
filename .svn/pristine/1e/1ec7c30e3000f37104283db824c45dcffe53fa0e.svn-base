<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function exportExcel_2015(type){
	     $("#check_dealdatailfrom_data_2015").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealDetail_listCheckUnitDealDetailNoClosingExcel.action'
			    	});
	}
	//查询提交
	function check_search_2015(type){
	    var txnDay = $('#xmh_txnDay').datebox('getValue');
    var txnDay1=$('#xmh_txnDay1').datebox('getValue');
      var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
      var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
	 if(((end-start)/(24*60*60*1000))>7){
	 	$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    7   天！");
	    }else{
	    $('#sysAdmin_check_unitdealdetail_2015').datagrid('load', serializeObject($('#check_dealdatailfrom_data_2015')));
	}
	}
		//清除表单内容
	function check_close_20150() {
		$('#check_dealdatailfrom_data_2015 input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_unitdealdetail_2015').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_listCheckUnitDealDetailNoClosing.action',
		fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MAINTAINDATE',
			sortOrder: 'asc',
			idField : 'BDID',
			columns : [[{
				title : '待结算对账编号',
				field : 'BDID',
				width : 100,
				hidden : true
			},{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100
			},{
				title :'终端号',
				field :'TID',
				width : 100
			},{
				title :'授权号',
				field :'AUTHCODE',
				width : 90
			},{
				title :'交易日期',
				field :'TXNDAY',
				width : 100
			},{
				title :'交易时间',
				field :'TXNDATE',
				width : 100
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100
			},{
				title :'手续费',
				field :'MDA',
				width : 100
			},{
				title :'结算金额',
				field :'MNAMT',
				width : 100
			},{
				title :'预计结算日期',
				field :'SCHEDULESETTLEDATE',
				width : 100
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExcel_2015();
					                  }
				}]			
		});
	});
	
	

</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:5px;">
		<form id="check_dealdatailfrom_data_2015" style="padding-left:15%">
			<table class="tableForm" >
				<tr>
						<th>商户名称：</th>
						<td><input id="xmh_rname" name="rname" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<th>商户号：</th>
						<td><input id="xmh_mid" name="mid" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					    <th>授权号：</th>
						<td><input id="xmh_authCode" class="easyui-validatebox" name="authCode" style="width: 156px;"/></td>
					
				</tr>
				<tr>
						<th>交易日期：</th>
						<td><input id="xmh_txnDay" onblur="check_amount_data_1()" class="easyui-datebox" data-options="editable:false,required:true" name="txnDay" style="width: 162px;"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;-</td>
						<td><input id="xmh_txnDay1" class="easyui-datebox" data-options="editable:false,required:true" name="txnDay1" style="width: 162px;"/></td>
					<td></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_2015();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_20150();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
   <table id="sysAdmin_check_unitdealdetail_2015" style="overflow: hidden;"></table>
  </div> 
</div>
