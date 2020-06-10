<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	//初始化datagrid
	$(function() {
		var bno = '<%=request.getParameter("bno")%>';
		$('#sysAdmin_10423_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantInfoBnoRname.action?bno='+bno,
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmtid',
			sortOrder: 'desc',
			idField : 'bmtid',
			columns : [[{
				title : '机构名称',
				field : 'unitName',
				width : 100
			},{
				title :'商户编号',
				field :'mid',
				width : 100,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'业务人员',
				field :'busidName',
				width : 100
			},{
				title :'维护人员',
				field :'maintainUserIdName',
				width : 100
			},{
				title :'终端数量',
				field :'tidCount',
				width : 100
			},{
				title :'商户入网时间',
				field :'joinConfirmDate',
				width : 100,
				sortable :true
			},{
				title :'受理人员',
				field :'approveUidName',
				width : 100
			}]]
		});
	});
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10423_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


