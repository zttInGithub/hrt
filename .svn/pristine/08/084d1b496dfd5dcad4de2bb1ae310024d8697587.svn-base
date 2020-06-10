<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 下级代理分润差额钱包强制开通（结算） -->
<script type="text/javascript">
    $(function () {
        $('#rebateType_wallet_cash_switch_js').combogrid({
            url : '${ctx}/sysAdmin/checkWalletCashSwitch_listAvailableRebateType.action',
            idField:'VALUEINTEGER',
            textField:'NAME',
            value:-1,
            mode:'remote',
            fitColumns:true,
            columns:[[
                {field:'NAME',title:'活动类型',width:70},
                {field:'VALUEINTEGER',title:'id',width:70,hidden:true}
            ]]
        });
        $('#wallet_cash_switch_js').datagrid({
            url: '${ctx}/sysAdmin/checkWalletCashSwitch_listCheckWalletCashSwitchJS.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '机构号',
                    field: 'UNNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '机构名称',
                    field: 'UN_NAME',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '活动类型',
                    field: 'REBATETYPE',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter:function(value,row,index){
                    	if(value < '20'){
                    		return "秒到";
                    	}
                    	return value;
                    }
                }, {
                	title: '本月状态',
                    field: 'WALLETSTATUS',
                    width: 80,
                    align: 'center',
                    halign: 'center',
                    formatter: function (value, row, index) {
                        // 取上个月数据修改记录
                        if(row.WALLETSTATUS2!="2"){
                            if (row.WALLETSTATUS2 == "0") {
                                return "关";
                            } else if(row.WALLETSTATUS2 == "1") {
                                return "开";
                            }
                        }else{
                            if (value == "0") {
                                return "关";
                            } else if(value=="1"){
                                return "开";
                            }
                        }
                    }
                } /* , {
    				title :'操作',
    				field :'operation',
    				width : 100,
    				align : 'center',
    				formatter : function(value,row,index) {
    					if(row.WALLETSTATUS == 1){
    						return '<img src="${ctx}/images/query_search.png" title="关闭" style="cursor:pointer;" onclick="sysAdmin_updateWalletCash('+index+',1)"/>&nbsp;&nbsp'
    					 }else{
    						return '<img src="${ctx}/images/query_search.png" title="开启" style="cursor:pointer;" onclick="sysAdmin_updateWalletCash('+index+',2)"/>&nbsp;&nbsp'
    					}
    				}
    			} */]
            ], toolbar: [/* {
				id:'btn_add',
				text:'钱包开通',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_walletCashSwitchJs_addFun();
				}
			}, */{
				id:'btn_batch_add',
				text:'批量开通',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_walletCashSwitchJs_batchAddFun();
			}
		}]
        });

    });

    function wallet_cash_switch_js_exportFun() {
        $('#wallet_cash_switch_js_searchForm').form('submit', {
            url: '${ctx}/sysAdmin/checkDeductionAction_export10170.action'
        });
    }

    //列表查询
    function wallet_cash_switch_js_queryFun() {
        $('#wallet_cash_switch_js').datagrid('load', serializeObject($('#wallet_cash_switch_js_searchForm')));
    }

    function wallet_cash_switch_js_cleanFun() {
        $('#wallet_cash_switch_js_searchForm input').val('');
        $('#wallet_cash_switch_js_searchForm select').val('全部');
    }
    
  	//单个修改
	function sysAdmin_updateWalletCash(index,type){
    	var rows = $('#wallet_cash_switch_js').datagrid('getRows');
		var row = rows[index];
		$.messager.confirm('确认','您确认要修改钱包状态吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/checkWalletCashSwitch_updateWalletCashOne.action?type="+type,
					type:'post',
					data:{"row":row},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#wallet_cash_switch_js').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '修改钱包状态出错！');
					}
				});
			}
		});
	}
    
    //单个开通
    function sysAdmin_walletCashSwitchJs_addFun() {
		$('<div id="sysAdmin_walletCashSwitchJs_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增钱包开通</span>',
			width: 380,
		    height:200,
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/wallet_cash_switch_jsAdd.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#wallet_cash_switch_js_singleFrom').form('submit',{
			    		url:'${ctx}/sysAdmin/checkWalletCashSwitch_addWalletCashSwitchSingleJS.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#wallet_cash_switch_js').datagrid('reload');
					    			$('#sysAdmin_walletCashSwitchJs_addDialog').dialog('destroy');
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
					$('#sysAdmin_walletCashSwitchJs_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
    
    //批量开通
    function sysAdmin_walletCashSwitchJs_batchAddFun(){
        $('<div id="sysAdmin_walletCashSwitchJs_batchAddDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量钱包开通</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/wallet_cash_switch_jsBatchAdd.jsp',
            modal: true,
            buttons:[{
                text:'导入',
                iconCls:'icon-ok',
                handler:function() {
                    $('#wallet_cash_switch_js_batchFrom').form('submit', {
                        url:'${ctx}/sysAdmin/checkWalletCashSwitch_importBatchAddWalletCashSwitchJS.action',
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
                            console.log($.parseJSON(data))
                            var resault = $.parseJSON(data);
                            if(resault.sessionExpire){
                                window.location.href = getProjectLocation();
                            }else{
                                if(resault.success){
                                    $('#sysAdmin_walletCashSwitchJs_batchAddDialog').dialog('destroy');
                                    $('#wallet_cash_switch_js').datagrid('load');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }else{
                                    $('#sysAdmin_walletCashSwitchJs_batchAddDialog').dialog('destroy');
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
                    $('#sysAdmin_walletCashSwitchJs_batchAddDialog').dialog('destroy');
                }
            }],
            onClose:function() {
                $(this).dialog('destroy');
            }
        });
    }
</script>
<style type="text/css">
  .pdl20 {
    padding-left: 20px;
  }
</style>
<div class="easyui-layout" data-options="fit:true, border:false">
  <div data-options="region:'north',border:false" style="overflow: hidden; padding-top:5px;">
    <form id="wallet_cash_switch_js_searchForm" style="padding:0px;">
      <table class="tableForm pdl20">
        <tr>
          <th style="">机构号:</th>
          <td><input id="unno" name="unno" style="width: 135px;"/></td>
          <th>活动类型:</th>
          <td><select id="rebateType_wallet_cash_switch_js" name="rebateType" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:135px;"></select></td>
          <th>开启状态：</th>
          <td>
            <select id="walletStatus" name="walletStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
              <option value="" selected="selected">All</option>
              <option value="0">关闭</option>
              <option value="1">开启</option>
            </select>
          </td>
          <th>&nbsp;</th>
          <td>
            <a href="javascript:void(0);"
               class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="wallet_cash_switch_js_queryFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true" onclick="wallet_cash_switch_js_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="wallet_cash_switch_js"></table>
  </div>
</div>
