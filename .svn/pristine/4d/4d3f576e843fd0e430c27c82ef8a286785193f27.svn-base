<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		var tempname =$("#tempName").val();
		$('#sysAdmin_20271_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryprofittemplateAll.action?tempName='+encodeURIComponent(encodeURIComponent(tempname)),
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
				title : '唯一主键',
				field : 'APTID',
				width : 100,
				hidden : true
			},{
				title :'机构号',
				field :'UNNO',
				width : 100,
				hidden : true
			},{
				title :'模版名称',
				field :'TEMPNAME',
				width : 100
			},{
				title :'商户类型',
				field :'SETTMETHOD',
				width : 100,
				formatter : function(value,row,index) {
					if((value=="100000"||value=="200000")&&row.MERCHANTTYPE==2){
					  return "秒到0.72";
					}else if((value=="100000"||value=="200000")&&row.MERCHANTTYPE==3){
					  return "秒到非0.72";
					}else{
					  return "理财";
					}  
				}
			},{
				title :'借记卡手续费',
				field :'STARTAMOUNT',
				width : 100
			},{
				title :'借记卡封顶值',
				field :'ENDAMOUNT',
				width : 100
			},{
				title :'借记卡费率',
				field :'RULETHRESHOLD',
				width : 100
			},{
				title :'贷记卡费率',
				field :'CREDITBANKRATE',
				width : 100
			},{
				title :'T0提现费率',
				field :'CASHRATE',
				width : 100
			},{
				title :'转账费',
				field :'CASHAMT',
				width : 100
			},{
				title :'扫码费率',
				field :'SCANRATE',
				width : 100
			},{
				title :'利润百分比',
				field :'PROFITPERCENT',
				width : 100
			},{
				title :'操作类型',
				field :'MATAINTYPE',
				width : 100,
				formatter:function(value,row,index){
					if(value=='A'){
						return '添加';
					}else if(value=='M'){
						return '修改';
					}else if(value=='D'){
						return '删除';
					}
				}
			}]]
		});
	});

</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:20px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20271_searchForm" style="padding-left:5%">
			<input type="hidden" id="tempName" name="tempName" value=" <%=java.net.URLDecoder.decode(request.getParameter("tempname"),"UTF-8")%>">
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20271_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


