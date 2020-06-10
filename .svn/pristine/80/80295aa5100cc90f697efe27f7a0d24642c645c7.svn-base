<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#profitmicro_20510_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_querySubTemplate.action',
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
				title : '编号',
				field : 'APTID',
				hidden: true,
				width : 100
			},{
				title : '模版名称',
				field : 'TEMPNAME',
				width : 100
			}/*,{
				title : '代还手续费',
				field : 'SUBRATE',
				width : 100
			}*/,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="Profitmicro_queryFun('+index+')"/>&nbsp;&nbsp';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'添加分润模版',
				iconCls:'icon-add',
				handler:function(){
					Addprofitmicro_20510();
				}
			}]
		});
	});
	
	//表单查询
	function sysAdmin_profitmicro_searchFun() {
		$('#profitmicro_20510_datagrid').datagrid('load', serializeObject($('#profitmicro_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_profitmicro_cleanFun() {
		$('#profitmicro_searchForm input').val('');
	}

	//删除模版
	function Profitmicro_DelFun(index) {
	 var rows = $('#profitmicro_20510_datagrid').datagrid('getRows');
  		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				var row = rows[index];
				$.ajax({
					url:"${ctx}/sysAdmin/CheckUnitProfitMicro_Delprofitmicro.action",
					type:'post',
					data:{"ids":row.TEMPNAME},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#profitmicro_20510_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#profitmicro_20510_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#profitmicro_20510_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#profitmicro_20510_datagrid').datagrid('unselectAll');
			}
		});	
    }
	//修改模版
	function Profitmicro_queryFun(index){
		var rows = $('#profitmicro_20510_datagrid').datagrid('getRows');
		var row = rows[index];
		var MATAINTYPE = row.MATAINTYPE;
		$('<div id="sysAdmin_profitmicroUpdate"/>').dialog({
			title: '<span style="color:#157FCC;">代还分润模版修改</span>', 
			width: 850,    
			    height: 560,  
			    resizable:true,
	    		maximizable:true,
			    closed: false,
			    href: '${ctx}/biz/check/20512.jsp?aptId='+row.APTID,
			    modal: true, 
			    onLoad:function() {
			    	// $('#tempName').val(row.TEMPNAME);
			    	// $('#subRate').val((row.SUBRATE*100).toFixed(4));
			    	// $('#aptId').val(row.APTID);
			},buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
          $.messager.confirm("操作提示", "您确定要修改下月生效信息吗？", function (data) {
            if (data){
            	if(MATAINTYPE=='P'){
								$.messager.alert("提示","App创建的模板不允许在平台修改！");
								return ;
							}
                var inputs = document.getElementById("Profitmicro_20512_Next").getElementsByTagName("input");
                for(var i=0;i<inputs.length;i++){
                    if(inputs[i].type=="file"){continue;}
                    inputs[i].value = $.trim(inputs[i].value);
                }

                $('#Profitmicro_20512_Next').form('submit', {
                    url:'${ctx}/sysAdmin/CheckUnitProfitMicro_updateSubTemplate.action',
                    success:function(data) {
                        var result = $.parseJSON(data);
                        if (result.sessionExpire) {
                            window.location.href = getProjectLocation();
                        } else {
                            if (result.success) {
                                $('#profitmicro_20510_datagrid').datagrid('reload');
                                $('#sysAdmin_profitmicroUpdate').dialog('destroy');
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
                return true;
            }else{
                return false;
            }
				  })
        }
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_profitmicroUpdate').dialog('destroy');
				}
			}],onClose:function() {
				$(this).dialog('destroy');
			}		
		});
	}
//添加分润模版
	function Addprofitmicro_20510(){
		$('<div id="profitmicro_20510_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加代还分润模版</span>',
			width: 900,
		    height:560, 
		    closed: false,
		    href: '${ctx}/biz/check/20511.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {	    	
		    		var inputs = document.getElementById("profitmicro_20511_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}		    	
		    		var validator = $('#profitmicro_20511_addForm').form('validate');		    		
		    		if(validator){
		    			$.messager.progress();
		    		}		    		
		    		$('#profitmicro_20511_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/CheckUnitProfitMicro_addSubTemplate.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#profitmicro_20510_datagrid').datagrid('reload');
					    			$('#profitmicro_20510_addDialog').dialog('destroy');
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
					$('#profitmicro_20510_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	//查看明细
	function sysAdmin_profitmicro20270_queryFun(index){
	 	var rows = $('#profitmicro_20510_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10630_run"/>').dialog({
			title: '查看明细',
			width: 950,   
		    height: 300, 
		    closed: false,
		    href:'${ctx}/biz/check/20271.jsp?tempname='+encodeURIComponent(encodeURIComponent(row.TEMPNAME)),  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:11px;">
		<form id="profitmicro_searchForm" style="padding-left:25%" method="post">
			<table class="tableForm" >
				<tr>				    
					<th>模版名称：</th>
					<td><input name="tempName" style="width: 316px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_profitmicro_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_profitmicro_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="profitmicro_20510_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


