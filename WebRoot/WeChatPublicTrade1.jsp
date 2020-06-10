<%@ page language="java" import="java.util.*,com.hrt.frame.entity.pagebean.UserBean" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	UserBean userBean = (UserBean) session.getAttribute("user");
	String openid = (String) session.getAttribute("openid");
	Calendar calendar = Calendar.getInstance();
	int YY = calendar.get(Calendar.YEAR);
    int MM = calendar.get(Calendar.MONTH)+1;
    int DD = calendar.get(Calendar.DATE);
    String mms ="";
    String dds ="";
    if(MM<10){
    	mms="0"+MM;
    }else{
    	mms=MM+"";
    }
    if(DD<10){
    	dds="0"+DD;
    }else{
    	dds=DD+"";
    }
    String timeDate=YY+"-"+mms+"-"+dds;
%>
<script type="text/javascript" src="${ctx}/jquery/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
 <link rel="stylesheet" type="text/css" href="${ctx}/css/weui.min.css" /> 
<script type="text/javascript" src="${ctx}/js/AjaxPager.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/sysutil.css" />
<link  type="text/css"  href="${ctx}/kindeditor-4.1.10/themes/default/default.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
<title>刷卡交易记录</title>
<script type="text/javascript">

	$(function(){
      // 默认选中已登录的产品类型
      var prodDefault='${sessionScope.prod}'+"";
      if(prodDefault){
          $('#weChat_prod').val(prodDefault);
      }

		  $(":radio").click(function(){
			   	$("#date-start").val("");
			   	$("#date-end").val("");
		  });
 	});

	 Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}


	//查询提交
	 function check_search(){
	  var html1 = $("#rowss");
	  html1.empty();
	  $("#sum").empty();
	  var txnDay = $("#date-start").val();
      var txnDay1=$("#date-end").val();
      var prod=$("#weChat_prod").val();
      var txn1 =$('input:radio:checked').val()
      var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
      var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
      var thepage = 0;
	  if(((end-start)/(24*60*60*1000))>31){
	 		alert("对不起，日期范围须31天内！");
	  }else{
	  	if(txnDay!=""&&txnDay1!=""){
	  		$('input:radio:checked').attr("checked",false); 
	  	}
	  	if(txn1=='2'&&txnDay==""&&txnDay1==""){
	  		var day = new Date();
    		day.setDate(01);
    		txnDay =day.Format("yyyy-MM-dd");
    		txnDay1=new Date().Format("yyyy-MM-dd");
	   	}else if(txn1=='1'&&txnDay==""&&txnDay1==""){
	   		var day = new Date();
	   		day.setDate(day.getDate()-1);
	   		txnDay =day.Format("yyyy-MM-dd");
    		txnDay1=txnDay;
	   	}
	 	var params= {
		    "page":"1",                  //开始页
		    "rowsPerPage":"13",        //本次查询多少页
		    "txnDay1":txnDay1,
		    "txnDay":txnDay,
		    "prod":prod,
		    "mid":$("#xmh_mid_20130").val(),
		    "openid":$("#openid").val()
		 };
		 var url = "${ctx}/phone/phoneWechatPublicAcc_listCheckUnitDealDetail.action?mid="+$("#xmh_mid_20130").val()+"&txnDay="+txnDay+"&txnDay1="+txnDay1+"&prod="+prod+"&openid="+$("#openid").val();//+"&rowsPerPage="+rowsPerPage+"&page="+page;
		 var dataArray=0;
		 var cardPager = AjaxPager.$(url, params, mycard_load, "rowss");
		 cardPager.runAs();
	 
				function mycard_load(data) {
				   var dataArray = data.obj.rows;
				    if(!dataArray.length&&thepage==0){
					    alert("该日期范围内无数据！");
					} 
					thepage++;
				    var rows = $("#rowss");
				    rows.css("display", "");
					var total = data.obj.total;
				    var sum = data.countTxnAmount;
				    var sumMDA = data.numberUnits;
				    html1.empty();
				    $.each(dataArray, function (i, json) {
				        var TXNDAY=json.TXNDAY;
			 			var CARDPAN=json.CARDPAN.substring(json.CARDPAN.length-4);
						var txnamount=json.TXNAMOUNT;
						var mda=json.SMAMOUNT;
				        rows.append("<table><tr><td class=\"col-xs-5\">"+TXNDAY+"</td><td class=\"col-xs-3\">*"+CARDPAN+"</td><td class=\"col-xs-4\">"+txnamount+"&nbsp;&nbsp;</td><td class=\"col-xs-4\">"+mda+"&nbsp;&nbsp;</td></tr></table>");
				    });
				    var mypage=Math.ceil(total/13);
				    if(thepage <= mypage){
					    $("#sum").empty();
					    $("#sum").append("<table><tr><td class='col-xs-3'><font style='font-size: 15px'>笔数</font></td><td class='col-xs-3'><font style='font-size: 15px'>交易额(元)</font></td><td class='col-xs-3'><font style='font-size: 15px'>结算额(元)</font></td></tr><tr><td class='col-xs-3'><font style='font-size: 15px'>"+total+"</font></td><td class='col-xs-3'><font style='font-size: 15px'>"+sum+"</font></td><td class='col-xs-3'><font style='font-size: 15px'>"+sumMDA+"</font></td></tr></table>");
					}
				    return rows;
				} 
				
			}
		} 
	 function closeTxn1(){
		 $('input:radio:checked').attr("checked",false); 
	 }
	
</script>
<style>
 /*全局的css样式*/
        html{
            font-size: 625%;
            height: 100%;
            width: 100%;
        }
        body{
            height: 100%;
            width: 100%;
            font-family: "\5FAE\8F6F\96C5\9ED1", Helvetica, sans-serif;
            font-size: .15rem;
            background:#f2f3f8 ;
        }
        .containerc{
            height: 100%;
            width: 100%;
        }
         .transaction-contentc{
            padding: .05rem;
            height: 100%;
            width: 99%;
        }
        /*交易日期查询样式*/
        .transaction-query{
            /*height: 22%;*/
            height: 1.5rem;
            width: 100%;
            margin-bottom: .1rem;
            background-color: #fff;
            border-radius: 3px;
            box-shadow: 1px 1px 1px 1px #e1e1e1;
        }
        .transaction-query .transaction-date{
            overflow: auto;
            zoom: 1;
            width: 100%;
            padding: .02rem .02rem;
        }
        .transaction-query p:nth-of-type(1){
            width: 23%;
            /*width: .6rem;*/
            height: .3rem;
            line-height: .3rem;
            text-align: center;
            word-break: keep-all;
            white-space: nowrap;
            float: left;
            margin: 0;
            padding:0;
            font-size: .16rem;
        }
        .transaction-query p:nth-of-type(2){
            font-size: .16rem;
            float: left;
            /*margin: 0 .05rem;*/
            margin: 0;
            padding: 0;
            /*width: 5%;*/
            height: .3rem;
            line-height: .3rem;
        }
        .transaction-date input{
            float: left;
            width: 35%;
            display: block;
            height: .3rem;
            line-height: .3rem;
            font-size: .14rem;
            text-align: center;
            margin: 0;
            padding: 0;
            border: 1px solid #dcdcdc;
            border-radius: 2px;
            
             /*清除iOS系统默认样式*/
            -webkit-appearance:none;
            appearance:none;
            outline:none;
            -webkit-tap-highlight-color:rgba(0,0,0,0);
        }
        .transaction-query-btn{
            width: 1.5rem;
            height: .3rem;
            margin: .1rem auto;
            display: block;
            border: none;
            font-size: .16rem;
            color: #fff;
            background-color: #00a0e9;
            border-radius: 4px;
            margin-top: .2rem;
         }
         /*交易内容显示样式*/
        .transaction-content{
            background-color: #fff;
            height: 5.4rem;
            border-radius: 4px;
        }
        .transaction-content table{
            width: 100%;
            border-collapse: collapse;
        }
        .transaction-content table thead tr th{
            word-break: keep-all;
            white-space: nowrap;
            text-align: center;
            padding: 0;
            height: .3rem;
        }
        .transaction-content table thead tr th p{
            font-size: .16rem;
            margin: 0;
            color: #fff;
        }
        .yuan{
            font-size: .12rem;
        }
        .special{
            border-right: 1px solid #fff;
        }
        .transaction-content table thead{
            background-color: #5d5d5d;
        }
        .transaction-content-all{
            height: 90%;
        }
        .transaction-content-all{
            overflow: scroll;
        }
        .transaction-content-all table{
            width: 100%;
        }
        ::-webkit-scrollbar {/*隐藏滚轮*/
            display: none;
        }
        .transaction-content-all table tbody tr{
            border-bottom: 1px solid #dcdcdc;
        }
        .transaction-content-all table tbody tr td{
            word-break: keep-all;
            white-space: nowrap;
            font-size: .16rem;
            /**text-align: center;**/
            padding: 0;
            padding-bottom: .05rem;
        }
        .mysum{
        text-align: center;
        	padding-top:.06rem;
            background: #f2f3f8;
        }
         .mysum table tbody tr td{
            word-break: keep-all;
            white-space: nowrap;
            font-size: .10rem;
            text-align: center;
            padding: 0;
            padding-top: .05rem;
            padding-bottom: .05rem;
        }
        .col-xs-5{
        	width: 22%;
        	text-align: center;
        }
        .col-xs-3{
        	width: 23%;
        	text-align: center;
        }
        .col-xs-4{
        	width: 23%;
        	text-align: right;
        }
        #rowss table:nth-child(odd){background-color:#fff;}
		#rowss table:nth-child(even){background-color:#f2f3f8;}
		
</style>
<body>
	<div class="containerc">
    <div class="transaction-contentc">
    <div class="transaction-query">
		<form id="weixin_jiaoyi" >
		<input type="hidden" id="xmh_mid_20130" name="mid"   value="<%=userBean.getLoginName()%>" />
		<input type="hidden" id="openid" name="openid" value="<%=openid%>" />
    <div class="transaction-date">
      <p style="margin-left: 2%;margin-right: 2%;">产品名称：</p >
      <select class="form-control" id="weChat_prod" name="prod" style="line-height: 0.31rem;width: auto;">
        <option value="10000">会员宝秒到</option>
<%--        <option value="10005">会员宝收银台</option>--%>
        <option value="10006">会员宝PLUS</option>
        <option value="10007">会员宝极速版</option>
        <option value="10009">会员宝Pro</option>
      </select>
    </div>
		<div class="transaction-date">
               	<p style="margin-left: 2%">交易日期：</p><input id="date-start" name="txnDay" type="date" placeholder="2017-01-01" style="margin-left: 2%;width: 30%" onchange="closeTxn1();"/>
                <p style="margin-left: 2%;width: 5%">-</p>
                <input id="date-end" name="txnDay1" type="date" placeholder="2017-10-01"  style="margin-left: 2%;width: 30%" onchange="closeTxn1();"/>
           	 	</div>
        <p style="margin-left: 2.5%; float: left">快速查询：</p>
					<input name="txn1" type="radio" checked="checked" value="1"
						style="margin-left: 2.5%; width: 5%; float: left; height: .3rem;" />
					<p style="margin-left: 2%; float: left">昨日</p>
					<input name="txn1" type="radio" value="2"
						style="margin-left: 6%; width: 5%; float: left; height: .3rem;" />
					<p style="margin-left: 2%; float: left; line-height: .3rem;">当月</p>
					<br>
					<button type="button" class="transaction-query-btn"
						onclick="check_search();">查询</button>
		</form>
	</div>  
	<!-- 交易内容显示-->
        <div  class="transaction-content">
        <div id = "sum" class= "mysum">
        </div>
            <table style="margin-top: 0.2rem">
                <thead class="row">
                <tr>
                    <th class="col-xs-5"><p>交易日期</p></th>
                    <th class="col-xs-3"><p>卡号</p></th>
                    <th class="col-xs-3"><p>交易额<span class="yuan">(元)</span></p></th>
                    <th class="col-xs-3"><p>结算额<span class="yuan">(元)</span></p></th>
                </tr>
                </thead>
             </table>
           
            <div id="rowss" class="transaction-content-all"> 
	          
            </div>
          </div>
   	 
</div>	 
</div>
</body>