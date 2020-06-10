<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

		function exportExcel_zerocountcheck(){
	      $("#check_zerocount").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealDetail_zerocountCheckExport.action'
			    	});
	}
	//查询提交
	function check_search_data_zero(){
		var txnDay = $('#xmh_txnDay_20190').datebox('getValue');
	    var txnDay1=$('#xmh_txnDay1_20190').datebox('getValue');
	    var txnDay_ = txnDay;
	    var txnDay1_=txnDay1;
	      var start=new Date(txnDay_.replace("-", "/").replace("-", "/"));  
	      var end=new Date(txnDay1_.replace("-", "/").replace("-", "/")); 
			if(txnDay==null||txnDay==""){
	          $.messager.alert('提示', "请选择起始日期 ！");
	       }
	       else if(txnDay1==null||txnDay1==""){
	        $.messager.alert('提示', "请选择结束日期 ！");
	       }
		 else if(((end-start)/(24*60*60*1000))>31){
		 	$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过  30   天！");
		    }
		  else{
		     $('#zerocountcheck').datagrid('load', serializeObject($('#check_zerocount')));
		    }
	}
		//清除表单内容
	function check_close_data_zero() {
		$('#check_zerocount input').val('');
	}

	//初始化datagrid
	$(function() {
		$('#zerocountcheck').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_zerocountChecklist.action',
			fit : true,
			fitColumns : true,
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
				width : 200
			},{
				title :'商户名称',
				field :'RNAME',
				width : 200
			},{
				title :'销售',
				field :'SALENAME',
				width : 200
			},{
				title :'交易总笔数',
				field :'ZEROCOUNT',
				width : 100
			},{
				title :'交易总金额',
				field :'ZEROMONCOUNT',
				width : 100
			},{
				title :'日期',
				field :'ZEROTIME',
				width : 100
			},{
				title :'归属地',
				field :'UN_NAME',
				width : 100
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExcel_zerocountcheck();
					                  }
				}]			
		});
	});
	
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:5px;">
		<form id="check_zerocount" style="padding-left:15%">
				<table class="tableForm" >
				<tr>
						<th>商户号：</th>
						<td><input name="mid" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					     <th>商户名称：</th>
						<td><input name="rname" style="width: 156px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>     
				</tr>
				<tr>
				<th>交易日期：</th>
						<td><input name="txnDay" id="xmh_txnDay_20190" class="easyui-datebox" data-options="editable:false" style="width: 162px;"/>
						</td>
						<td>&nbsp;&nbsp;-&nbsp;&nbsp;</td>
						<td><input name="txnDay1" id="xmh_txnDay1_20190" class="easyui-datebox" data-options="editable:false" style="width: 162px;"/>
						</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search_data_zero();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close_data_zero();">清空</a>	
					</td>
				</tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
    <table id="zerocountcheck" style="overflow: hidden;"></table>
  </div> 
</div>