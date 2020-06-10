<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	function exportExcel_tranficcbank(){
		var txnDay = $('#dateone_20180').datebox('getValue');
    	var txnDay1=$('#datetwo_20180').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay==null||txnDay==""){
	      	$.messager.alert('提示', "请选择清算起始日期 ！");
	    }
        else if(txnDay1==null||txnDay1==""){
       	 	$.messager.alert('提示', "请选择清算结束日期 ！");
        }
	    else if(((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
	      $("#check_incomehh").form('submit',{
			   url:'${ctx}/sysAdmin/checkIncome_CheckIncomeListExport.action'
		  });
	    }
	}
	//查询提交
	function check_search_data_tranf(){
	 	var txnDay = $('#dateone_20180').datebox('getValue');
    	var txnDay1=$('#datetwo_20180').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay==null||txnDay==""){
	      	$.messager.alert('提示', "请选择清算起始日期 ！");
	    }
        else if(txnDay1==null||txnDay1==""){
       	 	$.messager.alert('提示', "请选择清算结束日期 ！");
       }
	   else if(((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	   }
	   else{
			 $('#checkIncomelistrr').datagrid('load', serializeObject($('#check_incomehh')));
	   }
	}
		//清除表单内容
	function check_close_data_tranf() {
		$('#check_incomehh input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#checkIncomelistrr').datagrid({
			url :'${ctx}/sysAdmin/checkIncome_CheckIncomeList.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MID',
			sortOrder: 'MID',
			idField : 'MID',
			columns : [[{
				title :'商户编号',
				field :'MID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 150
			},{
				title :'结算日期',
				field :'SETTLEDAY',
				width : 100
			},{
				title :'交易日期',
				field :'TXNDAY',
				width : 130
			},{
				title :'交易金额',
				field :'TOTSAMT',
				width : 100
			},{
				title :'商户手续费',
				field :'TOTMFEE',
				width : 100
			},{
				title :'应结算金额',
				field :'SHOUCOUNT',
				width : 100
			},{
				title :'退货扣款',
				field :'TOTRAMT',
				width : 100
			},{
				title :'结算调整',
				field :'TOTAAMT',
				width : 100
			},{
				title :'实结金额',
				field :'TOTMNAMT',
				width : 100
			},{
				title :'附言 ',
				field :'MINFO1',
				width : 200,
				formatter : function(value,row,index) {
					if(value!='null'){
						return value;
					}
				}
			},{
				title :'结算状态',
				field :'STATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value==0){
						return '处理中';
					}else if(value==1){
						return '成功';	
					}else if(value==2){
						return '失败';
					}else{
					    return '已入账';
					}
				}
			},{
				title :'失败原因',
				field :'FAILMSG',
				width : 100,
				formatter : function(value,row,index) {
					if(value!='null'){
						return value;
					}
				}
			},{
				title :'备注 ',
				field :'REMARKS',
				width : 100,
				formatter : function(value,row,index) {
					if(value!='null'){
						return value;
					}
				}
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExcel_tranficcbank();
					                  }
				}]			
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:5px;">
		<form id="check_incomehh" style="padding-left:15%">
				<table class="tableForm" >
				<tr>
						<th>商户号：</th>
						<td><input name="mid" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					 
				</tr>
				<tr>
				<th>清算日期：</th>
						<td><input name="dateone" id="dateone_20180" class="easyui-datebox" data-options="editable:false" style="width: 162px;"/>
						</td>
						<td>&nbsp;&nbsp;-&nbsp;&nbsp;</td>
						<td><input name="datetwo" id="datetwo_20180" class="easyui-datebox" data-options="editable:false" style="width: 162px;"/>
						</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_tranf();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_tranf();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="checkIncomelistrr" style="overflow: hidden;"></table>
  </div> 
</div>