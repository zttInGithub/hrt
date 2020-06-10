<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Integer  bmtkid=Integer.parseInt(request.getParameter("bmtkid1")); 
%>
<script type="text/javascript">
	$(function(){
		var bmtkid1=$("#bmtkid1").val(); 
		var timestamp=new Date().getTime();
		$.ajax({
			url:'${ctx}/sysAdmin/merchantTaskOperate_serachMerahctTaskDetail4.action',
    		dataType:"json",  
    		type:"post",
    		data:{bmtkid:bmtkid1},
			success:function(data) {
    			var json=eval(data);
	    			if (json!="") {  
	    				$("#mtype_10536").val(json[0].mtype);
						$("#singleLimit").val(json[0].singleLimit);
		    			$("#dailyLimit").val(json[0].dailyLimit);
		    			$("#isAuthorized").val(json[0].isAuthorized);
		    			if(json[0].mtype!=1){
	   	    				$("#xiaopiao1").text("大额交易小票");
	   	    				$("#doorPic1").text("门头照");
	   	    				$("#neibu1").text("内部经营照");
	   	    				$("#account1").text("银行卡/对公账户");
	   	    				 $("#type1").val("刷卡");
	   	    			}else{
	   	    				$("#type1").val("扫码");
	   	    				$("#account1").hide();
		    				$("#accountPic1").hide();
		    				$("#bankCarId1").hide();
	   	    			} 
		    			var path1='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].bnoImg)+'&timestamp='+timestamp;
		    			$("#bnoImg_10536").attr("src",path1);
		    			var path2='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].identityImg)+'&timestamp='+timestamp;
		    			$("#identityImg_10536").attr("src",path2);
		    			var path3='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].bankCardImg)+'&timestamp='+timestamp;
		    			$("#bankCardImg_10536").attr("src",path3);
		    			var path4='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].activityImg)+'&timestamp='+timestamp;
		    			$("#activityImg_10536").attr("src",path4);
		    			var path5='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].orderImg)+'&timestamp='+timestamp;
		    			$("#orderImg_10536").attr("src",path5);
		    			var path6='${ctx}/sysAdmin/rand_ImageShow.action?upload='+(json[0].ortherImg)+'&timestamp='+timestamp;
		    			$("#ortherImg_10536").attr("src",path6);
		    		}
    	} 
	});
});
	
	function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog' style='padding:10px;'><img id='img'></div>";
		$('#sysAdmin_10536_addForm').after(imgDialog);
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
</script>
 
<form id="sysAdmin_10536_addForm" method="post">
	<input type="hidden" id="mtype_10536" name="mtype">
	<fieldset>
	<legend>基本信息</legend>
	<input type="hidden" name="bmtkid" id="bmtkid1" value="<%=bmtkid %>">  
	<table class="table1">
		<tr>
    		<th>工单编号：</th>
    		<td>
    		<input type="text" readonly="readonly" name="taskId" id="taskId">
    		</td>
    	</tr>
    	<tr>
    		<th>商户名称：</th>
    		<td>
    		<input type="text" readonly="readonly" name="infoName" id="infoName">
    		</td>
    		<th>商户编号：</th>
    		<td>
    		<input type="text" readonly="readonly" name="mid" id="mid" >
    		</td>
    	</tr>
    	<tr>
    		<th>受理状态：</th>
    		<td>
    		<select onchange="Change3()"  name="approveStatus" id="approveStatus">
    		<option value="Z">待受理</option>
    		<option value="K" selected="selected">退回</option>
    		<option value="Y">通过</option>
    		</select>
    		</td>
    		<th>工单描述：</th>
    		<td>
    		<input type="text" readonly="readonly" name="taskContext" id="taskContext">
    		</td>
    	</tr>
    	<tr>
    		<th>受理描述：</th>
    		<td colspan="3">
				<textarea style="display:block" id="texta2" rows="6" cols="38" style="resize:none;" name="processContext" maxlength="200"></textarea>
			</td>
    	</tr>
    </table>
    </fieldset>
	<fieldset>
		<legend >工单信息</legend>
		<table class="table">
			<tr>
	    		<th>单笔限额：</th>
	    		<td><input type="text" name="singleLimit" id="singleLimit" style="width:200px;" readonly="readonly"   class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
	    		<th>单日限额：</th>
	    		<td><input type="text" name="dailyLimit" id="dailyLimit" style="width:200px;" readonly="readonly"  class="easyui-validatebox" data-options="validType:'intOrFloat'"/></td>
			 </tr>
			<tr class="pic" style="height: 100px">
	    		<th>营业执照：</th>
				<td>
					<div><img id="bnoImg_10536" src="" name="bnoImg" width="120" height="90" onclick="showBigImg(this.src);" /></div>
	    		</td>	    	
	    		<th>法人手持身份证照片：</th>
				<td>
					<div><img id="identityImg_10536" src="" name="identityImg" width="120" height="90" onclick="showBigImg(this.src);" /></div>
	    		</td>	    	
	    	</tr>
	    	<tr class="pic" style="height: 100px">
	    		<th id="xiaopiao1">店面经营照片：</th>
				<td>
					<div><img id="activityImg_10536" src="" name="activityImg" width="120" height="90" onclick="showBigImg(this.src);" /></div>
	    		</td>	    	
	    		<th id="doorPic1">交易流水证明：</th>
				<td>
					<div><img id="orderImg_10536" src="" name="orderImg" width="120" height="90" onclick="showBigImg(this.src);" /></div>
	    		</td>	    	
	    	</tr>
	    	<tr class="pic1" style="height: 100px">
	    		<th id="neibu">补充证明材料：</th>
				<td>
					<div><img id="ortherImg_10536" src="" name="ortherImg" width="120" height="90" onclick="showBigImg(this.src);" /></div>
	    		</td>	
	    		<th id="account1" style="height: 100px">银行卡/对公账户：</th>
	    		<td id="bankCarId1">
					<div><img id="bankCardImg_10536" src="" name="bankCardImg" width="120" height="90" /></div>
	    		</td>
				 	
	    	</tr>
		</table>
	</fieldset>
</form>  

