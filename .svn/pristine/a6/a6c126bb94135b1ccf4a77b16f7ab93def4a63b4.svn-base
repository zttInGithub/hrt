<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_10930_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findMerterminalInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'MTID',
			columns : [[{
				title :'名称',
				field :'UNNONAME',
				width : 100
			},{
				title :'机型',
				field :'MODEL',
				width : 100
			},{
				title :'sn号开头',
				field :'SNSTART',
				width : 100
			},{
				title :'sn号结尾',
				field :'SNEND',
				width : 100
			},{
				title :'出库',
				field :'DELIVERNUM',
				width : 100
			},{
				title : '日期',
				field : 'DELIVERDATE',
				width : 100
			}]], 
			toolbar:[{
				id:'btn_add',
				text:'新增',
				iconCls:'icon-add',
				handler:function(){
					sysAdmin_10930_addMerterminalInfo();
				}
			}]
		});
	});
	
	//新增
	function sysAdmin_10930_addMerterminalInfo() {
		$('<div id="sysAdmin_10930_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">新增</span>',
			width: 900,
		    height:230, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10931.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    	
		    		var inputs = document.getElementById("sysAdmin_10931_addForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    	
		    		var validator = $('#sysAdmin_10931_addForm').form('validate');
		    		
		    		var snStart = $("#snStart").val();
		    		var snEnd = $("#snEnd").val();
		    		var deliverNum = $("#deliverNum").val();
		    		if(snEnd-snStart+1!=deliverNum){
		    			$.messager.alert('提示', "输入数量与sn数量不一致");
		    			return;
		    		}
		    		
		    		$('#sysAdmin_10931_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/terminalInfo_addMerterminalInfo.action',
		    			success:function(data) {
		    				var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_10930_datagrid').datagrid('reload');
					    			$('#sysAdmin_10930_addDialog').dialog('destroy');
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
					$('#sysAdmin_10930_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//表单查询
	function sysAdmin_10930_searchFun() {
		var start = $("#10930_cdate").datebox('getValue');
    	var end = $("#10930_cdateEnd").datebox('getValue');
	   	if((!start&&end)||(!end&&start)){
	   		$.messager.alert('提示', "查询时间必须为时间段");
	   		return;
	   	}
	   	start = start.replace(/\-/gi, "/");
	   	end = end.replace(/\-/gi, "/");
	    var startTime = new Date(start).getTime();
	    var endTime = new Date(end).getTime();
	   	if ((endTime-startTime)>(1000*3600*24*30)){
	   	   	$.messager.alert('提示', "查询最长时间为30天");
	   	   	return;
	   	}
	   	if ((endTime-startTime)<0){
	   	   	$.messager.alert('提示', "起始日期需小于截止日期");
	   	   	return;
	   	}
		$('#sysAdmin_10930_datagrid').datagrid('load', serializeObject($('#sysAdmin_10930_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10930_cleanFun() {
		$('#sysAdmin_10930_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:15px;">
		<form id="sysAdmin_10930_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th>&nbsp;日期&nbsp;</th>
					<td><input name="keyConfirmDate" id="10930_cdate" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					&nbsp;至&nbsp;<input name="keyConfirmDateEnd" id="10930_cdateEnd" class="easyui-datebox" data-options="editable:false" style="width: 108px;"/>
					</td>
					
					<td colspan="10" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10930_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10930_cleanFun();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_10930_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>
