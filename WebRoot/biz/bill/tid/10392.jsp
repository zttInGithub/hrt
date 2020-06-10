<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_snback_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_searchBackTerminalInfo.action',
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
				title :'机构编号',
				field :'UNNO',
				width : 150,
				sortable:true
			},{
				title :'机构编号',
				field :'UN_NAME',
				width : 150,
				sortable:true
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
			}]]		
		});
	});
	
	//表单查询
	function sysAdmin_10392_searchFun() {
		var termIDStart= $("#termIDStart2").val();
		var termIDEnd= $("#termIDEnd2").val();
		var snStart = $("#snStart2").val();
		var snEnd = $("#snEnd2").val();
		if((termIDStart == null || termIDStart =="") && (termIDEnd ==null || termIDEnd=="") && 
		   (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
		   	$('#sysAdmin_snback_datagrid').datagrid('load', serializeObject($('#sysAdmin_snback_searchForm')));		 
		}else if( (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
			if(termIDStart!=null && termIDStart!="" && termIDEnd!=null && termIDEnd !=""){
				if(termIDStart>termIDEnd){
					$.messager.alert('提示', "起始终端号大于终止终端号！");
				}else{
					$('#sysAdmin_snback_datagrid').datagrid('load', serializeObject($('#sysAdmin_snback_searchForm')));
				}
			}else{
				$.messager.alert('提示', "请完善终端号查询！");
			}
		   		 
		}else if((termIDStart ==null || termIDStart=="") && (termIDEnd ==null || termIDEnd=="")){
			if(snStart !=null && snStart != "" && snEnd != null && snEnd !=""){
				if(snStart >snEnd){
					$.messager.alert('提示', "起始SN号大于终止SN号！");
				}else{
					$('#sysAdmin_snback_datagrid').datagrid('load', serializeObject($('#sysAdmin_snback_searchForm')));
				}
			}else{
				$.messager.alert('提示', "请完善SN号查询！");
			}
				
		}else{
				$.messager.alert('提示', "请选择一种方式查询！"); 
			
		}
	}

	//清除表单内容
	function sysAdmin_10392_cleanFun() {
		$('#sysAdmin_snback_searchForm input').val('');
	}
	function f2(){
		var termIDStart= $("#termIDStart2").val();
		var termIDEnd= $("#termIDEnd2").val();
		var snStart = $("#snStart2").val();
		var snEnd = $("#snEnd2").val();
		if((termIDStart == null || termIDStart =="") && (termIDEnd ==null || termIDEnd=="") && 
		   (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
		   	  	 $.messager.alert('提示', "请选择一种方式回拨！");
		}else if( (snStart ==null || snStart == "") && (snEnd == null || snEnd =="") ){
			if(termIDStart!=null && termIDStart!="" && termIDEnd!=null && termIDEnd !=""){
				if(termIDStart>termIDEnd){
					$.messager.alert('提示', "起始终端号大于终止终端号！");
				}else{
					sysAdmin_backTerm_updateFun(termIDStart,termIDEnd,snStart,snEnd);
				}
			}else{
				$.messager.alert('提示', "请完善终端号！");
			}
		   		 
		}else if((termIDStart ==null || termIDStart=="") && (termIDEnd ==null || termIDEnd=="")){
			if(snStart !=null && snStart != "" && snEnd != null && snEnd !=""){
				if(snStart >snEnd){
					$.messager.alert('提示', "起始SN号大于终止SN号！");
				}else{
				    sysAdmin_backTerm_updateFun(termIDStart,termIDEnd,snStart,snEnd);
				}
			}else{
				$.messager.alert('提示', "请完善SN号！");
			}
				
		}else{
				$.messager.alert('提示', "请选择一种回拨方式！"); 			
		}
	}
	
	//终端回拨
	function sysAdmin_backTerm_updateFun(termIDStart,termIDEnd,snStart,snEnd) {
		$.messager.confirm('确认','您确认回拨吗?',function(r){   
		    if (r){
		        $.ajax({
			        url:'${ctx}/sysAdmin/terminalInfo_backTerminal.action',
		        	data: {"termIDStart":termIDStart,"termIDEnd":termIDEnd,"snStart":snStart,"snEnd":snEnd},
		            dataType: 'json',
		        	success: function(data, textStatus) {
			        	if (data.success) {
			        		$.messager.show({
								title : '提示',
								msg : data.msg
							});
				        	$('#sysAdmin_snback_datagrid').datagrid('reload');
				        } else {
				        	$.messager.alert('提示', data.msg);
				        	$('#sysAdmin_snback_datagrid').datagrid('unselectAll');
					    }
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			        	$.messager.alert('提示', '操作记录出错！');
			        	$('#sysAdmin_snback_datagrid').datagrid('unselectAll');
				    }
			    });

		    	 
		    } else {
		    	$('#sysAdmin_snback_datagrid').datagrid('unselectAll');
			}   
		}); 
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_snback_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
				
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端号开始：</th>
					<td>
						<input id="termIDStart2" name="termIDStart" />&nbsp;至&nbsp;
						<input id="termIDEnd2" name="termIDEnd" />
					</td>
					<th>&nbsp;或&nbsp;</th>
					<th>SN号开始：</th>
					<td>
						<input id="snStart2" name="snStart" />&nbsp;至&nbsp;
						<input id="snEnd2" name="snEnd" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="f2();">回拨</a> &nbsp;
					</td>
					
				</tr>

				<tr>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10392_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10392_cleanFun();">清空</a>	
						
					</td>
						
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_snback_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


