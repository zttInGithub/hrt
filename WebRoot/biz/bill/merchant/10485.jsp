<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantList2_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantInfoY.action',
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
			},{
			    title :'受理状态',
				field :'approveStatusName',
				width : 100
			}
			,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看明细" style="cursor:pointer;" onclick="sysAdmin_merchantList_queryFun('+index+','+row.bmid+','+row.mid+')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/frame_pencil.png" title="查看终端" style="cursor:pointer;" onclick="sysAdmin_10485_queryFun('+row.mid+')"/>';
				}
			}]]
		});
	});
	
	/*function sysAdmin_merchantterminalinfo_exportFun() {
		$('#sysAdmin_10485_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMer.action'
			    	});
	}
	
	function sysAdmin_merchantterminalinfoSelected_exportFun() {
		var checkedItems = $('#sysAdmin_merchantList2_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var ids=names.join(",");
		if(ids==null||ids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#ids").val(ids);
		$('#sysAdmin_10485_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMerSelected.action'
			    	});
	}*/
	
	//查看明细
	function sysAdmin_merchantList_queryFun(index,bmid,mid){
		$('<div id="sysAdmin_merchantList_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10484.jsp?bmid='+bmid+'&mid='+mid,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantList2_datagrid').datagrid('getRows');
				var row = rows[index];
                if(row.contactPhone!=""&&row.contactPhone!=null && row.contactPhone.length==11){
                    row.contactPhone=row.contactPhone.substring(0,3)+'****'+row.contactPhone.substring(row.contactPhone.length-4,row.contactPhone.length);
                }
		    	$('#sysAdmin_merchantList_queryForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantList_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantList2_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10485_searchFun() {
		$('#sysAdmin_merchantList2_datagrid').datagrid('load', serializeObject($('#sysAdmin_10485_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10485_cleanFun() {
		$('#sysAdmin_10485_searchForm input').val('');
	}
	
	//查看终端
	function sysAdmin_10485_queryFun(mid){
		$('<div id="sysAdmin_10485_run"/>').dialog({
			title: '查看终端',
			width: 850,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10482.jsp?mid='+mid,  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10485_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端编号</th>
					<td><input name="tid" style="width: 100px;" /></td>
				</tr> 
				<tr>
					<th>机构编号</th>
					<td><input name="unno" style="width: 216px;" /></td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入网时间</th>
					<td><input name="createDateStart" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input name="createDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="C">审批中</option>
		    				<option value="Y">审批通过</option>
		    			</select>
					</td>
				</tr>
				<tr>
					<th>开户账号</th>
					<td colspan="5"><input name="bankAccNo" style="width: 216px;" /></td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10485_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10485_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantList2_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


