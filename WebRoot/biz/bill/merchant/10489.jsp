<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantMicroList_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantMicroToUnbid.action',
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
				field : 'unno',
				width : 100
			},{
				title :'商户编号',
				field :'mid',
				width : 100,
				sortable :true
			},{
				title :'SN号',
				field :'sn',
				width : 100,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'商户入网时间',
				field :'joinConfirmDate',
				width : 100,
				sortable :true
			},{
			    title :'受理状态',
				field :'approveStatus',
				width : 100,
				formatter:function(value,row,index){
					if(value=="K"){ 
						return '退回'; 
					}else if(value=="Z"){
						return '待审核';
					}else if(value=='W'){
						return '待审批'; 
					}else if(value=='Y'){
						return '审核通过'; 
					}else if(value=='C'){
						return '审批中';
					}
				} 
			},{
				title :'操作',
				field :'OPERATION',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/query_search.png" title="查看终端" style="cursor:pointer;" onclick="sysAdmin_10480_queryFun(\''+row.mid+'\')"/>&nbsp;&nbsp'+
					'<img src="${ctx}/images/close.png" title="解绑终端" style="cursor:pointer;" onclick="sysAdmin_merchant_unbindSn('+index+')"/>';
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'资料导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfo_exportFun();
				}
			},{
				id:'btn_runSelected',
				text:'勾选导出',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected_exportFun();
				}
			},{
				id:'btn_add',
				text:'批量解绑导入',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_merchantterminalinfoSelected_import();
				}
			}]
		});
	});

	function sysAdmin_merchantterminalinfoSelected_import(){
		$('<div id="sysAdmin_10489_1_uploadDialog"/>').dialog({
			title: '<span style="color:#157FCC;">批量解绑导入</span>',
			width: 650,
			height: 240,
			closed: false,
			href: '${ctx}/biz/bill/merchant/10489_1.jsp',
			modal: true,
			buttons:[{
				text:'导入',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10489_1_upload').form('submit', {
						url:'${ctx}/sysAdmin/merchant_importMicroToUnbid.action',
						onSubmit: function(){
							var contact = document.getElementById('upload').value;
							if(contact == "" || contact == null){
								$.messager.show({
									title:'提示',
									msg:'请选择要上传的文件',
									timeout:5000,
									showType:'slide'
								});
								return false;
							}
							if(contact != "" && contact != null){
								var l = contact.split(".");
								if(l[1] != "xls"){
									$.messager.show({
										title:'提示',
										msg:'请选择后缀名为.xls文件',
										timeout:5000,
										showType:'slide'
									});
									return false;
								}
								//如果格式正确，处理
								if(l[1] == "xls"){
									document.getElementById("fileContact").value = contact.replace(/.{0,}\\/, "");	//获取jsp页面hidden的值
									return true;
								}
							}
						},
						//成功返回数据
						success:function(data){
							console.log($.parseJSON(data))
							var resault = $.parseJSON(data);
							if(resault.sessionExpire){
								window.location.href = getProjectLocation();
							}else{
								if(resault.success){
									$('#sysAdmin_10489_1_uploadDialog').dialog('destroy');
									$('#sysAdmin_merchantMicroList_datagrid').datagrid('load');
									$.messager.show({
										title:'提示',
										msg  :resault.msg
									});
								}else{
									$('#sysAdmin_10489_1_uploadDialog').dialog('destroy');
									$.messager.show({
										title:'提示',
										msg  :resault.msg
									});
								}
							}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10489_1_uploadDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_merchantterminalinfo_exportFun() {
		$('#sysAdmin_merchantUnbid_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMer.action'
			    	});
	}
	
	function sysAdmin_merchantterminalinfoSelected_exportFun() {
		var checkedItems = $('#sysAdmin_merchantMicroList_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmids_10489").val(bmids);
		$('#sysAdmin_merchantUnbid_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMerSelected.action'
			    	});
	}
	

	
	//表单查询
	function sysAdmin_merchantUnbid_searchFun80() {
		$('#sysAdmin_merchantMicroList_datagrid').datagrid('load', serializeObject($('#sysAdmin_merchantUnbid_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantUnbid_cleanFun80() {
		$('#sysAdmin_merchantUnbid_searchForm input').val('');
	}
	
	//查看终端
	function sysAdmin_10480_queryFun(mid){
		$('<div id="sysAdmin_10489_run"/>').dialog({
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
	
	
	//解绑终端
	function sysAdmin_merchant_unbindSn(index){
		var rows = $('#sysAdmin_merchantMicroList_datagrid').datagrid('getRows');
		var row = rows[index];
		var sn = row.sn;
		var mid = row.mid;
		$.messager.confirm('确认','您确认要解绑终端吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/merchant_unbindSN.action",
					type:'post',
					data:{"Sn":sn,"mid":mid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_merchantMicroList_datagrid').datagrid('unselectAll');
							$('#sysAdmin_merchantMicroList_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_menu_treegrid').treegrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '解绑终端出错！');
						$('#sysAdmin_merchantMicroList_datagrid').treegrid('unselectAll');
					}
				});
			}
		});
	}
	


</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:120px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merchantUnbid_searchForm" style="padding-left:10%">
		<input type="hidden" id="bmids_10489" name="bmids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
				    
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称</th>
					<td><input name="rname" style="width: 316px;" /></td>
				</tr> 
				<tr>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SN号</th>
					<td><input name="sn" style="width: 100px;" /></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;受理状态</th>
					<td>
						<select name="approveStatus" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width:105px;">
		    				<option value="" selected="selected">查询所有</option>
		    				<option value="C">审批中</option>
		    				<option value="Y">审批通过</option>
		    				<option value="K">退回</option>
		    			</select>
					</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantUnbid_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantUnbid_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantMicroList_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


