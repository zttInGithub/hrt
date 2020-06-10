<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
	
		$("#unNO").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			valueField : 'UNNO',
			textField : 'UN_NAME',
			onSelect:function(rec){
				$("#unNO").val(rec.UNNO);
				$('#sysAdmin_terminalUse10312_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalUse10312_searchForm')));
			}
		});
		$('#sysAdmin_terminalUse10312_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findUseUpdate.action',
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15 ,100],
			/* ctrlSelect:true,
			checkOnSelect:true, */
			sortName: 'termID',
			sortOrder: 'asc',
			idField : 'btID',
			columns : [[{
				title :'唯一编号',
				field :'btID',
				width : 100,
				checkbox:true
			},{
				title :'机构编号',
				field :'unNO',
				width : 100,
				sortable:true
			},{
				title :'终端编号',
				field :'termID',
				width : 100,
				sortable:true
			},{
				title :'SN号',
				field :'sn',
				width : 100,
				sortable:true
			},{
				title :'活动类型',
				field :'rebateType',
				width : 100,
				sortable:true
			},{
				title :'押金金额',
				field :'depositAmt',
				width : 100,
				sortable:true
			},{
				title :'费率',
				field :'rate',
				width : 100,
				sortable:true
			},{
				title :'秒到手续费',
				field :'secondRate',
				width : 100,
				sortable:true
			},{
				title :'扫码费率',
				field :'scanRate',
				width : 100,
				sortable:true
			},{
				title :'密钥类型',
				field :'keyType',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '短密钥';
					}else if(value=='2'){
						return '长密钥';
					}else{
						return 'Mpos密钥';
					}
				}
			}/* ,{
				title :'密钥明文',
				field :'keyContext',
				width : 100,
				sortable:true
			} */,{
				title :'入网时间/合成状态',
				field :'keyConfirmDate',
				width : 200,
				sortable:true
			},{
				title :'分配情况',
				field :'allotConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未分配';
					}else{
						return '已分配';
					}
				}
			},{
				title :'使用情况',
				field :'usedConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未使用';
					}else{
						return '已使用';
					}
				}
			}
			/**,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.usedConfirmDate == null&&row.sn !=null){
						return '<img src="${ctx}/images/start.png" title="修改费率" style="cursor:pointer;" onclick="sysAdmin_terminalinfo_queryFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '';
					}
				}
			}**/
			]], 
			toolbar:[{
					id:'btn_add',
					text:'修改未使用终端费率信息',
					iconCls:'icon-add',
					handler:function(){
						sysAdmin_terminalinfo_queryFun_10312();
					}
				}]		
		});
	});
	
	function sysAdmin_terminalinfo_queryFun_10312(){
		var checkedItems = $('#sysAdmin_terminalUse10312_datagrid').datagrid('getChecked');
		var names = [];
		var activeList = [];
		var result = true;
		$.each(checkedItems, function(index, item){
			if(item.usedConfirmDate == null&&item.sn !=null){
			}else{
				result = false;
			}
			names.push(item.btID);
			activeList.push(item.rebateType);
		});
		if(!result){
			$.messager.alert('提示','已有使用或传统终端！');
			return;
		}
		var ids=names.join(",");
		if(ids==null||ids==""){
			$.messager.alert('提示','请勾选操作列');
			return;
		}
		
		//判断修改费率的设备是否是统一活动20200102
		var temp_1 = []; //一个新的临时数组
	    for(var i = 0; i < activeList.length; i++){
	    	if(i==0){
	    		temp_1.push(activeList[i]);
	    	}else if(temp_1.indexOf(activeList[i]) == -1){
	    		$.messager.alert('提示','存在不同活动设备，请检查');
				return;
	        }
	    }
		var isActivety = temp_1[0];
		
		
		var type = checkedItems[0].type;
		$.ajax({
				url:"${ctx}/sysAdmin/terminalInfo_judgeTerminalActivtyInfo.action?btids="+ids+'&isActivety='+isActivety,
				type:"post",
				dataType:'json',
				success:function(data){
					if(data.sessionExpire){
						$.messager.alert('提示', "登陆超时，请重新登录");
						return;
					}
					if(data.success){
						var str = data.msg;
						if(str==undefined || str == null){
							$.messager.alert('提示', "特殊设备费率设置问题");
							return;
						}else{
							var specialValue = str.split(',');
							
							var limitunno = specialValue[0];
							var limitrebatetype = specialValue[1];
							/* var specialRate1 = specialValue[0];
							var specialRate2 = specialValue[1];
							var specialAmt1 = specialValue[2];
							var specialAmt2 = specialValue[3];
							if(specialAmt2==undefined||specialAmt1==undefined
									||specialRate2==undefined||specialRate1==undefined){
								$.messager.alert('提示', "特殊设备费率设置问题");
								return;
							} */
						}
						
						$('<div id="sysAdmin_editRate"/>').dialog({
							title: '<span style="color:#157FCC;">修改未使用终端费率信息</span>',
							width: 350,
						    height:350,
						    closed: false,
						   // href: '${ctx}/biz/bill/tid/10315.jsp?specialRate1='+specialRate1+'&specialRate2='+specialRate2+'&specialAmt1='+specialAmt1+'&specialAmt2='+specialAmt2,
						    href: '${ctx}/biz/bill/tid/10315.jsp?limitunno='+limitunno+'&limitrebatetype='+limitrebatetype,
						    modal: true,
							buttons:[{
								text:'确认',
								iconCls:'icon-ok',
								handler:function() {
									$('#sysAdmin_editRate_editForm').form('submit', {
										url:'${ctx}/sysAdmin/terminalInfo_editTerminalRateInfo.action?btids='+ids+'&specialFlag=1',
										success:function(data) {
											var result = $.parseJSON(data);
											if (result.sessionExpire) {
							    				window.location.href = getProjectLocation();
								    		} else {
								    			if (result.success) {
								    				$('#sysAdmin_terminalUse10312_datagrid').datagrid('reload');
									    			$('#sysAdmin_editRate').dialog('destroy');
									    			$.messager.show({
														title : '提示',
														msg : result.msg
													});
									    		} else {
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
									$('#sysAdmin_editRate').dialog('destroy');
								}
							}],
							onClose:function() {
								$(this).dialog('destroy');
							}
						});
					}else{
						$('<div id="sysAdmin_editRate"/>').dialog({
							title: '<span style="color:#157FCC;">修改未使用终端费率信息</span>',
							width: 400,
						    height:250,
						    closed: false,
						    href: '${ctx}/biz/bill/tid/10311.jsp?type='+type,
						    modal: true,
							buttons:[{
								text:'确认',
								iconCls:'icon-ok',
								handler:function() {
									$('#sysAdmin_editRate_editForm').form('submit', {
										url:'${ctx}/sysAdmin/terminalInfo_editTerminalRateInfo.action?btids='+ids,
										success:function(data) {
											var result = $.parseJSON(data);
											if (result.sessionExpire) {
							    				window.location.href = getProjectLocation();
								    		} else {
								    			if (result.success) {
								    				$('#sysAdmin_terminalUse10312_datagrid').datagrid('reload');
									    			$('#sysAdmin_editRate').dialog('destroy');
									    			$.messager.show({
														title : '提示',
														msg : result.msg
													});
									    		} else {
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
									$('#sysAdmin_editRate').dialog('destroy');
								}
							}],
							onClose:function() {
								$(this).dialog('destroy');
							}
						});
					}
				}
			});
	}
	
	//表单查询
	function sysAdmin_10312_searchFun() {
		$('#sysAdmin_terminalUse10312_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalUse10312_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10312_cleanFun() {
		$('#sysAdmin_terminalUse10312_searchForm input').val('');
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_terminalUse10312_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<!-- <th>机构名称：</th>
						<td><input id="unNO" name="unNO" class="easyui-combobox" style="width: 150px;"/>
					</td> -->
					
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端编号：</th>
					<td>
						<input name="termIDStart" />&nbsp;至&nbsp;
						<input name="termIDEnd" />
					</td>
					<th>&nbsp;&nbsp;&nbsp;SN号：</th>
					<td>
						<input name="snStart" />&nbsp;至&nbsp;
						<input name="snEnd" />
					</td>
					<th>&nbsp;&nbsp;&nbsp;使用情况：</th>
					<td>
						<select name="status">
							<option selected="selected" value="">全部</option>
							<option value="2">已使用</option>
							<option value="0">未使用</option>
						</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;版本类型：</th>
					<td>
						<select name="type">
							<option selected="selected" value="0">秒到版</option>
							<option value="1">极速版</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10312_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10312_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_terminalUse10312_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


