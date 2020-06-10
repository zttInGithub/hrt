<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- MPOS活动分润模板维护 -->
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#profitmicro_20288_datagrid').datagrid({
			url :'${ctx}/sysAdmin/CheckUnitProfitMicro_queryMposTemplate.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			columns : [[{
				title : '编号',
				field : 'APTID',
				hidden: true,
				width : 100
			},{
				title : '模版名称',
				field : 'TEMPNAME',
				width : 100
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					return '<img src="${ctx}/images/frame_pencil.png" title="修改" style="cursor:pointer;" onclick="Profitmicro_queryFun20288('+index+')"/>';
				}
			}]],toolbar:[{
				id:'btn_add',
				text:'添加MPOS活动分润模版',
				iconCls:'icon-add',
				handler:function(){
					Addprofitmicro_20288();
				}
			}]
		});
	});
	
	//表单查询
	function sysAdmin_profitmicro_searchFun20288() {
		$('#profitmicro_20288_datagrid').datagrid('load', serializeObject($('#profitmicro_20288_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_profitmicro_cleanFun20288() {
		$('#profitmicro_20288_searchForm input').val('');
	}

	//修改模版
	function Profitmicro_queryFun20288(index){
		var rows = $('#profitmicro_20288_datagrid').datagrid('getRows');
		var row = rows[index];
		$('<div id="sysAdmin_profitmicroUpdate20288"/>').dialog({
			title: '<span style="color:#157FCC;">MPOS活动分润模版修改</span>',
			width: 950,
		    height: 600,
		    resizable:true,
    		maximizable:true,
		    closed: false,
		    href: '${ctx}/biz/check/20288_2.jsp?tempname='+encodeURIComponent(encodeURIComponent(row.TEMPNAME)),
		    modal: true, 
			onClose:function() {
		    	$('#Profitmicro').form('load', row);
			},buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var tr_list = $('#profitmicroNext_20288_2').find("tr");
		    		var tiNum = parseInt(tr_list.length/9);
					var cost_data = [];
	    			for(var ti = 1; ti < tiNum+1; ti++){
	    				var inputs = $("#tr_20288_2_"+ti+" input");
	    				var o = serializeTrObj(inputs);
	    				cost_data.push(o);
	    				if('P' == o.matainType){
			    			$.messager.alert('Warning','这是手机端模板');
							return;
			    		}
	    				//var jugeMataintype = document.getElementById("mataintype_20288_2_"+ti+"").value;
	    				//jugeMataintypes.push(jugeMataintype)
	    			}
    				$("#tempname20288_2").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
    				var validator = $('#Profitmicro20288_2').form('validate');		    		
	    			if(validator){
		    			$.messager.progress();
		    		}
					$('#ProfitmicroNext20288_2').form('submit', {
						url:'${ctx}/sysAdmin/CheckUnitProfitMicro_updateProfitmicro20288.action',
						success:function(data) {
							$.messager.progress('close'); 
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
		    					window.location.href = getProjectLocation();
			    			} else {
			    				if (result.success) {
			    					$('#profitmicro_20288_datagrid').datagrid('reload');
				    				$('#sysAdmin_profitmicroUpdate20288').dialog('destroy');
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
					$('#sysAdmin_profitmicroUpdate20288').dialog('destroy');
				}
			}],onClose:function() {
			$(this).dialog('destroy');
			}		
		});
	}
//添加mpos活动分润模版
	function Addprofitmicro_20288(){
		$('<div id="profitmicro_20288_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加MPOS活动分润模版</span>',
			width: 900,
		    height:500, 
		    closed: false,
		    href: '${ctx}/biz/check/20288_1.jsp',
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		var tr_list = $('#20288_1_table').find("tr");
		    		var tiNum = parseInt(tr_list.length/10);
		    		if(tiNum==0){
		    			$.messager.alert('提示',"请添加MPos活动");
		    		}else{
		    			var cost_data = [];
		    			for(var ti = 1; ti < tiNum+1; ti++){
		    				var inputs = $("#tr_20288_1_"+ti+" input");
		    				var o = serializeTrObj(inputs);
		    				cost_data.push(o);
		    			}
	    				$("#Tempname_20288_1").val(JSON.stringify(cost_data).replace(/\"/g,"'"));
	    				var validator = $('#profitmicro_20288_addForm').form('validate');		    		
		    			if(validator){
			    			$.messager.progress();
			    		}
		    			$('#profitmicro_20288_addForm').form('submit',{
			    			url:'${ctx}/sysAdmin/CheckUnitProfitMicro_addMposTempla.action',
		    				success:function(data) {
		    					$.messager.progress('close'); 
		    					var result = $.parseJSON(data);
			    				if (result.sessionExpire) {
			    					window.location.href = getProjectLocation();
				    			} else {
				    				if (result.success) {
				    					$('#profitmicro_20288_datagrid').datagrid('reload');
					    				$('#profitmicro_20288_addDialog').dialog('destroy');
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
		    	}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#profitmicro_20288_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:11px;">
		<form id="profitmicro_20288_searchForm" style="padding-left:25%" method="post">
			<table class="tableForm" >
				<tr>				    
					<th>模版名称：</th>
					<td><input name="tempName" style="width: 316px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_profitmicro_searchFun20288();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_profitmicro_cleanFun20288();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="profitmicro_20288_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>

