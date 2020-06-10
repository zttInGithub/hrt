<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    function exportdowloadMerchantMicroToUnbid(){
        $('#cbmo_dowloadMerchantMicroToUnbid').form('submit',{
            url:'${ctx}/sysAdmin/merchant_dowloadMerchantMicroToUnbid.action'
        });
    }
</script>

<form id="cbmo_dowloadMerchantMicroToUnbid">
    <fieldset>
        <legend>模板下载</legend>
        <table class="table">
            <tr>
                <th>批量解绑导入模板：</th>
                <td>
                    &nbsp;&nbsp;<input type="button" value="下载" onclick="exportdowloadMerchantMicroToUnbid()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="sysAdmin_10489_1_upload">
	<input type="hidden" name="fileContact" id="fileContact">
	<table class="table">
		<tr>
			<td style="width: 200px;">批量解绑导入:</td>
			<td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
		</tr>
	</table>
</form>