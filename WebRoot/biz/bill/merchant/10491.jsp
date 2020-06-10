<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	var bmid = <%=request.getParameter("bmid")%>;
	var unno = $("#UNNO").val();
	var mid = <%=request.getParameter("mid")%>;
	$(function() {
		$('#sysAdmin_104911_datagrid').datagrid({
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
				title :'机型',
				field :'bmaidName',
				width : 100
			},{
				title :'装机地址',
				field :'installedAddress',
				width : 100
			},{
				title :'设备厂商',
				field :'installedName',
				width : 100
			},{
				title :'联系人',
				field :'contactPerson',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
						return '<img src="${ctx}/images/start.png" title="取消终端" style="cursor:pointer;" onclick="sysAdmin_10491_deleteFun('+row.bmtid+','+row.tid+')"/>';
				}
			}]]
		});
	});

	function sysAdmin_10491_deleteFun(bmtid,tid){
		$.messager.confirm('确认','您确认要删除所选终端吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/merchantterminalinfo_deleteMerchantTerminalInfo.action?bmtid="+bmtid+"&tid="+tid,
					type:'post',
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_104911_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
					}
				});
			}
		});
	}	
	
	$(function(){
		$('#qtid_10491').combogrid({
			url : '${ctx}/sysAdmin/terminalInfo_searchTerminalInfoUnno.action?unno='+unno,
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
				$("#qsn").val(row.SN);
			}
		});
		
		$('#qbmaid').combogrid({
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
	
	function qdosubmit(){
		var rows = $('#sysAdmin_104911_datagrid').datagrid('getRows');
		if(row == 1 && rows.length <= 0){
			$.messager.alert('提示', "请添加终端");
			return false;
		}
		$.messager.confirm('确认','您确认要添加终端吗？',function(r){    
		    if (r){
	    		$.messager.progress();
		        $('#sysAdmin_10491_addForm').form('submit',{
		    		url:'${ctx}/sysAdmin/merchantterminalinfo_addMerchantTerminalInfo.action',
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
								$('#bbody').children().remove();
								$('#sysAdmin_merchantZK_datagrid').datagrid('reload');
								$('#sysAdmin_merchantterminalinfo_run').dialog('destroy');
				    		} else {
				    			$.messager.alert('提示', result.msg);
					    	}
				    	}
	    			}
		    	});
		    }    
		});  
	}
	
	function qcloseTab(){
		$('#sysAdmin_merchantterminalinfo_run').dialog('destroy');
	}
	
	var row = 1;
	function qaddTr() {
		if(row>10){
			$.messager.alert('提示', "一次添加不要大于10条");
			return;
		}
		var html = "<tr id='trm"+row+"'>";
		var td = "<td style='text-align: center;'>";
		var tid = $("#qtid_10491").combobox('getValue');
		var bmaid=$("#qbmaid").combobox('getValue');
		var tidText = $("#qtid_10491").combobox('getText');
		
		if(!qchickTid(tidText)){
			$.messager.alert('提示', "不能添加重复终端编号");
			return;
		}
		var bmaidText = $("#qbmaid").combobox('getText');
		var installedAddress = $("#qinstalledAddress").val();
		var installedName = $("#qinstalledName").val();
		var contactPerson = $("#qcontactPerson").val();
		var contactPhone = $("#qcontactPhone").val();
		var contactTel = $("#qcontactTel").val();
		var sn = $("#qsn").val();
		var installedSIM = '2';
		if(tidText==""||bmaidText==""||installedAddress==""||installedSIM=="0"||installedSIM==""||sn==""){
			$.messager.alert('提示', "有空值，不能添加");
			return;
		}
		html += "<input type='hidden' name='IDNUM' value='"+tid+"#separate#"+bmaid+"#separate#"+installedAddress+"#separate#"+installedName+"#separate#"+contactPerson+"#separate#"+contactPhone+"#separate#"+contactTel+"#separate#"+installedSIM+"#separate#"+sn+"'/>";
		html += td
				+ tidText
				+ "</td>"
				+ td
				+ bmaidText
				+ "</td>"
				+ td
				+ installedAddress
				+ "</td>"
				+ td
				+ installedName
				+ "</td><td style='width: 65px;text-align: center;'><a href='#' class='easyui-linkbutton 1-btn' onclick='qdelTr(\"trm"+row+"\")'  >"
				+ "删除"
				+ "</a></td>";
		$("#bbody").append(html);
		row++;
	}
	
	function qdelTr(id) {
		var tr=$("#"+id);
		tr.remove();
		row--;
	}
	
	function qchickTid(tidV){
		for(var i=0;i<row-1;i++){
			var tr=$("#trm"+(i+1));
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
.table1 td{
	width:300px;
}
</style>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false">
	   	<form id="sysAdmin_10491_addForm" style="padding-left:20px;" method="post">
		   	<fieldset style="width: 900px;">
				<legend>终端信息</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>终端编号：</th>
		   				<td><select id="qtid_10491" name="tid" style="width:280px;" class="easyui-combogrid" data-options="editable:false" ></select><font color="red">&nbsp;*</font></td>
		   			
		   				<th>设备型号：</th>
		   				<td><select id="qbmaid" name="bmaid" style="width:155px;" ></select><font color="red">&nbsp;*</font></td>
		   				
		   				<td style="width: 65px;"><a href="#" class="easyui-linkbutton" onclick="qaddTr()">添加</a></td>
		   			</tr>
		   			<tr>
		   				<th>装机地址：</th>
		   				<td><input type="text" id="qinstalledAddress" value="<%=java.net.URLDecoder.decode(request.getParameter("baddr"),"UTF-8")%>" name="installedAddress" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="200"><font color="red">&nbsp;*</font></td>
		   			
		   				<th>设备厂商：</th>
		   				<td><input type="text" id="qinstalledName" name="installedName" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="50"></td>
		   			</tr>
		   			<tr>
		   				<th>联系人：</th>
		   				<td><input type="text" name="contactPerson"  value="<%=java.net.URLDecoder.decode(request.getParameter("contactPerson"),"UTF-8")%>" id="qcontactPerson" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="30"></td>
		   			
		   				<th>联系人电话：</th>
		   				<td><input type="text" name="contactPhone" id="qcontactPhone"  value="<%=request.getParameter("contactPhone")%>"  style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"></td>
		   			</tr>
		   			<tr>
		   				<th>联系人固话：</th>
		   				<td><input type="text" name="contactTel" id="qcontactTel" value="<%=request.getParameter("contactTel")%>" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" maxlength="20"></td>
		   				<th>设备TUSN号：</th>
		   				<td><input type="text" name="sn" id="qsn"  style="width: 150px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"><font color="red">&nbsp;*</font></td>
		   				<!-- <th>通讯方式：</th>
		   				<td>
		   					<select name="installedSIM" id="qinstalledSIM" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:155px;">
			    				<option value="0"></option>
			    				<option value="1">机构自备</option>
			    				<option value="2">公司提供</option>
			    				<option value="3">网络</option>
		    				    <option value="4">电话线</option>
			    			</select>
			    			<font color="red">&nbsp;*</font>
		   				</td>
		   				 -->
		   			</tr>
		   		</table>
		   		<table class="table1">
					<thead>
						<tr>
							<th style="text-align: center;">终端编号</th>
							<th style="text-align: center;">设备编号</th>
							<th style="text-align: center;">装机地址</th>
							<th style="text-align: center;">设备厂商</th>
							<th style="text-align: center; width: 65px;">操作</th>
						</tr>
					</thead>
					<tbody id="bbody">
					</tbody>
				</table>
			</fieldset>
			<table class="tableForm" style="text-align: center;">
				<tr>
					<td style="padding-left: 350px;">
						<a id="" onclick="qdosubmit()" class="l-btn" href="javascript:void(0)">
							<span class="l-btn-left">
							<span class="l-btn-text icon-ok" style="padding-left: 20px;">确认</span>
							</span>
						</a>
						<a id="" onclick="qcloseTab()" class="l-btn" href="javascript:void(0)">
							<span class="l-btn-left">
							<span class="l-btn-text icon-cancel" style="padding-left: 20px;">关闭</span>
							</span>
						</a>
					</td>
				</tr>
			</table>
			<fieldset>
				<legend>终端信息</legend>
				   <div class="easyui-layout" style="height: 250px;" data-options="border:false">
				   		<div data-options="region:'center', border:false" style="overflow: hidden;height: 10px;">
				   			<table id="sysAdmin_104911_datagrid"></table>
				   		</div>
				   </div>
			</fieldset>
			<input type="hidden" name="BMID" value="<%=request.getParameter("bmid")%>" />
			<input type="hidden" name="MID" value="<%=request.getParameter("mid")%>" />
			<input type="hidden" name="UNNO" id="UNNO" value="<%=request.getParameter("unno")%>" />
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10491_datagrid"></table>
	</div> 
</div>
		