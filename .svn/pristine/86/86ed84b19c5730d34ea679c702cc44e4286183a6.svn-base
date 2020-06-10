<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">


	var mid = <%=request.getParameter("mid")%>
	$(function() {
		$('#sysAdmin_merchantterminalinfo711_datagrid').datagrid({
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
				title :'装机名称',
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
			url:'${ctx}/sysAdmin/merchant_serachMerchantMicroInfoDetailed.action',
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
	    			$("#legalUploadFile").attr("src",path1);
    				$("#materialUpLoadFile").attr("src",path2);
    				$("#materialUpLoad1File").attr("src",path3);
    				$("#materialUpLoad2File").attr("src",path4);
    				$("#materialUpLoad3File").attr("src",path5);
    				$("#bupLoadFile").attr("src",path6);
    				$("#materialUpLoad5File").attr("src",path7);
    			}  
	    	} 
	    	
		});
	});

	function showBigImg(img,type){
		
		var cardPan=$('#bankAccNo').val();
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'>";
		if(type==1){
			imgDialog=imgDialog+"<th><h1 style='font-size:15pt;'>卡号：</h1></th><h1 id='cardId'></h1>";
		}
		imgDialog=imgDialog+"<img id='img'></div>";
		$('#sysAdmin_merchantmicroList_queryForm').after(imgDialog);
		if(type==1){
			$("#cardId").text(cardPan);
		}
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
</script>
<form id="sysAdmin_merchantmicroList_queryForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>商户信息</legend>
		<table class="table">
			
		   	<tr>
		   	    <th>商户MID：</th>
		   		<td><input type="text" readonly="readonly" name="mid" style="width:200px;"/></td>
		   		<th>商户注册名称：</th>
		   		<td><input type="text" readonly="readonly" name="rname" style="width:200px;"/></td>
		   	</tr>
			
		</table>
	</fieldset>
	
	<fieldset>
		<legend>上传文件</legend>
		<table class="table">
	   		<tr>
		   		<th>法人身份证正面：</th>
	   			<td>
			         	<img id="legalUploadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,0);" />
	   			</td>
	   			
	   			<th>法人身份证反面：</th>
	   			<td>
	   				<img id="materialUpLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,0);" />
	   			</td>
		   	</tr>
		   	<tr>
	   			<th>个人照片：</th>
	   			<td>
	   			<img id="materialUpLoad1File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,0);" />
	   			</td>

	   			<th>银行卡正面照片：</th>
	   			<td>
	   			<img id="materialUpLoad2File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,1);" />
	   			</td>
			</tr>
			<tr>
				<th>银行卡反面照片：</th>
	   			<td>
	   			<img id="materialUpLoad3File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,0);" />
	   			</td>
	   			<th>营业执照照片：</th>
	   			<td>
	   			<img id="bupLoadFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,0);" />
	   			</td>
			</tr>
			<tr>
				<th>信用卡正面照片：</th>
				<td>
	   			<img id="materialUpLoad5File" src="" width="60" height="40" border="0" onclick="showBigImg(this.src,0);" />
	   			</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>结算信息</legend>
		<table class="table">
		   	<tr>
		   		<th>支付系统行号：</th>
		   		<td><input type="text" readonly="readonly" name="payBankId" style="width:200px;"/></td>
		   		<th>开户银行：</th>
		   		<td >
		   			<input type="text" readonly="readonly" name="bankBranch" style="width:200px;"/>
		   		</td>
			</tr>
				<tr>
		   		<th>借记卡費率：</th>
		   		<td><input type="text" readonly="readonly" name="bankFeeRate" style="width:200px;"/></td>
			
		   		<th>借记卡封顶手续费：</th>
		   		<td><input type="text" readonly="readonly" name="feeAmt" style="width:200px;"/></td>
		   	</tr>
			<tr>
		   		<th>贷记卡費率：</th>
		   		<td><input type="text" readonly="readonly" name="creditBankRate" style="width:200px;"/></td>
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
		<legend>联系人信息</legend>
		<table class="table">
			<tr>
				<th>联系人：</th>
		   		<td><input type="text" readonly="readonly" name="contactPerson" style="width:200px;"/></td>
		   		<th>联系手机：</th>
		   		<td><input type="text" readonly="readonly" name="contactPhone" style="width:200px;"/></td>
			</tr>
			<tr>
		   		<th>法人证件号码：</th>
	   			<td><input type="text" readonly="readonly" name="legalNum" style="width:200px;" /></td>
	   			<th>法人身份证有效期：</th>
		   		<td><input type="text" readonly="readonly" name="legalExpdate" style="width:200px;"/></td>
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
		        <table id="sysAdmin_merchantterminalinfo711_datagrid"></table>
		    </div> 
		</div>
	</fieldset>
</form>
