<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!--中心一代返现税点 -->
<script type="text/javascript">
    $(function () {
        $('#cashbackTaxPoint_datagrid').datagrid({
            url: '${ctx}/sysAdmin/cashbackTemplate_queryCashbackTaxPoint.action',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            nowrap: true,
            pageSize: 10,
            pageList: [10, 15],
            columns: [
                [{
                    title: '中心及一代机构号',
                    field: 'UNNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '归属上级机构号',
                    field: 'UPPERUNNO',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '活动类型',
                    field: 'REBATETYPE',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                    title: '返现类型',
                    field: 'CASHBACKTYPE',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                }, {
                	title: '税点',
                    field: 'TAXPOINT',
                    width: 80,
                    align: 'center',
                    halign: 'center'
                } , {
    				title :'操作',
    				field :'operation',
    				width : 100,
    				align : 'center',
    				formatter : function(value,row,index) {
    					return '<img src="${ctx}/images/frame_pencil.png" title="编辑" style="cursor:pointer;" onclick="cashbackTaxPoint_datagrid_updateFun('+index+')"/>&nbsp;&nbsp'
    				}
    			} ]
            ],toolbar: [{
				id:'btn_batch_add',
				text:'创建与批量变更',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_cashbackTaxPoint_batchAddFun();
				}
			},{
				id:'btn_add',
				text:'导出',
				iconCls:'icon-xls',
				handler:function(){
					report_cashbackTaxPoint();
				}
			}]
        });

    });

    //列表查询
    function cashbackTaxPoint_datagrid_queryFun() {
        $('#cashbackTaxPoint_datagrid').datagrid('load', serializeObject($('#cashbackTaxPoint_datagrid_searchForm')));
    }
    
    //导出
    function report_cashbackTaxPoint(){
        $('#cashbackTaxPoint_datagrid_searchForm').form('submit',{
            url:'${ctx}/sysAdmin/cashbackTemplate_reportCashbackTaxPoint.action'
        });
    }

    function cashbackTaxPoint_datagrid_cleanFun() {
        $('#cashbackTaxPoint_datagrid_searchForm input').val('');
        $('#cashbackTaxPoint_datagrid_searchForm select').val('全部');
    }
    
    function cashbackTaxPoint_datagrid_updateFun(index) {
    	debugger;
    	var rows = $('#cashbackTaxPoint_datagrid').datagrid('getRows');
		var row = rows[index];
        $('<div id="cashbackTaxPoint_datagrid_updateDialog"/>').dialog({
            title: '<span style="color:#157FCC;">税点修改</span>',
            width: 350,
            height: 200,
            closed: false,
            href: '${ctx}/biz/bill/agent/cashback/CashbackTaxPoint_update.jsp?id='+row.ID,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#cashbackTaxPoint_datagrid_update_from').form('submit', {
                        url: '${ctx}/sysAdmin/cashbackTemplate_updateCashbackTaxPoint.action?unno='+row.UNNO,
                        success: function (data) {
                            $.messager.progress('close');
                            var result = $.parseJSON(data);
                            if (result.sessionExpire) {
                                window.location.href = getProjectLocation();
                            } else {
                                if (result.success) {
                                    $('#cashbackTaxPoint_datagrid').datagrid('reload');
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg
                                    });
                                    $('#cashbackTaxPoint_datagrid_updateDialog').dialog('destroy');
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
                    $('#cashbackTaxPoint_datagrid_updateDialog').dialog('destroy');
                }
            }],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }
    
    //批量创建与变更
    function sysAdmin_cashbackTaxPoint_batchAddFun(){
        $('<div id="sysAdmin_cashbackTaxPoint_UpdateDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量创建与变更</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/cashback/CashbackTaxPoint_batchAdd.jsp',
            modal: true,
            buttons:[{
                text:'导入',
                iconCls:'icon-ok',
                handler:function() {
                    $('#cashbackTaxPoint_datagrid_UpdateFrom').form('submit', {
                        url:'${ctx}/sysAdmin/cashbackTemplate_importCashbackTaxPointUpdate.action',
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
                                    $('#sysAdmin_cashbackTaxPoint_UpdateDialog').dialog('destroy');
                                    $('#cashbackTaxPoint_datagrid').datagrid('load');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }else{
                                    $('#sysAdmin_cashbackTaxPoint_UpdateDialog').dialog('destroy');
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
                    $('#sysAdmin_cashbackTaxPoint_UpdateDialog').dialog('destroy');
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
    <form id="cashbackTaxPoint_datagrid_searchForm" style="padding:0px;">
      <table class="tableForm pdl20">
        <tr>
          <th style="">机构号:</th>
          <td><input id="unno" name="unno" style="width: 135px;"/></td>
          <th style="">归属上级机构号:</th>
          <td><input id="upperUnno" name="upperUnno" style="width: 135px;"/></td>
          <th>活动类型</th>
		  <td><input name="rebatetype" style="width: 140px;" /></td>
          <th>返现类型：</th>
          <td>
            <select id="cashbacktype" name="cashbacktype" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
              <option value="" selected="selected">All</option>
              <option value="1">刷卡</option>
              <option value="2">押金</option>
              <option value="3">花呗分期</option>
            </select>
          </td>
          <th>&nbsp;</th>
          <td>
            <a href="javascript:void(0);"
               class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
               onclick="cashbackTaxPoint_datagrid_queryFun();">查询</a> &nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-cancel',plain:true" onclick="cashbackTaxPoint_datagrid_cleanFun();">清空</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div data-options="region:'center', border:false" style="overflow: hidden;">
    <table id="cashbackTaxPoint_datagrid"></table>
  </div>
</div>
