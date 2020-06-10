<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!-- 业务员下代理开通商户数 -->
<script type="text/javascript">
		//初始化datagrid
		$('#sysAdmin_merchantList10970_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_queryYwyMerchantinfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'joinConfirmDate',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				title : '机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '机构名称',
				field : 'UN_NAME',
				width : 100
			},{
				title :'开通手刷商户数',
				field :'SSMER',
				width : 80,
				sortable :true
			},{
				title :'开通收银台商户数',
				field :'SYTMER',
				width : 80,
				sortable :true
			},{
				title :'开通传统商户数',
				field :'CTMER',
				width : 80,
				sortable :true
			},{
				title :'业务员',
				field :'SALENAME',
				width : 80
			}]],
			toolbar:[{
				id:'btn_run',
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo10970_exportFun();
				}
			}]
		});
	
	//导出
	function sysAdmin_merchantterminalinfo10970_exportFun() {
		var txnDay = $('#createDateStart_10970').datebox('getValue');
    	var txnDay1= $('#createDateEnd_10970').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_merchant10970_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportYwyMerchantinfo.action'
			});
		}
	}
	
	//表单查询
	function sysAdmin_merchant_searchFun10970() {
		var txnDay = $('#createDateStart_10970').datebox('getValue');
    	var txnDay1= $('#createDateEnd_10970').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_merchantList10970_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchant10970_searchForm'))); 
		}
	}

	//清除表单内容
	function sysAdmin_merchant_cleanFun10970() {
		$('#sysAdmin_merchant10970_searchForm input').val('');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchant10970_searchForm" style="padding-left:15%" method="post">
			<table class="tableForm" >
				<tr>
					<th>开通日期</th>
					<td><input name="createDateStart" id="createDateStart_10970" class="easyui-datebox" data-options="editable:false" style="width: 120px;"/>&nbsp;至&nbsp;
						<input name="createDateEnd" id="createDateEnd_10970" class="easyui-datebox" data-options="editable:false" style="width: 120px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;机构号</th>
					<td><input name="unno" style="width: 120px;" /></td>
					<td colspan="8" style="text-align: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchant_searchFun10970();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchant_cleanFun10970();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantList10970_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


