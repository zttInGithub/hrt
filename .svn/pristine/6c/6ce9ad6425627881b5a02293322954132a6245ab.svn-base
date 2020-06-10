<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 人工实名认证 -->  
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_10150_datagrid').datagrid({
			url :"${ctx}/sysAdmin/merchant_listMerchantFalse.action",
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 100,
			pageList : [ 100,200,300,500 ],
			sortName: 'joinConfirmDate',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				field : 'bmid',
				title : '商户id',
				checkbox:true
			},{
				title : '商户编号',
				field : 'mid',
				width : 100
			},{
				title : '账户卡号',
				field : 'bankAccNo',
				width : 100
			},{
				title : '账户名称',
				field : 'bankAccName',
				width : 100
			},{
				title :'状态',
				field :'authType',
				width : 100,
				formatter:function(value,row,index){
					//0成功  1失败  2 补入账中 3入账失败
					if(value=='1'){
						return "待补入账";
					}else if(value=='3'){
						return "补入账中";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_10150_datagrid').datagrid('getData').rows[index];
					return '<input type="button" value="通过" onclick="sysAdmin_10643_go('+index+')"/>&nbsp;&nbsp'+ 
							'<input type="button" value="退回" onclick="sysAdmin_10643_back('+index+')"/>&nbsp;&nbsp';
				}
			}]],
			toolbar:[{
				id:'btn_run',
				text:'提交结算',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10643_exportFun();
				}
			},{
				id:'btn_run',
				text:'勾选通过',
				iconCls:'icon-start',
				handler:function(){
					sysAdmin_10643_goFun();
				}
			},{
				id:'btn_run',
				text:'勾选退回',
				iconCls:'icon-close',
				handler:function(){
					sysAdmin_10643_backFun();
				}
			},{
				id:'btn_run',
				text:'线下导入',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10643_ImportAuthTypeFun();
				}
			},{
				id:'btn_run',
				text:'批量导入',
				iconCls:'icon-query-export',
				handler:function(){
					sysAdmin_10643_updateFun();
				}
			}]
		});
	});
	
	//线下导入
	function sysAdmin_10643_ImportAuthTypeFun(){
		$('<div id="sysAdmin_10643_ImportAuthTypeFun"/>').dialog({
			title: '批量导入认证信息',
			width: 670,   
		    height: 216,   
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10973.jsp',
		    modal: true,
			buttons:[],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	//批量导入
	function sysAdmin_10643_updateFun(){
		$('<div id="sysAdmin_10643_addFun"/>').dialog({
			title: '批量导入认证信息',
			width: 670,   
		    height: 216,   
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10649.jsp',
		    modal: true,
			buttons:[],
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	
	function sysAdmin_10150_exportAgents(){
		$('#sysAdmin_10150_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_exportAgents.action'
			    	});
	}
	//提交结算
	function sysAdmin_10643_exportFun() {
		var checkedItems = $('#sysAdmin_10150_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var ids=names.join(",");
		if(ids==null||ids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}

        if(window.confirm('你确定要提交结算吗？')){
            //alert("确定");
            $("#searchids").val(ids);
            $('#sysAdmin_10150_searchForm').form('submit',{
                url:"${ctx}/sysAdmin/merchant_export.action?searchids="+ids,
                dataType:"json",
                success:function(data){
                    var result = $.parseJSON(data);
                    $('#sysAdmin_10150_datagrid').datagrid('reload');
                    $.messager.alert('提示',result.msg);
                },
                error:function(data){
                    var result = $.parseJSON(data);
                    $.messager.alert('提示',result.msg);
                }
            });
            setTimeout("$('#sysAdmin_10150_datagrid').datagrid('reload')", 3000 );
            $('#sysAdmin_10150_datagrid').datagrid('clearSelections');
            // return true;
        }else{
            //alert("取消");
            // return false;
        }
	}
	//回退
	function sysAdmin_10643_back(index){
	var rowData = $('#sysAdmin_10150_datagrid').datagrid('getData').rows[index];
	$.messager.confirm('确认','您确认要退回所选记录吗?',function(result) {
		if (result) {
			$.ajax({ 
				url:"sysAdmin/merchant_MerchantAuthGoOrBack.action?mid="+rowData.mid+"&flag=back&bankAccNo="+rowData.bankAccNo+"&bankAccName="+rowData.bankAccName+"&legalNum="+rowData.legalNum, 
				dataType:"json",
				type:"post",
				error:function(err){
					$.messager.alert('提示',err.msg);
				},
				success:function(data){
					if (data.sessionExpire) {
	    				window.location.href = getProjectLocation();
		    		} else {
		    			if (data.success) {
		    				$('#sysAdmin_10150_datagrid').datagrid('reload');
			    			$.messager.show({
								title : '提示',
								msg : data.msg
							});
			    		} else {
			    			$('#sysAdmin_10150_datagrid').datagrid('reload');
			    			$.messager.alert('提示',data.msg);
				    	}
			    	}	
				}
			});
		} else {
				$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
			}
		});
	}
	//通过
	function sysAdmin_10643_go(index){
	var rowData = $('#sysAdmin_10150_datagrid').datagrid('getData').rows[index];
	$.messager.confirm('确认','您确认要通过所选记录吗?',function(result) {
		if (result) {
			$.ajax({ 
				url:"sysAdmin/merchant_MerchantAuthGoOrBack.action?mid="+rowData.mid+"&flag=go&bankAccNo="+rowData.bankAccNo+"&bankAccName="+rowData.bankAccName+"&legalNum="+rowData.legalNum, 
				dataType:"json",
				type:"post",
				error:function(err){
					$.messager.alert('提示',err.msg);
				},
				success:function(data){
					if (data.sessionExpire) {
	    				window.location.href = getProjectLocation();
		    		} else {
		    			if (data.success) {
		    				$('#sysAdmin_10150_datagrid').datagrid('reload');
			    			$.messager.show({
								title : '提示',
								msg : data.msg
							});
			    		} else {
			    			$('#sysAdmin_10150_datagrid').datagrid('reload');
			    			$.messager.alert('提示',data.msg);
				    	}
			    	}	
				}
			});
		} else {
			$('#sysAdmin_10150_datagrid').datagrid('unselectAll');
		}
	});
}
	
	function bill_agentunitdata10150_searchFun() {
		$('#sysAdmin_10150_datagrid').datagrid('load', serializeObject($('#sysAdmin_10150_searchForm')));
	}

	//清除表单内容
	function bill_agentunitdata10150_cleanFun() {
		$('#sysAdmin_10150_searchForm input').val('');
		$('#sysAdmin_10150_searchForm select').val('全部');
	}
	//勾选通过
	function sysAdmin_10643_goFun() {
		$.messager.confirm('确认','您确认要通过所选记录吗?',function(result) {
			if (result) {
				var checkedItems = $('#sysAdmin_10150_datagrid').datagrid('getChecked');
				var arr = [];
				$.each(checkedItems, function(index, item){
					var obj = {"mid":item.mid,"bankAccNo":item.bankAccNo,"bankAccName":item.bankAccName,"legalNum":item.legalNum,"flag":"go"};
					arr.push(obj);
				});
				if(arr.length<=0){
					$.messager.alert('提示',"请勾选操作列");
					return;
				}
				var names = JSON.stringify(arr);
				$.ajax({
					url:'${ctx}/sysAdmin/merchant_AllMerchantAuthGoOrBack.action',
					data:{names: names,flag:"go"},
					dataType:"json",
					type:"post",
					success:function(data){
						if (data.sessionExpire) {
		    				window.location.href = getProjectLocation();
			    		} else {
			    			if (data.success) {
			    				$('#sysAdmin_10150_datagrid').datagrid('reload');
				    			$.messager.show({
									title : '提示',
									msg : data.msg
								});
				    		} else {
				    			$('#sysAdmin_10150_datagrid').datagrid('reload');
				    			$.messager.alert('提示',data.msg);
					    	}
				    	}
					},
					error:function(data){
						var result = $.parseJSON(data);
						$.messager.alert('提示',result.msg);
					}
				});
			}
		});
	}
	//勾选退回
	function sysAdmin_10643_backFun() {
		$.messager.confirm('确认','您确认要回退所选记录吗?',function(result) {
			if (result) {
				var checkedItems = $('#sysAdmin_10150_datagrid').datagrid('getChecked');
				var arr = [];
				$.each(checkedItems, function(index, item){
					var obj = {"mid":item.mid,"bankAccNo":item.bankAccNo,"bankAccName":item.bankAccName,"legalNum":item.legalNum,"flag":"go"};
					arr.push(obj);
				});
				if(arr.length<=0){
					$.messager.alert('提示',"请勾选操作列");
					return;
				}
				var names = JSON.stringify(arr);
				$.ajax({
					url:'${ctx}/sysAdmin/merchant_AllMerchantAuthGoOrBack.action',
					data:{names: names,flag:"back"},
					dataType:"json",
					type:"post",
					success:function(data){
						if (data.sessionExpire) {
		    				window.location.href = getProjectLocation();
			    		} else {
			    			if (data.success) {
			    				$('#sysAdmin_10150_datagrid').datagrid('reload');
				    			$.messager.show({
									title : '提示',
									msg : data.msg
								});
				    		} else {
				    			$('#sysAdmin_10150_datagrid').datagrid('reload');
				    			$.messager.alert('提示',data.msg);
					    	}
				    	}
					},
					error:function(data){
						var result = $.parseJSON(data);
						$.messager.alert('提示',result.msg);
					}
				});
			}
		});
	}
</script> 

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="sysAdmin_10150_searchForm" style="padding-left:10%" method="post">
			<input type="hidden" id="searchids" name="searchids" />
			<table class="tableForm" >
					<tr>
						<th>商户编号</th>
						<td><input name="mid" style="width: 150px;" /></td>
						<td width="10px"></td>
						<th>认证状态</th>
						<td>
							<select name="authType" style="width: 150px;">
									<option value="" selected="selected">全部</option>
									<option value="1">待入账</option>
									<option value="3">补入账中</option>
							</select>
						</td>
						<td width="10px"></td>
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
							onclick="bill_agentunitdata10150_searchFun();">查询</a> &nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
							onclick="bill_agentunitdata10150_cleanFun();">清空</a>	
						</td>
					</tr>
				</table>
			</form>
	</div> 
	 <div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10150_datagrid" style="overflow: hidden;"></table>
	</div> 
</div>
