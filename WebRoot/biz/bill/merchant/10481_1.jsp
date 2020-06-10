<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	var mid = <%=request.getParameter("mid")%>
	$(function() {
		$('#sysAdmin_merchantterminalinfo_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantterminalinfo_listMerchantTerminalInfoBmidZ.action?mid='+mid,
			fit : true,
			fitColumns : true,
			idField : 'bmtid',
			columns : [[{
				title : '唯一主键',
				field : 'bmtid',
				width : 100,
				hidden : true
			},{
				title :'终端',
				field :'tid',
				width : 100
			},{
				title :'名称',
				field :'installedName',
				width : 100
			},{
				title :'联系人',
				field :'contactPerson',
				width : 100
			}]]
		});
	});
	
	$(function(){
		var bmid=<%=request.getParameter("bmid")%>;
		$.ajax({
			url:'${ctx}/sysAdmin/merchant_serachMerchantInfoDetailed.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmid:bmid},
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
		    		var pathC='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[11]);
		    		var pathD='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[12]);
		    		var pathE='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[13]);
	    			$("#legalUploadFile").attr("src",path1);
    				$("#bupLoadFile").attr("src",path2);
    				$("#rupLoadFile").attr("src",path3);
    				$("#registryUpLoadFile").attr("src",path4);
    				$("#materialUpLoadFile").attr("src",path5);
    				$("#photoUpLoadFile").attr("src",path6);
    				$("#bigdealUpLoadFile").attr("src",path7);
    				$("#materialUpLoad1File").attr("src",path8);
    				$("#materialUpLoad2File").attr("src",path9);
    				$("#materialUpLoad3File").attr("src",pathA);
    				$("#materialUpLoad4File").attr("src",pathB);
    				$("#materialUpLoad5File").attr("src",pathC);
    				$("#materialUpLoad6File").attr("src",pathD);
    				$("#materialUpLoad7File").attr("src",pathE);
    			}  
	    	} 
	    	
		});
	});

	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_merchantList_queryForm').after(imgDialog);
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
<form id="sysAdmin_merchantList_queryForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>商户信息</legend>
		<table class="table">
			<tr>
				<th>上级商户</th>
				<td colspan="3">
					<input type="text" readonly="readonly" name="parentMIDName" style="width:205px;"/>
				</td>
		   	</tr>
		   	<tr>
		   		<th>商户注册名称：</th>
		   		<td><input type="text" readonly="readonly" name="rname" style="width:200px;"/></td>
		   	
		   		<th>商户账单名称：</th>
	   			<td><input type="text" readonly="readonly" name="shortName" style="width:200px;"/></td>
		   	</tr>
		   	<tr>
			   	<th>凭条打印名称：</th>
	   			<td><input type="text" readonly="readonly" name="printName" style="width:200px;"/></td>
	   			
	   			<th>商户所在地区码：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="areaCodeName" style="width:200px;" />
	   			</td>
	   		</tr>
	   		<tr>
	   			<th>经营范围：</th>
	   			<td><input type="text" readonly="readonly" name="businessScope" style="width:200px;"/></td>
		   	
		   		<th>电子邮箱：</th>
	   			<td><input type="text" readonly="readonly" name="email" style="width:200px;"/></td>
	   		</tr>
		   	<!-- <tr>	
		   		<th>业务人员：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="busidName" style="width:200px;" />
	   			</td>
	   			
	   			<th>维护人员：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="maintainUserIdName" style="width:200px;" />
	   			</td>
	   		</tr> -->
		   	<tr>
	   			<th>法人：</th>
	   			<td ><input type="text" readonly="readonly" name="legalPerson" style="width:200px;"/></td>
		   		<th>法人身份证有效期：</th>
		   		<td><input type="text"  readonly="readonly" name="legalExpdate" style="width:200px;"/></td>
		   	
		   	</tr>
		   	<tr>
		   		<th>法人证件号码：</th>
	   			<td><input type="text" readonly="readonly" name="legalNum" style="width:200px;" /></td>
		   	
		   		<th>法人证件类型：</th>
		   		<td>
		   			<input type="text" readonly="readonly" name="legalTypeName" style="width:200px;"/>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>营业执照编号：</th>
	   			<td><input type="text" readonly="readonly" name="bno" style="width:200px;" /></td>
		   		<th>营业执照有效期：</th>
	   			<td><input type="text" readonly="readonly" name="bnoExpdate" style="width:200px;" /></td>
		   	</tr>
		   	<tr>
		   		<th>营业地址：</th>
	   			<td><input type="text" readonly="readonly" name="baddr" style="width:200px;" /></td>
	   			
		   		<th>注册地址：</th>
	   			<td><input type="text" readonly="readonly" name="raddr" style="width:200px;" /></td>
	   		</tr>
		   	<tr>
		   		<th>组织机构代码：</th>
	   			<td><input type="text" readonly="readonly" name="rno" style="width:200px;" /></td>
	   		
		   		<th>税务登记证号：</th>
	   			<td><input type="text" readonly="readonly" name="registryNo" style="width:200px;" /></td>
	   		</tr>
		   	<tr>
		   		<th>补充材料编号：</th>
		   		<td ><input type="text" readonly="readonly" name="materialNo" style="width:200px;" /></td>
				<th>注册资金：</th>
	   			<td><input type="text" readonly="readonly" name="regMoney" style="width:200px;" /></td>
			</tr>
			<tr>
		   		<th>合同编号：</th>
		   		<td><input type="text" readonly="readonly" name="contractNo" style="width:200px;" /></td>
		   		
		   		<th>合同起始时间：</th>
		   		<td><input type="text" readonly="readonly" name="contractPeriod" style="width:200px;" /></td>
		   	</tr>
		   	<tr>
		   		<th>押金：</th>
		   		<td><input type="text" name="deposit" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="100"/></td>
		   		
		   		<th>服务费：</th>
		   		<td><input type="text" name="charge" style="width:200px;" class="easyui-validatebox" data-options="validType:'intOrFloat'" maxlength="100"/></td>
		   	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>上传文件</legend>
		<table class="table">
	   		<tr>
		   		<th>法人身份上传文件：</th>
	   			<td>
			         	<img id="legalUploadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
	   			
	   			<th>营业执照上传文件：</th>
	   			<td>
	   				<img id="bupLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	</tr>
		   	<tr>
	   			<th>组织机构证上传文件：</th>
	   			<td>
	   			<img id="rupLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>

	   			<th>税务登记证上传文件：</th>
	   			<td>
	   			<img id="registryUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
			</tr>
		   	<tr>
		   		<th>门面照片上传文件：</th>
	   			<td>
	   			<img id="photoUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	
		   		<th>大协议照片上传文件：</th>
		   		<td>
		   		<img id="bigdealUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料上传文件：</th>
		   		<td>
		   		<img id="materialUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   		
		   		<th>补充材料1上传文件：</th>
	   			<td>
	   			<img id="materialUpLoad1File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料2上传文件：</th>
	   			<td>
	   			<img id="materialUpLoad2File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
	   			</td>
		   	
		   		<th>补充材料3上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad3File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料4上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad4File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>

		   		<th>补充材料5上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad5File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>补充材料6上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad6File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>

		   		<th>补充材料7上传文件：</th>
		   		<td>
		   		<img id="materialUpLoad7File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
		   		</td>
		   	</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>结算信息</legend>
		<table class="table">
			<tr>
		   		<th>行业选择：</th>
		   		<td>
		   			<input type="text" readonly="readonly" name="industryTypeName" style="width:200px;" />
		   		</td>
		
		   		<th>行业编码：</th>
		   		<td>
		   			<input type="text" readonly="readonly" name="mccidName" style="width:200px;" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>结算周期：</th>
		   		<td>
		   			T&nbsp;&nbsp;+&nbsp;&nbsp;
		   			<input type="text" readonly="readonly" name="settleCycle" style="width:160px;" />
		   		</td>
		   
		   		<th>结算时间点：</th>
		   		<td>
		   			<input type="text" readonly="readonly" name="settleRange" style="width:200px;" />
		   		</td>
			</tr>
		   	<tr>
		   		<th>支付系统行号：</th>
		   		<td><input type="text" readonly="readonly" name="payBankId" style="width:200px;"/></td>
		   		
		   		<th>节假日是否合并结账：</th>
		   		<td>
		   			<input type="text" readonly="readonly" name="settleMergerName" style="width:200px;"/>
		   		</td>
			</tr>
		   	<tr>
				<th>是否大小额商户：</th>
		   		<td colspan="3">
		   			<input type="text" readonly="readonly" name="merchantTypeName" style="width:200px;" />
		   		</td>
			</tr>
			<tr>
		   		<th>借记卡费率：</th>
		   		<td ><input type="text" readonly="readonly" name="bankFeeRate" style="width:100px;" />%</td>
		   		<th>借记卡手续费：</th>
		   		<td><input type="text" readonly="readonly" name="feeAmt" style="width:200px;" class="easyui-numberbox"/></td>
		   	</tr>
		   	<tr>
		   		<th>贷记卡费率：</th>
		   		<td ><input type="text" readonly="readonly" name="creditBankRate" style="width:100px;" />%</td>
		   		<td></td>
		   		<td></td>
		   		<!-- <th>贷记卡手续费：</th>
		   		<td><input type="text" readonly="readonly" name="creditFeeamt" style="width:200px;" class="easyui-numberbox"/></td>  -->
		   	</tr>
		   	  	<tr>
		   		<th>扫码支付费率：</th>
		   		<td ><input type="text" readonly="readonly" name="scanRate" style="width:100px;" />%</td>
		   		<td></td>
		   		<td></td>
		   		<!-- <th>贷记卡手续费：</th>
		   		<td><input type="text" readonly="readonly" name="creditFeeamt" style="width:200px;" class="easyui-numberbox"/></td>  -->
		   	</tr>
		   	<tr>
		   		<th>开户类型：</th>
		   		<td colspan="3">
		   			<input type="text" readonly="readonly" name="accTypeName" style="width:200px;" />
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行：</th>
		   		<td colspan="3">
		   			<input type="text" readonly="readonly" name="bankBranch" style="width:640px;"/>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>开户银行账号：</th>
		   		<td><input type="text" readonly="readonly" id="bankAccNo" name="bankAccNo" style="width:200px;"/></td>
			
		   		<th>开户账号名称：</th>
		   		<td><input type="text" readonly="readonly" name="bankAccName" style="width:200px;"/></td>
		   	</tr>
		   	<tr>
		   		<th>入账人身份证号：</th>
		   		<td><input type="text" readonly="readonly" name="accNum" style="width:200px;"/></td>
			
		   		<th>入账人身份证有效期：</th>
		   		<td><input type="text" readonly="readonly" name="accExpdate" style="width:200px;"/></td>
		   	</tr>
		</table> 
	</fieldset>
	
	<fieldset>
		<legend>外卡费率</legend>
		<table class='table'>
			<tr>
				<th>VISA外卡费率：</th>
				<td>
					<input type='text' id='feeRateV' name='feeRateV' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
			
				<th> MASTER外卡费率：</th>
				<td>
					<input type='text' id='feeRateM' name='feeRateM' style='width:100px;'  maxlength='18' readonly="readonly"/>%
				</td>
			</tr>
			
			<tr>
				<th>JCB外卡费率：</th>
				<td>
					<input type='text' name='feeRateJ' id='feeRateJ' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
				
				<th>美运外卡费率：</th>
				<td>
					<input type='text' name='feeRateA' id='feeRateA' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
			</tr>
			
			<tr>
				<th>大莱外卡费率：</th>
				<td colspan="3">
					<input type='text' name='feeRateD' id='feeRateD' style='width:100px;' maxlength='18' readonly="readonly"/>%
				</td>
			</tr>
		</table>
	</fieldset>
		
	<fieldset>
		<legend>联系人信息</legend>
		<table class="table">
			<tr>
				<th>联系人：</th>
		   		<td><input type="text" readonly="readonly" name="contactPerson" style="width:200px;"/></td>
		   		
		   		<th>联系地址：</th>
		   		<td><input type="text" readonly="readonly" name="contactAddress" style="width:200px;"/></td>
			</tr>
		   	<tr>
		   		<th>联系手机：</th>
		   		<td><input type="text" readonly="readonly" name="contactPhone" style="width:200px;"/></td>
	
		   		<th>联系固话：</th>
		   		<td><input type="text" readonly="readonly" name="contactTel" style="width:200px;"/></td>
			</tr>
			<tr>
		   		<th>备注：</th>
		   		<td  colspan="3">
	    			<textarea rows="6" cols="38" readonly="readonly"  style="resize:none;" name="remarks" maxlength="100"></textarea>
	    		</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>终端信息</legend>
		<div class="easyui-layout" style="height: 250px;" data-options="border:false">
		   <div data-options="region:'center', border:false" style="overflow: hidden;height: 10px;">  
		        <table id="sysAdmin_merchantterminalinfo_datagrid"></table>
		    </div> 
		</div>
	</fieldset>
</form>
