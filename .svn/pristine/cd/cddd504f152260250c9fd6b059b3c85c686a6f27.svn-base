<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,java.sql.*"
    pageEncoding="UTF-8"%>
<%
	Calendar calendar = Calendar.getInstance();
	int YY = calendar.get(Calendar.YEAR);
    int MM = calendar.get(Calendar.MONTH)+1;
    int DD = calendar.get(Calendar.DATE);
    String mms ="";
    String dds ="";
    if(MM<10){
    	mms="0"+MM;
    }else{
    	mms=MM+"";
    }
    if(DD<10){
    	dds="0"+DD;
    }else{
    	dds=DD+"";
    }
    String timeDate=YY+""+mms+""+dds;
%>
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20412_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkMisTake_querMisTakeInfo.action?orderType=2',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'dpid',
			sortOrder: 'desc',
			idField : 'dpid',
			columns : [[{
				field : 'dpid',
				checkbox:true
			},{
				title : '通知日期',
				field : 'inportDate',
				width : 70
			},{
				title : '商户编号',
				field : 'mid',
				width : 120
			},{
				title : '商户名称',
				field : 'rname1',
				width : 100
			},{
				title :'交易卡号',
				field :'cardNo',
				width : 120,
				sortable :true
			},{
				title :'交易金额',
				field :'samt',
				width : 70,
				sortable :true
			},{
				title :'交易日期',
				field :'transDate',
				width : 60,
				sortable :true
			},{
				title :'最终回复日期',
				field :'finalDate',
				width : 80
			},{
				title :'银联备注',
				field :'bankRemarks',
				width : 80,
				sortable :true,
				hidden:true
			},{
				title :'参考号',
				field :'rrn',
				width : 90
			},{
				title :'商户注册店名',
				field :'rname',
				width : 100
			},{
				title :'营业地址',
				field :'raddr',
				width : 70,
				sortable :true
			},{
				title :'联系电话',
				field :'contactPhone',
				width : 70
			},{
				title :'联系人',
				field :'contactPerson',
				width : 70
			},{
				title :'代理回复备注',
				field :'agRemarks',
				width : 80,
				sortable :true
			},{
				title :'实际回复日',
				field :'maintainDate',
				width : 80,
				hidden:true
			},{
				title :'类型',
				field :'orderType',
				width : 80,
				hidden:true
			},{
				title :'机构号',
				field :'unno',
				width : 80,
				hidden:true
			},{
				title :'原因码',
				field :'reason',
				width : 50,
				hidden:false
			},{
				title :'终端号',
				field :'tid',
				width : 80,
				hidden:true
			},{
				title :'商户类型',
				field :'isM35',
				width : 80,
				formatter : function(value,row,index) {
					if (value=='1'){
					   return "手刷";
					}else{
						return "非手刷";
					}
				}
			},{
				title :'回复状态',
				field :'status',
				width : 60,
				formatter : function(value,row,index) {
						//处理状态  1:已回复；0：未回复
						if (value=='1'){
						   return "已回复";
						}else if(value=='0'){
							return "未回复";
						}
					}
			},{
				title :'操作',
				field :'operation',
				width : 50,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_20412_datagrid').datagrid('getData').rows[index];
					var now =<%=timeDate %>;
					if(rowData.status=='0'&&rowData.finalDate>=now){
						return '<img src="${ctx}/images/start.png" title="回复" style="cursor:pointer;" onclick="sysAdmin_20412_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;';
					}else if(rowData.status=='1'){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_20412_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;';
					}
			}
			}]],toolbar:[{
					text:'导出查询所有',
					iconCls:'icon-query-export',
					handler:function(){
						exportExceldata();
					}
				}],	
		});
	});
	
	//回复
	function sysAdmin_20412_editFun(index) {
		
		//获取操作对象
		var rows = $('#sysAdmin_20412_datagrid').datagrid('getRows');
		var row = rows[index];
        var mid='-1';
        if(''!=row.mid.replace(/\s+/g,"")){
            mid=row.mid;
        }
		$('<div id="sysAdmin_20412_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">回复信息</span>',
			width: 800,
		    height:550,
		    closed: false,
		    href: '${ctx}/biz/check/20413.jsp?dpid='+row.dpid+'&mid='+mid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20412_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.dpid);    
		    	$('#sysAdmin_20413_editForm').form('load', row);
			},
			buttons:[{
				text:'确认回复',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_20413_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkMisTake_updateDispatchOrder.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20412_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20412_datagrid').datagrid('reload');
					    			$('#sysAdmin_20412_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20412_editDialog').dialog('destroy');
					    			$('#sysAdmin_20412_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_20412_editDialog').dialog('destroy');
					$('#sysAdmin_20412_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20412_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//导出EXCEL
   function exportExceldata(){
	     $("#sysAdmin_20412_searchForm").form('submit',{
			    		url:'${ctx}/sysAdmin/checkMisTake_querMisTakeInfoExport.action?orderType=2'
			    	});
	}
	
	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun20412() {
		$('#sysAdmin_20412_datagrid').datagrid('load', serializeObject($('#sysAdmin_20412_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun20412() {
		$('#sysAdmin_20412_searchForm input').val('');
		$('#sysAdmin_20412_searchForm select').val('全部');
	}
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_20412_searchForm" style="padding-left:5%" method="post">
		<input type="hidden" id="bmatids" name="bmatids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号:</th>
					<td><input name="mid" style="width: 100px;" /></td>
					<td width="10px"> </td>
					<th>交易参考号:</th>
					<td><input name="rrn" style="width: 100px;" /></td>
					
					
					<td width="10px"> </td>
					<th>通知时间:</th>
					<td><input name="txnDay" class="easyui-datebox" style="width: 100px;" />&nbsp;至&nbsp;
					<input name="txnDayEnd" class="easyui-datebox" style="width: 100px;" /></td>
					<td width="20px"></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun20412();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun20412();">清空</a>	
					</td>
				</tr>
				<tr>
					<th>交易卡号:</th>
					<td><input name="cardNo" style="width: 100px;" /></td>
					<td width="10px"> </td>
					<th>回复状态:</th>
					<td><select  name="status" style="width: 105px;" >
							<option value="">ALL</option>
							<option value="1">已回复</option>
							<option value="0">未回复</option>
						</select>
					</td>
					<td width="10px"> </td>
					<th>是否手刷:</th>
					<td><select  name="isM35" style="width: 105px;" >
							<option value="">ALL</option>
							<option value="1">手刷</option>
							<option value="0">非手刷</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20412_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


