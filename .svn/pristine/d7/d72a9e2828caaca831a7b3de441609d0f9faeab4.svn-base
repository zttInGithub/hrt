<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--发布公告--%>
<script type="text/javascript">
	$(function() {
		$('#sysAdmin_noticeNew_datagrid').datagrid({
			url :'${ctx}/sysAdmin/notice_listNoticeNew.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'msgSendTime',
			sortOrder: 'desc',
			idField : 'noticeID',
			columns : [[{
				title : '消息编号',
				field : 'noticeID',
				width : 100,
				hidden : true
			},{
				title :'发布日期',
				field :'msgSendTime',
				width : 100
			},{
				title :'消息标题',
				field :'msgTopic',
				width : 100,
				formatter: function(value,row,index){
					return "<a onclick='sysAdmin_notice_dialogFun("+index+","+row.noticeID+")'>"+value+"</a>";
				}
			},{
				title :'接收机构',
				field :'msgReceUnitName',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_notice_dialogFun('+index+','+row.noticeID+')"/>&nbsp;&nbsp';
				}
			}]]		
		});
	});
	
	//查看公告
	function sysAdmin_notice_dialogFun(index,noticeID){

		$('<div id="sysAdmin_notice_descWindow"/>').dialog({
			title: '<span style="color:#157FCC;">查看公告</span>',
			width: 700,   
		    height: 600,
		    closed: false,
		    href: '${ctx}/frame/sysadmin/notice/00114.jsp?noticeID='+noticeID,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_noticeNew_datagrid').datagrid('getRows');
				var row = rows[index];
				row.noticeID = stringToList(row.noticeID);

				if(row.status == 0){
					$.ajax({
						url: "${ctx}/sysAdmin/notice_editStatus.action",
						type:'post',
						data: {"noticeID":row.noticeID},
						dataType: 'json'
				    });
			    }
				
		    	$('#sysAdmin_00114_showDiv').form('load', row);
			},
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
</script>

<table id="sysAdmin_noticeNew_datagrid" style="overflow: hidden;"></table>

