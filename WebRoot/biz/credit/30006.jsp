<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		var PROTOCOLNO =$("#PROTOCOLNO30006").val();
		$('#sysAdmin_30006_datagrid').datagrid({
			url :'${ctx}/sysAdmin/loanRepay_queryDeAmtHist.action?protocolNo='+PROTOCOLNO,
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'APTID',
			sortOrder: 'desc',
			idField : 'APTID',
			columns : [[{
				field : 'CERINFOID',
				title : '协议编号',
				width : 130
				//hidden : true ,
				//checkbox:false
			},{
				title : '扣款类型',
				field : 'DEAMTTYPE',
				width : 100,
				formatter:function(value,row,index){
					//  0本金 1利息
					if(value=='0'){
						return "本金";
					}else if(value=='1'){
						return "利息";
					}
				}
			},{
				title : '扣款来源',
				field : 'SOURCE',
				width : 100,
				formatter:function(value,row,index){
					//  SOURCE;//扣款来源 0现金 1分润
					if(value=='0'){
						return "现金";
					}else if(value=='1'){
						return "分润";
					}
				}
			},{
				title : '扣款金额',
				field : 'DEAMT',
				width : 100
			},{
				title :'扣款日',
				field :'DEAMTDATE',
				width : 100
			},{
				title :'备注',
				field :'REMARKS',
				width : 120
			}]]
		});
	});

</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:20px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_30006_searchForm" style="padding-left:5%">
			<input type="hidden" id="PROTOCOLNO30006" name="PROTOCOLNO30006" value="<%=java.net.URLDecoder.decode(request.getParameter("PROTOCOLNO30006"),"UTF-8")%>">
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_30006_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


