<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 添加返现模板 -->
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript">
		//初始化datagrid
		$(function() {
				$('#unno_add').combogrid({
				url : '${ctx}/sysAdmin/cashbackTemplate_getChildrenUnit.action',
				idField:'UNNO',
				textField:'UN_NAME',
				mode:'remote',
				fitColumns:true,
				columns:[[
					{field:'UN_NAME',title:'机构名称',width:150}			
				]]
			});
		});
	$.extend($.fn.validatebox.defaults.rules,{
		spaceValidator:{
			validator : function(value) {
	            return /^[^\s]+$/i.test(value); 
	        }, 
	        message : '禁止输入的内容中出现空格' 
		}
	});

	$.extend($.fn.validatebox.defaults.rules,{
		rateValidator:{
			validator : function(value) {
	            return /^(0(\.\d{2})?|1(\.0{2})?)$/i.test(value); 
	        }, 
	        message : '比例不合规，请输入0至1间数值【且两位小数】！' 
		}
	});
	
	var allRebateTypecashbackTemplate_add = new Array();
	function sysAdmin_cashbackTemplate_add_addRebate(){
		
		$('<div id="sysAdmin_cashbackTemplate_add_addDialog"/>').dialog({
			title: '<span style="color:#157FCC;">添加返现活动类型</span>',
			width: 500,
		    height:300,
		    closed: false,
		    href: '${ctx}/biz/bill/agent/cashback/CashbackTemplate_add_1.jsp',  
		    modal: true,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		//debugger;
		    		sysAdmin_cashbackTemplate_add_edit();
		    		$('#sysAdmin_cashbackTemplate_add_addDialog').dialog('destroy');
		    	}			    
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_cashbackTemplate_add_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}
	var j = 1;
	function sysAdmin_cashbackTemplate_add_edit(){
		debugger;
		var tr_list = $("#cashbackTemplate_add_table").children("tr");
		var id = tr_list.length;
		var rebateType = $('#rebateType_cashbackTemplate_add_1').combobox('getValue');
		
		var cashbacktype = $('#cashbacktype_add_1').combobox('getValue');
		var cashbacktype_1 = "";
		if(cashbacktype==1){
			cashbacktype_1="刷卡";
		}else if(cashbacktype==2){
			cashbacktype_1="押金";
		}else{
			cashbacktype_1="花呗分期";
		}
		var cashbackratio = $('#cashbackratio_add_1').val();
		var cashbackrationext = $('#cashbackrationext_add_1').val();
		
		
		var validator = $('#cashbackTemplate_add_1_from').form('validate');
		if(!validator){
			$.messager.alert('提示', "填写模板信息有误");
			return ;
		}
		//debugger;
		if(allRebateTypecashbackTemplate_add.indexOf(rebateType+":"+cashbacktype)!=-1){
			$.messager.alert('提示', "已添加该活动的返现类型，请勿重复添加");
			return ;
		}
		allRebateTypecashbackTemplate_add[allRebateTypecashbackTemplate_add.length] = rebateType+":"+cashbacktype;
		
		var html = 
			 '<tr id="tr_cashbackTemplate_2_'+j+'" >'
			+'<td><input type="text" name="rebatetype" style="width:100px;" readonly="true" " value="'+rebateType+'"  /></td>'
			+'<td><input type="text" name="cashbacktype" style="width:100px;" readonly="true" " value="'+cashbacktype_1+'"  /></td>'
			+'<td><input type="text" name="cashbackratio" style="width:100px;" readonly="true" class="easyui-validatebox" data-options="required:true,validType:"rateValidator" " value="'+cashbackratio+'"  /></td>'
			+'<td><input type="text" name="cashbackrationext" style="width:100px;" readonly="true" class="easyui-validatebox" data-options="required:true,validType:"rateValidator" "  value="'+cashbackrationext+'"  /></td>'
			+'</tr>';
			j++;
			$("#nbody_add").append(html);
	}
	
</script>
<form id="cashbackTemplate_add_addForm" method="post" enctype="multipart/form-data" >
	<fieldset>
	  <table class="table">
		 <tr>
		   	<th>机构编号：</th>
		   	<td>
				<select name="unno" id="unno_add" class="easyui-combogrid" style="width:205px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'"><font color="red">&nbsp;*</font></select>
			</td>
		   	<!-- <td><input type="text" id="unno_add" name="unno" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:'spaceValidator'" /><font color="red">&nbsp;*</font></td> -->
		 </tr>
	  </table>
	</fieldset>
	<input type="hidden" name="unnoRebatetype" id="unnoRebatetype" />
</form> 
	<fieldset>
		<legend>活动</legend>
	  <table class="table">
		<tbody id="cashbackTemplate_add_table">
			<tr id="cashbackTemplate_addTRebateType">
				<td style="text-align: center;" colspan="9">
					<a id="" onclick="sysAdmin_cashbackTemplate_add_addRebate()" class="l-btn" href="javascript:void(0)"><span class="l-btn-left"><span class="l-btn-text icon-add" style="padding-left: 20px;">添加活动</span></span></a>
				</td>
			</tr>
		</tbody>
	  </table>
	  <table class="table1" id="table1_input">
		<thead>
			<tr>
				<th style="text-align: center;">活动编号</th>
				<th style="text-align: center;">返现类型</th>
				<th style="text-align: center;">下级代理可分比例-本月</th>
				<th style="text-align: center;">下级代理可分比例-下月</th>
				<!-- <th style="text-align: center; width: 100px;">操作</th> -->
			</tr>
		</thead>
		<tbody id="nbody_add">
		</tbody>
	</table>
	</fieldset> 
