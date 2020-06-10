<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    var type = <%=request.getParameter("type")%>;
    $(function () {
        if (type == 1) {
            $('#titleName').html('sn修改导入')
            $('#change_term_import_temp').html('sn修改导入模板')
        } else if (type == 2) {
            $('#titleName').html('商户费率修改导入')
            $('#change_term_import_temp').html('商户费率修改导入模板')
        } else if (type == 3) {
            $('#titleName').html('数据变更单导入')
            $('#change_term_import_temp').html('数据变更单导入模板')
        }
    });

    function dowloadDataChangeTempXlsFun() {
        if (type == 1) {
            $('#change_term_tempFileFrom').form('submit', {
                url: '${ctx}/sysAdmin/agentunit_dowloadSnModifyTempXls.action'
            });
        } else if (type == 2) {
            $('#change_term_tempFileFrom').form('submit', {
                url: '${ctx}/sysAdmin/agentunit_dowloadMerRateModifyTempXls.action'
            });
        } else if (type == 3) {
        }
    }
</script>

<form id="change_term_tempFileFrom">
  <fieldset>
    <legend>模板下载</legend>
    <table class="table">
      <tr>
        <th style="text-align: center" id="change_term_import_temp">导入模板：</th>
        <td>
          &nbsp;&nbsp;<input type="button" value="下载" onclick="dowloadDataChangeTempXlsFun()"/>
        </td>
      </tr>
    </table>
  </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="sysAdmin_change_term_import_upload">
  <input type="hidden" name="fileContact" id="fileContact">
  <fieldset>
    <legend>模板导入</legend>
    <table class="table">
      <tr>
        <th style="text-align: center" id="titleName">批量导入:</th>
        <td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
      </tr>
      <tr>
        <td colspan="2"><font color="red">使用介绍:1.下载模板、2.模板信息填写、3.导入、4.操作按钮上传变更文件、5.审批文件</font></td>
      </tr>
    </table>
    <input type="hidden" name="FID" style="width: 200px;"/>
  </fieldset>
</form>