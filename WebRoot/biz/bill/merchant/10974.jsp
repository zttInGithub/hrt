<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#producttype_10974_datagrid').datagrid({
			url :'${ctx}/sysAdmin/producttypeInRebatetypeAction_queryProducttypeInRebatetype.action',
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
				title : '活动',
				field : 'REBATETYPE',
				width : 100
			},{
				title : '产品名称',
				field : 'PRODUCTTYPE',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="Producttype_queryFun('+index+')"/>'
					+'<img src="${ctx}/images/close.png" title="删除" style="cursor:pointer;" onclick="Producttype_DelFun('+index+')"/>';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'添加活动产品对应关系',
				iconCls:'icon-add',
				handler:function(){
					Addproducttype_10974();
				}
			}]
		});
	});
	
	//表单查询
	function sysAdmin_producttype_searchFun() {
		//debugger;
		$('#producttype_10974_datagrid').datagrid('load', serializeObject($('#producttype_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_producttype_cleanFun() {
		$('#producttype_searchForm input').val('');
	}

	//删除模版
	function Producttype_DelFun(index) {
	 var rows = $('#producttype_10974_datagrid').datagrid('getRows');
  		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				var row = rows[index];
				$.ajax({
					url:"${ctx}/sysAdmin/producttypeInRebatetypeAction_delProducttypeInRebatetype.action",
					type:'post',
					data:{"id":row.ID},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#producttype_10974_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#producttype_10974_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#producttype_10974_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#producttype_10974_datagrid').datagrid('unselectAll');
			}
		});	
    }
	//修改模版
	function Producttype_queryFun(index){
		var rows = $('#producttype_10974_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_producttypeUpdate"/>').dialog({
			title: '<span style="color:#157FCC;">活动产品对应信息修改</span>',
			width: 400,    
			    height: 200,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/bill/merchant/10976.jsp?id='+row.ID,  
			    modal: true, 
			    buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$.messager.confirm('操作提示','您确定要修改该活动产品对应信息吗？',function(data){
					    if (data){
							$('#Producttype_10976_update').form('submit', {
								url:'${ctx}/sysAdmin/producttypeInRebatetypeAction_updateProducttypeInRebatetype.action',
								success:function(data) {
									var result = $.parseJSON(data);
									if (result.sessionExpire) {
					    				window.location.href = getProjectLocation();
						    		} else {
						    			if (result.success) {
						    				$('#producttype_10974_datagrid').datagrid('reload');
							    			$('#sysAdmin_producttypeUpdate').dialog('destroy');
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
					});
					
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_producttypeUpdate').dialog('destroy');
				}
			}],onClose:function() {
				$(this).dialog('destroy');
			}		
		});
	}
	//添加模版
	function Addproducttype_10974(){
		debugger;
		$('<div id="producttype_10974_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加活动产品模版</span>',
			width: 400,
		    height:200, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10975.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_editProduct_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/producttypeInRebatetypeAction_addProducttypeInRebatetype.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#producttype_10974_datagrid').datagrid('reload');
					    			$('#producttype_10974_addDialog').dialog('destroy');
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
					$('#producttype_10974_addDialog').dialog('destroy');
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
		<form id="producttype_searchForm" style="padding-left:25%" method="post">
			<table class="tableForm" >
				<tr>				    
					<th>活动类型：</th>
					<td><input name="rebatetype" style="width: 316px;" /></td>
					<th>产品类型：</th>
					<!-- <td><input name="producttype" style="width: 316px;" /></td> -->
					<td style="width:270px;">
	   					<select id="producttype" name="producttype" class="easyui-combogrid" style="width:250px;"></select>
   					</td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_producttype_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_producttype_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="producttype_10974_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>
<script type="text/javascript">
	$('#producttype').combogrid({
			url : '${ctx}/sysAdmin/producttypeInRebatetypeAction_queryProducttype.action',
			idField:'PRODUCTTYPE',
			textField:'PRODUCTTYPE',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'PRODUCTTYPE',title:'产品类型',width:250}
			]] 
		});
</script>


