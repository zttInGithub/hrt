<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$("#parentUnitName").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes',
			valueField : 'UNNO',
			textField : 'UN_NAME',
			onSelect:function(rec){
				$("#parentUnitName").val(rec.UNNO);
				$('#sysAdmin_merchantList106301_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchant106301_searchForm')));
			}
		});
		$('#sysAdmin_merchantList106301_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMicroMerchantInfoY.action',
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
				field : 'bmid',
				checkbox:true
			},{
				title : '机构名称',
				field : 'unitName',
				width : 120
			},{
				title :'商户编号',
				field :'mid',
				width : 130,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'商户审批时间',
				field :'approveDate',
				width : 100,
				sortable :true
			},{
				title :'商户入网时间',
				field :'joinConfirmDate',
				width : 100,
				sortable :true
			},{
			    title :'受理状态',
				field :'approveStatusName',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				hidden : true,
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantList10630_queryFun('+index+','+row.bmid+','+row.mid+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_pencil.png" title="查看终端" style="cursor:pointer;" onclick="sysAdmin_10630_queryFun('+index+')"/>';
				}
			}]],
			toolbar:[/**{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo10630_exportFun();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected10630_exportFun();
				}
			}**/]
		});
		$('#machineModel1').combogrid({
			url : '${ctx}/sysAdmin/machineInfo_searchNormalMachineInfo.action',
			idField:'bmaID',
			textField:'machineModel',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'bmaID',title:'主键',width:150,hidden:true},
				{field:'machineModel',title:'机具型号',width:150},
				{field:'machineTypeName',title:'机具类型',width:150}
			]]
		});
	});
	
	function sysAdmin_merchantterminalinfo10630_exportFun() {
		var txnDay = $('#createDateStart_106301').datebox('getValue');
    	var txnDay1= $('#createDateEnd_106301').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_merchant106301_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMicroMer.action'
			});
		}
	}
	
	function sysAdmin_merchantterminalinfoSelected10630_exportFun() {
		var checkedItems = $('#sysAdmin_merchantList106301_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmids_630").val(bmids);
		$('#sysAdmin_merchant106301_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMicroMerSelected.action'
			    	});
	}
	
	//查看明细
	function sysAdmin_merchantList10630_queryFun(index,bmid,mid){
		$('<div id="sysAdmin_merchantList10630_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10484.jsp?bmid='+bmid+'&mid='+mid,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantList106301_datagrid').datagrid('getRows');
				var row = rows[index];
				row.bankAccNo=row.bankAccNo.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
				var first = row.secondRate.substring(0,1);
				if(first=="\.")row.secondRate="0"+row.secondRate;
                if(row.contactPhone!=""&&row.contactPhone!=null && row.contactPhone.length==11){
                    row.contactPhone=row.contactPhone.substring(0,3)+'****'+row.contactPhone.substring(row.contactPhone.length-4,row.contactPhone.length);
                }
		    	$('#sysAdmin_merchantList_queryForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantList10630_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantList106301_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_merchant_searchFun10630() {
		var txnDay = $('#createDateStart_106301').datebox('getValue');
    	var txnDay1= $('#createDateEnd_106301').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_merchantList106301_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchant106301_searchForm'))); 
		}
	}

	//清除表单内容
	function sysAdmin_merchant_cleanFun10630() {
		$('#sysAdmin_merchant106301_searchForm input').val('');
	}
	
	//查看终端
	function sysAdmin_10630_queryFun(index){
		var rows = $('#sysAdmin_merchantList106301_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10630_run"/>').dialog({
			title: '查看终端',
			width: 1100,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10487.jsp?mid='+encodeURIComponent(row.mid),  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchant106301_searchForm" style="padding-left:10%" method="post">
		<input type="hidden" id="bmids_630" name="bmids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 190x;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;审批时间</th>
					<td><input name="createDateStart" id="createDateStart_106301" class="easyui-datebox" data-options="editable:false" style="width: 90px;"/>&nbsp;至&nbsp;
						<input name="createDateEnd" id="createDateEnd_106301" class="easyui-datebox" data-options="editable:false" style="width: 90px;"/>
					</td>
					<td colspan="8" style="text-align: center;">&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchant_searchFun10630();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchant_cleanFun10630();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantList106301_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


