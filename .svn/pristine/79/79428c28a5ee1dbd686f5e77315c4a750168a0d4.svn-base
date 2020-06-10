<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    //钱包开通模板下载
    function exportRebatetypeWalletUpdateXls(){
        $('#RebatetypeWalletUpdateXls').form('submit',{
            url:'${ctx}/sysAdmin/agentunit_downloadRebatetypeWalletUpdateXls.action'
        });
    }
</script>

<form id="RebatetypeWalletUpdateXls">
    <fieldset>
        <legend>模板下载</legend>
        <table class="table">
            <tr>
                <th>批量钱包变更导入模板：</th>
                <td>
                    &nbsp;&nbsp;<input type="button" value="下载" onclick="exportRebatetypeWalletUpdateXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="rebatetypeWallet_cash_switch_UpdateFrom">
	<input type="hidden" name="fileContact" id="fileContact">
	<table class="table">
		<tr>
			<td style="width: 200px;">批量变更导入:</td>
			<td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
		</tr>
	</table>
</form>