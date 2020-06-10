<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    //返现钱包税点批量变更模板下载
    function exportCashbackTaxPointUpdateXls(){
        $('#CashbackTaxPointUpdateXls').form('submit',{
            url:'${ctx}/sysAdmin/cashbackTemplate_downloadCashbackTaxPointUpdateXls.action'
        });
    }
</script>

<form id="CashbackTaxPointUpdateXls">
    <fieldset>
        <legend>模板下载</legend>
        <table class="table">
            <tr>
                <th>批量创建与变更模板：</th>
                <td>
                    &nbsp;&nbsp;<input type="button" value="下载" onclick="exportCashbackTaxPointUpdateXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="cashbackTaxPoint_datagrid_UpdateFrom">
	<input type="hidden" name="fileContact" id="fileContact">
	<table class="table">
		<tr>
			<td style="width: 200px;">批量变更导入:</td>
			<td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
		</tr>
	</table>
</form>