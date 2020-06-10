<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script type="text/javascript">
	function Change(){
	var tes=$('#rebanck111').val();
	if(tes=='其他'){
	$('#textar').show();
	}else{
	$('#textar').hide();
	}
	}
                        
    </script>

<form id="sysAdmin_merchantjoinK_editForm" method="post">
	<table class="table">
		<tr>
		   	<th>受理描述：</th>
		   	<td>
		   		<select onchange="Change()" id="rebanck111" name="processContext" style="width:205px;">
		   		<option value="照片不合格">照片不合格</option>
		   		<option value="请提供门面照片">请提供门面照片</option>
		   		<option value="请提供税务登记证或企业信用网截图">请提供税务登记证或企业信用网截图</option>
		   		<option value="开户银行账号填写有误">开户银行账号填写有误</option>
		   		<option value="开户银行填写有误">开户银行填写有误</option>
		   		<option value="行业选择与银联费率不符">行业选择与银联费率不符</option>
		   		<option value="风控退回">风控退回</option>
		   		<option value="法人身份证不清楚">法人身份证不清楚</option>
		   		<option value="非法人身份证不清楚">非法人身份证不清楚</option>
		   		<option value="营业照片不清楚">营业照片不清楚</option>
		   		<option value="税务登记证不清楚">税务登记证不清楚</option>
		   		<option value="组织机构代码证不清楚">组织机构代码证不清楚</option>
		   		<option value="开户银行过长，开通失败">开户银行过长，开通失败</option>
		   		<option value="组织机构代码证过期">组织机构代码证过期</option>
		   		<option value="请提供店内商品照片和收银台照片">请提供店内商品照片和收银台照片</option>
		   		<option value="行业编码选择有误">行业编码选择有误</option>
		   		<option value="开户银行与开户许可证不一致">开户银行与开户许可证不一致</option>
		   		<option value="按笔签约费率不予通过">按笔签约费率不予通过</option>
		   		<option value="请提供授权书">请提供授权书</option>
		   		<option value="授权书不清楚">授权书不清楚</option>
		   		<option value="所有证件请加盖公司公章">所有证件请加盖公司公章</option>
		   		<option value="其他">其他</option>
		   		</select>
		   		<textarea  style="display:none" id="textar" rows="6" cols="38" style="resize:none;" name="processContext1"></textarea>
		   	</td>
		</tr>
	</table>
	<input type="hidden" name="bmid" />
</form>  

