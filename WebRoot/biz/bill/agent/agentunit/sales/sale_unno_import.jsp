<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<script type="text/javascript">
    function dowloadTempXlsFun() {
        $('#dowloadTempXlsFrom').form('submit', {
            url: '${ctx}/sysAdmin/agentunit_dowloadSaleUnnoTempXls.action'
        });
    }
</script>

<form id="dowloadTempXlsFrom">
  <fieldset>
    <legend>模板下载</legend>
    <table class="table">
      <tr>
        <th style="text-align: center">模板下载：</th>
        <td>
          &nbsp;&nbsp;<input type="button" value="下载" onclick="dowloadTempXlsFun()"/>
        </td>
      </tr>
    </table>
  </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="sysAdmin_sale_unno_import_upload">
  <input type="hidden" name="fileContact" id="fileContact">
  <fieldset>
    <legend>模板导入</legend>
    <table class="table">
      <tr>
        <th style="text-align: center">批量导入:</th>
        <td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
      </tr>
    </table>
  </fieldset>
</form>