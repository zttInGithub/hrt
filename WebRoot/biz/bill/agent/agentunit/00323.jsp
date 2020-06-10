<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_00323_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listRebateRateForW.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'HUCID',
			sortOrder: 'desc',
			idField : 'HUCID',
			columns : [[{
				title : 'ID',
				field : 'HUCID',
				width : 100,
				hidden:true
			},{
				title : '机构编号',
				field : 'UNNO',
				width : 100
			},{
				title : '机构名称',
				field : 'UN_NAME',
				width : 100
			},{
				title : '活动类型',
				field : 'TXN_DETAIL',
				width : 100
			},{
				title : '状态',
				field : 'STATUS',
				width : 100,
				formatter : function(value,row,index) {
					if(value==0){
						return "待审核";
					}else if(value==1){
						return "已通过";
					}else if(value == 2){
						return "退回";
					}else {
						return "";
					}
				}
			},{
				title : '刷卡成本(%)',
				field : 'DEBIT_RATE',
				width : 100
			},{
				title :'刷卡提现转账手续费',
				field :'DEBIT_FEEAMT',
				width : 100
			},{
				title :'扫码1000以下（终端0.38）费率(%)',
				field :'CREDIT_RATE',
				width : 100
			},{
				title :'扫码1000以下（终端0.38）转账费',
				field :'CASH_COST',
				width : 100
			},{
				title :'扫码1000以上（终端0.38）费率(%)',
				field :'WX_UPRATE',
				width : 100
			},{
				title :'扫码1000以上（终端0.38）转账费',
				field :'WX_UPCASH',
				width : 100
			},{
				title :'扫码1000以上（终端0.45）费率(%)',
				field :'WX_UPRATE1',
				width : 100
			},{
				title :'扫码1000以上（终端0.45）转账费',
				field :'WX_UPCASH1',
				width : 100
			},{
				title :'扫码1000以下（终端0.45）费率(%)',
				field :'ZFB_RATE',
				width : 100
			},{
				title :'扫码1000以下（终端0.45）转账费',
				field :'ZFB_CASH',
				width : 100
			},{
				title :'银联二维码费率(%)',
				field :'EWM_RATE',
				width : 100
			},{
				title :'银联二维码转账费',
				field :'EWM_CASH',
				width : 100
			},{
				title :'云闪付费率(%)',
				field :'YSF_RATE',
				width : 100
			},{
				title :'花呗费率(%)',
				field :'HB_RATE',
				width : 100
			},{
				title :'花呗转账费',
				field :'HB_CASH',
				width : 100
			},{
				title :'申请时间',
				field :'LMDATE',
				width : 100
			},{
				title :'操作时间',
				field :'CDATE',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.STATUS==2){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_00323_editFun('+index+')"/>';	
					}
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'新增活动成本',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_00323_addFun();
					}
				},{
					id:'btn_batch_add',
					text:'批量新增',
					iconCls:'icon-query-export',
					handler:function(){
						sysAdmin_00323_batchaddFun();
				}
			}]
		});
	});

    function sysAdmin_00323_batchaddFun(){
        $('<div id="sysAdmin_00323_1_uploadDialog"/>').dialog({
            title: '<span style="color:#157FCC;">批量新增活动成本</span>',
            width: 650,
            height: 240,
            closed: false,
            href: '${ctx}/biz/bill/agent/agentunit/00323_1.jsp',
            modal: true,
            buttons:[{
                text:'导入',
                iconCls:'icon-ok',
                handler:function() {
                    $('#sysAdmin_00323_1_upload').form('submit', {
                        url:'${ctx}/sysAdmin/agentunit_importBatchRebateRate.action',
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
                                    $('#sysAdmin_00323_1_uploadDialog').dialog('destroy');
                                    $('#sysAdmin_00323_datagrid').datagrid('load');
                                    $.messager.show({
                                        title:'提示',
                                        msg  :resault.msg
                                    });
                                }else{
                                    $('#sysAdmin_00323_1_uploadDialog').dialog('destroy');
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
                    $('#sysAdmin_00323_1_uploadDialog').dialog('destroy');
                }
            }],
            onClose:function() {
                $(this).dialog('destroy');
            }
        });
    }
	
	//修改信息 
	function sysAdmin_00323_editFun(index) {
		var rows = $('#sysAdmin_00323_datagrid').datagrid('getRows');
		var row  = rows[index];
		//商户基本信息明细
		$('<div id="sysAdmin_agentTaskDetail1_datagrid"/>').dialog({
		title: '<span style="color:#157FCC;">修改活动成本</span>', 
		width: 500,
	    height: 650,
	    resizable:true,
    	maximizable:true, 
	    closed: false,
	    onLoad:function() {
	    	row.hucid = row.HUCID;
	    	row.unno = row.UNNO;
	    	row.txnDetail = row.TXN_DETAIL;
	    	row.remarks = row.DEBIT_RATE;
	    	row.rate = row.CREDIT_RATE;
	    	row.cashRate = row.CASH_COST;
	    	row.curAmt = row.DEBIT_FEEAMT?row.DEBIT_FEEAMT:"0";

	    	row.wxUpRate = row.WX_UPRATE?row.WX_UPRATE:"";
	    	row.wxUpCash = row.WX_UPCASH?row.WX_UPCASH:"";
	    	row.wxUpRate1 = row.WX_UPRATE1?row.WX_UPRATE1:"";
	    	row.wxUpCash1 = row.WX_UPCASH1?row.WX_UPCASH1:"";
	    	row.zfbRate = row.ZFB_RATE?row.ZFB_RATE:"";
	    	row.zfbCash = row.ZFB_CASH?row.ZFB_CASH:"";
	    	row.ewmRate = row.EWM_RATE?row.EWM_RATE:"";
	    	row.ewmCash = row.EWM_CASH?row.EWM_CASH:"";
	    	row.ysfRate = row.YSF_RATE?row.YSF_RATE:"";
	    	row.hbRate = row.HB_RATE?row.HB_RATE:"";
	    	row.hbCash = row.HB_CASH?row.HB_CASH:"";
	    	$("#sysAdmin_00325_from").form('load',row);
		},
	    href: '${ctx}/biz/bill/agent/agentunit/00325.jsp?rebateType='+ row.TXN_DETAIL+"&hbRate="+row.HB_RATE+'&ysfRate='+row.YSF_RATE,
	    modal: true,
	    buttons:[{
	    	text:'确认',
	    	iconCls:'icon-ok',
	    	handler:function() {
	    		var validator = $('#sysAdmin_00325_from').form('validate');
	    		if(validator){
	    			$.messager.progress();
	    		}
	    		$('#sysAdmin_00325_from').form('submit',{
		    		url:'${ctx}/sysAdmin/agentunit_updateUnnoRebateRate.action',
	    			success:function(data) {
	    				$.messager.progress('close'); 
	    				var result = $.parseJSON(data);
		    			if (result.sessionExpire) {
		    				window.location.href = getProjectLocation();
			    		} else {
			    			if (result.success) {
			    				$('#sysAdmin_00323_datagrid').datagrid('reload');
				    			$('#sysAdmin_agentTaskDetail1_datagrid').dialog('destroy');
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
				$('#sysAdmin_agentTaskDetail1_datagrid').dialog('destroy');
			} 
		}],onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	//表单查询
	function sysAdmin_00323_searchFun() {
		$('#sysAdmin_00323_datagrid').datagrid('load', serializeObject($('#sysAdmin_00323_searchForm')));
	}

	//清除表单内容
	function sysAdmin_00323_cleanFun() {
		$('#sysAdmin_00323_searchForm input').val('');
	}
	
	function sysAdmin_00323_addFun() {
		$('<div id="sysAdmin_agentunit_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增活动成本</span>',
			width: 400,
		    height:650,
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00324.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var validator = $('#sysAdmin_00331_from').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_00324_from').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_addUnnoRebateRate.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_00323_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_addDialog').dialog('destroy');
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
					$('#sysAdmin_agentunit_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_00323_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 130px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>申请时间</th>
					<td><input name="adate" style="width: 130px;"  class="easyui-datebox" data-options="editable:false"/></td>
					<th>-</th>
					<td><input name="zdate" style="width: 130px;"  class="easyui-datebox" data-options="editable:false"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>状态</th>
					<td>
						<select name="status" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
							<option value="" selected="selected">All</option>
							<option value="0">待审核</option>
							<option value="2">退回</option>
						</select>
					</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_00323_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_00323_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_00323_datagrid"></table>
    </div> 
</div>
