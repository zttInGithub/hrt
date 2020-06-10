<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%--钱包分润调整--%> 
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#GenerationQuotaStatistics_datagrid').datagrid({	
			url:'${ctx}/sysAdmin/ProxyPurse_queryGenerationQuotaStatistics.action',
			fit:true,
			frozen:true,
			striped:true,
			fitColumns : false,
			rownumbers:true,
			border : false,
			nowrap : true,
			singleSelect:true,
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 15 ],
			idField : 'FIRSTUNNO',
			sortName : 'FIRSTUNNO',
			sortOrder : 'desc',
			columns : [[{
				title :'一代机构号',
				field :'FIRSTUNNO',
				width : 100			
			},{
				title :'一代名称',
				field :'PROVINCE',
				width : 200			
			},{
				title :'总额度',
				field :'BALANCE',
				width : 130			
			},{
				title :'已提现额度',
				field :'FEEAMT',
				width : 130
			},{
				title :'剩余额度',
				field :'CURAMT',
				width : 100
			},{
				title :'归属中心机构号',
				field :'CENTERUNNO',
				width : 100			
			},{
				title :'归属中心机构名称',
				field :'CENTERUNNONAME',
				width : 200			
			}]],
			toolbar:[{
					text:'图标说明：'
				},{
					id:'btn_add',
					text:'导出',
					iconCls:'icon-xls',
					handler:function(){
						reportGenerationQuotaStatistics();
					}
				}]
		});
	});

	//表单查询
	function searchFun_GenerationQuotaStatistics() {
		$('#GenerationQuotaStatistics_datagrid').datagrid('load', serializeObject($('#searchForm_GenerationQuotaStatistics')));
	}

	//清除表单内容
	function cleanFun_GenerationQuotaStatistics() {
		$('#searchForm_GenerationQuotaStatistics input').val('');
	}
	 //导出
    function reportGenerationQuotaStatistics(){
        $('#searchForm_GenerationQuotaStatistics').form('submit',{
            url:'${ctx}/sysAdmin/ProxyPurse_reportGenerationQuotaStatistics.action'
        });
    }

</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:65px; overflow: hidden; padding-top:10px;">
		<form id="searchForm_GenerationQuotaStatistics" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="UNNO" style="width: 140px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>机构简称</th>
					<td><input name="PROVINCE" style="width: 140px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="searchFun_GenerationQuotaStatistics();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="cleanFun_GenerationQuotaStatistics();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="GenerationQuotaStatistics_datagrid"></table>
    </div> 
</div>