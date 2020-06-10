<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String  tempname=request.getParameter("tempname"); 
	Integer  aptId=Integer.parseInt(request.getParameter("aptId"));
 %>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules,{
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
	            return /^([0-3]\.[0-9]{1,4}|[1-3])$/i.test(value);
	        },
	        message : '费率格式不正确！'
		}
	});

</script>
	<div data-options="region:'north',border:false"
		style="height: 500px; padding-top: 2px;">
    <div id="20512_tabs" class="easyui-tabs" data-options="fit:true,border:false">
      <div id="20512_tab_1" title="本月生效">
		<form id="Profitmicro_20512" style="padding-left: 1%; padding-top: 1%"
			method="post" enctype="multipart/form-data">
			<fieldset>
				<table class="table">
					<tr>
						<th>模版名称：</th>
						<td><input type="text" name="tempName" id="tempName"
							style="width: 100px;" class="easyui-validatebox"
							data-options="required:true,validType:'spaceValidator'" disabled="disabled"/><font color="red">&nbsp;*</font></td>
						<th>代还费率：</th>
						<td><input type="text" name="subRate" id="subRate"
							style="width: 100px;" class="easyui-validatebox"
							data-options="required:true,validType:'rateValidator'"
							maxlength="18" readonly="readonly" />%<font color="red">&nbsp;*</font></td>
					</tr>
				</table>
			</fieldset>
			<input name="aptId" id="aptId" type="hidden">
		</form>
      </div>
      <div id="20512_tab_2" title="下月生效">
        <form id="Profitmicro_20512_Next" style="padding-left: 1%; padding-top: 1%"
              method="post" enctype="multipart/form-data">
          <fieldset>
            <table class="table">
              <tr>
                <th>模版名称：</th>
                <td><input type="text" name="tempName" id="tempNameNext"
                           style="width: 100px;" class="easyui-validatebox"
                           data-options="required:true,validType:'spaceValidator'" disabled="disabled"/><font color="red">&nbsp;*</font></td>
                <th>代还费率：</th>
                <td><input type="text" name="subRate" id="subRateNext"
                           style="width: 100px;" class="easyui-validatebox"
                           data-options="required:true,validType:'rateValidator'"
                           maxlength="18" />%<font color="red">&nbsp;*</font></td>
              </tr>
            </table>
          </fieldset>
          <input name="aptId" id="aptIdNext" type="hidden">
        </form>
      </div>
    </div>
	</div>
<script type="text/javascript">
	$(function () {
		$.ajax({
			url:"${ctx}/sysAdmin/CheckUnitProfitMicro_queryNextMonthTemplate.action",
			type:'post',
			data:{"aptid":<%=aptId%>,"txnType":2},
			dataType:'json',
			success:function(data) {
				if (data) {
					// console.log(data);
					$('#tempNameNext').val(data.TEMPNAME);
					$('#subRateNext').val(parseFloat(data.SUBRATE*100).toFixed(4));
					$('#aptIdNext').val(data.APTID);
				}
			}
		});
		$.ajax({
			url:"${ctx}/sysAdmin/CheckUnitProfitMicro_queryNextMonthTemplate.action",
			type:'post',
			data:{"aptid":<%=aptId%>,"txnType":1},
			dataType:'json',
			success:function(data) {
				if (data) {
					// console.log(data);
					$('#tempName').val(data.TEMPNAME);
					$('#subRate').val(parseFloat(data.SUBRATE*100).toFixed(4));
					$('#aptId').val(data.APTID);
				}
			}
		});
	});
</script>