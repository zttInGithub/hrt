<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
    //成本模板下载
    function exportBatchRebateRateXls(){
        $('#cbmo_BatchRebateRateXls').form('submit',{
            url:'${ctx}/sysAdmin/agentunit_dowloadBatchRebateRateXls.action'
        });
    }
</script>

<form id="cbmo_BatchRebateRateXls">
    <fieldset>
        <legend>模板下载</legend>
        <table class="table">
            <tr>
                <th>批量新增成本导入模板：</th>
                <td>
                    &nbsp;&nbsp;<input type="button" value="下载" onclick="exportBatchRebateRateXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="sysAdmin_00323_1_upload">
	<input type="hidden" name="fileContact" id="fileContact">
	<table class="table">
<%--		<tr>
			<td style="width: 200px;">模板</td>
			<td><a style="color: green" href="${ctx}/sysAdmin/agentunit_dowloadTmp.action" target="_blank">模板下载</a></td>
		</tr>--%>
		<tr>
			<td style="width: 200px;">批量新增导入:</td>
			<td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
		</tr>
	</table>
</form>