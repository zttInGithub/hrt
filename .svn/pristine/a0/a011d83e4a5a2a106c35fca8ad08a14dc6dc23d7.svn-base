<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	
	//清除表单内容
	function sysAdmin_pwd_cleanFun() {
		$('#sysAdmin_pwd_searchForm input').val('');
	}
	
	function sysAdmin_pwd_updateFun() {
		if(check()){
			$('#sysAdmin_pwd_searchForm').form('submit', {
				url:'${ctx}/sysAdmin/user_editPwd.action',
				success:function(data){
					var res = $.parseJSON(data);
					if(res.success) {
						var resetFlag = ${user.resetFlag};
						if(resetFlag == 0){
		 					$.messager.alert('提示','修改密码成功',null,function(data){
		 						logoutFun();
		 					});
	 					}else{
	 						$.messager.show({
								title : '提示',
								msg : res.msg
							});
							sysAdmin_pwd_cleanFun();
	 					}
					} else {
						$.messager.alert('提示','请输入正确的原始密码');
					}  
				}
			});
		}
	}
	
	function check(){
		var re=/^((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})$/;
		if($.trim($('#password').val()) == ""){
             $.messager.alert("提示","请输入原始密码");
             return false;
        }else if($.trim($('#newPassword').val()) == "" || $.trim($('#RepeatNewPassword').val()) == ""){
             $.messager.alert("提示","请输入新密码");
             return false;
        }else if($('#password').val() == $('#newPassword').val()){
        	 $.messager.alert("提示","新密码不可和原始密码相同");
        	 return false;
        }else if($('#newPassword').val() != $('#RepeatNewPassword').val()){
        	 $.messager.alert("提示","输入的密码新密码不一致");
        	 return false;
        }else if(!re.test($('#newPassword').val())){
			 $.messager.alert("提示","新密码长度应为8到20位，必须包含数字、大写字母、小写字母、特殊字符@#$%");
			 return false;
        }
        return true;
    }
	
</script>

<div data-options="region:'north',border:false" style="height:150px; overflow: hidden; margin-top:80px;">
	<form id="sysAdmin_pwd_searchForm" style="padding-left:10%" onsubmit="check()">
		<table class="table">
			<tr>
				<th>原始密码</th>
				<td colspan="2"><input type="text" name="password" id="password" style="width: 316px;" /></td>
			</tr>
			<tr>
				<th>新密码</th>
				<td><input type="password" name="newPassword" id="newPassword" style="width: 316px;" /></td>
				<td><span style="color: red;">新密码长度应为8到20位，必须包含数字、大写字母、小写字母、特殊字符@#$% 例：Hrt@20140529</span></td>
			</tr>
			<tr>
				<th>重复新密码</th>
				<td colspan="2"><input type="password" name="RepeatNewPassword" id="RepeatNewPassword" style="width: 316px;" /></td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: center;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="sysAdmin_pwd_updateFun();">修改</a>
					&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="sysAdmin_pwd_cleanFun();">清空</a>	
				</td>
			</tr>
		</table>
	</form>
</div>  
