<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 
	$(function(){
		 $('#rebateType_cashbackTemplate_add_1').combogrid({
			url : '${ctx}/sysAdmin/cashbackTemplate_listRebateRate.action',
			idField:'VALUEINTEGER',
			textField:'NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'NAME',title:'活动类型',width:70},
				{field:'VALUEINTEGER',title:'id',width:70,hidden:true}
			]],
			onChange: function (newValue, oldValue) {
    			getCashbackType();
    		}
		}); 
	});
	
	function getCashbackType() {
		//debugger;
		var cashbackTempType_add_1=$('#rebateType_cashbackTemplate_add_1').combobox('getValue');
		//$('#cashbacktype_add_1').val("");
		if(cashbackTempType_add_1==-1){
			$.messager.alert('提示', '请先选择活动');
		}else{
			//debugger;
			$("#cashbacktype_add_1").combogrid("clear"); 
			$('#cashbacktype_add_1').combogrid({
				url :'${ctx}/sysAdmin/cashbackTemplate_queryRebatetypeCashbackType.action?rebateType='+cashbackTempType_add_1,
				//multiple: true,
			    idField:'CASHBACKTYPE',
				textField:'NAME1',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'NAME1',title:'返现类型',width:150},
					{field:'CASHBACKTYPE',title:'id',width:70,hidden:true}
				]]/* ,
				onSelect: function (newValue, oldValue) {
					$('#jisupinjie').val(jisushezhi);
	    		} */
				
			});
		}
	};
	$.extend($.fn.validatebox.defaults.rules, {
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});
	$.extend($.fn.validatebox.defaults.rules,{
		rateValidator:{
			validator : function(value) {
	            return /^(0(\.\d{2})?|1(\.0{2})?)$/i.test(value); 
	        }, 
	        message : '比例不合规，请输入0至1间数值【且两位小数】！' 
		}
	});
</script>
</head>
<form id="cashbackTemplate_add_1_from" style="padding-left:2%;padding-top:1%" method="post">
	<fieldset style="width: 400px; height: 190px;">
		<table class="table">
			<tr>	
	    		<th>活动类型</th>
	    		<td><select id="rebateType_cashbackTemplate_add_1" name="rebateType" data-options="required:true,editable:false,validType:'spaceValidator'" style="width:135px;"></select></td>
	    	</tr>
	    	
	    	<tr>
	    		<th>返现类型：</th>
	    		<td>
		    		<select id="cashbacktype_add_1" class="easyui-combogrid" data-options="required:true,editable:false,validType:'spaceValidator'"  style="width:130px;">
		              <!-- <option value="" selected="selected">All</option> -->
		             <!--  <option value="1">刷卡</option>
		              <option value="2">押金</option>
		              <option value="3">花呗分期</option> -->
		            </select>
	            </td>
	    	</tr>
	    	<tr>
	    		<th>下级代理可分比例-本月：</th>
	    		<td><input type="text" id="cashbackratio_add_1" name="cashbackratio" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'"/></td>
	    	</tr>
	    	<tr>
	    		<th>下级代理可分比例-下月：</th>
	    		<td><input type="text" id="cashbackrationext_add_1" name="cashbackrationext" style="width:130px;" maxlength="20" class="easyui-validatebox" data-options="required:true,validType:'rateValidator'"/></td>
	    	</tr>
		</table>
	</fieldset>
</form>