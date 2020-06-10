<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
  var ok=<%=request.getParameter("ok")%>+"";
  $(function () {
      if(ok=="1"){
          $('#tableRemark').remove();
          $('#titleName').html('   请确认审核通过？');
      }else if(ok=="-1"){
          $('#remarkTitle').html('退回原因');
      }
  })
</script>
<form id="change_term_merRemarks_form" method="post" style="padding-top:10px;">
		<table class="table" id="tableRemark">
			<tr>
	    		<th id="remarkTitle">审核通过/退回原因：</th>
	    		<td id="remarkInfo"><input type="text" name="remarks" style="width:200px;" class="easyui-validatebox" data-options="required:true" maxlength="100"/></td>
	    	</tr>
		</table>
  <p id="titleName"></p>
	<input type="hidden" name="FID">
	<input type="hidden" name="cycle" id="cycle">
</form>

