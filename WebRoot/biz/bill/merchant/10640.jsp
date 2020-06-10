<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantList10640_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listAllMerchantInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'joinConfirmDate',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				field : 'bmid',
				checkbox:true
			},{
				title : '机构名称',
				field : 'unitName',
				width : 100
			},{
				title : '上级机构名称',
				field : 'parentUnitName',
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
				title :'商户审批时间',
				field :'joinConfirmDate',
				width : 100,
				sortable :true
			},{
				title :'商户入网时间',
				field :'maintainDate',
				width : 100,
				sortable :true
			},{
				title :'受理人员',
				field :'approveUidName',
				width : 100
			},{
			    title :'受理状态',
				field :'approveStatusName',
				width : 100
			},{
			    title :'是否手刷',
				field :'isM35',
				width : 100,
				formatter : function(value,row,index) {
				if (value=='1'){
				   return "手刷商户";
				}else{
				   return "传统商户";
					}  
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantList10640_queryFun('+index+','+row.bmid+',\''+row.mid+'\')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_pencil.png" title="查看终端" style="cursor:pointer;" onclick="sysAdmin_10640_queryFun(\''+row.mid+'\')"/>';
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo10640_exportFun();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected10640_exportFun();
				}
			}]
		});
	});
	
	function sysAdmin_merchantterminalinfo10640_exportFun() {
		$('#sysAdmin_merchant10640_searchForm').form('submit',{
			url:'${ctx}/sysAdmin/merchant_exportAllMer.action'
		});
	}
	
	function sysAdmin_merchantterminalinfoSelected10640_exportFun() {
		var checkedItems = $('#sysAdmin_merchantList10640_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmids_10640").val(bmids);
		$('#sysAdmin_merchant10640_searchForm').form('submit',{
			 url:'${ctx}/sysAdmin/merchant_exportMerSelected.action'
		});
	}
	
	//查看明细
	function sysAdmin_merchantList10640_queryFun(index,bmid,mid){
		$('<div id="sysAdmin_merchantList10640_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10484.jsp?bmid='+bmid+'&mid='+mid,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantList10640_datagrid').datagrid('getRows');
				var row = rows[index];
				//手机号
				if(row.contactPhone!=""&&row.contactPhone!=null && row.contactPhone.length==11){
	            	row.contactPhone=row.contactPhone.substring(0,3)+'****'+row.contactPhone.substring(row.contactPhone.length-4,row.contactPhone.length);
	            }
				//身份证号
	            if(row.legalNum != null && row.legalNum != ""){
	              	row.legalNum = row.legalNum.substring(0,4)+'****'+row.legalNum.substring(row.legalNum.length-4,row.legalNum.length);
	            }
	            //银行卡号
	            if(row.bankAccNo != null && row.bankAccNo != ""){
	              	row.bankAccNo = row.bankAccNo.substring(0,4)+'****'+row.bankAccNo.substring(row.bankAccNo.length-4,row.bankAccNo.length);
	            }
	            //入账人身份证号
	            if(row.accNum != null && row.accNum != "" && row.accNum.length == 18){
                	row.accNum = row.accNum.substring(0,4)+'****'+row.accNum.substring(row.accNum.length-4,row.accNum.length);
                }
		    	$('#sysAdmin_merchantList_queryForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantList10640_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantList10640_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_merchant_searchFun10640() {
		$('#sysAdmin_merchantList10640_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchant10640_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchant_cleanFun10640() {
		$('#sysAdmin_merchant10640_searchForm input').val('');
	}
	
	//查看终端
	function sysAdmin_10640_queryFun(mid){
		$('<div id="sysAdmin_10640_run"/>').dialog({
			title: '查看终端',
			width: 850,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10487.jsp?mid='+mid,  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:140px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_merchant10640_searchForm" style="padding-left:2%" method="post">
		<input type="hidden" id="bmids_10640" name="bmids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td style="width: 170px;"><input name="mid" style="width: 150px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端编号</th>
					<td style="width: 180px;"><input name="tid" style="width: 150px;" /></td>
					
					<th>SN号</th>
					<td ><input name="sn" style="width: 100px;" /></td>
				</tr> 
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 150px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入网时间</th>
					<td><input name="createDateStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="createDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:155px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="C">审批中</option>
		    				<option value="Y">审批通过</option>
		    			</select>
					</td>
					
					<th>受理人员</th>
					<td ><input name="approveUidName" style="width: 100px;" /></td>
				</tr>
				<tr>
					<th>入账人姓名</th>
					<td ><input name="bankAccName" style="width: 150px;" /></td>
					<th>入账人身份证号</th>
					<td ><input name="accNum" style="width: 316px;" /></td>
					<th>开户账号</th>
					<td ><input name="bankAccNo" style="width: 150px;" /></td>
					<th>开户银行</th>
					<td ><input name="bankBranch" style="width: 100px;" /></td>
				</tr>
				<tr>
					<th>法人姓名</th>
					<td ><input name="legalPerson" style="width: 150px;" /></td>
					<th>法人身份证号</th>
					<td ><input name="legalNum" style="width: 316px;" /></td>
					<th>联系人姓名</th>
					<td ><input name="contactPerson" style="width: 150px;" /></td>
					<th>联系人电话</th>
					<td ><input name="contactPhone" style="width: 100px;" /></td>
				</tr>
				<tr>
					<td colspan="7" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchant_searchFun10640();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchant_cleanFun10640();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantList10640_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


