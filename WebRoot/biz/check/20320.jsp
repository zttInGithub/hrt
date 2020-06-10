<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
     //导出EXCEL
   function check_20320_exportExcell(){
	     $("#check_20320_serchform").form('submit',{
			    		url:'${ctx}/sysAdmin/checkCash_listCashDataExcel.action'
			    	});
	}

		//查询提交
	function check_20320_searchFun(type){
	    $('#sysAdmin_20320_datagrid').datagrid('load', serializeObject($('#check_20320_serchform')));
	}
		//清除表单内容
	function check_20320_cleanFun() {
		$('#check_20320_serchform input').val('');
	}
	
	//初始化datagrid
	$(function() {
		$('#sysAdmin_20320_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkCash_queryCashListInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'PCID',
			sortOrder: 'desc',
			idField : 'PCID',
			columns : [[{
				title :'商户编号',
				field :'MID',
				width : 90
			},{
				title :'提现时间',
				field :'CASHDATE',
				width : 140
			},{
				title :'提现金额',
				field :'CASHAMT',
				width : 90
			},{
				title :'提现手续费',
				field :'CASHFEE',
				width : 90
			},{
				title :'提现状态',
				field :'CASHSTATUS',
				width : 90,
				formatter : function(value,row,index) {
					if(value==1){
					 return '提现中';
					}else if(value==2){
						return '审核通过';
					}else if(value==3){
						return '审核失败';
					}else if(value==4){
						return '已入账';
					}
				}
			}]],
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					check_20320_exportExcell();
					                  }
				}],	
		});
	});
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="check_20320_serchform" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
				     <th>商户编号：</th>
						<td><input id="mid"  class="easyui-validatebox" name="mid" style="width: 156px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提现时间</th>
					<td>
						<input name="cashDateStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="cashDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
			    	 <td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20320_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20320_cleanFun();">清空</a>	
					</td>
			    </tr>
				
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
     <table id="sysAdmin_20320_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>



