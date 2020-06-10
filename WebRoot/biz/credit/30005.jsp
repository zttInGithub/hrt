<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		var PROTOCOLNO =$("#PROTOCOLNO30005").val();
		$('#sysAdmin_30005_datagrid').datagrid({
			url :'${ctx}/sysAdmin/loanRepay_queryRepayInfo.action?protocolNo='+PROTOCOLNO,
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
				field : 'PROTOCOLNO',
				title : '协议编号',
				width : 130
				//hidden : true ,
				//checkbox:false
			},{
				title : '还款单据号',
				field : 'POSINVOICENO',
				width : 100
			},{
				title : '还款日期',
				field : 'REPAYTIME',
				width : 85
			},{
				title :'还款金额',
				field :'REPAYAMT',
				width : 100
			},{
				title :'开户银行账号',
				field :'PAYNO',
				width : 120
			},{
				title :'申请人',
				field :'PROPOSER',
				width : 80
			},{
				title :'申请人邮箱',
				field :'PROPEMAIL',
				width : 100
			},{
				title :'申请人电话',
				field :'PROPHONE',
				width : 100
			},{
				title :'审批标志',
				field :'APPROVEFLAG',
				width : 100,
				formatter:function(value,row,index){
					// 0:待审批1：已审批2：已拒绝
					if(value=='0'){
						return "待审批";
					}else if(value=='1'){
						return "已审批";
					}else if(value=='2'){
						return "已拒绝";
					}
				}
			},{
				title :'审批时间',
				field :'APPROVEDTIME',
				width : 85
			},{
				title :'拒绝原因',
				field :'REFUSALREASON',
				width : 115
			},{
				title :'备注',
				field :'REMARKS',
				width : 115
			}]]
		});
	});

</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:20px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_30005_searchForm" style="padding-left:5%">
			<input type="hidden" id="PROTOCOLNO30005" name="PROTOCOLNO30005" value="<%=java.net.URLDecoder.decode(request.getParameter("PROTOCOLNO30005"),"UTF-8")%>">
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_30005_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


