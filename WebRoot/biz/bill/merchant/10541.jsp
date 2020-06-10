<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
	String mid=request.getParameter("mid");
%>
<script type="text/javascript">
	$(function(){
		var bmtkid1=$("#bmtkid1").val();
		var mid=$("#mid").val();  
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail5.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmtkid:bmtkid1,mid:mid},
			success:function(data) {
    			var json=eval(data);
	    			if (json!="") {  
	    			$("#bankAccName").val(json[0].bankAccName);
	    			$("#legalNum").val(json[0].legalNum);
	    			$("#legalPositiveFile").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+json[0].legalPositiveName);
	    			$("#legalReverseFile").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+json[0].legalReverseName);
	    			$("#legalHandFile").attr("src",'${ctx}/sysAdmin/rand_ImageShow.action?upload='+json[0].legalHandName);
	    			$("#settleCycle1").val(json[0].settleCycle);

    			} 
    		}
	});
});
	    			function showBigImg(img){
	    				var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
	    				$('#sysAdmin_10541_addForm').after(imgDialog);
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
 
<form id="sysAdmin_10541_addForm" method="post">
	<fieldset>
		<legend>工单信息</legend>
		<table class="table">
			<tr>
	    		<th>入账人名称：</th>
	    		<td>
	    			<input type="text" name="bankAccName" id="bankAccName" readonly="readonly" value=""/>
	    		</td>
	    		<th>入账人身份证号：</th>
	    		<td>
	    			<input type="text" name="legalNum" id="legalNum" readonly="readonly" value=""/>
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>原有结算周期：</th>
	    		<td>
	    			<input type="text" name="settleCycle1" id="settleCycle1" readonly="readonly" value=""/>
	    		</td>
	    		<th>更改后结算周期：</th>
	    		<td>
	    			<input type="text" name="settleCycle" id="settleCycle"  value="0"/>
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>受理状态：</th>
    			<td>
    			<select name="approveStatus" id="approveStatus">
    				<option value="Z">待受理</option>
    				<option value="K" selected="selected">不受理</option>
    				<option value="Y">已受理</option>
    			</select>
    			</td>
	    	</tr>
	    	<tr>
    		<th>工单描述：</th>
    		<td>
    		<textarea style="display:block" id="texta2" rows="6" cols="38" style="resize:none;" name="processContext" maxlength="200"></textarea>
    		</td>
    	</tr>
		<tr>
    		<th>身份证正面照片：</th>
    		<td>
			<img id="legalPositiveFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    		<th>身份证反面照片：</th>
    		<td>
			<img id="legalReverseFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    	</tr>
    	<tr>
    		<th>入账人手持身份证照片：</th>
    		<td>
			<img id="legalHandFile" src="" width="60" height="40" border="0" onclick="showBigImg(this.src);" />
    		</td>
    	</tr>
			<input type="hidden" name="bmtkid" id="bmtkid1" value="<%=bmtkid %>">  
			<input type="hidden" name="mid" id="mid" value="<%=mid %>">  
		</table>
	</fieldset>
</form>  

