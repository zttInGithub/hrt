<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_snallot_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_searchTerminalInfo2.action',
			fit : true,
			fitColumns : false,
			border : false,
			nowrap : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10,15 ],
			columns : [[{
				title :'唯一编号',
				field :'BTID',
				width : 100,
				hidden:true
			},{
				title :'终端编号',
				field :'TERMID',
				width : 150,
				sortable:true
			},{
				title : 'SN',
				field : 'SN',
				width : '150',
				sortable:true
			},{
				title :'是否手刷',
				field :'ISM35',
				width : 150,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 1){
						return '是';
					}else{
						return '否';
					}
				}
			},{
				title : '费率',
				field : 'RATE',
				width : '150',
				sortable:true
			},{
				title : '秒到手续费(元)',
				field : 'SECONDRATE',
				width : '150',
				sortable:true
			}]]		
		});
	});
	
	//表单查询
	function sysAdmin_10390_searchFun() {
		var termIDStart= $("#termIDStart0").val();
		var termIDEnd= $("#termIDEnd0").val();
		var snStart = $("#snStart0").val();
		var snEnd = $("#snEnd0").val();
		if((termIDStart == null || termIDStart =="") && (termIDEnd ==null || termIDEnd=="") && 
		   (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
		   	$('#sysAdmin_snallot_datagrid').datagrid('load', serializeObject($('#sysAdmin_snallot_searchForm')));		 
		}else if( (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
			if(termIDStart!=null && termIDStart!="" && termIDEnd!=null && termIDEnd !=""){
				if(termIDStart>termIDEnd){
					$.messager.alert('提示', "起始终端号大于终止终端号！");
				}else{
					$('#sysAdmin_snallot_datagrid').datagrid('load', serializeObject($('#sysAdmin_snallot_searchForm')));
				}
			}else{
				$.messager.alert('提示', "请完善终端号查询！");
			}
		   		 
		}else if((termIDStart ==null || termIDStart=="") && (termIDEnd ==null || termIDEnd=="")){
			if(snStart !=null && snStart != "" && snEnd != null && snEnd !=""){
				if(snStart >snEnd){
					$.messager.alert('提示', "起始SN号大于终止SN号！");
				}else{
					$('#sysAdmin_snallot_datagrid').datagrid('load', serializeObject($('#sysAdmin_snallot_searchForm')));
				}
			}else{
				$.messager.alert('提示', "请完善SN号查询！");
			}
				
		}else{
				$.messager.alert('提示', "请选择一种方式查询！"); 
			
		}
	}

	//清除表单内容
	function sysAdmin_10390_cleanFun() {
		$('#sysAdmin_snallot_searchForm input').val('');
	}
	function f2s(){
		var termIDStart= $("#termIDStart0").val();
		var termIDEnd= $("#termIDEnd0").val();
		var snStart = $("#snStart0").val();
		var snEnd = $("#snEnd0").val();
		if((termIDStart == null || termIDStart =="") && (termIDEnd ==null || termIDEnd=="") && 
		   (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
		   	  	 $.messager.alert('提示', "请选择一种方式分配！");
		}else if( (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
			if(termIDStart!=null && termIDStart!="" && termIDEnd!=null && termIDEnd !=""){
				if(termIDStart>termIDEnd){
					$.messager.alert('提示', "起始终端号大于终止终端号！");
				}else{
					sysAdmin_gaveTerm_updateFun(termIDStart,termIDEnd,snStart,snEnd);
				}
			}else{
				$.messager.alert('提示', "请完善终端号！");
			}
		   		 
		}else if((termIDStart ==null || termIDStart=="") && (termIDEnd ==null || termIDEnd=="")){
			if(snStart !=null && snStart != "" && snEnd != null && snEnd !=""){
				if(snStart >snEnd){
					$.messager.alert('提示', "起始SN号大于终止SN号！");
				}else{
					sysAdmin_gaveTerm_updateFun(termIDStart,termIDEnd,snStart,snEnd);
				}
			}else{
				$.messager.alert('提示', "请完善SN号！");
			}
				
		}else{
				$.messager.alert('提示', "请选择一种分配方式！"); 			
		}
	}
	
	//分配终端
	function sysAdmin_gaveTerm_updateFun(termIDStart,termIDEnd,snStart,snEnd) {
		$('<div id="sysAdmin_gaveSn_updateDialog"/>').dialog({
			title: '<span style="color:#157FCC;">选择分配机构</span>',
			width: 400,   
		    height: 300, 
		    resizable:true,
		    maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/bill/tid/10391.jsp?termIDStart='+termIDStart+'&termIDEnd='+termIDEnd+'&snStart='+snStart+'&snEnd='+snEnd,
		    modal: true,
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_gaveTerminal_serchForm').form('submit', {
						url:'${ctx}/sysAdmin/terminalInfo_gaveTerminal.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_snallot_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_snallot_datagrid').datagrid('reload');
					    			$('#sysAdmin_gaveSn_updateDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_gaveSn_updateDialog').dialog('destroy');
					    			$('#sysAdmin_snallot_datagrid').datagrid('unselectAll');
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
					$('#sysAdmin_gaveSn_updateDialog').dialog('destroy');
					$('#sysAdmin_snallot_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_snallot_datagrid').datagrid('unselectAll');
			}
		});
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_snallot_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
				
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端号开始：</th>
					<td>
						<input id="termIDStart0" name="termIDStart" />&nbsp;至&nbsp;
						<input id="termIDEnd0" name="termIDEnd" />
					</td>
					<th>&nbsp;或&nbsp;</th>
					<th>SN号开始：</th>
					<td>
						<input id="snStart0" name="snStart" />&nbsp;至&nbsp;
						<input id="snEnd0" name="snEnd" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="f2s();">分配</a> &nbsp;
					</td>
					
				</tr>

				<tr>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10390_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10390_cleanFun();">清空</a>	
						
					</td>
						
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_snallot_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


