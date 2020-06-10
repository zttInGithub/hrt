<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

    <!-- 结算成本变更页面 -->
<script type="text/javascript">
	//成本模板下载
	function exportCBXls(){
		$('#cbmo_from').form('submit',{
			url:'${ctx}/sysAdmin/agentunit_exportUnnoCost1.action'
		});
	}
	
	$.extend($.fn.validatebox.defaults.rules,{
		imgValidator:{
			validator : function(value) { 
				return util(value); 
	      }, 
	      message : '分润照片格式不正确,请打包成zip压缩包进行上传' 
		}
	});
	
	function util(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".zip"){
				return false;
			}else{ 
				return true;
			}
		}
	}
	
	$.extend($.fn.validatebox.defaults.rules,{
		imgValidate:{
			validator : function(value) { 
				return util1(value); 
	      }, 
	      message : '请选择.xls文件进行上传' 
		}
	});
	
	function util1(value){
		var ename= value.toLowerCase().substring(value.lastIndexOf("."));
		if(ename ==""){
			return false;
		}else{
			if(ename !=".xls"){
				return false;
			}else{ 
				return true;
			}
		}
	}
</script>

<style type="text/css">
	.hrt-label_pl15{
		padding-left:15px;
		color: red;
	}
</style>

<form id="cbmo_from">
	<fieldset>
		<legend>模板下载</legend>
		<table class="table">
			<tr>
				<th>结算成本变更模板：</th>
				<td>
				&nbsp;&nbsp;<input type="button" name="agentShortNm" value="下载" onclick="exportCBXls()"/><font color="red">&nbsp;&nbsp;&nbsp;请先下载模板在进行成本变更 *</font>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
<form id="sysAdmin_agentunit_bg1Form" method="post" enctype="multipart/form-data">
	<!-- 创建隐藏域 用于传递文件名 --> 
	<input type="hidden" name="zipUpLoad" id="zipUpLoad">
	<input type="hidden" name="cbUpLoad" id="cbUpLoad">
	<fieldset>
		<legend>照片信息</legend>
		<table class="table">
			<tr>
				<th>分润照片：</th>
   				<td>
   				&nbsp;<input type="file" name="upload" id="upLoadZip" class="easyui-validatebox" data-options="required:true,validType:'imgValidator'" />
				</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>成本导入</legend>
		<table class="table">
			<tr>
				<th>分润结算成本：</th>
   				<td>
   				&nbsp;<input type="file" name="upload1" id="upLoadCB" class="easyui-validatebox" data-options="required:true,validType:'imgValidate'" />
				</td>
			</tr>
		</table>
	</fieldset>
	
</form>  
