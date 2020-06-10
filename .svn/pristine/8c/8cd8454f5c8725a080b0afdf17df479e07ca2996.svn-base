<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	$(function() {
		sysAdmin_30010_find();
		sysAdmin_30010_findQuota();
	});
	//查询当前机构的申请状态
	function sysAdmin_30010_find(){
		$.ajax({
			url:'${ctx}/sysAdmin/loanApplication_queryLoanApply.action',
			type:'post',
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
				} else {
					//已申请禁止重复提交
					$('#button_30010').html('已申请');
					$('#button_30010').attr('disabled','disabled');
					$('#button_30010').css('background-color','#ccc');
				}
			}
		});
	}
	var loanAmt_30010 = 0;
	//查询当前机构的额度
	function sysAdmin_30010_findQuota(){
		$.ajax({
			url:'${ctx}/sysAdmin/loanApplication_queryLoanQuota.action',
			type:'post',
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					loanAmt_30010=data.msg*10000;
					$('#head_30010').html('最高授信额度：￥ '+data.msg+'万元');
				} else {
					//没有额度，禁止申请
					$('#head_30010').html('最高授信额度：——');
					$('#button_30010').attr('disabled','disabled');
					$('#button_30010').css('background-color','#ccc');
				}
			}
		});
	}
	//新增
	function sysAdmin_30010_add() {
		$('<div id="sysAdmin_30010_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">申请贷款</span>',
			width: 900,
		    height:350, 
		    closed: false,
		    onLoad:function() {
		    	$("#applyQuota").numberbox({max:loanAmt_30010,min:10000});
			},
		    href: '${ctx}/biz/credit/30011.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		
		    		var inputs = document.getElementById("sysAdmin_30011_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_30011_addForm').form('validate');
		    		var amt = $('#applyQuota').val();
		    		if(amt<10000){
		    			$.messager.alert('提示', "申请额度不能小于10000元");
		    			return ;
		    		}
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_30011_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/loanApplication_addLoanApply.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
			    			$('#sysAdmin_30010_addDialog').dialog('destroy');
		    				var result = $.parseJSON(data);
		    				console.log(result)
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
					    			$.messager.alert('提示', result.msg);
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
		    				sysAdmin_30010_find();
			    		}
			    	});
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_30010_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
</script> 
<style>
#head_30010{
font-size: 30px;
font-family:"微软雅黑";
color:#222;
text-align:center;
}
#con_30010{
width:90%;
font-size: 20px;
font-family:"微软雅黑";
color:#888;
margin:10px auto;
}
#button_30010 {  
width: 150px;  
padding:8px;  
background-color: #428bca;  
border-color: #357ebd;  
color: #fff;  
-moz-border-radius: 10px;  
-webkit-border-radius: 10px;  
border-radius: 10px; /* future proofing */  
-khtml-border-radius: 10px; /* for old Konqueror browsers */  
text-align: center;  
vertical-align: middle;  
border: 1px solid transparent;  
font-weight: 900;  
font-size:125%  
}
.img_30010{
width:100%;
}
</style>
<div style='width:100%'>
	<div><p id='head_30010'></p></div>
	<div style='text-align:center;margin: 10px;'>
		<button id='button_30010' onclick="sysAdmin_30010_add()">申请贷款</button>
	</div>
	<div style='width:100%;margin:5px auto;'>
		<img class="img_30010" src="${ctx}/images/dk_1.png"/>
		<img class="img_30010" src="${ctx}/images/dk_2.png"/>
		<img class="img_30010" src="${ctx}/images/dk_3.png"/>
		<img class="img_30010" src="${ctx}/images/dk_4.png"/>
		<img class="img_30010" src="${ctx}/images/dk_5.png"/>
	</div>
</div>