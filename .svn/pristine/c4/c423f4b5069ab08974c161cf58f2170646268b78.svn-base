<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
			$('#unno_20282').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	
		$('#sysAdmin_20282_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryUnitProfitTempList.action',
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
			sortName: 'UNNO1',
			sortOrder: 'desc',
			columns : [[{
				title : 'UNNO1',
				field : 'UNNO1',
				width : 80 ,
				checkbox: true
			},{
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
				title :'模板类型',
				field :'SETTMETHOD',
				width : 100,
				hidden:true,
				formatter : function(value,row,index) {
					if(value=="100000"||value=="200000"){
							return "秒到"
					}else{
							return "理财";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 80,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/close.png" title="机构解除模板" style="cursor:pointer;" onclick="DeletesysAdmin_20282('+index+')"/>';
				}
			}]]	,
			toolbar:[{
					id:'btn_add',
					text:'分配分润模板',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_20282_addFun();
					}
			}]	
		});
	});
	
	//表单查询
	function sysAdmin_20282_searchFun80() {
		$('#sysAdmin_20282_datagrid').datagrid('load', serializeObject($('#sysAdmin_20282_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_20282_cleanFun80() {
		$('#sysAdmin_20282_searchForm input').val('');
	}
	
	function sysAdmin_20282_addFun(){
		$('<div id="sysAdmin_20282_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">分配分润模板</span>',
			width: 500,
		    height:250, 
		    closed: false,
		    href: '${ctx}/biz/check/20283.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_20283_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_20283_addForm').form('validate');
		    		
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_20283_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_addUnitProfitTempInfo.action', 
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20282_datagrid').datagrid('reload');
					    			$('#sysAdmin_20282_addDialog').dialog('destroy');
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
					$('#sysAdmin_20282_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//删除
	function DeletesysAdmin_20282(index){
		var rows = $('#sysAdmin_20282_datagrid').datagrid('getRows');
		var UNNO=rows[index].UNNO1;
		var settMethod=rows[index].SETTMETHOD;
		var mataintypeA=rows[index].MATAINTYPEA;
		$.messager.confirm('确认','您确认要解除机构为'+UNNO+'的所有模板吗?解除绑定模板后：若不绑定新模板该代理自绑定日（含）起分润钱包无分润；若绑定新模板，则绑定之日（含）起分润采用新模板成本计算，绑定之日之前按照原模板进行计算',function(result) {
			if (result) {
				if(mataintypeA=='P'){
					$.messager.alert("提示","App创建的模板不允许在平台解除模板！");
					return ;
				}

				$('#sysAdmin_20282_searchForm').form('submit',{
					url:'${ctx}/sysAdmin/CheckUnitProfitMicro_DeleteProfitMicroUnno.action?unno='+UNNO,//&settMethod='+settMethod,
					success:function(msg) {
			    		var result = $.parseJSON(msg);
				    	if (result.sessionExpire) {
				    		window.location.href = getProjectLocation();
					    } else {
					    	if (result.success) {
					    		$('#sysAdmin_20282_datagrid').datagrid('unselectAll');
					    		$('#sysAdmin_20282_datagrid').datagrid('reload');
						    	$.messager.show({
									title : '提示',
									msg : result.msg
								});
						    } else {
						    	$('#sysAdmin_20282_datagrid').datagrid('unselectAll');
						    	$('#sysAdmin_20282_datagrid').datagrid('reload');
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
		<form id="sysAdmin_20282_searchForm" style="padding-left:10%" method="post">
			<table class="tableForm" >
				<tr>
					<td style="width: 250px;"></td>
					<th>机构编号</th>
					<td>
						<select name="unno" id="unno_20282" class="easyui-combogrid" style="width:205px;"></select>
					</td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_20282_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_20282_cleanFun80();">清空</a>	
					</td>
				</tr>
				<tr>

				</tr> 
			</table>
		</form>
	</div>  
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20282_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


