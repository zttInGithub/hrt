<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	var specialRate1 = null;
	var specialRate2 = null;
	var specialAmt1 = null;
	var specialAmt2 = null;


	$('#bankFeeRate1').combogrid({
			//url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=Rate',
			url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRatebyActiveInfo.action?type=Rate',
			idField:'BANKFEERATE',
			textField:'RATE',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'BANKFEERATE',title:'费率-手续费',width:150,hidden:true},
				{field:'RATE',title:'费率',width:250}
			]] 
		});
	$('#creditBankRate1').combogrid({
			//url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=MicRate',
			url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRatebyActiveInfo.action?type=MicRate',
			idField:'KEYCONTEXT',
			textField:'MINFO1',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'KEYCONTEXT',title:'费率-手续费',width:150,hidden:true},
				{field:'MINFO1',title:'描述',width:250}
			]] 
		});
	$('#creditBankRate2').combogrid({
		url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=SytRate',
		idField:'KEYCONTEXT',
		textField:'MINFO1',
		mode:'remote',
		fitColumns:true,  
		columns:[[ 
			{field:'KEYCONTEXT',title:'费率-手续费',width:150,hidden:true},
			{field:'MINFO1',title:'描述',width:250}
		]] 
	});
	/**$('#creditBankRate1').combogrid({
			url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=Rate',
			idField:'RATE',
			textField:'RATE',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'RATE',title:'费率',width:250}
			]] 
		});**/
	
		function getHuodong() {
			var rebateTypeValue=($('#termIDStart').combobox('getValue')*10000).toFixed(0);
			$('#termIDEnd1').val("");
			if(rebateTypeValue==-1){
				$.messager.alert('提示', '请先选择费率');
			}else{
				$("#termIDEnd1").combogrid("clear"); 
				//var firstSelect=true; // 使用一个变量在判断是不是第一次
				$('#termIDEnd1').combogrid({
					url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateSXUFei.action?rebateType='+rebateTypeValue,
					idField:'SECONDRATE',
					textField:'SECONDRATE',
					mode:'remote',
					fitColumns:true,
					columns:[[
						{field:'SECONDRATE',title:'手续费',width:150}
					]],
					onSelect: function (newValue, oldValue) {
						var jisu1 = $('#termIDStart').combobox('getValue')/100;
						var jisu2 = oldValue.SECONDRATE;
						var jisushezhi = jisu1 +"-"+jisu2
						//var jisushezhi = ($('#termIDStart').combobox('getValue')/100) +"-"+ oldValue.SECONDRATE;
						//$("#termIDEnd1").combogrid("setValue",jisushezhi);
						$('#jisupinjie').val(jisushezhi);
		    		}
					
				});
			}
		};
		
	
	$(function(){
		$("#sendInfo").hide();
		$("#sendInfo2").hide();
		$("#sendInfo3").hide();
		$("#sendInfo4").hide();
		$("#sendInfo5").hide();
		$("#sendInfo6").hide();
		$("#sendInfo7").hide();
			$('#busid').combogrid({
			url : '${ctx}/sysAdmin/agentsales_searchAgentSales.action',
			idField:'BUSID',
			textField:'SALENAME',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'SALENAME',title:'销售姓名',width:150},
				{field:'UNNO',title:'所属机构',width:150},
				{field:'BUSID',title:'id',width:150,hidden:true}
			]] 
		});
		
		
	}); 

	//提交商户编号获取商户信息
	function dosubmit(){
			    var type=$("#type").val();
			    if(type=="1"){ 
			       	 $("#sendInfo").show(); 
				     $("#sendInfo2").hide();
					 $("#sendInfo3").hide();
					 $("#sendInfo4").hide();
					 $("#sendInfo5").hide();
					 $("#sendInfo6").hide();
					 $("#sendInfo7").hide();
				     $('#sendMid').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetail1_serchMerchantInfo.action',
				    		dataType:"json",
			    			success:function(data) {
				    			var json=eval(data);
				    			if (json!="") { 
					    			$("#rname").val(json[0].rname);
					    			$("#busid").combogrid("setValue",json[0].busid); 
					    			$("#legalPerson").val(json[0].legalPerson);
					    			$("#contactAddress").val(json[0].contactAddress);
					    			$("#contactPhone").val(json[0].contactPhone);
					    			$("#contactPerson").val(json[0].contactPerson);
					    			$("#contactTel").val(json[0].contactTel);
					    			$("#legalType").val(json[0].legalType); 
					    			$("#legalNum").val(json[0].legalNum);

					    			
					    			$("#unno").val(json[0].unno);
					    			$("#mid1").val(json[0].mid);
				    				$("#sendInfo").show();
					    		} else {
					    			$("#sendInfo").hide();
						    		$.messager.alert("提示","您输入的商户编号不存在！");
						    	}
				    		} 
				    	}); 
				 
				    }
			    if(type=="2"){
			  	 // $("#sendInfo2").show();
		    	  $("#sendInfo").hide();
		  		  $("#sendInfo3").hide();
		  		  $("#sendInfo4").hide();
			      $("#sendInfo5").hide();
				  $("#sendInfo6").hide();
				  $("#sendInfo7").hide(); 
			    	  $('#sendMid').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetail2_serchMerchantInfo.action',
			    			success:function(data) {
			    				var json=eval(data);
				    			if (json!="") {
					    			$("#bankBranch").val(json[0].bankBranch);
					    			$("#bankAccNo").val(json[0].bankAccNo);
					    			$("#bankAccName").val(json[0].bankAccName);
					    			$("#settleCycle").val(json[0].settleCycle);
					    			$("#areaType").val(json[0].areaType);
					    			$("#bankType").val(json[0].bankType);
					    			$("#unno2").val(json[0].unno);
					    			$("#mid2").val(json[0].mid);
					    			$("#payBankId").val(json[0].payBankId);
				    				$("#sendInfo2").show();	
					    		} else {
					    			$("#sendInfo2").hide();
						    		$.messager.alert("提示","您输入的商户编号不存在！");
						    	} 
				    		}
				    	});  
			    
				    } 
			    if(type=="3"){
			    	/* var sytmid = $("#sendmid").val();
			    	if(sytmid.indexOf("HRTSYT") == 0 || sytmid.indexOf("H864") == 0){
			    		$.messager.alert("提示","暂不允许调整费率，敬请谅解！");
			    		return;
			    	} */
			    	// $("#sendInfo3").show();
			    	 $("#sendInfo2").hide();
			 		 $("#sendInfo").hide();
			 	 	 $("#sendInfo4").hide();
					 $("#sendInfo5").hide();
					 $("#sendInfo6").hide();
					 $("#sendInfo7").hide();  
					 
					 var sendMid = $("#sendmid").val();
					 $.ajax({
							url:"${ctx}/sysAdmin/merchantTaskDetail3_judgeMid.action?sendMid="+sendMid,
							type:"post",
							dataType:'json',
							success:function(data){
							//debugger;
								var msgflag = data.msg;
								if(!data.success){
									$.messager.alert("提示",data.msg);
									return;
								}
								
								$('#sendMid').form('submit',{
						    		url:'${ctx}/sysAdmin/merchantTaskDetail3_serchMerchantInfo.action',
					    			success:function(data) {
					    				//debugger;
					    			var json=eval(data);
						    				if (json!="") {
						    				    // console.log(json[0].isM35)
						    				    // console.log(json[0].settMethod)
						    				$("#isM35").val(json[0].isM35);
						    				
						    				 if(msgflag.indexOf("zidingyi") != -1){
						    					 $("#isspecial").val('1');
						    					 $(".notM35").remove();
							    				 $(".M35").remove();
							    				 $(".M35SYT").remove();
							    				 $(".jisu91").remove();
						    					 var specialValue = msgflag.split(',');
												 specialRate1 = specialValue[1];
												 specialRate2 = specialValue[2];
												 specialAmt1 = specialValue[3];
												 specialAmt2 = specialValue[4];
												 if(json[0].creditBankRate==null){
								    			 }else{
									    			$("#termIDStart").val(parseFloat((json[0].creditBankRate*100).toFixed(4)));
								    			 }
												 $("#termIDEnd").val(json[0].secondRate);
						    				 }else{
						    					 $(".is_specialUnno").remove();
						    					    //秒到
								    				if(json[0].isM35==1&&(json[0].settMethod=='100000'||json[0].settMethod=='200000')){
								    					
								    					//20200117,极速版特殊费率显示
									    				if(json[0].bno=='10007'){
									    					$(".notM35").remove();
									    					$(".M35").remove();
									    					$(".M35SYT").remove();
									    					$(".jisu91").show();
									    					
									    					$('#termIDStart').combogrid({
									    			    		url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateFeiLv.action',
									    			    		idField:'MINFO1',
									    			    		textField:'MINFO1',
									    			    		mode:'remote',
									    			    		fitColumns:true,
									    			    		columns:[[
									    			    			{field:'MINFO1',title:'费率',width:70}
									    			    		]],
									    			    		onChange: function (newValue, oldValue) {
									    			    			getHuodong();
									    			    		}
									    			    	 
									    			    	});
									    				}else{
									    				//	$(".jisu91").remove();
									    					$(".notM35").hide();
									    					$(".M35").show();
									    				}
								    				}
								    				if(json[0].mid.indexOf("HRTSYT") != -1){
								    				//	$(".jisu91").remove();
								    					$(".notM35").hide();
								    					$(".M35").hide();
								    					$("#tr_scanRate").hide();
								    					$(".M35SYT").show();
								    				}
								    				if(json[0].bno!='10007'){
								    					$(".jisu91").remove();
								    				}
						    				 }
						    				
						    				
						    				//理财disabled="disabled"
						    				//if(json[0].isM35==1&&json[0].settMethod=='000000'){
						    					//$("#feeAmt").attr("disabled","disabled"); 
						    				//	$("#feeAmt").hide();
						    				//}
						    				if(json[0].scanRate==null&&json[0].isM35!=1){
						    					$("#scanRate").val("0.6");
						    				}else if(json[0].scanRate==null&&json[0].isM35==1){
						    					$("#scanRate").val("0.38");
						    				}else{
						    					$("#scanRate").val(parseFloat((json[0].scanRate*100).toFixed(4))); 
						    				}

											if(json[0].scanRate1==null&&json[0].isM35!=1){
											    // console.log($("#scanRate").val())
												$("#scanRate1").val($("#scanRate").val());
											}else if(json[0].scanRate1==null&&json[0].isM35==1){
												$("#scanRate1").val(0);
											}else{
												$("#scanRate1").val(parseFloat((json[0].scanRate1*100).toFixed(4)));
											}
											if(json[0].scanRate2==null&&json[0].isM35!=1){
												$("#scanRate2").val($("#scanRate").val());
											}else if(json[0].scanRate2==null&&json[0].isM35==1){
												$("#scanRate1").val(0);
											}else{
												$("#scanRate2").val(parseFloat((json[0].scanRate2*100).toFixed(4)));
											}
						    				//手刷
						    				/**if(json[0].isM35==1){
						    					$("#tr_scanRate").hide();
						    				}**/
							    			$("#bankFeeRate").val(parseFloat((json[0].bankFeeRate*100).toFixed(4)));
							    			if(json[0].creditBankRate==null){
							    				$("#creditBankRate").val(parseFloat((json[0].bankFeeRate*100).toFixed(4)));
							    			}else{
								    			$("#creditBankRate").val(parseFloat((json[0].creditBankRate*100).toFixed(4)));
							    			}
							    			$("#feeAmt").val(json[0].feeAmt);
							    			$("#rname1").val(json[0].rname);
							    			var bankFeeRate = json[0].bankFeeRate;
											var dealAmt = json[0].feeAmt / bankFeeRate;
							    			$("#maxAmt").val(Math.floor(dealAmt));
							    			/**$("#isForeign").val(json[0].isForeign);
							    			if($("#isForeign").val()=="1"){
							    			$("#feeRateV").val(parseFloat((json[0].feeRateV*100).toFixed(4)));
				    						$("#feeRateM").val(parseFloat((json[0].feeRateM*100).toFixed(4)));
							    			$("#feeRateD").val(parseFloat((json[0].feeRateD*100).toFixed(4)));
							    			$("#feeRateJ").val(parseFloat((json[0].feeRateJ*100).toFixed(4)));
							    			$("#feeRateA").val(parseFloat((json[0].feeRateA*100).toFixed(4)));
							    				$("#isHide1").show();
							    				$("#isHide2").show();
							    				$("#isHide3").show();
								    			} else{**/
								    				$("#isHide1").hide();
								    				$("#isHide2").hide();
								    				$("#isHide3").hide();
								    				$("#isHide0").hide();
									    		//}
							    			$("#unno3").val(json[0].unno);
							    			$("#mid3").val(json[0].mid);
						    				$("#sendInfo3").show();
							    		} else {
							    			$("#sendInfo3").hide();
								    		$.messager.alert("提示","您输入的商户编号不存在！");
								    	}
						    		}
						    	});
							}
						});
				    }
			    if(type=="4"){
			    	//
			    	 $("#sendInfo2").hide();
			 		 $("#sendInfo3").hide();
			 		 $("#sendInfo").hide();
					 $("#sendInfo5").hide();
					 $("#sendInfo6").hide();
					 $("#sendInfo7").hide();
					 $('#sendMid').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_serchMerchantInfo.action',
			    			success:function(data) {
			    			var json=eval(data);
				    			if (json!="") {
				    				$("#sendInfo4").show();
					    			$("#unno4").val(json[0].unno);
					    			$("#mid4").val(json[0].mid);
					    		} else {
					    			$("#sendInfo4").hide();
						    		$.messager.alert("提示","您输入的商户编号不存在！");
						    	}
				    		}
				    	});

				    }
			    if(type=="5"){

			    	 $("#sendInfo2").hide();
			 		 $("#sendInfo3").hide();
			 		 $("#sendInfo").hide();
			 		 $("#sendInfo4").hide();
					 $("#sendInfo6").hide();
					 $("#sendInfo7").hide();
					 $('#sendMid').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_serchMerchantInfo.action',
			    			success:function(data) {
			    			var json=eval(data);
				    			if (json!="") {
				    				$("#sendInfo5").show();
					    			$("#unno5").val(json[0].unno);
					    			$("#mid5").val(json[0].mid);
					    		} else {
					    			$("#sendInfo5").hide();
						    		$.messager.alert("提示","您输入的商户编号不存在！");
						    	}
				    		}
				    	});

			    }
			    if(type=="6"){

			    	 $("#sendInfo2").hide();
			 		 $("#sendInfo3").hide();
			 		 $("#sendInfo").hide();
			 		 $("#sendInfo4").hide();
					 $("#sendInfo5").hide();
					 $("#sendInfo7").hide();
					 $('#sendMid').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_serchMerchantInfo.action',
			    			success:function(data) {
			    			var json=eval(data);
				    			if (json!="") {
				    				 $("#sendInfo6").show();
					    			$("#unno6").val(json[0].unno);
					    			$("#mid6").val(json[0].mid);
					    		} else {
					    			$("#sendInfo6").hide();
						    		$.messager.alert("提示","您输入的商户编号不存在！");
						    	}
				    		}
				    	});

			    }
			    if(type=="7"){

			    	 $("#sendInfo2").hide();
			 		 $("#sendInfo3").hide();
			 		 $("#sendInfo").hide();
			 		 $("#sendInfo4").hide();
					 $("#sendInfo5").hide();
					 $("#sendInfo6").hide();
					 $('#sendMid').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_serchMerchantInfo.action',
			    			success:function(data) {
			    			var json=eval(data);
				    			if (json!="") {
				    				 $("#sendInfo7").show();
					    			$("#unno7").val(json[0].unno);
					    			$("#mid7").val(json[0].mid);
					    		} else {
					    			$("#sendInfo7").hide();
						    		$.messager.alert("提示","您输入的商户编号不存在！");
						    	}
				    		}
				    	});

			    }
	}
/**
 * 商户基本信息工单
 */

	//商户基本信息工单申请form表单提交
	function dosubmit2(){
		$.messager.confirm('确认','您确认要提交吗？',function(r){
		    if (r){
		        $('#sendInfo').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantTaskDetail1_addMerchantTaskDetail1.action',
		    			success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    			$("#sendInfo").hide();
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
			    		}
			    	});
		    }
		});
		}

	//商户基本信息工单申请验数据证格式
	function util(){
		dosubmit2();
	}


	//商户基本信息工单申请验证图片格式
	function orsc(){

		var imgList=new Array(6);
		imgList[0] = $("#legalUploadFileName").val();
		imgList[1] = $("#bUpload").val();
		imgList[2] = $("#rUpload").val();
		imgList[3] = $("#registryUpLoad").val();
		imgList[4] = $("#materialUpLoad").val();
		imgList[5] = $("#legalUpload2FileName").val();

		var boo=0;
		for(var i=0;i<imgList.length;i++){
			if(imgList[i]!=""){
			var ename =imgList[i].toLowerCase().substring(imgList[i].toLowerCase().lastIndexOf("."));
			if(ename!=".jpg" && ename!=".gif" && ename!=".png" && ename!=".jpeg"){
				boo=boo+1;
				//alert(boo);
				$.messager.alert('提示', "您上传的图片格式有误请仔细核对！");
				}
				}
			}

		if(boo==0){
			//提交
			util();
			}
		}
	//商户基本信息工单申请图片预览
	$(function(){
		 $("#legalUploadFileName").uploadPreview({ Img: "ImgPr", Width: 120, Height: 120 });

		 $("#bUpload").uploadPreview({ Img: "ImgPr1", Width: 120, Height: 120 });

		 $("#rUpload").uploadPreview({ Img: "ImgPr2", Width: 120, Height: 120 });

		 $("#registryUpLoad").uploadPreview({ Img: "ImgPr3", Width: 120, Height: 120 });

		 $("#materialUpLoad").uploadPreview({ Img: "ImgPr4", Width: 120, Height: 120 });

		 $("#legalUpload2FileName").uploadPreview({ Img: "ImgPr9", Width: 120, Height: 120 });
	});


/**
 * 商户银行基本信息工单申请
 */
	function dosubmit4(){
		$.messager.confirm('确认','您确认要提交吗？',function(r){
		    if (r){
		        $('#sendInfo2').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantTaskDetail2_addMerchantTaskDetail2.action',
		    			success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    			$("#sendInfo2").hide();
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
			    		}
			    	});
		    }
		});
		}

		function util2(){
			var bankBranch = $("#bankBranch").val();
			var bankAccNo = $("#bankAccNo").val();
			var bankAccName = $("#bankAccName").val();
			var areaType = $("#areaType").val();
			var bankType =$("#bankType").val();
			var accUpLoad=$("#accUpLoad").val();
			if(bankBranch!="" && bankAccNo!="" && bankAccName!="" && areaType!="" && bankType!="" ){
				dosubmit4();
			} else {
				$.messager.alert('提示', "提交的信息不能为空" );
			 }
			}

		//验证图片格式
		function orsc2(){
			var imgList2=new Array(1);
			imgList2[0] = $("#accUpLoad").val();
			//alert(imgList2[0]);
			var boo=0;
			for(var i=0;i<imgList2.length;i++){
				if(imgList2[i]!=""){
				var ename =imgList2[i].toLowerCase().substring(imgList2[i].toLowerCase().lastIndexOf("."));
				if(ename!=".jpg" && ename!=".gif" && ename!=".png" && ename!=".jpeg"){
					boo=boo+1;
					//alert(boo);
					$.messager.alert('提示', "您上传的图片格式有误请仔细核对！");
					}
					}
				}
			if(boo==0){
				//提交
				util2();
				}
			}

	$(function(){
		 $("#accUpLoad").uploadPreview({ Img: "ImgPr5", Width: 120, Height: 120 });
		 $("#authUpLoad").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });
		 $("#dUpLoad").uploadPreview({ Img: "ImgPr7", Width: 120, Height: 120 });
		 $("#openUpLoad").uploadPreview({ Img: "ImgPr8", Width: 120, Height: 120 });

	});

/**
 * 商户费率信息工单申请
 */
	function dosubmit6(){
		$.messager.confirm('确认','您确认要提交吗？',function(r){
		    if (r){
		        $('#sendInfo3').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantTaskDetail3_addMerchantTaskDetail3.action',
		    			success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    			$("#sendInfo3").hide();
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
			    		}
			    	});
		    }
		});
		}
 	function util3(){
		var bankFeeRate = $("#bankFeeRate").val();
		var creditBankRate = $("#creditBankRate").val();
		var scanRate = $("#scanRate").val();
		var feeAmt = $("#feeAmt").val();
		var isForeign =$("#isForeign").val();
		var feeRateD = $("#feeRateD").val();
		var feeRateV = $("#feeRateV").val();
		var feeRateM = $("#feeRateM").val();
		var feeRateJ = $("#feeRateJ").val();
		var feeRateA = $("#feeRateA").val();
		var feeUpLoad =$("#feeUpLoad").val();
		var isM35 =$("#isM35").val();
		
		debugger;
		var isspecial =$("#isspecial").val();
		if(isspecial==1){
			var termIDStart =$("#termIDStart").val();
			var termIDEnd =$("#termIDEnd").val();
			if(termIDStart<specialRate1||termIDStart>specialRate2){
				$.messager.alert('提示', "请正确输入费率为"+specialRate1+"%-"+specialRate2+"%！");
				return ;
			}
			if(termIDEnd<specialAmt1||termIDEnd>specialAmt2){
				$.messager.alert('提示',"请正确输入的手续费在"+specialAmt1+"-"+specialAmt2+"！");
				return ;
			}
			if(/^[0-9]\d*$/.test(termIDEnd)){
			}else{
				$.messager.alert('提示',"请正确输入的手续费在"+specialAmt1+"-"+specialAmt2+"整数！");
				return ;
			}
			dosubmit6();
		}else{
			if(!scanRate.match(/^([0-3]\.[0-9]{1,4}|[1-3])$/)||scanRate.match(/^0.0*$/)){
				$.messager.alert('提示', "您输入的费率格式不正确！");
				return ;
			}else if(isM35==1&&(scanRate<0.38||scanRate>0.6)){
				$.messager.alert('提示', "请输入扫码费率为0.38%-0.6%");
				return ;
			}else if((scanRate<0.2||scanRate>0.6)&&isM35>=0){
				$.messager.alert('提示', "请输入扫码费率为0.2%-0.6%");
				return ;
			}
			if(isForeign=="1"){
				if(creditBankRate.match(/^([0-3]\.[0-9]{1,4}|[1-3])$/) && !creditBankRate.match(/^0.0*$/) &&bankFeeRate.match(/^([0-3]\.[0-9]{1,4}|[1-3])$/) && !bankFeeRate.match(/^0.0*$/) && feeAmt.match(/^[0-9]+\.{0,1}[0-9]{0,}$/) && feeRateV.match(/^[0,1,2]+\.{0,9}[0-9]{0,}$/) && feeRateM.match(/^[0,1,2]+\.{0,9}[0-9]{0,}$/) ){
					dosubmit6();
				}else {
					$.messager.alert('提示', "您输入的数据格式不正确！");
				}
			}else{
				if(bankFeeRate.match(/^([0-3]\.[0-9]{1,4}|[1-3])$/) &&  !bankFeeRate.match(/^0.0*$/) && feeAmt.match(/^[0-9]+\.{0,1}[0-9]{0,}$/)){
					dosubmit6();
				}else {
					$.messager.alert('提示', "您输入的数据格式不正确！");
				}
			}
		}
		

 	 }

	 function isChange(){
		 var isForeign =$("#isForeign").val();
		 if(isForeign=="1"){
				$("#isHide1").show();
				$("#isHide2").show();
				$("#isHide3").show();
				$("#isHide0").hide();
			}else{
				$("#isHide1").hide();
				$("#isHide2").hide();
				$("#isHide3").hide();
				$("#isHide0").hide();
				 }
		 }

	//验证图片格式
		function orsc3(){
			var imgList3=new Array(1);
			imgList3[0] = $("#feeUpLoad").val();
			//alert(imgList3[0]);
			var boo=0;
			for(var i=0;i<imgList3.length;i++){
				if(imgList3[i]!=""){
				var ename =imgList3[i].toLowerCase().substring(imgList3[i].toLowerCase().lastIndexOf("."));
				if(ename!=".jpg" && ename!=".gif" && ename!=".png" && ename!=".jpeg"){
					boo=boo+1;
					//alert(boo);
					$.messager.alert('提示', "您上传的图片格式有误请仔细核对！");
					}
					}
				}
			if(boo==0){
				//提交
				util3();
				}
			}

		$(function(){
			 $("#feeUpLoad").uploadPreview({ Img: "ImgPr6", Width: 120, Height: 120 });

		});

		function dosubmit7(){
			$.messager.confirm('确认','您确认要提交吗？',function(r){
		var win = $.messager.progress({
		title:'Please waiting',
		msg:'Loading data...'
		});
		setTimeout(function(){
		$.messager.progress('close');
		},2000);
			    if (r){
			        $('#sendInfo4').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_addMerchantTaskDetail4.action',
			    			success:function(data) {
								var result = $.parseJSON(data);
								if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
						    			$.messager.show({
											title : '提示',
											msg : result.msg
										});
						    			$("#sendInfo4").hide();
						    		} else {
						    			$.messager.alert('提示', result.msg);
							    	}
						    	}
				    		}
				    	});
			    }
			});
			}
		function dosubmit8(){
			$.messager.confirm('确认','您确认要提交吗？',function(r){
		var win = $.messager.progress({
		title:'Please waiting',
		msg:'Loading data...'
		});
		setTimeout(function(){
		$.messager.progress('close');
		},2000);
			    if (r){
			        $('#sendInfo5').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_addMerchantTaskDetail5.action',
			    			success:function(data) {
								var result = $.parseJSON(data);
								if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
						    			$.messager.show({
											title : '提示',
											msg : result.msg
										});
						    			$("#sendInfo5").hide();
						    		} else {
						    			$.messager.alert('提示', result.msg);
							    	}
						    	}
				    		}
				    	});
			    }
			});
			}
		function dosubmit9(){
			$.messager.confirm('确认','您确认要提交吗？',function(r){
		var win = $.messager.progress({
		title:'Please waiting',
		msg:'Loading data...'
		});
		setTimeout(function(){
		$.messager.progress('close');
		},2000);
			    if (r){
			        $('#sendInfo6').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_addMerchantTaskDetail6.action',
			    			success:function(data) {
								var result = $.parseJSON(data);
								if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
						    			$.messager.show({
											title : '提示',
											msg : result.msg
										});
						    			$("#sendInfo6").hide();
						    		} else {
						    			$.messager.alert('提示', result.msg);
							    	}
						    	}
				    		}
				    	});
			    }
			});
			}
		function dosubmit10(){
		$.messager.confirm('确认','您确认要提交吗？',function(r){
		var win = $.messager.progress({
		title:'Please waiting',
		msg:'Loading data...'
		});
		setTimeout(function(){
		$.messager.progress('close');
		},2000);
			    if (r){
			        $('#sendInfo7').form('submit',{
				    		url:'${ctx}/sysAdmin/merchantTaskDetailOther_addMerchantTaskDetail7.action',
			    			success:function(data) {
								var result = $.parseJSON(data);
								if (result.sessionExpire) {
				    				window.location.href = getProjectLocation();
					    		} else {
					    			if (result.success) {
						    			$.messager.show({
											title : '提示',
											msg : result.msg
										});
						    			$("#sendInfo7").hide();
						    		} else {
						    			$.messager.alert('提示', result.msg);
							    	}
						    	}
				    		}
				    	});
			    }
			});

			}

	function dealAmtShow(){
 		if(($('#feeAmt').length>0 && $('#feeAmt').val()!="") && ($('#bankFeeRate').length>0 && $('#bankFeeRate').val()!="")){
			var bankFeeRate = $('#bankFeeRate').val() / 100;
			var dealAmt = $('#feeAmt').val() / bankFeeRate;
			$('#maxAmt').val(Math.floor(dealAmt));
		}
	}

	$.extend($.fn.validatebox.defaults.rules,{
		payBankIdValidator:{
			validator : function(value) {
	            return /^\d{12}$/i.test(value);
	        },
	        message : '必须是十二位数字！'
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) {
		return utilImg(value);
	        },
	        message : '图片格式不正确'
		}
	});

	function utilImg(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".jpg" && ename!=".png" && ename!=".gif" && ename!=".jpeg" ){
				return false;
			}else{
				return true;
				}
			}
		}

	$('#accType').change(function(){
		$("#is3").show();
		$("#is2").hide();
		$("#is1").hide();
		$('#openUpLoad').validatebox({
			required: true,
			validType:'imgValidator'
		});
		$('#authUpLoad').validatebox({
			required: false
		});
		$('#dUpLoad').validatebox({
			required: false
		});
		$('#accUpLoad').validatebox({
			required: false
		});
	});

	$('#accType2').change(function(){
		$("#is1").show();
		$("#is2").show();
		$("#is3").hide();
		$('#authUpLoad').validatebox({
			required: true,
			validType:'imgValidator'
		});
		$('#dUpLoad').validatebox({
			required: true,
			validType:'imgValidator'
		});
		$('#accUpLoad').validatebox({
			required: true,
			validType:'imgValidator'
		});
		$('#openUpLoad').validatebox({
			required: false
		});
	});

</script>
</head>
<div class="easyui-layout"  data-options="fit:true, border:false">

	<div data-options="region:'north',border:false"
		style="height:500px; padding-top:5px;">
		<%--
			商户三种工单申请公用获取信息from表单（根据商户编号）
		--%>
		<form id="sendMid" style="padding-left:5%">
			<table class="table1">
				<tr>
					<th >商户编号：</th>
					<td><input id="sendmid"  name="mid" style="width:200px"  type="text"/> </td>
				</tr>
				<tr>
					<th>工单申请类型：</th>
					<td>
					<select id="type" >
						<option value="1">商户基本信息变更申请</option>
						<option value="2">商户账户/名称变更申请</option>
						<option value="3">商户费率变更申请</option>
						<!--<option value="4">终端绑定变更申请</option>
						<option value="5">预授权业务开通申请</option>
						 <option value="6">商户换机申请</option>
						<option value="7">商户撤机申请</option> -->
					</select>
					</td>
						<td style="padding-left: 300px;"><input type="button" value="工单明细" onclick="dosubmit()"></td>
				</tr>
			</table>
		</form>

		<%--
			商户基本信息工单from表单
		--%>
		<form id="sendInfo" style="padding-left:5%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 1200px;">
				<legend>商户基本信息修改</legend>
				<table class="table1">
					<!-- <tr>
						<th>所属销售：</th>
						<td>
	   						<select id="busid" name="merchantTaskDetail1.busid" class="easyui-combogrid" required="true" style="width:180px;"></select><font color="red">&nbsp;*</font>
	   					</td>
					</tr> -->
					<tr>
						<th>商户全称：</th>
						<td><input id="rname" name="merchantTaskDetail1.rname" style="width: 156px;"
							 />
						</td>
						<th>法人：</th>
						<td><input id="legalPerson" name="merchantTaskDetail1.legalPerson" style="width: 156px;"
							/></td>
					</tr>
					<tr>
						<th>法人证件类型：</th>
						<td>
		   				<select id="legalType" name="merchantTaskDetail1.legalType"   style="width:156px;" >
	    					<option value="1" >身份证</option>
	    					<option value="2">军官证</option>
	    					<option value="3">护照</option>
	    					<option value="4">港澳通行证</option>
	    					<option value="5">其他</option>
	    				</select>
		   				</td>

						<th>法人证件号码：</th>
						<td><input type="text" id="legalNum" name="merchantTaskDetail1.legalNum" style="width: 150px;"
							class=" easyui-validatebox" data-options="required:true">
						</td>
					</tr>

					<tr>
						<th>联系地址：</th>
						<td><input id="contactAddress" name="merchantTaskDetail1.contactAddress" style="width: 156px;"/></td>
						<th>联系人：</th>
						<td><input id="contactPerson" name="merchantTaskDetail1.contactPerson" style="width: 156px;"/></td>
					</tr>
					<tr>
						<th>联系手机：</th>
						<td><input id="contactPhone" name="merchantTaskDetail1.contactPhone" style="width: 156px;"/></td>
						<th>联系电话：</th>
						<td><input id="contactTel" name="merchantTaskDetail1.contactTel" style="width: 156px;"/></td>
					</tr>
					<tr>
						<th>法人身份上传文件名：</th>
						<td><input type="file" id="legalUploadFileName" name="legalUpload" style="width: 156px;"/>
							<div><img id="ImgPr" width="120" height="120" /> </div>
						</td>

						<th>营业执照上传文件名：</th>
						<td> <input type="file" id="bUpload" name="bupload" style="width: 156px;"/>
							<div><img id="ImgPr1" width="120" height="120" /> </div>
						</td>
					</tr>
					<tr>
						<th>组织结构证上传文件名：</th>
						<td> <input type="file" id="rUpload" name="rupload" style="width: 156px;"/>
							<div><img id="ImgPr2" width="120" height="120" /> </div>
						</td>
						<th>税务登记证上传文件名：</th>
						<td><input type="file" id="registryUpLoad" name="registryUpLoad" style="width: 156px;">
								<div><img id="ImgPr3" width="120" height="120" /> </div>
						 </td>
					</tr>
					<tr>
						<th>补充材料上传文件名：</th>
						<td><input type="file" id="materialUpLoad" name="materialUpLoad" style="width: 156px;"/>
							<div><img id="ImgPr4" width="120" height="120" /> </div>
						</td>
						<th>法人身份反面上传文件名：</th>
						<td><input type="file" id="legalUpload2FileName" name="legalUpload2" style="width: 156px;"/>
							<div><img id="ImgPr9" width="120" height="120" /> </div>
						</td>
						<td><input id="unno" name="unno" style="width: 156px;"  type="hidden"/></td>

						<td><input id="mid1" name="mid" style="width: 156px;"    type="hidden"/></td>
					</tr>
				</table>
						<td style="padding-left: 300px;"><input type="button" value="提交" onclick="orsc();"> <input
						type="reset" value="清空"></td>
			</fieldset>
		</form>
		<%--
			银行基本信息工单from表单
		--%>
		<form id="sendInfo2" style="padding-left:5%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 900px;">
				<legend>商户银行信息修改</legend>
				<table class="table1">
					<tr>
						<th>对公对私：</th>
						<td><input id="accType" type="radio" name="merchantTaskDetail2.accType" value="1"/>对公
							<input id="accType2" type="radio" name="merchantTaskDetail2.accType" value="2"/>对私
						</td>
					</tr>
					<tr>
						<th>开户银行：</th>
						<td><input id="bankBranch" name="merchantTaskDetail2.bankBranch" style="width: 156px;"/>
						</td>
						<th>开户银行账号：</th>
						<td><input id="bankAccNo" name="merchantTaskDetail2.bankAccNo" style="width: 156px;" maxlength="30"/></td>
					</tr>
					<tr>
						<th>开户账号名称：</th>
						<td><input type="text" id="bankAccName"
							name="merchantTaskDetail2.bankAccName" style="width: 150px;" ></td>
						<th>结算周期：</th>
						<td><input id="settleCycle" name="merchantTaskDetail2.settleCycle" style="width: 156px;"/></td>
					</tr>
					<tr>
						<th>开户银行所在地：</th>
						<td><select id="areaType" name="merchantTaskDetail2.areaType">
								<option value="1" >北京</option>
								<option value="2">非北京</option>
							</select>
						</td>
						<th>开户银行是否为交通银行：</th>
						<td><select id="bankType" name="merchantTaskDetail2.bankType">
								<option value="1" >交通银行</option>
								<option value="2">非交通银行</option>
							</select>
						</td>
					</tr>
			<tr>
		   		<th>支付系统行号：</th>
		   		<td colspan="3">
		   		<input type="text" id="payBankId" name="merchantTaskDetail2.payBankId" style="width:200px;" class="easyui-validatebox"  data-options="required:true,validType:'payBankIdValidator'" maxlength="20"/><font color="red">&nbsp;*支付系统行号作为结算信息依据，填写错误会影响成功付款!</font>
		   		</td>
			</tr>
					<tr id="is1">
						<th>入账人身份证正反面加盖公章(开户证明上传文件名)：</th>
						<td> <input type="file"  id="accUpLoad" name="accUpLoad"/><font color="red">*</font>
							<div><img id="ImgPr5" width="120" height="120" /> </div>
						</td>
						<td><input id="unno2" name="unno" style="width: 156px;"   type="hidden"/></td>
						<td><input id="mid2" name="mid" style="width: 156px;"   type="hidden"/></td>
					</tr>
					<tr id="is2">
						<th>入账法人/非法人授权书加盖公章：</th>
						<td> <input type="file"  id="authUpLoad" name="authUpLoad"/><font color="red">*</font>
							<div><img id="ImgPr6" width="120" height="120" /> </div>
						</td>
						<th>入账卡正反面加盖公章：</th>
						<td> <input type="file"  id="dUpLoad" name="dUpLoad"/><font color="red">*</font>
							<div><img id="ImgPr7" width="120" height="120" /> </div>
						</td>
					</tr>
					<tr id="is3">
						<th>开户许可证加盖公章/一般账户证明加盖公章：</th>
						<td> <input type="file"  id="openUpLoad" name="openUpLoad"/><font color="red">*</font>
							<div><img id="ImgPr8" width="120" height="120" /> </div>
						</td>
					</tr>
				</table>
						<td style="padding-left: 300px;"><input type="button" value="提交" onclick="orsc2();"> <input
						type="reset" value="清空"></td>
			</fieldset>
		</form>

		<%--
			商户费率信息工单from表单
		--%>
		<form id="sendInfo3" style="padding-left:5%;padding-top:1%" method="post" enctype="multipart/form-data">
			<fieldset style="width: 900px;">
				<legend>商户费率信息修改</legend>
				<table class="table1">
					<tr>
						<th>商户名称：</th>
						<td colspan="3"><input type="text" disabled="disabled" id="rname1" name="merchantTaskDetail3.rname1" style="width: 300px" ></td>

					</tr>
					<tr class="notM35">
						<th>借记卡费率：</th>
						<td><input id="bankFeeRate" name="merchantTaskDetail3.bankFeeRate" style="width: 150px;" onblur="dealAmtShow()"/>%<font color="red">&nbsp;*</font>
						</td>
						<th>借记卡封顶手续费：</th>
						<td><input type="text" id="feeAmt" name="merchantTaskDetail3.feeAmt"	style="width: 150px;" onblur="dealAmtShow()"><font color="red">&nbsp;*</font></td>
					</tr>
					<!--
					<tr>
						<th>封顶值：</th>
						<td><input type="text" disabled="disabled" id="maxAmt" name="maxAmt" style="width: 150px;" ></td>
						<td></td>
						<td></td>
					</tr> -->
					<tr class="notM35">
						<th>贷记卡费率：</th>
						<td><input id="creditBankRate" name="merchantTaskDetail3.creditBankRate" style="width: 150px;" />%<font color="red">&nbsp;*</font>
						</td>
						<td></td>
						<td></td>
						<!-- <th>贷记手续费：</th>
						<td><input type="text" id="feeAmt" name="merchantTaskDetail3.feeAmt"	style="width: 150px;" "><font color="red">&nbsp;*</font></td>
						 -->
					</tr>
					<tr class="M35" style="display: none">
						<th>贷记卡费率：</th>
						<td><select id="creditBankRate1" name="merchantTaskDetail3.creditBankRate1" class="easyui-combogrid" data-options="editable:false" style="width: 150px;">
							</select>%<font color="red">&nbsp;*</font>
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr class="M35SYT" style="display: none">
						<th>费率：</th>
						<td><select id="creditBankRate2" name="merchantTaskDetail3.creditBankRate2" class="easyui-combogrid" data-options="editable:false" style="width: 150px;">
							</select>%<font color="red">&nbsp;*</font>
						</td>
						<th></th>
						<td></td>
					</tr>
					
					<tr class="jisu91" id="jisu911">
			          <th style="width:100px;">刷卡费率：</th>
			          <td style="width:170px;" id="termIDStart1"  >
			           <select id="termIDStart" class="easyui-combogrid" data-options="required:true,editable:false" style="width:150px;" ></select>%<font color="red">&nbsp;*</font>
			          </td>
			          
			          <th style="width:100px;">转账费：</th>
			          <td style="width:170px;" id="huodong91">
			            <select id="termIDEnd1" class="easyui-combogrid" data-options="required:true,editable:false" style="width:150px;"></select><font color="red">&nbsp;*</font>
			          </td>
			          
			          <td><input id="bankFeeRate" name="merchantTaskDetail3.bankFeeRate" style="width: 156px;"  type="hidden"/></td> 
			          <td><input id="feeAmt" name="merchantTaskDetail3.feeAmt" style="width: 156px;"  type="hidden"/></td> 
			        </tr>
			      

	                <tr class="is_specialUnno" id="is_specialUnno">
          			  <th style="width:100px;">费率：</th>
          		      <td style="width:270px;">
            		  		<input type="text"  name="termIDStart" id="termIDStart" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'selfrateValidator',required:true"/><font color="red">&nbsp;%*</font>
          		      </td>
          		      
          		      <th style="width:100px;">手续费：</th>
	                  <td style="width:270px;">
	            	  		<input type="text" name="termIDEnd" id="termIDEnd" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'range[0,3]',required:true"/><font color="red">&nbsp;*</font>
	                  </td>
	                  
	                   <td><input id="bankFeeRate" name="merchantTaskDetail3.bankFeeRate" style="width: 156px;"  type="hidden"/></td> 
			          <td><input id="feeAmt" name="merchantTaskDetail3.feeAmt" style="width: 156px;"  type="hidden"/></td> 
        			</tr>
					
					<tr id="tr_scanRate">
						<th>扫码费率：</th>
						<td><input id="scanRate" name="merchantTaskDetail3.scanRate" style="width: 150px;" />%<font color="red">&nbsp;*</font>
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr class="notM35" id="tr_scanRate2">
						<th>银联二维码费率：</th>
						<td><input id="scanRate1" name="merchantTaskDetail3.scanRate1" style="width: 150px;" />%<font color="red">&nbsp;*</font>
						<th>支付宝费率：</th>
						<td><input id="scanRate2" name="merchantTaskDetail3.scanRate2" style="width: 150px;" />%<font color="red">&nbsp;*</font></td>
					</tr>
					
					<tr id="isHide0">
						<th>是否开通外卡：</th>
						<td>
							<select  id="isForeign" name="merchantTaskDetail3.isForeign" style="width: 156px;" onchange="isChange();">
							<option value="1" >是</option>
							<option value="2" selected="selected">否</option>
							</select>
						</td>
						<th></th> 
						<td></td>
					</tr> 
					<tr id="isHide3">
						<th>大莱费率：</th>
						<td><input type="text" id="feeRateD" name="merchantTaskDetail3.feeRateD"	value="0" style="width: 150px;" >%</td>
					</tr>
					<tr id="isHide1">
						<th>VISA费率：</th>
						<td><input type="text" id="feeRateV" name="merchantTaskDetail3.feeRateV"	value="0" style="width: 150px;" >%<font color="red">&nbsp;*</font></td>
						<th>MASTER费率：</th>
						<td><input type="text" id="feeRateM" name="merchantTaskDetail3.feeRateM"	 value="0" style="width: 150px;" >%<font color="red">&nbsp;*</font></td>
					</tr>
					<TR id="isHide2">
						<th>JCB费率：</th>
						<td><input type="text" id="feeRateJ" name="merchantTaskDetail3.feeRateJ"	value="0" style="width: 150px;" >%</td>
						<th>美运费率：</th>
						<td><input type="text" id="feeRateA" name="merchantTaskDetail3.feeRateA"	value="0" style="width: 150px;" >%</td>
					</TR>
					<TR>
						
						<th>变更申请上传文件名：</th>
						<td> <input type="file" id="feeUpLoad" name="feeUpLoad"	style="width: 150px;" />
							<div><img id="ImgPr6" width="120" height="120" /> </div>
						</td>
						<td><input id="unno3" name="unno" style="width: 156px;"  type="hidden"/></td>
						<td><input id="mid3" name="mid" style="width: 156px;"  type="hidden"/></td> 
						<td> <input name="merchantTaskDetail3.creditBankRate1" id="jisupinjie" type="hidden" ></td>
					</TR>
				</table>
						<td style="padding-left: 300px;"> <input type="button" value="提交" onclick="orsc3()"> <input type="reset" value="清空"><input id="isM35" type="hidden"/><input id="isspecial" name="isspecial" type="hidden"/>
			</fieldset>
		</form>
		 
		<%-- 
			商户终端变更申请from表单
		--%>
		<form id="sendInfo4" style="padding-left:5%;padding-top:1%" method="post" >
			<fieldset style="width: 900px;">
				<legend>商户终端变更申请</legend>
				<table class="table1"> 
					<tr>
						<th>工单描述：</th> 
						<td><textarea rows="4" cols="60"  id="descr1" name="descr" maxlength="100"></textarea>   
						</td> 
						<td><input id="unno4" name="unno" style="width: 156px;"  type="hidden"/></td>
						<td><input id="mid4" name="mid" style="width: 156px;"  type="hidden"/></td> 
					</tr>
				</table>
				<td style="padding-left: 300px;"> <input type="button" value="提交" onclick="dosubmit7()"> <input type="reset" value="清空"></td> 
			</fieldset>
		</form>  
		<%-- 
			预授权业务申请from表单
		--%>
		<form id="sendInfo5" style="padding-left:5%;padding-top:1%" method="post" >
			<fieldset style="width: 900px;">
				<legend>预授权业务申请</legend>
				<table class="table1">
					<tr>
						<th>工单描述：</th> 
						<td><textarea rows="4" cols="60"  id="descr2" name="descr" maxlength="100"></textarea>   
						</td> 
						<td><input id="unno5" name="unno" style="width: 156px;"  type="hidden"/></td>
						<td><input id="mid5" name="mid" style="width: 156px;"  type="hidden"/></td> 
					</tr>
				</table>
				<td style="padding-left: 300px;"> <input type="button" value="提交" onclick="dosubmit8()"> <input type="reset" value="清空"></td> 
			</fieldset>
		</form>  
		<%-- 
			商户换机申请from表单 
		--%>
		<form id="sendInfo6" style="padding-left:5%;padding-top:1%" method="post" >
			<fieldset style="width: 900px;">
				<legend>商户换机申请</legend>
				<table class="table1">
					<tr>
						<th>工单描述：</th> 
						<td><textarea rows="4" cols="60"  id="descr3" name="descr" maxlength="100"></textarea>   
						</td> 
						<td><input id="unno6" name="unno" style="width: 156px;"  type="hidden"/></td>
						<td><input id="mid6" name="mid" style="width: 156px;"  type="hidden"/></td> 
					</tr>
				</table>
				<td style="padding-left: 300px;"> <input type="button" value="提交" onclick="dosubmit9()"> <input type="reset" value="清空"></td> 
			</fieldset>
		</form>  
		<%-- 
			商户撤机申请from表单
		--%>
		<form id="sendInfo7" style="padding-left:5%;padding-top:1%" method="post" >
			<fieldset style="width: 900px;">
				<legend>商户撤机申请</legend>
				<table class="table1">
					<tr>
						<th>工单描述：</th> 
						<td><textarea rows="4" cols="60"  id="descr4" name="descr" maxlength="100"></textarea>   
						</td>  
						<td><input id="unno7" name="unno" style="width: 156px;"  type="hidden"/></td>
						<td><input id="mid7" name="mid" style="width: 156px;"  type="hidden"/></td> 
					</tr>
				</table>
				<td style="padding-left: 300px;"> <input type="button" value="提交" onclick="dosubmit10()"> <input type="reset" value="清空"></td> 
			</fieldset>
		</form>  
	</div>
	
	<%-- 
			商户基本信息工单申请div
	--%>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_TaskDetail1_datagrid"></table>
	</div>
	
	<%-- 
			银行基本信息工单申请div
	--%>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_TaskDetail2_datagrid"></table>
	</div>
	
	<%-- 
			商户费率信息工单申请div
	--%>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_user_datagrid"></table>
	</div>
</div>

