<%@ page language="java" import="java.util.*,java.sql.*,com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>  

<%
	Calendar calendar = Calendar.getInstance();
	int YY = calendar.get(Calendar.YEAR);
    int MM = calendar.get(Calendar.MONTH)+1;
    int DD = calendar.get(Calendar.DATE);
    String timeDate=YY+"年"+MM+"月"+DD+"日";
    
    Object userSession = request.getSession().getAttribute("user");
  	UserBean user = ((UserBean) userSession);
%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery-detailview.js"></script>
		<script type="text/javascript" src="${ctx}/js/sysutil.js"></script>
		
    	<script type="text/javascript"  src="${ctx}/kindeditor-4.1.10/kindeditor.js"></script>
   	    <script type="text/javascript"  src="${ctx}/kindeditor-4.1.10/kindeditor-all.js"></script>
        <script type="text/javascript"  src="${ctx}/kindeditor-4.1.10/kindeditor-all-min.js"></script>
        <script type="text/javascript"  src="${ctx}/kindeditor-4.1.10/kindeditor-min.js"></script>
        <script type="text/javascript"  src="${ctx}/kindeditor-4.1.10/lang/zh_CN.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/jquery/jquery-easyui-1.3.1/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/jquery/jquery-easyui-1.3.1/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/sysutil.css" />
		<link  type="text/css"  href="${ctx}/kindeditor-4.1.10/themes/default/default.css" rel="stylesheet" />
		<title>${title }</title>
		
	</head>

<script>
function loan_center(){
	$("#sysAdmin_center_loanRepay").css("display","none");
	var centerTab = $('#center-tab');
	centerTab.tabs('add', {
		title : "业务经营贷",
	    closable : true, 
	    href : '${ctx}/biz/credit/30010.jsp'
	});
}
</script>
	<body class="easyui-layout">
		<div data-options="region:'north', href:'North.jsp'" style="height:80px; overflow: hidden; border: 0px;"></div>  
	    <div data-options="region:'south', href:'South.jsp'" style="height:30px; overflow: hidden"></div>
	    <% if(user.getResetFlag()==1){ %>
	    	<div data-options="region:'west', title:'<span style=&quot;color:black;&quot;>工作菜单</span>',split:'true', href:'West.jsp'" style="width:200px; overflow: hidden"></div>
	    <% }else{ %>
	    	<script type="text/javascript">
	    		$.messager.alert('提示','首次登陆请先修改密码然后重新登录');
	    	</script>
	    <% } %>
	    <% if(user.getUseLvl()==3||user.getUseLvl()==4||user.getUseLvl()==5){ %>
	    	<div data-options="region:'center', title:'<span style=&quot;color:black;&quot;>登录机构：</span>北京和融通支付&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=&quot;color:black;&quot;>登陆名：${user.loginName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=&quot;color:black;&quot;>用户名称：</span> ${user.userName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=&quot;color:black;&quot;>当前日期：</span><%=timeDate %>', href:'Center.jsp'" style="overflow: hidden">
	    <%}else{ %>
	    	<div data-options="region:'center', title:'<span style=&quot;color:black;&quot;>登录机构：</span>${user.unitName }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=&quot;color:black;&quot;>登陆名：</span>${user.loginName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=&quot;color:black;&quot;>用户名称：</span> ${user.userName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=&quot;color:black;&quot;>当前日期：</span><%=timeDate %>', href:'Center.jsp'" style="overflow: hidden">
	    <%} %>
	</body>
</html>