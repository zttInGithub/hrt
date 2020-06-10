<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 中心设备出库情况数据 -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10590_datagrid').datagrid({
			url :'${ctx}/sysAdmin/purchaseDetail_queryCenterCheckout.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'MACHINEMODEL',
			columns : [[{
				title : '中心',
				field : 'YUNGYING',
				width : 100
			},{
				title : '中心名称',
				field : 'UNNAME',
				width : 100
			},{
				title : '销售',
				field : 'PURCHASER',
				width : 100
			},{
				title : '活动类型',
				field : 'REBATETYPE',
				width : 100
			},{
				title : '出库总数',
				field : 'ZCOUNT',
				width : 100
			}]],
			toolbar:[ {
				text:'导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10590_exportFun();
				}
			}]		
		});
	});
	
	//导出
	function sysAdmin_10590_exportFun() {
		$('#sysAdmin_10590_searchForm').form('submit',{
    		url:'${ctx}/sysAdmin/purchaseDetail_exportCenterCheckout.action'
    	});
	}
	
	//表单查询
	function sysAdmin_10590_searchFun() {
		var sd = $('#detailCdate').datebox('getValue');
		var ed = $('#detailCdateEnd').datebox('getValue');
		var sd_ = sd;
		var ed_ = ed;
		var start = new Date(sd_.replace("-", "/").replace("-", "/"));
		var end = new Date(ed_.replace("-", "/").replace("-", "/"));
		if (sd == null || sd == "") {
			$.messager.alert('提示', "请选择起始日期 ！", "info");
		} else if (ed == null || ed == "") {
			$.messager.alert('提示', "请选择结束日期 ！", "info");
		}
		else if(((end-start)/(24*60*60*1000))>31){
			$.messager.alert('警告', "报表日期区间超过31天 !",'warning');
		}else{
			$('#sysAdmin_10590_datagrid').datagrid('load', serializeObject($('#sysAdmin_10590_searchForm')));
		}
	}

	//清除表单内容
	function sysAdmin_10590_cleanFun() {
		$('#sysAdmin_10590_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10590_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>出库时间：</th>
					<td><input name="detailCdate" id="detailCdate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;</td>
					<td><input name="detailCdateEnd" id="detailCdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/></td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10590_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10590_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10590_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>