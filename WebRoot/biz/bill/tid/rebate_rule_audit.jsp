<%@ page import="java.util.Map" %>
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

        $('#rebate_rule_audit_grid').datagrid({
            url: '${ctx}/sysAdmin/rebateRuleAction_listRebateRules.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            remoteSort: true,
            idField: 'brrId',
            columns: [[{
                title: '活动规则ID',
                field: 'brrId',
                width: 100,
                hidden: true
            }, {
                title: '规则名称',
                field: 'brrName',
                width: 100
            }, {
                title: '规则类型',
                field: 'pointType',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|POINTTYPE|POINTTYPE|"+value]
                }
            }, {
                title: '交易类型',
                field: 'txnWay',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|TXNWAY|TXNWAY|"+value]
                }
            }, {
                title: '开始时间类型',
                field: 'timeType',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|TIMETYPE|TIMETYPE|"+value]
                }
            }, {
                title: '统计周期',
                field: 'cycle',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|CYCLE|CYCLE|"+value]
                }
            }, {
                title: '开始时间',
                field: 'startTime',
                width: 100
            }, {
                title: '结束时间',
                field: 'endTime',
                width: 100
            }, {
                title: '交易金额类型',
                field: 'txnType',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|TXNTYPE|TXNTYPE|"+value]
                }
            }, {
                title: '交易金额-刷卡',
                field: 'txnAmount',
                width: 100
            }, {
                title: '返现对象1',
                field: 'backType1',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|BACKTYPE1|BACKTYPE1|"+value]
                }
            }, {
                title: '返利金额1',
                field: 'backAmount1',
                width: 100
            }, {
                title: '返现对象2',
                field: 'backType2',
                width: 100,
                formatter: function (value, row, index) {
                    return json["2|BILL_REBATE_RULE|BACKTYPE2|BACKTYPE2|"+value]
                }
            }, {
                title: '返利金额2',
                field: 'backAmount2',
                width: 100
            }, {
                title: '交易月-销售月开始',
                field: 'startMonth',
                width: 100
            }, {
                title: '交易月-销售月结束',
                field: 'endMonth',
                width: 100
            }, {
                title: '扣款成功金额',
                field: 'paymentAmount',
                width: 100
            }, {
                title: '规则描述',
                field: 'remark1',
                width: 100
            }, {
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
            },{
                title: '操作',
                field: 'operation',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                	if(row.auditStatus==1){
                   		 return '<img src="${ctx}/images/start.png" title="活动规则审批" style="cursor:pointer;" onclick="rebate_rule_audit_modifyFun(' + index + ','+ row.brrId +')"/>';
                	}
                }
            }]],
            toolbar: [{
                /* id: 'btn_add',
                text: '新增活动规则',
                iconCls: 'icon-add',
                handler: function () {
                    rebate_rule_addFun();
                } */
            }]
        });
    });

	//审批详情页
    function rebate_rule_audit_modifyFun(index,brrId) {
        var rows = $('#rebate_rule_audit_grid').datagrid('getRows');
        var row = rows[index];
        $('<div id="rebate_rule_ModifyDialog"/>').dialog({
            title: '<span style="color:#157FCC;">活动规则详情</span>',
            width: 900,
            height: 450,
            closed: false,
            href: '${ctx}/biz/bill/tid/rebate_rule_sel.jsp?type=1',
            modal: true,
            onLoad: function () {
                $('#s_rebate_rule_audit_selForm').form('load', row);
            },
            buttons: [{
                text: '审批通过',
                iconCls: 'icon-ok',
                handler: function () {
                	 $.messager.confirm('确认', '您确认要审核通过该活动规则吗?', function (result) {
                  		if (result) {
                  			 $.ajax({
                   				 url: "${ctx}/sysAdmin/rebateRuleAction_updateRebateRuleAuditStatus.action",
                    			 type: 'post',
                    			 data: {"brrId": brrId},
                    			 dataType: 'json',
                    			 success: function (data, textStatus, jqXHR) {
                        			 if (data.success) {
                            			$.messager.show({
                                			title: '提示',
                                			msg: data.msg
                            			});
                            			$('#rebate_rule_ModifyDialog').dialog('destroy');
                            			$('#rebate_rule_audit_grid').datagrid('reload');
                       			 	} else {
                           			 	$.messager.alert('提示', data.msg);
                        			}
                    			},
                   				error: function () {
                        			$.messager.alert('提示', '审核通过活动规则出错！');
                    			}
               			 });
                  		}
                  });
                }
            },
             {
                text: '退回',
                iconCls: 'icon-cancel',
                handler: function () {
                    updateRebateRuleAuditStatusBack(brrId);
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#rebate_rule_ModifyDialog').dialog('destroy');
                    
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
                 $('#rebate_rule_audit_grid').datagrid('unselectAll');
            }
        });
    }
	//退回
	function updateRebateRuleAuditStatusBack(brrId){
	
		$('<div id="rebate_rule_auditBackDialog"/>').dialog({
			title: '<span style="color:#157FCC;">退回</span>',
			width: 380,   
		    height: 240, 
		    closed: false,
		    onLoad:function() {
		    	$('#rejectReason_brrId').val(brrId);
			},
		    href: '${ctx}/biz/bill/tid/rebate_rule_rejectReason.jsp',
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					if($('#rejectReason').val()==null||$('#rejectReason').val()==''){
						$.messager.alert('提示', '请填写退回原因');
						return false;
					}
					$('#rebate_rule_rejectReason').form('submit', {
						url:'${ctx}/sysAdmin/rebateRuleAction_updateRebateRuleAuditStatus.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#rebate_rule_auditBackDialog').dialog('destroy');
				    				$('#rebate_rule_ModifyDialog').dialog('destroy');
				    				$('#rebate_rule_audit_grid').datagrid('reload');
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
					$('#rebate_rule_auditBackDialog').dialog('destroy');
					
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				
			}
		});
	}

    //表单查询
    function sysAdmin_rebate_rule_searchFun() {
        $('#rebate_rule_audit_grid').datagrid('load', serializeObject($('#rebate_rule_searchForm_audit')));
    }

    //清除表单内容
    function sysAdmin_rebate_rule_cleanFun() {
        $('#rebate_rule_searchForm_audit input').val('');
    }

    $('#txnWayA').combogrid({
        url: '${ctx}/sysAdmin/enumAction_getEnumInfoList.action',
        idField: 'enumValue',
        textField: 'enumName',
        mode: 'remote',
        fitColumns: true,
        queryParams: {
            enumTable: 'BILL_REBATE_RULE',
            enumColumn: 'txnWay'
        },
        columns: [[
            {field: 'enumValue', title: '值', width: 50, checkbox: true},
            {field: 'enumName', title: '交易类型', width: 150}
        ]]
    });
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding:15px;">
    <form id="rebate_rule_searchForm_audit" style="padding-left:2%">
      <table class="tableForm">
        <tr>
          <th>规则名称</th>
          <td>&nbsp;&nbsp;<input name="brrName" id="brrNameA" style="width: 150px;"/></td>
          <th>&nbsp;&nbsp;交易类型</th>
          <td>&nbsp;&nbsp;<input name="txnWay" id="txnWayA" style="width: 150px;"/></td>
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
               onclick="sysAdmin_rebate_rule_searchFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
               onclick="sysAdmin_rebate_rule_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="rebate_rule_audit_grid" style="overflow: hidden;"></table>
  </div>
</div>