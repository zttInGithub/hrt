<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script type="text/javascript">
	function Change1(){
	var tes=$('#rebanck1').val();
	if(tes=='其他'){
	$('#texta1').show();
	}else{
	$('#texta1').hide();
	}
	}
                        
    </script>
<form id="sysAdmin_merchantconfirm_editForm2" method="post">
	<table class="table">
		<tr>
			<th>受理描述：</th>
			<td>
			<select onchange="Change1()" id="rebanck1" name="processContext" style="width:205px;">
		   		<option value="5台之外的增机需求发邮件申请">5台之外的增机需求发邮件申请</option>
		   		<option value="交易量较小，暂不批准">交易量较小，暂不批准</option>
		        <option value="其他">其他</option>
		   		</select>
				<textarea style="display:none" id="texta1" rows="6" cols="38" style="resize:none;" name="processContext1" maxlength="200"></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" name="bmtid" />
</form>  

