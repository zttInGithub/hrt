<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
    <!-- 代理商资料 -->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
        // @author:xuegangliu-20190227 隐藏返现导入功能图标
        //console.log("${sessionScope.user.loginName}")
        //console.log("${sessionScope.user.unitLvl}")
        sessionLoginName="${sessionScope.user.loginName}";
        sessionUnitLvl = "${sessionScope.user.unitLvl}";

		$('#sysAdmin_10410_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentUnitData.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				field : 'buid',
				title : '代理商编号',
				checkbox:true
			},{
				title : '机构编号',
				field : 'unno',
				width : 100
			},{
				title : '代理商名称',
				field : 'agentName',
				width : 100
			},{
				title :'经营地址',
				field :'baddr',
				width : 100
			},{
				title :'开通状态',
				field :'openName',
				width : 100
			},{
				title :'开通日期',
				field :'openDate',
				width : 100,
				sortable : true
			},{
				title :'缴款状态',
				field :'amountConfirmName',
				width : 100
			},{
                title :'是否开通返现钱包',
                field :'cashStatus',
                hidden:sessionUnitLvl<=2&&sessionUnitLvl?false:true,
                width : 100,
                formatter : function(value,row,index) {
                    if(value==1){
                        return "是";
                    }else if((value==0)){
                        return "否";
                    }else{
                        return "否";
					}
                }
			},{
				title :'签约人员',
				field :'signUserIdName',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_10140_editFun('+index+')"/>';
				}
			}]]	,
			toolbar:[{
				id:'btn_runAll',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_agentunit_exportAll();
				}
			},{
				id:'btn_run',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_agentunit_exportFun();
				}
			},{
                id:'btn_runImportCashStatus',
                text:'返现导入',
                iconCls:'icon-query-export',
                handler:function(){
                    sysAdmin_agentunit_importCashStatusFun();
                }
            }]
		});
        // TODO @xuegangliu-20190304 提现状态导入功能,先开启总公司权限, 待修改
        if(sessionUnitLvl==0){
        }else{
            $('#btn_runImportCashStatus').eq(0).hide();
        }
	});
    function sysAdmin_agentunit_importCashStatusFun(){
        $('<div id="sysAdmin_10410_uploadDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量返现导入修改</span>',
            width: 380,
            height: 120,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/10140_1.jsp',
            modal: true,
            buttons:[{
                text:'导入',
                iconCls:'icon-ok',
                handler:function() {
                    $('#sysAdmin_10140_1_upload_addForm').form('submit', {
                        url:'${ctx}/sysAdmin/agentunit_importBatchCashDate.action',
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
                                    $('#sysAdmin_10410_uploadDialog').dialog('destroy');
                                    $('#sysAdmin_10410_datagrid').datagrid('load');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }else{
                                    $('#sysAdmin_10410_uploadDialog').dialog('destroy');
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
                    $('#sysAdmin_10410_uploadDialog').dialog('destroy');
                    $('#sysAdmin_10410_datagrid').datagrid('unselectAll');
                }
            }],
            onClose:function() {
                $(this).dialog('destroy');
                $('#sysAdmin_10410_datagrid').datagrid('unselectAll');
            }
        });
    }
	function sysAdmin_agentunit_exportAll(){
		$('#sysAdmin_10410_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_exportAgents.action'
			    	});
	}
	function sysAdmin_agentunit_exportFun() {
		var checkedItems = $('#sysAdmin_10410_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.buid);
		});               
		var buids=names.join(",");
		if(buids==null||buids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#buids").val(buids);
		$('#sysAdmin_10410_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_export.action'
			    	});
	}
	
	//查看明细
	function sysAdmin_10140_editFun(index){
    	var rows = $('#sysAdmin_10410_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_10410_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看明细</span>',
			width: 950,
		    height:430, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/10141.jsp?buid='+row['buid']+'&unno='+row['unno'],  
		    modal: true,
		    onLoad:function() {
		    	$('#sysAdmin_10141_queryForm').form('load', row);
			},
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10410_datagrid').datagrid('unselectAll');
			}
		});
	}
	function bill_agentunitdata_searchFun() {
		$('#sysAdmin_10410_datagrid').datagrid('load', serializeObject($('#sysAdmin_10410_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata_cleanFun() {
		$('#sysAdmin_10410_searchForm input').val('');
	}
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_10410_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="buids" name="buids" />
			<table class="tableForm" >
				<tr>
					<th>代理商名称</th>
					<td><input name="agentName" style="width: 200px;" />&nbsp;&nbsp;&nbsp;</td>
					<th>机构号</th>
					<td><input name="unno" style="width: 200px;" />&nbsp;&nbsp;&nbsp;</td>
					<th>运营中心机构号</th>
					<td><input name="remarks" style="width: 200px;" />&nbsp;&nbsp;&nbsp;</td>
					<th>代理商级别</th>
					<td>
						<select	name="unLvl" style="width: 150px;">
							<option value="" selected="selected">ALL</option>
							<option value="0">0-总公司</option>
							<option value="1">1-分公司</option>
							<option value="2">2-作业中心/代理机构</option>
							<option value="3">3-二级作业中心/二级代理机构</option>
						</select>
					</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="bill_agentunitdata_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="bill_agentunitdata_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div> 
	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10410_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
