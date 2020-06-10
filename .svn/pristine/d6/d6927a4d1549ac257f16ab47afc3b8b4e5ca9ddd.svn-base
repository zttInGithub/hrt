<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var row = 1;
	$(function(){
		/*
		$('#nmid').combogrid({
			onClickRow:function(index, row){
				console.log("111")
				var mid = $('#nmid').combogrid('getValues');
				$('#ntid').combogrid({
					url : '${ctx}/sysAdmin/terminalInfo_searchTerminalInfo.action?mid='+mid,
					idField:'TERMID',
					textField:'TERMID',
					fitColumns:true,
					columns:[[ 
						{field:'BTID',title:'主键',width:150,hidden:true},
						{field:'TERMID',title:'终端编号',width:150},
						{field:'KEYTYPENAME',title:'密钥类型',width:150},
						{field:'SN',title:'SN',width:150}
						]],
					onClickRow:function(index, row){
						$("#ncontactTel").val(row.SN);
					}
				});
			}
		});
		*/
		$('#nbmaid').combogrid({
			url : '${ctx}/sysAdmin/machineInfo_searchNormalMachineInfo.action',
			idField:'bmaID',
			textField:'machineModel',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'bmaID',title:'主键',width:150,hidden:true},
				{field:'machineModel',title:'机具型号',width:150},
				{field:'machineTypeName',title:'机具类型',width:150}
			]]
		});
	});
	
	function dosubmit(){
		if(row==1){
			$.messager.alert('提示', "请添加终端");
			return false;
		}
		$.messager.confirm('确认','您确认要添加终端吗？',function(r){    
		    if (r){
		    	$.messager.progress();
		        $('#sysAdmin_merchantterminalinfo_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchantterminalinfo_addMerchantTerminalInfoEdit.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
									$('.table1 input').val('');
									$('#nbody').children().remove();
									closeTab();
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
			    		}
			    	});  
		    }    
		});  
	}
	function addTr2() {
		//$("#ninstalledAddress").val("0");
		//$("#ninstalledSIM").val("机构自备");
		$.messager.confirm('确认','您确认要导入终端吗？',function(){
			$('#sysAdmin_merchantterminalinfo_addForm').form('submit',{
				url:'${ctx}/sysAdmin/merchantterminalinfo_addMerchantTerminalInfoImport.action',
				onSubmit:function(){
					var contact = document.getElementById('upload_merchant_File2').value;
					if(contact == "" || contact == null){
						$.messager.show({
							title:'提示',
							msg:'请选择要上传的文件',
							timeout:5000,
							showType:'slide'
						});
						return false;
					}
					if(contact != "" && contact != null){
						var l = contact.split(".");
						if(l[1] == "xls" || l[1]=="csv"){
							document.getElementById('import_merchant_Name2').value = contact.replace(/.{0,}\\/, "");
							//$('#hidenButton').linkbutton({disabled:true});
							return true;
						}else{
							$.messager.show({
								title:'提示',
								msg:'请选择后缀名为.xls/.csv文件',
								timeout:5000,
								showType:'slide'
							});
							return false;
							}
					}
				},
				//成功返回数据
				success:function(data){
					var resault = $.parseJSON(data);
					if(resault.sessionExpire){
						window.location.href = getProjectLocation();
					}else{
						if(resault.success){
							$.messager.show({
								title:'提示',
								msg  :resault.msg
							});
						}else{
							$.messager.show({
								title:'提示',
								msg  :resault.msg
							});
						}
					}
				}
			});
		});  
	}
	function addTr1() {
		if(row>10){
			$.messager.alert('提示', "一次添加不要大于10条");
			return;
		}
		var html = "<tr id='mtr"+row+"'>";
		var td = "<td style='text-align: center;'>";	
		var mid = $("#nmid").combobox('getValue');//商户编号	
		var tid=$("#ntid").combobox('getText');//终端编号
		if(tid!="")
		{if(!chickTid(tid)){
			$.messager.alert('提示', "不能添加重复终端编号");
			return;
		}}
		var bmaid = $("#nbmaid").combobox('getValue');//设备编号		
		var installedAddress = $("#ninstalledAddress").val();//装机地址
		var installedName = "";//设备厂商
		var contactPerson="";
		var contactPhone="";
		var contactTel="";		//设备SN
		
		//var installedSIM=$("#ninstalledSIM").combobox('getValue');
		var installedSIM="2";
		
		if($("#ninstalledName").val()==""){
			installedName = "null";//联系人
		}else{
			installedName = $("#ninstalledName").val();//联系人
		}
		if($("#ncontactPerson").val()==""){
			contactPerson = "null";//联系人
		}else{
			contactPerson = $("#ncontactPerson").val();//联系人
		}
		if($("#ncontactPhone").val()==""){
			contactPhone = "null";//联系人
		}else{
			contactPhone = $("#ncontactPhone").val();//联系人电话
		}
		if($("#ncontactTel").val()==""){
			contactTel = "null";//联系人
		}else{
			contactTel = $("#ncontactTel").val();//设备SN
		}
		var sn = $("#nsn").val();
		if(mid==""||tid==""||bmaid==""||installedAddress==""||installedSIM=="0"||installedSIM==""||sn==""){
			$.messager.alert('提示', "必填项有空值，不能添加");
			return;
		}
		html += "<input type='hidden' name='MIDNUM' value='"+mid+"#separate#"+tid+"#separate#"+bmaid+"#separate#"+installedAddress+"#separate#"+installedName+"#separate#"+contactPerson+"#separate#"+contactPhone+"#separate#"+contactTel+"#separate#"+installedSIM+"#separate#"+sn+"'/>";
		html += td
				+ tid
				+ "</td>"
				+ td
				+ bmaid
				+ "</td>"
				+ td
				+ installedAddress
				+ "</td>"
				+ td
				+ installedName
				+ "</td><td style='width: 100px;text-align: center;'><a href='#' class='easyui-linkbutton 1-btn' onclick='delTr(\"mtr"+row+"\")'  >"
				+ "删除"
				+ "</a></td>";
		$("#nbody").append(html);
		row++;
		if(row>1){
			$('#nmid').combobox('disable');
		}
	}
	function delTr(id) {
		var tr=$("#"+id);
		tr.remove();
		row--;
		if(row==1){
			 $('#nmid').combobox('enable');
		}
	}
	function closeTab(){
		var tab = $('#center-tab').tabs('getSelected');
		var index = $('#center-tab').tabs('getTabIndex', tab);
		if (tab.panel('options').closable) {
			$('#center-tab').tabs('close', index);
		} 
	}
	function chickTid(tidV){
		for(var i=0;i<row-1;i++){
			var tr=$("#mtr"+(i+1));
			var tid=tr.find("td").eq(0).text();
			if(tidV==tid){
				return false;
			}
		}
		return true;
	}
	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
</script>
<style>
.table1 th{
	width:120px;
}
</style>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:500px;padding-top:10px;">
	   	<form id="sysAdmin_merchantterminalinfo_addForm" style="padding-left:5%;" method="post" enctype="multipart/form-data">
	   	<input type="hidden" name="importMerchantFile2" id="import_merchant_Name2">
	   	<fieldset style="width: 950px;">
			<legend>装机信息</legend>
	   		<table class="table1">
	   			<tr>
	   				<th>商户名称：</th>
	   				<td style="width: 280px;" colspan="3">
	   					<select class="easyui-combogrid" name="mid" id="nmid" data-options="  
				            required:true,
				            url : '${ctx}/sysAdmin/merchant_searchMerchantInfo.action?type=0,2,3,4,5,9',
							idField:'mid',
							textField:'rname',
							mode:'remote',
							width:583,
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
							]],
							onClickRow:function(index, row){
								var mid = $('#nmid').combogrid('getValues');
								$('#ntid').combogrid({
									url : '${ctx}/sysAdmin/terminalInfo_searchTerminalInfo.action?mid='+mid,
									idField:'TERMID',
									textField:'TERMID',
									fitColumns:true,
									columns:[[ 
										{field:'BTID',title:'主键',width:150,hidden:true},
										{field:'TERMID',title:'终端编号',width:150},
										{field:'KEYTYPENAME',title:'密钥类型',width:150},
										{field:'SN',title:'SN',width:150}
										]],
									onClickRow:function(index, row){
										$('#nsn').val(row.SN);
									}
								});
							}
				        "></select><font color="red">&nbsp;*</font>
	   				</td>
	   				<td style="width: 100px;">
						<a id="" onclick="addTr1()" class="l-btn" href="javascript:void(0)">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add" style="padding-left: 20px;">添加</span>
							</span>
						</a>
					</td>
	   			</tr>
	   			<tr>
	   				<th>终端编号：</th>
	   				<td style="width:300px"><select id="ntid" name="tid" style="width:280px;" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:155px;"></select><font color="red">&nbsp;*</font></td>
	   				
	   				<th>设备型号：</th>
	   				<td><select id="nbmaid" name="bmaid" class="easyui-combogrid" data-options="required:true,validType:'spaceValidator'" style="width:155px;"></select><font color="red">&nbsp;*</font></td>
	   			</tr>
	   			<tr>
	   				<th>装机地址：</th>
	   				<td><input type="text" id="ninstalledAddress"  name="installedAddress" style="width: 150px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" maxlength="200"><font color="red">&nbsp;*</font></td>
	   			
	   				<th>设备厂商：</th>
	   				<td><input type="text" id="ninstalledName" name="installedName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="50"></td>
	   			</tr>
	   			<tr>
	   				<th>联系人：</th>
	   				<td><input type="text" id="ncontactPerson" name="contactPerson" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="30"></td>
	   			
	   				<th>联系人电话：</th>
	   				<td><input type="text" id="ncontactPhone" name="contactPhone" style="width: 150px;" maxlength="20" class="easyui-validatebox" data-options="validType:'spaceValidator'"></td>
	   			</tr>
	   			<tr>
	   				<th>联系人固话：</th>
	   				<td><input type="text" id="ncontactTel" name="contactTel" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"></td>
	   				<th>设备TUSN：</th>
	   				<td><input type="text" id="nsn" name="sn" style="width: 150px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"><font color="red">&nbsp;*</font></td>
	   				<!-- 
	   				<th>通讯方式：</th>
	   				<td colspan="4">
	   					<select name="installedSIM" id="ninstalledSIM" class="easyui-combobox" data-options="panelHeight:'auto',editable:false,required:true"  style="width:155px;">
		    				<option value="0"></option>
		    				<option value="1">机构自备</option>
		    				<option value="2">公司提供</option>
		    				<option value="3">网络</option>
		    				<option value="4">电话线</option>
		    			</select><font color="red">&nbsp;*</font>
	   				</td>
	   				 -->
	   			</tr>
	   		</table>
	   		<table class="table1">
					<thead>
						<tr>
							<th style="text-align: center;">终端编号</th>
							<th style="text-align: center;">设备型号</th>
							<th style="text-align: center;">装机地址</th>
							<th style="text-align: center;">设备厂商</th>
							<th style="text-align: center;width: 100px;">操作</th>
						</tr>
					</thead>
					<tbody id="nbody">
					</tbody>
				</table>
	   		<table>
	   			<tr>
	   				<td style="padding-left: 350px;"><a id="" onclick="dosubmit()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-ok" style="padding-left: 20px;">确认</span>
						</span>
					</a>
					<a id="" onclick="closeTab()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-cancel" style="padding-left: 20px;">取消</span>
						</span>
					</a>
	   			</tr>
	   		</table>
	   		</fieldset>
	   	</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
	<table id="sysAdmin_10490_datagrid"></table>
</div>
</div>
