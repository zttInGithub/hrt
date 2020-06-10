<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 返现钱包二代以下模板设置-->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#cashbackTemplate_datagrid').datagrid({
			url :'${ctx}/sysAdmin/cashbackTemplate_queryCashbackTemplateUnno.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title : '下级代理机构号',
				field : 'UNNO',
				width : 100
			},{
				title : '下级代理机构名称',
				field : 'UNNONAME',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="编辑" style="cursor:pointer;" onclick="cashbackTemplate_queryForUnno('+index+')"/>'
					     +'<img src="${ctx}/images/close.png" title="删除" style="cursor:pointer;" onclick="cashbackTemplate_deleteForUnno('+index+')"/>';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'返现设置',
				iconCls:'icon-add',
				handler:function(){
					cashbackTemplate_addForUnno();
				}
			}]
		});
	});
	
	//表单查询
	function sysAdmin_profitmicro_searchFun20288() {
		$('#cashbackTemplate_datagrid').datagrid('load', serializeObject($('#cashbackTemplate_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_profitmicro_cleanFun20288() {
		$('#cashbackTemplate_searchForm input').val('');
	}
	
	//删除模版
	function cashbackTemplate_deleteForUnno(index) {
	 var rows = $('#cashbackTemplate_datagrid').datagrid('getRows');
  		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				var row = rows[index];
				$.ajax({
					url:"${ctx}/sysAdmin/cashbackTemplate_deleteCashbackTemplate.action",
					type:'post',
					data:{"unno":row.UNNO},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#cashbackTemplate_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#cashbackTemplate_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除出错！');
						$('#cashbackTemplate_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#cashbackTemplate_datagrid').datagrid('unselectAll');
			}
		});	
    }

	//修改模版
	function cashbackTemplate_queryForUnno(index){
		//debugger;
		var rows = $('#cashbackTemplate_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="cashbackTemplate_update_1"/>').dialog({
			title: '<span style="color:#157FCC;">返现设置变更</span>',
			width: 600,
		    height: 400,
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/agent/cashback/CashbackTemplate_update.jsp?unno='+encodeURIComponent(encodeURIComponent(row.UNNO)),
		    modal: true, 
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					//debugger;
					var tr_list = $('#table1_input').find("tr");
		    		var tiNum = parseInt(tr_list.length-1);
					var cost_data = [];
	    			for(var ti = 1; ti < tiNum+1; ti++){
	    				var inputs = $("#tr_cashbackTemplate_2_"+ti+" input");
	    				var o = serializeTrObj(inputs);
	    				cost_data.push(o);
	    			}
    				$("#unnoRebatetype_2").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
    				
    				var validator = $('#cashbackTemplate_update_2').form('validate');		    		
	    			if(validator){
		    			$.messager.progress();
		    		}
					$('#cashbackTemplate_update_2').form('submit', {
						url:'${ctx}/sysAdmin/cashbackTemplate_updateCashbackTemplateNext.action',
						success:function(data) {
							$.messager.progress('close'); 
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
		    					window.location.href = getProjectLocation();
			    			} else {
			    				if (result.success) {
			    					$('#cashbackTemplate_datagrid').datagrid('reload');
				    				$('#cashbackTemplate_update_1').dialog('destroy');
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
					$('#cashbackTemplate_update_1').dialog('destroy');
				}
			}],onClose:function() {
				$(this).dialog('destroy');
			}		
		});
	}
	
	//添加返现模板
	function cashbackTemplate_addForUnno(){
		$('<div id="cashbackTemplate_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加返现模版</span>',
			width: 600,
		    height:400, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/cashback/CashbackTemplate_add.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		//debugger;
		    		var tr_list = $('#nbody_add').find("tr");
		    		var tiNum = parseInt(tr_list.length);
		    		if(tiNum==0){
		    			$.messager.alert('提示',"请添加设置机构返现活动");
		    		}else{
		    			var unno_add = $('#unno_add').combobox('getValue');
		    			if(unno_add==null||unno_add.length<1){
		    				$.messager.alert('提示',"请添加返现机构");
		    				return;
		    			}
		    			var cost_data = [];
		    			for(var ti = 1; ti < tiNum+1; ti++){
		    				var inputs = $("#tr_cashbackTemplate_2_"+ti+" input");
		    				var o = serializeTrObj(inputs);
		    				cost_data.push(o);
		    			}
	    				$("#unnoRebatetype").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
	    				var validator = $('#profitmicro_20288_addForm').form('validate');		    		
		    			if(validator){
			    			$.messager.progress();
			    		}
		    			$('#cashbackTemplate_add_addForm').form('submit',{
			    			url:'${ctx}/sysAdmin/cashbackTemplate_addCashbackTemplate.action',
		    				success:function(data) {
		    					$.messager.progress('close'); 
		    					var result = $.parseJSON(data);
			    				if (result.sessionExpire) {
			    					window.location.href = getProjectLocation();
				    			} else {
				    				if (result.success) {
				    					$('#cashbackTemplate_datagrid').datagrid('reload');
					    				$('#cashbackTemplate_addDialog').dialog('destroy');
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
		    	}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#cashbackTemplate_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:11px;">
		<form id="cashbackTemplate_searchForm" style="padding-left:25%" method="post">
			<table class="tableForm" >
				<tr>				    
					<th>下级代理机构号：</th>
					<td><input name="unno" style="width: 316px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_profitmicro_searchFun20288();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_profitmicro_cleanFun20288();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="cashbackTemplate_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


