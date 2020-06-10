<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    var json;
    $(function () {
        $.ajax({
            url: '${ctx}/sysAdmin/enumAction_getEnumNameMap.action',
            dataType: "json",
            success: function(data){
                json = JSON.parse(data.enumMap);
            }
        });

        $('#sysAdmin_rebate_info_audit_grid').datagrid({
            url: '${ctx}/sysAdmin/rebateInfoAction_listRebateInfoAudits.action',
           // queryParams:{auditStatus:'1'},
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            remoteSort: true,
            idField: 'brId',
            columns: [[{
                title: '活动ID',
                field: 'brId',
                width: 100,
                hidden: true
            }, {
                title: '活动',
                field: 'rebateType',
                width: 100
            }, {
                title: '活动名称',
                field: 'rebateName',
                width: 100
            }, {
                title: '开始时间',
                field: 'startDate',
                width: 100
            }, {
                title: '结束时间',
                field: 'endDate',
                width: 100
            }, {
                title: '结算价',
                field: 'settlement',
                width: 100
            }, {
                title: '默认刷卡费率',
                field: 'defaultTxnRate',
                width: 100
            }, {
                title: '默认刷卡转账手续费',
                field: 'defaultTxnCashfee',
                width: 100
            }, {
                title: '默认扫码费率(1000以下)',
                field: 'defaultScanRate',
                width: 100
            }, {
                title: '默认扫码费率(1000以上)',
                field: 'defaultScanRateUp',
                width: 100
            }, {
                title: '默认扫码转账手续费(1000以下)',
                field: 'defaultScanCashfee',
                width: 100
            }, {
                title: '默认扫码转账手续费(1000以上)',
                field: 'defaultScanCashfeeUp',
                width: 100
            }, {
                title: '默认花呗费率',
                field: 'defaultScanhbRate',
                width: 100
            }, {
                title: '默认花呗转账手续费',
                field: 'defaultScanhbCashfee',
                width: 100
            }, {
                title: '交易需要累加月数',
                field: 'sumMonth',
                width: 100
            }, {
                title: '标识',
                field: 'flag',
                width: 100,
                hidden: true,
                formatter:function (value, row, index) {
                    return json["2|BILL_REBATE_INFO|FLAG|FLAG|"+value];
                }
            }, {
                title: '操作标识',
                field: 'operateType',
                width: 100,
                hidden: true,
                formatter:function (value, row, index) {
                    return json["2|BILL_REBATE_INFO|OPERATE_TYPE|OPERATE_TYPE|"+value];
                }
            }, {
                title: '状态',
                field: 'status',
                width: 100,
                formatter:function (value, row, index) {
                    return json["1|SYS|STATUS|STATUS|"+value];
                }
            }, {
                title: '身份证号返现次数',
                field: 'cardBack',
                width: 100
            }, {
                title: '新商户定义信息',
                field: 'merDefine',
                width: 100
            }, {
                title: '创建时间',
                field: 'createDate',
                width: 100
            }, 
            {
            	title:'审核状态',
            	field:'auditStatus',
            	width:100,
            	 formatter:function (value, row, index) {
                    if(value==1){
                    	return '待审核';
                    }else if(value==2){
                    	return '审核通过';
                    }else if(value==3){
                    	return '退回';
                    }else{
                    	return '待审核';
                    }
                }
            },
            {
            	title:'退回原因',
            	field:'rejectReason',
            	width:100
            },
              {
            	title:'审核时间',
            	field:'auditDate',
            	width:100
            },
             {
            	title:'审核人',
            	field:'auditUser',
            	width:100
            },
            {
                title: '备注1',
                field: 'remark1',
                width: 100
            }, {
                title: '备注2',
                field: 'remark2',
                width: 100
            }, {
                title: '操作',
                field: 'operation',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    var buttonHtml='<img src="${ctx}/images/query_search.png" title="查看活动定义" style="cursor:pointer;" onclick="rebate_info_queryFun(' + row.brId + ','+index+')"/>&nbsp;&nbsp;';
                	if(row.auditStatus==1){
                      buttonHtml = buttonHtml + '<img src="${ctx}/images/start.png" title="审核活动定义" style="cursor:pointer;" onclick="rebate_info_auditFun(' + row.brId + ','+index+')"/>';
                		
                	}
                	return buttonHtml;
                }
            }]],
            toolbar: [{
                /*id: 'btn_add',
                text: '新增活动定义',
                iconCls: 'icon-add',
                handler: function () {
                    rebate_info_addFun();
                }*/
            }]
        });
    });

    function rebate_info_queryFun(brId,index) {
        $('<div id="rebate_info_queryDialog"/>').dialog({
            title: '<span style="color:#157FCC;">活动定义信息</span>',
            width: 1200,
            height: 550,
            closed: false,
            href: '${ctx}/biz/bill/tid/rebate_info_sel.jsp',
            modal: true,
            onLoad: function () {
                var rows = $('#sysAdmin_rebate_info_audit_grid').datagrid('getRows');
                var row = rows[index];
                $('#s_rebate_info_audit_addForm').form('load', row);
                $('#remark3').val(row.remark2);
            },
            buttons: [{
                text: '返回',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#rebate_info_queryDialog').dialog('destroy');
                    $('#sysAdmin_rebate_info_audit_grid').datagrid('reload');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
                $('#sysAdmin_rebate_info_audit_grid').datagrid('unselectAll');
            }
        });
    }

 
    function rebate_info_auditFun(brId,index) {
      
        $('<div id="rebate_info_editDialog"/>').dialog({
            title: '<span style="color:#157FCC;">审核活动定义信息</span>',
            width: 1200,
            height: 550,
            closed: false,
            href: '${ctx}/biz/bill/tid/rebate_info_sel.jsp',
            modal: true,
            onLoad: function () {
                var rows = $('#sysAdmin_rebate_info_audit_grid').datagrid('getRows');
                var row = rows[index];
                $('#s_rebate_info_audit_addForm').form('load', row);
                $('#remark3').val(row.remark2);
            },
            buttons: [{
                text: '审批通过',
                iconCls: 'icon-ok',
                handler: function () {
                  $.messager.confirm('确认', '您确认要审核通过该活动定义吗?', function (result) {
                  		if (result) {
                  			 $.ajax({
                   				 url: "${ctx}/sysAdmin/rebateInfoAction_updateRebateInfoAuditStatus.action",
                    			 type: 'post',
                    			 data: {"brId": brId},
                    			 dataType: 'json',
                    			 success: function (data, textStatus, jqXHR) {
                        			 if (data.success) {
                            			$.messager.show({
                                			title: '提示',
                                			msg: data.msg
                            			});
                            			$('#rebate_info_editDialog').dialog('destroy');
                            			$('#sysAdmin_rebate_info_audit_grid').datagrid('reload');
                       			 	} else {
                           			 	$.messager.alert('提示', data.msg);
                        			}
                    			},
                   				error: function () {
                        			$.messager.alert('提示', '审核通过活动定义出错！');
                    			}
               			 });
                  		}
                  });
                }
            }, {
                text: '退回',
                iconCls: 'icon-cancel',
                handler: function () {
                   updateRebateInfoAuditStatusBack(brId);
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
                $('#sysAdmin_rebate_info_audit_grid').datagrid('unselectAll');
            }
        });
    }

    //表单查询
    function s_rebate_info_searchFun() {
        $('#sysAdmin_rebate_info_audit_grid').datagrid('load', serializeObject($('#s_rebate_info_searchForm_audit')));
    }

    //清除表单内容
    function s_rebate_info_cleanFun() {
        $('#s_rebate_info_searchForm_audit input').val('');
    }
    
    //驳回
    function updateRebateInfoAuditStatusBack(brId){
    	
		$('<div id="rebate_info_auditBackDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    onLoad:function() {
		    	$('#rejectReason_brId').val(brId);
			},
		    href: '${ctx}/biz/bill/tid/rebate_info_rejectReason.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					if($('#rejectReason').val()==null||$('#rejectReason').val()==''){
						$.messager.alert('提示', '请填写退回原因');
						return false;
					}
					$('#rebate_info_rejectReason').form('submit', {
						url:'${ctx}/sysAdmin/rebateInfoAction_updateRebateInfoAuditStatus.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#rebate_info_auditBackDialog').dialog('destroy');
				    				$('#rebate_info_editDialog').dialog('destroy');
				    				$('#sysAdmin_rebate_info_audit_grid').datagrid('reload');
					    			$.messager.show({title : '提示',msg : '退回成功'});
					    			
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
					$('#rebate_info_auditBackDialog').dialog('destroy');
					//$('#sysAdmin_rebate_info_audit_grid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				//$('#sysAdmin_rebate_info_audit_grid').datagrid('unselectAll');
			}
		});
	
    }
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding:15px;">
    <form id="s_rebate_info_searchForm_audit" style="padding-left:2%">
      <table class="tableForm">
        <tr>
          <th>活动</th>
          <td>&nbsp;&nbsp;<input name="rebateType" style="width: 150px;"/></td>

          <th>&nbsp;&nbsp;&nbsp;&nbsp;审核状态：</th>
          <td>
            <select name="auditStatus" id="auditStatus" style="width: 150px;">
              <option value="">全部</option>
              <option value="1">待审核</option>
              <option value="2">审核通过</option>
              <option value="3">退回</option>
            </select>
          </td>
          <td colspan="10" style="text-align: center;">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="s_rebate_info_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="s_rebate_info_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="sysAdmin_rebate_info_audit_grid" style="overflow: hidden;"></table>
  </div>
</div>