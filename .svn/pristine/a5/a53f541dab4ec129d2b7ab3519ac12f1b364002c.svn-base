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
		
		  $.messager.confirm('提示', '请在25天内上传请款资料，逾期将不能请款！', function(result) {
			if(result){
				$('#sysAdmin_20414_datagrid').datagrid({
					url :'${ctx}/sysAdmin/checkReOrder_queryReOrderInfo.action',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					nowrap : true,
					ctrlSelect:true,
					checkOnSelect:true,
					pageSize : 10,
					pageList : [ 10, 15 ],
					sortName: 'roid',
					sortOrder: 'desc',
					idField : 'roid',
					columns : [[{
						field : 'roid',
						checkbox:true
					},{
						title : '商户编号',
						field : 'mid',
						width : 100
					},{
						title : '终端编号',
						field : 'tid',
						width : 100
					},{
						title :'交易卡号',
						field :'cardPan',
						width : 110,
						sortable :true
					},{
						title :'交易日期',
						field :'txnDayStr',
						width : 60,
						sortable :true
					},{
						title :'原交易金额',
						field :'samt',
						width : 80
					},{
						title :'调账类型',
						field :'refundType',
						width : 50,
						sortable :true,
						formatter : function(value,row,index) {
								//处理状态  1:一次退单；2：二次退单
								if (value=='1'){
								   return "一次退单";
								}else if(value=='2'){
									return "二次退单";
								}
							}
					},{
						title :'参考号',
						field :'rrn',
						width : 90
					},{
						title :'原因码',
						field :'refundCode',
						width : 40,
						hidden:false
					},{
						title :'银联备注',
						field :'remarks',
						width : 120,
						hidden:false
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
						title :'状态',
						field :'status',
						width : 40,
						formatter : function(value,row,index) {
								//处理状态  1:已回复；0：未回复
								if (value=='1'){
								   return "已回复";
								}else if(value=='0'){
									return "未回复";
								}else if(value == '2'){
									return "已退回";
								}else if(value='3'){
									return "已通过";
								}
							}
					},{
						title :'回复日期',
						field :'reOrderUpload',
						width : 120,
						 formatter : function(value,row,index) {
						 		if(value != null){
						 			value = value.replace(/-/g, "");
						 			return value.substr(0, 8);
						 		}
							} 
					},{
						title :'退单时间',
						field :'refundDate',
						width : 120,
						hidden:false
					},{
						title : '受理描述',
						field : 'processContext',
						width : 100,
						hidden:true
					},{
						title :'操作',
						field :'operation',
						width : 50,
						align : 'center',
						formatter : function(value,row,index) {
							var rowData = $('#sysAdmin_20414_datagrid').datagrid('getData').rows[index];
							//var now =<%=timeDate %>;
							if(rowData.status=='0'||rowData.status=='2'){
								return '<img src="${ctx}/images/start.png" title="回复" style="cursor:pointer;" onclick="sysAdmin_20414_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;';
							}else if(rowData.status=='1'||rowData.status=='3'){
								return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_20414_editFun2('+index+')"/>&nbsp;&nbsp;&nbsp;';
							}
					}
					}]],toolbar:[{
							text:'导出查询所有',
							iconCls:'icon-query-export',
							handler:function(){
								exportExceldatare();
							}
						}],	
				});
			
			}
		});  
		 
	});
	
	//退单
	function sysAdmin_20414_editFun(index) {
		//获取操作对象
		var rows = $('#sysAdmin_20414_datagrid').datagrid('getRows');
		var row = rows[index];
        var mid='-1';
        if(''!=row.mid.replace(/\s+/g,"")){
            mid=row.mid;
        }
		$('<div id="sysAdmin_20414_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">退单信息</span>',
			width: 800,
		    height:550,
		    closed: false,
		    href: '${ctx}/biz/check/20415.jsp?roid='+row.roid+'&mid='+mid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20414_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.roid);    
		    	$('#sysAdmin_20415_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_20415_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkReOrder_updateReOrder.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20414_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20414_datagrid').datagrid('reload');
					    			$('#sysAdmin_20414_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20414_editDialog').dialog('destroy');
					    			$('#sysAdmin_20414_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_20414_editDialog').dialog('destroy');
					$('#sysAdmin_20414_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20414_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//通过菜单
	function sysAdmin_20414_editFun2(index) {
		
		//获取操作对象
		var rows = $('#sysAdmin_20414_datagrid').datagrid('getRows');
		var row = rows[index];
        var mid='-1';
        if(''!=row.mid.replace(/\s+/g,"")){
            mid=row.mid;
        }
		$('<div id="sysAdmin_20414_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">退单信息</span>',
			width: 800,
		    height:550,
		    closed: false,
		    href: '${ctx}/biz/check/20415.jsp?roid='+row.roid+'&mid='+mid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20414_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.roid);    
		    	$('#sysAdmin_20415_editForm').form('load', row);
			},
			buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_20414_editDialog').dialog('destroy');
					$('#sysAdmin_20414_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20414_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//导出EXCEL
   function exportExceldatare(){
	     $("#sysAdmin_20414_searchForm").form('submit',{
			    		url:'${ctx}/sysAdmin/checkReOrder_queryReOrderExport.action'
			    	});
	}
	
	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun20414() {
		$('#sysAdmin_20414_datagrid').datagrid('load', serializeObject($('#sysAdmin_20414_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun20414() {
		$('#sysAdmin_20414_searchForm input').val('');
		$('#sysAdmin_20414_searchForm select').val('全部');
	}
	
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:90px; overflow: hidden; padding-top:30px;">
		<form id="sysAdmin_20414_searchForm" style="padding-left:5%" method="post">
		<input type="hidden" id="bmatids" name="bmatids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号:</th>
					<td><input name="mid" style="width: 120px;" /></td>
					<td style="width: 10px;" ></td>
					<th>交易参考号:</th>
					<td><input name="rrn" style="width: 120px;" /></td>
					 <td width="10px"> </td> 
					<th>退单时间:</th>
					<td><input name="refundDate1" class="easyui-datebox" style="width: 120px;" /> -</td>
					<td><input name="refundDate2" class="easyui-datebox" style="width: 120px;" /></td>
				</tr>
				<tr>
					<th>交易卡号:</th>
					<td><input name="cardPan" style="width: 120px;" /></td>
					<td style="width: 10px;" ></td>
				    <th>状态:</th>
					<td><select  name="status" style="width: 100px;" >
							<option value="">ALL</option>
							<option value="1">已回复</option>
							<option value="0">未回复</option>
							<option value="2">已退回</option>
							<option value="3">已通过</option>
						</select>
					</td>  	
					<td style="width: 10px;" ></td>
					<th>回复日期:</th>
					<td><input name="reOrderUploadStat" class="easyui-datebox" style="width: 120px;" /> -</td>
					<td><input name="reOrderUploadEnd" class="easyui-datebox" style="width: 120px;" /></td>
					<td style="text-align: center; column-span: 3">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun20414();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun20414();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20414_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


