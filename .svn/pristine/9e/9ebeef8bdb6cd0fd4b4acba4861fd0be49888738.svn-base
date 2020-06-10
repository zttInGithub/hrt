<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	<%
		String mid=new String(request.getParameter("mid").getBytes("iso-8859-1"),"UTF-8");
	 %>
	//初始化datagrid
	$(function() {
		//var mid = <%=request.getParameter("mid")%>;
		var mid=$("#mid").val();
		$('#sysAdmin_10487_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantterminalinfo_listMerchantTerminalInfoBmid.action?mid='+mid,
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,	
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmtid',
			sortOrder: 'desc',
			idField : 'bmtid',
			columns : [[{
				title : '唯一主键',
				field : 'bmtid',
				width : 100,
				hidden : true
			},{
				title :'终端',
				field :'tid',
				width : 100
			},{
				title :'入库SN',
				field :'sn',
				width : 100
			},{
				title :'TUSN',
				field :'merSn',
				width : 100
			},{
				title :'机型',
				field :'bmaidName',
				width : 50
			},{
				title :'装机地址',
				field :'installedAddress',
				width : 100
			},{
				title :'装机名称',
				field :'installedName',
				width : 70
			},{
				title :'联系人',
				field :'contactPerson',
				width : 60
			},{
				title :'联系人电话',
				field :'contactPhone',
				width : 80,
                formatter : function(value,row,index) {
                    if(value!=""&&value!=null && value.length==11){
                        return value.substring(0,3)+'****'+value.substring(value.length-4,value.length);
                    }
                }
			},{
				title :'报单日期',
				field :'maintainDateStr',
				width : 80
			},{
				title :'批单日期',
				field :'approveDateStr',
				width : 80
			},{
				title :'装机状态',
				field :'status',
				width : 70,
				 formatter:function(value,row,index){
					if(value=='1'){
						return '正常添加';
					}else if(value=='2'){
						return '增机';
					}else if(value=='3'){
						return '取消装机';
					}else if(value=='4'){
						return '撤机';
					}
				} 
			},{
				title :'装机日期',
				field :'approveDateStr',
				width : 80
			},{
				title :'押金金额',
				field :'depositAmt',
				width : 70
			},{
				title :'是否收取',
				field :'depositFlag',
				width : 70,
				formatter:function(value,row,index){
					if(value=='1'){
						return '未收取';
					}else if(value=='2'){
						return '已收取';
					}else{
						return '无押金';
					}
				} 
			},{
				title :'操作',
				field :'operation',
				width : 50,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_merchantList7_editFun('+index+')"/>'; 
				}
			}]]
		});
	});
	
	//修改
	function sysAdmin_merchantList7_editFun(index){
		$('<div id="sysAdmin_merchantterminalinfo7__editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改装机信息</span>', 
			width: 920,
		    height:300, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10488.jsp',
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10487_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merchantterminalinfo8_editForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantterminalinfo7__editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10487_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10487_searchFun() {
		$('#sysAdmin_10487_datagrid').datagrid('load', serializeObject($('#sysAdmin_10487_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10487_cleanFun() {
		$('#sysAdmin_10487_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10487_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>终端编号</th>
					<td><input name="tid" style="width: 156px;" /></td>
					
					<td style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10487_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10487_cleanFun();">清空</a>	
						<input type="hidden" id="mid" value="<%=mid %>">
					</td>
				</tr> 
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10487_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


