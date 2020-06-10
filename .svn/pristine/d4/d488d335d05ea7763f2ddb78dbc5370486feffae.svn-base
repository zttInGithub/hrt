<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    //钱包开通模板下载
    function exportBatchRebateRateXls(){
        $('#cbmo_BatchRebateRateXls').form('submit',{
            url:'${ctx}/sysAdmin/checkWalletCashSwitch_downloadBatchCashSwitchXls.action'
        });
    }
</script>

<form id="cbmo_BatchRebateRateXls">
    <fieldset>
        <legend>模板下载</legend>
        <table class="table">
            <tr>
                <th>批量钱包开通导入模板：</th>
                <td>
                    &nbsp;&nbsp;<input type="button" value="下载" onclick="exportBatchRebateRateXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="wallet_cash_switch_js_batchFrom">
	<input type="hidden" name="fileContact" id="fileContact">
	<table class="table">
<%--		<tr>
			<td style="width: 200px;">模板</td>
			<td><a style="color: green" href="${ctx}/sysAdmin/agentunit_dowloadTmp.action" target="_blank">模板下载</a></td>
		</tr>--%>
		<tr>
			<td style="width: 200px;">批量开通导入:</td>
			<td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
		</tr>
	</table>
</form>