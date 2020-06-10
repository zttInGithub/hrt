<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

  //导出EXCEL
   function exportExceldatartf01(){
	     $("#check_dealdatailfrom_data_gen01").form('submit',{
			    		url:'${ctx}/sysAdmin/checkRealtime_listRealTimeDataExcel01.action'
			    	});
	}

		//查询提交
	function check_search_data_gen(type){
	    var txnDay = $('#xmh_txnDay').datebox('getValue');
        var txnDay1= $('#xmh_txnDay1').datebox('getValue');
        var txnDay_ = txnDay;
        var txnDay1_=txnDay1;
        var start=new Date(txnDay_.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1_.replace("-", "/").replace("-", "/")); 
		if(txnDay==null||txnDay==""||txnDay1==null||txnDay1==""||((end-start)/(24*60*60*1000))<=7){
	        $('#sysAdmin_check_dealdata_gen01').datagrid('load', serializeObject($('#check_dealdatailfrom_data_gen01')));
	    }else{
	    $.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    7   天！");
	    }
	}
		//清除表单内容
	function check_close_data_gen(){
		$('#check_dealdatailfrom_data_gen01 input').val('');
	}
	
	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_dealdata_gen01').datagrid({
			url :'${ctx}/sysAdmin/checkRealtime_queryRealTimeDate01.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MID',
			sortOrder: 'asc',
			idField : 'PKID',
			columns : [[{
				title :'交易日期',
				field :'TXNDAY',
				width : 90
			},{
				title :'交易时间',
				field :'TXNDATE',
				width : 90
			},{
				title :'商户编号',
				field :'MID',
				width : 140
			},{
				title :'卡号',
				field :'CARDPAN',
				width : 140
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 80
			},{
				title :'终端号',
				field :'TID',
				width : 90
			},{
				title :'交易种类',
				field :'TXNTYPE',
				width : 100,
				formatter:function(value,row,index){
					if(value=='0210'){
						return '正常';
					}else if(value=='0230'){
						return '退货';
					}else{
						return "冲正";
					}
				}
			},{
				title :'授权号',
				field :'AUTHCODE',
				width : 100
			},{
				title :'流水号',
				field :'STAN',
				width : 90
			},{
				title :'系统参考号',
				field :'RRN',
				width : 90
			},{
				title :'处理码',
				field :'PROCCODE', 
				width : 90,
				formatter:function(value,row,index){
				if(value=='000000'){
					return '消费';
				}else if(value=='200000'){
					return '撤销';
				}else{
					return '查余额';
				}
			}
			},{
				title :'响应码',
				field :'RESPCODE',
				width : 140
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExceldatartf01();
					                  }
				}],	
		});
	});
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="check_dealdatailfrom_data_gen01" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
				     <th>商户编号：</th>
						<td><input id="xmh_mid_gen"  class="easyui-validatebox" name="mid" style="width: 156px;"/></td>
					      <th>卡号：</th>
						<td><input id="xmh_card_gen"  class="easyui-validatebox" name="cardpan" style="width: 156px;"/></td>
					     <th>终端编号：</th>
						<td><input id="xmh_tid_gen"  class="easyui-validatebox" name="tid" style="width: 156px;"/></td>
				        <td>
					    <th>交易日期：</th>
						<td><input id="xmh_txnDay" class="easyui-datebox" data-options="editable:false" name="txnday" style="width: 162px;"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input id="xmh_txnDay1" class="easyui-datebox" data-options="editable:false" name="txnday1" style="width: 162px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
				      <td></td><td></td><td></td>
				       <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_gen();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_gen();">清空</a>	
					</td>
			    </tr>
					</td>
			    </tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_check_dealdata_gen01" style="overflow: hidden;"></table>
    </div> 
</div>