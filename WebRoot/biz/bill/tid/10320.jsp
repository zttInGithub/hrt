<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		$("#level").combobox({
			valueField : 'id',
			textField : 'value',
			data : [ {
				id : '7',
				value : '代理商'
			}, {
				id : '5',
				value : '分公司'
			} ],
			onSelect : function(rec) {
				loadModel(rec);
			}
		});
		
		$("#tidtype").combobox({
			valueField : 'id',
			textField : 'value',
			data : [ {
				id : '1',
				value : '短密钥'
			}, {
				id : '2',
				value : '长密钥'
			} ]
		});
	});

	function loadModel(rec) {
		var lvl=0;
		if(rec.id==7){
			lvl=2;
		}else{
			lvl=1;
		}
		$("#unno").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes2.action?unLvl='+ lvl,
			valueField : 'UNNO',
			textField : 'UN_NAME'
		});
	}

	function dosubmit(){
		var level = $("#level").combobox('getValue');
		var unno = $("#unno").combobox('getValue');
		var tidtype = $("#tidtype").combobox('getValue');
		var num = $("#tidnum").val();
		if(level==""||unno==""||num==""||tidtype==""){
			$.messager.alert('提示', "有空值，不能生成");
			return;
		}
		if(num<1 || num>200){
			$.messager.alert('提示', ' 都说了一次最多生成200个，你是要闹哪样！');
			return;
		}
		$("#params").val(level+"#"+unno+"#"+tidtype+"#"+num);
		$.messager.confirm('确认','您确认要生成终端号吗？',function(r){    
		    if (r){    
		        $('#sysAdmin_10320_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/terminalInfo_createInfo.action',
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
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
			    		}
			    	});  
		    }    
		});  
	}
	
	function closeTab(){
		var tab = $('#center-tab').tabs('getSelected');
		var index = $('#center-tab').tabs('getTabIndex', tab);
		if (tab.panel('options').closable) {
			$('#center-tab').tabs('close', index);
		} 
	}
	
	function checkNum(){
		var val=$("#tidnum").val(); 
		if(val<1 || val>200){
			$.messager.alert('提示', '一次最多生成200个，请填写1-200之间的数量');
		}
	}
</script>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height:500px; padding-top:10px;">
		<form id="sysAdmin_10320_addForm" style="padding-left:2%">
			<fieldset style="width: 400px;">
				<legend>终端号生成</legend>
				<table class="table1">
					<tr>
						<th>级别：</th>
						<td><input id="level" style="width: 200px;" class="easyui-combobox" />
						</td>
					</tr>
					<tr>
						<th>机构：</th>
						<td><input id="unno" style="width: 200px;" class="easyui-combobox" /></td>
					</tr>
					<tr>
						<th>密钥类型：</th>
						<td><input id="tidtype" style="width: 200px;" class="easyui-combobox" ></td>
					</tr>
					<tr>
						<th>数量：</th>
						<td><input type="text" id="tidnum" style="width: 195px;" onchange="checkNum();" class=" easyui-validatebox" data-options="required:true">
						</td>
					</tr>
				</table>
			</fieldset>
			<table class="tableForm" style="text-align: center;">
				<tr>
					<td style="padding-left: 150px;">
						<a id="" onclick="dosubmit()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-ok" style="padding-left: 20px;">确认</span>
						</span>
					</a>
					<a id="" onclick="closeTab()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-cancel" style="padding-left: 20px;">取消</span>
						</span>
					</a>
						
						</td>
				</tr>
			</table>
			<input type="hidden" name="params" id="params">
		</form>
	</div>

	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_10320_datagrid"></table>
	</div>
</div>
