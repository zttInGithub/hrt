<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--创建退货--%>
<script type="text/javascript">	
</script>
<form id="sysAdmin_10500_addForm" style="padding-left:20px;" method="post">
	<input type="hidden" name="poid" id="poid"/>
	<input type="hidden" name="pdid" id="pdid"/>
	<input type="hidden" name="detailOrderID" id="detailOrderID"/>
	<input type="hidden" name="brandName" id="brandName"/>
	<input type="hidden" name="machineModel" id="machineModel"/>
	<input type="hidden" name="snType" id="snType"/>
	<input type="hidden" name="rate" id="rate"/>
	<input type="hidden" name="scanRate" id="scanRate"/>
	<input type="hidden" name="scanRateUp" id="scanRateUp"/>
	<input type="hidden" name="huaBeiRate" id="huaBeiRate"/>
	<input type="hidden" name="secondRate" id="secondRate"/>
	<input type="hidden" name="rebateType" id="rebateType"/>
	<input type="hidden" name="machinePrice" id="machinePrice"/>
		<table class="table1">
			<tr>
				<td>退货数量：</td>
				<td><input type="text" name="returnNum" id="returnNum" style="width: 150px;" class="easyui-numberbox" data-options="validType:'spaceValidator'" maxlength="20"></td>
			</tr>
		</table>			
</form>