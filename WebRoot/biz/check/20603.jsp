<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
			$('#unno_20603').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	
		$('#sysAdmin_20603_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_querySytTemplateList.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			singleSelect:true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'UNNO',
			sortOrder: 'desc',
			columns : [[{
				title :'机构名称',
				field :'UNITNAME',
				width : 100
			},{
				title :'机构号',
				field :'UNNO',
				width : 100
			},{
				title :'模板名称',
				field :'TEMPNAME',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 80,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.MATAINTYPE == 'P'){
						return '手机模板不可修改';
					}
					return '<img src="${ctx}/images/close.png" title="机构解除模板" style="cursor:pointer;" onclick="DeletesysAdmin_20603('+index+')"/>';
				}
			}]]	,
			toolbar:[{
					id:'btn_add',
					text:'分配分润模板',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_20603_addFun();
					}
			}]	
		});
	});
	
	//表单查询
	function sysAdmin_20603_searchFun80() {
		$('#sysAdmin_20603_datagrid').datagrid('load', serializeObject($('#sysAdmin_20603_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_20603_cleanFun80() {
		$('#sysAdmin_20603_searchForm input').val('');
	}
	
	function sysAdmin_20603_addFun(){
		$('<div id="sysAdmin_20603_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">分配分润模板</span>',
			width: 500,
		    height:250, 
		    closed: false,
		    href: '${ctx}/biz/check/20604.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_20604_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_20604_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_20604_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_addSytTemplateInfo.action', 
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20603_datagrid').datagrid('reload');
					    			$('#sysAdmin_20603_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
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
					$('#sysAdmin_20603_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//删除
	function DeletesysAdmin_20603(index){
		var rows = $('#sysAdmin_20603_datagrid').datagrid('getRows');
		var UNNO=rows[index].UNNO;
		$.messager.confirm('确认','您确认要解除机构为'+UNNO+'的所有模板吗?解除绑定模板后：若不绑定新模板该代理自绑定日（含）起分润钱包无分润；若绑定新模板，则绑定之日（含）起分润采用新模板成本计算，绑定之日之前按照原模板进行计算',function(result) {
			if (result) {
				$('#sysAdmin_20603_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/CheckUnitProfitMicro_deleteSytTemplateUnno.action?unno='+UNNO,//&settMethod='+settMethod,
					success:function(msg) {
			    		var result = $.parseJSON(msg);
				    	if (result.sessionExpire) {
				    		window.location.href = getProjectLocation();
					    } else {
					    	if (result.success) {
					    		$('#sysAdmin_20603_datagrid').datagrid('unselectAll');
					    		$('#sysAdmin_20603_datagrid').datagrid('reload');
						    	$.messager.show({
									title : '提示',
									msg : result.msg
								});
						    } else {
						    	$('#sysAdmin_20603_datagrid').datagrid('unselectAll');
						    	$('#sysAdmin_20603_datagrid').datagrid('reload');
						    	$.messager.alert('提示', result.msg);
							  }
						 }
				    }
				});
			}
		});
	}
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_20603_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<td style="width: 250px;"></td>
					<th>机构编号</th>
					<td>
						<select name="unno" id="unno_20603" class="easyui-combogrid" style="width:205px;"></select>
					</td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20603_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20603_cleanFun80();">清空</a>	
					</td>
				</tr>
				<tr>

				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20603_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


