<%@ page language="java" import="java.util.*,com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">

	function exportExcel(type){
	var a=<%=((UserBean)request.getSession().getAttribute("user")).getUseLvl()%>
	
	if(a==3){
				var name=<%=((UserBean)request.getSession().getAttribute("user")).getLoginName()%>;
				$('#xmh_mid_20130').val(name);
			    $("#check_dealdatailfrom").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealDetail_listCheckUnitDealDetailExcel.action'
			    });
	}else{
		var mm=$('#xmh_mid_20130').val();
		if(mm!="" && mm!=null){
			$("#check_dealdatailfrom").form('submit',{
			    		url:'${ctx}/sysAdmin/checkUnitDealDetail_listCheckUnitDealDetailExcel.action'
			    });
		}else{
			$.messager.alert('提示', "商户号不能为空！");
		}
	
	}
	

	}
	//查询提交
	function check_search(type){
	var a=<%=((UserBean)request.getSession().getAttribute("user")).getUseLvl()%>
	var rnames=$('#xmh_mid_20130').val();
	var txnDay = $('#xmh_txnDay_20130').datebox('getValue');
    var txnDay1=$('#xmh_txnDay1_20130').datebox('getValue');
    var txnDay_ = txnDay;
    var txnDay1_=txnDay1;
      var start=new Date(txnDay_.replace("-", "/").replace("-", "/"));  
      var end=new Date(txnDay1_.replace("-", "/").replace("-", "/")); 
  	if(a==3){
			if(txnDay==null||txnDay==""){
	          $.messager.alert('提示', "请选择起始日期 ！");
	       }
	       else if(txnDay1==null||txnDay1==""){
	        $.messager.alert('提示', "请选择结束日期 ！");
	       }
		 else if(((end-start)/(24*60*60*1000))>7){
		 	$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    7   天！");
		    }
		  else{
			  $('#check_dealdatailfrom').form('submit',{
		    		url:'${ctx}/sysAdmin/checkUnitDealData_queryCheckUnitDate.action',
					success:function(data) {
						var result = $.parseJSON(data);
		    			if (result.sessionExpire) {
		    				window.location.href = getProjectLocation();
			    		} else {
			    			if (result.success) {
			    				$('#sysAdmin_check_unitdealdetail').datagrid('load', serializeObject($('#check_dealdatailfrom')));
			    			} else {
				    			$.messager.alert('提示', "日期输入有误，请从 "+result.msg+" 起，开始查询");
				    		}
				    	}
		    		 }
		    	});
		    }
	}else{
		if(rnames==null||rnames==""){
      		$.messager.alert('提示', "商户号不能为空！");
       }
       else if(txnDay==null||txnDay==""){
        $.messager.alert('提示', "请选择起始日期 ！");
       }
       else if(txnDay1==null||txnDay1==""){
        $.messager.alert('提示', "请选择结束日期 ！");
       }
	 else if(((end-start)/(24*60*60*1000))>7){
	 	$.messager.alert('提示', "开始日期  和   截止日期  之差不能超过    7   天！");
	    }
	  else{
		  $('#check_dealdatailfrom').form('submit',{
	    		url:'${ctx}/sysAdmin/checkUnitDealData_queryCheckUnitDate.action',
				success:function(data) {
					var result = $.parseJSON(data);
	    			if (result.sessionExpire) {
	    				window.location.href = getProjectLocation();
		    		} else {
		    			if (result.success) {
		    				$('#sysAdmin_check_unitdealdetail').datagrid('load', serializeObject($('#check_dealdatailfrom')));
			    		} else {
			    			$.messager.alert('提示', "日期输入有误，请从 "+result.msg+" 起，开始查询");
			    		}
			    	}
	    		 }
	    	});
	    }
	}
	    
	}
		//清除表单内容
	function check_close() {
		$('#check_dealdatailfrom input').val('');
	}
	
	
	//验证
	function check_amount1(){
		var xmh_profit1=$("#xmh_profit1").val();
	     if(xmh_profit1!=null&&!xmh_profit1.match(/^\d+(\.\d+)?$/)&&xmh_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！");
	         $("#xmh_profit1").val('');
	    }
	}
	function check_amount(){
	var xmh_profit=$("#xmh_profit").val();
	     if(xmh_profit!=null&&!xmh_profit.match(/^\d+(\.\d+)?$/)&&xmh_profit!=""){
	      	$.messager.alert('提示', "您输入金额格式有误！"+xmh_profit);
	         $("#xmh_profit").val('');
	    }
	}

	//初始化datagrid
	$(function() {
		$('#sysAdmin_check_unitdealdetail').datagrid({
			url :'${ctx}/sysAdmin/checkUnitDealDetail_listCheckUnitDealDetail.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'MAINTAINDATE',
			sortOrder: 'asc',
			idField : 'BDID',
			columns : [[{
				title : '对账明细编号',
				field : 'BDID',
				width : 10,
				hidden : true
			},{
				title :'商户编号',
				field :'MID',
				width : 140
			},{
				title :'终端号',
				field :'TID',
				width : 100
			},{
				title :'商户名称',
				field :'RNAME',
				width : 90
			},{
				title :'卡号',
				field :'CARDPAN',
				width : 100,
				hidden : true
			}
//			,{
//				title :'卡别',
//				field :'CBRAND',
//				width : 90,
//				formatter:function(value,row,index){
//					if(value=='1'){
//						return '借记卡';
//					}else{
//						return '贷记卡';
//					}
//				}
//			}
			,{
				title :'交易日期',
				field :'TXNDAY',
				width : 90,
				hidden : true
			},{
				title :'交易时间',
				field :'TXNDATE',
				width : 90,
				hidden : true
			},
//			{
//				title :'交易类型',
//				field :'TXNTYPE',
//				width : 80,
//				formatter:function(value,row,index){
//					if(value=='0210'){
//						return '消费';
//					}else if(value=='0230'){
//						return '退货';
//					}else{
//						return "冲正";
//					}
//				}
//			},
                 {
				title :'授权号',
				field :'AUTHCODE',
				width : 90,
				hidden : true
			},{
				title :'系统参考号',
				field :'RRN',
				width : 140,
				hidden : true
			},{
				title :'流水号',
				field :'STAN',
				width : 90,
				hidden : true
			},{
				title :'交易金额',
				field :'TXNAMOUNT',
				width : 90
			},{
				title :'手续费',
				field :'MDA',
				width : 90
			},{
				title :'交易净额',
				field :'MNAMT',
				width : 90
			},{
				title :'店名',
				field :'INSTALLEDNAME',
				width : 90
			}]],
			view: detailview,
			detailFormatter:function(index,row){
				return '<div><table class="ddvv"></table></div>';
			},
			onExpandRow: function(index,row){
				var ddv = $(this).datagrid('getRowDetail',index).find('table.ddvv');
				ddv.datagrid({
					url:'${ctx}/sysAdmin/checkUnitDealDetail_findCheckUnitDealDetail.action?tid='+row.TID+'&mid='+row.MID+'&firsix='+$('#firo').val()+'&lastfour='+$('#laso').val()+'&txnDate='+$('#xmh_txnDay_20130').datebox('getValue')+'&txnDate1='+$('#xmh_txnDay1_20130').datebox('getValue'),
					singleSelect:true,
					rownumbers:true,
					loadMsg:'',
					height:'auto',
					columns:[[
						{title :'卡号',field :'CARDPAN',width:120},
						{title :'卡别',field :'CBRAND',width:100,
						formatter:function(value,row,index){if(value=='1'){	return '借记卡';	}else if(value=='2'){return '贷记卡';}else{return '未知';}}}, 
						{field:'TXNDAY',title:'交易日期',width:100},
						{field:'TXNDATE',title:'交易时间',width:100},
						{title :'授权号',field :'AUTHCODE',width:100},
						{title :'系统参考号',field :'RRN',width:100},
						{title :'流水号',field :'STAN',width:100},
						{title :'交易金额',field :'TXNAMOUNT',width:100},
						{title :'手续费',field :'MDA',width:100},
						{title :'交易净额',field :'MNAMT',width:100}
					]],
					onResize:function(){
						$('#sysAdmin_check_unitdealdetail').datagrid('fixDetailRowHeight',index);
					},
					onLoadSuccess:function(){
						setTimeout(function(){
							$('#sysAdmin_check_unitdealdetail').datagrid('fixDetailRowHeight',index);
						},0);
					}
				});
				$('#sysAdmin_check_unitdealdetail').datagrid('fixDetailRowHeight',index);
				$('.datagrid-view2').children('div.datagrid-body').css('position','relative');
			},
			toolbar:[{
					text:'导出Excel',
					iconCls:'icon-query-export',
					handler:function(){
					exportExcel();
					                  }
				}]			
		});
	});
 /**$('#xmh_txnDay_20130').datetimebox
({ 
          showSeconds:true
}); 
 $('#xmh_txnDay1_20130').datetimebox
({ 
          showSeconds:true
});**/
    function changeclassone(){ 
       $("#buttontwo").hide();
       $("#buttonone").show();
       $(".spinner:first").show();
       $("a.datebox-ok:first").click(function(){
	   var ii=$('#xmh_txnDay_20130').datebox('getValue');
	   var mmi=$("#00000").val(); 
	   $('#xmh_txnDay_20130').datebox('setValue',mmi);               
	   });
}
	function changeclass(){
      $("#buttonone").hide();
	  $(".spinner:first").hide();
      $("#buttontwo").show();
	  $("a.datebox-ok:first").click(function(){
	  var ii=$('#xmh_txnDay_20130').datebox('getValue');
	  $("#00000").val(ii); 
	  var tt=ii.substring('0','10');
	  $('#xmh_txnDay_20130').datebox('setValue',tt);     
	  }); 
		}
	
    function changeclassthree(){
       $("#buttonfour").hide();
       $("#buttonthree").show();
      $(".spinner:eq(1)").show();
     $("a.datebox-ok:eq(1)").click(function(){	
	var ii=$('#xmh_txnDay1_20130').datebox('getValue');
	var mmi=$("#11111").val(); 
	$('#xmh_txnDay1_20130').datebox('setValue',mmi);               
	});
}
	function changeclasstwo(){
       $("#buttonthree").hide();
   	$("span.spinner:eq(1)").hide();
    $("#buttonfour").show();
	$("a.datebox-ok:eq(1)").click(function(){
	var ii=$('#xmh_txnDay1_20130').datebox('getValue');
	$("#11111").val(ii); 
	var tt=ii.substring('0','10');
	$('#xmh_txnDay1_20130').datebox('setValue',tt);     
	}); 
		}		

	$("a.datebox-ok:eq(0)").parent().before("<div><button id='buttontwo'  onclick='changeclassone();' style='right:0px;display:none;'>开启时间</button></div>");
	$("a.datebox-ok:eq(0)").parent().before("<div><button id='buttonone' onclick='changeclass();' style='right:0px;'>关闭时间</button></div>");
	$("a.datebox-ok:eq(1)").parent().before("<div><button id='buttonfour'  onclick='changeclassthree();' style='right:0px;display:none;'>开启时间</button></div>");
	$("a.datebox-ok:eq(1)").parent().before("<div><button id='buttonthree' onclick='changeclasstwo();' style='right:0px;'>关闭时间</button></div>");
	changeclass();
	changeclasstwo();
	//初始化datagrid
	$(function() {
			$('#unno20130').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodes.action',
			//url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	});
</script>

<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:10px;">
		<form id="check_dealdatailfrom" style="padding-left:8%">
			<table class="tableForm" >
				<tr>
					<th>机构：</th>
					<td><select name="unno" id="unno20130" class="easyui-combogrid" style="width:200px;"></select></td>
					<th>商户号：</th>
					<td><input id="xmh_mid_20130" name="mid" style="width: 316px;" /></td>
				</tr>
				<tr>
				<th>交易卡号：</th>
					<td><input id="firo" name="firsix"   style="width: 150px;"/>&nbsp;-&nbsp;
						<input id="laso" name="lastfour"   style="width: 150px;"/>
					</td>					
					<th>交易日期：</th>
					<td><input id="xmh_txnDay_20130" name="txnDay" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;
						<input id="xmh_txnDay1_20130" name="txnDay1" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>
					</td>				
					<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_search();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_close();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	<input id='00000' style="display:none;"></input>
	<input id='11111' style="display:none;"></input>
	<div data-options="region:'center', border:false" style="overflow: hidden;">  
<form id="exportform_xmh"></form>
<table id="sysAdmin_check_unitdealdetail" style="height:170px; overflow: hidden;"></table>
   </div>
	</div>
