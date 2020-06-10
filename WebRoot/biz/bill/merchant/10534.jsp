<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
%>
<script type="text/javascript">
	$(function(){
		var timestamp=new Date().getTime();
		var bmtkid1=$("#bmtkid1_10534").val(); 
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail4.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmtkid:bmtkid1},
			success:function(data) {
    			var json=eval(data);
	    			if (json!="") {  
   	    			if(json[0].mtype!=1){
   	    				$("#scanSingleLimit").val(json[0].singleLimit);
   	    				$("#scanDailyLimit").val(json[0].dailyLimit);
   	    				$("#scanSingleLimit1").val(json[0].singleLimit1);
   	    				$("#scanDailyLimit1").val(json[0].dailyLimit1);
   	    				$("#xiaopiao").text("大额交易小票");
   	    				$("#doorPic").text("门头照");
   	    				$("#neibu").text("内部经营照");
   	    				$("#account").text("银行卡/对公账户");
   	    			 	$("#type_10534").val("刷卡");
   	    			 	$("#daijika").hide();
   	    			}else{
   	    				$("#scanSingleLimit2").val(json[0].singleLimit);
   	    				$("#scanDailyLimit2").val(json[0].dailyLimit);
   	    			 	$("#type_10534").val("扫码");
   	    				$("#account").hide();
	    				$("#accountPic").hide();
	    				$("#bankCarId").hide();
	    				$("#daijika1").hide();
	    				$("#daijika2").hide();
   	    			} 
	    			var authorized=json[0].isAuthorized;
	    			if(authorized==0)
	    				$("#isAuthorized").val("是");
    				
    				if(authorized==1){
    					$("#isAuthorized").val("否");
        			}
    				var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].bnoImg)+'&timestamp='+timestamp;
	    			$("#bnoImg").attr("src",path1);
	    			var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].identityImg)+'&timestamp='+timestamp;
	    			$("#identityImg").attr("src",path2);
	    			var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].bankCardImg)+'&timestamp='+timestamp;
	    			$("#bankCardImg").attr("src",path3);
	    			var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].activityImg)+'&timestamp='+timestamp;
	    			$("#activityImg").attr("src",path4);
	    			var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].orderImg)+'&timestamp='+timestamp;
	    			$("#orderImg").attr("src",path5);
	    			var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].ortherImg)+'&timestamp='+timestamp;
	    			$("#ortherImg").attr("src",path6);
	    		}
    	} 
	});
});
</script>
 
<form id="sysAdmin_10532_addForm" method="post">
	<fieldset>
		<legend>工单信息</legend>
		<table class="table">
			<tr>
	    		<th style="width:150px;">是否开通预授权：</th>
	    		<td>
	    			<input type="text" name="isAuthorized" id="isAuthorized" style="width:100px;" readonly="readonly" value=""/>
	    		</td>
	    		<th>工单类型：</th>
	    		<td><input type="text"  id="type_10534" style="width:200px;" readonly="readonly"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
	    	</tr>
			<tr id="daijika">
	    		<th>单日限额：</th>
	    		<td><input type="text" name="scanDailyLimit" id="scanDailyLimit2" style="width:100px;" readonly="readonly"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
				<th>单笔限额：</th>
	    		<td><input type="text" name="scanSingleLimit" id="scanSingleLimit2" style="width:100px;" readonly="readonly"   class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
			</tr>
			<tr id="daijika1">
	    		<th>贷记卡单日限额：</th>
	    		<td><input type="text" name="scanDailyLimit" id="scanDailyLimit" style="width:100px;" readonly="readonly"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
				<th>贷记卡单笔限额：</th>
	    		<td><input type="text" name="scanSingleLimit" id="scanSingleLimit" style="width:100px;" readonly="readonly"   class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
			</tr>
			<tr id="daijika2">
	    		<th>借记卡单日限额：</th>
	    		<td><input type="text" name="scanDailyLimit1" id="scanDailyLimit1" style="width:100px;" readonly="readonly"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
				<th>借记卡单笔限额：</th>
	    		<td><input type="text" name="scanSingleLimit1" id="scanSingleLimit1" style="width:100px;" readonly="readonly"   class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
			</tr>
			<tr class="pic" style="height: 100px">
	    		<th >营业执照：</th>
				<td>
					<div><img id="bnoImg" src="" name="bnoImg" width="120" height="90" /></div>
	    		</td>	    	
	    		<th >法人手持身份证照片：</th>
				<td>
					<div><img id="identityImg" src="" name="identityImg" width="120" height="90" /></div>
	    		</td>	    	
	    	</tr>
	    	<tr class="pic" style="height: 100px">
	    		<th id="xiaopiao">店面经营照片：</th>
				<td>
					<div><img id="activityImg" src="" name="activityImg" width="120" height="90" /></div>
	    		</td>
	    		<th id="doorPic">交易流水证明：</th>
				<td>
					<div><img id="orderImg" src="" name="orderImg" width="120" height="90" /></div>
	    		</td>		    	
	    	</tr>
	    	<tr class="pic" style="height: 100px">
	    		<th id="neibu">补充证明材料：</th>
				<td>
					<div><img id="ortherImg" src="" name="ortherImg" width="120" height="90" /></div>
	    		</td>	    	
	    		<th id="account" style="height: 100px">银行卡/对公账户：</th>
	    		<td id="bankCarId">
					<div><img id="bankCardImg" src="" name="bankCardImg" width="120" height="90" /></div>
	    		</td>
				 	
	    	</tr>
			<input type="hidden"  name="bmtkid" id="bmtkid1_10534" value="<%=bmtkid %>">   
		</table>
	</fieldset>
</form>  

