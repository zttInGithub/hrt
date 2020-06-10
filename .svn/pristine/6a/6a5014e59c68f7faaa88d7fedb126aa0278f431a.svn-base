//接受传的参数
function GetInfo(info) {
	var regn = new RegExp("(^|&)" + info + "=([^&]*)(&|$)");
	var ri = window.location.search.substr(1).match(regn);
    // var tt = "http://localhost:8888/HrtApp/promoteinfo.html?orderid=orderid1&mid=mid1&trantype=trantype1&amount=amount33&trantime=trantime11";
	// var ri = tt.substr(1).match(regn);
	if(ri != null) {
		return decodeURI(ri[2])
	}
}

<!--orderid mid trantype amount trantime-->
// var orderid = GetInfo('orderid');
var orderid = document.paramsFrom.orderid.value;
// var mid = GetInfo('mid');
var mid = document.paramsFrom.mid.value;
// var trantype = GetInfo('trantype');
var trantype = document.paramsFrom.trantype.value;
// var amount = GetInfo('amount');
var amount = document.paramsFrom.amount.value;
// var trantime = GetInfo('trantime');
var trantime = document.paramsFrom.trantime.value;
var problem = "";


// console.log(reqData)
// 商编，交易时间，订单号，交易金额，交易状态(0.成功)，交易类型(1.微信,2.支付宝)
var title = '您好，我们发现您有一笔【'+(trantype==1?'微信':'支付宝')+'】交易未成功，交易金额为【'+amount+'元】。请在以下列表中选择未成功的原因，谢谢您的反馈！';
// console.log(title)
if(amount!=undefined){
    document.getElementById('protitle').innerHTML=title;
}

$("li").click(function() {
	if($(this).hasClass("selected")) {
		$(this).removeClass("selected");
	} else {
		$(this).addClass("selected").siblings().removeClass("selected");
        problem=$(this).html();
	}
})

$("#btnNext").click(function() {
	var other = $("#other").val();
    var map = new Map;
    map.set("提示商户收款存在异常",1);
    map.set("提示谨防刷单、投资等",2);
    map.set("无法选择花呗/信用卡支付",3);
    map.set("提示不支持信用卡，请选择储蓄卡",4);
    map.set("提示交易异常",5);
    map.set("提示URL未注册",6);
    map.set("密码输入错误/操作失误",7);
    map.set("不想支付",8);
    // <li>提示商户收款存在异常</li>
    // <li>提示谨防刷单、投资等</li>
    // <li>无法选择花呗/信用卡支付</li>
    // <li>提示不支持信用卡，请选择储蓄卡</li>
    // <li>提示交易异常</li>
    // <li>提示URL未注册</li>
    // <li>密码输入错误/操作失误</li>
    // <li>不想支付</li>

    <!--orderid mid trantype amount trantime-->
    var reqData = JSON.stringify({
        "orderid": orderid,
        "mid": mid,
        "trantype": trantype,
        "amount": amount,
        "trantime": trantime,
        "problem": map.get(problem),
        "other": other
    });


	var flag = false;
	var flagtext = false;
	for(var i = 0; i < $("li").length; i++) {
		if($("li").eq(i).hasClass('selected')) {
			flag = true;
		}
	}
	if(other != '') {
		flagtext = true;
	}
	if(flag || flagtext) {
	    // console.log(data)
		// alert("发请求提交数据")//发请求
        $(".loading").hide();
        $.ajax({
            type: "post",
            url: "${ctx}/phone/phoneWechatPublicAcc_problemFeedback.action",
    		dataType: "json",
            // dataType:"jsonp",
    		// contentType: "application/json",
            data: {"data":reqData},
            beforeSend: function(resp) {
                $(".loading").show();
            },
            success: function(data) {
                $(".loading").hide();
                //返回结果处理
                if(data.obj = '1') {
                    // {"msg":"反馈成功","numberUnits":"","obj":"1","sessionExpire":false,"success":true}
                    alert(data.msg)
                    // window.close();
                    window.location.reload()
                } else {
                    settimeWarn(".error_wrap");
                    $(".errorp").html(data.msg);
                    window.location.reload()
                }
            },
            error:function() {
                $(".errorp").html("反馈错误");
                window.location.reload()
            }
        });
	} else {
		alert("请回答")
	}
});