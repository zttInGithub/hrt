<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	$(function(){
		//归属地
		$('#provinceCode').combogrid({
			url : '${ctx}/sysAdmin/unit_searchProvince.action',
			idField:'PROVINCE_CODE',
			textField:'PROVINCE_NAME',
			fitColumns:true,
			columns:[[ 
				{field:'PROVINCE_NAME',title:'省市',width:150},
				{field:'PROVINCE_CODE',title:'id',width:150,hidden:true}
			]]
		});
		
		//根据机构查询省市
		$('#_parentId').combotree({
			onSelect:function(node){
				$.ajax({
					url:'${ctx}/sysAdmin/unit_searchUnitProvince.action',
					data: {"unitUnno":node.id},
		            success: function(data, textStatus){
		            	var result = $.parseJSON(data);
		            	if(result.unNo != '110000'){
		            		$('#provinceCode').combogrid('grid').datagrid('selectRecord',result.provinceCode);
		            	}
		            },
		            error:function(XMLHttpRequest, textStatus, errorThrown){
		            	$.messager.alert('提示', '操作记录出错！');
					    $('#sysAdmin_unit_addDialog').dialog('destroy');
		            }
				});
			}
		});
	});
	
	$.extend($.fn.validatebox.defaults.rules,{
		unitCode:{
			validator : function(value) { 
	            return /^\d{2,3}$/i.test(value); 
	        }, 
	        message : '分公司请输入2位数字,其他选项请输入3位数字' 
		}
	});
</script>

<form id="sysAdmin_unit_addForm" method="post">  
    <table class="table">
    	<tr>
    		<th>上级机构：</th>
    		<td>
    			<select class="easyui-combotree" name="_parentId" id="_parentId" style="width:205px;" 
    				data-options="
	    				lines:true,
	    				required:true,
	    				url:'${ctx}/sysAdmin/unit_listTreeUnits.action'
    				">
    			</select><font color="red">&nbsp;*</font>
    		</td>
    		<th>省市：</th>
    		<td>
    			<select id="provinceCode" name="provinceCode" class="easyui-combogrid" data-options="required:true,editable:false" style="width:205px;"></select><font color="red">&nbsp;*</font>
    		</td>
    	</tr>
    	<tr>
    		<th>机构级别：</th>
    		<td>
    			<select name="unLvl" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false"  style="width:205px;">
    				<option value="0">总公司</option>
    				<option value="1">分公司</option>
    				<option value="2">作业中心/代理机构</option>
    				<option value="3">二级作业中心/二级代理机构</option>
    			</select><font color="red">&nbsp;*</font>
    		</td>
    	
    		<th>机构名称：</th>
    		<td ><input type="text" name="unitName" style="width:200px;" class="easyui-validatebox" data-options="required:true" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<th>简码：</th>
    		<td>
    			<input type="text" name="unitCode" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'unitCode'" maxlength="3" /><font color="red">&nbsp;*</font>
    		</td>
		</tr>
    </table>
</form>  


