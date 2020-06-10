<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    function dowloadTermSimMatchExportFun() {
        $('#term_sim_manage_match_export').form('submit', {
            url: '${ctx}/sysAdmin/billTerminalSim_dowloadTermSimMatchXls.action'
        });
    }
</script>

<form id="term_sim_manage_match_export">
  <fieldset>
    <legend>模板下载</legend>
    <table class="table">
      <tr>
        <th style="text-align: center" id="change_term_import_temp">匹配导入/导出模板：</th>
        <td>
          &nbsp;&nbsp;<input type="button" value="下载" onclick="dowloadTermSimMatchExportFun()"/>
        </td>
      </tr>
    </table>
  </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="term_sim_manage_match_export_upload">
  <input type="hidden" name="fileContact" id="fileContact">
  <fieldset>
    <legend>模板导入</legend>
    <table class="table">
      <tr>
        <th style="text-align: center" id="titleName">匹配导入/导出模板导入:</th>
        <td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
      </tr>
      <tr>
        <td colspan="2"><font color="red">使用介绍:1.下载模板、2.模板信息填写、3.导入、4.操作按钮上传文件</font></td>
      </tr>
    </table>
  </fieldset>
</form>