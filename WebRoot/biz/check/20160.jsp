<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20160_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealData_searchToolDealDatas.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			singleSelect:true,
	        striped: true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'TXNAMOUNT',
			sortOrder: 'DESC',
			idField : 'CUID',
			columns : [[{
				title : 'CUID',
				field : 'CUID',
				width : 100,
				hidden : true
			},{
				title :'机构编号',
				field :'UNNO',
				width : 100,
				sortable:true
			},{
				title :'终端号',
				field :'TID',
				width : 100,
				sortable:true
			},{
				title :'商户名称',
				field :'RNAME',
				width : 100,
				sortable:true
			},{
				title :'REFUNDCOUNT',
				field :'REFUNDCOUNT',
				width : 100,
				sortable:true,
				hidden : true
			},{
				title :'REFUNDAMT',
				field :'REFUNDAMT',
				width : 100,
				sortable:true,
				hidden : true
			},{
				title :'交易笔数',
				field :'TXNCOUNT',
				width : 100,
				sortable:true
			},{
				title :'结算金额',
				field :'MNAMT',
				width : 100,
				sortable:true
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 100,
				sortable:true
			},{
				title :'店名',
				field :'INSTALLEDNAME',
				width : 100,
				sortable:true
			}]],
			view: detailview,
			detailFormatter:function(index,row){
				return '<div><table class="ddvv"></table></div>';
			},
			onExpandRow: function(index,row){
				var ddv = $(this).datagrid('getRowDetail',index).find('table.ddvv');
				ddv.datagrid({
					url:'${ctx}/sysAdmin/checkUnitDealData_findToolDealDatas.action?tid='+row.TID+'&firsix='+$('#fir').val()+'&lastfour='+$('#las').val()+'&txnDate='+$('#xtxnDay').datebox('getValue')+'&txnDate1='+$('#xtxnDay1').datebox('getValue'),
					singleSelect:true,
					rownumbers:true,
					loadMsg:'',
					height:'auto',
					columns:[[
						{field:'MID',title:'商户编号',width:100},
						{field:'TID',title:'终端编号',width:100},
						{field:'CARDPAN',title:'卡号',width:100},
						{title :'卡别',field :'CBRAND',width:100,
						formatter:function(value,row,index){if(value=='1'){	return '借记卡';	}else if(value=='2'){return '贷记卡';}else{return '未知';}}}, 
						{field:'TXNDAY',title:'交易日期',width:100},
						{field:'TXNDATE',title:'交易时间',width:100},
						{field:'STAN',title:'流水号',width:100},
						{field:'AUTHCODE',title:'授权码',width:100},
						{field:'MDA',title:'手续费',width:100},
						{field:'MNAMT',title:'交易净额',width:100}
					]],
					onResize:function(){
						$('#sysAdmin_20160_datagrid').datagrid('fixDetailRowHeight',index);
					},
					onLoadSuccess:function(){
						setTimeout(function(){
							$('#sysAdmin_20160_datagrid').datagrid('fixDetailRowHeight',index);
						},0);
					}
				});
				$('#sysAdmin_20160_datagrid').datagrid('fixDetailRowHeight',index);
				$('.datagrid-view2').children('div.datagrid-body').css('position','relative');
			},
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_checkUnitDealData_exportFun();
				}
			}]	
		}); 
	});
	
	//资料导出
	function sysAdmin_checkUnitDealData_exportFun() {
		var txnDay = $('#xtxnDay').datebox('getValue');
	    var txnDay1= $('#xtxnDay1').datebox('getValue');
		if(txnDay==null||txnDay==""){
			$.messager.alert('提示', "请选择起始日期 ！");
		}else if(txnDay1==null||txnDay1==""){
			$.messager.alert('提示', "请选择结束日期 ！");
		}else{    
			$('#sysAdmin_20160_searchForm').form('submit',{url:'${ctx}/sysAdmin/checkUnitDealData_exportxxx.action'});
		}
	}

	//表单查询
	function sysAdmin_20160_searchFun() {
	var rnames=$('#id').val();
	var txnDay = $('#xtxnDay').datebox('getValue');
    var txnDay1= $('#xtxnDay1').datebox('getValue');
       if(rnames==null||rnames==""){
       $.messager.alert('提示', "商户号不能为空！");
       }
       else if(txnDay==null||txnDay==""){
        $.messager.alert('提示', "请选择起始日期 ！");
       }
       else if(txnDay1==null||txnDay1==""){
        $.messager.alert('提示', "请选择结束日期 ！");
       }
       else{    
		$('#sysAdmin_20160_datagrid').datagrid('load', serializeObject($('#sysAdmin_20160_searchForm')));
		 }
	}

	//清除表单内容
	function sysAdmin_20160_cleanFun() {
		$('#sysAdmin_20160_searchForm input').val('');
	}
	
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:110px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_20160_searchForm" style="padding-left:10%">
			<input type="hidden" id="ids" name="ids" />
			<table class="tableForm" >
				<tr>
					<th>商户号：</th>
					<td><input id="id" name="mid" style="width: 316px;" /></td>
				</tr>
				<tr>
				<th>交易卡号：</th>
					<td><input id="fir" name="firsix"   style="width: 150px;"/>&nbsp;-&nbsp;
						<input id="las" name="lastfour"   style="width: 150px;"/>
					</td>					
				</tr>
				<tr>
				<th>交易日期：</th>
					<td><input id="xtxnDay" name="txnDayStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input id="xtxnDay1" name="txnDayEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20160_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20160_cleanFun();">清空</a>	
					</td>
				</tr>		
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_20160_datagrid"></table>
    </div> 
</div>

