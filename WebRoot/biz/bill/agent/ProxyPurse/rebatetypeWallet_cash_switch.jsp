<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 下级代理分润差额钱包强制开通（结算） -->
<script type="text/javascript">
    $(function () {
        $('#rebatetypeWallet_cash_switch').datagrid({
            url: '${ctx}/sysAdmin/agentunit_rebatetypeWalletCashSwitch.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '代理机构号',
                    field: 'UNNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '归属上级机构号',
                    field: 'BNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '归属一代机构号',
                    field: 'RNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '归属中心机构号',
                    field: 'REGISTRYNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                	title: '钱包提现状态',
                    field: 'ISCASHSWITCH',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                } , {
    				title :'操作',
    				field :'operation',
    				width : 100,
    				align : 'center',
    				formatter : function(value,row,index) {
    					return '<img src="${ctx}/images/frame_pencil.png" title="编辑" style="cursor:pointer;" onclick="rebatetypeWallet_cash_switch_updateFun('+index+')"/>&nbsp;&nbsp'
    				}
    			} ]
            ],toolbar: [{
				id:'btn_batch_add',
				text:'批量开通',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_rebatetypeWalletCashSwitchJs_batchAddFun();
				}
			},{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-xls',
				handler:function(){
					report_rebatety_switch();
				}
			}]
        });

    });

    //列表查询
    function rebatetypeWallet_cash_switch_queryFun() {
        $('#rebatetypeWallet_cash_switch').datagrid('load', serializeObject($('#rebatetypeWallet_cash_switch_searchForm')));
    }
    
    //导出
    function report_rebatety_switch(){
        $('#rebatetypeWallet_cash_switch_searchForm').form('submit',{
            url:'${ctx}/sysAdmin/agentunit_reportRebatetypeWalletCashSwitch.action'
        });
    }

    function rebatetypeWallet_cash_switch_cleanFun() {
        $('#rebatetypeWallet_cash_switch_searchForm input').val('');
        $('#rebatetypeWallet_cash_switch_searchForm select').val('全部');
    }
    
    function rebatetypeWallet_cash_switch_updateFun(index) {
    	var rows = $('#rebatetypeWallet_cash_switch').datagrid('getRows');
		var row = rows[index];
        $('<div id="rebatetypeWallet_cash_switch_updateDialog"/>').dialog({
            title: '<span style="color:#157FCC;">活动提现钱包变更</span>',
            width: 400,
            height: 250,
            closed: false,
            href: '${ctx}/biz/bill/agent/ProxyPurse/rebatetypeWallet_cash_switch_update.jsp',
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var validator = $('#rebatetypeWallet_cash_switch_update_from').form('validate');
                    if (validator) {
                        $.messager.progress();
                    }
                    $('#rebatetypeWallet_cash_switch_update_from').form('submit', {
                        url: '${ctx}/sysAdmin/agentunit_updateRebatetypeWalletCashSwitch.action?unno='+row.UNNO,
                        success: function (data) {
                            $.messager.progress('close');
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#rebatetypeWallet_cash_switch').datagrid('reload');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                    $('#rebatetypeWallet_cash_switch_updateDialog').dialog('destroy');
                                } else {
                                    $.messager.alert('提示', result.msg);
                                }
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#rebatetypeWallet_cash_switch_updateDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }
    
    //批量开通
    function sysAdmin_rebatetypeWalletCashSwitchJs_batchAddFun(){
        $('<div id="sysAdmin_rebatetypeWalletCashSwitch_UpdateDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量变更</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/ProxyPurse/rebatetypeWallet_cash_switchBatchAdd.jsp',
            modal: true,
            buttons:[{
                text:'导入',
                iconCls:'icon-ok',
                handler:function() {
                    $('#rebatetypeWallet_cash_switch_UpdateFrom').form('submit', {
                        url:'${ctx}/sysAdmin/agentunit_importrebatetypeWalletCashSwitchUpdate.action',
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
                                    $('#sysAdmin_rebatetypeWalletCashSwitch_UpdateDialog').dialog('destroy');
                                    $('#rebatetypeWallet_cash_switch').datagrid('load');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }else{
                                    $('#sysAdmin_rebatetypeWalletCashSwitch_UpdateDialog').dialog('destroy');
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
                    $('#sysAdmin_rebatetypeWalletCashSwitch_UpdateDialog').dialog('destroy');
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
  <div data-options="region:'north',border:false" style="height:65px;overflow: hidden; padding-top:5px;">
    <form id="rebatetypeWallet_cash_switch_searchForm" style="padding:0px;">
      <table class="tableForm pdl20">
        <tr>
          <th style="">机构号:</th>
          <td><input id="unno" name="unno" style="width: 135px;"/></td>
          <th style="">归属上级机构号:</th>
          <td><input id="bno" name="bno" style="width: 135px;"/></td>
          <th style="">归属一代机构号:</th>
          <td><input id="rno" name="rno" style="width: 135px;"/></td>
          <th style="">归属中心机构号:</th>
          <td><input id="registryNo" name="registryNo" style="width: 135px;"/></td>
          <th>钱包提现状态：</th>
          <td>
            <select id="accType" name="accType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
              <option value="" selected="selected">All</option>
              <option value="0">关闭</option>
              <option value="1">开启</option>
            </select>
          </td>
          <th>&nbsp;</th>
          <td>
            <a href="javascript:void(0);"
               class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="rebatetypeWallet_cash_switch_queryFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true" onclick="rebatetypeWallet_cash_switch_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="rebatetypeWallet_cash_switch"></table>
  </div>
</div>
