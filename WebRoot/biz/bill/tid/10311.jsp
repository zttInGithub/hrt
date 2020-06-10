<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	var type = <%=request.getParameter("type") %>
	unno = "${sessionScope.user.unNo}";
    // @author:lxg-20190613 登录机构b11023单独修改费率
    if('b11023'==unno){
     //if('110000'==unno){
        $('.is_not_b11023').remove();
        $('#huodong91').remove();
        $('#termIDStart1').remove();
    }else if(type=='1'){
    	$('.is_not_b11023').remove();
    	$('#termIDb11023').remove();
    	$('#termIDStart').remove();
    	
	    	$('#termIDStart').combogrid({
	    		url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateFeiLv.action',
	    		idField:'MINFO1',
	    		textField:'MINFO1',
	    		mode:'remote',
	    		fitColumns:true,
	    		columns:[[
	    			{field:'MINFO1',title:'费率',width:70}
	    		]],
	    		onChange: function (newValue, oldValue) {
	    			getHuodong();
	    		}
	    	 
	    	});
    }else{
        $('.is_b11023').remove();
    }
	$('#keyContext').combogrid({
			//url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=MicRate',
			url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateIncludeActiveInfo.action?type=MicRate',//把活动带出来
			idField:'KEYCONTEXT',
			textField:'MINFO1',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'KEYCONTEXT',title:'费率-手续费',width:150,hidden:true},
				{field:'MINFO1',title:'描述',width:250}
			]] 
		});
	$('#secondRate').combogrid({
			//url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateInfo.action?type=SecondRate',
			url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateIncludeActiveInfo.action?type=SecondRate',//把活动带出
			idField:'UPLOAD_PATH',
			textField:'UPLOAD_PATH',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'UPLOAD_PATH',title:'手续费',width:150}
			]] 
	});
	

	$.extend($.fn.validatebox.defaults.rules, {
		rateValidator:{
			validator : function(value) {
				if(value<0.38||value>0.6){
					return false;
				}
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value); 
	        }, 
	        message : '请正确输入费率为0.38%-0.60%！' 
		},
		// @author:lxg-20190613 b11023修改限制0.52-0.6
		selfrateValidator: {
			validator: function (value, param) {
				if(/^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value)){
					if(value<0.52 || value>0.6){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			},
			message: '请正确输入费率为0.52%-0.60%！'
		},
		// @author:yq-20191107 极速版修改限制0.5-0.6
		selfrateValidatorJisu: {
			validator: function (value, param) {
				if(/^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value)){
					if(value<0.5 || value>0.6){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			},
			message: '请正确输入费率为0.50%-0.60%！'
		},
		// @author:lxg-20190613 b11023修改限制0-3  -add活动91不可以为0
		range:{
			validator:function(value,param){
				if(/^[0-9]\d*$/.test(value)){
					return value >= param[0] && value <= param[1]
				}else{
					return false;
				}
			},
			message:'请正确输入的手续费在{0}到{1}之间！'
		},
	});
	
	function getHuodong() {
		// console.log($('#rebateType_wallet_cash_switch_add').combobox('getValue'));
		var rebateTypeValue=($('#termIDStart').combobox('getValue')*10000).toFixed(0);
		$('#termIDEnd1').val("");
		if(rebateTypeValue==-1){
			$.messager.alert('提示', '请先选择费率');
		}else{
			$("#termIDEnd1").combogrid("clear"); 
			$('#termIDEnd1').combogrid({
				url : '${ctx}/sysAdmin/terminalInfo_queryTerminalRateSXUFei.action?rebateType='+rebateTypeValue,
				idField:'SECONDRATE',
				textField:'SECONDRATE',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'SECONDRATE',title:'手续费',width:150}
				]]
			});
		}
	};
	

})
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="sysAdmin_editRate_editForm" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
				<tr class="is_not_b11023">
					<th style="width:100px;">费率&手续费：</th>
   					<td style="width:270px;">
   						<!-- <input type="text" name="rate" id="rate" style="width:100px;" class="easyui-validatebox"  data-options="validType:'unnoValidator',editable:false" maxlength="25"  /><font color="red">&nbsp;*</font> -->
	   					<select id="keyContext" name="keyContext" class="easyui-combogrid" data-options="required:true,editable:false" style="width:250px;"></select><font color="red">&nbsp;*</font>
   					</td>
				</tr>
        <%-- @author:lxg-20190613 b11023登录机构用户,单独修改费率与手续费,费率0.52-0.6--%>
				<tr><td>&nbsp;</td></tr>
        <tr class="is_b11023">
          <th style="width:100px;">费率：</th>
          <td style="width:270px;" id="termIDStart">
            <input type="text"  name="termIDStart" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'selfrateValidator',required:true"/><font color="red">&nbsp;%*</font>
          </td>
          <!-- 极速版费率修改为0.5-0.6 -->
          <td style="width:170px;" id="termIDStart1"  >
           <select id="termIDStart" name="termIDStart" class="easyui-combogrid"   data-options="required:true,editable:false" style="width:150px;" ></select>
          </td>
        </tr>
				<tr><td>&nbsp;</td></tr>
        <tr class="is_b11023">
          <th style="width:100px;">手续费：</th>
          <td style="width:270px;" id="termIDb11023"  >
            <input type="text" name="termIDEnd" id="termIDEnd" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'range[0,3]',required:true"/><font color="red">&nbsp;*</font>
          </td>
          <!-- 活动91（极速版）的手续费不包括0 -->
          <td style="width:170px;" id="huodong91">
            <select id="termIDEnd1" name="termIDEnd" class="easyui-combogrid" data-options="required:true,editable:false" style="width:150px;"></select>

        </tr>
				<tr><td>&nbsp;</td></tr>
				<tr >
					<th style="width:120px;">扫码费率：</th>
   					<td >
   						<input type="text" name="scanRate" id="scanRate" style="width:100px;" class="easyui-validatebox" maxlength="50" data-options="validType:'rateValidator',required:true"/><font color="red">&nbsp;%*</font>
   					</td>
				</tr> 
			</table>
		</form>
</div>


