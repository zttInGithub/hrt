<%@page import="com.hrt.frame.entity.pagebean.TreeNodeBean"%>
<%@ page language="java" import="java.util.*,java.sql.*,com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>  
<%
    Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession);
  	Object menus = request.getSession().getAttribute("menus");
	List<Map<String,Object>> nodeList = (List<Map<String,Object>>)menus;
%>
	<style type="text/css">
	#sysAdmin_center_loanRepay{
		position: fixed;
		width: 100%;
		height: 100%;
		z-index: 99999;
		display:none;
	}
	.sysAdmin_center_loanRepay{
		width: 434px;
		position: relative;
		top:-20px;
		left:410px;
	}
	.sysAdmin_center_loanRepay_close{
		width: 40px;
		height: 40px;
		position: absolute;
		top: 20px;
		right: -155px;
	}
	.sysAdmin_center_loanRepay_in{
		width: 180px;
		height: 50px;
		position: absolute;
		bottom: 10px;
		left: 210px;
	}
</style>
<script type="text/javascript">
	function Center_addTabFun(text,url) {
		var centerTab = $('#center-tab');
		if (centerTab.tabs('exists', text)) {
			centerTab.tabs('select', text);
		} else {
			if (url && url.length > 1) {
				centerTab.tabs('add', {
					title : text,
				    closable : true, 
				    href : '${ctx}/'+url
				});
			}
		}
	}
	
	function Center_addTabFun1(text,url,id) {
		var flag=false;
	<% 
		for(int i=0;i<nodeList.size();i++) {
		%>
		var mid=<%=nodeList.get(i).get("ID")%>;
		if(mid==id){
			flag=true;
		}
		<%}%>
		if(!flag){
			$.messager.alert('提示', "您没有权限操作！");
			return;
		}
		var centerTab = $('#center-tab');
		if (centerTab.tabs('exists', text)) {
			centerTab.tabs('select', text);
		} else {
			if (url && url.length > 1) {
				centerTab.tabs('add', {
					title : text,
				    closable : true, 
				    href : '${ctx}/'+url
				});
			}
		}
	}
	//待办队列查询
	$(function(){
		$.ajax({
			url: "${ctx}/sysAdmin/todo_todoCount.action",
			type:'post',
			dataType: 'json',
			success: function(data, textStatus, jqXHR) {
				$("#todoCount").html(data.todoCount);
	        }
	    }); 
	});
	//未读公告查询
	$(function(){
		$.ajax({
			url: "${ctx}/sysAdmin/notice_noticeCount.action",
			type:'post',
			dataType: 'json',
			success: function(data, textStatus, jqXHR) {
				$("#noticeCount").html(data.noticeCount);
	        }
	    }); 
	});
	$(".sysAdmin_center_loanRepay_close").click(function(){
		$("#sysAdmin_center_loanRepay").css("display","none")
		//修改为不显示的状态
		$.ajax({
			url:'${ctx}/sysAdmin/user_updateBannerToClose.action',
			type:'post',
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				if (data.success) {
					$("#sysAdmin_center_loanRepay").css("display","none")
				} 
			}
		});
	})
	$(".sysAdmin_center_loanRepay_in").click(function(e){
		e.preventDefault();
		loan_center();
	})
	
	//贷款弹窗
$(function(){
	$.ajax({
		url:'${ctx}/sysAdmin/user_queryRoleLvlByUser.action',
		type:'post',
		dataType:'json',
		success:function(data, textStatus, jqXHR) {
			if (data.success) {
				$("#sysAdmin_center_loanRepay").css("display","block")
			} 
		}
	});
})

</script>

<style type="text/css">
	.center_task{
		color: #FFF;
	}
	.task1{
		margin: 80px 0px 0px 130px;
		float: left;
		cursor:pointer;
	}
	.task1_number{
		text-align: center; 
		margin-top: -85px; 
		margin-left: 30px; 
		font-size: 40px;
		font-weight: bold;
	}
	.task1_content{
		text-align: right; 
		margin-right: 10px;
		margin-top: 10px;
		font-size: 14px;
	}
	.task2{
		margin: 80px 0px 0px 50px; 
		float: left;
		cursor:pointer;
	}
	.task3{
		margin: 80px 0px 0px 600px;
		float: left;
		cursor:pointer;
	}
	.task_new_img_info{
		clear: both;
		margin: 250px 0px 0px 200px;
	}
	.task_new_img_info>img{
		margin-left: 200px;
	}
	.task_new_img_info_p1{
		color: #098ce1;
		font-size: 30px;
		margin-left: 100px;
	}
	.task_new_img_info_p2,.task_new_img_info_p3{
		color: #098ce1;
		font-size: 18px;
		margin-left: 100px;
	}
	.task_new_img_info_p3{
		text-indent: 36px;
	}
	.task2_content{
		text-align: right; 
		margin-right: 10px;
		margin-top: -25px;
		font-size: 14px;
	}
	.task3_content{
		text-align: right; 
		margin-right: 10px;
		margin-top: -25px;
		font-size: 14px;
	}
</style>

<div id="center-tab" class="easyui-tabs" 
	data-options="
		fit : true,
		border : false, 
		tools:[{
			iconCls : 'icon-reload',
			handler : function(){
				$('#center-tab').tabs('getSelected').panel('refresh');
			}				
		},{
			iconCls : 'icon-cancel',
			handler : function(){
				var tab = $('#center-tab').tabs('getSelected');
				var index = $('#center-tab').tabs('getTabIndex', tab);
				if (tab.panel('options').closable) {
					$('#center-tab').tabs('close', index);
				} 
			}	
		}]">
		
	<div title="工作台" class="center_task">
		<% if(user.getResetFlag()==1){ %>
			<div class="task1" onclick="Center_addTabFun1('待办队列','/frame/sysadmin/todo/00120.jsp','10');">
				<img alt="" src="${ctx }/images/test1.png" width="168px" height="110px">
				<div class="task1_number" id="todoCount">0</div>
				<div class="task1_content">待办队列</div>
			</div>
			<div class="task2" onclick="Center_addTabFun1('预警事项','','11');">
				<img alt="" src="${ctx }/images/test2.png" width="168px" height="110px">
				<div class="task1_number">0</div>
				<div class="task1_content">预警事项</div>
			</div>
			<div class="task2" onclick="Center_addTabFun1('公告消息','/frame/sysadmin/notice/00113.jsp','17');">
				<img alt="" src="${ctx }/images/test3.png" width="168px" height="110px">
				<div class="task1_number" id="noticeCount">0</div>
				<div class="task1_content">公告消息</div>
			</div>
			<div class="task2" onclick="Center_addTabFun1('下载专区','/frame/sysadmin/download/00140.jsp','13');">
				<img alt="" src="${ctx }/images/test4.png" width="168px" height="110px">
				<div class="task3_content">下载专区</div>
			</div>
		<% }else{ %>
			<div class="task3" onclick="Center_addTabFun('修改密码','/frame/sysadmin/user/00243.jsp');">
				<img alt="" src="${ctx }/images/test4.png" width="168px" height="110px">
				<div class="task2_content">修改密码</div>
			</div>
		<% } %>
		<div class="task_new_img_info">
			<img src="${ctx }/images/download_zyb_ewm.png">
			<p class="task_new_img_info_p1">高效展业，尽在展业宝APP，请及时下载！</p>
			<p class="task_new_img_info_p2">注：1.登录时，您可以选择“机构号登录”，机构号和密码，同本平台</p>
			<p class="task_new_img_info_p3">2.维护手机号后，您也可以通过手机号和密码登录。</p>
		</div>

		<div id="sysAdmin_center_loanRepay">
   <div class="sysAdmin_center_loanRepay">
   	<img width="600" src="${ctx}/images/sysAdmin_center_loanRepay.png">
   	<img class="sysAdmin_center_loanRepay_close" src="${ctx}/images/sysAdmin_center_loanRepay_rem.png">
    <img class="sysAdmin_center_loanRepay_in" src="${ctx}/images/sysAdmin_center_loanRepay_btn.png">
   </div>     
  </div>
	</div>
</div>