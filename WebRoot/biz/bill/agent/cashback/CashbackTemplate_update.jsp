<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String  unNo=request.getParameter("unno"); 
	String  unno = java.net.URLDecoder.decode(unNo,"UTF-8").trim();
	String  unnoNow = java.net.URLDecoder.decode(unNo,"UTF-8").trim();
 %>
<script src="js/uploadPreview.min.js" type="text/javascript"></script>
<script type="text/javascript"> 

/* 查询模板展示 */
$(function(){
	//debugger;
	//var a= unno;
		var j=1;
		var unno_update=$('#unno_update').val();
		$.ajax({
			url:'${ctx}/sysAdmin/cashbackTemplate_queryCashbackTemplateForUnno.action',
			dataType:"json",
			type:"post",
			data:{unno:unno_update},
			success:function(data) {
				//debugger;
				var json=eval(data);
				for(var i=0;i<json.length;i++){
					var html = 
						 '<tr id="tr_cashbackTemplate_2_'+j+'" >'
						+'<td><input type="text" name="rebatetype" style="width:100px;" readonly="true" " value="'+json[i].REBATETYPE+'"  /></td>'
						+'<td><input type="text" name="cashbacktype" style="width:100px;" readonly="true" " value="'+json[i].CASHBACKTYPE+'"  /></td>'
						+'<td><input type="text" name="cashbackratio" style="width:100px;" readonly="true" " value="'+json[i].CASHBACKRATIO+'"  /></td>'
						+'<td><input type="text" name="cashbackrationext" style="width:100px;" class="easyui-validatebox" data-options="required:true,validType:"rateValidator"" value="'+json[i].CASHBACKRATIONEXT+'"  /></td>'
						+'</tr>';
						j++;
						$("#nbody").append(html);
				}
    		}
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
	
</script>
<div data-options="region:'north',border:false"
	style="height: 500px; padding-top: 2px;">
	<div id = "20288_2_tabs" class="easyui-tabs" data-options = "fit:true,border:false">
		<form id="cashbackTemplate_update_2" style="padding-left:1%;padding-top:1%" method="post">
			<fieldset>
				<table class="table1">
					<tr>
						<th>机构编号：</th>
						<td><input type="text" name="unno"
							id="unno_update" style="width: 100px;"
							class="easyui-validatebox"
							data-options="required:true,validType:'spaceValidator'"
							value="<%=unnoNow%>" readonly="true" /><font color="red">&nbsp;*</font></td>
					</tr>
				</table>
			</fieldset>
			<input type="hidden" id="unnoRebatetype_2" name="unnoRebatetype" />
		</form>
			<fieldset>
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
					<tbody id="nbody">
					</tbody>
				</table>
			</fieldset>
	</div>
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="aas" style="overflow: hidden;"></table>
    </div> 
</div>
