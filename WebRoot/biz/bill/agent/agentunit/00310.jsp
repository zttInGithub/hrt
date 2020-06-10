<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>
<%
    Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession);
%>
<script type="text/javascript">
	var yunYinghrtCost = new Map(); // 运营中心的成本
	var valueHrtCost = new Map(); // 页面传过来的成本
	var hrtKey = [];

    // MACHINE_TYPE	N	INTEGER	Y			机器类型 1手刷 2传统
    var tMachineType = new Map();
    tMachineType.set("1","手刷");
    tMachineType.set("2","传统");
    // TXN_TYPE	N	INTEGER	Y			产品类型 1-秒到、2-理财、3-信用卡代还、4-云闪付、5-快捷、6-T0、7-T1
    var tTxnTypeMap = new Map();
    tTxnTypeMap.set("1","秒到");
    tTxnTypeMap.set("2","理财");
    tTxnTypeMap.set("3","信用卡代还");
    tTxnTypeMap.set("4","云闪付");
    tTxnTypeMap.set("5","快捷");
    tTxnTypeMap.set("6","T0");
    tTxnTypeMap.set("7","T1");
    // TXN_DETAIL	N	INTEGER	Y
    // 产品细分 1-0.6秒到、2-0.72秒到、3-理财、4-信用卡代还、5-云闪付、6-快捷VIP、7-快捷完美、8-微信、9-支付宝、10-银联二维码、11-传统-标准、12-传统-优惠、13-传统-减免
    var tTxnDetilMap = new Map();
    tTxnDetilMap.set("1","0.6秒到");
    tTxnDetilMap.set("2","0.72秒到");
    tTxnDetilMap.set("3","理财");
    tTxnDetilMap.set("4","信用卡代还");
    tTxnDetilMap.set("5","云闪付");
    tTxnDetilMap.set("6","快捷VIP");
    tTxnDetilMap.set("7","快捷完美");
    tTxnDetilMap.set("8","扫码1000以下");
    tTxnDetilMap.set("9","扫码1000以上");
    tTxnDetilMap.set("10","银联二维码");
    tTxnDetilMap.set("11","传统-标准");
    tTxnDetilMap.set("12","传统-优惠");
    tTxnDetilMap.set("13","传统-减免");
    tTxnDetilMap.set("20","20");
    tTxnDetilMap.set("21","21");

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentunit_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentUnit.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				title : '代理商编号',
				field : 'buid',
				width : 100,
				align : 'center',
				formatter: function(value,row,index){
					value="00000"+value;
					var todoIDfo=value.substring(value.length-6,value.length);
					return todoIDfo;
				}
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
				title :'缴款状态',
				field :'amountConfirmName',
				width : 100
			},{
				title :'签约人员',
				field :'signUserIdName',
				width : 100
			},{
				title :'退回原因',
				field :'returnReason',
				width : 100
			},{
				title :'审核状态',
				field :'approveStatus',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(value=="W"){
						return "待审核";
					}else if(value=="Y"){
						return "审核通过";
					}else if(value=="C"){
						return "审核中";
					}else if(value == "K"){
						return "退回";
					}else {
						return "";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					var restVal="";
					if(row['approveStatus']=="K"){
						restVal+='<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_agentunit_editFun('+index+','+row.buid+')"/>';
					}
					var amountConfirmDate =row['amountConfirmDate'];
					if(!amountConfirmDate && amountConfirmDate!=""){
						if(restVal!=""){restVal+="&nbsp;&nbsp;";}
						restVal+='<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_agentunit_00410_delFun('+row.buid+')"/>';
					}
					return restVal;
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'代理商签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_agentunit_addFun();
					}
				}]		
		});
	});
	
	
	//表单查询
	function bill_agentunit_searchFun() {
		$('#sysAdmin_agentunit_datagrid').datagrid('load', serializeObject($('#bill_agentunit_searchForm')));
	}

	//清除表单内容
	function bill_agentunit_cleanFun() {
		$('#bill_agentunit_searchForm input').val('');
	}
	
	function sysAdmin_agentunit_addFun() {
		$('<div id="sysAdmin_agentunit_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">代理商签约</span>',
			width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00311.jsp',  
		    modal: true,
            onLoad:function() {
                <% if(user.getUnitLvl()==1||user.getUnitLvl()==0){ %>
                // 获取运营中心的成本
                $.ajax({
                    url: "sysAdmin/agentunit_getYunYingHrtCost00310.action",
                    dataType: "json",
                    success: function(data){
                        yunYinghrtCost = JSON.parse(data.msg);
                        // console.log(yunYinghrtCost);
                    }
                });
                <%}%>
            },
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		// 获取封装信息
		    		<% if(user.getUnitLvl()==1||user.getUnitLvl()==0){ %>
		    		//1、成本信息
			    		if($('#00311_tab2_select').val()!=1){
		    				$.messager.alert("提示","请确保成本信息已录入","info");
		    				return;
		    			}
			    		var tr_list = $("#00311_tab2_body").children("tr")
			    		var cost_data = [];
			    		for(var i = 1; i <= tr_list.length; i ++){
			    			var inputs = $("#00311T"+i+" input");
			    			var o = serializeTrObj(inputs);
			    			cost_data.push(o);
			    		}
							var tr_act_list = $("#00311_ACT_TR").children("tr")
							for(var i = 0; i < tr_act_list.length; i ++){
								var inputsAct = $("#00311TACT"+i+" input");
								var oAct = serializeTrObj(inputsAct);
								cost_data.push(oAct);
							}
		    			$("#00311_costData").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
                    // @author:xuegangliu-20190313 校验成本值 签约一代代理商时 成本值不能大于运营中心的成本值
                    for (var i = 0; i < cost_data.length; i++) {
                        var hrtCostH = cost_data[i];
                        var prefixKey = hrtCostH.machineType + '|' + hrtCostH.txnType + '|' + hrtCostH.txnDetail + '|';
                        valueHrtCost.set(prefixKey + "cashCost", hrtCostH.cashCost);
                        hrtKey.push(prefixKey + "cashCost");
                        valueHrtCost.set(prefixKey + "creditRate", hrtCostH.creditRate);
                        hrtKey.push(prefixKey + "creditRate");
                        valueHrtCost.set(prefixKey + "debitRate", hrtCostH.debitRate);
                        hrtKey.push(prefixKey + "debitRate");
                        valueHrtCost.set(prefixKey + "debitFeeamt", hrtCostH.debitFeeamt);
                        hrtKey.push(prefixKey + "debitFeeamt");
                        // valueHrtCost.set(prefixKey + "cashRate", hrtCostH.cashRate);
                        // hrtKey.push(prefixKey + "cashRate");
												valueHrtCost.set(prefixKey + "wxUpRate", hrtCostH.wxUpRate);
												hrtKey.push(prefixKey + "wxUpRate");
												valueHrtCost.set(prefixKey + "wxUpRate1", hrtCostH.wxUpRate1);
												hrtKey.push(prefixKey + "wxUpRate1");
												valueHrtCost.set(prefixKey + "wxUpCash", hrtCostH.wxUpCash);
												hrtKey.push(prefixKey + "wxUpCash");
												valueHrtCost.set(prefixKey + "wxUpCash1", hrtCostH.wxUpCash1);
												hrtKey.push(prefixKey + "wxUpCash1");
												valueHrtCost.set(prefixKey + "zfbRate", hrtCostH.zfbRate);
												hrtKey.push(prefixKey + "zfbRate");
												valueHrtCost.set(prefixKey + "zfbCash", hrtCostH.zfbCash);
												hrtKey.push(prefixKey + "zfbCash");
												valueHrtCost.set(prefixKey + "ewmRate", hrtCostH.ewmRate);
												hrtKey.push(prefixKey + "ewmRate");
												valueHrtCost.set(prefixKey + "ewmCash", hrtCostH.ewmCash);
												hrtKey.push(prefixKey + "ewmCash");
												valueHrtCost.set(prefixKey + "ysfRate", hrtCostH.ysfRate);
												hrtKey.push(prefixKey + "ysfRate");
												valueHrtCost.set(prefixKey + "hbRate", hrtCostH.hbRate);
												hrtKey.push(prefixKey + "hbRate");
												valueHrtCost.set(prefixKey + "hbCash", hrtCostH.hbCash);
												hrtKey.push(prefixKey + "hbCash");
                    }
                    // console.log(valueHrtCost)
                    for (var i = 0; i < hrtKey.length; i++) {
                        var costValue = valueHrtCost.get(hrtKey[i]);
                        if (costValue) {
                            var yunYingValue = yunYinghrtCost[hrtKey[i]];
                            if (yunYingValue) {
                                if (yunYingValue > Number(costValue)) {
                                    infoHrtNo = hrtKey[i].split("|");
                                    infoCostErr = "";
                                    actNum = tTxnDetilMap.get(infoHrtNo[2])==undefined?infoHrtNo[2]:tTxnDetilMap.get(infoHrtNo[2]);
                                    var isAct = infoHrtNo[2]>=20;
                                    if(infoHrtNo[3]=='cashCost'){
                                        if(isAct){
                                            infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.38）转账费低于运营中心成本，请修改!';
                                        }else {
                                            infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',提现成本低于运营中心成本，请修改!';
                                        }
                                    }else if(infoHrtNo[3]=='creditRate'){
                                        if(isAct){
                                            infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.38）费率小于运营中心成本,请修改!';
                                        }else {
                                            infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',贷记卡成本(%)小于运营中心成本,请修改!';
                                        }
                                    }else if(infoHrtNo[3]=='debitRate'){
                                        if(isAct){
                                            infoCostErr='活动成本'+actNum+'刷卡成本小于运营中心成本,请修改!';
                                        }else{
                                          infoCostErr='机器类型:'+tMachineType.get(infoHrtNo[0])+',产品类型:'+tTxnTypeMap.get(infoHrtNo[1])+',产品细分:'+actNum+',借记卡成本(%)小于运营中心成本,请修改!';
                                        }
                                    }else if(infoHrtNo[3]=='debitFeeamt'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',刷卡提现小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',借记卡封顶手续费小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='cashRate'){
                                        infoCostErr='机器类型:'+tMachineType.get(infoHrtNo[0])+',产品类型:'+tTxnTypeMap.get(infoHrtNo[1])+',产品细分:'+actNum+',提现成本费率(%)小于运营中心成本,请修改!';
                                    }else if(infoHrtNo[3]=='wxUpRate'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.38）费率(%)小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.38）费率(%)小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='wxUpCash'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.38）转账费小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.38）转账费小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='wxUpRate1'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.45）费率(%)小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.45）费率(%)小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='wxUpCash1'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.45）转账费小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.45）转账费小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='zfbRate'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.45）费率(%)小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以下（终端0.45）费率(%)小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='zfbCash'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.45）转账费小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以下（终端0.45）转账费小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='ewmRate'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',银联二维码费率(%)小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',银联二维码费率(%)小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='ewmCash'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',银联二维码转账费小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',银联二维码转账费小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='ysfRate'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',云闪付费率(%)小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',云闪付费率(%)小于运营中心成本,请修改!';
																			}
                                    }else if(infoHrtNo[3]=='hbRate'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',花呗费率(%)小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',花呗费率(%)小于运营中心成本,请修改!';
																			}
																		}else if(infoHrtNo[3]=='hbCash'){
																			if(isAct){
																				infoCostErr = '活动成本' + actNum + ',花呗转账费小于运营中心成本,请修改!';
																			}else {
																				infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',花呗小于运营中心成本,请修改!';
																			}
																		}
                                    // console.log(infoHrtNo)
                                    $.messager.alert('提示', infoCostErr);
                                    return;
                                }
                            }
                        }
                    }
					<%}%>
		    		var validator = $('#sysAdmin_agentunit_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_agentunit_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_addAgentUnit.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentunit_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			//$('#sysAdmin_agentunit_addDialog').dialog('destroy');
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
	
	function sysAdmin_agentunit_editFun(index) {
    	var rows = $('#sysAdmin_agentunit_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_agentunit_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改代理商信息</span>',
			width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00312.jsp?buid='+row['buid'],  
		    modal: true,
		    onLoad:function() {
		    	row.menuIds = stringToList(row.buid);    	
		    	$('#sysAdmin_agentunit_editForm').form('load', row);
                // 获取运营中心的成本
                $.ajax({
                    url: "sysAdmin/agentunit_getYunYingHrtCost00310.action",
                    dataType: "json",
                    success: function(data){
                        yunYinghrtCost = JSON.parse(data.msg);
                        // console.log(yunYinghrtCost)
                    }
                });
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					// 获取封装信息
		    		if($("#flag_00312").val() == 1){//有成本信息
		    			//1、成本信息
			    		var tr_list = $("#00312_tab2_body").children("tr")
			    		var cost_data = [];
			    		for(var i = 1; i <= tr_list.length; i ++){
			    			var inputs = $("#00312T"+i+" input");
			    			var o = serializeTrObj(inputs);
			    			cost_data.push(o);
			    		}
							var tr_listAct = $("#00312_ACT_TR").children("tr")
							for(var i = 1; i < tr_listAct.length; i ++){
								var inputsAct = $("#00312_ACTT"+i+" input");
								var oAct = serializeTrObj(inputsAct);
								cost_data.push(oAct);
							}
			    		// console.log(cost_data)
		    			$("#00312_costData").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
                        // @author:xuegangliu-20190313 校验成本值 签约一代代理商时 成本值不能大于运营中心的成本值
                        for (var i = 0; i < cost_data.length; i++) {
                            var hrtCostH = cost_data[i];
                            var prefixKey = hrtCostH.machineType + '|' + hrtCostH.txnType + '|' + hrtCostH.txnDetail + '|';
                            valueHrtCost.set(prefixKey + "cashCost", hrtCostH.cashCost);
                            hrtKey.push(prefixKey + "cashCost");
                            valueHrtCost.set(prefixKey + "creditRate", hrtCostH.creditRate);
                            hrtKey.push(prefixKey + "creditRate");
                            valueHrtCost.set(prefixKey + "debitRate", hrtCostH.debitRate);
                            hrtKey.push(prefixKey + "debitRate");
                            valueHrtCost.set(prefixKey + "debitFeeamt", hrtCostH.debitFeeamt);
                            hrtKey.push(prefixKey + "debitFeeamt");
                            // valueHrtCost.set(prefixKey + "cashRate", hrtCostH.cashRate);
                            // hrtKey.push(prefixKey + "cashRate");
														valueHrtCost.set(prefixKey + "wxUpRate", hrtCostH.wxUpRate);
														hrtKey.push(prefixKey + "wxUpRate");
														valueHrtCost.set(prefixKey + "wxUpRate1", hrtCostH.wxUpRate1);
														hrtKey.push(prefixKey + "wxUpRate1");
														valueHrtCost.set(prefixKey + "wxUpCash", hrtCostH.wxUpCash);
														hrtKey.push(prefixKey + "wxUpCash");
														valueHrtCost.set(prefixKey + "wxUpCash1", hrtCostH.wxUpCash1);
														hrtKey.push(prefixKey + "wxUpCash1");
														valueHrtCost.set(prefixKey + "zfbRate", hrtCostH.zfbRate);
														hrtKey.push(prefixKey + "zfbRate");
														valueHrtCost.set(prefixKey + "zfbCash", hrtCostH.zfbCash);
														hrtKey.push(prefixKey + "zfbCash");
														valueHrtCost.set(prefixKey + "ewmRate", hrtCostH.ewmRate);
														hrtKey.push(prefixKey + "ewmRate");
														valueHrtCost.set(prefixKey + "ewmCash", hrtCostH.ewmCash);
														hrtKey.push(prefixKey + "ewmCash");
														valueHrtCost.set(prefixKey + "ysfRate", hrtCostH.ysfRate);
														hrtKey.push(prefixKey + "ysfRate");
                        }
                        for (var i = 0; i < hrtKey.length; i++) {
                            var costValue = valueHrtCost.get(hrtKey[i]);
                            if (costValue) {
                                var yunYingValue = yunYinghrtCost[hrtKey[i]];
                                if (yunYingValue) {
                                    if (yunYingValue > Number(costValue)) {
                                        infoHrtNo = hrtKey[i].split("|");
                                        infoCostErr = "";
                                        actNum = tTxnDetilMap.get(infoHrtNo[2])==undefined?infoHrtNo[2]:tTxnDetilMap.get(infoHrtNo[2]);
																				var isAct = infoHrtNo[2]>=20;
																				if(infoHrtNo[3]=='cashCost'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.38）转账费低于运营中心成本，请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',提现成本低于运营中心成本，请修改!';
																					}
																				}else if(infoHrtNo[3]=='creditRate'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.38）费率(%)小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',贷记卡成本(%)小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='debitRate'){
																					if(isAct){
																						infoCostErr='活动成本'+actNum+'刷卡成本小于运营中心成本,请修改!';
																					}else{
																						infoCostErr='机器类型:'+tMachineType.get(infoHrtNo[0])+',产品类型:'+tTxnTypeMap.get(infoHrtNo[1])+',产品细分:'+actNum+',借记卡成本(%)小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='debitFeeamt'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',刷卡提现小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',借记卡封顶手续费小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='cashRate'){
																					infoCostErr='机器类型:'+tMachineType.get(infoHrtNo[0])+',产品类型:'+tTxnTypeMap.get(infoHrtNo[1])+',产品细分:'+actNum+',提现成本费率(%)小于运营中心成本,请修改!';
																				}else if(infoHrtNo[3]=='wxUpRate'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.38）费率(%)小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.38）费率(%)小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='wxUpCash'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.38）转账费小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.38）转账费小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='wxUpRate1'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.45）费率(%)小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.45）费率(%)小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='wxUpCash1'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以上（终端0.45）转账费小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以上（终端0.45）转账费小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='zfbRate'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.45）费率(%)小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以下（终端0.45）费率(%)小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='zfbCash'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',扫码1000以下（终端0.45）转账费小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',扫码1000以下（终端0.45）转账费小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='ewmRate'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',银联二维码费率(%)小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',银联二维码费率(%)小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='ewmCash'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',银联二维码转账费小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',银联二维码转账费小于运营中心成本,请修改!';
																					}
																				}else if(infoHrtNo[3]=='ysfRate'){
																					if(isAct){
																						infoCostErr = '活动成本' + actNum + ',云闪付费率(%)小于运营中心成本,请修改!';
																					}else {
																						infoCostErr = '机器类型:' + tMachineType.get(infoHrtNo[0]) + ',产品类型:' + tTxnTypeMap.get(infoHrtNo[1]) + ',产品细分:' + actNum + ',云闪付费率(%)小于运营中心成本,请修改!';
																					}
																				}
                                        // console.log(infoHrtNo)
                                        $.messager.alert('提示', infoCostErr);
                                        return;
                                    }
                                }
                            }
                        }
		    		}
					$('#sysAdmin_agentunit_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentunit_editAgentUnit.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_agentunit_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			// $('#sysAdmin_agentunit_editDialog').dialog('destroy');
					    			$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_agentunit_editDialog').dialog('destroy');
					$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_agentunit_delFun(buid){
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/agentunit_deleteAgentUnit.action",
					type:'post',
					data:{"ids":buid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_agentunit_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_agentunit_datagrid').datagrid('unselectAll');
			}
		});
	}

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="bill_agentunit_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>代理商名称</th>
					<td><input name="agentName" style="width: 260px;" /></td>		
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="bill_agentunit_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="bill_agentunit_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_agentunit_datagrid"></table>
    </div> 
</div>

