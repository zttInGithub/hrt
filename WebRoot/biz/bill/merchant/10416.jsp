<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	var bmid = <%=request.getParameter("bmid")%>
	var unno = <%=request.getParameter("unno")%>
	var mid = <%=request.getParameter("mid")%>

	$(function() {
		$('#sysAdmin_104161_datagrid').datagrid({
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
				title :'装机名称',
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
		$.messager.confirm('确认','您确认要删除所选终端吗?</br><span style="color:red">终端删除后不可再次添加到此商户下！</span>',function(result) {
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
							$('#sysAdmin_104161_datagrid').datagrid('reload');
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
		$('#qtid1').combogrid({
			url : '${ctx}/sysAdmin/terminalInfo_searchTerminalInfoUnno2.action?unno='+unno,
			idField:'TERMID',
			textField:'TERMID',
			fitColumns:true,
			columns:[[ 
				{field:'BTID',title:'主键',width:150,hidden:true},
				{field:'TERMID',title:'终端编号',width:150},
				{field:'KEYTYPENAME',title:'密钥类型',width:150}
			]]
		});
		
		$('#qbmaid').combogrid({
			url : '${ctx}/sysAdmin/machineInfo_searchMachineInfo.action',
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
		var rows = $('#sysAdmin_104161_datagrid').datagrid('getRows');
		if(row == 1 && rows.length <= 0){
			$.messager.alert('提示', "请添加终端");
			return false;
		}
		$.messager.confirm('确认','您确认要添加终端吗？',function(r){    
		    if (r){
	    		$.messager.progress();
		        $('#sysAdmin_10416_addForm').form('submit',{
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
								$('#sysAdmin_merchantZK_datagrid1').datagrid('reload');
								$('#sysAdmin_merchantterminalinfo_run1').dialog('destroy');
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
		$('#sysAdmin_merchantterminalinfo_run1').dialog('destroy');
	}
	
	var row = 1;
	function qaddTr() {
		if(row>10){
			$.messager.alert('提示', "一次添加不要大于10条");
			return;
		}
		var html = "<tr id='trm"+row+"'>";
		var td = "<td style='text-align: center;'>";
		var tid = $("#qtid1").combobox('getValue');
		var bmaid=$("#qbmaid").combobox('getValue');
		var tidText = $("#qtid1").combobox('getText');
		
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
		var installedSIM = $("#qinstalledSIM").combobox('getValue');
		
		if(tidText==""||bmaidText==""||installedAddress==""||installedSIM=="0"||installedSIM==""){
			$.messager.alert('提示', "有空值，不能添加");
			return;
		}
		html += "<input type='hidden' name='IDNUM' value='"+tid+"#separate#"+bmaid+"#separate#"+installedAddress+"#separate#"+installedName+"#separate#"+contactPerson+"#separate#"+contactPhone+"#separate#"+contactTel+"#separate#"+installedSIM+"'/>";
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
</script>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false">
	   	<form id="sysAdmin_10416_addForm" style="padding-left:20px;">
		   	<fieldset style="width: 900px;">
				<legend>终端信息</legend>
		   		<table class="table1">
		   			<tr>
		   				<th>终端编号：</th>
		   				<td><select id="qtid1" name="tid" style="width:155px;" class="easyui-combogrid" data-options="editable:false" ></select><font color="red">&nbsp;*</font></td>
		   			
		   				<th>设备型号：</th>
		   				<td><select id="qbmaid" name="bmaid" style="width:155px;"></select><font color="red">&nbsp;*</font></td>
		   				
		   				<td style="width: 65px;"><a href="#" class="easyui-linkbutton" onclick="qaddTr()">添加</a></td>
		   			</tr>
		   			<tr>
		   				<th>装机地址：</th>
		   				<td><input type="text" id="qinstalledAddress" value="<%=java.net.URLDecoder.decode(request.getParameter("baddr"),"UTF-8")%>" name="installedAddress" style="width: 150px;" maxlength="200"><font color="red">&nbsp;*</font></td>
		   			
		   				<th>装机名称：</th>
		   				<td><input type="text" id="qinstalledName" name="installedName" style="width: 150px;" maxlength="50"></td>
		   			</tr>
		   			<tr>
		   				<th>联系人：</th>
		   				<td><input type="text" name="contactPerson"  value="<%=java.net.URLDecoder.decode(request.getParameter("contactPerson"),"UTF-8")%>" id="qcontactPerson" style="width: 150px;" maxlength="30"></td>
		   			
		   				<th>联系人电话：</th>
		   				<td><input type="text" name="contactPhone" id="qcontactPhone"  value="<%=request.getParameter("contactPhone")%>"  style="width: 150px;" maxlength="20"></td>
		   			</tr>
		   			<tr>
		   				<th>联系人固话：</th>
		   				<td><input type="text" name="contactTel" id="qcontactTel" value="<%=request.getParameter("contactTel")%>" style="width: 150px;" maxlength="20"></td>
		   				
		   				<th>通讯方式：</th>
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
		   			</tr>
		   		</table>
		   		<table class="table1">
					<thead>
						<tr>
							<th style="text-align: center;">终端编号</th>
							<th style="text-align: center;">设备编号</th>
							<th style="text-align: center;">装机地址</th>
							<th style="text-align: center;">装机名称</th>
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
				   			<table id="sysAdmin_104161_datagrid"></table>
				   		</div>
				   </div>
			</fieldset>
			<input type="hidden" name="BMID" value="<%=request.getParameter("bmid")%>" />
			<input type="hidden" name="MID" value="<%=request.getParameter("mid")%>" />
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10416_datagrid"></table>
	</div> 
</div>

		