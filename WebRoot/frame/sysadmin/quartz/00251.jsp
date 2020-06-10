<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<iframe name="cron" style="padding:10px;" id="cron" width="610" height="230" frameborder="0" scrolling="no" src="${ctx }/frame/sysadmin/quartz/iframe.jsp"></iframe>

<form id="sysAdmin_quartz_addForm" method="post">
	<table style="padding-top: 5px; padding-left: 20px;">
		<tr>
            <td nowrap>
				任务名称：
            </td>
            <td >
                <input type="text" name ="p_triggerName" id="p_triggerName"  class="easyui-validatebox" data-options="required:true" style="width:200px;">
            </td>
        </tr>
		<tr>
			<td style="width: 135px;">
				请选择需要执行的任务：
			</td>
			<td>
				<select name="p_jobClass" style="width: 100px;">
	            	<option value="quartz.BatchJob1">BatchJob1</option>
					<option value="quartz.BatchJob2">BatchJob2</option>	
					<option value="quartz.BatchJob3">BatchJob3</option>
					<option value="quartz.BatchJob4">BatchJob4</option>
					<option value="quartz.BatchJob5">BatchJob5</option>
	            </select>
			</td>
		</tr>
	</table>
</form>
