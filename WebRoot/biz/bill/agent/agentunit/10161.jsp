<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
    $.extend($.fn.validatebox.defaults.rules, {
        intOrFloat: { // 验证整数或小数   
            validator: function(value) {
                if (/^\d{0,8}(\.\d{1,2})?$/.test(value)) {
                    if ("0.00" == value || "0.0" == value || "0" == value) {
                        return false;
                    }
                } else {
                    return false
                }
                return true;
            },
            message: '请输入整数或有效两位小数，并确保格式正确'
        }
    });

    function num(obj){
        obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
    }
</script>

<!-- <div class="easyui-layout" data-options="fit:true, border:false"> -->
<div style="height:100%; padding:0px;">
    <form id="cash10161" style="padding:1% 2% 0 2%" method="post">
        <table class="table">
            <tr>
                <th>提现金额：</th>
                <td>
                    <input type="text" id="pushCuramt" name="curAmt" style="width:200px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" maxlength="25"  onkeyup="num(this)" />
                    <font color="red">&nbsp;*</font>
                </td>
            </tr>
            <tr>
                <th>入账卡号：</th>
                <td>
                    <input type="text" name="bankaccname" style="width:200px;" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <th>入帐名称：</th>
                <td>
                    <input type="text" name="bankaccno" style="width:200px;" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <th>开户行名称：</th>
                <td>
                    <input type="text" name="bankbranch" style="width:200px;" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <th>支付系统行号：</th>
                <td>
                    <input type="text" name="paymentnumber" style="width:200px;" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <font color="red">&nbsp;*</font>提现金额不能大于当前可提现金额
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- </div> -->
