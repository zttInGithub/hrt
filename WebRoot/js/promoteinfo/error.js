$(function() {
	pushHistory();
	window.addEventListener("popstate", function(e) {
		self.location.reload();
	}, false);
});

function pushHistory() {
	var state = {
		title: "",
		url: "#"
	};
	window.history.replaceState(state, "", "#");
};
//错误提示5秒自动关闭，点击蒙层自动关闭
var countdownW = 5; //错误提示2s操作
function settimeWarn(obj) {
	if(countdownW == 0) {
		$(".error").hide();
		countdownW = 5;
		return;
	} else {
		$(".error").show();
		countdownW--;
	}
	setTimeout(function() {
		$(".error").hide();
		countdownW = 5;

	}, 5000);
}
$(".error").click(function(){
	$(".error").hide();
});


//成功1.5秒后自动关闭，点击蒙层自动关闭
var countdownW = 2; //错误提示2s操作
function settimeWarn2(obj,url) {
	if(countdownW == 0) {
		$(".success").hide();
		countdownW = 2;
		return;
	} else {
		$(".success").show();
		countdownW--;
	}
	setTimeout(function() {
		$(".success").hide();
		countdownW = 2;
		location.href=url;

	}, 1500);
}
//使用
//	settimeWarn2(".success")