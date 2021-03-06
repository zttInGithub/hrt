<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10641_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchantAuthenticity_listMerchantAuthenticity.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15, 50, 100, 150, 200 ],
			sortName: 'bmatid',
			sortOrder: 'desc',
			idField : 'bmatid',
			columns : [[{
				field : 'bmatid',
				checkbox:true
			},{
				title : 'APP用户名',
				field : 'username',
				width : 100
			},{
				title : '商户编号',
				field : 'mid',
				width : 120
			},{
				title :'身份证号',
				field :'legalNum',
				width : 120,
				sortable :true
			},{
				title :'持卡人名称',
				field :'cardName',
				width : 80,
				sortable :true,
				hidden:true
			},{
				title :'卡号',
				field :'bankAccNo',
				width : 130
			},{
				title :'认证状态',
				field :'status',
				width : 70,
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
				width : 100
				//sortable :true
			},{
				title :'认证退回原因',
				field :'approveNote',
				width : 100
			},{
				title :'认证时间',
				field :'cdate',
				width : 120,
				sortable :true
			},{
				title :'操作',
				field :'operation',
				width : 50,
				align : 'center',
				formatter : function(value,row,index) {
					var rowData = $('#sysAdmin_10641_datagrid').datagrid('getData').rows[index];
					if(rowData.status!='00' && rowData.cardName != '13'){
						return '<img src="${ctx}/images/frame_pencil.png" title="查询认证结果" style="cursor:pointer;" onclick="sysAdmin_10641_editFun1('+index+')"/>&nbsp;&nbsp';
					}else if(rowData.status=='00'&&rowData.respcd!='2000'&&rowData.authUpload!=null&&rowData.approveNote==null){
						return '<img src="${ctx}/images/start.png" title="审核" style="cursor:pointer;" onclick="sysAdmin_10641_editFun('+index+')"/>&nbsp;&nbsp;&nbsp;';
					}
					/*else if(rowData.respinfo=='人工认证成功'){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10641_showFun('+index+',1)"/>';
					}else if(rowData.approveNote!=null){
						return '<img src="${ctx}/images/query_search.png" title="查看" style="cursor:pointer;" onclick="sysAdmin_10641_showFun('+index+',2)"/>';
					}*/
			}
			}]],
			toolbar:[{
				id:'btn_runSelected',
				text:'异常勾选重发',
				iconCls:'icon-start',
				handler:function(){
					sysAdmin_merchant10641_chongfa();
				}
			}]
		});
	});

	function sysAdmin_merchant10641_chongfa() {
		var checkedItems = $('#sysAdmin_10641_datagrid').datagrid('getChecked');
		var names = [];
		var flag = false;
		$.each(checkedItems, function(index, item){
			//debugger;
			names.push(item.bmatid);
			if(item.cardName=='13'){
				$.messager.alert('提示',"当前商户"+item.mid+"不支持重发");
				flag = true;
			}
		});
		if(flag){
			return;
		}
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		//获取操作对象
		//var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
		//var row = rows[index];
		
		$('<div id="sysAdmin_10641_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">选择通道</span>',
			width: 400,
		    height:150,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10648.jsp',
		    modal: true,
		   /** onLoad:function() {
		    	var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.bmatid);    
		    	$('#sysAdmin_10642_editForm').form('load', row);
			},**/
			buttons:[{
				text:'重新认证',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_10648_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantAuthenticity_addMerchantAuthInfoForECP.action?bmids='+bmids,
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10641_datagrid').datagrid('reload');
					    			$('#sysAdmin_10641_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10641_editDialog').dialog('destroy');
					    			$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10641_editDialog').dialog('destroy');
					$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//修改商户认证信息
	function sysAdmin_10641_editFun1(index) {

		//获取操作对象
		var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
		var row = rows[index];
		
		$('<div id="sysAdmin_10642_editDialog"><div align="center" style="color:#157FCC;">确认查询认证结果？</div></div>').dialog({
			title: '<span style="color:#157FCC;align:center">提示</span>',
			width: 200,
		    height:100, 
		    closed: false,
		    //href: "<span style='color:#157FCC;'>确认再次认证？</span>",  
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					//等待窗口
					$('#sysAdmin_10642_editDialog').dialog('destroy');
					$('<div id="sysAdmin_10642_wait"><div align="center" style="color:#157FCC;">请稍等,正在认证中...</div></div>').dialog({
						title: '<span style="color:#157FCC;align:center">提示</span>',
						width: 200,
					    height:100,
					    //closed: false,
					    modal: true
					});
					//认证
					$.ajax({ 
						url:"sysAdmin/merchantAuthenticity_editMerchantAuthenticity.action?bmatid="+row.bmatid+"&sysseqnb="+row.sysseqnb+"&cdate="+row.cdate, 
						dataType:"json",
						type:"post",
						//async:"true",
						error:function(err){
							$('#sysAdmin_10642_wait').dialog('destroy');
							alert("错误");
						},
						success:function(data){
							//var result = $.parseJSON(data);
							//var result = JSON.parse(data);
							$('#sysAdmin_10642_wait').dialog('destroy');
							if (data.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (data.success) {
									$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
					    			$('#sysAdmin_10641_datagrid').datagrid('reload');
					    			$.messager.show({
										title : '提示',
										msg : data.msg
									});
					    		} else {
									$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
									$('#sysAdmin_10641_datagrid').datagrid('reload');
					    			$.messager.alert('提示',data.msg);
						    	}
					    	}	
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10642_editDialog').dialog('destroy');
					$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantAuthenticity_datagrid').datagrid('unselectAll');
			}
		});
	}
	//修改交易认证信息 人工通过 /退回
	function sysAdmin_10641_editFun(index) {

		//获取操作对象
		var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
		var row = rows[index];
		
		$('<div id="sysAdmin_10641_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">商户实名认证信息</span>',
			width: 800,
		    height:510,
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10642.jsp?bmatid='+row.bmatid+'&go="false"',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.bmatid);    
		    	$('#sysAdmin_10642_editForm').form('load', row);
			},
			buttons:[{
				text:'通过认证',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_10642_editForm").getElementsByTagName("input");

		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_10642_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantAuthenticity_editMerAuthenticity.action?flag=go',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10641_datagrid').datagrid('reload');
					    			$('#sysAdmin_10641_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10641_editDialog').dialog('destroy');
					    			$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
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
					var inputs = document.getElementById("sysAdmin_10642_editForm").getElementsByTagName("input");
					//var approveNote = $("#sysAdmin_10642_editForm #backinfo .table #approveNote").val();
		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_10642_editForm').form('submit', {
						url:'${ctx}/sysAdmin/merchantAuthenticity_editMerAuthenticity.action?flag=back',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_10641_datagrid').datagrid('reload');
					    			$('#sysAdmin_10641_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_10641_editDialog').dialog('destroy');
					    			$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_10641_editDialog').dialog('destroy');
					$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
			}
		});
	}
	//查看认证信息 人工认证成功
	function sysAdmin_10641_showFun(index,type) {
		//获取操作对象
		var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
		var row = rows[index];
		var url ='${ctx}/biz/bill/merchant/10642.jsp?bmatid='+row.bmatid;
		//alert("type"+type+"----"+url);
		if(type=='1'){
			url+='&go="true"';
		}
		$('<div id="sysAdmin_10641_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">商户实名认证信息</span>',
			width: 800,
		    height:510,
		    closed: false,
		    href: url,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_10641_datagrid').datagrid('getRows');
				var row = rows[index];
				if(row.approveNote==null||row.approveNote==""){
					url+='&go="true"';
				}
		    	row.menuIds = stringToList(row.bmatid);    
		    	$('#sysAdmin_10642_editForm').form('load', row);
			},
			buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_10641_editDialog').dialog('destroy');
					$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_10641_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun10641() {
		$('#sysAdmin_10641_datagrid').datagrid('load', serializeObject($('#sysAdmin_10641_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun10641() {
		$('#sysAdmin_10641_searchForm input').val('');
		$('#sysAdmin_10641_searchForm select').val('全部');
	}

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_10641_searchForm" style="padding-left:2%" method="post">
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
							<option value="10">认证中</option>
							<option value="11">未认证</option>
						</select>
					</td>
					<th>商户类型</th>
					<td>
						<select name="sendType" style="width: 120px;">
							<option value="" selected="selected">全部</option>
							<option value="1">手刷</option>
							<option value="2">收银台</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>持卡人姓名</th>
					<td><input name="bankAccName" style="width: 100px;" /></td>
					<td width="10px"></td>
					<th>卡号</th>
					<td><input name="bankAccNo" style="width: 150px;" /></td>
					<td width="10px"></td>
					<th>认证日期</th>
					<td><input name="cdate" class="easyui-datebox" style="width: 120px;"  /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun10641();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun10641();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_10641_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


