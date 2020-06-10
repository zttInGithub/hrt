<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>
<%
    Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession);
%>
    
<!-- 一级运营中心&运营中心入网 -->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_agentunit_00410_datagrid').datagrid({
			url :'${ctx}/sysAdmin/agentunit_listAgentUnit00410.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'buid',
			sortOrder: 'desc',
			idField : 'buid',
			columns : [[{
				title : '运营中心编号',
				field : 'buid',
				width : 100,
				align : 'center',
				formatter: function(value,row,index){
					value="00000"+value;
					var todoIDfo=value.substring(value.length-6,value.length);
					return todoIDfo;
				}
			},{
				title : '运营中心名称',
				field : 'agentName',
				width : 100
			},{
				title : '类别',
				field : 'agentLvl',
				width : 100 ,
				formatter : function(value,row,index) {
					if(value==1){
						return '运营中心';
					}else if(value=2){
						return '分销运营中心';
					}else{
						return '代理商';
					}
				} 
			},{
				title :'经营地址',
				field :'baddr',
				width : 100
			},{
				title :'开通状态',
				field :'openName',
				width : 100
			},{
				title :'缴款状态',
				field :'amountConfirmName',
				width : 100
			},{
				title :'签约人员',
				field :'signUserIdName',
				width : 100
			},{
				title :'退回原因',
				field :'returnReason',
				width : 100
			},{
				title :'审核状态',
				field :'approveStatus',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(value=="W"){
						return "待审核";
					}else if(value=="Y"){
						return "审核通过";
					}else if(value=="C"){
						return "审核中";
					}else if(value == "K"){
						return "退回";
					}else {
						return "";
					}
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					var restVal="";
					if(row['approveStatus']=="K"){
						restVal+='<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="sysAdmin_agentunit_00410_editFun('+index+','+row.buid+')"/>';
					}
					var amountConfirmDate =row['amountConfirmDate'];
					if(!amountConfirmDate && amountConfirmDate!=""){
						if(restVal!=""){restVal+="&nbsp;&nbsp;";}
						restVal+='<img src="${ctx}/images/frame_remove.png" title="删除" style="cursor:pointer;" onclick="sysAdmin_agentunit_00410_delFun('+row.buid+')"/>';
					}
					return restVal;
				}
			}]],
			toolbar:[{
					id:'btn_add',
					text:'运营中心签约',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_agentunit_00410_addFun();
					}
				}]		
		});
	});
	
	
	//表单查询
	function bill_agentunit_00410_searchFun() {
		$('#sysAdmin_agentunit_00410_datagrid').datagrid('load', serializeObject($('#bill_agentunit_00410_searchForm')));
	}

	//清除表单内容
	function bill_agentunit_00410_cleanFun() {
		$('#bill_agentunit_00410_searchForm input').val('');
	}
	
	
	
	function sysAdmin_agentunit_00410_addFun() {
		$('<div id="sysAdmin_agentunit_00410_addDialog"/>').dialog({
			 title: '<span style="color:#157FCC;">运营中心签约</span>',
			 width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00411.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() { 
		    		// 总公司用户，签约运营中心
		    		if($('#00411_tab2_select').val()!=1){
	    				$.messager.alert("提示","请确保成本信息已录入","info");
	    				return;
	    			}
		    		//1、成本信息
		    		var tr_list = $("#00411_tab2_body").children("tr");
		    		var cost_data = [];
		    		for(var i = 1; i <= tr_list.length; i ++){
		    			var inputs = $("#00411T"+i+" input");
		    			var o = serializeTrObj(inputs);
		    			cost_data.push(o);
		    		}
						var tr_act_list = $("#00411_ACT_TR").children("tr")
						for(var i = 0; i < tr_act_list.length; i ++){
							var inputsAct = $("#00411TACT"+i+" input");
							var oAct = serializeTrObj(inputsAct);
							cost_data.push(oAct);
						}
		    		console.log(cost_data);
	    			$("#00411_costData").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
		    		var validator = $('#sysAdmin_agentunit_00411_addForm').form('validate');
		    		if(validator){
		    			$.messager.progress();
		    		}
		    		$('#sysAdmin_agentunit_00411_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/agentunit_addAgentUnit00410.action',
		    			success:function(data) {
		    				$.messager.progress('close'); 
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentunit_00410_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_00410_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			//$('#sysAdmin_agentunit_addDialog').dialog('destroy');
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
					$('#sysAdmin_agentunit_00410_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	function sysAdmin_agentunit_00410_editFun(index,buid) {
		$('<div id="sysAdmin_agentunit_00410_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改运营中心信息</span>',
			 width: 950,
		    height:550, 
		    closed: false,
		    href: '${ctx}/biz/bill/agent/agentunit/00412.jsp?buid='+buid,  
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_agentunit_00410_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.buid);    	
		    	$('#sysAdmin_agentunit_00412_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					// 获取封装信息
					var tr_list = $("#00412_tab2_body").children("tr");
		    		var cost_data = [];
		    		<%-- <% if(user.getUnitLvl()==0){ %> --%>
		    		//1、成本信息
		    		
		    		for(var i = 1; i <= tr_list.length; i ++){
		    			var inputs = $("#00412T"+i+" input");
		    			var o = serializeTrObj(inputs);
		    			cost_data.push(o);
		    		}
						var tr_listAct = $("#00412_ACT_TR").children("tr")
						for(var i = 1; i < tr_listAct.length; i ++){
							var inputsAct = $("#00412_ACTT"+i+" input");
							var oAct = serializeTrObj(inputsAct);
							cost_data.push(oAct);
						}
		    		$("#00412_costData").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
					<%-- <%}%> --%>
					
					$('#sysAdmin_agentunit_00412_editForm').form('submit', {
						url:'${ctx}/sysAdmin/agentunit_editAgentUnit00410.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_agentunit_00410_datagrid').datagrid('reload');
					    			$('#sysAdmin_agentunit_00410_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			// $('#sysAdmin_agentunit_00410_editDialog').dialog('destroy');
					    			$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_agentunit_00410_editDialog').dialog('destroy');
					$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	function sysAdmin_agentunit_00410_delFun(buid){
		$.messager.confirm('确认','您确认要删除所选记录吗?',function(result) {
			if (result) {
				$.ajax({
					url:"${ctx}/sysAdmin/agentunit_deleteAgentUnit.action",
					type:'post',
					data:{"ids":buid},
					dataType:'json',
					success:function(data, textStatus, jqXHR) {
						if (data.success) {
							$.messager.show({
								title : '提示',
								msg : data.msg
							});
							$('#sysAdmin_agentunit_00410_datagrid').datagrid('reload');
						} else {
							$.messager.alert('提示', data.msg);
							$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
						}
					},
					error:function() {
						$.messager.alert('提示', '删除记录出错！');
						$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
					}
				});
			} else {
				$('#sysAdmin_agentunit_00410_datagrid').datagrid('unselectAll');
			}
		});
	}

</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:50px; overflow: hidden; padding-top:5px;">
		<form id="bill_agentunit_00410_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<th>运营中心名称</th>
					<td><input name="agentName" style="width: 260px;" /></td>		
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="bill_agentunit_00410_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="bill_agentunit_00410_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="sysAdmin_agentunit_00410_datagrid"></table>
    </div> 
</div>
