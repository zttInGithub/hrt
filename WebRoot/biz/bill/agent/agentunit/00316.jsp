<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化treegrid
	$(function() {
		$('#sysAdmin_unit00316_treegrid').datagrid({
			url : '${ctx}/sysAdmin/agentunit_listUnits.action',
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination: true,
			pageList : [ 10, 15 ],
			idField : 'UNNO',
			columns : [[{
				title : '机构名称',
				field : 'UN_NAME',
				width : 150
			},{
				title : '机构编号',
				field : 'UNNO',
				width : 50
			},{
				title : '所在区域',
				field : 'PROVINCE_CODE',
				width : 50,
				hidden : true
			},{
				title : '上级机构号',
				field : 'UPPER_UNIT',
				width : 50
			},{
				title : '机构级别',
				field : 'UN_LVL',
				width : 50,
				//hidden : true
				formatter: function(value,row,index){
					if(value==2){
						return '1';
					}else if(value==3){
						return '2';
					}else if(value==5){
						return '3';
					}else if(value==6){
						return '4';
					}else if(value==7){
						return '5';
					}
				}
			},{
				title :'创建时间',
				field :'CREATE_DATE',
				width : 100,
				sortable : true
			},{
				title :'创建者',
				field :'CREATE_USER',
				width : 100,
				sortable : true
			},{
				title :'最近修改时间',
				field :'UPDATE_DATE',
				width : 100,
				sortable : true
			},{
				title :'修改者',
				field :'UPDATE_USER',
				width : 100,
				sortable : true
			}/**,{
				title :'状态',
				field :'statusName',
				width : 100,
				sortable : true
			}
			,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_unit_editFun('+row.unNo+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_unit_delFun('+row.unNo+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="停用" style="cursor:pointer;" onclick="sysAdmin_unit_closeFun('+row.unNo+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/start.png" title="启用" style="cursor:pointer;" onclick="sysAdmin_unit_startFun('+row.unNo+')"/>';
				}}**/
			]],toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_00316_exportFun();
				}
			}]
		});
	});
	
	function sysAdmin_00316_exportFun() {
		$('#bill_agentunit00316_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_exportUnits.action'
			    	});
	}
	
	//表单查询
	function bill_agentunit00316_searchFun() {
		//$('#sysAdmin_unit00316_treegrid').treegrid('loadData', serializeObject($('#bill_agentunit00316_searchForm')));
		$('#sysAdmin_unit00316_treegrid').datagrid({ url:'${ctx}/sysAdmin/agentunit_listUnits.action?unno='+$('#unno00316').val()});
	}

	//清除表单内容
	function bill_agentunit00316_cleanFun() {
		$('#bill_agentunit00316_searchForm input').val('');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:70px; overflow: hidden; padding-top:20px;">
		<form id="bill_agentunit00316_searchForm" style="padding-left:30%">
			<table class="tableForm" >
				<tr>
					<th>代理商机构号</th>
					<td><input name="unno" id="unno00316" style="width: 100px;" /></td>		
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="bill_agentunit00316_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="bill_agentunit00316_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	<div data-options="region:'center', border:false" style="overflow: hidden;">   
		<table id="sysAdmin_unit00316_treegrid" ></table>
	</div>
</div>