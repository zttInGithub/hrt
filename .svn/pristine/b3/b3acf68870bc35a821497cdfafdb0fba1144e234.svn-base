<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	var total=0.00;
	var row = 1;
	$(function() {
		$("#machineType").combobox({
			valueField : 'id',
			textField : 'value',
			data : [ {
				id : '1',
				value : '有线(拨号)'
			}, {
				id : '2',
				value : '有线(拨号和网络)'
			}, {
				id : '3',
				value : '无线'
			} ,{
				id : '4',
				value : '手刷'
			}],
			onSelect : function(rec) {
				loadModel(rec);
			}
		});
	});

	function loadModel(rec) {
		$("#machineModel")
				.combobox(
						{
							url : '${ctx}/sysAdmin/machineInfo_searchMachineInfoType.action?machineType='
									+ rec.id,
							valueField : 'bmaID',
							textField : 'machineModel',
							onSelect : function(rce) {
								setPrice(rce);
							}
						});
	}

	function setPrice(rec) {
		$.ajax({
			type : "POST",
			url : '${ctx}/sysAdmin/machineInfo_getInfo.action',
			data : {
				"bmaID" : rec.bmaID
			},
			error : function(request) {
				alert("Connection error");
			},
			success : function(data) {
				var json = eval("(" + data + ")");
				$("#machinePrice").val(json[0].machinePrice);
			}
		});
	}

	function delTr(id) {
		var tr=$("#"+id);
		var price=tr.find("td").eq(2).text();
		var num=tr.find("td").eq(3).text();
		total-=price*num;
		$("#totalAmo").text(total);
		$("#orderAmount").val(total);
		tr.remove();
		row--;
	}
	function addTr() {
		if(row>10){
			$.messager.alert('提示', "一个订单不能大于10种机具");
			return;
		}
		var html = "<tr id='tr"+row+"'>";
		var td = "<td style='text-align: center;'>";
		var modelValue = $("#machineModel").combobox('getValue');
		var typeText=$("#machineType").combobox('getText');
		var model = $("#machineModel").combobox('getText');
		var price = $("#machinePrice").val();
		var num = $("#orderNum").val();
		if(typeText==""||model==""||price==""||num==""){
			$.messager.alert('提示', "有空值，不能添加");
			return;
		}
		html += "<input type='hidden' name='IDNUM' value='"+modelValue+"#"+num+"#"+price+"#"+num*price+"'/>";
		html += td
				+ typeText
				+ "</td>"
				+ td
				+ model
				+ "</td>"
				+ td
				+ price
				+ "</td>"
				+ td
				+ num
				+ "</td><td style='width: 65px;text-align: center;'><a href='#' class='easyui-linkbutton 1-btn' onclick='delTr(\"tr"+row+"\")'  >"
				+ "删除"
				+ "</a></td>";
		$("#tbody").append(html);
		total+=price*num;
		$("#totalAmo").text(total);
		$("#orderAmount").val(total);
		row++;
	}
	function dosubmit(){
		if(row==1){
			$.messager.alert('提示', "请添加机具");
			return false;
		}
		$.messager.confirm('确认','您确认要提交订单吗？',function(r){    
		    if (r){  
		    	$("#submitb").hide();
		        $('#sysAdmin_info_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/machineApprove_add.action',
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
									closeTab();
					    		} else {
					    			$.messager.alert('提示', result.msg);
					    			$("#submitb").show();
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
	function changFielld(val){
		if(val=='1'){
			$("#ziti").show();
			$("#songhuo").hide();
		}else{
			$("#songhuo").show();
			$("#ziti").hide();
		}
	}
</script>
<div class="easyui-layout"  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false"
		style="height:400px; padding-top:10px;">
		<form id="sysAdmin_info_addForm" style="padding-left:5%">
			<fieldset style="width: 950px;">
				<legend>机具信息</legend>
				<table class="table1">
					<tr>
						<th>机具类型：</th>
						<td><input id="machineType" style="width: 156px;"
							class="easyui-combobox" />
						</td>
						<th>机具型号：</th>
						<td><input id="machineModel" style="width: 156px;"
							class="easyui-combobox" /></td>
						<td style="width: 100px;">
						<a id="" onclick="addTr()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-add" style="padding-left: 20px;">添加</span>
						</span>
						</a>
						</td>
					</tr>
					<tr>
						<th>销售单价：</th>
						<td><input type="text" id="machinePrice"
							style="width: 150px;" readonly="readonly">元</td>
						<th>机具数量：</th>
						<td><input type="text" id="orderNum" style="width: 150px;"
							class=" easyui-numberbox" data-options="required:true">个
						</td>
					</tr>
				</table>
				<table class="table1">
					<thead>
						<tr>
							<th style="text-align: center;">机具类型</th>
							<th style="text-align: center;">机具型号</th>
							<th style="text-align: center;">销售单价</th>
							<th style="text-align: center;">机具数量</th>
							<th style="text-align: center;width: 100px;">操作</th>
						</tr>
					</thead>
					<tbody id="tbody">
					</tbody>
				</table>
			</fieldset>
			<fieldset style="width: 950px;">
				<legend>送货方式</legend>
				<table class="table1">
					<tr>
						<td><input type="radio" name="shipmeThod" value="1" onclick="changFielld('1')" checked="checked">
						自提
						<input type="radio" name="shipmeThod" value="2" onclick="changFielld('2')">
						送货
						</td>
					</tr>
					</table>
				</fieldset >
			<fieldset id="ziti" style="width: 950px;">
				<legend>自提地址</legend>
				<table class="table1">
					<tr>
					<th>北京市</th>
						<td style="width: 75%;" colspan="3">中关村北大街甲28号海淀文化艺术大厦A座9层</td>
					</tr>
					</table>
				</fieldset>
			<fieldset id="songhuo" style="width: 950px;display: none;">
				<legend>收货人信息</legend>
				<table class="table1">
					<tr>
						<th>收货人姓名：</th>
						<td><input type="text" name="consigneeName" style="width: 150px;"><font color="red">&nbsp;*</font>
						</td>
						<th>收货人手机：</th>
						<td><input type="text" name="consigneePhone" style="width: 150px;"
						class=" easyui-numberbox" data-options="validType:'length[11,11]'"><font color="red">&nbsp;*</font></td>
					</tr>
					<tr>
						<th>收货人固定电话：</th>
						<td><input type="text" name="consigneeTel" style="width: 150px;"></td>
						<th>收货人地址：</th>
						<td><input type="text" name="consigneeAddress" style="width: 150px;"><font color="red">&nbsp;*</font>
						</td>
					</tr>
					<tr>
						<th>收货人邮编：</th>
						<td><input type="text" name="postCode" style="width: 150px;"
						class=" easyui-numberbox" data-options="validType:'length[6,6]'"><font color="red">&nbsp;*</font></td>
					</tr>
				</table>
			</fieldset>
			<table class="tableForm" style="text-align: center;">
				<tr>
					<td>订单总金额为：<span id="totalAmo" style="font-style: oblique;font-size: 14px;font-weight:bold;">0</span>元
					<input id="orderAmount" type="hidden" name="orderAmount">
					</td>
					<td style="padding-left: 300px;">
					<a id="submitb" onclick="dosubmit()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-ok" style="padding-left: 20px;">确认</span>
						</span>
					</a>
					<a id="closeb" onclick="closeTab()" class="l-btn" href="javascript:void(0)">
						<span class="l-btn-left">
						<span class="l-btn-text icon-cancel" style="padding-left: 20px;">取消</span>
						</span>
					</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center', border:false"
		style="overflow: hidden;">
		<table id="sysAdmin_machineBuy_datagrid"></table>
	</div>
</div>
