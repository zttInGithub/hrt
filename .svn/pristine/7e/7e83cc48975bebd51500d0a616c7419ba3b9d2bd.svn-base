<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10646_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantAuthenticity_listTxnAuthenticity.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'bmatid',
			sortOrder: 'desc',
			idField : 'bmatid',
			columns : [[{
				field : 'bmatid',
				checkbox:true
			},{
				title : 'APP用户名',
				field : 'username',
				width : 90
			},{
				title : '商户编号',
				field : 'mid',
				width : 100
			},{
				title :'身份证号',
				field :'legalNum',
				width : 120,
				sortable :true
			},{
				title :'持卡人名称',
				field :'bankAccName',
				width : 70,
				sortable :true
			},{
				title :'卡号',
				field :'bankAccNo',
				width : 130
			},{
				title :'认证状态',
				field :'status',
				width : 50,
				formatter : function(value,row,index) {
						if (value=='00'){
						   return "已认证";
						}else if(value=='01'){
							return "认证中";
						}else if(value==''||value==null){
							   return "未认证";
						}
					}
			},{
				title :'认证返回信息',
				field :'respinfo',
				width : 190
				//sortable :true
			},{
				title :'认证时间',
				field :'cdate',
				width : 120,
				sortable :true
			},{
				title :'退回原因',
				field :'approveNote',
				width : 70,
				sortable :true
			},{
				title :'操作',
				field :'operation',
				width : 70,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_10646_datagrid').datagrid('getData').rows[index];
					if(rowData.status!='00'||(rowData.status=='00'&&rowData.respcd!='2000'&&rowData.approveNote==null&&rowData.authUpload!=null)){
						return '<img src="${ctx}/images/start.png" title="审核" style="cursor:pointer;" onclick="sysAdmin_10646_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;';
						//'<img src="${ctx}/images/close.png" title="退回" style="cursor:pointer;" onclick="sysAdmin_10646_backFun('+index+')"/>&nbsp;&nbsp';
					}else if(rowData.respinfo=='人工认证成功'){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10646_showFun('+index+',1)"/>';
					}else if(rowData.approveNote!=null){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10646_showFun('+index+',2)"/>';
					}
			}
			}]]
		});
	});
	
	//修改交易认证信息 人工通过 /退回
	function sysAdmin_10646_editFun(index) {

		//获取操作对象
		var rows = $('#sysAdmin_10646_datagrid').datagrid('getRows');
		var row = rows[index];
		
		$('<div id="sysAdmin_10646_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">交易认证信息</span>',
			width: 800,
		    height:510,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10645.jsp?bmatid='+row.bmatid+'&go="false"',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10646_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.bmatid);    
		    	$('#sysAdmin_10645_editForm').form('load', row);
			},
			buttons:[{
				text:'通过认证',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_10645_editForm").getElementsByTagName("input");

		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_10645_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantAuthenticity_editTxnAuthenticity.action?flag=go',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10646_datagrid').datagrid('reload');
					    			$('#sysAdmin_10646_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10646_editDialog').dialog('destroy');
					    			$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'退回',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_10645_editForm").getElementsByTagName("input");
					//var approveNote = $("#sysAdmin_10645_editForm #backinfo .table #approveNote").val();
		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_10645_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantAuthenticity_editTxnAuthenticity.action?flag=back',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10646_datagrid').datagrid('reload');
					    			$('#sysAdmin_10646_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10646_editDialog').dialog('destroy');
					    			$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10646_editDialog').dialog('destroy');
					$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
			}
		});
	}
	//查看交易认证信息 人工认证成功
	function sysAdmin_10646_showFun(index,type) {
		//获取操作对象
		var rows = $('#sysAdmin_10646_datagrid').datagrid('getRows');
		var row = rows[index];
		var url ='${ctx}/biz/bill/merchant/10645.jsp?bmatid='+row.bmatid;
		//alert("type"+type+"----"+url);
		if(type=='1'){
			url+='&go="true"';
		}
		$('<div id="sysAdmin_10646_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">交易认证信息</span>',
			width: 800,
		    height:510,
		    closed: false,
		    href: url,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10646_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.bmatid);    
		    	$('#sysAdmin_10645_editForm').form('load', row);
			},
			buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10646_editDialog').dialog('destroy');
					$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
			}
		});
	}
	/**
		交易认证退回	（弃用）
	*/
	function sysAdmin_10646_backFun(index) {

		//获取操作对象
		var rows = $('#sysAdmin_10646_datagrid').datagrid('getRows');
		var row = rows[index];
		
		$('<div id="sysAdmin_10646_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">交易认证退回</span>',
			width: 900,
		    height:510, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10645.jsp?bmatid='+row.bmatid,  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10646_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.bmatid);    
		    	$('#sysAdmin_10645_editForm').form('load', row);			
		    },
			buttons:[{
				text:'退回',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_10645_editForm").getElementsByTagName("input");
					var approveNote = $("#sysAdmin_10645_editForm #backinfo .table #approveNote").val();
		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_10645_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantAuthenticity_editTxnAuthenticity.action?flag=back&approveNote='+approveNote,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10646_datagrid').datagrid('reload');
					    			$('#sysAdmin_10646_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10646_editDialog').dialog('destroy');
					    			$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10646_editDialog').dialog('destroy');
					$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10646_datagrid').datagrid('unselectAll');
			}
		});
		
	}

	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun10646() {
		$('#sysAdmin_10646_datagrid').datagrid('load', serializeObject($('#sysAdmin_10646_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun10646() {
		$('#sysAdmin_10646_searchForm input').val('');
		$('#sysAdmin_10646_searchForm select').val('全部');
	}
	
	//查看终端
	function sysAdmin_10646_queryFun(mid){
		$('<div id="sysAdmin_10646_run"/>').dialog({
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
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10646_searchForm" style="padding-left:2%" method="post">
		<input type="hidden" id="bmatids" name="bmatids" />
			<table class="tableForm" >
				<tr>
					<th>app用户名(电话号)</th>
					<td><input name="username" style="width: 100px;" /></td>
					<td width="10px"></td>
					<th>商户编号</th>
					<td><input name="mid" style="width: 150px;" /></td>
					<td width="10px"></td>
					<th>认证状态</th>
					<td>
						<select name="status" style="width: 120px;">
							<option value="" selected="selected">全部</option>
							<option value="00">认证成功</option>
							<option value="01">失败已上传</option>
							<option value="02">失败已上传待审</option>
							<option value="03">失败未上传</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>持卡人姓名</th>
					<td><input name=bankAccName style="width: 100px;" /></td>
					<td width="10px"></td>
					<th>卡号</th>
					<td><input name="bankAccNo" style="width: 150px;" /></td>
					<td width="10px"></td>
					<th>认证日期</th>
					<td><input name="cdate" class="easyui-datebox" style="width: 120px;"  /></td>
					<td width="20px"></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun10646();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun10646();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10646_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


