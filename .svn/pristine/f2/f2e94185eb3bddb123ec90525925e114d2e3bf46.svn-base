/**
 * 微信公众号公用方法
 * @author lxg by 20190614
 */

/**
 * 微信公众号跳转登录成功页面
 * @param defaultProd 默认产品类型不请求后台
 * @param newValue  新产品类型,请求后台
 * @param elementId 请求后台的form表单id
 * @param url 请求后台地址
 */
function jumpLoginSuccess(defaultProd,newValue,elementId,url) {
    if(defaultProd!=newValue){
        var loginFrom = document.getElementById(elementId);
        loginFrom.action=url;
        loginFrom.submit();
    }
}