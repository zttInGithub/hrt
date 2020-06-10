<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
		//查询提交
	function check_20500_login(){
		$('#sysAdmin_check_20500_login').form('submit',{
			         	url:'${ctx}/sysAdmin/paymentRisk_Login',
			         	success:function(msg) {
		    				var result = $.parseJSON(msg);
				    			if (result.success) {
					    			$.messager.show({ 
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	//}
			    		}
					});
	}

	function check_20500_logout(){
		$('#sysAdmin_check_20500_login').form('submit',{
			         	url:'${ctx}/sysAdmin/paymentRisk_logout',
			         	success:function(msg) {
		    				var result = $.parseJSON(msg);
				    			if (result.success) {
					    			$.messager.show({ 
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	//}
			    		}
					});
	}


</script>


<div  data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_check_20500_login" style="padding-left:3%">
			<table class="tableForm" >
				<tr>
				     <th>会员单位机构号：</th>
						<td><input  class="easyui-validatebox" name="OrigSender" style="width: 156px;"/>
					</td>
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20500_login();">登录风险系统</a>
						
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20500_logout();">退出风险系统</a>
							
					</td>
			    </tr>
				
			</table>
		</form>
	</div>  

</div>
