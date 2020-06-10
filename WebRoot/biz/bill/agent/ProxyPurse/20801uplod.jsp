
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	function dowloadUnno_AdjtxnXlsFun() {
		$('#dowloadUnnoAdjtxnTempXls').form('submit', {
			url: '${ctx}/sysAdmin/ProxyPurse_dowloadUnnoAdjtxnXls.action'
		});
	}
</script>

<form id="dowloadUnnoAdjtxnTempXls">
	<fieldset>
		<legend>模板下载</legend>
		<table class="table">
			<tr>
				<th style="text-align: center">模板下载：</th>
				<td>
					&nbsp;&nbsp;<input type="button" value="下载" onclick="dowloadUnno_AdjtxnXlsFun()"/>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
   
 <form method="post" enctype="multipart/form-data" id="importUnnoAdjtxn_uploadForm" onsubmit="subbutton.disabled=1">
	<input type="hidden" name="fileUnnoAdj" id="fileUnnoAdj">
	<table style="padding-top:10px;padding-left:50px">
		<tr>
			<th>文件：</th>
            <td><input type="file" name="upload" id="uploadUnnoAdj"></input></td>
		</tr>
	</table>
</form>
