<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- 运营中心 详情信息 -->
 <script type="text/javascript">
 	$(function(){
 		var buid=<%=request.getParameter("buid")%>;
		//带入成本信息
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_queryHrtUnnoCostSingle.action',
			dataType:"json",  
	   		type:"post",
	   		data:{buid:buid},
	   		success:function(data){
	   			if(data.success){
	   				var obj = data.obj;
	   				// console.log(obj)
	   				$("#10143_hrtCostForm").form('load', obj);
	   				//去加载活动成本
	   				// sysAdmin_10143_queryRebate(buid);
						sysAdmin_10143_queryRebateNew(buid);
	   			}
	   		}
		});
 		
 		$.ajax({
 			url:'${ctx}/sysAdmin/agentunit_serachAgentInfoDetailed.action',
     		dataType:"json",  
     		type:"post",
     		data:{buid:buid},
    			success:function(data) {
    				var json=eval(data);
	 	    		if (json!="") { 
	 	    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0]);
	 		    		var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[1]);
	 		    		var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[2]);
	 		    		var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[3]);
	 		    		var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[4]);
	 		    		var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[5]);
	 		    		var path7='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[6]);
	 		    		var path8='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[7]);
	 		    		var path9='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[8]);
	 		    		var pathA='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[9]);
	 		    		var pathB='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[10]);
	 		    		
	 	    			$('#dealUpLoadFile').attr('src',path1);
	 	    			$('#busLicUpLoadFile').attr('src',path2);
	 	    			$('#proofOfOpenAccountUpLoadFile').attr('src',path3);
	 	    			$('#legalAUpLoadFile').attr('src',path4);
	 	    			$('#legalBUpLoadFile').attr('src',path5);
	 	    			$('#accountAuthUpLoadFile').attr('src',path6);
	 	    			$('#accountLegalAUpLoadFile').attr('src',path7);
	 	    			$('#accountLegalBUpLoadFile').attr('src',path8);
	 	    			$('#accountLegalHandUpLoadFile').attr('src',path9);
	 	    			$('#officeAddressUpLoadFile').attr('src',pathA);
	 	    			$('#profitUpLoadFile').attr('src',pathB);
	     			}  
 	    	} 
 		});
 	});

	function sysAdmin_10143_queryRebateNew(buid){
		//加载活动成本
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_queryHrtUnnoCostSingleRebate.action',
			dataType:"json",
			type:"post",
			data:{buid:buid},
			success:function(data){
				if(data.success){
					var obj = data.list;
					if(obj.length != 0){
						for(var k=0;k<obj.length;k++){
							var tr_list = $("#10143_ACT_TR").children("tr");
							var id = tr_list.length;

							var html = '<tr id="10143_ACTT'+id+'">'
									+'<td style="text-align: center;">'+obj[k].TXN_DETAIL+'</th>'
									+'<input type="hidden" name="machineType" value="1"/>'
									+'<input type="hidden" name="txnType" value="1"/>'
									+'<input type="hidden" name="txnDetail" value="'+obj[k].TXN_DETAIL+'"/>'
									+'<input type="hidden" name="hucid" id="H2610" value="'+obj[k].HUCID+'"/>'
									+'<td><input name="creditRate" type="text" readonly="readonly" value="'+obj[k].CREDIT_RATE+'" /></td>'
									+'<td><input name="cashCost" type="text" readonly="readonly" value="'+obj[k].CASH_COST+'"/></td>'
									+'<td><input name="wxUpRate" type="text" readonly="readonly" value="'+obj[k].WX_UPRATE+'"/></td>'
									+'<td><input name="wxUpCash" type="text" readonly="readonly" value="'+obj[k].WX_UPCASH+'" /></td>'
									+'<td><input name="wxUpRate1" type="text" readonly="readonly" value="'+obj[k].WX_UPRATE1+'"/></td>'
									+'<td><input name="wxUpCash1" type="text" readonly="readonly" value="'+obj[k].WX_UPCASH1+'" /></td>'
									+'<td><input name="zfbRate" type="text" readonly="readonly" value="'+obj[k].ZFB_RATE+'" /></td>'
									+'<td><input name="zfbCash" type="text" readonly="readonly" value="'+obj[k].ZFB_CASH+'" /></td>'
									+'<td><input name="ewmRate" type="text" readonly="readonly" value="'+obj[k].EWM_RATE+'" /></td>'
									+'<td><input name="ewmCash" type="text" readonly="readonly" value="'+obj[k].EWM_CASH+'" /></td>';
							var hbHtml;
							if(obj[k].SUBTYPE==1){
								hbHtml='<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>'+'<th style="text-align: center;">—</th>'+'<td><input name="hbRate" type="text" readonly="readonly" value="'+obj[k].HB_RATE+'" /></td>'
										+'<td><input name="hbCash" type="text" readonly="readonly" value="'+obj[k].HB_CASH+'" /></td>'
							}else if(obj[k].SUBTYPE==2){
								hbHtml='<td><input name="ysfRate" type="text" readonly="readonly" value="' + obj[k].YSF_RATE + '" /></td>'
										+ '<td><input name="debitRate" type="text" readonly="readonly" value="' + obj[k].DEBIT_RATE + '" /></td>'
										+ '<td><input name="debitFeeamt" type="text" readonly="readonly" value="' + obj[k].DEBIT_FEEAMT + '" /></td>'
										+'<td><input name="hbRate" type="text" readonly="readonly" value="'+obj[k].HB_RATE+'" /></td>'
										+'<td><input name="hbCash" type="text" readonly="readonly" value="'+obj[k].HB_CASH+'" /></td>'
							}else {
								hbHtml = '<td><input name="ysfRate" type="text" readonly="readonly" value="' + obj[k].YSF_RATE + '" /></td>'
										+ '<td><input name="debitRate" type="text" readonly="readonly" value="' + obj[k].DEBIT_RATE + '" /></td>'
										+ '<td><input name="debitFeeamt" type="text" readonly="readonly" value="' + obj[k].DEBIT_FEEAMT + '" /></td>'
										+'<th style="text-align: center;">—</th>'
										+'<th style="text-align: center;">—</th>';
							}
							html=html+hbHtml+ '</tr>'
							$("#10143_ACT_TRebateType").before(html);
						}
					}
				}
			}
		});
	}

 	function sysAdmin_10143_queryRebate(buid){
		//加载活动成本
		$.ajax({
			url:'${ctx}/sysAdmin/agentunit_queryHrtUnnoCostSingleRebate.action',
			dataType:"json",  
	   		type:"post",
	   		data:{buid:buid},
	   		success:function(data){
	   			if(data.success){
	   				var obj = data.list;
	   				if(obj.length != 0){
	   					console.log(obj)
	   					for(var k=0;k<obj.length;k++){
	   						var tr_list = $("#10143_tab2_body").children("tr");
		   					var id = tr_list.length;
								var tt = '0';
								if(obj[k].DEBIT_FEEAMT){
									tt=obj[k].DEBIT_FEEAMT
								}
	   						var html = '<tr id="10143T'+id+'">'
			   					+'<th style="text-align: center;">活动</th>'
			   					+'<th style="text-align: center;" colspan="4">活动'+obj[k].TXN_DETAIL+'</th>'
			   					+'<input type="hidden" name="machineType" value="1"/>'
			   					+'<input type="hidden" name="txnType" value="1"/>'
			   					+'<input type="hidden" name="txnDetail" value="'+obj[k].TXN_DETAIL+'"/>'
			   					+'<input type="hidden" name="hucid" id="H2610" value="'+obj[k].HUCID+'"/>'
			   					+'<td style="text-align: center;">'
			   					+'	<input type="text" name="debitRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].DEBIT_RATE+'" readonly="readonly"/>'
			   					+'</td>'
			   					+'<td style="text-align: center;">'
									+'<input type="text" name="debitFeeamt" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+tt+'" readonly="readonly"/>'
									+'</td>'
			   					+'<td style="text-align: center;">'
			   					+'	<input type="text" name="creditRate" style="width:70px;" class="easyui-validatebox" data-options="validType:\'money\'" value="'+obj[k].CREDIT_RATE+'" readonly="readonly"/>'
			   					+'</td>'
			   					+'<td style="text-align: center;">'
			   					+'	<input style="text-align: center;width:70px" name="cashCost" type="text" value="'+obj[k].CASH_COST+'" readonly="readonly"/>'
			   					+'</td>'
			   					+'<th style="text-align: center;">—</th>'
			   				+'</tr>';
		   					$("#10143_tab2_body").append(html);
	   					}
	   				}
	   			}
	   		}
		});
	}
 	
 	function showBigImg(img){
 		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
 		$('#sysAdmin_10143_queryForm').after(imgDialog);
 		$("#img").attr("src",img);
 		$('#sysAdmin_imgDialog').dialog({
 				title: '<span style="color:#157FCC;">查看</span>',
 				width: 800,   
 			    height: 500,
 			    resizable:true,
 		    	maximizable:true, 
 			    modal:false,
 			    buttons:[{
 					text:'顺时针',
 					iconCls:'',
 					handler:function() {
 						rotateRight();
 					}
 				},{
 					text:'逆时针',
 					iconCls:'',
 					handler:function() {
 						rotateLeft();
 					}
 				},{
 				text:'关闭',
 				iconCls:'icon-ok',
 				handler:function() {
 					$('#sysAdmin_imgDialog').dialog('destroy');
 				}
 				}],
 				onClose:function() {
 					$('#sysAdmin_imgDialog').remove();
 					rotation=0;
 					rotate=0;
 				}
 			});
 	}
 	
 	var rotation=0;
 	var rotate=0;   
 	function rotateRight(){   
 		rotation=(rotation==3)?0:++rotation;
 		rotate=(rotate==270)?0:rotate+90;
 		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
 		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";   
 	}
 	
 	function rotateLeft(){
 		rotation=(rotation==0)?3:--rotation;
 		rotate=(rotate==0)?270:rotate-90;
 		$("#img").css({"-moz-transform":"rotate("+rotate+"deg)","-webkit-transform":"rotate("+rotate+"deg)"});
 		document.getElementById("img").style.filter="progid:DXImageTransform.Microsoft.BasicImage(Rotation="+rotation+")";
 	}
 	
 </script>
 <style type="text/css">
 	.hrt-label_pl15{
 		padding-left:15px;
 		color: red;
 	}
 </style>
<form id="sysAdmin_10143_queryForm" method="post">
	<fieldset>
		<legend>基础信息</legend>
		<table class="table">
			<tr>
	    		<th>代理商全称：</th>
	    		<td ><input type="text" name="agentName" style="width:200px;"  readonly="readonly" /></td>
	    		<th>归属机构号：</th>
	    		<td><input type="text" style="width:200px;"  readonly="readonly" name="parentUnno" />
	    		</td>
			</tr>
			<tr>
				<th>代理商级别：</th>
				<td ><input type="text" style="width:200px;"  readonly="readonly" value="分公司/运营中心"/></td>
				<th>代理商简称：</th>
				<td><input type="text" name="agentShortNm" id="agentShortNm" style="width:200px;" readonly="readonly"/></td>
			</tr>
			<tr>
				<th>营业执照号：</th>
				<td><input type="text" name="bno" style="width:200px;"  readonly="readonly" /></td>
	    		<th>注册地址：</th>
    			<td >
					<input type="text" name="regAddress" style="width:200px;" id="regAddress" readonly="readonly"/>
				</td>
			</tr>
			<tr>
	    		<th>营业地址：</th>
    			<td >
					<input type="text" name="baddr" style="width:200px;" id="baddr" readonly="readonly"/>
				<th>法人：</th>
				<td><input type="text" name="legalPerson" style="width:200px;"  readonly="readonly" /></td>
	   		</td>
    		</tr>
    		<tr>
    			<th>法人证件号码：</th>
    			<td><input type="text" name="legalNum" style="width:200px;"  readonly="readonly" /></td>
 			</tr>
	    	<tr>
	    		<td colspan="4">
					<label class="hrt-label_pl15">注意：以上信息均为“必填”。如代理商身份为个人，“营业执照号”填写代理商身份证号，“注册地址”填写身份证住址</label>
	    		</td>
    		</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>账户信息</legend>
		<table class="table">
			<tr>
				<th>账户名称：</th>
				<td><input type="text" name="bankAccName" style="width:200px;"  readonly="readonly" /></td>
				<th>开户行名称：</th>
				<td><input type="text" name="bankBranch" style="width:200px;"  readonly="readonly" /></td>
			</tr>
			<tr>
				<th>账号：</th>
				<td><input type="text" name="bankAccNo" style="width:200px;"  readonly="readonly" /></td>
				<th>支付系统行号：</th>
	    		<td><input type="text" name="payBankID" style="width:200px;"  readonly="readonly" /></td>
    		</tr>
    		<tr>
	    		<th>开户类型：</th>
	    		<td>
	    			<input type="radio" name="accType" value="1" disabled="disabled"/>对公
					<input type="radio" name="accType" value="2" disabled="disabled"/>对私
	    		</td>
	    		<th>开户地：</th>
	    		<td><input type="text" name="bankArea" style="width:200px;"  readonly="readonly" /></td>
	    	</tr>
	    	<tr>
	    		<td colspan="4">
					<label class="hrt-label_pl15">注意：以上信息均为“必填”，个体工商户或个人主体代理商结算账户必须为代理经营者本人，只支持企业商户授权入账。</label>
	    		</td>
	    	</tr>
		</table>
	</fieldset>
	<!-- <div id="fenrun_10141"></div> -->
	<fieldset id="fenrun_10141">
		<legend>分润信息</legend>
		<table class="table">
			<tr>
				<th>是否开通分润钱包：</th>
				<td><select name="purseType" class="easyui-combobox" disabled="disabled"
					data-options="panelHeight:'auto',editable:false"
					style="width: 155px;">
						<option value="">否</option>
						<option value="1">是</option>
						<option value="0">否</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>分润结算周期：</th>
				<td><input name="cycle" class="easyui-validatebox" readonly="readonly"
					data-options="required:true,validType:'money'" style="width: 151px;" /></td>
			</tr>
			<tr>
				<th>分润税点（%）：</th>
				<td><input name="cashRate" class="easyui-validatebox" readobly="readonly"
					data-options="validType:'money'" style="width: 151px;" /></td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>签约信息</legend>
		<table class="table">
			<tr>
				<th>签约销售：</th>
	    		<td>
	    			<!-- <select id="signUserId" name="signUserId" class="easyui-combogrid" data-options="editable:false" style="width:135px;" readonly="readonly"></select> -->
	    			<input type="text" name="signUserIdName" style="width:130px;"   readonly="readonly"/>
	    		</td>
	    		<th>保证金：</th>
    			<td colspan="3"><input type="text" name="riskAmount" style="width:130px;" value="0"  readonly="readonly"/></td>
			</tr>
			<tr>
    			<th>合同编号：</th>
	    		<td colspan="5"><input type="text" name="contractNo" style="width:130px;" maxlength="20" readonly="readonly"/></td>
	    	</tr>
	    	<tr>	
	    		<th>代理商负责人：</th>
	    		<td><input type="text" name="contact" style="width:130px;"    maxlength="20" readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="contactTel" style="width:130px;"     readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="contactPhone" style="width:130px;" maxlength="20"   readonly="readonly"/></td>
	    	</tr>
	    	<tr>
	    		<th>业务联系人：</th>
	    		<td><input type="text" name="businessContacts" style="width:130px;" maxlength="20"  readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="businessContactsPhone" style="width:130px;" maxlength="20"   readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="businessContactsMail" style="width:130px;" maxlength="20"   readonly="readonly"/></td>
	    	</tr>
	    	<tr>	
	    		<th>风控联系人：</th>
	    		<td><input type="text" name="riskContact" style="width:130px;" maxlength="20"  readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="riskContactPhone" style="width:130px;" maxlength="20"   readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="riskContactMail" style="width:130px;" maxlength="30"   readonly="readonly"/></td>
	    	</tr>
	    	<tr>
	    		<th>结算联系人：</th>
	    		<td><input type="text" name="secondContact" style="width:130px;" maxlength="20"  readonly="readonly"/></td>
	    		<th>联系电话：</th>
	    		<td><input type="text" name="secondContactTel" style="width:130px;" maxlength="20"   readonly="readonly"/></td>
	    		<th>邮箱：</th>
	    		<td><input type="text" name="secondContactPhone" style="width:130px;" maxlength="30"   readonly="readonly"/></td>
			</tr>
			<tr>	
				<th>分润联系人：</th>
				<td><input type="text" name="profitContactPerson" style="width:130px;" maxlength="20"  readonly="readonly"/></td>
				<th>联系电话：</th>
				<td><input type="text" name="profitContactPhone" style="width:130px;" maxlength="20"   readonly="readonly"/></td>
	    		<th>联系邮箱：</th>
	    		<td><input type="text" name="profitContactEmail" style="width:130px;" maxlength="30"   readonly="readonly"/></td>
	    	</tr>
	    	<tr>
	    		<td colspan="4">
					<label class="hrt-label_pl15">注意：请准确填写联系信息，以免由于收不到业务通知造成经济损失。</label>
	    		</td>
	    	</tr>
		</table>
	</fieldset>
	<!-- 图片信息 -->
	<fieldset>
		<legend>图片信息</legend>
		<table class="table">
			<tr>
				<th>协议签章页照片：</th>
   			<td>
   				<img id="dealUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   		<th>营业执照：</th>
   			<td>
   				<img id="busLicUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
   		</tr>
   		<tr>
	   		<th>对公开户证明：</th>
	   		<td>
	   			<img id="proofOfOpenAccountUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   		</td>
	   		
				<th>法人身份证正面：</th>
   			<td>
   				<img id="legalAUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>法人身份证反面：</th>
   			<td>
   				<img id="legalBUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
				<th>入账授权书：</th>
   			<td>
   				<img id="accountAuthUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人身份证正面：</th>
   			<td>
   				<img id="accountLegalAUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
				<th>入账人身份证反面：</th>
   			<td>
   				<img id="accountLegalBUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>入账人手持身份证：</th>
   			<td>
   				<img id="accountLegalHandUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
				<th>办公地点照片：</th>
   			<td>
   				<img id="officeAddressUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
	   	</tr>
	   	<tr>
				<th>分润照片：</th>
   			<td>
   				<img id="profitUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
   			</td>
   		</tr>
	   </table>
	</fieldset>
	<!-- <fieldset id="returnReasonTr"  style="display: none;">
		<legend>审核信息</legend>
		<table class="table">	
			<tr>
				<th>退回原因：</th>
				<td colspan="3">
					<textarea rows="6" cols="38" style="resize:none;" name="returnReason" id="returnReason" ></textarea>
				</td>
			</tr>
		</table>
	</fieldset> -->
	<input type="hidden" name="buid" id="buid">
</form>
<form id="10143_hrtCostForm" method="post">
	<fieldset>
		<legend>成本信息</legend>
		<table class="table" id="10143_tab2_body">
			<thead>
				<tr>
					<th style="text-align: center;" rowspan="2">是否活动</th>
					<th style="text-align: center;" rowspan="2">机器类型</th>
					<th style="text-align: center;" rowspan="2">交易类型</th>
					<th style="text-align: center;" rowspan="2">产品类型</th>
					<th style="text-align: center;" rowspan="2">产品细分</th>
					<th style="text-align: center;" colspan="3">交易成本</th>
					<th style="text-align: center;" colspan="2">提现成本</th>
				</tr>
				<tr>
					<th style="text-align: center;">借记卡成本（%）</th>
					<th style="text-align: center;">借记卡封项手续费</th>
					<th style="text-align: center;">贷记卡成本（%）</th>
					<th style="text-align: center;">提现成本</th>
					<th style="text-align: center;">提现手续费成本（%）</th>
				</tr>
			</thead>
			<tbody>
				<tr id="10143T1">
					<th style="text-align: center;" rowspan="19">非活动</th>
					<th style="text-align: center;" rowspan="13">手刷</th>
					<th style="text-align: center;" rowspan="3">刷卡</th>
					<th style="text-align: center;" rowspan="2">秒到</th>
					<th style="text-align: center;">0.6秒到</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="1"/>
					<input type="hidden" name="txnDetail" value="1"/>
					
					<td style="text-align: center;">
						<input name="K111A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K111C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K111D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T2">
					<th style="text-align: center;">0.72秒到</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="1"/>
					<input type="hidden" name="txnDetail" value="2"/>
					
					<td style="text-align: center;">
						<input name="K112A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K112C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K112D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T3">
					<th style="text-align: center;">理财</th>
					<th style="text-align: center;">理财</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="2"/>
					<input type="hidden" name="txnDetail" value="3"/>
					
					<td style="text-align: center;">
						<input name="K123A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K123B" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K123C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K123D" style="text-align: center;width: 70px" type="text" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K123E" style="text-align: center;width:70px" type="text" readonly="readonly"/>
					</td>
				</tr>
				<tr id="10143T4">
					<th style="text-align: center;">代还</th>
					<th style="text-align: center;">代还</th>
					<th style="text-align: center;">信用卡代还</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="3"/>
					<input type="hidden" name="txnDetail" value="4"/>
					
					<td style="text-align: center;">
						<input name="K134A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K134C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T5">
					<th style="text-align: center;">云闪付</th>
					<th style="text-align: center;">云闪付</th>
					<th style="text-align: center;">云闪付</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="4"/>
					<input type="hidden" name="txnDetail" value="5"/>
					
					<td style="text-align: center;">
						<input name="K145A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K145C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T6">
					<th style="text-align: center;" rowspan="2">快捷</th>
					<th style="text-align: center;" rowspan="2">快捷</th>
					<th style="text-align: center;">快捷支付成本VIP</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="5"/>
					<input type="hidden" name="txnDetail" value="6"/>
					
					<td style="text-align: center;">
						<input name="K156A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K156C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K156D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T7">
					<th style="text-align: center;">快捷支付成本完美账单</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="5"/>
					<input type="hidden" name="txnDetail" value="7"/>
					
					<td style="text-align: center;">
						<input name="K157A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K157C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K157D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
			
				<tr id="10143T8">
					<th style="text-align: center;" rowspan="6">扫码</th>
					<th style="text-align: center;" rowspan="3">T0</th>
					<th style="text-align: center;">微信</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="6"/>
					<input type="hidden" name="txnDetail" value="8"/>
					
					<td style="text-align: center;">
						<input name="K168A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K168C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K168D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T9">
					<th style="text-align: center;">支付宝</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="6"/>
					<input type="hidden" name="txnDetail" value="9"/>
					
					<td style="text-align: center;">
						<input name="K169A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K169C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K169D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T10">
					<th style="text-align: center;">银联二维码</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="6"/>
					<input type="hidden" name="txnDetail" value="10"/>
					
					<td style="text-align: center;">
						<input name="K1610A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K1610C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input name="K1610D" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
				</tr>
				
				<tr id="10143T11">
					<th style="text-align: center;" rowspan="3">T1</th>
					<th style="text-align: center;">微信</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="7"/>
					<input type="hidden" name="txnDetail" value="8"/>
					
					<td style="text-align: center;">
						<input name="K178A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K178C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T12">
					<th style="text-align: center;">支付宝</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="7"/>
					<input type="hidden" name="txnDetail" value="9"/>
					
					<td style="text-align: center;">
						<input name="K179A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K179C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<th style="text-align: center;">—</th>
				</tr>
				<tr id="10143T13">
					<th style="text-align: center;">银联二维码</th>
					<input type="hidden" name="machineType" value="1"/>
					<input type="hidden" name="txnType" value="7"/>
					<input type="hidden" name="txnDetail" value="10"/>
					
					<td style="text-align: center;">
						<input name="K1710A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K1710C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<th style="text-align: center;">—</th>
				</tr>
			
				<tr id="10143T14">
					<th style="text-align: center;" rowspan="6">传统</th>
					<th style="text-align: center;" rowspan="3">扫码</th>
					<th style="text-align: center;" rowspan="3">T1/T0</th>
					<th style="text-align: center;">微信</th>
					<input type="hidden" name="machineType" value="2"/>
					<input type="hidden" name="txnType" value="6"/>
					<input type="hidden" name="txnDetail" value="8"/>
					
					<td style="text-align: center;">
						<input name="K268A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K268C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input style="text-align: center;width:70px" name="K268D" type="text" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input style="text-align: center;width:70px" type="text" name="K268E" readonly="readonly"/>
					</td>
				</tr>
				<tr id="10143T15">
					<th style="text-align: center;">支付宝</th>
					<input type="hidden" name="machineType" value="2"/>
					<input type="hidden" name="txnType" value="6"/>
					<input type="hidden" name="txnDetail" value="9"/>
					
					<td style="text-align: center;">
						<input name="K269A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'"  readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K269C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input style="text-align: center;width:70px" name="K268D" type="text" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input style="text-align: center;width:70px" type="text" name="K268E" readonly="readonly"/>
					</td>
				</tr>
				<tr id="10143T16">
					<th style="text-align: center;">银联二维码</th>
					<input type="hidden" name="machineType" value="2"/>
					<input type="hidden" name="txnType" value="6"/>
					<input type="hidden" name="txnDetail" value="10"/>
					
					<td style="text-align: center;">
						<input name="K2610A" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<th style="text-align: center;">—</th>
					<td style="text-align: center;">
						<input name="K2610C" type="text" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input style="text-align: center;width: 70px" type="text" name="K2610D" readonly="readonly"/>
					</td>
					<td style="text-align: center;">
						<input style="text-align: center;width: 70px" type="text" name="K2610E" readonly="readonly"/>
					</td>
				</tr>
				<tr id="10143T17">
						<th style="text-align: center;" rowspan="3">刷卡</th>
						<th style="text-align: center;" rowspan="3">T1/T0</th>
						<th style="text-align: center;">标准</th>
						<input type="hidden" name="machineType" value="2"/>
						<input type="hidden" name="txnType" value="6"/>
						<input type="hidden" name="txnDetail" value="11"/>
						<td style="text-align: center;">
							<input type="text" name="K2611A" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
							<input type="text" name="K2611B" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
							<input type="text" name="K2611C" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
						<input style="text-align: center;width: 70px" type="text" name="K2611D" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
						<input style="text-align: center;width:70px" type="text" name="K2611E" readonly="readonly"/>
						</td>
					</tr>
					<tr id="10143T18">
						<th style="text-align: center;">优惠</th>
						<input type="hidden" name="machineType" value="2"/>
						<input type="hidden" name="txnType" value="6"/>
						<input type="hidden" name="txnDetail" value="12"/>
						<td style="text-align: center;">
							<input type="text" name="K2612A" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
							<input type="text" name="K2612B" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
							<input type="text" name="K2612C" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
						<input style="text-align: center;width: 70px" type="text" name="K2611D" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
						<input style="text-align: center;width:70px" type="text" name="K2611E" readonly="readonly"/>
						</td>
					</tr>
					<tr id="10143T19">
						<th style="text-align: center;">减免</th>
						<input type="hidden" name="machineType" value="2"/>
						<input type="hidden" name="txnType" value="6"/>
						<input type="hidden" name="txnDetail" value="13"/>
						<td style="text-align: center;">
							<input type="text" name="K2613A" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<th style="text-align: center;">—</th>
						<td style="text-align: center;">
							<input type="text" name="K2613C" style="width:70px;" class="easyui-validatebox" data-options="validType:'money'" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
						<input style="text-align: center;width: 70px" type="text" name="K2611D" readonly="readonly"/>
						</td>
						<td style="text-align: center;">
						<input style="text-align: center;width:70px" type="text" name="K2611E" readonly="readonly"/>
						</td>
					</tr>
			</tbody>
		</table>
	</fieldset>
</form>
<fieldset>
	<legend>活动成本信息</legend>
	<table class="table" id="10143_ACT_TABLE">
		<thead>
		<tr>
			<th style="text-align: center;">活动类型</th>
			<th style="text-align: center;">扫码1000以下（终端0.38）费率(%)</th>
			<th style="text-align: center;">扫码1000以下（终端0.38）转账费</th>
			<th style="text-align: center;">扫码1000以上（终端0.38）费率(%)</th>
			<th style="text-align: center;">扫码1000以上（终端0.38）转账费</th>
			<th style="text-align: center;">扫码1000以上（终端0.45）费率(%)</th>
			<th style="text-align: center;">扫码1000以上（终端0.45）转账费</th>
			<th style="text-align: center;">扫码1000以下（终端0.45）费率(%)</th>
			<th style="text-align: center;">扫码1000以下（终端0.45）转账费</th>
			<th style="text-align: center;">银联二维码费率(%)</th>
			<th style="text-align: center;">银联二维码转账费</th>
			<th style="text-align: center;">云闪付费率(%)</th>
			<th style="text-align: center;">刷卡成本(%)</th>
			<th style="text-align: center;">刷卡提现成本</th>
			<th style="text-align: center;">花呗成本(%)</th>
			<th style="text-align: center;">花呗提现成本</th>
		</tr>
		</thead>
		<tbody id="10143_ACT_TR">
			<tr id="10143_ACT_TRebateType"></tr>
		</tbody>
	</table>
</fieldset>

