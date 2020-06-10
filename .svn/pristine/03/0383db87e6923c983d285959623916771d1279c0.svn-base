<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--下载专区--%>
<script type="text/javascript">
	$(function() {
		$('#sysAdmin_download_datagrid').datagrid({
			url :'${ctx}/sysAdmin/file_listFiles.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'fileID',
			sortOrder: 'desc',
			idField : 'fileID',
			columns : [[{
				title : 'ID',
				field : 'fileID',
				width : 100,
				hidden : true
			},{
				title : '上传时间',
				field : 'createDate',
				width : 100
			},{
				title : '文件名称',
				field : 'fileName',
				width : 100
			},{
				title : '文件描述',
				field : 'fileDesc',
				width : 100
			},{
				title : '上传人',
				field : 'createUser',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<a style="color: #4169E1;text-decoration: underline;cursor:pointer;" href="${ctx}/sysAdmin/file_downloadFile.action?fileID='+row.fileID+'">下载</a>';
				}
			}]]
		});
	});
</script>

<table id="sysAdmin_download_datagrid" style="overflow: hidden;"></table>
