<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    //成本模板下载
    function exportCBSZHrtCostXls(){
        $('#export_hrt_unno_cost_rXls').form('submit',{
            url:'${ctx}/sysAdmin/agentunit_dowloadRGSZHrtCostXls.action'
        });
    }
</script>

<form id="export_hrt_unno_cost_rXls">
  <fieldset>
    <legend>模板下载</legend>
    <table class="table">
      <tr>
        <th>成本中心设置导入模板：</th>
        <td>
          &nbsp;&nbsp;<input type="button" value="下载" onclick="exportCBSZHrtCostXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行导入 *</font>
        </td>
      </tr>
    </table>
  </fieldset>
</form>

<form method="post" enctype="multipart/form-data" id="sysAdmin_00319h_01_upload_addForm">
  <input type="hidden" name="fileContact" id="fileContact">
  <table class="table">
    <tr>
      <td>导入人工成本文件：</td>
      <td><input type="file" name="upload" id="upload" style="width: 200px;"/></td>
    </tr>
  </table>
</form>