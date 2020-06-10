<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$('#producttype_10975').combogrid({
			url : '${ctx}/sysAdmin/producttypeInRebatetypeAction_queryProducttype.action',
			idField:'PRODUCTTYPE',
			textField:'PRODUCTTYPE',
			mode:'remote',
			fitColumns:true,  
			columns:[[ 
				{field:'PRODUCTTYPE',title:'产品类型',width:250}
			]] 
		});
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
		<form id="sysAdmin_editProduct_addForm" style="padding-left:2%;margin-top: 5%" method="post">
			<table class="tableForm" >
				<tr align="center">
					<th style="width:100px;">产品类型：</th>
   					<td style="width:270px;">
	   					<select id="producttype_10975" name="producttype" class="easyui-combogrid" data-options="required:true,editable:false" style="width:250px;"></select><font color="red">&nbsp;*</font>
   					</td>
				</tr>
				<tr align="center">
					<th style="width:100px;">活动类型：</th>
   					<td style="width:270px;">
	   					<input id="rebatetype" name="rebatetype" data-options="required:true,editable:false" style="width:250px;"></input><font color="red">&nbsp;*</font>
   					</td>
				</tr>
			</table>
		</form>
</div>


