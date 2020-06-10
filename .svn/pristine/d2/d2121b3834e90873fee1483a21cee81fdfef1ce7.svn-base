<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 采购单(代理) -->
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10580_datagrid').datagrid({
			url :'${ctx}/sysAdmin/changeTerRate_queryChangeTerRate.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'bctrid',
			columns : [[{
				title : 'ID',
				field : 'bctrid',
				width : 100,
				hidden : true
			},{
				title : 'SN起始',
				field : 'snStart',
				width : 120
			},{
				title : 'SN截止',
				field : 'snEnd',
				width : 80
			},{
				title : '费率',
				field : 'rate',
				width : 120
			},{
				title : '扫码费率',
				field : 'scanRate',
				width : 80
			},{
				title : '手续费',
				field : 'secondRate',
				width : 120
			},{
				title : '总数量',
				field : 'totalNum',
				width : 100
			},{
				title : '申请时间',
				field : 'maintainDate',
				width : 100
			},{
				title :'状态',
				field :'approveStatus',
				width : 100,
				formatter : function(value,row,index) {
					if(value=='W'){
						return "待审核";
					}else if(value=='Y'){
						return "通过";
					}else if(value=='K'){
						return "退回";
					}
				}
			},{
				title :'审核时间',
				field :'approvedate',
				width : 100
			}]], 
			toolbar:[{
				id:'btn_add',
				text:'新增费率修改',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10580_add();
				}
			},{
                text:'批量导入费率修改',
                iconCls:'icon-query-export',
                handler:function(){
                    sysAdmin_10580_import();
                }
			}]
		});
	});

    function sysAdmin_10580_import(){
        $('<div id="sysAdmin_10580_uploadDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量导入费率修改</span>',
            width: 380,
            height: 120,
            closed: false,
            href: '${ctx}/biz/bill/tid/10583.jsp',
            modal: true,
            buttons:[{
                text:'导入',
                iconCls:'icon-ok',
                handler:function() {
                    $('#sysAdmin_10580_01_upload_addForm').form('submit', {
                        url:'${ctx}/sysAdmin/changeTerRate_importBatchChangeTerRate.action',
                        onSubmit: function(){
                            var contact = document.getElementById('upload').value;
                            if(contact == "" || contact == null){
                                $.messager.show({
                                    title:'提示',
                                    msg:'请选择要上传的文件',
                                    timeout:5000,
                                    showType:'slide'
                                });
                                return false;
                            }
                            if(contact != "" && contact != null){
                                var l = contact.split(".");
                                if(l[1] != "xls"){
                                    $.messager.show({
                                        title:'提示',
                                        msg:'请选择后缀名为.xls文件',
                                        timeout:5000,
                                        showType:'slide'
                                    });
                                    return false;
                                }
                                //如果格式正确，处理
                                if(l[1] == "xls"){
                                    document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
                                    return true;
                                }
                            }
                        },
                        //成功返回数据
                        success:function(data){
                            var resault = $.parseJSON(data);
                            // console.log(resault)
                            if(resault.sessionExpire){
                                window.location.href = getProjectLocation();
                            }else{
                                if(resault.success){
                                    $('#sysAdmin_10580_uploadDialog').dialog('destroy');
                                    $('#sysAdmin_10580_datagrid').datagrid('load');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }else{
                                    $('#sysAdmin_10580_uploadDialog').dialog('destroy');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }
                            }
                        }
                    });
                }
            },{
                text:'取消',
                iconCls:'icon-cancel',
                handler:function() {
                    $('#sysAdmin_10580_uploadDialog').dialog('destroy');
                    $('#sysAdmin_10580_datagrid').datagrid('unselectAll');
                }
            }],
            onClose:function() {
                $(this).dialog('destroy');
                // $('#sysAdmin_10580_datagrid').datagrid('load');
                $('#sysAdmin_10580_datagrid').datagrid('unselectAll');
            }
        });
    }
	
	//通过
	function sysAdmin_10580_goFun(bctrid){
		$.messager.confirm('确认','您确认要通过本次修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeTerRateGo.action",
					type:'post',
					data:{"bctrid":bctrid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10580_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}
	
	//退回
	function sysAdmin_10580_backFun(bctrid){
		$.messager.confirm('确认','您确认要退回本次修改吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/changeTerRate_updateChangeTerRateBack.action",
					type:'post',
					data:{"bctrid":bctrid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_10580_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '提交审核采购单出错！');
					}
				});
			}
		});
	}

	//新增
	function sysAdmin_10580_add() {
		$('<div id="sysAdmin_10580_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增费率修改</span>',
			width: 600,
		    height:350, 
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10581.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_10581_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/changeTerRate_addChangeTerRate.action',
		    			success:function(data) {
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10580_datagrid').datagrid('reload');
					    			$('#sysAdmin_10580_addDialog').dialog('destroy');
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
					$('#sysAdmin_10580_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10580_searchFun() {
		$('#sysAdmin_10580_datagrid').datagrid('load', serializeObject($('#sysAdmin_10580_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10580_cleanFun() {
		$('#sysAdmin_10580_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10580_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
				    <th>&nbsp;&nbsp;状态&nbsp;</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="W">待审核</option>
		    				<option value="K">退回</option>
		    				<option value="Y">通过</option>
		    			</select>
					</td>
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10580_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10580_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10580_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>