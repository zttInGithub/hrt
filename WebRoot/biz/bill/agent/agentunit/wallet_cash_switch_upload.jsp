<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    function dowloadWalletCashSwitchXls(){
        $('#dowloadWalletCashSwitchFrom').form('submit',{
            url:'${ctx}/sysAdmin/checkWalletCashSwitch_dowloadWalletCashSwitchXls.action'
        });
    }
</script>

<form id="dowloadWalletCashSwitchFrom">
    <fieldset>
        <legend>模板下载</legend>
        <table class="table">
            <tr>
                <th>批量开通模板：</th>
                <td>
                    &nbsp;&nbsp;<input type="button" value="下载" onclick="dowloadWalletCashSwitchXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="wallet_cash_switch_uploadFrom">
	<input type="hidden" name="fileContact" id="fileContact">
  <fieldset>
    <legend>模板导入</legend>
    <table class="table">
      <tr>
        <th>批量开通模板导入:</th>
        <td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
      </tr>
    </table>
  </fieldset>
</form>