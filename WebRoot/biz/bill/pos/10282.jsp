<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	function midblur(){
		//$('#mid').combogrid({
			//onSelect:function(node){
			//	var mid = $('#mid').combogrid('getValues');
				var mid = $('#mid').val();
				
				$('#bmtID').combogrid('clear');
				$('#bmtID').combogrid({
					url : '${ctx}/sysAdmin/merchantterminalinfo_searchMerchantTerminalInfo.action?mid='+mid+'&type=0,2,3,4,5',
					idField:'BMTID',
					textField:'TID',
					mode:'remote',
					fitColumns:true,
					columns:[[ 
						{field:'BMTID',title:'主键',width:150,hidden:true},
						{field:'TID',title:'终端编号',width:150}
					]],
					onSelect:function(node){
						var bmtID = $('#bmtID').combogrid('getValues');
						
						$.ajax({
							url:"${ctx}/sysAdmin/merchantterminalinfo_searchMerchantTerminalInfoBMTID.action?bmtid="+bmtID,
							type:'post',
							dataType:'json',
							success:function(data, textStatus, jqXHR) {
								$('#bmaidNameFirst').val(data.machineModel);
							}
						});
					}
				});
				
				$('#tid').combogrid('clear');
				$('#tid').combogrid({
					url : '${ctx}/sysAdmin/terminalInfo_searchTerminalInfo.action?mid='+mid,
					idField:'TERMID',
					textField:'TERMID',
					mode:'remote',
					fitColumns:true,
					columns:[[ 
						{field:'BTID',title:'主键',width:150,hidden:true},
						{field:'TERMID',title:'终端编号',width:150},
						{field:'KEYTYPENAME',title:'密钥类型',width:150}
					]]
				});
			//}
	}

	$(function(){
		
		$('#type').combobox({
			onSelect:function(node){
				if(node.value == 2){
					$("#newBmtID").show();
					$('#tid').combogrid({  
					    required: true
					});
					$('#bmaID').combogrid({  
					    required: true
					});
					$('#changeType').combobox({  
					    required: true
					});	
					 $('#remarks').validatebox({ 
					    required: true
					});	 
				}else{
					$("#newBmtID").hide();	
					 $('#remarks').validatebox({ 
					    required: false
					}); 
					$('#tid').combogrid({ 
					    required: false
					});
					$('#bmaID').combogrid({
					    required: false
					});
					$('#changeType').combobox({  
					    required: false
					});
					 $('#remarks').val("");	
                 	 $('#changeType').combobox('setValue', '');
					 $('#tid').combogrid('clear');  
					 $('#bmaID').combogrid('clear');	
				}
			}
		});
		
		$('#changeType').combobox({
			onSelect:function(node){
				var value = node.value;
				if(value == 2){
					$("#tidss").hide();
					$("#tids").hide();
					$('#tid').combogrid('clear'); 
					$('#tid').combogrid({  
					    required: false
					});
				}else{
					$("#tidss").show();
					$("#tids").show();
				    $('#tid').combogrid('clear'); 
					$('#tid').combogrid({  
					    required: true
					});
				}
			}
		});
	});
	
</script>
<form id="sysAdmin_10282_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>终端信息</legend>
		<table class="table">
	    	<tr>
	    		<th>商户编号：</th>
	    		<td colspan="3">
	    			<input id="mid"  name="mid" onblur="midblur()" class="easyui-validatebox" data-options="required:true" style="width:150px" />
	    			<!-- <select class="easyui-combogrid" name="mid" id="mid" data-options="  
			            required:true,
			            url : '${ctx}/sysAdmin/merchant_searchMerchantInfo.action?type=0,2,3,4,5',
						idField:'mid',
						textField:'rname',
						mode:'remote',
						width:560,
						fitColumns:true,
						pagination : true,
	                    rownumbers:true,  
	                    collapsible:false,  
	                    fit: false,  
	                    pageSize: 10,  
	                    pageList: [10,15],
						columns:[[ 
							{field:'mid',title:'mid',width:280},
							{field:'rname',title:'商户名称',width:280}
						]]
			        "></select> -->
	    		</td>
		</tr>
		<tr>
	    		<th>工单类别：</th>
	    		<td colspan="3">
	    			<select name="type" id="type" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:205px;">
	    				<option value="2">换机</option>
	    				<option value="3">撤机</option>
	    				<option value="4">服务</option>
	    			</select>
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>原终端：</th>
	    		<td>
	    			<select id="bmtID" name="bmtID" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select>
	    		</td>
	    		
	    		<th>原机型：</th>
	    		<td>
	    			<input type="text" id="bmaidNameFirst" name="bmaidNameFirst" disabled="disabled" style="width:200px;"/>
	    		</td>
	    	</tr>
	    </table>
	</fieldset>
    
    <fieldset id='newBmtID'>
		<legend>新终端信息</legend>
	    <table class="table">
	    	<tr>
	    		<th>换机类型：</th>
	    		<td>
	    			<select name="changeType" id="changeType" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true"  style="width:205px;">
	    				<option></option>
	    				<option value="1">加号换机</option>
	    				<option value="2">不加号换机</option>
	    			</select>
	    		</td>
	    	
	    		<th>机具型号：</th>
	    		<td>
	    			<select id="bmaID" name="bmaID" class="easyui-combogrid" data-options="
	    				editable:false,
	    				required:true,
						url : '${ctx}/sysAdmin/machineInfo_searchNormalMachineInfo.action',
						idField:'bmaID',
						textField:'machineModel',
						mode:'remote',
						width:205,
						fitColumns:true,
						columns:[[ 
							{field:'bmaID',title:'主键',width:150,hidden:true},
							{field:'machineModel',title:'机具型号',width:150},
							{field:'machineTypeName',title:'机具类型',width:150}
						]]
	    			"></select>
	    		</td>
	    	</tr>
	    	<tr id="newTID">
	    		<th id="tids">新终端：</th>
	    		<td id="tidss">
	    			<select id="tid" name="tid" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select>
	    		</td>
	    		
	    		 <th>备注：</th>
				<td colspan="3"><input id="remarks"  name="remarks"  class="easyui-validatebox" data-options="required:true" style="width:150px" /><font color="red">&nbsp;*</font></td>
				</td> 
	    		
	    	</tr>
	    </table>
	</fieldset>
</form>  

