<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
var t;
function Change3(){
if(t=='2'){
	var tes=$('#approveStatus').val();
	if(tes=='K'){
	$('#rebanck4').show();
	$('#texta2').hide();
	}else {
	$('#rebanck4').hide();
	$('#texta2').show();
	}
}
else if(t=='3'){
var tes=$('#approveStatus').val();
	if(tes=='K'){
	$('#rebanck2').show();
	$('#texta2').hide();
	}else{
	$('#rebanck2').hide();
	$('#texta2').show();
	}
}else{
$('#rebanck2').hide();
$('#rebanck4').hide();
}
	}
function Change2(){
    if(t=='2'){
        $('#rebanck4').show();
        var tes=$('#rebanck4').val();
	    if(tes=='其他'){
	    $('#texta2').show();
	    }else{
	  $('#texta2').hide();
	   }
    }else{
       $('#rebanck2').show();
       var tes=$('#rebanck2').val();
	   if(tes=='其他'){
	   $('#texta2').show();
	   }else{
	   $('#texta2').hide();
	    }
    }
	}
$(function(){
	$("#sysAdmin_imgDialog").hide();
	var id=<%=request.getParameter("id")%>;
	var unno='<%=request.getParameter("unno")%>';
	var timestamp=new Date().getTime();
	$.ajax({
		url:"${ctx}/sysAdmin/taskDataApprove_findDetail.action?timestamp="+timestamp,
		type:'post',
		data:{"id":id},
		dataType:'json',
		success:function(data, textStatus, jqXHR) {
			var json=eval(data);
			$("#taskId").val(json.TASK.taskId);
			$("#unno").val(json.TASK.unName);
			$("#mids").val(json.TASK.infoName);
			$("#taskContext").val(json.TASK.taskContext);
			if(json.TASK.type==1){
			t="1";
				$("#type").val("商户基本信息变更申请");
				$("#title").show();
				$("#tab1").show();
				$("#tab2").hide();
				$("#tab3").hide();
				$("#busid").val(json.TASK1.busid);
				$("#busidName").val(json.TASK1.busidName);
				$("#rname").val(json.TASK1.rname);
				$("#legalPerson").val(json.TASK1.legalPerson);
				$("#legalType").val(json.TASK1.legalType);
				$("#legalNum").val(json.TASK1.legalNum);
				$("#legalExpDate").val(json.TASK1.legalExpDate);
				$("#contactAddress").val(json.TASK1.contactAddress);
				$("#contactPerson").val(json.TASK1.contactPerson);
				$("#contactPhone").val(json.TASK1.contactPhone);
				$("#contactTel").val(json.TASK1.contactTel);
				$("#legalUploadFileName1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK1.legalUploadFileName));
				$("#bUpload1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK1.bupload));
				$("#rUpload1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK1.rupload));
				$("#registryUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK1.registryUpLoad));
				$("#materialUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK1.materialUpLoad));
				$("#legalUploadFileName2").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK1.legalUpload2FileName));
			}
			if(json.TASK.type==2){
			       t="2";
				$("#type").val("商户账户/名称变更申请");
				$("#title").show();
				$("#tab1").hide(); 
				$("#tab2").show();
				$("#tab3").hide();
				$("#bankBranch").val(json.TASK2.bankBranch);
				$("#bankAccNo").val(json.TASK2.bankAccNo);
				$("#bankAccName").val(json.TASK2.bankAccName);
				$("#bankType").val(json.TASK2.bankType);
				$("#areaType").val(json.TASK2.areaType);
				$("#settleCycle").val(json.TASK2.settleCycle);
				$("#payBankId").val(json.TASK2.payBankId);
				if(json.TASK2.accType==1){
    				$("#accType").val("对公");
    			}else{
    				$("#accType").val("对私"); 
    			}
				$("#accUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK2.accUpLoad));
				$("#authUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK2.authUpLoad));
				$("#DUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK2.dUpLoad));
				$("#openUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK2.openUpLoad));
			}
			if(json.TASK.type==3){
			     t="3";
				$("#type").val("商户费率变更申请");
				$("#title").show();
				$("#tab1").hide();
				$("#tab2").hide();
				$("#tab3").show();
				$("#bankFeeRate").val(json.TASK3.bankFeeRate);
				$("#scanRate").val(json.TASK3.scanRate);
				$("#scanRate1").val(json.TASK3.scanRate1);
				$("#scanRate2").val(json.TASK3.scanRate2);
				$("#creditBankRate").val(json.TASK3.creditBankRate);
				$("#feeAmt").val(json.TASK3.feeAmt);
				$("#dealAmt").val(json.TASK3.dealAmt);
				$("#isForeign").val(json.TASK3.isForeign);
				$("#isForeignName").val(json.TASK3.isForeignName);
				$("#feeRateV").val(json.TASK3.feeRateV);
				$("#feeRateM").val(json.TASK3.feeRateM);
				$("#feeRateJ").val(json.TASK3.feeRateJ);
				$("#feeRateA").val(json.TASK3.feeRateA);
				$("#feeRateD").val(json.TASK3.feeRateD);
				if(json.TASK3.secondRate!=null&&json.TASK3.secondRate!=''){
	    			$("#secondRate").val(parseFloat((json.TASK3.secondRate).toFixed(4)));
    			}else{
    				$(".secondRate1").hide();
    			}
				if(json.TASK3.scanRate1==null||json.TASK3.scanRate1<=0){
    				$("#scanRate_wx").html("扫码费率：");
    				$("#scanRate_ct").hide();
    			}
				$("#feeUpLoad1").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+getImgPath(json.UPLOAD,json.TASK3.feeUpLoad));
			}
			if(json.TASK.type==4 ){
			t="4";
				$("#title").hide();
				$("#type").val("商户终端变更申请");
				}
			if(json.TASK.type==5){
			t="5";
				$("#title").hide();
				$("#type").val("预授权业务开通申请");
				}
			if(json.TASK.type==6){
			t="6";
				$("#title").hide();
				$("#type").val("商户关机申请");
				}
			if(json.TASK.type==7){
			t="7";
				$("#title").hide();
				$("#type").val("商户撤机申请");
				}
		},
		error:function() {

		}
	});
});

function showBigImg(img){
	var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
	$('#sysAdmin_taskDataApprove_editForm').after(imgDialog);
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

	function getImgPath(path,name){
		if(name != null){
			var arr = name.split("\.");
			if(arr[1].length==8){
				return path+"/"+arr[1]+"/"+name;
			}else{
				return path+"/"+arr[2]+"/"+name;
			}
		}else{
			return "";
		}
	}
</script>
<form id="sysAdmin_taskDataApprove_editForm" method="post">
	<fieldset>
	<legend>基本信息</legend>
	<table class="table1">
		<tr>
    		<th>工单编号：</th>
    		<td>
    		<input type="text" readonly="readonly" id="taskId">
    		</td>
    		<th></th>
    		<td>
    		</td>
    	</tr>
    	<tr>
    		<th>商户名称：</th>
    		<td>
    		<input type="text" readonly="readonly" id="mids">
    		</td>
    		<th>工单类别：</th>
    		<td>
    		<input type="text" readonly="readonly" id="type">
    		</td>
    	</tr>
    	<tr>
    		<th>受理状态：</th>
    		<td>
    		<select onchange="Change3()"  name="approveStatus" id="approveStatus">
    		<option value="Z">待受理</option>
    		<option value="K" selected="selected">不受理</option>
    		<option value="Y">已受理</option>
    		</select>
    		</td>
    		<th>工单描述：</th>
    		<td>
    		<input type="text" readonly="readonly" id="taskContext">
    		</td>
    	</tr>
    	<tr>
    		<th>受理描述：</th>
    		<td colspan="3">
    		   <select style="display:none" onchange="Change2()" id="rebanck2" name="processContext1" style="width:205px;">
		   		<option value="0">请选择</option>
		   		<option value="请负责人在变更申请表中签字确认">请负责人在变更申请表中签字确认</option>
		   		<option value="所变更费率低于指导费率">所变更费率低于指导费率</option>
		        <option value="其他">其他</option>
		   		</select>

		   		<select style="display:none" onchange="Change2()" id="rebanck4" name="processContext2" style="width:205px;">
		   		<option value="0">请选择</option>
		   		<option value="提交材料不齐全">提交材料不齐全</option>
		   		<option value="提交材料不清晰，无法核实信息">提交材料不清晰，无法核实信息</option>
		   		<option value="填写资料与提供资料不一致，请核实">填写资料与提供资料不一致，请核实</option>
		        <option value="其他">其他</option>
		   		</select>

				<textarea style="display:block" id="texta2" rows="6" cols="38" style="resize:none;" name="processContext" maxlength="200"></textarea>
			</td>
    	</tr>
    </table>
    </fieldset>

    <fieldset>
	<legend id="title">详细信息</legend>
	<table class="table1" id="tab1" style="display: none;">
		<tr>
    		<th>所属销售：</th>
    		<td>
    		<input type="text" readonly="readonly" id="busidName">
    		</td>
    		<th>商户全称：</th>
    		<td>
    		<input type="text" readonly="readonly" id="rname">
    		</td>
    	</tr>
    	<tr>
    		<th>法人证件号码：</th>
    		<td>
    		<input type="text" readonly="readonly" id="legalNum">
    		</td>
    		<th>法人证件有效期：</th>
    		<td>
    		<input type="text" readonly="readonly" id="legalExpDate">
    		</td>
    	</tr>
    	<tr>
    		<th>联系地址：</th>
    		<td>
    		<input type="text" readonly="readonly" id="contactAddress">
    		</td>
    		<th>联系人：</th>
    		<td>
    		<input type="text" readonly="readonly" id="contactPerson">
    		</td>
    	</tr>
    	<tr>
    		<th>法人身份上传文件：</th>
    		<td>
    		<!-- <input type="text" readonly="readonly" id="legalUploadFileName"> -->
				<img id="legalUploadFileName1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    		<th>法人证件类型：</th>
    		<td>
    		<input type="text" readonly="readonly" id="legalType">
    		</td>
    	</tr>
    	<tr>
    		<th>营业执照上传文件：</th>
    		<td>
    		<!-- <input type="text" readonly="readonly" id="bUpload"> -->
				<img id="bUpload1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />

    		</td>
    		<th>法人：</th>
    		<td>
    		<input type="text" readonly="readonly" id="legalPerson">
    		</td>
    	</tr>
    	<tr>
    		<th>组织机构证上传文件：</th>
    		<td>
    		<!-- <input type="text" readonly="readonly" id="rUpload"> -->
			 <img id="rUpload1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />

    		</td>
    		<th>联系固话：</th>
    		<td>
    		<input type="text" readonly="readonly" id="contactTel">
    		</td>
    	</tr>
    	<tr>
    		<th>税务登记证上传文件：</th>
    		<td>
    		<!-- <input type="text" readonly="readonly" id="registryUpLoad"> -->
				 <img id="registryUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    		<th>联系手机：</th>
    		<td>
    		<input type="text" readonly="readonly" id="contactPhone">
    		</td>
    	</tr>
    	<tr>
    		<th>补充材料上传文件：</th>
    		<td>
        	<img id="materialUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />

    		</td>
    		<th>法人身份反面上传文件：</th>
    		<td>
    		<!-- <input type="text" readonly="readonly" id="legalUploadFileName"> -->
				<img id="legalUploadFileName2" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    	</tr>
	</table>
	<table class="table1" id="tab2"  style="display: none;">
		<tr>
    		<th>开户银行：</th>
    		<td>
    		<input type="text" readonly="readonly" id="bankBranch">
    		</td>
    		<th>开户银行账号：</th>
    		<td>
    		<input type="text" readonly="readonly" id="bankAccNo">
    		</td>
    	</tr>
    	<tr>
    		<th>开户账号名称：</th>
    		<td>
    		<input type="text" readonly="readonly" id="bankAccName">
    		</td>
    		<th>开户银行是否为交通银行：</th>
    		<td>
    		<input type="text" readonly="readonly" id="bankType">
    		</td>
    	</tr>
    	<tr>
    		<th>开户银行所在地类别：</th>
    		<td>
    		<input type="text" readonly="readonly" id="areaType">
    		</td>
    		<th>结算周期：</th>
    		<td>
    		<input type="text" readonly="readonly" id="settleCycle">
    		</td>
    	</tr>
    	<tr>
		   		<th>支付系统行号：</th>
		   		<td>
		   		<input type="text" id="payBankId" readonly="readonly" style="width:200px;"  maxlength="20"/><font color="red">&nbsp;*支付系统行号作为结算信息依据，填写错误会影响成功付款!</font>
		   		</td>
		   		<th>对公对私：</th>
		   		<td>
		   		<input type="text" id="accType" readonly="readonly" style="width:200px;"  maxlength="20"/>
		   		</td>
			</tr>
    	<tr>
    		<th>入账人身份证正反面加盖公章(开户证明上传文件名)：</th>
    		<td>
			<img id="accUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    		<th>入账法人/非法人授权书加盖公章：</th>
    		<td>
			<img id="authUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    	</tr>
    	    <tr>
    		<th>入账卡正反面加盖公章：</th>
    		<td>
			<img id="DUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    		<th>开户许可证加盖公章/一般账户证明加盖公章：</th>
    		<td>
			<img id="openUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    	</tr>
	</table>
	<table class="table1" id="tab3"  style="display: none;">
		<tr>
    		<th>银联卡费率：</th>
    		<td>
    		<input type="text" readonly="readonly" id="bankFeeRate">
    		</td>
    		<th>封顶手续费：</th>
    		<td>
    		<input type="text" readonly="readonly" id="feeAmt">
    		</td>
    	</tr>
    	<tr>
    		<!-- <th>封顶值：</th>
    		<td>
    		<input type="text" readonly="readonly" id="dealAmt">
    		</td> -->
    		<th>是否开通外卡：</th>
    		<td>
    		<input type="text" readonly="readonly" id="isForeignName">
    		<input type="hidden" readonly="readonly" id="isForeign">
    		</td>
    		<th class="secondRate1">秒到手续费</th>
    		<td class="secondRate1">
    			<input type="text" readonly="readonly" id="secondRate">
    		</td>
    	</tr>
    	<tr>
    		<th>贷记卡费率：</th>
    		<td>
    		<input type="text" readonly="readonly" id="creditBankRate">
    		</td>
    		<th id="scanRate_wx">微信费率:</th>
    		<td>
    		<input type="text" readonly="readonly" id="scanRate">
    		</td>
    	</tr>
		<tr id="scanRate_ct">
			<th>银联二维码费率：</th>
			<td>
				<input type="text" readonly="readonly" id="scanRate1">
			</td>
			<th>支付宝费率:</th>
			<td>
				<input type="text" readonly="readonly" id="scanRate2">
			</td>
		</tr>
    	<tr>
    		<th>外卡费率-visa：</th>
    		<td>
    		<input type="text" readonly="readonly" id="feeRateV">
    		</td>
    		<th>外卡费率-master：</th>
    		<td>
    		<input type="text" readonly="readonly" id="feeRateM">
    		</td>
    	</tr>
    	<tr>
    		<th>外卡费率-jcb：</th>
    		<td>
    		<input type="text" readonly="readonly" id="feeRateJ">
    		</td>
    		<th>外卡费率-美运：</th>
    		<td>
    		<input type="text" readonly="readonly" id="feeRateA">
    		</td>
    	</tr>
    	<tr>
    	<th>变更申请上传文件：</th>
    		<td>
    		<!-- <input type="text" readonly="readonly" id="feeUpLoad"> -->
				<img id="feeUpLoad1" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    		<th>外卡费率-大莱：</th> 
    		<td>
    		<input type="text" readonly="readonly" id="feeRateD">
    		</td>
    	</tr>
	</table>
	</fieldset>
    <input type="hidden" id="bmtkid" name="bmtkid">
</form>
